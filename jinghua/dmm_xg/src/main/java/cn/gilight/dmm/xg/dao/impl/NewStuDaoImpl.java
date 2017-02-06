package cn.gilight.dmm.xg.dao.impl;

import java.sql.Types;
import java.text.DecimalFormat;
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
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.NewStuDao;
import cn.gilight.dmm.xg.dao.StuFromDao;
import cn.gilight.dmm.xg.service.NewStuService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;

@Repository("newStuDao")
public class NewStuDaoImpl implements NewStuDao {
	@Resource
	private BaseDao basedao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private StuFromDao stuFromDao;
	public static final int ISREGISTER = 1;//已报到
	public static final int NOTREGISTER = 0;//未报到
	public static final int FIELDNUM = 8;//控制贫困生线图显示的年数
	public static final String WWH = "未维护";//未报到
	@Override
	public List<Map<String,Object>> queryIsRegisterCount(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu = queryStuSql(schoolYear,deptList,stuAdvancedList);
		String sql ="select edu.id as code,edu.name_ as name,stu.isregister as register,count(*) as value  from ("+stu+") stu"
				+ "  left join t_code_education edu on stu.edu_id"
				+ " = edu.id group by edu.name_,stu.isregister,edu.id";
		return basedao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryEdu(int schoolYear,List<String> deptList,List<AdvancedParam> advancedList){
		String stu = queryStuSql(schoolYear,deptList,advancedList);
		String str1 = " inner join t_code_education edu on stu.edu_id = edu.id group by edu.id,edu.name_";
		String sql= "select edu.id as code,edu.name_ as name,count(*) as value from ("+stu+") stu "+str1;
		List<Map<String,Object>> list = basedao.queryListInLowerKey(sql);
//		List<Map<String,Object>> list1 = basedao.queryListInLowerKey(sql1);
//		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
//		for(int i=0;i<list1.size();i++){
//			Map<String,Object> map = list1.get(i);
//			String id = MapUtils.getString(map, "code");
//			for(int k =0;k<list.size();k++){
//				Map<String,Object> map1 = list.get(k);
//				String ids = MapUtils.getString(map1, "code");
//				if(id != null && ids != null && id.equals(ids)){
//					result.add(map1);
//				}
//			}
//			if(result.size()<i+1){
//				result.add(map);
//			}
//		}
		return list;
	}
	@Override
	public List<Map<String,Object>> queryCountAndLv(Integer schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu = queryStuSql(null,deptList,stuAdvancedList);
		String str = "select t.enroll_grade as name,count(0) as value from ("+stu+") t where t.isregister="+ISREGISTER+" "
				+ " and t.enroll_grade > '"+(schoolYear-10)+"'"
				+ " group by t.enroll_grade ";//各入学年级已报到学生
		String str2 = "select t.enroll_grade as name,count(0) as value from ("+stu+") t "
				+ " where t.enroll_grade > '"+(schoolYear-10)+"'"
				+ " group by t.enroll_grade ";
		String sql = "select a.name,a.name as code,a.value as count,round((a.value*100/b.value),2) as lv from ("+str+") a ,("+str2+") b "
				+ " where a.name = b.name group by a.name,a.value,b.value order by a.name";
		return basedao.queryListInLowerKey(sql);
	}
	@Override
	public Map<String,Integer> queryMinAndMax(int schoolYear,List<String> deptList){
		String stu = queryStuSql(null,deptList,null);
		String sql ="select min(stu.enroll_grade) as min,"
				+ "  max(stu.enroll_grade) max from ("+stu+") stu ";
		Map<String,Object> map  = basedao.queryMapInLowerKey(sql);
		int min =schoolYear; int max = schoolYear;
		if (MapUtils.getString(map, "min") == null&&MapUtils.getString(map, "max") ==null){
			
		}else{
		 min = MapUtils.getIntValue(map, "min");
		 max = MapUtils.getIntValue(map, "max");
		}
		Map<String,Integer> result = new HashMap<String,Integer>();
		int x =schoolYear-min;
		if (x>(FIELDNUM-2)){
			result.put("min", schoolYear-(FIELDNUM-1));
		}else{
			result.put("min", min);
		}
		int y = result.get("min");
		if(max<y){ 
		result.put("max", schoolYear);
		}else{
		result.put("max", max);	
		}
		result.put("now",schoolYear);
		return result;	
	}
	@Override
	public List<Map<String,Object>> queryDeptAvgLv(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu = queryStuSql(null,deptList,stuAdvancedList);
		String str ="select stu.dept_id,stu.major_id,stu.class_id,stu.enroll_grade as name,count(0) as value"
				+ "  from ("+stu+") stu where stu.enroll_grade >"
				+(schoolYear-5)+" ";
		String str1 =" group by stu.enroll_grade,stu.dept_id,stu.major_id,stu.class_id ";
		String tb  =str+" and stu.isregister ="+ISREGISTER+str1;
		String tb1 = str+str1;
		String sql = "select a.dept_id,a.major_id,a.class_id,a.name,a.value as val1,b.value as val2 from ("
				+ tb+")a inner join ("+tb1+") b on a.name = b.name and a.dept_id = b.dept_id "
						+ " and a.major_id= b.major_id and a.class_id = b.class_id";
		String deptSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, null,null);
		String orderSql = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, null,null);
		String reSql = "select code,field,next_dept_order,round(avg(value),2) as value from (select a.id as code, a.name_ as field,a.next_dept_order,dept.name,sum(dept.val1)*100/sum(dept.val2) as value from ("+deptSql+") dept,("+orderSql+") a where dept.next_dept_code = a.id group by  a.id,a.name_,a.next_dept_order,dept.name "
			+ " order by a.next_dept_order ) group by code,field,next_dept_order order by next_dept_order";
		return basedao.queryListInLowerKey(reSql);
		
	}
	@Override
	public List<Map<String,Object>> queryCountByAny(int schoolYear,List<String> deptList,String table,List<AdvancedParam> stuAdvancedList){
			String stu = queryStuSql(null,deptList,stuAdvancedList);
			String sql = "select stu.enroll_grade as field,count(0) AS value from ("+stu+") stu inner join "+table+" dm "
					+ " on dm.stu_id = stu.no_ and substr(dm.school_year,0,4) = stu.enroll_grade "
					+ "  where stu.isregister ="+ISREGISTER +" and stu.enroll_grade > '"+(schoolYear-8)+"'"
							+ " group by stu.enroll_grade order by stu.enroll_grade ";
		return basedao.queryListInLowerKey(sql);
	}
	private int queryCountByAny(int schoolYear,List<String> deptList,String table,List<AdvancedParam> stuAdvancedList,int grade){
			String stu = queryStuSql(null,deptList,stuAdvancedList);
			String sql = "select nvl(count(0),0) AS value from ("+stu+") stu inner join "+table+" dm "
					+ " on dm.stu_id = stu.no_ and substr(dm.school_year,0,4) = stu.enroll_grade "
					+ "  where stu.isregister ="+ISREGISTER +" and stu.enroll_grade = '"+grade+"'";
		return basedao.queryForInt(sql);
	}
	@Override
	public List<Map<String,Object>> queryAnyPie(int schoolYear,List<String> deptList,String table,List<AdvancedParam> stuAdvancedList){
		String stu = queryStuSql(schoolYear,deptList,stuAdvancedList);
		String str = "select stu.* from ("+stu+") stu inner join "+table+" dm "
				+ " on dm.stu_id = stu.no_ and substr(dm.school_year,0,4) = stu.enroll_grade "
				+ "  where stu.isregister ="+ISREGISTER;
		String code = "select * from t_code where code_type ='ANMELDEN_CODE'";
		String sql = "select nvl(b.code_,'wwh') as code,nvl(b.name_,'"+WWH+"') as name,count(0) as value from ("+str+") a "
				+ " left join ("+code+") b on a.ANMELDEN_CODE = b.code_ group by "
				+ "b.code_,b.name_ ";
		List<Map<String,Object>> list = basedao.queryListInLowerKey(sql);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		int a = queryCountByAny(schoolYear, deptList, table, stuAdvancedList, schoolYear);
		int b = queryCountByAny((schoolYear-1), deptList, table, stuAdvancedList, (schoolYear-1));
		double zzl =MathUtils.get2Point(MathUtils.getDivisionResult((a-b), b, 4)*100);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", a);
		map.put("lv", zzl);
		map.put("year",schoolYear);
		result.add(0,map);result.addAll(list);
		return result;
	}
	/**
	 * 计算某一年入学新生的人数
	 * @param schoolYear
	 * @param deptList
	 * @return
	 */
	private Integer queryStuCount(Integer schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu = queryStuSql(schoolYear,deptList,stuAdvancedList);
		String sql = "select count(*) as value from ("+stu+") ";
		int result = basedao.queryForInt(sql);
		return result;
	}
	/**
	 * 获取学生数据查询sql
	 * @param schoolYear
	 * @param deptList
	 * @return
	 */
	@Override
	public String queryStuSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList){
		return businessDao.getNewStuSql(schoolYear,deptList, stuAdvancedList);
	}
	
	@Override
	public String getStuSql(List<String> deptList, List<AdvancedParam> stuAdvancedList){
		return businessDao.getNewStuSql(deptList, stuAdvancedList);
	}
}
