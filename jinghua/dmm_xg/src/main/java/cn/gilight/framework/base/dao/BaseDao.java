package cn.gilight.framework.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import cn.gilight.framework.page.Page;


/** 
* @ClassName: BaseDao 
* @Description: TODO 持久层基础操作接口
* @author Sunwg  
* @date 2016年3月7日 22:38:37   
*/
public interface BaseDao {
	
	/*根据查询sql返回结果集*/
	public abstract List<Map<String, Object>> queryForList(String queryString);
	/**
	 * 查询数据 （大量数据）
	 * @param queryString
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryForListLarge(String queryString);

	/**
	 * 查询List<Map>,可定义返回类型
	 * @param queryString
	 * @param columnTypes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryForList(String queryString, Integer... columnTypes);
	/**
	 * 查询List<Map>,可定义返回类型 （大量数据）
	 * @param queryString
	 * @param columnTypes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryForListLarge(String queryString, Integer... columnTypes);

	/**
	 * 查询 List< Bean>
	 * @param sql
	 * @param beanType
	 * @return List<T>
	 */
	public <T> List<T> queryForListBean(String sql, Class<T> beanType);
	/**
	 * 查询 List< Bean> （大量数据）
	 * @param sql
	 * @param beanType
	 * @return List<T>
	 */
	public <T> List<T> queryForListBeanLarge(String sql, Class<T> beanType);
	
	/**
	 * 根据sql查询 List< String>
	 * @param sql
	 * @return List<String>
	 */
	public List<String> queryForListString(String sql);

	/**
	 * 根据sql查询 List< String> （大量数据）
	 * @param sql
	 * @return List<String>
	 */
	public List<String> queryForListStringLarge(String sql);
	
	/**
	 * 根据sql查询 List< Double>
	 * @param sql
	 * @return List<Double>
	 */
	public List<Double> queryForListDouble(String sql);
	/**
	 * 根据sql查询 List< Double> （大量数据，100000）
	 * @param sql
	 * @return List<Double>
	 */
	public List<Double> queryForListDoubleLarge(String sql);
	
	/**
	 * 根据查询sql返回结果集
	 * @param queryString
	 * @return SqlRowSet
	 */
	public abstract SqlRowSet queryForResultSet(String queryString);

	/**
	 * 根据查询sql查询数据量-封装count
	 * @param sql
	 * @return int
	 */
	public abstract int queryForCount(String sql);

	/**
	 * 根据查询sql查询数据量-未封装
	 * @param sql
	 * @return int
	 */
	public abstract int queryForInt(String sql);

	/**
	 * 查询单个数据字符串
	 * @param sql
	 * @return String
	 */
	public String queryForString(String sql);
	
	/**
	 * 根据sql插入数据
	 * @param sql void
	 */
	public abstract void insert(String sql);

	/**
	 * 使用prepareStatement来传递参数进行更新数据
	 * @param sql
	 * @param preparedStatementSetter
	 * @return int
	 */
	public abstract int update(String sql, PreparedStatementSetter preparedStatementSetter);

	/**
	 * 根据sql更新数据
	 * @param sql
	 * @return int
	 */
	public abstract int update(String sql);

	/**
	 * 根据sql更新数据
	 * @param sql
	 * @param args
	 * @return int
	 */
	public abstract int update(String sql, Object... args);

	/**
	 * 根据sql更新数据
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return int
	 */
	public abstract int update(String sql, Object[] args, int[] argTypes);

	/**
	 * 根据dmlSql更新数据库，返回执行结果
	 * @param sql
	 * @return int
	 */
	public abstract int delete(String sql);

	/**
	 * 根据查询sql进行分页查询，返回结果集
	 * @param sql
	 * @param page
	 * @return Map<String,Object>
	 */
	public abstract Map<String, Object> createPageQuery(String sql, Page page);

	/** 
	* @Title: queryWithPageInLowerKey 
	* @Description: TODO 根据分页参数查询分页数据集
	* @param @param sql
	* @param @param page
	* @return Map<String,Object>
	*/
	public Map<String,Object> createPageQueryInLowerKey(String sql, Page page);
	
	/**
	 * 查询map
	 * @param sql
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMap(String sql);
	/**
	 * 查询map（小写键）
	 * @param sql
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMapInLowerKey(String sql);
	/**
	 * 查询map（小写键）
	 * @param sql
	 * @param columnTypes 结果类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMapInLowerKey(String sql, Integer... columnTypes);
	
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * return List<Map>
	 */
	public List<Map<String, Object>> queryListInLowerKey(String sql);
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写 （大量数据）
	 * @param sql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryListInLowerKeyLarge(String sql);

	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * @param Integer... types 数据转换参数
	 * return List<Map>
	 */
	public List<Map<String, Object>> queryListInLowerKey(String sql, Integer... types);
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写 （大量数据）
	 * @param sql
	 * @param columnTypes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryListInLowerKeyLarge(String sql, Integer... columnTypes);
	
	
	public abstract JdbcTemplate getJdbcTemplate();
}
