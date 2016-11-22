package com.jhkj.mosdc.framework.dto;

public class Info {
	//返回成功值
	private boolean success=false;
	//记录数据
	private Object data = new Object();
	//记录数
	private int count =0;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
