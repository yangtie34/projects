package cn.gilight.business.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.business.dao.BusinessDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.business.model.TCode;

/**
 * 学生工作者数据查询
 * 
 * @author xuebl
 * @date 2016年4月19日 下午5:14:24
 */
@Repository("businessDao")
public class BusinessDaoImpl implements BusinessDao  {

	@Resource
	private BaseDao baseDao;
	
	
	@Override
	public Map<String, Object> querySchoolData(){
		return baseDao.queryMapInLowerKey("select t.id,t.name_ name from t_code_dept t where t.istrue = 1 and t.pid = '-1'");
	}

	@Override
	public List<TCode> queryBzdmList(String codeType, String codes){
		String sql = "select t.* from t_code t where code_type='" +codeType+ "' and istrue = 1 " +(codes==null ? "" : " and t.code_ in("+codes+")")+ " order by t.order_,t.code_";
		return baseDao.queryForListBean(sql, TCode.class);
	}

	
	@Override
	public List<Map<String, Object>> queryGradeGroup(String stuSql, int schoolYear){
		String sql = "select count(0) value, t.grade code from"
				+ " (select t.stu_id, stu.enroll_grade, "+schoolYear+"+1-stu.enroll_grade as grade"
				+ " from (" +stuSql+ ") t, t_stu stu"
				+ " where t.stu_id=stu.no_)t group by t.grade order by grade";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.NUMERIC);
	}
	
	@Override
	public List<Map<String, Object>> querySubjectUsefulList(){
		String sql = "select tp.name_ name, tp.id id from t_code_subject tp,"
				+ " (select distinct(t.id),t.* from t_code_subject t, t_code_dept_teach dept where t.id=dept.subject_id)t"
				+ " where tp.istrue=1 and substr(t.path_,1,4)=tp.path_ order by tp.level_,tp.order_,tp.code_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public Integer queryMinSchoolYear(String table, String column){
		String sql = "select min(" +column+ ") from " +table;
		return Integer.valueOf(baseDao.queryForString(sql).substring(0, 4));
	}

	@Override
	public List<Map<String, Object>> queryCodeSubject() {
		String sql = "select t.id,t.code_ as code,t.path_ as path,t.name_ as mc from t_code_subject t where t.level_ = 1 and t.istrue = 1 order by t.order_";
		return baseDao.queryListInLowerKey(sql);
	}
}
