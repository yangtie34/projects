package cn.gilight.dmm.xg.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.ScholarshipDao;
import cn.gilight.dmm.xg.pojo.ScholarshipTop;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 奖学金
 * 
 * @author xuebl
 * @date 2016年5月19日 下午5:48:31
 */
@Repository("scholarshipDao")
public class ScholarshipDaoImpl implements ScholarshipDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	
	
	@Override
	public String getScholarshipSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table){
		return getScholarshipSql(schoolYear, deptList, stuAdvancedList, ship_schoolYear, null, table);
	}
	
	@Override
	public String getScholarshipSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, String ship_codes, Constant.JCZD_Table table){
		String ship_schoolYear_sql = ship_schoolYear==null ? "" : (" and t.school_year='" +ship_schoolYear+"-"+(ship_schoolYear+1)+ "'"),
			   ship_codes_sql = ship_codes==null ? "" : (" and t."+table.getCode()+" in('"+ship_codes+"')");
		return "select t.* from "+table.getTable()+" t,("+ businessDao.getStuSql(schoolYear, deptList, stuAdvancedList) +")stu"
		+ " where t.stu_id=stu.no_ " +ship_schoolYear_sql+ship_codes_sql;
	}
	
	@Override
	public String getScholarshipStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table){
		String sql = getScholarshipSql(schoolYear, deptList, stuAdvancedList, ship_schoolYear, table);
		return "select distinct(stu_id) no_ from ("+ sql +")";
	}
	
	@Override
	public String getScholarshipStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, String ship_codes, Constant.JCZD_Table table){
		String sql = getScholarshipSql(schoolYear, deptList, stuAdvancedList, ship_schoolYear, ship_codes, table);
		return "select distinct(stu_id) no_ from ("+ sql +")";
	}
	
	@Override
	public Map<String, Object> queryCountAndMoney(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table){
		String scholarshipSql = getScholarshipSql(schoolYear, deptList, stuAdvancedList, ship_schoolYear, table);
		String sql = "select sum(t.money) money, count(0) count from " +table.getTable()+ " t,(" +scholarshipSql+ ")awd where t.id = awd.id";
		return baseDao.queryMapInLowerKey(sql, Types.NUMERIC, Types.NUMERIC);
	}
	
	@Override
	public List<Map<String, Object>> queryTypeList(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table){
		String scholarshipSql = getScholarshipSql(schoolYear, deptList, stuAdvancedList, ship_schoolYear, table);
		String sql = "select code.name_ name, code.code_ code, t.money, t.count from"
				+ " (select t." +table.getCode()+ " code, count(0) count, sum(t.money) money from "
				+ table.getTable()+ " t,(" +scholarshipSql+ ")awd where t.id = awd.id group by t." +table.getCode()+ ") t"
				+ " right join t_code code on t.code=code.code_ where code.istrue=1 and code.code_type='" +table.getCodeType()+ "' order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC);
	}
	
	@Override
	public List<ScholarshipTop> queryTopList(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, String desc_column, Constant.JCZD_Table table){
		String scholarshipSql = getScholarshipSql(schoolYear, deptList, stuAdvancedList, ship_schoolYear, table);
		String order_sql = "";
		if(desc_column != null) order_sql = " order by "+desc_column+" desc";
		String sql = "select s.*,stu.name_ name from (select t.stu_id, count(0) count, sum(t.money) money, wm_concat(t.school_year) school_years from "+table.getTable()+" t,"
				+ " (" +scholarshipSql+ ")awd, t_stu stu where t.id = awd.id and t.stu_id = stu.no_ group by t.stu_id" + order_sql +") s"
				+ " left join t_stu stu on s.stu_id = stu.no_";
		return baseDao.queryForListBean(sql, ScholarshipTop.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getStuDetailSql(Integer schoolYear,List<String> deptList, List<AdvancedParam> stuAdvancedList,Map<String, Object> keyValue, List<String> fields,Constant.JCZD_Table table){
		String stuDetailSql=null,stuSql=null;
		String shipNameSql="select name_,code_ from t_code code  where code.istrue=1 and code.code_type='" +table.getCodeType()+ "' order by code.order_,code.code_";
		for(Map.Entry<String, Object> entry : keyValue.entrySet()){
			String type = entry.getKey();
			switch(type){
				case "dept":
					Map<String,String> map=(Map<String,String>)entry.getValue();
					String deptId=MapUtils.getString(map, "deptId");
					Integer level=MapUtils.getInteger(map, "level");//机构层次
					String code=MapUtils.getString(map, "code");
					deptList=PmsUtils.getDeptListByDeptAndLevel(deptId,level);//code 奖学金code,level，部门层次,deptId 部门id
					String ScholarshipSql=getScholarshipSql(schoolYear, deptList, stuAdvancedList,schoolYear ,code, table);
					stuSql="select t.* ,code.name_ as shipName from (select stu.*,awd.money,awd."+table.getCode()+" from ( "+ScholarshipSql+" ) awd,t_stu stu where awd.stu_id=stu.no_) t,("+shipNameSql+") code "
							+ " where t."+table.getCode()+"=code.code_";
					break;
				case "all":
					ScholarshipSql=getScholarshipSql(schoolYear, deptList, stuAdvancedList,schoolYear ,null, table);
					stuSql="select t.* ,code.name_ as shipName from (select stu.*,awd.money,awd."+table.getCode()+" from ( "+ScholarshipSql+" ) awd,t_stu stu where awd.stu_id=stu.no_) t,("+shipNameSql+") code "
							+ " where t."+table.getCode()+"=code.code_";
					break;
				case "type":
					map=(Map<String,String>)entry.getValue();
					code=MapUtils.getString(map, "code");//奖学金编码
					ScholarshipSql=getScholarshipSql(schoolYear, deptList, stuAdvancedList,schoolYear ,code, table);
					stuSql="select t.* ,code.name_ as shipName from (select stu.*,awd.money,awd."+table.getCode()+" from ( "+ScholarshipSql+" ) awd,t_stu stu where awd.stu_id=stu.no_) t,("+shipNameSql+") code "
							+ " where t."+table.getCode()+"=code.code_";
					break;
				case "sex" :
					map=(Map<String,String>)entry.getValue();
					code=MapUtils.getString(map, "code");//性别编码
					String grade=MapUtils.getString(map, "grade");//年级
					stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_GRADE, grade));
					stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Common_SEX_CODE, code));
					ScholarshipSql=getScholarshipStuIdSql(schoolYear, deptList, stuAdvancedList,schoolYear ,null, table);
					String awd="select awd.* ,code.name_ as shipName from "+table.getTable()+" awd ,("+shipNameSql+") code where awd."+table.getCode()+"=code.code_";
					ScholarshipSql="select awd.stu_id,wm_concat(awd.money) as money,wm_concat(awd.shipName) as shipName from ("+ScholarshipSql+") t ,("+awd+") awd where t.no_=awd.stu_id group by awd.stu_id ";
					stuSql=" select stu.* ,awd.shipName ,awd.money from ("+ScholarshipSql+") awd ,t_stu stu where awd.stu_id=stu.no_ ";
					break;
				case "year":
					map=(Map<String,String>)entry.getValue();
					code=MapUtils.getString(map, "code");//奖项编码
					schoolYear=Integer.parseInt((MapUtils.getString(map, "schoolYear").substring(0, 4)));//历史学年
					ScholarshipSql=getScholarshipSql(schoolYear, deptList, stuAdvancedList,schoolYear ,code, table);
					stuSql="select t.* ,code.name_ as shipName from (select stu.*,awd.money,awd."+table.getCode()+" from ( "+ScholarshipSql+" ) awd,t_stu stu where awd.stu_id=stu.no_) t,("+shipNameSql+") code "
							+ " where t."+table.getCode()+"=code.code_";
					break;
			}
				
		}
		stuDetailSql =getStuDetailSql(stuSql);
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql =  "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
		}
		return stuDetailSql;
		
	}
	//获取学生违纪处分信息 以及日期
			private String getStuDetailSql(String stuSql){
				String sql = "select t.id,t.no_ no,t.name_ name, sex.name_ sexmc, sex.code_ sexid, dept.name_ deptmc, t.dept_id deptid,"
						+ " major.name_ majormc, t.major_id majorid, class.name_ classmc, t.class_id,t.enroll_grade,t.length_schooling,"
						+ " xzqh.sname shengmc, xzqh.sid shengid, shi.sname shimc, shi.sid shiid, shi.name xianmc, shi.id xianid,t.shipName,t.money"
						+ " from ("+stuSql+")t"
						+ " left join t_code sex on t.sex_code=sex.code_ and sex.code_type='"+Constant.CODE_SEX_CODE+"'"
						+ " left join t_code_dept_teach dept on t.dept_id=dept.id"
						+ " left join t_code_dept_teach major on t.major_id=major.id"
						+ " left join t_classes class on t.class_id=class.no_"
						+ " left join (select s.id sid, s.name_ sname, q.id from t_code_admini_div s, t_code_admini_div q"
						+ " where substr(q.path_,1,4)=s.path_) xzqh on t.STU_ORIGIN_ID=xzqh.id"
						+ " left join (select s.id sid, s.name_ sname, q.id, q.name_ name from t_code_admini_div s, t_code_admini_div q"
						+ " where substr(q.path_,1,8)=s.path_) shi on t.stu_origin_id=shi.id";
				return sql;
			}
	
}