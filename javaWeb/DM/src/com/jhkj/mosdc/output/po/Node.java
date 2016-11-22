package com.jhkj.mosdc.output.po;

import java.util.ArrayList;
import java.util.List;

public class Node {
	// 节点id
	private Long id;
	// 父节点id
	private Long pid;
	// 是否可扩展
	private boolean expanded;
	// 是否叶子节点
	private boolean leaf;
	// 代码
	private String dm;
	// 名称
	private String text;
	// 层次类型
	private String cclx;
	// 子节点们
	private List<Node> children = new ArrayList<Node>();

	public Node(Long id, Long pid, String dm, String text, String cclx,String leaf) {
		this.id = id;
		this.pid = pid;
		this.dm = dm;
		this.text = text;
		this.cclx = cclx;
		if("1".equals(leaf)){
			this.leaf = true;
		}else if("0".equals(leaf)){
			this.leaf = false;
		}
	}
	public Node(){
		
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

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isLeaf() {
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

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
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
	/**
	 * 循环遍历获取所有的叶子节点。
	 * @param nodes 叶子节点空集合
	 * @param node 
	 */
	public void getAllYzNode(List<Node> nodes,Node node){
		if(node.isLeaf()){
			nodes.add(node);
		}else{
			for(Node cNode : node.getChildren()){
				getAllYzNode(nodes,cNode);
			}
		}
	}
}
