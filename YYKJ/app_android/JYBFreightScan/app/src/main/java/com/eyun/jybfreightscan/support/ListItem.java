package com.eyun.jybfreightscan.support;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eyun.framework.angular.core.Scope;
import com.eyun.jybfreightscan.R;

import static com.eyun.framework.angular.core.Scope.activity;

/**
 * Created by Administrator on 2017/2/15.
 */

public class ListItem {
    private View itemView;
    private TextView titleView;
    private TextView msgView;
    private TextView timeView;
    private TextView infoView;

    public ListItem(String title, String msg, String time, String info){
        LayoutInflater inflater = LayoutInflater.from(activity);
        itemView= Scope.activity.scope.inflate(R.layout.item_for_table);
/*        Scope.inflateScope.key("LItitle").val(title);
        Scope.inflateScope.key("LImsg").val(msg);
        Scope.inflateScope.key("LItime").val(time);
        Scope.inflateScope.key("LIinfo").val(info);*/
        itemView = inflater.inflate(R.layout.item_for_table, null);
        titleView= (TextView) itemView.findViewById(R.id.title);
        titleView.setText("编号:"+title);
        msgView= (TextView) itemView.findViewById(R.id.msg);
        if(msg!=null)msgView.setText(msg);
        timeView= (TextView) itemView.findViewById(R.id.time);
        if(time!=null)timeView.setText("数量:"+time);
        infoView= (TextView) itemView.findViewById(R.id.info);
        if(info!=null)infoView.setText("类别:"+info);
    }
    public View getItemView(){
        return itemView;
    }


}
