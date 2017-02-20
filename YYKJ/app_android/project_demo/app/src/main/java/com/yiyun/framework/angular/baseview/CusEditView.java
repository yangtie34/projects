package com.yiyun.framework.angular.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yiyun.framework.angular.core.DataListener;
import com.yiyun.framework.angular.core.Scope;
import com.yiyun.framework.util.common.StringUtils;
import com.yiyun.project_demo.R;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusEditView extends EditText {

    private Scope scope;

    public CusEditView(Context context) {
        super(context);
    }

    public CusEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewParent);//TypedArray是一个数组容器
        setModel(typedArray.getString(R.styleable.ViewParent_ng_model));
        typedArray.recycle();//回收资源
    }

    public void setModel(final String model) {
        if(StringUtils.hasLength(model)){
            scope.key(model).watch(new DataListener() {
                @Override
                public void hasChange(Object o) {
                    CusEditView.this.setText(o.toString());
                }
            });
            this.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {// 此处为得到焦点时的处理内容

                    } else {// 此处为失去焦点时的处理内容
                        scope.key(model).val(CusEditView.this.getText()) ;
                    }
                }
            });
        }
    }
}
