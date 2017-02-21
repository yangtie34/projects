package com.eyunsoft.app_wasteoil.Publics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2014-12-11.
 */
public class SqlString {
    /**
     * 返回带单引号的字符串
     * @param ss
     * @return
     */
    public static String GetQuoteString(Object ss) {
        if (ss == null)
            return "''";

        return "'" + String.valueOf(ss).trim() + "'";
    }

    /**
     * 转化日期为字符串
     * @param date日志
     * @param format格式
     * @return
     */
    public static String GetSimpleDate(Date date,String format)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return  simpleDateFormat.format(date);
    }

    /**
     * 转化字符串为日期
     * @param strDate
     * @return
     */
    public static Date ConverToDate(String strDate) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(strDate);
        }
        catch (Exception ex)
        {
            return  new Date();
        }

    }


    /**
     * 根据哈希表,构造SQL语句中的Insert子句
     * @param tableName表名
     * @param hthashtable表
     * @return
     */
    public static String GetConstructionInsert(String tableName, HashMap<String, Object> ht) {
        int Count = 0;

        if (ht.size() <= 0) {
            return "";
        }

        String Fields = " (";
        String Values = " values(";

        for (String key : ht.keySet()) {
            if (Count != 0) {
                Fields += ",";
                Values += ",";
            }
            Fields += key;
            Values += ht.get(key);

            Count++;

        }

        Fields += ")";
        Values += ")";

        String SqlString = "insert into " + tableName + Fields + Values;
        return SqlString;
    }

    /**
     * 根据哈希表,构造SQL语句中的Insert子句
     * @param tableName表名
     * @param ht
     * @param where条件
     * @return
     */
    public static String GetConstructionUpdate(String tableName, HashMap<String, Object> ht,String where) {
        int Count = 0;

        if (ht.size() <= 0) {
            return "";
        }

        String Fields = "";

        for (String key : ht.keySet()) {
            if (Count != 0) {
                Fields += ",";
            }
            Fields += key+"="+ht.get(key);

            Count++;

        }

        String SqlString = "update " + tableName +" Set " + Fields +where;
        return SqlString;
    }
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };

    public static String toHexString(byte[] b) {
        //String to  byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }


    /// <summary>
    /// 将布尔值转换成16位有符号整数类型，适用于SQL Server
    /// </summary>
    /// <param name="pStr">要转换的布尔值</param>
    /// <returns>转换后的数字</returns>
    public static int GetQuotedBool(boolean pStr)
    {
        if(pStr)
            return 1;
        return 0;
    }

    //MD5加密
    public static String getMD5(String str, String encoding) throws Exception {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();

            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }



}
