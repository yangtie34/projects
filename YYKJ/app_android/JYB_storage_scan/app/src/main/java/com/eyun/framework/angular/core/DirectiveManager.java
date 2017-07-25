package com.eyun.framework.angular.core;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.eyun.jybStorageScan.R;

/**
 * Created by Administrator on 2017/2/27.
 */

public class DirectiveManager {

    public static void init(final View view, AttributeSet attrs) {
        TypedArray typedArray = Scope.activity.obtainStyledAttributes(attrs, R.styleable.ViewParent);//TypedArray是一个数组容器
        final String ng_model = typedArray.getString(R.styleable.ViewParent_ng_model);
        final String ng_show = typedArray.getString(R.styleable.ViewParent_ng_show);
        final String ng_enabled = typedArray.getString(R.styleable.ViewParent_ng_enabled);
        final String ng_repeat = typedArray.getString(R.styleable.ViewParent_ng_repeat);//以后完善//以后完善
        typedArray.recycle();//回收资源
        final DirectiveViewControl directiveViewControl = new DirectiveViewControl();
        directiveViewControl.setView(view);
        directiveViewControl.setScope(Scope.inflateScope);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (view.getParent() != null) {
                    Scope.activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            directiveViewControl.setNg_model(ng_model);
                            directiveViewControl.setNg_Show(ng_show);
                            directiveViewControl.setNg_enabled(ng_enabled);
                            directiveViewControl.setNg_repeat(ng_repeat);
                         /*   if (view.getParent() instanceof CusViewGroup) {
                                ((CusViewGroup) view.getParent()).addChildren(directiveViewControl);
                            } else {

                            }*/
                        }
                    });
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    run();
                }
            }
        }).start();
    }

}
