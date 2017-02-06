package cn.gilight.business.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 年龄转换
 * 
 * @author xuebl
 * @date 2016年4月22日 下午4:55:13
 */
public class ConvertAge {
	
	private static final int year_len = 4;
	
	/**
	 * 计算年龄，截止到end_date多少岁（只用年计算）
	 * @param birthday_date yyyy-MM-dd
	 * @param end_date yyyy-MM-dd / yyyy
	 * @return int
	 */
	public static int CalculateAge(String birthday_date, String end_date){
		if(birthday_date==null || birthday_date.length()<year_len || end_date==null || end_date.length()<year_len) return 0;
		end_date = end_date.length() == year_len ? end_date : end_date.substring(0, year_len);
		int s_d = Integer.parseInt(birthday_date.substring(0, year_len)),
			e_d = Integer.parseInt(end_date),
			age = e_d - s_d,
			min_age = 0;
		return age > min_age ? age : min_age;
	}
	
	/**
	 * 计算年龄，截止到end_date多少岁（只用年计算）
	 * @param birthday_list List<yyyy-MM-dd>
	 * @param end_date yyyy-MM-dd
	 * @return List<Integer>
	 */
	public static List<Integer> CalculateAge(List<String> birthday_list, String end_date){
		List<Integer> reList = new ArrayList<Integer>();
		if(end_date == null)
			return reList;
		end_date = end_date.length() == year_len ? end_date : end_date.substring(0, year_len);
		for(String start_date : birthday_list){
			reList.add(CalculateAge(start_date, end_date));
		}
		return reList;
	}
	
	public static void main(String[] args) {
		DevUtils.p(CalculateAge("2010-04-01", "2016-01-01"));
	}
}
