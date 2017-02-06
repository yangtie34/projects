package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_CODE_CAMPUS_CLASS")
public class TCodeCampusClass implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String campus_id;
	private String class_id;
	public TCodeCampusClass(){
	}
	public TCodeCampusClass(String id,String campus_id,String class_id){
		this.id = id;
		this.campus_id = campus_id;
		this.class_id = class_id;
	}
	public TCodeCampusClass(String campus_id,String class_id){
		this.campus_id = campus_id;
		this.class_id = class_id;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "CAMPUS_ID", length = 20)
	public String getCampus_id() {
		return campus_id;
	}
	public void setCampus_id(String campus_id) {
		this.campus_id = campus_id;
	}
	@Column(name = "CLASS_ID", length = 20)
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
}
