package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






















import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

public interface StuHighCostService {
    /**
     * 获取周次和时间选择
     * @return Map<String,Object>
     */
	public Map<String, Object> getTimeSelectList();
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
     * 查询各部门高、低消费学生
     * @param start 起始时间
     * @param weekth 周次
     * @param advancedParamList 高级查询参数
     * @param type 高、低消费
     * @return Map<String, Object> 
     */
//	public Map<String, Object> getCountByDept(String start, Integer weekth,String column,Boolean asc,List<String> deptList,
//			List<AdvancedParam> advancedParamList, String[] type,String abnormal_type);
	/**
	 * 上周高、低消费数据
	 * @param advancedParamList 高级查询参数
	 * @param type 高、低消费
	 * @return Map<String, Object>
	 */
//	public Map<String, Object> getLastWeekData(List<String> deptList,List<AdvancedParam> advancedParamList,
//			String[] type,String start,Integer weeks);
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
	 * 学期维度高低消费学生
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高、低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> getTermByGrade(String schoolYear, String termCode,
			String[] type, List<String> deptList,
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
	 * 学年维度-高低消费学生年级分布
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级参数参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getYearByGrade(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList);
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
	 * 学期维度-高低消费学生性别分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTermBySex(String schoolYear, String termCode,
			String[] type, List<String> deptList,
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
	 * 学年维度-高低消费学生性别分布
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getYearBySex(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList);
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
	 * 学期维度-高低消费学生助学金人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTermBySubsidy(String schoolYear, String termCode,
			String[] type, List<String> deptList,
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
	 * 学年维度-高低消费学生助学金人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getYearBySubsidy(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList);
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
	 * 学期维度-高低消费学生助学贷款人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getTermByLoan(String schoolYear, String termCode,
			String[] type, List<String> deptList,
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
	 * 学年维度-高低消费学生助学贷款人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getYearByLoan(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList);
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
	 * 学期维度-高低消费学生学费减免人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
 	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTermByJm(String schoolYear, String termCode,
			String[] type, List<String> deptList,
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
	 * 学年维度-高低消费学生学费减免人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getYearByJm(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList);
	/**
	 * 各年级高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getGradeHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 各年级高低消费学生变化趋势
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getGradeHistory(String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 *  各性别高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getSexHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 各性别高低消费学生变化趋势
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getSexHistory(String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 * 获得助学金高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getSubsidyHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 获得助学金高低消费学生变化趋势
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getSubsidyHistory(String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 * 获得助学贷款高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLoanHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 获得助学贷款高低消费学生变化趋势
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLoanHistory(String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 * 获得学费减免高消费学生变化趋势
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getJmHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 获得学费减免高低消费学生变化趋势
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getJmHistory(String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学期维度-各餐别高低消费分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getTermByMeal(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学期维度-各餐别高低消费分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getTermByMeal(String schoolYear, String termCode,
			String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学年维度-各餐别高低消费分布
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getYearByMeal(int start, int end,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学年维度-各餐别高低消费分布
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getYearByMeal(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList);
	/**
	 * 各餐别高低消费历史趋势
	 * @param advancedParamList 高级查询参数
	 * @return  Map<String,Object>
	 */
	public Map<String, Object> getMealHistory(List<AdvancedParam> advancedParamList);
	/**
	 * 各餐别高低消费历史趋势
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object> 
	 */
	public Map<String, Object> getMealHistory(String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学期维度-各部门高低消费人数
	 * @param schoolYear 学年
	 * @param termCode 学期
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
	 * 学期维度-各部门高低消费人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getTermCountDept(String schoolYear, String termCode,
			String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList);
	/**
	 * 学年维度-各部门高低消费人数
	 * @param start 开始学年
	 * @param end 结束学年
	 * @param type 高低消费
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getYearCountDept(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList);
	/**
	 * 获取导出的具体数据
	 * @param start 开始日期
	 * @param weekth 周次 
	 * @param pid 组织结构id
	 * @param advancedParamList 高级查询参数
	 */
//	public void exportData(String mc,String start, Integer weekth, String pid,
//			List<AdvancedParam> advancedParamList,HttpServletResponse response);
	/**4
	 * 获取导出的具体数据
	 * @param start 开始日期
	 * @param weekth 周次
	 * @param pid 组织结构id
	 * @param type 高低消费
	 * @param advancedParamList 高级查询参数
	 */
//	public void exportData(String mc,String start, Integer weekth, String pid,
//			String[] type, List<AdvancedParam> advancedParamList,HttpServletResponse response);
	/**
	 * 获取导出数据
	 * @param sendType 发送类型
	 * @param mc 标题
	 * @param start 开始时间
	 * @param weekth 周次
	 * @param pid 单位id
	 * @param advancedParamList 高级查询参数
	 * @param request 请求
	 * @return Map<String, Object>
	 */
//	public Map<String, Object> getExportData(String sendType, String mc, String start,
//			Integer weekth, String pid, List<AdvancedParam> advancedParamList,
//			HttpServletRequest request);
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
//			Integer weekth, String pid, String[] type,
//			List<AdvancedParam> advancedParamList, HttpServletRequest request,String type_);
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
	 * 保存mail发信状态
	 * @param start
	 * @param end
	 * @param weekth
	 * @param status
	 * @param deptid
	 * @param deptname
	 * @param type
	 * @param type_
	 */
//	public JobResultBean saveSendStatus(String start, String end, int weekth, int status,
//			String deptid, String deptname, String[] type, String type_);
	/**
	 * 高消费标准代码
	 * @return
	 */
	public Map<String, Object> getCostCode();
	/**
	 * 获取分页-学生明细
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 获取分页-学生明细（带高低消费类别）
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @param type 高低消费类别
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields,
			String[] type);
	/**
	 * 获取学生明细列表（导出用）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 获取学生明细列表（导出用，带高低消费类型）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @param type 高低消费类别
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields, String[] type);
	/**
	 * 查询占比和变化的趋势
	 * @param startList 学年学期集合
	 * @param weekList 月次集合
	 * @param deptid 单位id
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getNearLv(String xnxqList, String monthList,
			String deptid, List<AdvancedParam> advancedParamList);
	/**
	 * 
	 * @param startList 学年学期集合
	 * @param weekList 月次集合
	 * @param deptid 单位id
	 * @param advancedParamList 高级查询参数
	 * @param type 高低消费
	 * @param abnormal_type 异常类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getNearLv(String xnxqList, String monthList,
			String deptid, List<AdvancedParam> advancedParamList,
			String[] type, String abnormal_type);
	/**
	 * 获取月份选择数据
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getMonthData();
	/**
	 * 获取各部门高消费学生
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param month 月次
	 * @param column 排序字段
	 * @param asc 正序倒序
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCountByDept(String schoolYear, String term,
			Integer month, String column, Boolean asc,
			List<AdvancedParam> advancedParamList);
	/**
	 * 获取上月高低消费数据
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param month 月次
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> getLastMonthData(
			List<AdvancedParam> advancedParamList, String schoolYear,
			String termCode, Integer month);
	/**
	 * 获取上月高低消费数据
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param month 月次
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> getLastMonthData(List<String> deptList,
			List<AdvancedParam> advancedParamList, String[] type,
			String schoolYear, String termCode, Integer month);
	/**
	 * 获取导出的具体数据
	 * @param mc 名称
	 * @param schoolYear 学年 
	 * @param termCode 学期
	 * @param month 月次
	 * @param pid 组织机构父节点
	 * @param advancedParamList 高级查询参数
	 * @param response 
	 */
	public void exportData(String mc, String schoolYear, String termCode,
			Integer month, String pid, List<AdvancedParam> advancedParamList,
			HttpServletResponse response);
	/**
	 * 获取导出的具体数据
	 * @param mc 名称
	 * @param schoolYear 学年 
	 * @param termCode 学期
	 * @param month 月次
	 * @param pid 组织机构父节点
	 * @param type 高低消费类型
	 * @param advancedParamList 高级查询参数
	 * @param response 
	 */
	public void exportData(String mc, String schoolYear, String termCode,
			Integer month, String pid, String[] type,
			List<AdvancedParam> advancedParamList,
			HttpServletResponse response);
	/**
	 * 获取导出数据
	 * @param sendType 发送类型
	 * @param mc 标题
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param month 月次
	 * @param pid 单位id
	 * @param advancedParamList 高级查询参数
	 * @param request 请求
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getExportData(String sendType, String mc,
			String schoolYear, String termCode, Integer month, String pid,
			List<AdvancedParam> advancedParamList, HttpServletRequest request);
	/**
	 * 获取导出数据
	 * @param sendType 发送类型
	 * @param mc 标题
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param month 月次
	 * @param pid 单位id
	 * @param advancedParamList 高级查询参数
	 * @param request 请求
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getExportData(String sendType, String mc,
			String schoolYear, String termCode, Integer month, String pid,
			String[] type, List<AdvancedParam> advancedParamList,
			HttpServletRequest request);
	/**
	 * 保存mail发信状态
	 * @param start
	 * @param end
	 * @param weekth
	 * @param status
	 * @param deptid
	 * @param deptname
	 * @param type
	 */
	public JobResultBean saveSendStatus(String schoolYear, String termCode,
			int month, int status, String deptid, String deptname,
			String[] type);
	/**
	 *  获取各部门高消费学生
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param month 月次
	 * @param column 排序字段
	 * @param asc 正序倒序
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @param type 高低消费类型
	 * @return
	 */
	public Map<String, Object> getCountByDept(String schoolYear, String term,
			Integer month, String column, Boolean asc, List<String> deptList,
			List<AdvancedParam> advancedParamList, String[] type);
	/**
	 * 根据学年学期获得今天的高低消费标准
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 高低消费类型
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStandardMap(String schoolYear, String termCode,
			String[] type);
	/**
	 * 根据学年学期获得今天的高消费标准
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStandardMap(String schoolYear, String termCode);
	/**
	 * 获取数据来源截止日期
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDataEndDate();
	/**
	 * 月次集合
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getMonthList();
	
}
