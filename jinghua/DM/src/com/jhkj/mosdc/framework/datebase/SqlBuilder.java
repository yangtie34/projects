package com.jhkj.mosdc.framework.datebase;

public class SqlBuilder {
	
	/**
	 * 根据基础sql，拼装分页sql
	 * @param sql 基础sql
	 * @param min 分页最小记录
	 * @param max 分页最大记录
	 * @return
	 */
	public static String getPagingSql(String sql,int min,int max){
		return getOraclePagingSql(sql, min, max);
	}
	private  static String getOraclePagingSql(String sql,int min,int max){
		StringBuilder bd = new StringBuilder();
		bd.append("select * from (")
				.append("select t.*,rownum rn from (").append(sql).append(") t")
				.append(") where rn>").append(min).append(" and rn<=").append(max);
		return bd.toString();
	}
	private static String generateOracleAliasSql(String[] columnArray,String[] aliasArray,String view){
		StringBuilder sql = new StringBuilder("select ");
		for(int i=0;i<columnArray.length;i++){
			sql.append(columnArray[i].trim()).append(" as \"").append(aliasArray[i].trim()).append("\"");
			if(i != columnArray.length-1){
			   sql.append(",");
			}
		}
		sql.append(" from ").append(view);
		return sql.toString();
	}
	/**
	 * 根据参数生成别名sql
	 * @param columnArray 列名数组
	 * @param aliasArray 列别名数组
	 * @return 
	 */
	public static String generateAliasSql(String[] columnArray,String[] aliasArray,String view){
		return generateOracleAliasSql(columnArray, aliasArray, view);
	}
	public static void main(String []args){
		String sql = SqlBuilder.getPagingSql("select * from ts_event_msg", 10, 20);
		System.out.println(sql);
	}
}
