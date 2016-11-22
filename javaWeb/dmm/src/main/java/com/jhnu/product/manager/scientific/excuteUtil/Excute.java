package com.jhnu.product.manager.scientific.excuteUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.common.BeanMap;
@Component("excute")
public class Excute {
	@Autowired
	private BaseDao baseDao;
	private SqlByClass getsql=new SqlByClass();
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public List query(String sql,Object obj){
		  Class cla=obj.getClass();
		  Field[] field=cla.getDeclaredFields();
		  Map<String, Method> map=new LinkedHashMap(); 
		  for(int i=0;i<field.length;i++){
			  String meth=field[i].getName().substring(0, 1).toUpperCase() + field[i].getName().substring(1);
			  Method methodGet;
			try {
				methodGet = cla.getMethod("get"+meth);
				  Method methodSet=cla.getMethod("set"+meth,field[i].getType());
				  map.put(methodGet.getAnnotation(Column.class).name(),methodSet); 
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
		List<Map<String, Object>> results = baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		List list=new LinkedList();
		for(int i=0;i<results.size();i++){
			 Object object=null;
			try {
				object = cla.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 for (String key : results.get(i).keySet()) {
				 try {
					 Object val=null;
					 if(results.get(i).get(key)!=null){
								val=results.get(i).get(key).toString();
					 }else{
						 val="";
					 }
						if(map.get(key).getParameterTypes()[0].getSimpleName().equalsIgnoreCase("Short")){
							 val=new Short(val+"");
						 }
					 map.get(key).invoke(object,val);
					 
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					System.out.println(results.get(i).get(key).toString());
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
				list.add(BeanMap.toMap(object));
			
		}
		return list;
	}
	public boolean insert(List list){
		String sql=getsql.insertSql(list);
		 try {
		baseDao.getBaseDao().getJdbcTemplate().execute(sql);
		return true;
		 } catch (Exception e) {
			 e.printStackTrace();
		 return false;
		 }
		
	}
	public boolean update(List list){
		String[] sql=getsql.updateSql(list);
		 try {
			 for(int i=0;i<sql.length;i++)
		baseDao.getBaseDao().getJdbcTemplate().execute(sql[i]);
		return true;
		 } catch (Exception e) { e.printStackTrace();
		 return false;
		 }
	}
	public boolean delete(List list){
		String[] sql=getsql.deleteSql(list);
		 try {
			 for(int i=0;i<sql.length;i++)
		baseDao.getBaseDao().getJdbcTemplate().execute(sql[i]);
		return true;
		 } catch (Exception e) { e.printStackTrace();
		 return false;
		 }
	}
}
