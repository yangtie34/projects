package com.eyunsoft.app_wasteoilCostomer.Publics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 版本管理 2016.10.11
 * .
 */
public class Version {

    public static String getCurrentVersion(Context mContext) {
        try {
            //获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packInfo.versionName;
        } catch (Exception ex) {
            System.out.println("获取版本号异常:" + ex.toString());
            return "未知";
        }

    }

    public static boolean isExists(Context context, String src) {
        File file = new File(src);
        if (file.exists()) {
            System.out.println("文件存在"+src);
            return true;
        }
        else {
            System.out.println("文件不存在"+src);
            return false;
        }
    }


    /**
     * 解压Assets中的文件
     *
     * @param context上下文对象
     * @param assetName压缩包文件名
     * @param outputDirectory输出目录
     * @throws java.io.IOException
     */


    public static boolean unZip(Context context, String assetName, String outputDirectory) {

        try {
            System.out.println(outputDirectory);
            //创建解压目标目录
            File dir = new File(outputDirectory);
            //如果目标目录不存在，则创建
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file=new File(outputDirectory+"/"+assetName);



            String[] strings = context.getAssets().list("");
            for (int i = 0; i < strings.length; i++) {
                System.out.println("Assets文件" + strings[i]);
            }

            InputStream is = context.getAssets().open(assetName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            while (true) {
                count++;
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
            return true;
        } catch (Exception ex) {

            ex.printStackTrace();
            System.out.println("异常");
            return false;
        }
    }




}
