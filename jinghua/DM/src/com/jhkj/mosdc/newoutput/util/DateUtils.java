package com.jhkj.mosdc.newoutput.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
/**
 * @Comments: 
 * Created by wangyongtai(490091105@.com)
 * @DATE:2013-8-15
 * @TIME: 上午12:34:55
 */
public class DateUtils {
	/**
	 * 指定日期格式
	 */
	public static final String DATEFORMAT_MILLI_SECOND = "yyyy-MM-dd HH:mm:ss SSS";// 默认日期格式
	public static final String DATEFORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";// 默认日期格式
	public static final String DATEFORMAT_DAY = "yyyy-MM-dd";// 默认日期格式
	/**
	 * 日期增减的类型
	 */
	public static final int YEAR = Calendar.YEAR;//年份
	public static final int MONTH = Calendar.MONTH;//月份
	public static final int DAY_OF_MONTH = Calendar.DAY_OF_MONTH;//一个月中的天数
	public static final int HOURE = Calendar.HOUR;//小时
	public static final int MINUTE = Calendar.MINUTE;//分钟
	public static final int SECOND = Calendar.SECOND;//秒
	public static final int MILLISECOND = Calendar.MILLISECOND;//毫秒

	/**
	 * 方法功能：获得当前运行毫秒
	 */
	public static Long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static String getYear() {
		return getCalendarField(Calendar.YEAR);
	}

	/**
	 * 获取当前月份,默认是返回的月份小于10月的时候不带0，如果zeroRequire为true，则带0
	 * 
	 * @param zeroRequire
	 *            是否需要0
	 * @return
	 */
	public static String getMonth(boolean zeroRequire) {
		Calendar ca = Calendar.getInstance();
		int month = ca.get(Calendar.MONTH);
		if (zeroRequire) {
			if (month > 0 && month <= 9) {
				return "0" + String.valueOf(month);
			} else {
				return String.valueOf(month);
			}
		} else {
			return String.valueOf(month);
		}
	}

