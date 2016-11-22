package com.jhkj.mosdc.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Comments: 通过实体类获取相应的方法或名称
 * Company: 河南精华科技有限公司
 * Created by gaodj(gaodongjie@126.com) 王永太编写
 * @DATE:2013-5-17
 * @TIME: 下午5:42:15
 */
public class ReflectUtil {

	/**
	 * 通过属性名调用Po的Getter方法
	 * 
	 * @param obj
	 * @param fieldname
	 * @return
	 */
	public static Object invokeGetter(Object obj, String fieldname) {
		Class cls = obj.getClass();
		String getMethodName = "get" + StringUtils.capitalize(fieldname);
		Method method = ReflectionUtils.findMethod(cls, getMethodName);
		return ReflectionUtils.invokeMethod(method, obj);
	}
	/**
	 * 根据字段名获取字段的值类型
	 * @return
	 */
	public static String getType(Object obj,String fieldname){
		Class cls = obj.getClass();
		Field field = ReflectionUtils.findField(cls, fieldname);
		Class typeclass = field.getType();
		return typeclass.getSimpleName();
		
	}
	/**
	 * 根据传入的class以及域属性名，查询出数据库中的字段名
	 * @param cls
	 * @param fieldname
	 * @return columnname
	 */
	public static String getColumnName(Class cls ,String fieldname){
		String getMethodName = "get" + StringUtils.capitalize(fieldname);
		Method method = ReflectionUtils.findMethod(cls, getMethodName);
		Column column = method.getAnnotation(Column.class);
		return column.name();
	}
	/**
	 * 获取实体对象的表名
	 * @param obj
	 * @return
	 */
	public static String getTableName(Object obj) {
		Table table = obj.getClass().getAnnotation(Table.class);
		return table.name();
	}
	public static Long getId(Object obj){
		Long l = (Long) invokeGetter(obj, "id");
		return l;
	}
}
