package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.eyun.framework.angular.core.BaseView;
import com.eyun.framework.angular.core.Directive;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.project_demo.R;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusEditView extends EditText implements BaseView {

    private Scope scope;
    private String modelExpression;
    private String[] models=new String[]{};
    public String[] getModels() {
        return models;
    }

    public void setModels(String[] models) {
        this.models = models;
    }
    public CusEditView(Context context) {
        super(context);
    }

    public CusEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Directive.forBaseView(this,attrs);
    }
    @Override
    public void setModel(final String model) {
        if (StringUtils.hasLength(model)) {
            scope.key(model).watch(new DataListener() {
                @Override
                public void hasChange(Object o) {
                    CusEditView.this.setText(o.toString());
                }
            });
            this.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {// 此处为得到焦点时的处理内容

                    } else {// 此处为失去焦点时的处理内容
                        scope.key(model).val(CusEditView.this.getText());
                    }
                }
            });
        }
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public void setScope(Scope scope) {
        this.scope=scope;
    }

    @Override
    public void setModelExpression(String modelExpression) {
        this.modelExpression=modelExpression;
    }

    @Override
    public String getModelExpression() {
        return this.modelExpression;
    }
    @Override
    public CusEditView clone() {
        CusEditView o = null;
        try {
            o = (CusEditView) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

}
