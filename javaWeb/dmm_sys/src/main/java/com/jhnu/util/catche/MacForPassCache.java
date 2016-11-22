package com.jhnu.util.catche;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MacForPassCache {

	private static LFUCache<String, Map<String,Map<String,Long>>> macForPass = new LFUCache<String, Map<String,Map<String,Long>>>(20000, 0);
	private static long cs=3;//冻结限定次数
	private static long secs=60*60*24;//冻结限定时间 秒数 一天
	private static Map<String,Map<String,Long>> map;
	 /*
	 * 移除某个冻结
	 */
	public static void moveFreeze(String mac,String username){
		if(macForPass.get(mac)!=null){
			macForPass.get(mac).remove(username);
			if(macForPass.get(mac).isEmpty()){
				macForPass.remove(mac);
			}
		}
		
	}
	 /*
	 * 获取某个冻结信息
	 */
	public static Map<String,Long> getFreezeInfo(String mac,String username){
		Map<String,Long> usermap=new HashMap<String,Long>();
		Map<String,Long> usermap1=macForPass.get(mac).get(username);
		if(usermap1.get("secs")==null)return usermap1;
		long time=secs-(new Date().getTime()/1000-usermap1.get("secs"));
		long time_=time/60/60+(time%60>0?1:0);
/*		if(time_==0){
			System.out.println(time);
		}*/
		usermap.put("secs", time_);
		usermap.put("cs", usermap1.get("cs"));
		return usermap;
	}
	 /*
	 * 冻结某个mac一次
	 */
	public static void setFreeze(String mac,String username){
		map=macForPass.get(mac);
		Map<String,Long> usermap=new HashMap<String,Long>();
		if(map==null){
			usermap.put("cs", 1L);
			map=new HashMap<String,Map<String,Long>>();
			map.put(username, usermap);
		}else{
			usermap=map.get(username);
			if(usermap==null){
				usermap=new HashMap<String,Long>();
				usermap.put("cs", 1L);
			}else{
				usermap.put("cs", usermap.get("cs")+1);
				if(usermap.get("cs")==cs){
					usermap.put("secs",new Date().getTime()/1000);	
				}
			}
			map.put(username, usermap);
		}
		macForPass.put(mac, map);
	}
	 /*
	 * 是否已经冻结
	 */
	public static boolean isFreeze(String mac,String username){
		map=macForPass.get(mac);
		if(map==null){
			return false;
		}
		Map<String,Long> usermap=map.get(username);
		if(usermap==null){
			return false;
		}else if(usermap.get("cs")<cs){
			return false;
		}else if(new Date().getTime()/1000-usermap.get("secs")>secs){
			moveFreeze(mac, username);
			return false;
		}
		return true;
	}
}
