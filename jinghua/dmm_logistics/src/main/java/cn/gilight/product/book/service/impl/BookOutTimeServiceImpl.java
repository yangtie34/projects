package cn.gilight.product.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookOutTimeDao;
import cn.gilight.product.book.dao.BookOutTimePageDao;
import cn.gilight.product.book.service.BookOutTimeService;

/**
 * 图书借阅超时统计分析
 *
 */
@Service("bookOutTimeService")
public class BookOutTimeServiceImpl implements BookOutTimeService {

	@Autowired
	private BookOutTimeDao bookOutTimeDao;
	
	@Autowired
	private BookOutTimePageDao bookOutTimePageDao;
	
	@Override
	public int getNowOutTimeBook() {
		
		return bookOutTimeDao.getNowOutTimeBook();
	}

	@Override
	public Map<String,Object> getOutTimeBookCount(String startDate, String endDate) {
		
		return bookOutTimeDao.getOutTimeBookCount(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleByPeopleType(
			String startDate, String endDate) {
		
		return bookOutTimeDao.getOutTimePeopleByPeopleType(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleTrend() {
		
		return bookOutTimeDao.getOutTimePeopleTrend();
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateByPeopleType(
			String startDate, String endDate) {
		
		return bookOutTimeDao.getOutTimeRateByPeopleType(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateTrend() {
		
		return bookOutTimeDao.getOutTimeRateTrend();
	}

	@Override
	public List<Map<String, Object>> getOutTimeByType(String startDate,
			String endDate) {
		
		return bookOutTimeDao.getOutTimeByType(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getOutTimeByDept(String startDate,
			String endDate) {
		
		return bookOutTimeDao.getOutTimeByDept(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getoutTimeTrend() {
		
		return bookOutTimeDao.getoutTimeTrend();
	}

	@Override
	public Page getNowOutTime(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc) {
		
		return bookOutTimePageDao.getNowOutTime(currentPage, numPerPage,totalRow,sort,isAsc);
	}

	@Override
	public Page getOutTime(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate) {
		
		return bookOutTimePageDao.getOutTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getOutTimeCountByPeople(String startDate,
			String endDate) {
		
		return bookOutTimePageDao.getOutTimeCountByPeople(startDate, endDate);
	}

	@Override
	public Page getOutTimeByPeople(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String people) {
		
		return bookOutTimePageDao.getOutTimeByPeople(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, people);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateCountByPeople(
			String startDate, String endDate) {
		
		return bookOutTimePageDao.getOutTimeRateCountByPeople(startDate, endDate);
	}

	@Override
	public Page getOutTimeByStore(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String store) {
		
		return bookOutTimePageDao.getOutTimeByStore(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, store);
	}

	@Override
	public Page getOutTimeByDeptTeach(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String deptTeach) {
		
		return bookOutTimePageDao.getOutTimeByDeptTeach(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, deptTeach);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String time, String people) {
		
		return bookOutTimePageDao.getBorrowByTimePeo(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, time, people);
	}

	@Override
	public Page getOutTimeBySchoolYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String schoolYeasr) {
		
		return bookOutTimePageDao.getOutTimeBySchoolYear(currentPage,numPerPage,totalRow,sort,isAsc, schoolYeasr);
	}
	
}
