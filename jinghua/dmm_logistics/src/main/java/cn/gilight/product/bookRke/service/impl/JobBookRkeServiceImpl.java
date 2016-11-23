package cn.gilight.product.bookRke.service.impl;


import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.product.bookRke.dao.JobBookRkeDao;
import cn.gilight.product.bookRke.service.JobBookRkeService;
import cn.gilight.product.card.service.impl.JobCardServiceImpl;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
@Service("jobBookRkeService")
public class JobBookRkeServiceImpl implements JobBookRkeService {
	@Autowired
	private JobBookRkeDao jobBookRkeDao;
	
	private String months[]={"01","02","03","04","05","06","07","08","09","10","11","12"};
	private int startYear=2010;
	private int nowYear=Integer.parseInt(DateUtils.getNowYear());
	private String nowYearMonth = DateUtils.YM.format(new Date());
	
	private static final Logger logger = Logger.getLogger(JobCardServiceImpl.class);
	@Override
	public JobResultBean initBookRkeStuMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化图书馆门禁信息开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobBookRkeDao.updateBookRkeStuMonth(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					System.out.println(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中图书馆门禁信息月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	public JobResultBean initStuRkeBookMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化学生出入图书馆门禁信息开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobBookRkeDao.updateStuRkeBookMonth(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					System.out.println(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中学生出入图书馆门禁信息月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	public JobResultBean updateBookRkeStuMonth() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新图书馆门禁信息开始：");
		try {
			Map<String, Integer> map = jobBookRkeDao.updateBookRkeStuMonth(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
		}
		return jrb;
	}

	@Override
	public JobResultBean updateStuRkeBookMonth() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新学生出入图书馆门禁信息开始：");
		try {
			Map<String, Integer> map = jobBookRkeDao.updateStuRkeBookMonth(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
		}
		return jrb;
	}
}
