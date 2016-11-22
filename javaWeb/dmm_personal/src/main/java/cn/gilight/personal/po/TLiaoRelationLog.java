package cn.gilight.personal.po;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_LIAO_RELATION_LOG")
public class TLiaoRelationLog implements java.io.Serializable {
	private static final long serialVersionUID = 4188841749347696881L;
	private String id;
	private String usernamea;
	private String usernameb;
	private String operation;
	private Date createTime;

	// Constructors

	/** default constructor */
	public TLiaoRelationLog() {
	}

	/** minimal constructor */
	public TLiaoRelationLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public TLiaoRelationLog(String id, String usernamea, String usernameb,
			String operation, Date createTime) {
		this.id = id;
		this.usernamea = usernamea;
		this.usernameb = usernameb;
		this.operation = operation;
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

	@Column(name = "OPERATION", length = 20)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}