package com.chengyi.android.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2014-12-11.
 */
public class Convert {


    /**
     * 转化为长整形
     * @param str 字符串
     * @return
     */
    public static long ToInt64(String str)
    {

        if(TextUtils.isEmpty(str))
        {
            return 0;
        }
        try
        {
            return Long.parseLong(str);
        }
        catch (Exception e)
        {
            System.out.println("转换失败"+e.toString());
            return 0;
        }
    }

    /**
     * 转化为整形
     * @param str 字符串
     * @return
     */
    public static int ToInt32(Object str)
    {

        System.out.println("拿到的值为："+str);
        if(TextUtils.isEmpty(str.toString()))
        {
            return 0;
        }
        try
        {

            return Integer.parseInt(str.toString());
        }
        catch (Exception e)
        {
            System.out.println("转换失败"+e.toString());
            return 0;
        }
    }

    /**
     * 转化为字符串
     * @param str 字符串
     * @return
     */
    public static String ToString(Object str)
    {
        if(str==null)return "";

        System.out.println("拿到的值为："+str);
        if(TextUtils.isEmpty(str.toString()))
        {
            return "";
        }
        try
        {

            return str.toString();
        }
        catch (Exception e)
        {
            System.out.println("转换失败"+e.toString());
            return "";
        }
    }

    /**
     * 转化为浮点形
     * @param str 字符串
     * @return
     */
    public static double ToDouble(String str)
    {

        if(TextUtils.isEmpty(str))
        {
            return 0;
        }
        try
        {
            return Double.parseDouble(str);
        }
        catch (Exception e)
        {
            System.out.println("转换失败"+e.toString());
            return 0;
        }
    }

    /**
     * 转化为bool
     * @param str 字符串
     * @return
     */
    public static boolean ToBoolean(String str)
    {
        if(TextUtils.isEmpty(str))
        {
            return false;
        }
        try
        {
            return Boolean.parseBoolean(str);
        }
        catch (Exception e)
        {
            System.out.println("转换失败"+e.toString());
            return false;
        }
    }

    /**
     * 转化为日期
     * @param str 字符串
     * @return
     */
    public static Date ToDate(String str)
    {
        if(TextUtils.isEmpty(str))
        {
            Date curDate = new Date(System.currentTimeMillis());
            return curDate;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(str);

        }
        catch (Exception e)
        {
            Date curDate = new Date(System.currentTimeMillis());
            return curDate;
        }

    }
}
