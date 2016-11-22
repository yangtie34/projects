package com.jhkj.mosdc.sc.service;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.po.ConditionsEntity;

public interface CommonStatisticSerivce {
	/**
	 * 多表关联查询统计
	 * @param str
	 * @return
	 * @throws Throwable
	 */
	public Object getMutiTableCount(String str) throws Throwable;
	/**
	 * 单表多 字段统计查询
	 * @param str
	 * @return
	 * @throws Throwable
	 */
	public Object getMutiFieldCount(String str) throws Throwable;
	/**
	 * 单表单字段统计查询
	 * @param str
	 * @return
	 * @throws Throwable
	 */
	public Object getSingleFieldCount(String str) throws Throwable;
	
	/**
	 * 单子段统计查询
	 * @param entityName
	 * @param fieldName
	 * @param conditions
	 * @return
	 * @throws Throwable 
	 */
	public List<Map> singleFieldCount(String entityName,String fieldName,List<ConditionsEntity> conditions) throws Throwable;
	
	public List<Map> mutiFieldCount(String entityName,String[] fieldNames,List<ConditionsEntity> conditions) throws Throwable;
}
