package cn.gilight.product.common.school.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dept implements Serializable{
	
	private static final long serialVersionUID = -4601959918498262611L;
	private String id;
	private String code_;
	private String name_;
	private String pid;
	private String path_;
	private Integer level_;
	private String level_type;
	private Integer istrue;
	private Integer order_;
	private String dept_category_code;
	private List<Dept> children=new ArrayList<Dept>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode_() {
		return code_;
	}
	public void setCode_(String code_) {
		this.code_ = code_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPath_() {
		return path_;
	}
	public void setPath_(String path_) {
		this.path_ = path_;
	}
	public Integer getLevel_() {
		return level_;
	}
	public void setLevel_(Integer level_) {
		this.level_ = level_;
	}
	public String getLevel_type() {
		return level_type;
	}
	public void setLevel_type(String level_type) {
		this.level_type = level_type;
	}
	public Integer getIstrue() {
		return istrue;
	}
	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}
	public Integer getOrder_() {
		return order_;
	}
	public void setOrder_(Integer order_) {
		this.order_ = order_;
	}
	public String getDept_category_code() {
		return dept_category_code;
	}
	public void setDept_category_code(String dept_category_code) {
		this.dept_category_code = dept_category_code;
	}
	public List<Dept> getChildren() {
		return children;
	}
	public void setChildren(List<Dept> children) {
		this.children = children;
	}
	
	
}
