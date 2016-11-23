package com.jhkj.mosdc.framework.message.service;

/**
 * 用于收集用户的意见和建议
 * @author Administrator
 *
 */
public interface SuggestService {
	/**
	 * 添加建议
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String addSuggest(String params) throws Exception;
	/**
	 * 修改建议
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updateSuggest(String params);
	/**
	 * 删除建议
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String deleteSuggest(String params);
	/**
	 * 查询我的建议
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryMineSuggest(String params) throws Exception;
	/**
	 * 查询所有建议
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryAllSuggest(String params) throws Exception;
}
