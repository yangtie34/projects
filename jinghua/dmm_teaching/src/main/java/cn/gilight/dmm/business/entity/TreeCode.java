package cn.gilight.dmm.business.entity;

import java.util.ArrayList;
import java.util.List;

public class TreeCode {
	private String id;
	private String code_;
	private String name_;
	private String pid;
	private String path_;
	private Integer level_;
	private String levelType;
	private Integer isTrue;
	private Integer order_;
	private List<TreeCode> children = new ArrayList<>();

	
	public TreeCode() {
		super();
	}
	public TreeCode(String id, String code_, String name_, String pid) {
		super();
		this.id = id;
		this.code_ = code_;
		this.name_ = name_;
		this.pid = pid;
	}
	public TreeCode(String id, String code_, String name_, String pid, String path_, Integer level_, String levelType,
			Integer isTrue, Integer order_, List<TreeCode> children) {
		super();
		this.id = id;
		this.code_ = code_;
		this.name_ = name_;
		this.pid = pid;
		this.path_ = path_;
		this.level_ = level_;
		this.levelType = levelType;
		this.isTrue = isTrue;
		this.order_ = order_;
		this.children = children;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode_() {
		return code_;
	}
	public void setCode_(String code_) {
		this.code_ = code_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPath_() {
		return path_;
	}
	public void setPath_(String path_) {
		this.path_ = path_;
	}
	public int getLevel_() {
		return level_;
	}
	public void setLevel_(Integer level_) {
		this.level_ = level_;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	public int getIsTrue() {
		return isTrue;
	}
	public void setIsTrue(Integer isTrue) {
		this.isTrue = isTrue;
	}
	public int getOrder_() {
		return order_;
	}
	public void setOrder_(Integer order_) {
		this.order_ = order_;
	}
	public List<TreeCode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeCode> children) {
		this.children = children;
	}
}
