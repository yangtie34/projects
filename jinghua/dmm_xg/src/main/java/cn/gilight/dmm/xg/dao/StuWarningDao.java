package cn.gilight.dmm.xg.dao;

import java.util.List;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月15日 下午3:26:55
 */
public interface StuWarningDao {

	/**
	 * 疑似逃课人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stustuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int querySkipClassesRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stustuAdvancedList, String date);
	/**
	 * 疑似逃课人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int querySkipClassesRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	/**
	 * 疑似逃课人次 sql
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return String <br>
	 * （select stu_id, date_ from t）
	 */
	public String getSkipClassesRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 疑似逃课人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int querySkipClassesRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date);
	/**
	 * 疑似逃课人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int querySkipClassesRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	/**
	 * 疑似逃课人数 sql
	 * @param schoolYear
	 * @param deptList
	 * @param stuAdvancedList
	 * @param dates
	 * @return String
	 */
	public String getSkipClassesRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 疑似未住宿人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int queryNotStayRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date);
	/**
	 * 疑似未住宿人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int queryNotStayRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	/**
	 * 疑似未住宿人次 sql
	 * @param schoolYear
	 * @param deptList
	 * @param stuAdvancedList
	 * @param dates
	 * @return String
	 */
	public String getNotStayRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 疑似未住宿人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int queryNotStayRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date);
	/**
	 * 疑似未住宿人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int queryNotStayRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);

	public String getNotStayRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 晚勤晚归人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int queryStayLateRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date);
	/**
	 * 晚勤晚归人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int queryStayLateRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	/**
	 * 晚勤晚归人次 sql
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return String
	 */
	public String getStayLateRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 晚勤晚归人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int queryStayLateRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date);
	/**
	 * 晚勤晚归人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int queryStayLateRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);

	public String getStayLateRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 疑似不在校人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int queryStayNotinRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date);
	/**
	 * 疑似不在校人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int queryStayNotinRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	/**
	 * 疑似不在校人次 sql
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return String
	 */
	public String getStayNotinRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 疑似不在校人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param date 日期
	 * @return int
	 */
	public int queryStayNotinRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date);
	/**
	 * 疑似不在校人数
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段；eg:['2016-01-01','2016-01-07']
	 * @return int
	 */
	public int queryStayNotinRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);

	public String getStayNotinRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);
	
	/**
	 * 查询周几逃课学生
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param dates 日期段
	 * @param weekDay 周几
	 * @return String 
	 */
	public String getSkipClassStuSqlByWeekDay(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates,String weekDay);
	
	/**
	 * 查询逃课节次分布
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList
	 * @param dates 日期段
	 * @param period_start 开始节次
	 * @param period_end 结束节次
	 * @return String
	 */
	public String getSkipClassStuSqlByPeriod(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates,String period_start,String period_end);
	
	/**
	 * 获取上午逃课学生总量
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList
 	 * @param dates  日期段
	 * @param period_start 开始节次
	 * @param period_end 结束节次
	 * @return int
	 */
	public int getSkipClassStuByAM(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates);

	/**
	 * 上午逃课下午逃课学生
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级参数
	 * @param dates 日期段
	 * @param period_start 上午逃课开始节次
	 * @param period_end 上午逃课结束节次
	 * @return
	 */
	public String getSkipClassStuByagain(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates,String period_start,String period_end);

}