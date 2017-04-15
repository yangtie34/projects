package com.eyun.framework.jdbc.jdbcUtil;

import java.sql.SQLException;

import com.eyun.framework.jdbc.jdbcUtil.ConnectionPool.PooledConnection;

public class DBManager {

    private static DBManager dbManager;
    private static PooledConnection conn;
    private static ConnectionPool connectionPool;

    /**
     * 关闭连接池 等待重新创建
     */
    private static void close() {
        try {
            if (conn != null) conn.close();
            if (connectionPool != null) {
                connectionPool.closeConnectionPool();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从连接池中获取一个连接
     *
     * @return
     */
    public static PooledConnection getConnection() {
        if(dbManager==null){
            dbManager=new DBManager();
        }
		synchronized (dbManager) {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
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
		}
        return conn;
    }

    /**
     * 重启db连接
     */
    public static void reStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                close();
                try {
                    if(connectionPool!=null){
                        connectionPool.init();
                        connectionPool.createPool();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static boolean checkConnection(){
        PooledConnection pooledConnection=getConnection();
        if(pooledConnection==null){
            return true;
        }else{
            return false;
        }
    };
}
