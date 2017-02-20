package com.yiyun.framework.util.common;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 常用的针对Map的操作
 * @Comments: 
 * Created by wangyongtai(490091105@.com)
 * @DATE:2013-8-14
 * @TIME: 上午9:04:23
 */
@SuppressWarnings("rawtypes")
public class MapUtils {
	 /**
     * 用Null-Safe的方式从Map中获取对象
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值，如果Map is null ,返回null
     */
	public static Object getObject(final Map map, final Object key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * 用Null-Safe的方式从Map中获取String对象
     * <p>
     * 字符串是通过调用toString的方式得到的
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的String对象，如果Map is null ,返回null
     */
    public static String getString(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return "";
    }

    /**
     *
     *  用Null-Safe的方式从Map中获取Boolean对象
     * <p>
     *  如果值是一个<code>Boolean</code>，它就会被立即返回。
     *  如果值是一个字符串并且equals 'true' 忽略大小写，那么将会返回<code>true</code>，其他情况返回<code>false</code>
     *  如果值是一个<code>Number</code>一个，并且是一个0值，那么返回<code>true</code>，其他非0情况，返回<code>true</code>
     *  其他情况，返回<code>null</code>
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Boolean对象，如果Map is null ,返回null
     */
    public static Boolean getBoolean(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;
                    
                } else if (answer instanceof String) {
                    return new Boolean((String) answer);
                    
                } else if (answer instanceof Number) {
                    Number n = (Number) answer;
                    return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return null;
    }

    /**
     *  用Null-Safe的方式从Map中获取Number对象
     * <p>
     * 如果值是一个<code>Number</code>，它就会被立即返回。
     * 如果值是 <code>String</code> 它会被使用调用
     * {@link NumberFormat#parse(String)} ，使用系统默认转换
     * 转换失败返回 <code>null</code>
     * 其他情况返回 <code>null</code> 
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Number对象，如果Map is null ,返回null
     */
    public static Number getNumber(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;
                    
                } else if (answer instanceof String) {
                    try {
                        String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);
                        
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 用Null-Safe的方式从Map中获取Byte对象
     * <p>
     * Byte值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Byte对象，如果Map is null ,返回null
     */
    public static Byte getByte(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return new Byte(answer.byteValue());
    }

    /**
     * 用Null-Safe的方式从Map中获取Short对象
     * <p>
     * Short值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Short对象，如果Map is null ,返回null
     */
    public static Short getShort(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Short) {
            return (Short) answer;
        }
        return new Short(answer.shortValue());
    }

    /**
     * 用Null-Safe的方式从Map中获取Integer对象
     * <p>
     * Integer值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Integer对象，如果Map is null ,返回null
     */
    public static Integer getInteger(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return new Integer(answer.intValue());
    }

    /**
     * 用Null-Safe的方式从Map中获取Long对象
     * <p>
     * Long值的获取参照{@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Long对象，如果Map is null ,返回null
     */
    public static Long getLong(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return new Long(answer.longValue());
    }

    /**
     * 用Null-Safe的方式从Map中获取Float对象
     * <p>
     * Float值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Float对象，如果Map is null ,返回null
     */
    public static Float getFloat(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Float) {
            return (Float) answer;
        }
        return new Float(answer.floatValue());
    }

    /**
     * 用Null-Safe的方式从Map中获取Double对象
     * <p>
     * Double值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Double对象，如果Map is null ,返回null
     */
    public static Double getDouble(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return new Double(answer.doubleValue());
    }

