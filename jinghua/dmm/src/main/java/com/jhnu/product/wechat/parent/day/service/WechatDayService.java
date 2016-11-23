package com.jhnu.product.wechat.parent.day.service;

import java.util.List;

import com.jhnu.product.wechat.parent.day.entity.WechatDay;

public interface WechatDayService {
	
	/**
	 * 保存一天生活
	 * @param days
	 */
	public void saveWechatDays(List<WechatDay> days);
	
	/**
	 * 按照时间统计，保存消费情况
	 * @param exeTime 当前统计时间
	 */
	public void saveWechatDaysByCard(String exeTime);
	
	/**
	 * 按照时间统计，保存课程情况
	 * @param action_date 当前统计日期 yyyy-MM-dd
	 */
	public void saveWechatDaysByCourse(String action_date);
	
	/**
	 * 按照时间统计，保存告警情况
	 * @param exeTime 当前统计时间
	 */
	public void saveWechatDaysByWarn(String exeTime);
	
	/**
	 * 按照时间统计，保存宿舍门禁情况
	 * @param exeTime 当前统计时间
	 */
	public void saveWechatDaysByDormRKE(String exeTime);
	
	/**
	 * 按照时间统计，保存图书门禁情况
	 * @param exeTime 当前统计时间
	 */
	public void saveWechatDaysByBookRKE(String exeTime);
	
	/**
	 * 按照时间统计，保存请假信息
	 * @param exeTime 当前统计时间
	 */
	public void saveWechatDaysByLeave(String exeTime);
	
	/**
	 * 按照时间统计，保存销假信息
	 * @param exeTime 当前统计时间
	 */
	public void saveWechatDaysByCancelLeave(String exeTime);

	/**
	 * 获取生活记录集合
	 * @param day 条件
	 * @return
	 */
	public List<WechatDay> getWechatDaysByThis(WechatDay day);
	
	/**
	 * 获取最近一次统计时间到结果的时间
	 * @param action_from 动作源
	 * @return
	 */
	public String getLaseExeTimeByFrom(String action_from);
	
	/**
	 * 精确到分钟，执行的JOB文件
	 * @param exeTime 统计时间 yyyy-MM-dd HH:mm:ss
	 */
	public void exeDayJobByCardTime(String exeTime);
	
	/**
	 * 精确到分钟，执行的JOB文件
	 * @param exeTime 统计时间 yyyy-MM-dd HH:mm:ss
	 */
	public void exeDayJobByBookRkeTime(String exeTime);
	
	/**
	 * 精确到分钟，执行的JOB文件
	 * @param exeTime 统计时间 yyyy-MM-dd HH:mm:ss
	 */
	public void exeDayJobByDormRKETime(String exeTime);
	
	/**
	 * 精确到天执行的JOB文件
	 * @param action_date 统计时间 yyyy-MM-dd
	 */
	public void exeDayJobByDay(String action_date);
	
	/**
	 * 移动日志到LOG文件
	 * @param action_date 统计时间 yyyy-MM-dd
	 */
	public void moveDayToLog(String action_date);
	
	
}
