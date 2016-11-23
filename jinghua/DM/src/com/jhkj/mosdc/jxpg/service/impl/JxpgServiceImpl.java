package com.jhkj.mosdc.jxpg.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.jxpg.service.JxpgService;
import com.jhkj.mosdc.jxpg.util.JxpgUtil;

public class JxpgServiceImpl implements JxpgService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/** 
	* @Title: queryGridContent 
	* @Description: TODO 查询表格数据
	*/
	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String tableName = json.getString("tableName");
		String xn = json.getString("xn");
		String ignoreXn = json.getString("ignoreXn");
		String sql = "select * from "+tableName +" t ";
		if ("false".equals(ignoreXn)) {
			sql += "where t.xn = '" + xn+ "'" ;
		}
		sql += " order by id";
		@SuppressWarnings("rawtypes")
		List result = baseDao.queryListMapBySQL(sql);		
		return Struts2Utils.list2json(result);
	}
	
	/** 
	* @Title: saveUpdateItems 
	* @Description: TODO 保存用户输入的数据
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String saveInputItems(String params){
		Map result = new HashMap();
		JSONObject json = JSONObject.fromObject(params);
		String tableName = json.getString("tableName");
		String method = json.getString("method");
		List<Map> items = json.getJSONArray("items");
		String beforeSaveInvokeService  = json.getString("beforeSaveInvokeService");
		if (beforeSaveInvokeService != null && !beforeSaveInvokeService.equals("")) {
			Map beforeResult =  this.beforeSave(beforeSaveInvokeService, params);
			if (!(Boolean)beforeResult.get("success")) {
				result.put("success", false);
				result.put("error_message", beforeResult.get("error_message"));
				return Struts2Utils.map2json(result);
			}else{
				items = (List<Map>) beforeResult.get("items");
			}
		}else{
			items = JxpgUtil.changeObjectAttrToMap( this.sendChangeItemsIntoDatabaseList(params));
		}
		//开始保存动作
		if(method.equals("save")){
			for (int i = 0; i < items.size(); i++) {
				String rowid = String.valueOf( items.get(i).get("rowid"));
				String itemCode = String.valueOf(items.get(i).get("itemCode"));
				String itemValue = String.valueOf( items.get(i).get("itemValue"));
				if("null".equals(itemValue)){
					itemValue = "";
				}
				String itemSql = "update " + tableName + " t set t."+itemCode+" = '"+itemValue+"' " +
							" where t.ID = "+rowid;
				baseDao.update(itemSql);
			}
		}
		
		String afterSaveInvokeService  = json.getString("afterSaveInvokeService");
		if (afterSaveInvokeService != null && !afterSaveInvokeService.equals("")) {
			this.afterSave(afterSaveInvokeService, params);
		}
		result.put("success", true);
		if (method.equals("save")) {
			result.put("items", JSONArray.fromObject(this.queryGridContent(params)));
		}else{
			result.put("items",this.changeItemsToObject(items));
		}
		JSONObject jsonResult = JSONObject.fromObject(result);
		return jsonResult.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map beforeSave(String requestService, String params) {
		String[] beanAndMethod = StringUtils.split(requestService, "?");
		String service = beanAndMethod[0];
		String methodName = beanAndMethod[1];
		// 获取服务对象
		Object serviceBean = ApplicationComponentStaticRetriever
				.getComponentByItsName(service);
		Class cls = serviceBean.getClass();
		Class[] paraTypes = new Class[] { String.class };
		Map result = new HashMap();
		// 反射方法
		try {
			Method method = cls.getMethod(methodName, paraTypes);
			result = (Map) method.invoke(serviceBean, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void afterSave(String requestService, String params) {
		String[] beanAndMethod = StringUtils.split(requestService, "?");
		String service = beanAndMethod[0];
		String methodName = beanAndMethod[1];
		// 获取服务对象
		Object serviceBean = ApplicationComponentStaticRetriever
				.getComponentByItsName(service);
		Class cls = serviceBean.getClass();
		Class[] paraTypes = new Class[] { String.class };
		// 反射方法
		try {
			Method method = cls.getMethod(methodName, paraTypes);
			method.invoke(serviceBean, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private List<Map> changeItemsToObject(List<Map> items){
		List<Map> listTemp = new ArrayList<Map>();
		Set<String> ids = new HashSet<String>();
		for (int i = 0; i < items.size(); i++) {
			Map item = items.get(i);
			String rowid = String.valueOf(item.get("rowid"));
			if (ids.contains(rowid)) {
				for (int j = 0; j < listTemp.size(); j++) {
					Map mapTemp = listTemp.get(j);
					if (mapTemp.get("ID").equals(rowid)) {
						String value = String.valueOf(item.get("itemValue"));
						value = value.equals("null") ? "" : value;
						mapTemp.put(item.get("itemCode"), value);
					}
				}
			}else{
				ids.add(rowid);
				Map mapTemp = new HashMap();
				mapTemp.put("ID", rowid);
				mapTemp.put(item.get("itemCode"), item.get("itemValue"));
				listTemp.add(mapTemp);
			}
		}
		return listTemp;
	}
	
	/** 
	* @Title: sendChangeItemsIntoDatabaseList 
	* @Description: TODO 取出前台修改的值对应的行数据，并将修改过的值放到从数据库取出的数据中，生成新的List返回
	* @param @param params 前台参数
	* @return List<Map>
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> sendChangeItemsIntoDatabaseList(String params){
		JSONObject json = JSONObject.fromObject(params);
		List<Map> items = json.getJSONArray("items");
		List<Map> listTemp = new ArrayList<Map>();
		Set<String> ids = new HashSet<String>();
		for (int i = 0; i < items.size(); i++) {
			Map item = items.get(i);
			String rowid = String.valueOf(item.get("rowid"));
			if (ids.contains(rowid)) {
				for (int j = 0; j < listTemp.size(); j++) {
					Map mapTemp = listTemp.get(j);
					if (mapTemp.get("ID").equals(rowid)) {
						String value = String.valueOf(item.get("itemValue"));
						value = value.equals("null") ? "" : value;
						mapTemp.put(item.get("itemCode"), value);
					}
				}
			}else{
				ids.add(rowid);
				Map mapTemp = new HashMap();
				mapTemp.put("ID", rowid);
				mapTemp.put(item.get("itemCode"), item.get("itemValue"));
				listTemp.add(mapTemp);
			}
		}
		String sqlIds = "";
		for (String id : ids) {
			sqlIds += (id+",");
		}
		sqlIds = sqlIds.substring(0, sqlIds.length()-1);
		String tableName = json.getString("tableName");
		String xn = json.getString("xn");
		String ignoreXn = json.getString("ignoreXn");
		String sql = "select * from "+ tableName +" t where 1=1 "; //t.ID IN ("+sqlIds+")";
		if (ignoreXn.endsWith("false")) {
			sql += " and t.xn = '" + xn +"'";
		}
		List<Map> oldItems = baseDao.queryListMapBySQL(sql);
		for (int i = 0; i < oldItems.size(); i++) {
			Map oldItem = oldItems.get(i);
			for (int j = 0; j < listTemp.size(); j++) {
				Map mapTemp = listTemp.get(j);
				if (String.valueOf(oldItem.get("ID")).equals(String.valueOf(mapTemp.get("ID")))) {
					oldItem.putAll(mapTemp);
					break;
				}
			}
		}
		return oldItems;
	}
	
	public String invokeTest(String params){
		System.out.println(params);
		System.out.println(" this is the after invoke");
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public Map invokeBefore(String params){
		JSONArray json = JSONArray.fromObject(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("error_message", "this is ");
		result.put("items", json);
		return result;
	}
}
