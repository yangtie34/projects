package cn.gilight.product.net.service.impl;


import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.product.card.service.impl.JobCardServiceImpl;
import cn.gilight.product.net.dao.JobNetDao;
import cn.gilight.product.net.service.JobNetService;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
@Service("jobNetService")
public class JobNetServiceImpl implements JobNetService {
	@Autowired
	private JobNetDao jobNetDao;
	
	private String months[]={"01","02","03","04","05","06","07","08","09","10","11","12"};
	private int startYear=2010;
	private int nowYear=Integer.parseInt(DateUtils.getNowYear());
	private String nowYearMonth = DateUtils.YM.format(DateUtils.getLastMonth(new Date()));
	
	private static final Logger logger = Logger.getLogger(JobCardServiceImpl.class);
	@Override
	public JobResultBean initNetStuMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化学生上网信息开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobNetDao.updateNetStuMonth(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中上网信息月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getMessage());
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	public JobResultBean initNetTypeMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化学生上网信息开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobNetDao.updateNetTypeMonth(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中学生上网信息月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getMessage());
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	public JobResultBean updateNetStuMonth() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新学生上网信息开始：");
		try {
			Map<String, Integer> map = jobNetDao.updateNetStuMonth(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}

	@Override
	public JobResultBean updateNetTypeMonth() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新学生上网信息开始：");
		try {
			Map<String, Integer> map = jobNetDao.updateNetTypeMonth(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}

	@Override
	public JobResultBean initStuNumMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobNetDao.updateStuNumMonth(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中学生人数月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getMessage());
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	public JobResultBean updateStuNumMonth() {
		JobResultBean jrb=new JobResultBean();
		try {
			Map<String, Integer> map = jobNetDao.updateStuNumMonth(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}

	@Override
	public JobResultBean updateNetTrend() {
		JobResultBean jrb=new JobResultBean();
		try {
			Map<String, Integer> map = jobNetDao.updateNetTrend();
			jrb.setIsTrue(true);
			jrb.setMsg("同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}
	@Override
	public JobResultBean initNetTeaWarnMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化教师网络账号预警信息开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobNetDao.updateNetTeaWarn(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中教师网络账号预警信息月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getMessage());
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	public JobResultBean updateNetTeaWarnMonth() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新教师网络账号预警信息开始：");
		try {
			Map<String, Integer> map = jobNetDao.updateNetTeaWarn(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}

	@Override
	public JobResultBean initNetTeaMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化教师上网信息开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobNetDao.updateNetTeaMonth(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中上网信息月报,共计"+allNum+"条数据");
					System.out.println(jrb.getMsg());
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getMessage());
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	public JobResultBean updateNetTeaMonth() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新教师上网信息开始：");
		try {
			Map<String, Integer> map = jobNetDao.updateNetTeaMonth(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}
}
