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
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.support.SoundSupport;


public class Menu extends AngularActivity {
    ThreadUtil threadUtil=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setIndex();
        setContentView(R.layout.activity_menu);
        scope.key("fullname").val(AppUser.fullName);
        scope.key("username").val(AppUser.username);
        threadUtil= ThreadUtil.getLoopThread(new CallBack() {
            @Override
            public Object run() {
                final boolean net= NetWorkUtil.IsNetWorkEnable();
                final boolean db= DBManager.checkConnection();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reflushNetAndDB(net,db);
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
    private void reflushNetAndDB(boolean net,boolean db){
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
        }else{
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
        }else{
            scope.key("dbConnection").val("正常");
        }
    }
    //入库分拣
    public void inRecScan(View view) {
        AppContext.intent(InRecScan.class);
    }
    //分拣入库
    public void scanInRec(View view) {
        AppContext.intent(ScanInRec.class);
    }
    //入库单入仓
    public void inStorageScan(View view) {
        AppContext.intent(InStorageScan.class);
    }
    //扫描出仓生成出库单
    public void scanOutRec(View view) {
        AppContext.intent(ScanOutRec.class);
    }

    //出库单出仓
    public void outStorageScan(View view) {
        AppContext.intent(OutStorageScan.class);
    }

    //托运单扫描
    public void consignScan(View view) {
        AppContext.intent(ConsignScanA.class);
    }
/*    //出仓后出库单分拣
    public void outRecScan(View view) {
        AppContext.intent(OutRecScan.class);
    }*/

    //仓库盘点
    public void storageTake(View view) {
        AppContext.intent(StorageTake.class);
    }

    public void logout(View view) {
        AppContext.intent(Login.class);
    }
    //车辆调度
    public void vehicleDispath(View view) {
        AppContext.intent(VehicleDispathA.class);
    }
}
