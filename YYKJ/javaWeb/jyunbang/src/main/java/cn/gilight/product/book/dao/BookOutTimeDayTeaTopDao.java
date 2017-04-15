package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 图书借阅排名统计分析
 *
 */
public interface BookOutTimeDayTeaTopDao {
	
	/**
	 * 图书借阅Top10排名
	 * @param deptTeachId 	教学机构ID
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:rank_		value:名次<br>
	 * Key:no_			value:上榜者ID<br>
	 * Key:name			value:上榜者名称<br>
	 * key:ofName 		value:所属名称<br>
	 * key:ofId			value:所属ID<br>
	 * key:borrowDay	value:借书次数<br>
	 * key:topDay		value:上榜次数<br>
	 */
	public Page getOutTimeDayTopByTea(int currentPage, int numPerPage,int totalRow,Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 类型对比
	 * @param deptTeachId 	教学机构ID
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopByPeopleTypeTea(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 类型对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopByPeopleTypeTeaTrend(Map<String,String> deptTeachs,int rank);
	
	/**
	 * 性别对比
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopBySexTea(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 性别对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopBySexTeaTrend(Map<String,String> deptTeachs,int rank);
	
	/**
	 * 职称对比
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopByTeaZC(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 职称对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopByTeaZCTrend(Map<String,String> deptTeachs,int rank);
	
	/**
	 * 所属情况对比
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopByDeptTeach(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 所属情况对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeDayTopByDeptTeachTrend(Map<String,String> deptTeachs,int rank);
	
}
