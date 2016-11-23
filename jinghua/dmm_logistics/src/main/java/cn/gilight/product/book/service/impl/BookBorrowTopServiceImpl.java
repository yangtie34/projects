package cn.gilight.product.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.product.book.dao.BookBorrowBookTopDao;
import cn.gilight.product.book.dao.BookBorrowBookTopPageDao;
import cn.gilight.product.book.dao.BookBorrowStuTopDao;
import cn.gilight.product.book.dao.BookBorrowStuTopPageDao;
import cn.gilight.product.book.dao.BookBorrowTeaTopDao;
import cn.gilight.product.book.dao.BookBorrowTeaTopPageDao;
import cn.gilight.product.book.service.BookBorrowTopService;

/**
 * 图书借阅排名统计分析
 *
 */
@Service("bookBorrowTopService")
public class BookBorrowTopServiceImpl implements BookBorrowTopService{
	@Autowired
	private BookBorrowBookTopDao bookBorrowBookTopDao;
	@Autowired
	private BookBorrowStuTopDao bookBorrowStuTopDao;
	@Autowired
	private BookBorrowTeaTopDao bookBorrowTeaTopDao;
	
	@Autowired
	private BookBorrowBookTopPageDao bookBorrowBookTopPageDao;
	@Autowired
	private BookBorrowStuTopPageDao bookBorrowStuTopPageDao;
	@Autowired
	private BookBorrowTeaTopPageDao bookBorrowTeaTopPageDao;

	@Override
	public Page getBorrowTop(int currentPage, int numPerPage,int totalRow, String type,Map<String,String> ofId, String startDate, String endDate, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopByStu(currentPage,numPerPage,totalRow, ofId, startDate, endDate, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopByTea(currentPage,numPerPage,totalRow, ofId, startDate, endDate, rank);
		}else if("book".equals(type)){
			return bookBorrowBookTopDao.getBorrowTopByBook(currentPage,numPerPage,totalRow, ofId.get("store"), startDate, endDate, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByPeopleType(String type,
			Map<String,String> ofId, String startDate, String endDate, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopByPeopleTypeStu(ofId, startDate, endDate, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopByPeopleTypeTea(ofId, startDate, endDate, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByPeopleTypeTrend(String type,
			Map<String,String> ofId, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopByPeopleTypeStuTrend(ofId, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopByPeopleTypeTeaTrend(ofId, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopBySex(String type,
			Map<String,String> ofId, String startDate, String endDate, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopBySexStu(ofId, startDate, endDate, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopBySexTea(ofId, startDate, endDate, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopBySTrend(String type,
			Map<String,String> ofId, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopBySexStuTrend(ofId, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopBySexTeaTrend(ofId, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByGrade(String type,
			Map<String,String> ofId, String startDate, String endDate, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopByStuGrade(ofId, startDate, endDate, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopByTeaZC(ofId, startDate, endDate, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByGradeTrend(String type,
			Map<String,String> ofId, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopByStuGradeTrend(ofId, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopByTeaZCTrend(ofId, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByOf(String type,
			Map<String,String> ofId, String startDate, String endDate, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopByDeptTeach(ofId, startDate, endDate, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopByDeptTeach(ofId, startDate, endDate, rank);
		}else if("book".equals(type)){
			return bookBorrowBookTopDao.getBorrowTopByStore(ofId.get("store"), startDate, endDate, rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByOfTrend(String type,
			Map<String,String> ofId, int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopDao.getBorrowTopByDeptTeachTrend(ofId, rank);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopDao.getBorrowTopByDeptTeachTrend(ofId, rank);
		}else if("book".equals(type)){
			return bookBorrowBookTopDao.getBorrowTopByStoreTrend(ofId.get("store"),rank);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByYear(String type,
			String schoolYear) {
		return bookBorrowStuTopDao.getBorrowTopByYearStu(schoolYear);
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByQuarter(String type,
			String schoolYear) {
		List<Map<String, Object>> list=bookBorrowStuTopDao.getBorrowTopByQuarterStu(schoolYear);
		for(Map<String, Object> map:list){
			switch (MapUtils.getIntValue(map, "Quarter")) {
			case 1:
					map.put("QUARTERNAME", "秋季");
				break;
			case 2:
					map.put("QUARTERNAME", "冬季");
				break;
			case 3:
					map.put("QUARTERNAME", "春季");
				break;
			case 4:
					map.put("QUARTERNAME", "夏季");
				break;

			default:
				break;
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByMonth(String type,
			String schoolYear) {
		return bookBorrowStuTopDao.getBorrowTopByMonthStu(schoolYear);
	}

	@Override
	public Page getBorrow(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String startDate, String endDate, String id) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getBorrowByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getBorrowByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
		}else if("book".equals(type)){
			return bookBorrowBookTopPageDao.getBorrowByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
		}
		return null;
	
	}

	@Override
	public Page getTop(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String startDate, String endDate, String id) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
		}else if("book".equals(type)){
			return bookBorrowBookTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
		}
		return null;
	
	}

	@Override
	public Page getEdu(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			Map<String, String> ofId, String startDate, String endDate,
			int rank, String value) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getEdu(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getEdu(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
		}
		return null;
	}

	@Override
	public Page getSex(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			Map<String, String> ofId, String startDate, String endDate,
			int rank, String value) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getSex(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getSex(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
		}
		return null;
	}

	@Override
	public Page getGrade(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			Map<String, String> ofId, String startDate, String endDate,
			int rank, String value) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getGrade(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getZc(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
		}
		return null;
	}

	@Override
	public Page getOf(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			Map<String, String> ofId, Map<String, String> value, String startDate, String endDate,
			int rank) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getDept(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId,value);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getDept(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId,value);
		}else if("book".equals(type)){
			return bookBorrowBookTopPageDao.getStore(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId.get("store"),value.get("store"));
		}
		return null;
	}

	@Override
	public Page getAllBorrow(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String id) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getAllBorrow(currentPage,numPerPage,totalRow,sort,isAsc, id);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getAllBorrow(currentPage,numPerPage,totalRow,sort,isAsc, id);
		}else if("book".equals(type)){
			return bookBorrowBookTopPageDao.getAllBorrow(currentPage,numPerPage,totalRow,sort,isAsc, id);
		}
		return null;
	}

	@Override
	public Page getAllTop(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String id) {
		if("stu".equals(type)){
			return bookBorrowStuTopPageDao.getAllTop(currentPage,numPerPage,totalRow,sort,isAsc, id);
		}else if("tea".equals(type)){
			return bookBorrowTeaTopPageDao.getAllTop(currentPage,numPerPage,totalRow,sort,isAsc, id);
		}else if("book".equals(type)){
			return bookBorrowBookTopPageDao.getAllTop(currentPage,numPerPage,totalRow,sort,isAsc, id);
		}
		return null;
	}
}
