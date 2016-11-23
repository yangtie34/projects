package com.jhkj.mosdc.sc.dao;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.po.ConditionsEntity;

public interface CommonDao {
	/**
	 * @description 单表单子段查询
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param conditions 查询条件
	 * @return
	 */
	public List<Map> singleFieldStatistic(String tableName,String columnName,List<ConditionsEntity> conditions);
	public List<Map> mutiFieldStatistic(String tableName,String[] columnNames,List<ConditionsEntity> conditions);
}