    /**
     * 用Null-Safe的方式从Map中获取Map对象
     * <p>
     * 如果找到的值不是一个Map类型的对象的话，那么将返回<code>null</code>
     * <code>null</code> is returned.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的Map对象，如果Map is null ,返回null
     */
    public static Map getMap(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null && answer instanceof Map) {
                return (Map) answer;
            }
        }
        return null;
    }
    // Type safe primitive getters
    //-------------------------------------------------------------------------
    /**
     *
     *  用Null-Safe的方式从Map中获取boolean值
     * <p>
     *  如果值是一个<code>boolean</code>，它就会被立即返回。
     *  如果值是一个字符串并且equals 'true' 忽略大小写，那么将会返回<code>true</code>，其他情况返回<code>false</code>
     *  如果值是一个<code>Number</code>一个，并且是一个0值，那么返回<code>true</code>，其他非0情况，返回<code>true</code>
     *  其他情况，返回<code>null</code>
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的boolean，如果Map is null ,返回null
     */
    public static boolean getBooleanValue(final Map map, final Object key) {
        Boolean booleanObject = getBoolean(map, key);
        if (booleanObject == null) {
            return false;
        }
        return booleanObject.booleanValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取byte值
     * <p>
     * byte值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的byte，如果Map is null ,返回null
     */
    public static byte getByteValue(final Map map, final Object key) {
        Byte byteObject = getByte(map, key);
        if (byteObject == null) {
            return 0;
        }
        return byteObject.byteValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取short
     * <p>
     * short值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的short，如果Map is null ,返回null
     */
    public static short getShortValue(final Map map, final Object key) {
        Short shortObject = getShort(map, key);
        if (shortObject == null) {
            return 0;
        }
        return shortObject.shortValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取int
     * <p>
     * int值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的int对象，如果Map is null ,返回null
     */
    public static int getIntValue(final Map map, final Object key) {
        Integer integerObject = getInteger(map, key);
        if (integerObject == null) {
            return 0;
        }
        return integerObject.intValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取long
     * <p>
     * long值的获取参照{@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的long，如果Map is null ,返回null
     */
    public static long getLongValue(final Map map, final Object key) {
        Long longObject = getLong(map, key);
        if (longObject == null) {
            return 0L;
        }
        return longObject.longValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取float
     * <p>
     * float值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的float，如果Map is null ,返回null
     */
    public static float getFloatValue(final Map map, final Object key) {
        Float floatObject = getFloat(map, key);
        if (floatObject == null) {
            return 0f;
        }
        return floatObject.floatValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取double
     * <p>
     * Double值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @return Map中的值转换成的double，如果Map is null ,返回null
     */
    public static double getDoubleValue(final Map map, final Object key) {
        Double doubleObject = getDouble(map, key);
        if (doubleObject == null) {
            return 0d;
        }
        return doubleObject.doubleValue();
    }

    // Type safe primitive getters with default values
    //-------------------------------------------------------------------------
    /**
     * 用Null-Safe的方式从Map中获取boolean
     * 如果转换失败，使用defaultvalue
     * <p>
     *  如果值是一个<code>Boolean</code>，它就会被立即返回。
     *  如果值是一个字符串并且equals 'true' 忽略大小写，那么将会返回<code>true</code>，其他情况返回<code>false</code>
     *  如果值是一个<code>Number</code>一个，并且是一个0值，那么返回<code>true</code>，其他非0情况，返回<code>true</code>
     *  其他情况，返回<code>null</code>
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @param defaultValue  如果转换失败，或者是一个null值，那么返回该defaultValue
     * @return Map中的值转换成的boolean, 如果map为空<code>defaultValue</code>
     */
    public static boolean getBooleanValue(final Map map, final Object key, boolean defaultValue) {
        Boolean booleanObject = getBoolean(map, key);
        if (booleanObject == null) {
            return defaultValue;
        }
        return booleanObject.booleanValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取byte,
     * 如果转换失败，使用defaultvalue  
     * <p>
     * byte值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @param defaultValue  如果转换失败，或者是一个null值，那么返回该defaultValue
     * @return Map中的值转换成的byte, 如果map为空<code>defaultValue</code>
     */
    public static byte getByteValue(final Map map, final Object key, byte defaultValue) {
        Byte byteObject = getByte(map, key);
        if (byteObject == null) {
            return defaultValue;
        }
        return byteObject.byteValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取short,
     * 如果转换失败，使用defaultvalue  
     * <p>
     * short值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @param defaultValue  如果转换失败，或者是一个null值，那么返回该defaultValue
     * @return Map中的值转换成的short, 如果map为空<code>defaultValue</code>
     */
    public static short getShortValue(final Map map, final Object key, short defaultValue) {
        Short shortObject = getShort(map, key);
        if (shortObject == null) {
            return defaultValue;
        }
        return shortObject.shortValue();
    }

    /**
     *  用Null-Safe的方式从Map中获取int,
     * 如果转换失败，使用defaultvalue  
     * <p>
     * int值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @param defaultValue  如果转换失败，或者是一个null值，那么返回该defaultValue
     * @return Map中的值转换成的int, 如果map为空<code>defaultValue</code>
     */
    public static int getIntValue(final Map map, final Object key, int defaultValue) {
        Integer integerObject = getInteger(map, key);
        if (integerObject == null) {
            return defaultValue;
        }
        return integerObject.intValue();
    }

    /**
     *  用Null-Safe的方式从Map中获取long,
     * 如果转换失败，使用defaultvalue  
     * <p>
     * int值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @param defaultValue  如果转换失败，或者是一个null值，那么返回该defaultValue
     * @return Map中的值转换成的long, 如果map为空<code>defaultValue</code>
     */
    public static long getLongValue(final Map map, final Object key, long defaultValue) {
        Long longObject = getLong(map, key);
        if (longObject == null) {
            return defaultValue;
        }
        return longObject.longValue();
    }

    /**
     *  用Null-Safe的方式从Map中获取float,
     * 如果转换失败，使用defaultvalue  
     * <p>
     * float值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @param defaultValue  如果转换失败，或者是一个null值，那么返回该defaultValue
     * @return Map中的值转换成的float, 如果map为空<code>defaultValue</code>
     */
    public static float getFloatValue(final Map map, final Object key, float defaultValue) {
        Float floatObject = getFloat(map, key);
        if (floatObject == null) {
            return defaultValue;
        }
        return floatObject.floatValue();
    }

    /**
     * 用Null-Safe的方式从Map中获取double,
     * 如果转换失败，使用defaultvalue  
     * <p>
     * double值的获取参照 {@link #getNumber(Map,Object)}.
     *
     * @param map  使用的Map
     * @param key  查找的Key值
     * @param defaultValue  如果转换失败，或者是一个null值，那么返回该defaultValue
     * @return Map中的值转换成的double, 如果map为空<code>defaultValue</code>
     */
    public static double getDoubleValue(final Map map, final Object key, double defaultValue) {
        Double doubleObject = getDouble(map, key);
        if (doubleObject == null) {
            return defaultValue;
        }
        return doubleObject.doubleValue();
    }
 // Conversion methods
    //-------------------------------------------------------------------------
    /**
     * 根据Map中的值，生成一个Properties对象，如果Map为空，则返回一个空的Properties对象
     * 
     * @param map  转换成为Properties的Map，不能为空
     * @return the properties object
     */
	public static Properties toProperties(final Map map) {
        Properties answer = new Properties();
        if (map != null) {
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                answer.put(key, value);
            }
        }
        return answer;
    }
  //-----------------------------------------------------------------------
    /**
     * 反转提供的map,生成一个新的HashMap,即原map中的key值成为新map的value，value值成为新的map的key值
     * <p>
     * 这个操作设置反转的map对象是被很好的定义的
     * 如果提供的map拥有value的情况，并且映射到不同的key值，那么返回的map就会映射到这么多值的其中一个，
     * 其余的key会映射到未定义的值
     *
     * @param map  需要转置的map，最好不要是空的
     * @return 一个新的包含转置数据的map
     * @throws NullPointerException 如果map is null
     */
    @SuppressWarnings("unchecked")
	public static Map invertMap(Map map) {
        Map out = new HashMap(map.size());
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            out.put(entry.getValue(), entry.getKey());
        }
        return out;
    }
    //-----------------------------------------------------------------------
    /**
     * 防止将null值添加到map
     * <p>
     * 这个方法检测被添加到map的值，如果它是null，那么它将会被替换成为一个空字符串
     * <p>
     * 这将会对那些不接收null值的map非常有用，它可以以相同的方式对Map添加值
     * <p>
     * 
     * @param map  被添加值的map
     * @param key  key
     * @param value  value, null将会被转换成为""
     * @throws NullPointerException 如果map is null
     */
    @SuppressWarnings("unchecked")
	public static void safeAddToMap(Map map, Object key, Object value) throws NullPointerException {
        if (value == null) {
            map.put(key, "");
        } else {
            map.put(key, value);
        }
    }
}
