package cn.gilight.product.book.dao;

import cn.gilight.framework.page.Page;


/**
 * 逾期还书次数统计
 *
 */
public interface BookOutTimeNumBookTopPageDao {
	/**
	 * 获取根据书名获取所有逾期还书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * tl_book_borrow_detil表所有字段
	 * 
	 */
	public Page getAllOutTime(int currentPage ,int numPerPage,int totalRow,String bookName);
	
	/**
	 * 根据书名获取所有逾期还书上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * school_year,year_,month_,nums_rank,nums,name,ofid,ofname<br>
	 * 所处学年，年份，月份，名次，借阅次数，书名，所属ID，所属名称
	 */
	public Page getAllTop(int currentPage ,int numPerPage,int totalRow,String bookName);
	
	/**
	 * 获取根据书名和起止时间获取所有逾期还书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param bookName
	 * @return
	 * tl_book_borrow_detil表所有字段
	 * 
	 */
	public Page getOutTimeByTime(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String bookName);
	
	/**
	 * 根据书名和起止时间获取所有逾期还书上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param bookName
	 * @return
	 * school_year,year_,month_,nums_rank,nums,name,ofid,ofname<br>
	 * 所处学年，年份，月份，名次，借阅次数，书名，所属ID，所属名称
	 * 
	 */
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String bookName);
	
	/**
	 * 根据藏书类别和起止时间与名次获取逾期还书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,outtime_num,BOOK_NAME,STORE_CODE,STORE_NAME <br>
	 * 名次，借阅次数，书名，藏书类别，类别名称
	 */
	public Page getStore(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,String storeId,String value);
	
}
