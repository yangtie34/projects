package com.jhnu.product.wechat.parent.day.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.enums.ActionEnum;
import com.jhnu.enums.ActionFromEnum;
import com.jhnu.product.common.school.service.SchoolService;
import com.jhnu.product.wechat.parent.day.dao.WechatDayDao;
import com.jhnu.product.wechat.parent.day.entity.WechatDay;
import com.jhnu.product.wechat.parent.day.service.WechatDayService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.product.EduUtils;

@Service("wechatDayService")
public class WechatDayServiceImpl implements WechatDayService{
	@Autowired
	private WechatDayDao wechatDayDao;
	
	@Autowired
	private SchoolService schoolService;
	
	private static final Logger logger = Logger.getLogger(WechatDayServiceImpl.class);
	
	@Override
	@Transactional
	public void saveWechatDays(List<WechatDay> days) {
		wechatDayDao.saveWechatDays(days);
	}

	@Override
	@Transactional
	public void saveWechatDaysByCard(String exeTime) {
		logger.info("开始查询孩子一天上次时间");
		String lastTime=getLaseExeTimeByFrom(ActionFromEnum.XF.getCode());
		logger.info("孩子一天上次时间为："+lastTime);
		logger.info("开始查询微信——孩子一天——一卡通消费："+lastTime+"-"+exeTime);
		List<WechatDay> days=wechatDayDao.getWechatDaysByCard(lastTime, exeTime);
		logger.info("微信——孩子一天——一卡通消费："+lastTime+"-"+exeTime+",消费有："+days.size()+"条记录");
		saveWechatDays(days);
	}

	@Override
	@Transactional
	public void saveWechatDaysByCourse(String action_date) {
		
		try {
			String year_trem[]=EduUtils.getSchoolYearTerm(DateUtils.SDF.parse(action_date));
			String start=schoolService.getStartSchool(year_trem[0],year_trem[1]);
			if(start!=null && !"".equals(start)){
				String lastTime=wechatDayDao.getLaseExeTimeByFrom(ActionFromEnum.KC.getCode());
				if(lastTime==null || "".equals(lastTime) || "null".equals(lastTime) ){
					List<Date> date=DateUtils.getBeforeDates(DateUtils.SDF.parse(action_date),2);
					for (int i = 0; i < date.size(); i++) {
						String dateS=DateUtils.SDF.format(date.get(i));
						int zc = DateUtils.getZcByDateFromBeginDate(DateUtils.SDF.parse(start),DateUtils.SDF.parse(dateS));
						int weak=DateUtils.getWeekNo(dateS);
						saveWechatDays(wechatDayDao.getWechatDaysByCourse(year_trem[0],year_trem[1],zc,weak,dateS));
					}
				}
				int zc = DateUtils.getZcByDateFromBeginDate(DateUtils.SDF.parse(start),DateUtils.SDF.parse(action_date));
				int weak=DateUtils.getWeekNo(action_date);
				saveWechatDays(wechatDayDao.getWechatDaysByCourse(year_trem[0],year_trem[1],zc,weak,action_date));
			}else{
				logger.error("没有设置学年学期的开学日期");
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void saveWechatDaysByWarn(String exeTime) {
		saveWechatDays(wechatDayDao.getWechatDaysByWarn());
	}

	@Override
	@Transactional
	public void saveWechatDaysByDormRKE(String exeTime) {
		saveWechatDays(wechatDayDao.getWechatDaysByDormRKE(getLaseExeTimeByFrom(ActionFromEnum.SSMJ.getCode()), exeTime));
	}

	@Override
	@Transactional
	public void saveWechatDaysByBookRKE(String exeTime) {
		saveWechatDays(wechatDayDao.getWechatDaysByBookRKE(getLaseExeTimeByFrom(ActionFromEnum.TSMJ.getCode()), exeTime));
	}
	
	@Override
	@Transactional
	public void saveWechatDaysByLeave(String exeTime) {
		saveWechatDays(wechatDayDao.getWechatDaysByLeave(getLaseExeTimeByFrom(ActionFromEnum.QJ.getCode()), exeTime));
		
	}

	@Override
	@Transactional
	public void saveWechatDaysByCancelLeave(String exeTime) {
		saveWechatDays(wechatDayDao.getWechatDaysByCancelLeave(getLaseExeTimeByFrom(ActionEnum.QJ.getCode()), exeTime));
	}

	@Override
	public List<WechatDay> getWechatDaysByThis(WechatDay day) {
		return wechatDayDao.getWechatDaysByThis(day);
	}

	@Override
	public String getLaseExeTimeByFrom(String action_code) {
		String lastTime=wechatDayDao.getLaseExeTimeByFrom(action_code);
		if(lastTime==null || "".equals(lastTime) || "null".equals(lastTime) ){
			return DateUtils.getYesterday();
		}
		return lastTime;
	}

	@Override
	public void exeDayJobByCardTime(String exeTime) {
		saveWechatDaysByCard(exeTime);
	}
	
	@Override
	@Transactional
	public void exeDayJobByBookRkeTime(String exeTime) {
		saveWechatDaysByBookRKE(exeTime);
	}
	
	@Override
	@Transactional
	public void exeDayJobByDormRKETime(String exeTime) {
		saveWechatDaysByDormRKE(exeTime);
	}
	
	@Override
	@Transactional
	public void exeDayJobByDay(String action_date) {
		saveWechatDaysByCourse(action_date);
	}

	@Override
	@Transactional
	public void moveDayToLog(String action_date) {
		wechatDayDao.moveDayToLog(action_date);
	}
	
}
