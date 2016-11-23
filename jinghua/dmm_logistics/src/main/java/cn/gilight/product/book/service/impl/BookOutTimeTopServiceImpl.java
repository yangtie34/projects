package cn.gilight.product.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookOutTimeDayBookTopDao;
import cn.gilight.product.book.dao.BookOutTimeDayBookTopPageDao;
import cn.gilight.product.book.dao.BookOutTimeDayStuTopDao;
import cn.gilight.product.book.dao.BookOutTimeDayStuTopPageDao;
import cn.gilight.product.book.dao.BookOutTimeDayTeaTopDao;
import cn.gilight.product.book.dao.BookOutTimeDayTeaTopPageDao;
import cn.gilight.product.book.dao.BookOutTimeNumBookTopDao;
import cn.gilight.product.book.dao.BookOutTimeNumBookTopPageDao;
import cn.gilight.product.book.dao.BookOutTimeNumStuTopDao;
import cn.gilight.product.book.dao.BookOutTimeNumStuTopPageDao;
import cn.gilight.product.book.dao.BookOutTimeNumTeaTopDao;
import cn.gilight.product.book.dao.BookOutTimeNumTeaTopPageDao;
import cn.gilight.product.book.service.BookOutTimeTopService;

/**
 * 图书逾期排名统计分析
 *
 */
@Service("bookOutTimeTopService")
public class BookOutTimeTopServiceImpl implements BookOutTimeTopService {
	
	@Autowired
	private BookOutTimeDayBookTopDao bookOutTimeDayBookTopDao;
	@Autowired
	private BookOutTimeDayStuTopDao bookOutTimeDayStuTopDao;
	@Autowired
	private BookOutTimeDayTeaTopDao bookOutTimeDayTeaTopDao;
	@Autowired
	private BookOutTimeNumBookTopDao bookOutTimeNumBookTopDao;
	@Autowired
	private BookOutTimeNumStuTopDao bookOutTimeNumStuTopDao;
	@Autowired
	private BookOutTimeNumTeaTopDao bookOutTimeNumTeaTopDao;
	@Autowired
	private BookOutTimeDayBookTopPageDao bookOutTimeDayBookTopPageDao;
	@Autowired
	private BookOutTimeDayStuTopPageDao bookOutTimeDayStuTopPageDao;
	@Autowired
	private BookOutTimeDayTeaTopPageDao bookOutTimeDayTeaTopPageDao;
	@Autowired
	private BookOutTimeNumBookTopPageDao bookOutTimeNumBookTopPageDao;
	@Autowired
	private BookOutTimeNumStuTopPageDao bookOutTimeNumStuTopPageDao;
	@Autowired
	private BookOutTimeNumTeaTopPageDao bookOutTimeNumTeaTopPageDao;
	
