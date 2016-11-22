package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;


/**
 * 图书馆逾期还书统计
 *
 */
public interface BookOutTimeStuPageDao {
	/**
	 * 获取当前未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getNowOutTime(int currentPage ,int numPerPage,int totalRow,Map<String,String> deptTeachs);
	
	/**
	 * 获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTime(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 获取逾期未还书籍人均逾期比
	 * @param startDate
	 * @param endDate
	 * @return tl_book_borrow_detil 所有内容
	 */
	public List<Map<String,Object>> getOutTimeCountByPeople(String startDate, String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 通过人员获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param people
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeByPeople(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String people,Map<String,String> deptTeachs);
	
	/**
	 * 获取逾期未还书籍人员比
	 * @param startDate
	 * @param endDate
	 * @return tl_book_borrow_detil 所有内容
	 */
	public List<Map<String,Object>> getOutTimeRateCountByPeople(String startDate, String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 通过藏书类别获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param store
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeByStore(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String store,Map<String,String> deptTeachs);
	
	/**
	 * 通过学院获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeByDeptTeach(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String deptTeach,Map<String,String> deptTeachs);
	
	/**
	 * 通过还书时间获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param time
	 * @param people
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getBorrowByTimePeo(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String time,String people,Map<String,String> deptTeachs);
	
	/**
	 * 通过学年获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param schoolYeasr
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeBySchoolYear(int currentPage ,int numPerPage,int totalRow,String schoolYeasr,Map<String,String> deptTeachs);
}
