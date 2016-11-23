package com.jhkj.mosdc.permission.po;

import java.util.ArrayList;
import java.util.List;

/**
 * @Comments: 菜单节点
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-6-2
 * @TIME: 下午8:28:26
 */
public class TreeNode 
{
//子id
private Long id;
//父id
private Long pid;
//是否有父节点
private boolean expanded;
//是否叶子节点
private boolean leaf;
//描述
private String text;
//URL
private String url;

//权限按钮
private String permiss;
//层次
private Long cc;
    private String cclx;

private boolean checked;

//组织机构类型
private Long zzjglb;
//子节点
public List<TreeNode> children = new ArrayList<TreeNode>();


public TreeNode(Long id,Long pid,String text,String url,String sfyzjd)
{
  this.id = id;
  
  this.pid= pid;
  
  this.text=text;
  
  this.url=url;
  if("0".equals(sfyzjd)){
	  leaf=true;
  }else{
	  leaf=false;
  }
}

public TreeNode(Long id,Long pid,String text,Long cc){
	this.id = id;
	this.pid = pid;
	this.text = text;
	this.cc = cc;
}

public TreeNode(Long id,Long pid,String text,Long cc,String permiss){
	this.id = id;
	this.pid = pid;
	this.text = text;
	this.cc = cc;
	this.permiss = permiss;
}

public TreeNode(Long id,Long pid,String text,String url,String sfyzjd,Long cc,String permiss)
{
  this.id = id;
  
  this.pid= pid;
  
  this.text=text;
  
  this.url=url;
  if("0".equals(sfyzjd)){
	  leaf=true;
  }else{
	  leaf=false;
  }
  this.cc=cc;
  this.permiss =permiss;
}

public TreeNode(Long id,Long pid,String text,String cclx)
{
  this.id = id;
  this.pid= pid;
  this.text=text;
  this.cclx = cclx;
  
}

public TreeNode(Long id,Long pid,String text,String cclx,boolean leaf){
	this.id = id;
	this.pid = pid;
	this.text = text;
	this.cclx = cclx;
	this.leaf = leaf;
}

public TreeNode(Long id,Long pid,boolean expanded,boolean checked,String text,boolean leaf)
{
  this.id = id;
  this.pid= pid;
  this.expanded = expanded;
  this.checked = checked;
  this.text=text;
  this.leaf = leaf;
  
}

public TreeNode(Long id,Long pid,boolean expanded,String text,boolean leaf)
{
  this.id = id;
  this.pid= pid;
  this.expanded = expanded;
  this.text=text;
  this.leaf = leaf;
  
}

public TreeNode(Long id,Long pid,boolean expanded,boolean checked,String text,boolean leaf,Long zzjglb)
{
  this.id = id;
  this.pid= pid;
  this.expanded = expanded;
  this.checked = checked;
  this.text=text;
  this.leaf = leaf;
  this.zzjglb = zzjglb;
  
}


public Long getId() {
	return id;
}



public void setId(Long id) {
	this.id = id;
}



public Long getPid() {
	return pid;
}



public void setPid(Long pid) {
	this.pid = pid;
}



public void setExpanded(boolean expanded) {
	this.expanded = expanded;
}



public boolean getExpanded() {
	/*if(leaf==true){
		expanded=false;
	}else{
		expanded=true;
	}*/
	return expanded;
}

//public void setExpanded(boolean expanded) {
//	this.expanded = expanded;
//}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}



public String getUrl() {
	return url;
}



public void setUrl(String url) {
	this.url = url;
}
public boolean getLeaf() {
	return leaf;
}

public void setLeaf(boolean leaf) {
	this.leaf = leaf;
}

public Long getCc() {
	return cc;
}

public void setCc(Long cc) {
	this.cc = cc;
}

public String getPermiss() {
	return permiss;
}

public void setPermiss(String permiss) {
	this.permiss = permiss;
}

    public String getCclx() {
        return cclx;
    }

    public void setCclx(String cclx) {
        this.cclx = cclx;
    }

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getZzjglb() {
		return zzjglb;
	}

	public void setZzjglb(Long zzjglb) {
		this.zzjglb = zzjglb;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
    
    
}
