package com.jhkj.mosdc.sc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.po.ConditionsEntity;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.service.EntityService;
import com.jhkj.mosdc.framework.util.EntityUtil;
import com.jhkj.mosdc.sc.dao.CommonDao;
import com.jhkj.mosdc.sc.service.CommonStatisticSerivce;

public class CommonStatisticSerivceImpl implements CommonStatisticSerivce {
	private BaseService baseService = null;
	private EntityService entityService = null;
	private CommonDao commonDao=null;
	
	@Override
	public Object getMutiFieldCount(String str) throws Throwable {
		JSONObject json=JSONObject.fromObject(str);
		String entityName=json.getString("entityName");
		String fieldName=json.getString("fieldName");
		JSONArray array=json.getJSONArray("conditions");
		List<ConditionsEntity> conditions=null;
		if(array!=null && array.size()>0){
			conditions=this.generateConditions(array);
		}
		List dataList=this.mutiFieldCount(entityName, fieldName.split(","), conditions);
		return JSONArray.fromObject(dataList);
	}
	
	@Override
	public Object getMutiTableCount(String str) throws Throwable {
		
		return null;
	}
	
	@Override
	public Object getSingleFieldCount(String str) throws Throwable{
		JSONObject json=JSONObject.fromObject(str);
		String entityName=json.getString("entityName");
		String fieldName=json.getString("fieldName");
		JSONArray array=json.getJSONArray("conditions");
		List<ConditionsEntity> conditions=null;
		if(array!=null && array.size()>0){
			conditions=this.generateConditions(array);
		}
		List dataList=this.singleFieldCount(entityName, fieldName, conditions);
		return JSONArray.fromObject(dataList);
	}
	
	@Override
	public List<Map> singleFieldCount(String entityName, String fieldName,
			List<ConditionsEntity> conditions) throws Throwable {
		Class clazz=entityService.getEntityBySimpleName(entityName);
		// 把实体类Name转换为表名
		String tableName=EntityUtil.getTableName(clazz);
		// 得到列名
		String columnName=EntityUtil.getColumnName(clazz, fieldName);
		//将条件的名称，映射到表的列名
		for(ConditionsEntity condition:conditions){
			condition.setConditionName(EntityUtil.getColumnName(clazz, condition.getConditionName()));
		}
		List<Map> dataList=this.commonDao.singleFieldStatistic(tableName, columnName, conditions);
		for(Map m:dataList){
			if(this.baseService.isBmField(entityName, fieldName)){
				m.put("name", this.baseService.getMcByIdAndStsx(m.get(columnName), entityName, fieldName));
			}
			m.put(fieldName, m.get(columnName));
			m.remove(columnName);
		}
		return dataList;
	}

	@Override
	public List<Map> mutiFieldCount(String entityName, String[] fieldNames,
			List<ConditionsEntity> conditions) throws Throwable {
		Class clazz=entityService.getEntityBySimpleName(entityName);
		String tableName=EntityUtil.getTableName(clazz);
		String[] columnNames=new String[fieldNames.length];
		int i=0;
		Map<String,String> columnToFieldMap=new HashMap();
		for(String fieldName:fieldNames){
			String columnName=EntityUtil.getColumnName(clazz, fieldName);
			columnNames[i++]=columnName;
			columnToFieldMap.put(columnName, fieldName);
		}
		//将条件的名称，映射到表的列名
		for(ConditionsEntity condition:conditions){
			condition.setConditionName(EntityUtil.getColumnName(clazz, condition.getConditionName()));
		}
		List<Map> dataList=this.commonDao.mutiFieldStatistic(tableName, columnNames, conditions);
		for(Map m:dataList){
			for(String columnName:columnNames){
				String fieldName=columnToFieldMap.get(columnName);
				m.put(fieldName, m.get(columnName));
				if(this.baseService.isBmField(entityName, fieldName)){
					m.put(fieldName+"Name", this.baseService.getMcByIdAndStsx(m.get(columnName), entityName, fieldName));
				}
				m.remove(columnName);
			}
		}
		return dataList;
	}
	
	private List<ConditionsEntity> generateConditions(JSONArray array){
		if(array==null){
			System.out.println("标记array为空");
			return null;
		}
		List<ConditionsEntity> conditions= new ArrayList<ConditionsEntity>();
		Iterator i=array.iterator();
		while(i.hasNext()){
			JSONObject json=(JSONObject)i.next();
			ConditionsEntity condition=new ConditionsEntity();
			condition.setConditionName(json.getString("conditionName"));;
			condition.setConditionValu(json.getString("conditionValu"));
			condition.setDt(json.getString("dt"));
			conditions.add(condition);
		}
		return conditions;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
