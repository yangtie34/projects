package com.eyun.eyunstorage.cusviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.eyun.framework.angular.baseview.WindowPop;
import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.util.ThreadUtil;

/**
 * data:show
 * paramas: left right top bottom center
 */

public class CusWindowPop extends AngularViewParent {

    public CusWindowPop(Scope parent, String Data,String params ,String return_) {
        super(parent);
        setData(Data);
        setParams(params);
        setReturn(return_);
        init();
    }
    public CusWindowPop(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void init() {
        this.setVisibility(GONE);
        scope.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup viewParent= (ViewGroup) CusWindowPop.this.getParent();
                if(viewParent==null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ThreadUtil.sleep(200);
                            init();
                        }
                    }).start();
                    return;
                }
                viewParent.removeView(CusWindowPop.this);
                int grav=Gravity.CENTER;
                if(getParams()!=null)
                    switch (getParams()){
                        case "left":
                            grav=Gravity.LEFT;
                            break;
                        case "right":
                            grav=Gravity.RIGHT;
                            break;
                        case "top":
                            grav=Gravity.TOP;
                            break;
                        case "bottom":
                            grav=Gravity.BOTTOM;
                            break;
                        default:
                            grav=Gravity.CENTER;
                            break;

                    }
                CusWindowPop.this.setVisibility(VISIBLE);
                final WindowPop windowPop=new WindowPop(CusWindowPop.this, grav);
                scope.key(getData()).watch(new DataListener<Boolean>() {
                    @Override
                    public void hasChange(Boolean bool) {
                        if(bool==true){
                            windowPop.show();
                        }else if(bool==false){
                            windowPop.hide();
                        }
                    }
                });
            }
        });

    }

}
