/**
 * 
 */
package com.jhkj.mosdc.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * @author 党冬(mrdangdong@163.com) 建数据连接工具类
 * 
 */
public class ConnectionUtils {
	/**
	 * 
	 * @param username
	 *            数据库用户名
	 * @param password
	 *            数据库密码
	 * @param url
	 *            连接url
	 * @param driver
	 *            驱动类
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnectionInstance(String username,
			String password, String url, String driver)
			throws ClassNotFoundException, SQLException {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		if (StringUtils.isBlank(driver)) {
			return null;
		}
		if (StringUtils.isBlank(username)) {
			return null;
		}
		if (StringUtils.isBlank(password)) {
			return null;
		}
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}

	public static void main(String[] args) {
		//使用测试
		try {
			Connection conn = ConnectionUtils.getConnectionInstance("", "", "","");
			String sql = "";
			Statement statement = conn.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
