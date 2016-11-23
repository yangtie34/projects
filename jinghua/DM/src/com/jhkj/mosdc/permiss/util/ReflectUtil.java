package com.jhkj.mosdc.permiss.util;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;

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
     * 通过属性名调用Po的Getter方法
     *
     * @param obj
     * @param fieldname
     * @return
     */
    public static Object invokeSetter(Object obj, String fieldname,Object value) throws SQLException {
        Class cls = obj.getClass();
        Field field = ReflectionUtils.findField(cls, fieldname);
        String setMethodName = "set" + StringUtils.capitalize(fieldname);
        Method method = ReflectionUtils.findMethod(cls, setMethodName, new Class[]{field.getType()});
        return ReflectionUtils.invokeMethod(method, obj, new Object[]{value});
    }
	/**
	 * 根据字段名获取一个对象的某个字段的值类型
	 * @return {String}
	 */
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
		Long l = (Long) invokeGetter(obj, "id");
		return l;
	}
}
