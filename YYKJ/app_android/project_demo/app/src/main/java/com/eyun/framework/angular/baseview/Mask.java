package com.eyun.framework.angular.baseview;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.eyun.framework.Constant;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.util.android.ActivityUtil;
import com.eyun.framework.util.CSS;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.ThreadUtil;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.project_demo.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by administrator on 2016-10-20.
 */

public class Mask {
    private static Map<Activity, Mask> cache = new HashMap<>();
    private static long time = CSS.effect.duration;
    private static int DELYED = 5;//每几毫秒执行一次
    private static float alphaStart = 0f;
    private static float alphaEnd = 0.2f;
    private static float everyAlpha = (float) (alphaEnd * DELYED / time);
    private static ThreadUtil thread;


    private RelativeLayout view;
    private float alpha = alphaStart;
    private boolean show = true;


    private Mask() {
        view = new RelativeLayout(Scope.activity);
        view.setLayoutParams(Constant.layoutParamsMATCH);
        view.setBackgroundColor(Scope.activity.getResources().getColor(R.color.black));
        view.setAlpha(alphaStart);
        ViewUtil.getRootView().addView(view);
    }

    //静态工厂方法
    public static Mask getInstance() {
        Mask mask;
        if (cache.containsKey(Scope.activity)) {
            mask = cache.get(Scope.activity);
        } else {
            mask = new Mask();
            cache.put(Scope.activity, mask);
        }
        return mask;
    }

    public void show() {
        show = true;
        go();
    }

    public void hide() {
        show = false;
        go();
    }

    synchronized private void go() {
        if (thread != null) {
            // if(!thread.isAlive())
            thread.resume();

        } else {
            thread = ThreadUtil.getLoopThread(new CallBack() {
                @Override
                public Object run() {
                    if ((show == false && alpha > alphaStart) || (show == true && alpha < alphaEnd)) {
                        alpha = show == true ? alpha + everyAlpha : alpha - everyAlpha;
                        if (alpha > alphaEnd) {
                            alpha = alphaEnd;
                        } else if (alpha < alphaStart) {
                            alpha = alphaStart;
                        }
                        view.setAlpha(alpha);
                        try {
                            Thread.sleep(DELYED);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        thread.suspend();
                    }
                    return null;
                }
            });
            thread.start();
        }
    }
}
