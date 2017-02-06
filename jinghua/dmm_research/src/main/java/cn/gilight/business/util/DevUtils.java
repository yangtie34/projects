package cn.gilight.business.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gilight.business.model.AdvancedParam;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 产品级开发工具类
 * 
 * @author xuebl
 * @date 2016年4月20日 下午4:38:12
 */
public class DevUtils {
	
	/**
	 * Ajax 常用对象 Map
	 * {"status":true}
	 */
	public static Map<String, Object> MAP(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		return map;
	}
	
	/**
	 * 判断权限是否是全部权限（学校权限）
	 * @param deptList
	 * @return boolean
	 */
	public static boolean isAllPmsData(List<String> deptList){
		boolean flag = false;
		if(deptList==null || deptList.get(0).equals("'0'")){
			flag = true;
		}
		return flag;
	}
	/**
	 * 权限节点是否是空权限
	 * @param dept 权限节点
	 * @return boolean
	 */
	public static boolean isNullPmsData(String dept){
		return "''".equals(dept);
	}

	/**
	 * 根据权限Ids和节点类型 封装为这个节点的标准权限
	 * @param depts 权限ids
	 * @param level_type 节点类型
	 * @return List<String>
	 */
	public static List<String> getDeptListByDeptAndLevelType(String depts, String level_type){
		String[] deptAry = {"''", "''", "''", "''"};
		switch (level_type) {
		case Constant.Level_Type_YX:
			deptAry[1] = depts;
			break;
		case Constant.Level_Type_ZY:
			deptAry[2] = depts;
			break;
		case Constant.Level_Type_BJ:
			deptAry[3] = depts;
			break;
		default:
			break;
		}
		// 定义这个节点的权限
		return ListUtils.ary2List(deptAry);
	}
	
	/**
	 * 获取高级查询参数-业务
	 * @param paramList
	 * @return List<AdvancedParam>
	 */
	public static List<AdvancedParam> getAdvancedParamBusiness(List<AdvancedParam> paramList){
		return getParam(paramList, AdvancedConstant.Group_Business);
	}
	/**
	 * 获取高级查询参数
	 * @param paramList
	 * @param groups
	 * @return List<AdvancedParam>
	 */
	private static List<AdvancedParam> getParam(List<AdvancedParam> paramList, String... groups) {
		List<AdvancedParam> list = new ArrayList<>();
		if(paramList == null || !(paramList instanceof List)) return list;
		String group = null;
		for(AdvancedParam t : paramList){
			group = t.getGroup();
			for(int i=0,len=groups.length; i<len; i++){
				if(groups[i].equals(group)){
					list.add(t);
					break;
				}
			}
		}
		return list;
	}
	
	/**
	 * 年龄分组
	 * <br>（如果第一个指标范围的开始值为null，则将0岁的数据(有值的情况下)作为第一列显示）
	 * @param ageList 年龄列表; eg:[17,18,19,18,19,17]
	 * @param age_group 分组标准; eg:[[null,16, "16岁及以下"],[17,18, "17~18岁"]]
	 * @return List<Map<String,Object>> 
	 * <br> [{name:'16岁及以下',value:3437},{name:"17~18岁",value:13256}]
	 */
	public static List<Map<String, Object>> groupAge(List<Integer> ageList, Object[][] age_group){
		// [ [age1, age2, name, count] ]
		List<List<Object>> age_group_list = new ArrayList<List<Object>>();
		int index_start = 0, index_end = 1, index_name = 2, index_value = 3;
		for(Object[] age_ary : age_group){
			List<Object> li = new ArrayList<Object>();
			li.add(age_ary[index_start]); // start
			li.add(age_ary[index_end]); // end
			li.add(age_ary[index_name]); // name
			li.add(0);
			age_group_list.add(li);
		}
		// 是否显示0岁（如果第一个指标范围的开始值为null，则单独显示0岁）
		boolean showZero = age_group[0][0] == null ? true : false;
		// 将年龄为0的单独拿出来，对剩余的数据进行分组
		int zeroCount = 0;
		for(Integer age : ageList){
			if(showZero && (age == null || age == 0)){
				zeroCount++;
			}else{
				age = age == null ? 0 : age;
				for(List<Object> age_group_li : age_group_list){
					Integer end_age = (Integer) age_group_li.get(1),
							count = (Integer) age_group_li.get(index_value);
					if(end_age == null || age <= end_age){
						age_group_li.set(index_value, ++count); // value
						break;
					}
				}
			}
		}
		if(zeroCount > 0){
			List<Object> li = new ArrayList<Object>();
			li.add(null);
			li.add(0);
			li.add("0岁");
			li.add(zeroCount);
			age_group_list.add(0, li);
		}
		// 数据转换
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(List<Object> l : age_group_list){
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", l.get(index_name));
			m.put("value", l.get(index_value));
			list.add(m);
		}
		return list;
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
	public static Map<String, Object> cloneMap(Map<String, Object> map){
		Map<String, Object> reMap = new HashMap<>();
		for(Map.Entry<String, Object> m : map.entrySet()){
			reMap.put(m.getKey(), m.getValue());
		}
		return reMap;
	}
	/**
	 * copy
	 * @param obj
	 * @return Object
	 * @deprecated
	 */
	public static Object deepClone(Object obj) {// 将对象写到流里
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = null;
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = null;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj); // 从流里读出来
			oi = new ObjectInputStream(bi);
			return (oi.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 测试 替换 System.out.println()
	 * @param str
	 * @Test
	 */
	public static void p(String str){
		System.out.println("/**********/ " +str);
	}
	/**
	 * 测试 替换 System.out.println()
	 * @param obj
	 * @Test
	 */
	public static void p(Object o){
		if(null != o){ System.out.println("/**********/ " +o.toString()); }
	}
}
