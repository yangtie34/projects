package test.test1.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 反射工具类,主要供MVC使用
 *
 * <br/>
 * E-mail: IsaidIwillgoon@gmail.com <br/>
 * Date: 2015-3-12 <br/>
 * Time: 上午10:10:34 <br/>
 * @author hank
 * @version 1.0
 */
public class ReflectUtil {
	
	/**
	 * 根据methodname查询Method对象,暂不支持同名方法且参数个数相同的重载方法
	 * @param cls
	 * @param methodname
	 * @return
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("rawtypes")
	public static Method findMethod(Class cls,String methodname,int plen) throws NoSuchMethodException{
		List<Method> methods = getInterfaceMethods(cls);
		for(Method method : methods){
			if(methodname.equals(method.getName()) && method.getParameterTypes().length == plen){
				return method;
			}
		}
		throw new NoSuchMethodException(new StringBuffer("cann’t find method:").append(cls.getName()).append(".").append(methodname).append(",please check your parameters.").toString());
	}
	
	@SuppressWarnings("rawtypes")
	private static List<Method> getInterfaceMethods(Class clazz){
		Class[] interfaces = clazz.getInterfaces();
		
		List<Method> list = new ArrayList<Method>();
		
        for(Class cls : interfaces){
        	list.addAll(Arrays.asList(cls.getDeclaredMethods()));
        }
        return list;
	}
	
	public static Object invoke(Class<?> klass,String methodname,String jsonArray) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return invoke(klass.getName(), methodname, jsonArray);
	}
	/**
	 * 执行反射调用
	 * 
	 * @param classname
	 * @param methodname
	 * @param jsonArray
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static Object invoke(String classname, String methodname, String jsonArray)
			throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		/* 1、找到相同方法列表 2、找到相同参数个数的该方法 3、匹配剩余方法中的参数类型,尝试转换 4、将最相近的留下(暂不支持同参数个数的重写)，并执行 5、如果没有,则抛异常 */
		
		Class<?>  	klass = Class.forName(classname);
		JSONArray 	array = JSONArray.parseArray(jsonArray);
		List<Method> list = new ArrayList<Method>();
		for (Method m : klass.getMethods()) {
			if (methodname.equals(m.getName()) && m.getParameterTypes().length == array.size()) {// 且参数个数一致,暂支持一个,因此break
				list.add(m);break;
			}
		}
		/*1、如果list为空,则抛异常 2、如果list不唯一,则进行相似度匹配(暂不支持) */
		if (list.size() != 1) {
			throw new NoSuchMethodException(new StringBuffer("cann’t find method:").append(klass.getName()).append(".").append(methodname).append(",please check your parameters.").toString());
		}
		Object object = klass.newInstance();
		Method method = list.get(0);// 暂不支持同名方法,相同参数的情况
		return invoke(object, method, array);
	}
	/**
	 * 执行反射调用
	 * @param object
	 * @param method
	 * @param jsonArrayParams
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invoke(Object object,Method method,JSONArray jsonArrayParams) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		Object[] args = convert(method.getGenericParameterTypes(),jsonArrayParams);// 进行参数-值转换
		
		Object result = null;
		if (args == null) {
			result = method.invoke(object);
		} else {
			result = method.invoke(object, args);
		}
		
		return result;//result = args==null?(method.invoke(object)):(method.invoke(object, args));//效率差
	}
	
	public static Object[] convert(Type[] types, JSONArray array) {
		Type type = null;
		Object[] paramObjects = new Object[types.length];
		for (int i = 0, len = types.length; i < len; i++) {
			type = types[i];
			if (type instanceof ParameterizedType) {// 如果是泛型
				ParameterizedType ptype = (ParameterizedType) type;
				if (type.toString().startsWith("java.util.List")) {// 如果是List
					paramObjects[i] = listConvert(ptype, array.getJSONArray(i));
				} else if (type.toString().startsWith("java.util.Map")) {// 如果是Map
					paramObjects[i] = mapConvert(ptype, array.getJSONObject(i));
				}
			} else { // 如果是基本类型
				paramObjects[i] = basicConvert(type, array.get(i));
			}
		}
		return paramObjects;
	}
	
	/**
	 * MAP转换
	 * @param type
	 * @param json
	 * @return
	 */
	public static Map<String, Object> mapConvert(ParameterizedType type, JSONObject json) {
		Type[] ata = type.getActualTypeArguments();
		int length = ata.length;
		if (length == 0) { return json; }
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> entry : json.entrySet()) {
			map.put(entry.getKey(), basicConvert(ata[1], entry.getValue()));
		}
		return map;
	}
	
	/**
	 * List转换
	 * @param type
	 * @param json
	 * @return
	 */
	public static List<Object> listConvert(ParameterizedType type, List<Object> json) {
		Type[] ata = type.getActualTypeArguments();
		int length = ata.length;
		if (length == 0) { return json; } 
		Type t = ata[0];
		List<Object> list = new ArrayList<Object>();
		for (int i = 0, len = json.size(); i < len; i++) {
			list.add(basicConvert(t, json.get(i)));
		}
		return list;
	}
	
	/**
	 * 基本类型转换
	 * @param type
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object basicConvert(Type type, Object value) {
		if (TypeUtil.isBaseType(type) || TypeUtil.isPackageType(type)) {//基础
			if (TypeUtil.isBoolean(type)) {
				return toBoolean(value);
			} else if (TypeUtil.isString(type)) {
				return toString(value);
			} else if (TypeUtil.isInt(type)) {
				return toInteger(value);
			} else if(TypeUtil.isDouble(type)){
				return toDouble(value);
			}else if(TypeUtil.isLong(type)){
				return toLong(value);
			}
		}else if(TypeUtil.isObject(type)){
			return value;
		} else {// Bean
			if(type.toString().contains("java.util.Map")){
				if(type instanceof ParameterizedType){
					ParameterizedType ptype = (ParameterizedType) type;
					if (type.toString().startsWith("java.util.Map")){
						return mapConvert(ptype, (JSONObject) value);
					}else{
						return value;
					}
				}else{
					return value;
				}
			}else{
				return JSON.parseObject(value.toString(), (Class) type);
			}
		}
		return value;
	}
	
	public static Boolean toBoolean(Object o){
		return Boolean.valueOf(String.valueOf(o));
	}
	
	public static String toString(Object o){
		return String.valueOf(o);
	}
	
	public static Long toLong(Object o){
		return Long.valueOf(String.valueOf(o));
	}
	
	public static Float toFloat(Object o){
		return Float.valueOf(String.valueOf(o));
	}
	
	public static Double toDouble(Object o){
		return Double.valueOf(String.valueOf(o));
	}
	
	public static Integer toInteger(Object o){
		return Integer.valueOf(String.valueOf(o));
	}
	
	@SuppressWarnings("unchecked")
	public static Object toObject(Class<?> clazz,Object o) throws Throwable{
		Method[] methods=clazz.getDeclaredMethods();
		Map<String,Object> methodMap=new HashMap<String,Object>();
		for(Method m:methods){
			methodMap.put(m.getName(), m);
		}
		Object newObj=clazz.newInstance();
		if(o instanceof Map){
			Map<String,Object> oMap=(Map<String, Object>)o;
			Iterator<String> i=oMap.keySet().iterator();
			while(i.hasNext()){
				String key=i.next();
				Method m=(Method)methodMap.get("set"+key.substring(0,1).toUpperCase()+key.substring(1));
				m.invoke(newObj, ReflectUtil.basicConvert(m.getParameterTypes()[0], oMap.get(key)));
			}
		}
		return newObj;
	}
	
	
}
