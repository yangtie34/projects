package com.jhkj.mosdc.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

/**
 * @Comments: 参数转化ＳＱＬ条件
 * Company: 河南精华科技有限公司
 * Created by gaodj(gaodongjie@126.com) 
 * @DATE:2013-5-16
 * @TIME: 下午2:28:16
 * 1、前台传到后台参数转化为ＳＱＬ可执行的条件
 * ２、拼装ＳＱＬ条件
 */
public class SqlParamsChange {
	/**
	 *前台数据高级查询参数修改为SQL字符串
	 * @param seniorArray　高级查询条件
	 * @return
	 * @throws Exception
	 */
	public static String seniorSQL(JSONArray seniorArray,Boolean isAlias) throws Exception {
		StringBuffer sb = new StringBuffer();
		// 实现SQL拼装
		for (int i = 0; i < seniorArray.size(); i++) {
			Map objMap = (Map) seniorArray.get(i);
			String fullField = objMap.get("field").toString();
			// 去掉实体名
			String[] fieldName = fullField.split("\\.");
			String field = fieldName[0];
			if(isAlias== true){
				field = "\""+field+"\"";
			}
			sb.append(" ( t." +field + " ");
			String value = Struts2Utils.isoToUTF8(objMap.get("value")
					.toString());
			// 操作符为like的加上　％％，同时把汉字转码为utf-8
			if (objMap.get("operator").equals("like")) {
				sb.append(objMap.get("operator") + " '%" + value + "%')");
			} else {
				sb.append(objMap.get("operator") + " '" + value + "')");
			}
			// 增加 and或or关联
			if (objMap.get("logical") != null && objMap.get("logical") != "") {
				sb.append(" " + objMap.get("logical"));
			}
		}
		return sb.toString();
	}
	
	public static String seniorSQL(JSONArray seniorArray) throws Exception {
		StringBuffer sb = new StringBuffer();
		// 实现SQL拼装
		for (int i = 0; i < seniorArray.size(); i++) {
			Map objMap = (Map) seniorArray.get(i);
			String fullField = objMap.get("field").toString();
			// 去掉实体名
			String[] fieldName = fullField.split("\\.");
			sb.append(" ( t." +fieldName[0].toString() + " ");
			String value = Struts2Utils.isoToUTF8(objMap.get("value")
					.toString());
			// 操作符为like的加上　％％，同时把汉字转码为utf-8
			if (objMap.get("operator").equals("like")) {
				sb.append(objMap.get("operator") + " '%" + value + "%')");
			} else {
				sb.append(objMap.get("operator") + " '" + value + "')");
			}
			// 增加 and或or关联
			if (objMap.get("logical") != null && objMap.get("logical") != "") {
				sb.append(" " + objMap.get("logical"));
			}
		}
		return sb.toString();
	}
	/**
	 * 把前台传入的请求参数转化为SQL条件
	 * @param maps 传入前台params(转化为JSONObject)
	 * @param isAlias 是否是虚表
	 * @throws Exception
	 * @return maps(start,limit,list,senior) 
	 * start　分页开始记录数
	 * limit 每页记录数
	 * list 普通ＳＱＬ参数(单字段查询、表头查询、查询条件等的sql条件)
	 * senior 高级查询条件
	 */
	public static Map getSQLParams(Map maps,Boolean isAlias) throws Exception {
		Map<String, Object> map = new HashMap();
		Set<String> key = maps.keySet();
		List<String> retList = new ArrayList<String>();
		Iterator<String> iter = key.iterator();
		String startStr = "";
		String limitStr = "";
		// String pageStr = "";
		String seniorStr = "";
		StringBuffer rqStr = new StringBuffer();
		while (iter.hasNext()) {
			String field = iter.next();
			String value = "";
			if ("seniorQuery".equals(field)) {
				value = String.valueOf(maps.get(field));
				// 高级查询条件
				JSONArray jsonArray = JSONArray.fromObject(value);
				// 判断jsonArray是否有值
				if (jsonArray.size() > 0) {
					seniorStr = seniorSQL(jsonArray,isAlias);
				}
				// 判断条件，并组装相应SQL
			} else {
				String[] paramFields = null;
				String fieldName ="";
				if (field.indexOf(".") > 0) {
					paramFields = field.split("\\.");
					fieldName = paramFields[0].toString();
					if(isAlias==true){
						fieldName = "\""+paramFields[0].toString()+"\"";
					}
				}
				StringBuffer str = new StringBuffer();
				value = Struts2Utils.isoToUTF8(String.valueOf(maps.get(field)));
				if (value.startsWith("[") && value.endsWith("]")
						&& value.contains(",")) {
					List list = (List) maps.get(field);
					value = (String) list.get(0);
				}
//				value = Struts2Utils.isoToUTF8(value);
				// 目前可确定的以下几种类型不是查询条件
				if ("start".equals(field)) {
					startStr = value;
					continue;
				} else if ("limit".equals(field)) {
					limitStr = value;
					continue;
				} else if ("page".equals(field)) {
					continue;
				} else if ("error".equals(field)) {
					continue;
					// pageStr = value;
				} else if ("entityName".equals(field)) {
					continue;
				} else if ("menuId".equals(field)) {
					continue;
				} else if ("buttonId".equals(field)) {
					continue;
				} else if ("singleReturnNoComponent".equals(field)) {
					continue;
				} else if (paramFields != null) {
					if (paramFields[1].equals("equals")) {
						str.append(fieldName + " ");
						str.append("= '" + value + "' ");
					} else if (paramFields[1].equals("like")) {
						if(!"".equals(value)){
							str.append(fieldName + " ");
							str.append("like '%" + value + "%' ");
						}
					} else if (("date1").equals(paramFields[1])) {
						rqStr.append(" and " + fieldName + " "
								+ ">= '" + value + "'");
					} else if (("date2").equals(paramFields[1])) {
						rqStr.append(" and " + fieldName + " "
								+ " <= '" + value + "'");
					} else if ("in".equals(paramFields[1])) {
//						str.append(fieldName + " ");
						str.append(" in ( " + value + " )");
					} else if ("notequals".equals(paramFields[1])) {
						str.append(fieldName + " ");
						str.append(" <>  " + value + " ");
					} else {
						str.append(fieldName + " ");
						str.append("= '" + value + "' ");
					}

				} else if (!value.equals("")) {
					field =(isAlias==true?"\""+field+"\"":field);
					if(value.equals("null")){
						str.append(field + " = " + value);
					}else{
						str.append(field + " = '" + value+"' ");
					}
				}
				if (str.toString().length() != 0) {
					retList.add(str.toString());
				}
			}
		}
		if (rqStr.toString().length() != 0) {
			retList.add(rqStr.toString().replaceFirst("and", ""));
		}
		if (!"".equals(startStr) && !"".equals(limitStr)) {
			map.put("start", startStr);
			map.put("limit", limitStr);
			map.put("list", retList);
		} else {
			map.put("list", retList);
		}
		map.put("senior", seniorStr);
		return map;
	}
	
