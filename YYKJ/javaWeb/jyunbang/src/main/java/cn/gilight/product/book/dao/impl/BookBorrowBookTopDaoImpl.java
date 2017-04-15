package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowBookTopDao;
import cn.gilight.product.book.dao.BookBorrowTjUtil;

/**
 * 图书借阅排名统计分析
 *
 */
@Repository("bookBorrowBookTopDao")
public class BookBorrowBookTopDaoImpl implements BookBorrowBookTopDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getBorrowTopByBook(int currentPage, int numPerPage,int totalRow,
			String storeId, String startDate, String endDate, int rank) {
		String tj=BookBorrowTjUtil.getBookTj(storeId);
		String sql="select a.borrow_num ,a.rank_,a.BOOK_NAME name,a.STORE_CODE ofid,a.STORE_NAME ofname, "+
					"nvl(b.topNum,0) topNum from  "+
					"(select * from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(order by sum(borrow_num) desc) rank_ , "+
					"BOOK_NAME,STORE_CODE,STORE_NAME "+
					"from tl_book_borrow_book_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					tj+"group by BOOK_NAME,STORE_CODE,STORE_NAME "+
					") where rank_<="+rank+" ) a "+
					"left join (select BOOK_NAME,count(*) topNum from tl_book_borrow_book_month where rank_ <= 10 group by BOOK_NAME ) b on a.BOOK_NAME=b.BOOK_NAME "+
					"order by a.rank_";
		
		return new Page(sql,currentPage,numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByStore(String storeId,
			String startDate, String endDate, int rank) {
		String tj=BookBorrowTjUtil.getBookTj(storeId);
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(order by sum(borrow_num) desc) rank_ , "+
					"BOOK_NAME,STORE_CODE code,STORE_NAME name "+
					"from tl_book_borrow_book_month  "+
					"where year_||'-'||month_>=? and  year_||'-'||month_<? "+
					tj+" group by BOOK_NAME,STORE_CODE,STORE_NAME "+
					") where rank_<=? group by code,name order by value desc";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate,rank});
	}

	@Override
	public List<Map<String, Object>> getBorrowTopByStoreTrend(String storeId,
			int rank) {
		String tj=BookBorrowTjUtil.getBookTj(storeId);
		String sql="SELECT SCHOOL_YEAR SCHOOLYEAR, CODE, NAME,nvl(count(*),0) VALUE FROM ( "+
					"SELECT SUM(BORROW_NUM) BORROW_NUM,DENSE_RANK() OVER(PARTITION BY SCHOOL_YEAR  "+
					"ORDER BY SUM(BORROW_NUM) DESC) RANK_ , "+
					"SCHOOL_YEAR,BOOK_NAME,STORE_CODE CODE,STORE_NAME NAME "+
					"FROM TL_BOOK_BORROW_BOOK_MONTH  "+
					"WHERE 1=1 "+tj+" GROUP BY SCHOOL_YEAR ,BOOK_NAME,STORE_CODE,STORE_NAME "+
					") WHERE RANK_<=? GROUP BY SCHOOL_YEAR,CODE,NAME ORDER BY SCHOOL_YEAR";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{rank});
	}
	
}
