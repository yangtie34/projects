package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;


/**
 * 图书馆借书统计
 *
 */
public interface BookBorrowStuPageDao {
	/**
	 * 获取借阅列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @return
	 * tl_book_borrow_detil 所有内容
	 */
	public Page getBorrow(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 通过借阅者获取人均借书比
	 * @param startDate
	 * @param endDate
	 * @return
	 * tl_book_borrow_detil 所有内容
	 */
	public List<Map<String,Object>> getBorrowCountByPeople(String startDate, String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 通过借阅者获取借阅列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param people
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getBorrowByPeople(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String people,Map<String,String> deptTeachs);
	
	/**
	 * 通过借阅者获取人均比
	 * @param startDate
	 * @param endDate
	 * @return tl_book_borrow_detil 所有内容
	 */
	public List<Map<String,Object>> getBorrowPeosCountByPeople(String startDate, String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 通过藏书列表获取借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param store
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getBorrowByStore(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String store,Map<String,String> deptTeachs);
	
	/**
	 * 通过学院获取借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getBorrowByDeptTeach(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String deptTeach,Map<String,String> deptTeachs);
	
	/**
	 * 通过时间和人员类型获取借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param time
	 * @param people
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getBorrowByTimePeo(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String time,String people,Map<String,String> deptTeachs);
	
	/**
	 * 通过学年获取借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param schoolYear
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getBorrowBySchoolYear(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String schoolYear,Map<String,String> deptTeachs);
	
	/**
	 * 通过月份获取借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param month
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getBorrowCountByMonth(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String month,Map<String,String> deptTeachs);
}
