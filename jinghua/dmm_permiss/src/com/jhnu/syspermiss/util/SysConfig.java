package com.jhnu.syspermiss.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**   
* @Description: TODO
* @author Sunwg  
* @date 2016年3月9日 下午2:05:02   
*/
public class SysConfig {
	
	private static Logger logger = Logger.getLogger(SysConfig.class);
	
	private static final String configFile = "/permiss/sysConfig.properties";

	/** 
	* @Fields serverUrl : 本项目外网访问地址（注意是域名）
	*/ 
	private String serverUrl;
	
	private String serverUrlNodes;
	
	private String serverName;
	
	private String casServerUrl;

	private String casServerLoginUrl;
	
	private String casIgnoreUrls;
	
	private String dmmUrl;
	private String noPermissUrl;
	private String logoutUrl;
	
	private String permissIgnoreUrls;
	
	private Map<String,String> dataMap=new HashMap<String, String>();

	private static SysConfig config = new SysConfig();
	
	private SysConfig(){
		Properties p = new Properties();
		InputStream inStream = this.getClass().getResourceAsStream(configFile);
		if(inStream == null){
			logger.error("根目录下找不到sysConfig.properties文件");
			return;
		}
		try {
			p.load(new InputStreamReader(inStream, "UTF-8"));
			
			/* 重点在这里，读取properties文件进行赋值 */
			this.serverName = p.getProperty("sys.serverName").trim();
			this.serverUrl = p.getProperty("sys.serverUrl").trim();
			this.serverUrlNodes = p.getProperty("sys.serverUrlNodes").trim();
			this.casServerUrl = p.getProperty("sys.casServerUrl").trim();
			this.casServerLoginUrl = p.getProperty("sys.casServerLoginUrl").trim();
			this.casIgnoreUrls = p.getProperty("sys.casIgnoreUrls").trim().toLowerCase();
			this.permissIgnoreUrls = p.getProperty("sys.permissIgnoreUrls").trim().toLowerCase();
			this.noPermissUrl = p.getProperty("sys.noPermissUrl").trim();
			this.dmmUrl = p.getProperty("sys.dmmUrl").trim();
			this.logoutUrl=this.casServerUrl+"/logout?service="+this.serverUrl;
			 	this.dataMap.put("driverName",p.getProperty("permiss.driverName").trim());   
		        this.dataMap.put("url",p.getProperty("permiss.url").trim());  
		        this.dataMap.put("user",p.getProperty("permiss.user").trim());  
		        this.dataMap.put("password",p.getProperty("permiss.password").trim());  
		        this.dataMap.put("validationQuery",p.getProperty("permiss.validationQuery").trim());
		        this.dataMap.put("minPool",p.getProperty("permiss.minPool").trim());
		        this.dataMap.put("upPool",p.getProperty("permiss.upPool").trim());
		        this.dataMap.put("maxPool",p.getProperty("permiss.maxPool").trim());
		        this.dataMap.put("seqGenerator",p.getProperty("permiss.seqGenerator").trim());  
			inStream.close();
		} catch (IOException e) {
			logger.error("load sysConfig.properties error,class根目录下找不到sysConfig.properties文件");
			e.printStackTrace();
		}
		logger.info("load sysConfig.properties success");
	}
	
	/** 
	* @Description: 返回实例 
	* @return SysConfig
	*/
	public static SysConfig instance(){
		return config;
	}
	
	public String getServerUrl() {
		return serverUrl;
	}
	
	public String getServerUrlNodes() {
		return serverUrlNodes;
	}

	public String getCasServerUrl() {
		return casServerUrl;
	}

	public String getCasServerLoginUrl() {
		return casServerLoginUrl;
	}
	
	public String getServerName() {
		return serverName;
	}
	
	public String getCasIgnoreUrls() {
		return casIgnoreUrls;
	}
	
	public String getDmmUrl() {
		return dmmUrl;
	}

	public String getNoPermissUrl() {
		return noPermissUrl;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public String getPermissIgnoreUrls() {
		return  permissIgnoreUrls;
	}
	
}