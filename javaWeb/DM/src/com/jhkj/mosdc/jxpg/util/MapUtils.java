package com.jhkj.mosdc.jxpg.util;

import java.util.Map;

/**
 * 教学评估-Map工具类
 * @author xuebl
 * @date 2015-01-14
 */
public class MapUtils {
	
	/**
	 * 获取数值型数据，默认为0，null为0
	 * @param map
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static long getLong(Map map, String key){
		long val = 0L;
		if(null != map){
			if(map.containsKey(key)){
				Object o = map.get(key);
				if(null != o && !"null".equals(o) && !"".equals(o)){
					val = Long.valueOf(String.valueOf(o));
				}
			}
		}
		return val;
	}

	/**
	 * 获取数值型数据，默认为0，null为0
	 * @param map
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static double getDouble(Map map, String key){
		double val = 0D;
		if(null != map){
			if(map.containsKey(key)){
				Object o = map.get(key);
				if(null != o && !"null".equals(o) && !"".equals(o)){
					val = Double.valueOf(String.valueOf(o));
				}
			}
		}
		return val;
	}
	
}
