package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.DirectiveManager;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.util.common.StringUtils;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusCheckBox extends CheckBox implements  CusBaseView{

    public CusCheckBox(Context context) {
        super(context);
    }

    public CusCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        DirectiveManager.init(this,attrs);
    }

    private Scope scope;
    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public void setScope(Scope scope) {
        this.scope=scope;
    }
}
