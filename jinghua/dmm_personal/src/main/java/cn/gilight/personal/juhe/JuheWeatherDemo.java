package cn.gilight.personal.juhe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.framework.uitl.common.MapUtils;

/**
*查询天气调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/112
**/
public class JuheWeatherDemo {
	public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
  
    //配置您申请的KEY
    public static final String APPKEY ="f23402a12cfe7637f66a6554665b46ff";
  
    //1.根据城市查询天气
    public static String getRequest1(String cityName){
    	String w = "";
        String result =null;
        String url ="http://op.juhe.cn/onebox/weather/query";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("cityname",cityName);//要查询的城市，如：温州、上海、北京
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("dtype","");//返回数据的格式,xml或json，默认json
  
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getIntValue("error_code")==0){
            	Map<String,Object> res = (Map<String, Object>) object.get("result");
            	Map<String,Object> data = MapUtils.getMap(res, "data");
            	Map<String,Object> realtime = MapUtils.getMap(data, "realtime");
            	Map<String,Object> weather = MapUtils.getMap(realtime, "weather");
            	Map<String,Object> wind = MapUtils.getMap(realtime, "wind");
            	w = "当前实况天气：温度"+MapUtils.getString(weather, "temperature")+"℃;湿度"+MapUtils.getString(weather, "humidity")+";"
            			+ MapUtils.getString(wind, "direct")+";"+MapUtils.getString(wind, "power")+"。\r\n未来几天天气：\r\n";
            	List<Map<String,Object>> weathers = (List<Map<String, Object>>) MapUtils.getObject(data, "weather");
            	for(Map<String,Object> m : weathers){
            		List<String> d = (List<String>) MapUtils.getObject(MapUtils.getMap(m, "info"), "day");
            		List<String> n = (List<String>) MapUtils.getObject(MapUtils.getMap(m, "info"), "night");
            		w = w + MapUtils.getString(m, "date") + " 白天天气:"+d.get(1)+","+d.get(2)+"℃,"+d.get(3)+","+d.get(3)+";"
            				+ " 夜间天气:"+ n.get(1)+","+n.get(2)+"℃,"+n.get(3)+","+n.get(4)+"\r\n";
            	}
            	System.out.println(w);
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
                w = object.get("error_code")+":"+object.get("reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }
    
    public static void main(String[] args) {
    	getRequest1("郑州");
    }
     
     //2.数据类型
     public static void getRequest2(){
         String result =null;
         String url ="http://op.juhe.cn/robot/code";//请求接口地址
         Map params = new HashMap();//请求参数
             params.put("dtype","");//返回的数据格式，json或xml，默认json
             params.put("key",APPKEY);//您申请本接口的APPKEY，请在应用详细页查询
  
         try {
             result =net(url, params, "GET");
             JSONObject object = JSONObject.parseObject(result);
             if(object.getIntValue("error_code")==0){
                 System.out.println(object.get("result"));
             }else{
                 System.out.println(object.get("error_code")+":"+object.get("reason"));
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     
     /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
     
     
}
