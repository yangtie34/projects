package com.eyun.jybfreightscan.cusviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.NodeEntity;
import com.eyun.jybfreightscan.R;

import java.util.List;


/**
 * data:NodeEntity
 * return :NodeEntity
 */

public class CusTreeSpinner extends AngularViewParent {
    NodeEntity nodeEntity;
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
        nodeEntity=new NodeEntity("","请选择");
        scope.key(getData()).watch(new DataListener<NodeEntity>() {
            @Override
            public void hasChange(NodeEntity treeEntity) {
                fillView(CusTreeSpinner.this,treeEntity);

            }
        });
    }
    public boolean fillView(LinearLayout parent,NodeEntity treeEntity){
        final List<NodeEntity> treeEntities=treeEntity.getChildrenList();
        if(treeEntities.size()==0){
            return true;
        }else if(!treeEntities.contains(nodeEntity)){
            treeEntities.add(0,nodeEntity);
        }
        final LinearLayout linearLayout= (LinearLayout) scope.inflate(R.layout.base_spinner);
        Spinner spinner= (Spinner) linearLayout.findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter=new ArrayAdapter(scope.activity,android.R.layout.simple_spinner_item,treeEntities);
        spinner.setAdapter(arrayAdapter);
        parent.addView(linearLayout);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==0)return;
                NodeEntity treeEntity1=treeEntities.get(arg2);
                if(linearLayout.getChildCount()>1){
                    linearLayout.removeViewAt(1);
                }
                if(fillView(linearLayout,treeEntity1)){
                    CusTreeSpinner.this.setReturn(treeEntity1);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        return false;
    }

}
