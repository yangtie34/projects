package com.jhkj.mosdc.output.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @comments:模板表 表_统计功能_模板表
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-12-4
 * @time:上午11:33:17
 * @version :
 */
@Entity
@Table(name = "TB_TJGN_TEMPLATE")
public class TbTjgnTemplate {

	private Long id;// id
	private String mc;// 模板中文表示名称
	private String ywmc;// 模板英文表示名称——供tb_sc_mbb内地类型使用

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MC", length = 30)
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
	@Column(name = "YWMC", length = 15)
	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public TbTjgnTemplate() {
	}

	public TbTjgnTemplate(Long id) {
		this.id = id;
	}

	public TbTjgnTemplate(Long id, String mc, String ywmc) {
		this.id = id;
		this.mc = mc;
		this.ywmc = ywmc;
	}

}