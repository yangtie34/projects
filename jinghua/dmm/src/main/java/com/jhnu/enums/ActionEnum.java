package com.jhnu.enums;

public enum ActionEnum {
	
	CTXF("01","餐厅消费"),
	CSXF("02","超市消费"),
	XYXF("03","洗浴消费"),
	KC("04","课程"),
	GJ("05","告警"),
	JS("06","借书"),
	TSMJ("07","图书门禁"),
	SSMJ("08","宿舍门禁"),
	YLXF("09","医疗消费"),
	QJ("10","请假"),
	ZZMM("11","POLITICS_CODE");//政治面貌
    // 成员变量  
    private String code;  
    private String name;
    private ActionEnum(String code, String name) {
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