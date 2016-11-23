package cn.gilight.product.book.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowBookTopPageDao;
import cn.gilight.product.book.dao.BookBorrowTjUtil;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookBorrowBookTopPageDao")
public class BookBorrowBookTopPageDaoImpl implements BookBorrowBookTopPageDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getAllBorrow(int currentPage ,int numPerPage,int totalRow, String bookName) {
		String sql=BookBorrowTjUtil.BORROWSQL+" and book_name='"+bookName+"' ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getAllTop(int currentPage ,int numPerPage,int totalRow, String bookName) {
		String sql="select school_year,year_,month_,rank_,borrow_num,book_name name,store_code ofid,store_code ofname "
				+ "from tl_book_borrow_book_month where  book_name='"+bookName+"' and rank_ <= 10 ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
	@Override
	public Page getBorrowByTime(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String bookName) {
		String sql=BookBorrowTjUtil.BORROWSQL
				+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and book_name='"+bookName+"' ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, String bookName) {
		String sql="select school_year,year_,month_,rank_,borrow_num,book_name name,store_code ofid,store_code ofname "
				+ "from tl_book_borrow_book_month where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<='"+endDate+"' and book_name='"+bookName+"' and rank_ <= 10 ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getStore(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, int rank, String storeId,String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("borrow","book",startDate,endDate,null,null,storeId,rank,"and STORE_CODE='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
