package com.glite.flink.example.tohdfs;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.table.typeutils.RowTypeInfo;

public class TStu {

	
	public static JDBCInputFormat createInputFormat(String dayPre) {
		//ID,CARD_ID,PAY_MONEY,SURPLUS_MONEY,TIME_,CARD_PORT_ID,CARD_DEAL_ID,WALLET_CODE
		
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.DOUBLE_TYPE_INFO, BasicTypeInfo.DOUBLE_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO };
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select * from T_STU")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
}
