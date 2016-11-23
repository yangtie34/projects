package com.jhnu.util.common;

import java.io.IOException;
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
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


public class HttpUtil {
	
    public static Object rpcJobPost(String sysurl,String beanName,String methodName,String planLogDetailId){
    	Object result = null;
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	try {
	        try {
	            HttpPost httpPost = new HttpPost(sysurl+"/task/job/execute");
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            nvps.add(new BasicNameValuePair("beanName", beanName ));
	            nvps.add(new BasicNameValuePair("methodName",  methodName ));
	            nvps.add(new BasicNameValuePair("planLogDetailId",  planLogDetailId ));
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);
	            try {
	            	   HttpEntity entity2 = response2.getEntity();
		                String line =EntityUtils.toString(
		                		entity2, "UTF-8");
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
