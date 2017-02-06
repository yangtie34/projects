package cn.gilight.research.util;

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

public class HttpUtil {
	
	//根据微信客户端传入的随机码，查询客户端登陆微信用户的openid
    public static JSONObject getWechatOpenidByCode(String APP_ID,String APP_SECRET,String code) throws IOException{
    	JSONObject result = new JSONObject();
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
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
		return result;
	}
}
