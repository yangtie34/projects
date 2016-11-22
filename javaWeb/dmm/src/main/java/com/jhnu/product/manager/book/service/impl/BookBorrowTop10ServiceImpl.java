package com.jhnu.product.manager.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.book.dao.BookBorrowTop10Dao;
import com.jhnu.product.manager.book.service.BookBorrowTop10Service;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.StringUtils;

@Service("bookBorrowTop10Service")
public class BookBorrowTop10ServiceImpl implements BookBorrowTop10Service{
	
	@Autowired
	private BookBorrowTop10Dao bookBorrowTop10Dao;

	@Override
	public List<Map<String, Object>> getBookBorrowTop10(String startDate,String endDate, String dept_id,boolean isLeaf) {
		if(!StringUtils.hasText(startDate)){
			startDate = DateUtils.getLastMonth();
		}
		if(!StringUtils.hasText(endDate)){
			endDate = DateUtils.getNowDate();
		}
		
		return bookBorrowTop10Dao.getBookBorrowTop10(startDate, endDate, dept_id,isLeaf);
	}

	@Override
	public List<Map<String, Object>> getBookBorrowStuTop10(String startDate,String endDate, String dept_id,boolean isLeaf) {
		if(!StringUtils.hasText(startDate)){
			startDate = DateUtils.getLastMonth();
		}
		if(!StringUtils.hasText(endDate)){
			endDate = DateUtils.getNowDate();
		}
		
		return bookBorrowTop10Dao.getBookBorrowStuTop10(startDate, endDate, dept_id,isLeaf);
	}

	@Override
	public List<Map<String, Object>> getPeopleByBook(String deptId,boolean isLeaf,String bookId,
			String startDate,String endDate) {
		return bookBorrowTop10Dao.getPeopleByBook(deptId,isLeaf,bookId,startDate,endDate);
	}
	
	@Override
	public List<Map<String, Object>> getBookByStu(String stuId,String startDate,String endDate) {
		return bookBorrowTop10Dao.getBookByStu(stuId,startDate,endDate);
	}
	
	

}
