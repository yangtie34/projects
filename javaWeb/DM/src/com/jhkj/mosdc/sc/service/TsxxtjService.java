package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 图书信息统计Service
* @author Sunwg  
* @date 2014-8-13 上午10:35:15   
*/
public interface TsxxtjService {
	/** 
	* @Title: queryBookTypes 
	* @Description: TODO 查询图书分类数
	* @return String
	*/
	public String queryBookTypes(String jsonParams);
	
	/** 
	* @Title: queryBookNumber 
	* @Description: TODO 查询图书总册数
	* @return String
	*/
	public String queryBookNumber(String jsonParams);
	
	/** 
	* @Title: queryBookCosts
	* @Description: TODO 查询图书总资金投入
	* @return String
	*/
	public String queryBookCosts(String jsonParams);
	
	/** 
	* @Title: queryBookNumberByType 
	* @Description: TODO 按图书分类查询图书册数
	* @return String
	*/
	public String queryBookNumberByType(String jsonParams);
	
	/** 
	* @Title: queryBookCostByType 
	* @Description: TODO 按图书分类查询图书资金投入
	* @return String
	*/
	public String queryBookCostByType(String jsonParams);
	
	/** 
	* @Title: queryBookCostByTime 
	* @Description: TODO 按时间查询图书资金投入
	* @return String
	*/
	public String queryBookCostByTime(String jsonParams);
	
	/** 
	* @Title: queryBookJyxxByType 
	* @Description: TODO 按图书分类查询图书借阅信息
	* @return String
	*/
	public String queryBookJyxxByType(String jsonParams);
	
	/** 
	* @Title: queryBookJylxxByday 
	* @Description: TODO 查询图书借阅量信息
	* @return String
	*/
	public String queryBookJylxxByday(String jsonParams);
	
	/** 
	* @Title: queryXsjctsgqk 
	* @Description: TODO 学生进出图书馆情况
	* @param @param params
	*/
	public String queryXsjctsgqk(String params);
	
	/** 
	* @Title: queryXsjctsgqk 
	* @Description: TODO 各院系学生进出图书馆情况
	* @param @param params
	*/
	public String queryGyxxsjctsgqk(String params);
}
