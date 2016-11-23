package com.jhkj.mosdc.output.process;

import java.util.Map;

/**
 * @comments:输出页面-统计细粒度service
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-6
 * @time:上午10:51:33
 * @version :
 */
public interface OutPutCommonProcess {

	/**
	 * 用于得到简单数据请求的统计结果的方法接口 请暂时不要使用此方法
	 * @deprecated
	 * @param params
	 * @return
	 */
	public String queryResultForChartSimple(String params);

	/**
	 * 用于得到复杂数据请求的统计结果的方法接口  请暂时不要使用此方法
	 * @deprecated
	 * @param params
	 * @return
	 */
	String queryResultForChartComplex(String params);

	/**
	 * 多种自定义指标 固定范围的情况下   请暂时不要使用此方法
	 * @deprecated
	 * @param params
	 * @return
	 */
	String queryResultManyToOne(String params);

	/**********************************************************************
	 * 新思路~~~~~
	 */

	/**
	 * 得到通用图形结果
	 */
	Map<String,Object> queryCommonChartResult(String params);

	/**
	 * 得到文本的内容
	 * 
	 * @param params
	 *            参数格式：初始化时无联动组件参数，初始化格式：{
	 *            'componid':'xxxx','init':'true'}
	 *            非初始化时格式
	 *            ：{componid:'xxx','ld':'此项可选'}
	 * @return
	 */
	Map<String,Object> queryTextResult(String params);

	/**
	 * 得到文本点击后grid显示的数据
	 * 
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	String queryGridResult(String params);

	/**
	 * 测试
	 * @deprecated
	 * @return
	 */
	String querytest(String params);
}