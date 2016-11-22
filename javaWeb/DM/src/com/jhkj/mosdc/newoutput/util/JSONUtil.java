package com.jhkj.mosdc.newoutput.util;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.Arrays;
import java.util.List;


/**
 * author : wangyongtai
 * Date: 13-5-31
 * Time: 下午5:21
 * Mail: 490091105@qq.com
 */
public class JSONUtil {

    /**
     * 通过JSON字符串的映射关系，将字符串转换成为JSON 对象
     * @param params
     * @param cls
     * @param fields
     * @param realFields
     * @return
     */
    public static Object stringToBean(String params,Class cls,List<String> fields,List<String> jsonFields) throws Exception{
    	Object obj = cls.newInstance();//创建一个PO对象的实例
    	JSONObject json = JSONObject.fromObject(params);
    	for(int i=0;i<fields.size();i++){
    		String jsonField = jsonFields.get(i);//JSON对象中的键
    		String fieldname = fields.get(i).trim();//实际对象中的键
    		String value = getString(json,jsonField);
    		String type = ReflectUtil.getType(obj, fieldname);
    		if("Boolean".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getBoolean(json,jsonField));
    		}else if("String".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getString(json,jsonField));
    		}else if("Double".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getDouble(json,jsonField));
    		}else if("Integer".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getInteger(json,jsonField));
    		}else if("Long".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getLong(json,jsonField));
    		}else if("Short".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getShort(json,jsonField));
    		}
    	}
    	return obj;
    }
    /**
     * 通过JSON字符串的映射关系，将字符串转换成为JSON 对象
     * @param params
     * @param cls
     * @param fields
     * @param jsonFields
     * @return
     */
    public static Object stringToBean(String params,Class cls,String fields,String jsonFields) throws Exception{
    	return stringToBean(params, cls, Arrays.asList(fields.split(",")), Arrays.asList(jsonFields.split(",")));
    }
    /**
     * 返回一个JSONObject,如果找不到对应的key值，返回空
     * @param json
     * @param key
     * @return
     */
    public static JSONObject getJSONObject(JSONObject json,String key){
    	if(json.containsKey(key)){
    	   return json.getJSONObject(key);
    	}else{
    	   return null;
    	}
    }
    /**
     * 返回一个JSONArray对象，如果找不到对应的key值，返回空
     * @param json
     * @param key
     * @return
     */
    public static JSONArray getJSONArray(JSONObject json,String key){
    	if(json.containsKey(key)){
     	   return json.getJSONArray(key);
     	}else{
     	   return null;
     	}
    }
    /**
     * 获取json对象中key 对应的value，如果key不存在，则返回null
     * @param json
     * @param key
     * @return
     */
    public static Object getObject (JSONObject json,String key){
    	if(json.containsKey(key)){
    	   return json.get(key);
    	}else{
    	   return null;
    	}
    }
    /**
     * 获取json对象中key 对应的value，如果key不存在，则返回null
     * @param json
     * @param key
     * @return
     */
    public static String getString (JSONObject json,String key){
    	if(json.containsKey(key)){
    	   return json.getString(key);
    	}else{
    	   return null;
    	}
    }
    /**
     * 获取json对象中key 对应的value，如果key不存在，则返回null
     * @param json
     * @param key
     * @return
     */
    public static Integer getInteger (JSONObject json,String key){
    	if(json.containsKey(key)){
    	   String value = json.getString(key);
    	   if("".equals(value)||value == null){
    		   return null;
    	   }else{
    		   return Integer.valueOf(value);
    	   }
    	}else{ 
    	   return null;
    	}
    }
    /**
     * 获取json对象中key 对应的value，如果key不存在，则返回null
     * @param json
     * @param key
     * @return
     */
    public static Long getLong (JSONObject json,String key){
    	if(json.containsKey(key)){
    		String value = json.getString(key);
     	   if("".equals(value)||value == null){
     		   return null;
     	   }else{
     		   return Long.valueOf(value);
     	   }
    	}else{
    	   return null;
    	}
    }
    /**
     * 获取json对象中key 对应的value，如果key不存在，则返回null
     * @param json
     * @param key
     * @return
     */
    public static Double getDouble (JSONObject json,String key){
    	if(json.containsKey(key)){
    		String value = json.getString(key);
     	   if("".equals(value)||value == null){
     		   return null;
     	   }else{
     		   return Double.valueOf(value);
     	   }
    	}else{
    	   return null;
    	}
    }
    /**
     * 获取json对象中key 对应的value，如果key不存在，则返回null
     * @param json
     * @param key
     * @return
     */
    public static Boolean getBoolean (JSONObject json,String key){
    	if(json.containsKey(key)){
    		String value = json.getString(key);
     	   if("".equals(value)||value == null){
     		   return null;
     	   }else{
     		   return Boolean.valueOf(value);
     	   }
    	}else{
    	   return null;
    	}
    }
    /**
     * 获取json对象中key 对应的value，如果key不存在，则返回null
     * @param json
     * @param key
     * @return
     */
    public static Short getShort (JSONObject json,String key){
    	if(json.containsKey(key)){
    		String value = json.getString(key);
     	   if("".equals(value)||value == null){
     		   return null;
     	   }else{
     		   return Integer.valueOf(value).shortValue();
     	   }
    	}else{
    	   return null;
    	}
    }
    
}

