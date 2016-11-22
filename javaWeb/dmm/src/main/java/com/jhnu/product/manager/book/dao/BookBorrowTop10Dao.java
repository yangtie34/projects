package com.jhnu.product.manager.book.dao;

import java.util.List;
import java.util.Map;

public interface BookBorrowTop10Dao {
	
	/**
	 * 获取某段时间的图书借阅Top10
	 * @param startDate
	 * @param endDate
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getBookBorrowTop10(String startDate,String endDate,String dept_id,boolean isLeaf);
	
	/**
	 * 获取某段时间的借书学生TOP10
	 * @param startDate
	 * @param endDate
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getBookBorrowStuTop10(String startDate,String endDate,String dept_id,boolean isLeaf);

	/**
	 * 根据图书Id获取某段时间内的借阅人
	 * @param bookId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getPeopleByBook(String deptId,boolean isLeaf,String bookId,String startDate, String endDate);

	/**
	 * 根据学生获取借阅的图书
	 * @param stuId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getBookByStu(String stuId,String startDate, String endDate);
}
