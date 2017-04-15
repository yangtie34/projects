package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookBorrowTjUtil;
import cn.gilight.product.book.dao.BookOutTimeStuPageDao;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookOutTimeStuPageDao")
public class BookOutTimePageStuDaoImpl implements BookOutTimeStuPageDao{
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getNowOutTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,Map<String,String> deptTeachs) {
		String sql="select * from tl_book_borrow_detil t where to_char(sysdate,'yyyy-mm-dd')>t.should_return_time and t.return_time is null and t.Grade is not null "
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_OS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getOutTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc, String startDate,String endDate,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+" and t.Grade is not null "+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+BookBorrowTjUtil.OUTTIMEORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_OS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public List<Map<String, Object>> getOutTimeCountByPeople(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getOutTimeByPeople(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String people,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and t.Grade is not null and t.edu_id='"+people+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_OS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateCountByPeople(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		//TODO 点击展示比例时，目前只能站在分子，没有分母
		return null;
	}

	@Override
	public Page getOutTimeByStore(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String store,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and t.Grade is not null and t.store_code='"+store+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_OS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getOutTimeByDeptTeach(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String deptTeach,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and t.Grade is not null and (t.dept_id='"+deptTeach+"' or t.major_id='"+deptTeach+"' or t.class_id='"+deptTeach+"' ) "+BookBorrowTjUtil.OUTTIMEORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_OS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getBorrowByTimePeo(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, String time, String people,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+BookBorrowTjUtil.getReturnDateTJ(startDate, endDate)+" and t.Grade is not null and t.edu_id='"+people+"' and substr(t.return_time,12,2)='"+time+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_OS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getOutTimeBySchoolYear(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,
			String schoolYear,Map<String,String> deptTeachs) {
		String sql=BookBorrowTjUtil.OUTTIMESQL+" and t.Grade is not null and substr(t.return_time,6,2)='"+schoolYear+"' "+BookBorrowTjUtil.OUTTIMEORDERSQL
				+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_OS.getCode(),"t");
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}
	
}
