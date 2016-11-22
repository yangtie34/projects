package com.jhkj.mosdc.sc.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jhkj.mosdc.framework.po.ConditionsEntity;

public interface TestOneTableQuery {
	
	public List<Map> queryOneTable(String tableName,
			String[] propertyNames, List<ConditionsEntity> conditions);
	
}
