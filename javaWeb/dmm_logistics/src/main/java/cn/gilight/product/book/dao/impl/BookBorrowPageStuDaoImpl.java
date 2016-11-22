package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookBorrowStuPageDao;
import cn.gilight.product.book.dao.BookBorrowTjUtil;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookBorrowStuPageDao")
public class BookBorrowPageStuDaoImpl implements BookBorrowStuPageDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getBorrow(int currentPage ,int numPerPage,int totalRow, String startDate,String endDate,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.BORROWSQL+" and t.Grade is not null "+BookBorrowTjUtil.getDateTJ(startDate, endDate)+BookBorrowTjUtil.ORDERSQL+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getBorrowCountByPeople(String startDate,String endDate,Map<String,String> deptTeachs) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getBorrowByPeople(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate, String people,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and t.Grade is not null and t.edu_id='"+people+"' "+BookBorrowTjUtil.ORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getBorrowPeosCountByPeople(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getBorrowByStore(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String store,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and t.Grade is not null and t.store_code='"+store+"' "+BookBorrowTjUtil.ORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowByDeptTeach(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String deptTeach,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and (t.dept_id='"+deptTeach+"' or t.major_id='"+deptTeach+"'or t.class_id='"+deptTeach+"') and t.Grade is not null "+BookBorrowTjUtil.ORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage ,int numPerPage,int totalRow,
			String startDate, String endDate, String time, String people,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startDate, endDate)+" and t.Grade is not null and t.edu_id='"+people+"' and substr(t.borrow_time,12,2)='"+time+"' "+BookBorrowTjUtil.ORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowBySchoolYear(int currentPage ,int numPerPage,int totalRow,
			String schoolYear,Map<String,String> deptTeachs) {
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String sql=BookBorrowTjUtil.BORROWSQL+BookBorrowTjUtil.getDateTJ(startYear+"",endYear+"")+BookBorrowTjUtil.ORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBorrowCountByMonth(int currentPage ,int numPerPage,int totalRow,
			String month,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.BORROWSQL+" and t.Grade is not null and substr(t.borrow_time,6,2)='"+month+"' "+BookBorrowTjUtil.ORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

}
