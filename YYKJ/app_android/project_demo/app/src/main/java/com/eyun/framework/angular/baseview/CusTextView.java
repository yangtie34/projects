package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.eyun.framework.angular.core.BaseView;
import com.eyun.framework.angular.core.Directive;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.util.ActivityUtil;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.ThreadUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.project_demo.R;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusTextView extends TextView  implements BaseView {

    public Scope scope;
    public String modelExpression;

    public String[] models=new String[]{};

    public CusTextView(Context context) {
        super(context);
    }

    public CusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Directive.forBaseView(this,attrs);
    }

    @Override
    public void setModel(String model) {
        if(StringUtils.hasLength(model)){
            this.getScope().key(model).addWatch(new DataListener() {
                @Override
                public void hasChange(Object o) {
                    CusTextView.this.setText(
                            CusTextView.this.getParent().toString()+
                            CusTextView.this.toString().split("\\{")[1]+
                            Directive.modelChange(CusTextView.this));
                }
            });
        }
    }
/*    class ModelDataListner implements DataListener{
        private CusTextView cusTextView;
        public ModelDataListner(CusTextView cusTextView){
            this.cusTextView=cusTextView;
        }
        @Override
        public void hasChange(Object o) {
            cusTextView.setText(Directive.modelChange(cusTextView));
        }
    }*/
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
    public CusTextView clone() {
        CusTextView o = null;
        try {
            o = (CusTextView) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String[] getModels() {
        return models;
    }

    public void setModels(String[] models) {
        this.models = models;
    }
}
