package com.jhkj.mosdc.permission.po;

import java.util.ArrayList;
import java.util.List;

/**
 * @Comments: 权限树菜单实体
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-5-16
 * @TIME: 下午01:56:40
 */
public class MenuNode {
	//子id
	private Long id;
	//父id
	private Long pid;
	//是否有父节点
	private boolean expanded;

	private boolean leaf;
	//描述
	private String text;

	private String url;

	//权限按钮
	private String permiss;
	//层次
	private Long cc;
	
	  private String cclx;
	//树节点类型
	private int type;
	
	private boolean selectFlag;
	private Long pxh;
	//子节点
	public List<MenuNode> children = new ArrayList<MenuNode>();
	
	public MenuNode(Long id,Long pid,String text,String url,String sfyzjd,Long cc,String permiss)
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
	
	public MenuNode(Long id,Long pid,String text,String cclx)
	{
	  this.id = id;
	  this.pid= pid;
	  this.text=text;
	  this.cclx = cclx;
	  
	}
	
	
	public MenuNode(Long id,Long pid,boolean expanded,String text,boolean leaf)
	{
	  this.id = id;
	  this.pid= pid;
	  this.expanded = expanded;
	  this.text=text;
	  this.leaf = leaf;
	  
	}
	
	public MenuNode(Long id,Long pid,boolean expanded,String text,boolean leaf,String cclx)
	{
	  this.id = id;
	  this.pid= pid;
	  this.expanded = expanded;
	  this.text=text;
	  this.leaf = leaf;
	  this.cclx = cclx;
	}
	public MenuNode(Long id,Long pid,boolean expanded,String text)
	{
	  this.id = id;
	  this.pid= pid;
	  this.expanded = expanded;
	  this.text=text;
	}
	
	public MenuNode(Long id,Long pid,boolean expanded,String text,boolean leaf,Long cc)
	{
	  this.id = id;
	  this.pid= pid;
	  this.expanded = expanded;
	  this.text=text;
	  this.leaf = leaf;
	  this.cc = cc;
	  
	}
	
	public MenuNode(Long id,Long pid,boolean expanded,String text,boolean leaf,Long cc,boolean selectFlag)
	{
	  this.id = id;
	  this.pid= pid;
	  this.expanded = expanded;
	  this.text=text;
	  this.leaf = leaf;
	  this.cc = cc;
	  this.selectFlag = selectFlag;
	}
	
	public MenuNode() {
		// TODO Auto-generated constructor stub
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
	public boolean getExpanded() {
		/*if(leaf==true){
			expanded=false;
		}else{
			expanded=true;
		}*/
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
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
	public String getPermiss() {
		return permiss;
	}
	public void setPermiss(String permiss) {
		this.permiss = permiss;
	}
	public Long getCc() {
		return cc;
	}
	public void setCc(Long cc) {
		this.cc = cc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getCclx() {
		return cclx;
	}

	public void setCclx(String cclx) {
		this.cclx = cclx;
	}

	public boolean isSelectFlag() {
		return selectFlag;
	}

	public void setSelectFlag(boolean selectFlag) {
		this.selectFlag = selectFlag;
	}

	public List<MenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}

	public Long getPxh() {
		return pxh;
	}

	public void setPxh(Long pxh) {
		this.pxh = pxh;
	}
	
	
}
