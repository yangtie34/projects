package com.eyunsoft.app_wasteoilCostomer.Publics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/12.
 */
public class DownLoad {

    private Context mContext;
    //更新文件
    private String fileName="splash.png";

    //返回的更新提示
    private String serverUrl="";

    /* 下载包安装路径 */
    private   String savePath = "/sdcard/splash_jyunb/";

    private boolean interceptFlag = false;

    private Thread downLoadThread;

    public   DownLoad(Context context,String server,String clientPath,String clientFileName)
    {
        mContext=context;
        serverUrl=server;
        savePath=clientPath;
        fileName=clientFileName;


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Thread thread=new Thread(downLoadRunnable);
        thread.start();
    }


    private Runnable downLoadRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(serverUrl);
                System.out.println("拿到的地址为：" + serverUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.connect();
                int length = 0;
                InputStream is = null;
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    length = conn.getContentLength();
                    is = conn.getInputStream();

                }

                System.out.println(savePath+ fileName);
                System.out.println(serverUrl);

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String downFile = savePath + fileName;
                System.out.println(downFile);
                File DownFile = new File(downFile);
                FileOutputStream fos = new FileOutputStream(DownFile);

                // bitmap;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                BufferedOutputStream bos = null;
                File dirFile = new File(savePath);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
                File myCaptureFile = new File(savePath+ fileName);
                if (!myCaptureFile.exists()) {
                    myCaptureFile.createNewFile();
                }




                bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                System.out.println("下载完成==========================");
                bos.flush();
                bos.close();
                is.close();
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {

            e.printStackTrace();
        }
     }
    };

}
