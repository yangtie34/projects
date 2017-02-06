package cn.gilight.framework.uitl;

import java.io.Serializable;

public class JobResultBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6670519197378647056L;

	private String msg;
	
	private boolean isTrue = true;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}
	
}
