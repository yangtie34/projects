package cn.gilight.framework.enums;

/**
 * 季度枚举
 * @author Administrator
 *
 */
public enum QuarterEnum {
	
	
	qj("1","自定义教学组织机构","'09','10','11'"),
	dj("2","自定义行政组织机构","'12','01','02'"),
	cj("3","辅导员所带班级","'03','04','05'"),
	xj("4","辅导员所带班级","'06','07','08'");
	
    private String code;  
    private String name;
    private String value;
    
    private QuarterEnum(String code, String name,String value) {
		this.code = code;
		this.name = name;
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
}  