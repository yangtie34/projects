package com.eyunsoft.app_wasteoilCostomer.Publics;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by Administrator on 2014-12-11.
 */
public class Phone {
    /**
     * 获取手机基本信息
     * @param context
     * @return
     */
    public  static  String GetPhoneInfo(Context context) {


        try {
            TelephonyManager tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            StringBuilder sb=new StringBuilder();

            sb.append("\nDeviceID(IMEI)"+tm.getDeviceId());
            sb.append("\nDeviceSoftwareVersion:"+tm.getDeviceSoftwareVersion());
            sb.append("\ngetLine1Number:"+tm.getLine1Number());
            sb.append("\nNetworkCountryIso:"+tm.getNetworkCountryIso());
            sb.append("\nNetworkOperator:"+tm.getNetworkOperator());
            sb.append("\nNetworkOperatorName:"+tm.getNetworkOperatorName());
            sb.append("\nNetworkType:"+tm.getNetworkType());
            sb.append("\nPhoneType:"+tm.getPhoneType());
            sb.append("\nSimCountryIso:"+tm.getSimCountryIso());
            sb.append("\nSimOperator:"+tm.getSimOperator());
            sb.append("\nSimOperatorName:"+tm.getSimOperatorName());
            sb.append("\nSimSerialNumber:"+tm.getSimSerialNumber());
            sb.append("\ngetSimState:"+tm.getSimState());
            sb.append("\nSubscriberId:"+tm.getSubscriberId());
            sb.append("\nVoiceMailNumber:"+tm.getVoiceMailNumber());



            System.out.println(sb.toString());

            return sb.toString();

        }
        catch (Exception ex)
        {
            System.out.println("获取异常"+ex.toString());
            return "";
        }

    }

    /**
     * 获取手机号
     * @param context
     * @return
     */
    public  static  String GetPhoneNum(Context context) {


        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String num = tm.getLine1Number();
            if (TextUtils.isEmpty(num)) {
                return "";
            } else {
                return num.replace("+86", "");
            }
        } catch (Exception ex) {
            return "";
        }
    }
}
