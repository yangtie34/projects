package com.chengyi.android.angular.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.R;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewData;
import com.chengyi.android.angular.core.ViewParent;

/**
 * Data:"String"
 * params:"checkBox"||"checkButton"
 */

public class CheckView extends ViewParent {

    public CheckView(Scope parent, String Data, String params, String Return){
        super(parent);
        setData(Data);
        setParams(params);
        setReturn(Return);
        init();
    }
    public CheckView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    @Override
    protected void init() {
        final ViewData textData=new ViewData();
        switch (CheckView.this.getParams()){
            case "checkBox":
                final CheckBox checkBox=new CheckBox(scope.activity);
                checkBox.setClickable(false);
                CheckView.this.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBox.setChecked(!checkBox.isChecked());
                        CheckView.this.setReturn(checkBox.isChecked());
                    }
                });
                CheckView.this.addView(checkBox);
                TextView textView=new TextView(scope.activity);
                scope.bind(textView,textData);
                CheckView.this.addView(textView);
                break;
            case "checkButton":
                Button button= new Button(scope.activity);

                scope.bind(button,textData);

                CheckView.this.addView(button);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textData.setCheck(!textData.isCheck());
                        CheckView.this.setReturn(textData.isCheck());
                        if(textData.isCheck()){
                            v.setBackground(scope.activity.getResources().getDrawable(R.drawable.sharp_boder));
                        }else{
                            v.setBackground(scope.activity.getResources().getDrawable(R.drawable.none_bg));
                        }
                    }
                });
                break;
        }
        scope.key(this.getData()).watch(new DataListener<String>() {
            @Override
            public void hasChange(String text ) {
                textData.setText(text);
            }
        });
    }
}
