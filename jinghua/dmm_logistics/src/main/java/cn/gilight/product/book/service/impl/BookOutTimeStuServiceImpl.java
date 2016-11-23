package cn.gilight.product.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookOutTimeStuDao;
import cn.gilight.product.book.dao.BookOutTimeStuPageDao;
import cn.gilight.product.book.service.BookOutTimeStuService;

/**
 * 图书借阅超时统计分析
 *
 */
@Service("bookOutTimeStuService")
public class BookOutTimeStuServiceImpl implements BookOutTimeStuService {

	@Autowired
	private BookOutTimeStuDao bookOutTimeStuDao;
	
	@Autowired
	private BookOutTimeStuPageDao bookOutTimeStuPageDao;
	
	@Override
	public int getNowOutTimeBook(Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getNowOutTimeBook(deptTeachs);
	}

	@Override
	public Map<String,Object> getOutTimeBookCount(String startDate, String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getOutTimeBookCount(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleByPeopleType(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getOutTimePeopleByPeopleType(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleTrend(Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getOutTimePeopleTrend(deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateByPeopleType(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getOutTimeRateByPeopleType(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateTrend(Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getOutTimeRateTrend(deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimeByType(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getOutTimeByType(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimeByDept(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getOutTimeByDept(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getoutTimeTrend(Map<String,String> deptTeachs) {
		
		return bookOutTimeStuDao.getoutTimeTrend(deptTeachs);
	}

	@Override
	public Page getNowOutTime(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getNowOutTime(currentPage, numPerPage,totalRow,sort,isAsc,deptTeachs);
	}

	@Override
	public Page getOutTime(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getOutTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimeCountByPeople(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getOutTimeCountByPeople(startDate, endDate,deptTeachs);
	}

	@Override
	public Page getOutTimeByPeople(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String people,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getOutTimeByPeople(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, people,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateCountByPeople(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getOutTimeRateCountByPeople(startDate, endDate,deptTeachs);
	}

	@Override
	public Page getOutTimeByStore(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String store,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getOutTimeByStore(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, store,deptTeachs);
	}

	@Override
	public Page getOutTimeByDeptTeach(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String deptTeach,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getOutTimeByDeptTeach(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, deptTeach,deptTeachs);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String time, String people,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getBorrowByTimePeo(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, time, people,deptTeachs);
	}

	@Override
	public Page getOutTimeBySchoolYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String schoolYeasr,Map<String,String> deptTeachs) {
		
		return bookOutTimeStuPageDao.getOutTimeBySchoolYear(currentPage,numPerPage,totalRow,sort,isAsc, schoolYeasr,deptTeachs);
	}
	
}
