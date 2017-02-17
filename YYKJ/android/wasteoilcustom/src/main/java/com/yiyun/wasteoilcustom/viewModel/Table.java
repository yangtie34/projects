package com.yiyun.wasteoilcustom.viewModel;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.UI.WindowPop;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.CSS;
import com.yiyun.wasteoilcustom.R;

import static com.chengyi.android.angular.core.Scope.activity;
import static com.yiyun.wasteoilcustom.R.id.table;

/**
 * Created by Administrator on 2017/2/15.
 */

public class Table {
    private View tableView;
    private View back;
    private View title;
    private View filterButton;
    private WindowPop filterPage;
    private View addButton;
    private WindowPop addPage;
    private View tableList;
    public Table(){
        LayoutInflater inflater = LayoutInflater.from(activity);
        tableView = inflater.inflate(R.layout.activity_order_list, null);
        ActivityUtil.getRootView().addView(tableView);
        back= activity.findViewById(R.id.back);
        title= activity.findViewById(R.id.title);
        filterButton= activity.findViewById(R.id.filter);
        addButton= activity.findViewById(R.id.add);
        tableList=activity.findViewById(R.id.table);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scope.activity.onBackPressed();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPage.showFullScreen();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPage.showFullScreen();
            }
        });
    }
    public void setTitle(String title_){
        ((TextView)title).setText(title_);
    }

    public void setFilterPage(WindowPop filterPage){
        this.filterButton.setVisibility(View.VISIBLE);
        this.filterPage=filterPage;
    }
    public void setAddPage(WindowPop addPage){
        this.addButton.setVisibility(View.VISIBLE);
        this.addPage=addPage;
    }
    public void setTableView(View view){
            ActivityUtil.clear(tableList);
        ((LinearLayout)tableList).addView(view);
    }

}
