package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.util.CSS;

/**
 * Created by Administrator on 2017/2/15.
 */

public class SwitchCheck extends ViewParent {

        public SwitchCheck(Scope parent, String Data, String Return) {
            super(parent);
            setData(Data);
            setReturn(Return);
            init();
        }
        public SwitchCheck(Context context, AttributeSet attr) {
            super(context, attr);
        }
        @Override
        protected void init() {
            View check = new View(scope.activity);
            LinearLayout.LayoutParams lp = CSS.LinearLayoutParams.widthHeight(90, 40);
            check.setLayoutParams(lp);
            check.setBackground(getResources().getDrawable(R.drawable.password_bg_selector));
            this.addView(check);
            boolean initCheck = (this.getData()!=null&&(Boolean) scope.key(this.getData()).val()) || false;
            check.setSelected(initCheck);
            check.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    SwitchCheck.this.setReturn(v.isSelected());
                }
            });
        }

}
