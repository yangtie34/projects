package com.jhkj.mosdc.framework.util;
import java.util.Properties; 
import java.io.InputStream; 
import java.io.IOException; 

/** 
* 读取Properties文件的例子 
* File: PropertiesUtils.java 
* User: gaodongjie 
* Date: 2008-2-15 18:38:40 
*/ 
public final class PropertiesUtils { 
    public static Properties prop;
    static { 
        prop = new Properties(); 
        try { 
        	ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
        	InputStream in = classLoader.getResourceAsStream("/dataSource.properties"); 
            prop.load(in); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 

    /** 
     * 私有构造方法，不需要创建对象 
     */ 
    private PropertiesUtils() { 
    } 
    /**
     * 获取数据库类型
     * @return
     */
    public static String getDataBase() { 
        return prop.getProperty("database").trim(); 
    }
    /**
     * 获取学校名称
     * @return
     */
    public static String getXXMC(){
    	return prop.getProperty("xxmc").trim();
    }
}
