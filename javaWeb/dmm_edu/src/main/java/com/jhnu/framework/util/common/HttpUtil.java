package com.jhnu.framework.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;


public class HttpUtil {
	
	public static final String WechatPath = "/wechat/wechat.properties";
	
	//根据微信客户端传入的随机码，查询客户端登陆微信用户的openid
    public static JSONObject getWechatOpenidByCode(String code){
    	JSONObject result = new JSONObject();
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	try {
	        try {
	        	Properties p=PropertiesUtils.getProperties(WechatPath);
	        	String APP_ID="",APP_SECRET="";
	        	if(p!=null){
	        		APP_ID=p.getProperty("APP_ID");
	        		APP_SECRET=p.getProperty("APP_SECRET");
	        	}
	        	//TODO 此处连接需要通过配置文件进入
	            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/sns/oauth2/access_token");
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            nvps.add(new BasicNameValuePair("appid", APP_ID ));
	            nvps.add(new BasicNameValuePair("secret",  APP_SECRET));
	            nvps.add(new BasicNameValuePair("code",  code));
	            nvps.add(new BasicNameValuePair("grant_type",  "authorization_code"));
	            
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);
	
	            try {
	                HttpEntity entity2 = response2.getEntity();
	                BufferedReader in = new BufferedReader(new InputStreamReader(entity2.getContent()));
	                String line = in.readLine();
	                if ( line != null) {
	                	result = JSONObject.parseObject(line);
	                }
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
	    } catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
    
    public static Object rpcPost(String sysType,String beanName,String methodName,List<Object> list){
    	Object result = new Object();
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	try {
	        try {
	        	//TODO 此处需要通过sysType系统类型和相关配置文件进行url的设置
	            HttpPost httpPost = new HttpPost("http://localhost:8088/dmm/common/getDatas");
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
    
public static void main(String[] args) {
	  
		  List<Object>list=new ArrayList<Object>();
		  list.add("test");
		  // 使用方式
		  Object sss=rpcPost("xgxt","userService","testString",null);
		  // 带参数的
		  Object aaa=rpcPost("xgxt","userService","testString",list); 
		  System.out.println(sss);
		  System.out.println(aaa);
	
  }

}
