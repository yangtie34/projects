package com.eyunsoft.app_wasteoil.utils.PollingMsg;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import com.eyunsoft.app_wasteoil.App;
import com.eyunsoft.app_wasteoil.Publics.Convert;
import com.eyunsoft.app_wasteoil.R;
import com.eyunsoft.app_wasteoil.bll.Notice_BLL;
import com.eyunsoft.app_wasteoil.bll.TransferRecState_BLL;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class PollingService extends Service {
    public static final String ACTION = "com.eyunsoft.app_wasteoil.utils.PollingMsg.PollingService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }



    /**
     * 检测订单
     */
    public  void  CheckNewOrder()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err","0");
            jsonObject.put("ReadStatus","0");
            jsonObject.put("NoticeToComBrID", Convert.ToString(App.getInstance().getSysComBrID()));
            jsonObject.put("NoticeToUserID", Convert.ToString(App.getInstance().getCompanyUserID()));
            jsonObject.put("NoticeToComID", Convert.ToString(App.getInstance().getSysComID()));

            JSONObject jsonHeader = new JSONObject();
            jsonHeader.put("Condition", jsonObject);
            String jsonStr = jsonHeader.toString();


            list= Notice_BLL.GetNoticeList(jsonStr,0,20);
            System.out.println("查询到"+list.size()+"条数据");
            count=list.size();
            currentIndex=0;

        } catch (Exception ex) {

            list=new ArrayList<HashMap<String,Object>>();
            count=0;
            currentIndex=0;
            ex.printStackTrace();
        }

    }

    public void SendBroadCast()
    {
        if(currentIndex>=0) {
            String RecNumber=list.get(currentIndex).get("NoticeRecNumber").toString();
            String recType=list.get(currentIndex).get("NoticeType").toString();
            long noticeId=Convert.ToInt64(list.get(currentIndex).get("NoticeID").toString());
            //发送广播
            Intent intent = new Intent();
            intent.putExtra("NoticeRecNumber", RecNumber);
            intent.putExtra("RecType",recType);
            intent.putExtra("NoticeID",noticeId);
            intent.setAction(ACTION);
            sendBroadcast(intent);
        }
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count=0;//获取单据条数
    int currentIndex=0;//当前单据序号
    public List<HashMap<String,Object>> list;
    class PollingThread extends Thread {
        @Override
        public void run() {
            if(count==0||currentIndex==count)
            {
                CheckNewOrder();
            }
            else
            {
                SendBroadCast();
                currentIndex++;

            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("Service:onDestroy");
    }
}
