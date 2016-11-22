package com.jhnu.product.wechat.parent.score.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.score.dao.WechatScoreDao;
import com.jhnu.product.wechat.parent.score.entity.CourseScore;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreCourse;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreRank;

@Repository("wechatScoreDao")
public class WechatScoreDaoImpl implements WechatScoreDao {
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getScoreAnalyzeData(String stuId) {
		String sql ="SELECT * FROM TL_WECHAT_SCORE_RANK WHERE STU_ID = ? ORDER BY SCHOOL_YEAR DESC,TERM_CODE DESC";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId});
	}
	
	@Override
	public List<Map<String, Object>> getScoreCourseData(String stuId,String schoolYear, String termCode) {
		String sql ="SELECT * FROM TL_WECHAT_SCORE_COURSE WHERE STU_ID =? AND SCHOOL_YEAR=? AND TERM_CODE=?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId,schoolYear,termCode});
	}
	
	@Override
	public int getClassStuCount(String stuId) {
		String sql ="SELECT COUNT(*) NM FROM T_STU STU1,T_STU STU2 WHERE STU1.CLASS_ID = STU2.CLASS_ID AND STU1.NO_=?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{stuId});
	}
	
	@Override
	public int getMajorStuCount(String stuId) {
		String sql ="SELECT COUNT(1) FROM T_STU STU1 ,T_STU STU2 WHERE STU1.ENROLL_GRADE = STU2.ENROLL_GRADE AND STU1.MAJOR_ID = STU2.MAJOR_ID AND STU1.NO_=?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{stuId});
	}
	
	@Override
	public List<Map<String, Object>> getAvgScoreLine(String stuId) {
		String sql ="SELECT SCHOOL_YEAR,TERM_CODE,AVG_ FROM TL_WECHAT_SCORE_RANK WHERE STU_ID =? ORDER BY SCHOOL_YEAR,TERM_CODE";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId});
	}
	
	@Override
	public List<Map<String, Object>> getCountScoreLine(String stuId) {
		String sql ="SELECT SCHOOL_YEAR,TERM_CODE,COUNT_ FROM TL_WECHAT_SCORE_RANK WHERE STU_ID =? ORDER BY SCHOOL_YEAR,TERM_CODE";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TlWechatScoreRank> getAllStusSumAvgScore(String schoolYear,String termCode) {
		String sql ="select stu_id,stu.major_id,stu.class_id,school_year,term_code,nvl(sum(centesimal_score),0) score_count,nvl(round(avg(centesimal_score),2),0) score_avg "+
				" from t_stu_score sc inner join t_stu stu on stu.no_ = sc.stu_id where sc.school_year =? and term_code=? "
				+ "group by school_year,term_code,stu_id,stu.major_id,stu.class_id order by stu_id,school_year desc,term_code desc";
		return (List<TlWechatScoreRank>)baseDao.getBaseDao().getJdbcTemplate().query(sql,new Object[]{schoolYear,termCode}, new BeanPropertyRowMapper(TlWechatScoreRank.class));
	}
	
	@Override
	public void saveAllStusSumAvgScore2Log(final List<TlWechatScoreRank> result) {
		String inserSql ="INSERT INTO TL_WECHAT_SCORE_RANK VALUES(?,?,?,?,?,?,?)";
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(inserSql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, result.get(i).getStuId());
				ps.setString(2, result.get(i).getSchoolYear());
				ps.setString(3, result.get(i).getTermCode());
				ps.setInt(4, result.get(i).getClassRank());
				ps.setInt(5, result.get(i).getMajorRank());
				ps.setDouble(6, result.get(i).getScoreCount());
				ps.setDouble(7, result.get(i).getScoreAvg());
			}
			
			@Override
			public int getBatchSize() {
				return result.size();
			}
		});
	}
	@Override
	public void saveAllStusCourse2Log(final List<TlWechatScoreCourse> result) {
		String inserSql ="INSERT INTO TL_WECHAT_SCORE_Course VALUES(?,?,?,?,?,?,?,?,?,?)";
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(inserSql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, result.get(i).getStuId());
				ps.setString(2, result.get(i).getSchoolYear());
				ps.setString(3, result.get(i).getTermCode());
				ps.setString(4, result.get(i).getTopCourse());
				ps.setString(5, result.get(i).getTopCourseS());
				ps.setString(6, result.get(i).getTopCourseMs());
				ps.setString(7, result.get(i).getLowCourse());
				ps.setString(8, result.get(i).getLowCourseS());
				ps.setString(9, result.get(i).getLowCourseMs());
				ps.setString(10, result.get(i).getFailCourse());
			}
			
			@Override
			public int getBatchSize() {
				return result.size();
			}
		});
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CourseScore> getCourseScores(String schoolYear,String termCode) {
		String sql ="SELECT COURE_CODE AS CODE,C.NAME_ AS NAME,nvl(CENTESIMAL_SCORE,0) SCORE,STU_ID,SCHOOL_YEAR,TERM_CODE FROM T_STU_SCORE T LEFT JOIN T_COURSE C ON C.CODE_ = T.COURE_CODE WHERE SCHOOL_YEAR=? AND TERM_CODE=?";
		return (List<CourseScore>)baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[]{schoolYear,termCode},  new BeanPropertyRowMapper(CourseScore.class));
	}
	
	@Override
	public List<Map<String, Object>> getMajorCourseMaxScore(String schoolYear,
			String termCode) {
		String sql = "select major_id,coure_code code,nvl(max(centesimal_score),0) score from "
				+ "(select s.*,stu.major_id from t_stu_score s,t_stu stu where stu.no_ = s.stu_id "
				+ "and s.school_year=? and s.term_code=?)group by major_id,coure_code";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{schoolYear,termCode});
	}
	
	@Override
	public List<Map<String, Object>> getAllClassGrade() {
		String sql = "select no_,name_,teach_dept_id,length_schooling,islive,grade from t_classes";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
}
