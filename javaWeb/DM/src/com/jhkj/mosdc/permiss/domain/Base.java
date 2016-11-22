package com.jhkj.mosdc.permiss.domain;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jhkj.mosdc.permiss.po.TpMenu;

public class Base implements ApplicationContextAware{
	private SessionFactory sessionFactory;
	private ApplicationContext context;
	
	/**
	 * 获取一个会话
	 * @return
	 */
	public Session getSession(){
		return sessionFactory.openSession();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getEntityListBySql(Class cls,String sql){
		Session session = getSession();
		List list = (List<TpMenu>) session.createSQLQuery(sql).addEntity(cls).list();
		session.close();
		return list;
	}
	@SuppressWarnings("rawtypes")
	protected Object getEntity(Object entity) {
		List list = (List) getEntityList(entity);
		return list.size() == 1 ? list.get(0) : null;
	}

	protected Object getEntityList(Object entity) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(entity.getClass());
		criteria.add(Example.create(entity));
		List list = criteria.list();
		session.close();
		return list;
	}
	protected Long countByEntity(Object entity){
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(entity.getClass());
		criteria.add(Example.create(entity));
		criteria.setProjection(Projections.rowCount());
		Long num = Long.valueOf(criteria.uniqueResult().toString());
		session.close();
		return num;
	}

	protected Object getEntityById(Long id, Class cls) {
		Session session = sessionFactory.openSession();
		Object obj = session.get(cls, id);
		session.close();
		return obj;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		context = arg0;
	}
	/**
	 * 通过Bean ID 获取当前对象在Spring容器中的实例
	 * @param beanId
	 * @return
	 */
	public Object getBean(String beanId){
		return context.getBean(beanId);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static String getUserGroupCondition(Long usergroupId){
		String condition = null;
		if(usergroupId == null)condition = " is null "; else condition = "="+usergroupId;
		return condition;
	}
	
}
