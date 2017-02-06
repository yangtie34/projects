package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.CourseDao;
import cn.gilight.framework.base.dao.BaseDao;
@Repository("courseDao")
public class CourseDaoImpl implements CourseDao{
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 课程计算不包含选课表信息，统计范围 ：开课计划表
	 */
	
	@Override
	public List<Map<String, Object>> queryCourseByAttribute(List<String> deptList, List<AdvancedParam> advancedParamList,
			String edu,String schoolYear, String termCode) {
		String courseSql=getCourseSql(deptList, edu, schoolYear, termCode);
		courseSql="select t.course_code,t.COURSE_ATTR_CODE from ("+courseSql+") t";
		courseSql="select case when code.name_ is null or t.COURSE_ATTR_CODE is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,t.value as value,t.COURSE_ATTR_CODE as code from "
				+ " (select count (0) as value ,case when t.COURSE_ATTR_CODE is null then 'null' else t.COURSE_ATTR_CODE end COURSE_ATTR_CODE from ("+courseSql+") t group by COURSE_ATTR_CODE ) t left join "
				+ " ( select name_,code_ from  t_code where code_type='"+Constant.CODE_COURSE_ATTR_CODE+"') code on t.COURSE_ATTR_CODE=code.code_";
		List<Map<String ,Object>> list=baseDao.queryListInLowerKey(courseSql);
		return list;
	}

	@Override
	public List<Map<String , Object>> queryCourseByNature(List<String> deptList, List<AdvancedParam> advancedParamList,
			String edu,String schoolYear, String termCode) {
		String courseSql=getCourseSql(deptList, edu, schoolYear, termCode);
		courseSql="select t.course_code,t.COURSE_NATURE_CODE from ("+courseSql+") t";
		courseSql="select case when code.name_ is null or t.COURSE_NATURE_CODE is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,t.value as value,t.COURSE_NATURE_CODE as code from "
				+ " (select count (0) as value ,case when t.COURSE_NATURE_CODE is null then 'null' else t.COURSE_NATURE_CODE end COURSE_NATURE_CODE from ("+courseSql+") t group by COURSE_NATURE_CODE ) t left join "
				+ " ( select name_,code_ from  t_code where code_type='"+Constant.CODE_COURSE_NATURE_CODE+"') code on t.COURSE_NATURE_CODE=code.code_";
		List<Map<String ,Object>> list=baseDao.queryListInLowerKey(courseSql);
		return list;
	}

