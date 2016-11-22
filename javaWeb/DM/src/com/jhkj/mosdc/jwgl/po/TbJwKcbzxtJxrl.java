package com.jhkj.mosdc.jwgl.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 
 * @Detail : 教务-课程表子系统-教学日历总表
 * @Author: 韩庆（QQ:haw_king@foxmail.com）
 * @E-mail: IsaidIwillgoon@gmail.com
 * @Date: 2013-6-17
 * @Time: 下午8:26:47
 * 
 */
@Entity
@Table(name="TB_JW_KCBZXT_JXRL")
public class TbJwKcbzxtJxrl {
	
	private Long id;// 主键
	private Long xnId;// 学年
	private Long xqId;// 学期
	private String kssj;// 开始时间
	private String jssj;// 结束时间
	private Long jxzzjgId;// 教学组织结构

	public TbJwKcbzxtJxrl() {
		super();
	}
    
	public TbJwKcbzxtJxrl(Long id) {
		super();
		this.id = id;
	}

	public TbJwKcbzxtJxrl(Long id, Long xnId, Long xqId, String kssj,
			String jssj, Long jxzzjgId) {
		super();
		this.id = id;
		this.xnId = xnId;
		this.xqId = xqId;
		this.kssj = kssj;
		this.jssj = jssj;
		this.jxzzjgId = jxzzjgId;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "XN_ID",length=16)
	public Long getXnId() {
		return xnId;
	}

	public void setXnId(Long xnId) {
		this.xnId = xnId;
	}
	@Column(name = "XQ_ID",length=16)
	public Long getXqId() {
		return xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
	}
	@Column(name = "KSSJ",length=60)
	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	@Column(name = "JSSJ",length=60)
	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	@Column(name = "JXZZJG_ID",length=16)
	public Long getJxzzjgId() {
		return jxzzjgId;
	}

	public void setJxzzjgId(Long jxzzjgId) {
		this.jxzzjgId = jxzzjgId;
	}
}
