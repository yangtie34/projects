package cn.gilight.personal.student.major.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface MyMajorService {

	/**
	 * 获取专业
	 * @param stu_id
	 * @return
	 */
	public Map<String, Object> getMajor(String stu_id);

	/**
	 * 获取本学期计划课程
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getCourse(String stu_id);

	/**
	 * 获取课程通过率
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getCourseScore(String stu_id);

	/**
	 * 获取选修课排行
	 * @return
	 */
	public Page getChooseCourse(int currpage);

	/**
	 * 获取考研方向排行
	 * @param currpage
	 * @return
	 */
	public Page getPostgraduate(int currpage);

}
