package cn.gilight.business.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodeAngularTree implements Serializable{
	
	private static final long serialVersionUID = -8149207516677598776L;
	
	private String id;
	
	private String mc;
	
	private String pid;
	
	private boolean checked=false;
	
	private List<NodeAngularTree> children=new ArrayList<NodeAngularTree>(0);

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

	public List<NodeAngularTree> getChildren() {
		return children;
	}

	public void setChildren(List<NodeAngularTree> children) {
		this.children = children;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
	
}
