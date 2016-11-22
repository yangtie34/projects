package com.jhnu.edu.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.util.BeanMap;

public class DataCovert {
	public static Map<String, Object> coverMap(Map<String, Object> mapData,Map<String, String> Map) {
		Map<String, Object> mapret=new HashMap<String, Object>();
		for (String key : Map.keySet()) { 
			mapret.put(Map.get(key), mapData.get(key));
		}
		return mapret;
	}
	public static List<Map<String, Object>> coverList(List<Map<String, Object>> list,Object obj,Map<String, String> Map) {
		list=coverList(list,obj);
		List<Map<String, Object>> l=new ArrayList();
		for(int i=0;i<list.size();i++){
			l.add(coverMap(list.get(i),Map));
		}
		return l;
	}
	/*{id:{
		name:'学校名称',
		value:165}
	}*/
	public static Map<String, Object> coverList(List<Map<String, Object>> list) {
		Map<String, Object> Map=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("name", list.get(i).get("NAME"));
			m.put("value", list.get(i).get("VALUE"));
			Map.put((String) list.get(i).get("id"), m);
		}
		return Map;
	}
	//数据库查询数据转成bean对应map
	public static List<Map<String, Object>> coverList(List<Map<String, Object>> list,Object obj) {
		List<Map<String, Object>> l=null;
		try {
			l=coverList1(list, obj);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
		return l;
	}
	public static List<Map<String, Object>> coverList1(List<Map<String, Object>> list,Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(list.size()==0){
			list.add(new HashMap());
			return list;
		}
		Map<String, Method> mapField=SqlByEdu.getFieldMethod(obj);
		List<Map<String, Object>> relis=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			Object o=obj;
			 for (String key : mapField.keySet()) { 
				 Object value=map.get(key);
				 Method m=null;
					m=obj.getClass().getMethod(mapField.get(key).getName().replace("get", "set"),String.class);
					m.invoke(o, value);
			 }
			 relis.add(BeanMap.toMap(o));
		}
		return relis;
	}
	public static List<Object> listToBeans(List<Map<String, Object>> list,Object obj)  {
		if(list.size()==0){
			return null;
		}
		Map<String, Method> mapField=SqlByEdu.getFieldMethod(obj);
		List<Object> relis=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			Object o=obj;
			 for (String key : mapField.keySet()) { 
				 String value=map.containsKey(key)? map.get(key).toString():null;
				 Method m=null;
					try {
						m=obj.getClass().getMethod(mapField.get(key).getName().replace("get", "set"),String.class);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						m.invoke(o, value);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 }
			 relis.add(o);
		}
		return relis;
	}
}
