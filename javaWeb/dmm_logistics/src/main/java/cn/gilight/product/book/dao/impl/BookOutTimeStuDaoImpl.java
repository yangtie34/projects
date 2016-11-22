package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookBorrowTjUtil;
import cn.gilight.product.book.dao.BookOutTimeStuDao;

/**
 * 图书借阅超时统计分析
 *
 */
@Repository("bookOutTimeStuDao")
public class BookOutTimeStuDaoImpl implements BookOutTimeStuDao{
	
	@Autowired
	private BaseDao baseDao;
	
	private String getTj(Map<String,String> deptTeachs){
		return SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
	}
	@Override
	public int getNowOutTimeBook(Map<String,String> deptTeachs) {
		String tj=getTj( deptTeachs);
		String sql="select nvl(count(*),0) from tl_book_borrow_detil t where 1=1 "+tj+"  and t.Grade is not null and to_char(sysdate,'yyyy-mm-dd')>t.should_return_time and t.return_time is null";
		return baseDao.getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	@Override
	public Map<String,Object> getOutTimeBookCount(String startDate, String endDate,Map<String,String> deptTeachs) {
		String datetj=BookBorrowTjUtil.getNewDateTJ(startDate, endDate);
		String detptj=SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
		String sql=" select nvl(sum(a.outtime_num),0) nums,round(nvl(sum(a.outtime_num/b.books),0)*10000,2) numRate from                                "+
				" (select t.RETURN_DATE,sum(t.outtime_num) outtime_num from TL_BOOK_BORROW_STU t                    "+
				" where 1=1 "+BookBorrowTjUtil.getNewReturnDateTJ(startDate, endDate)+detptj+" group by t.RETURN_DATE ) a  "+
				" left join    "+
				" (select t.year_month,sum(books) books from TL_BOOK_MONTH t                           "+
				" where 1=1 "+datetj+" group by t.year_month ) b  "+
				" on a.RETURN_DATE=b.year_month      ";
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleByPeopleType(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql=" select code,name,sum(outtime_num) outtime_num,round(sum(outtime_num/all_stu),2) value from ( "+
				" select year_month,code ,name,sum(t.outtime_num) outtime_num,sum(t.all_stu) all_stu from temp_tl_book_borrow_trend t  "+
				" where 1=1 "+BookBorrowTjUtil.getNewDateTJ(startDate, endDate)+tj+" group by year_month,code ,name "+
				" ) group by code,name order by code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleTrend(Map<String,String> deptTeachs) {
		String tj=getTj( deptTeachs);
		String sql=" select to_char(year_month,'YYYY-MM') schoolyear,code ,name, "+
						" round(sum(t.outtime_num)/sum(t.all_stu),2) value from temp_tl_book_borrow_trend t  "+
						" where 1=1 "+tj+" group by  year_month,code ,name order by year_month,code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateByPeopleType(
			String startDate, String endDate,Map<String,String> deptTeachs) {
		String datetj=BookBorrowTjUtil.getNewReturnDateTJ(startDate, endDate);
		String detptj=SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
		String sql=" select a.edu_id code,a.edu_name name,round(b.stu_num/a.nums,2) value from                        "+
				" (select count(distinct t.no_) nums,t.edu_id ,ce.name_ edu_name from t_stu t                         "+
				" left join t_code_education ce on t.edu_id=ce.id              "+
				" where '"+endDate+"' > substr(t.enroll_date,0,7) and  '"+startDate+"'   "+
				" <=to_char(add_months(to_date(t.enroll_date,'yyyy-mm-dd'),t.length_schooling*12),'yyyy-mm') "+detptj+
				" group by t.edu_id,ce.name_ ) a                               "+
				" left join             "+
				" (select count(distinct stu_id) stu_num,edu_id,edu_name from TL_BOOK_BORROW_STU t where 1=1 "+datetj+detptj+" group by edu_id,edu_name ) b                      "+
				" on a.edu_id=b.edu_id  ";

		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateTrend(Map<String,String> deptTeachs) {
		String tj=getTj( deptTeachs);
		String sql=" select to_char(year_month,'YYYY-MM') schoolyear,code ,name,  "+
				" round(sum(t.outtime_stu)/sum(t.all_stu),2) value from temp_tl_book_borrow_trend t  "+
				" where 1=1 "+tj+" group by  year_month,code ,name order by year_month,code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimeByType(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		String datetj=BookBorrowTjUtil.getNewDateTJ(startDate, endDate);
		String detptj=SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
		String sql=" select a.store_id code,a.name ,sum(a.outtime_num) nums,"+
				" round(avg(nvl(a.outtime_num/c.books,0))*10000,2) numRate,    "+
				" round(avg(nvl(a.outtime_day,0))) avgTime from    "+
				" (select RETURN_DATE,sum(t.outtime_num) outtime_num,avg(t.outtime_day) outtime_day,t.store_id store_id,t.store_name name from TL_BOOK_BORROW_STU t "+
				" where t.store_id is not null "+BookBorrowTjUtil.getNewReturnDateTJ(startDate, endDate)+detptj+" group by t.RETURN_DATE,t.store_id,t.store_name ) a"+
				" left join                "+
				" (select year_month,sum(books) books from TL_BOOK_MONTH t where 1=1 "+datetj+"  group by t.year_month ) c"+
				" on a.RETURN_DATE=c.year_month   "+
				" group by a.store_id,a.name     order by a.store_id";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimeByDept(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		
		String querySql="SELECT * FROM TL_BOOK_STU t where 1=1 "+BookBorrowTjUtil.getNewDateTJ(startDate, endDate)+tj;

		String sqldept=SqlUtil.getDeptTeachGroup(deptTeachs,querySql,true,"inner join");
		
		String querySql1="SELECT * FROM TL_BOOK_BORROW_STU t where 1=1 "+BookBorrowTjUtil.getNewReturnDateTJ(startDate, endDate)+tj;

		String sqldept1=SqlUtil.getDeptTeachGroup(deptTeachs,querySql1,true,"inner join");
		
		String sql=" select a.dept_id code,a.dept_name name,nvl(sum(b.outtime_num),0) nums, "+
				" round(nvl(sum(b.outtime_num/a.stu_num),0),2) stuAVG,round(avg(nvl(b.outtime_day,0))) avgTime from"+
				" (select t.year_month,t.NEXT_DEPT_CODE dept_id,t.NEXT_DEPT_NAME dept_name,sum(t.nums) stu_num from ("+sqldept+") T "+
				"   group by t.year_month,t.NEXT_DEPT_CODE,t.NEXT_DEPT_NAME ) a "+
				" left join                "+
				" (select t.RETURN_DATE,t.NEXT_DEPT_CODE dept_id,T.NEXT_DEPT_NAME dept_name,"+
				"sum(t.outtime_num) outtime_num,avg(t.outtime_day) outtime_day "+
				"from ("+sqldept1+") T "+
				" group by t.RETURN_DATE,t.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME ) b    "+
				" on a.dept_id=b.dept_id and a.year_month=b.RETURN_DATE   "+
				" group by a.dept_id,a.dept_name order by code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getoutTimeTrend(Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql=" select to_char(year_month,'YYYY') code,to_char(year_month,'YYYY') schoolyear,"
				+ "sum(t.outtime_num) value ,nvl(round(sum(t.outtime_num)/(case sum(t.borrow_num) when 0 then 1 else sum(t.borrow_num) end )*100 ,2),0) rate"+
				" from temp_tl_book_borrow_trend t where 1=1 "+tj+" group by to_char(year_month,'YYYY') order by code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
