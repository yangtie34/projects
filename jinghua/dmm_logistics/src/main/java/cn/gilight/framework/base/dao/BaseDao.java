package cn.gilight.framework.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.rowset.SqlRowSet;


/** 
* @ClassName: BaseDao 
* @Description: TODO 持久层基础操作接口
* @author Sunwg  
* @date 2016年3月7日 22:38:37   
*/
public interface BaseDao {
	/*根据查询sql返回结果集*/
	public abstract List<Map<String, Object>> queryForList(String queryString);

	/*根据查询sql返回结果集*/
	public abstract SqlRowSet queryForResultSet(String queryString);

	/*根据查询sql返回结果集*/
	public abstract List<Map<String, Object>> queryForList(String queryString,
			Object... obj);

	/*根据查询sql查询数据量-封装count*/
	public abstract int queryForInt(String sql);

	/*根据查询sql查询数据量-未封装*/
	public abstract int queryForIntBase(String sql);

	/*根据sql插入数据*/
	public abstract void insert(String sql);

	/* 使用prepareStatement来传递参数进行更新数据 */
	public abstract int update(String sql,
			PreparedStatementSetter preparedStatementSetter);

	/*根据sql更新数据*/
	public abstract int update(String sql);

	/*根据sql更新数据*/
	public abstract int update(String sql, Object... args);

	/*根据sql更新数据*/
	public abstract int update(String sql, Object[] args, int[] argTypes);

	/*根据dmlSql更新数据库，返回执行结果*/
	public abstract int delete(String sql);

	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * return List<Map>
	 */
	public abstract List<Map<String, Object>> queryListInLowerKey(String sql);
	
	public abstract JdbcTemplate getJdbcTemplate();
}
