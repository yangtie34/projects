package cn.gilight.framework.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.PropertiesUtils;

public class Code {

	
	private static final Logger logger = Logger.getLogger(Code.class);
	
	private static Map<String,String> map=new HashMap<String, String>();
	
	public static Map<String, String> getMap() {
		return map;
	}
	
	public static String getKey(String key) {
		return MapUtils.getString(map, key);
	}
	
	static {
		logger.info("============初始化程序中的CODE==============");
		Properties p= PropertiesUtils.getProperties("/param.properties");
		Iterator<Entry<Object, Object>> it=p.entrySet().iterator();
		while(it.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry entry=(Map.Entry)it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    map.put(key.toString(), value.toString());
		}
		
	}
}
	
