package com.jhkj.mosdc.framework.bean;

import java.text.ParseException;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jhkj.mosdc.framework.job.CacheEvictJob;
import com.jhkj.mosdc.framework.job.XnxqSwitchJob;
import com.jhkj.mosdc.framework.util.SysConstants;

public class InitServlet extends HttpServlet {
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * Spring Bean 上下文对象
	 */
	private static WebApplicationContext springContext = null;

	/**
	 * Schedualr 上下文对象
	 */
	private static Scheduler sched = null;

	/**
	 * application 域
	 */
	private static ServletContext application = null;

	/**
	 * 从文件中读取参数表，保存在application里
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = this.getServletContext();
		log.debug("start to intitail ");
		// 初始化实体类对应关系
		entityInit(context);
		// 任务调度方法
		taskInit(config);
		// 获取WebApplicationContext
		ServletContext application = getServletContext();
		String path = getServletContext().getRealPath("/");
		try {
			Map servicesMap = new HashMap();
			xmlParse(getWebRootPath().replace("%20", " ") // 解析服务xml
					+ "/WEB-INF/classes/service.xml", servicesMap);
			application
					.setAttribute(SysConstants.REQUEST_SERVICES, servicesMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void taskInit(ServletConfig config) throws ServletException {
		ServletContext sc = config.getServletContext();
		SchedulerFactory sf = new StdSchedulerFactory();
		InitServlet.setApplication(sc);
		try {
			InitServlet.setSched(sf.getScheduler());
			sched.start();
			InitServlet.setSpringContext(WebApplicationContextUtils
					.getWebApplicationContext(sc));
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// System.out.println("开始创建任务...");
		/**
		 * 创建Job
		 */
		// 缓存清理任务调度，每隔五分钟，执行一次
		JobDetail cacheJobDetail = new JobDetail("cacheEvictJob",
				CacheEvictJob.class);
		SimpleTrigger cacheEvictTriger = new SimpleTrigger("cacheEvictTriger",
				Scheduler.DEFAULT_GROUP, new Date(), null,
				SimpleTrigger.REPEAT_INDEFINITELY, 5*60*1000L);
		
		//自动切换学年学期的job
//		JobDetail xnxqSwitchDetail = new JobDetail("xnxqSwitchJob",XnxqSwitchJob.class);
		
