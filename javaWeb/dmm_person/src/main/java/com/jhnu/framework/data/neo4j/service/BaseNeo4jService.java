package com.jhnu.framework.data.neo4j.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.conversion.Result;


public interface BaseNeo4jService {


	/**
	 * 通用查询方法
	 * 
	 * @param cypher
	 * @return
	 */
	public List<Map<String, Object>> findByCypher(String cypher);
	
	@SuppressWarnings({ "rawtypes" })
	public Result<Object> findObjectByCypher(String cypher, Class cla);
	
	public void save(Object object);
	
	public void delete(Object object);
	
}