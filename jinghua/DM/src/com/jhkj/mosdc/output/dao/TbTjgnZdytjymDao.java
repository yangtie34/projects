package com.jhkj.mosdc.output.dao;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.jhkj.mosdc.framework.bean.PageParam;

/**
 * @comments:表—统计功能-自定义统计页面dao层接口
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-12-4
 * @time:下午05:57:30
 * @version :
 */
public interface TbTjgnZdytjymDao {

	/**
	 * 保存自定义页面数据
	 * 
	 * @param map
	 */
	public Boolean saveTjgnZdytjym(HashMap<String, Object> map);

	/**
	 * 遍历自定义页面数据
	 * 
	 * @param conditionList
	 *            查询条件
	 * @param pageParam
	 *            分页参数
	 * @return
	 */
	public List queryTjgnZdytjym(List<String> conditionList,String seniorStr, PageParam pageParam);

	/**
	 * 删除方法
	 * 
	 * @param ids
	 *            用于删除的id集合
	 * @return
	 */
	public Boolean deleteTjgnZdytjym(JSONArray ids);

	/**
	 * 用于修改操作
	 * @param map 前台传递的map形式数据集合
	 * @return
	 */
	public boolean updateTjgnZdytjym(HashMap<String, Object> map);

	/**
	 * 通过id查找加载自定义统计页面（用于查看修改）
	 * 
	 * @param id
	 * @return
	 */
	public List loadTjgnZdytjymById(Long id);

	/**
	 * 更新自定义统计页面的是否审核通过的标志
	 * 
	 * @param ids
	 *            自定义页面的id集合
	 * @param sftgsh
	 *            是否通过审核
	 */
	public Boolean updateTjgnZdytjymSftgSh(JSONArray ids, String sftgsh);

	/**
	 * 用于得倒是否通过审核标志的id
	 * 
	 * @param shzt
	 *            审核状态 1 代表审核通过 0 代表退回
	 * @return
	 */
	public String getSftgshFlag(String shzt);

	/**
	 * 遍历组件表内的数据（目前仅限于查询id mc componentType三种,如有需要再添加）
	 * 
	 * @param params
	 *            查询参数(仅用于名称查询)
	 * @param pageParam
	 *            分页参数
	 * @return
	 */
	public List queryZjData(String params, PageParam pageParam);

}
