package com.jhnu.syspermiss.util.jdbcUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.util.BeanMap;


/**
 * @author Administrator 基础DAO实现类
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseDao extends BaseDaoUtil{
	
	private BaseDao() {
		
	}  
    private static BaseDao baseDao=null;
	
	public static BaseDao getInstance() {
		if (baseDao == null){
			synchronized (new String()) {
				if (baseDao == null){
					baseDao = new BaseDao();
				}
			}
		}
		return baseDao;
	}
	
	public List queryForList(String sql) {
		return getRs(sql,new Object[]{});
	}
	public List queryForList(String sql,Object[] param) {
		return getRs(sql,param);
	}
	
	public List query(String sql, Class clazz,Object[] param) {
		List l=queryForList(sql,param);
		List list=new LinkedList();
		for(int i=0;i<l.size();i++){
			list.add(BeanMap.toBean(clazz, (Map)l.get(i)));
		}
		return list;
	}
	
	public List query(String sql, Class clazz,Object param) {
		return query(sql,clazz,new Object[]{param});
	}
	public List query(String sql, Class clazz) {
		return query(sql,clazz,new Object[]{});
	}
	
	public List queryForList(String sql, Class<String> class1,
			Object[] param) {
		return getRs(sql,param);
	}
	public List queryForList(String sql, Class<String> class1,
			String param) {
		return getRs(sql,new Object[]{param});
	}
	public String queryForObject(String sql, Class<String> class1,
			Object[] objects) {
		String a=(String) queryForList(sql, class1, objects).get(0);
		return a;
	}
	public void excute(String sql){
		excute(sql,new Object[]{});
	}
	public long getSeq(){
	String sql="select " + GetConn.getSeq() + ".nextval ID from dual";
	List<Object> l=queryForList(sql);
	return Long.parseLong(l.get(0).toString());
	}
}