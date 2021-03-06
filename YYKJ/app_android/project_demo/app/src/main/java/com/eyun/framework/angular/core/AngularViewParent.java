package com.eyun.framework.angular.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.eyun.framework.Constant;
import com.eyun.project_demo.R;

/**
 * Created by Administrator on 2016/10/26.
 */

public class AngularViewParent extends LinearLayout implements Cloneable {

    protected Scope scope;

    private String Data;

    private String params;

    private String state1;
    private String state2;
    private String state3;
    private String state4;
    private String state5;
    private String state6;

    private String Return;

    private String show;

    public AngularViewParent(Scope parent){
        super(Scope.activity);
        scope=Scope.init(parent,this);
        this.setLayoutParams(Constant.layoutParamsWRAP);
        this.setOrientation(LinearLayout.VERTICAL);
    }
    public AngularViewParent(Context context, AttributeSet attr) {
        super(context, attr);
        scope=Scope.init(Scope.activity.scope,this);
        //获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.ViewParent);//TypedArray是一个数组容器
        setData(typedArray.getString(R.styleable.ViewParent_ng_data));
        setParams(typedArray.getString(R.styleable.ViewParent_ng_params));
        setState1(typedArray.getString(R.styleable.ViewParent_ng_state1));
        setState2(typedArray.getString(R.styleable.ViewParent_ng_state2));
        setState3(typedArray.getString(R.styleable.ViewParent_ng_state3));
        setState4(typedArray.getString(R.styleable.ViewParent_ng_state4));
        setState5(typedArray.getString(R.styleable.ViewParent_ng_state5));
        setState6(typedArray.getString(R.styleable.ViewParent_ng_state6));
        setReturn(typedArray.getString(R.styleable.ViewParent_ng_return));
        setShow(typedArray.getString(R.styleable.ViewParent_ng_show));
        typedArray.recycle();//回收资源
        init();
    }

    protected void init(){

    }
    protected void setReturn(Object obj) {
        scope.key(getReturn()).val(obj);
    }

    protected void clear(){
       ((ViewGroup) this).removeAllViews();
    }
    public void setData(String data) {
        if(data==null)return;
        this.Data = data;
        if(scope.parent.key(data).val()!=null){
            scope.key(getData()).val(scope.parent.key(data).val());
        }
        scope.parent.key(data).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.key(getData()).val(o);
            }
        });
    }

    public void setState1(String state1) {
        if(state1==null)return;
        this.state1 = state1;
        scope.parent.key(state1).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.key(getState1()).val(o);
            }
        });
    }

    public void setState2(String state2) {
        if(state2==null)return;
        this.state2 = state2;
        scope.parent.key(state2).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.key(getState2()).val(o);
            }
        });
    }

    public void setState3(String state3) {
        if(state3==null)return;
        this.state3 = state3;
        scope.parent.key(state3).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.key(getState3()).val(o);
            }
        });
    }

    public void setState4(String state4) {
        if(state4==null)return;
        this.state4 = state4;
        scope.parent.key(state4).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.key(getState4()).val(o);
            }
        });
    }

    public void setState5(String state5) {
        if(state5==null)return;
        this.state5 = state5;
        scope.parent.key(state5).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.key(getState5()).val(o);
            }
        });
    }

    public void setState6(String state6) {
        if(state6==null)return;
        this.state6 = state6;
        scope.parent.key(state6).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.key(getState6()).val(o);
            }
        });
    }

    public void setReturn(String aReturn) {
        if(aReturn==null)return;
        this.Return = aReturn;
        scope.key(aReturn).watch(new DataListener() {
            @Override
            public void hasChange(Object o) {
                scope.parent.key(getReturn()).val(o);
            }
        });
    }
    public void setShow(String show) {
        if(show==null)return;
        this.show = show;
        switch (show){
            case "false":
                this.hide();
                break;
            case "true":
                this.show();
                break;
            default:
                scope.parent.key(show).watch(new DataListener<Boolean>() {
                    @Override
                    public void hasChange(Boolean o) {
                        if(o==true){
                            AngularViewParent.this.show();
                        }else{
                            AngularViewParent.this.hide();
                        }
                        scope.parent.lockSync();
                        scope.key(getShow()).val(o);
                        scope.parent.openSync();
                    }
                });
                scope.key(show).watch(new DataListener<Boolean>() {
                    @Override
                    public void hasChange(Boolean o) {
                        if(o==true){
                            AngularViewParent.this.show();
                        }else{
                            AngularViewParent.this.hide();
                        }
                        scope.parent.lockSync();
                        scope.parent.key(getShow()).val(o);
                        scope.parent.openSync();
                    }
                });
                break;
        }
    }
    public void setParams(String params) {
        if(params==null)return;
        this.params = params;
    }


    public String getData() {
        return Data;
    }

    public String getParams() {
        return params;
    }

    public String getState1() {
        return state1;
    }

    public String getState2() {
        return state2;
    }

    public String getState3() {
        return state3;
    }

    public String getState4() {
        return state4;
    }

    public String getState5() {
        return state5;
    }

    public String getState6() {
        return state6;
    }

    public String getReturn() {
        return Return;
    }
    public String getShow() {
        return show;
    }
    private void show(){
        this.setVisibility(View.VISIBLE);
    }
    private void hide(){
        this.setVisibility(View.GONE);
    }

    public Object clone() throws CloneNotSupportedException {
        return (Object) super.clone();
    }
}
