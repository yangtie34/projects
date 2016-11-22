package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 图书借阅排名统计分析
 *
 */
public interface BookBorrowBookTopDao {
	
	/**
	 * 图书借阅Top10排名
	 * @param storeId 	藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:rank_		value:名次<br>
	 * Key:no_			value:上榜者ID<br>
	 * Key:name			value:上榜者名称<br>
	 * key:ofName 		value:所属名称<br>
	 * key:ofId			value:所属ID<br>
	 * key:borrowNum	value:借书次数<br>
	 * key:topNum		value:上榜次数<br>
	 */
	public Page getBorrowTopByBook(int currentPage, int numPerPage,int totalRow,String storeId,String startDate, String endDate,int rank);
	
	/**
	 * 所属情况对比
	 * @param storeId  藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getBorrowTopByStore(String storeId,String startDate, String endDate,int rank);
	
	/**
	 * 所属情况对比趋势
	 * @param storeId  藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getBorrowTopByStoreTrend(String storeId ,int rank);
	
	
	
	
}
