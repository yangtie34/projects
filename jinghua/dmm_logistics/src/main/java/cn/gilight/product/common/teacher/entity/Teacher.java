package cn.gilight.product.common.teacher.entity;

public class Teacher {
	private String no_;
	private String name_;
	private String deptName;
	private String idno;
	private String sex;
	private String enrollDate;
	
	public String getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Teacher(String no_, String name_, String deptName, String idno,String sex) {
		super();
		this.no_ = no_;
		this.name_ = name_;
		this.deptName = deptName;
		this.idno = idno;
		this.sex = sex;
	}
	public Teacher() {
		super();
	}
}
