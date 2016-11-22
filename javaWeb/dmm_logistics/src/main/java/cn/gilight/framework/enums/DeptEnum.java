package cn.gilight.framework.enums;

public enum DeptEnum {
	
	ALL("all","组织机构"),
	DQ("01","党群组织机构"),
	XZ("02","行政组织机构"),
	JF("04","教辅组织机构");
	
    private String code;  
    private String name;
    private DeptEnum(String code, String name) {
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