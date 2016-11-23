package com.jhkj.mosdc.sc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构对象。
 * User: Administrator
 * Date: 13-8-1
 * Time: 下午3:03
 */
public class TreeNode {
    private Long id;
    private String text;
    private Long pid;
    private Long cc;
    private String cclx;
    private boolean leaf;
    private boolean expanded;
    private boolean checked;
    private String qxm;
    private List<TreeNode> children = new ArrayList<TreeNode>();

    public TreeNode() {
    }

    public TreeNode(Long id, String text, Long pid, Long cc, String cclx, boolean leaf, String qxm) {
        this.id = id;
        this.text = text;
        this.pid = pid;
        this.cc = cc;
        this.cclx = cclx;
        this.leaf = leaf;
        this.qxm = qxm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getCc() {
        return cc;
    }

    public void setCc(Long cc) {
        this.cc = cc;
    }

    public String getCclx() {
        return cclx;
    }

    public void setCclx(String cclx) {
        this.cclx = cclx;
    }

    public boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getQxm() {
        return qxm;
    }

    public void setQxm(String qxm) {
        this.qxm = qxm;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

	public String toString() {
		StringBuilder json = new StringBuilder();
    	json.append("{");
    	json.append("id:").append(this.getId());
    	json.append(",text:'").append(this.getText()).append("'");
    	json.append(",pid:").append(this.getPid());
    	json.append(",qxm:'").append(this.getQxm()).append("'");
    	json.append(",cclx:'").append(this.getCclx()).append("'");
    	json.append(",children:").append(this.getChildren());
    	json.append("}");
    	return json.toString();
	}
    
}
