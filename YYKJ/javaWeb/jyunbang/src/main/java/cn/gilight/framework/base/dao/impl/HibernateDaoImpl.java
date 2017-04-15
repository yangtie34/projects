package cn.gilight.framework.base.dao.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.base.dao.IDGenerator;
import cn.gilight.framework.uitl.ReflectUtils;
import cn.gilight.framework.uitl.TypeConvert;
@Component("hibernateDao")
public class HibernateDaoImpl implements HibernateDao{
	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private IDGenerator idGenerator;
	
	private int hibernate_batch_size = 20;
	
	public IDGenerator getIdGenerator() {
		return idGenerator;
	}
	public void setIdGenerator(IDGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> createQuery(String queryString) {
		return getSession().createSQLQuery(queryString).list();
	}

	@Override
	public Object save(Object t) throws SecurityException, NoSuchFieldException {
		if(ReflectUtils.isContain(t, "id")){
			if(ReflectUtils.invokeGetter(t, "id") == null){
				ReflectUtils.invokeSetter(t, "id", new Object[]{idGenerator.getId()});
			}
		}
		Object result =  getSession().save(t);
		return result;
	}

	@Override
	public void saveAll(List<?> list) throws SecurityException, NoSuchFieldException {
		// TODO Auto-generated method stub
		List<String> idlist = idGenerator.getIds(list.size());
		
		Session session = getSession();
		for(int i=0;i<list.size();i++){
			Object object = list.get(i);
			if(ReflectUtils.isContain(object, "id")){
				if(ReflectUtils.invokeGetter(object, "id") == null){
					ReflectUtils.invokeSetter(object, "id", new Object[]{idlist.get(i)});
				}
			}
			session.save(object);
			if (i!=0 && i % hibernate_batch_size == 0 ) { 
			   session.flush();
			   session.clear();
			}
		}
	}
	
	@Override
	public void update(Object model) {
		getSession().update(model);
	}
	
	@Override
	public void updates(List<?> list) {
		// TODO Auto-generated method stub
		Session session = getSession();
		for(int i=0;i<list.size();i++){
			session.update(list.get(i));
			if (i!=0 &&  i % hibernate_batch_size == 0 ) { 
				 session.flush();
				 session.clear();
			}
		}
	}

	@Override
	public void delete(Object model) {
		getSession().delete(model);
	}

	@Override
	public void deletes(List<?> list) {
		// TODO Auto-generated method stub
		for(Object obj : list){
			getSession().delete(obj);
		}
	}
	
	@Override
	public <T> void deleteByIds(List<?> ids, Class<T> cls) {
		// TODO Auto-generated method stub
		Session session = getSession();
		for(Object id : ids){
			session.delete(session.get(cls, (Serializable) id));
		}
	}
	
	@Override
	public <T> void deleteAll(Class<T> cls){
		Table table = cls.getAnnotation(Table.class);
		String tablename = table.name();
		String sql = "delete from "+tablename;
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List createHqlQuery(String hql) {
		return getSession().createQuery(hql).list();
	}
	
	@Override
	@SuppressWarnings({ "unchecked"})
	public <T> T getById(Serializable id, Class<T> cls) {
		return (T) getSession().get(cls, id);
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public <T> List<T> findByExample(T t) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(t.getClass());
		criteria.add(Example.create(t));
		return criteria.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T>List<T> getByIds(String[] ids, Class<T> cls) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery(
		"FROM " + cls.getSimpleName() + " WHERE id IN(:ids)")
		.setParameterList("ids", ids).list();
	}

	@Override
	public <T>Long getTotalCount(T t){
		Criteria criteria = getSession().createCriteria(t.getClass());
		criteria.add(Example.create(t));
		criteria.setProjection(Projections.rowCount());
		Long count = TypeConvert.toLong(criteria.uniqueResult());
		return count;
	}

	@Override
	public int update(String hql, Object... field) {
		System.out.println(hql);
		for (Object obj : field) {
			System.out.println(obj);
		}
		Query query = getSession().createQuery(hql);

		if (field.length != 0) {
			for (int i = 0; i < field.length; i++) {
				query.setParameter(i, field[i]);
			}
		}
		return query.executeUpdate();
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}