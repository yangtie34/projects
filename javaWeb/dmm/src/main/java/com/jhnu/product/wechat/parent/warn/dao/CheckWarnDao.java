package com.jhnu.product.wechat.parent.warn.dao;

import java.util.List;

import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;

public interface CheckWarnDao {
	
	/**
	 * 获取考勤告警
	 * @param exe_date yyyy-MM-dd hh:mm:ss
	 */
	public List<WechatWarn> getCheckWarn(String last_time,String exe_time);
	
	
	
	
}