package com.jhkj.mosdc.newoutput.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Table;

import org.springframework.util.ReflectionUtils;


public class ReflectUtils {

	/**
	 * 通过属性名调用Po的Getter方法
	 * 
	 * @param obj
	 * @param fieldname
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getValue(Object obj, String fieldname) {
		Class cls = obj.getClass();
		String methodName = "get" + StringUtils.capitalize(fieldname);
		Method method = null;
		Object value = null;
		try {
			method = cls.getMethod(methodName, new Class[]{});
			value =  method.invoke(obj, new Object[]{});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
    /**
     * 设置对象对应域属性的值
     * @param obj
     * @param fieldname
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setValue(Object obj, String fieldname,Object value) {
    	Class cls = obj.getClass();
    	
		String methodName = "set" + StringUtils.capitalize(fieldname);
		Method method = null;
		try {
			Field field = cls.getDeclaredField(fieldname);
			method = cls.getMethod(methodName, new Class[]{field.getType()});
			value =  method.invoke(obj, new Object[]{value});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * 根据字段名获取一个对象的某个字段的值类型
	 * @return {String}
	 */
	@SuppressWarnings("rawtypes")
	public static String getType(Object obj,String fieldname){
		Class cls = obj.getClass();
		Field field = ReflectionUtils.findField(cls, fieldname);
		Class typeclass = field.getType();
		return typeclass.getSimpleName();
	}
	/**
	 * 获取实体对象的表名
	 * @param {String} obj
	 * @return
	 */
	public static String getTableName(Object obj) {
		Table table = obj.getClass().getAnnotation(Table.class);
		return table.name();
	}

    /**
     * 获取一个对象的id
     * @param {Object} obj
     * @return
     */
	public static Long getId(Object obj){
		Long l = (Long) getValue(obj, "id");
		return l;
	}
}
