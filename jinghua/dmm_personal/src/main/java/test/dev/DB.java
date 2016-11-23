package test.dev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {
	private static Connection conn = null;
	private static String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static String DB_USER = "dm";
	private static String DB_PASSWORD = "dm";
	
	private DB() {
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库连接失败！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("驱动加载出错!");
		}
		return conn;
	}
	
	public static List<Map<String, Object>> queryListBySql(String sql){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			Map<String, Object> map = null;
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 1; i <= cols; i++) {
					map.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				result.add(map);
			}
		}catch(Exception e){
			System.out.println(sql);
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void batchUpdate(List<String> sqls){
		Connection conn = null;
		Statement ps = null;
		try {
			conn = getConnection();
			ps = conn.createStatement();
			for (String sql : sqls) {
				ps.addBatch(sql);
			}
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update(String sql){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void freeConnection( ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) {
				rs.close(); // 关闭结果集
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close(); // 关闭Statement
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close(); // 关闭连接
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	};
}
