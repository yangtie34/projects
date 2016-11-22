package com.jhkj.mosdc.framework.scheduling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.JobMethodInvocationFailedException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.MethodInvokingJob;
import org.springframework.util.MethodInvoker;

import com.jhkj.mosdc.framework.util.DateUtils;

public class MethodInvokeTimeJob extends QuartzJobBean {
	public String lastEndTime;//上次任务执行结束时间
	public String taskName;//任务的名字
	protected static final Log logger = LogFactory.getLog(MethodInvokingJob.class);

	private MethodInvoker methodInvoker;

	/**
	 * Set the MethodInvoker to use.
	 */
	public void setMethodInvoker(MethodInvoker methodInvoker) {
		this.methodInvoker = methodInvoker;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getLastEndTime() {
		return lastEndTime;
	}

	public void setLastEndTime(String lastEndTime) {
		this.lastEndTime = lastEndTime;
	}
	/**
	 * 标识当前任务已经停止
	 */
	public void taskIsStop(){
		String path = MethodInvokeTimeJob.class.getClassLoader().getResource("framework/jobTime.properties").getPath();
		Properties properties = new Properties();
		try {
			InputStream input = new FileInputStream(new File(path));
			properties.load(input);
			input.close();
			properties.setProperty(taskName+"_end", DateUtils.date2StringV2(new Date()));
			OutputStream output = new FileOutputStream(new File(path));
			properties.store(output, "");
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 标识任务开始时间
	 * @throws Exception 
	 */
	public void taskIsBegin() throws Exception{
		String path = MethodInvokeTimeJob.class.getClassLoader().getResource("framework/jobTime.properties").toURI().getPath();
		Properties properties = new Properties();
		try {
			InputStream input = new FileInputStream(new File(path));
			properties.load(input);
			input.close();
			properties.setProperty(taskName+"_begin", DateUtils.date2StringV2(new Date()));
			OutputStream output = new FileOutputStream(new File(path));
			properties.store(output, "");
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Invoke the method via the MethodInvoker.
	 */
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			this.setProperties();
			this.taskIsBegin();
			this.methodInvoker.invoke();
			this.taskIsStop();
		}
		catch (InvocationTargetException ex) {
			if (ex.getTargetException() instanceof JobExecutionException) {
				// -> JobExecutionException, to be logged at info level by Quartz
				throw (JobExecutionException) ex.getTargetException();
			}
			else {
				// -> "unhandled exception", to be logged at error level by Quartz
				throw new JobMethodInvocationFailedException(this.methodInvoker, ex.getTargetException());
			}
		}
		catch (Exception ex) {
			// -> "unhandled exception", to be logged at error level by Quartz
			throw new JobMethodInvocationFailedException(this.methodInvoker, ex);
		}
	}
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public void setProperties() throws Exception{
		MethodInvokingJobDetailFactoryTimeBean timeJob = (MethodInvokingJobDetailFactoryTimeBean) this.methodInvoker;
		
		String path = MethodInvokeTimeJob.class.getClassLoader().getResource("framework/jobTime.properties").toURI().getPath();
		Properties properties = new Properties();
		InputStream input = new FileInputStream(new File(path));
		properties.load(input);
		input.close();
		
		JobDetail jobDetail = timeJob.getJobDetail();
		Map map = jobDetail.getJobDataMap();
		String taskName = map.get("taskName").toString();
		
		String endTime = properties.getProperty(taskName+"_end");
		if(endTime == null)endTime = "";
		map.put("endTime", endTime);
	}
}
