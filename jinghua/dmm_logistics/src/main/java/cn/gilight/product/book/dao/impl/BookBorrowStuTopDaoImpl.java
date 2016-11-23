package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookBorrowStuTopDao;

/**
 * 图书借阅排名统计分析
 *
 */
@Repository("bookBorrowStuTopDao")
public class BookBorrowStuTopDaoImpl implements BookBorrowStuTopDao{
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getBorrowTopByStu(int currentPage, int numPerPage,int totalRow,Map<String,String> deptTeachs,
			String startDate, String endDate,int rank) {
		
		String sql="select a.borrow_num ,a.rank_,a.stu_id no_,a.user_name name,a.dept_id||'-'||a.major_id||'-'||a.class_id OFID, "+
					"a.dept_name||'-'||a.major_name||'-'||a.class_name OFNAME,nvl(b.topNum,0) topNum from  "+
					"(select * from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(order by nvl(sum(borrow_num),0) desc) rank_ , "+
					"stu_id,user_name,dept_id,major_id,class_id,dept_name,major_name,class_name "+
					"from tl_book_borrow_stu_month "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null)+
					"group by stu_id,user_name,dept_id,major_id,class_id,dept_name,major_name,class_name "+
					") where rank_<="+rank+" ) a "+
					"left join (select stu_id,count(*) topNum from tl_book_borrow_stu_month where rank_ <= 10 group by stu_id ) b on a.stu_id=b.stu_id "+
					"order by a.rank_";
		
		
		return new Page(sql,currentPage,numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByPeopleTypeStu(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(order by nvl(sum(borrow_num),0) desc) rank_ , "+
					"stu_id,user_name,edu_id code,edu_name name "+
					"from tl_book_borrow_stu_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null)+
					"group by stu_id,user_name,edu_id,edu_name "+
					") where rank_<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByPeopleTypeStuTrend(
			Map<String,String> deptTeachs, int rank) {
		
		String sql="select school_year schoolyear, code, name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(partition by school_year  "+
					"order by nvl(sum(borrow_num),0) desc) rank_ , "+
					"school_year,stu_id,user_name,edu_id code,edu_name name "+
					"from tl_book_borrow_stu_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null)+
					"group by school_year ,stu_id,user_name,edu_id,edu_name "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopBySexStu(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(order by nvl(sum(borrow_num),0) desc) rank_ , "+
					"stu_id,user_name,sex_code code,sex_name name "+
					"from tl_book_borrow_stu_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null)+
					"group by stu_id,user_name,sex_code,sex_name "+
					") where rank_<="+rank+" group by code,name  order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopBySexStuTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear,code, name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(partition by school_year "+
					"order by nvl(sum(borrow_num),0) desc) rank_ ,school_year, "+
					"stu_id,user_name,sex_code code,sex_name name "+
					"from tl_book_borrow_stu_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null)+
					"group by school_year,stu_id,user_name,sex_code,sex_name "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByStuGrade(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(order by nvl(sum(borrow_num),0) desc) rank_ , "+
					"stu_id,user_name,GRADE code,GRADE_NAME name "+
					"from tl_book_borrow_stu_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null)+
					"group by stu_id,user_name,GRADE,GRADE_NAME "+
					") where rank_<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByStuGradeTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear,code, name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(partition by school_year "+
					"order by nvl(sum(borrow_num),0) desc) rank_ ,school_year, "+
					"stu_id,user_name,GRADE code,GRADE_NAME name "+
					"from tl_book_borrow_stu_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null)+
					"group by school_year,stu_id,user_name,GRADE,GRADE_NAME "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByDeptTeach(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		
		String querySql="select * from tl_book_borrow_stu_month where "+
			"year_||'-'||month_>='"+startDate+"' and  "+
			"year_||'-'||month_<'"+endDate+"' "+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null);
		
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeachs,querySql,true,"inner join");
		
		String sql="select code,name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(order by nvl(sum(borrow_num),0) desc) rank_ , "+
					"stu_id,user_name, next_dept_code code, next_dept_name name "+
					"from ("+sqldept+") "+
					"group by stu_id,user_name,next_dept_code,next_dept_name "+
					") where rank_<="+rank+" group by code, name";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByDeptTeachTrend(
			Map<String,String> deptTeachs, int rank) {
		
		String querySql="select * from tl_book_borrow_stu_month where 1=1 "+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RDT.getCode(), null);
		
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeachs,querySql,true,"inner join");
			
		String sql="select school_year schoolyear,code,name,nvl(count(*),0) value from ( "+
					"select nvl(sum(borrow_num),0) borrow_num,dense_rank() over(partition by school_year "+
					"order by nvl(sum(borrow_num),0) desc) rank_ ,school_year, "+
					"stu_id,user_name, next_dept_code code, next_dept_name name "+
					"from  ("+sqldept+") "+
					"group by school_year,stu_id,user_name,next_dept_code,next_dept_name "+
					") where rank_<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByYearStu(String schoolYear) {
		String sql="select * from TL_BOOK_BORROW_STU_YEAR_TOP where school_year =? and month_ is null and quarter is null order by rank_";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{schoolYear});
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByQuarterStu(String schoolYear) {
		String sql="select * from TL_BOOK_BORROW_STU_YEAR_TOP where school_year =? and month_ is null and quarter is not null order by quarter";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{schoolYear});
	}


	@Override
	public List<Map<String, Object>> getBorrowTopByMonthStu(String schoolYear) {
		String sql="select * from TL_BOOK_BORROW_STU_YEAR_TOP where school_year =? and month_ is not null and quarter is null order by YEAR_||MONTH_";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{schoolYear});
	}


}
