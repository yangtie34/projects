package cn.gilight.personal.po;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_LIAO_RELATION_APPLY")
public class TLiaoRelationApply implements java.io.Serializable {

	private static final long serialVersionUID = -7978290955056666625L;
	private String id;
	private String username;
	private String targetUsername;
	private Integer state; // 0：初始，1：通过，2：拒绝，3完结
	private Date createTime;

	// Constructors

	/** default constructor */
	public TLiaoRelationApply() {
	}

	/** minimal constructor */
	public TLiaoRelationApply(String id) {
		this.id = id;
	}

	/** full constructor */
	public TLiaoRelationApply(String id, String username,
			String targetUsername, Integer state, Date createTime) {
		this.id = id;
		this.username = username;
		this.targetUsername = targetUsername;
		this.state = state;
		this.createTime = createTime;
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

	@Column(name = "TARGET_USERNAME", length = 50)
	public String getTargetUsername() {
		return this.targetUsername;
	}

	public void setTargetUsername(String targetUsername) {
		this.targetUsername = targetUsername;
	}

	@Column(name = "STATE", precision = 1, scale = 0)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}