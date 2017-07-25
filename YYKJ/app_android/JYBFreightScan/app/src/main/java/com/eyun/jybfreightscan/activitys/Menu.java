package com.eyun.jybfreightscan.activitys;

import android.os.Bundle;
import android.view.View;

import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.AppContext;
import com.eyun.framework.jdbc.jdbcUtil.DBManager;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.ThreadUtil;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.support.SoundSupport;


public class Menu extends AngularActivity {
    ThreadUtil threadUtil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setIndex();
        setContentView(R.layout.activity_menu);
        scope.key("fullname").val(AppUser.fullName);
        scope.key("username").val(AppUser.username);
        threadUtil = ThreadUtil.getLoopThread(new CallBack() {
            @Override
            public Object run() {
                final boolean net = NetWorkUtil.IsNetWorkEnable();
                final boolean db = DBManager.checkConnection();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reflushNetAndDB(net, db);
                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        threadUtil.start();
        scope.key("modelName").val(ServerInfo.jdbcEntity.getModelName());
    }

    private void reflushNetAndDB(boolean net, boolean db) {
        if (!net) {
            scope.key("netConnection").val("断开");
            SoundSupport.play(R.raw.net_close);
            ViewUtil.confirm("警告", "网络连接失败！请连接网络！", new CallBack() {
                @Override
                public Object run() {
                    threadUtil.resume();
                    return null;
                }
            }, null);
            threadUtil.suspend();
        } else {
            scope.key("netConnection").val("通畅");
        }
        if (!db) {
            scope.key("dbConnection").val("失败");
            SoundSupport.play(R.raw.db_close);
            ViewUtil.confirm("警告", "数据库连接失败！请检测数据库！", new CallBack() {
                @Override
                public Object run() {
                    threadUtil.resume();
                    return null;
                }
            }, null);
            threadUtil.suspend();
        } else {
            scope.key("dbConnection").val("正常");
        }
    }

    //装车点击事件
    public void LoadScan(View view) {

        if (view.getTag() != null) {
            ConsignScan.recType= TypeConvert.toInteger(view.getTag().toString());
            AppContext.intent(ConsignScan.class);
        }
    }
    //客户签收点击事件
    public void SignScan(View view) {
            AppContext.intent(ConsignSign.class);
    }

    //注销
    public void logout(View view) {
        AppContext.intent(Login.class);
    }

}
