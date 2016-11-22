package com.jhkj.mosdc.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.nobject.common.lang.ClassUtils;
import org.nobject.common.lang.ObjectUtils;
import org.springframework.util.StringUtils;

public class JsonsUtils {
	
	/**
	 * 将String字符串转化为Bean
	 * @param param "{'id':'111','entityName':'TsJs','jslxId':'1202323','ms':'中仍','start':0,'limit':25}"
	 * @return bean对象实例
	 * @throws Exception 
	 */
	public static Object stringToBean(String param) throws Exception{
		//将参数转化为JSONObject
		JSONObject jsonObj = JSONObject.fromObject(param);
		if(jsonObj.isEmpty())
			return null;
		//获取实体的包路径
		String entityPack = ServletUtils.entityPath(jsonObj.get("entityName").toString());
		//反射实体类
		Class cls = Class.forName(entityPack);
		//实例化对象
		Object obj = cls.newInstance();
		//将传入的参数的值放到对象实例中
		obj = getObject(jsonObj, cls);
		return obj;
	}
	public static Object stringToBean(String params ,Class cls) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		return JSONObject.toBean(json, cls);
	}
	/**
	 * 将JSONObject转化为实例
	 * @param obj
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object objectToBean(JSONObject obj ,Class cls) throws Exception{
//		ObjectUtils.toObject(obj, cls);
		return ObjectUtils.toObject(obj, cls);
	}
	
	public static Object mapToBean(Map map,Class cls) throws Exception{
		return getObject(map,cls);
	}
	
	/**
	 * 将String字符串转化为Bean
	 * @param param "{'id':'111','entityName':'TsJs','jslxId':'1202323','ms':'中仍','start':0,'limit':25}"
	 * @return bean对象实例
	 * @throws Exception 
	 */
	public static Object stringToBean(String param,Map map,Class poclass) throws Exception{
		//将参数转化为JSONObject
		JSONObject jsonObj = JSONObject.fromObject(param);
		if(jsonObj.isEmpty())
			return null;
		//获取实体的包路径
//		String entityPack = ServletUtils.entityPath(jsonObj.get("entityName").toString());
//		String entityPack = ServletUtils.entityPath(entityName);
//		//反射实体类
//		Class cls = Class.forName(entityPack);
		//实例化对象
		Object obj = poclass.newInstance();
		//将传入的参数的值放到对象实例中
		obj = getObject(jsonObj, poclass,map);
		return obj;
	}
	
	/**
	 * 根据提供的值，读取包装类对象　
	 * ""[{'id':'111','entityName':'TsJs','jslxId':'1202323','ms':'中仍','start':0,'limit':25}
	 * ,{'id':'222','entityName':'TsJs','jslxId':'444444','ms':'大哥大仍','start':1,'limit':25}]"
	 * @param params 字符串
	 * @param entityClass 实体Class
	 * @throws Exception 
	 */
	public static List<Object> stringToList(String params,Class poclass) throws Exception{
		//字符串转化为array
		JSONArray jsonArray = JSONArray.fromObject(params);
		//定义返回的List
		List<Object> list = new ArrayList();
		//判断jsonArray是否为空
		if(jsonArray.isEmpty()){
			return null;
		}
		//遍历jsonArray
		for(int i = 0 ;i<jsonArray.size();i++){
			//获取jsonArray中的jsonObject
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			Object obj = getObject(jsonObj,poclass);
			//将对象存入到List
			list.add(obj);
		}
		return list;
	}
	/**
	 * 根据映射关系把数组对象
	 * ""[{'id':'111','entityName':'TsJs','jslxId':'1202323','ms':'中仍','start':0,'limit':25}
	 * ,{'id':'222','entityName':'TsJs','jslxId':'444444','ms':'大哥大仍','start':1,'limit':25}]"
	 * @param params 字符串
	 * @param entityClass 实体Class
	 * @throws Exception 
	 */
	public static List<Object> stringToList(String params,Class poclass,Map map) throws Exception{
		//字符串转化为array
		JSONArray jsonArray = JSONArray.fromObject(params);
		//定义返回的List
		List<Object> list = new ArrayList();
		//判断jsonArray是否为空
		if(jsonArray.isEmpty()){
			return null;
		}
		//遍历jsonArray
		for(int i = 0 ;i<jsonArray.size();i++){
			//获取jsonArray中的jsonObject
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			Object obj = getObject(jsonObj,poclass,map);
			//将对象存入到List
			list.add(obj);
		}
		return list;
	}
	/**
	 * 读取一个key值
	 * @params  {person : {name : 'zhangsan',age : '12' ,sex : '男'}}
	 * @param key name  
	 * @param valueclass String.Class
	 * @return
	 * @throws Exception 
	 */
	public static Object propertyToObject(String params,String key,Class poclass) throws Exception{
		//将传入的参数转化JSONObject
		JSONObject jsonObj = JSONObject.fromObject(params);
		//判断jsonObj是否为空
		if(jsonObj.isEmpty()){
			return null;
		}
		//判断当前的Key是否存在
		if(!jsonObj.containsKey(key))
			return null;
		//获取当前的Key转化为JSONObject
		JSONObject keyObj = jsonObj.getJSONObject(key);
		//JSONObject对象转化为实体对象
		Object obj = getObject(keyObj, poclass);
		return obj;
	}
	/**
	 * 读取一个key值
	 * @params  {person : {name : 'zhangsan',age : '12' ,sex : '男'}}
	 * @param key name  
	 * @param valueclass String.Class
	 * @return
	 * @throws Exception 
	 */
	public static Object propertyToObject(String params,String key,Class poclass,Map map) throws Exception{
		//将传入的参数转化JSONObject
		JSONObject jsonObj = JSONObject.fromObject(params);
		//判断jsonObj是否为空
		if(jsonObj.isEmpty()){
			return null;
		}
		//判断当前的Key是否存在
		if(!jsonObj.containsKey(key))
			return null;
		//获取当前的Key转化为JSONObject
		JSONObject keyObj = jsonObj.getJSONObject(key);
		//JSONObject对象转化为实体对象
		Object obj = getObject(keyObj, poclass,map);
		return obj;
	}
	/**
	 * 把JSON中的皱皱键值对存入到Class实例对应字段中
	 * @param methodName　
	 * @param obj
	 * @param cls
	 * @param value
	 * @param fieldType
	 */
	 public static void invokeSetter(String methodName,Object obj,Class cls,Object value,Class fieldType) throws Exception{
			Method method = cls.getMethod(methodName, fieldType);
			method.invoke(obj, new Object[]{value});
	   }
	 /**
	  * JSONObject转化为可用的实体对象
	  * @param jsonObj　传入键值关系的字符串
	  * @param poclass　实体类
	  * @return　返回实体对象
	  * @throws Exception
	  */
	 private static Object getObject(JSONObject jsonObj,Class poclass) throws Exception{
			//获取反射的类
			Class cls = poclass;//TsJs.class
			//实例化类
			Object obj = cls.newInstance();
			//获取反射类的所有字段
			Field[] fields =cls.getDeclaredFields();
			//获取jsonObj的所有Key
			Set<String> mapSet =jsonObj.keySet();
			//Key转化为Iterator
			Iterator<String> iter = mapSet.iterator();
			//遍历iter
			while(iter.hasNext()){
				//获取Key
				String field = iter.next();
				//循环遍历对象的所有属性
				for(Field f :fields){
					//获取类的字段属性
					Field fi = f;
					//判断是key和属性名是否一致
					if(field.equals(f.getName())){
						Object valueObj = null;
						if(fi.getType().getName().equals("java.lang.Boolean") || fi.getType().getName().equals("java.lang.boolean")){
							Boolean b = false;
							if(jsonObj.get(field).equals("1")){
								b =jsonObj.get(field).equals("1")?true:false;
							}else if(jsonObj.get(field).equals("true") || jsonObj.getBoolean(field) == true){
								b =true;
							}else if(jsonObj.get(field).equals("false") || jsonObj.getBoolean(field) == false){
								b =false;
							}
							valueObj = ObjectUtils.toObject(b, fi.getType());
						}else if(fi.getType().getName().equals("java.lang.Integer") || fi.getType().getName().equals("java.lang.int")){
							Class c=jsonObj.get(field).getClass();
							if(ClassUtils.isNumber(c)){
								valueObj=ObjectUtils.toObject(jsonObj.get(field),fi.getType());
							}else if(ClassUtils.isBoolean(c)){
								valueObj = jsonObj.get(field).equals(true)?1:0;
									//ObjectUtils.toObject((), fi.getType());
							}else if(ClassUtils.isString(c)){
								valueObj =ObjectUtils.toObject(jsonObj.get(field), fi.getType());
							}else{
								throw new Error("暂不支持"+c.getName()+"->数值的转换");
							}
						}else if(fi.getType().getName().equals("java.lang.Long") || fi.getType().getName().equals("java.lang.long")){
							valueObj = ObjectUtils.toObject((jsonObj.get(field).equals("")?null:jsonObj.get(field)), fi.getType());
						}else{
							//获取值类型转化为属性类型
							valueObj = ObjectUtils.toObject(jsonObj.get(field), fi.getType());
						}
						//将值放到对象的属性中
						JsonsUtils.invokeSetter("set"+StringUtils.capitalize(fi.getName()), obj, cls, valueObj, fi.getType());
						break;
					}
				}
			}
			return obj;
	 }
	 
	 /**
	  * JSONObject转化为可用的实体对象
	  * @param map　传入键值关系的字符串
	  * @param poclass　实体类
	  * @return　返回实体对象
	  * @throws Exception
	  */
	 private static Object getObject(Map map,Class poclass) throws Exception{
			//获取反射的类
			Class cls = poclass;//TsJs.class
			//实例化类
			Object obj = cls.newInstance();
			//获取反射类的所有字段
			Field[] fields =cls.getDeclaredFields();
			//获取jsonObj的所有Key
			Set<String> mapSet =map.keySet();
			//Key转化为Iterator
			Iterator<String> iter = mapSet.iterator();
			//遍历iter
			while(iter.hasNext()){
				//获取Key
				String field = iter.next();
				//循环遍历对象的所有属性
				for(Field f :fields){
					//获取类的字段属性
					Field fi = f;
					//判断是key和属性名是否一致
					if(field.equals(f.getName())){
						Object valueObj = null;
						if(fi.getType().getName().equals("java.lang.Boolean") || fi.getType().getName().equals("java.lang.boolean")){
							valueObj = ObjectUtils.toObject((map.get(field).equals("1")?true:false), fi.getType());
						}else if(fi.getType().getName().equals("java.lang.Integer") || fi.getType().getName().equals("java.lang.int")){
							Class c=map.get(field).getClass();
							if(ClassUtils.isNumber(c)){
								valueObj=ObjectUtils.toObject(map.get(field),fi.getType());
							}else if(ClassUtils.isBoolean(c)){
								valueObj = map.get(field).equals(true)?1:0;
									//ObjectUtils.toObject((), fi.getType());
							}else if(ClassUtils.isString(c)){
								valueObj =ObjectUtils.toObject(map.get(field), fi.getType());
							}else{
								throw new Error("暂不支持"+c.getName()+"->数值的转换");
							}
						}else if(fi.getType().getName().equals("java.lang.Long") || fi.getType().getName().equals("java.lang.long")){
							valueObj = ObjectUtils.toObject((("".equals(map.get(field))|| map.get(field)==null)?null:map.get(field)), fi.getType());
						}else{
							//获取值类型转化为属性类型
							valueObj = ObjectUtils.toObject(map.get(field), fi.getType());
						}
						//将值放到对象的属性中
						JsonsUtils.invokeSetter("set"+StringUtils.capitalize(fi.getName()), obj, cls, valueObj, fi.getType());
						break;
					}
				}
			}
			return obj;
	 }
	 
	 /**
	  * 根据映射表将JSONObject转化为对实体对象
	  * @param jsonObj　传入键值关系的字符串
	  * @param poclass　实体类
	  * @param map　映射表
	  * @return　返回实体对象
	  * @throws Exception
	  */
	 private static Object getObject(JSONObject jsonObj,Class poclass,Map map) throws Exception{
			//获取反射的类
			Class cls = poclass;//poclassTsJs.class
			//实例化类
			Object obj = cls.newInstance();
			//获取反射类的所有字段
			Field[] fields =cls.getDeclaredFields();
			//获取jsonObj的所有Key
			Set<String> mapSet =jsonObj.keySet();
			//Key转化为Iterator
			Iterator<String> iter = mapSet.iterator();
			//遍历iter
			while(iter.hasNext()){
				//获取Key
				String field = iter.next();
				//获取当前key对应的映射关系字段
				String val = (String) map.get(field);
				//循环遍历对象的所有属性
				for(Field f :fields){
					//获取类的字段属性
					Field fi = f;
					if(val == null){
						break;
					};
					//判断是映射字段和属性名是否一致
					if(val.equals(fi.getName())){
						Object valueObj = null;
						//获取值类型转化为属性类型
						if(fi.getType().getName().equals("java.lang.Boolean") || fi.getType().getName().equals("java.lang.boolean")){
							valueObj = ObjectUtils.toObject(jsonObj.get(field).equals("1")?true:false, fi.getType());
						}else if(fi.getType().getName().equals("java.lang.Integer") || fi.getType().getName().equals("java.lang.int")){
							Class c=jsonObj.get(field).getClass();
							if(ClassUtils.isNumber(c)){
								valueObj=ObjectUtils.toObject(jsonObj.get(field),fi.getType());
							}else if(ClassUtils.isBoolean(c)){
								valueObj = jsonObj.get(field).equals(true)?1:0;
									//ObjectUtils.toObject((), fi.getType());
							}else if(ClassUtils.isString(c)){
								valueObj =ObjectUtils.toObject(jsonObj.get(field), fi.getType());
							}else{
								throw new Error("暂不支持"+c.getName()+"->数值的转换");
							}
						}else if(fi.getType().getName().equals("java.lang.Long") || fi.getType().getName().equals("java.lang.long")){
							valueObj = ObjectUtils.toObject((jsonObj.get(field).equals("")?null:jsonObj.get(field)), fi.getType());
						}else{
							valueObj = ObjectUtils.toObject(jsonObj.get(field), fi.getType());
						}
							
						//将值放到对象的属性中
						JsonsUtils.invokeSetter("set"+StringUtils.capitalize(fi.getName()), obj, cls, valueObj, fi.getType());
						break;
					}
				}
			}
			return obj;
	 }
	 /**
	  * 把映射关系转化为Ｍap
	  * @param sqlFields 查询sql字段数组
	  * @param entityFields　实体字段数组
	  * @return　map sql字段作为Key,实体字段为value
	  */
	 public static Map mappingEntityField(String[] sqlFields,String[] entityFields){
		 //字符串转化List
		 List<String> sqlFieldList = Arrays.asList(sqlFields);
		//字符串转化List
		 List<String> entityFieldList = Arrays.asList(entityFields);
		 //定义map对象
		 Map<String,String> map = new HashMap<String,String>();
		 //遍历数组元素，并保存到map
		 for(int i=0;i<sqlFieldList.size();i++){
			map.put(sqlFieldList.get(i),entityFieldList.get(i));
		}
		return map;
	 }
	 /**
	  * 把映射关系转化为Ｍap
	  * @param mappingFields 数组元素[０]为查询sql字段字符串，数组元素[１]实体字段字符串
	  * @return　map sql字段作为Key,实体字段为value
	  */
	 public static Map mappingEntityField(String[] mappingFields){
		 //获取查询sql字段字符串
		 String[] sqlFields = mappingFields[0].split(",");
		//获取实体字段字符串
		 String[] entityFields = mappingFields[1].split(",");
		 
		 //字符串转化List
		 List<String> sqlFieldList = Arrays.asList(sqlFields);
		//字符串转化List
		 List<String> entityFieldList = Arrays.asList(entityFields);
		 //定义map对象
		 Map<String,String> map = new HashMap<String,String>();
		 //循环遍历List
		 for(int i=0;i<sqlFieldList.size();i++){
			//两个List转化为map对象
			map.put(sqlFieldList.get(i),entityFieldList.get(i));
		}
		return map;
	 }
	 /**
	  * 把映射关系转化为Ｍap
	  * @param sqlFields sql字段字符串
	  * @param entityFields　实体字段字符串
	  * @return　map sql字段作为Key,实体字段为value
	  */
	 public static Map mappingEntityField(String sqlFields,String entityFields){
		 //通过逗号分隔，转化为数组
		 String[] sqlArray = sqlFields.split(",");
		 //通过逗号分隔，转化为数组
		 String[] entityArray = entityFields.split(",");
		 //字符串转化List
		 List<String> sqlFieldList = Arrays.asList(sqlArray);
		//字符串转化List
		 List<String> entityFieldList = Arrays.asList(entityArray);
		 //定义map对象
		 Map<String,String> map = new HashMap<String,String>();
		 //循环遍历List
		 for(int i=0;i<sqlFieldList.size();i++){
			//两个List转化为map对象
			map.put(sqlFieldList.get(i),entityFieldList.get(i));
		}
		return map;
	 }
	 /**
	  * 把映射关系转化为Ｍap
	  * @param sqlFields sql字段List
	  * @param entityFields　实体字段List
	  * @return　map sql字段作为Key,实体字段为value
	  */
	 public static Map mappingEntityField(List sqlFieldList,List entityFieldList){
		//定义map对象
		 Map<String,String> map = new HashMap<String,String>();
		 //循环遍历List
		 for(int i=0;i<sqlFieldList.size();i++){
			//两个List转化为map对象
			map.put(sqlFieldList.get(i).toString(),entityFieldList.get(i).toString());
		}
		return map;
	 }
	 /**
	  * 把映射关系转化为Ｍap
	  * @param mappingFields  list.get(0) sql字段List,list.get(1)实体字段字符串
	  * @return　map sql字段作为Key,实体字段为value
	  */
	 public static Map mappingEntityField(List mappingFieldList){
		 //通过逗号分隔，转化为数组
		 String[] sqlFields = (String.valueOf(mappingFieldList.get(0))).split(",");
		 //通过逗号分隔，转化为数组
		 String[] entityFields = (String.valueOf(mappingFieldList.get(1))).split(",");
		 //字符串转化List
		 List<String> sqlFieldList = Arrays.asList(sqlFields);
		//字符串转化List
		 List<String> entityFieldList = Arrays.asList(entityFields);
		//定义map对象
		 Map<String,String> map = new HashMap<String,String>();
		 for(int i=0;i<sqlFieldList.size();i++){
			 map.put(sqlFieldList.get(i), entityFieldList.get(i));
		 }
		 return map;
	 }
	 /**
		 * 根据传入的部分参数和值用于更新对象
		 * ""{'id':'111','entityName':'TsJs','jslxId':'1202323','ms':'中仍','start':0,'limit':25}"
		 * @param jsonObj 字符串转化后json
		 * @param obj 实体对象
		 * @throws Exception 
		 */
	 public static Object updatePartEntityField(JSONObject jsonObj ,Object obj) throws Exception{
//		 Class cls =obj.getClass();
		 return getObject(jsonObj, obj);
	 }
	 
	 private static Object getObject(JSONObject jsonObj,Object obj) throws Exception{
			//获取反射的类
			Class cls = obj.getClass();//TsJs.class
			//实例化类
//			Object obj = cls.newInstance();
			//获取反射类的所有字段
			Field[] fields =cls.getDeclaredFields();
			//获取jsonObj的所有Key
			Set<String> mapSet =jsonObj.keySet();
			//Key转化为Iterator
			Iterator<String> iter = mapSet.iterator();
			//遍历iter
			while(iter.hasNext()){
				//获取Key
				String field = iter.next();
				//循环遍历对象的所有属性
				for(Field f :fields){
					//获取类的字段属性
					Field fi = f;
					//判断是key和属性名是否一致
					if(field.equals(f.getName())){
						Object valueObj = null;
						if(fi.getType().getName().equals("java.lang.Boolean") || fi.getType().getName().equals("java.lang.boolean")){
							valueObj = ObjectUtils.toObject((jsonObj.get(field).equals("1")?true:false), fi.getType());
						}else if(fi.getType().getName().equals("java.lang.Integer") || fi.getType().getName().equals("java.lang.int")){
							Class c=jsonObj.get(field).getClass();
							if(ClassUtils.isNumber(c)){
								valueObj=ObjectUtils.toObject(jsonObj.get(field),fi.getType());
							}else if(ClassUtils.isBoolean(c)){
								valueObj = jsonObj.get(field).equals(true)?1:0;
									//ObjectUtils.toObject((), fi.getType());
							}else if(ClassUtils.isString(c)){
								valueObj =ObjectUtils.toObject(jsonObj.get(field), fi.getType());
							}else{
								throw new Error("暂不支持"+c.getName()+"->数值的转换");
							}
						}else if(fi.getType().getName().equals("java.lang.Long") || fi.getType().getName().equals("java.lang.long")){
							valueObj = ObjectUtils.toObject((jsonObj.get(field).equals("")?null:jsonObj.get(field)), fi.getType());
						}else{
							//获取值类型转化为属性类型
							valueObj = ObjectUtils.toObject(jsonObj.get(field), fi.getType());
						}
						//将值放到对象的属性中
						JsonsUtils.invokeSetter("set"+StringUtils.capitalize(fi.getName()), obj, cls, valueObj, fi.getType());
						break;
					}
				}
			}
			return obj;
	 }
}
