package com.eyun.framework.jdbc.jdbcUtil;

import java.sql.SQLException;

import com.eyun.framework.jdbc.jdbcUtil.ConnectionPool.PooledConnection;

public class DBManager {


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
                connectionPool.init();
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

//		synchronized (new String()) {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
            try {
                connectionPool.createPool();
            } catch (Exception e) {
                connectionPool = null;
                e.printStackTrace();
            }
        }
        try {
            conn = connectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//		}
        return conn;
    }

    /**
     * 重启db连接
     */
    private static void reStart() {
        close();
    }

}
