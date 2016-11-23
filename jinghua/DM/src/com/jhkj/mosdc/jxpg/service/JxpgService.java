package com.jhkj.mosdc.jxpg.service;

import java.util.List;
import java.util.Map;

/**   
* @Description: TODO 教学评估二维表处理service
* @author Sunwg  
* @date 2015-1-14 下午6:23:22   
*/
public interface JxpgService {
	
	/** 
	* @Title: queryGridContent 
	* @Description: TODO 查询表格数据
	*/
	public String queryGridContent(String params);
	
	/** 
	* @Title: saveUpdateItems 
	* @Description: TODO 保存用户输入的数据
	*/
	public String saveInputItems(String params);
	
	public String invokeTest(String params);
	
	public Map invokeBefore(String params);
	/** 
	* @Title: sendChangeItemsIntoDatabaseList 
	* @Description: TODO 取出前台修改的值对应的行数据，并将修改过的值放到从数据库取出的数据中，生成新的List返回
	* @param @param params 前台参数
	* @return List<Map>
	*/
	public List<Map> sendChangeItemsIntoDatabaseList(String params);
}
