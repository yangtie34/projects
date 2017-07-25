package cn.gilight.product.book.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 图书借阅超时统计分析
 *
 */
public interface BookOutTimeService {
	
	/**
	 * 获取当前未还书籍
	 * @return
	 */
	public int getNowOutTimeBook();
	
	/**
	 * 获取逾期书籍概要统计
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * Map内容：<br>
	 * Key:NUMS   	  value:逾期书籍<br>
	 * Key:NUMRATE	  value:逾期率(万分)<br>
	 */
	public Map<String,Object> getOutTimeBookCount(String startDate,String endDate);
	
	
	/**
	 * 未还书籍人数对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE   value:类型ID<br>
	 * Key:NAME   value:读者类型<br>
	 * Key:VALUE  value:逾期人数<br>
	 */
	public List<Map<String,Object>> getOutTimePeopleByPeopleType(String startDate,String endDate);
	
	/**
	 * 未还书籍人数趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:CODE   value:类型ID<br>
	 * Key:NAME   value:读者类型<br>
	 * Key:VALUE  value:逾期人数<br>
	 */
	public List<Map<String,Object>> getOutTimePeopleTrend();
	
	/**
	 * 逾期率对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	   value:类型ID<br>
	 * Key:name   	   value:读者类型<br>
	 * Key:value       value:逾期率(万分)<br>
	 */
	public List<Map<String,Object>> getOutTimeRateByPeopleType(String startDate,String endDate);
	
	/**
	 * 逾期率趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:code   	   value:类型ID<br>
	 * Key:name   	   value:读者类型<br>
	 * Key:value       value:逾期率(万分)<br>
	 */
	public List<Map<String,Object>> getOutTimeRateTrend();
	
	/**
	 * 获取各类型数据对比情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	  value:藏书ID<br>
	 * Key:name   	  value:藏书名称<br>
	 * Key:nums   	  value:逾期数量<br>
	 * Key:numRate    value:逾期率(%)(万分)<br>
	 * Key:avgTime    value:平均逾期时长<br>
	 */
	public List<Map<String,Object>> getOutTimeByType(String startDate,String endDate);
	
	/**
	 * 获取各类型数据对比情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	  value:学院ID<br>
	 * Key:name   	  value:学院名称<br>
	 * Key:nums   	  value:逾期数量<br>
	 * Key:numRate    value:逾期率(%)<br>
	 * Key:avgTime    value:平均逾期时长<br>
	 */
	public List<Map<String,Object>> getOutTimeByDept(String startDate,String endDate);
	
	/**
	 * 获取逾期历史情况分析
	 * @return
	 * List中Map内容：<br>
	 * Key:SCHOOLYEAR  value:学年<br>
	 * Key:CODE        value:学年<br>
	 * Key:RATE        value:逾期率<br>
	 * Key:VALUE       value:逾期本数<br>
	 */
	public List<Map<String,Object>> getoutTimeTrend();
	

	/**
	 * 获取当前未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getNowOutTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc);
	
	/**
	 * 获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTime(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate);
	
	/**
	 * 获取逾期未还书籍人均逾期比
	 * @param startDate
	 * @param endDate
	 * @return tl_book_borrow_detil 所有内容
	 */
	public List<Map<String,Object>> getOutTimeCountByPeople(String startDate, String endDate);
	
	/**
	 * 通过人员获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param people
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeByPeople(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String people);
	
	/**
	 * 获取逾期未还书籍人员比
	 * @param startDate
	 * @param endDate
	 * @return tl_book_borrow_detil 所有内容
	 */
	public List<Map<String,Object>> getOutTimeRateCountByPeople(String startDate, String endDate);
	
	/**
	 * 通过藏书类别获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param store
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeByStore(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String store);
	
	/**
	 * 通过学院获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeByDeptTeach(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String deptTeach);
	
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
	public Page getBorrowByTimePeo(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,String time,String people);
	
	/**
	 * 通过学年获取逾期未还书籍
	 * @param currentPage
	 * @param numPerPage
	 * @param schoolYeasr
	 * @return tl_book_borrow_detil 所有内容
	 */
	public Page getOutTimeBySchoolYear(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String schoolYeasr);

}
