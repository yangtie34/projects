package cn.gilight.dmm.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月23日 下午6:04:26
 */
public class ScoreUtils {


	/**
	 * 绩点分组
	 * @param gpaList 年龄列表; eg:[2.4, 2.8, 2.1, 3.3, 0, 3.7]
	 * @param group 分组标准; eg:[[4,4, "4.0"],[3.5,3.99, "3.5~3.99"]]
	 * @return List<Map<String,Object>> 
	 * <br> [{name:'4.0',value:3437},{name:"3.5~3.99",value:13256}]
	 */
	public static List<Map<String, Object>> group(List<Double> gpaList, Object[][] group){
		// [ [start, end, name, count] ]
		List<List<Object>> group_list = new ArrayList<List<Object>>();
		int index_start = 0, index_end = 1, index_name = 2, index_value = 3;
		for(Object[] ary : group){
			List<Object> li = new ArrayList<Object>();
			li.add(ary[index_start]); // start
			li.add(ary[index_end]); // end
			li.add(ary[index_name]); // name
			li.add(0);
			group_list.add(li);
		}
		for(Double val : gpaList){
			val = val==null ? 0 : val;
			for(List<Object> group_one : group_list){
				Double start = (Double) group_one.get(0),
					   end   = (Double) group_one.get(1);
				int count = (int) group_one.get(index_value);
				if(start <= val && val <= end){
					group_one.set(index_value, ++count); // value
					break;
				}
			}
		}
		// 数据转换
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(List<Object> l : group_list){
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", l.get(index_name));
			m.put("value", l.get(index_value));
			list.add(m);
		}
		return list;
	}

	
}
