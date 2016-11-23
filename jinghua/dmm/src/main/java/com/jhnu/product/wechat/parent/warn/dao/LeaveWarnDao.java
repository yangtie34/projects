package com.jhnu.product.wechat.parent.warn.dao;

import java.util.List;

import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;

public interface LeaveWarnDao {
	/**
	 * 获取请假告警
	 * @param exe_time
	 * @param last_time
	 * @return
	 */
	public List<WechatWarn> getLeaveWarn(String exe_time, String last_time);
	
	/**
	 * 获取销假告警
	 * @param exe_time
	 * @param last_time
	 * @return
	 */
	public List<WechatWarn> getCancelLeaveWarn(String exe_time, String last_time);
	
	/**
	 * 获取续假告警
	 * @param exe_time
	 * @param last_time
	 * @return
	 */
	public List<WechatWarn> getKeepLeaveWarn(String exe_time);
	
	/**
	 * 将续假信息设置成已通知状态
	 */
	public void informKeepLeave();
}
