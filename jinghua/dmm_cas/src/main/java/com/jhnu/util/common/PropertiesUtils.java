package com.jhnu.util.common;

import java.io.IOException;
import java.io.InputStreamReader;
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
			p.load(new InputStreamReader(PropertiesUtils.class.getResourceAsStream(dbPath),"UTF-8"));
			return p.getProperty(name);
		} catch (IOException e) {
			logger.error("加载配置文件错误！");
		}
    	return null;
    }  
	
	public static String getProperties(String path,String name) {
    	try {
	    	Properties p=new Properties();
	    	p.load(new InputStreamReader(PropertiesUtils.class.getResourceAsStream(path),"UTF-8"));
			return p.getProperty(name);
		} catch (IOException e) {
			logger.error("加载配置文件错误！");
		}
    	return null;
    } 
	
	public static Properties getProperties(String path) {
    	try {
	    	Properties p=new Properties();
	    	p.load(new InputStreamReader(PropertiesUtils.class.getResourceAsStream(path),"UTF-8"));
			return p;
		} catch (IOException e) {
			logger.error("加载配置文件错误！");
		}
    	return null;
    }
	
}
