package com.yiyun.wasteoilcustom.viewModel;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.UI.WindowPop;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.ActivityUtil;
import com.yiyun.wasteoilcustom.R;

import static com.chengyi.android.angular.core.Scope.activity;
import static com.yiyun.wasteoilcustom.R.id.back;

/**
 * Created by Administrator on 2017/2/15.
 */

public class Order {
    private WindowPop addPage;
    private TextView title;
    public Order(int id){
        LayoutInflater inflater = LayoutInflater.from(activity);
        View orderView = inflater.inflate(R.layout.filter, null);
        View order=inflater.inflate(id, null);
        LinearLayout orderLayout= (LinearLayout) orderView.findViewById(R.id.filter_conditions);
        orderLayout.addView(order);
        View back= orderView.findViewById(R.id.back);
        title= (TextView) orderView.findViewById(R.id.title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scope.activity.onBackPressed();
            }
        });
        addPage=new WindowPop(orderView, Gravity.RIGHT);
    }
    public void setTitle(String title_){
        title.setText(title_);
    }
    public WindowPop getAddPage(){
        return addPage;
    }

}
