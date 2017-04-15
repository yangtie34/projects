package com.eyun.framework.angular.core;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.eyun.framework.angular.baseview.CusBaseView;
import com.eyun.framework.angular.baseview.CusLinearLayout;
import com.eyun.framework.angular.baseview.CusViewGroup;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.customXghScan.R;

import java.util.List;

import static com.eyun.framework.angular.core.DirectiveUtil.newNg_modelFormat;
import static com.eyun.framework.angular.core.DirectiveUtil.newNg_repeatFormat;

/**
 * Created by Administrator on 2017/2/27.
 */

public class DirectiveManager1 {

    public static void init(final View view, AttributeSet attrs) {
        TypedArray typedArray = Scope.activity.obtainStyledAttributes(attrs, R.styleable.ViewParent);//TypedArray是一个数组容器
        final String ng_model = typedArray.getString(R.styleable.ViewParent_ng_model);
        final String ng_repeat = typedArray.getString(R.styleable.ViewParent_ng_repeat);//以后完善
        typedArray.recycle();//回收资源
        //if(StringUtils.hasLength(ng_model)||StringUtils.hasLength(ng_repeat))
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (view.getParent() != null) {
                    final DirectiveViewControl directiveViewControl = new DirectiveViewControl();
                    directiveViewControl.setView(view);
                    directiveViewControl.setScope(Scope.inflateScope);
                    directiveViewControl.setNg_model(ng_model);
                    directiveViewControl.setNg_repeat(ng_repeat);
                    Scope.activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (view.getParent() instanceof CusViewGroup) {
                                ((CusViewGroup) view.getParent()).addChildren(directiveViewControl);
                            } else {
                                viewControl(directiveViewControl);
                            }
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
        }).start();
    }

    private static void viewControl(final DirectiveViewControl directiveViewControl) {
        final View view = directiveViewControl.getView();
        if (view instanceof CusViewGroup) {
            //viewCusGroupControl(directiveViewControl);
        } else {
            viewModelControl(directiveViewControl);
        }
    }

    private static void viewModelControl(final DirectiveViewControl directiveViewControl) {
        final View view = directiveViewControl.getView();
        final ViewGroup viewParent = (ViewGroup) view.getParent();
        final int viewIndex = viewParent.indexOfChild(view);

        String ng_repeat = directiveViewControl.getNg_repeat();
        if (StringUtils.hasLength(ng_repeat)) {//有ng_repeat
            final Scope scopeRepeat=Scope.init(directiveViewControl.getScope(),view);
            viewParent.removeView(view);
            final CusLinearLayout cusLinearLayout=new CusLinearLayout(Scope.activity);
            cusLinearLayout.setLayoutParams(viewParent.getLayoutParams());
            viewParent.addView(cusLinearLayout,viewIndex);
            String[] repeatD = ng_repeat.split(" in ");
            if (repeatD.length != 2 || !StringUtils.hasLength(repeatD[0]) || !StringUtils.hasLength(repeatD[1])) {
                throw new IllegalArgumentException("ng_repeat Directive is wrong");
            }
            final String listStr = repeatD[1].trim();
            final String childrenStr = repeatD[0].trim();
            directiveViewControl.getScope().key(listStr).addWatch(new DataListener<List<Object>>() {
                @Override
                public void hasChange(List<Object> list) {
                    ViewUtil.clear(cusLinearLayout);
                    //scopeRepeat.key(listStr).valInScope(list);
                    for (int i = 0; i < list.size(); i++) {
                        Object o = list.get(i);
                        String newNg_model = newNg_modelFormat(directiveViewControl.getNg_model(), childrenStr, listStr + "[" + i + "]");
                        newNg_model = StringUtils.replace(newNg_model, "$index", "'" + i + "'");
                        DirectiveViewControl directiveViewControlc = new DirectiveViewControl();
                        directiveViewControlc.setNg_model(newNg_model);
                        View viewc = ViewUtil.clone(view);
                        directiveViewControlc.setView(viewc);
                        directiveViewControlc.setScope(scopeRepeat);
                        cusLinearLayout.addView(viewc);
                        new ModelListner(directiveViewControlc).run();
                    }
                }
            });
        } else {
            new ModelListner(directiveViewControl).run();
        }
    }

    static class ModelListner implements DataListener {
        private DirectiveViewControl directiveViewControl;
        private View view;
        private String[] models;
        private Scope scope;
        private String model;

        public ModelListner(DirectiveViewControl directiveViewControl) {
            this.scope = directiveViewControl.getScope();
            this.view = directiveViewControl.getView();
            this.models = directiveViewControl.getModels();
            this.model=directiveViewControl.getNg_model();
            this.directiveViewControl = directiveViewControl;
        }

        @Override
        public void hasChange(Object o) {
            if (view instanceof TextView) {
                ((TextView) view).setText(
                        DirectiveUtil.modelChange(directiveViewControl));
            }else if(view instanceof CheckBox){
                ((CheckBox) view).setChecked((Boolean)o);
            }
        }

        public void run() {
            for (int j = 0; j < models.length; j++) {
                String model = models[j];
                scope.key(model).addWatch(this);
            }
            if(view instanceof CheckBox){
                ((CheckBox) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if(isChecked){
                            scope.key(model).val(true);
                        }else{
                            scope.key(model).val(false);
                        }
                    }
                });
            }else if (view instanceof EditText){
                ((EditText) view).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {// 此处为得到焦点时的处理内容

                        } else {// 此处为失去焦点时的处理内容
                            scope.key(model).val(((EditText) view).getText().toString().trim());
                        }
                    }
                });
            }
        }
    }

    private static void viewCusGroupControl(final DirectiveViewControl directiveViewControl) {
        final View view = directiveViewControl.getView();
        final ViewGroup viewParent = (ViewGroup) view.getParent();
        final int viewIndex = viewParent.indexOfChild(view);
        final List<DirectiveViewControl> listDC = ((CusViewGroup) view).getChildrens();
        String ng_repeat = directiveViewControl.getNg_repeat();
        if (StringUtils.hasLength(ng_repeat)) {
            final Scope scopeRepeat=Scope.init(directiveViewControl.getScope(),view);
            viewParent.removeView(view);
            String[] repeatD = ng_repeat.split(" in ");
            if (repeatD.length != 2 || !StringUtils.hasLength(repeatD[0]) || !StringUtils.hasLength(repeatD[1])) {
                throw new IllegalArgumentException("ng_repeat Directive is wrong");
            }
            final String listStr = repeatD[1].trim();
            final String childrenStr = repeatD[0].trim();

            directiveViewControl.getScope().key(listStr).addWatch(new DataListener<List<Object>>() {
                @Override
                public void hasChange(List<Object> list) {
                    ViewUtil.clear(viewParent);
                   // scopeRepeat.key(listStr).valInScope(list);
                    for (int i = 0; i < list.size(); i++) {
                        View viewc = ViewUtil.clone(view);
                        ((CusBaseView)viewc).setScope(scopeRepeat);
                        for (int j = 0; j < listDC.size(); j++) {
                            DirectiveViewControl directiveViewControlM = listDC.get(j);

                            DirectiveViewControl directiveViewControlc = new DirectiveViewControl();
                            directiveViewControlc.setNg_model(directiveViewControlM.getNg_model());
                            directiveViewControlc.setScope(scopeRepeat);
                            directiveViewControlc.setView(directiveViewControlM.getView());
                            directiveViewControlc.setNg_repeat(directiveViewControlM.getNg_repeat());

                            String newNg_repeat = newNg_repeatFormat(directiveViewControlc.getNg_repeat(), childrenStr, listStr + "[" + i + "]");
                            directiveViewControlc.setNg_repeat(newNg_repeat);
                            String newNg_model = newNg_modelFormat(directiveViewControlc.getNg_model(), childrenStr, listStr + "[" + i + "]");
                            newNg_model = StringUtils.replace(newNg_model, "$index", "'" + i + "'");

                            directiveViewControlc.setNg_model(newNg_model);
                            View viewdc = directiveViewControlc.getView();
                            directiveViewControlc.setView(ViewUtil.clone(viewdc));
                            ((ViewGroup) viewc).addView(directiveViewControlc.getView());
                            if (directiveViewControlc.getView() instanceof CusViewGroup) {
                                ((CusViewGroup) directiveViewControlc.getView()).setChildrens(((CusViewGroup) directiveViewControlM.getView()).getChildrens());
                                viewCusGroupControl(directiveViewControlc);
                            } else {
                                viewModelControl(directiveViewControlc);
                            }
                        }
                        viewParent.addView(viewc, viewIndex + i);
                    }
                }

            });
        } else {
            for (int j = 0; j < listDC.size(); j++) {
                DirectiveViewControl directiveViewControlM = listDC.get(j);
                DirectiveViewControl directiveViewControlc = new DirectiveViewControl();
                directiveViewControlc.setNg_model(directiveViewControlM.getNg_model());
                directiveViewControlc.setScope(directiveViewControlM.getScope());
                directiveViewControlc.setView(directiveViewControlM.getView());
                View viewdc = directiveViewControlc.getView();
                directiveViewControlc.setView(ViewUtil.clone(viewdc));
                ((ViewGroup) view).addView(directiveViewControlc.getView());
                if (directiveViewControlc.getView() instanceof CusViewGroup) {
                    ((CusViewGroup) directiveViewControlc.getView()).setChildrens(((CusViewGroup) directiveViewControlM.getView()).getChildrens());
                    viewCusGroupControl(directiveViewControlc);
                } else {
                    viewModelControl(directiveViewControlc);
                }
            }
        }

    }


}
