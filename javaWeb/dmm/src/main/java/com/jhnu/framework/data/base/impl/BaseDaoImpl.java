package com.jhnu.framework.data.base.impl;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.framework.data.base.JtaBaseDao;
import com.jhnu.framework.data.base.JtaLogDao;

/**
 * @author Administrator 基础DAO实现类
 * 
 */
public class BaseDaoImpl implements BaseDao {

	private JtaBaseDao jtaBaseDao;
	
	private JtaLogDao jtaLogDao;
	
	public JtaBaseDao getJtaBaseDao() {
		return jtaBaseDao;
	}
	public void setJtaBaseDao(JtaBaseDao jtaBaseDao) {
		this.jtaBaseDao = jtaBaseDao;
	}
	public JtaLogDao getJtaLogDao() {
		return jtaLogDao;
	}
	public void setJtaLogDao(JtaLogDao jtaLogDao) {
		this.jtaLogDao = jtaLogDao;
	}
	@Override
	public JtaBaseDao getBaseDao() {
		return jtaBaseDao;
	}
	@Override
	public JtaLogDao getLogDao() {
		return jtaLogDao;
	}
	
}
