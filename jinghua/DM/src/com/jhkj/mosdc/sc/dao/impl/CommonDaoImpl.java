package com.jhkj.mosdc.sc.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jhkj.mosdc.framework.po.ConditionsEntity;
import com.jhkj.mosdc.sc.dao.CommonDao;

public class CommonDaoImpl extends HibernateDaoSupport implements CommonDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> singleFieldStatistic(String tableName, String columnName,
			List<ConditionsEntity> conditions) {
		StringBuilder sbd = new StringBuilder();
		sbd.append("select count(*) counts,").append(columnName)
				.append(" from ").append(tableName).append(" where 1=1 ");
		if(conditions!=null && conditions.size()>0){
			for(ConditionsEntity condition:conditions){
				sbd.append(" and ").append(this.generateConditionStr(condition));
			}
		}
		sbd.append(" group by ").append(columnName);
		List<Object[]> l=this.getSession().createSQLQuery(sbd.toString()).list();
		List<Map> dataList=new ArrayList<Map>();
		for(Object[] os:l){
			Map m=new HashMap();
			m.put("counts", String.valueOf(os[0]));
			m.put(columnName, os[1]);
			dataList.add(m);
		}
		return dataList;
	}
	
	public List<Map> mutiFieldStatistic(String tableName,String[] columnNames,List<ConditionsEntity> conditions){
		StringBuilder sbd = new StringBuilder();
		StringBuilder columnSbd=new StringBuilder();
		sbd.append("select count(*) counts ,");
		for(String columnName:columnNames){
			columnSbd.append(columnName).append(",");
		}
		columnSbd.replace(columnSbd.length()-1, columnSbd.length(), " ");
		sbd.append(columnSbd).append(" from ").append(tableName).append(" where 1=1 ");
		
		if(conditions!=null && conditions.size()>0){
			for(ConditionsEntity condition:conditions){
				sbd.append(" and ").append(this.generateConditionStr(condition));
			}
		}
		sbd.append(" group by ").append(columnSbd);
		List<Object[]> l=this.getSession().createSQLQuery(sbd.toString()).list();
		List<Map> dataList=new ArrayList<Map>();
		for(Object[] os:l){
			Map m=new HashMap();
			m.put("counts", String.valueOf(os[0]));
			int i=1;
			for(String columnName:columnNames){
				m.put(columnName, os[i++]);
			}
			dataList.add(m);
		}
		return dataList;
	}
	/**
	 * @description 把查询所需要的条件进行拼接(eg:sex_code='1')
	 * @param condition 查询条件
	 * @return
	 */
	private String generateConditionStr(ConditionsEntity condition){
		return condition.getConditionName()+condition.getDt()+"'"+condition.getConditionValu()+"'";
	}

}
