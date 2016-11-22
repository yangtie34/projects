package com.jhnu.framework.util.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * properties 工具类
 *
 */
public class PropertiesUtils {

    public static final String dbPath = "/db.properties";
    
    private static final Logger logger = Logger.getLogger(PropertiesUtils.class);

    
	public static String getDBPropertiesByName(String name) {
    	try {
	    	Properties p=new Properties();
			p.load(PropertiesUtils.class.getResourceAsStream(dbPath));
			return p.getProperty(name);
		} catch (IOException e) {
			logger.error("加载配置文件错误！");
		}
    	return null;
    }  
	
	public static String getProperties(String path,String name) {
    	try {
	    	Properties p=new Properties();
			p.load(PropertiesUtils.class.getResourceAsStream(path));
			return p.getProperty(name);
		} catch (IOException e) {
			logger.error("加载配置文件错误！");
		}
    	return null;
    } 
	
	public static Properties getProperties(String path) {
    	try {
	    	Properties p=new Properties();
			p.load(PropertiesUtils.class.getResourceAsStream(path));
			return p;
		} catch (IOException e) {
			logger.error("加载配置文件错误！");
		}
    	return null;
    }
	
}
