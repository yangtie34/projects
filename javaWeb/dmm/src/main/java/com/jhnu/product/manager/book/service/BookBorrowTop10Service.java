package com.jhnu.product.manager.book.service;

import java.util.List;
import java.util.Map;

public interface BookBorrowTop10Service {

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
	 * 查询某段时间某本书的借阅人
	 * @param deptId
	 * @param isLeaf
	 * @param bookId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getPeopleByBook(String deptId,boolean isLeaf,String bookId,String startDate,String endDate);
	/**
	 * 根据学生查询某段时间内结果的书籍
	 * @param stuId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getBookByStu(String stuId,String startDate,String endDate);

	
}
