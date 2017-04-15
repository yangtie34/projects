package cn.gilight.product.common.teacher.dao;

import java.util.List;

import cn.gilight.product.common.teacher.entity.Teacher;


public interface TeacherDao {
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
