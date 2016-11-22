package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowPageDao;
import cn.gilight.product.book.dao.BookBorrowTjUtil;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookBorrowPageDao")
public class BookBorrowPageDaoImpl implements BookBorrowPageDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getBorrow(int currentPage ,int numPerPage,int totalRow, String startDate,String endDate) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+BookBorrowTjUtil.ORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getBorrowCountByPeople(String startDate,String endDate) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getBorrowByPeople(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate, String people) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and t.people_type_code='"+people+"' "+BookBorrowTjUtil.ORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getBorrowPeosCountByPeople(
			String startDate, String endDate) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getBorrowByStore(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String store) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and t.store_code='"+store+"' "+BookBorrowTjUtil.ORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowByDeptTeach(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String deptTeach) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and (t.dept_id='"+deptTeach+"' or t.major_id='"+deptTeach+"') and t.Grade is not null "+BookBorrowTjUtil.ORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String time, String people) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and t.people_type_code='"+people+"' and substr(t.borrow_time,12,2)='"+time+"' "+BookBorrowTjUtil.ORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowBySchoolYear(int currentPage ,int numPerPage,int totalRow,
			String schoolYear) {
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startYear+"",endYear+"")+BookBorrowTjUtil.ORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowCountByMonth(int currentPage ,int numPerPage,int totalRow,
			String month) {
		String sql=BookBorrowTjUtil.BORROWSQL+"and substr(t.borrow_time,6,2)='"+month+"' "+BookBorrowTjUtil.ORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

}
