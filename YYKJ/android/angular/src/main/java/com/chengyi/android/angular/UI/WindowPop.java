package com.chengyi.android.angular.UI;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.CSS;


/**
 * showAsDropDown(anchor);
 showAsDropDown(anchor, xoff, yoff);
 showAtLocation(parent, gravity, x, y);
 */
public class WindowPop extends PopupWindow {

    private View view;
    private Object[] args;
    private int gravity= CSS.effect.Animation.byScale();
    private WindowPop(int loyout,Object[] args) {
        init(loyout);
        this.args=args;
    }
    private WindowPop(View view,Object[] args){
        init(view);
        this.args=args;
    }

    public WindowPop(View view,View clickView){
        init(view);
        this.args=new Object[]{clickView};
    }

    public WindowPop(View view,int gravity) {
        this.gravity=gravity;
        init(view);
        this.args=new Object[]{ActivityUtil.getRootView(), gravity, 0, 0};
    }

    public WindowPop(int loyout,View clickView) {
        init(loyout);
        this.args=new Object[]{clickView};
    }
    /**
     *
     * @param loyout
     * @param gravity Gravity.BOTTOM...
     */
    public WindowPop(int loyout,int gravity) {
        this.gravity=gravity;
        init(loyout);
        this.args=new Object[]{ActivityUtil.getRootView(), gravity, 0, 0};
    }
    private void init(int loyout){
        init(LayoutInflater.from(Scope.activity).inflate(loyout, null));
    }
    private void init(View view) {
        this.view = view;
        this.setBackgroundDrawable(new BitmapDrawable());
// 设置点击窗口外边窗口消失
        this.setOutsideTouchable(true);
// 设置此参数获得焦点，否则无法点击
        this.setFocusable(true);
    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);

        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        //String styleName="windowPop";

        this.setAnimationStyle(CSS.effect.AnimationHalf.getAnimByGravity(gravity));

    }
    private void showMode(Object[] args){


        if(args.length==3){//showAsDropDown(View anchor, int xoff, int yoff)：相对某个控件的位置，有偏移
            showAsDropDown((View) args[0], (Integer) args[2], (Integer) args[3]);
        }else if(args.length==4){//showAtLocation(View parent, int gravity, int x, int y)：相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
            showAtLocation((View) args[0], (Integer) args[1], (Integer) args[2], (Integer) args[3]);
        }else{
            showAsDropDown((View) args[0]);//showAsDropDown(View anchor)：相对某个控件的位置（正左下方），无偏移
        }
    }
    // 取消按钮
    public void hide(){
        // 销毁弹出框
        dismiss();
    }
    public void show(){
        showNoMask();
        Mask.getInstance().show();
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Mask.getInstance().hide();
            }
        });
    }
    public void showNoMask(){
        showMode(args);
    }

}