	/**
	 * 获取当前月份的某一天,默认是返回的天数小于10的时候不带0，如果zeroRequire为true，则带0
	 * 
	 * @param zeroRequire
	 *            是否需要0
	 * @return
	 */
	public static String getDay(boolean zeroRequire) {
		Calendar ca = Calendar.getInstance();
		int dayOfMonth = ca.get(Calendar.DATE);
		if (zeroRequire) {
			if (dayOfMonth > 0 && dayOfMonth <= 9) {
				return "0" + String.valueOf(dayOfMonth);
			} else {
				return String.valueOf(dayOfMonth);
			}
		} else {
			return String.valueOf(dayOfMonth);
		}
	}
	/**
	 * 获取当前星期几(字符串)
	 * @return (1、2、3...7)
	 * (1，星期一；2，星期二...7，星期日)
	 */
	public static String getWeek(){
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK);
		int max = cal.getActualMaximum(Calendar.DAY_OF_WEEK);
		if(max == 7){week = week-1;}
		if(week == 0){
		   return String.valueOf(7);
		}else{
		   return String.valueOf(week);
		}
	}
	/**
	 * 根据一个日期，返回是星期几的字符串
	 * @param sdate
	 * @return
	 */
	public static String getWeekChStr() {
		Calendar cal = Calendar.getInstance();
		int max = cal.getActualMaximum(Calendar.DAY_OF_WEEK);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		String abcStr="日一二三四五六";
		if(max==7){week = week-1;}
		return "星期"+abcStr.charAt(week);
	}
	
	private static String getCalendarField(int n) {
		Calendar ca = Calendar.getInstance();
		return String.valueOf(ca.get(n));
	}
	/**
	 * 获取日期对象的格式化字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateFormat(Date date,String format){
		try{
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
//            if (as_format.equals("dd-MMM1-yy")){
//                dateformat=new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);
//            }else if (as_format.equals("dd-MMM2-yy")){
//                dateformat=new SimpleDateFormat("dd-MMM-yy");
//            }else{
//                dateformat=new SimpleDateFormat(as_format);
//            }
            return dateformat.format(date);
        }catch(Exception ex){
            return "";
        }
	}
	/**
	 * 获取当前日期字符串,精确到毫秒
	 * @return
	 */
	public static String getCurrentDate_MilliSecondString(){
        return getDateFormat(new Date(),DATEFORMAT_MILLI_SECOND);
    }
	/**
	 * 获取当前日期字符串,精确到秒
	 * @return
	 */
    public static String getCurrentDate_SecondString(){
        return getDateFormat(new Date(),DATEFORMAT_SECOND);
    }
    /**
	 * 获取当前日期字符串,精确到天
	 * @return
	 */
    public static String getCurrentDate_DayString(){
        return getDateFormat(new Date(),DATEFORMAT_DAY);
    }
	/**
	 * 获取当前日期对象
	 * @return
	 */
	public static Date getCurrentDate(){
		Date nowDate = new Date();
        return nowDate;
	}
	/**
	 * 获取当前日期的格式化字符串，格式可以参照类的静态变量：DATEFORMAT_MILLI_SECOND、DATEFORMAT_SECOND、DATEFORMAT_DAY
	 * @param format
	 * @return
	 */
	public static String getCurrentDateFormat(String format){
		Date nowDate = getCurrentDate();
		return getDateFormat(nowDate, format);
	}
	/**
	 * 获取某一个日期向后的某一段时间的日期
	 * example DateUtils.getNext(new Date(),DateUtils.YEAR,2)//加入当前年份是2012年，那么输出的应该是2014年
	 * @param args
	 */
	public static Date getNext(Date source_date,int add_time_type,int add){
		Calendar cal = Calendar.getInstance();
		cal.setTime(source_date);
        cal.add(add_time_type,add);
        Date result_date = cal.getTime();
        return result_date;
	}
	/**
	 * 获取某几天后的日期
	 * @param source_date
	 * @param days
	 * @return
	 */
	public static Date getNextDay(Date source_date,int days){
		return getNext(source_date,DAY_OF_MONTH,days);
	}
	/**
	 * 获取两个时间段之间的差值,类型是毫秒
	 * @return
	 */
	public Long getTimeInterval(Date begin,Date end){
		return getTimeInterval(begin, end, MILLISECOND);
	}
	/**
	 * 获取两个时间段的差值，差值类型可以是年、月、日、时、分、秒、毫秒
	 * DateUtils.getTimeBetweenDateRange(日期1，日期2，差值类型);//
	 * @param begin
	 * @param end
	 * @param type
	 * @return
	 */
	public Long getTimeInterval(Date begin,Date end,int type){
		Calendar b_cal = Calendar.getInstance();
		b_cal.setTime(begin);
		Calendar e_cal = Calendar.getInstance();
		e_cal.setTime(end);
		switch(type){
			case YEAR : return (long) (b_cal.get(Calendar.YEAR)-e_cal.get(Calendar.YEAR));
			case MONTH : return (long) (b_cal.get(Calendar.MONTH)-e_cal.get(Calendar.MONTH));
			case DAY_OF_MONTH : return (long) (b_cal.get(Calendar.DAY_OF_MONTH)-e_cal.get(Calendar.DAY_OF_MONTH));
			case HOURE : return (long) (b_cal.get(Calendar.HOUR)-e_cal.get(Calendar.HOUR));
			case MINUTE : return (long) (b_cal.get(Calendar.MINUTE)-e_cal.get(Calendar.MINUTE));
			case SECOND : return (long) (b_cal.get(Calendar.SECOND)-e_cal.get(Calendar.SECOND));
			case MILLISECOND : return (long) (b_cal.get(Calendar.MILLISECOND)-e_cal.get(Calendar.MILLISECOND));
		}
		return 0l;
	}
	/**
	 * 给定一个字符串，尝试着把字符串转换成为日期类型，如果转换不成共，返回null
	 * @param dateStr
	 * @return
	 */
	public static Date getDate(String dateStr){
        Date ldt_rq=null;
        String as_date = dateStr;
        try{
            int li_len=as_date.length();
            if (as_date.indexOf("-") > -1 && li_len == 10) {
                ldt_rq = getDate(as_date, "yyyy-MM-dd");
            } else if (as_date.indexOf("-") > -1 && li_len>=8 && li_len<=9) {
                ldt_rq = getDate(as_date, "yyyy-M-d");
            } else if (as_date.indexOf("-") > -1 && li_len == 19) {
                ldt_rq = getDate(as_date, "yyyy-MM-dd HH:mm:ss");
            } else if (as_date.indexOf("-") > -1 && li_len == 23) {
                ldt_rq = getDate(as_date, "yyyy-MM-dd HH:mm:ss SSS");
            } else if (as_date.indexOf("-") > -1 && li_len>=21 && li_len<=22) {
                ldt_rq = getDate(as_date, "yyyy-M-d HH:mm:ss SSS");
            } else if (as_date.indexOf("-") > -1 && li_len==7) {
                ldt_rq = getDate(as_date, "yyyy-MM");
            } else if (as_date.indexOf("-") > -1 && li_len==6) {
                ldt_rq = getDate(as_date, "yyyy-M");
            } else if (li_len==4) {
                ldt_rq = getDate(as_date, "yyyy");
            } else if (as_date.indexOf("/") > -1 && li_len == 10) {
                ldt_rq = getDate(as_date, "yyyy/MM/dd");
            } else if (as_date.indexOf("/") > -1 && li_len>=8 && li_len<=9) {
                ldt_rq = getDate(as_date, "yyyy/M/d");
            } else if (as_date.indexOf("/") > -1 && li_len == 19) {
                ldt_rq = getDate(as_date, "yyyy/MM/dd HH:mm:ss");
            } else if (as_date.indexOf("/") > -1 && li_len == 23) {
                ldt_rq = getDate(as_date, "yyyy/MM/dd HH:mm:ss SSS");
            } else if (as_date.indexOf("/") > -1 && li_len>=21 && li_len<=22) {
                ldt_rq = getDate(as_date, "yyyy/M/d HH:mm:ss SSS");
            } else if (as_date.indexOf("/") > -1 && li_len==7) {
                ldt_rq = getDate(as_date, "yyyy/MM");
            } else if (as_date.indexOf("/") > -1 && li_len==6) {
                ldt_rq = getDate(as_date, "yyyy/M");
            }else{
           	 return null;
            }
        }catch(Exception ex){
            return null;
        }
        return ldt_rq;
   }
	/**
	 * 根据给定的日期格式化字符串,把源字符串转换成为Date
	 * @param source 源字符串
	 * @param format 格式化字符串(用于DateFormat)
	 * @return
	 */
    public static Date getDate(String source,String format){
        Date ldt_rq=null;
        SimpleDateFormat df=null;
        if (format.equals("dd-MMM1-yy")) {
            df = new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);
        }else if (format.equals("dd-MMM2-yy")) {
            df = new SimpleDateFormat("dd-MMM-yy");
        }else{
            df = new SimpleDateFormat(format);
        }
        df.setLenient(false);//严格控制输入
        try{
            ldt_rq=df.parse(source);
        }catch(Exception ex){
            return null;
        }
        return ldt_rq;
    }
 	/**
 	 * 把毫秒数转换成为Date
 	 * @param al_time
 	 * @return
 	 */
    public static Date getDate(long al_time){
        Calendar   cal=Calendar.getInstance();  
        cal.setTimeInMillis(al_time); 
        return cal.getTime(); 
    }
    /**
     * 根据给定的年份、月份、天，生成一个日期Date对象
     * @param yyyy
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(final int yyyy, final int month, final int day) {
        Calendar   cal=Calendar.getInstance();  
        cal.set(yyyy, month - 1, day);
        return cal.getTime();
    }
    /**
     * 根据给定的年、月、天、时、分生成一个日期Date对象
     * @param yyyy
     * @param month
     * @param day
     * @param hour
     * @param min
     * @return
     */
    public static Date getDate(final int yyyy, final int month, final int day, final int hour, final int min) {
        Calendar   cal=Calendar.getInstance();  
        cal.set(yyyy, month - 1, day, hour, min);
        return cal.getTime();
    }
    /**
     * 判断是否是闰年
     * @param args
     */
    public static boolean isLeapYear(int ai_year){
        Calendar cal = Calendar.getInstance();
        return ((GregorianCalendar) cal).isLeapYear(ai_year);
    }
    /**
     * 通过给定的日期格式,判断字符串是否是日期
     * @param source 字符串
     * @param format 日期格式
     * @return
     */
    public static boolean isDateString(String source,String format){
    	Date ldt_rq=null;
 	    ldt_rq = getDate(source,format);
 	    if(ldt_rq==null) 
 		  return false;
          return true;
    }
	/**
	 * 查询给定的日期,在一个日期范围内属于第几周，开始日期、结束日期、查询日期 的时间精度为天
	 * @return 第几周
	 * @throws ParseException 
	 */
	public static int getWeekNumberInDateRange(String beginDate,String endDate,String queryDate) throws ParseException{
		Calendar begin = Calendar.getInstance();
    	Calendar end = Calendar.getInstance();
    	Calendar in = Calendar.getInstance();
    	
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	begin.setTime(format.parse(beginDate));//开始日期
    	int beginweekday = begin.get(Calendar.DAY_OF_WEEK);//开始日期在一周中的第几天
    	end.setTime(format.parse(endDate));//结束日期
    	int endweekday = end.get(Calendar.DAY_OF_WEEK);//结束日期在一周中的第几天
    	in.setTime(format.parse(queryDate));//查询日期
    	int inweekday = in.get(Calendar.DAY_OF_WEEK);//查询日期在一周中的第几天
    	
    	int week = 0;
    	
    	//计算日期的策略是计算开始日期和结束日期之间的天数，减去开始日期所在的残缺的周的天数，然后对剩余的天数除以7，然后向上取整即可
    	long day = (in.getTimeInMillis()-begin.getTimeInMillis())/(1000*3600*24);
    	//日历计算中，一周的开始是以星期日开始的，和我们对星期的计算方式有差异，因此计算
    	//一周的日期的时候，如果是1，表示是星期日，因此算是单独一周，两个日期的天数差直接减1即可，如果大于1，直接用7-weekday即可
    	if(beginweekday == 1){
    	   week += 1;
    	   day -= 1;
    	}else{
    	   week += 1;
    	   day = day-(7-beginweekday);
    	}
    	if(day>0){
    	   week += Double.valueOf(Math.ceil(day/7.0)).intValue();//剩余天数除以7,并向上取整
    	}
    	return week;
	}
	/**
	 * 查询给定的日期,在一个日期范围内属于第几周，开始日期、结束日期、查询日期 的时间精度为天
	 * @return 第几周
	 * @throws ParseException 
	 */
	public static Map getWeekRangeWithWeekNumber(String beginDate,String endDate,Integer weekNumber) throws ParseException{
		Map map = new HashMap();
		
		if(weekNumber == null){
		   weekNumber = 1;
		}
		
		Calendar begin = Calendar.getInstance();
    	Calendar end = Calendar.getInstance();
    	
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	begin.setTime(format.parse(beginDate));//开始日期
    	int beginweekday = begin.get(Calendar.DAY_OF_WEEK);//开始日期在一周中的第几天
    	end.setTime(format.parse(endDate));//结束日期
    	int endweekday = end.get(Calendar.DAY_OF_WEEK);//结束日期在一周中的第几天
    	
    	int maxWeek = DateUtils.getWeekNumberInDateRange(beginDate, endDate, endDate);
    	
    	long beginMilin = begin.getTimeInMillis();//开始毫秒数
    	
    	if(maxWeek<weekNumber){
    	   return null;
    	}else{
    		  if(beginweekday == 1){
    			 beginMilin -= 3600*24*6*1000;
    		  }else{
    			 beginMilin -= (beginweekday-2)*3600*24*1000;
    	      }
    		 beginMilin += (weekNumber-1)*7L*24*3600*1000;
    		 begin.setTimeInMillis(beginMilin);
 			 map.put("begin", (begin.get(Calendar.MONTH)+1)+"月"+begin.get(Calendar.DAY_OF_MONTH)+"日");
 			 beginMilin += (6)*24*3600*1000;
 			 begin.setTimeInMillis(beginMilin);
 			 map.put("end", (begin.get(Calendar.MONTH)+1)+"月"+begin.get(Calendar.DAY_OF_MONTH)+"日");
 			 map.put("queryweek", weekNumber);
    	}
    	return map;
    	
	}
	/**
	 * 查询一个日期是否在给定的日期范围内
	 * @param beginDate
	 * @param endDate
	 * @param queryDate
	 * @return
	 * @throws Exception 
	 */
	public static boolean checkIfInDateRange(String beginDate,String endDate,String queryDate) throws Exception{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long begin = format.parse(beginDate).getTime();
		long end = format.parse(endDate).getTime();
		long query = format.parse(queryDate).getTime();
		if(query >= begin && query <=end){
		   return true;
		}else{
		   return false;
		}
	}

	public static void main(String args[]){
		System.out.println(DateUtils.isLeapYear(2009));
//		int max = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_WEEK);
//		System.out.println(max);
//		System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
//		System.out.println(DateUtils.getWeekChStr());
//		System.out.println(DateUtils.getDateFormat(DateUtils.getNext(new Date(), DateUtils.DAY_OF_MONTH, 32), "yyyy-MM-dd"));
	}
}
