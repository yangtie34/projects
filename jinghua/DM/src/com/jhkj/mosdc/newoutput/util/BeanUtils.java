package com.jhkj.mosdc.newoutput.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class BeanUtils {
	/**
	 * 把一个Bean类转换成为Map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String field = fields[i].toString();
			String[] keys = field.split("\\.");
			String key = keys[keys.length - 1];
			char toUpperCase = (char) (key.charAt(0) - 32);
			String keyUpper = key.replace(key.charAt(0), toUpperCase);
			Method getMethod;
			try {
				getMethod = obj.getClass().getDeclaredMethod("get" + keyUpper);// 根据
																				// field得到对应的get方法
				Object value = getMethod.invoke(obj);
				map.put(key, value);
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
		}
		return map;
	}

	/**
	 * 提取一个Bean对象集合的共有属性的的值的集合
	 * 
	 * @param list
	 * @param property
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List pluck(List<?> list, String property) {
		Method getMethod = null;
		Object value = null;
		List<Object> result = new ArrayList<Object>();
		for (Object obj : list) {
			try {
				getMethod = obj.getClass().getDeclaredMethod(
						"get" + StringUtils.capitalize(property));// 根据
				// field得到对应的get方法
				value = getMethod.invoke(obj);
				result.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * Map Try To Bean,尝试着将map对应的数据转换成为bean对象 Map中的键 映射到Bean中的属性值 kcId
	 * --->kcid/kcId kc_id --->kcid/kcId/KCID/KcId/kc_id
	 * 
	 * @param map
	 * @param cls
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static Object mapToBean(Map map, Class cls) {
		Field[] fields = cls.getDeclaredFields();
		Object obj = null;
		try {
			obj = cls.newInstance();
			for (Field field : fields) {
				String name = field.getName();
				String type = field.getType().getSimpleName();
				Object value = null;
				value = map.get(name);// 原始匹配(完全匹配)
				if (value == null) {
					value = map.get(name.toLowerCase());// 小写匹配
				} 
				if (value == null) {
					value = map.get(name.toUpperCase());// 大写匹配
				} 
				if (value == null) {
					// 可能存在这种匹配map:get_name|bean getName
					List<String> tokens = splitByUpperCharacter(name,true);
					String s_name = StringUtils.join(tokens, "_");
					value = map.get(s_name);
				}
				if(value == null){
					// 可能存在这种匹配map:GET_NAME|bean getName
					List<String> tokens = splitByUpperCharacter(name,false);
					String s_name = StringUtils.join(tokens, "_");
					value = map.get(s_name);
				}
				if("String".equals(type)){
					String result = BasicTypesConvert.toString(value);
					Method method = cls.getMethod("set"+StringUtils.capitalize(name), String.class);
					method.invoke(obj, new Object[]{result});
				}else if("Boolean".equals(type)){
					Boolean result = BasicTypesConvert.toBoolean(value);
					Method method = cls.getMethod("set"+StringUtils.capitalize(name), Boolean.class);
					method.invoke(obj, new Object[]{result});
				}else if("Double".equals(type)){
					Double result = BasicTypesConvert.toDouble(value);
					Method method = cls.getMethod("set"+StringUtils.capitalize(name), Double.class);
					method.invoke(obj, new Object[]{result});
				}else if("Integer".equals(type)){
					Integer result = BasicTypesConvert.toInteger(value);
					Method method = cls.getMethod("set"+StringUtils.capitalize(name), Integer.class);
					method.invoke(obj, new Object[]{result});
				}else if("Long".equals(type)){
					Long result = BasicTypesConvert.toLong(value);
					Method method = cls.getMethod("set"+StringUtils.capitalize(name), Long.class);
					method.invoke(obj, new Object[]{result});
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 根据大写字母拆分字符串
	 * @return
	 */
	private static List<String> splitByUpperCharacter(String source,boolean lower) {
		List<String> list = new ArrayList<String>(10);
		StringBuilder bd = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if (c >= 'a' && c <= 'z') {
				bd.append(c);
			} else if (c <= 'Z' && c >= 'A') {
				if(lower){
				   list.add(bd.toString().toLowerCase());
				}else{
					list.add(bd.toString().toUpperCase());
				}
				bd = new StringBuilder();
				bd.append(c);
			}
		}
		if(lower){
			list.add(bd.toString().toLowerCase());
		}else{
			list.add(bd.toString().toUpperCase());
		}
		return list;
	}
	
	public static void main(String args[]) {
//		List<String> ag = BeanUtils.splitByUpperCharacter("getName",true);
//		System.out.println(ag);
		
//		Map map = new HashMap();
//		map.put("name", "zhangsan");
//		map.put("age", "124");
//		map.put("bj_id", 122323233);
//		map.put("bjid", 122323233);
//		map.put("BJID", 122323233);
//		map.put("BJ_ID", 122323233);
//		map.put("bjId", 122323233);
//		map.put("sexId", 134343);
//		Bean bean = (Bean) BeanUtils.mapToBean(map, Bean.class);
//		System.out.println(bean);
		
	}
}
