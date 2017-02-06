package cn.gilight.framework.uitl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**   
* @Description: TODO
* @author Sunwg  
* @date 2016年3月9日 下午2:05:02   
*/
public class SysConfig {
	
	private static Logger logger = Logger.getLogger(SysConfig.class);
	
	private static final String configFile = "/sysConfig.properties";

	/** 
	* @Fields serverUrl : 本项目外网访问地址（注意是域名）
	*/ 
	private String serverUrl;
	
	private String serverName;
	
	private String casServerUrl;

	private String casServerLoginUrl;
	
	private String casIgnoreUrls;
	
	private String dmmUrl;

	private static SysConfig config = new SysConfig();
	
	private SysConfig(){
		Properties p = new Properties();
		InputStream inStream = this.getClass().getResourceAsStream(configFile);
		BufferedReader bf = new BufferedReader(new InputStreamReader(inStream));  
		if(inStream == null){
			logger.error("根目录下找不到sysConfig.properties文件");
			return;
		}
		try {
			p.load(bf);
			
			/* 重点在这里，读取properties文件进行赋值 */
			this.serverName = p.getProperty("sys.serverName").trim();
			this.serverUrl = p.getProperty("sys.serverUrl").trim();
			this.casServerUrl = p.getProperty("sys.casServerUrl").trim();
			this.casServerLoginUrl = p.getProperty("sys.casServerLoginUrl").trim();
			this.casIgnoreUrls = p.getProperty("sys.casIgnoreUrls").trim();
			this.dmmUrl = p.getProperty("sys.dmmUrl").trim();
			
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
}