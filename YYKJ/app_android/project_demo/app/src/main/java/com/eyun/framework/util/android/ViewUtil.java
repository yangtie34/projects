package com.eyun.framework.util.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eyun.framework.angular.baseview.CusBaseView;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.util.CallBack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.eyun.framework.angular.core.Scope.activity;
import static com.eyun.project_demo.R.id.textView;

/**
 * Created by Administrator on 2017/2/27.
 */

public class ViewUtil {
    /**
     * 获取Activity根节点
     *
     * @return
     */
    public static FrameLayout getRootView() {
        return (FrameLayout) activity.findViewById(android.R.id.content);
    }


    public static View clone(View view) {
        View newView = null;
        Class clazz = view.getClass();
        Constructor c1 = null;
        try {
            c1 = clazz.getDeclaredConstructor(new Class[]{Context.class});
            c1.setAccessible(false);
            newView = (View) c1.newInstance(new Object[]{Scope.activity});

            newView.setLayoutParams(view.getLayoutParams());
            newView.setBackgroundDrawable(view.getBackground());
            newView.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());

            if (view instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) view;
                ((LinearLayout) newView).setOrientation(linearLayout.getOrientation());
            }
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                ((TextView) newView).setText(textView.getText());
                ((TextView) newView).setTextSize(textView.getTextSize());
                ((TextView) newView).setTextColor(textView.getTextColors());
                ((TextView) newView).setHintTextColor(textView.getHintTextColors());
                ((TextView) newView).setHint(textView.getHint());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return newView;
    }

    /**
     * 点击的是否为此view
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isViewArea(View v, MotionEvent event) {
        if (v != null) {
            int[] leftTop = {0, 0};
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
     *
     * @param view
     * @param i
     * @return
     */
    public static View getViewByIndex(View view, int i) {
        ViewGroup group = (ViewGroup) view;
        return group.getChildAt(i);
    }

    /**
     * 清除view内元素
     *
     * @param view
     */
    public static void clear(View view) {
        ViewGroup group = (ViewGroup) view;
        group.removeAllViews();
    }

    /**
     * 展示一个系统对话框
     *
     * @param title    对话框标题
     * @param message  显示的内容
     * @param positive 确定按钮响应事件
     * @param negative 返回按钮响应事件
     */
    public static void confirm(String title, String message, final CallBack positive, final CallBack negative) {
        AlertDialog.Builder confirm = new AlertDialog.Builder(Scope.activity);
        confirm.setTitle(title);//设置对话框标题
        confirm.setMessage(message);//设置显示的内容
        if (positive != null)
            confirm.setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                    positive.run();
                }
            });
        if (negative != null)
            confirm.setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                    negative.run();
                }
            });
        confirm.show();
    }

    /**
     * 弹出提示自动消失
     *
     * @param str
     */
    public static void alert(String str) {
        Toast.makeText(Scope.activity.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
