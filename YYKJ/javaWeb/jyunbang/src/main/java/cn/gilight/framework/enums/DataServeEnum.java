package cn.gilight.framework.enums;

public enum DataServeEnum {
	
	
	JXJG("1","自定义教学组织机构"),
	XZJG("2","自定义行政组织机构"),
	FDY("3","辅导员所带班级");
    private String code;  
    private String name;
    private DataServeEnum(String code, String name) {
		this.code = code;
		this.name = name;
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
    
    
}  