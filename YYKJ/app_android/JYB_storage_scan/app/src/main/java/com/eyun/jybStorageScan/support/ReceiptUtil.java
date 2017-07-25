package com.eyun.jybStorageScan.support;


import com.eyun.jybStorageScan.AppUser;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/18.
 */

public class ReceiptUtil {

    public static String getReceiptID(){
        String sign="0"+ AppUser.ReceiptSign;
        String dateStr=new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date() );
        int max=99;
        int min=10;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return sign+dateStr+s;
    }
}
