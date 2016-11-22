package com.jhnu.framework.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodeTree implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String mc;
	
	private String pid;
	private String level_;
	private boolean checked=false;
	
	private List<Integer> children=new ArrayList<Integer>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<Integer> getChildren() {
		return children;
	}

	public void setChildren(List<Integer> children) {
		this.children = children;
	}
	public void setChildrenId(int chid) {
		this.children .add(chid);
	}
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getLevel_() {
		return level_;
	}

	public void setLevel_(String level_) {
		this.level_ = level_;
	}
	
	
	
}
