package cn.gilight.dmm.business.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 年龄转换
 * 
 * @author xuebl
 * @date 2016年4月22日 下午4:55:13
 */
public class AgeUtils {
	
	/** 年长度 */
	private static final int YEAR_LEN = 4;
	/** null即为0; false:null不是0 */
	private static final boolean NULL_IS_0 = false;
	
	/**
	 * 计算年龄，截止到end_date多少岁（只用年计算）
	 * @param birthday_date yyyy-MM-dd
	 * @param end_date yyyy-MM-dd / yyyy
	 * @return Integer
	 */
	public static Integer CalculateAge(String birthday_date, String end_date){
		if(birthday_date==null ||"null".equals(birthday_date) || birthday_date.length()<YEAR_LEN || end_date==null || end_date.length()<YEAR_LEN) return null;
		end_date = end_date.length() == YEAR_LEN ? end_date : end_date.substring(0, YEAR_LEN);
		int s_d = Integer.parseInt(birthday_date.substring(0, YEAR_LEN)),
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
		for(String start_date : birthday_list){
			reList.add(CalculateAge(start_date, end_date));
		}
		return reList;
	}
	/**
	 * 计算年龄，截止到end_date多少岁（只用年计算）
	 * @param birthday_list List<Map<yyyy-MM-dd,人数>>
	 * @param end_date yyyy-MM-dd
	 * @param ageColumn 出生日期字段名
	 * @param countColumn 人数字段名
 	 * @return List<Map<String,Object>>
 	 * <br>[{age:16,count:3437},{age:17,count:13256},{age:18,count:32}]
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> CalculateAge(List<Map<String,Object>> birthday_list, String end_date,String ageColumn,String countColumn){
		List<Map<String,Object>> reList = new ArrayList<Map<String,Object>>();
		if(end_date == null)
			return reList;
		Map<String,Object> ageMap = new HashMap<String, Object>();
		for(Map<String,Object> startMap : birthday_list){
			Map<String,Object> map = new HashMap<String, Object>();
			String start_date = MapUtils.getString(startMap, ageColumn);
			int count = MapUtils.getIntValue(startMap, countColumn);
			Integer age = CalculateAge(start_date, end_date);
			String age_s = age == null ? null : String.valueOf(age);
			if(ageMap.containsKey(age_s)){
				map = (Map<String, Object>) MapUtils.getObject(ageMap, age_s);
				int count_1 = MapUtils.getIntValue(map, "count");
				map.put("count", count_1+count);
			}else{
				map.put("age", age);
				map.put("count", count);
				ageMap.put(age_s,map);
				reList.add(map);
			}
		}
		return reList;
	}
	
	/**
	 * 计算出生日期，截止到date，startAge-endAge的人的出生日期
	 * @param startAge 年龄1
	 * @param endAge 年龄2
	 * @param date 截止日期
	 * @return String[] <br>
	 * { 1973-01-01, 1990-12-31 }
	 */
	public static String[] CalculateBirthday(int startAge, int endAge, String date){
		int year = Integer.valueOf(date.length()==YEAR_LEN ? date : date.substring(0, YEAR_LEN));
		if(endAge < startAge){
			int x = endAge;
			endAge   = startAge;
			startAge = x;
		}
		int startYear = year - endAge, endYear = year - startAge;
		return new String[]{startYear+"-01-01", endYear+"-12-31"};
	}

