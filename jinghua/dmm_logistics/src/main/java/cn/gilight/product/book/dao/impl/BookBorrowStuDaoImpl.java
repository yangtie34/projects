package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.book.dao.BookBorrowStuDao;
import cn.gilight.product.book.dao.BookBorrowTjUtil;

/**
 * 图书馆统计分析
 *
 */
@Repository("bookBorrowStuDao")
public class BookBorrowStuDaoImpl implements BookBorrowStuDao{
	
	@Autowired
	private BaseDao baseDao;

	private String getTj(Map<String,String> deptTeachs){
		return SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
	}
	
	@Override
	public Map<String, Object> getBorrowNum(String startDate, String endDate,Map<String,String> deptTeachs) {
		String datetj=BookBorrowTjUtil.getNewDateTJ(startDate, endDate);
		String detptj=SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
		String sql="select sum(a.borrow_num) nums,round(nvl(sum(a.borrow_num/b.books),0)*100,2) numRate, "+
					"round(nvl(sum(a.renew_num/b.books),0)*100,2) renewRate, "+
					"round(nvl(sum(a.borrow_num)/(to_date('"+endDate+"','yyyy-mm')-to_date('"+startDate+"','yyyy-mm')),0)) avgNums  from  "+
					"(select t.BORROW_DATE,sum(t.borrow_num) borrow_num,sum(t.renew_num) renew_num from TL_BOOK_BORROW_STU t  "+
					"where 1=1 "+BookBorrowTjUtil.getNewBorrowDateTJ(startDate, endDate)+detptj+" group by t.BORROW_DATE ) a  "+
					"left join  "+
					"(select t.year_month,sum(books) books from TL_BOOK_MONTH t  "+
					"where 1=1 "+datetj+" group by t.year_month ) b  "+
					"on a.BORROW_DATE=b.year_month";
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowByPeopleType(String startDate, String endDate,Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql="select code,name,sum(borrow_num) borrow_num,round(sum(borrow_num/all_stu),2) value from ( "+
					"select year_month,code ,name,sum(t.borrow_num) borrow_num,sum(t.all_stu) all_stu from temp_tl_book_borrow_trend t  "+
					"where 1=1 "+BookBorrowTjUtil.getNewDateTJ(startDate, endDate)+tj+" group by year_month,code ,name "+
					") group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowTrend(Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql="select to_char(year_month,'YYYY-MM') year_month,code ,name, "+
					"round(sum(t.borrow_num)/sum(t.all_stu),2) value from temp_tl_book_borrow_trend t  "+
					"where 1=1 "+tj+" group by  year_month,code ,name order by year_month,code "; 
	return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getReaderRateByPeopleType(String startDate, String endDate,Map<String,String> deptTeachs) {
		String datetj=BookBorrowTjUtil.getNewBorrowDateTJ(startDate, endDate);
		String detptj=SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
		String sql="select a.edu_id code,a.edu_name name,round(b.stu_num/a.nums,2) value from "+
					"(select count(distinct t.no_) nums,t.edu_id ,ce.name_ edu_name from t_stu t  "+
					"left join t_code_education ce on t.edu_id=ce.id     "+
					"where '"+endDate+"' > substr(t.enroll_date,0,7) and  '"+startDate+"' < "+
					"to_char(add_months(to_date(t.enroll_date,'yyyy-mm-dd'),t.length_schooling*12),'yyyy-mm') "+detptj+
					"group by t.edu_id,ce.name_ ) a "+
					"left join  "+
					"(select count(distinct stu_id) stu_num,edu_id,edu_name from TL_BOOK_BORROW_STU t where 1=1 "+datetj+detptj+
					"group by edu_id,edu_name ) b "+
					"on a.edu_id=b.edu_id order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getReaderRateTrend(Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql="select to_char(year_month,'YYYY-MM') year_month,code ,name, "+
					"round(sum(t.borrow_stu)/sum(t.all_stu),2) value from temp_tl_book_borrow_trend t  "+
					"where 1=1 "+tj+" group by  year_month,code ,name order by year_month,code "; 
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBorrowByType(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		String datetj=BookBorrowTjUtil.getNewDateTJ(startDate, endDate);
		String detptj=SqlUtil.getDeptTeachTj(deptTeachs, ShiroTagEnum.BOOK_RS.getCode(), "t");
		String sql="select a.store_id code,a.name ,sum(a.borrow_num) nums, "+
					"round(avg(nvl(a.borrow_num/c.books,0))*10000,2) numRate, "+
					"round(avg(nvl(a.borrow_num/b.books,0))*10000,2) useRate, "+
					"round(avg(nvl(a.renew_num/b.books,0))*10000,2) renewRate  from  "+
					"(select BORROW_DATE,sum(t.borrow_num) borrow_num,sum(t.renew_num) renew_num,t.store_id store_id,t.store_name name from TL_BOOK_BORROW_STU t  "+
					"where t.store_id is not null  "+BookBorrowTjUtil.getNewBorrowDateTJ(startDate, endDate)+detptj+
					"group by t.BORROW_DATE,t.store_id,t.store_name ) a left join  "+
					"(select year_month,sum(books) books,t.store_id store_id from TL_BOOK_MONTH t where 1=1 "+datetj+
					"group by t.year_month,t.store_id ) b  "+
					"on a.store_id=b.store_id and a.BORROW_DATE=b.year_month "+
					"left join  "+
					"(select year_month,sum(books) books from TL_BOOK_MONTH t  "+
					"where 1=1 "+datetj+" group by t.year_month ) c "+ 
					"on a.BORROW_DATE=c.year_month "+
					"group by a.store_id,a.name order by a.store_id";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getStuBorrowByDept(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		
		String querySql="SELECT * FROM TL_BOOK_STU t where 1=1 "+BookBorrowTjUtil.getNewDateTJ(startDate, endDate)+tj;

		String sqldept=SqlUtil.getDeptTeachGroup(deptTeachs,querySql,true,"inner join");
		
		String querySql1="SELECT * FROM TL_BOOK_BORROW_STU t where 1=1 "+BookBorrowTjUtil.getNewBorrowDateTJ(startDate, endDate)+tj;

		String sqldept1=SqlUtil.getDeptTeachGroup(deptTeachs,querySql1,true,"inner join");

		String sql="SELECT A.DEPT_ID CODE,A.DEPT_NAME NAME,NVL(SUM(B.BORROW_NUM),0) NUMS,ROUND(SUM(NVL(B.BORROW_NUM/A.STU_NUM,0)),2) STUAVG FROM  "+
					"(SELECT T.YEAR_MONTH,T.NEXT_DEPT_CODE DEPT_ID,T.NEXT_DEPT_NAME DEPT_NAME,SUM(T.NUMS) STU_NUM FROM ("+sqldept+") T "+
					"GROUP BY T.YEAR_MONTH,T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME ) A "+
					"LEFT JOIN "+
					"(SELECT T.BORROW_DATE,T.NEXT_DEPT_CODE DEPT_ID,T.NEXT_DEPT_NAME DEPT_NAME,SUM(T.BORROW_NUM) BORROW_NUM FROM ("+sqldept1+") T "+
					"GROUP BY T.BORROW_DATE,T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME ) B "+
					"ON A.DEPT_ID=B.DEPT_ID AND A.YEAR_MONTH=B.BORROW_DATE "+
					"GROUP BY A.DEPT_ID,A.DEPT_NAME ORDER BY CODE ";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getBorrowByTime(String startDate,
			String endDate,Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql="select t.borrow_hours TIME,t.edu_id CODE,t.edu_name NAME ,sum(t.borrow_num) VALUE from TL_BOOK_BORROW_STU t where 1=1 "+
					BookBorrowTjUtil.getNewBorrowDateTJ(startDate, endDate)+tj+
					"group by t.borrow_hours,t.edu_id,t.edu_name "+
					"order by t.borrow_hours,t.edu_id";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBorrowTrendByMonth(Map<String,String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql="select to_char(year_month,'MM') code,to_char(year_month,'MM') name,sum(t.borrow_num) value "+
					"from temp_tl_book_borrow_trend t where 1=1 "+tj+" group by to_char(year_month,'MM') order by code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBorrowTrend(
			Map<String, String> deptTeachs) {
		String tj=getTj(deptTeachs);
		String sql="select to_char(t.year_month,'YYYY-MM') code,to_char(t.year_month,'YYYY-MM') name,sum(t.borrow_num) value "+
					"from temp_tl_book_borrow_trend t where 1=1 "+tj+" group by year_month order by year_month";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

}
