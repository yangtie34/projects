package com.jhkj.mosdc.framework.dao.impl;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.jhkj.mosdc.framework.dao.BaseBatchDao;
import com.jhkj.mosdc.framework.dao.BaseDao;

public class BaseBatchDaoImpl extends JdbcDaoSupport  implements BaseBatchDao {
	private BaseDao baseDao = null;
	public void batchInsertWidthoutId(final List<?> objList,Class clazz) throws Throwable{		
		if(clazz==null){
			throw new RuntimeException("类不能为空");
		}
		Table table=(Table)clazz.getAnnotation(Table.class);
		String tableName=table.name();		
		Method[] methods=clazz.getDeclaredMethods();
		final Method setIdMethod=clazz.getDeclaredMethod("setId", Long.class);
		Map methodMap=new HashMap();
		List methodList=new ArrayList();
		List<String> columnNameList=new ArrayList();
		for(int i=0;i<methods.length;i++){
			Method method=methods[i];
			if(method.getName().indexOf("get")>-1){
				methodList.add(method);
				Column column=method.getAnnotation(Column.class);
				String columnName=column.name();
				columnNameList.add(columnName);
				methodMap.put(columnName, method);
				
			}
			
		}
		StringBuilder sbd=new StringBuilder();
		sbd.append("insert into ").append(tableName).append("(");
		for(String s:columnNameList){
			sbd.append(s).append(",");
		}
		sbd.replace(sbd.length()-1, sbd.length(), ")");
		sbd.append(" values(");
		for(String s:columnNameList){
			sbd.append("?").append(",");
		}
		sbd.replace(sbd.length()-1, sbd.length(), ")");
		final Map tmpMethodMap=methodMap;
		final List<Method> tmpMethodList=methodList;	
		final List<String> tmpColumnNameList=columnNameList;
		final List<Long> idList=this.baseDao.getMutiId(objList.size());
		this.getJdbcTemplate()
				.batchUpdate(
						sbd.toString(),
						new BatchPreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								Object o=objList.get(i);
								Long id=idList.get(i);
								try {
									setIdMethod.invoke(o, id);
								} catch (Throwable e1) {
									throw new RuntimeException(e1);
								} 
								for(int j=0;j<tmpColumnNameList.size();j++){
									String name=tmpColumnNameList.get(j);
									Method m=(Method)tmpMethodMap.get(name);
									Class type=m.getReturnType();
									Object value=null;
									try {
										value=m.invoke(o, null);										
									} catch (Throwable e) {								
										throw new RuntimeException(e);
									} 
									if(value!=null){
										if(Long.class.equals(type)){
											ps.setLong(j+1, (Long)value);
										}else if(String.class.equals(type)){
											ps.setString(j+1, (String)value);
										}else if(Integer.class.equals(type)){
											ps.setInt(j+1, (Integer)value);
										}else if(Short.class.equals(type)){
											ps.setShort(j+1, (Short)value);
										}else if(Boolean.class.equals(type)){
											Boolean b=(Boolean)value;
											if(b){
												ps.setInt(j+1, 1);
											}else{
												ps.setInt(j+1, 0);
											}
										}else{
											ps.setObject(j+1, value);
										}
									}else{
										ps.setObject(j+1, null);
									}
								}
							}

							@Override
							public int getBatchSize() {
								return objList.size();
							}
						});
	}
	
	@Override
	public void batchInsertWidthId(final List<?> objList, Class clazz) throws Throwable {
		if(clazz==null){
			throw new RuntimeException("类不能为空");
		}
		Table table=(Table)clazz.getAnnotation(Table.class);
		String tableName=table.name();			
		Method[] methods=clazz.getDeclaredMethods();
		Map methodMap=new HashMap();
		List methodList=new ArrayList();
		List<String> columnNameList=new ArrayList();
		for(int i=0;i<methods.length;i++){
			Method method=methods[i];
			if(method.getName().indexOf("get")>-1){
				methodList.add(method);
				Column column=method.getAnnotation(Column.class);
				String columnName=column.name();
				columnNameList.add(columnName);
				methodMap.put(columnName, method);
				
			}
			
		}
		StringBuilder sbd=new StringBuilder();
		sbd.append("insert into ").append(tableName).append("(");
		for(String s:columnNameList){
			sbd.append(s).append(",");
		}
		sbd.replace(sbd.length()-1, sbd.length(), ")");
		sbd.append(" values(");
		for(String s:columnNameList){
			sbd.append(" ? ").append(",");
		}
		sbd.replace(sbd.length()-1, sbd.length(), ")");
		final Map tmpMethodMap=methodMap;
		final List<Method> tmpMethodList=methodList;	
		final List<String> tmpColumnNameList=columnNameList;
		final List<Long> idList=this.baseDao.getMutiId(objList.size());	
		this.getJdbcTemplate()
				.batchUpdate(
						sbd.toString(),
						new BatchPreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								Object o=objList.get(i);
								for(int j=0;j<tmpColumnNameList.size();j++){
									String name=tmpColumnNameList.get(j);
									Method m=(Method)tmpMethodMap.get(name);
									Class type=m.getReturnType();
									Object value=null;
									try {
										value=m.invoke(o, null);										
									} catch (Throwable e) {								
										throw new RuntimeException(e);
									} 
									if(value!=null){
										if(Long.class.equals(type)){
											ps.setLong(j+1, (Long)value);
										}else if(String.class.equals(type)){
											ps.setString(j+1, (String)value);
										}else if(Integer.class.equals(type)){
											ps.setInt(j+1, (Integer)value);
										}else if(Short.class.equals(type)){
											ps.setShort(j+1, (Short)value);
										}else if(Boolean.class.equals(type)){
											Boolean b=(Boolean)value;
											if(b){
												ps.setInt(j+1, 1);
											}else{
												ps.setInt(j+1, 0);
											}
										}else{
											ps.setObject(j+1, value);
										}
									}else{
										ps.setObject(j+1, null);
									}
								}
								
							}

							@Override
							public int getBatchSize() {
								return objList.size();
							}
						});

	
		
	}

	@Override
	public void batchUpdate(final List<?> objList, Class clazz) throws Throwable{
		if(clazz==null){
			throw new RuntimeException("类不能为空");
		}
		Table table=(Table)clazz.getAnnotation(Table.class);
		String tableName=table.name();			
		Method[] methods=clazz.getDeclaredMethods();
		Method getIdMethod=null;;
		Map methodMap=new HashMap();
		List methodList=new ArrayList();
		List<String> columnNameList=new ArrayList();
		for(int i=0;i<methods.length;i++){
			Method method=methods[i];
			if(method.getName().indexOf("get")>-1){
				methodList.add(method);
				Column column=method.getAnnotation(Column.class);
				String columnName=column.name();
				columnNameList.add(columnName);
				methodMap.put(columnName, method);
				if("getId".equals(method.getName())){
					getIdMethod=method;
				}
				
			}
			
		}
		if(getIdMethod==null){
			throw new RuntimeException("getId 方法必须");
		}
		
		StringBuilder sbd=new StringBuilder();
		sbd.append("update ").append(tableName).append("  set ");
		for(String s:columnNameList){
			sbd.append(s).append("=?,");
		}
		sbd.replace(sbd.length()-1, sbd.length(), " ").append(" where id=?");
		final Method tmpGetIdMethod=getIdMethod;
		final Map tmpMethodMap=methodMap;
		final List<Method> tmpMethodList=methodList;	
		final List<String> tmpColumnNameList=columnNameList;
		final List<Long> idList=this.baseDao.getMutiId(objList.size());	
		this.getJdbcTemplate()
				.batchUpdate(
						sbd.toString(),
						new BatchPreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								Object o=objList.get(i);
								Long id=null;
								try {
									id=(Long)tmpGetIdMethod.invoke(o, null);
								} catch (Throwable e1) {
									throw new RuntimeException(e1);
								} 
								if(id==null){
									throw new RuntimeException("id 必须不能为空");
								}								
								for(int j=0;j<tmpColumnNameList.size();j++){
									String name=tmpColumnNameList.get(j);
									Method m=(Method)tmpMethodMap.get(name);
									Class type=m.getReturnType();
									Object value=null;
									try {
										value=m.invoke(o, null);										
									} catch (Throwable e) {								
										throw new RuntimeException(e);
									} 
									if(value!=null){
										if(Long.class.equals(type)){
											ps.setLong(j+1, (Long)value);
										}else if(String.class.equals(type)){
											ps.setString(j+1, (String)value);
										}else if(Integer.class.equals(type)){
											ps.setInt(j+1, (Integer)value);
										}else if(Short.class.equals(type)){
											ps.setShort(j+1, (Short)value);
										}else if(Boolean.class.equals(type)){
											Boolean b=(Boolean)value;
											if(b){
												ps.setInt(j+1, 1);
											}else{
												ps.setInt(j+1, 0);
											}
										}else{
											ps.setObject(j+1, value);
										}
									}else{
										ps.setObject(j+1, null);
									}									
								}
								ps.setLong(tmpColumnNameList.size()+1, id);
								
							}

							@Override
							public int getBatchSize() {
								return objList.size();
							}
						});

	
		
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
}
