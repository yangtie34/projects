package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface BysScoreService {

	 /**
	  * 获取成绩测试数据
	  * @param keyValue 参数
	  * @param advancedParamList 高级查询参数
	  * @return
	  */
	public Map<String, Object> getScoreTest(Map<String, Object> keyValue,
			List<AdvancedParam> advancedParamList);

	/**
	 * 获取课程列表
	 * @return
	 */
	public Map<String, Object> getCourseList();
    /**
     * 查询就读学历集合
     * @return Map<String,Object>
     */
	public Map<String, Object> getEduList();
    /**
     * 查询届别集合
     * @return Map<String, Object> 
     */
	public Map<String, Object> getPeriodList();
    /**
     * 查询时间段选择集合
     * @return Map<String,Object>
     */
	public Map<String, Object> getDateList();
    /**
     * 根据高级查询参数和学历查询学生所有的学制
     * @param advancedParamList 高级查询参数
     * @param edu 就读学历
     * @return Map<String,Object>
     */
	public Map<String, Object> getXzList(List<AdvancedParam> advancedParamList,
			String edu);
    /**
     * 查询成绩变化图数据
     * @param advancedParamList 高级查询参数
     * @param edu 就读学历
     * @param date 届别
     * @param xz 学制
     * @param scoreType 成绩类型
     * @param target 成绩指标
     * @return Map<String, Object>
     */
	public Map<String, Object> getScoreLine(List<AdvancedParam> advancedParamList,
			String edu, String date, Integer xz, String scoreType, String target);

	/**
	 * 获取某届学生成绩分布
	 * @param advancedParamList 高级查询参数
	 * @param period 届别
	 * @param xz 学制
	 * @param edu 就读学历
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getScoreFb(List<AdvancedParam> advancedParamList,
			Integer period, Integer xz, String edu);
    /**
     * 查询成绩指标集合
     * @return  Map<String, Object>
     */
	public Map<String, Object> getTargetList();
    /**
     * 查询成绩类型集合
     * @return Map<String, Object>
     */
	public Map<String, Object> getScoreTypeList();
    /**
     * 获取成绩分组规则
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getScoreGroup();

}
