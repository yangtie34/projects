package com.jhkj.mosdc.framework.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 桌面入口快捷方式实体类
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-29
 * @TIME: 上午10:15:34
 */
@Entity
@Table(name = "ts_desktop_shortcut")
public class DesktopShortCut {

	private Long id;
	
	private Long shortcutId;//快捷方式id
	
	private Integer sortnumber;//排序序号
	
	private Long userId;//用户id
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "SHORTCUT_ID", precision = 10, scale = 0)
	public Long getShortcutId() {
		return shortcutId;
	}

	public void setShortcutId(Long shortcutId) {
		this.shortcutId = shortcutId;
	}
	@Column(name = "SORTNUMBER", precision = 16, scale = 0)
	public Integer getSortnumber() {
		return sortnumber;
	}
	public void setSortnumber(Integer sortnumber) {
		this.sortnumber = sortnumber;
	}
	
	@Column(name = "USER_ID", precision = 16, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
