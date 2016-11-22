package cn.gilight.product.book.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowTjUtil;
import cn.gilight.product.book.dao.BookOutTimeDayBookTopPageDao;


/**
 * 图书馆统计分析
 *
 */
@Repository("BookOutTimeDayBookTopPageDao")
public class BookOutTimeDayBookTopPageDaoImpl implements BookOutTimeDayBookTopPageDao{

	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getAllOutTime(int currentPage ,int numPerPage,int totalRow, String bookName) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+" and book_name='"+bookName+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getAllTop(int currentPage ,int numPerPage,int totalRow, String bookName) {
		String sql="select school_year,year_,month_,days_rank,days,book_name name,store_code ofid,store_code ofname "
				+ "from tl_book_outtime_book_month where  book_name='"+bookName+"' and days_rank <= 10 ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getOutTimeByTime(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String bookName) {
		String sql=BookBorrowTjUtil.OUTTIMESQL
				+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and book_name='"+bookName+"' "
				+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, String bookName) {
		String sql="select school_year,year_,month_,days_rank,days,book_name name,store_code ofid,store_code ofname "
				+ "from tl_book_outtime_book_month where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<='"+endDate+"' and book_name='"+bookName+"' and days_rank <= 10 ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getStore(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, int rank, String storeId,String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("day","book",startDate,endDate,null,null,storeId,rank,"and STORE_CODE='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
