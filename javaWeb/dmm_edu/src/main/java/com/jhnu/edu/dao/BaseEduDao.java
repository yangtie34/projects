package com.jhnu.edu.dao;

import java.util.Map;

public interface BaseEduDao {
	/**
	 * 根据学校id及字段id获取value
	 */
	public Map<String,Object> getAval(String schId,String titleId,String start,String end);
	/**
	 * 根据学校id及字段ids获取values
	 */
	public Map<String,Object> getValsInIds(String schId,String[] titleId,String start,String end);
	/**
	 * 根据学校id及字段id获取values
	 */
	public Map<String,Object> getValsLikeId(String schId,String titleId,String start,String end);
	/**
	 * 根据学校id获取values
	 */
	public Map<String, Object> getVals(String schId, String start, String end);
	
	Map<String, Object> getValsInIds(String schId, String[] titleId);
	
	Map<String, Object> getValsLikeId(String schId, String titleId);
	//返回格式
	/*
	 * 例：{id:{
	 * name:'学校名称',
	 * value:165}
	 * }
	 */
}
