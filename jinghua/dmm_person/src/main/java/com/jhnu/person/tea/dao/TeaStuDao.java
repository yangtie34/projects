package com.jhnu.person.tea.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;


public interface TeaStuDao {
	/**
	 * 学生管理班级信息
	 * @param id
	 * @return
	 */
	public List stuxzGrade(String id);
	/**
	 * 学生管理 信息列表
	 * @param id
	 * @return
	 */
	public Page stuxzbxx(String teaId,String gradeId,int currentPage, int numPerPage);
	
	/**
	 * 学生管理班级性别比例
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> stuxbbl(String teaId,String gradeId);
	/**
	 * 行政请假人数比例
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> xzStuqjxb(String teaId,String gradeId);
	/**
	 * 学生管理 获取学生信息
	 * @param id
	 * @param gradeId
	 * @return
	 */
	public Map getStuxx(String stuId);
	
	
	/**
	 * 学生管理 --行政班级信息
	 * @param id
	 * @return
	 */
	public List jxStuGrade(String id);
	/**
	 * 学生管理-- 学生学科通过率
	 * @param id
	 * @return
	 */
	public List jxStutgl(String teaId,String gradeId);
	/**
	 * 学生管理--全部人数性别通过率
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> jxStuxbbl(String teaId,String gradeId);
	/**
	 * 教学请假人数比例
	 * @param gradeId
	 * @return
	 */
	public List<Map<String, Object>> jxStuqjxb(String teaId,String gradeId);
	/**
	 * 获取行政学生信息
	 * @param gradeId
	 * @return
	 */
	public Page getjxStuxx(String teaId,String stuid,int currentPage, int numPerPage);
	/**
	 * 获得行政学生信息明细
	 * @param id
	 * @return
	 */
	public List getStuxxmx(String id);

	
}
