package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_SYS_USER")
public class TSysUser implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String password;
	private String realName;
	private String salt;
	private Integer istrue;
	private String createTime;
	private String updateTime;
	private String deptId;

	// Constructors
	public TSysUser() {
	}

	public TSysUser(long id) {
		this.id = id;
	}

	public TSysUser(long id, String username, String password, String realName,
			String salt, Integer istrue, String createTime, String updateTime,
			String deptId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.realName = realName;
		this.salt = salt;
		this.istrue = istrue;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.deptId = deptId;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "USERNAME", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", length = 60)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "REAL_NAME", length = 60)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "SALT", length = 60)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "ISTRUE", precision = 1, scale = 0)
	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}
	
	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", length = 20)
	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "DEPT_ID", length = 20)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}