package com.yiyun.wasteoilcustom.util;

import com.chengyi.android.angular.entity.TreeEntity;
import com.yiyun.wasteoilcustom.viewModel.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/17.
 */

public class ConditionUtil {

    public static Condition.DataItem getConditionDate(String code, String name){
        Condition.DataItem date=new Condition.DataItem(Condition.DataItem.Type.date);
        date.setCode(code);
        date.setName(name);
        return date;
    }
    public static Condition.DataItem getConditionList(String code, String name, List<TreeEntity> list){
        Condition.DataItem date=new Condition.DataItem(Condition.DataItem.Type.list);
        date.setCode(code);
        date.setName(name);
        date.setData(list);
        return date;
    }
}
