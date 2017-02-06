package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface StuLowCostService {
    /**
     * 查询各部门高消费学生
     * @param start 起始时间
     * @param weekth 周次
     * @param advancedParamList 高级查询参数
     * @return Map<String, Object> 
     */
//	public Map<String, Object> getCountByDept(String start, Integer weekth,String column,Boolean asc,
//			List<AdvancedParam> advancedParamList);
	/**
	 * 上周高消费数据
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
//	public Map<String, Object> getLastWeekData(List<AdvancedParam> advancedParamList,String start,Integer weeks);
	/**
	 * 学期维度各年级高消费学生分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTermByGrade(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);

	
	/**
	 * 学年维度-高消费学生年级分布
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getYearByGrade(int start, int end,
			List<AdvancedParam> advancedParamList);

	/**
	 * 学期维度-高消费学生性别分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getTermBySex(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);

	/**
	 * 学年维度-高消费学生性别分布
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> getYearBySex(int start, int end,
			List<AdvancedParam> advancedParamList);

	/**
	 * 学期维度-高消费学生助学金人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getTermBySubsidy(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);

	/**
	 * 学年维度-高消费学生助学金人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> getYearBySubsidy(int start, int end,
			List<AdvancedParam> advancedParamList);
	
	/**
	 * 学期维度-高消费学生助学贷款人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTermByLoan(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);

	/**
	 * 学年维度-高消费学生助学贷款人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getYearByLoan(int start, int end,
			List<AdvancedParam> advancedParamList);
	
	/**
	 * 学期维度-高消费学生学费减免人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTermByJm(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学年维度-高消费学生学费减免人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getYearByJm(int start, int end,
			List<AdvancedParam> advancedParamList);
	/**
	 * 各年级高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getGradeHistory(List<AdvancedParam> advancedParamList);
	/**
	 *  各性别高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getSexHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 获得助学金高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getSubsidyHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 获得助学贷款高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLoanHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 获得学费减免高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getJmHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 各餐别低消费变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getMealHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 学年维度-各餐别低消费分布
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getYearByMeal(int start, int end,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学期维度-各餐别低消费分布 
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getTermByMeal(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学期维度-各部门高低消费人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTermCountDept(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学年维度-各部门高低消费人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getYearCountDept(int start, int end,
			List<AdvancedParam> advancedParamList);
	/**
	 * 查询导出数据
	 * @param start 开始时间
	 * @param weekth 周次
	 * @param pid 组织结构id
	 * @param advancedParamList 高级查询参数
	 */
//	public void exportData(String mc,String start, Integer weekth, String pid,
//			List<AdvancedParam> advancedParamList,HttpServletResponse response);
	/**
	 * 获取导出数据
	 * @param sendType 发送类型
	 * @param mc 标题
	 * @param start 开始时间
	 * @param weekth 周次
	 * @param pid 单位id
	 * @param type 高低消费
	 * @param advancedParamList 高级查询参数
	 * @param request 请求
	 * @return  Map<String, Object>
	 */
//	public Map<String, Object> getExportData(String sendType, String mc, String start,
//			Integer weekth, String pid, 
//			List<AdvancedParam> advancedParamList, HttpServletRequest request);
    /**
     * 压缩
     * @param request
     * @param response
     */
	public void excelAll(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 压缩
	 * @param request
	 * @return
	 */
	public Map<String, Object> sendAll(HttpServletRequest request);
	/**
	 * 低消费标准代码
	 * @return
	 */
	public Map<String, Object> getCostCode();
	/**
	 * 分页-学生明细
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 学生明细（导出用）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 
	 * @param xnxqList
	 * @param monthList
	 * @param deptid
	 * @param advancedParamList
	 * @return
	 */
	public Map<String, Object> getNearLv(String xnxqList, String monthList,
			String deptid, List<AdvancedParam> advancedParamList);
	/**
	 * 各部门低消费数据
	 * @param schoolYear
	 * @param termCode
	 * @param month
	 * @param column
	 * @param asc
	 * @param advancedParamList
	 * @return
	 */
	public  Map<String, Object> getCountByDept(String schoolYear, String termCode,
			Integer month, String column, Boolean asc,
			List<AdvancedParam> advancedParamList);
	/**
	 * 上月数据
	 * @param advancedParamList
	 * @param schoolYear
	 * @param termCode
	 * @param month
	 * @return
	 */
	public Map<String, Object> getLastMonthData(
			List<AdvancedParam> advancedParamList, String schoolYear,
			String termCode, Integer month);
	/**
	 * 数据导出
	 * @param mc
	 * @param schoolYear
	 * @param termCode
	 * @param month
	 * @param pid
	 * @param advancedParamList
	 * @param response
	 */
	public void exportData(String mc, String schoolYear, String termCode,
			Integer month, String pid, List<AdvancedParam> advancedParamList,
			HttpServletResponse response);
	/**
	 * 获取导出数据
	 * @param sendType
	 * @param mc
	 * @param schoolYear
	 * @param termCode
	 * @param month
	 * @param pid
	 * @param advancedParamList
	 * @param request
	 * @return
	 */
	public Map<String, Object> getExportData(String sendType, String mc,
			String schoolYear, String termCode, Integer month, String pid,
			List<AdvancedParam> advancedParamList, HttpServletRequest request);

    /**
     * 根据学年学期获得低消费标准
     * @param schoolYear 学年	
     * @param termCode 学期
     * @return Map<String, Object>
     */
	public Map<String, Object> getStandardMap(String schoolYear, String termCode);

}
