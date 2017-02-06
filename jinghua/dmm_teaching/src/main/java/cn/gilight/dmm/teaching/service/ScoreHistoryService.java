package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月30日 上午11:58:58
 */
public interface ScoreHistoryService {

	/**
	 * 获取学生历年平均绩点
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistoryYear(List<AdvancedParam> advancedParamList);

	/**
	 * 获取性别分组
	 * @param advancedParamList
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getSex(List<AdvancedParam> advancedParamList);

	/**
	 * 获取年级分组
	 * @param advancedParamList
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getGrade(List<AdvancedParam> advancedParamList);
	
}