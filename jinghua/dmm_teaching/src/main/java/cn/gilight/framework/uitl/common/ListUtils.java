package cn.gilight.framework.uitl.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	 * 字符串数组 转List
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
	 * 字符串 转List
	 * @param str
	 * @return List<String>
	 */
	public static List<String> string2List(String str){
		String[] ary = str.split(",");
		return ary2List(ary);
	}
	
	/**
	 * 把一个字符串集合，通过一个字符进行连接，生成一个新的字符串
	 *  example : List<String> list = ["a","b","c"]
	 *            ListUtils.join(list,'_');//a_b_c;
	 * @param list
	 * @param string
	 * @return
	 */
	public static String join(List<?> list,String string){
		  StringBuilder bd = new StringBuilder();
		  for(int i=0;i<list.size();i++){
			  if(i!=list.size()-1){
				 bd.append(list.get(i)).append(string);
			  }else{
				 bd.append(list.get(i));
			  }
		  }
		  return bd.toString();
	}

    /**
     * 替换 List中 map的key所对应的空(null)值
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
	
	/**
	 * List< Map> 排序，根据某个字段排序
	 * <br> key所对应的数据必须可转换为Double
	 * @param list
	 * @param key 排序字段
	 * @param isAsc 升序
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> sort(List<Map<String, Object>> list, final String key, final boolean isAsc){
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int flag = 0;
				Double v1 = MapUtils.getDouble(o1, key),
					   v2 = MapUtils.getDouble(o2, key);
				if(v1 != null && v1 > v2){
					flag = 1;
				}else if(v2!=null && (v1==null || (v1 != null && v1 < v2)) ){
					flag = -1;
				}
				flag = isAsc ? flag : -flag;
				return flag;
			}
		});
		return list;
	}
	
}
