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
import com.chengyi.android.angular.entity.TreeEntity;
import com.example.views.DateView;
import com.yiyun.wasteoilcustom.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chengyi.android.angular.UI.FormView.code;
import static com.chengyi.android.angular.core.Scope.activity;

/**
 * Created by Administrator on 2017/2/16.
 */

public class Condition extends ViewParent {


    public static class DataAll{
        private String title;
        private List<DataItem> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<DataItem> getList() {
            return list;
        }

        public void setList(List<DataItem> list) {
            this.list = list;
        }
    }
    public static class DataItem{
        private String type;
        private String code;
        private String name;
        private Object data;

        public class Type{
            public final static String date="date";
            public final static String list="list";
        }

        public DataItem(String type){
            this.type=type;
        }
        public String getType() {
            return type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
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

    public void initData() {
        setReturn(conditionResult);
    }
    public void init(){
        DataAll dataAll= (DataAll) scope.key(getData()).val();
        String title=dataAll.getTitle();
        List<DataItem> list= dataAll.getList();
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
            DataItem map=list.get(i);
            View filter_condition = inflater.inflate(R.layout.filter_condition, null);
            filter_conditions.addView(filter_condition);
            TextView nameView= (TextView) filter_condition.findViewById(R.id.name);
            nameView.setText(map.getName());
            TextView value= (TextView) filter_condition.findViewById(R.id.value);
            value.setText("");
            switch (map.getType()){
                case DataItem.Type.date:
                    String return_="date"+Scope.getId();
                    DateView dateView= new DateView(scope,null,return_);
                    scope.key(return_).watch(new DateDataListner(value,map.getCode()));
                    filter_condition.setOnClickListener(new FCDateOnClickListener(dateView));
                    break;
                case DataItem.Type.list:
                    View conditionItems = inflater.inflate(R.layout.filter_condition_item, null);

                     WindowPop cIWP=new WindowPop(conditionItems, Gravity.RIGHT);
                    conditionItems.findViewById(R.id.back).setOnClickListener(new BackOnClickListener(cIWP));
                    TextView conditionItemName= (TextView) conditionItems.findViewById(R.id.name);
                    conditionItemName.setText("选择"+map.getName());
                    LinearLayout conditionItemsLayout= (LinearLayout) conditionItems.findViewById(R.id.items);
                    List<TreeEntity> dataList= (List<TreeEntity>) map.getData();
                    for (int j = 0; j < dataList.size(); j++) {
                         TreeEntity itemMap=dataList.get(j);
                        View item = inflater.inflate(R.layout.item, null);
                        TextView itemName= (TextView) item.findViewById(R.id.name);
                        itemName.setText(itemMap.getName());
                        item.setOnClickListener(new ListOnClickListener(value,itemMap,map.getCode(),cIWP));
                        conditionItemsLayout.addView(item);
                    }
                    filter_condition.setOnClickListener(new FCListOnClickListener(cIWP));
                    break;

            }
        }


    }

    public WindowPop getWinPopView(){
        return windowPopOption;
    };

    class DateDataListner implements DataListener{
        private TextView value;
        private String codeV;
        public DateDataListner(TextView value,String code){
            this.value=value;
            this.codeV=code;
        }
        @Override
        public void hasChange(Object o) {
            value.setText(o.toString());
            conditionResult.put(codeV,o.toString());
        }
    }
    class ListOnClickListener implements View.OnClickListener{
        private TextView value;
        private TreeEntity itemMap;
        private String codeV;
        private WindowPop windowPop;
        public ListOnClickListener(TextView value,TreeEntity itemMap,String code,WindowPop windowPop){
            this.value=value;
            this.itemMap=itemMap;
            this.codeV=code;
            this.windowPop=windowPop;
        }
        @Override
        public void onClick(View v) {
            value.setText(itemMap.getName());
            conditionResult.put(codeV,itemMap);
            windowPop.hide();
        }
    }
    class BackOnClickListener implements View.OnClickListener{
        private WindowPop windowPop;
        public BackOnClickListener(WindowPop windowPop){
            this.windowPop=windowPop;
        }
        @Override
        public void onClick(View v) {
            windowPop.hide();
        }
    }
    class FCDateOnClickListener implements View.OnClickListener{
        private DateView dateView;
        public FCDateOnClickListener(DateView dateView){
            this.dateView=dateView;
        }
        @Override
        public void onClick(View v) {
            dateView.show();
        }
    }
    class FCListOnClickListener implements View.OnClickListener{
        private WindowPop windowPop;
        public FCListOnClickListener(WindowPop windowPop){
            this.windowPop=windowPop;
        }
        @Override
        public void onClick(View v) {
            windowPop.showFullScreen();
        }
    }
}
