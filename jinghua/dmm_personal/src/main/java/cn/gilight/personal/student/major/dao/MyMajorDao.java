package cn.gilight.personal.student.major.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface MyMajorDao {


	/**
	 * 获取专业
	 * @param stu_id
	 * @return
	 */
	public Map<String, Object> getMajor(String stu_id) ;

	/**
	 * 获取本学期计划课程
	 * @param stu_id
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String, Object>> getCourse(String stu_id,String school_year,String term_code);

	/**
	 * 获取课程通过率
	 * @param stu_id
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String, Object>> getCourseScore(String stu_id,String school_year, String term_code);

	/**
	 * 获取选修课排行
	 * @return
	 */
	public Page getChooseCourse(Page page);

	/**
	 * 获取考研方向排行
	 * @param page
	 * @return
	 */
	public Page getPostgraduate(Page page,int year);

}
