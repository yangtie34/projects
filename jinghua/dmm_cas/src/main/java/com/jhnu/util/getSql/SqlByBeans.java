package com.jhnu.util.getSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

public class SqlByBeans {
	String tablename=null;
	/*
	 * 插入多个对象
	 */
	public String insertSql(List list){
//		insert all into t_code
//		  (code_,name_,code_type)
//		 values( 'a','a','a')
//		 into t_code
//		  (code_,name_,code_type)
//		 values( 'a1','a1','a1')
//		 SELECT 1 FROM DUAL;	
		String sql=null;
		String cloumns=null;
		List vallist=new ArrayList();		
		Map<String, Method> map=getFieldValue(list.get(0));
		 for (String key : map.keySet()) {
			 if(cloumns==null){
				 cloumns=key;
			 }else{
				 cloumns+=","+key;
			 }
		 }
		 
		 for(int i=0;i<list.size();i++){
			 String values=null;
			  try {
			  for (String key : map.keySet()) {
				 if(values==null){
					values="'"+map.get(key).invoke(list.get(i))+"'";
				 }else{
					 values+=",'"+map.get(key).invoke(list.get(i))+"'";
				 }
			  }
			  vallist.add(values);
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
			  sql="insert all ";
			  for(int i=0;i<vallist.size();i++){
				  sql+=" into "+tablename+"("+cloumns+") values ("+vallist.get(i)+") ";
			  }
			  sql+=" SELECT 1 FROM DUAL ";
		return sql;
	}
	
	/*
	 * 更新多个对象
	 */
	public String[] updateSql(List list){	
		String[] sqls=new String[list.size()];
		Map<String, Method> map=getFieldValue(list.get(0));		 
		 for(int i=0;i<list.size();i++){
			 String values=null;
			 String sql="update "+tablename+" set ";
			  try {
			  for (String key : map.keySet()) {
				 if(values==null){
					values=key+"='"+map.get(key).invoke(list.get(i))+"'";
				 }else{
					 values+=","+key+"='"+map.get(key).invoke(list.get(i))+"'";
				 }
			  }
				sql+=values+" where id='"+list.get(i).getClass().getMethod("getId").invoke(list.get(i))+"'; ";
				List ins=new LinkedList();
				ins.add(list.get(i));
				String sqlinsert=insertSql(ins);
				sql="BEGIN   "+
						sql+
				" if sql%rowcount=0 or sql%rowcount is null then "+
				sqlinsert+";"+
				"end if;"+
				"end;";
				
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  sqls[i]=sql;
		}
		return sqls;
	}
	/*
	 * 删除多个对象
	 */
	public String[] deleteSql(List list){	
		String[] sqls=new String[list.size()];
		getFieldValue(list.get(0));
		Map<String, Method> map=getFieldValue(list.get(0));	
		try {
		Method m = list.get(0).getClass().getMethod("getId");
		 for(int i=0;i<list.size();i++){
			 String values=null;
			 String sql="delete from "+tablename+" where id='"+m.invoke(list.get(i))+"' ";
			 sqls[i]=sql;
		}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqls;
	}	
	public Map getFieldValue(Object obj){
		  Class cla=obj.getClass();
		  //获取表名
		  Table table=(Table) cla.getAnnotation(Table.class);
		   tablename=table.name();
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
	
}
