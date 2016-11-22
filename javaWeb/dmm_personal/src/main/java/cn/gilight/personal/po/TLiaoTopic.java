package cn.gilight.personal.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_LIAO_TOPIC")
public class TLiaoTopic implements java.io.Serializable {
	private static final long serialVersionUID = -8212520910376653663L;
	private String id;
	private String username;
	private String content;
	private Boolean state;
	private Date createTime;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public TLiaoTopic() {
	}

	/** minimal constructor */
	public TLiaoTopic(String id) {
		this.id = id;
	}

	/** full constructor */
	public TLiaoTopic(String id, String username, String content,
			Boolean state, Date createTime, Date updateTime) {
		this.id = id;
		this.username = username;
		this.content = content;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USERNAME", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "CONTENT", length = 3000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "STATE", precision = 1, scale = 0)
	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", length = 11)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}