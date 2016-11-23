package com.jhkj.mosdc.output.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @comments:表_统计功能_自定义统计页面表
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-12-4
 * @time:下午02:30:45
 * @version :
 */
@Entity
@Table(name = "TB_TJGN_ZDYTJYM")
public class TbTjgnZdytjym {

	private Long id;// id
	private String mc;// 页面名称
	private Long fid;// 父id 用于关联菜单树
	private String cdlj;//该页面对应的路径（菜单路径）
	private Long userId;// 用户id
	private Long sfsy;// 是否私有（非私有即公有，公有需要通过审核才能使用）
	private Long sftgsh;// 是否通过审核

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "FID", length = 16)
	public Long getFid() {
		return fid;
	}
  
	public void setFid(Long fid) {
		this.fid = fid;
	}

	@Column(name = "CDLJ", length = 500)
	public String getCdlj() {
		return cdlj;
	}

	public void setCdlj(String cdlj) {
		this.cdlj = cdlj;
	}

	@Column(name = "MC", length = 30)
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "USER_ID", length = 16)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "SFSY", length = 16)
	public Long getSfsy() {
		return sfsy;
	}

	public void setSfsy(Long sfsy) {
		this.sfsy = sfsy;
	}

	@Column(name = "SFTGSH", length = 16)
	public Long getSftgsh() {
		return sftgsh;
	}

	public void setSftgsh(Long sftgsh) {
		this.sftgsh = sftgsh;
	}

}
