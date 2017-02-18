package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.util.CSS;

/**
 * Created by Administrator on 2017/2/15.
 */

public class InputText extends ViewParent {
    private EditText editText;
        public InputText(Scope parent, String Data, String Return) {
            super(parent);
            setData(Data);
            setReturn(Return);
            init();
        }
        public InputText(Context context, AttributeSet attr) {
            super(context, attr);
        }
        @Override
        protected void init() {
            editText=new EditText(scope.activity);
            LayoutParams lp=CSS.LinearLayoutParams.widthHeight(FormDD.width, FormDD.height);
            lp.gravity=Gravity.CENTER_VERTICAL;
            this.setLayoutParams(lp);
            editText.setGravity(Gravity.CENTER_VERTICAL);
            editText.setSingleLine();
            editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.editsharp));
            //this.setWeightSum(1);
            scope.key(getData()).watch(new DataListener<StringBuffer>() {
                @Override
                public void hasChange(StringBuffer text) {
                    editText.setText(text);
                }
            });

            this.addView(editText);
            editText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                            // 此处为得到焦点时的处理内容
                    } else {
                            // 此处为失去焦点时的处理内容
                       setReturn(new StringBuffer(editText.getText()));
                    }
                }
            });
        }
    @Override
     public void setEnabled(boolean bool){
        editText.setEnabled(bool);
    };

}
