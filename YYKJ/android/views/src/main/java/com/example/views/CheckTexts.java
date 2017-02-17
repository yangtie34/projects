package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.angular.entity.TreeEntity;
import com.chengyi.android.util.CSS;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.layout_height;
import static android.R.attr.textColor;
import static android.R.attr.textSize;
import static android.R.id.list;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CheckTexts extends ViewParent {

        public CheckTexts(Scope parent, String Data,String Return) {
            super(parent);
            setData(Data);
            setReturn(Return);
            init();
        }
        private List<TreeEntity> resultList=new ArrayList<>();
        public CheckTexts(Context context, AttributeSet attr) {
            super(context, attr);
        }
        @Override
        protected void init() {
            LinearLayout linearLayout=new LinearLayout(scope.activity);
            linearLayout.setLayoutParams(CSS.LinearLayoutParams.wrapAll());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            List<TreeEntity> list= (List<TreeEntity>) scope.key(getData()).val();
            for (int i = 0; i < list.size(); i++) {
                final TreeEntity treeEntity=list.get(i);
                Button button=new Button(scope.activity);
                LayoutParams lp=CSS.LinearLayoutParams.matchAll();
                lp.setMargins(5,5,5,5);
                button.setLayoutParams(lp);
                button.setPadding(5,5,5,5);
                button.setBackgroundColor(getResources().getColor(R.color.grey));
                button.setTextSize(15);
                button.setText(treeEntity.getName());
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setSelected(v.isSelected());
                        int color=getResources().getColor(R.color.grey);
                        if(v.isSelected()){
                           resultList.add(treeEntity);
                            color=getResources().getColor(R.color.colorAccent);
                        }else{
                            resultList.remove(treeEntity);
                        }
                        setReturn(resultList);
                        v.setBackgroundColor(color);
                    }
                });

                linearLayout.addView(button);
                this.addView(linearLayout);
            }
        }

}
