package cn.gilight.research.xkjs.service;

import java.util.List;
import java.util.Map;

public interface XkjsService {
	
	/** 
	* @Description: 查询学科人员组成
	* @param year
	* @return: Map<String, Object>
	*/
	public Map<String, Object> queryXkryzc(String year);
	
	/** 
	* @Description: 计算学科人员组成
	*/
	public void calculateXkryzc();
	
	/** 
	* @Description: 根据年份查询学科建设应完成指标明细
	* @param year
	* @return: Map<String,Object>
	*/
	public Map<String, Object> queryYwczbOfYear(String year);
	
	/** 
	* @Description: 根据年份查询学科建设指标完成率
	* @param year
	* @return: Map<String,Object>
	*/
	public Map<String, Object> queryZbwclOfYear(String year);

	/** 
	 * @Description: 学科业绩指标进展
	 * @param year
	 * @return: Map<String,Object>
	 */
	public List<Map<String, Object>> queryZbjzOfYear(String year);

	/** 
	* @Description: 各学科分项指标建设进展
	* @param year
	* @param zbid
	* @return: Map<String,Object>
	*/
	public Map<String, Object> queryGxkzbjzOfYearAndZb(String year,String zbid);

	/** 
	* @Description: 查询指标列表
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String, Object>> queryZblist();

	/** 
	* @Description: 计算指标完成数据
	*/
	public void calculateZbwcsj();
	
	/**
	* @Description: 查询个人指标完成率
	*/
	public List<Map<String, Object>> queryGrzbwcl(String year,String zgh,String xkid);

	/**
	* @Description: 查询个人职工号
	*/
	public List<Map<String, Object>> queryGrzgh(String xkid);

	/**
	* @Description: 查询各学科名称
	*/
	public List<Map<String, Object>> queryXkmc();
	
}
