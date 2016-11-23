package com.jhnu.syspermiss.util.jdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.util.jdbcUtil.ConnectionPool.PooledConnection;


/**
 * @author Administrator 基础DAO实现类
 * 
 */
public class BaseDaoUtil {

	public List<Object> getRs(String sql,Object[] param) {
		List<Object> list=new ArrayList<Object>();
		  PreparedStatement pstmt = null;  
		  ResultSet rs = null;  
		  PooledConnection con = null;  
		 try {
			 con=DBManager.getConnection();
			 pstmt= con.getConnection().prepareStatement(sql);
			  for(int i=0;i<param.length;i++){
				  pstmt.setString(i+1, param[i].toString()); 
		         }
			  rs = pstmt.executeQuery();  
			  list = extractData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(pstmt,rs,con);
		}
		 return list;
	}
	public void excute(String sql,Object[] param) {
		PreparedStatement pstmt = null;  
		  ResultSet rs = null;  
		  PooledConnection con = null;  
		  try {
			 con=DBManager.getConnection();
			 pstmt= con.getConnection().prepareStatement(sql);
		  for(int i=0;i<param.length;i++){
			  pstmt.setString(i+1, param[i].toString()); 
	         }
			pstmt.execute(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			 close(pstmt,rs,con);
		}
	}

	public List<Object> extractData(ResultSet rs) throws SQLException {
		List<Object>  listOfRows = new ArrayList<Object> ();
		ResultSetMetaData md = rs.getMetaData();
		int num = md.getColumnCount();
		while (rs.next()) {
			Map<String, Object> mapOfColValues = new LinkedHashMap<String, Object>(num);
			if (num == 1) {
				listOfRows.add(rs.getString(1));
			} else {
				for (int i = 1; i <= num; i++) {
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
				}
				listOfRows.add(mapOfColValues);
			}
		}
		return listOfRows;
	}
	
	public void close(PreparedStatement pstmt ,
	  ResultSet rs ,
	  PooledConnection con ){
		try {
			if(pstmt!=null){
				pstmt.close();	
			}
			if(rs!=null){
				rs.close();	
			}
			if(con!=null){
				con.close();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
}