package cn.gilight.product.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.product.book.dao.BookBorrowStuDao;
import cn.gilight.product.book.dao.BookBorrowStuPageDao;
import cn.gilight.product.book.service.BookBorrowStuService;

/**
 * 图书借阅统计分析
 *
 */
@Service("bookBorrowStuService")
public class BookBorrowStuServiceImpl implements BookBorrowStuService {
	@Autowired
	private BookBorrowStuDao bookBorrowStuDao;
	
	@Autowired
	private BookBorrowStuPageDao bookBorrowStuPageDao;
	
	@Override
	public Map<String, Object> getBorrowCount(String startDate, String endDate,Map<String,String> deptTeachs) {
		return bookBorrowStuDao.getBorrowNum(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowByPeopleType(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		return bookBorrowStuDao.getPeopleAvgBorrowByPeopleType(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowTrend(Map<String,String> deptTeachs) {
		return bookBorrowStuDao.getPeopleAvgBorrowTrend(deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getReaderRateByPeopleType(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		
		return bookBorrowStuDao.getReaderRateByPeopleType(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getReaderRateTrend(Map<String,String> deptTeachs) {
		return bookBorrowStuDao.getReaderRateTrend(deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getBorrowByType(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		return bookBorrowStuDao.getBorrowByType(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getStuBorrowByDept(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		return bookBorrowStuDao.getStuBorrowByDept(startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getBorrowByTime(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		return bookBorrowStuDao.getBorrowByTime(startDate,endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getBorrowTrendByMonth(Map<String,String> deptTeachs) {
		
		return bookBorrowStuDao. getBorrowTrendByMonth(deptTeachs);
	}

	@Override
	public Page getBorrow(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate,Map<String,String> deptTeachs) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.BOOK_READSTU_JYXZ.getCode())){
			return new Page(false);
		}
		return bookBorrowStuPageDao.getBorrow(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getBorrowCountByPeople(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		return bookBorrowStuPageDao.getBorrowCountByPeople(startDate, endDate,deptTeachs);
	}

	@Override
	public Page getBorrowByPeople(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String people,Map<String,String> deptTeachs) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.BOOK_READSTU_JYXZ.getCode())){
			return new Page(false);
		}
		return bookBorrowStuPageDao.getBorrowByPeople(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, people,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getBorrowPeosCountByPeople(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		
		return bookBorrowStuPageDao.getBorrowPeosCountByPeople(startDate, endDate,deptTeachs);
	}

	@Override
	public Page getBorrowByStore(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String store,Map<String,String> deptTeachs) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.BOOK_READSTU_JYXZ.getCode())){
			return new Page(false);
		}
		return bookBorrowStuPageDao.getBorrowByStore(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, store,deptTeachs);
	}

	@Override
	public Page getBorrowByDeptTeach(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String deptTeach,Map<String,String> deptTeachs) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.BOOK_READSTU_JYXZ.getCode())){
			return new Page(false);
		}
		return bookBorrowStuPageDao.getBorrowByDeptTeach(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, deptTeach,deptTeachs);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String time, String people,Map<String,String> deptTeachs) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.BOOK_READSTU_JYXZ.getCode())){
			return new Page(false);
		}
		return bookBorrowStuPageDao.getBorrowByTimePeo(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, time, people,deptTeachs);
	}

	@Override
	public Page getBorrowBySchoolYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String schoolYear,Map<String,String> deptTeachs) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.BOOK_READSTU_JYXZ.getCode())){
			return new Page(false);
		}
		return bookBorrowStuPageDao.getBorrowBySchoolYear(currentPage,numPerPage,totalRow,sort,isAsc, schoolYear,deptTeachs);
	}

	@Override
	public Page getBorrowCountByMonth(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String month,Map<String,String> deptTeachs) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.BOOK_READSTU_JYXZ.getCode())){
			return new Page(false);
		}
		return bookBorrowStuPageDao.getBorrowCountByMonth(currentPage,numPerPage,totalRow,sort,isAsc, month,deptTeachs);
	}

	@Override
	public List<Map<String, Object>> getBorrowTrend(
			Map<String, String> deptTeachs) {
		return bookBorrowStuDao.getBorrowTrend(deptTeachs);
	}
	
}
