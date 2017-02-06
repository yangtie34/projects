package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 违纪处分
 * 
 * @author xuebl
 * @date 2016年5月31日 下午4:31:25
 */
public interface PunishService {

	/**
	 * 获取标准代码
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getBzdm();
	
	/**
	 * 摘要信息
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAbstract(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 违纪、处分分布
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDistribution(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 组织机构数据 人次
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptPersonTimeList(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 组织机构数据 人数、比例
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptCountScaleList(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 月份分组数据（违纪、处分）
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getMonthList(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 年级分组数据（违纪、处分）
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getGrade(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 年龄分组数据（违纪、处分）
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAge(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 二次异动变化
	 * @param edu 学历
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getPunishAgain(String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 违纪处分学生下钻
	 * @return Map<String Object> 
	 */
	public Map<String,Object> getStuDetail(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 下载违纪处分学生信息
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 详细参数
	 * @param fields 查询字段
	 * @return
	 */
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 获取对比摘要lab
	 * @param start_year 开始年
	 * @param end_year 结束年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return
	 */
	public Map<String,Object> getLiForAdd(String start_year, String end_year, String edu,List<AdvancedParam> advancedParamList);
	/**
	 * 性别，年级，学科，民族对比
	 * @param start_year 开始学年
	 * @param end_year 结束学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return map
	 */
	public Map<String, Object> getSexAndGradeAndOther(String start_year, String end_year, String edu,String type, List<AdvancedParam> advancedParamList);
 
}