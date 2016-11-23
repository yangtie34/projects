package com.jhnu.product.wechat.parent.score.entity;

import java.util.ArrayList;
import java.util.List;

import com.jhnu.product.common.stu.entity.Student;

/**
 * 学生 、课程成绩关联对象
 * @author Administrator
 *
 */
public class StuCourseScore {
	private Student stu;
	private List<CourseScore> css = new ArrayList<CourseScore>();
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public List<CourseScore> getCss() {
		return css;
	}
	public void setCss(List<CourseScore> css) {
		this.css = css;
	}
	public StuCourseScore(Student stu) {
		super();
		this.stu = stu;
	}
}
