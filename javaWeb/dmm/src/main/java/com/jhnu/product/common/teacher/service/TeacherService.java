package com.jhnu.product.common.teacher.service;

import java.util.List;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.teacher.entity.Teacher;

public interface TeacherService {
	/**
	 * 根据教职工标识获取教职工信息
	 * @param teacherNo
	 * @return
	 */
	public Teacher getTeacherInfo(String teacherNo);
	
	/**
	 * 获取辅导员信息
	 * @param classId 班级信息
	 * @param year 学年
	 * @param term 学期
	 * @return
	 */
	public Teacher getInstructorInfo(String classId,String year,String term);
	
	/**
	 * 获取当前学年学期的辅导员信息
	 * @param classId 班级信息
	 * @return
	 */
	public Teacher getInstructorInfoByThisYearTerm(String classId);
	
	/**
	 * 判断此教师是否在这些单位
	 * @param teaId
	 * @return
	 */
	public boolean isInDeptForTeacher(String teaId,String depts);
	/**
	 * 获取当前正常在校但是不在用户表中的教师列表
	 * @return
	 */
	public List<Teacher> getTeasInSchoolNotInUser();
}
