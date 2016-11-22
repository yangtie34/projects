package com.jhkj.mosdc.jxpg.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

/**   
 * @Description: TODO 教学评估工具类
 * @author Sunwg  
 * @date 2015-1-15 下午12:03:02   
 */
public class JxpgUtil {
	
	/**
	 * 转换公共保存数据格式
	 * [{rowid:1,itemCode:BS_F,itemValue,35},{rowid:1,itemCode:SS_F,itemValue,25}] 转成 {1:[[BS_F,35], [SS_F,25]], 2:[[BS_F,35], [SS_F,25]]}
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, ArrayList<String[]>> changeMapToObject(String params){
		List<Map<String, String>> ary = JSONArray.fromObject(params);
		// {1:[[BS_F,35], [SS_F,25]], 2:[[BS_F,35], [SS_F,25]]}
		Map<String, ArrayList<String[]>> updateM = new HashMap<String, ArrayList<String[]>>();
		for(Map<String, String> map : ary){
			String id = map.get("rowid");
			if(!updateM.containsKey(id)){
				updateM.put(id, new ArrayList<String[]>());
			}
			updateM.get(id).add(new String[]{map.get("itemCode"), map.get("itemValue")});
		}
		return updateM;
	}
	
	/**
	 * 合并数据 {将前台更新的数据合并到数据库查询的数据中}
	 * @param updateM
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public static void mergeMap2List(Map<String, ArrayList<String[]>> updateM, List<Map> list){
		for(Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()){
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for(Map<String, String> map : list){
				if(id.equals(String.valueOf(map.get("ID")))){
					for(String[] ar : valL){
						map.put(ar[0], ar[1]);
					}
				}
			}
		}
	}
	
	
	/** 
	* @Title: changeObjectAttrToMap 
	* @Description: TODO 将一个对象集合中的所有对象的属性拆分，拼装成一个list返回
	* @return List<Map>
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> changeObjectAttrToMap(List<Map> source){
		List<Map> result = new ArrayList<Map>();
		for (int i = 0; i < source.size(); i++) {
			Map items = source.get(i);
			String rowid = String.valueOf(items.get("ID"));
			Set<String> keys = items.keySet();
			for (String key : keys) {
				if (!key.equals("ID")){
					Map item = new HashMap();
					item.put("rowid", rowid);
					item.put("itemCode", key);
					item.put("itemValue", items.get(key));
					result.add(item);
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		JSONArray json = JSONArray.fromObject("[{'LSX_F':'23','JYX_T':'','FX_T':'','JJX_T':'','LX_F':'','GX_T':'','GLX_F':'6','WX_F':'23','XN':'2014-2015','YSX_T':'','JYX_F':'23','JJX_F':'9','WX_T':'','XKML':'所含专业数','LX_T':'','ID':'1','GX_F':'4','LSX_T':'','YSX_F':'12','GLX_T':'','FX_F':'123'},{'LSX_F':'23','JYX_T':'','FX_T':'','JJX_T':'','LX_F':'','GX_T':'','GLX_F':'7','WX_F':'12','XN':'2014-2015','YSX_T':'','JYX_F':'5','JJX_F':'6','WX_T':'','XKML':'专业平均总学分','LX_T':'','ID':'2','GX_F':'5','LSX_T':'','YSX_F':'12','GLX_T':'','FX_F':'2asdf'},{'LSX_F':'11','JYX_T':'','FX_T':'','JJX_T':'','LX_F':'','GX_T':'','GLX_F':'8','WX_F':'23','XN':'2014-2015','YSX_T':'','JYX_F':'4','JJX_F':'4','WX_T':'','XKML':'专业平均时间教学环节学分比例（%）','LX_T':'','ID':'3','GX_F':'6','LSX_T':'','YSX_F':'12','GLX_T':'','FX_F':'3'}]");
//		System.out.println(JxpgUtil.changeObjectAttrToMap(json));
		String a = "3";
		String b = "23";
//		System.out.println(getNewPercent(a,b,4));
		System.out.println(getPercent0(1L, 8L));
	}
	
	public static String getNowXn(){
		SimpleDateFormat ndf = new SimpleDateFormat("yyyy");
		String year = ndf.format(new Date());
		SimpleDateFormat ydf = new SimpleDateFormat("MM");
		int y = Integer.parseInt(ydf.format(new Date()));
		if(y<7){
			return Integer.parseInt(year)-1+"-"+year;	
		}else{
			return year+"-"+Integer.parseInt(year)+1;	
		}
	}
	/**
	 * 获取百分比，保留两位小数  str1 为分子  str2为分母 带有百分号
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getPercent(String str1,String str2){
		String result = "0";
		if(null == str1 || "".equals(str1) || "0".equals(str1)){
			return result;
		}
		if(null == str2 || "".equals(str2) || "0".equals(str2)){
			return result;
		}
		double fenzi=Double.valueOf(str1);
		double fenmu=Double.valueOf(str2);
		double precent = fenzi/fenmu;
		DecimalFormat df = new DecimalFormat("0.0%");
		result = String.valueOf(df.format(precent));
		return result;
	}
	/**
	 * 获取百分比，保留两位小数  str1 为分子  str2为分母 不带百分号
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getNewPercent(String str1,String str2,int len){
		String result = "0";
		if(null == str1 || "".equals(str1) || "0".equals(str1)){
			return result;
		}
		if(null == str2 || "".equals(str2) || "0".equals(str2)){
			return result;
		}
		double fenzi=Double.valueOf(str1);
		double fenmu=Double.valueOf(str2);
		double precent = fenzi*100/fenmu;
		DecimalFormat df = null; 
		if(len == 0){
			df = new DecimalFormat("###");
		}else if(len == 1){
			df = new DecimalFormat("###.#");
		}else if(len == 2){
			df = new DecimalFormat("###.##");
		}else if(len == 3){
			df = new DecimalFormat("###.###");
		}else if(len == 4){
			df = new DecimalFormat("###.####");
		}
		result = String.valueOf(df.format(precent));
		return result;
	}
	/**
	 * 求倍数，保留一位小数
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getNewNum(String str1,String str2){
		String result = "0";
		if(null == str1 || "".equals(str1) || "0".equals(str1)){
			return result;
		}
		if(null == str2 || "".equals(str2) || "0".equals(str2)){
			return result;
		}
		double fenzi=Double.valueOf(str1);
		double fenmu=Double.valueOf(str2);
		double precent = fenzi/fenmu;
		DecimalFormat df = new DecimalFormat("###.#");
		result = String.valueOf(df.format(precent));
		return result;
	}
	/**
	 * 获取百分比，保留1位小数  str1 为分子  str2为分母
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getPercent(long str1, long str2){
		return getNewPercent(String.valueOf(str1), String.valueOf(str2), 1);
	}
	/**
	 * 获取百分比，保留1位小数  str1 为分子  str2为分母
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getPercent(int str1, int str2){
		return getNewPercent(String.valueOf(str1), String.valueOf(str2), 1);
	}
	/**
	 * 获取百分比，没有小数  str1 为分子  str2为分母
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getPercent0(long str1, long str2){
		return getNewPercent(String.valueOf(str1), String.valueOf(str2), 0);
	}
	
	/**
	 * List join ','
	 * @param list
	 * @return
	 */
	public static String listSpilt(List<String> list){
		String str = "";
		for(String s : list){
			if(!"".equals(str)) str += ",";
			str += s;
		}
		return str;
	}
}