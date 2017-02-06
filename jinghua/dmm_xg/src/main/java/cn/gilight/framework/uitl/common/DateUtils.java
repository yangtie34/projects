package cn.gilight.framework.uitl.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils {
	public static final SimpleDateFormat SSS = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat YEAR = new SimpleDateFormat(
			"yyyy");
	public static final SimpleDateFormat MONTH = new SimpleDateFormat(
			"MM");
	public static final SimpleDateFormat DAY = new SimpleDateFormat(
			"dd");
	
	
	
	/**
	 * 
	 * @param strDate yyyy-MM-dd
	 * @return  Date
	 */
	public static Date string2Date(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null == strDate || "".equals(strDate.trim())) {
			return null;
		}
		Date date = null;
		try {
			// 多个线程访问会出现异常，加入同步 20160506
			synchronized (sdf) {
				date = sdf.parse(strDate.trim());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 
	 * @param strDate yyyy-MM-dd HH:mm:ss
	 * @return  Date
	 */

	public static Date string2DateV2(String strDate) {
		SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == strDate || "".equals(strDate.trim())) {
			return null;
		}
		Date date = null;
		try {
			date = sss.parse(strDate.trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 
	 * @param Date 
	 * @return  yyyy-MM-dd
	 */
	public static String date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null == date) {
			return null;
		}
		try {
			String dateStr = sdf.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @param Date 
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String date2StringV2(Date date) {
		SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == date) {
			return null;
		}
		try {
			String dateStr = sss.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 得到今日前一个月的日期
	 * @return
	 */
	public static String getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return SDF.format(calendar.getTime());
	}
	/**
	 * 得到给定日期后一个月的日期
	 * @return
	 */
	public static String getNextMonth(String month_) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(string2Date(month_));
		calendar.add(Calendar.MONTH, +1);
		return SDF.format(calendar.getTime());
	}
	/**
	 * 得到昨天日期
	 * @return
	 */
	public static String getYesterday(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return SDF.format(calendar.getTime());
	}
	
	/**
	 * 得到昨天日期
	 * @param date 通过该时间
	 * @return
	 */
	public static Date getYesterday(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	/**
	 * 得到7天前日期
	 * @return
	 */
	public static String getLastWeek(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		return SDF.format(calendar.getTime());
	}
	/**
	 * 获取当前日期
	 * YY-DD-MM 格式的
	 * @return
	 */
	public static String getNowDate(){
		Calendar calendar = Calendar.getInstance();
		return SDF.format(calendar.getTime());
	}
	
	/**
	 * yy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime(){
		Calendar ca = Calendar.getInstance();
		return SSS.format(ca.getTime());
	}
	
	/**
	 * 获取当前日期
	 * YY-DD-MM hh:mm:ss 格式的 
	 * @return
	 */
	public static String getNowDate2(){
		Calendar ca = Calendar.getInstance();
		return SSS.format(ca.getTime());
		
	}
	
	/**
	 * 获取当前年
	 * @return
	 */
	public static String getNowYear() {
		Calendar calendar = Calendar.getInstance();
		return YEAR.format(calendar.getTime());
	}
	
	/**
	 * 获取当前月(去'0')
	 * @return
	 */
	public static String getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		return MONTH.format(calendar.getTime()).replaceAll("^0*", "");
	}
	
	/**
	 * 获取当前日(去'0')
	 * @return
	 */
	public static String getNowDay() {
		Calendar calendar = Calendar.getInstance();
		return DAY.format(calendar.getTime()).replaceAll("^0*", "");
	}
	
	/**
	 * 获取当前星期几(汉字)
	 * @return (一、二...日)
	 */
	public static String getWeekCn(){
		Calendar calendar = java.util.Calendar.getInstance();
		int weekDay = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		String week = "";
		switch(weekDay){
			case 1 :
				week = "日";
				break;
			case 2 :
				week = "一";
				break;
			case 3 :
				week = "二";
				break;
			case 4 :
				week = "三";
				break;
			case 5 :
				week = "四";
				break;
			case 6 :
				week = "五";
				break;
			case 7 :
				week = "六";
				break;
		}
		return week;
	}
	
	/**
	 * 获取当前星期几(汉字)
	 * @param date 所要获得的日期
	 * @return (一、二...日)
	 */
	public static String getWeekCn(Date date){
		Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		String week = "";
		switch(weekDay){
			case 1 :
				week = "日";
				break;
			case 2 :
				week = "一";
				break;
			case 3 :
				week = "二";
				break;
			case 4 :
				week = "三";
				break;
			case 5 :
				week = "四";
				break;
			case 6 :
				week = "五";
				break;
			case 7 :
				week = "六";
				break;
		}
		return week;
	}
	
	/**
	 * 获取上午下午。
	 * @param date
	 * @return (上、下)
	 */
	public static String getTimeBytime(Date date){
		String out;
		SimpleDateFormat sdf=new SimpleDateFormat("mm");
		int hour =Integer.valueOf(sdf.format(date));
		if(hour<12){
			out="上";
		}else{
			out="下";
		}
		return out;
	}
	
	/**
	 * 获取当前星期几(阿拉伯数字)
	 * @return (1、2、3...7)
	 * (1，星期一；2，星期二...7，星期日)
	 */
	public static int getWeekNo(){
		Calendar calendar = java.util.Calendar.getInstance();
		int weekDay = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		if(1 == weekDay){
			weekDay = 7;
		}else{
			weekDay -= 1; 
		}
		return weekDay;
	}
	
	/**
	 * 判定给定日期是星期几(阿拉伯数字)
	 * (1，星期一；2，星期二...7，星期日)
	 * @param date 日期格式：yyyy-MM-dd
	 * @return (1、2、3...7)
	 * @throws ParseException 
	 */
	public static int getWeekNo(String date) {
		Calendar calendar = java.util.Calendar.getInstance();
		Date da2 = null;
		try {
			da2 = SDF.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		calendar.setTime(da2);
		int weekday = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		return (5 + weekday) % 7 + 1;
	}
	
	/**
	 * 获取下一天
	 * @param date
	 *        yyyy-MM-dd
	 * @return
	 */
	public static String getNextDay(String date){
		Date da2 = null;
		try {
			da2 = SDF.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Calendar cal = new GregorianCalendar(); 
		cal.setTime(da2);
		cal.add(Calendar.DATE, 1);
		da2 = cal.getTime();
		return date2String(da2);
	}
	
	/**
	 * 从开始日期开始，获取给定周次和星期对应的日期
	 * @param beginDate
	 * @param zc
	 * @param week
	 * @return
	 */
	public static Date getDateByZcAndWeekFromBeginDate(Date beginDate,Integer zc,Integer week){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(beginDate);//设置日期
		calendar.add(Calendar.DATE, 1-calendar.get(Calendar.DAY_OF_WEEK));//所在周的第一天
		calendar.add(Calendar.WEEK_OF_YEAR, zc-1);//调整到周次
		calendar.add(Calendar.DATE, week);//调整到星期
		return calendar.getTime();
	}
	
	/**
	 * 从开始日期算第一周，获取给定日期的周次
	 * @param beginDate
	 * @param date
	 * @return
	 */
	public static int getZcByDateFromBeginDate(Date beginDate,Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(beginDate);//设置日期
		calendar.add(Calendar.DATE, 1-calendar.get(Calendar.DAY_OF_WEEK));
		int num=0;
		if(beginDate.before(date) || beginDate.equals(date)){
			while(calendar.getTime().before(date)){
				calendar.add(Calendar.DATE, 7);
				num++;
			}
		}else{
			while(calendar.getTime().after(date)){
				calendar.add(Calendar.DATE, -7);
				num--;
			}
		}		
		return num;
	}
	/**
	 * 获取当前周周一的日期。
	 * @return
	 */
	public static String getWeekFirstDay(){
		return getWeekFirstDay(getNowDate());
	}
	/**
	 * 获取指定日期对应周的 周一
	 * @param date
	 * @return String
	 */
	public static String getWeekFirstDay(String date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(string2Date(date));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return SDF.format(cal.getTime());
	}
	/**
	 * 获取当前周周日的日期。
	 * @return
	 */
	public static String getWeekLastDay(){
		return getWeekLastDay(getNowDate());
	}
	/**
	 * 获取指定日期对应周的 周日
	 * @param date
	 * @return String
	 */
	public static String getWeekLastDay(String date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(string2Date(getNextDayByLen(date, 7))); // 需要取下周的周日，因为默认周日为第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return SDF.format(cal.getTime());
	}
	/**
	 * 获取当前周的开始日期、结束日期
	 * @return String[] [2015-12-28,2016-01-03]
	 */
	public static String[] getWeekDates(){
		return getWeekDates(getNowDate());
	}
	/**
	 * 获取指定日期对应周的开始日期、结束日期
	 * @param date eg：2016-01-01
	 * @return String[] [2015-12-28,2016-01-03]
	 */
	public static String[] getWeekDates(String date){
		String[] ary = {getWeekFirstDay(date), getWeekLastDay(date)};
		return ary;
	}
	
	/**
	 * 获取月初日期
	 * @param date
	 * @return
	 */
	public static String getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return SDF.format(calendar.getTime());
    }
	/**
	 * 获得月末日期
	 * @param date
	 * @return
	 */
	public static String getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return SDF.format(calendar.getTime());
    }
	/**
	 * 获得时间前几天日历集合
	 * @param date 要计算的时间
	 * @param beforeDays 往前推移天数
	 * @return
	 */
	public static List<Date> getBeforeDates(Date date,int beforeDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		List<Date> dates=new ArrayList<Date>();
		for (int i = 1; i <= beforeDays; i++) {
			cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - i);
			dates.add(cal.getTime());
		}
		return dates;
		}
	
	/**
	 * 通过开始日期，第几周次，星期几，得出那天日期
	 * @param start 开始日期 格式'YYYY-DD-MM'
	 * @param zc 第几周 
	 * @param weak 星期几 1-7
	 * @return 那天日期  格式'YYYY-DD-MM'
	 * @throws ParseException 
	 */
	public static String getDateByStartZcWeak(String start,int zc,int weak) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		int startWeak=getWeekNo(start);
		Calendar calendar=Calendar.getInstance();
	    calendar.setTime(sdf.parse(start)); 
	    calendar.add(Calendar.DATE,(zc-1)*7);
	    Date zcDay=calendar.getTime();   
		calendar.setTime(zcDay); 
	    calendar.add(Calendar.DATE,(weak-startWeak));
	    String thisDay=sdf.format(calendar.getTime());
		return thisDay;
	}
	
	/**
	 * 获取所有月份
	 * @return List<Integer>
	 * <br> [1, 2 ... 12]
	 */
	public static List<Integer> getMonthNoList(){
		List<Integer> list = new ArrayList<>();
		for(int i=1; i<=12; ){
			list.add(i++);
		}
		return list;
	}
	
	/**
	 * 获取len天后的日期
	 * @param date eg：2015-01-01
	 * @param len eg：10
	 * @return String eg：2015-01-11
	 */
	public static String getNextDayByLen(String date, int len){
		Date da2 = null;
		try {
			da2 = SDF.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Calendar cal = new GregorianCalendar(); 
		cal.setTime(da2);
		cal.add(Calendar.DATE, len);
		da2 = cal.getTime();
		return date2String(da2);
	}
	/**
	 * 日期比较方法 （date1 > date2 ? true : false）
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean compare(String date1, String date2){
		long time1 = string2Date(date1).getTime(),
			 time2 = string2Date(date2).getTime();
		return time1>time2 ? true : false;
	}
	/**
	 * 日期比较方法 （date1 >= date2 ? true : false）
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean compareEqual(String date1, String date2){
		long time1 = string2Date(date1).getTime(),
			 time2 = string2Date(date2).getTime();
		return time1>=time2 ? true : false;
	}
	/**
	 * 获取日期天数差（date2 - date1）
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int getDayBetween(String date1, String date2){
		long time1 = string2Date(date1).getTime(),
			 time2 = string2Date(date2).getTime(),
        	 between_days = (time2-time1)/(1000*3600*24);  
    	return Integer.parseInt(String.valueOf(between_days));
	}
	/**
	 * 获取这个日期下一月的前一天，如果下月没有这一天就取最后一天
	 * (eg:day = '2015-01-31' 下月是2月，没有31号，就返回2月的最后一天)
	 * @param day eg:yyyy-mm-dd
	 * @return String
	 */
    public static String getNextMonthYesterday(String day){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.string2Date(day));
		int now = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.MONTH, +1);
		calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (max >= now ){
		 calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		return sdf.format(calendar.getTime());
    }
	
	public static void main(String[] args) throws Throwable{
//		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		Date d=format.parse("2013-09-09");
//		System.out.println(getZcByDateFromBeginDate(d, format.parse("2013-09-17")));
		
//		System.out.println(getDayBetween("2015-08-01", "2015-08-03"));
		
/*		Calendar calendar=Calendar.getInstance();
//		calendar.add(Calendar.DATE, 1-calendar.get(Calendar.DAY_OF_WEEK));
//		calendar.setTime(new Date);
		
		System.out.println("周次"+getZcByDateFromBeginDate(new Date(),d));
//		System.out.println("Calendar.getFirstDayOfWeek--"+calendar.getFirstDayOfWeek());
		
		System.out.println("Calendar.DATE--"+calendar.get(Calendar.DATE));
		System.out.println("Calendar.DAY_OF_MONTH--"+calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("Calendar.DAY_OF_WEEK--"+calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("Calendar.DECEMBER--"+calendar.get(Calendar.DECEMBER));
		System.out.println("Calendar.DAY_OF_YEAR--"+calendar.get(Calendar.DAY_OF_YEAR));
		System.out.println("Calendar.DAY_OF_WEEK_IN_MONTH--"+calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println("Calendar.WEEK_OF_MONTH--"+calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("Calendar.WEEK_OF_YEAR--"+calendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("当周周一日期："+DateUtils.getWeekFirstDay());
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		  Calendar cal=Calendar.getInstance();
		  cal.set(Calendar.DAY_OF_WEEK, 1);
		  System.out.println("上周日为："+sdf.format(cal.getTime()));
		  cal.add(Calendar.WEEK_OF_MONTH, -1);
		  cal.set(Calendar.DAY_OF_WEEK, 2);
		  System.out.println("上周一为："+sdf.format(cal.getTime()));
		  
		  System.out.println(getMonthStart(d));
		  System.out.println(getMonthEnd(d));*/
//		System.out.println(getWeekDates());
	}
}