	/**
	 * 把前台传入的请求参数转化为SQL条件
	 * @param maps 传入前台params(转化为JSONObject)
	 * @throws Exception
	 * @return maps(start,limit,list,senior) 
	 * start　分页开始记录数
	 * limit 每页记录数
	 * list 普通ＳＱＬ参数(单字段查询、表头查询、查询条件等的sql条件)
	 * senior 高级查询条件
	 */
	public static Map getSQLParams(Map maps) throws Exception {
		Map<String, Object> map = new HashMap();
		Set<String> key = maps.keySet();
		List<String> retList = new ArrayList<String>();
		Iterator<String> iter = key.iterator();
		String startStr = "";
		String limitStr = "";
		// String pageStr = "";
		String seniorStr = "";
		StringBuffer rqStr = new StringBuffer();
		while (iter.hasNext()) {
			String field = iter.next();
			String value = "";
			if ("seniorQuery".equals(field)) {
				value = String.valueOf(maps.get(field));
				// 高级查询条件
				JSONArray jsonArray = JSONArray.fromObject(value);
				// 判断jsonArray是否有值
				if (jsonArray.size() > 0) {
					seniorStr = seniorSQL(jsonArray);
				}
				// 判断条件，并组装相应SQL
			} else {
				String[] paramFields = null;
				String fieldName ="";
				if (field.indexOf(".") > 0) {
					paramFields = field.split("\\.");
					fieldName = paramFields[0].toString();
				}
				StringBuffer str = new StringBuffer();
				value = Struts2Utils.isoToUTF8(String.valueOf(maps.get(field)));
				if (value.startsWith("[") && value.endsWith("]")
						&& value.contains(",")) {
					List list = (List) maps.get(field);
					value = (String) list.get(0);
				}
//				value = Struts2Utils.isoToUTF8(value);
				// 目前可确定的以下几种类型不是查询条件
				if ("start".equals(field)) {
					startStr = value;
					continue;
				} else if ("limit".equals(field)) {
					limitStr = value;
					continue;
				} else if ("page".equals(field)) {
					continue;
				} else if ("error".equals(field)) {
					continue;
					// pageStr = value;
				} else if ("entityName".equals(field)) {
					continue;
				} else if ("menuId".equals(field)) {
					continue;
				} else if ("buttonId".equals(field)) {
					continue;
				} else if ("singleReturnNoComponent".equals(field)) {
					continue;
				} else if (paramFields != null) {
					if (paramFields[1].equals("equals")) {
						str.append(fieldName + " ");
						str.append("= '" + value + "' ");
					} else if (paramFields[1].equals("like")) {
						str.append(fieldName + " ");
						str.append("like '%" + value + "%' ");
					} else if (("date1").equals(paramFields[1])) {
						rqStr.append(" and " + fieldName + " "
								+ ">= '" + value + "'");
					} else if (("date2").equals(paramFields[1])) {
						rqStr.append(" and " + fieldName + " "
								+ " <= '" + value + "'");
					} else if ("in".equals(paramFields[1])) {
						str.append(fieldName + " ");
						str.append(" in ( " + value + " )");
					} else if ("notequals".equals(paramFields[1])) {
						str.append(fieldName + " ");
						str.append(" <>  " + value + " ");
					} else {
						str.append(fieldName + " ");
						str.append("= '" + value + "' ");
					}

				} else if (!value.equals("")) {
					str.append(field + " = '" + value + "' ");
				}
				if (str.toString().length() != 0) {
					retList.add(str.toString());
				}
			}
		}
		if (rqStr.toString().length() != 0) {
			retList.add(rqStr.toString().replaceFirst("and", ""));
		}
		if (!"".equals(startStr) && !"".equals(limitStr)) {
			map.put("start", startStr);
			map.put("limit", limitStr);
			map.put("list", retList);
		} else {
			map.put("list", retList);
		}
		map.put("senior", seniorStr);
		return map;
	}
}
