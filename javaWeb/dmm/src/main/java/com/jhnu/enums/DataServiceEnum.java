package com.jhnu.enums;

public enum DataServiceEnum {
	
	JXJG(1l,"教学组织机构"),
	ZZJG(2l,"组织机构"),
	FDY(3l,"辅导员负责的班级");
    // 成员变量  
    private Long code;
    private String name;
    
	private DataServiceEnum(Long code, String name) {
		this.code = code;
		this.name = name;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
    
}  