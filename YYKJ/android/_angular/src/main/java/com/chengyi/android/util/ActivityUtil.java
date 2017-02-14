package com.chengyi.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chengyi.android.angular.core.Scope;

import java.util.Timer;
import java.util.TimerTask;

import static com.chengyi.android.angular.core.Scope.activity;

/**
 * Created by administrator on 2016-10-20.
 */

public class ActivityUtil {
    public static ScrollView getScrollView(){
        ScrollView scrollView = new ScrollView(activity);
        RelativeLayout.LayoutParams relLayoutParams=new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(relLayoutParams);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);
        return scrollView;
    }
    public static RelativeLayout getWrapRelativeLayout(){
        RelativeLayout showLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams relLayoutParams=new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        showLayout.setLayoutParams(relLayoutParams);
        showLayout.setGravity(Gravity.CENTER);
        return showLayout;
    }
    public static LinearLayout getWrapLinearLayout(){
        LinearLayout showLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams relLayoutParams=new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        showLayout.setLayoutParams(relLayoutParams);
        showLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        showLayout.setOrientation(LinearLayout.VERTICAL);
        return showLayout;
    }
    /**
     * 获取Activity根节点
     * @return
     */
    public static LinearLayout getRootView()
    {
        //content:AbsoluteLayout, ChildAt(0):FrameLayout
        return (LinearLayout) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }


    /**
     * 点击EditText文本框之外任何地方隐藏键盘的解决办法
     * @param ev
     * @param pubInterface
     * @return
     */
    public static boolean dispatchTouchEvent(MotionEvent ev,PubInterface pubInterface) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = activity.getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return (Boolean) pubInterface.run();
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (activity.getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return activity.onTouchEvent(ev);

    }

    /**
     * 是否应该隐藏输入法
     * @param v
     * @param event
     * @return
     */
    public static  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 点击的是否为此view
     * @param v
     * @param event
     * @return
     */
    public static boolean isViewArea(View v, MotionEvent event){
        if (v != null) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是View区域
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    /**
     * 获取view内序号为i的元素
     * @param view
     * @param i
     * @return
     */
    public static View getViewByIndex(View view,int i) {
        ViewGroup group= (ViewGroup) view;
        return group.getChildAt(i);
    }

    /**
     * 清除view内元素
     * @param view
     */
    public static void clear(View view) {
        ViewGroup group= (ViewGroup) view;
        group.removeAllViews();
    }

    /**
     * 展示一个系统对话框
     * @param title 对话框标题
     * @param message 显示的内容
     * @param positive 确定按钮响应事件
     * @param negative 返回按钮响应事件
     */
    public static void confirm(String title, String message, final PubInterface positive, final PubInterface negative){
        AlertDialog.Builder confirm=new AlertDialog.Builder(Scope.activity);
        confirm.setTitle(title);//设置对话框标题
        confirm.setMessage(message);//设置显示的内容
        if(positive!=null)
            confirm.setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                positive.run();
            }});
        if(negative!=null)
        confirm.setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                negative.run();
            }});
        confirm.show();
    }

    /**
     * 弹出提示自动消失
     * @param str
     */
    public static void alert(String str){
        Toast.makeText(Scope.activity.getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    public static void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            alert("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            AppContext.getInstance().exit();
        }
    }
}
