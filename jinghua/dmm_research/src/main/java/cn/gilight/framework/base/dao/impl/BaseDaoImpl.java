package cn.gilight.framework.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

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
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryForList(String queryString, Integer... columnTypes) {
		return jdbcTemplate.query(queryString, new ResultMapper(columnTypes));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T> queryForListBean(String sql, Class<T> elementType){
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(elementType));
	}
	
	@Override
	public List<String> queryForListString(String sql) {
		return jdbcTemplate.queryForList(sql, String.class);
	}
	
	/*根据查询sql返回结果集*/
	public SqlRowSet queryForResultSet(String queryString) {
		return jdbcTemplate.queryForRowSet(queryString);
	}
	
	/*根据查询sql查询数据量-封装count*/
	public int queryForCount(String sql){
		String countSql = "select count(1) from ("+sql+")";
		return queryForInt(countSql);
	}
	/*根据查询sql查询数据量-未封装*/
	public int queryForInt(String sql){
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public String queryForString(String sql){
		return jdbcTemplate.queryForObject(sql, String.class);
	}
	
	/*根据sql插入数据*/
	public void insert(String sql) {
			jdbcTemplate.execute(sql);
	}
	/* 使用prepareStatement来传递参数进行更新数据 */
	public int update(String sql, PreparedStatementSetter preparedStatementSetter) {
		return jdbcTemplate.update(sql,preparedStatementSetter);
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
		page.setSumcount(queryForCount(queryString));
		results.put("total", page.getSumcount());
		results.put("rows", list);
		return results;
	}

	@Override
	public Map<String, Object> queryMap(String sql){
		List<Map<String, Object>> list = queryForList(sql);
		return (list!=null && !list.isEmpty()) ? list.get(0) : null;
	}

	@Override
	public Map<String, Object> queryMapInLowerKey(String sql){
		List<Map<String, Object>> list = queryListInLowerKey(sql);
		return (list!=null && !list.isEmpty()) ? list.get(0) : null;
	}

	@Override
	public Map<String, Object> queryMapInLowerKey(String sql, Integer... columnTypes){
		List<Map<String, Object>> list = queryListInLowerKey(sql, columnTypes);
		return (list!=null && !list.isEmpty()) ? list.get(0) : null;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Map<String, Object>> queryListInLowerKey(String sql) {
		return jdbcTemplate.query(sql, new ResultMapper(true));
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public List<Map<String, Object>> queryListInLowerKey(String sql, Integer... columnTypes) {
		return jdbcTemplate.query(sql, new ResultMapper(true, columnTypes));
	}
	/**
	 * 结果集转换Mapper
	 */
	@SuppressWarnings({ "rawtypes","unchecked" })
	private class ResultMapper implements RowMapper {
		ResultMapper(Integer... columnTypes){
			this.columnTypes = columnTypes;
		}
		ResultMapper(boolean isLowerCase){
			this.isLowerCase = isLowerCase;
		}
		ResultMapper(boolean isLowerCase, Integer... columnTypes){
			this.isLowerCase = isLowerCase;
			this.columnTypes = columnTypes;
		}
		private Integer[] columnTypes;
		private boolean isLowerCase = false;
		private ResultSetMetaData rsm = null;
		private int count = 0;
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			if (rsm == null) {
				rsm = rs.getMetaData();
				count = rsm.getColumnCount();
			}
			HashMap map = new HashMap();
			for (int i = 0; i < count; i++) {
				String columnName = rsm.getColumnName((i + 1));
				columnName = isLowerCase ? columnName.toLowerCase() : columnName;
				int columnType = (columnTypes==null || columnTypes.length < i+1) ? 10000 : columnTypes[i];
				switch (columnType) {
				case Types.CHAR:
					map.put(columnName, rs.getCharacterStream(columnName));
					break;
				case Types.NUMERIC:
					map.put(columnName, rs.getInt(columnName));
					break;
				case Types.VARCHAR:
					map.put(columnName, rs.getString(columnName));
					break;
				case Types.BIGINT:
					map.put(columnName, rs.getLong(columnName));
					break;
				default:
					map.put(columnName, rs.getObject(columnName)==null ? "" : rs.getObject(columnName).toString());
					break;
				}
			}
			return map;
		}
    }  
	
	public Map<String, Object> queryWithPageInLowerKey(String sql, Page page) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select * from (select p_m.*, rownum nm from (select * from (");
		sbf.append(sql);
		sbf.append(")");
		/*增加了列排序*/
		if(page.getSortColumn() != null && !page.getSortColumn().equals("")){
			sbf.append(" order by " + page.getSortColumn() + " " + page.getSortOrder());
		}
		sbf.append(")p_m )p_n where p_n.nm > "+(page.getCurpage()-1)*page.getPagesize() + " and p_n.nm <=" + page.getCurpage()*page.getPagesize());
		log.debug("page query sql:"+sbf.toString());
		List<Map<String, Object>> list = this.queryListInLowerKey(sbf.toString());
		Map<String,Object> results = new HashMap<String, Object>();
		page.setSumcount(queryForCount(sql));
		int pageCount = 0;
		if(page.getSumcount()%page.getPagesize() != 0){
			pageCount = page.getSumcount()/page.getPagesize() +1;
		}else{
			pageCount = page.getSumcount()/page.getPagesize();
		}
		page.setPagecount(pageCount);
		results.put("total", page.getSumcount());
		results.put("pagecount", page.getPagecount());
		results.put("rows", list);
		return results;
	}
	
	public Page queryPageWithPageInLowerKey(String sql, Page page) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select * from (select p_m.*, rownum nm from (");
		sbf.append(sql);
		sbf.append(" )p_m )p_n where p_n.nm > "+(page.getCurpage()-1)*page.getPagesize() + " and p_n.nm <= "+page.getCurpage()*page.getPagesize());
		log.debug("page query sql:"+sbf.toString());
		List<Map<String, Object>> list = this.queryListInLowerKey(sbf.toString());
		page.setSumcount(this.queryForCount(sql));
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

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
}
