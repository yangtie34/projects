package cn.gilight.framework.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;


/** 
* @ClassName: BaseDaoImpl 
* @Description: TODO 持久层基础操作类
* @date 2016年3月7日 22:44:09   
*/
@Component("baseDao")
public class BaseDaoImpl implements BaseDao{
	private final transient Log log = LogFactory.getLog(BaseDaoImpl.class);
	/*获取Spring配置的jdbcTemplate*/
	@Resource
	JdbcTemplate jdbcTemplate;

	/*根据查询sql返回结果集*/
	public List<Map<String,Object>> queryForList(String queryString) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(queryString);
		return list;
	}
	
	/*根据查询sql返回结果集*/
	public SqlRowSet queryForResultSet(String queryString) {
		return jdbcTemplate.queryForRowSet(queryString);
	}
	/*根据查询sql返回结果集*/
	public List<Map<String,Object>> queryForList(String queryString, Object... obj) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(queryString, obj);
		return list;
	}
	
	/*根据查询sql查询数据量-封装count*/
	public int queryForInt(String sql){
		String countSql = "select count(1) from ("+sql+")";
		return jdbcTemplate.queryForInt(countSql);
	}
	/*根据查询sql查询数据量-未封装*/
	public int queryForIntBase(String sql){
		return jdbcTemplate.queryForInt(sql);
	}
	/*根据sql插入数据*/
	public void insert(String sql) {
			jdbcTemplate.execute(sql);
	}
	/* 使用prepareStatement来传递参数进行更新数据 */
	public int update(String sql, PreparedStatementSetter preparedStatementSetter) {
		return jdbcTemplate.update(sql,preparedStatementSetter);
	}
	
	/* 使用prepareStatement来传递参数进行更新数据 */
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter preparedStatementSetter) {
		return jdbcTemplate.batchUpdate(sql,preparedStatementSetter);
	}
	/*根据sql更新数据*/
	public int update(String sql) {
			return jdbcTemplate.update(sql);
	}
	/*根据sql更新数据*/
	public int update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
	}
	/*根据sql更新数据*/
	public int update(String sql, Object[] args, int[] argTypes) {
		return jdbcTemplate.update(sql, args, argTypes);
	}
	/*根据dmlSql更新数据库，返回执行结果*/
	public int delete(String sql) {
		int result = jdbcTemplate.update(sql);
		return result;
	}
	
	/*根据查询sql进行分页查询，返回结果集*/
	public Map<String,Object> createPageQuery(String queryString, Page page) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select * from (select p_m.*, rownum nm from (");
		sbf.append(queryString);
		sbf.append(" )p_m where rownum < "+ page.getCurpage()*page.getPagesize());
		sbf.append(" )p_n where p_n.nm > "+(page.getCurpage()-1)*page.getPagesize());
		log.debug("page query sql:"+sbf.toString());
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sbf.toString());
		Map<String,Object> results = new HashMap<String, Object>();
		page.setSumcount(this.queryForInt(queryString));
		results.put("total", page.getSumcount());
		results.put("rows", list);
		return results;
	}
	
	
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * return List<Map>
	 */
	@SuppressWarnings({ "rawtypes","unchecked" })
	public List<Map<String, Object>> queryListInLowerKey(String sql) {
		return jdbcTemplate.query(sql, new RowMapper() {
			public ResultSetMetaData rsm = null;
			public int count = 0;
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				if (rsm == null) {
					rsm = rs.getMetaData();
					count = rsm.getColumnCount();
				}
				HashMap map = new HashMap();
				for (int i = 0; i < count; i++) {
					String columnName = rsm.getColumnName((i + 1)).toLowerCase();
					int sqlType = rsm.getColumnType(i + 1);
					Object sqlView = rs.getObject(columnName);
					switch (sqlType) {
					case Types.CHAR:
						map.put(columnName, sqlView.toString().trim());
						break;
					case Types.NUMERIC:
						if (sqlView == null)
							sqlView = "";
						map.put(columnName, sqlView.toString());
						break;
					default:
						map.put(columnName,
						sqlView != null ? sqlView.toString() : "");
						break;
					}
				}
				return map;
			}
		});
	}

	public Page queryWithPageInLowerKey(String sql, Page page) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select * from (select p_m.*, rownum nm from (");
		sbf.append(sql);
		sbf.append(" )p_m )p_n where p_n.nm > "+(page.getCurpage()-1)*page.getPagesize() + " and p_n.nm <="+page.getCurpage()*page.getPagesize());
		log.debug("page query sql:"+sbf.toString());
		List<Map<String, Object>> list = this.queryListInLowerKey(sbf.toString());
		page.setSumcount(this.queryForInt(sql));
		int pageCount = 0;
		if(page.getSumcount()%page.getPagesize() != 0){
			pageCount = page.getSumcount()/page.getPagesize() +1;
		}else{
			pageCount = page.getSumcount()/page.getPagesize();
		}
		page.setPagecount(pageCount);
		page.setResult(list);
		return page;
	}
}
