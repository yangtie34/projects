package cn.gilight.product.common.stu.dao;

import java.util.List;

import cn.gilight.product.common.stu.entity.LateDormStudent;
import cn.gilight.product.common.stu.entity.Student;


public interface StuDao {
	/**
	 * 根据毕业届别获得学生总数
	 * @param endYear 毕业届别=2011届
	 * @return
	 */
	public int getStuCByEndYear(int endYear);
	/**
	 * 根据毕业届别、性别获得学生总数
	 * @param endYear
	 * @param sexCode
	 * @return
	 */
	public int getStuCByEndYearAndSexcode(int endYear,String sexCode);
	
	/**
	 * 根据毕业届别、性别获得学生总数
	 * @param endYear 毕业届别
	 * 
	 * @param majorId 专业id
	 * @return
	 */
	public int getStuCByEndYearAndMajor(int endYear,String majorId);
	
	/**
	 * 获取该学生的毕业年
	 * @param stuId 学生ID
	 * @return
	 */
	public int getEndYearByStuId(String stuId);
	
	/**
	 * 获取该学生的学制
	 * @param stuId 学生ID
	 * @return
	 */
	public int getSchoolYearByStuId(String stuId);
	
	/**
	 * 获取当前正常在校但是不在用户表中的学生列表
	 * @return
	 */
	public List<Student> getStusInSchoolNotInUser();
	
	/**
	 * 获取当前正常在校的学生列表
	 * @return
	 */
	public List<Student> getStusInSchool();
	/**
	 * 获取当前已毕业的学生列表
	 * @return
	 */
	public List<Student> getStusGraduated();
	/**
	 * 根据毕业届别获取学生列表
	 * @param endYear
	 * @return
	 */
	public List<Student> getStusByEndyear(int endYear);
	/**
	 * 根据学生主键获取学生信息
	 * @param stuId
	 * @return
	 */
	public Student getStudentInfo(String stuId);
	
	/**
	 * 根据学生身份证获取学生信息
	 * @param stuIdNo
	 * @return
	 */
	public Student getStudentInfoByIdno(String stuIdNo);
	
	/**
	 * 获取晚寝晚归人群
	 * @param lds
	 * @return
	 */
	public List<LateDormStudent> getLateDormStudents(LateDormStudent lds);
	
	/**
	 * 保存迟到人数集合
	 * @param ldsList 迟到人数集合
	 */
	public void saveLateDormStudent(List<LateDormStudent> ldsList);
	
	/**
	 * 获取昨天的迟到人数
	 * @param startTime 迟到的开始时间
	 * @param endTime	迟到的结束时间
	 * @param exe_time  执行的同步时间
	 * @return
	 */
	public List<LateDormStudent> getLateDormStudentBytime(String startTime,String endTime,String action_date,String exe_time);
	
	/**
	 * 判断此教师是否在这些单位
	 * @param teaId
	 * @return
	 */
	public boolean isInClassForStudent(String stuId,String classes);
}
