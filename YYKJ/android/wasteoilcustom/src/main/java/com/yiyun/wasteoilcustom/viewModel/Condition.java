package com.yiyun.wasteoilcustom.viewModel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.UI.WindowPop;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.example.views.DateView;
import com.yiyun.wasteoilcustom.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chengyi.android.angular.core.Scope.activity;
import static com.yiyun.wasteoilcustom.viewModel.Condition.Type.date;

/**
 * Created by Administrator on 2017/2/16.
 */

public class Condition extends ViewParent {
    public static String type= "type";
    public static String code= "code";
    public static String name= "name";
    public static String data= "data";
    public static class Type{
        public final static String date="date";
        public final static String list="list";
    }
    public static class Data{
        public final static String id="id";
        public final static String name="name";
    }
    private Map<String,Object> conditionResult=new HashMap<>();
    private WindowPop windowPopOption;
    /**
     * [{
     *     type:
     *     code:
     *     name:
     *     data:
     * }...
     * ]
     */
    public Condition(Scope parent, String Data, String Return) {
        super(parent);
        setData(Data);
        setReturn(Return);
        init();
    }
    public Condition(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public void init(){
        Map<String,Object> dataAll= (Map<String, Object>) scope.key(getData()).val();
        String title=dataAll.get("title").toString();
        List<Map<String,Object>> list= (List<Map<String, Object>>) dataAll.get("list");
        LayoutInflater inflater = LayoutInflater.from(activity);
        View conditionView = inflater.inflate(R.layout.filter, null);
        conditionView.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                windowPopOption.hide();
            }
        });
        conditionView.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setReturn(conditionResult);
                windowPopOption.hide();
            }
        });
        windowPopOption=new WindowPop(conditionView, Gravity.RIGHT);
        TextView titleView= (TextView) conditionView.findViewById(R.id.title);
        titleView.setText(title);
        LinearLayout filter_conditions= (LinearLayout) conditionView.findViewById(R.id.filter_conditions);
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> map=list.get(i);
            View filter_condition = inflater.inflate(R.layout.filter_condition, null);
            TextView name= (TextView) conditionView.findViewById(R.id.name);
            name.setText(map.get(name).toString());
            final TextView value= (TextView) conditionView.findViewById(R.id.name);
            value.setText("");
            switch (map.get(type).toString()){
                case date:
                    String return_="date"+getId();
                    final DateView dateView= new DateView(activity.scope,null,return_);
                    activity.scope.key(return_).watch(new DataListener<String>() {
                        @Override
                        public void hasChange(String d) {
                            value.setText(d);
                            conditionResult.put(code,d);
                        }
                    });
                    filter_condition.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dateView.show();
                        }
                    });
                    break;
                case Type.list:
                    View conditionItems = inflater.inflate(R.layout.filter_condition_item, null);

                    final WindowPop cIWP=new WindowPop(conditionItems, Gravity.RIGHT);
                    conditionItems.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cIWP.hide();
                        }
                    });
                    TextView conditionItemName= (TextView) conditionView.findViewById(R.id.name);
                    conditionItemName.setText("选择"+map.get(name));
                    LinearLayout conditionItemsLayout= (LinearLayout) conditionView.findViewById(R.id.items);
                    List<Map<String,String>> dataList= (List<Map<String, String>>) map.get(date);
                    for (int j = 0; j < dataList.size(); j++) {
                        final Map<String,String> itemMap=dataList.get(j);
                        View item = inflater.inflate(R.layout.item, null);
                        TextView itemName= (TextView) item.findViewById(R.id.name);
                        itemName.setText(itemMap.get(Data.name));
                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                value.setText(itemMap.get(Data.name));
                                conditionResult.put(code,itemMap.get(Data.id));
                                cIWP.hide();
                            }
                        });
                        conditionItemsLayout.addView(item);
                    }
                    filter_condition.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cIWP.showNoMask();
                        }
                    });
                    break;

            }
        }


    }
    public void show(){
        windowPopOption.showNoMask();
    }
    public void hide(){
        windowPopOption.hide();
    }
}
