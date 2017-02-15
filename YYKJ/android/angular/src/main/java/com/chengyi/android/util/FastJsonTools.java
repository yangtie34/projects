package com.chengyi.android.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;


public class FastJsonTools {

    public FastJsonTools() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /** 转换成json格式的字符串
     * @param object 要转换的对象
     * @return
     */
    public static String createJsonString(Object object) {
        String jsonString = JSON.toJSONString(object);
        return jsonString;
    }

    /**将json字符串转换为指定的bean对象
     * @param jsonString
     * @param cls  要转换为对象的类型
     * @return
     */
    public static <T> T createJsonBean(String jsonString, Class<T> cls) {
        T t = JSON.parseObject(jsonString, cls);
        return t;
    }

    /**将json字符串转换为List<T>
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> createJsonToListBean(String jsonString,
                                                   Class<T> cls) {
        List<T> list = null;
        list = JSON.parseArray(jsonString, cls);
        return list;
    }

    /**将json字符串转换为List<Map<String,Object>>
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> createJsonToListMap(
            String jsonString) {
        List<Map<String, Object>> list2 = JSON.parseObject(jsonString,
                new TypeReference<List<Map<String, Object>>>() {
                });
        return list2;
    }

    /**将json字符串转换为List<String>
     * @param jsonString
     * @return
     */
    public static List<String> createJsonToListString(String jsonString) {
        List<String> list2 = JSON.parseObject(jsonString,
                new TypeReference<List<String>>() {
                });
        return list2;
    }



}
