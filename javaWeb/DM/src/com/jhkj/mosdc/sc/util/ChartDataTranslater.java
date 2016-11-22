package com.jhkj.mosdc.sc.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jhkj.mosdc.sc.domain.ScConstant;
/**
 * HightChart 数据转换类
 * @author Administrator
 *
 */
public class ChartDataTranslater {
	/**
	 * 将结果集转换为chart展示数据。
	 * @param arrs
	 * @param bzdm
	 * @param result
	 * @return
	 */
	public static List<Map> getFhChartData(int[] arrs,List<Map>bzdm,List<Map>result){
		
		/*首先，将结果集转化成map形式的数据*/
		Map<String,List<Map>> tempMap = new TreeMap<String,List<Map>>();
		for(Map tjMap : result){
			String lylx = tjMap.get("LX")==null?ScConstant.wwh:tjMap.get("LX").toString();
			if(tempMap.containsKey(lylx)){
				tempMap.get(lylx).add(tjMap);
			}else{
				List<Map> tempList = new ArrayList<Map>();
				tempList.add(tjMap);
				tempMap.put(lylx, tempList);
			}
		}
		/*循环查看map中每个键值对下的数据，补全数据*/
		for(Map dmMap : bzdm){
			String mc = dmMap.get("MC").toString();
			if(tempMap.containsKey(mc)){
				tempMap.put(mc, translate(arrs, tempMap.get(mc)));
			}else{
				tempMap.put(mc, translate(arrs, new ArrayList<Map>()));
			}
		}
		/*最后转化为LISTmap ，返回结果*/
		List<Map> backval = new ArrayList<Map>();
		Iterator it = tempMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			List<Map> alist = tempMap.get(key);
			for(Map tm : alist){
				tm.put("field", tm.get("DM"));
				tm.put("name", key);
				tm.put("value", tm.get("MC"));
			}
			backval.addAll(alist);
		}
		
		return backval;
	}
	/**
	 * 转换数据方法。
	 * @param months
	 * @param result
	 * @return
	 */
	public static List<Map> translate(int[] months,List<Map> result){
		List<Map> backval = new ArrayList<Map>();
		for(int i=0 ;i<months.length;i++){
			Map temp = new TreeMap();
			temp.put("DM", months[i]);
			boolean flag = false;
			for(Map map : result){
				String yf = map.get("DM").toString();
				if(Integer.parseInt(yf)==months[i]){
					temp.put("MC", map.get("MC"));
					flag = true;
					break;
				}
			}
			if(!flag){
				temp.put("MC", 0);
			}
			backval.add(temp);
		}
		return backval;
	}
	/**
	 * 获得年份区间的年份列表
	 * @param from
	 * @param to
	 * @return
	 */
	public static int[] getYears(int from,int to){
		int a= to-from>0?to-from:0;
		int[] backval = new int[a+1];
		for(int i = 0;i<backval.length;i++){
			backval[i] = from+i;
		}
		return backval;
	}
	/**
	 * 设置转换属性
	 * @param result
	 */
	public static void filterSet(String name,List<Map> result){
		for(Map map :result){
			int dm = map.containsKey("DM")?Integer.parseInt(map.get("DM").toString()):0;
			map.put("name", name);
			map.put("field", dm);
			map.put("value", map.get("MC"));
		}
	}
	
}
