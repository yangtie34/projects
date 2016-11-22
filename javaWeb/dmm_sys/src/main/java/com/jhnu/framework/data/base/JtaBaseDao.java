package com.jhnu.framework.data.base;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

public interface JtaBaseDao {
	
	public JdbcTemplate getJdbcTemplate();
	
	public int querySqlCount(String sql);
	
	public void executeSql(String sql);
	
	public List<Map<String, Object>> querySqlList(String sql);
	
	public OracleSequenceMaxValueIncrementer getSeqGenerator();
	
	public List<Map<String, Object>> queryListMapInLowerKeyBySql(String sql);
}
