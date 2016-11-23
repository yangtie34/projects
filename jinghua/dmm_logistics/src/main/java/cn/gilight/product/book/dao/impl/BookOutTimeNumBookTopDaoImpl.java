package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowTjUtil;
import cn.gilight.product.book.dao.BookOutTimeNumBookTopDao;

/**
 * 图书借阅排名统计分析
 *
 */
@Repository("bookOutTimeNumBookTopDao")
public class BookOutTimeNumBookTopDaoImpl implements BookOutTimeNumBookTopDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getOutTimeNumTopByBook(int currentPage, int numPerPage,int totalRow,
			String storeId, String startDate, String endDate, int rank) {
		String tj=BookBorrowTjUtil.getBookTj(storeId);
		String sql="select a.nums outtime_num,a.nums_rank rank_,a.BOOK_NAME name,a.STORE_CODE ofid,a.STORE_NAME ofname, "+
					"nvl(b.topNum,0) topNum from  "+
					"(select * from ( "+
					"select sum(nums) nums,dense_rank() over(order by sum(nums) desc) nums_rank , "+
					"BOOK_NAME,STORE_CODE,STORE_NAME "+
					"from tl_book_outtime_book_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					tj+" group by BOOK_NAME,STORE_CODE,STORE_NAME "+
					") where nums_rank<="+rank+" ) a "+
					"left join (select BOOK_NAME,count(*) topNum from tl_book_outtime_book_month where nums_rank <= 10 group by BOOK_NAME ) b on a.BOOK_NAME=b.BOOK_NAME "+
					"order by a.nums_rank";
		
		return new Page(sql,currentPage,numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getOutTimeNumTopByStore(String storeId,
			String startDate, String endDate, int rank) {
		String tj=BookBorrowTjUtil.getBookTj(storeId);
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select sum(nums) nums,dense_rank() over(order by sum(nums) desc) nums_rank , "+
					"BOOK_NAME,STORE_CODE code,STORE_NAME name "+
					"from tl_book_outtime_book_month  "+
					"where year_||'-'||month_>=? and  year_||'-'||month_<? "+
					tj+" group by BOOK_NAME,STORE_CODE,STORE_NAME "+
					") where nums_rank<=? group by code,name order by value desc";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate,rank});
	}

	@Override
	public List<Map<String, Object>> getOutTimeNumTopByStoreTrend(String storeId,int rank) {
		String tj=BookBorrowTjUtil.getBookTj(storeId);
		String sql="select school_year schoolyear, code, name,nvl(count(*),0) value from ( "+
					"select sum(nums) nums,dense_rank() over(partition by school_year  "+
					"order by sum(nums) desc) nums_rank , "+
					"school_year,BOOK_NAME,STORE_CODE code,STORE_NAME name "+
					"from tl_book_outtime_book_month  "+
					"where 1=1 "+tj+" group by school_year ,BOOK_NAME,STORE_CODE,STORE_NAME "+
					") where nums_rank<=? group by school_year,code,name order by school_year";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{rank});
	}
	
}
