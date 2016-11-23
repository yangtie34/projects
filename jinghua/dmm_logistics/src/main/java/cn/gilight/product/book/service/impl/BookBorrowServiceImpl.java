package cn.gilight.product.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowDao;
import cn.gilight.product.book.dao.BookBorrowPageDao;
import cn.gilight.product.book.service.BookBorrowService;

/**
 * 图书借阅统计分析
 *
 */
@Service("bookBorrowService")
public class BookBorrowServiceImpl implements BookBorrowService {
	@Autowired
	private BookBorrowDao bookBorrowDao;
	
	@Autowired
	private BookBorrowPageDao bookBorrowPageDao;
	
	@Override
	public Map<String, Object> getBorrowCount(String startDate, String endDate) {
		return bookBorrowDao.getBorrowNum(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowByPeopleType(
			String startDate, String endDate) {
		return bookBorrowDao.getPeopleAvgBorrowByPeopleType(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowTrend() {
		return bookBorrowDao.getPeopleAvgBorrowTrend();
	}

	@Override
	public List<Map<String, Object>> getReaderRateByPeopleType(
			String startDate, String endDate) {
		
		return bookBorrowDao.getReaderRateByPeopleType(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getReaderRateTrend() {
		return bookBorrowDao.getReaderRateTrend();
	}

	@Override
	public List<Map<String, Object>> getBorrowByType(String startDate,
			String endDate) {
		return bookBorrowDao.getBorrowByType(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getStuBorrowByDept(String startDate,
			String endDate) {
		return bookBorrowDao.getStuBorrowByDept(startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getBorrowByTime(String startDate,
			String endDate) {
		return bookBorrowDao.getBorrowByTime(startDate,endDate);
	}

	@Override
	public List<Map<String, Object>> getBorrowTrendByYear() {
		
		return bookBorrowDao.getBorrowTrendByYear();
	}

	@Override
	public List<Map<String, Object>> getBorrowTrendByMonth() {
		
		return bookBorrowDao. getBorrowTrendByMonth();
	}

	@Override
	public Page getBorrow(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate) {
		return bookBorrowPageDao.getBorrow(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getBorrowCountByPeople(String startDate,
			String endDate) {
		return bookBorrowPageDao.getBorrowCountByPeople(startDate, endDate);
	}

	@Override
	public Page getBorrowByPeople(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String people) {
		
		return bookBorrowPageDao.getBorrowByPeople(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, people);
	}

	@Override
	public List<Map<String, Object>> getBorrowPeosCountByPeople(
			String startDate, String endDate) {
		
		return bookBorrowPageDao.getBorrowPeosCountByPeople(startDate, endDate);
	}

	@Override
	public Page getBorrowByStore(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String store) {
		
		return bookBorrowPageDao.getBorrowByStore(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, store);
	}

	@Override
	public Page getBorrowByDeptTeach(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String deptTeach) {
		
		return bookBorrowPageDao.getBorrowByDeptTeach(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, deptTeach);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String time, String people) {
		
		return bookBorrowPageDao.getBorrowByTimePeo(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, time, people);
	}

	@Override
	public Page getBorrowBySchoolYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String schoolYear) {
		
		return bookBorrowPageDao.getBorrowBySchoolYear(currentPage,numPerPage,totalRow,sort,isAsc, schoolYear);
	}

	@Override
	public Page getBorrowCountByMonth(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String month) {
		
		return bookBorrowPageDao.getBorrowCountByMonth(currentPage,numPerPage,totalRow,sort,isAsc, month);
	}
	
}
