package cn.gilight.business.service;

import java.util.List;
import java.util.Map;

import cn.gilight.business.model.SelfDefinedYear;

/**   
* @Description: 自定义的编码
* @author Sunwg  
* @date 2016年6月8日 下午4:11:52   
*/
public interface SelfDefinedCodeService {
	
	/** 
	* @Description: 获取自定义的年份查询列表（今年，去年，近五年，近十年）
	* @Return: List<Map<String,Object>>
	* @return 
	*/
	public List<SelfDefinedYear> getSelfDefinedYearCode();
	
	/** 
	* @Description: 获取自定义的年份查询年份（近10年）
	* @Return: List<Map<String,Object>>
	* @return 
	*/
	public List<Map<String,Object>> getYears();
	
}