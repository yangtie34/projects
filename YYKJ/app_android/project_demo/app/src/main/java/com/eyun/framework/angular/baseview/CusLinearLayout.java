package com.eyun.framework.angular.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eyun.framework.angular.core.BaseView;
import com.eyun.framework.angular.core.Directive;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.TreeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CusLinearLayout extends LinearLayout implements BaseView {

    private Scope scope;

    public CusLinearLayout(Context context) {
        super(context);
    }

    public CusLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Directive.forBaseView(this,attrs);
    }

    @Override
    public void setModel(String model) {
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public void setScope(Scope scope) {
        this.scope=scope;
    }

    @Override
    public void setModelExpression(String modelExpression) {

    }

    @Override
    public String getModelExpression() {
        return null;
    }

    @Override
    public String[] getModels() {
        return null;
    }

    @Override
    public void setModels(String[] models) {

    }

    @Override
    public CusLinearLayout clone() {
        CusLinearLayout o = null;
        try {
            o = (CusLinearLayout) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public CusLinearLayout deepClone() {
           return  (CusLinearLayout)cloneViews(treeView);
    }
    private TreeEntity treeView;
    public void getTreeView(){
        treeView=getTreeView(this);
    }
    private  TreeEntity getTreeView(View v){
        //((BaseView)v).getScope().clear();
        TreeEntity treeEntity=new TreeEntity();
        treeEntity.setContent(v);
        if (v instanceof ViewGroup){
            ViewGroup viewGroup= (ViewGroup) v;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View viewc =  viewGroup.getChildAt(i);
                treeEntity.addChildren(getTreeView(viewc));
                viewGroup.removeView(viewc);
            }
        }
        return treeEntity;
    };

    private BaseView cloneViews(TreeEntity tree){
        BaseView view= ((BaseView) tree.getContent()).clone();
        if (tree.getChildrenList().size()>0){
            List<View> viewlist=new ArrayList<>();
            for (int i = 0; i <tree.getChildrenList().size() ; i++) {
                BaseView viewc=cloneViews(tree.getChildrenList().get(i));
                ((ViewGroup)view).addView((View) viewc);
            }
        }
        return view;
    }
    public void scopeViews(BaseView v,Scope scop){
        if(v==null)return;
        v.setScope(scop);
        if (v instanceof ViewGroup){
            ViewGroup viewGroup= (ViewGroup) v;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View viewc =  viewGroup.getChildAt(i);
                scopeViews((BaseView) viewc,scop);
            }
        }else{
            for (int j = 0; j < v.getModels().length; j++) {
                v.setModel( v.getModels()[j]);
            }
        }
    }
}
