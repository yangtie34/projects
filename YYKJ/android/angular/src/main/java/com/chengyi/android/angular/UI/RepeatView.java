package com.chengyi.android.angular.UI;

import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.chengyi.android.angular.R;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.android.angular.core.Scope.activity;

/**
 * Created by Administrator on 2016/10/26.
 *
 * Data:List<View>
 * params:0横向/1竖向_行数/列数_可否点击  0/1_2_0/1
 * state1:初始化点击index
 */

public class RepeatView extends ViewParent {
    private  ViewGroup scrollView;
    private int clickId=0;
    private List<View> items=new ArrayList<>();

    public RepeatView(Scope parent,String Data, String params, String state1, String Return){
        super(parent);
        setData(Data);
        setState1(state1);
        setParams(params);
        setReturn(Return);
        init();
    }

    @Override
    protected void init( ){
        String []param=this.getParams().split("_");
        boolean hengxiang=param[0].equalsIgnoreCase("0");
        final int hangshu=Integer.parseInt(param[1]);
        boolean clickable=param[2].equalsIgnoreCase("1");
        LayoutParams layoutParams= new LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(layoutParams);


        if(hengxiang){
            scrollView = new HorizontalScrollView(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
        }else{
            scrollView = new ScrollView(activity);
            layout.setOrientation(LinearLayout.HORIZONTAL);
        }
        scrollView.addView(layout);
        for(int i=0;i<hangshu;i++){
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(hengxiang?LinearLayout.HORIZONTAL:LinearLayout.VERTICAL);
            layout.addView(linearLayout);
        }
        scrollView.setLayoutParams(layoutParams);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);
        this.addView(scrollView);

        scope.key(this.getData()).watch(new DataListener<List<View>>() {
            @Override
            public void hasChange(final List<View> items) {
                if(items.size()==0)return;
                RepeatView.this.items=items;
                int i=0;
                while(i<items.size()){
                    for(int j=0;j<hangshu;j++){
                        if(i<items.size()) {
                            ((LinearLayout) ((ViewGroup) layout).getChildAt(j)).addView(items.get(i));
                            items.get(i).setOnClickListener(new Onclick(i));
                        }
                        i++;
                    }
                }
            }
        });

       if(clickable) scope.key(this.getState1()).watch( new DataListener<Integer>() {
            @Override
            public void hasChange(Integer index) {
                RepeatView.this.itemClick(index);
            }
        });
    }

    public void itemClick(int index){
        items.get(index).callOnClick();
    }

/*    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return items.get(position);
        }

    }*/
    class Onclick implements OnClickListener{
    public Onclick(int index){
        this.index=index;
    }
    private int index;
    @Override
    public void onClick(View v) {

        items.get(clickId).setBackground(activity.getResources().getDrawable(R.drawable.none_bg));
        items.get(index).setBackground(activity.getResources().getDrawable(R.drawable.sharp_boder));
        RepeatView.this.clickId=index;

        RepeatView.this.setReturn(index);
    }
}
}
