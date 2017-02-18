package com.yiyun.wasteoilcustom.viewModel;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.UI.WindowPop;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.PubInterface;
import com.example.views.FormDD;
import com.example.views.FormDataEntity;
import com.yiyun.wasteoilcustom.R;

import java.util.List;

import static com.chengyi.android.angular.core.Scope.activity;

/**
 * Created by Administrator on 2017/2/15.
 */

public class Order {
    private LinearLayout orderLayout;
    private WindowPop addPage;
    private TextView title;
    private View ok;
    private View edit;
    private PubInterface addOK;
    private PubInterface editOK;
    private boolean okadd;
    private List<FormDataEntity> list;
    public Order(){
        LayoutInflater inflater = LayoutInflater.from(activity);
        View orderView = inflater.inflate(R.layout.filter, null);
        orderLayout= (LinearLayout) orderView.findViewById(R.id.filter_conditions);
        View back= orderView.findViewById(R.id.back);
        title= (TextView) orderView.findViewById(R.id.title);
         ok= orderView.findViewById(R.id.ok);
         edit= orderView.findViewById(R.id.edit);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(okadd){
                    getAddOK().run();
                }else{
                    getEditOK().run();
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok.setVisibility(View.VISIBLE);
                getEditInfo().getEditInfo().getWindow().showFullScreen();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scope.activity.onBackPressed();
            }
        });
        addPage=new WindowPop(orderView, Gravity.RIGHT);
    }

    public Order setTitle(String title_){
        title.setText(title_);
        return  this;
    }
    public Order getAddInfo(){
        edit.setVisibility(View.INVISIBLE);
        okadd=true;
        ActivityUtil.clear(orderLayout);
        for (int i = 0; i <list.size() ; i++) {
            FormDataEntity formDataEntity=list.get(i);
            formDataEntity.setView(false);
            formDataEntity.setEdit(false);
            formDataEntity.setAdd(true);
            orderLayout.addView(new FormDD(Scope.activity.scope,formDataEntity));
        }
        return this;
    }
    public Order getEditInfo(){
        edit.setVisibility(View.INVISIBLE);
        okadd=false;
        ActivityUtil.clear(orderLayout);
        for (int i = 0; i <list.size() ; i++) {
            FormDataEntity formDataEntity=list.get(i);
            formDataEntity.setView(false);
            formDataEntity.setAdd(false);
            formDataEntity.setEdit(true);
            orderLayout.addView(new FormDD(Scope.activity.scope,formDataEntity));
        }
        return this;
    }
    public Order getOrderInfo(){
        edit.setVisibility(View.VISIBLE);
        ok.setVisibility(View.INVISIBLE);
        ActivityUtil.clear(orderLayout);
        for (int i = 0; i <list.size() ; i++) {
            FormDataEntity formDataEntity=list.get(i);
            formDataEntity.setEdit(false);
            formDataEntity.setAdd(false);
            formDataEntity.setView(true);
            orderLayout.addView(new FormDD(Scope.activity.scope,formDataEntity));
        }
        return this;
    }
    public WindowPop getWindow(){
        return addPage;
    }

    public Order setList(List<FormDataEntity> list) {
        this.list = list;
        return this;
    }
    public List<FormDataEntity> getList(){
        return list;
    }
    public PubInterface getAddOK() {
        return addOK;
    }

    public void setAddOK(PubInterface addOK) {
        this.addOK = addOK;
    }

    public PubInterface getEditOK() {
        return editOK;
    }

    public void setEditOK(PubInterface editOK) {
        this.editOK = editOK;
    }
}
