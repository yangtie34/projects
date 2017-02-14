package com.chengyi.android.angular.UI;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.CSS;

import java.util.List;
import java.util.Map;

/**
 * 表单元件
 * Data:[{
 *     name:'',
 *     code:'',
 *     type:'',//'checkBox',''
 *     data:object
 * },...]
 * params:"title-change"||"title-view"  修改模式||查看模式
 * Return:[{
 *     code:'',
 *     value:MapObject
 * }...]
 */

public class FormView extends ViewParent {
    //key值
    public static String name="name";
    public static String code="code";
    public static String type="type";
    public static String data="data";
    //key值
    private List<Map<String,Object>> list;


    public FormView(Scope parent,String Data, String params, String Return){
        super(parent);
        setData(Data);
        setParams(params);
        setReturn(Return);
        init();
    }

    @Override
    protected void init() {
        scope.key(this.getData()).watch(new DataListener<List<Map<String,Object>>>() {
            @Override
            public void hasChange(List<Map<String,Object>> items) {
                FormView.this.list=items;
               createViews();
            }
        });


    }
    private void createViews(){
        TextView textView=new TextView(scope.activity);
        textView.setText(this.getParams().split("-")[0]);
        this.addView(textView);
        TableLayout tableLayout=new TableLayout(scope.activity);
        tableLayout.setLayoutParams(CSS.LayoutParams.wrapAll());
        for(int i=0;i<list.size();i++){
            tableLayout.addView(createRow(list.get(i)));
        }
        tableLayout.setEnabled(false);//不可编辑
        this.addView(tableLayout);
    }
    private TableRow createRow(Map<String,Object> map){
        /*{
            *     name:'',
            *     code:'',
            *     type:'',//'checkBox',''
            *     data:object
                * }*/
        TableRow tableRow=new TableRow(scope.activity);
        TextView textView=new TextView(scope.activity);
        textView.setText(map.get(this.name).toString());
        tableRow.addView(textView);
        View typeView=null;
        switch (map.get(this.type).toString()){
            case "input":
                typeView=new EditText(scope.activity);
                break;
            case "radio":
                typeView=new RadioGroup(scope.activity);
                List<String> list= (List<String>) map.get(this.data);
                for (int i = 0; i < list.size(); i++) {
                    RadioButton radioButton=new RadioButton(scope.activity);
                    radioButton.setText(list.get(i));
                    ((RadioGroup)typeView).addView(radioButton);
                }
                break;
            case "checkBox":
                typeView= ActivityUtil.getWrapLinearLayout();
                List<String> listcheckBox= (List<String>) map.get(this.data);
                for (int i = 0; i < listcheckBox.size(); i++) {
                    CheckBox checkBox=new CheckBox(scope.activity);
                    checkBox.setText(listcheckBox.get(i));
                    ((LinearLayout)typeView).addView(checkBox);
                }
                break;
        }
        tableRow.addView(typeView);
        return tableRow;
    }

}
