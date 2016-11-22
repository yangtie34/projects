package com.jhnu.syspermiss.util;

import java.io.IOException;
import java.util.Properties;


/**
 * properties 工具类
 *
 */
public class PropertiesUtils {
	
	private static String  base="/permiss/sysConfig.properties";
	
	public static String getBasePropertiesByName(String name) {
    	try {
	    	Properties p=new Properties();
			p.load(PropertiesUtils.class.getResourceAsStream(base));
			return p.getProperty(name);
		} catch (IOException e) {
		}
    	return null;
    } 

	public static String getProperties(String path,String name) {
    	try {
	    	Properties p=new Properties();
			p.load(PropertiesUtils.class.getResourceAsStream(path));
			return p.getProperty(name);
		} catch (IOException e) {
		}
    	return null;
    } 
	
	public static Properties getProperties(String path) {
    	try {
	    	Properties p=new Properties();
			p.load(PropertiesUtils.class.getResourceAsStream(path));
			return p;
		} catch (IOException e) {
		e.printStackTrace();
		}
    	return null;
    }
	
}
