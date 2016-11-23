/*
 * 
 * Copyright (c) 2009 HZH All Rights Reserved.
 */
package com.jhkj.mosdc.permiss.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * 系统表-功能菜单资源<br>
 * TableName: TP_MENU<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_MENU")
public class TpMenu implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 菜单资源名称
	 */
	private String mc;
	/**
	 * 菜单所属分类标识
	 */
	private Integer cdssfl;
	/**
	 * 排序号
	 */
	private Integer sortnum;
	/**
	 * 父节点ID
	 */
	private Long parentId;
	/**
	 * 菜单路径
	 */
	private String url;
	/**
	 * 是否叶子节点(0非叶子，１叶子)
	 */
	private Boolean leaf;
	/**
	 * 标识
	 */
	private String funtype;
	/**
	 * 描述
	 */
	private String fundesc;
	
	/**
	 * 全息码
	 */
	private String qxm;
	
	/**
	 * 深度
	 */
	private Integer deep;

	public TpMenu() {
	}

	public TpMenu(Long id) {
		this.id = id;
	}
 
	public TpMenu(Long id, String mc, Integer cdssfl, Integer sortnum,
			Long parentId, String url, Boolean leaf, String funtype,
			String fundesc,String qxm,Integer deep) {
		this.id = id;
		this.mc = mc;
		this.cdssfl = cdssfl;
		this.sortnum = sortnum;
		this.parentId = parentId;
		this.url = url;
		this.leaf = leaf;
		this.funtype = funtype;
		this.fundesc = fundesc;
		this.qxm = qxm;
		this.deep = deep;
	}

	/**  
	 * set ID.
	 * @return ID
	 */
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	/**
	 * get ID.
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**  
	 * set 菜单资源名称.
	 * @return 菜单资源名称
	 */

	@Column(name = "MC", length = 100)
	public String getMc() {
		return this.mc;
	}

	/**
	 * get 菜单资源名称.
	 * @param mc 菜单资源名称
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}

	/**  
	 * set 菜单所属分类标识.
	 * @return 菜单所属分类标识
	 */

	@Column(name = "CDSSFL", precision = 5, scale = 0)
	public Integer getCdssfl() {
		return this.cdssfl;
	}

	/**
	 * get 菜单所属分类标识.
	 * @param cdssfl 菜单所属分类标识
	 */
	public void setCdssfl(Integer cdssfl) {
		this.cdssfl = cdssfl;
	}

	/**  
	 * set 排序号.
	 * @return 排序号
	 */

	@Column(name = "SORTNUM", precision = 5, scale = 0)
	public Integer getSortnum() {
		return this.sortnum;
	}

	/**
	 * get 排序号.
	 * @param sortnum 排序号
	 */
	public void setSortnum(Integer sortnum) {
		this.sortnum = sortnum;
	}

	/**  
	 * set 父节点ID.
	 * @return 父节点ID
	 */

	@Column(name = "PARENT_ID", precision = 16, scale = 0)
	public Long getParentId() {
		return this.parentId;
	}

	/**
	 * get 父节点ID.
	 * @param parentId 父节点ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**  
	 * set 菜单路径.
	 * @return 菜单路径
	 */

	@Column(name = "URL", length = 500)
	public String getUrl() {
		return this.url;
	}

	/**
	 * get 菜单路径.
	 * @param url 菜单路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**  
	 * set 是否叶子节点(0非叶子，１叶子).
	 * @return 是否叶子节点(0非叶子，１叶子)
	 */

	@Column(name = "LEAF", precision = 1, scale = 0)
	public Boolean getLeaf() {
		return this.leaf;
	}

	/**
	 * get 是否叶子节点(0非叶子，１叶子).
	 * @param leaf 是否叶子节点(0非叶子，１叶子)
	 */
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	/**  
	 * set 标识.
	 * @return 标识
	 */

	@Column(name = "FUNTYPE", length = 50)
	public String getFuntype() {
		return this.funtype;
	}

	/**
	 * get 标识.
	 * @param funtype 标识
	 */
	public void setFuntype(String funtype) {
		this.funtype = funtype;
	}

	/**  
	 * set 描述.
	 * @return 描述
	 */

	@Column(name = "FUNDESC", length = 100)
	public String getFundesc() {
		return this.fundesc;
	}

	/**
	 * get 描述.
	 * @param fundesc 描述
	 */
	public void setFundesc(String fundesc) {
		this.fundesc = fundesc;
	}
	/**  
	 * set 全息码.
	 * @return 全息码
	 */

	@Column(name = "QXM", length = 50)
	public String getQxm() {
		return qxm;
	}

	public void setQxm(String qxm) {
		this.qxm = qxm;
	}

	@Column(name = "DEEP", precision = 5, scale = 0)
	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

}
