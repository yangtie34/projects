package com.jhnu.util.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-3
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
public class Utils4Service {
    /**
     * 封装参数
     * @param params
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map packageParams(String params){
        Map param = new HashMap();
        JSONObject paramObj = JSONObject.parseObject(params);
        int start = paramObj.containsKey("start")?paramObj.getIntValue("start"):0;
        int limit = paramObj.containsKey("limit")?paramObj.getIntValue("limit"):25;
        param.put("start",start);
        param.put("limit",limit);
        return param;
    }

    /**
     * 通过baseDao.queryTableContentBySql查询出的结果。
     * @param map
     * @return
     */
	@SuppressWarnings({ "rawtypes" })
    public static String packageJsonStr(Map map){
        List list = (List)map.get("queryList");
        String resultStr ="{success:true,\"data\":" + JSONObject.toJSONString(list)
                + " ,\"count\":\"" + map.get("count") + "\"}";
        return resultStr;
    }
    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    /**
	 * 根据字段属性反射获取该字段的属性值的字符串形式。通过get方法。
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getValueByField(Object obj ,Field field){
		String fieldName = field.getName();
		String getMethodName = "get"
				+ fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		String textValue = null;
		try {
			Class tCls = obj.getClass();
			Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
			Object value = getMethod.invoke(obj, new Object[] {});
			if(value==null){
				return "";
			}
			textValue = value.toString();
		} catch (Exception e) {
		}
		return textValue;
	}
}
