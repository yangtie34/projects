package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年8月30日 上午11:53:21
 */
@Entity
@Table(name="T_STU_WARNING_MAIL_STATUS")
public class TStuWarningMailStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String dept_id;
	private String date_;
	private String time_;
	private Integer status;
	
	
	public TStuWarningMailStatus() {
		super();
	}
	
	public TStuWarningMailStatus(String id, String dept_id, String date_, String time_, Integer status) {
		super();
		this.id = id;
		this.dept_id = dept_id;
		this.date_ = date_;
		this.time_ = time_;
		this.status = status;
	}
	
	
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DEPT_ID", length = 20)
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	
	@Column(name = "DATE_", length = 10)
	public String getDate_() {
		return date_;
	}
	public void setDate_(String date_) {
		this.date_ = date_;
	}
	
	@Column(name = "STATUS", precision = 1)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "TIME_", length = 20)
	public String getTime_() {
		return time_;
	}

	public void setTime_(String time_) {
		this.time_ = time_;
	}


	
}
