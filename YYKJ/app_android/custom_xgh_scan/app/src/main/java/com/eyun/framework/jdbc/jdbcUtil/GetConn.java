package com.eyun.framework.jdbc.jdbcUtil;

import java.sql.Connection;

import com.eyun.configure.ServerConfig;

public class GetConn {

    private GetConn() {
    }

    static {
        init();
        SimpleConnetionPool.initDriver();
    }

    public static void init() {
        String driverName = ServerConfig.JDBC.driverName;
        SimpleConnetionPool.setM_driver(driverName);
        String url =  ServerConfig.JDBC.url;   //test为数据库名称，1521为连接oracle数据库的默认端口
        SimpleConnetionPool.setUrl(url);
        String user =  ServerConfig.JDBC.user;    //aa为用户名
        SimpleConnetionPool.setUser(user);
        String password =  ServerConfig.JDBC.password;    //123为密码
        SimpleConnetionPool.setPassword(password);

    }

    public static Connection getConn() {
        return SimpleConnetionPool.getConnection();
    }

}
