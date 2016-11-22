package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 在籍学生素质统计
* @author Sunwg  
* @date 2014-6-9 下午3:38:22   
*/
public interface ZjxssztjService {

	/**
	 * 查询在籍学生入学学历
	 * @return 
	 */
	public String queryRxxl(String params);
	
	/**
	 * 查询在籍学生培养层次
	 * @return 
	 */
	public String queryPycc(String params);
	
	/**
	 * 查询在籍学生学制
	 * @return 
	 */
	public String queryXz(String params);
}
