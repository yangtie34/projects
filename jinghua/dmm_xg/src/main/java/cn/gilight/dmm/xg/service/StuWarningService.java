package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月15日 下午3:57:59
 */
public interface StuWarningService {

	/**
	 * 摘要信息
	 * @return Map<String,Object>
	 */
	Map<String, Object> getAbstract(String date, List<AdvancedParam> advancedParamList, String mold);

	/**
	 * 查询表格数据
	 * @param date
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptDataGrid(String date, String type, String valueType, String asc, 
			List<AdvancedParam> advancedParamList, String mold);

	/**
	 * 判断预警日期是否已学期结束
	 * @param date
	 * @return boolean
	 */
	public boolean isTermOver(String date);
	
	/**
	 * 标准代码-学年、学期
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getBzdmXnXq();

	/**
	 * 判断学年学期是否设置开始、结束时间
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getIsSetStartEndDate(String schoolYear, String termCode);
	
	/**
	 * 判断学年学期是否设置开始、结束时间
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return boolean
	 */
	public boolean isSetStartEndDate(String schoolYear, String termCode);
	
	/**
	 * 获取预警分布
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDistribution(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String mold);

	/**
	 * 获取各机构预警分布
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptDataList(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String mold);

	/**
	 * 发送邮件
	 * @param deptId 组织机构ID
	 * @param date 日期
	 * @return Map<String,Object>
	 */
	public Map<String, Object> send(String deptId, String date);
	
	/**
	 * 获取详情
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields,String mold);

	/**
	 * 获取全部详情
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getDetailAll(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields,String mold);
	
	/**
	 * 获取逃课学生分布情况
	 * @param advancedParamList 高级参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return map
	 */
	public Map<String,Object> getSkipClassByWeekDayOrClas(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String mold);
}