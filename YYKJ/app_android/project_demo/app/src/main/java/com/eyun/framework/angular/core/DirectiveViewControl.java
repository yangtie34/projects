package com.eyun.framework.angular.core;

/**
 * Created by Administrator on 2017/2/27.
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eyun.framework.angular.baseview.CusLinearLayout;
import com.eyun.framework.angular.baseview.CusSwitchCheck;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;

import java.util.List;

import static com.eyun.framework.angular.core.DirectiveUtil.newNg_modelFormat;

public class DirectiveViewControl {

    private View view;

    private Scope scope;

    private String ng_repeat;

    private String ng_Show;

    private String ng_enabled;

    private String ng_model;

    private String modelExpression;

    private String[] models = new String[]{};

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getNg_repeat() {
        return ng_repeat;
    }

    public void setNg_repeat(String ng_repeat) {
        this.ng_repeat = ng_repeat;
        if (StringUtils.hasLength(ng_repeat)&&  (view  instanceof ViewGroup ==false)) {//有ng_repeat
            ViewGroup viewParent= (ViewGroup) view.getParent();
            int viewIndex = viewParent.indexOfChild(view);

            viewParent.removeViewAt(viewIndex);
            final CusLinearLayout cusLinearLayout=DirectiveUtil.getCusLinearLayout();
            cusLinearLayout.setOrientation(((LinearLayout)viewParent).getOrientation());
            viewParent.addView(cusLinearLayout,viewIndex);
            String[] repeatD = ng_repeat.split(" in ");
            if (repeatD.length != 2 || !StringUtils.hasLength(repeatD[0]) || !StringUtils.hasLength(repeatD[1])) {
                throw new IllegalArgumentException("ng_repeat Directive is wrong");
            }

            final String listStr = repeatD[1].trim();
            final String childrenStr = repeatD[0].trim();
            scope.key(listStr).addWatch(new DataListener<List<Object>>() {
                @Override
                public void hasChange(List<Object> list) {
                    ViewUtil.clear(cusLinearLayout);
                    //scopeRepeat.key(listStr).valInScope(list);
                    for (int i = 0; i < list.size(); i++) {
                        Object o = list.get(i);
                        String newNg_model = newNg_modelFormat(getNg_model(), childrenStr, listStr + "[" + i + "]");
                        newNg_model = StringUtils.replace(newNg_model, "$index", "'" + i + "'");
                        DirectiveViewControl directiveViewControlc = new DirectiveViewControl();
                        View viewc = ViewUtil.clone(view);
                        directiveViewControlc.setView(viewc);
                        directiveViewControlc.setScope(scope);
                        directiveViewControlc.setNg_model(newNg_model);
                        cusLinearLayout.addView(viewc);
                    }
                }
            });
        }
    }

    public String getNg_model() {
        return ng_model;
    }

    public void setNg_model(final String ng_model) {
        this.ng_model = ng_model;
        String[] ng_modelFormat = DirectiveUtil.ng_modelFormat(ng_model);
        setModelExpression(ng_modelFormat[0]);
        String[] ng_models = ng_modelFormat[1].split(",");
        setModels(ng_models);

        if (view instanceof CheckBox) {
            scope.key(ng_model).addWatch(new DataListener() {
                @Override
                public void hasChange(Object o) {
                    ((CheckBox) view).setChecked((Boolean) o);
                }
            });
            ((CheckBox) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if (isChecked) {
                        scope.key(ng_model).val(true);
                    } else {
                        scope.key(ng_model).val(false);
                    }
                }
            });
        } else if (view instanceof EditText) {
            ((EditText) view).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {// 此处为得到焦点时的处理内容

                    } else {// 此处为失去焦点时的处理内容
                        scope.key(ng_model).val(((EditText) view).getText().toString().trim());
                    }
                }
            });
        } else if (view instanceof CusSwitchCheck) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    scope.key(ng_model).val(v.isSelected());
                }
            });
        }
    }

    public String getModelExpression() {
        return modelExpression;
    }

    public void setModelExpression(String modelExpression) {
        this.modelExpression = modelExpression;
    }

    public String[] getModels() {
        return models;
    }

    public void setModels(String[] models) {
        this.models = models;
        if (view instanceof CheckBox){

        }else if (view instanceof TextView) {
            for (int j = 0; j < models.length; j++) {
                String model = models[j];
                scope.key(model).addWatch(new DataListener() {
                    @Override
                    public void hasChange(Object o) {
                        ((TextView) view).setText(
                                DirectiveUtil.modelChange(DirectiveViewControl.this));
                    }
                });
            }
        }
    }

    public String getNg_Show() {
        return ng_Show;
    }

    public void setNg_Show(final String ng_Show) {
        if(!StringUtils.hasLength(ng_Show))return;
        this.ng_Show = ng_Show;
        switch (ng_Show){
            case "false":
                hide();
                break;
            case "true":
                show();
                break;
            default:
               String split="null";
                if(ng_Show.split("==").length==2){
                    split="==";
                }else if(ng_Show.split("!=").length==2){
                    split="!=";
                }
                final String [] ngshows=ng_Show.split(split);
                final String spli=split;
                for (int i = 0; i <ngshows.length ; i++) {
                    String show=ngshows[i];
                    if(!show.startsWith("'"))
                    scope.key(show).watch(new DataListener<Object>() {
                        @Override
                        public void hasChange(Object o) {
                            forNgshow(ngshows,spli);

                        }
                    });
                }
                break;
        }
    }
    private void forNgshow(String[] ngshows, String spli){
        boolean bool=false;
        if(spli.equalsIgnoreCase("null")){
            bool= (boolean) scope.key(ngshows[0]).val();
        }else if(spli.equalsIgnoreCase("==")){
            if(ngshows[0].startsWith("'")){
                bool=scope.key(ngshows[1]).val().toString().equalsIgnoreCase(StringUtils.replace(ngshows[0],"'",""));
            }else if(ngshows[1].startsWith("'")){
                bool=scope.key(ngshows[0]).val().toString().equalsIgnoreCase(StringUtils.replace(ngshows[1],"'",""));
            }else{
                bool=scope.key(ngshows[0]).val().toString().equalsIgnoreCase(scope.key(ngshows[1]).val().toString());
            }
        }else if(spli.equalsIgnoreCase("!=")){
            if(ngshows[0].startsWith("'")){
                bool=!scope.key(ngshows[1]).val().toString().equalsIgnoreCase(StringUtils.replace(ngshows[0],"'",""));
            }else if(ngshows[1].startsWith("'")){
                bool=!scope.key(ngshows[0]).val().toString().equalsIgnoreCase(StringUtils.replace(ngshows[1],"'",""));
            }else{
                bool=!scope.key(ngshows[0]).val().toString().equalsIgnoreCase(scope.key(ngshows[1]).val().toString());
            }
        }
        if (bool == true) {
            show();
        } else {
            hide();
        }
    }
    private void show(){
        this.view.setVisibility(View.VISIBLE);
    }
    private void hide(){
        this.view.setVisibility(View.GONE);
    }

    public String getNg_enabled() {
        return ng_enabled;
    }

    public void setNg_enabled(String ng_enabled) {
        this.ng_enabled = ng_enabled;
        if(!StringUtils.hasLength(ng_enabled))return;
        switch (ng_enabled){
            case "false":
                this.view.setEnabled(false);
                break;
            case "true":
                this.view.setEnabled(true);
                break;
            default:
               scope.key(ng_enabled).watch(new DataListener<Boolean>() {
                   @Override
                   public void hasChange(Boolean bool) {
                       if(bool==true){
                           view.setEnabled(true);
                       }else if(bool==false){
                           view.setEnabled(false);
                       }
                   }
               });
                break;
        }
    }
}
