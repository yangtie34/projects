package com.jhnu.enums;

public enum WarnLevelEnum {  
	
	YZJG("01","严重警告"),
	JG("02","警告"),
	PT("03","普通"),
	LH("04","良好"),
	BY("05","表扬");

    // 成员变量  
    private String code;  
    private String name;
    private WarnLevelEnum(String code, String name) {
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