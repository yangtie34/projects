package com.chengyi.android.util;

import com.chengyi.android.angular.core.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Object> list=new ArrayList<>();
        for (int i = 0; i < scope.size(); i++) {
            list.add(scope.key(i).val());
        }
        return list;
    };

    /**
     * scope中map数据提取出
     * @param scope
     * @return
     */
    public static Map<String,Object> toMap(Scope scope){
        Map<String,Object> map=new HashMap<>();
        String[] keyArray=scope.keyArray();
        Map<String,Object> children=new HashMap<>();
        for (int i = 0; i < keyArray.length; i++) {
            if(!isNumber(keyArray[i])){
                children.put(keyArray[i],toMap(scope.key(keyArray[i])));
            }
        }
        map.put("id",scope.id);
        map.put("value",scope.val());
        map.put("pid",scope.parent.id);
        map.put("children",children);
        return map;
    };

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
