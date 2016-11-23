package com.jhnu.util.common;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	/**
	 * 获取当前登陆者名称
	 * @param sysurl
	 * @return
	 */
    public static String getNowUser(String sysurl){
    	String user = null;
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	try {
	        try {
	            HttpGet httpGet = new HttpGet(sysurl);
	            CloseableHttpResponse response2 = httpclient.execute(httpGet);
	            try {
	            	   HttpEntity entity2 = response2.getEntity();
		                String line =EntityUtils.toString(
		                		entity2, "UTF-8");
		                user=XmlUtils.getTextForElement(line,"user");
		                return user;
	            }finally {
	                response2.close();
	            }
	        }  finally {
	        	httpclient.close();
        	}
    	} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
    
}
