package com.jhnu.person.sys;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PersonInfo {
	public static Map<String,List> OUMap=new LinkedHashMap();
	public static Map getOUMap() {
		return OUMap;
	}
	public static void setOUMap(String key,List list) {
		OUMap.put(key, list);
	}
	public static boolean hasOther(String key) {
		return getOther(key).size()>0;
	}
	public static List getOther(String key) {
		return OUMap.get(key);
		}
}
