package com.eyun.jybfreightscan.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.DeviceManager;
import android.device.ScanManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.eyun.framework.angular.baseview.CusEditView;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.product.entity.VehicleDispathState;
import com.eyun.jybfreightscan.product.service.impl.VehicleDispathStateServiceImpl;

public class ConsignSign extends AngularActivity {

    private final static String SCAN_ACTION = "urovo.rcv.message";//扫描结束action


    private ScanManager mScanManager;
    private SoundPool soundpool = null;
    private int soundid;
    private String barcodeStr;

    private CusEditView txtBarCode;
    private CusEditView txtValidCode;

    private Button btnSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consign_sign);

        initView();
        initScan();
    }

    /**
     * 界面初始化
     */
    public void  initView()
    {
        scope.key("title").val("客户签收");
        scope.key("fullname").val(AppUser.fullName);
        scope.key("username").val(AppUser.username);

        btnSign=(Button)findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign();
            }
        });

        ShowErrTip("");

        txtBarCode=(CusEditView)findViewById(R.id.txtBarCode);
        txtBarCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode== KeyEvent.KEYCODE_ENTER)
                {
                    String code=txtBarCode.getText().toString().trim();
                    InitData(code);
                }
                return false;
            }
        });

    }

    /**
     * 签收确认
     */
    private void Sign()
    {

    }

    /**
     * 加载数据
     * @param barcode
     */
    private void InitData(String barcode)
    {

    }

    /**
     * 错误提示-货物扫描
     * @param tip
     */
    public void ShowErrTip(String tip)
    {
        scope.key("scanErrTip").val(tip);
    }

    /**
     * 清空扫描窗口
     */
    public void DelBarCode(){scope.key("scanCode").val("");}

    /**
     * 条码扫描接收广播
     */
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            soundpool.play(soundid, 1, 1, 0, 0, 1);
            scope.key("scanCode").val("");

            byte[] barocode = intent.getByteArrayExtra("barocode");
            byte[] barcode = intent.getByteArrayExtra("barcode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.i("debug", "----codetype--" + temp);
            barcodeStr = new String(barcode, 0, barocodelen);

            scope.key("scanCode").val(barcodeStr);

            InitData(barcodeStr);

        }
    };
    /**
     * 初始化扫描
     */
    private void initScan() {
        // TODO Auto-generated method stub
        mScanManager = new ScanManager();
        mScanManager.openScanner();

        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundid = soundpool.load("/etc/Scan_new.ogg", 1);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mScanManager != null) {
            mScanManager.stopDecode();
        }
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initScan();
        scope.key("scanCode").val("");

        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

}
