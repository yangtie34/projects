package cn.gilight.product.book.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowTeaTopPageDao;
import cn.gilight.product.book.dao.BookBorrowTjUtil;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookBorrowTeaTopPageDao")
public class BookBorrowTeaTopPageDaoImlp implements BookBorrowTeaTopPageDao {

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getAllBorrow(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String peopleId) {
		String sql=BookBorrowTjUtil.BORROWSQL+" and people_id='"+peopleId+"' ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getAllTop(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String peopleId) {
		String sql="select school_year,year_,month_,rank_,borrow_num,tea_id id,user_name name,dept_id ofid,dept_name ofname "
				+ "from tl_book_borrow_tea_month where  tea_id='"+peopleId+"' and rank_ <= 10 ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getBorrowByTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String peopleId) {
		String sql=BookBorrowTjUtil.BORROWSQL
				+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and people_id='"+peopleId+"' ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate, String peopleId) {
		String sql="select school_year,year_,month_,rank_,borrow_num,tea_id id,user_name name,dept_id ofid,dept_name ofname "
				+ "from tl_book_borrow_tea_month where "
				+ "year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<='"+endDate+"' "
				+ "and tea_id='"+peopleId+"' and rank_ <= 10 ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getEdu(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate, int rank, Map<String, String> deptId,
			String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("borrow","tea",startDate,endDate,deptId,null,"",rank," and edu_id='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getSex(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate, int rank, Map<String, String> deptId,
			String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("borrow","tea",
				startDate,endDate,deptId,null,"",rank," and sex_code='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getZc(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate, int rank, Map<String, String> deptId,
			String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("borrow","tea",
				startDate,endDate,deptId,null,"",rank," and zc_id='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getDept(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,
			String endDate, int rank, Map<String, String> deptId, Map<String, String> value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("borrow","tea",
				startDate,endDate,deptId,value,"",rank,"");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}
	
	
}
