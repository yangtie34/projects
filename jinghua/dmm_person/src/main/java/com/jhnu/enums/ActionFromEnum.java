package com.jhnu.enums;

public enum ActionFromEnum {
	
	XF("01","消费"),
	JS("02","借书"),
	KC("03","课程"),
	TSMJ("04","图书门禁"),
	SSMJ("05","宿舍门禁"),
	QJ("06","请假");

    // 成员变量  
    private String code;  
    private String name;
    private ActionFromEnum(String code, String name) {
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