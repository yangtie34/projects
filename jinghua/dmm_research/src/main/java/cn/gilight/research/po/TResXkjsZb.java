package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_RES_XKJS_ZB")
public class TResXkjsZb implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String description;

	// Constructors

	/** default constructor */
	public TResXkjsZb() {
	}

	/** minimal constructor */
	public TResXkjsZb(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResXkjsZb(String id, String name, String description) {
		this.id = id;
		this.name = name;
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

	@Column(name = "NAME_", length = 120)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 400)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}