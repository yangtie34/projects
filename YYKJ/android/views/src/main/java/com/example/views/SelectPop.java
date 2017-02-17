package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chengyi.android.angular.UI.WindowPop;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.angular.entity.TreeEntity;
import com.chengyi.android.util.CSS;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/2/15.
 */

public class SelectPop extends ViewParent {

        public SelectPop(Scope parent, String Data, String Return) {
            super(parent);
            setData(Data);
            setReturn(Return);
            init();
        }
        public SelectPop(Context context, AttributeSet attr) {
            super(context, attr);
        }
        @Override
        protected void init() {
            this.setOrientation(LinearLayout.HORIZONTAL);
            final TextView textView=new TextView(scope.activity);
            textView.setLayoutParams(CSS.LinearLayoutParams.wrapAll());
            Spinner spinner=new Spinner(scope.activity);
            spinner.setLayoutParams(CSS.LinearLayoutParams.wrapAll());
            this.addView(textView);
            this.addView(spinner);
            List<TreeEntity> list= (List<TreeEntity>) scope.key(getData()).val();
            LinearLayout popLinear=new LinearLayout(scope.activity);
            popLinear.setOrientation(LinearLayout.VERTICAL);
            final WindowPop windowPop=new WindowPop(popLinear,this);
            for (int i = 0; i <list.size() ; i++) {
                final TreeEntity treeEntity=list.get(i);
                TextView text=new TextView(scope.activity);
                text.setText(treeEntity.getName());
                text.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setReturn(treeEntity);
                        windowPop.hide();
                        textView.setText(treeEntity.getName());
                    }
                });
                popLinear.addView(text);
            }


            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    windowPop.show();
                }
            });
        }

}
