package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午7:11:11
 */
@Entity
@Table(name="T_STU_GPA")
public class TStuGpa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name_;
	private String start_;
	private String end_;
	private Double gpa;
	private String category_code;
	private Boolean istrue;
	
	public TStuGpa() {
		super();
	}
	public TStuGpa(String id, String name_, String start_, String end_, Double gpa, String category_code,
			Boolean istrue) {
		super();
		this.id = id;
		this.name_ = name_;
		this.start_ = start_;
		this.end_ = end_;
		this.gpa = gpa;
		this.category_code = category_code;
		this.istrue = istrue;
	}
	
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "STU_ID", length = 20)
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}

	@Column(name = "START_", length = 20)
	public String getStart_() {
		return start_;
	}
	public void setStart_(String start_) {
		this.start_ = start_;
	}

	@Column(name = "END_", length = 20)
	public String getEnd_() {
		return end_;
	}
	public void setEnd_(String end_) {
		this.end_ = end_;
	}

	@Column(name = "GPA", precision = 8, scale = 2)
	public Double getGpa() {
		return gpa;
	}
	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	@Column(name = "CATEGORY_CODE", length = 20)
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}

	@Column(name = "ISTRUE", precision = 1)
	public Boolean getIstrue() {
		return istrue;
	}
	public void setIstrue(Boolean istrue) {
		this.istrue = istrue;
	}
}
