package com.jhkj.mosdc.output.service;

/**
 * @comments:统计功能-自定义统计页面Serivce接口
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-12-4
 * @time:下午06:01:41
 * @version :
 */
public interface TbTjgnZdytjymService {

	/**
	 * 新增保存自定义统计页面数据
	 * 
	 * @param params
	 * @return
	 */
	public String saveTjgnZdytjym(String params);

	/**
	 * 修改更新自定义统计页面数据
	 * 
	 * @param params
	 * @return
	 */
	public String updateTjgnZdytjym(String params);

	/**
	 * 删除自定义统计页面数据
	 * 
	 * @param params
	 * @return
	 */
	public String deleteTjgnZdytjym(String params);

	/**
	 * 遍历查询自定义统计页面数据
	 * 
	 * @param params
	 * @return
	 */
	public String queryTjgnZdytjym(String params);

	/**
	 * 管理员审核更新方法
	 * 
	 * @param params
	 * @return
	 */
	public String updateTjgnZdytjymSftgSh(String params);

	/**
	 * 通过id加载数据
	 * 
	 * @param params
	 * @return
	 */
	public String loadTjgnZdytjymById(String params);

	/**
	 * 遍历组件数据
	 * 
	 * @param params
	 * @return
	 */
	public String queryZjData(String params);

}
