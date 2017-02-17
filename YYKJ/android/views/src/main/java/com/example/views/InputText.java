package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.util.CSS;

/**
 * Created by Administrator on 2017/2/15.
 */

public class InputText extends ViewParent {

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
            final EditText editText=new EditText(scope.activity);
            editText.setLayoutParams(CSS.LinearLayoutParams.widthHeight(50,100));
            editText.setText("45646464");
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

}
