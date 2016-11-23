package com.jhnu.framework.data.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.framework.data.base.JtaBaseDao;

/**
 * @author Administrator 基础DAO实现类
 * 
 */
@Component("baseDao")
public class BaseDaoImpl implements BaseDao {

	@Resource
	private JtaBaseDao jtaBaseDao;
	
	
	public JtaBaseDao getJtaBaseDao() {
		return jtaBaseDao;
	}
	public void setJtaBaseDao(JtaBaseDao jtaBaseDao) {
		this.jtaBaseDao = jtaBaseDao;
	}
	
	@Override
	public JtaBaseDao getBaseDao() {
		return jtaBaseDao;
	}
	
}
