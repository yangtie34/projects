package com.jhnu.cas.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoginResultBean implements Serializable{

	private static final long serialVersionUID = -1660142276536775552L;

	private String errorMas;
	
	private String username;
	
	private Map<String,Object> data =new HashMap<String, Object>();
	
	private Object object;
	
	private boolean isTrue = true;
	
	public  LoginResultBean(){
		
	}
	
	public  LoginResultBean(String username){
		this.username=username;
	}

	public String getErrorMas() {
		return errorMas;
	}

	public void setErrorMas(String errorMas) {
		this.errorMas = errorMas;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
