package com.yiyun.wasteoilcustom.activities;

import android.os.Bundle;

import com.chengyi.android.angular.core.AngularActivity;
import com.yiyun.wasteoilcustom.R;

public class Menu extends AngularActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setIndex();
        setContentView(R.layout.activity_main);
    }
}
