package com.jhkj.mosdc.output.dao;

import java.util.List;

/**
 * @comments:输出组件图形通用dao层
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-6
 * @time:下午03:19:37
 * @version :
 */
public interface OutPutChartDao {

	/**
	 * 得到学生统计表--为囊括学生表所有信息的综合表
	 * @param zd 查询统计字段
	 * @param condition 查询统计条件--这个暂时简单的置为纬度
	 * 还应有个范围标识
	 * @return
	 */
	public List<Object> queryStudentStatisticsTable(String zd,String condition,String fw);
	/**
	 * 这个是临时的--用于统计学生统计表的总数的--需要的仅仅会是查询条件--即范围
	 * @return
	 */
	public String queryStudentStatisticsTableCount(String fw);
	
	/**
	 * 用于得到纬度表的纬度字段名和SQL  
	 * @param id id查询条件
	 * @return
	 */
	public List<Object> queryWdCxzdmSql(String id);
	
	/**
	 * 得到纬度集合
	 * @param wdSql
	 * @return
	 */
	public List<Object> queryWdList(String wdSql);
	
	/**
	 * 得到指标集合
	 * @param zbId
	 * @return
	 */
	public List<Object> queryZbLists(String zbId);
	
	/**
	 * 得到指标里的sql的执行结果
	 * @param sqls
	 * @return
	 */
	public List<Object> queryZbSqlResult(String sql,String condition);
	
	/**
	 * 得到纬度范围指标集合
	 * @param tableName  表名
	 * @param componentId 组件id
	 * @return
	 */
	public List<Object> queryWdFwZbList(String tableName,String componentId);
	
	
	/*******************************************************************
	 * 重新的思路~~~~~
	 */
	/**
	 * 
	 */
	List<Object> queryWdList(String componentId,String id);
	/**
	 * 得到范围集合
	 * @param componentId
	 * @return
	 */
	List<Object> queryFwList(String componentId,String id);
	/**
	 * 得到指标集合
	 * @param componentId
	 * @return
	 */
	List<Object> queryZbList(String componentId,String id);
	
	/**
	 * 遍历普通的SQL
	 * @param sql sql执行语句
	 * @return
	 */
	List<Object> queryCommonSQL(String sql);
	
	/**
	 * 遍历文本统计功能的集合
	 * @param id 文本功能id
	 * @return
	 */
	List<Object> queryWbtjgnList(String id);
	/**
	 * 用以判断同一组件id下 范围是否小于指标的查询条数
	 * @param componentId
	 * @return
	 */
	boolean isFwLessZb(String componentId);
}
