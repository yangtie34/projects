package com.jhnu.framework.base.dao.impl;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.jhnu.framework.base.dao.IDGenerator;

/**
 * 基于oracle序列的的ID生成策略
 * @Title: OracleSequenceIDGenerator.java 
 * @Package cn.gilight.framework.dp.id 
 * @date 2015年3月17日 上午11:12:21 
 */
public class OracleSequenceIDGenerator implements IDGenerator,InitializingBean {
	
	private SessionFactory sessionFactory;
	
	private String sequenceName;
	
	private String lock = "lock";
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}


	@Override
	public String getId() {
		Session session = sessionFactory.openSession();
		Number id = (Number) session.createSQLQuery("select "+sequenceName+".nextval from dual").uniqueResult();
		session.close();
		return id.toString();
	}

	@Override
	public List<String> getIds(int num) {
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		long lid;
		synchronized (lock) {
			query = session.createSQLQuery("alter sequence "+sequenceName+" increment by "+num+" nocache");
			query.executeUpdate();
			
			Number id = (Number) session.createSQLQuery("select "+sequenceName+".nextval from dual").uniqueResult();
			lid = id.longValue();
			
			query = session.createSQLQuery("alter sequence "+sequenceName+" increment by 1 nocache");
			query.executeUpdate();
		}
		session.close();
		List<String> list = new ArrayList<String>(num);
		for(int i=0;i<num;i++){
			list.add((lid-num+i+1) + "" );
		}
		return list;
	}
	
	/**
	 * 判断并初始化Sequence
	 * @throws SQLDataException 
	 */
	private void sequenceInit() throws SQLDataException{
		if(sequenceName == null){
			System.out.println("---------------------------------------no seq");
			sequenceName = "ID_SEQ";
		}
		Session session = sessionFactory.openSession();
		//判断sequence是否存在，如果不存在，创建之
		Object object = session.createSQLQuery("select count(1) from user_sequences t where t.SEQUENCE_NAME = '"+sequenceName+"'").uniqueResult();
		session.close();
		if(object == null){
			throw new SQLDataException("序列SEQ_SYSTEM 未被创建,请创建该序列!");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		sequenceInit();
	}
}
