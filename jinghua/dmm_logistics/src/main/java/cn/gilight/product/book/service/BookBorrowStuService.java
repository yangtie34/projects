package cn.gilight.product.book.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 图书馆统计分析
 *
 */
public interface BookBorrowStuService {
	
	
	/**
	 * 图书借阅概要统计
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * Map内容：<br>
	 * Key:NUMS   	  value:流通量<br>
	 * Key:AVGNUMS	  value:日均流通量<br>
	 * Key:NUMRATE    value:流通率<br>
	 * Key:RENEWRATE  value:续借率<br>
	 */
	public Map<String,Object> getBorrowCount(String startDate,String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 各类型人群人均借书量对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE  value:类型ID<br>
	 * Key:NAME   value:读者类型<br>
	 * Key:VALUE  value:人均借书数<br>
	 */
	public List<Map<String,Object>> getPeopleAvgBorrowByPeopleType(String startDate,String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 各类型人群人均借书量趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:SCHOOLYEAR  value:学年<br>
	 * Key:CODE  value:类型ID<br>
	 * Key:NAME   value:读者类型<br>
	 * Key:VALUE  value:人均借书数<br>
	 */
	public List<Map<String,Object>> getPeopleAvgBorrowTrend(Map<String,String> deptTeachs);
	
	/**
	 * 各类型借书人数比例对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE  value:类型ID<br>
	 * Key:NAME   	   value:读者类型<br>
	 * Key:VALUE       value:读者人数<br>
	 */
	public List<Map<String,Object>> getReaderRateByPeopleType(String startDate,String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 各类型借书人数比例趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:SCHOOLYEAR  value:学年<br>
	 * Key:NAME        value:读者类型<br>
	 * Key:VALUE  	   value:读者人数<br>
	 */
	public List<Map<String,Object>> getReaderRateTrend(Map<String,String> deptTeachs);
	
	/**
	 * 获取当前图书藏书类别对比
	 * @return
	 * List中Map内容：<br>
	 * Key:NAME   	  value:藏书名称<br>
	 * Key:NUMS   	  value:流通量<br>
	 * Key:NUMRATE    value:流通率(%)<br>
	 * Key:USERATE    value:利用率(%)<br>
	 * Key:RENEWRATE  value:续借率(%)<br>
	 */
	public List<Map<String,Object>> getBorrowByType(String startDate,String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 分学院分析学生借阅情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE   	   value:学院ID<br>
	 * Key:NAME   	   value:学院名称<br>
	 * Key:VALUE       value:借书本数<br>
	 */
	public List<Map<String,Object>> getStuBorrowByDept(String startDate,String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 各类人群借阅书籍时段情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:TIME   	   value:时间<br>
	 * Key:CODE   	   value:类型ID<br>
	 * Key:NAME        value:读者类型<br>
	 * Key:VALUE       value:读者人数<br>
	 */
	public List<Map<String,Object>> getBorrowByTime(String startDate,String endDate,Map<String,String> deptTeachs);
	
	/**
	 * 图书借阅历史趋势按月份
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE    value:月份<br>
	 * Key:NAME    value:月份<br>
	 * Key:VALUE   value:借阅量<br>
	 */
	public List<Map<String,Object>> getBorrowTrendByMonth(Map<String,String> deptTeachs);
	

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
	
	/**
	 * 图书借阅历史趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:name    value:月份<br>
	 * Key:value   value:借阅量<br>
	 */
	public List<Map<String,Object>> getBorrowTrend(Map<String,String> deptTeachs);

}
