package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 仪器设备信息
* @author Sunwg  
* @date 2014-7-31 下午4:19:16   
*/
public interface YqsbxxService {

	/**
	 * 查询学校仪器概况
	 * @return 
	 */
	public String queryYqgk(String params);
	
	/** 
	* @Title: queryYaly 
	* @Description: TODO 按照来源查询仪器设备数量
	*/
	public String queryYqly(String json);
	
	/** 
	* @Title: queryYqlyByYx 
	* @Description: TODO 分院系查询设备来源信息
	*/
	public String queryYqlyByYx(String json);
	
	/** 
	* @Title: queryYaly 
	* @Description: TODO 按照用途查询仪器设备数量
	*/
	public String queryYqyt(String json);
	
	/** 
	* @Title: queryYqytByYx 
	* @Description: TODO 分院系查询设备用途信息
	*/
	public String queryYqytByYx(String json);
	
	/** 
	* @Title: queryYaly 
	* @Description: TODO 按照状态查询仪器设备数量
	*/
	public String queryYqzt(String json);
	
	/** 
	* @Title: queryYqlyByYx 
	* @Description: TODO 分院系查询设备状态信息
	*/
	public String queryYqztByYx(String json);
	
	/** 
	* @Title: queryYzzkByYx 
	* @Description: TODO 查询各院系的仪器设备状况信息
	*/
	public String queryYzzkByYx(String json);
	
	/** 
	* @Title: querySbjzqj 
	* @Description: TODO 查询仪器设备价值区间情况
	*/
	public String querySbjzqj(String json);
	/**
	 * 设备经费组成
	 * @param params
	 * @return
	 */
	public String querySbjfzc(String params);
	/**
	 * 设备单位类别
	 * @param params
	 * @return
	 */
	public String querySbdwlb(String params);
	/** 
	* @Title: querySbflxx 
	* @Description: TODO 查询设备分类信息
	* @return String
	*/
	public String querySbflxx(String json);
	
	/** 
	* @Title: queryZcxxList 
	* @Description: TODO 查询资产信息列表
	* @return String
	*/
	public String queryZcxxList(String params);
	
	/** 
	* @Title: updateZcxx 
	* @Description: TODO 修改资产信息列表
	* @return String
	*/
	public String updateZcxx(String params);
	
	/** 
	* @Title: queryZcztgk 
	* @Description: TODO 查询资产总体概况
	* @return String
	*/
	public String queryZcztgk(String params);
}
