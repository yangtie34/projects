package com.jhnu.framework.data.base.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

import com.jhnu.framework.data.base.JtaLogDao;
public class JtaLogDaoImpl implements JtaLogDao {
	private JdbcTemplate jdbcTemplate;
	private OracleSequenceMaxValueIncrementer seqGenerator;
	
	public void setSeqGenerator(OracleSequenceMaxValueIncrementer seqGenerator) {
		this.seqGenerator = seqGenerator;
	}
	
	public OracleSequenceMaxValueIncrementer getSeqGenerator() {
		return seqGenerator;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Override
	public void executeSql(String sql) {
		this.jdbcTemplate.execute(sql);
	}
	
	@Override
	public int querySqlCount(String sql) {
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	@Override
	public List<Map<String, Object>> querySqlList(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * return List<Map>
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> queryListMapInLowerKeyBySql(String sql){
		return this.getJdbcTemplate().query(sql, new RowMapper() {
			public ResultSetMetaData rsm = null;
			public int count = 0;

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				if (rsm == null) {
					rsm = rs.getMetaData();
					count = rsm.getColumnCount();
				}
				HashMap map = new HashMap();
				for (int i = 0; i < count; i++) {
					String columnName = rsm.getColumnName((i + 1))
							.toLowerCase();
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
}
