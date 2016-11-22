package com.jhnu.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

public class SystemHttpUtil {
    public static Object rpcPost(String url,String beanName,String methodName,List<Object> list){
    	Object result = new Object();
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	try {
	        try {
	        	//TODO 此处需要通过sysType系统类型和相关配置文件进行url的设置
	            HttpPost httpPost = new HttpPost(url);
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            nvps.add(new BasicNameValuePair("beanName", beanName ));
	            nvps.add(new BasicNameValuePair("methodName",  methodName ));
	            nvps.add(new BasicNameValuePair("data",  JSONObject.toJSONString(list) ));
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);
	            try {
	                HttpEntity entity2 = response2.getEntity();
	                BufferedReader in = new BufferedReader(new InputStreamReader(entity2.getContent()));
	                String line = in.readLine();
	                if ( line != null) {
	                	result = JSONObject.parse(line);
	                }
	            }finally {
	                response2.close();
	            }
	        }  finally {
	        	httpclient.close();
        	}
    	} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
