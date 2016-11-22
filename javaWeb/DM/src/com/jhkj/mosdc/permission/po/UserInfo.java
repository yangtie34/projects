package com.jhkj.mosdc.permission.po;

import com.jhkj.mosdc.permiss.util.UserPermiss;

/**
 * @Comments: 用户信息
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-6-11
 * @TIME: 下午9:34:18
 */
public class UserInfo {
	
	/**
	 * ＩＤ
	 */
	private Long id;

	/**
	 * 教职工ID
	 */

	private Long zgId;
	
	/**
	 * 人员类别
	 */
	private Long rylbId;
	
	/**
	 * 中文名
	 */
	private String zwm;

	/**
	 * 用户名
	 */

	private String username;
	
	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 状态ID
	 */

	private Long ztId;

	/**
	 * 部门id代表教学组织机构ID
	 */
	private Long bmId;
	
	/**
	 * 部门名称
	 */
	private String bmmc;
	
	/**
	 * 联系电话
	 */
	private String lxdh;
	
	/**
	 * 学校代码
	 * @return
	 */
	private String xxdm;

	
	/**
	*学籍号
	*/ 
	private String xjh;
	
	/**
	*班级
	*/ 
	private Long bjId;
	
	/**
	*院系
	*/ 
	private Long yxId;
	/**
	*专业
	*/ 
	private Long zyId;
	
	/**
	 * 文化程度代码
	 */
	private String whcddm;
	/**
	 * 学位代码
	 */
	private String xwdm;
	/**
	 * 聘任职务代码
	 */
	private String przwdm;
	/**
	 * 用户数据权限
	 */
	private String permissJxzzIds;
	/**
	 * 用户数据权限
	 */
	private String permissXzzzIds;
	
	private Boolean groupPermiss;

	/**
	 * 职工号
	 */
	private String jzsh;
	/**
	 * 拥有的角色ID
	 */
	private String roleIds;
	@Deprecated
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Deprecated
	public Long getZgId() {
		return zgId;
	}

	public void setZgId(Long zgId) {
		this.zgId = zgId;
	}
	@Deprecated
	public Long getRylbId() {
		return rylbId;
	}

	public void setRylbId(Long rylbId) {
		this.rylbId = rylbId;
	}
	@Deprecated
	public String getZwm() {
		return zwm;
	}

	public void setZwm(String zwm) {
		this.zwm = zwm;
	}
	@Deprecated
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Deprecated
	public Long getZtId() {
		return ztId;
	}

	public void setZtId(Long ztId) {
		this.ztId = ztId;
	}
	@Deprecated
	public Long getBmId() {
		return bmId;
	}

	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}
	@Deprecated
	public String getXxdm() {
		return xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}
	@Deprecated
	public String getXjh() {
		return xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}
	@Deprecated
	public Long getBjId() {
		return bjId;
	}

	public void setBjId(Long bjId) {
		this.bjId = bjId;
	}
	@Deprecated
	public Long getYxId() {
		return yxId;
	}

	public void setYxId(Long yxId) {
		this.yxId = yxId;
	}
	@Deprecated
	public Long getZyId() {
		return zyId;
	}

	public void setZyId(Long zyId) {
		this.zyId = zyId;
	}
	@Deprecated
	public String getWhcddm() {
		return whcddm;
	}

	public void setWhcddm(String whcddm) {
		this.whcddm = whcddm;
	}
	@Deprecated
	public String getXwdm() {
		return xwdm;
	}

	public void setXwdm(String xwdm) {
		this.xwdm = xwdm;
	}
	@Deprecated
	public String getPrzwdm() {
		return przwdm;
	}

	public void setPrzwdm(String przwdm) {
		this.przwdm = przwdm;
	}
	@Deprecated
	public String getJzsh() {
		return jzsh;
	}

	public void setJzsh(String jzsh) {
		this.jzsh = jzsh;
	}
	@Deprecated
	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	@Deprecated
	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	@Deprecated
	public String getPermissJxzzIds() {
//		return permissJxzzIds;
		return UserPermiss.getUser().getCurrentSqlDataPermiss();
//		return UserPermiss.getUser().getCurrentJxzzjgIds();
	}

	public void setPermissJxzzIds(String permissJxzzIds) {
		this.permissJxzzIds = permissJxzzIds;
	}
	@Deprecated
	public String getPermissXzzzIds() {
		return "select id from tb_xzzzjg jg where jg.sfky = 1";
	}

	public void setPermissXzzzIds(String permissXzzzIds) {
		this.permissXzzzIds = permissXzzzIds;
	}
	@Deprecated
	public Boolean getGroupPermiss() {
		return groupPermiss;
	}
	
	public void setGroupPermiss(Boolean groupPermiss) {
		this.groupPermiss = groupPermiss;
	}
	@Deprecated
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	@Deprecated
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	
}
