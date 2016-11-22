package com.jhkj.mosdc.permiss.util;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * author : wangyongtai
 * Date: 13-5-31
 * Time: 下午5:21
 * Mail: 490091105@qq.com
 */
public class JSONUtil {
    /***
     * 根据传入的Class，将一个字符串转换成为一个对象
     * @param {String} params 参数
     * @param {Class} cls
     * @return {Object}
     * @throws SQLException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static Object stringToBean(String params,Class cls) throws Exception{
        JSONObject json = JSONObject.fromObject(params);
        Field[] fields = cls.getDeclaredFields();
        Object obj = cls.newInstance();
        for(Field field : fields){
        	String type = field.getType().getSimpleName();
        	String fieldname = field.getName();
        	if("Boolean".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getBoolean(json,fieldname));
    		}else if("String".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getString(json,fieldname));
    		}else if("Double".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getDouble(json,fieldname));
    		}else if("Integer".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getInteger(json,fieldname));
    		}else if("Long".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getLong(json,fieldname));
    		}else if("Short".equals(type)){
    			ReflectUtil.invokeSetter(obj, fieldname, getShort(json,fieldname));
    		}
        }
        return obj;
    }

    /**
     * 将一个字符串转换成为，Class对应的实体List
     * @param params
     * @param cls
     * @return
     */
    public static List<?> stringToList(String params,Class cls){
        List<Object> list = new ArrayList<Object>();
        JSONArray array = JSONArray.fromObject(params);
        for(Object obj : array){
            list.add(JSONObject.toBean(JSONObject.fromObject(obj),cls));
        }
        return list;
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
     	   }else if("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)){
     	       return Boolean.valueOf(value);
     	   }else{
     		   try{
     			  Long v = Long.valueOf(value);
     			  if(v>0){
     				  return true;
     			  }else{
     				  return false;
     			  }
     		   }catch(Exception e){
     			  return Boolean.valueOf(value);
     		   }
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
