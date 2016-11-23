package com.jhnu.product.four.book.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface FourBookDao {
	/**
	 * 获得第一次借书
	 * @return
	 */
	public List<Map<String,Object>> getFirstBorrow();
	
	/**
	 * 获得第一次进出图书馆
	 * @return
	 */
	public List<Map<String,Object>> getFirstRKE();
	
	/**
	 * 获得借书统计
	 * @return
	 */
	public List<Map<String,Object>> getAllBorrow();
	
	/**
	 * 获得各届学生的借书平均数和总数
	 * @return
	 */
	public List<Map<String, Object>> getAllBorrowAC();
	
	/**
	 * 获得进出图书馆统计
	 * @return
	 */
	public List<Map<String,Object>> getAllRKE();
	
	/**
	 * 获得各届学生的进出图书馆平均数和总数
	 * @return
	 */
	public List<Map<String, Object>> getAllRKEAC();
	
	/**
	 * 保存第一次借书的LOG
	 * @param list
	 */
	public void saveFirstBorrowLog(List<Map<String,Object>> list);
	
	/**
	 * 保存第一次进出图书馆的LOG
	 * @param list
	 */
	public void saveFirstRKELog(List<Map<String,Object>> list);
	
	/**
	 * 保存借书统计的LOG
	 * @param list
	 */
	public void saveAllBorrowLog(List<Map<String,Object>> list);
	
	/**
	 * 保存进出图书馆统计的LOG
	 * @param list
	 */
	public void saveAllRKELog(List<Map<String,Object>> list);
	
	/**
	 * 通过Log获取第一次借书
	 * @param id 学号ID
	 * @return
	 */
	public List<Map<String,Object>> getFirstBorrowLog(String id);
	
	/**
	 * 通过Log获取第一次进入图书馆
	 * @param id 学号ID
	 * @return
	 */
	public List<Map<String,Object>> getFirstRKELog(String id);
	
	/**
	 * 通过Log获取借书统计
	 * @param id 学号ID
	 * @return
	 */
	public List<Map<String,Object>> getAllBorrowLog(String id);
	
	/**
	 * 通过Log获取进出图书馆统计
	 * @param id 学号ID
	 * @return
	 */
	public List<Map<String,Object>> getAllRKELog(String id);
	
	/**
	 * 获得经常去图书馆的时间
	 * @return
	 */
	public List<Map<String,Object>> getLikeRKETime();
	
	/**
	 * 保存经常去图书馆的时间到LOG
	 * @param list
	 */
	public void saveLikeRKETimeLog(List<Map<String,Object>> list);
	
	/**
	 * 从LOG获得经常去图书馆的时间
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getLikeRKETimeLog(String id);
	

	/**
	 * 获取每个每学年学期的图书借阅次数
	 * @return
	 */
	public List<Map<String,Object>> getBookBorrowCount();
	/**
	 * 保存每个每学年学期的图书借阅次数
	 * @return
	 */
	public void saveBookBorrowCountLog(List<Map<String,Object>> list);
	/**
	 * 获取指定学生的每学年学期的图书借阅次数
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getBookBorrowStuLine(String id);
	/**
	 * 获取本届学生的每学年学期的平均图书借阅次数
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getBookBoAvgGradeLine(String enrollGrade);
	
	
	/**
	 * 获取每个每学年学期的图书进出次数
	 * @return
	 */
	public List<Map<String,Object>> getBookRKECount();
	/**
	 * 保存每个每学年学期的图书进出次数
	 * @return
	 */
	public void saveBookRKECountLog(List<Map<String,Object>> list);
	/**
	 * 获取指定学生的每学年学期的图书进出次数
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getBookRKEStuLine(String id);
	/**
	 * 获取本届学生的每学年学期的平均图书进出次数
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getBookRKEAvgGradeLine(String enrollGrade);
	
	
	public Page getBookDetailLog(int currentPage,int numPerPage, String tj);
	
	public List<Map<String, Object>> getBookDetailGroupByTime(String id);
	public List<Map<String, Object>> getBookDetailGroupByDeal(String id);

}
