package com.glite.flink.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertieUtils {
	public static Properties loadProperties(String path){
		Properties p = new Properties();
		try {
			p.load(PropertieUtils.class
					.getResourceAsStream(path));
			return p;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
