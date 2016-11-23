package cn.gilight.product.card.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.product.card.dao.JobCardDao;
import cn.gilight.product.card.service.JobCardService;

@Service("jobCardService")
public class JobCardServiceImpl implements JobCardService{
	
	@Autowired
	private JobCardDao jobCardDao;
	
	private String months[]={"01","02","03","04","05","06","07","08","09","10","11","12"};
	private int startYear=2010;
	private int nowYear=Integer.parseInt(DateUtils.getNowYear());
	private String nowYearMonth = DateUtils.YM.format(new Date());
	
	private static final Logger logger = Logger.getLogger(JobCardServiceImpl.class);
	
	@Override
	public JobResultBean initCardUsed() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化一卡通使用情况开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobCardDao.updateCardUsed(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
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
	public JobResultBean initRecharge() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化充值结果表开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobCardDao.updateRecharge(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
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
	public JobResultBean initCardPeople() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化一卡通持卡人月度数量开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobCardDao.updateCardPeople(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
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
	public JobResultBean initStuPay() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化学生月度消费表开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobCardDao.updateStuPay(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
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
	public JobResultBean initStuEat() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化学生用餐情况月度统计开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobCardDao.updateStuEat(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
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
	public JobResultBean initStuPayDetil() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		logger.debug("初始化学生消费明细开始：");
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobCardDao.updateStuPayDetil(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					logger.debug(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
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
	public JobResultBean updateCardUsed() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新一卡通使用情况开始：");
		try {
			Map<String, Integer> map = jobCardDao.updateCardUsed(nowYearMonth);
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
	public JobResultBean updateRecharge() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新充值结果表开始：");
		try {
			Map<String, Integer> map = jobCardDao.updateRecharge(nowYearMonth);
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
	public JobResultBean updateCardPeople() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新一卡通持卡人月度数量开始：");
		try {
			Map<String, Integer> map = jobCardDao.updateCardPeople(nowYearMonth);
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
	public JobResultBean updateStuPay() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新学生月度消费表开始：");
		try {
			Map<String, Integer> map = jobCardDao.updateStuPay(nowYearMonth);
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
	public JobResultBean updateStuEat() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新学生用餐情况月度统计开始：");
		try {
			Map<String, Integer> map = jobCardDao.updateStuEat(nowYearMonth);
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
	public JobResultBean updateStuPayDetil() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新学生消费明细开始：");
		try {
			Map<String, Integer> map = jobCardDao.updateStuPayDetil(nowYearMonth);
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
	public JobResultBean updateCardTrend() {
		JobResultBean jrb=new JobResultBean();
		logger.debug("更新一卡通趋势开始：");
		try {
			Map<String, Integer> map = jobCardDao.updateCardTrend();
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
	public JobResultBean initCardHot() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					for(int k=1;k<32;k++){
						String day=k+"";
						if(k<10)day="0"+day;
					//	String thisDay=yearMonth+"-"+day;
						//map = jobCardDao.updateCardHot(yearMonth);
					}
					map=null;
					allNum+=MapUtils.getIntValue(map, "addNum");
					System.out.println(yearMonth+"结束：当前"+allNum);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
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
	public JobResultBean updateCardHot() {
	//	String nowDate=DateUtils.getNowDate();
		JobResultBean jrb=new JobResultBean();
		try {
			Map<String, Integer> map = null;//jobCardDao.updateCardHot(nowDate);
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
