package com.jhnu.cas.entity;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 9179499599148096288L;
	private Long id;
    private String username;
    private String password;
    private String real_name;
    private String salt;
    private String create_time;
    private String update_time;
    private Integer istrue ;
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

    public String getUsernameOrRealName() {
		return usernameOrRealName;
	}

	public void setUsernameOrRealName(String usernameOrRealName) {
		this.usernameOrRealName = usernameOrRealName;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }


	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getRole_ids() {
		return role_ids;
	}

	public void setRole_ids(String role_ids) {
		this.role_ids = role_ids;
	}

	public String getRole_names() {
		return role_names;
	}

	public void setRole_names(String role_names) {
		this.role_names = role_names;
	}

	public String getRole_descs() {
		return role_descs;
	}

	public void setRole_descs(String role_descs) {
		this.role_descs = role_descs;
	}

}
