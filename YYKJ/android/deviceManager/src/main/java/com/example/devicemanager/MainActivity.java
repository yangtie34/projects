package com.example.devicemanager;

import android.app.Activity;
import android.device.DeviceManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    DeviceManager mDevice;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDevice = new DeviceManager();

        Button disableHomekey = (Button) findViewById(R.id.button1);
        disableHomekey.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDevice.enableHomeKey(false);
            }
        });
        
        Button enableHomekey = (Button) findViewById(R.id.button2);
        enableHomekey.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDevice.enableHomeKey(true);
            }
        });
        
        
        Button disableStatuBar= (Button) findViewById(R.id.button3);
        disableStatuBar.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDevice.enableStatusBar(false);
            }
        });
        
        Button enableStatuBar = (Button) findViewById(R.id.button4);
        enableStatuBar.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDevice.enableStatusBar(true);
            }
        });
        
        Button setTime = (Button) findViewById(R.id.button5);
        setTime.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                long cur = System.currentTimeMillis();
                mDevice.setCurrentTime(cur+ 60*60*1000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //mDevice.switchHomeKey(true);
        //mDevice.switchStatusBar(true);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setTitle("DeviceID:   " + mDevice.getDeviceId());
    }

}
