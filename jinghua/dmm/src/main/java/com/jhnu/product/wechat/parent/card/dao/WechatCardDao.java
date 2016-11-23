package com.jhnu.product.wechat.parent.card.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;

public interface WechatCardDao {
	/**
	 * 依据学生标识从消费分析结果表获取数据。
	 * @param stuId
	 * @param anaType
	 * @param timeType
	 * @return
	 */
	public List<Map<String, Object>> getCustomAnalyzeResult(String stuId,String anaType,String timeType);
	/**
	 * 依据学生标识从消费分析结果表获取数据。
	 * @param stuId
	 * @param timeType
	 * @return
	 */
	public List<Map<String, Object>> getCustomAnalyzeResult(String stuId,String timeType);
	
	/**
	 * 获取全校男、女学生的日均消费水平
	 * @param anaType
	 * @param timeType
	 * @param sexCode
	 * @return
	 */
	public double getCustomAvg4AllStu(String anaType,String timeType,String sexCode);
	/**
	 * 保存消费统计数据到日志表中
	 * @param cads
	 */
	public void saveWachatCardAna2Log(List<TlWechatConsumAnalyze> cads);
	/**
	 * 根据日期类型、消费类型获得统计数据
	 * @param timeType
	 * @param dateSection
	 * @param cardDealIds
	 * @return
	 */
	public List<TlWechatConsumAnalyze> getWachatCardAnaData(String timeType,String[] dateSection,String groupcode,List<String> cardDealIds);
	
	public List<TlWechatConsumAnalyze> getTlWechatConsumAnalyzes(String timeType ,String groupCode);
	public void updateTlWechatConsumAnalyzes(List<TlWechatConsumAnalyze> list);
}
