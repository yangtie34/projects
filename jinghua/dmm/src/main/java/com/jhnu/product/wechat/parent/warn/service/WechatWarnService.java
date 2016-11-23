package com.jhnu.product.wechat.parent.warn.service;

import java.util.List;

import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;
import com.jhnu.system.common.page.Page;

public interface WechatWarnService {
	
	/**
	 * 保存告警信息
	 * @param warn
	 */
	public void saveWechatWarn(List<WechatWarn> warns);
	
	/**
	 * 分页获取告警信息
	 * @param stuId 学生ID
	 * @param page 里面存当前页和每页多少数据
	 * @return
	 */
	public Page getWechatWarns(String stuId,Page page);
	
	/**
	 * 将告警信息更新为已读
	 * @param warns map里面存id值即可
	 */
	public void readWechatWarns(List<WechatWarn> warns);
	
	/**
	 * 获取未读告警信息数
	 * @param stuId 学生ID
	 * @return
	 */
	public int countNoReadWarns(String stuId);
	
	/**
	 * 通过告警类型，获取其上次统计时间
	 * @param warnTypeCode 告警类型代码
	 * @return
	 */
	public String getLastTimeByTypeCode(String warnTypeCode);
	
}
