package com.jhkj.mosdc.framework.scheduling;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerBean;

/**
 * xml解析工具类抽象类
 * @author Administrator
 *
 */
public abstract class TaskXmlParser {
	List<JobDetail> jobDetailList = new ArrayList<JobDetail>();
	List<CronTriggerBean> triggerList = new ArrayList<CronTriggerBean>();
	public  String inputFile;
	
	public abstract void parserTaskXml()  throws Exception;
	
	public abstract List<JobDetail> getMethodInvokingList();
	
	public abstract List<CronTriggerBean> getTriggerList();
}
