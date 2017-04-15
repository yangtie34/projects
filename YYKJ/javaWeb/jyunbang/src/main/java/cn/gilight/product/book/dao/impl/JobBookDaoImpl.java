package cn.gilight.product.book.dao.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.QuarterEnum;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.Globals;
import cn.gilight.product.book.dao.JobBookDao;

@Repository("jobBookDao")
public class JobBookDaoImpl implements JobBookDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public void updateBookYear(String schoolYear){
		
		String delSql="delete TL_BOOK_YEAR where school_year=? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		String addSql="insert into TL_BOOK_YEAR "+
						"SELECT t.school_year,t.books,(t.books-nvl(a.books,0)) upbooks,nvl(t.moneys,0), "+
						"nvl(t.moneys-nvl(a.moneys,0),0) upmoneys FROM "+
						"( select ? school_year,nvl(count(*),0) books,round(nvl(sum(b.price),0),2) moneys  from t_book b  "+
						"where b.store_date < ? ) t "+
						"FULL JOIN  "+
						"( SELECT * FROM TL_BOOK_YEAR WHERE SCHOOL_YEAR= ? ) a ON 1=1 ";
		
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,(startYear+1)+"-"+Globals.CUT_YEAR,(startYear-1)+"-"+startYear});
		
	}
	@Override
	public void updateBookStateYear(String schoolYear){
		String delSql=" drop table TL_BOOK_STATE_YEAR ";
		try{
			baseDao.getJdbcTemplate().execute(delSql);
		}catch(Exception e){
			
		}
		
		
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		String addSql=" CREATE TABLE TL_BOOK_STATE_YEAR AS "+
						"select '"+schoolYear+"' school_year,count(t.no_) books,t.state_code,tc.name_ state_name from t_book t "+
          " left join t_code tc on (tc.code_= t.state_code and tc.code_type like '%STATE_CODE%')"+
          " where "
         // + " t.store_date>= '"+startYear+"-"+Globals.CUT_YEAR+"' and"
          		+ " t.store_date < '"+(startYear+1)+"-"+Globals.CUT_YEAR+"'"+
         " group by t.state_code,tc.name_ ";
		
		baseDao.getJdbcTemplate().execute(addSql);
		
	}
	
	@Override
	public void updateBookTypeYear(String schoolYear){
		String delSql="delete TL_BOOK_TYPE_YEAR where school_year=? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		
		String addSql="insert into TL_BOOK_TYPE_YEAR "+
						"select ? school_year,nvl(count(*),0) BOOK_NUM,nvl(sum(b.price),0) BOOK_MONEY,b.store_code book_store_code ,'1' BOOK_LANGUAGE_CODE  from t_book b  "+
						"left join t_code c on b.store_code=c.code_ and c.code_type='BOOK_STORE_CODE'  "+
						"where b.store_date < ? and c.code_ <> ?  group by b.store_code  ";
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,(startYear+1)+"-"+Globals.CUT_YEAR,"00013"});
		
		addSql="insert into TL_BOOK_TYPE_YEAR "+
				"select ? school_year,nvl(count(*),0) BOOK_NUM,nvl(sum(b.price),0) BOOK_MONEY,b.store_code book_store_code,'2' BOOK_LANGUAGE_CODE  from t_book b  "+
				"left join t_code c on b.store_code=c.code_ and c.code_type='BOOK_STORE_CODE'  "+
				"where b.store_date < ? and c.code_= ?  group by b.store_code  ";
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,(startYear+1)+"-"+Globals.CUT_YEAR,"00013"});
		
	}
	
	@Override
	public void updateBookReaderYear(String schoolYear) {
		String delSql="delete TL_BOOK_READER_YEAR where school_year=? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		String startS=startYear+"-"+Globals.CUT_YEAR;
		String endS=(startYear+1)+"-"+Globals.CUT_YEAR;
		
		
		String addSql="insert into TL_BOOK_READER_YEAR "+
						"select ? school_year,count(*) ,r.people_type_code ,nvl(c.name_,'未维护') from t_book_reader r "+
						"left join t_code_reader_identity c on r.people_type_code=c.id "+
						"where ( r.istrue=0 and ? <= r.logout_time and r.time_ < ?   ) "+
						"or (r.istrue=1 and r.time_ < ? ) group by r.people_type_code ,c.name_";
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,startS,endS,endS});
		
	}
	
	@Override
	public void updateBookReaderDetilYear(String schoolYear) {
		String delSql="delete TL_BOOK_READER_DETIL_YEAR where school_year=? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		String startS=startYear+"-"+Globals.CUT_YEAR;
		String endS=(startYear+1)+"-"+Globals.CUT_YEAR;
		
		
		String addSql="insert into TL_BOOK_READER_DETIL_YEAR "+
				"select ? school_year,r.no_ reader_id,                                  "+
				"case when s.no_ is not null then s.no_ else tea.tea_no end people_id,            "+
				"case when s.no_ is not null then s.name_ else tea.name_ end people_name,         "+
				"case when s.no_ is not null then s.sex_code else tea.sex_code end sex_code,        "+
				"case when s.no_ is not null then nvl(sexS.name_,'未维护') else nvl(sext.name_,'未维护') end sex_name,        "+
				"case when s.no_ is not null then s.dept_id else tea.dept_id end dept_id,                  "+
				"case when s.no_ is not null then nvl(cdt.name_,'未维护') else nvl(cd.name_,'未维护') end dept_name,          "+
				"r.people_type_code,nvl(cci.name_,'未维护') people_type_name,R.TIME_ ADD_TIME,R.LOGOUT_TIME     "+
				"from t_book_reader r                                                             "+
				"left join t_code_reader_identity cci on r.people_type_code=cci.id                  "+
				"left join t_stu s on s.no_=r.people_id                        "+
				"left join t_tea tea on tea.tea_no=r.people_id               "+
				"left join t_code_dept_teach cdt on s.dept_id =cdt.id                             "+
				"left join t_code_dept cd on tea.dept_id =cd.id                                   "+
				"left join t_code sexS on (s.sex_code=sexS.Code_ and sexS.Code_Type='SEX_CODE')   "+
				"left join t_code sext on (tea.sex_code=sext.Code_ and sext.Code_Type='SEX_CODE') "+
				"where ( r.istrue=0 and ? <= r.logout_time and r.time_ < ?   ) "+
				"or (r.istrue=1 and r.time_ < ? )  ";
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,startS,endS,endS});
		
	}
	
	@Override
	public void updateBookBorrowTypeMonth(String schoolYear,String month) {
		
		String delSql="delete TL_BOOK_BORROW_TYPE_MONTH WHERE school_year= ? and month_= ? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		String addSql="insert into TL_BOOK_BORROW_TYPE_MONTH "+
						"select ? school_year ,? year_,? month_,nvl(count(*),0) BORROW_NUM,nvl(sum(t.RENEW_COUNT),0) "+
						"RENEW_NUM,bo.store_code book_store_code "+
						"from t_book_borrow t  left join t_book bo on t.book_id=bo.no_ "+
						"where substr(t.borrow_time,0,7) = ? "+
						"group by bo.store_code";
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,year,month,year+"-"+month});
	}

	@Override
	public void updateBookBorrowPeopleMonth(String schoolYear,String month) {
		
		String delSql="delete TL_BOOK_BORROW_PEOPLE_MONTH WHERE school_year= ? and month_= ? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String addSql="insert into TL_BOOK_BORROW_PEOPLE_MONTH "+
						"select ? school_year ,? year_,? month_,substr(t.borrow_time,12,2) time_ ,nvl(count(*),0) BORROW_NUM,"+
						"nvl(sum(t.RENEW_COUNT),0) RENEW_NUM,br.people_type_code "+
						"from t_book_borrow t  left join t_book_reader br on t.book_reader_id=br.no_ "+
						"where substr(t.borrow_time,0,7) = ? "+
						"group by substr(t.borrow_time,12,2),br.people_type_code ";
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,year,month,year+"-"+month});
		
	}

	@Override
	public void updateBookOutTimeTypeMonth(String schoolYear,String month) {
		
		String delSql="DELETE TL_BOOK_OUTTIME_TYPE_MONTH WHERE SCHOOL_YEAR=? AND MONTH_= ? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String addSql="insert into TL_BOOK_OUTTIME_TYPE_MONTH "+
						"select ? school_year,? year_,? month_,nvl(count(*),0), "+
						"nvl(round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ),0) days,bo.store_code "+
						"from t_book_borrow t left join t_book bo on t.book_id=bo.no_ "+
						"where  substr(t.return_time,0,7) =? and t.return_time>t.should_return_time group by bo.store_code";
		
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,year,month,year+"-"+month});
	}

	@Override
	public void updateBookOutTimePeopleMonth(String schoolYear,String month) {
		
		String delSql="DELETE TL_BOOK_OUTTIME_PEOPLE_MONTH WHERE SCHOOL_YEAR=? AND MONTH_= ? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String addSql="insert into TL_BOOK_OUTTIME_PEOPLE_MONTH "+
						"select ? school_year,? year_,? month_,nvl(count(*),0), "+
						"nvl(round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ),0) days,br.people_type_code "+
						"from t_book_borrow t left join t_book_reader br on t.book_reader_id=br.no_ "+
						"where substr(t.return_time,0,7) =? and t.return_time>t.should_return_time group by br.people_type_code";
		baseDao.getJdbcTemplate().update(addSql,new Object[]{schoolYear,year,month,year+"-"+month});
	}

	@Override
	public Map<String,Integer> updateBookBorrowStuMonth(String schoolYear,String month){
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete TL_BOOK_BORROW_STU_MONTH where school_year= ? and month_= ? ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		map.put("delNum", delNum);
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String sql="insert into TL_BOOK_BORROW_STU_MONTH "+
					"select ? school_year,? year_,? monthes ,dense_rank() "+
					"over(order by count(*) desc) rank_ ,nvl(count(*),0) stu_borrows ,s.no_ stuId, "+
					"s.name_ user_name,s.dept_id,nvl(c.name_,'未维护') dept_name,s.major_id,nvl(cc.name_,'未维护') major_name,s.class_id class_id,nvl(cl.name_,'未维护') class_name, "+
					"s.edu_id,nvl(ce.name_,'未维护') edu_name,s.sex_code,nvl(tc.name_,'未维护') sex_name,?-s.ENROLL_GRADE+1 GRADE ,(?-s.ENROLL_GRADE+1)||'年级' GRADE_NAME "+
					"from t_book_borrow t  "+
					"left join t_book_reader br on t.book_reader_id=br.no_ "+
					"inner join t_stu s on br.people_id= s.no_ "+
					"left join t_code_dept_teach c on s.dept_id =c.id  "+
					"left join t_code_dept_teach cc on s.major_id=cc.id  "+
					"left join t_classes cl on s.class_id=cl.no_ "+
					"left join t_code_education ce on s.edu_id=ce.id "+
					"left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
					"where substr(t.borrow_time,0,7) =? "+
					"group by s.no_,s.name_,s.dept_id,c.name_,s.major_id,cc.name_ ,s.class_id,cl.name_, "+
					"s.edu_id,ce.name_,s.sex_code,tc.name_ ,s.ENROLL_GRADE ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{schoolYear,year,month,startYear,startYear,year+"-"+month});
		map.put("addNum", addNum);
		
		return map;
	}
	
	@Override
	public Map<String,Integer> updateBookBorrowTeaMonth(String schoolYear,String month){
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete TL_BOOK_BORROW_TEA_MONTH where school_year= ? and month_= ? ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		map.put("delNum", delNum);
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String sql="insert into TL_BOOK_BORROW_TEA_MONTH "+
					"select ? school_year,? year_,? monthes ,dense_rank() "+
					"over(order by count(*) desc) rank_ ,nvl(count(*),0) stu_borrows ,s.TEA_NO tea_id, "+
					"s.name_ tea_name,s.dept_id,nvl(c.name_,'未维护') dept_name,s.sex_code,nvl(tc.name_ ,'未维护') sex_name, "+
					"s.edu_id,nvl(ce.name_,'未维护') edu_name,s.ZYJSZW_ID,nvl(z.name_,'未维护') zc_name "+
					"from t_book_borrow t  "+
					"left join t_book_reader br on t.book_reader_id=br.no_ "+
					"inner join t_tea s on br.people_id= s.TEA_NO "+
					"left join t_code_dept c on s.dept_id =c.id "+
					"left join t_code_education ce on s.edu_id=ce.id "+
					"left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
					"left join T_CODE_ZYJSZW z on  s.ZYJSZW_ID=z.id "+
					"where substr(t.borrow_time,0,7) =? "+
					"group by s.TEA_NO,s.name_,s.dept_id,c.name_,s.sex_code,tc.name_,s.edu_id,ce.name_,s.ZYJSZW_ID,z.name_";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{schoolYear,year,month,year+"-"+month});
		map.put("addNum", addNum);
		
		return map;
	}
	
	@Override
	public Map<String,Integer> updateBookBorrowBookMonth(String schoolYear,String month){
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete TL_BOOK_BORROW_BOOK_MONTH where school_year= ? and month_= ? ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		map.put("delNum", delNum);
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String sql="insert into TL_BOOK_BORROW_BOOK_MONTH "+
					"select ? school_year,? year,? month_ ,dense_rank() over(order by count(*) desc) rank_ , "+
					"nvl(count(*),0) borrow_num,bo.name_ book_name, "+
					"bo.store_code store_code,nvl(C.NAME_,'未维护') store_name "+
					"from t_book_borrow t "+
					"left join t_book bo on t.book_id=bo.no_ "+
					"left join t_code c on bo.store_code=c.code_ and c.code_type='BOOK_STORE_CODE' "+
					"where substr(t.borrow_time,0,7) =? "+
					"and bo.name_ is not null "+
					"group by bo.name_,bo.store_code,C.NAME_ ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{schoolYear,year,month,year+"-"+month});
		map.put("addNum", addNum);
		
		return map;
	}
	
	@Override
	public Map<String, Integer> updateBookOutTimeStuMonth(String schoolYear,String month) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete TL_BOOK_OUTTIME_STU_MONTH where school_year= ? and month_= ? ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		map.put("delNum", delNum);
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String sql="insert into TL_BOOK_OUTTIME_STU_MONTH "+
					"select ? school_year,? year_,? monthes ,dense_rank() over(order by count(*) desc) nums_rank,nvl(count(*),0) nums, "+
					"dense_rank() over(order by  "+
					"round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ) desc) days_rank , "+
					"nvl(round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ),0) days, "+
					"s.no_ stuId,s.name_ user_name,s.dept_id,nvl(c.name_,'未维护') dept_name,s.major_id,nvl(cc.name_,'未维护') major_name,s.class_id class_id,nvl(cl.name_,'未维护') class_name, "+
					"s.edu_id,nvl(ce.name_,'未维护') edu_name,s.sex_code,nvl(tc.name_,'未维护') sex_name,?-s.ENROLL_GRADE+1 GRADE ,(?-s.ENROLL_GRADE+1)||'年级' GRADE_NAME "+
					"from t_book_borrow t  "+
					"left join t_book_reader br on t.book_reader_id=br.no_ "+
					"inner join t_stu s on br.people_id= s.no_ "+
					"left join t_code_dept_teach c on s.dept_id =c.id  "+
					"left join t_code_dept_teach cc on s.major_id=cc.id  "+
					"left join t_classes cl on s.class_id=cl.no_ "+
					"left join t_code_education ce on s.edu_id=ce.id "+
					"left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
					"where substr(t.return_time,0,7) =? and t.return_time>t.should_return_time "+
					"group by s.no_,s.name_,s.dept_id,c.name_,s.major_id,cc.name_ ,s.class_id,cl.name_, "+
					"s.edu_id,ce.name_,s.sex_code,tc.name_ ,s.ENROLL_GRADE order by nums_rank ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{schoolYear,year,month,startYear,startYear,year+"-"+month});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateBookOutTimeTeaMonth(String schoolYear,String month) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete TL_BOOK_OUTTIME_TEA_MONTH where school_year= ? and month_= ? ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		map.put("delNum", delNum);
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String sql="insert into TL_BOOK_OUTTIME_TEA_MONTH "+
					"select ? school_year,? year_,? monthes ,dense_rank() over(order by count(*) desc) nums_rank,nvl(count(*),0) nums, "+
					"dense_rank() over(order by  "+
					"round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ) desc) days_rank , "+ 
					"nvl(round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ),0) days, "+ 
					"s.TEA_NO tea_id,s.name_ tea_name,s.dept_id,nvl(c.name_,'未维护') dept_name,s.sex_code,nvl(tc.name_ ,'未维护') sex_name, "+
					"s.edu_id,nvl(ce.name_,'未维护') edu_name,s.ZYJSZW_ID,nvl(z.name_,'未维护') zc_name "+ 
					"from t_book_borrow t  "+
					"left join t_book_reader br on t.book_reader_id=br.no_ "+ 
					"inner join t_tea s on br.people_id= s.TEA_NO "+ 
					"left join t_code_dept c on s.dept_id =c.id "+
					"left join t_code_education ce on s.edu_id=ce.id "+
					"left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
					"left join T_CODE_ZYJSZW z on  s.ZYJSZW_ID=z.id "+
					"where substr(t.return_time,0,7) =? and t.return_time>t.should_return_time  "+
					"group by s.TEA_NO,s.name_,s.dept_id,c.name_,s.sex_code,tc.name_,s.edu_id,ce.name_,s.ZYJSZW_ID,z.name_";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{schoolYear,year,month,year+"-"+month});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateBookOutTimeBookMonth(String schoolYear,String month) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete TL_BOOK_OUTTIME_BOOK_MONTH where school_year= ? and month_= ? ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		map.put("delNum", delNum);
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String sql="insert into TL_BOOK_OUTTIME_BOOK_MONTH "+
					"select ? school_year,? year_,? month_ ,dense_rank() over(order by count(*) desc) nums_rank,nvl(count(*),0) nums, "+
					"dense_rank() over(order by "+
					"round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ) desc) days_rank , "+
					"nvl(round(avg(to_date(substr(t.return_time,0,10),'yyyy-mm-dd')-to_date(substr(t.should_return_time,0,10),'yyyy-mm-dd')),0 ),0) days, "+
					"bo.name_ book_name,bo.store_code store_code,nvl(C.NAME_,'未维护') store_name "+
					"from t_book_borrow t "+
					"left join t_book bo on t.book_id=bo.no_ "+
					"left join t_code c on bo.store_code=c.code_ and c.code_type='BOOK_STORE_CODE' "+
					"where substr(t.return_time,0,7) =? "+
					"and bo.name_ is not null   "+
					"and t.return_time>t.should_return_time "+
					"group by bo.name_,bo.store_code,C.NAME_";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{schoolYear,year,month,year+"-"+month});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public void updateBorrowTopYear(String schoolYear) {
		String delSql="delete TL_BOOK_BORROW_STU_YEAR_TOP where school_year= ? and month_ is null and QUARTER is null ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear});
		
		String sql="insert into TL_BOOK_BORROW_STU_YEAR_TOP "+
				"SELECT '"+schoolYear+"' SCHOOL_YEAR,'' YEAR_,'' MONTH_,'' QUARTER,NO_,NAME,ttt.sex_name,OFNAME,C.RANK_,0 MONTHNUM,0 QUARTERNUM,   "+
				"YEARNUM,nvl(count(*),0) TOPNUM,C.HISTORYNUM,C.HISTORYTOP  FROM                                                     "+
				"(select  no_,name,ofname,yearnum,b.rank_,b.historyTop,nvl(sum(tt.borrow_num),0) historyNum from                    "+
				"(select no_,name,ofname,yearnum,a.rank_ ,nvl(count(*),0) historyTop  from                                          "+
				"(select stu_id no_,user_name name,dept_name||'-'||major_name||'-'||class_name ofname,yearnum, rank_  from ( "+
				"select stu_id,user_name,dept_name,major_name,class_name, nvl(sum(borrow_num),0) yearNum,                           "+
				"dense_rank() over(order by sum(borrow_num) desc) rank_                                                      "+
				"from tl_book_borrow_stu_month                                                                               "+
				"where school_year='"+schoolYear+"'                                                                          "+
				"group by stu_id,user_name,dept_name,major_name,class_name ) where rank_<=3 ) a                              "+
				"left join tl_book_borrow_stu_month t on a.no_=t.stu_id                                                      "+
				"where t.rank_ <=10 group by no_,name,ofname,yearnum,a.rank_ ) b                                             "+
				"left join tl_book_borrow_stu_month tt on b.no_=tt.stu_id                                                    "+
				"group by no_,name,ofname,yearnum,b.rank_,b.historyTop ) c                                                   "+
				"left join tl_book_borrow_stu_month ttt on c.no_=ttt.stu_id                                                  "+
				"where ttt.school_year='"+schoolYear+"' and ttt.rank_ <=10                                                   "+
				"group by no_,name,ofname,yearnum,c.rank_,c.historyTop ,c.historyNum,ttt.sex_name                            ";
		baseDao.getJdbcTemplate().update(sql);
	}

	@Override
	public void updateBorrowTopQuarter(String schoolYear, QuarterEnum quarter) {
		String delSql="delete TL_BOOK_BORROW_STU_YEAR_TOP where school_year= ? and month_ is null and QUARTER =? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,quarter.getCode()});
		
		String sql="insert into TL_BOOK_BORROW_STU_YEAR_TOP   "+
				"select '"+schoolYear+"' school_year,'' YEAR_,'' month_,'"+quarter.getCode()+"' quarter,                                 "+
				"no_,name,s.sex_name,ofname,d.rank_,0 monthNum,quarterNum,                                                                 "+
				"nvl(sum(s.borrow_num),0) yearnum,d.topNum,d.historyNum ,d.historyTop  from                                            "+
				"(select no_,name,ofname,quarterNum,c.rank_,c.historyTop ,c.historyNum,nvl(count(*),0) topNum from                     "+
				"(select  no_,name,ofname,quarterNum,b.rank_,b.historyTop,nvl(sum(tt.borrow_num),0) historyNum from                    "+
				"(select no_,name,ofname,quarterNum,a.rank_ ,nvl(count(*),0) historyTop  from                                          "+
				"(select stu_id no_,user_name name,dept_name||'-'||major_name||'-'||class_name ofname,quarterNum, rank_  from ( "+
				"select stu_id,user_name,dept_name,major_name,class_name, nvl(sum(borrow_num),0) quarterNum,                           "+
				"dense_rank() over(order by sum(borrow_num) desc) rank_                                                         "+
				"from tl_book_borrow_stu_month                                                                                  "+
				"where school_year='"+schoolYear+"' and month_ in ("+quarter.getValue()+")                                      "+
				"group by stu_id,user_name,dept_name,major_name,class_name ) where rank_<=1 ) a                                 "+
				"left join tl_book_borrow_stu_month t on a.no_=t.stu_id                                                         "+
				"where t.rank_ <=10 group by no_,name,ofname,quarterNum,a.rank_ ) b                                             "+
				"left join tl_book_borrow_stu_month tt on b.no_=tt.stu_id                                                       "+
				"group by no_,name,ofname,quarterNum,b.rank_,b.historyTop ) c                                                   "+
				"left join tl_book_borrow_stu_month ttt on c.no_=ttt.stu_id                                                     "+
				"where ttt.school_year='"+schoolYear+"' and ttt.rank_ <=10                                                      "+
				"group by no_,name,ofname,quarterNum,c.rank_,c.historyTop ,c.historyNum                                         "+
				") d                                                                                                            "+
				"left join tl_book_borrow_stu_month s on d.no_=s.stu_id                                                         "+
				"where s.school_year='"+schoolYear+"'                                                                           "+
				"group by no_,name,ofname,quarterNum,d.rank_,d.historyTop ,d.historyNum,d.topNum,s.sex_name                                ";
		baseDao.getJdbcTemplate().update(sql);
	}

	@Override
	public void updateBorrowTopMonth(String schoolYear, String month) {
		String delSql="delete TL_BOOK_BORROW_STU_YEAR_TOP where school_year= ? and month_ =? and QUARTER is null ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{schoolYear,month});
		
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int year=startYear;
		if(Integer.parseInt(month)<Integer.parseInt(Globals.CUT_YEAR)){
			year=startYear+1;
		}
		
		String sql="insert into TL_BOOK_BORROW_STU_YEAR_TOP   "+
				"select '"+schoolYear+"' school_year,'"+year+"' YEAR_,'"+month+"' month_,'' quarter,                                           "+
				"no_,name,s.sex_name,ofname,d.rank_,monthNum,0 quarterNum ,                                                   	 		  "+
				"nvl(sum(s.borrow_num),0) yearnum,d.topNum,d.historyNum ,d.historyTop  from                                          "+
				"(select no_,name,ofname,monthNum,c.rank_,c.historyTop ,c.historyNum,nvl(count(*),0) topNum from                     "+
				"(select  no_,name,ofname,monthNum,b.rank_,b.historyTop,nvl(sum(tt.borrow_num),0) historyNum from                    "+
				"(select no_,name,ofname,monthNum,a.rank_ ,nvl(count(*),0) historyTop  from                                          "+
				"(select stu_id no_,user_name name,dept_name||'-'||major_name||'-'||class_name ofname,monthNum, rank_  from ( "+
				"select stu_id,user_name,dept_name,major_name,class_name, nvl(sum(borrow_num),0) monthNum,                           "+
				"dense_rank() over(order by sum(borrow_num) desc) rank_                                                       "+
				"from tl_book_borrow_stu_month                                                                                "+
				"where school_year='"+schoolYear+"' and month_='"+month+"'                                                    "+
				"group by stu_id,user_name,dept_name,major_name,class_name ) where rank_<=1 ) a                               "+
				"left join tl_book_borrow_stu_month t on a.no_=t.stu_id                                                       "+
				"where t.rank_ <=10 group by no_,name,ofname,monthNum,a.rank_ ) b                                             "+
				"left join tl_book_borrow_stu_month tt on b.no_=tt.stu_id                                                     "+
				"group by no_,name,ofname,monthNum,b.rank_,b.historyTop ) c                                                   "+
				"left join tl_book_borrow_stu_month ttt on c.no_=ttt.stu_id                                                   "+
				"where ttt.school_year='"+schoolYear+"' and ttt.rank_ <=10                                                    "+
				"group by no_,name,ofname,monthNum,c.rank_,c.historyTop ,c.historyNum) d                                      "+
				"left join tl_book_borrow_stu_month s on d.no_=s.stu_id                                                       "+
				"where s.school_year='"+schoolYear+"'                                                                         "+
				"group by no_,name,ofname,monthNum,d.rank_,d.historyTop ,d.historyNum,d.topNum,s.sex_name                     ";
		baseDao.getJdbcTemplate().update(sql);
	}

	@Override
	public Map<String,Integer> updateBorrowDetil(String date) {
		String tj="";
		if(date != null){
			if("TwoMonth".equals(date)){
				String thisMonth=DateUtils.YEAR.format(new Date())+"-"+DateUtils.MONTH.format(new Date());
				String lastMonth=DateUtils.YM.format(DateUtils.getLastMonth(new Date()));
				tj=" and ( bb.borrow_time like '"+thisMonth+"%' or bb.borrow_time like '"+lastMonth+"%' )";
			}else{
				tj=" and bb.borrow_time like '"+date+"%'";
			}
		}
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_book_borrow_detil bb where 1=1 "+tj;
		int delNum=baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);
		
		String sqlStu="insert into TL_BOOK_BORROW_DETIL                                                                                                                          "+
				"select bb.id borrow_id,bb.book_id,b.name_ book_name,b.store_code,st.name_ store_name,bb.borrow_time,bb.should_return_time,bb.return_time,                         "+
				"        bb.renew_time,bb.renew_count,bb.book_reader_id,br.people_id,br.people_type_code,cci.name_ people_type_name,                                               "+
				"        case when s.no_ is not null then s.name_ else tea.name_ end people_name,                                                                                  "+
				"        case when s.no_ is not null then s.sex_code else tea.sex_code end sex_code,                                                                               "+
				"        case when s.no_ is not null then nvl(tc.name_,'未维护') else nvl(teatc.name_,'未维护') end sex_name,                                                      "+
				"        case when s.no_ is not null then s.dept_id else tea.dept_id end dept_id,                                                                                  "+
				"        case when s.no_ is not null then nvl(c.name_,'未维护') else nvl(teac.name_,'未维护') end dept_name,                                                       "+
				"		s.major_id,nvl(cc.name_,'未维护') major_name,s.class_id,nvl(cl.name_,'未维护') class_name,		                                                          "+
				"        case when s.no_ is not null then s.edu_id else tea.edu_id end edu_id,                                                                                     "+
				"        case when s.no_ is not null then nvl(ce.name_,'未维护') else nvl(teace.name_,'未维护') end edu_name,                                                      "+
				"		case when substr(bb.borrow_time,6,2)<'09' then substr(bb.borrow_time,0,4)- s.enroll_grade else substr(bb.borrow_time,0,4)- s.enroll_grade+1 end Grade_id, "+
				"        case when substr(bb.borrow_time,6,2)<'09' then substr(bb.borrow_time,0,4)- s.enroll_grade||nvl2(s.enroll_grade,'','年级')                                 "+
				"        else substr(bb.borrow_time,0,4)- s.enroll_grade+1||nvl2(s.enroll_grade,'','年级') end Grade_name,                                                         "+
				"        tea.ZYJSZW_ID zc_id,z.name_ zc_name                                                                                                                       "+           
				"        from t_book_borrow bb left join t_book b on bb.book_id=b.no_                                                                                              "+
				"        left join t_code st on b.store_code=st.code_ and st.code_type='BOOK_STORE_CODE'                                                                           "+
				"        left join t_book_reader br on bb.book_reader_id=br.no_                                                                                                    "+
				"        left join t_code_reader_identity cci on br.people_type_code=cci.id                                                                                          "+
				"        left join t_stu s on br.people_id=s.no_                                                                                                                   "+
				"        left join t_code_dept_teach c on s.dept_id =c.id                                                                                                          "+
				"        left join t_code_dept_teach cc on s.major_id=cc.id                                                                                                        "+
				"        left join t_classes cl on s.class_id=cl.no_                                                                                                               "+
				"        left join t_code_education ce on s.edu_id=ce.id                                                                                                           "+
				"        left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'                                                                                    "+
				"        left join t_tea tea on br.people_id=tea.tea_no                                                                                                            "+
				"        left join t_code_dept teac on tea.dept_id =teac.id                                                                                                          "+
				"        left join t_code_education teace on tea.edu_id=teace.id                                                                                                   "+   
				"        left join t_code teatc on tea.sex_code=teatc.code_ and teatc.code_type='SEX_CODE'                                                                         "+
				"        left join T_CODE_ZYJSZW z on  tea.ZYJSZW_ID=z.id                                                                                                          "+
				"where 1=1 "+tj;
		int addNum=baseDao.getJdbcTemplate().update(sqlStu);
		map.put("addNum", addNum);
		return map;
	}

	@Override
	public Map<String, Integer> updateBookStu(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String sql="delete tl_book_stu where year_month=to_date('"+yearMonth+"','YYYY-MM') ";
		int delNum=baseDao.getJdbcTemplate().update(sql);
		map.put("delNum", delNum);
		sql="insert into tl_book_stu "+
			"select to_date('"+yearMonth+"','YYYY-MM') year_month, count(*) nums,S.SEX_CODE SEX_ID,TC.NAME_ SEX_NAME,S.DEPT_ID,CD.NAME_ DEPT_NAME,S.MAJOR_ID, "+
			"CDT.NAME_ MAJOR_NAME,S.CLASS_ID,CL.NAME_ CLASS_NAME,S.EDU_ID,CE.NAME_ EDU_NAME,s.nation_code mz_id,mz.name_ mz_name from t_stu s  "+
			" left join t_code_dept_teach cd on s.dept_id =cd.id  "+
			" left join t_code_dept_teach cdt on s.major_id=cdt.id  "+
			" left join t_code_education ce on s.edu_id=ce.id "+
			" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
			" left join t_code mz on s.nation_code=mz.code_ and mz.code_type='NATION_CODE' "+
			" left join t_classes cl on s.class_id=cl.no_   "+
			"where '"+yearMonth+"' >= substr(s.enroll_date,0,7) and  '"+yearMonth+"'  "+
			"<to_char(add_months(to_date(s.enroll_date,'yyyy-mm-dd'),s.length_schooling*12),'yyyy-mm') "+
			"group by  "+
			"S.SEX_CODE ,TC.NAME_ ,S.DEPT_ID,CD.NAME_ ,S.MAJOR_ID, "+
			"CDT.NAME_ ,S.CLASS_ID,CL.NAME_ ,S.EDU_ID,CE.NAME_ ,s.nation_code ,mz.name_ ";
		int addNum=baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);
		return map;
	}

	@Override
	public Map<String, Integer> updateBookMonth(String yearMonth) {
		String lastMonth="";
		try {
			lastMonth=DateUtils.YM.format(DateUtils.getLastMonth(DateUtils.YM.parse(yearMonth)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String,Integer> map =new HashMap<String, Integer>();
		String sql="delete TL_BOOK_MONTH where year_month=to_date('"+yearMonth+"','YYYY-MM') ";
		int delNum=baseDao.getJdbcTemplate().update(sql);
		map.put("delNum", delNum);
		sql="insert into TL_BOOK_MONTH "+
			"select to_date('"+yearMonth+"','YYYY-MM') year_month,store_id,sum(upbook) books, "+
			"sum(upbook)*2-sum(books) upbooks,sum(upmoney) moneys,sum(upmoney)*2-sum(moneys) upmoneys from ( "+
			"select store_code store_id,nvl(count(*),0) books,nvl(sum(b.price),0) moneys, "+
			"nvl(count(*),0) upbook,nvl(sum(b.price),0) upmoney  from t_book b  "+
			"where b.store_date <'"+yearMonth+"' group by store_code  "+
			"union all "+
			"SELECT store_id,books,moneys,0 upbook,0 upmoney FROM TL_BOOK_MONTH WHERE year_month=to_date('"+lastMonth+"','YYYY-MM')  "+
			") group by store_id";
		int addNum=baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateBorrowStu(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String sql="delete TL_BOOK_BORROW_STU where BORROW_DATE=to_date('"+yearMonth+"','YYYY-MM') ";
		int delNum=baseDao.getJdbcTemplate().update(sql);
		map.put("delNum", delNum);
		sql="INSERT INTO TL_BOOK_BORROW_STU                                                                                      	 "+
				"select TO_DATE(t.BORROW_DATE,'YYYY-MM') BORROW_DATE,TO_DATE(t.RETURN_DATE,'YYYY-MM') RETURN_DATE, "+
				"t.borrow_hours,t.borrow_num,t.outtime_num,t.outtime_day,          "+
				"t.renew_num,t.store_code store_id,bs.name_ store_name,                                                              "+
				"S.NO_ STU_ID,S.NAME_ STU_NAME,S.SEX_CODE SEX_ID,TC.NAME_ SEX_NAME,S.DEPT_ID,CD.NAME_ DEPT_NAME,S.MAJOR_ID,          "+
				"CDT.NAME_ MAJOR_NAME,S.CLASS_ID,CL.NAME_ CLASS_NAME,S.EDU_ID,CE.NAME_ EDU_NAME,s.nation_code mz_id,mz.name_ mz_name "+
				"from (                                                                                                              "+
				"select '"+yearMonth+"' BORROW_DATE,substr(bb.Return_Time,0,7) RETURN_DATE,substr(bb.borrow_time,12,2) borrow_hours,                              "+
				"count(*) borrow_num,                                                                                                "+
				"count(case when bb.return_time>bb.borrow_time then 1  else null end ) outtime_num,                                  "+
				"round(avg(to_date(bb.return_time,'yyyy-mm-dd hh24:mi:ss')-to_date(bb.should_return_time,'yyyy-mm-dd hh24:mi:ss')),0 ) outtime_day, "+
				"nvl(sum(bb.renew_count),0) renew_num,                                                                               "+
				"s.no_ stu_id,b.store_code                                                                                           "+
				"  from t_stu s                                                                                                      "+
				"  inner join t_book_reader br on s.no_ = br.people_id                                                               "+
				"  inner join t_book_borrow bb on br.no_ = bb.book_reader_id                                                         "+
				"  left join t_book b on bb.book_id = b.no_                                                                          "+
				"  where substr(bb.borrow_time,0,7)='"+yearMonth+"' "+
				"  group by substr(bb.Return_Time,0,7),substr(bb.borrow_time,12,2), 		                                         "+
				"  s.no_,b.store_code                                                                                                "+
				") t                                                                                                                 "+
				" left join t_stu s on t.stu_id=s.no_                                                                                "+
				" left join t_code_dept_teach cd on s.dept_id =cd.id                                                                 "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id                                                               "+
				" left join t_code_education ce on s.edu_id=ce.id                                                                    "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'                                             "+
				" left join t_code mz on s.nation_code=mz.code_ and mz.code_type='NATION_CODE'                                       "+
				" left join t_classes cl on s.class_id=cl.no_                                                                        "+
				" left join t_code bs on ( t.store_code=bs.code_ and bs.code_type='BOOK_STORE_CODE' )                                ";
		int addNum=baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);
		return map;
	}
	@Override
	public void createTemp(){
		//TODO 此处为临时结果表
		String drop="drop table temp_tl_book_borrow_trend ";
		try {
			baseDao.getJdbcTemplate().execute(drop);
		} catch (Exception e) {
		}
		String sql2="create table temp_tl_book_borrow_trend as                                                                               "+
		"select b.year_month,b.dept_id,b.major_id,b.class_id,nvl(a.borrow_num,0) borrow_num,nvl(a.renew_num,0) renew_num,     "+
		"nvl(C.outtime_num,0) outtime_num,nvl(C.outtime_day,0) outtime_day,nvl(a.stu_num,0) borrow_stu,                       "+
		"nvl(C.stu_num,0) OUTTIME_stu,b.nums all_stu,b.code,b.name from                                                       "+
		"(select t.year_month,t.dept_id,t.major_id,t.class_id,sum(nums) nums,t.edu_id code,t.edu_name name from tl_book_stu t "+
		"where 1=1 group by t.year_month,t.dept_id,t.major_id,t.class_id,t.edu_id,t.edu_name) b                               "+
		"left join                                                                                                            "+
		"(select t.BORROW_DATE,t.dept_id,t.major_id,t.class_id,sum(t.borrow_num) borrow_num,sum(t.renew_num) renew_num,       "+
		"sum(t.outtime_num) outtime_num,round(avg(t.outtime_day)) outtime_day,                                                "+
		"count(distinct t.stu_id) stu_num,t.edu_id code,t.edu_name name from TL_BOOK_BORROW_STU t                             "+
		"where 1=1 group by t.BORROW_DATE,t.dept_id,t.major_id,t.class_id,t.edu_id,t.edu_name ) a                             "+
		"on a.code=b.code and a.BORROW_DATE=b.year_month and a.class_id=b.class_id                                            "+
		"left join                                                                                                            "+
		"(select t.RETURN_DATE,t.dept_id,t.major_id,t.class_id,                                                               "+
		"sum(t.outtime_num) outtime_num,round(avg(t.outtime_day)) outtime_day,                                                "+
		"count(distinct t.stu_id) stu_num,t.edu_id code,t.edu_name name from TL_BOOK_BORROW_STU t                             "+
		"where 1=1 group by t.RETURN_DATE,t.dept_id,t.major_id,t.class_id,t.edu_id,t.edu_name ) C                             "+
		"on C.code=b.code and C.RETURN_DATE=b.year_month and C.class_id=b.class_id                                            "+
		"order by year_month                                                                                                  ";
		try {
			baseDao.getJdbcTemplate().execute(sql2);
		} catch (Exception e) {
		}
	}
	
}
