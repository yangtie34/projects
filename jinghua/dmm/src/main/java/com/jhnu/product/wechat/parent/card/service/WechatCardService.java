package com.jhnu.product.wechat.parent.card.service;

import java.util.List;
import java.util.Map;

import com.jhnu.product.wechat.parent.card.entity.CardAnalyzeData;
import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;

public interface WechatCardService {
	/**
	 * 依据学生标识获取学生成绩消费统计。
	 * @param stuId
	 * @param timeType
	 * @return
	 */
	public CardAnalyzeData getAnalyzeData(String stuId,String timeType);
	
	/**
	 * 保存消费统计数据
	 * @param cads
	 */
	public void saveWachatCardAna2Log(List<TlWechatConsumAnalyze> cads);
	
	/**
	 * 根据日期类型、消费类型获得统计数据
	 * @param cardDeal
	 * @return
	 */
	public Map<String,List<TlWechatConsumAnalyze>> getWachatCardAnaData(String groupCode);
	/**
	 * 获取所有的消费统计对象 
	 * @return
	 */
	public List<TlWechatConsumAnalyze> getTlWechatConsumAnalyzes(String timeType ,String groupCode);
	/**
	 * 更新数据对象
	 * @param list
	 */
	public void updateTlWechatConsumAnalyzes(List<TlWechatConsumAnalyze> list);
}
