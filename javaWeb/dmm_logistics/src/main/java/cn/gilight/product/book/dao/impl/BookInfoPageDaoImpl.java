package cn.gilight.product.book.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.book.dao.BookInfoPageDao;


/**
 * 图书馆统计分析
 *
 */
@Repository("bookInfoPageDao")
public class BookInfoPageDaoImpl implements BookInfoPageDao{
	@Autowired
	private BaseDao baseDao;

	final String bookSql="select B.*,SC.NAME_ STATE_NAME,ST.NAME_ STORE_NAME from t_book b "+
					"left join t_code sc on b.state_code=sc.code_ and sc.code_type='BOOK_STATE_CODE' "+
					"left join t_code st on b.store_code=st.code_ and st.code_type='BOOK_STORE_CODE' where 1=1 ";
	final String bookOrderSql=" order by b.store_date desc ";
	final String readerSql="select * from tl_book_reader_detil_year where 1=1 ";
	final String readerOrderSql=" order by add_time desc ";
	
	
	@Override
	public Page getAllBook(int currentPage ,int numPerPage,int totalRow) {
		return new Page(bookSql+bookOrderSql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getReaderBySchoolYear(int currentPage ,int numPerPage,int totalRow,String schoolYear) {
		String sql=readerSql+" and school_year='"+schoolYear+"' "+readerOrderSql;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBookBySchoolYear(int currentPage ,int numPerPage,int totalRow,String schoolYear) {
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String sql=bookSql+"and b.store_date <'"+endYear+"-09' "+bookOrderSql;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
	@Override
	public Page getUpBookBySchoolYear(int currentPage ,int numPerPage,int totalRow,String schoolYear) {
		int startYear=Integer.parseInt(schoolYear.substring(0, 4));
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String sql=bookSql+"and b.store_date <'"+endYear+"-09' and b.store_date>='"+startYear+"-09' "+bookOrderSql;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}


	@Override
	public Page getBookByLanguage(int currentPage ,int numPerPage,int totalRow,boolean isCN,String schoolYear) {
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String where ="";
		if(isCN){
			where="and b.store_code<> '00013' and b.store_date <'"+endYear+"-09' ";
		}else{
			where="and b.store_code= '00013' and b.store_date <'"+endYear+"-09' ";
		}
		return new Page(bookSql+where+bookOrderSql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	@Override
	public Page getBookByState(int currentPage ,int numPerPage,int totalRow,String state,String schoolYear) {
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String where ="";
	
			where="and b.state_code='"+state+"' and b.store_date <'"+endYear+"-09' ";
		
		return new Page(bookSql+where+bookOrderSql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getReaderByType(int currentPage ,int numPerPage,int totalRow,String type,String schoolYear) {
		String sql=readerSql+" and school_year='"+schoolYear+"' and people_type_code='"+type+"'"+readerOrderSql;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getBookByStore(int currentPage ,int numPerPage,int totalRow,String store,String schoolYear) {
		int endYear=Integer.parseInt(schoolYear.substring(0, 4))+1;
		String where="and b.store_code= '"+store+"' and b.store_date <'"+endYear+"-09' ";
		return new Page(bookSql+where+bookOrderSql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
