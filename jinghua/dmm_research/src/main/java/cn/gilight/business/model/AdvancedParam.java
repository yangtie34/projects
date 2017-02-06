package cn.gilight.business.model;

/**
 * 数据参数
 * 
 * @author xuebl
 * @date 2016年5月16日 上午11:18:12
 */
public class AdvancedParam {

	/**
	 * 数据分组
	 * common：公共；stu：学生；tea：教师；business：业务
	 */
	private String group;
	/**
	 * 代码
	 * eg：'SEX_CODE'
	 */
	private String code;
	/**
	 * 值
	 * eg:"'01','02'"
	 */
	private String values;
	
	
	public AdvancedParam() {
		super();
	}
	public AdvancedParam(String group, String code, String values) {
		super();
		this.group = group;
		this.code = code;
		this.values = values;
	}
	
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
}
