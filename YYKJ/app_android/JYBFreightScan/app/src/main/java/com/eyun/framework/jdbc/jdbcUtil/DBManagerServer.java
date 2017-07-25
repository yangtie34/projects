package com.eyun.framework.jdbc.jdbcUtil;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DBManagerServer {

    private static DBManagerServer dbManager;
    private static ConnectionPoolServer.PooledConnection conn;
    private static ConnectionPoolServer connectionPool;

    /**
     * 关闭连接池 等待重新创建
     */
    private static void close() {
        try {
            if (conn != null) conn.close();
            if (connectionPool != null) {
                connectionPool.closeConnectionPool();
            }
            connectionPool=null;
            conn=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从连接池中获取一个连接
     *
     * @return
     */
    public static ConnectionPoolServer.PooledConnection getConnection() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPoolServer();
            connectionPool.init();
            try {
                connectionPool.createPool();
            } catch (Exception e) {
                connectionPool = null;
                e.printStackTrace();
                return null;
            }
        }
        try {
            conn = connectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }

    /**
     * 重启db连接
     */
    public static void reStart() {
        close();
    }

    public static boolean checkConnection(){
        ConnectionPoolServer.PooledConnection pooledConnection=getConnection();
        if(pooledConnection==null){
            return false;
        }else{
            pooledConnection.close();
            return true;
        }
    };
}

