package com.jhnu.syspermiss.util.jdbcUtil;

import java.sql.SQLException;

import com.jhnu.syspermiss.util.jdbcUtil.ConnectionPool.PooledConnection;

public class DBManager {


	private static PooledConnection conn;
	private static ConnectionPool connectionPool;

	public void close() {
		try {
			conn.close();
			connectionPool.closeConnectionPool();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static PooledConnection getConnection() {
		
//		synchronized (new String()) {
			if (connectionPool == null){
				connectionPool = new ConnectionPool();
				try {
					connectionPool.createPool();
				} catch (Exception e) {
					connectionPool=null;
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

}
