package com.eyun.framework.webservice;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.FastJsonTools;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/2.
 */

public class SoapObjectUtil {

    public static List<Map<String,Object>> forList(String jsonString){
            return FastJsonTools.createJsonToListMap(jsonString);
    }

    public static ResultMsg forResultMsg(String jsonString){
        return FastJsonTools.createJsonBean(jsonString,ResultMsg.class);
    }

    public static <T> List<T> forListBean(String jsonString, Class<T> cls){
        return FastJsonTools.createJsonToListBean(jsonString,cls);
    }


}
