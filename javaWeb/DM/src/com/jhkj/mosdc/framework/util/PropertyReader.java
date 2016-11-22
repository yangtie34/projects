package com.jhkj.mosdc.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工具类PropertyReader,取得项目配制信息,当然也可以通过JAVA里字符串直接连接数据库,
 * 但同一应用所有连接数据源的连接信息应该都取自同一配制文件以保证数据源一致性,利于项目维护
 * @author BirdYfaN
 * @version 1.0 2008-10-29 16:30
 */
public class PropertyReader {
	public static String getProperty(String key,String fileName){
		Properties props=new Properties();
		String value=null;
		InputStream is=null;
		
		try {
			is=PropertyReader.class.getClassLoader().getResourceAsStream(fileName);
			props.load(is);
			value=props.getProperty(key);
			return value;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("File with the name: '"+fileName+
					"' does not exist or an error occured while reading property file!");
		}
		return null;
	}
}