	/**
	 * 年龄分组
	 * <br> 用 [null, null, ''] 判断空数据 null，根据指标出现在哪个下标位置，空数据即在哪一列显示
	 * @param ageList 年龄列表; eg:[17,18,19,18,19,17]
	 * @param age_group 分组标准; eg:[[0,16, "16岁及以下"],[17,18, "17~18岁"],[null,null, '未维护']]
	 * @return List<Map<String,Object>> 
	 * <br> [{name:'16岁及以下',value:3437},{name:"17~18岁",value:13256},{name:'未维护',value:32}]
	 */
	public static List<Map<String, Object>> groupAge(List<Integer> ageList, Object[][] age_group){
		// [ [age1, age2, name, count], [] ]
		List<List<Object>> age_group_list = new ArrayList<List<Object>>();
		int index_start = 0, index_end = 1, index_name = 2, index_value = 3, index_unknown = 0;
		boolean showUnknown = false; // 是否显示未知的数据  null数据
		Object[] unknownAry = new Object[]{null, null, Constant.CODE_Unknown_Name, 0}; // 未知数据
		for(int i=0,len=age_group.length; i<len; i++){
			Object[] age_ary = age_group[i];
			if(age_ary[index_start]==unknownAry[index_start] && age_ary[index_end]==unknownAry[index_end]){
				showUnknown   = true;
				index_unknown = i;
				if(age_ary[index_name]!=null) unknownAry[index_name] = age_ary[index_name];
				continue;
			}
			List<Object> li = new ArrayList<Object>();
			li.add(age_ary[index_start]); // start
			li.add(age_ary[index_end]); // end
			li.add(age_ary[index_name]); // name
			li.add(0); // count
			age_group_list.add(li);
		}
		// 将未知的单独拿出来，对剩余的数据进行分组
		int unknownCount = 0;
		for(Integer age : ageList){
			if(showUnknown && age==null){ // 只判断null，不再判断0
				unknownCount++;
			}else{
				if(age == null){
					if(NULL_IS_0) age = 0; // null值数据 当成0处理
					else continue; // null值数据 不计算
				}
				for(List<Object> age_group_li : age_group_list){
					Integer end_age = (Integer) age_group_li.get(index_end),
							start_age = (Integer) age_group_li.get(index_start),
							count = (Integer) age_group_li.get(index_value);
					if((end_age == null || age <= end_age) && age >= start_age){
						age_group_li.set(index_value, ++count); // value
						break;
					}
				}
			}
		}
		if(unknownCount > 0){
			List<Object> li = new ArrayList<Object>();
			li.add(unknownAry[index_start]);
			li.add(unknownAry[index_end]);
			li.add(unknownAry[index_name]);
			li.add(unknownCount);
			age_group_list.add(index_unknown, li);
		}
		// 数据转换
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(List<Object> l : age_group_list){
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", l.get(index_name));
			m.put("value", l.get(index_value));
			m.put("code", l.get(index_start)+","+l.get(index_end));
			list.add(m);
		}
		return list;
	}
	/**
	 * 年龄分组
	 * <br> 用 [null, null, ''] 判断空数据 null，根据指标出现在哪个下标位置，空数据即在哪一列显示
	 * @param ageColumn 年龄字段 eg:'age'
	 * @param countColumn 人数字段 eg:'count'
	 * @param ageList 年龄列表; eg:[{age:17,count:155},{age:18,count:155},{age:19,count:155}]
	 * @param age_group 分组标准; eg:[[0,16, "16岁及以下"],[17,18, "17~18岁"],[null,null, '未维护']]
	 * @return List<Map<String,Object>> 
	 * <br> [{code:'0,16',name:'16岁及以下',value:3437},{code:'17,18',name:"17~18岁",value:13256},{code:'null,null',name:'未维护',value:32}]
	 */
	public static List<Map<String, Object>> groupAge(List<Map<String, Object>> ageList,String ageColumn,String countColumn,Object[][] age_group){
		List<List<Object>> age_group_list = new ArrayList<List<Object>>();
		int index_start = 0, index_end = 1, index_name = 2, index_value = 3, index_unknown = 0;
		boolean showUnknown = false; // 是否显示未知的数据  null数据
		Object[] unknownAry = new Object[]{null, null, Constant.CODE_Unknown_Name, 0}; // 未知数据
		for(int i=0,len=age_group.length; i<len; i++){
			Object[] age_ary = age_group[i];
			if(age_ary[index_start]==unknownAry[index_start] && age_ary[index_end]==unknownAry[index_end]){
				showUnknown   = true;
				index_unknown = i;
				if(age_ary[index_name]!=null) unknownAry[index_name] = age_ary[index_name];
				continue;
			}
			List<Object> li = new ArrayList<Object>();
			li.add(age_ary[index_start]); // start
			li.add(age_ary[index_end]); // end
			li.add(age_ary[index_name]); // name
			li.add(0); // count
			age_group_list.add(li);
		}
		// 将未知的单独拿出来，对剩余的数据进行分组
				int unknownCount = 0;
				for(Map<String,Object> ageMap : ageList){
					Integer age = MapUtils.getInteger(ageMap, ageColumn);
					int count = MapUtils.getIntValue(ageMap, countColumn);
					if(showUnknown && age==null){ // 只判断null，不再判断0
						unknownCount += count;
					}else{
						if(age == null){
							if(NULL_IS_0) age = 0; // null值数据 当成0处理
							else break; // null值数据 不计算
						}
						for(List<Object> age_group_li : age_group_list){
							Integer end_age = (Integer) age_group_li.get(index_end),
									start_age = (Integer) age_group_li.get(index_start),
									count_age = (Integer) age_group_li.get(index_value);
							if((end_age == null || age <= end_age) && age>= start_age){
								age_group_li.set(index_value, count_age+count); // value
								break;
							}
						}
					}
				}
				if(unknownCount > 0){
					List<Object> li = new ArrayList<Object>();
					li.add(unknownAry[index_start]);
					li.add(unknownAry[index_end]);
					li.add(unknownAry[index_name]);
					li.add(unknownCount);
					age_group_list.add(index_unknown, li);
				}
				// 数据转换
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				for(List<Object> l : age_group_list){
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("name", l.get(index_name));
					m.put("value", l.get(index_value));
					m.put("code", l.get(index_start)+","+l.get(index_end));
					list.add(m);
				}
		return list;
	}
	
