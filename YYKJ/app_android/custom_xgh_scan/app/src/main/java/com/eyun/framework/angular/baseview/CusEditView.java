package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.eyun.framework.angular.core.DirectiveManager;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.util.common.StringUtils;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusEditView extends EditText implements CusBaseView{


    public CusEditView(Context context) {
        super(context);
    }

    public CusEditView(Context context, AttributeSet attrs) {
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
