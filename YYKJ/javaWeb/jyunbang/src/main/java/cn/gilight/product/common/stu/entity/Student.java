package cn.gilight.product.common.stu.entity;

import java.io.Serializable;


public class Student implements Serializable{
	private static final long serialVersionUID = 364471312901284273L;
	private String no_;// 学号
	private String name_;// 姓名
	private String idno;// 身份证号
	private String major; // 专业
	private String dept;// 院系
	private String class_id;//班级ID
	private String sex;
	private String enrolldate;
	
	public String getEnrolldate() {
		return enrolldate;
	}
	public void setEnrolldate(String enrolldate) {
		this.enrolldate = enrolldate;
	}
	public String getNo_() {
		return no_;
	}
	public void setNo_(String no_) {
		this.no_ = no_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Student(){
		super();
	};
	
	public Student(String no_, String name_, String idno) {
		super();
		this.no_ = no_;
		this.name_ = name_;
		this.idno = idno;
	}
	public Student(String no_, String name_, String idno, String major,
			String dept) {
		super();
		this.no_ = no_;
		this.name_ = name_;
		this.idno = idno;
		this.major = major;
		this.dept = dept;
	}
	public Student(String no_, String name_, String idno, String major,
			String dept, String sex) {
		super();
		this.no_ = no_;
		this.name_ = name_;
		this.idno = idno;
		this.major = major;
		this.dept = dept;
		this.sex = sex;
	}
	
	@Override
	public String toString() {
		return this.name_+"-"+this.enrolldate;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	
}
