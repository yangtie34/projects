package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统级配置参数信息表:比如每页显示记录数，是否计算个税，默认密码等。
 */
@Entity
@Table(name = "TS_CONFIG")
public class TsConfig implements java.io.Serializable {

	private static final long serialVersionUID = -7270507385188558645L;
	/**
	 * 代码
	 */
	private String code;
	/**
	 * 参数值
	 */
	private String configValue;
	/**
	 * 描述
	 */
	private String ms;
	
	
	@Id
	@Column(name = "CODE", unique = true, nullable = false, length = 100)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "CONFIG_VALUE", length = 50)
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	@Column(name = "MS", length = 256)
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}


}