	@Override
	public List<Map<String , Object>> queryCourseByCategory(List<String> deptList, List<AdvancedParam> advancedParamList,
			String edu,String schoolYear, String termCode) {
		String courseSql=getCourseSql(deptList, edu, schoolYear, termCode);
		courseSql="select t.course_code,t.COURSE_CATEGORY_CODE from ("+courseSql+") t";
		courseSql="select case when code.name_ is null or t.COURSE_CATEGORY_CODE is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,t.value as value,t.COURSE_CATEGORY_CODE as code from "
				+ " (select count (0) as value ,case when t.COURSE_CATEGORY_CODE is null then 'null' else t.COURSE_CATEGORY_CODE end COURSE_CATEGORY_CODE from ("+courseSql+") t group by COURSE_CATEGORY_CODE ) t left join "
				+ " ( select name_,code_ from  t_code where code_type='"+Constant.CODE_COURSE_CATEGORY_CODE+"') code on t.COURSE_CATEGORY_CODE=code.code_";
		List<Map<String ,Object>> list=baseDao.queryListInLowerKey(courseSql);
		return list;
	}
	@Override
	public List<Map<String,Object>> queryselectDown(){
		//获取库中全部属性，性质，类别
		String attrSql="select distinct(t.COURSE_ATTR_CODE) as code,'课程属性（'||code.name_||'）' as mc,code.code_type as type from T_COURSE_ARRANGEMENT_PLAN t, "
				+ "( select name_,code_,code_type from  t_code where code_type='"+Constant.CODE_COURSE_ATTR_CODE+"') code where t.COURSE_ATTR_CODE=code.code_  order by t.COURSE_ATTR_CODE";
		String natSql="select distinct(t.COURSE_NATURE_CODE) as code,'课程性质（'||code.name_||'）' as mc,code.code_type as type from T_COURSE_ARRANGEMENT_PLAN t, "
				+ "( select name_,code_,code_type from  t_code where code_type='"+Constant.CODE_COURSE_NATURE_CODE+"') code where t.COURSE_NATURE_CODE=code.code_  order by t.COURSE_NATURE_CODE";
		String cateSql="select distinct(t.COURSE_CATEGORY_CODE) as code,'课程类别（'||code.name_||'）' as mc,code.code_type as type from T_COURSE_ARRANGEMENT_PLAN t, "
				+ "( select name_,code_,code_type from  t_code where code_type='"+Constant.CODE_COURSE_CATEGORY_CODE+"') code where t.COURSE_CATEGORY_CODE=code.code_  order by t.COURSE_CATEGORY_CODE";		
		List<Map<String,Object>> alist=baseDao.queryListInLowerKey(attrSql);
		List<Map<String,Object>> nlist=baseDao.queryListInLowerKey(natSql);
		List<Map<String,Object>> clist=baseDao.queryListInLowerKey(cateSql);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code", "all");map.put("mc", "全部");map.put("type", "all");
		list.add(0,map);
		list.addAll(alist);list.addAll(nlist);list.addAll(clist);
			for(int i=0;i<list.size();i++){
			list.get(i).put("id", i+"");
		}
		return list;
	}
	@Override
	public String getlevelSql(List<String> deptList,String edu,String schoolYear, String termCode,String codeType,String code){
		String courSql=getCourseSql(deptList, edu, schoolYear, termCode);
		//判断查看全部或者某一属性，性质，类别
		if(codeType.equals("all")){
			courSql="select course_code from ("+courSql+") ";
		}else{
			courSql="select course_code from ("+courSql+")  where "+codeType+" in ("+code+")";
		}
		//门次
		/*String clasSql="select c.course_code,c.class_id,t.TEACH_DEPT_ID as major_id from ("+courSql+") c,t_classes t"
				+ " where c.class_id=t.no_";
		String majorSql="select t.course_code,t.class_id,t.major_id,substr(dept.path_,0,8) as path_ from ("+clasSql+") t,t_code_dept_teach dept "
				+ " where t.major_id=dept.id";
		String deptSql="select id,path_ from t_code_dept_teach where level_type='YX' and level_='1' and istrue='1'";
		deptSql="select t.course_code,t.class_id,t.major_id,dept.id as dept_id from ("+majorSql+") t,("+deptSql+") dept "
				+ " where t.path_ = dept.path_";*/ 
		
		return courSql;
		
	}
	@Override
	public String getCourseBySubject(List<String> deptList,String edu,String schoolYear, String termCode,String codeType,String code,String id){
		int year=Integer.parseInt(schoolYear.substring(0, 4));
		String classSql=businessDao.getClassesIdSqlByDeptList(deptList,year);
		String termCodeSql = termCode!=null ? (" and t.TERM_CODE='"+termCode+"'") : "";
		classSql="select class_id from  (select class_id ,edu_id from t_stu where class_id in ("+classSql+") group by class_id,edu_id) where edu_id in ("+edu+")";
		String courSql="select t.course_code,t.class_id,t.COURSE_NATURE_CODE,t.COURSE_ATTR_CODE,t.COURSE_CATEGORY_CODE from T_COURSE_ARRANGEMENT_PLAN t"
				+ " where t.SCHOOL_YEAR='"+schoolYear+"'"+termCodeSql;//此位置去重
		//判断查看全部或者某一属性，性质，类别
		if(codeType.equals("all")){
			courSql="select class_id,course_code from ("+courSql+") ";
		}else{
			//传入属性以及编码
			courSql="select class_id,course_code from ("+courSql+") where "+codeType+" in ("+code+")";
		}
		String clasSql="select c.course_code,c.class_id,t.TEACH_DEPT_ID as major_id from ("+courSql+") c,t_classes t"
				+ " where c.class_id=t.no_";
		String subSql="select distinct(t.course_code) as course_code,dept.subject_id as code from ("+clasSql+") t,t_code_dept_teach dept where t.major_id=dept.id ";
		subSql="select course_code  from ("+subSql+") t where code='"+id+"'";
		return subSql;
	}
	private String getCourseSql(List<String> deptList,String edu,String schoolYear, String termCode){
		int year=Integer.parseInt(schoolYear.substring(0, 4));
		String classSql=businessDao.getClassesIdSqlByDeptList(deptList,year);
		//查看历史以学年为单位学期为空
		String termCodeSql = termCode!=null ? (" and t.TERM_CODE='"+termCode+"'") : "";
		classSql="select class_id from  (select class_id ,edu_id from t_stu where class_id in ("+classSql+") group by class_id,edu_id) where edu_id in ("+edu+")";
		String sql="select t.course_code,t.class_id,t.COURSE_NATURE_CODE,t.COURSE_ATTR_CODE,t.COURSE_CATEGORY_CODE from T_COURSE_ARRANGEMENT_PLAN t"
				+ " where t.SCHOOL_YEAR='"+schoolYear+"'"+termCodeSql;//此位置去重
		sql="select distinct(t.course_code) as course_code,t.COURSE_CATEGORY_CODE,t.COURSE_ATTR_CODE,t.COURSE_NATURE_CODE from ("+sql+") t where t.class_id in ("+classSql+")";
		return sql;
	}
	
	@Override
	public int queryAbstract(List<String> deptList,String edu,String schoolYear, String termCode){
		String sql=getCourseSql(deptList, edu, schoolYear, termCode);
		sql="select count(0) as value from ("+sql+")";
		int count =baseDao.queryForInt(sql);
		return count;
	}

}
