package cn.gilight.product.book.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowTjUtil;
import cn.gilight.product.book.dao.BookOutTimeNumStuTopPageDao;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookOutTimeNumStuTopPageDao")
public class BookOutTimeNumStuTopPageDaoImpl implements BookOutTimeNumStuTopPageDao{

	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getAllOutTime(int currentPage ,int numPerPage,int totalRow, String peopleId) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+" and people_id='"+peopleId+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getAllTop(int currentPage ,int numPerPage,int totalRow, String peopleId) {
		String sql="select school_year,year_,month_,nums_rank,nums,stu_id id,"
				+ "user_name name,dept_id||'-'||major_id||'-'||class_id OFID,"
				+ "dept_name||'-'||major_name||'-'||class_name OFNAME "
				+ "from tl_book_outtime_stu_month where  stu_id='"+peopleId+"' and nums_rank <= 10  ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getOutTimeByTime(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String peopleId) {
		String sql=BookBorrowTjUtil.OUTTIMESQL
				+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and people_id='"+peopleId+"' "
				+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, String peopleId) {
		String sql="select school_year,year_,month_,nums_rank,nums,stu_id id,"
				+ "user_name name,dept_id||'-'||major_id||'-'||class_id OFID,"
				+ "dept_name||'-'||major_name||'-'||class_name OFNAME "
				+ "from tl_book_outtime_stu_month where "
				+ "year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<='"+endDate+"' "
				+ "and stu_id='"+peopleId+"' and nums_rank <= 10  ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getEdu(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, int rank, Map<String, String> deptId,
			String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("num","stu",startDate,endDate,deptId,null,"",rank," and edu_id='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getSex(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, int rank, Map<String, String> deptId,
			String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("num","stu",
				startDate,endDate,deptId,null,"",rank," and sex_code='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getGrade(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, int rank, Map<String, String> deptId,
			String value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("num","stu",
				startDate,endDate,deptId,null,"",rank," and grade='"+value+"' ");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getDept(int currentPage ,int numPerPage,int totalRow, String startDate,
			String endDate, int rank, Map<String, String> deptId,Map<String,String> value) {
		String sql=BookBorrowTjUtil.getBorrowTopPage("num","stu",
				startDate,endDate,deptId,value,"",rank,"");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
