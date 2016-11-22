package com.jhkj.mosdc.output.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @comments:输出功能所需的表--组件表
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-12-4
 * @time:下午05:18:10
 * @version :
 */
@Entity
@Table(name = "TB_SC_ZJ")
public class TbScZj {

	private Long id;
	private String mc;// 组件名称
	private String componentDetail;// 组件详细信息
	private String componentStyle;// 组件类型
	private String componentType;// 组件样式
	private String serviceName;// service名称
	private String bz;// 备注

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
	@Column(name = "COMPONENTDETAIL", length = 300)
	public String getComponentDetail() {
		return componentDetail;
	}

	public void setComponentDetail(String componentDetail) {
		this.componentDetail = componentDetail;
	}
	@Column(name = "COMPONENTSTYLE", length = 30)
	public String getComponentStyle() {
		return componentStyle;
	}

	public void setComponentStyle(String componentStyle) {
		this.componentStyle = componentStyle;
	}
	@Column(name = "COMPONENTTYPE", length = 30)
	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	@Column(name = "SERVICENAME", length = 120)
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	@Column(name = "BZ", length = 300)
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}
