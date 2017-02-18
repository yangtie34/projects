package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.UI.TreeView;
import com.chengyi.android.angular.UI.WindowPop;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.angular.entity.TreeEntity;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.CSS;

import java.util.List;

import static android.R.attr.value;
import static com.chengyi.android.angular.core.Scope.activity;
import static com.example.views.FormDataEntity.Type.spinner;
import static com.example.views.R.drawable.ic_sale_arrow_down;

/**
 * Created by Administrator on 2017/2/15.
 * params title.type
 */

public class FormDD extends ViewParent {
    public  static int width=400;
                public  static int height=100;
        private FormDataEntity formDataEntity;
        public FormDD(Scope parent,FormDataEntity formDataEntity) {
            super(parent);
            setData(formDataEntity.getId());
            setParams(formDataEntity.getName()+"."+formDataEntity.getType());
            setReturn(formDataEntity.getId());
            this.formDataEntity=formDataEntity;
            init();
        }
        public FormDD(Context context, AttributeSet attr) {
            super(context, attr);
        }
        @Override
        protected void init() {
            this.setOrientation(LinearLayout.VERTICAL);
            LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(layoutParams);
            this.setBackgroundColor(getResources().getColor(R.color.white));
            LayoutInflater inflater = LayoutInflater.from(activity);
            LinearLayout form_item = (LinearLayout) inflater.inflate(R.layout.form_item, null);
            TextView nameView= (TextView) form_item.findViewById(R.id.name);
            LinearLayout valueView= (LinearLayout) form_item.findViewById(R.id.value);
            TextView imageView=(TextView) form_item.findViewById(R.id.img);
            nameView.setText(formDataEntity.getName());
            this.addView(form_item);

            switch (formDataEntity.getType()){
                case FormDataEntity.Type.input:
                    imageView.setVisibility(INVISIBLE);
                    InputText inputText=new InputText(scope,getData(),getReturn());

                    if(formDataEntity.isView()){
                        inputText.setEnabled(false);
                        scope.key(getData()).val(new StringBuffer(formDataEntity.getThisData()));
                    }else if(formDataEntity.isEdit()){
                        scope.key(getData()).val(new StringBuffer(formDataEntity.getThisData()));
                    }
                    valueView.addView(inputText);
                    break;
                case FormDataEntity.Type.date:
                    final TextView dateTextView =getTextView();
                    valueView.addView(dateTextView);
                    String return_="date"+Scope.getId();
                    final DateView dateView= new DateView(scope,null,return_);
                    if(formDataEntity.isEdit()||formDataEntity.isAdd())
                        valueView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dateView.show();
                        }
                    });
                    scope.key(return_).watch(new DataListener() {
                        @Override
                        public void hasChange(Object o) {
                            formDataEntity.setThisData(o.toString());
                            dateTextView.setText(formDataEntity.getThisData());
                        }
                    });
                    break;
                case FormDataEntity.Type.list:

                    final TextView listTextView =getTextView();
                    valueView.addView(listTextView);

                    View conditionItems = inflater.inflate(R.layout.filter_condition_item, null);
                    final WindowPop cIWP=new WindowPop(conditionItems, Gravity.RIGHT);
                    conditionItems.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cIWP.hide();
                        }
                    });
                    TextView conditionItemName= (TextView) conditionItems.findViewById(R.id.name);
                    conditionItemName.setText("选择"+formDataEntity.getName());
                    LinearLayout conditionItemsLayout= (LinearLayout) conditionItems.findViewById(R.id.items);
                    List<TreeEntity> dataList=  formDataEntity.getData();
                    for (int j = 0; j < dataList.size(); j++) {
                        TreeEntity itemMap=dataList.get(j);
                        View item = inflater.inflate(R.layout.item, null);
                        TextView itemName= (TextView) item.findViewById(R.id.name);
                        itemName.setText(itemMap.getName());
                        item.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                formDataEntity.setThisData(((TextView)ActivityUtil.getViewByIndex(v,0)).getText().toString());
                                listTextView.setText(formDataEntity.getThisData());
                                cIWP.hide();
                            }
                        });
                        conditionItemsLayout.addView(item);
                    }
                    valueView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cIWP.showFullScreen();
                        }
                    });
                    if(formDataEntity.isView()){
                        valueView.setEnabled(false);
                        listTextView.setText(formDataEntity.getThisData());
                    }else if(formDataEntity.isEdit()){
                        listTextView.setText(formDataEntity.getThisData());
                    }

                    break;
                case spinner:
                    imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_sale_arrow_down));
                    final TextView spinnerTextView =new TextView(scope.activity);
                    spinnerTextView.setLayoutParams(CSS.LinearLayoutParams.wrapHeight());
                    spinnerTextView.setText("单位：");
                    final InputText spinnerinputText=new InputText(scope,getData(),getReturn());
                    if(formDataEntity.isView()){
                        spinnerinputText.setEnabled(false);
                        scope.key(getData()).val(new StringBuffer(formDataEntity.getThisData()));
                    }else if(formDataEntity.isEdit()){
                        scope.key(getData()).val(new StringBuffer(formDataEntity.getThisData()));
                    }
                    scope.key(getReturn()).addWatch(new DataListener<StringBuffer>() {
                        @Override
                        public void hasChange(StringBuffer o) {
                            formDataEntity.setThisData(o.toString());
                        }
                    });

                    valueView.addView(spinnerinputText);
                    valueView.addView(spinnerTextView);
                    TreeView spinnertreeView=new TreeView(scope,"spinnerData","spinnerReturn");
                    TreeEntity spinnertreeEntity=new TreeEntity();
                    spinnertreeEntity.setChildrenList(formDataEntity.getData());
                    scope.key("spinnerData").val(spinnertreeEntity);
                    final WindowPop windowPopOption=new WindowPop(spinnertreeView,imageView);
                    scope.key("spinnerReturn").watch(new DataListener<TreeEntity>() {
                        @Override
                        public void hasChange(TreeEntity o) {
                            windowPopOption.hide();
                            formDataEntity.setThisData_(o.getId());
                            spinnerTextView.setText("单位："+o.getName());
                        }
                    });
                    if(formDataEntity.isView()){
                        spinnerTextView.setText(formDataEntity.getThisData());
                        imageView.setEnabled(false);
                    }else if(formDataEntity.isEdit()){
                        spinnerTextView.setText(formDataEntity.getThisData());
                    }
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            windowPopOption.showNoMask();
                        }
                    });
                    break;

            }

            View line_horizontal = inflater.inflate(R.layout.view_line_horizontal, null);
            form_item.addView(line_horizontal);
        }
    private TextView getTextView(){
        TextView titleView =new TextView(scope.activity);
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setEms(80);
        titleView.setLayoutParams(CSS.LinearLayoutParams.widthHeight(width,height));
        titleView.setPadding(50,0,0,50);
        return titleView;
    }

}
