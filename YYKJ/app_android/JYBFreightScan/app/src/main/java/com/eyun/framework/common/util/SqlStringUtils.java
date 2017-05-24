package com.eyun.framework.common.util;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/7.
 */

public class SqlStringUtils {

    /**
     * 将文本转换成适合在Sql语句里使用的字符串。
     * @param pStr
     * @return
     */
    public static String GetQuotedString(String pStr)
    {
        if(pStr==null)
            return ("''");
        return ("'" + pStr.replace("'", "''") + "'");
    }

    /**
     *  根据HashMap,构造SQL语句中的Insert子句
     * @param tableName
     * @param ht
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
     * 根据HashMap,构造SQL语句中的Insert子句
     * @param tableName
     * @param ht
     * @param where
     * @return
     */
    public static String GetConstructionUpdate(String tableName, HashMap<String, Object> ht, String where) {
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
}
