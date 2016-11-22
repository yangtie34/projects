package com.jhnu.product.wechat.parent.day.dao;

import java.util.List;

import com.jhnu.product.wechat.parent.day.entity.WechatDay;

public interface WechatDayDao {
	
	public void saveWechatDays(List<WechatDay> days);
	
	public List<WechatDay> getWechatDaysByCard(String lastTime,String exeTime);
	
	public List<WechatDay> getWechatDaysByCourse(String year,String term,int zc,int weak,String action_date);
	
	public List<WechatDay> getWechatDaysByWarn();
	
	public List<WechatDay> getWechatDaysByDormRKE(String lastTime,String exeTime);
	
	public List<WechatDay> getWechatDaysByBookRKE(String lastTime,String exeTime);
	
	public List<WechatDay> getWechatDaysByLeave(String lastTime,String exeTime);
	
	public List<WechatDay> getWechatDaysByCancelLeave(String lastTime,String exeTime);
	
	public List<WechatDay> getWechatDaysByThis(WechatDay day);
	
	public String getLaseExeTimeByFrom(String action_from);
	
	public void moveDayToLog(String action_date);
	
}
