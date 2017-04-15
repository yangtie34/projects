package com.eyun.project_demo.cusviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.NodeEntity;
import com.eyun.project_demo.R;

import java.util.List;


/**
 * data:NodeEntity
 * return :NodeEntity
 */

public class CusTreeSpinner extends AngularViewParent {
    public CusTreeSpinner(Scope parent, String Data) {
        super(parent);
        setData(Data);
        init();
    }
    public CusTreeSpinner(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void init() {
        scope.key(getData()).watch(new DataListener<NodeEntity>() {
            @Override
            public void hasChange(NodeEntity treeEntity) {
                fillView(treeEntity);

            }
        });
    }
    public boolean fillView(NodeEntity treeEntity){
        final List<NodeEntity> treeEntities=treeEntity.getChildrenList();
        if(treeEntities.size()==0){
            return true;
        }
        Spinner spinner= (Spinner) scope.inflate(R.layout.base_spinner);
        ArrayAdapter arrayAdapter=new ArrayAdapter(scope.activity,android.R.layout.simple_spinner_item,treeEntities);
        this.addView(spinner);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                NodeEntity treeEntity1=treeEntities.get(arg2);
                if(fillView(treeEntity1)){
                    CusTreeSpinner.this.setReturn(treeEntity1);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        return false;
    }

}
