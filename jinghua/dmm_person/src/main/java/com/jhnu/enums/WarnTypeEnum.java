
package com.jhnu.enums;

public enum WarnTypeEnum { 
	
	XFYJ("01","消费预警"),
	XYYJ("02","学业预警"),
	QJYJ("03","请假预警"),
	KQYJ("04","考勤预警"),
	CFYJ("05","处分预警");

    // 成员变量  
    private String code;  
    private String name;
    private WarnTypeEnum(String code, String name) {
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