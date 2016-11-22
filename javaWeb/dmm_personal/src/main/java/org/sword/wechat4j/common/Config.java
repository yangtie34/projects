/**
 * 
 */
package org.sword.wechat4j.common;

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
public class Config {
	
	private static Logger logger = Logger.getLogger(Config.class);
	
	private static final String configFile = "/wechat4j.properties";
	
	private String url;
	private String token;
	private String encodingAESKey;
	private String appid;
	private String appSecret;
	private String accessTokenServer;
	private String jsApiTicketServer;
	private String subscribeMessage;

	private static Config config = new Config();
	
	private Config(){
		Properties p = new Properties();
		InputStream inStream = this.getClass().getResourceAsStream(configFile);
		if(inStream == null){
			logger.error("根目录下找不到sysConfig.properties文件");
			return;
		}
		try {
			p.load(new InputStreamReader(inStream, "UTF-8"));
			this.url = p.getProperty("wechat.url").trim();
			this.encodingAESKey = p.getProperty("wechat.encodingaeskey").trim();
			this.token = p.getProperty("wechat.token").trim();
			this.appid = p.getProperty("wechat.appid").trim();
			this.appSecret = p.getProperty("wechat.appsecret").trim();
			this.accessTokenServer = p.getProperty("wechat.accessToken.server.class").trim();
			this.jsApiTicketServer = p.getProperty("wechat.ticket.jsapi.server.class").trim();
			this.subscribeMessage = p.getProperty("wechat.subscribeMessage").trim();
			inStream.close();
		} catch (IOException e) {
			logger.error("load wechat4j.properties error,class根目录下找不到wechat4j.properties文件");
			e.printStackTrace();
		}
		logger.info("load wechat4j.properties success");
	}
	
	public static Config instance(){
		return config;
	}
	public String getToken() {
		return token;
	}
	public String getAppid() {
		return appid;
	}
	public String getAppSecret() {
		return appSecret;
	}

	public String getUrl() {
		return url;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}
	
	public String getAccessTokenServer(){
		return accessTokenServer;
	}

	public String getJsApiTicketServer() {
		return jsApiTicketServer;
	}

	public String getSubscribeMessage() {
		return subscribeMessage;
	}
}