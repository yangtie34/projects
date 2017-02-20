package com.yiyun.framework.angular.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yiyun.framework.angular.core.DataListener;
import com.yiyun.framework.angular.core.Scope;
import com.yiyun.framework.util.common.StringUtils;
import com.yiyun.project_demo.R;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusTextView extends TextView {

    private Scope scope;

    public CusTextView(Context context) {
        super(context);
    }

    public CusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewParent);//TypedArray是一个数组容器
        setModel(typedArray.getString(R.styleable.ViewParent_ng_model));
        typedArray.recycle();//回收资源
    }

    public void setModel(String model) {
        if(StringUtils.hasLength(model)){
            scope.key(model).watch(new DataListener() {
                @Override
                public void hasChange(Object o) {
                    CusTextView.this.setText(o.toString());
                }
            });
        }
    }
}