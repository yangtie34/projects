package com.eyun.framework.util.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.eyun.configure.ServerConfig;
import com.eyun.framework.angular.core.Scope;
import com.eyun.jybfreightscan.R;

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
 * 跟App相关的辅助类
 * 
 * @author zhy
 * 
 */
public class AppUtils
{

	private AppUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(){
		Context context= Scope.activity;
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName() {
		Context context= Scope.activity;
		try{
			//获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			//getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	//外部接口让主Activity调用
	public static void checkUpdateInfo(){

		boolean isUpdate=false;
		try{
			String currentVersion = getVersionName();//获取版本
			UpdateInfo updateInfo=getUpdateInfo();
			if(updateInfo!=null&&updateInfo.isExists){
				/*String updateMsg=updateInfo.description;

				fileName=updateInfo.url;*/
				if(!updateInfo.version.equals(currentVersion))
				{
					showNoticeDialog(updateInfo);
				}
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}


	//获取自动更新内容
	private static UpdateInfo getUpdateInfo() throws Exception {
		UpdateInfo updateInfo = new UpdateInfo();

		URL url = new URL(ServerConfig.Update.APK_XML);


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

	/**
	 * 展示有版本更新窗口
	 */
	private static void showNoticeDialog(final UpdateInfo updateInfo){
		AlertDialog.Builder builder = new AlertDialog.Builder(Scope.activity);
		builder.setTitle("软件版本更新");
		builder.setMessage(updateInfo.description.replace("|","\n"));
		builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog(updateInfo);
			}
		});
		builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 展示版本更新中窗口
	 */
	private static boolean interceptFlag=false;
	private static  void showDownloadDialog(final UpdateInfo updateInfo){
		AlertDialog.Builder builder = new AlertDialog.Builder(Scope.activity);
		builder.setTitle("软件版本更新");

		final LayoutInflater inflater = LayoutInflater.from(Scope.activity);
		View v = inflater.inflate(R.layout.base_update_progress, null);
		//进度条
		final ProgressBar mProgress = (ProgressBar)v.findViewById(R.id.progress);
		builder.setView(v);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		Dialog downloadDialog = builder.create();
		downloadDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				downloadApk(updateInfo,mProgress);
			}
		}).start();
	}
	/**
	 * 下载apk
	 * @param
	 */

	private static void downloadApk(UpdateInfo updateInfo, final ProgressBar mProgress){
		try {
			URL url = new URL(ServerConfig.Update.APK_URL+updateInfo.url);

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();

			File file = new File(ServerConfig.Update.PATH_SAVE);
			if(!file.exists()){
				file.mkdir();
			}
			File ApkFile = new File(ServerConfig.Update.FILE_SAVE);
			FileOutputStream fos = new FileOutputStream(ApkFile);

			int count = 0;
			byte buf[] = new byte[1024];

			final int DOWN_UPDATE = 1;
			final int DOWN_OVER = 2;
			int progress;
			do{
				int numread = is.read(buf);
				count += numread;
				progress =(int)(((float)count / length) * 100);
				//更新进度
				mProgress.setProgress(progress);
				if(numread <= 0){
					//下载完成通知安装
					installApk();
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
	/**
	 * 安装apk
	 * @param
	 */
	private static void installApk(){
		File apkfile = new File(ServerConfig.Update.PATH_SAVE);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		Scope.activity.startActivity(i);

	}
	 static class UpdateInfo
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
