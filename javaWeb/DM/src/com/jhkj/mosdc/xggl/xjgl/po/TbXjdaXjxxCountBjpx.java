package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
* @ClassName: TbXjdaXjxxCountBjpx
* @Description: 学籍报表显示-班级排序
* @author zhangyc
* @date 2013年12月2日 下午2:35:00 
*
 */
@Entity
@Table(name = "TB_XJDA_COUNT_BJPX")
public class TbXjdaXjxxCountBjpx implements java.io.Serializable {

	private Long id;
	private Long bj_id;
	private Long pxxh;
	

	public TbXjdaXjxxCountBjpx() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "BJ_ID", precision = 16, scale = 0)
	public Long getBj_id() {
		return bj_id;
	}

	public void setBj_id(Long bj_id) {
		this.bj_id = bj_id;
	}
	@Column(name = "PXXH", precision = 16, scale = 0)
	public Long getPxxh() {
		return pxxh;
	}

	public void setPxxh(Long pxxh) {
		this.pxxh = pxxh;
	}
	

}
