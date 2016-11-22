package com.jhkj.mosdc.framework.util;

import java.util.ArrayList;
import java.util.List;

public class Node 
{
//子id
private Long id;
//父id
private Long pid;

private String dm;
//是否展开
private boolean expanded;

private boolean leaf;
//描述
private String text;

private String url;

//层次类型
private String cclx;

//权限按钮
private String permiss;
//层次
private Long cc;
//子节点
public List<Node> children = new ArrayList<Node>(0);


public Node(Long id,Long pid,String text,String url,String sfyzjd,Long cc,String permiss,String dm)
{
  this.id = id;
  
  this.pid= pid;
  
  this.text=text;
  
  this.url=url;
  if("1".equals(sfyzjd)){
	  leaf=true;
  }else{
	  leaf=false;
  }
  this.cc=cc;
  this.permiss =permiss;
  this.dm =dm;
}

public Node(Long id,Long pid,String text,String url,String sfyzjd)
{
  this.id = id;
  
  this.pid= pid;
  
  this.text=text;
  
  this.url=url;
  if("1".equals(sfyzjd)){
	  leaf=true;
  }else{
	  leaf=false;
  }
}

public Node(Long id,Long pid,String text,String url,String sfyzjd,Long cc,String permiss)
{
  this.id = id;
  
  this.pid= pid;
  
  this.text=text;
  
  this.url=url;
  if("1".equals(sfyzjd)){
	  leaf=true;
  }else{
	  leaf=false;
  }
  this.cc=cc;
  this.permiss =permiss;
}

public Node(Long id,Long pid,String text,String sfyzjd)
{
  this.id = id;
  
  this.pid= pid;
  
  this.text=text;
  if("1".equals(sfyzjd)){
	  leaf=true;
  }else{
	  leaf=false;
  }
  
}




/**
 * @param id
 * @param pid
 * @param dm
 * @param leaf
 * @param text
 * @param url
 * @param cclx
 * @param cc
 */
public Node(Long id, Long pid, String dm, boolean leaf, String text,
		String url, String cclx, Long cc) {
	super();
	this.id = id;
	this.pid = pid;
	this.dm = dm;
	this.leaf = leaf;
	this.text = text;
	this.url = url;
	this.cclx = cclx;
	this.cc = cc;
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

public String getDm() {
	return dm;
}

public void setDm(String dm) {
	this.dm = dm;
}

public String getCclx() {
	return cclx;
}

public void setCclx(String cclx) {
	this.cclx = cclx;
}

}
