package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 图书借阅排名统计分析
 *
 */
public interface BookBorrowStuTopDao {
	
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
	 * key:borrowNum	value:借书次数<br>
	 * key:topNum		value:上榜次数<br>
	 */
	public Page getBorrowTopByStu(int currentPage, int numPerPage,int totalRow,Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
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
	public List<Map<String,Object>> getBorrowTopByPeopleTypeStu(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 类型对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getBorrowTopByPeopleTypeStuTrend(Map<String,String> deptTeachs,int rank);
	
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
	public List<Map<String,Object>> getBorrowTopBySexStu(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 性别对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getBorrowTopBySexStuTrend(Map<String,String> deptTeachs,int rank);
	
	/**
	 * 年级对比
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getBorrowTopByStuGrade(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 年级对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getBorrowTopByStuGradeTrend(Map<String,String> deptTeachs,int rank);
	
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
	public List<Map<String,Object>> getBorrowTopByDeptTeach(Map<String,String> deptTeachs,String startDate, String endDate,int rank);
	
	/**
	 * 所属情况对比趋势
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getBorrowTopByDeptTeachTrend(Map<String,String> deptTeachs,int rank);
	
	/**
	 * 年度冠军情况
	 * @param type  查询类型，学生：stu,教师：tea
	 * @param schoolYear 学年
	 * @return
	 * List中Map内容：<br>
	 * Key:rank_		value:名次<br>
	 * Key:no_			value:上榜者ID<br>
	 * Key:name			value:上榜者名称<br>
	 * key:ofName 		value:所属单位名称：文学院-历史系-历史1班<br>
	 * key:yearNum		value:本年度借书次数<br>
	 * key:historyNum	value:历史借书次数<br>
	 * key:topNum		value:本年月度上榜次数<br>
	 * key:historyTop	value:历史月度上榜次数<br>
	 */
	public List<Map<String,Object>> getBorrowTopByYearStu(String schoolYear);
	
	/**
	 * 年度季军情况
	 * @param schoolYear 学年
	 * @return
	 * List中Map内容：<br>
	 * Key:季度Code		value:quarter<br>
	 * Key:rank_		value:名次<br>
	 * Key:no_			value:上榜者ID<br>
	 * Key:name			value:上榜者名称<br>
	 * key:ofName 		value:所属单位名称：文学院-历史系-历史1班<br>
	 * key:quarterNum	value:本季借书次数<br>
	 * key:yearNum		value:年度借书次数<br>
	 * key:historyNum	value:历史借书次数<br>
	 * key:topNum		value:本年月度上榜次数<br>
	 * key:historyTop	value:历史月度上榜次数<br>
	 */
	public List<Map<String,Object>> getBorrowTopByQuarterStu(String schoolYear);
	
	/**
	 * 年度月冠军情况
	 * @param schoolYear 学年
	 * @return
	 * List中Map内容：<br>
	 * Key:month			value:月份<br>
	 * Key:rank_		value:名次<br>
	 * Key:no_			value:上榜者ID<br>
	 * Key:name			value:上榜者名称<br>
	 * key:ofName 		value:所属单位名称：文学院-历史系-历史1班<br>
	 * key:monthNum		value:本月借书次数<br>
	 * key:yearNum		value:年度借书次数<br>
	 * key:historyNum	value:历史借书次数<br>
	 * key:topNum		value:本年月度上榜次数<br>
	 * key:historyTop	value:历史月度上榜次数<br>
	 */
	public List<Map<String,Object>> getBorrowTopByMonthStu(String schoolYear);
	
}
