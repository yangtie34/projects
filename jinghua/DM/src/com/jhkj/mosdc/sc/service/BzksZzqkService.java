package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 本专科生资助情况
* @author Sunwg  
* @date 2014-8-18 下午2:35:36   
*/
public interface BzksZzqkService {
	
	/** 
	* @Title: queryJxjInfoByYear 
	* @Description: TODO 查询年度奖学金信息
	* @return String
	*/
	public String queryJxjInfoByYear(String json);
	
	/** 
	* @Title: queryJxjzeByYear 
	* @Description: TODO 查询年度各院系奖学金总额
	* @return String
	*/
	public String queryJxjzeByYear(String json);
	
	/** 
	* @Title: queryJxjhdrsByYear 
	* @Description: TODO 查询年度各院系奖学金获得人数
	* @return String
	*/
	public String queryJxjhdrsByYear(String json);
	
	/** 
	* @Title: queryJxjalbhdrsByYear 
	* @Description: TODO 查询年度各院系按类别分奖学金获得情况
	* @return String
	*/
	public String queryJxjalbhdrsByYear(String json);
	
	/** 
	* @Title: queryZxjInfoByYear 
	* @Description: TODO 查询年度助学金信息
	* @return String
	*/
	public String queryZxjInfoByYear(String json);
	
	/** 
	* @Title: queryZxjzeByYear 
	* @Description: TODO 查询年度各院系助学金总额
	* @return String
	*/
	public String queryZxjzeByYear(String json);
	
	/** 
	* @Title: queryZxjhdrsByYear 
	* @Description: TODO 查询年度各院系助学金获得人数
	* @return String
	*/
	public String queryZxjhdrsByYear(String json);
	
	/** 
	* @Title: queryZxjalbhdrsByYear 
	* @Description: TODO 查询年度各院系按类别分助学金获得情况
	* @return String
	*/
	public String queryZxjalbhdrsByYear(String json);
	
	/** 
	* @Title: queryXfjmqkByYear 
	* @Description: TODO 查询年度各院系学费减免申请和通过人数
	* @return String
	*/
	public String queryXfjmqkByYear(String json);
	
	/** 
	* @Title: queryZxdkInfoByYear 
	* @Description: TODO 查询年度助学贷款情况
	* @return String
	*/
	public String queryZxdkInfoByYear(String json);
	
	/** 
	* @Title: queryZxdksqqkByYear 
	* @Description: TODO 查询年度各院系助学贷款申请情况
	* @return Strign
	*/
	public String queryZxdksqqkByYear(String json);
	
	/** 
	* @Title: queryZxdkalbhdrsByYear 
	* @Description: TODO 查询年度各院系助学贷款按类别获得人数
	* @return String
	*/
	public String queryZxdkalbhdrsByYear(String json);
}
