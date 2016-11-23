package com.jhkj.mosdc.framework.scheduling;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.MethodInvokingJob;
import org.springframework.util.StringUtils;

/**
 * 任务配置解析工具类实现
 * @author Administrator
 *
 */
public class TaskXmlParserImpl extends TaskXmlParser implements  InitializingBean,BeanFactoryAware  {
	private List<JobDetail> jobDetailList = new ArrayList<JobDetail>();
	private List  triggerList = new ArrayList();
	private String inputFile;
	private BeanFactory beanFactory;
	
	/**
	 * beanFactory注入
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	/***
	 * 配置文件注入
	 * @param inputFile
	 */
	
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public List<JobDetail> getMethodInvokingList() {
		return jobDetailList;
	}
	
	
	public List<CronTriggerBean> getTriggerList() {
		return triggerList;
	}
	public void initMethod() {
		System.out.println("InitSequenceBean: init-method");
	}
	@SuppressWarnings("unchecked")
	public void parserTaskXml() throws Exception{
		 URL path = this.getClass().getClassLoader().getResource(inputFile.trim());
		 SAXReader saxReader = new SAXReader();
		 Document document = saxReader.read(new File(path.toURI().getPath()));
		 Element root = document.getRootElement();
		 Iterator<Element> iter = root.elementIterator();
		 
		 String propertiesPath = MethodInvokeTimeJob.class.getClassLoader().getResource("framework/jobTime.properties").toURI().getPath();
		 Properties properties = new Properties();
		 InputStream input = new FileInputStream(new File(propertiesPath));
		 properties.load(input);
		 input.close();
		 
		 
		 String taskName = null;
		 String taskService = null;
		 String startDelay = null;
		 String repeat = null;
		 String express = null;
		 String method = null;
		 
		 while(iter.hasNext()){
			 Element task = iter.next();
			 Iterator<Element> taskIter = task.elementIterator();
			 while(taskIter.hasNext()){
				 Element ce = taskIter.next();
				 if(ce.getName().equals("service")){
					 taskService = ce.getText();
				 }else if(ce.getName().equals("name")){
					 taskName = ce.getText();
				 }else if(ce.getName().equals("startDelay")){
					 startDelay = ce.getText();
				 }else if(ce.getName().equals("repeatInterval")){
					 repeat = ce.getText();
				 }else if(ce.getName().equals("cronExpression")){
					 express = ce.getText();
				 }else if(ce.getName().equals("method")){
					 method = ce.getText();
				 }
			 }
			 
			 Trigger trigger = null;
			 if(repeat != null && !"".equals(repeat)){
				 trigger = new SimpleTriggerBean();
				 ((SimpleTrigger) trigger).setRepeatInterval(Long.parseLong(repeat));
				 trigger.setStartTime(new Date(System.currentTimeMillis() + Long.parseLong(startDelay)));
			 }else{
				 if(express != null && !"".equals(express)){
					 trigger = new CronTriggerBean();
					 ((CronTrigger) trigger).setCronExpression(express);
				 }
			 }
			 
			 String name = taskService+"_"+method;
			 trigger.setName(name+"_trigger");
			 
			 MethodInvokingJobDetailFactoryTimeBean mb = new MethodInvokingJobDetailFactoryTimeBean();
			 mb.setTargetBeanName(taskService);
			 mb.setBeanName(taskService);
			 mb.setTargetMethod(method);
			 mb.setBeanFactory(beanFactory);
			 mb.afterPropertiesSet();
			 
			 Object beginTime = properties.get(taskName+"_begin");
			 Object endTime = properties.get(taskName+"_end");
			 
			 JobDetail jobDetail = mb.getJobDetail();
			 jobDetail.setName(name);
			 jobDetail.getJobDataMap().put("taskName", taskName);
			 jobDetail.getJobDataMap().put("beginTime", beginTime == null?"":beginTime);
			 jobDetail.getJobDataMap().put("endTime", endTime == null?"":endTime);
			 jobDetailList.add(jobDetail);
			 triggerList.add(trigger);
		 }
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		parserTaskXml();//解析XML
	}
}
