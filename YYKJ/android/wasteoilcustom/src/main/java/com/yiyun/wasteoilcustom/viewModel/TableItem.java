package com.yiyun.wasteoilcustom.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.UI.WindowPop;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.ActivityUtil;
import com.yiyun.wasteoilcustom.R;

import java.util.List;
import java.util.Map;

import static android.R.id.list;
import static com.chengyi.android.angular.core.Scope.activity;
import static com.yiyun.wasteoilcustom.R.id.back;
import static com.yiyun.wasteoilcustom.bll.User_BLL.msg;

/**
 * Created by Administrator on 2017/2/15.
 */

public class TableItem {
    private View itemView;
    private TextView titleView;
    private TextView msgView;
    private TextView timeView;
    private TextView infoView;

    public TableItem(String title,String msg,String time,String info){
        LayoutInflater inflater = LayoutInflater.from(activity);
        itemView = inflater.inflate(R.layout.table_item, null);
        titleView= (TextView) itemView.findViewById(R.id.title);
        titleView.setText(title);
        msgView= (TextView) itemView.findViewById(R.id.msg);
        msgView.setText(msg);
        timeView= (TextView) itemView.findViewById(R.id.time);
        timeView.setText(time);
        infoView= (TextView) itemView.findViewById(R.id.info);
        infoView.setText(info);
    }
    public View getItemView(){
        return itemView;
    }
}
