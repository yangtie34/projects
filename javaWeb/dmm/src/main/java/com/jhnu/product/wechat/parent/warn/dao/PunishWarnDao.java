package com.jhnu.product.wechat.parent.warn.dao;

import java.util.List;

import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;

public interface PunishWarnDao {
	/**
	 * 获取处分告警
	 * @param exe_time
	 * @param last_time
	 * @return
	 */
	public List<WechatWarn> getPunishWarn(String exe_time, String last_time);
	
}
