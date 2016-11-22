package cn.gilight.personal.student.score.dao;

import java.util.List;
import java.util.Map;

public interface StuScoreDao {

	public Map<String, Object> getScore(String stu_id, String school_year, String term_code);

	public int getMajorCounts(String stu_id);

	/**
	 * 获取该生考试的各学年学期
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getScoreTerm(String stu_id);

	public List<Map<String, Object>> getScoreBySchoolTerm(String stu_id,String school_year, String term_code);
	
	public Map<String,Object> getTotalCredit(String stu_id);
	
	public Map<String,Object> getMyCredit(String stu_id);
	
	public Map<String, Object> getTotalCreditCourseAttr(String stu_id);
	
	public Map<String,Object> getMyCreditCourseAttr(String stu_id);
 }
