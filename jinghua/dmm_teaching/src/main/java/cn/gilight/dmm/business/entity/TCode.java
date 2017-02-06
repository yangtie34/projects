package cn.gilight.dmm.business.entity;

/**
 * 标准代码
 * 
 * @author xuebl
 * @date 2016年5月11日 上午10:47:23
 */
public class TCode {

	/**
	 * 代码
	 */
	private String code_;
	/**
	 * 名称
	 */
	private String name_;
	/**
	 * 代码-分类
	 */
	private String code_category;
	/**
	 * 代码类型
	 */
	private String code_type;
	/**
	 * 代码类型名称
	 */
	private String codetype_name;
	/**
	 * 是否使用
	 */
	private String istrue;
	/**
	 * 排序
	 */
	private String order_;
	
	
	public TCode() {
		super();
	}

	public TCode(String code_, String name_, String code_category, String code_type, String codetype_name,
			String istrue, String order_) {
		super();
		this.code_ = code_;
		this.name_ = name_;
		this.code_category = code_category;
		this.code_type = code_type;
		this.codetype_name = codetype_name;
		this.istrue = istrue;
		this.order_ = order_;
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

	public String getCode_category() {
		return code_category;
	}

	public void setCode_category(String code_category) {
		this.code_category = code_category;
	}

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public String getCodetype_name() {
		return codetype_name;
	}

	public void setCodetype_name(String codetype_name) {
		this.codetype_name = codetype_name;
	}

	public String getIstrue() {
		return istrue;
	}

	public void setIstrue(String istrue) {
		this.istrue = istrue;
	}

	public String getOrder_() {
		return order_;
	}

	public void setOrder_(String order_) {
		this.order_ = order_;
	}
}
