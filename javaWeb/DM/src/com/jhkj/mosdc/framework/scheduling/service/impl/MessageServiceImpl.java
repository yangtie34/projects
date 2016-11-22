package com.jhkj.mosdc.framework.scheduling.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ApplicationAware;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import com.jhkj.mosdc.framework.dao.BaseDao;
//import com.jhkj.mosdc.framework.message.po.TsEventMsg;
//import com.jhkj.mosdc.framework.message.po.TsEventSendRange;
import com.jhkj.mosdc.framework.scheduling.po.Message;
import com.jhkj.mosdc.framework.scheduling.po.MessageConfig;
import com.jhkj.mosdc.framework.scheduling.service.MessageService;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;

public class MessageServiceImpl implements ApplicationContextAware,MessageService {
	private List<MessageConfig> listConfig;
	private ApplicationContext applicationContext;

	public void setListConfig(List<MessageConfig> listConfig) {
		this.listConfig = listConfig;
	}


	@Override
	public String queryMessages(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		JSONArray names = json.getJSONArray("names");
		List<Message> list = this.reflectDynamicCall(names);
		
		return Struts2Utils.list2json(list);
	}
	
	public List<Message> reflectDynamicCall(List names){
		List<MessageConfig> listC = this.getMessageConfigListByNames(names);
		List<Message> list = new ArrayList<Message>();
		for(int i=0;i<listC.size();i++){
			MessageConfig config = listC.get(i);
			if(config == null)continue;
			Object bean = this.applicationContext.getBean(config.getService());
			String methodName = config.getMethod();
			List<Message> itemList = callMethod(bean, methodName);
			list.addAll(itemList);
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Message> callMethod(Object obj,String methodName){
		Method method = ReflectionUtils.findMethod(obj.getClass(),methodName);
		return (List<Message>) ReflectionUtils.invokeMethod(method, obj);
	}
	
	public List<MessageConfig> getMessageConfigListByNames(List names){
		List<MessageConfig> list = new ArrayList<MessageConfig>();
		for(Object name : names){
			list.add(getMessageConfigByName(name.toString()));
		}
		return list;
	}
	/**
	 * 根据name匹配MessageConfig对象
	 * @param name
	 * @return
	 */
	public MessageConfig getMessageConfigByName(String name){
		for(MessageConfig mc : listConfig){
			if(mc.getName().equals(name)){
			   return mc;
			}
		}
		return null;
	}
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = context;
	}
	

	@Override
	public String updateReadYetFlag(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		long id = json.getLong("id");
		BaseDao dao = (BaseDao) this.applicationContext.getBean("baseDao");
		String hql = "from TsEventSendRange tsr where tsr.eventId="+id;
//		TsEventSendRange tsr = (TsEventSendRange) dao.queryHql(hql).get(0);
//		tsr.setReadYet(true);
//		tsr.setReadTime(DateUtils.date2StringV2(new Date()));
		return SysConstants.JSON_SUCCESS_TRUE;
	}

}
