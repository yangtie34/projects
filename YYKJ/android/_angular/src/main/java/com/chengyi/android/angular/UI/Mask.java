package com.chengyi.android.angular.UI;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.widget.RelativeLayout;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.CSS;
import com.chengyi.android.util.PubInterface;
import com.chengyi.android.util.ThreadUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by administrator on 2016-10-20.
 *
 */

public class Mask {
    private static Map<Activity,Mask> cache=new HashMap<>();
    private static long time= CSS.effect.duration;
    private static int DELYED=5;//每几毫秒执行一次
    private static float alphaStart=0f;
    private static float alphaEnd=0.2f;
    private static float everyAlpha= (float) (alphaEnd*DELYED/time);
    private static ThreadUtil thread;


    private RelativeLayout view;
    private float alpha= alphaStart;
    private boolean show=true;


    private Mask(){
        view = new RelativeLayout(Scope.activity);
        view.setLayoutParams(CSS.LayoutParams.matchAll());
        //view.setBackgroundColor(Color.parseColor("#000000"));
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        view.setBackgroundDrawable(dw);
        view.setAlpha(alphaStart);
        ActivityUtil.getRootView().addView(view);
    }
        //静态工厂方法
        public static Mask getInstance() {
            Mask mask;
            if(cache.containsKey(Scope.activity)){
                mask=cache.get(Scope.activity);
            }else{
                mask=new Mask();
                cache.put(Scope.activity,mask);
            }
            return mask;
        }
    public void show(){
        show=true;
        go();
    }
    public void hide(){
        show=false;
        go();
    }
    synchronized private void go(){
        if(thread!=null) {
           // if(!thread.isAlive())
           thread.resume();

        }else{
            thread=ThreadUtil.getLoopThread(new PubInterface() {
                @Override
                public Object run() {
                    if ((show==false&&alpha > alphaStart) ||(show==true&&alpha < alphaEnd) ) {
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
