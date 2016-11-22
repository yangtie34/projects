package cn.gilight.framework.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jhnu.syspermiss.permiss.entity.Resources;

public class NodeZtree implements Serializable{

	private static final long serialVersionUID = -834335446056853528L;

	private String id;
	
	private String name;
	
	private String pId;
	
	private String url;
	
	private String target;
	
	private Resources resources;
	
	private String icon;
	
	private boolean checked= false ;

	private boolean chkDisabled= false ;
	
	private boolean open = false ;
	private List<NodeZtree> children=new ArrayList<NodeZtree>(0);
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<NodeZtree> getChildren() {
		return children;
	}

	public void setChildren(List<NodeZtree> children) {
		this.children = children;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}
	
}
