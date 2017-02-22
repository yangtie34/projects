package com.eyun.framework.angular.core;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eyun.framework.Constant;
import com.eyun.framework.angular.baseview.CusLinearLayout;
import com.eyun.framework.util.ActivityUtil;
import com.eyun.framework.util.ThreadUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.project_demo.R;

import java.security.PublicKey;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/22.
 */

public class Directive {

    public static void forBaseView( BaseView baseView, AttributeSet attrs) {
        baseView.setScope(Scope.init(Scope.inflateScope, baseView));
         TypedArray typedArray = Scope.activity.obtainStyledAttributes(attrs, R.styleable.ViewParent);//TypedArray是一个数组容器
        new BaseViewThread(baseView,typedArray).start();
    }
    static class BaseViewThread extends Thread{
        private TypedArray typedArray;
        private BaseView baseView;
        public BaseViewThread(BaseView baseView,TypedArray typedArray){
            this.baseView=baseView;
            this.typedArray=typedArray;
        }
        public void run() {
            if (((View) baseView).getParent() != null) {
                Scope.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Directive.forBaseViewThread(baseView, typedArray);
                    }
                });
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();
            }
        }
    }
    public static void forBaseViewThread( BaseView baseView, TypedArray typedArray) {
        //获取属性

        String ng_model = typedArray.getString(R.styleable.ViewParent_ng_model);

        String ng_repeat = typedArray.getString(R.styleable.ViewParent_ng_repeat);
        String[] ng_modelFormat = ng_modelFormat(ng_model);
        baseView.setModelExpression(ng_modelFormat[0]);
        final String[] ng_models = ng_modelFormat[1].split(",");
        if (StringUtils.hasLength(ng_repeat)) {//有ng_repeat
            String[] repeatD = ng_repeat.split(" in ");
            if (repeatD.length != 2 || !StringUtils.hasLength(repeatD[0]) || !StringUtils.hasLength(repeatD[1])) {
                throw new IllegalArgumentException("ng_repeat Directive is wrong");
            }
            String list = repeatD[1].trim();
             String children = repeatD[0].trim();
            String repeatId = String.valueOf(Scope.getId());
             ViewGroup parentView = (ViewGroup) (((View) baseView).getParent());
             int viewIndex = parentView.indexOfChild((View) baseView);
            baseView.getScope().parent.key(repeatId).key("viewIndex").val(viewIndex);
            baseView.getScope().parent.key(repeatId).key("parentView").val(parentView);
            parentView.removeView((View) baseView);


            if (baseView instanceof CusLinearLayout) {
                ((CusLinearLayout) baseView).getTreeView();
                Log.d("e1",baseView.toString());
                LinearLayout linearLayout=new LinearLayout(Scope.activity);
                linearLayout.setLayoutParams(Constant.layoutParamsWRAP);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                parentView.addView(linearLayout,viewIndex);
                baseView.getScope().parent.key(list).addWatch(new ListListener(linearLayout,baseView,0,children,ng_models));
            }else{
                baseView.getScope().parent.key(list).addWatch(new ListListener(parentView,baseView,viewIndex,children,ng_models));
            }


        } else {
            baseView.setScope(Scope.inflateScope);
            baseView.setModels(ng_models);
            for (int i = 0; i < ng_models.length; i++) {
                baseView.setModel(ng_models[i]);
            }
        }
        typedArray.recycle();//回收资源
    }
    static class ListListener implements DataListener<List<Object>>{
        private ViewGroup parentView;
        private String children;
        private int viewIndex;
        private BaseView baseView;
        private String[] ng_models;
        public ListListener(ViewGroup parent,BaseView baseView,int viewIndex,String children,String[] ng_models){
            this.parentView=parent;
            this.baseView=baseView;
            this.viewIndex=viewIndex;
            this.children=children;
            this.ng_models=ng_models;
        }
        @Override
        public void hasChange(List<Object> list) {
            for (int i = 0; i < list.size(); i++) {
                Object childrenV = list.get(i);
                if (baseView instanceof CusLinearLayout) {
                    CusLinearLayout baseView_ =((CusLinearLayout) baseView).deepClone();
                    Scope scope = Scope.init(baseView.getScope().parent, baseView_);
                    scope.key(children).val(childrenV);
                    scope.key("$index").val(i);
                    baseView_.scopeViews(baseView_,scope);
                    Log.d("d",baseView_.getChildAt(0).toString());
                    new AddView(parentView,baseView_,viewIndex+i).run();
                    String []colors=new String[]{"FF3030","FF1493","FF00FF","FAFAD2"};
                    parentView.getChildAt(viewIndex+i).setBackgroundColor(Color.parseColor("#"+colors[i]));
                } else {
                    BaseView baseView_ = baseView.clone();
                    Scope scope = Scope.init(baseView.getScope().parent, baseView_);
                    scope.key(children).val(childrenV);
                    scope.key("$index").val(i);
                    baseView_.setScope(scope);
                    parentView.addView((View) baseView_, viewIndex + i);

                    for (int j = 0; j < ng_models.length; j++) {
                        baseView_.setModel(ng_models[j]);
                    }
                }
            }
           ActivityUtil.getRootView().postInvalidate();
        }
    }
    static class AddView{
        private int index;
        private ViewGroup parent;
        private View view;
        public AddView(ViewGroup parent, View view,int index){
            this.index=index;
            this.parent=parent;
            this.view=view;
        }
        public void  run(){
           parent.addView(view);

        }
    }
    /**
     * 表达式格式化
     *
     * @param ng_model
     * @return
     */
    private static String[] ng_modelFormat(String ng_model) {
        String[] ng_modelFormat = new String[]{"", ""};
        if (ng_model == null) {
            return ng_modelFormat;
        } else if (ng_model.contains("+")) {
            String[] exps = ng_model.split("\\+");
            for (int i = 0; i < exps.length; i++) {
                String exp = exps[i].trim();
                if (exp.contains("'")) {
                    ng_modelFormat[0] +=StringUtils.replace(exp,"'", "");
                } else {
                    ng_modelFormat[0] += "{" + exp + "}";
                    ng_modelFormat[1] += exp + ",";
                }
            }

        } else {
            ng_modelFormat[0] = "{" + ng_model + "}";
            ng_modelFormat[1] = ng_model;
        }
        return ng_modelFormat;
    }

    /**
     * model变化获取显示字符串
     *
     * @param baseView
     * @return
     */
    public static String modelChange(BaseView baseView) {
        String p1 = "\\{[^\\{\\}]{1,}\\}";//没有确定{后面一定是(
        Pattern p = Pattern.compile(p1);
        String modelExpression = baseView.getModelExpression();
        Matcher m = p.matcher(modelExpression);
        List<String> models = new Vector<String>();
        while (m.find()) {
            String model = m.group();
            if (!models.contains(model)) {
                models.add(model);
            }
        }
        for (int i = 0; i < models.size(); i++) {
            String model = models.get(i);
            modelExpression = StringUtils.replace(modelExpression, model, baseView.getScope().key(model.replace("{", "").replace("}", "")).val().toString());
        }
        return modelExpression;
    }
}
