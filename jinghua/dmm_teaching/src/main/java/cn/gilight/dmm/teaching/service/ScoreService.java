package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午12:11:14
 */
public interface ScoreService {


	/**
	 * 查询标准代码
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getBzdm();
	
	/**
	 * 获取概况数据
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 学历
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAbstract(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu);

	/**
	 * 获取绩点分组
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 学历
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getGroup(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu);

	/**
	 * 获取成绩比例
	 * @param advancedParamList
	 * @param schoolYear
	 * @param termCode
	 * @param edu
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getScale(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu);
	
	/**
	 * 不同 课程属性、课程性质分组的绩点类型数据
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 学历
	 * @param type 类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getGpaCourse(List<AdvancedParam> advancedParamList, 
			String schoolYear, String termCode, String edu, String type);

	/**
	 * 获取用户 显示的等级类型
	 * @return String
	 */
	public String getDisplayedLevelType(List<AdvancedParam> advancedParamList, String schoolYear);

	/**
	 * 查询列表数据
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 学历
	 * @param type 类型
	 * @param order 排序字段
	 * @param asc 排序
	 * @param index 页数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getGridList(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, 
			String edu, String type, String order, String asc, Integer index);

	/**
	 * 获取查询 成绩/绩点 所需要的参数
	 * @param stuSql 学生sql
	 * <br> { select * from t_stu }
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param scoreSql 学生成绩sql（自定义的scoreSql，可为null）【在需要自定义scoreSql时】
	 * <br> { select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t }
	 * @param target 处理的数据指标（avg、middle....可为null）【如果为null，处理所有数据指标，且采用实时计算成绩表策略】
	 * @param businessAdvancedList 高级查询-业务参数
	 * @return Map<String,Object>
	 * <br> { valueList:'', scoreSql:'', betterSql:'', failSql:'', rebuildSql:'' }
	 */
	public Map<String, Object> getParamMapForScoreOrGpa(String stuSql, String schoolYear, String termCode, 
			String scoreSql, String target, List<AdvancedParam> businessAdvancedList);
	
	/**
	 * 根据数据参数获取 数据值 
	 * @param valueList 数据值列表（平均数、中位数、众数、方差、标准差、低于平均数时必需）
	 * @param scoreSql 学生成绩sql（优秀率、挂科率、重修率时必需）
	 * @param betterSql 优秀学生成绩sql（优秀率）
	 * @param failSql 挂科学生成绩sql（挂科率）
	 * @param rebuildSql 重修学生成绩sql（重修率）
	 * @param target 处理的数据指标（avg、middle....；如果为null，处理所有数据指标）
	 * @return Double
	 */
	public Double getScoreValueByTarget(List<Double> valueList, String scoreSql, 
			String betterSql, String failSql, String rebuildSql, String target);
	
	/**
	 * 根据数据参数获取 数据值 
	 * @param valueList 数据值列表（平均数、中位数、众数、方差、标准差、低于平均数时必需）
	 * @param scoreSql 学生成绩sql（优秀率、挂科率、重修率时必需）
	 * @param betterSql 优秀学生成绩sql（优秀率）
	 * @param failSql 挂科学生成绩sql（挂科率）
	 * @param rebuildSql 重修学生成绩sql（重修率）
	 * @param target 处理的数据指标（avg、middle....；如果为null，处理所有数据指标）
	 * @return Map<String,Object>
	 * <br> { avg:'', middle:'', mode:'', fc:'', bzc:'', better:'', fail:'', rebuild:'', under:'' }
	 */
	public Map<String, Object> getScoreValueMapByTarget(List<Double> valueList, String scoreSql, 
			String betterSql, String failSql, String rebuildSql, String target);
	
	/**
	 * 根据数据参数设置 数据值 
	 * @param valueList 数据值列表（平均数、中位数、众数、方差、标准差、低于平均数时必需）
	 * @param scoreSql 学生成绩sql（优秀率、挂科率、重修率时必需）
	 * @param betterSql 优秀学生成绩sql（优秀率）
	 * @param failSql 挂科学生成绩sql（挂科率）
	 * @param rebuildSql 重修学生成绩sql（重修率）
	 * @param target 处理的数据指标（avg、middle....；如果为null，处理所有数据指标，排除firstOrder）
	 * @param firstOrder 第一次处理的数据指标（类型同上；在处理数据时会排除这个指标）
	 * @return Map<String,Object>
	 * <br> { avg:'', middle:'', mode:'', fc:'', bzc:'', better:'', fail:'', rebuild:'', under:'' }
	 */
	public Map<String, Object> getScoreValueMapByTarget(List<Double> valueList, String scoreSql, 
			String betterSql, String failSql, String rebuildSql, String target, String firstOrder);
	/**
	 * 查询成绩历史列表数据
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 学历
	 * @param type 类型
	 * @param order 排序字段
	 * @param asc 排序
	 * @param index 页数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistoryGridList(
			List<AdvancedParam> advancedParamList, String schoolYear,
			String termCode, String edu, String type, String order, String asc,
			Integer index);
	
}