		// new SimpleTrigger("cacheEvictTriger", Scheduler.DEFAULT_GROUP, 0,
		// 5*60*1000L);
		// JobDetail jobDetail = new
		// JobDetail("uploadJob",Scheduler.DEFAULT_GROUP, null);
		// JobDetail jobDetail2 = new JobDetail("InsertFreezeJob",
		// Scheduler.DEFAULT_GROUP, null);
		// String cronStr = "0 02-59 11 * * ?"; //每天的１０点０２分至５９分每隔一分钟执行一次
		// // String cronStr ="0 01 01 01 * ?"; //每个月的１号的１点１分开始执行
		// CronTrigger trigger =null;
		// CronTrigger trigger2 =null;
		// // System.out.println("创建定时器...");
		// try {
		// trigger = new CronTrigger("updateDataJob",
		// Scheduler.DEFAULT_GROUP,cronStr);
		// trigger2 = new CronTrigger("InsertDataJob", Scheduler.DEFAULT_GROUP);
		// trigger2.setCronExpression(cronStr);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// return;
		// }
		/**
		 * 加入调度器
		 */
		System.out.println("将任务和定时器加入到调度器...");
		try {
			sched.scheduleJob(cacheJobDetail, cacheEvictTriger);
			// sched.scheduleJob(jobDetail, trigger);
			// sched.scheduleJob(jobDetail2, trigger2);
		} catch (SchedulerException e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void entityInit(ServletContext context) {
		log.debug("start to intitail ");
		// 获取WebApplicationContext
		String path = getServletContext().getRealPath("/");

		try {
			Map entityMap = new HashMap();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(new File(path
					+ "/WEB-INF/classes/entity.xml"));
			NodeList nl = doc
					.getElementsByTagName(SysConstants.REQUEST_REQUESTS);
			for (int i = 0; i < nl.getLength(); i++) {
				Map service = new HashMap();
				Element node = (Element) nl.item(i);
				String requestName = node
						.getElementsByTagName(SysConstants.REQUEST_NAME)
						.item(0).getFirstChild().getNodeValue();
				service.put(SysConstants.REQUEST_NAME, requestName);
				service.put(SysConstants.request_package, node
						.getElementsByTagName(SysConstants.request_package)
						.item(0).getFirstChild().getNodeValue());
				service.put(SysConstants.request_entityname, node
						.getElementsByTagName(SysConstants.request_entityname)
						.item(0).getFirstChild().getNodeValue());
				if (null != entityMap.get(requestName)) {
					throw new Exception("entity.xml文件有重复记录,重复名称为："
							+ requestName);
				}
				entityMap.put(
						node.getElementsByTagName(SysConstants.REQUEST_NAME)
								.item(0).getFirstChild().getNodeValue(),
						service);
			}
			context.setAttribute(SysConstants.request_entitys, entityMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析服务xml
	 * 
	 * @param filename
	 *            文件名，包括完整的路径
	 * @param map
	 *            解析后的数据存放对象
	 */
	public static void xmlParse(String filename, Map map) {
		org.dom4j.io.SAXReader saxReader = new org.dom4j.io.SAXReader(); // 初始化读io流对象
		try {
			org.dom4j.Document document = saxReader.read(new File(filename)); // 读文件，
			org.dom4j.Element root = document.getRootElement(); // 获取根节点
			// 遍历根结点的所有孩子节点
			for (Iterator iter = root.elementIterator(); iter.hasNext();) {
				org.dom4j.Element element = (org.dom4j.Element) iter.next();
				if (element == null)
					continue;
				// 如果是引入的子xml文件，则解析此文件，递归方式
				if ("import".equalsIgnoreCase(element.getName())) {
					String resource = element.attributeValue("resource");
					xmlParse(getWebRootPath().replace("%20", " ")
							+ "/WEB-INF/classes/" + resource, map);
				} else if ("request".equalsIgnoreCase(element.getName())) { // 如果是配置的request服务节点，则解析该节点下的所有子节点
					Map service = new HashMap();
					for (Iterator iterInner = element.elementIterator(); iterInner
							.hasNext();) {
						org.dom4j.Element elementInner = (org.dom4j.Element) iterInner
								.next();
						if ("name".equalsIgnoreCase(elementInner.getName())) { // 以name节点的文本内容为key存放本层数据
							map.put(elementInner.getTextTrim(), service);
						}
						service.put(elementInner.getName(), // 以节点标签名为key，节点文本为值
								elementInner.getTextTrim());
					}

				} else {
					/*
					 * 其他情况的解析，一般用不到
					 */
					Map service = new HashMap();
					map.put(element.getName(), service);
					for (Iterator iterInner = element.elementIterator(); iterInner
							.hasNext();) {
						org.dom4j.Element elementInner = (org.dom4j.Element) iterInner
								.next();
						service.put(elementInner.getName(),
								elementInner.getTextTrim());
					}
				}
			}
		} catch (org.dom4j.DocumentException e) {
			e.printStackTrace();
		}
	}

	private static String getWebRootPath() {
		String LOCAL_CLASS_NAME_SHORT = InitServlet.class.getSimpleName()
				+ ".class";
		String LOCAL_CLASS_NAME_LONG = InitServlet.class.getName().replaceAll(
				"\\.", "/")
				+ ".class";
		String WEB_ROOT_PATH = InitServlet.class
				.getResource(LOCAL_CLASS_NAME_SHORT).getPath()
				.replaceFirst("/WEB-INF/classes/" + LOCAL_CLASS_NAME_LONG, "/");
		return WEB_ROOT_PATH;
	}

	/**
	 * @return the springContext
	 */
	public static WebApplicationContext getSpringContext() {
		return springContext;
	}

	/**
	 * @param springContext
	 *            the springContext to set
	 */
	public static void setSpringContext(WebApplicationContext springContext) {
		InitServlet.springContext = springContext;
	}

	/**
	 * @return the sched
	 */
	public static Scheduler getSched() {
		return sched;
	}

	/**
	 * @param sched
	 *            the sched to set
	 */
	public static void setSched(Scheduler sched) {
		InitServlet.sched = sched;
	}

	/**
	 * @return the application
	 */
	public static ServletContext getApplication() {
		return application;
	}

	public static void setApplication(ServletContext application) {
		InitServlet.application = application;
	}

}
