package com.jhnu.product.manager.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.book.dao.BookBorrowTop10Dao;
import com.jhnu.util.common.StringUtils;

@Repository("bookBorrowTop10Dao")
public class BookBorrowTop10DaoImpl implements BookBorrowTop10Dao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getBookBorrowTop10(String startDate,String endDate, String dept_id,boolean isLeaf) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (stu.dept_id = '"+ dept_id +"' or stu.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and stu.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and stu.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select * from ("
				+ " select t.*,rownum rm from ("
				+ " select book.no_ book_id,book.name_ book_name,count(*) sums from t_book_borrow bb "
				+ " inner join t_book book on book.no_ = bb.book_id  "
				+ " left join t_stu stu on stu.no_ = bb.book_reader_id"
				+ " left join t_code_dept_teach th on th.id = stu.dept_id"
				+ " left join t_code_dept_teach t on t.id = stu.major_id"
				+ " where to_date(bb.borrow_time,'yyyy-mm-dd hh24:mi:ss') between to_date('"+ startDate +"','yyyy-mm-dd') and to_date('"+ endDate +"','yyyy-mm-dd')"
				+ sql2 + " group by book.no_,book.name_ order by sums desc) t) where rm>0 and rm<=10";
		
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map<String,Object>> getBookBorrowStuTop10(String startDate,String endDate,String dept_id,boolean isLeaf){
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (stu.dept_id = '"+ dept_id +"' or stu.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and stu.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and stu.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select * from ("
				+ " select d.*,rownum rm from ("
				+ " select t.stu_id,s.name_ stu_name,t.sums from ("
				+ " select bb.book_reader_id stu_id,count(*) sums from t_book_borrow bb"
				+ " inner join t_stu stu on stu.no_ = bb.book_reader_id"
				+ " left join t_code_dept_teach th on th.id = stu.dept_id"
				+ " left join t_code_dept_teach teach on teach.id = stu.major_id"
				+ " where to_date(bb.borrow_time,'yyyy-mm-dd hh24:mi:ss') between to_date('"+ startDate +"','yyyy-mm-dd') and to_date('"+ endDate +"','yyyy-mm-dd')"
				+ sql2 + " group by bb.book_reader_id ) t inner join t_stu s on s.no_ = t.stu_id order by t.sums desc) d) where rm>0 and rm<=10";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPeopleByBook(String dept_id,boolean isLeaf,String bookId,String startDate, String endDate) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (stu.dept_id = '"+ dept_id +"' or stu.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and stu.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and stu.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select stu.no_ stu_id, stu.name_ stu_name,dt.name_ dept_name,cd.name_ major_name,bb.borrow_time from t_book_borrow bb"
				+ " left join t_book_reader r on r.no_ = bb.book_reader_id"
				+ " left join t_stu stu on stu.no_ = r.people_id"
				+ " left join t_code_dept_teach dt on dt.id = stu.dept_id"
				+ " left join t_code_dept_teach cd on cd.id = stu.major_id"
				+ " where book_id = '"+ bookId +"' and borrow_time between '"+ startDate +"' and '"+ endDate +"' " + sql2 ;
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBookByStu(String stuId,
			String startDate, String endDate) {
		
		String sql = "select t.*,rownum rm from ("
				+ " select b.name_ book_name,bb.borrow_time,bb.should_return_time from t_book_borrow bb "
				+ " left join t_book b on b.no_ = bb.book_id"
				+ " left join t_book_reader br on br.no_ = bb.book_reader_id"
				+ " left join t_stu stu on stu.no_ = br.people_id"
				+ " where stu.no_ = '"+ stuId +"' and bb.borrow_time between '"+ startDate +"' and '"+ endDate +"'"
				+ " order by borrow_time desc) t";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

}
