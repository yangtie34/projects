package com.jhnu.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
@Table(name = "T_SYS_USER")
public class User implements Serializable {
	private static final long serialVersionUID = 9179499599148096288L;
	private String id;
    private String username;
    private String password;
    private String real_name;
    private String salt;
    private String create_time;
    private String update_time;
    private String istrue ;
    private String dept_id;
    
    //下面参数不与数据库做关联，用于ModelView
    
    private String dept_name;
    private String role_ids;
    private String role_names;
    private String role_descs;
    private String usernameOrRealName;

	public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String gotUsernameOrRealName() {
		return usernameOrRealName;
	}

	public void setUsernameOrRealName(String usernameOrRealName) {
		this.usernameOrRealName = usernameOrRealName;
	}
	@Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name = "SALT")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String gotCredentialsSalt() {
        return username + salt;
    }

    @Column(name = "ISTRUE")
	public String getIstrue() {
		return istrue;
	}

	public void setIstrue(String istrue) {
		this.istrue = istrue;
	}
	@Column(name = "REAL_NAME")
	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	@Column(name = "CREATE_TIME")
	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	@Column(name = "UPDATE_TIME")
	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	@Column(name = "DEPT_ID")
	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String gotDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String gotRole_ids() {
		return role_ids;
	}

	public void setRole_ids(String role_ids) {
		this.role_ids = role_ids;
	}

	public String gotRole_names() {
		return role_names;
	}

	public void setRole_names(String role_names) {
		this.role_names = role_names;
	}

	public String gotRole_descs() {
		return role_descs;
	}

	public void setRole_descs(String role_descs) {
		this.role_descs = role_descs;
	}

}
