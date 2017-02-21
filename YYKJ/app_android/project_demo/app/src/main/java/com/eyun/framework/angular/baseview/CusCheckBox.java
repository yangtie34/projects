package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.project_demo.R;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusCheckBox extends CheckBox {

    private Scope scope;

    public CusCheckBox(Context context) {
        super(context);
    }

    public CusCheckBox(Context context, AttributeSet attrs) {
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
}
