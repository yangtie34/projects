package com.eyunsoft.app_wasteoil.Publics;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2017/1/7.
 */

public class TitleSet {
    /**
     * 设置标题
     * @param activity
     * @param moduleNo
     */
    public static void SetTitle(Activity activity, int moduleNo)
    {
        String title="危险废物监管系统";
        switch (moduleNo)
        {
            case 0:
                title+="";
                break;
            case 1:
                title+="-生产单";
                break;
            case 11:
                title+="-生产单添加";
                break;
            case 12:
                title+="-生产单修改";
                break;
            case 2:
                title+="-危运联单";
                break;
            case 21:
                title+="-危运联单添加";
                break;
            case 22:
                title+="-危运联单修改";
                break;
            case 23:
                title+="-危运联单详情";
                break;
            case 4:
                title+="-调度配送";
                break;
            case 41:
                title+="-调度配送明细";
                break;
            case 3:
                title+="-联单抢运";
                break;
            case 5:
                title+="-登录";
                break;
            case 6:
                title+="-系统通知";
                break;
            case 61:
                title+="-通知详情";
                break;


        }
        activity.setTitle(title);
    }
}
