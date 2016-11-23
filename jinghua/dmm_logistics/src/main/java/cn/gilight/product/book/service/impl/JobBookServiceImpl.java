package cn.gilight.product.book.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.enums.QuarterEnum;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.book.dao.JobBookDao;
import cn.gilight.product.book.service.JobBookService;

@Service("jobBookService")
public class JobBookServiceImpl implements JobBookService{

	@Autowired
	private JobBookDao jobBookDao;
	
	private String months[]={"01","02","03","04","05","06","07","08","09","10","11","12"};
	private int startYear=2000;
	private int nowYear=Integer.parseInt(DateUtils.getNowYear())+1;
	private String nowYearMonth = DateUtils.YM.format(new Date());
	
	@Override
	public JobResultBean initBookYear() {
		JobResultBean jrb=new JobResultBean();
		try{
			for (int i = startYear; i < nowYear; i++) {
				String schoolYear=i+"-"+(i+1);
				jobBookDao.updateBookYear(schoolYear);
				
			}
			jrb.setIsTrue(true);
			jrb.setMsg("执行成功");
			jobBookDao.updateBookStateYear(nowYear+"-"+(nowYear+1));
		}catch(Exception e){
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	public JobResultBean bookYearByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		try {
			jobBookDao.updateBookYear(schoolYear);
			jobBookDao.updateBookStateYear(schoolYear);
			jrb.setIsTrue(true);
			jrb.setMsg("执行成功");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookTypeYear() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			try {
				jobBookDao.updateBookTypeYear(schoolYear);
				jrb.setIsTrue(true);
				jrb.setMsg("执行成功");
			} catch (Exception e) {
				jrb.setIsTrue(false);
				jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return jrb;
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookBorrowTypeMonth() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				try {
					jobBookDao.updateBookBorrowTypeMonth(schoolYear, months[j]);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中图书借阅月报");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookBorrowPeopleMonth() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				try {
					jobBookDao.updateBookBorrowPeopleMonth(schoolYear, months[j]);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中人员借阅月报");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookOutTimeTypeMonth() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				try {
					jobBookDao.updateBookOutTimeTypeMonth(schoolYear, months[j]);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中图书类型超时借阅期报");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookOutTimePeopleMonth() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				try {
					jobBookDao.updateBookOutTimePeopleMonth(schoolYear, months[j]);
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中人员超时借阅期报");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookTypeYearByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		try {
			jobBookDao.updateBookTypeYear(schoolYear);
			jrb.setIsTrue(true);
			jrb.setMsg("执行成功");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookBorrowTypeMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		try {
			jobBookDao.updateBookBorrowTypeMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步图书分类型借阅完成");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookBorrowPeopleMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		try {
			jobBookDao.updateBookBorrowPeopleMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步人员借阅完成");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookOutTimeTypeMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		try {
			jobBookDao.updateBookOutTimeTypeMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步图书分类型逾期借阅完成");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookOutTimePeopleMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		try {
			jobBookDao.updateBookOutTimePeopleMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步人员逾期借阅完成");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookReaderYear() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			try {
				jobBookDao.updateBookReaderYear(schoolYear);
				jrb.setIsTrue(true);
				jrb.setMsg("执行成功");
			} catch (Exception e) {
				jrb.setIsTrue(false);
				jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return jrb;
			}
		}
		return jrb;
	}
	
	@Override
	@Transactional
	public JobResultBean initBookReaderDetilYear() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			try {
				jobBookDao.updateBookReaderDetilYear(schoolYear);
				jrb.setIsTrue(true);
				jrb.setMsg("执行成功");
			} catch (Exception e) {
				jrb.setIsTrue(false);
				jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return jrb;
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookReaderYearByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		try {
			jobBookDao.updateBookReaderYear(schoolYear);
			jrb.setIsTrue(true);
			jrb.setMsg("执行成功");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		return jrb;
	}
	
	@Override
	@Transactional
	public JobResultBean bookReaderDetilYearByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		try {
			jobBookDao.updateBookReaderDetilYear(schoolYear);
			jrb.setIsTrue(true);
			jrb.setMsg("执行成功");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}
	
	@Override
	@Transactional
	public JobResultBean initBookBorrowStuMonth(){
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookBorrowStuMonth(schoolYear, months[j]);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生借阅月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookBorrowStuMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBookBorrowStuMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步学生借阅人次："+MapUtils.getIntValue(map, "addNum")+"人"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"人）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}
	
	@Override
	@Transactional
	public JobResultBean initBookBorrowTeaMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookBorrowTeaMonth(schoolYear, months[j]);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中教师借阅月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookBorrowTeaMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBookBorrowTeaMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步教师借阅人次："+MapUtils.getIntValue(map, "addNum")+"人"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"人）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}
	
	@Override
	@Transactional
	public JobResultBean initBookBorrowBookMonth(){
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookBorrowBookMonth(schoolYear, months[j]);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中图书借阅月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
				
			}
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookBorrowBookMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBookBorrowBookMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步图书借阅人次："+MapUtils.getIntValue(map, "addNum")+"人"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"人）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}
	
	@Override
	@Transactional
	public JobResultBean initBookOutTimeStuMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookOutTimeStuMonth(schoolYear, months[j]);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中学生逾期还书月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookOutTimeTeaMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookOutTimeTeaMonth(schoolYear, months[j]);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中教师逾期还书月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
				
			}
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBookOutTimeBookMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			for (int j = 0; j < months.length; j++) {
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookOutTimeBookMonth(schoolYear, months[j]);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+nowYear+"-12中书籍逾期归还月报,共计"+allNum+"条数据");
				} catch (Exception e) {
					jrb.setIsTrue(false);
					jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return jrb;
				}
			}
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookOutTimeStuMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBookOutTimeStuMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步学生逾期还书人次："+MapUtils.getIntValue(map, "addNum")+"人"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"人）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookOutTimeTeaMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBookOutTimeTeaMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步教师逾期还书人次："+MapUtils.getIntValue(map, "addNum")+"人"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"人）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean bookOutTimeBookMonthByDay() {
		JobResultBean jrb=new JobResultBean();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
		String month = DateUtils.MONTH.format(new Date());
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBookOutTimeBookMonth(schoolYear, month);
			jrb.setIsTrue(true);
			jrb.setMsg(schoolYear+"-"+month+"同步书籍逾期归还本数："+MapUtils.getIntValue(map, "addNum")+"本"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"本）");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBorrowTop() {
		JobResultBean jrb=new JobResultBean();
		for (int i = startYear; i < nowYear; i++) {
			String schoolYear=i+"-"+(i+1);
			try {
				jobBookDao.updateBorrowTopYear(schoolYear);
				jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.qj);
				jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.dj);
				jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.cj);
				jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.xj);
				for (int j = 0; j < months.length; j++) {
					jobBookDao.updateBorrowTopMonth(schoolYear,months[j]);
				}
				jrb.setIsTrue(true);
				jrb.setMsg("执行成功");
			} catch (Exception e) {
				jrb.setIsTrue(false);
				jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return jrb;
			}
			
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean borrowTopByYear() {
		JobResultBean jrb=new JobResultBean();
		String months[]={"09","10","11","12","01","02","03","04","05","06","07","08"};
		String[] temp = EduUtils.getSchoolYearTerm(new Date())[0].split("-");
		int from = Integer.parseInt(temp[0])-1,to = Integer.parseInt(temp[1])-1;
		String schoolYear=from+"-"+to;
		
		try {
			jobBookDao.updateBorrowTopYear(schoolYear);
			jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.qj);
			jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.dj);
			jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.cj);
			jobBookDao.updateBorrowTopQuarter(schoolYear, QuarterEnum.xj);
			for (int j = 0; j < months.length; j++) {
				jobBookDao.updateBorrowTopMonth(schoolYear,months[j]);
			}
			jrb.setIsTrue(true);
			jrb.setMsg("执行成功");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean initBorrowDetil() {
		JobResultBean jrb=new JobResultBean();
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBorrowDetil(null);
			jrb.setIsTrue(true);
			jrb.setMsg("初始化：借书详细记录共增加"+map.get("addNum")+"条数据");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean borrowDetilByThisYear() {
		JobResultBean jrb=new JobResultBean();
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBorrowDetil(DateUtils.YEAR.format(new Date()));
			jrb.setIsTrue(true);
			jrb.setMsg("本年借书详细记录共增加"+map.get("addNum")+"条数据");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean borrowDetilByTwoMonth() {
		JobResultBean jrb=new JobResultBean();
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBorrowDetil("TwoMonth");
			jrb.setIsTrue(true);
			jrb.setMsg("近两月借书详细记录共增加"+map.get("addNum")+"条数据");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean borrowByLastDayByDay() {
		JobResultBean jrb=new JobResultBean();
		Map<String, Integer> map;
		try {
			map = jobBookDao.updateBorrowDetil(DateUtils.getYesterday());
			jrb.setIsTrue(true);
			jrb.setMsg("昨日借书详细记录共增加"+map.get("addNum")+"条数据");
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return jrb;
		}
		return jrb;
	}

	@Override
	public JobResultBean initBookStu() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookStu(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中学生读者月度人数,共计"+allNum+"条数据");
					if(yearMonth.equals(nowYearMonth)){
						jobBookDao.createTemp();
						return jrb;
					}
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
	public JobResultBean initBookMonth() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBookMonth(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中图书数量统计,共计"+allNum+"条数据");
					if(yearMonth.equals(nowYearMonth)){
						return jrb;
					}
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
	public JobResultBean initBorrowStu() {
		JobResultBean jrb=new JobResultBean();
		int allNum=0;
		for (int i = startYear; i <= nowYear; i++) {
			for (int j = 0; j < months.length; j++) {
				String yearMonth=i+"-"+months[j];
				Map<String, Integer> map;
				try {
					map = jobBookDao.updateBorrowStu(yearMonth);
					allNum+=MapUtils.getIntValue(map, "addNum");
					jrb.setIsTrue(true);
					jrb.setMsg("完成同步"+startYear+"-01至"+yearMonth+"中学生借阅统计,共计"+allNum+"条数据");
					if(yearMonth.equals(nowYearMonth)){
						jobBookDao.createTemp();
						return jrb;
					}
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
	public JobResultBean updateBookStu() {
		JobResultBean jrb=new JobResultBean();
		try {
			Map<String, Integer> map = jobBookDao.updateBookStu(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
			jobBookDao.createTemp();
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
		}
		return jrb;
	}

	@Override
	public JobResultBean updateBookMonth() {
		JobResultBean jrb=new JobResultBean();
		try {
			Map<String, Integer> map = jobBookDao.updateBookMonth(nowYearMonth);
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
	public JobResultBean updateBorrowStu() {
		JobResultBean jrb=new JobResultBean();
		try {
			Map<String, Integer> map = jobBookDao.updateBorrowStu(nowYearMonth);
			jrb.setIsTrue(true);
			jrb.setMsg(nowYearMonth+"同步数据："+MapUtils.getIntValue(map, "addNum")+"条"
					+ "（新增："+(MapUtils.getIntValue(map, "addNum")-MapUtils.getIntValue(map, "delNum"))+"条）");
			jobBookDao.createTemp();
		} catch (Exception e) {
			jrb.setIsTrue(false);
			jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
		}
		return jrb;
	}

}
