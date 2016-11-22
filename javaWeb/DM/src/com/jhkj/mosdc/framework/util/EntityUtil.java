package com.jhkj.mosdc.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * @Comments: 实体类相关的公共方法
 * Company: 河南精华科技有限公司
 * Created by gaodj(gaodongjie@126.com) 
 * @DATE:2013-6-1
 * @TIME: 下午3:41:45
 */
public class EntityUtil {
	/**
	 * 通过实体类获取表名
	 * @param classtype 实体类
	 * @return　实体对应的表名
	 */
	public static String getTableName(Class classtype) {
		//根据实体类获取所有注解
		Annotation[] anno = classtype.getAnnotations();
		//定义table名
		String tableName = "";
		//遍历所有注解
		for (int i = 0; i < anno.length; i++) {
			//判断注解是否为table
			if (anno[i] instanceof Table) {
				//如果是table，将其转化为table对象
				Table table = (Table) anno[i];
//				System.out.println(table.name());
				//将table的名称赋给tableName
				tableName = table.name();
				break;
			}
		}
		return tableName;
	}
	
	public static String getColumnName(Class classtype,String fieldName) throws Throwable{
		String getMethodName="get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
		Method m=classtype.getDeclaredMethod(getMethodName);
		Annotation[] anno=m.getAnnotations();
		String columnName="";
		for (int i = 0; i < anno.length; i++) {
			//判断注解是否为Column
			if (anno[i] instanceof Column) {
				Column column = (Column) anno[i];
				columnName = column.name();
			}
		}
		return columnName;
	}
	
	public static Object getSxValue(Object o,String sx) throws Throwable{
		String getMethod="get"+sx.substring(0,1).toUpperCase()+sx.substring(1);
		Method m=o.getClass().getDeclaredMethod(getMethod);
		if(m!=null){
			return m.invoke(o);
		}
		return null;
	}
	
	public static void copyto(Object from,Object to){		
		Field[] fields=from.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			String fieldName=field.getName();
			String letterName=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			String getName="get"+letterName;
			String setName="set"+letterName;
			try {
				Method getMethod=from.getClass().getMethod(getName, null);
				Method setMethod=from.getClass().getMethod(setName, field.getType());
				setMethod.invoke(to, getMethod.invoke(from, null));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public static Map getDataMapFromEntity(Object po){
		Map dataMap=new HashMap();
		Field[] fields=po.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			String fieldName=field.getName();
			String letterName=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			String getName="get"+letterName;
			try {
				Method getMethod=po.getClass().getMethod(getName, null);
				dataMap.put(fieldName, getMethod.invoke(po));
			} catch (Throwable e) {
				
				e.printStackTrace();
				throw new RuntimeException(e);
			} 
		}
		return dataMap;
	}
}
