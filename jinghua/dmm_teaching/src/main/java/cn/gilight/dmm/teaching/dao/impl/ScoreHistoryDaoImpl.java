package cn.gilight.dmm.teaching.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.ScoreHistoryDao;
import cn.gilight.framework.base.dao.BaseDao;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午12:13:43
 */
@Repository("scoreHistoryDao")
public class ScoreHistoryDaoImpl implements ScoreHistoryDao  {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	
	
	@Override
	public List<Map<String, Object>> getSex(String gpaSql){
		String sql = "select t.gpa value, code.code_ code, code.name_ name from"
				+ " (select round(sum(t.gpa)/count(0),2) gpa, stu.sex_code from ("+gpaSql+") t, t_stu stu where t.stu_id = stu.no_ group by stu.sex_code)t,"
				+ " t_code code where t.sex_code=code.code_ and code.code_type='"+Constant.CODE_SEX_CODE+"'"
				+ " order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.DOUBLE, Types.VARCHAR, Types.VARCHAR);
	}

	@Override
	public List<Map<String, Object>> getGrade(String gpaSql){
		String sql = "select round(sum(gpa)/count(0),2) value, grade code from"
				+ " ( select gpa, 2014-stu.ENROLL_GRADE+1 grade from ("+gpaSql+")t, t_stu stu"
				+ " where t.stu_id = stu.no_ )t group by grade";
		return baseDao.queryListInLowerKey(sql, Types.DOUBLE, Types.VARCHAR);
	}
}
