package com.jhnu.syspermiss.util.jdbcUtil;

import java.sql.Connection;
import java.util.Map;

import com.jhnu.syspermiss.util.SysConfig;

public class GetConn {
    private static Map<String,String> oMap=SysConfig.instance().getDataMap(); 
    private GetConn(){}
    static {   
    	init();
    	SimpleConnetionPool.initDriver();  
    }
    public static void init(){
    String driverName=oMap.get("driverName");  
    SimpleConnetionPool.setM_driver(driverName);
    String url=oMap.get("url");   //test为数据库名称，1521为连接数据库的默认端口  
    SimpleConnetionPool.setUrl(url);
    String user=oMap.get("user");    //aa为用户名  
    SimpleConnetionPool.setUser(user);
    String password=oMap.get("password");    //123为密码  
    SimpleConnetionPool.setPassword(password);
    
    }
	public static Connection getConn() {
		return SimpleConnetionPool.getConnection();
	}
	public static String getSeq() {
		return oMap.get("seqGenerator");
	}
}
