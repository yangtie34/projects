package cn.gilight.product.book.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.book.dao.BookInfoDao;

@Repository("bookInfoDao")
public class BookInfoDaoImpl implements BookInfoDao {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getBooks(String schoolYear) {
		String readerSql="SELECT SUM(PEOPLE_NUM) PEOS, SCHOOL_YEAR "+
					 "FROM TL_BOOK_READER_YEAR GROUP BY SCHOOL_YEAR";
		if(schoolYear.equals(EduUtils.getSchoolYearTerm(new Date())[0])){
			readerSql="SELECT nvl(COUNT(*),0) PEOS,'"+schoolYear+"' SCHOOL_YEAR FROM T_BOOK_READER WHERE ISTRUE=1 ";
		}
		
		String sql="SELECT A.BOOKS NOWBOOKS,A.MONEYS NOWMONEYS,A.UPBOOKS NEWBOOKS, "+
					 "A.UPMONEYS NEWMONEYS,B.PEOS NOWREADERS, ROUND(A.BOOKS/B.PEOS) PEOPLEHAS "+
					 "FROM TL_BOOK_YEAR A "+
					 "LEFT JOIN ("+readerSql+") B "+
					 "ON A.SCHOOL_YEAR = B.SCHOOL_YEAR "+
					 "WHERE A.SCHOOL_YEAR = ? ";
		return baseDao.getJdbcTemplate().queryForMap(sql,new Object[]{schoolYear});
	}

	@Override
	public List<Map<String,Object>> getReaders(String schoolYear) {
		String sql="";
		if(schoolYear.equals(EduUtils.getSchoolYearTerm(new Date())[0])){
			sql="select ? school_year,count(*) VALUE,nvl(r.people_type_code,'') CODE ,nvl(c.name_,'未维护') NAME from t_book_reader r "+
					"left join t_code_reader_identity c on r.people_type_code=c.id "+
					"where r.istrue=1  group by r.people_type_code ,c.name_ order by code";
		}else{
			sql="SELECT A.PEOPLE_NUM VALUE,B.ID CODE,B.NAME_ NAME "+
				 	"FROM TL_BOOK_READER_YEAR A "+
					"LEFT JOIN t_code_reader_identity B ON A.people_type_code=B.ID "+
					"WHERE A.SCHOOL_YEAR = ? ORDER BY CODE";
		}
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{schoolYear});
	}

	@Override
	public List<Map<String,Object>> getBookLangu(String schoolYear) {
		String sql=" select bty.book_language_code code,nvl(sum(bty.book_num),0) value,c.name_ name "+
					"from TL_BOOK_TYPE_YEAR bty  "+
					"left join t_code c on ( bty.book_language_code=c.code_ and c.code_type='BOOK_LANGUAGE_CODE' ) "+
					"where bty.school_year= ? group by bty.book_language_code ,c.name_ order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{schoolYear});
	}
	@Override
	public List<Map<String,Object>> getBookState(String schoolYear) {
		String sql=" select T.STATE_CODE code,T.BOOKS value,T.STATE_NAME name "+
					"from TL_BOOK_STATE_YEAR T  "+
					"where T.school_year= ?";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{schoolYear});
	}
	@Override
	public List<Map<String,Object>> getBookByType(String schoolYear) {
		
		String readerSql="SELECT SUM(PEOPLE_NUM) PEOS, SCHOOL_YEAR "+
				 "FROM TL_BOOK_READER_YEAR GROUP BY SCHOOL_YEAR";
		if(schoolYear.equals(EduUtils.getSchoolYearTerm(new Date())[0])){
			readerSql="SELECT nvl(COUNT(*),0) PEOS,'"+schoolYear+"' SCHOOL_YEAR FROM T_BOOK_READER WHERE ISTRUE=1 ";
		}
		
		String sql="select bty.book_store_code code,nvl(avg(bty.book_num),0) BOOKS,nvl(avg(bty.BOOK_MONEY),0) moneys, "+
					"round(nvl(avg(bty.book_num)/avg(r.PEOS),0)*100,2) PEOPLEHASRATE,c.name_ name "+
					"from TL_BOOK_TYPE_YEAR bty "+
					"left join t_code c on ( bty.book_store_code=c.code_ and c.code_type='BOOK_STORE_CODE' ) "+
					" left join ("+readerSql+") r on bty.school_year=r.school_year "+
					"where bty.school_year= ? group by bty.book_store_code ,c.name_ order by BOOKS desc";
		return baseDao.getJdbcTemplate().queryForList(sql,new Object[]{schoolYear});
	}

	@Override
	public List<Map<String, Object>> getBooksTrend() {
		String sql="SELECT SCHOOL_YEAR SCHOOLYEAR,ROUND(BOOKS/10000,2) BOOKS,ROUND(UPBOOKS/10000,2) UPBOOKS, "+
					"ROUND(MONEYS/10000,2) MONEYS,ROUND(UPMONEYS/10000,2) UPMONEYS FROM TL_BOOK_YEAR ORDER BY SCHOOL_YEAR";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getReadersTrend() {
		String sql="SELECT A.PEOPLE_NUM VALUE,B.ID CODE,B.NAME_ NAME ,A.SCHOOL_YEAR SCHOOLYEAR FROM TL_BOOK_READER_YEAR A "+
					"LEFT JOIN t_code_reader_identity B ON A.people_type_code=B.ID ORDER BY A.SCHOOL_YEAR,CODE";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBookLanguTrend() {
		String sql="SELECT BTY.BOOK_LANGUAGE_CODE CODE,nvl(SUM(BTY.BOOK_NUM),0) VALUE,C.NAME_ NAME,BTY.SCHOOL_YEAR SCHOOLYEAR "+
					"FROM TL_BOOK_TYPE_YEAR BTY "+
					"LEFT JOIN T_CODE C ON ( BTY.BOOK_LANGUAGE_CODE=C.CODE_ AND C.CODE_TYPE='BOOK_LANGUAGE_CODE' ) "+
					"GROUP BY BTY.SCHOOL_YEAR, BTY.BOOK_LANGUAGE_CODE ,C.NAME_ ORDER BY BTY.SCHOOL_YEAR,CODE ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> getBookStateTrend() {
		String sql="SELECT t.state_code CODE,t.books VALUE,t.state_name NAME,t.SCHOOL_YEAR SCHOOLYEAR "+
					"FROM TL_BOOK_State_YEAR t "+
					"ORDER BY t.SCHOOL_YEAR,CODE ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
