package com.eyun.customXghScan.activitys;

import android.os.Bundle;
import android.view.View;

import com.eyun.customXghScan.R;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.AppContext;


public class Menu extends AngularActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setIndex();
        setContentView(R.layout.activity_menu);
    }

    public void logout(View view) {
        AppContext.intent(Login.class);
    }

    public void inScan(View view) {
    }
}
