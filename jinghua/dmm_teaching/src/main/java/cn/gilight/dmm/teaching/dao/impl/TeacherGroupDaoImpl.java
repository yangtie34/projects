package cn.gilight.dmm.teaching.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.TeacherGroupDao;
import cn.gilight.framework.base.dao.BaseDao;

/**
 * 师资队伍
 * 
 * @author xuebl
 * @date 2016年7月11日 上午9:20:36
 */
@Repository("teacherGroupDao")
public class TeacherGroupDaoImpl implements TeacherGroupDao {

	@Resource
	private BaseDao baseDao;

	private static final String LEFT = "left";
	private static final String RIGHT = "right";
	
	@Override
	public List<Map<String, Object>> querySeniorGroup(String teaSql){
		String seniorSql = getSeniorSql();
		String sql = "select t.value, code.name_ name, t.code from"
				+ " (select t.senior_code code, count(0) value from ("+seniorSql+")t, ("+teaSql+")tea where t.no_=tea.tea_no group by t.senior_code)t,"
				+ " t_code code where code.istrue=1 and code.code_type='"+Constant.CODE_SENIOR_CODE+"'"
				+ " and code.code_=t.code order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	/**
	 * 获取高级人才sql
	 * @return String
	 */
	private String getSeniorSql(){
//		String sql = "select t.* from T_TEA_SENIOR t,"
//				+ " (select no_,max(get_date) get_date from T_TEA_SENIOR t group by no_)x"
//				+ " where t.no_=x.no_ and (t.get_date=x.get_date or (t.get_date is null and x.get_date is null))";
		String sql = "select t.* from T_TEA_SENIOR t";
		return sql;
	}
	
	@Override
	public List<Map<String, Object>> queryStuTrainingGroup(String stuSql){
		String sql = "select t.value,code.name_ name, t.code from "
				+ " (select stu.training_level_code code, count(0) value from ("+stuSql+")stu group by stu.training_level_code)t,"
				+ " t_code code where code.istrue=1 and t.code=code.code_ and code.code_type='"+Constant.CODE_TRAINING_LEVEL_CODE+"'"
				+ " order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryTechnicalGroup(String teaSql){
		return queryTechnicalGroup(teaSql, LEFT);
	}
	@Override
	public List<Map<String, Object>> queryTechnicalGroupRightJoin(String teaSql){
		return queryTechnicalGroup(teaSql, RIGHT);
	}
	private List<Map<String, Object>> queryTechnicalGroup(String teaSql, String joinType) {
		String sql = "select t.value, code.name_ name, code.code_ code from"
				+ " (select zw.zyjszw_jb_code code, count(0) value from ("+teaSql+") t "
				+ joinType + " join "+Constant.TABLE_T_CODE_ZYJSZW+" zw on t.zyjszw_id=zw.id group by zw.zyjszw_jb_code)t "
				+ joinType + " join t_code code on t.code=code.code_"
				+ " where code.istrue=1 and code.code_type='"+Constant.CODE_ZYJSZW_JB_CODE+"'"
				+ " order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}

	@Override
	public List<Map<String, Object>> queryDegreeGroup(String teaSql){
		return queryDegreeGroup(teaSql, LEFT);
	}
	@Override
	public List<Map<String, Object>> queryDegreeGroupRightJoin(String teaSql){
		return queryDegreeGroup(teaSql, RIGHT);
	}
	private List<Map<String, Object>> queryDegreeGroup(String teaSql, String joinType) {
		String sql = "select t.value, case when code.name_ is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,"
				+ " case when code.id is null then 'null' else code.id end code from"
				+ " (select sum(t.value) value, code.pid code from"
				+ " (select t.degree_id code, count(0) value from ("+teaSql+") t group by t.degree_id)t "
				+ joinType + " join "
				+ " (select code.id,case when code.path_=pcode.path_ then code.id else code.pid end pid,pcode.name_ pname from "+Constant.TABLE_T_CODE_DEGREE+" code,"
				+ Constant.TABLE_T_CODE_DEGREE+" pcode where code.istrue=1 and substr(code.path_,1,4)=pcode.path_)code"
				+ " on t.code=code.id group by pid)t left join "+Constant.TABLE_T_CODE_DEGREE+" code on t.code=code.id"
				+ " order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryEduGroup(String teaSql){
		return queryEduGroup(teaSql, LEFT);
	}
	@Override
	public List<Map<String, Object>> queryEduGroupRightJoin(String teaSql){
		return queryEduGroup(teaSql, RIGHT);
	}
	private List<Map<String, Object>> queryEduGroup(String teaSql, String joinType) {
		String sql = "select t.value, case when code.name_ is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,"
				+ " case when code.id is null then 'null' else code.id end code from" //t.code
				+ " (select sum(t.value) value, code.pid code from"
				+ " (select t.edu_id code, count(0) value from ("+teaSql+") t group by t.edu_id)t "
				+ joinType + " join "
				+ " (select code.id,case when code.path_=pcode.path_ then code.id else code.pid end pid from "+Constant.TABLE_T_CODE_EDUCATION+" code,"
				+ Constant.TABLE_T_CODE_EDUCATION+" pcode where code.istrue=1 and substr(code.path_,1,4)=pcode.path_)code"
				+ " on t.code=code.id group by pid)t left join "+Constant.TABLE_T_CODE_EDUCATION+" code on t.code=code.id"
				+ " order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> querySubjectGroup(String teaSql){
		String sql = "select t.count value, case when code.name_ is null then '"+Constant.CODE_Unknown_Name+"' else code.name_ end name,"
				+ " case when code.id is null then 'null' else code.id end code from"
				+ " (select pid, count(0) count from ("+teaSql+") t"
				+ " left join (select code.id,case when code.path_=pcode.path_ then code.id else code.pid end pid,pcode.name_ pname from "+Constant.TABLE_T_Code_Subject_Degree+" code, "+Constant.TABLE_T_Code_Subject_Degree+" pcode"
				+ " where code.istrue=1 and substr(code.path_,1,4)=pcode.path_) subject on t.subject_id=subject.id group by pid) t"
				+ " left join "+Constant.TABLE_T_Code_Subject_Degree+" code on t.pid=code.id order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
}
