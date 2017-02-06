package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.BysDegreeDao;
import cn.gilight.dmm.teaching.dao.BysQxDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;

@Repository("bysDegreeDao")
public class BysDegreeDaoImpl implements BysDegreeDao {

	@Resource
	private BysQxDao bysQxDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao baseDao;
	
	@Override
	public List<Double> getValueByList(String stuSql,String type,String schoolYear){
		String gpaSql = "",str="";
		if(type.equals("gpa")){
			gpaSql= businessDao.getStuGpaSql(stuSql, null, null,Constant.SCORE_GPA_BASE_CODE);
			str ="gpa";
		}else{
			gpaSql = businessDao.getStuScoreAvgSql(stuSql, null, null);
			str ="score";
		}
	    String valueSql = "select t."+str+" from ("+gpaSql+") t where t."+str+" is not null";
	    return baseDao.queryForListDouble(valueSql);
	}
	@Override
	public List<Map<String,Object>> getGpaGroup(String stuSql,String type){
		String gpaSql = "",str="";
		List<Map<String,Object>> list = getGpaWhereSql(),
                result = new ArrayList<Map<String,Object>>();
		if(type.equals("gpa")){
			gpaSql= businessDao.getStuGpaSql(stuSql, null, null,Constant.SCORE_GPA_BASE_CODE);
			str ="gpa";
		}else{
			gpaSql = businessDao.getStuScoreAvgSql(stuSql, null, null);
			str ="score";
			list = Constant.getScoreGroup();
		}
		int count = baseDao.queryForCount(gpaSql);
		for(Map<String,Object> map : list ){
			String id = MapUtils.getString(map, "id");
			String mc = MapUtils.getString(map, "mc1");
			String[] ary = id.split(",");
			String start = ary[0].equals("null") || ary[0] == null?
					"":" and t."+str+" >= "+ary[0]+"",
					end = ary[1].equals("null") || ary[1] == null?
					"":" and t."+str+" < "+ary[1]+"";
			String sql = "select '"+id+"' as id,'"+mc+"' as name, case when "+count+" =0 then 0 else"
					+ " round(count(0)*100/"+count+",0) end as value from ("+gpaSql+") t"
					+ " where  1=1 "+start+end;
			Map<String,Object> temp = baseDao.queryMapInLowerKey(sql);
			result.add(temp);
		}
		return result;
	}
	@Override
	public List<Map<String,Object>> getLvByDept(String schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stuSql = businessDao.getStuSql(deptList,stuAdvancedList);
		String allStuSql = bysQxDao.getBysSql(schoolYear, stuSql, null, null),
			   bySql = bysQxDao.getBysSql(schoolYear, stuSql, true, null),
			   sySql = bysQxDao.getBysSql(schoolYear, stuSql, null, true);
		allStuSql = "select t.dept_id||','||t.major_id||','||t.class_id as id,t.dept_id ,t.major_id,t.class_id,count(0) as value from ("+allStuSql+") t "
				+ " group by t.dept_id,t.major_id,t.class_id";
		bySql = "select t.dept_id||','||t.major_id||','||t.class_id as id,t.dept_id ,t.major_id,t.class_id,count(0) as value from ("+bySql+") t "
				+ " group by t.dept_id,t.major_id,t.class_id";
		sySql = "select t.dept_id||','||t.major_id||','||t.class_id as id,t.dept_id ,t.major_id,t.class_id,count(0) as value from ("+sySql+") t "
				+ " group by t.dept_id,t.major_id,t.class_id";
		String sql = "select a.dept_id,a.major_id,a.class_id,a.value as \"all\",b.value as \"by\",c.value as sy from ("+allStuSql+") a,("+bySql+") b,("+sySql+") c "
				+ " where a.id = b.id and a.id = c.id ";
		Integer grade = AdvancedUtil.getStuGrade(stuAdvancedList);
		String deptSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, Integer.parseInt(schoolYear.substring(0, 4)), grade);
		String orderSql = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, Integer.parseInt(schoolYear.substring(0, 4)), grade);
		String reSql = "select  a.id as code, a.name_ as field,a.next_dept_order, case when sum(dept.\"all\") >0 then "
				+ " round(sum(dept.\"by\")*100/sum(dept.\"all\"),2) else 0 end as bylv,"
				+ " case when sum(dept.\"all\")>0 then round(sum(dept.sy)*100/sum(dept.\"all\"),2) else 0 end as sylv from ("+deptSql+") dept "
				+ " ,("+orderSql+") a where  dept.next_dept_code = a.id group by  a.id,a.name_,a.next_dept_order "
				+ " order by a.next_dept_order";
		return  baseDao.queryListInLowerKey(reSql);
	}
	@Override
	public List<Map<String,Object>> getListByDeptList (String schoolYear,String deptSql){
		String sql = "select c.id,c.name_ as name,a.sum_count as \"all\",a.rel_count as \"by\",a.graduation_scale as bylv,"
				+ " a.last_graduation_scale as bybh,a.degree_count as sy,a.degree_scale as sylv,a.last_degree_scale as sybh" 
				+ " from t_stu_graduate_result_dept a,("+deptSql+") b,"+Constant.TABLE_T_Code_Dept_Teach+" c "
				+ " where a.school_year = '"+schoolYear+"' and a.dept_id = b.id and b.id = c.id ";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> getListBySubjectList (String schoolYear,String subjectSql){
		String sql = "select b.id,b.name_ as name,a.sum_count as \"all\",a.rel_count as \"by\",a.graduation_scale as bylv,"
				+ " a.last_graduation_scale as bybh,a.degree_count as sy,a.degree_scale as sylv,a.last_degree_scale as sybh" 
				+ " from t_stu_graduate_result_subject a left join ("+subjectSql+") b on a.subject_id = b.id "
				+ " where a.school_year = '"+schoolYear+"'  ";
		return baseDao.queryListInLowerKey(sql);
	}
	private List<Map<String,Object>> getGpaWhereSql(){
		Object[][] list = Constant.Score_Gpa_Group;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.length;i++){
			Map<String, Object> temp = new HashMap<String, Object>();
			Object[] ary = list[i];
			temp.put("id", ary[0]+","+ary[1]);
			temp.put("mc1", ary[2]);
			result.add(temp);
		}
		return result;
	}
	
}
