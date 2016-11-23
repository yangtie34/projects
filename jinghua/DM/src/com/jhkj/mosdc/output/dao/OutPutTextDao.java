package com.jhkj.mosdc.output.dao;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.bean.PageParam;

/**
 * @comments:输出-文本dao层接口
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-12
 * @time:下午05:12:45
 * @version :
 */
public interface OutPutTextDao {

	/**
	 * 得到文本的集合
	 * 
	 * @param componId
	 * @return
	 */
	public List<Object> queryTextList(String componId);

	/**
	 * 得到文本关联表的集合
	 * 
	 * @param textId
	 * @return
	 */
	public List<Object> queryGlTextList(String textId);

	/**
	 * 值
	 * 
	 * @param sql
	 * @return
	 */
	public List<Object> queryListValue(String sql);

	/**
	 * 得到文本组件的弹窗grid的数据集合
	 * 
	 * @param gridView
	 *            grid视图
	 * @param conditionList
	 *            范围和条件集
	 * @param seniorQuery
	 *            高级查询条件
	 * @param start
	 *            开始条数
	 * @param limit
	 *            分页条数
	 * @return
	 */
	public List<Map> queryGridListForText(String gridView,
			List<String> conditionList, String seniorQuery, PageParam pageParam);

}
