package com.jhnu.framework.enums;

public enum PeopleTypeEnum {
	
	STU("01","学生"),
	TEA("07","教师");
	
    private String code;  
    private String name;
    
    private PeopleTypeEnum(String code, String name) {
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
