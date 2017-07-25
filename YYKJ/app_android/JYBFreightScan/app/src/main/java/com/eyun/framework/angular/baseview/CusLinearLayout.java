package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.eyun.framework.angular.core.DirectiveManager;
import com.eyun.framework.angular.core.DirectiveViewControl;
import com.eyun.framework.angular.core.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusLinearLayout extends LinearLayout implements CusViewGroup {

    private List<DirectiveViewControl> directiveViewControls=new ArrayList<>();

    private Scope scope;

    public CusLinearLayout(Context context) {
        super(context);
    }

    public CusLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        DirectiveManager.init(this,attrs);
    }

    @Override
    public List<DirectiveViewControl> getChildrens() {
        return directiveViewControls;
    }

    @Override
    public void addChildren(DirectiveViewControl directiveViewControl) {
        this.removeView(directiveViewControl.getView());
        directiveViewControls.add(directiveViewControl);
    }
    @Override
    public void setChildrens(List<DirectiveViewControl> directiveViewControls) {
       this.directiveViewControls=directiveViewControls;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public void setScope(Scope scope) {
        this.scope=scope;
    }
}
