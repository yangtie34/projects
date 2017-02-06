package cn.gilight.framework.base.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;


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
	
	/** 大量数据查询参数设置 */
	private static final int FetchSizeLarge = 100000;

	/**
	 * 还原 FetchSize 默认配置 -1
	 *  void
	 */
	private void recoverFetchSize(){
		jdbcTemplate.setFetchSize(-1);
	}

	@Override
	public List<Map<String,Object>> queryForList(String queryString) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(queryString);
		return list;
	}
	@Override
	public List<Map<String,Object>> queryForListLarge(String queryString) {
		jdbcTemplate.setFetchSize(FetchSizeLarge);
		List<Map<String, Object>> list = queryForList(queryString);
		recoverFetchSize();
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryForList(String queryString, Integer... columnTypes) {
		return jdbcTemplate.query(queryString, new ResultMapper(columnTypes));
	}
	@Override
	public List<Map<String,Object>> queryForListLarge(String queryString, Integer... columnTypes) {
		jdbcTemplate.setFetchSize(FetchSizeLarge);
		List<Map<String, Object>> list = queryForListLarge(queryString, columnTypes);
		recoverFetchSize();
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T> queryForListBean(String sql, Class<T> elementType){
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(elementType));
	}
	@Override
	public <T> List<T> queryForListBeanLarge(String sql, Class<T> elementType){
		jdbcTemplate.setFetchSize(FetchSizeLarge);
		List<T> list = queryForListBean(sql, elementType);
		recoverFetchSize();
		return list;
	}
	
	@Override
	public List<String> queryForListString(String sql) {
		return jdbcTemplate.queryForList(sql, String.class);
	}
	@Override
	public List<String> queryForListStringLarge(String sql) {
		jdbcTemplate.setFetchSize(FetchSizeLarge);
		List<String> list = queryForListString(sql);
		recoverFetchSize();
		return list;
	}
	
	@Override
	public List<Double> queryForListDouble(String sql) {
		return jdbcTemplate.queryForList(sql, Double.class);
	}

	@Override
	public List<Double> queryForListDoubleLarge(String sql) {
		jdbcTemplate.setFetchSize(FetchSizeLarge);
		List<Double> list = queryForListDouble(sql);
		recoverFetchSize();
		return list;
	}
	
	@Override
	public SqlRowSet queryForResultSet(String queryString) {
		return jdbcTemplate.queryForRowSet(queryString);
	}
	
	@Override
	public int queryForCount(String sql){
		String countSql = "select count(1) from ("+sql+")";
		return jdbcTemplate.queryForObject(countSql,Integer.class);
	}
	@Override
	public int queryForInt(String sql){
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	@Override
	public String queryForString(String sql){
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	@Override
	public void insert(String sql) {
		jdbcTemplate.execute(sql);
	}
	@Override
	public int update(String sql, PreparedStatementSetter preparedStatementSetter) {
		return jdbcTemplate.update(sql,preparedStatementSetter);
	}
	@Override
	public int update(String sql) {
			return jdbcTemplate.update(sql);
	}
	@Override
	public int update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
	}
	@Override
	public int update(String sql, Object[] args, int[] argTypes) {
		return jdbcTemplate.update(sql, args, argTypes);
	}
	@Override
	public int delete(String sql) {
		int result = jdbcTemplate.update(sql);
		return result;
	}

	@Override
	public Map<String,Object> createPageQuery(String sql, Page page) {
		return createPageQuery(sql, page, false);
	}
	
	@Override
	public Map<String, Object> createPageQueryInLowerKey(String sql, Page page) {
		return createPageQuery(sql, page, true);
	}

	private Map<String, Object> createPageQuery(String sql, Page page, boolean isLower) {
		Integer curpage = 1, pagesize = 20;
		String sortColumn = null, order = null;
		if(page != null){
			curpage  = page.getCurpage();
			pagesize = page.getPagesize();
			sortColumn = page.getSortColumn();
			order = page.getOrder();
		}
		curpage  = curpage==null||curpage==0 ? 1 : curpage;
		pagesize = pagesize==null||pagesize==0 ? 20 : pagesize;
		StringBuffer sbf = new StringBuffer();
		sbf.append("select * from (select p_m.*, rownum nm from (");
		sbf.append("select * from ("+sql+") " + (sortColumn!=null&&!"".equals(sortColumn) ? " order by "+sortColumn+(order!=null ? " "+order : "") : ""));
		sbf.append(")p_m) where nm > "+(curpage-1)*pagesize+" and nm <= "+ curpage*pagesize );
		log.debug("page query sql:"+sbf.toString());
		List<Map<String, Object>> list = null;
		if(isLower)
			list = this.queryListInLowerKey(sbf.toString());
		else
			list = this.queryForList(sbf.toString());
		Map<String,Object> results = new HashMap<String, Object>();
		int sumcount = queryForCount(sql);
		page.setSumcount(sumcount);
		page.setPagecount(sumcount==0 ? 0 : (sumcount%pagesize==0 ? sumcount/pagesize : (sumcount/pagesize+1)));
		results.put("total", sumcount);
		results.put("pagecount", page.getPagecount());
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
	public List<Map<String, Object>> queryListInLowerKeyLarge(String sql) {
		jdbcTemplate.setFetchSize(FetchSizeLarge);
		List<Map<String, Object>> list = queryListInLowerKey(sql);
		recoverFetchSize();
		return list;
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public List<Map<String, Object>> queryListInLowerKey(String sql, Integer... columnTypes) {
		return jdbcTemplate.query(sql, new ResultMapper(true, columnTypes));
	}
	@Override
	public List<Map<String, Object>> queryListInLowerKeyLarge(String sql, Integer... columnTypes) {
		jdbcTemplate.setFetchSize(FetchSizeLarge);
		List<Map<String, Object>> list = queryListInLowerKey(sql, columnTypes);
		recoverFetchSize();
		return list;
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

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
