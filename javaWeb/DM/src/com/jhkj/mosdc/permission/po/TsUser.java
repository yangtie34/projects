package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 功能说明:用户
 * 
 * @author: 党冬(mrdangdong@163.com)
 * @DATE:2012-5-31 @TIME: 下午10:23:42
 */
@Entity
@Table(name = "TS_USER")
public class TsUser implements java.io.Serializable {

//	private static final long serialVersionUID = -3416446433086805369L;

	private Long id;

	/**
	 * 职工ID
	 */

	private Long zgId;
	
	/**
	 * 人员类别
	 */
	private Long rylbId;
	
	/**
	 * 中文名
	 */
//	private String zwm;

	/**
	 * 用户姓名
	 */

	private String username;
	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 用户密码
	 */

	private String password;

	/**
	 * 状态ID
	 */

	private Long ztId;
	

	/**
	 * 部门id
	 */
	private Long bmId;
	
	/**
	 * 学校代码
	 * @return
	 */
	private String xxdm;
	/**
	 * 组权限
	 * @return
	 */
	private Boolean groupPermiss;
	/**
	 * 用户名对照信息
	 */
	private String usernameComparison;
	/**
	 * 密码对照信息
	 */
	private String passwordComparison;
	
	@Column(name = "USERNAME_COMPARISON", nullable = true, length = 100)
	public String getUsernameComparison() {
		return usernameComparison;
	}

	public void setUsernameComparison(String usernameComparison) {
		this.usernameComparison = usernameComparison;
	}
	@Column(name = "PASSWORD_COMPARISON", nullable = true, length = 100)
	public String getPasswordComparison() {
		return passwordComparison;
	}

	public void setPasswordComparison(String passwordComparison) {
		this.passwordComparison = passwordComparison;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ZG_ID", precision = 16, scale = 0)
	public Long getZgId() {
		return zgId;
	}

	public void setZgId(Long zgId) {
		this.zgId = zgId;
	}

	@Column(name = "USER_NAME", nullable = false, length = 100)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "LOGIN_NAME",  length = 100)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PASSWORD",  length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ZT_ID", precision = 16, scale = 0)
	public Long getZtId() {
		return ztId;
	}

	public void setZtId(Long ztId) {
		this.ztId = ztId;
	}

	@Column(name = "BM_ID", precision = 16, scale = 0)
	public Long getBmId() {
		return bmId;
	}

	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}

	@Column(name = "RYLB_ID", precision = 16, scale = 0)
	public Long getRylbId() {
		return rylbId;
	}

	public void setRylbId(Long rylbId) {
		this.rylbId = rylbId;
	}
	
	@Column(name = "XXDM", length = 20)
	public String getXxdm() {
		return xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}
	
	@Column(name = "GROUPPERMISS", precision = 16, scale = 0)
	public Boolean getGroupPermiss() {
		return groupPermiss;
	}

	public void setGroupPermiss(Boolean groupPermiss) {
		this.groupPermiss = groupPermiss;
	}

	
	
}