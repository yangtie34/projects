package com.eyunsoft.app_wasteoilCostomer.Publics;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;


import com.eyunsoft.app_wasteoil.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2014-12-11.
 */
public class UpdateManager {

    private Context mContext;

    //提示语
    private String updateMsg = "有最新的软件包哦，亲快下载吧~";

    //返回的安装包url
    private String apkUrl = "http://update.eyunsoft.cn/App_WasteOil_customer/";

    //更新文件
    private String fileName="com.eyunsoft.app.app_wasteoil_customer.apk";

    //返回的更新提示
    private String apkXml="http://update.eyunsoft.cn/App_WasteOil_customer/UpdateInfo.xml";

    //提示窗口
    private Dialog noticeDialog;

    private Dialog downloadDialog;

    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/updatedemo/";

    private static final String saveFileName = savePath + "com.eyunsoft.app.app_wasteoil_customer.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;


    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;

    private boolean interceptFlag = false;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:

                    installApk();
                    break;
                default:
                    break;
            }
        };
    };



    public UpdateManager(Context context) {
        this.mContext = context;

    }

    //获取当前版本
    public String getCurrentVersion()  {
        try {
            //获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            System.out.println("当前版本为" + packInfo.versionName);
            return packInfo.versionName;

           // return   mContext.getString(R.string.app_version);
        }
        catch (Exception ex)
        {
            System.out.println("获取异常" + ex.toString());
            ex.printStackTrace();
            return "未知";
        }
    }

    //获取自动更新内容
    public UpdateInfo getUpdateInfo() throws Exception {
        UpdateInfo updateInfo = new UpdateInfo();

        URL url = new URL(apkXml);


        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);

            conn.setRequestMethod("GET");

            InputStream inStream = conn.getInputStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //创建DocumentBuilder，DocumentBuilder将实际进行解析以创建Document对象
            DocumentBuilder builder = factory.newDocumentBuilder();
            //解析该文件以创建Document对象
            Document document = builder.parse(inStream);
            //获取XML文件根节点
            Element root = document.getDocumentElement();
            //获得所有子节点
            NodeList childNodes = root.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = (Node) childNodes.item(i);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) childNode;
                    //版本号
                    if ("version".equals(childElement.getNodeName())) {
                        updateInfo.version = childElement.getFirstChild().getNodeValue();
                        updateInfo.isExists = true;
                        //软件名称
                    } else if ("description".equals(childElement.getNodeName())) {
                        updateInfo.description = childElement.getFirstChild().getNodeValue();
                        updateInfo.isExists = true;
                        //下载地址
                    } else if ("url".equals(childElement.getNodeName())) {
                        updateInfo.url = childElement.getFirstChild().getNodeValue();
                        updateInfo.isExists = true;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return updateInfo;

    }


    //外部接口让主Activity调用
    public boolean checkUpdateInfo(){

        boolean isUpdate=false;
        try
        {
            String currentVersion = getCurrentVersion();
            System.out.println(currentVersion);
            UpdateInfo updateInfo=getUpdateInfo();
            if(updateInfo!=null&&updateInfo.isExists)
            {
                updateMsg=updateInfo.description;

                fileName=updateInfo.url;
                if(!updateInfo.version.equals(currentVersion))
                {
                    isUpdate=true;
                    showNoticeDialog();
                }
            }

        }
        catch (Exception ex)
        {
            isUpdate=false;
        }

        return isUpdate;


    }


    private void showNoticeDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");
        updateMsg=updateMsg.replace("|","\n");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();


            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);

        mProgress = (ProgressBar)v.findViewById(R.id.progress);

        builder.setView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();

        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl+fileName);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do{
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     * @param url
     */

    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }
    /**
     * 安装apk
     * @param url
     */
    private void installApk(){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);

    }

    public class UpdateInfo
    {
        public String version="";
        public String description="";
        public String url="";
        public boolean isExists=false;
        public UpdateInfo()
        {

        }
    }
}
