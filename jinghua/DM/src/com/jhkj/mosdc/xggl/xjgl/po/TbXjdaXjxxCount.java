package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
* @ClassName: TbXjdaXjxxCountDetail 
* @Description: 学籍报表显示-统计字段
* @author zhangyc
* @date 2013年12月2日 下午2:35:00 
*
 */
@Entity
@Table(name = "TB_XJDA_COUNT")
public class TbXjdaXjxxCount implements java.io.Serializable {

	private Long id;
	private Long stsx_id;
	private Long user_id;
	private String lx;
	

	public TbXjdaXjxxCount() {
	}

	public TbXjdaXjxxCount(Long id, String lx) {
		this.id = id;
		this.lx = lx;
	}

	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "LX", length = 100)
	public String getLx() {
		return this.lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}
	@Column(name = "STSX_ID", precision = 16, scale = 0)
	public Long getStsx_id() {
		return stsx_id;
	}

	public void setStsx_id(Long stsx_id) {
		this.stsx_id = stsx_id;
	}
	@Column(name = "USER_ID", precision = 16, scale = 0)
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	

}
