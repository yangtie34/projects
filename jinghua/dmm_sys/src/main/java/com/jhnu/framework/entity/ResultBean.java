package com.jhnu.framework.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultBean implements Serializable{

	private static final long serialVersionUID = 1515729625377543831L;
	
	private String name;
	
	private Map<String,Object> data =new HashMap<String, Object>();
	
	private Object object;
	
	private boolean isTrue = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public boolean getIsTrue() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
