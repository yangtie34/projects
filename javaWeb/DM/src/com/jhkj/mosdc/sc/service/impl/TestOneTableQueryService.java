package com.jhkj.mosdc.sc.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.po.ConditionsEntity;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.sc.service.TestOneTableQuery;

public class TestOneTableQueryService extends BaseServiceImpl implements
		TestOneTableQuery {

	
	/**
	 * @description 拼接查询条件sql
	 * @param tableName
	 * @param propertyNames
	 * @param conditions
	 * @return
	 */
	public static String appendSql(String tableName, String[] propertyNames,
			List<ConditionsEntity> conditions) {
		StringBuffer sbf = new StringBuffer();
		StringBuffer groupBuffer = new StringBuffer();
		StringBuffer selectBuffer = new StringBuffer();
		String sql = "";
		groupBuffer.append(" group by ");
		if (conditions != null && conditions.size() > 0) {
			for (int j = 0; j < conditions.size(); j++) {
				sbf.append("and " + conditions.get(j).getConditionName())
						.append(" " + conditions.get(j).getDt())
						.append(" '" + conditions.get(j).getConditionValu()
								+ "'");
			}
		}
		for(int i = 0; i < propertyNames.length; i++){
			if(propertyNames[i].contains("count(")){
				selectBuffer.append(propertyNames[i]).append(" counts, ");
			} else {
				selectBuffer.append(propertyNames[i]).append(", ");
			}
			if(!propertyNames[i].contains("(")) {
				groupBuffer.append(propertyNames[i]+", ");
			}
		}
		String test1 = groupBuffer.toString().substring(0, groupBuffer.toString().length()-2);
		String test2 = selectBuffer.toString().substring(0, selectBuffer.toString().length()-2);
		sql = "select " + test2 + " from " + tableName
				+ " where 1=1 " + sbf + test1;
		return sql;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map> queryOneTable(String tableName,
			String[] propertyNames, List<ConditionsEntity> conditions) {
		String sql = appendSql(tableName, propertyNames, conditions);

		/*
		 * Session session = this.getSessionFactory().openSession(); Transaction
		 * tx = session.getTransaction(); tx.begin(); List<Map<String,Object>>
		 * qqq = session.createQuery(sql).list();
		 */
		/*List<Map<String, Object>> qqq = this.getSession().createSQLQuery(sql)
				.list();*/
		//List<Map<String, Object>> qqq = baseDao.getJdbcTemplate().queryForList(sql);
		List<Map> qqq = baseDao.queryListMapBySQL(sql);
		/*List<Object> xxxxxx = this.getSession().createSQLQuery(sql)
		.list();
		for(Object xx : xxxxxx) {
			try {
				Map<String,Object> xxxxxxxx = BeanUtils.describe(xx);
				Iterator<String> it = xxxxxxxx.keySet().iterator();
				while(it.hasNext()){
					String x = it.next();
					System.out.println(x);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}*/
		List<Map> resultList = new ArrayList<Map>();
		for (int i = 0; i < qqq.size(); i++) {
			Map<String, Object> xxxx = qqq.get(i);
			Map<String, Object> newMap = new HashMap<String, Object>();
			if (xxxx.get(propertyNames[i]) != null && !xxxx.get(propertyNames[i]).equals("")) {
				if(!propertyNames[i].contains("(")){
				String values = (String) xxxx.get(propertyNames[i]);
				newMap.put(propertyNames[i], values);
				}
			} else {
				newMap.put(propertyNames[i], "未维护");
			}
			resultList.add(newMap);
		}
		return resultList;
	}
	
	/*public List<Map<String,Object>> queryOneTableNames(String tableName, String[] propertyNames) {
		StringBuffer sbf = new StringBuffer();
		queryOneTable();
		for(int i = 0; i < propertyNames.length; i++) {
			String sql  = "select field_id, field_ch_name, connect_table_name from connect_table where ";
		}
		
		return null;
	}*/
}
