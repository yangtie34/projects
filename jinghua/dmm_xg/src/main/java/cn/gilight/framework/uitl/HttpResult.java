package cn.gilight.framework.uitl;

import com.fasterxml.jackson.annotation.JsonGetter;

public class HttpResult {
	private boolean success;
	private String errmsg;
	private Object result;
	
	@JsonGetter("success")
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@JsonGetter("errmsg")
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	@JsonGetter("result")
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
