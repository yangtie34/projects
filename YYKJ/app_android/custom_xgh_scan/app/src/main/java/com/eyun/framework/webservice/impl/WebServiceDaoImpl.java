package com.eyun.framework.webservice.impl;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.webservice.SoapObjectUtil;
import com.eyun.framework.webservice.WebServiceDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebServiceDaoImpl implements WebServiceDao {

	private static volatile com.eyun.framework.webservice.impl.WebServiceDaoImpl instance;
	private WebServiceDaoImpl() {
	}

	public static com.eyun.framework.webservice.impl.WebServiceDaoImpl getInstance() {
		if (instance == null) {
			synchronized (com.eyun.framework.webservice.impl.WebServiceDaoImpl.class) {
				if (instance == null) {
					instance = new com.eyun.framework.webservice.impl.WebServiceDaoImpl();
				}
			}
		}
		return instance;
	}
	private BaseWebServiceDaoImpl baseWebServiceDao= BaseWebServiceDaoImpl.getInstance();

	@Override
	public List<Map<String, Object>> queryForList(String url, final String methodName,HashMap<String, Object> properties) {

		return SoapObjectUtil.forList(baseWebServiceDao.callWebServiceUI(url,methodName,properties));
	}

	@Override
	public ResultMsg queryForResultMsg(String url, final String methodName, HashMap<String, Object> properties) {
		return SoapObjectUtil.forResultMsg(baseWebServiceDao.callWebServiceUI(url,methodName,properties));
	}

	@Override
	public <T> List<T> queryForListBean(String url, final String methodName,HashMap<String, Object> properties ,Class<T> cls) {
		return SoapObjectUtil.forListBean(baseWebServiceDao.callWebServiceUI(url,methodName,properties),cls);
	}
}