	/**
	 * 计算教龄，截止到end_date多少年（只用年计算）
	 * @param birthday_list List<yyyy-MM-dd>
	 * @param end_date yyyy-MM-dd
	 * @return Integer
	 */
	public static Integer CalculateSchoolAge(String date, String end_date){
		if(date==null || date.length()<YEAR_LEN || end_date==null || end_date.length()<YEAR_LEN) return null;
		end_date = end_date.length() == YEAR_LEN ? end_date : end_date.substring(0, YEAR_LEN);
		int s_d = Integer.parseInt(date.substring(0, YEAR_LEN)),
			e_d = Integer.parseInt(end_date),
			age = e_d - s_d,
			min_age = 0;
		return age > min_age ? age : min_age;
	}
	/**
	 * 计算教龄，截止到end_date多少年（只用年计算）
	 * @param birthday_list List<yyyy-MM-dd>
	 * @param end_date yyyy-MM-dd
	 * @return List< Integer>
	 */
	public static List<Integer> CalculateSchoolAge(List<String> dateList, String end_date){
		List<Integer> reList = new ArrayList<Integer>();
		if(end_date == null)
			return reList;
		end_date = end_date.length() == YEAR_LEN ? end_date : end_date.substring(0, YEAR_LEN);
		for(String start_date : dateList){
			reList.add(CalculateSchoolAge(start_date, end_date));
		}
		return reList;
	}

	/**
	 * 列表数据按月份排序（以学年开始月份开始）
	 * @param list 数据列表
	 * @param key_month month对应的key
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> sortMonthListFromXnFirstMonthByKey(List<Map<String, Object>> list, final String key_month){
		return sortMonthListFromXnFirstMonthByKey(list, key_month, null);
	}
	
	/**
	 * 列表数据按月份排序（以学年开始月份开始）（需要补全缺失的月份数据）
	 * @param list 数据列表
	 * @param key_month month对应的key
	 * @param addObject 补全缺失的月（添加属性  key_month:month ）
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> sortMonthListFromXnFirstMonthByKey(List<Map<String, Object>> list, final String key_month, 
			Map<String, Object> addObject){
		// 对月份数据补全
		List<Integer> monthList = DateUtils.getMonthNoList();
		for(Integer mon : monthList){
			boolean isFind = false;
			for(Map<String, Object> m : list){
				if (mon.toString().equals(MapUtils.getString(m, key_month))){
					isFind = true; break;
				}
			}
			if(!isFind && addObject!=null){
				Map<String, Object> copyMap = cloneMap(addObject);
				copyMap.put(key_month, mon);
				list.add(copyMap);
			}
		}
		// 排序 1-12
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int flag = 0;
				int month1 = MapUtils.getInteger(o1, key_month);
				int month2 = MapUtils.getInteger(o2, key_month);
				if(month1 > month2) flag = 1;
				else if(month1 < month2) flag = -1;
				return flag;
			}
		});
		// 排序 9-12-1-8
		List<Map<String, Object>> reList1 = new ArrayList<>(), reList2 = new ArrayList<>();
		int start_month = EduUtils.getMonthThisXn();
		for(Map<String, Object> map : list){
		int month = MapUtils.getInteger(map, "name");
		if(month >= start_month) reList1.add(map);
			else reList2.add(map);
		}
		reList1.addAll(reList2);
		return reList1;
	}
	
	/**
	 * copy Map
	 * @param map
	 * @return Map<String,Object>
	 */
	private static Map<String, Object> cloneMap(Map<String, Object> map){
		Map<String, Object> reMap = new HashMap<>();
		for(Map.Entry<String, Object> m : map.entrySet()){
			reMap.put(m.getKey(), m.getValue());
		}
		return reMap;
	}
	
	
	public static void main(String[] args) {
		DevUtils.p(CalculateAge("2010-04-01", "2016-01-01"));
	}
}
