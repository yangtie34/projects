package cn.gilight.framework.uitl.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * List 工具类
 * 
 * @author xuebl
 * @date 2016年4月29日 上午11:01:35
 */
public class ListUtils {

	
	/**
	 * 字符串数组转List
	 * @param ary
	 * @return List<String>
	 */
	public static List<String> ary2List(String[] ary){
		List<String> list = new ArrayList<>();
		for(String s : ary){
			list.add(s);
		}
		return list;
	}

    /**
     * 替换 List中 map的key所对应的空值
     * @param list
     * @param key
     * @param i void
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> replaceNullToValue(List<Map<String, Object>> list, String key, Object i){
    	for(Map map : list){
    		MapUtils.replaceNullToValue(map, key, i);
    	}
    	return list;
    }

	/**
	 * 获取 List< Map> 中某一个字段的集合
	 * @param list
	 * @param key
	 * @return List<String>
	 */
	public static List<String> getListValueFromListMap(List<Map<String, Object>> list, String key) {
		List<String> li = new ArrayList<String>();
		for(Map<String, Object> map : list){
			li.add(MapUtils.getString(map, key));
		}
		return li;
	}
	
}
