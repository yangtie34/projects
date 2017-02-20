package com.eyunsoft.app_wasteoilCostomer.Publics;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2014-12-11.
 */
public class RWProperties {
    /**
     * 读取配置文件
     * @param context
     * @param file文件路径
     * @return
     */
    public Properties loadConfig(Context context, String file) {
        Properties properties = new Properties();
        try {
            File file1=new File(file);
            if(!file1.exists())
                return null;
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    /**
     * 保存配置文件
     * @param context
     * @param path文件路径
     * @param file文件名称
     * @param properties
     * @return
     */
    public boolean saveConfig(Context context, String path, String file, Properties properties) {
        try {
            createDirection(path);
            File fil = new File(path + file);
            if (!fil.exists())
                fil.createNewFile();
            FileOutputStream s = new FileOutputStream(fil);
            properties.store(s, "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建目录
     * @param path
     */
    public void createDirection(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println(path);
                file.mkdirs();
            }
        } catch (Exception e) {
            System.out.println("创建目录失败：" + e.toString());
        }
    }

    /**
     * 重置配置文件
     * @param file
     */
    public static void resetConfig(String file) {

        File fil = new File(file);
        if (fil.exists())
            fil.delete();


    }

}
