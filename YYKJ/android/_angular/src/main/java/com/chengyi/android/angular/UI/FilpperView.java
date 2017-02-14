package com.chengyi.android.angular.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.chengyi.android.angular.R;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.android.angular.core.Scope.activity;

/**
 * Created by administrator on 2016-11-2.
 * Data:List<View>
 * state1: index
 */

public class FilpperView extends ViewParent implements GestureDetector.OnGestureListener{
    private ViewFlipper viewFlipper = null;
    public GestureDetector gestureDetector = null;
    private int index=0;
    private List<View> items=new ArrayList<>();
    public FilpperView(Scope parent, String data, String state1, String retur) {
        super(parent);
        this.setData(data);
        this.setState1(state1);
        this.setReturn(retur);
        init();
    }
    public FilpperView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public void init(){
        this.setBackground(activity.getResources().getDrawable(R.drawable.sharp_boder));
        viewFlipper=new ViewFlipper(activity);
        LinearLayout.LayoutParams relLayoutParams=new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1);
        gestureDetector = new GestureDetector(this);    // 声明检测手势事件
        viewFlipper.setLongClickable(true);
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewFlipper.stopFlipping();             // 点击事件后，停止自动播放
                viewFlipper.setAutoStart(false);
                return gestureDetector.onTouchEvent(event);         // 注册手势事件
            }
        });
        this.addView(viewFlipper);
        scope.key(this.getData()).watch( new DataListener<List<View>>() {
            @Override
            public void hasChange(List<View> items) {
                additems(items);
            }
        });
        scope.key(this.getState1()).watch( new DataListener<Integer>() {
            @Override
            public void hasChange(Integer index) {
                showByIndex(index);
            }
        });
    }

    public void additems(List<View> items){
        this.items=items;
        for (int i = 0; i < items.size(); i++) {          // 添加图片源
            viewFlipper.addView(items.get(i));
        }
        viewFlipper.setAutoStart(false);         // 设置自动播放功能（点击事件，前自动播放）
        //viewFlipper.setFlipInterval(3000);
        if(viewFlipper.isAutoStart() && !viewFlipper.isFlipping()){
            viewFlipper.startFlipping();
        }
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }
    public void showPrevious(){
        Animation rInAnim = AnimationUtils.loadAnimation(activity, R.anim.in_from_left);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）
        Animation rOutAnim = AnimationUtils.loadAnimation(activity, R.anim.out_to_right); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）

        viewFlipper.setInAnimation(rInAnim);
        viewFlipper.setOutAnimation(rOutAnim);
        viewFlipper.showPrevious();
        if(index>0) {
            index--;
            this.setReturn(index);
        }
    }
    public void showNext(){
        Animation lInAnim = AnimationUtils.loadAnimation(activity, R.anim.in_from_right);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
        Animation lOutAnim = AnimationUtils.loadAnimation(activity, R.anim.out_to_left);     // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）

        viewFlipper.setInAnimation(lInAnim);
        viewFlipper.setOutAnimation(lOutAnim);
        viewFlipper.showPrevious();
        if(index<items.size()-1){
            index++;
            this.setReturn(index);
        }
    }
    public void showByIndex(int i){
        while (index!=i){
            if(index<i){
                showNext();
            }else if(index>i){
                showPrevious();
            }
        }
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() - e1.getX() > 120) {            // 从左向右滑动（左进右出）

            showPrevious();
            return true;
        } else if (e2.getX() - e1.getX() < -120) {        // 从右向左滑动（右进左出）

            showNext();
            return true;
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}
