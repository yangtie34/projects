package com.jhkj.mosdc.output.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 小应用通用工具类。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-12-8
 * @TIME: 下午06:03:39
 */
public class WidgetCommonUtils {
	/**
	 * 获取当前的日期,格式为“2012-12-12”
	 * @return
	 */
	public static String getCurrentRQ(){
		String currentRQ ="";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentRQ = sdf.format(date);
		return currentRQ;
	}
	/**
	 * 获取当前时间对应的是上午还是下午。
	 * @return 上午 or 下午
	 */
	public static String getAmOrPm() {
		GregorianCalendar ca = new GregorianCalendar();
		int i = ca.get(GregorianCalendar.AM_PM);
		switch (i) {
		case 0:
			return "上午";
		case 1:
			return "下午";
		}
		return "";
	}
	/**
	 * 获取当前系统时间所在的月份。
	 * @return
	 */
	public static int getCurrentMonth(){
		GregorianCalendar ca = new GregorianCalendar();
		int month =  ca.get(GregorianCalendar.MONTH)+1;
		return month;
	}
	/**
	 * 获取当前系统时间所在的年份
	 * @return
	 */
	public static int getCurrentYear(){
		GregorianCalendar ca = new GregorianCalendar();
		int year = ca.get(GregorianCalendar.YEAR);
		return year;
	}
	public static void main(String[] args){
		System.out.println(getCurrentMonth());
	}
}
