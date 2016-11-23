package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookBorrowTeaTopDao;

/**
 * 图书借阅排名统计分析
 *
 */
@Repository("bookBorrowTeaTopDao")
public class BookBorrowTeaTopDaoImpl implements BookBorrowTeaTopDao{
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getBorrowTopByTea(int currentPage, int numPerPage,int totalRow,Map<String,String> deptTeachs,
			String startDate, String endDate,int rank) {
		String sql="select a.borrow_num ,a.rank_,a.tea_id no_,a.user_name name,a.dept_id OFID, "+
					"a.dept_name OFNAME,nvl(b.topNum,0) topNum from  "+
					"(select * from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(order by sum(borrow_num) desc) rank_ , "+
					"tea_id,user_name,dept_id,dept_name "+
					"from tl_book_borrow_tea_month "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by tea_id,user_name,dept_id,dept_name "+
					") where rank_<="+rank+" ) a "+
					"left join (select tea_id,count(*) topNum from tl_book_borrow_tea_month where rank_ <= 10 group by tea_id ) b on a.tea_id=b.tea_id "+
					"order by a.rank_";
		
		
		return new Page(sql,currentPage,numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByPeopleTypeTea(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(order by sum(borrow_num) desc) rank_ , "+
					"tea_id,user_name,edu_id code,edu_name name "+
					"from tl_book_borrow_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by tea_id,user_name,edu_id,edu_name "+
					") where rank_<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByPeopleTypeTeaTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear, code, name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(partition by school_year  "+
					"order by sum(borrow_num) desc) rank_ , "+
					"school_year,tea_id,user_name,edu_id code,edu_name name "+
					"from tl_book_borrow_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by school_year ,tea_id,user_name,edu_id,edu_name "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopBySexTea(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(order by sum(borrow_num) desc) rank_ , "+
					"tea_id,user_name,sex_code code,sex_name name "+
					"from tl_book_borrow_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by tea_id,user_name,sex_code,sex_name "+
					") where rank_<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopBySexTeaTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear ,code, name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(partition by school_year "+
					"order by sum(borrow_num) desc) rank_ ,school_year, "+
					"tea_id,user_name,sex_code code,sex_name name "+
					"from tl_book_borrow_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by school_year,tea_id,user_name,sex_code,sex_name "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByTeaZC(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(order by sum(borrow_num) desc) rank_ , "+
					"tea_id,user_name,ZC_ID code,ZC_NAME name "+
					"from tl_book_borrow_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by tea_id,user_name,ZC_ID,ZC_NAME "+
					") where rank_<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByTeaZCTrend(Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear ,code, name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(partition by school_year "+
					"order by sum(borrow_num) desc) rank_ ,school_year, "+
					"tea_id,user_name,ZC_ID code,ZC_NAME name "+
					"from tl_book_borrow_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by school_year,tea_id,user_name,ZC_ID,ZC_NAME "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByDeptTeach(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select code,name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(order by sum(borrow_num) desc) rank_ , "+
					"tea_id,user_name,dept_id code,dept_name name "+
					"from tl_book_borrow_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by tea_id,user_name,dept_id,dept_name "+
					") where rank_<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByDeptTeachTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear ,code,name,nvl(count(*),0) value from ( "+
					"select sum(borrow_num) borrow_num,dense_rank() over(partition by school_year "+
					"order by sum(borrow_num) desc) rank_ ,school_year, "+
					"tea_id,user_name,dept_id code,dept_name name "+
					"from tl_book_borrow_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_RD.getCode(), null)+
					"group by school_year,tea_id,user_name,dept_id,dept_name "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByYearTea(String schoolYear) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByQuarterTea(String schoolYear) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByMonthTea(String schoolYear) {
		// TODO Auto-generated method stub
		return null;
	}

}
