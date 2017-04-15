package com.eyun.jybfreightscan.cusviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.NodeEntity;
import com.eyun.framework.moduleview.widget.OnWheelScrollListener;
import com.eyun.framework.moduleview.widget.ViewWheelAdapter;
import com.eyun.framework.moduleview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 *
 * Data:NodeEntity
 * params:visible 显示数量
 */

public class CusWheelView extends AngularViewParent {
    private ViewFlipper viewfipper;
    private View mMenuView;
    private  int visible=5;

    public CusWheelView(Scope parent, String Data, String params , String return_) {
        super(parent);
        setData(Data);
        setParams(params);
        setReturn(return_);
        init();
    }
    public CusWheelView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    protected void  init(){
        this.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout mMenuView= new LinearLayout(scope.activity);
        this.mMenuView=mMenuView;
        LayoutParams relLayoutParams=new LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
        mMenuView.setLayoutParams(relLayoutParams);
        ((LinearLayout)mMenuView).setGravity(Gravity.CENTER_HORIZONTAL);
        ((LinearLayout)mMenuView).setOrientation(LinearLayout.HORIZONTAL);
        // mMenuView.setBackgroundColor(Color.parseColor("#00FF00"));
        viewfipper=new ViewFlipper(scope.activity);
        viewfipper.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,1));
        viewfipper.addView(mMenuView);
        viewfipper.setFlipInterval(6000000);
        this.addView(viewfipper);
        scope.key(this.getData()).watch( new DataListener<NodeEntity>() {
            @Override
            public void hasChange(NodeEntity treeEntity) {
                CusWheelView.this.fillView(treeEntity,0);
            }
        });
        scope.key(this.getState1()).watch( new DataListener<Integer>() {
            @Override
            public void hasChange(Integer visible) {
                CusWheelView.this.visible=visible;
               for(int i=0;i<((ViewGroup)mMenuView).getChildCount();i++){
                   ((WheelView)((ViewGroup)mMenuView).getChildAt(i)).setVisibleItems(visible);
               }
            }
        });

    }
    public void fillView(NodeEntity treeEntity, int index){
        List<NodeEntity> treeEntities=treeEntity.getChildrenList();
        if(treeEntities.size()==0){
            return;
        }
        this.setReturn(treeEntities.get(index));
        //TextWheelAdapter textWheelAdapter=new TextWheelAdapter(treeEntities);

        List<View> views=new ArrayList<View>();
        for (int i = 0; i <treeEntities.size() ; i++) {
           /* TextView textview=new TextView(scope.activity);
            textview.setText(treeEntities.get(i).getName());*/
            views.add((View) treeEntities.get(i).getContent());
            //views.add(textview);
        }
        ViewWheelAdapter viewWheelAdapter =new ViewWheelAdapter(views);

        WheelView wheelView=null;
        if(mMenuView.findViewById(treeEntity.getLevel()+1)==null){
            wheelView=new WheelView(scope.activity);
            LayoutParams relLayoutParams=new LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);

            wheelView.setId(treeEntity.getLevel()+1);
            wheelView.setLayoutParams(relLayoutParams);
            ((LinearLayout)mMenuView).addView(wheelView);
        }else{
            wheelView=(WheelView)mMenuView.findViewById(treeEntity.getLevel()+1);
        }
        wheelView.setViewAdapter(viewWheelAdapter);
        wheelView.setVisibleItems(visible);
        wheelView.setCurrentItem(index);
        wheelView.setScrollingListener(new Listener(treeEntity));
        fillView(treeEntities.get(index),0);
    }

    class Listener implements OnWheelScrollListener {
        private NodeEntity treeEntity;
        public Listener(NodeEntity treeEntity){
            this.treeEntity=treeEntity;
        }
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            fillView(treeEntity.getChildrenList().get(wheel.getCurrentItem()),0);
        }
    }

}
