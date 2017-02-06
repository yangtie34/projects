package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface BysQxService {
    /**
     * 查询学年选择集合
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getTimeList();
    /**
     * 根据学年，高级查询参数获取毕业生去向分布
     * @param advancedList 高级查询参数
     * @param schoolYear 学年
     * @return List<Map<String, Object>> 
     */
	public List<Map<String, Object>> getBysQxFb(List<AdvancedParam> advancedList,
			String schoolYear);
	/**
     * 根据学年，高级查询参数获取毕业生升造去向分布
     * @param advancedList 高级查询参数
     * @param schoolYear 学年
     * @return List<Map<String, Object>> 
     */
	public List<Map<String, Object>> getBysQxSzFb(List<AdvancedParam> advancedList,
			String schoolYear);
	/**
	 * 近五年毕业生去向占比分布
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLnBysQxfb(List<AdvancedParam> advancedList,
			String schoolYear);
	/**
	 * 近五年毕业生升造去向占比分布
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLnBysSzQxfb(List<AdvancedParam> advancedList,
			String schoolYear);
	/**
	 * 历年未就业主要原因占比分布
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLnReasonfb(List<AdvancedParam> advancedList,
			String schoolYear);
	/**
	 * 查询各单位毕业生去向占比分布
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getScaleByDept(List<AdvancedParam> advancedList,
			String schoolYear);
	/**
	 * echarts数据转化
	 * @param result 结果
	 * @param a 图例
	 * @param b x轴
	 * @param c 图例id
	 * @param d x轴id
	 * @return Map<String, Object>
	 */
	public Map<String, Object> shiftList(List<Map<String, Object>> result, String a,
			String b, String c, String d);
	/**
	 * 根据学年和长度获得学年集合
	 * @param schoolYear 学年
	 * @param Length 长度
	 * @param asc 排序
	 * @return  List<String> 
	 */
	public List<String> getListByYear(String schoolYear, int Length, Boolean asc);
	/**
	 * 获取学生明细(分页数据)
	 * @param advancedParamList
	 * @param page
	 * @param keyValue
	 * @param fields
	 * @return
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 获取学生明细(导出用数据)
	 * @param advancedParamList
	 * @param keyValue
	 * @param fields
	 * @return
	 */
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields);
	

}
