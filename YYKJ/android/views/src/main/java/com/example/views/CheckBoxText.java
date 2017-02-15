package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.util.CSS;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CheckBoxText extends ViewParent {

        public CheckBoxText(Scope parent,String Data, String params,String Return) {
            super(parent);
            setData(Data);
            setParams(params);
            setReturn(Return);
            init();
        }
        public CheckBoxText(Context context, AttributeSet attr) {
            super(context, attr);
        }
        @Override
        protected void init() {
            final View check = new View(scope.activity);
            LinearLayout.LayoutParams lp = CSS.LinearLayoutParams.widthHeight(35, 35);
            check.setLayoutParams(lp);
            lp.gravity=Gravity.CENTER_VERTICAL;
            check.setBackground(getResources().getDrawable(R.drawable.edit_check_selector));
            this.addView(check);
            TextView textView=new TextView(scope.activity);
            textView.setTextSize(10);
            textView.setText(" "+getParams());
            textView.setTextColor(scope.activity.getResources().getColor(R.color.lightgray));
            this.addView(textView);
            boolean initCheck = (this.getData()!=null&&(Boolean) scope.key(this.getData()).val()) || false;
            this.setSelected(initCheck);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    check.setSelected(!check.isSelected());
                    CheckBoxText.this.setReturn(check.isSelected());
                }
            });
        }

}
