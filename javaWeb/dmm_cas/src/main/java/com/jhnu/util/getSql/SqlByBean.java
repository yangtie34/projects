package com.jhnu.util.getSql;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;
public class SqlByBean {
	public String getInsertSql(Object obj){
		List list=new ArrayList();
		list.add(obj);
		return new SqlByBeans().insertSql(list);
	}
	public String getUpdateSql(Object obj){
		List list=new ArrayList();
		list.add(obj);
		return new SqlByBeans().updateSql(list)[0];
	}
	public String getDelSql(Object obj){
		List list=new ArrayList();
		list.add(obj);
		return new SqlByBeans().deleteSql(list)[0];
	}
	public String getQuerySqlById(Object obj){
		  Class cla=obj.getClass();
		  //获取表名
		  Table table=(Table) cla.getAnnotation(Table.class);
		  String id = null;
			try {
				id = (String) obj.getClass().getMethod("getId").invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return  "select * from "+table.name()+" where id='"+id+"'";
	}
	public String getQuerySqlByIdOrder(Object obj){
		  Class cla=obj.getClass();
		  //获取表名
		  Table table=(Table) cla.getAnnotation(Table.class);
		  String id = null;
			try {
				id = (String) obj.getClass().getMethod("getId").invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return  "select * from "+table.name()+" where id='"+id+"' group by order_";
	}
	public String getQuerySqlByName_(Object obj){
		  Class cla=obj.getClass();
		  //获取表名
		  Table table=(Table) cla.getAnnotation(Table.class);
		  String name_ = null;
			try {
				name_ = (String) obj.getClass().getMethod("getName_").invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return  "select * from "+table.name()+" where name_ like '%"+name_+"%'";
	}
	public String getQuerySql(Object obj){
		  Class cla=obj.getClass();
		  //获取表名
		  Table table=(Table) cla.getAnnotation(Table.class);
		 
		return  "select * from "+table.name();
	}
}
