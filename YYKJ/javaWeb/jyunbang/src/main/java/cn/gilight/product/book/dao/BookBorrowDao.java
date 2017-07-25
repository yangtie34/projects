package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

/**
 * 图书馆统计分析
 *
 */
public interface BookBorrowDao {
	
	
	/**
	 * 图书借阅统计
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * Map内容：<br>
	 * BORROW_NUM 流通量(外借本数)
	 */
	public Map<String,Object> getBorrowNum(String startDate, String endDate);
	
	/**
	 * 各类型人群人均借书量对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   value:读者类型<br>
	 * Key:value  value:人均借书数<br>
	 */
	public List<Map<String,Object>> getPeopleAvgBorrowByPeopleType(String startDate, String endDate);
	
	/**
	 * 各类型人群人均借书量趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   value:读者类型<br>
	 * Key:value  value:人均借书数<br>
	 */
	public List<Map<String,Object>> getPeopleAvgBorrowTrend();
	
	/**
	 * 各类型借书人数比例对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:读者类型<br>
	 * Key:value       value:读者人数<br>
	 */
	public List<Map<String,Object>> getReaderRateByPeopleType(String startDate, String endDate);
	
	/**
	 * 各类型借书人数比例趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name        value:读者类型<br>
	 * Key:value  	   value:读者人数<br>
	 */
	public List<Map<String,Object>> getReaderRateTrend();
	
	/**
	 * 获取图书藏书类别对比
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	  value:藏书名称<br>
	 * Key:nums   	  value:流通量<br>
	 * Key:numRate    value:流通率(%)<br>
	 * Key:useRate    value:利用率(%)<br>
	 * Key:renewRate  value:续借率(%)<br>
	 */
	public List<Map<String,Object>> getBorrowByType(String startDate, String endDate);
	
	/**
	 * 分学院分析学生借阅情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	   value:学院ID<br>
	 * Key:name   	   value:学院名称<br>
	 * Key:value       value:借书本数<br>
	 */
	public List<Map<String,Object>> getStuBorrowByDept(String startDate, String endDate);
	
	/**
	 * 分专业分析学生借阅情况
	 * @param deptTeachId 学院ID
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	   value:专业ID<br>
	 * Key:name   	   value:专业名称<br>
	 * Key:value       value:借书本数<br>
	 */
	public List<Map<String, Object>> getStuBorrowByMajor(String deptTeachId,String startDate,String endDate);
	
	/**
	 * 各类人群借阅书籍时段情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:time   	   value:时间<br>
	 * Key:name        value:读者类型<br>
	 * Key:value       value:读者人数<br>
	 */
	public List<Map<String,Object>> getBorrowByTime(String startDate, String endDate);
	
	/**
	 * 图书借阅历史趋势按学年
	 * @return
	 * List中Map内容：<br>
	 * Key:name    value:学年<br>
	 * Key:value   value:借阅量<br>
	 */
	public List<Map<String,Object>> getBorrowTrendByYear();
	
	/**
	 * 图书借阅历史趋势按月份
	 * @return
	 * List中Map内容：<br>
	 * Key:name    value:月份<br>
	 * Key:value   value:借阅量<br>
	 */
	public List<Map<String,Object>> getBorrowTrendByMonth();
}
