package com.jhnu.edu.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

public class SqlByEdu {
	
	public static String getQuerySql(Object obj){
		String where="";
		  Map<String, Method> map=getFieldMethod(obj);
		  for (String key : map.keySet()) { 
			  String val = null;
			try {
				val = (String) map.get(key).invoke(obj);
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				
				e.printStackTrace();
			}
			  if(val!=null){
				  where+=" and "+key+"='"+val+"' " ; 
			  }
		  }
		String sql="select * from "+getTableName(obj)+" where 1=1 "+where;
		return sql;
	}
	public static Map<String, Method> getFieldMethod(Object obj){
		  Class cla=obj.getClass();
		  //获取字段名及方法
		  Method[] methods=cla.getDeclaredMethods();
		  Map<String, Method> map=new LinkedHashMap();
		  for(int i=0;i<methods.length;i++){
			  String methodname=methods[i].getName();
			  if(methodname.substring(0, 3).equalsIgnoreCase("get")){
				  map.put(methods[i].getAnnotation(Column.class).name(), methods[i]);  
			  }
		  }
		return map;
	}
	public static String getTableName(Object obj){
		Class cla=obj.getClass();
		  //获取表名
		  Table table=(Table) cla.getAnnotation(Table.class);
		  return table.name();
	}
}
