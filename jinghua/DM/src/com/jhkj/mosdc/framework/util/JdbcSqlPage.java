package com.jhkj.mosdc.framework.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import com.jhkj.mosdc.framework.bean.PageParam;


/**
 * 
 * @DATE:2010-6-29 @TIME: 下午01:32:40
 * @Comments:
 * 扩展org.springframework.jdbc.core.JdbcTemplate
 * <br>增加了分页功能
 */

public class JdbcSqlPage extends org.springframework.jdbc.core.JdbcTemplate{
	protected final Logger log = Logger.getLogger(this.getClass());
	private JdbcTemplate jdbcTemplate ;
	
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	/**
	 * 止前仅适用于简单查询，未考虑union及嵌套查询的情况
	 * @param sql 传入的SQL语句
	 * @param pageNo	页号,从1开始.
	 * @param pageSize	分页大小
	 * @param values sql中需要传入的参数
	 * @param rowMapper	查询出的值与向页面传输对象的映射
	 * @return	含总记录数和当前页数据的Page对象
	 */
	public Page pagedQuery(String sql, Object[] values,RowMapper rowMapper, int pageNo, int pageSize ) {
		Assert.hasText(sql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		
		// Count查询
		String  queryString = removeSelect(removeOrders(sql));
		StringBuffer countQueryString = new StringBuffer("select count(*) ");
		countQueryString.append(removeGroupBy(queryString));
		this.log.debug("### count sql :"+countQueryString);
		long totalCount = this.queryForInt(countQueryString.toString(), values );
		
		if (totalCount < 1)
			return new Page();

		//组合select sql
		int orderBeginPos = sql.toLowerCase().indexOf("order");
		String orderByStr = "";
		if(-1 != orderBeginPos) 
			orderByStr = sql.substring(orderBeginPos);
//		this.log.debug("@@get order by str :"+  orderByStr);
		
		int fromBeginPos = sql.toLowerCase().indexOf("from");
		String rsnum = " ROW_NUMBER() over ( "+orderByStr +" ) as rsnum ";
		String selectStr = sql.substring(0, fromBeginPos)+" ," + rsnum;
		this.log.debug("@@get select str :"+  selectStr);
		
		int startIndex = (pageNo - 1) * pageSize;
		StringBuffer pageSql = new StringBuffer("select t_.* from ( "); 
		pageSql.append(selectStr);
		pageSql.append(queryString);
		pageSql.append(orderByStr);
		pageSql.append(" ) as t_  where t_.rsnum  between ");
		pageSql.append(startIndex+1);
		pageSql.append(" and ");
		pageSql.append(startIndex+pageSize);
//		this.log.debug("@@get pageSQL :" + pageSql);
		
		// 实际查询返回分页对象
		List list = this.query(pageSql.toString(), values, rowMapper);
		
		return  new Page(totalCount, pageSize, list);
	}
	
	/**
	 * 功能描述：分页查询
	 * @param sql：查询语句
	 * @param countSql：查询语句sql的记录条数语句（相当于 select count(*) from tablename）
	 * @param rowMapper
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page pagedQuery(String sql,String countSql,RowMapper rowMapper, int pageNo, int pageSize ) {
		Assert.hasText(sql);
		Assert.hasText(countSql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		
		// Count查询
		long totalCount = this.queryForLong(countSql);
		
		if (totalCount < 1)
			return new Page();
		
		//组合select sql
		int orderBeginPos = sql.toLowerCase().indexOf("order");
		String orderByStr = "";
		if(-1 != orderBeginPos) 
			orderByStr = sql.substring(orderBeginPos);
		this.log.debug("@@get order by str :"+  orderByStr);
		
		int startIndex = (pageNo - 1) * pageSize;
		StringBuffer pageSql = new StringBuffer("select t_.* from ( ");
		pageSql.append(" select t_n.* , ROW_NUMBER() over (");
		pageSql.append(orderByStr);
		pageSql.append(") as rsnum from (");
		pageSql.append(sql);
		pageSql.append(" ) t_n");
		pageSql.append(") as t_  where t_.rsnum  between ");
		pageSql.append(startIndex+1);
		pageSql.append(" and ");
		pageSql.append(startIndex+pageSize);
		
		this.log.debug("@@get pageSQL :" + pageSql);
		
		// 实际查询返回分页对象
		List list = this.query(pageSql.toString(),rowMapper);
		return  new Page(totalCount, pageSize, list);
	}
	
	/**
	 * 功能描述：分页查询
	 * @param sql：查询语句
	 * @param countSql：查询语句sql的记录条数语句（相当于 select count(*) from tablename）
	 * @param rowMapper
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page pagedQueryForExt(String sql,String countSql,RowMapper rowMapper, int start, int limit ) {
		Assert.hasText(sql);
		Assert.hasText(countSql);
		Assert.isTrue(start >= 0, "pageNo should start from 1");
		
		// Count查询
		long totalCount = this.queryForLong(countSql);
		
		if (totalCount < 1)
			return new Page();
		
		//组合select sql
		int orderBeginPos = sql.toLowerCase().indexOf("order");
		String orderByStr = "";
		if(-1 != orderBeginPos) 
			orderByStr = sql.substring(orderBeginPos);
//		this.log.debug("@@get order by str :"+  orderByStr);
		
		StringBuffer pageSql = new StringBuffer("select t_.* from ( " );
			pageSql.append(" select t_n.* , ROW_NUMBER() over (").append(orderByStr).append(") as rsnum from (").append(sql).append(" ) t_n");
			
			pageSql.append(")  t_  where t_.rsnum  between ").append(start+1).append(" and ").append(start+limit);
		
//		this.log.debug("@@get pageSQL :" + pageSql);
		
		// 实际查询返回分页对象
		List list = this.query(pageSql.toString(),rowMapper);
		return  new Page(totalCount, limit, list);
	}
	
	/**
	 * 止前仅适用于简单查询，未考虑union的情况
	 * @param sql	sql语句
	 * @param values	参数数组
	 * @param argTypes 参数的数据类型  java.sql.Types  如：Types.VARCHAR; Types.SMALLINT; 
	 * @param rowMapper sql转对象的映射转换器
	 * @param pageNo 页号,从1开始.
	 * @param pageSize 分页大小
	 * @return 含总记录数和当前页数据的Page对象
	 */
	public Page pagedQuery(String sql, Object[] values, int[] argTypes ,RowMapper rowMapper, int pageNo, int pageSize ) {
		Assert.hasText(sql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		
		// Count查询
		String  queryString = removeSelect(removeOrders(sql));
		String countQueryString = " select count (*) " + queryString;
		this.log.debug("@@ count sql :"+countQueryString);
		long totalCount = this.queryForLong(countQueryString, values);
		
		if (totalCount < 1)
			return new Page();

		//组合select sql
		int orderBeginPos = sql.toLowerCase().indexOf("order");
		String orderByStr = "";
		if(-1 != orderBeginPos) 
			orderByStr = sql.substring(orderBeginPos);
		this.log.debug("@@get order by str :"+  orderByStr);
		
		int fromBeginPos = sql.toLowerCase().indexOf("from");
		String rsnum = " ROW_NUMBER() over ( "+orderByStr +" ) as rsnum ";
		String selectStr = sql.substring(0, fromBeginPos)+" ," + rsnum;
		this.log.debug("@@get select str :"+  selectStr);
		
		int startIndex = (pageNo - 1) *  pageSize;
		StringBuffer pageSql = new StringBuffer("select t_.* from ( ");
		pageSql.append(selectStr).append(countQueryString).append(orderByStr).append(" ) as t_  where t_.rsnum  between ").append(startIndex+1).append(" and ").append(startIndex+pageSize);
//		this.log.debug("@@get pageSQL :" + pageSql);
		
		// 实际查询返回分页对象
		List list = this.query(pageSql.toString(), values , argTypes, rowMapper);
		
		return  new Page(totalCount, pageSize, list);		
	}
	
	
		
	/**
	 * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String sql) {
		Assert.hasText(sql);
		int beginPos = sql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " sql : " + sql + " must has a keyword 'from'");
		return sql.substring(beginPos);
	}
	
	/**
	 * 去除sql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String sql) {
		Assert.hasText(sql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 去除sql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeGroupBy(String sql) {
		Assert.hasText(sql);
		Pattern p = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * oracle分页设置
	 * @param sql
	 * @param start
	 * @param limit
	 * @return
	 */
	private static String[] oraclePageSql(String sql,int start,int limit){
		String[] sqlList = new String[2];
		Assert.hasText(sql);
		Assert.isTrue(start >= 0, "pageNo should start from 1");
		
		// Count查询
		String  queryString = removeSelect(sql);
		StringBuffer countQueryString = new StringBuffer("select count(*) from ( ").append(sql).append(" ) ");
//		long totalCount = this.queryForInt(countQueryString, values );
		sqlList[0] = new String(countQueryString.toString());
//		if (totalCount < 1)
//			return new Page();

		//组合select sql
		int orderBeginPos = sql.toLowerCase().indexOf("order");
		String orderByStr = "";
		if(-1 != orderBeginPos) 
			orderByStr = sql.substring(orderBeginPos);
		int fromBeginPos = sql.toLowerCase().indexOf("from");
//		int fromBeginPos = sql.toLowerCase().lastIndexOf("from");
		String rsnum = " ROWNUM as rsnum ";
//		String rsnum = " ROW_NUMBER() over ( "+orderByStr +" ) as rsnum ";
		String selectStr = sql.substring(0, fromBeginPos)+" ," + rsnum;
		
//		int startIndex = (pageNo - 1) * pageSize;
		StringBuffer pageSql = new StringBuffer("select t_.* from ( ").append(selectStr).append(queryString).append(" ) t_  where t_.rsnum  between ").append(start+1).append(" and ").append(start+limit);
		sqlList[1] = new String(pageSql.toString());
		return sqlList;
	}
	
	/**
	 * mysql分页设置
	 * @param sql
	 * @param start
	 * @param limit
	 * @return
	 */
	private static String[] mysqlPageSql(String sql,int start,int limit){
		return null;
	}
	
	/**
	 * 根据不同数据库生成不同分页SQL
	 * @param sql 原始SQL不带分页
	 * @param start
	 * @param limit
	 * @return String
	 */
	public static String[] pageSql(String sql,int start,int limit){
		String[] sqlPage = null;
		//获取properties文件中的数据库类型
		String dataSys = PropertiesUtils.getDataBase();
		//判断数据库类型是否为oracle
		if("oracle".equals(dataSys)){
			sqlPage = oraclePageSql(sql,start,limit);
		//判断数据库类型是否为oracle
		}else if("mysql".equals(dataSys)){
			sqlPage = mysqlPageSql(sql,start,limit);
		}
		return sqlPage;
	}

}
