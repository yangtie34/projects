package cn.gilight.qyh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**   
* @author Sunwg  
* @date 2016年3月9日 下午2:05:02   
*/
public class QyhConfig {
	
	private static Logger logger = Logger.getLogger(QyhConfig.class);
	
	private static final String configFile = "/qyh.properties";
	
	public static String getProperty(String name){
		Properties p = new Properties();
		InputStream inStream = QyhConfig.class.getResourceAsStream(configFile);
		BufferedReader bf = new BufferedReader(new InputStreamReader(inStream));  
		if(inStream == null){
			logger.error("根目录下找不到sysConfig.properties文件");
			return null;
		}
		String result = "";
		try {
			p.load(bf);
			result = p.getProperty(name).trim();
			inStream.close();
		} catch (IOException e) {
			logger.error("load sysConfig.properties error,class根目录下找不到sysConfig.properties文件");
			e.printStackTrace();
		}
		return result;
	};
	
	public static void main(String[] args) {
		System.out.println(QyhConfig.getProperty("qyh.CorpID"));
	}
}