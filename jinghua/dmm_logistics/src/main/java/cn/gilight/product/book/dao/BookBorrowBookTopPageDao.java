package cn.gilight.product.book.dao;

import cn.gilight.framework.page.Page;


/**
 * 图书馆统计分析
 *
 */
public interface BookBorrowBookTopPageDao {
	/**
	 * 获取根据书名获取所有借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * tl_book_borrow_detil表所有字段
	 * 
	 */
	public Page getAllBorrow(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String bookName);
	
	/**
	 * 根据书名获取所有上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * school_year,year_,month_,rank_,borrow_num,name,ofid,ofname<br>
	 * 所处学年，年份，月份，名次，借阅次数，书名，所属ID，所属名称
	 */
	public Page getAllTop(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String bookName);
	
	/**
	 * 获取根据书名和起止时间获取所有借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param bookName
	 * @return
	 * tl_book_borrow_detil表所有字段
	 * 
	 */
	public Page getBorrowByTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String bookName);
	
	/**
	 * 根据书名和起止时间获取所有上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param bookName
	 * @return
	 * school_year,year_,month_,rank_,borrow_num,name,ofid,ofname<br>
	 * 所处学年，年份，月份，名次，借阅次数，书名，所属ID，所属名称
	 * 
	 */
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String bookName);
	
	/**
	 * 根据藏书类别和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,borrow_num,BOOK_NAME,STORE_CODE,STORE_NAME <br>
	 * 名次，借阅次数，书名，藏书类别，类别名称
	 */
	public Page getStore(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,int rank,String storeId,String value);
	
}
