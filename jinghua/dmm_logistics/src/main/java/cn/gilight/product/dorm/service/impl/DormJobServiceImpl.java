package cn.gilight.product.dorm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.product.dorm.dao.DormJobDao;
import cn.gilight.product.dorm.service.DormJobService;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

@Service("dormJobService")
public class DormJobServiceImpl implements DormJobService{
	
	@Autowired
	private DormJobDao dormJobDao;

	private String months[]={"01","02","03","04","05","06","07","08","09","10","11","12"};
	private int startYear=2010;
	private int nowYear=Integer.parseInt(DateUtils.getNowYear());
	
	@Override
	public JobResultBean dormStuJob() {
		JobResultBean jrb=new JobResultBean();
		try {
			int i=dormJobDao.dormStuJob();
			jrb.setIsTrue(true);
			jrb.setMsg("初始化学生住宿情况数据："+i+"条。");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}

	@Override
	public JobResultBean initDormRkeUsedStuJob() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				int num;
				try {
					num = dormJobDao.dormRkeUsedStuJob(yearMonth);
					allNum+=num;
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步当前住宿学生的门禁使用情况,共计"+allNum+"条数据");
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
	public JobResultBean dormRkeUsedStuJob() {
		JobResultBean jrb=new JobResultBean();
		try {
			int i=dormJobDao.dormRkeUsedStuJob(DateUtils.YM.format(new Date()));
			jrb.setIsTrue(true);
			jrb.setMsg("完成同步当前住宿学生的门禁使用情况,共计"+i+"条数据");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}

	@Override
	public JobResultBean initDormTrend() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				int num;
				try {
					num = dormJobDao.dormTrend(yearMonth);
					allNum+=num;
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步当前住宿趋势情况,共计"+allNum+"条数据");
					System.out.println("---"+yearMonth+"---");
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
	public JobResultBean dormTrend() {
		JobResultBean jrb=new JobResultBean();
		try {
			int i=dormJobDao.dormTrend(DateUtils.YM.format(new Date()));
			jrb.setIsTrue(true);
			jrb.setMsg("完成同步当前住宿趋势情况,共计"+i+"条数据");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getMessage());
		}
		return jrb;
	}

}
