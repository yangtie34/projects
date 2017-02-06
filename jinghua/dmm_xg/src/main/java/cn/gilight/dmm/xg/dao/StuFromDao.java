package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface StuFromDao {
	/**
	 * 查询最小的入学年级
	 * @param deptList 标准权限
	 * @return Map<String,Object>
	 */
	public Map<String, Integer> queryMinGrade(List<String> deptList);
	/**
	 * 查询所有学生就读学历
	 * @param deptList 标准权限
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> queryStuEdu(List<String> deptList);
	/**
	 * 查询各地区学生人数
	 * @param schoolYear 当前学年 eg:2015
	 * @param deptList 标准权限
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> queryStuFrom(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id,Boolean updown,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询各地区增长数和增长率
	 * @param schoolYear 当前学年 eg:2015
	 * @param deptList 标准权限
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryGrowth(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id,Boolean updown,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询各地区各学校的来校人数
	 * @param schoolYear 当前学年 eg:2015
	 * @param deptList 标准权限
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> querySchoolTag(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id,Boolean updown,List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取除法结果
	 */
	public Double getDivisionResult(double dividend, int divisor, int maxDigits);
	/**
	 * 查询学生名单
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param from 开始学年
	 * @param to 结束学年
	 * @param edu 学历
	 * @param Id 行政区划
	 * @param updown 上钻还是下钻
	 * @param stuAdvancedList 学生高级查询参数
	 * @return String
	 */
	public String queryStuList(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id, Boolean updown, List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取中国的id
	 * @return String
	 */
	public String getChinaId();
	/**
	 * 根据id查询生源地信息sql
	 * @param Id 生源地id
	 * @return String
	 */
	public String queryNowDiv(String Id);
	/**
	 * 根据地区名称获取地区id
	 * @param mc 地区名称
	 * @return String
	 */
	public String getIdByMc(String mc);
	/**
	 * 获取某个地区的增长数，平均增长率
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param from 开始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param stuAdvancedList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getAllGrowth(int schoolYear, List<String> deptList,
			String from, String to, String edu, String Id,List<AdvancedParam> stuAdvancedList);
}
