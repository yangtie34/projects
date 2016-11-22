package com.jhkj.mosdc.framework.message.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ts_LETTER_Addressee")
public class TsLetterAddressee {

	private Long id;
	private Long jzgId;
	private String jzgxm;
	private Long letterId;
	private Boolean readYet;
	private String readTime;
	private Boolean sfky;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "JZG_ID", precision = 16, scale = 0)
	public Long getJzgId() {
		return jzgId;
	}
	public void setJzgId(Long jzgId) {
		this.jzgId = jzgId;
	}
	@Column(name = "JZGXM", length = 60)
	public String getJzgxm() {
		return jzgxm;
	}
	public void setJzgxm(String jzgxm) {
		this.jzgxm = jzgxm;
	}
	@Column(name = "LETTER_ID", precision = 16, scale = 0)
	public Long getLetterId() {
		return letterId;
	}
	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}
	@Column(name = "READ_YET", precision = 1, scale = 0)
	public Boolean getReadYet() {
		return readYet;
	}
	public void setReadYet(Boolean readYet) {
		this.readYet = readYet;
	}
	@Column(name = "READ_TIME", length = 60)
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return sfky;
	}
	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}
	
}
