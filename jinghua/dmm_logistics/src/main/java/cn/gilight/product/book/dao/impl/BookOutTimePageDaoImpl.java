package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookBorrowTjUtil;
import cn.gilight.product.book.dao.BookOutTimePageDao;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookOutTimePageDao")
public class BookOutTimePageDaoImpl implements BookOutTimePageDao{
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getNowOutTime(int currentPage ,int numPerPage,int totalRow) {
		String sql="select * from tl_book_borrow_detil t where to_char(sysdate,'yyyy-mm-dd')>t.should_return_time and t.return_time is null";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getOutTime(int currentPage ,int numPerPage,int totalRow, String startDate,String endDate) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getOutTimeCountByPeople(String startDate,
			String endDate) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getOutTimeByPeople(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String people) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and t.people_type_code='"+people+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateCountByPeople(
			String startDate, String endDate) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getOutTimeByStore(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String store) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and t.store_code='"+store+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getOutTimeByDeptTeach(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String deptTeach) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and (t.dept_id='"+deptTeach+"' or t.major_id='"+deptTeach+"') "+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String time, String people) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and t.people_type_code='"+people+"' and substr(t.return_time,12,2)='"+time+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getOutTimeBySchoolYear(int currentPage ,int numPerPage,int totalRow,
			String schoolYear) {
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startYear+"",endYear+"")+BookBorrowTjUtil.OUTTIMEORDERSQL;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
