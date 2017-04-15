package com.eyun.framework.angular.core;


import com.eyun.framework.angular.core.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by administrator on 2016-12-26.
 */

public class ScopeTool {
    /**
     * scope中Array数据提取为list
     * @param scope
     * @return
     */
    public static List<Object> toList(Scope scope){
        List<Object> list=(List<Object>) toObject(scope);
        return list;
    };

    /**
     * scope中map数据提取出
     * @param scope
     * @return
     */
    public static Map<String,Object> toMap(Scope scope){
        Map<String,Object> map= (Map<String, Object>) toObject(scope);
        return map;
    };

    public static Object toObject(Scope scope){
        Object o=null;
        String[] keyArray=scope.keyArray();
        if(scope.size()>0){
            List<Object> list=new ArrayList<>();
            for (int i = 0; i < scope.size(); i++) {
               Scope sc= scope.scopes.get(String.valueOf(i));
                list.add(i,toObject(sc));
            }
           o=list;
        }else if(keyArray.length>0){
            Map<String,Object> map=new HashMap<>();

            for (int i = 0; i < keyArray.length; i++) {
                if(!isNumber(keyArray[i])){
                    map.put(keyArray[i],toObject(scope.key(keyArray[i])));
                }
            }
            o=map;
        }else{
            o=scope.val();
        }
        return o;
    }


    /**
     * 将value之放入链式scope
     * @param scope
     * @return     */
    public static void valueInScope(Scope scope){
        Object value=scope.val();
        if(value instanceof List){
            //scope.val(value);
            List<Object> list= (List<Object>) value;
            for (int i = 0; i <list.size() ; i++) {
                Object o=list.get(i);
                scope.lockSync();
                scope.key(i).val(o);
                scope.openSync();
                valueInScope(scope.key(i));
            }
        }else if(value instanceof Map){
            //scope.val(null);
            Map<String,Object> map= (Map<String,Object>) value;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                scope.lockSync();
                scope.key(entry.getKey()).val(entry.getValue());
                scope.openSync();
                valueInScope(scope.key(entry.getKey()));
            }
        }
    }

    private static boolean isNumber(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