	@Override
	public Page getOutTimeTop(int currentPage,
			int numPerPage,int totalRow, String type, String numOrDay,
			Map<String, String> ofId, String startDate, String endDate, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopByStu(currentPage,numPerPage,totalRow, ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopByStu(currentPage,numPerPage,totalRow, ofId, startDate, endDate, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopByTea(currentPage,numPerPage,totalRow, ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopByTea(currentPage,numPerPage,totalRow, ofId, startDate, endDate, rank);
			}
		}else if("book".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumBookTopDao.getOutTimeNumTopByBook(currentPage,numPerPage,totalRow, ofId.get("store"), startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayBookTopDao.getOutTimeDayTopByBook(currentPage,numPerPage,totalRow, ofId.get("store"), startDate, endDate, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopByPeopleType(String type,
			String numOrDay, Map<String, String> ofId, String startDate,
			String endDate, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopByPeopleTypeStu(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopByPeopleTypeStu(ofId, startDate, endDate, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopByPeopleTypeTea(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopByPeopleTypeTea(ofId, startDate, endDate, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopByPeopleTypeTrend(
			String type, String numOrDay, Map<String, String> ofId, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopByPeopleTypeStuTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopByPeopleTypeStuTrend(ofId, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopByPeopleTypeTeaTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopByPeopleTypeTeaTrend(ofId, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopBySex(String type,
			String numOrDay, Map<String, String> ofId, String startDate,
			String endDate, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopBySexStu(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopBySexStu(ofId, startDate, endDate, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopBySexTea(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopBySexTea(ofId, startDate, endDate, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopBySTrend(String type,
			String numOrDay, Map<String, String> ofId, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopBySexStuTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopBySexStuTrend(ofId, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopBySexTeaTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopBySexTeaTrend(ofId, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopByGrade(String type,
			String numOrDay, Map<String, String> ofId, String startDate,
			String endDate, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopByStuGrade(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopByStuGrade(ofId, startDate, endDate, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopByTeaZC(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopByTeaZC(ofId, startDate, endDate, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopByGradeTrend(String type,
			String numOrDay, Map<String, String> ofId, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopByStuGradeTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopByStuGradeTrend(ofId, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopByTeaZCTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopByTeaZCTrend(ofId, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopByOf(String type,
			String numOrDay, Map<String, String> ofId, String startDate,
			String endDate, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopByDeptTeach(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopByDeptTeach(ofId, startDate, endDate, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopByDeptTeach(ofId, startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopByDeptTeach(ofId, startDate, endDate, rank);
			}
		}else if("book".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumBookTopDao.getOutTimeNumTopByStore(ofId.get("store"), startDate, endDate, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayBookTopDao.getOutTimeDayTopByStore(ofId.get("store"), startDate, endDate, rank);
			}
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOutTimeTopByOfTrend(String type,
			String numOrDay, Map<String, String> ofId, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopDao.getOutTimeNumTopByDeptTeachTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopDao.getOutTimeDayTopByDeptTeachTrend(ofId, rank);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopDao.getOutTimeNumTopByDeptTeachTrend(ofId, rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopDao.getOutTimeDayTopByDeptTeachTrend(ofId, rank);
			}
		}else if("book".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumBookTopDao.getOutTimeNumTopByStoreTrend(ofId.get("store"), rank);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayBookTopDao.getOutTimeDayTopByStoreTrend(ofId.get("store"), rank);
			}
		}
		return null;
	}
	@Override 
	public Page getOutTime(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String numOrDay, String startDate, String endDate, String id) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopPageDao.getOutTimeByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopPageDao.getOutTimeByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopPageDao.getOutTimeByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopPageDao.getOutTimeByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}
		}else if("book".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumBookTopPageDao.getOutTimeByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayBookTopPageDao.getOutTimeByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}
		}
		return null;
	}
	@Override
	public Page getTop(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String numOrDay, String startDate, String endDate, String id) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}
		}else if("book".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumBookTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayBookTopPageDao.getTopByTime(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, id);
			}
		}
		return null;
	}
	@Override
	public Page getEdu(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String numOrDay, Map<String, String> ofId, String startDate,
			String endDate, int rank, String value) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopPageDao.getEdu(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopPageDao.getEdu(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopPageDao.getEdu(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopPageDao.getEdu(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}
		}
		return null;
	}
	@Override
	public Page getSex(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String numOrDay, Map<String, String> ofId, String startDate,
			String endDate, int rank, String value) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopPageDao.getSex(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopPageDao.getSex(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopPageDao.getSex(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopPageDao.getSex(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}
		}
		return null;
	}
	@Override
	public Page getGrade(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String numOrDay, Map<String, String> ofId, String startDate,
			String endDate, int rank, String value) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopPageDao.getGrade(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopPageDao.getGrade(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopPageDao.getZc(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopPageDao.getZc(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId, value);
			}
		}
		return null;
	}
	@Override
	public Page getOf(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String type,
			String numOrDay, Map<String, String> ofId, Map<String, String> value, String startDate,
			String endDate, int rank) {
		if("stu".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumStuTopPageDao.getDept(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId,value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayStuTopPageDao.getDept(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId,value);
			}
		}else if("tea".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumTeaTopPageDao.getDept(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId,value);
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayTeaTopPageDao.getDept(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId,value);
			}
		}else if("book".equals(type)){
			if("num".equals(numOrDay)){
				return bookOutTimeNumBookTopPageDao.getStore(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId.get("store"),value.get("store"));
			}else if("day".equals(numOrDay)){
				return bookOutTimeDayBookTopPageDao.getStore(currentPage,numPerPage,totalRow,sort,isAsc, startDate, endDate, rank, ofId.get("store"),value.get("store"));
			}
		}
		return null;
	}
	
}
