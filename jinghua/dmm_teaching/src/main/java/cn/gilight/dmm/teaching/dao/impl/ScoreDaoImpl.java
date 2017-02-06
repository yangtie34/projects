package cn.gilight.dmm.teaching.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.teaching.dao.ScoreDao;
import cn.gilight.framework.base.dao.BaseDao;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午12:13:43
 */
@Repository("scoreDao")
public class ScoreDaoImpl implements ScoreDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	
	
	@Override
	public List<Double> queryScoreList(String stuSql, String schoolYear, String termCode, String gpaCode){
		String sql = businessDao.getStuGpaSql(stuSql, schoolYear, termCode, gpaCode);
		return baseDao.queryForListDouble("select gpa from ("+sql+")");
	}

	@Override
	public List<Map<String, Object>> queryCourseList(String stuSql, String schoolYear, String termCode){
		String scoreSql = businessDao.getStuScoreSql(stuSql, schoolYear, termCode);
		String sql = "select distinct(score.coure_code) id, course.name_ name from ("+scoreSql+")score,t_course course"
				+ " where score.coure_code=course.code_";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public String queryCourseSql(String stuSql, String schoolYear, String termCode){
		String scoreSql = businessDao.getStuScoreSql(stuSql, schoolYear, termCode);
		String sql = "select distinct(score.coure_code) id, course.name_ name from ("+scoreSql+")score,t_course course"
				+ " where score.coure_code=course.code_";
		return sql;
	}
	@Override
	public String queryTeachSql(String stuSql, String schoolYear, String termCode){
		String teachClassSql = businessDao.getTeachClassIdSql(stuSql);
		String sql = "select t.tea_id id, tea.name_ name from"
				+ " (select distinct(arr.tea_id) from T_COURSE_ARRANGEMENT arr, ("+teachClassSql+") teachClass "
				+ " where arr.teachingclass_id = teachClass.id "
				+ " and arr.school_year='"+schoolYear+"' and arr.term_code='"+termCode+"' group by arr.tea_id)t, t_tea tea where t.tea_id = tea.tea_no";
		return sql;
	}
	@Override
	public List<Map<String, Object>> queryTeachList(String stuSql, String schoolYear, String termCode){
		String teachClassSql = businessDao.getTeachClassIdSql(stuSql);
		String sql = "select t.tea_id id, tea.name_ name from"
				+ " (select distinct(arr.tea_id) from T_COURSE_ARRANGEMENT arr, ("+teachClassSql+") teachClass "
				+ " where arr.teachingclass_id = teachClass.id "
				+ " and arr.school_year='"+schoolYear+"' and arr.term_code='"+termCode+"' group by arr.tea_id)t, t_tea tea where t.tea_id = tea.tea_no";
		return baseDao.queryListInLowerKey(sql);
	}
	
}
