package com.jhnu.product.four.book.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.book.dao.FourBookDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;


@Repository("fourBookDao")
public class FourBookDaoImpl implements FourBookDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String,Object>> getFirstBorrow() {
		String sql="select b.borrow_time,bo.name_ book_name,nvl(r.no_,0) read_no,s.no_ stu_no,s.name_ stu_name "+
					"from t_book_borrow b "+
					"inner join (select min(b.borrow_time) tim,r.no_ rno from t_book_borrow b "+
					            "inner join t_book bo on b.book_id=bo.no_  "+
					            "inner join  t_book_reader r on b.book_reader_id=r.no_ where r.people_type_code='01' "+
					"group by r.no_) a on (b.borrow_time=a.tim and b.book_reader_id=rno) "+
					"inner join t_book_reader r on b.book_reader_id=r.no_ "+
					"inner join t_book bo on b.book_id=bo.no_ "+
					"inner join t_stu s on r.people_id=s.no_ "+
					"order by b.borrow_time";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public List<Map<String, Object>> getFirstRKE() {
		String sql="select a.tim,nvl(r.no_,0) rno,s.no_ sno   "+
			        "from (select min(b.time_) tim,r.no_ rno from t_book_rke b  "+
			        "inner join  t_book_reader r on b.book_reader_id=r.no_ where r.people_type_code='01'  "+
			        "group by r.no_) a  "+
			        "inner join t_book_reader r on a.rno=r.no_  "+
			        "inner join t_stu s on r.people_id=s.no_  ";
		return baseDao.getBaseDao().querySqlList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getAllBorrow() {
		String sql="select e.*,rownum r from ( "+
					"select nvl(t.sums,0) sums,nvl(r.no_,0) rno,s.no_ sno,s.name_ ,(s.enroll_grade+s.length_schooling) byn from  "+
					"(select count(*) sums ,b.book_reader_id from t_book_borrow b  "+
					"  group by b.book_reader_id ) t  "+
					"  right join t_book_reader r on t.book_reader_id=r.no_  "+
					"  right join t_stu s on r.people_id=s.no_ "+
					"  where s.stu_state_code='1' "+
					"  order by byn,sums ) e";
		return baseDao.getBaseDao().querySqlList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getAllBorrowAC() {
		String sql="select avg(sums) av ,nvl(count(*),0) cou ,byn from "+
				"(select nvl(t.sums,0) sums,r.no_ rno,s.no_ sno,s.name_,(s.enroll_grade+s.length_schooling) byn from "+
				"(select count(*) sums ,b.book_reader_id from t_book_borrow b  "+
				"  group by b.book_reader_id ) t  "+
				"  right join t_book_reader r on t.book_reader_id=r.no_  "+
				"  right join t_stu s on r.people_id=s.no_ "+
				"  where s.stu_state_code='1' ) group by byn order by byn";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public List<Map<String, Object>> getAllRKE() {
		String sql="select e.*,rownum r from( "+
					"select nvl(t.sums,0) sums,nvl(r.no_,0) rno,s.no_ sno,s.name_,(s.enroll_grade+s.length_schooling) byn from  "+
					"(select count(*) sums ,b.book_reader_id from t_book_rke b  "+
					"  group by b.book_reader_id ) t   "+
					"  right join t_book_reader r on t.book_reader_id=r.no_  "+
					"  right join t_stu s on r.people_id=s.no_ "+
					"  where s.stu_state_code='1' "+
					"  order by byn,sums ) e";
		return baseDao.getBaseDao().querySqlList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getAllRKEAC() {
		String sql="select avg(sums) av ,nvl(count(*),0) cou ,byn from "+
				"(select nvl(t.sums,0) sums,r.no_ rno,s.no_ sno,s.name_,(s.Enroll_Grade+s.LENGTH_SCHOOLING) byn from "+
				"(select count(*) sums ,b.book_reader_id from t_book_borrow b  "+
				"  group by b.book_reader_id ) t  "+
				"  right join t_book_reader r on t.book_reader_id=r.no_  "+
				"  right join t_stu s on r.people_id=s.no_ "+
				"  where s.stu_state_code='1' ) group by byn order by byn";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveFirstBorrowLog(List<Map<String,Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_book_first_borrow";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_book_first_borrow values (?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i), "borrow_time"));    
		                ps.setString(2, MapUtils.getString(LIST.get(i), "book_name"));    
		                ps.setString(3, MapUtils.getString(LIST.get(i), "read_no"));    
		                ps.setString(4, MapUtils.getString(LIST.get(i), "stu_no"));    
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public void saveFirstRKELog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_book_first_rke";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_book_first_rke values (?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"tim"));    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"rno"));    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"sno"));    
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public void saveAllBorrowLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_book_borrow";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_book_borrow values (?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"sums"));    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"av"));    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"more_than"));    
		                ps.setString(4, MapUtils.getString(LIST.get(i),"rno"));    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"sno"));    
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public void saveAllRKELog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_book_rke";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_book_rke values (?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"sums"));    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"av"));    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"more_than"));    
		                ps.setString(4, MapUtils.getString(LIST.get(i),"rno"));    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"sno"));    
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getFirstBorrowLog(String id) {
		String sql="select * from tl_book_first_borrow where stu_no= ? ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> getFirstRKELog(String id) {
		String sql="select * from tl_book_first_rke where sno= ? ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> getAllBorrowLog(String id) {
		String sql="select * from tl_book_borrow where sno= ?　";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> getAllRKELog(String id) {
		String sql="select * from tl_book_rke where sno=　? ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> getLikeRKETime() {
		String sql="select nvl(t2.a,0) maxrke,nvl(t2.book_reader_id,0) rno,nvl(t2.aa,0) time_ ,s.no_ sno,s.name_ sname "+
					"from (select max(a) m,t.book_reader_id from ( "+
					"select count(*) a ,br.book_reader_id ,substr(br.time_,12,2) aa from t_book_rke br  "+
					"  group by br.book_reader_id,substr(br.time_,12,2) ) t  "+
					"  group by t.book_reader_id ) t1 inner join ( "+
					"select count(*) a ,br.book_reader_id ,substr(br.time_,12,2) aa from t_book_rke br  "+
					"  group by br.book_reader_id,substr(br.time_,12,2) ) t2 on (t1.m=t2.a and t1.book_reader_id=t2.book_reader_id) "+
					"  right join t_book_reader bo on t2.book_reader_id=bo.no_  "+
					"  right join t_stu s on bo.people_id=s.no_  "+
					"  where s.stu_state_code='1' ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveLikeRKETimeLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_book_like_rke";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_book_like_rke values (?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"maxrke"));    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"time_"));    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"rno"));  
		                ps.setString(4, MapUtils.getString(LIST.get(i),"sno"));    
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getLikeRKETimeLog(String id) {
		String sql="select * from tl_book_like_rke where sno=? ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}
	

	@Override
	public List<Map<String, Object>> getBookBorrowCount() {
		String sql = "SELECT SCHOOL_YEAR,TERM_CODE,PEOPLE_ID,MAJOR_ID,SEX_CODE,ENROLL_GRADE,COUNT(BOOK_ID) COUNT_BORROW FROM ( "
				+ " SELECT CASE WHEN SUBSTR(BORROW_TIME,6,2)>='07' THEN SUBSTR(BORROW_TIME,1,4)||'-'||(TO_NUMBER(SUBSTR(BORROW_TIME,1,4))+1)  "
				+ " ELSE (TO_NUMBER(SUBSTR(BORROW_TIME,1,4))-1)||'-'||SUBSTR(BORROW_TIME,1,4)  END SCHOOL_YEAR,"
				+ " CASE WHEN SUBSTR(BORROW_TIME,6,2)>='07' THEN '01' ELSE '02' END TERM_CODE,"
				+ " BO.BORROW_TIME,BO.BOOK_ID,REA.NO_ READER_NO,REA.PEOPLE_ID,STU.NAME_,STU.SEX_CODE,STU.MAJOR_ID,STU.ENROLL_GRADE "
				+ " FROM T_BOOK_BORROW BO INNER JOIN T_BOOK_READER REA ON REA.NO_ = BO.BOOK_READER_ID "
				+ " INNER JOIN T_STU STU ON STU.NO_ = REA.PEOPLE_ID AND STU.STU_STATE_CODE=1"
				+ " )GROUP BY SCHOOL_YEAR,TERM_CODE,PEOPLE_ID,MAJOR_ID,SEX_CODE,ENROLL_GRADE";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public void saveBookBorrowCountLog(List<Map<String,Object>> list) {
		final List<Map<String, Object>> saveList = list;
		String delSql ="DELETE FROM TL_BOOK_STU_BORROW";
		baseDao.getLogDao().getJdbcTemplate().execute(delSql);
		String sql ="INSERT INTO TL_BOOK_STU_BORROW VALUES(?,?,?,?,?,?,?)";
		
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, MapUtils.getString(saveList.get(i),"SCHOOL_YEAR").toString());
				ps.setString(2, MapUtils.getString(saveList.get(i),"TERM_CODE").toString());
				ps.setString(3, MapUtils.getString(saveList.get(i),"PEOPLE_ID").toString());
				ps.setString(4, MapUtils.getString(saveList.get(i),"MAJOR_ID").toString());
				ps.setString(5, MapUtils.getString(saveList.get(i),"SEX_CODE").toString());
				ps.setString(6, MapUtils.getString(saveList.get(i),"COUNT_BORROW")==null?"":MapUtils.getString(saveList.get(i),"COUNT_BORROW").toString());
				ps.setString(7, MapUtils.getString(saveList.get(i),"ENROLL_GRADE").toString());
			}

			@Override
			public int getBatchSize() {
				return saveList.size();
			}
			
		});
	}
	
	@Override
	public List<Map<String, Object>> getBookBorrowStuLine(String id) {
		String sql ="SELECT SCHOOL_YEAR,TERM_CODE,COUNT_BORROW,ENROLL_GRADE FROM TL_BOOK_STU_BORROW WHERE STU_ID = ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}
	
	@Override
	public List<Map<String, Object>> getBookBoAvgGradeLine(String enrollGrade) {
		String sql ="SELECT SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE,ROUND(AVG(COUNT_BORROW),2) AVG_ FROM TL_BOOK_STU_BORROW"
				+ " WHERE ENROLL_GRADE=? GROUP BY SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{enrollGrade});
	}
	
	
	@Override
	public List<Map<String, Object>> getBookRKECount() {
		String sql = "SELECT SCHOOL_YEAR,TERM_CODE,PEOPLE_ID,MAJOR_ID,SEX_CODE,ENROLL_GRADE,"
				+ " COUNT(TIME_) COUNT_RKE FROM (SELECT CASE WHEN SUBSTR(RKE.TIME_,6,2)>='07' "
				+ " THEN SUBSTR(RKE.TIME_,1,4)||'-'||(TO_NUMBER(SUBSTR(RKE.TIME_,1,4))+1)  "
				+ " ELSE (TO_NUMBER(SUBSTR(RKE.TIME_,1,4))-1)||'-'||SUBSTR(RKE.TIME_,1,4)  END SCHOOL_YEAR,"
				+ " CASE WHEN SUBSTR(RKE.TIME_,6,2)>='07' THEN '01' ELSE '02' END TERM_CODE,"
				+ " RKE.TIME_,REA.NO_ READER_NO,REA.PEOPLE_ID,STU.NAME_,STU.SEX_CODE,STU.MAJOR_ID ,STU.ENROLL_GRADE"
				+ " FROM T_BOOK_RKE RKE INNER JOIN T_BOOK_READER REA ON REA.NO_ = RKE.BOOK_READER_ID "
				+ " INNER JOIN T_STU STU ON STU.NO_ = REA.PEOPLE_ID AND STU.STU_STATE_CODE=1"
				+ " )GROUP BY SCHOOL_YEAR,TERM_CODE,PEOPLE_ID,MAJOR_ID,SEX_CODE,ENROLL_GRADE";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public void saveBookRKECountLog(List<Map<String,Object>> list) {
		final List<Map<String, Object>> saveList = list;
		String delSql ="DELETE FROM TL_BOOK_STU_RKE";
		baseDao.getLogDao().getJdbcTemplate().execute(delSql);
		String sql ="INSERT INTO TL_BOOK_STU_RKE VALUES(?,?,?,?,?,?,?)";
		
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, MapUtils.getString(saveList.get(i),"SCHOOL_YEAR").toString());
				ps.setString(2, MapUtils.getString(saveList.get(i),"TERM_CODE").toString());
				ps.setString(3, MapUtils.getString(saveList.get(i),"PEOPLE_ID").toString());
				ps.setString(4, MapUtils.getString(saveList.get(i),"MAJOR_ID").toString());
				ps.setString(5, MapUtils.getString(saveList.get(i),"SEX_CODE").toString());
				ps.setString(6, MapUtils.getString(saveList.get(i),"ENROLL_GRADE").toString());
				ps.setString(7, MapUtils.getString(saveList.get(i),"COUNT_RKE")==null?"":MapUtils.getString(saveList.get(i),"COUNT_RKE").toString());
				
			}

			@Override
			public int getBatchSize() {
				return saveList.size();
			}
			
		});
	}
	
	@Override
	public List<Map<String, Object>> getBookRKEStuLine(String id) {
		String sql ="SELECT PEOPLE_ID,COUNT_RKE,ENROLL_GRADE,SCHOOL_YEAR,TERM_CODE FROM TL_BOOK_STU_RKE WHERE PEOPLE_ID = ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}
	
	@Override
	public List<Map<String, Object>> getBookRKEAvgGradeLine(String enrollGrade) {
		String sql ="SELECT SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE,ROUND(AVG(COUNT_RKE),2) AVG_ FROM TL_BOOK_STU_RKE"
				+ " WHERE ENROLL_GRADE=? GROUP BY SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{enrollGrade});
	}
	
	public Page getBookDetailLog(int currentPage,int numPerPage, String tj) {
		String sql="select * from tl_book_detail where 1=1 "+ tj;
		Page page = new Page(sql, currentPage, numPerPage, baseDao.getLogDao().getJdbcTemplate(),null);
		return page;
	}
	
	public List<Map<String, Object>> getBookDetailGroupByTime(String id) {
		String sql="select substr(t.borrow_time_,0,4) ||'年' mc,substr(t.borrow_time_,0,4) id  from tl_book_detail t where 1=1 and stu_id ='"+id+"' group by substr(t.borrow_time_,0,4)  order by id";
		return baseDao.getLogDao().queryListMapInLowerKeyBySql(sql);
	}
	
	public List<Map<String, Object>> getBookDetailGroupByDeal(String id) {
		String sql="select t.store_code id ,t.store_name mc  from tl_book_detail t where stu_id ='"+id+"' group by t.store_code,t.store_name  order by id";
		return baseDao.getLogDao().queryListMapInLowerKeyBySql(sql);
	}
}
