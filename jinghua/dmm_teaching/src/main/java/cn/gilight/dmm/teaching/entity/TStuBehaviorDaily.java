package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="T_STU_BEHAVIOR_DAILY")
public class TStuBehaviorDaily implements Serializable{
	private static final long serialVersionUID = 1L;
		
		private String id;
		private String stu_id;
		private String date_;
		private String breakfast_;
		private String lunch_;
		private String dinner_;
		private String first_dormrke;
		private String last_dormrke;
		
	public TStuBehaviorDaily() {
		super();
	}
	
	public TStuBehaviorDaily(String stu_id,String date_,String breakfast_,String lunch_,String dinner_,String first_dormrke,String last_dormrke) {
		super();
		this.stu_id= stu_id;
		this.date_= date_;
		this.breakfast_= breakfast_;
		this.lunch_= lunch_;
		this.dinner_= dinner_;
		this.first_dormrke= first_dormrke;
		this.last_dormrke= last_dormrke;
	}
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "STU_ID", length = 20)
	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	@Column(name = "DATE_", length = 20)
	public String getDate_() {
		return date_;
	}

	public void setDate_(String date_) {
		this.date_ = date_;
	}
	@Column(name = "BREAKFAST_", length = 20)
	public String getBreakfast_() {
		return breakfast_;
	}

	public void setBreakfast_(String breakfast_) {
		this.breakfast_ = breakfast_;
	}
	@Column(name = "LUNCH_", length = 20)
	public String getLunch_() {
		return lunch_;
	}

	public void setLunch_(String lunch_) {
		this.lunch_ = lunch_;
	}
	@Column(name = "DINNER_", length = 20)
	public String getDinner_() {
		return dinner_;
	}

	public void setDinner_(String dinner_) {
		this.dinner_ = dinner_;
	}
	@Column(name = "FIRST_DORMRKE", length = 20)
	public String getFirst_dormrke() {
		return first_dormrke;
	}

	public void setFirst_dormrke(String first_dormrke) {
		this.first_dormrke = first_dormrke;
	}
	@Column(name = "LAST_DORMRKE", length = 20)
	public String getLast_dormrke() {
		return last_dormrke;
	}

	public void setLast_dormrke(String last_dormrke) {
		this.last_dormrke = last_dormrke;
	}
}