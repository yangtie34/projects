package com.yiyun.wasteoilcustom.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.util.AppContext;
import com.yiyun.wasteoilcustom.AppUser;
import com.yiyun.wasteoilcustom.R;

public class Menu extends AngularActivity {

    public TextView txtUserName;
    public TextView txtUserFullName;
    public TextView txtComBrName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setIndex();
        setContentView(R.layout.activity_menu);
        AppUser appUser= AppUser.getInstance();
        //网点
        txtComBrName=(TextView)findViewById(R.id.txtComBrName);
        txtComBrName.setText(appUser.getComBrName());
        //姓名
        txtUserFullName=(TextView)findViewById(R.id.txtUserFullname);
        txtUserFullName.setText(appUser.getUserFullname());
        //帐号
        txtUserName=(TextView)findViewById(R.id.txtUserName);
        txtUserName.setText(appUser.getUserName());

    }

    public void recReport(View view) {
        AppContext.intent(RecReport.class);
    }

    public void product(View view) {
        AppContext.intent(Product.class);
    }

    public void schedule(View view) {
        AppContext.intent(Schedule.class);
    }
}
