package cn.gilight.personal.po;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_LIAO_RELATION")
public class TLiaoRelation implements java.io.Serializable {
	private static final long serialVersionUID = -234403442997645544L;
	private String id;
	private String usernamea;
	private String usernameb;
	private String applyId;
	private Date createTime;

	// Constructors

	/** default constructor */
	public TLiaoRelation() {
	}

	/** minimal constructor */
	public TLiaoRelation(String id) {
		this.id = id;
	}

	/** full constructor */
	public TLiaoRelation(String id, String usernamea, String usernameb,
			String applyId, Date createTime) {
		this.id = id;
		this.usernamea = usernamea;
		this.usernameb = usernameb;
		this.applyId = applyId;
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

	@Column(name = "USERNAMEA", length = 50)
	public String getUsernamea() {
		return this.usernamea;
	}

	public void setUsernamea(String usernamea) {
		this.usernamea = usernamea;
	}

	@Column(name = "USERNAMEB", length = 50)
	public String getUsernameb() {
		return this.usernameb;
	}

	public void setUsernameb(String usernameb) {
		this.usernameb = usernameb;
	}

	@Column(name = "APPLY_ID", length = 20)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}