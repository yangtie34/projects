package com.jhnu.product.wechat.parent.warn.service;


public interface LeaveWarnService {
	/**
	 * 保存请假告警
	 * @param exe_time
	 * @return
	 */
	public void saveLeaveWarn(String exe_time);
	
	/**
	 * 保存销假告警
	 * @param exe_time
	 * @return
	 */
	public void saveCancelLeaveWarn(String exe_time);
	
	/**
	 * 保存续假告警
	 * @param exe_time
	 */
	public void saveKeepLeaveWarn(String exe_time);
	
}
