package cn.gilight.research.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @Description: 项目配置文件
 * @author Sunwg
 * @date 2016年6月14日 下午3:45:36
 */
public class Config {

	private static Logger log = Logger.getLogger(Config.class);

	private static final String configFile = "/config.properties";

	private String famousTypes;
	private String schoolName;

	private Config() {
		Properties p = new Properties();
		InputStream inStream = this.getClass().getResourceAsStream(configFile);
		if (inStream == null) {
			log.error("根目录下找不到config.properties文件");
			return;
		}
		try {
			p.load(new InputStreamReader(inStream, "UTF-8"));
			/* 重点在这里，读取properties文件进行赋值 */
			this.famousTypes = p.getProperty("thesis.famous.types").trim();
			this.schoolName = p.getProperty("sys.schoolName").trim();
			inStream.close();
		} catch (IOException e) {
			log.error("load sysConfig.properties error,class根目录下找不到config.properties文件");
			e.printStackTrace();
		}
	}
 
	/**
	 * @Description: 返回实例
	 * @return Config
	 */
	public static Config instance() {
		return new Config();
	}

	public String getFamousTypes() {
		return famousTypes;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
}