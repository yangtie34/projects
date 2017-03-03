package com.eyun.project_demo.cusviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.eyun.framework.angular.baseview.WindowPop;
import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;

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
        ViewGroup viewParent= (ViewGroup) this.getParent();
        viewParent.removeView(this);
        int grav=Gravity.CENTER;
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
        final WindowPop windowPop=new WindowPop(this, grav);
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

}
