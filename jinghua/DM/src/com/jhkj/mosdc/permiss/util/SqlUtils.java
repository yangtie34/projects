package com.jhkj.mosdc.permiss.util;

public class SqlUtils {

	public static String generateAliasSql(String[] array, String[] alias, String table) {
		StringBuilder sql = new StringBuilder("select ");
		for (int i = 0; i < array.length; i++) {
			sql.append(array[i].trim()).append(" as \"")
					.append(alias[i].trim()).append("\"");
			if (i != array.length - 1) {
				sql.append(",");
			}
		}
		sql.append(" from ").append(table);
		return sql.toString();
	}
}
