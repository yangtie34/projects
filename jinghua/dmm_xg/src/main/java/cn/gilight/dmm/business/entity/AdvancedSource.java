package cn.gilight.dmm.business.entity;

/**
 * 数据源
 * 
 * @author xuebl
 * @date 2016年5月16日 上午10:44:53
 */
public class AdvancedSource {

	/**
	 * 数据分组
	 * common：公共；stu：学生；tea：教师；business：业务
	 */
	private String group;
	/**
	 * 数据类型
	 * tree/table
	 */
	private String type;
	/**
	 * 标准代码类型
	 * eg：'SEX_CODE'
	 */
	private String code;
	/**
	 * 标准代码名称
	 * eg：'性别'
	 */
	private String name;
	/**
	 * 是否显示'不限'
	 * eg： '不限' '男' '女'
	 */
	private boolean isall;
	/**
	 * 表名
	 * eg：T_CODE/T_CODE_EDUCATION
	 */
	private String table;
	/**
	 * 数据服务
	 * eg：bean?method
	 */
	private String service;
	/**
	 * 参数(针对数据服务，支持两种类型)
	 * eg："name,01" / bean?method
	 */
	private String param;
	
	
	public AdvancedSource() {
	}
	public AdvancedSource(String group, String type, String code, String name, 
			boolean isall, String table_, String service, String param) {
		this.group = group;
		this.type = type;
		this.code = code;
		this.name = name;
		this.isall = isall;
		this.table = table_;
		this.service = service;
		this.param = param;
	}
	
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isIsall() {
		return isall;
	}
	public void setIsall(Boolean isall) {
		this.isall = isall==null ? false : isall;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
