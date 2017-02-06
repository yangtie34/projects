package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_CODE_CAMPUS")
public class TCampus implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name_;
	private int istrue;
	public TCampus(){
	}
	public TCampus(String id,String name_,int istrue){
		this.id = id;
		this.name_ = name_;
		this.istrue = istrue;
	}
	public TCampus(String name_,int istrue){
		this.name_ = name_;
		this.istrue = istrue;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "NAME_", length = 30)
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	@Column(name = "ISTRUE", length = 1)
	public int getIstrue() {
		return istrue;
	}
	public void setIstrue(int istrue) {
		this.istrue = istrue;
	}
}
