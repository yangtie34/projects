package com.eyunsoft.app_wasteoil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.eyunsoft.app_wasteoil.Model.SysNoticeRead_Model;
import com.eyunsoft.app_wasteoil.Publics.Convert;
import com.eyunsoft.app_wasteoil.Publics.MsgBox;
import com.eyunsoft.app_wasteoil.Publics.NetWork;
import com.eyunsoft.app_wasteoil.Publics.TitleSet;
import com.eyunsoft.app_wasteoil.bll.Notice_BLL;
import com.eyunsoft.app_wasteoil.utils.CircleLayOut.CircleMenuLayout;
import com.eyunsoft.app_wasteoil.utils.PollingMsg.PollingService;
import com.eyunsoft.app_wasteoil.utils.PollingMsg.PollingUtils;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class CircleActivity extends AppCompatActivity {

    private long exitTime = 0;//退出次数

    private Switch recStatus;

    //登录信息
    public TextView txtUserName;
    public TextView txtUserFullName;
    public TextView txtComBrName;

    private MyReceiver receiver = null;


    private boolean isRunning = false;


    //声明消息管理器
    NotificationManager mNotifyManager = null;

    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[]{"生产单", "转运联单", "联单发布","调度配送", "通知管理", "系统设置"};
    private int[] mItemImgs = new int[]{R.drawable.prod,
           R.drawable.report, R.drawable.qiangdan,
            R.drawable.dis, R.drawable.notice, R.drawable.set};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);


        TitleSet.SetTitle(this,0);

        if(((App)getApplication()).getSysComID()==-1)
        {
            Intent login=new Intent(this,Login.class);
            startActivity(login);
        }

        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PollingService.ACTION);
        CircleActivity.this.registerReceiver(receiver, filter);

        //网点
        txtComBrName = (TextView) findViewById(R.id.txtComBrName);
        txtComBrName.setText(((App) getApplication()).getComBrName());

        //姓名
        txtUserFullName = (TextView) findViewById(R.id.txtUserFullname);
        txtUserFullName.setText(((App) getApplication()).getUserFullname());

        //帐号
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserName.setText(((App) getApplication()).getUserName());

        recStatus = (Switch) findViewById(R.id.receiveStatus);
        recStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)//
                {
                    PollingUtils.stopPollingService(CircleActivity.this, PollingService.class, PollingService.ACTION);
                    isRunning = false;
                } else {
                    PollingUtils.startPollingService(CircleActivity.this, 5, PollingService.class, PollingService.ACTION);
                    isRunning = true;
                }
            }
        });

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {

            @Override
            public void itemClick(View view, int pos) {
                if (!NetWork.isNetworkAvailable(CircleActivity.this)) {
                    MsgBox.Show(CircleActivity.this, "网络不可用，请稍后再试");
                    return;
                }

                switch (pos) {
                    case 0:
                        Intent myIntentProd = new Intent(CircleActivity.this, ProdRec.class);
                        startActivity(myIntentProd);
                        break;
                    case 1:
                        Intent myIntentProdReport = new Intent(CircleActivity.this, ProdRecReport.class);
                        startActivity(myIntentProdReport);
                        break;
                    case 2:
                        Intent myIntentTransferRecstate = new Intent(CircleActivity.this, TransferRecstate.class);
                        startActivity(myIntentTransferRecstate);
                        break;
                    case 3:
                        Intent myIntentVehDisp = new Intent(CircleActivity.this, VehDisp.class);
                        startActivity(myIntentVehDisp);
                        break;
                    case 4:
                        Intent myIntentNoticeList = new Intent(CircleActivity.this, NoticeList.class);
                        startActivity(myIntentNoticeList);
                        break;
                    case 5:
                        Intent myIntentSet = new Intent(CircleActivity.this, NoticeList.class);
                        startActivity(myIntentSet);
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(CircleActivity.this,
                        "you can do something just like ccb  ",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                if (isRunning) {
                    PollingUtils.stopPollingService(CircleActivity.this, PollingService.class, PollingService.ACTION);
                }
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取广播数据
     *
     * @author jiqinlin
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            /*final String recNumber = bundle.getString("RecNumber").toString();

            if (!TextUtils.isEmpty(recNumber)) {

                TransferRec_Model model= TransRec_BLL.TransRec_LoadData(recNumber);
                String msg="转运物品："+model.getProCategoryName()+model.getDistNumber()+model.getProMeasureUnitName()
                        +"\n转运时间："+model.getTransferTime().replace("T"," ")
                        +"\n转移地址："+model.getTransferAddress();

                if(readMsg!=null)
                {
                    readMsg.speak("有新的联单通过审核");
                }

                Intent myIntent=new Intent(MainActivity.this,TransferrecInfo.class);
                Bundle mBundle=new Bundle();
                myIntent.putExtra("RecNumber", recNumber);
                SendNotice(0,"有新的联单通过审核",myIntent,msg,mBundle,false,false);
            }*/
            String noticeNumber = bundle.getString("NoticeRecNumber");
            int recType = Convert.ToInt32(bundle.getString("RecType"));
            long noticeId = bundle.getLong("NoticeID");
            System.out.println("noticeNumber:" + noticeNumber);

            long comId = ((App) getApplication()).getSysComID();
            long userId = ((App) getApplication()).getCompanyUserID();
            long comBrId = ((App) getApplication()).getSysComBrID();

            SysNoticeRead_Model model = new SysNoticeRead_Model();
            model.setCreateUserID(userId);
            model.setCreateComBrID(comBrId);
            model.setCreateComID(comId);

            model.setNoticeID(noticeId);

            Notice_BLL.ReadNotice(model);

            if (recType == 0)//普通
            {
                String msg = "您有一条新的通知，点击查持详细!";
                Intent myIntent = new Intent(CircleActivity.this, NoticeDetail.class);
                myIntent.putExtra("NoticeId", noticeId);
                SendNotice(noticeId, "您有一条新的通知，点击查持详细", myIntent, msg, false, false);
            } else if (recType == 1)//转运联单
            {
                String msg = "点击查看明细!";
                Intent myIntent = new Intent(CircleActivity.this, TransferrecInfo.class);

                myIntent.putExtra("RecNumber", noticeNumber);
                SendNotice(noticeId, "有新的联单通过审核", myIntent, msg, false, false);
            } else if (recType == 2)//调度
            {
                String msg = "点击查看明细!";
                Intent myIntent = new Intent(CircleActivity.this, VehdispInfo.class);
                myIntent.putExtra("RecNo", noticeNumber);
                SendNotice(noticeId, "有新的调度信息", myIntent, msg, false, false);
            }
        }
    }

    public void SendNotice(long requestCode, String title, Intent mIntent, String content, boolean isCover, boolean isRepeat) {
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentTitle(title);
        builder.setContentText(content);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//这行代码会解决此问题
        mIntent.addFlags(Intent.FILL_IN_DATA);
        //  builder.addExtras(bundle);
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyManager.notify((int) requestCode, builder.build());

      /*  if(!isRepeat) {
            StatusBarNotification[] notifyList = mNotifyManager.getActiveNotifications();
            if (notifyList.length > 0) {
                for (int i = 0; i < notifyList.length; i++) {
                    if (notifyList[i].getId() == requestCode) {
                        if(isCover)
                        {
                            mNotifyManager.cancel(requestCode);
                            mNotifyManager.notify(requestCode, builder.build());
                        }
                        return;
                    }
                }
            }
        }
        else
        {

        }*/

    }

}


