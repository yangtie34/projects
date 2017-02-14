package com.chengyi.android.angular.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.chengyi.android.angular.R;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.chengyi.android.angular.core.Scope.activity;

/**
 * Created by administrator on 2016-11-2.
 */

public class PagerView extends ViewParent {
    private RepeatView repeatView=new RepeatView(scope,"repeat","0_1_1","repeat1","repeatR");
    private FilpperView filpperView=new FilpperView(scope,"filpper","filpper1","filpperR");
    public PagerView(Scope parent, String Data){
        super(parent);
        this.setData(Data);
        init();
    }
    public PagerView(Context context, AttributeSet attr) {
        super(context, attr);
        this.setOrientation(LinearLayout.VERTICAL);
        init();
    }
    public void init(){
        this.setBackground(activity.getResources().getDrawable(R.drawable.sharp_boder));
        this.addView(repeatView);
        //repeatView.setVisibility(View.GONE);
        this.addView(filpperView);
        scope.key(this.getData()).watch( new DataListener<Map<View,View>>() {
            @Override
            public void hasChange(Map<View,View> map) {
                List<View> repeatDataList=new ArrayList<>();
                List<View> filpperDataList=new ArrayList<>();
                repeatDataList.addAll(map.keySet());
                filpperDataList.addAll(map.values());
                scope.key("repeat").val(repeatDataList);
                scope.key("filpper").val(filpperDataList);
                scope.key("repeat1").val(0);
                scope.key("filpper1").val(0);

            }
        });
        final ViewTreeObserver vto = this.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    PagerView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = PagerView.this.getHeight();
                //int width = PagerView.this.getMeasuredWidth();
                filpperView.getLayoutParams().height=height-repeatView.getHeight();
            }
        });
        scope.key("repeatR").watch( new DataListener<Integer>() {
           @Override
           public void hasChange(Integer index) {
               scope.lockSync();
               PagerView.this.filpperView.showByIndex(index);
               scope.openSync();
           }
       });
        scope.key("filpperR").watch( new DataListener<Integer>() {
            @Override
            public void hasChange(Integer index) {
                scope.lockSync();
                PagerView.this.repeatView.itemClick(index);
                scope.openSync();
            }
        });
    }
}
