package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface StuCostDao{
	/**
	 * 查询学期开始时间
	 * @return List<Map<String, Object>> 
	 */
	public  List<Map<String, Object>> querySchoolStart();
	/**
	 * 周维度-各部门高低消费人数
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param moonth 月次
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return int
	 */
	public  List<Map<String,Object>> queryCountByDept(String schoolYear, String term,
			int moonth, String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 学期维度-各部门高低消费人数
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return int
	 */
	public  List<Map<String,Object>> queryCountByDept(String schoolYear, String term,
			String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询学期下拉数据
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param column 列
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public  List<Map<String, Object>> queryTermData(String schoolYear,
			String termCode, String column, String[] type,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 学年维度-高低消费学生
	 * @param schoolYear 学年
	 * @param column 列
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public  List<Map<String, Object>> queryYearData(String schoolYear,
			String column, String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
    /**
     * 学期维度-高低消费学生
     * @param schoolYear 学年
     * @param termCode 学期
     * @param table 表名
     * @param type 高低消费
     * @param deptList 标准权限
     * @param stuAdvancedList 高级查询参数
     * @return int
     */
	public  int queryTermByOther(String schoolYear, String termCode,
			String table, String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
    /**
     * 学年维度-高低消费学生
     * @param schoolYear 学年
     * @param table 表
     * @param type 高低消费 
     * @param deptList 标准权限
     * @param stuAdvancedList 高级查询参数
     * @return int
     */
	public  int queryYearByOther(String schoolYear, String table,
			String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
    /**
     * 各餐别高低消费人数
     * @param standard 标准
     * @param schoolYear 学年
     * @param termCode 学期
     * @param type 高低消费
     * @param mealName 餐别名
     * @param deptList 标准权限
     * @param stuAdvancedList 高级查询参数
     * @return Map<String, Object>
     */
	public  List<Map<String, Object>> queryCountByCbdm(String schoolYear, String termCode, String[] type, 
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
    /**
     * 上周数据
     * @param schoolYear 学年
     * @param termCode 学期
     * @return  Map<String, Object> 
     */
//	public  Map<String, Object> queryLastWeekth(String schoolYear,
//			String termCode);
    /**
     * 查询年级
     * @param schoolYear 学年
     * @param deptList 标准权限
     * @param stuAdvancedList 高级查询参数
     * @return List<Map<String, Object>>
     */
	public  List<Map<String, Object>> queryGrade(int schoolYear,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
    /**
     * 查询性别标准
     * @return List<Map<String, Object>>
     */
	public  List<Map<String, Object>> querySexList();
    /**
     * 查询人数
     * @param table 表名
     * @param schoolYear 学年
     * @param termCode 学期
     * @param deptList 标准权限
     * @param stuAdvancedList 高级查询参数
     * @return int
     */
	public  int queryCount(String table, String schoolYear,
			String termCode,String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 学年维度-各部门高低消费人数
	 * @param schoolYear 学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return int
	 */
	public int queryCountByDept(String schoolYear, String[] type,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询要导出的数据
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param weekth 周次
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return  List<Map<String, Object>>
	 */
//	public List<Map<String, Object>> queryExportData(String schoolYear, String term,
//			int weekth, String[] type, List<String> deptList,
//			List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询学校邮箱地址
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryEmailList();
	/**
	 * 查询发送状态
	 * @param schoolYear
	 * @param termCode
	 * @param moonth
	 * @param deptid
	 * @return int
	 */
	public int querySendStatus(String schoolYear, String termCode, int moonth,
			String deptid,String type);
	/**
	 * 查询高低消费标准
	 * @param table 表名 
	 * @param type 高低消费类型
	 * @param wheresql 条件sql
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryCostValue(String table, String[] type, String wheresql);
	/**
	 * 当前学期和以前的学期
	 * @param day
	 * @return
	 */
	public List<Map<String, Object>> querySchoolStart(String day);
	/**
	 * 查询高低消费标准（从结果表中取）
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type_ 标准类型
	 * @param type 高低消费类型
	 * @return Double
	 */
	public Double queryStandard(String schoolYear, String termCode, String type_,
			String[] type);
	/**
	 * 获取某一学期的开始结束时间算出这学期的天数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return int
	 */
	public int getStartAndEnd(String schoolYear, String termCode);
	/**
	 * 获取某一周的开始结束时间算出这周的天数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return int
	 */
	public int getStartAndEnd(String schoolYear, String termCode, int weekth);
	/**
	 * 获取月份集合
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryMonthList();
	/**
	 * 查询某学年学期最大的月次
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return int
	 */
	public int queryMaxMonth(String schoolYear, String termCode);
	/**
	 * 每月高低消费数据导出
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param moonth 月次
	 * @param type 高低消费类型
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param type_ 数据类型
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> queryExportData(String schoolYear, String term,
			int moonth, String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList, String type_);
	/**
	 * 表格数据来源截止日期
	 * @return String
	 */
	public String queryMonthMaxEnd();
	/**
	 * 根据标准表查询学年学期
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryStandardDate();
	/**
	 * 查询标准表最大最小学年
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryStandardYear();
	/**
	 * 查询高低消费算法说明
	 * @param schoolYear
	 * @param termCode
	 * @param type_
	 * @param type
	 * @return
	 */
	public String queryStandardName(String schoolYear, String termCode, String type_,
			String[] type);
	/**
	 * 获取高低消费计算的消费类型
	 * @return
	 */
	public String queryXflx();
	public int getDeptCost(int schoolYear, String term, int month, String[] type,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
}