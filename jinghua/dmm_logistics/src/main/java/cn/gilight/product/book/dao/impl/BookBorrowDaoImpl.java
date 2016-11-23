package cn.gilight.product.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.book.dao.BookBorrowDao;

/**
 * 图书馆统计分析
 *
 */
@Repository("bookBorrowDao")
public class BookBorrowDaoImpl implements BookBorrowDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getBorrowNum(String startDate, String endDate) {
		String sql="select nvl(sum(a.borrow_num),0) nums,round(nvl(avg(a.borrow_num/b.books),0)*100,2) numRate, "+
					"round(nvl(avg(a.renew_num/b.books),0)*100,2) renewRate, "+
					"round(nvl(sum(a.borrow_num)/(to_date(?,'yyyy-mm')-to_date(?,'yyyy-mm')),0)) avgNums from "+
					"(select school_year,sum(borrow_num) borrow_num,sum(renew_num) renew_num from  TL_BOOK_BORROW_TYPE_MONTH  "+
					"where year_||'-'||month_>=? and  year_||'-'||month_<? group by school_year ) a "+
					"left join tl_book_year b on a.school_year=b.school_year ";
		return baseDao.getJdbcTemplate().queryForMap(sql,new Object[]{endDate,startDate,startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowByPeopleType(String startDate, String endDate) {
		String sql="select nvl(c.code_,'') code,nvl(c.name_,'未维护') name,round(nvl(avg(a.value/r.people_num),0)*100,2) value from "+
					"tl_book_reader_year r "+
					"left join "+
					"(select t.school_year,people_type_code code,sum(borrow_num) value from tl_book_borrow_people_month t "+
					"where t.year_||'-'||t.month_>=? and  year_||'-'||month_<? group by t.school_year,t.people_type_code ) a "+
					"on r.school_year=a.school_year and r.people_type_code= a.code "+
					"left join t_code_reader_identity c on r.people_type_code=c.id "+
					"group by c.code_,c.name_ order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getPeopleAvgBorrowTrend() {
		String sql="select r.school_year schoolyear,nvl(c.code_,'') code,nvl(c.name_,'未维护') name,round(nvl(avg(a.value/r.people_num),0)*100,2) value from "+ 
					"tl_book_reader_year r  "+
					"left join  "+
					"(select t.school_year,people_type_code code,sum(borrow_num) value from tl_book_borrow_people_month t "+
					"group by t.school_year,t.people_type_code ) a "+
					"on r.school_year=a.school_year and r.people_type_code= a.code  "+
					"left join t_code_reader_identity c on r.people_type_code=c.id "+
					"group by r.school_year,c.code_,c.name_ order by schoolyear, code "; 
	return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getReaderRateByPeopleType(String startDate, String endDate) {
		String sql="select nvl(c.code_,'') code,nvl(c.name_,'未维护') name,round(nvl(avg(a.value/r.people_num),0)*100,2) value from "+
				"tl_book_reader_year r "+
				"left join "+
				"(select t.school_year,people_type_code code,count(borrow_num) value from tl_book_borrow_people_month t "+
				"where t.year_||'-'||t.month_>=? and  year_||'-'||month_<? group by t.school_year,t.people_type_code ) a "+
				"on r.school_year=a.school_year and r.people_type_code= a.code "+
				"left join t_code_reader_identity c on r.people_type_code=c.id "+
				"group by c.code_,c.name_ order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getReaderRateTrend() {
		String sql="select r.school_year schoolyear,nvl(c.code_,'') code,nvl(c.name_,'未维护') name,round(nvl(avg(a.value/r.people_num),0)*100,2) value from "+ 
				"tl_book_reader_year r  "+
				"left join  "+
				"(select t.school_year,people_type_code code,count(borrow_num) value from tl_book_borrow_people_month t "+
				"group by t.school_year,t.people_type_code ) a "+
				"on r.school_year=a.school_year and r.people_type_code= a.code  "+
				"left join t_code_reader_identity c on r.people_type_code=c.id "+
				"group by r.school_year,c.code_,c.name_ order by schoolyear, code "; 
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBorrowByType(String startDate,
			String endDate) {
		String sql="select a.book_store_code code,tc.name_ name,nvl(sum(b.borrow_num),0) nums,round(nvl(avg(b.borrow_num/c.books)*10000,0),2) numRate, "+
					"round(nvl(avg(b.borrow_num/a.book_num)*10000,0),2) useRate,round(nvl(avg(b.renew_num/a.book_num)*10000,0),2) renewRate "+
					"from tl_book_type_year a  "+
					"left join (select school_year,book_store_code,sum(borrow_num) borrow_num, "+
					"sum(renew_num) renew_num from  TL_BOOK_BORROW_TYPE_MONTH  "+
					"where year_||'-'||month_>=? and  year_||'-'||month_<? "+
					"group by school_year,book_store_code)  b  "+
					"on ( a.school_year=b.school_year and a.book_store_code=b.book_store_code )  "+
					"left join TL_BOOK_YEAR c on a.school_year=c.school_year "+
					"left join t_code tc on (a.book_store_code=tc.code_ and tc.code_type='BOOK_STORE_CODE') "+
					"group by a.book_store_code,tc.name_ order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getStuBorrowByDept(String startDate,
			String endDate) {
		String sql="select nvl(c.name_,'未维护') name,nvl(c.id,'') code,nvl(sum(a.borrow_num),0) value from TL_BOOK_BORROW_STU_MONTH a "+
					"left join t_stu s on a.stu_id=s.no_ "+
					"left join t_code_dept_teach c on s.dept_id =c.id "+
					"where a.year_||'-'||a.month_>=? and  a.year_||'-'||a.month_<? "+
					"group by c.name_,c.id  order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}
	
	@Override
	public List<Map<String, Object>> getStuBorrowByMajor(String deptTeachId,String startDate,String endDate) {
		String sql="select nvl(c.name_,'未维护') name,nvl(c.id,'') code,nvl(sum(a.borrow_num),0) value from TL_BOOK_BORROW_STU_MONTH a "+
					"left join t_stu s on a.stu_id=s.no_ "+
					"left join t_code_dept_teach c on s.major_id =c.id and c.pid=? "+
					"where a.year_||'-'||a.month_>=? and  a.year_||'-'||a.month_<? "+
					"group by c.name_,c.id  order by code ";
	return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{deptTeachId,startDate,endDate});
}

	@Override
	public List<Map<String, Object>> getBorrowByTime(String startDate,
			String endDate) {
		String sql="select nvl(t.time_,'未维护') time,nvl(t.people_type_code,'') code,nvl(c.name_,'未维护') name , "+
					"nvl(sum(t.borrow_num),0) value from tl_book_borrow_people_month t "+
					"left join t_code_reader_identity c on t.people_type_code=c.id "+
					"where t.year_||'-'||t.month_>=? and  year_||'-'||month_<? "+
					"group by t.time_,t.people_type_code,c.name_ order by time_,code";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate});
	}

	@Override
	public List<Map<String, Object>> getBorrowTrendByYear() {
		String sql="select nvl(sum(borrow_num),0) value,school_year name,school_year code from tl_book_borrow_people_month group by school_year order by school_year";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBorrowTrendByMonth() {
		String sql="select nvl(sum(borrow_num),0) value,month_ name,month_ code from tl_book_borrow_people_month group by month_ order by month_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

}
