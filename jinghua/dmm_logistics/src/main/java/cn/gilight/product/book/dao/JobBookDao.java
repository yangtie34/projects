package cn.gilight.product.book.dao;

import java.util.Map;

import cn.gilight.framework.enums.QuarterEnum;


public interface JobBookDao {

	/**
	 * 保存更新 图书年度统计
	 * @param year
	 */
	public void updateBookYear(String schoolYear) throws Exception;
	
	/**
	 * 保存更新 图书年度类别统计
	 * @param year
	 */
	public void updateBookTypeYear(String schoolYear) throws Exception;
	
	/**
	 * 保存更新 图书年度读者统计
	 * @param year
	 */
	public void updateBookReaderYear(String schoolYear) throws Exception;
	
	/**
	 * 保存更新 图书年度读者详细统计
	 * @param year
	 */
	public void updateBookReaderDetilYear(String schoolYear) throws Exception;
	
	/**
	 * 保存更新 图书借阅类型月度统计
	 * @param year
	 */
	public void updateBookBorrowTypeMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书借阅人员类型月度统计
	 * @param year
	 */
	public void updateBookBorrowPeopleMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书逾期学期书籍类型统计
	 * @param year
	 */
	public void updateBookOutTimeTypeMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书逾期学期人员类型统计
	 * @param year
	 */
	public void updateBookOutTimePeopleMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书借阅学生月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateBookBorrowStuMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书借阅教师月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateBookBorrowTeaMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书借阅月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateBookBorrowBookMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书借阅学生月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateBookOutTimeStuMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书借阅教师月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateBookOutTimeTeaMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存更新 图书借阅月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateBookOutTimeBookMonth(String schoolYear,String month) throws Exception;
	
	/**
	 * 保存年度排行榜
	 * @param schoolYear
	 */
	public void updateBorrowTopYear(String schoolYear) throws Exception;
	
	/**
	 * 保存季度冠军
	 * @param schoolYear
	 * @param qe
	 */
	public void updateBorrowTopQuarter(String schoolYear,QuarterEnum quarter) throws Exception;
	
	/**
	 * 保存月度冠军
	 * @param schoolYear
	 * @param month
	 */
	public void updateBorrowTopMonth(String schoolYear,String month) throws Exception;
	
	
	/**
	 * 保存借书明细表
	 */
	public Map<String,Integer> updateBorrowDetil(String date) throws Exception;
	
	/**
	 * 保存学生读者人数
	 * @param yearMonth
	 * @return
	 */
	public Map<String,Integer> updateBookStu(String yearMonth);
	
	/**
	 * 保存书籍情况
	 * @param yearMonth
	 * @return
	 */
	public Map<String,Integer> updateBookMonth(String yearMonth);
	
	/**
	 * 保存学生借书情况
	 * @param yearMonth
	 * @return
	 */
	public Map<String,Integer> updateBorrowStu(String yearMonth);
	
	public void createTemp();

	void updateBookStateYear(String schoolYear);
	
}
