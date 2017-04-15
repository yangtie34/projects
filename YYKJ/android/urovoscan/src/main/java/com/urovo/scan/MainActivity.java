package com.urovo.scan;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private final static String SCAN_ACTION = "urovo.rcv.message";//扫描结束action
    
    private EditText showScanResult;
    private Button btn;
    private Button mScan;
    private Button mClose;
    private int type;
    private int outPut;
    
    private ScanManager mScanManager;
    private SoundPool soundpool = null;
    private int soundid;
    private String barcodeStr;
    private boolean scanPowerState;
    private boolean lockTrigglerState;
    private boolean isScaning = false;
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            isScaning = false;
            //soundpool.play(soundid, 1, 1, 0, 0, 1);
            showScanResult.setText("");

            byte[] barocode = intent.getByteArrayExtra("barocode");
            byte[] barcode = intent.getByteArrayExtra("barcode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.i("debug", "----codetype--" + temp);
            barcodeStr = new String(barcode, 0, barocodelen);

            showScanResult.setText(barcodeStr);

        }

    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        setupView();
        
    }

    private void initScan() {
        // TODO Auto-generated method stub
        mScanManager = new ScanManager();
        mScanManager.openScanner(); 
       /* type = mScanManager.getScannerType();
        //outPut = mScanManager.getOutputMode();
        switch(type) {
            case 1:
                setTitle(R.string.opticol);
                break;
            case 2:
                setTitle(R.string.symbol);
                break;
                
            case 3:
                setTitle(R.string.honyware);
                mScan.setVisibility(View.VISIBLE);
                mClose.setVisibility(View.VISIBLE);
                break;
                
            case 4:
                setTitle(R.string.se4500);
                break;
        }*/
        /*scanPowerState =  mScanManager.getScannerState(); 
        if (!scanPowerState) {
            mScanManager.openScanner(); 
        }
        lockTrigglerState = mScanManager.getTriggerLockState(); 
        if (lockTrigglerState) {
            mScanManager.unlockTriggler(); 
        }*/
        //mScanManager.setOutputParameter(3, 1);
        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundid = soundpool.load("/etc/Scan_new.ogg", 1);
    }

    private void setupView() {
        // TODO Auto-generated method stub
        showScanResult = (EditText) findViewById(R.id.scan_result);
        btn = (Button) findViewById(R.id.manager);
        btn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this,ScannerSettings.class);
                startActivity(intent);
            }
        });
        
        mScan = (Button) findViewById(R.id.scan);
        mScan.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //if(type == 3)
                    mScanManager.stopDecode();
                    isScaning = true;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mScanManager.startDecode();
            }
        });
        
        mClose = (Button) findViewById(R.id.close);
        mClose.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(isScaning) {
                    isScaning = false;
                    mScanManager.stopDecode();
                } 
            }
        });
        
        mScan.setVisibility(View.GONE);
        mClose.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        
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
        if(mScanManager != null) {
            mScanManager.stopDecode();
            isScaning = false;
        }
       /* if (mScanManager != null && lockTrigglerState) {
            mScanManager.lockTriggler();
        }
    
        if (mScanManager != null && !scanPowerState) {
            mScanManager.closeScanner();
        }*/
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initScan();
        showScanResult.setText("");
        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        
        if(keyCode == 120 && type == 4) {
            mScanManager.startDecode();
            isScaning = true;
            return true;
        } 
            
        return super.onKeyDown(keyCode, event);
    }

}
