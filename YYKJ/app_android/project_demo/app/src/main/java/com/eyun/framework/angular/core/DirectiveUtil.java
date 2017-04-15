package com.eyun.framework.angular.core;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eyun.framework.angular.baseview.CusLinearLayout;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.project_demo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.eyun.project_demo.R.attr.ng_model;

/**
 * Created by Administrator on 2017/2/22.
 */

public class DirectiveUtil {

    /**
     * 表达式替换变量
     *
     * @param ng_model
     * @return
     */
    public static String newNg_modelFormat(String ng_model,String oldStr,String newStr) {
        String newNg_model="";
        if(ng_model==null) return newNg_model;
        String[] exps = ng_model.split("\\+");
        for (int i = 0; i < exps.length; i++) {
            newNg_model+="+";
            String exp = exps[i].trim();
            if (!exp.contains("'")&&exp.equalsIgnoreCase(oldStr)) {
                newNg_model+=newStr;
            }else{
                newNg_model+=exp;
            }
        }
        return newNg_model.replaceFirst("\\+","");
    };
    public static String newNg_repeatFormat(String ng_repeat,String oldStr,String newStr) {
        if(ng_repeat==null)return null;
        String newNg_model="";
        String[] exps = ng_repeat.split(" in ");
        if(exps[1].trim().equalsIgnoreCase(oldStr)){
            exps[1]=newStr;
        }
        return exps[0]+" in "+exps[1];
    };
    /**
     * 表达式格式化
     *
     * @param ng_model
     * @return
     */
    public static String[] ng_modelFormat(String ng_model) {
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
     * @param directiveViewControl
     * @return
     */
    public static String modelChange(DirectiveViewControl directiveViewControl) {
        String p1 = "\\{[^\\{\\}]{1,}\\}";//没有确定{后面一定是(
        Pattern p = Pattern.compile(p1);
        String modelExpression = directiveViewControl.getModelExpression();
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
            modelExpression = StringUtils.replace(modelExpression, model, TypeConvert.toString(directiveViewControl.getScope().key(model.replace("{", "").replace("}", "")).val()));
        }
        return modelExpression;
    }
    public static CusLinearLayout  getCusLinearLayout(){
        CusLinearLayout cusLinearLayout=new CusLinearLayout(Scope.activity);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return cusLinearLayout;
    }
}
