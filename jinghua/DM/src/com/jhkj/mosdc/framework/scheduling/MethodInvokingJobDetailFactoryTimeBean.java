package com.jhkj.mosdc.framework.scheduling;

import java.lang.reflect.Method;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.StatefulJob;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.MethodInvokingJob;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class MethodInvokingJobDetailFactoryTimeBean  extends ArgumentConvertingMethodInvoker
implements FactoryBean, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean {
	private String name;

	private String group = Scheduler.DEFAULT_GROUP;

	private boolean concurrent = true;

	private String targetBeanName;

	private String[] jobListenerNames;

	private String beanName;

	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	private BeanFactory beanFactory;

	private JobDetail jobDetail;


	/**
	 * Set the name of the job.
	 * <p>Default is the bean name of this FactoryBean.
	 * @see org.quartz.JobDetail#setName
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the group of the job.
	 * <p>Default is the default group of the Scheduler.
	 * @see org.quartz.JobDetail#setGroup
	 * @see org.quartz.Scheduler#DEFAULT_GROUP
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	
	/**
	 * Specify whether or not multiple jobs should be run in a concurrent
	 * fashion. The behavior when one does not want concurrent jobs to be
	 * executed is realized through adding the {@link StatefulJob} interface.
	 * More information on stateful versus stateless jobs can be found
	 * <a href="http://www.opensymphony.com/quartz/tutorial.html#jobsMore">here</a>.
	 * <p>The default setting is to run jobs concurrently.
	 */
	public void setConcurrent(boolean concurrent) {
		this.concurrent = concurrent;
	}

	/**
	 * Set the name of the target bean in the Spring BeanFactory.
	 * <p>This is an alternative to specifying {@link #setTargetObject "targetObject"},
	 * allowing for non-singleton beans to be invoked. Note that specified
	 * "targetObject" and {@link #setTargetClass "targetClass"} values will
	 * override the corresponding effect of this "targetBeanName" setting
	 * (i.e. statically pre-define the bean type or even the bean object).
	 */
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	/**
	 * Set a list of JobListener names for this job, referring to
	 * non-global JobListeners registered with the Scheduler.
	 * <p>A JobListener name always refers to the name returned
	 * by the JobListener implementation.
	 * @see SchedulerFactoryBean#setJobListeners
	 * @see org.quartz.JobListener#getName
	 */
	public void setJobListenerNames(String[] names) {
		this.jobListenerNames = names;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	protected Class resolveClassName(String className) throws ClassNotFoundException {
		return ClassUtils.forName(className, this.beanClassLoader);
	}


	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
		prepare();

		// Use specific name if given, else fall back to bean name.
		String name = (this.name != null ? this.name : this.beanName);

		// Consider the concurrent flag to choose between stateful and stateless job.
		Class jobClass = (this.concurrent ? (Class) MethodInvokeTimeJob.class : StatefulMethodInvokingJob.class);

		// Build JobDetail instance.
		this.jobDetail = new JobDetail(name, this.group, jobClass);
		this.jobDetail.getJobDataMap().put("methodInvoker", this);
		this.jobDetail.setVolatility(true);
		this.jobDetail.setDurability(true);

		// Register job listener names.
		if (this.jobListenerNames != null) {
			for (int i = 0; i < this.jobListenerNames.length; i++) {
				this.jobDetail.addJobListener(this.jobListenerNames[i]);
			}
		}

		postProcessJobDetail(this.jobDetail);
	}

	/**
	 * Callback for post-processing the JobDetail to be exposed by this FactoryBean.
	 * <p>The default implementation is empty. Can be overridden in subclasses.
	 * @param jobDetail the JobDetail prepared by this FactoryBean
	 */
	protected void postProcessJobDetail(JobDetail jobDetail) {
	}


	/**
	 * Overridden to support the {@link #setTargetBeanName "targetBeanName"} feature.
	 */
	public Class getTargetClass() {
		Class targetClass = super.getTargetClass();
		if (targetClass == null && this.targetBeanName != null) {
			Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
			targetClass = this.beanFactory.getType(this.targetBeanName);
		}
		return targetClass;
	}

	/**
	 * Overridden to support the {@link #setTargetBeanName "targetBeanName"} feature.
	 */
	public Object getTargetObject() {
		Object targetObject = super.getTargetObject();
		if (targetObject == null && this.targetBeanName != null) {
			Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
			targetObject = this.beanFactory.getBean(this.targetBeanName);
		}
		String lastEndTime = this.jobDetail.getJobDataMap().get("endTime").toString();
		if(targetObject instanceof Task){
		   ((Task) targetObject).setLastEndTime(lastEndTime);
		}
		return targetObject;
	}

	public Object getObject() {
		return this.jobDetail;
	}

	public Class getObjectType() {
		return JobDetail.class;
	}

	public boolean isSingleton() {
		return true;
	}
	public JobDetail getJobDetail(){
		return this.jobDetail;
	}
}
