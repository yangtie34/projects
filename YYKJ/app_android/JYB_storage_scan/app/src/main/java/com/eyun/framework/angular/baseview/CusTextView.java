package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.eyun.framework.angular.core.DirectiveManager;
import com.eyun.framework.angular.core.Scope;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusTextView extends TextView  implements  CusBaseView{

    public CusTextView(Context context) {
        super(context);
    }

    public CusTextView(Context context, AttributeSet attrs) {
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
