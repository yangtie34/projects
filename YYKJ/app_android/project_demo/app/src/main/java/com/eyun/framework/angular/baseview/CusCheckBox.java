package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.eyun.framework.angular.core.BaseView;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Directive;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.project_demo.R;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusCheckBox extends CheckBox  implements BaseView {

    private Scope scope;
    private String modelExpression;
    private String[] models=new String[]{};
    public String[] getModels() {
        return models;
    }

    public void setModels(String[] models) {
        this.models = models;
    }
    public CusCheckBox(Context context) {
        super(context);
    }

    public CusCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        Directive.forBaseView(this,attrs);
    }
    @Override
    public void setModel(final String model) {
        if(StringUtils.hasLength(model)){
            scope.key(model).watch(new DataListener() {
                @Override
                public void hasChange(Object o) {
                    CusCheckBox.this.setText(o.toString());
                }
            });
            this.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if(isChecked){
                        scope.key(model).val(true);
                    }else{
                        scope.key(model).val(false);
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
    public CusCheckBox clone() {
        CusCheckBox o = null;
        try {
            o = (CusCheckBox) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
