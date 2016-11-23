package com.jhkj.mosdc.framework.cas;

import java.util.HashMap;
import java.util.Map;

public class CasThreadVariable {

	public static Map<Long,UrlVariable> map = new HashMap<Long, UrlVariable>();
	public static void setThreadVariable(long threadId,UrlVariable uv){
		synchronized (map) {
			map.put(threadId, uv);
		}
	}
	public static UrlVariable getThreadVariable(long threadId){
		return map.get(threadId) == null ? new UrlVariable() : map.get(threadId);
	}
	public static void remove(long threadId){
		synchronized (map) {
			map.remove(threadId);
		}
	}
	
}
