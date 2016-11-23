package com.jhkj.mosdc.framework.dto;

/**
 * 缓存DTO 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by gaodj(gaodongjie@126.com) 
 * @DATE:2013-2-25
 * @TIME: 上午11:36:34
 */
public class Cache {
	private String key;//缓存ID
	private Object value;//缓存数据
	private long timeOut;//更新时间
	private boolean expired; //是否终止
	public Cache() {
		super();
	}
	public Cache(String key, Object value, long timeOut, boolean expired) {
		this.key = key;
		this.value = value;
		this.timeOut = timeOut;
		this.expired = expired;
	}
	public String getKey() {
		return key;
	}            
	public long getTimeOut() {                  
		return timeOut;
	}
	public Object getValue() {
		return value;
	}
	public void setKey(String string) {                  
		key = string;          
	}            
	public void setTimeOut(long l) {                  
		timeOut = l;
	}
	public void setValue(Object object) {
		value = object;
	}            
	public boolean isExpired() {                  
		return expired;
	}            
	public void setExpired(boolean b) {                  
		expired = b;
	}
}  
