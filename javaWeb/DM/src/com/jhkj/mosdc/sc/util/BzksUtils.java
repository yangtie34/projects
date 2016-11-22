package com.jhkj.mosdc.sc.util;

import java.util.Calendar;

public class BzksUtils {
	/**
	 * 获取统一追加在在校生统计的所有SQL中
	 * @return
	 */
	public static String getAndWhereSql(){
		Calendar cal = Calendar.getInstance();
	     int month = cal.get(Calendar.MONTH) + 1;
	     String str = month<7?" and substr(xjxx.yjbysj,0,4)>=to_char(sysdate,'yyyy')":" and substr(xjxx.yjbysj,0,4)>to_char(sysdate,'yyyy')";
		return str;
	}

}
