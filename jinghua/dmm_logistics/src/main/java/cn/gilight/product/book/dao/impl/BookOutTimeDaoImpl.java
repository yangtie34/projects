package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.book.dao.BookOutTimeDao;

/**
 * 图书借阅超时统计分析
 *
 */
@Repository("bookOutTimeDao")
public class BookOutTimeDaoImpl implements BookOutTimeDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public int getNowOutTimeBook() {
		String sql="select nvl(count(*),0) from t_book_borrow t where to_char(sysdate,'yyyy-mm-dd')>t.should_return_time and t.return_time is null";
		return baseDao.getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	@Override
	public Map<String,Object> getOutTimeBookCount(String startDate, String endDate) {
		String sql="select nvl(sum(outs),0) nums ,round(nvl(avg(outs/books),0)*10000,2) numRate from ( "+
					"select t.school_year,sum(outtime_num) outs,b.books  from tl_book_outtime_type_month t "+
					"left join tl_book_year b on t.school_year=b.school_year "+
					"where t.year_||'-'||t.month_>=? and  year_||'-'||month_<? "+
					"group by t.school_year ,b.books )";
		return baseDao.getJdbcTemplate().queryForMap(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleByPeopleType(
			String startDate, String endDate) {
		String sql="select nvl(t.people_type_code,'') code,nvl(c.name_,'未维护') name,nvl(sum(t.outtime_num),0) value "+
					"from tl_book_outtime_people_month t  "+
					"left join t_code_reader_identity c on t.people_type_code=c.id "+
					"where t.year_||'-'||t.month_>=? and  year_||'-'||month_<? "+
					"group by t.people_type_code ,c.name_  order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getOutTimePeopleTrend() {
		String sql="select t.school_year schoolyear,nvl(t.people_type_code,'') code,nvl(c.name_,'未维护') name,nvl(sum(t.outtime_num),0) value "+ 
					"from tl_book_outtime_people_month t  "+
					"left join t_code_reader_identity c on t.people_type_code=c.id "+
					"group by t.school_year,t.people_type_code ,c.name_ order by t.school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateByPeopleType(
			String startDate, String endDate) {
		String sql="select code,name,round(nvl(avg(value/books),0)*10000,2) value from ( "+
					"select nvl(t.people_type_code,'') code,nvl(c.name_,'未维护') name, "+
					"nvl(sum(t.outtime_num),0) value,b.books from tl_book_outtime_people_month t  "+
					"left join t_code_reader_identity c on t.people_type_code=c.id "+
					"left join tl_book_year b on t.school_year=b.school_year "+
					"where t.year_||'-'||t.month_>=? and  year_||'-'||month_<? "+
					"group by t.people_type_code ,c.name_,t.school_year ,b.books ) group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getOutTimeRateTrend() {
		String sql="select school_year schoolyear,code,name,round(nvl(avg(value/books),0)*10000,2) value from ( "+
					"select t.school_year,nvl(t.people_type_code,'') code,nvl(c.name_,'未维护') name, "+
					"nvl(sum(t.outtime_num),0) value,b.books from tl_book_outtime_people_month t  "+
					"left join t_code_reader_identity c on t.people_type_code=c.id "+
					"left join tl_book_year b on t.school_year=b.school_year "+
					"group by t.people_type_code ,c.name_,t.school_year ,b.books )  "+
					"group by code,name,school_year order by school_year,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOutTimeByType(String startDate,
			String endDate) {
		String sql="select code,name,sum(value) nums,round(nvl(avg(value/books),0)*10000,2) numRate,round(nvl(avg(days),0),0) avgTime from ( "+
					"select nvl(t.book_store_code,'') code,nvl(c.name_,'未维护') name,avg(t.outtime_day) days, "+
					"nvl(sum(t.outtime_num),0) value,b.books from tl_book_outtime_type_month t  "+
					"left join t_code c on (t.book_store_code=c.code_ and c.code_type='BOOK_STORE_CODE') "+
					"left join tl_book_year b on t.school_year=b.school_year "+
					"where t.year_||'-'||t.month_>=? and  year_||'-'||month_<? "+
					"group by t.book_store_code ,c.name_,t.school_year ,b.books ) group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getOutTimeByDept(String startDate,
			String endDate) {
		String sql="select code,name,sum(value) nums,round(nvl(avg(value / books), 0) * 10000, 2) numRate, "+
					"round(nvl(avg(days), 0), 0) avgTime from (select nvl(A.DEPT_ID, '') code, "+
					"nvl(A.DEPT_NAME, '未维护') name,avg(A.DAYS) days,nvl(sum(A.NUMS), 0) value,b.books "+
					"from TL_BOOK_OUTTIME_STU_MONTH A  left join tl_book_year b "+
					"on A.school_year = b.school_year where A.year_ || '-' || A.month_ >= ? "+
					"and A.year_ || '-' || A.month_ < ?  "+
					"group by A.DEPT_ID, A.DEPT_NAME,A.school_year, B.BOOKS) group by code, name  order by code ";
	return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
}

	@Override
	public List<Map<String, Object>> getoutTimeTrend() {
		String sql="select t.school_year schoolyear,nvl(sum(t.outtime_num),0) value,t.school_year code, "+
				"round(nvl(sum(t.outtime_num),0)/ nvl(sum(r.people_num),1)*100,2) Rate "+
				"from tl_book_outtime_type_month t left join tl_book_reader_year r on t.school_year=r.school_year "+
				"group by t.school_year";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
