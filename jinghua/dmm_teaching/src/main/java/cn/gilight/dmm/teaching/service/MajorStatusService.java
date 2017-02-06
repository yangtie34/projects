package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年10月21日 下午4:57:42
 */
public interface MajorStatusService {

	/**
	 * 查询学年学期
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryXn();

	/**
	 * 查询专业成绩排名（根据学分计算的成绩）
	 * @param schoolYear
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorScoreList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc);

	/**
	 * 查询专业挂科率
	 * @param schoolYear
	 * @param orderType
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorFailScaleList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc);

	/**
	 * 查询专业评教情况
	 * @param schoolYear
	 * @param orderType
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorEvaluateTeachList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc);
	
	/**
	 * 查询专业毕业就业率
	 * @param schoolYear
	 * @param orderType
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorByJyList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc);

	/**
	 * 查询专业
	 * @param year
	 * @param score
	 * @param failScale
	 * @param evaluateTeach
	 * @param by
	 * @param jy
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorSearchList(List<AdvancedParam> advancedParamList, Integer year, String score, 
			String failScale, String evaluateTeach,	String by, String jy);

	/**
	 * 查询专业成绩历年变化
	 * @param advancedParamList
	 * @param id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorScoreHis(List<AdvancedParam> advancedParamList, String id);
	/**
	 * 查询专业挂科率历年变化
	 * @param advancedParamList
	 * @param id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorFailScaleHis(List<AdvancedParam> advancedParamList, String id);
	/**
	 * 查询专业评教历年变化
	 * @param advancedParamList
	 * @param id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorEvaluateTeachHis(List<AdvancedParam> advancedParamList, String id);
	/**
	 * 查询专业毕业历年变化
	 * @param advancedParamList
	 * @param id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorByHis(List<AdvancedParam> advancedParamList, String id);
	/**
	 * 查询专业就业历年变化
	 * @param advancedParamList
	 * @param id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMajorJyHis(List<AdvancedParam> advancedParamList, String id);
	
}