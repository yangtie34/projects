package com.jhkj.mosdc.framework.message.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.message.dao.SuggestDao;
import com.jhkj.mosdc.framework.message.po.TsMsgSuggest;

public class SuggestDaoImpl extends BaseDaoImpl implements SuggestDao {

	@Override
	public Boolean addSuggest(TsMsgSuggest suggest) throws Exception {
		// TODO Auto-generated method stub
		suggest.setId(getId());
		this.save(suggest);
		return true;
	}

	@Override
	public Boolean updateSuggest(TsMsgSuggest suggest) {
		// TODO Auto-generated method stub
		this.update(suggest);
		return true;
	}

	@Override
	public Boolean deleteSuggest(String ids) {
		// TODO Auto-generated method stub
		this.delete(ids, "ts_msg_suggest");
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsMsgSuggest> queryMineSuggest(TsMsgSuggest suggest,int start,int end) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().findByExample(suggest, start, end);
	}
	
	
	@Override
	public List<TsMsgSuggest> queryAllSuggest(TsMsgSuggest suggest,int start,int end) {
		return this.getHibernateTemplate().findByExample(suggest, start, end);
	}

	public Session getCurrentSession(){
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

	

}
