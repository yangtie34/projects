package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_RES_XKJS_XK" )
public class TResXkjsXk implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String yxId;
	private String level;
	private String description;

	/** default constructor */
	public TResXkjsXk() {
	}

	/** minimal constructor */
	public TResXkjsXk(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResXkjsXk(String id, String name, String yxId, String level,
			String description) {
		this.id = id;
		this.name = name;
		this.yxId = yxId;
		this.level = level;
		this.description = description;
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

	@Column(name = "NAME_", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "YX_ID", length = 20)
	public String getYxId() {
		return this.yxId;
	}

	public void setYxId(String yxId) {
		this.yxId = yxId;
	}

	@Column(name = "LEVEL_", length = 20)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "DESCRIPTION", length = 400)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}