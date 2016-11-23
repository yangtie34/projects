package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookOutTimeDayTeaTopDao;

/**
 * 图书借阅排名统计分析
 *
 */
@Repository("bookOutTimeDayTeaTopDao")
public class BookOutTimeDayTeaTopDaoImpl implements BookOutTimeDayTeaTopDao{
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getOutTimeDayTopByTea(int currentPage, int numPerPage,int totalRow,Map<String,String> deptTeachs,
			String startDate, String endDate,int rank) {
		String sql="select a.days outtime_num,a.days_rank rank_,a.tea_id no_,a.user_name name,a.dept_id OFID, "+
					"a.dept_name OFNAME,nvl(b.topNum,0) topNum from  "+
					"(select * from ( "+
					"select round(avg(days)) days,dense_rank() over(order by round(avg(days)) desc) days_rank , "+
					"tea_id,user_name,dept_id,dept_name "+
					"from tl_book_outtime_tea_month "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by tea_id,user_name,dept_id,dept_name "+
					") where days_rank<="+rank+" ) a "+
					"left join (select tea_id,count(*) topNum from tl_book_outtime_tea_month where days_rank <= 10 group by tea_id ) b on a.tea_id=b.tea_id "+
					"order by a.days_rank";
		
		
		return new Page(sql,currentPage,numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByPeopleTypeTea(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(order by round(avg(days)) desc) days_rank , "+
					"tea_id,user_name,edu_id code,edu_name name "+
					"from tl_book_outtime_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by tea_id,user_name,edu_id,edu_name "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByPeopleTypeTeaTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear, code, name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(partition by school_year  "+
					"order by round(avg(days)) desc) days_rank , "+
					"school_year,tea_id,user_name,edu_id code,edu_name name "+
					"from tl_book_outtime_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by school_year ,tea_id,user_name,edu_id,edu_name "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopBySexTea(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(order by round(avg(days)) desc) days_rank , "+
					"tea_id,user_name,sex_code code,sex_name name "+
					"from tl_book_outtime_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by tea_id,user_name,sex_code,sex_name "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopBySexTeaTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear,code, name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(partition by school_year "+
					"order by round(avg(days)) desc) days_rank ,school_year, "+
					"tea_id,user_name,sex_code code,sex_name name "+
					"from tl_book_outtime_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by school_year,tea_id,user_name,sex_code,sex_name "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByTeaZC(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(order by round(avg(days)) desc) days_rank , "+
					"tea_id,user_name,ZC_ID code,ZC_NAME name "+
					"from tl_book_outtime_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by tea_id,user_name,ZC_ID,ZC_NAME "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByTeaZCTrend(Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear,code, name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(partition by school_year "+
					"order by round(avg(days)) desc) days_rank ,school_year, "+
					"tea_id,user_name,ZC_ID code,ZC_NAME name "+
					"from tl_book_outtime_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by school_year,tea_id,user_name,ZC_ID,ZC_NAME "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByDeptTeach(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String sql="select code,name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(order by round(avg(days)) desc) days_rank , "+
					"tea_id,user_name,dept_id code,dept_name name "+
					"from tl_book_outtime_tea_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by tea_id,user_name,dept_id,dept_name "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByDeptTeachTrend(
			Map<String,String> deptTeachs, int rank) {
		String sql="select school_year schoolyear,code,name,nvl(count(*),0) value from ( "+
					"select round(avg(days)) days,dense_rank() over(partition by school_year "+
					"order by round(avg(days)) desc) days_rank ,school_year, "+
					"tea_id,user_name,dept_id code,dept_name name "+
					"from tl_book_outtime_tea_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTj(deptTeachs, ShiroTagEnum.BOOK_OD.getCode(), null)+
					"group by school_year,tea_id,user_name,dept_id,dept_name "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

}
