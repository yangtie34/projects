package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

/**
 * 图书借阅超时统计分析
 *
 */
public interface BookOutTimeDao {
	
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
	 * Key:nums   	  value:逾期书籍<br>
	 * Key:numRate	  value:逾期率<br>
	 */
	public Map<String,Object> getOutTimeBookCount(String startDate, String endDate);
	
	
	/**
	 * 未还书籍人数对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   value:读者类型<br>
	 * Key:value  value:逾期人数<br>
	 */
	public List<Map<String,Object>> getOutTimePeopleByPeopleType(String startDate, String endDate);
	
	/**
	 * 未还书籍人数趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   value:读者类型<br>
	 * Key:value  value:逾期人数<br>
	 */
	public List<Map<String,Object>> getOutTimePeopleTrend();
	
	/**
	 * 逾期率对比
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:读者类型<br>
	 * Key:value       value:逾期率<br>
	 */
	public List<Map<String,Object>> getOutTimeRateByPeopleType(String startDate, String endDate);
	
	/**
	 * 逾期率趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name        value:读者类型<br>
	 * Key:value  	   value:逾期率<br>
	 */
	public List<Map<String,Object>> getOutTimeRateTrend();
	
	/**
	 * 获取各类型数据对比情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	  value:藏书名称<br>
	 * Key:nums   	  value:逾期数量<br>
	 * Key:numRate    value:逾期率(%)<br>
	 * Key:avgTime    value:平均逾期时长<br>
	 */
	public List<Map<String,Object>> getOutTimeByType(String startDate, String endDate);
	
	/**
	 * 获取各类型数据对比情况
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	  value:学院名称<br>
	 * Key:nums   	  value:逾期数量<br>
	 * Key:numRate    value:逾期率(%)<br>
	 * Key:avgTime    value:平均逾期时长<br>
	 */
	public List<Map<String,Object>> getOutTimeByDept(String startDate, String endDate);
	
	/**
	 * 获取逾期历史情况分析
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name        value:查询类型<br>
	 * Key:value       value:查询值<br>
	 */
	public List<Map<String,Object>> getoutTimeTrend();
	
}
