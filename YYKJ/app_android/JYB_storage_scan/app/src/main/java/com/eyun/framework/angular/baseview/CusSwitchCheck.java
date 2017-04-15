package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.eyun.framework.angular.core.DirectiveManager;
import com.eyun.framework.angular.core.Scope;
import com.eyun.jybStorageScan.R;


/**
 * Created by Administrator on 2017/3/5.
 */

public class CusSwitchCheck extends View  implements CusBaseView {

    public CusSwitchCheck(Context context) {
        super(context);
    }

    public CusSwitchCheck(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackground(getResources().getDrawable(R.drawable.base_switchcheck_bg_selector));
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
