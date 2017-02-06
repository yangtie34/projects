package cn.gilight.dmm.teaching.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.PeriodDao;
import cn.gilight.framework.base.dao.BaseDao;
@Repository("periodDao")
public class PeriodDaoImpl implements PeriodDao {
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> queryCourseByAttribute(List<String> deptList,
			List<AdvancedParam> advancedParamList, String edu, String schoolYear, String termCode) {
		String sql=getCourseSql(deptList, edu, schoolYear, termCode);
		 sql="select t.course_code,case when t.COURSE_ATTR_CODE is null then 'null' else t.COURSE_ATTR_CODE end COURSE_ATTR_CODE,t.THEORY_PERIOD from ("+sql+") t";
		String attrSql="select COURSE_ATTR_CODE ,sum(THEORY_PERIOD) as value from ("+sql+") group by COURSE_ATTR_CODE";
		attrSql="select case when code.name_ is null or t.COURSE_ATTR_CODE is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,t.value as value,t.COURSE_ATTR_CODE as code from  ("+attrSql+") t left join "
				+ " ( select name_,code_ from  t_code where code_type='"+Constant.CODE_COURSE_ATTR_CODE+"') code on t.COURSE_ATTR_CODE=code.code_";
		List<Map<String ,Object>> list=baseDao.queryListInLowerKey(attrSql);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCourseByNature(List<String> deptList, List<AdvancedParam> advancedParamList,
			String edu, String schoolYear, String termCode) {
		String sql=getCourseSql(deptList, edu, schoolYear, termCode);
		 sql="select t.course_code,case when t.COURSE_NATURE_CODE is null then 'null' else t.COURSE_NATURE_CODE end COURSE_NATURE_CODE,t.THEORY_PERIOD from ("+sql+") t";
		String natSql="select COURSE_NATURE_CODE ,sum(THEORY_PERIOD) as value from ("+sql+") group by COURSE_NATURE_CODE";
		natSql="select case when code.name_ is null or t.COURSE_NATURE_CODE is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,t.value as value,t.COURSE_NATURE_CODE as code from  ("+natSql+") t left join "
				+ " ( select name_,code_ from  t_code where code_type='"+Constant.CODE_COURSE_NATURE_CODE+"') code on t.COURSE_NATURE_CODE=code.code_";
		List<Map<String ,Object>> list=baseDao.queryListInLowerKey(natSql);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCourseByCategory(List<String> deptList, List<AdvancedParam> advancedParamList,
			String edu, String schoolYear, String termCode) {
		String sql=getCourseSql(deptList, edu, schoolYear, termCode);
		 sql="select t.course_code,case when t.COURSE_CATEGORY_CODE is null then 'null' else t.COURSE_CATEGORY_CODE end COURSE_CATEGORY_CODE,t.THEORY_PERIOD from ("+sql+") t";
		String cateSql="select COURSE_CATEGORY_CODE ,sum(THEORY_PERIOD) as value from ("+sql+") group by COURSE_CATEGORY_CODE";
		cateSql="select case when code.name_ is null or t.COURSE_CATEGORY_CODE is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,t.value as value,t.COURSE_CATEGORY_CODE as code from  ("+cateSql+") t left join"
				+ " ( select name_,code_ from  t_code where code_type='"+Constant.CODE_COURSE_CATEGORY_CODE+"') code on t.COURSE_CATEGORY_CODE=code.code_";
		List<Map<String ,Object>> list=baseDao.queryListInLowerKey(cateSql);
		return list;
	}

	@Override
	public String getlevelSql(List<String> deptList, String edu, String schoolYear, String termCode, String codeType,
			String code) {
		String periodSql=getCourseSql(deptList, edu, schoolYear, termCode);
		if(codeType.equals("all")){
			periodSql="select sum(THEORY_PERIOD) as value from ("+periodSql+") ";
		}else{
			periodSql="select sum(THEORY_PERIOD) as value from ("+periodSql+")  where "+codeType+" in ("+code+")";
		}
		String value=baseDao.queryForString(periodSql);
		if(value==null||value.equals("") ) value="0";
		return value;
	}

	@Override
	public String getCourseBySubject(List<String> deptList, String edu, String schoolYear, String termCode,
			String codeType, String code, String id) {
		String periodSql=getCourseSql(deptList, edu, schoolYear, termCode);
		if(codeType.equals("all")){
			periodSql="select class_id,course_code,THEORY_PERIOD from ("+periodSql+") ";
		}else{
			periodSql="select class_id,course_code,THEORY_PERIOD from ("+periodSql+")  where "+codeType+" in ("+code+")";
		}
		String clasSql="select c.course_code,c.THEORY_PERIOD,c.class_id,t.TEACH_DEPT_ID as major_id from ("+periodSql+") c,t_classes t"
				+ " where c.class_id=t.no_";
		String subSql="select t.THEORY_PERIOD,dept.subject_id as code from ("+clasSql+") t,t_code_dept_teach dept where t.major_id=dept.id ";
		subSql="select sum(THEORY_PERIOD) as value from ("+subSql+") t where code='"+id+"'";
		String value=baseDao.queryForString(subSql);
		if( value==null || value.equals("")) value="0";
		return value;

	}
	@Override
	public int queryAbstract(List<String> deptList,String edu,String schoolYear, String termCode){
		String sql=getCourseSql(deptList, edu, schoolYear, termCode);
		sql="select sum(THEORY_PERIOD) as value from ("+sql+")";
		int count =baseDao.queryForInt(sql);
		return count;
	}
	private String getCourseSql(List<String> deptList,String edu,String schoolYear, String termCode){
		int year=Integer.parseInt(schoolYear.substring(0, 4));
		String classSql=businessDao.getClassesIdSqlByDeptList(deptList,year);
		String termCodeSql = termCode!=null ? (" and t.TERM_CODE='"+termCode+"'") : "";//判断学期为空时
		classSql="select class_id from  (select class_id ,edu_id from t_stu where class_id in ("+classSql+") group by class_id,edu_id) where edu_id in ("+edu+")";
		String sql="select t.course_code,t.class_id,t.COURSE_NATURE_CODE,t.COURSE_ATTR_CODE,t.COURSE_CATEGORY_CODE from T_COURSE_ARRANGEMENT_PLAN t"
				+ " where t.SCHOOL_YEAR='"+schoolYear+"'"+termCodeSql;
		sql="select t.* from ("+sql+") t where t.class_id in ("+classSql+")";
		sql="select t.*,cour.THEORY_PERIOD from ("+sql+") t,t_course cour where t.course_code=cour.code_";
		return sql;
	}

}
