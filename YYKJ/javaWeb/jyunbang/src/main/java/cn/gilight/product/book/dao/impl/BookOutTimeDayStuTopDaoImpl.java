package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookOutTimeDayStuTopDao;

/**
 * 图书借阅排名统计分析
 *
 */
@Repository("bookOutTimeDayStuTopDao")
public class BookOutTimeDayStuTopDaoImpl implements BookOutTimeDayStuTopDao{
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getOutTimeDayTopByStu(int currentPage, int numPerPage,int totalRow,Map<String,String> deptTeachs,
			String startDate, String endDate,int rank) {
		
		String sql="select a.days outtime_num,a.days_rank rank_,a.stu_id no_,a.user_name name,a.dept_id||'-'||a.major_id||'-'||a.class_id OFID, "+
					"a.dept_name||'-'||a.major_name||'-'||a.class_name OFNAME,nvl(b.topNum,0) topNum from  "+
					"(select * from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(order by round(nvl(avg(days),0)) desc) days_rank , "+
					"stu_id,user_name,dept_id,major_id,class_id,dept_name,major_name,class_name "+
					"from tl_book_outtime_stu_month "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null)+
					"group by stu_id,user_name,dept_id,major_id,class_id,dept_name,major_name,class_name "+
					") where days_rank<="+rank+" ) a "+
					"left join (select stu_id,count(*) topNum from tl_book_outtime_stu_month where days_rank <= 10 group by stu_id ) b on a.stu_id=b.stu_id "+
					"order by a.days_rank";
		
		
		return new Page(sql,currentPage,numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByPeopleTypeStu(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(order by round(nvl(avg(days),0)) desc) days_rank , "+
					"stu_id,user_name,edu_id code,edu_name name "+
					"from tl_book_outtime_stu_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null)+
					"group by stu_id,user_name,edu_id,edu_name "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByPeopleTypeStuTrend(
			Map<String,String> deptTeachs, int rank) {
		
		String sql="select school_year schoolyear, code, name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(partition by school_year  "+
					"order by round(nvl(avg(days),0)) desc) days_rank , "+
					"school_year,stu_id,user_name,edu_id code,edu_name name "+
					"from tl_book_outtime_stu_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null)+
					"group by school_year ,stu_id,user_name,edu_id,edu_name "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopBySexStu(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(order by round(nvl(avg(days),0)) desc) days_rank , "+
					"stu_id,user_name,sex_code code,sex_name name "+
					"from tl_book_outtime_stu_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null)+
					"group by stu_id,user_name,sex_code,sex_name "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopBySexStuTrend(
			Map<String,String> deptTeachs, int rank) {
		
		String sql="select school_year schoolyear,code, name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(partition by school_year "+
					"order by round(nvl(avg(days),0)) desc) days_rank ,school_year, "+
					"stu_id,user_name,sex_code code,sex_name name "+
					"from tl_book_outtime_stu_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null)+
					"group by school_year,stu_id,user_name,sex_code,sex_name "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByStuGrade(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		
		String sql="select  code, name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(order by round(nvl(avg(days),0)) desc) days_rank , "+
					"stu_id,user_name,GRADE code,GRADE_NAME name "+
					"from tl_book_outtime_stu_month  "+
					"where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null)+
					"group by stu_id,user_name,GRADE,GRADE_NAME "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByStuGradeTrend(
			Map<String,String> deptTeachs, int rank) {
		
		String sql="select school_year schoolyear,code, name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(partition by school_year "+
					"order by round(nvl(avg(days),0)) desc) days_rank ,school_year, "+
					"stu_id,user_name,GRADE code,GRADE_NAME name "+
					"from tl_book_outtime_stu_month  "+
					"where 1=1 "+
					SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null)+
					"group by school_year,stu_id,user_name,GRADE,GRADE_NAME "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByDeptTeach(
			Map<String,String> deptTeachs, String startDate, String endDate, int rank) {
		String querySql="select * from tl_book_outtime_stu_month where "+
				"year_||'-'||month_>='"+startDate+"' and  "+
				"year_||'-'||month_<'"+endDate+"' "+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null);
			
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeachs,querySql,true,"inner join");
			
		String sql="select code,name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(order by round(nvl(avg(days),0)) desc) days_rank , "+
					"stu_id,user_name, next_dept_code code, next_dept_name name "+
					"from ("+sqldept+") "+
					"group by stu_id,user_name,next_dept_code,next_dept_name "+
					") where days_rank<="+rank+" group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getOutTimeDayTopByDeptTeachTrend(
			Map<String,String> deptTeachs, int rank) {
		String querySql="select * from tl_book_outtime_stu_month where 1=1 "+SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_ODT.getCode(), null);
			
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeachs,querySql,true,"inner join");
		String sql="select school_year schoolyear,code,name,nvl(count(*),0) value from ( "+
					"select round(nvl(avg(days),0)) days,dense_rank() over(partition by school_year "+
					"order by round(nvl(avg(days),0)) desc) days_rank ,school_year, "+
					"stu_id,user_name, next_dept_code code, next_dept_name name "+
					"from ("+sqldept+") "+
					"group by school_year,stu_id,user_name,next_dept_code,next_dept_name "+
					") where days_rank<="+rank+" group by school_year,code,name order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

}
