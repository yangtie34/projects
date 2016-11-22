package com.jhnu.enums;

public enum CardPayEnum {
	
	CTXF("210","餐厅消费"),
	CSXF("215","超市消费"),
	XYXF("211","洗浴消费"),
	YLXF("214","医疗消费");

    // 成员变量  
    private String code;  
    private String name;
    private CardPayEnum(String code, String name) {
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