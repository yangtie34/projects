package cn.gilight.product.card.dao;

import cn.gilight.framework.code.Code;


public class CardTjUtil {

	public static String ccx="'"+Code.getKey("card.ct")+"','"+Code.getKey("card.xy")+"','"+Code.getKey("card.cs")+"'";
	
	public static String getDateTJ(String startDate,String endDate){
		
		String tj=" and t.year_month>=to_date('"+startDate+"','yyyy-mm') "+
					"and t.year_month<to_date('"+endDate+"','yyyy-mm') ";
		return tj;
	}
	
	public static String getHourTj(String queryType){
		String tj="";
		switch (queryType) {
		case "zao":
			tj=" and t.hour_<='09' ";
			break;
		case "zhong":
			tj=" and t.hour_<='14' and t.hour_>'09' ";
			break;
		case "wan":
			tj=" and t.hour_>'14' ";
			break;
		default:
			break;
		}
		return tj;
	}
	
}
