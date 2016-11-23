package com.jhkj.mosdc.output.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.output.dao.OutputCommonDao;
/**
 * 获取日期类。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-14
 * @TIME: 上午11:26:12
 */
public class DateUtils {
	private OutputCommonDao commonDao;
	
	public void setCommonDao(OutputCommonDao commonDao) {
		this.commonDao = commonDao;
	}
	/**
	 * 获取本年的起止日期。
	 * 
	 * @return
	 */
	public static Map<String, String> getStartstopDateOfCurrentYear() {
		Map<String, String> startstop = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		try {
			String startDate = sdf.format(date) + "-01-01";
			String endDate = sdf.format(date) + "-12-31";
			startstop.put("startDate", startDate);
			startstop.put("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return startstop;
	}

	/**
	 * 获取本月的起止日期。
	 * 
	 * @return
	 */
	public static Map<String, String> getStartstopDateOfCurrentMonth() {
		Map<String, String> startstop = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		String startDate = "";
		String endDate = "";
		try {
			int currentYear = c.get(Calendar.YEAR);
			int currentMonth = c.get(Calendar.MONTH);// 此值比实际月份少1
			c.set(currentYear, currentMonth, 1);// 当前月的第一天
			startDate = sdf.format(c.getTime());
			c.set(currentYear, currentMonth + 1, 0);// 让下个月的第一天减一天即上个月的最后一天
			endDate = sdf.format(c.getTime());
			startstop.put("startDate", startDate);
			startstop.put("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return startstop;
	}

	/**
	 * 获取本周的起止日期。
	 * 
	 * @return
	 */
	public static Map<String, String> getStartstopDateOfCurrentWeek() {
		Map<String, String> startstop = new HashMap<String, String>();
		
		DateFormat df = DateFormat.getDateInstance();
		String startDate = "";
		String endDate = "";
		try {
			// 差额天数。
			int mondayPlus = getMondayPlus();
			GregorianCalendar currentDate = new GregorianCalendar();
			// 周日的日期
			currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
			Date end = currentDate.getTime();
			endDate = df.format(end);
			
			// 周一的日期
			GregorianCalendar acurrentDate = new GregorianCalendar();
			acurrentDate.add(GregorianCalendar.DATE, mondayPlus);
			Date start = acurrentDate.getTime();
			startDate = df.format(start);
			// 设置起止日期。
			startstop.put("startDate", startDate);
			startstop.put("stopDate", endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return startstop;
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * 
	 * @return
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1; // 因为按中国礼拜一作为第一天,所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1-dayOfWeek;
		}
	}

	/**
	 * 获取本季度的起止日期。
	 * 
	 * @return
	 */
	public static Map<String, String> getStartstopDateOfCurrentQuarter() {
		Map<String, String> startstop = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		try {
			int[][] array = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};   
	        int season = 1;   
	        int month = Integer.parseInt(sdf.format(new Date())); 
	        // 当前月是第几季度
	        for(int i =0;i<array.length;i++){
	        	for(int it : array[i]){
	        		if(it==month){
	        			season=i+1;
	        			break;
	        		}
	        	}
	        }
	        int start_month = array[season-1][0];   //季度中第一个月
	        int end_month = array[season-1][2];   // 季度中的最后一个月
	        Date date = new Date();   
	        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式      
	        String  years  = dateFormat.format(date);      
	        int years_value = Integer.parseInt(years);   
	        int start_days =1;
	        int end_days = getLastDayOfMonth(years_value,end_month);   
	        // 设置起止日期。
			startstop.put("startDate", years_value+"-"+start_month+"-"+start_days);
			startstop.put("stopDate", years_value+"-"+end_month+"-"+end_days);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return startstop;
	}

	/**
	 * 获取本学期的起止日期。
	 * 
	 * @return
	 */
	public Map<String, String> getStartstopDateOfCurrentSemester() {
		Map<String, String> startstop = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			List list = commonDao
					.queryStartstopDataBydate(sdf.format(new Date()));
			Object[] obj = (Object[]) list.get(0);
			startstop.put("startDate", obj[0].toString());
			startstop.put("stopDate", obj[1].toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return startstop;
	}
	
	/**  
     * 获取某年某月的最后一天  
     * @param year 年  
     * @param month 月  
     * @return 最后一天  
     */  
   private static int getLastDayOfMonth(int year, int month) {   
         if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8  
                   || month == 10 || month == 12) {   
               return 31;   
         }   
         if (month == 4 || month == 6 || month == 9 || month == 11) {   
               return 30;   
         }   
         if (month == 2) {   
               if (isLeapYear(year)) {   
                   return 29;   
               } else {   
                   return 28;   
               }   
         }   
         return 0;   
   }   
   /**  
    * 是否闰年  
    * @param year 年  
    * @return   
    */  
  public static boolean isLeapYear(int year) {   
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);   
  }  
} 