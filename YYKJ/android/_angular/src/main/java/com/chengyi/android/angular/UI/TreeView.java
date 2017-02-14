package com.chengyi.android.angular.UI;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.angular.entity.TreeEntity;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.CSS;

import java.util.List;


/**
 * 树状菜单
 * data:Treeentity
 * return:Treeentity
 *
 */
public class TreeView extends ViewParent {

    public TreeView(Scope parent,String Data, String Return){
        super(parent);
        setData(Data);
        setReturn(Return);
        init();
    }

    @Override
    protected void init() {
        scope.key(this.getData()).watch(new DataListener<TreeEntity>() {
            @Override
            public void hasChange(TreeEntity treeEntity) {
               // TreeView.this.addView(getViewByTreeEntity(treeEntity));
               createViews(TreeView.this,treeEntity);
            }
        });

    }
    protected View getViewByTreeEntity(final TreeEntity treeEntity){
        Button button=new Button(scope.activity);
        button.setText(treeEntity.getName());
        button.setLayoutParams(CSS.LayoutParams.wrapAll());
        button.setPadding(5,5,5,5);
        button.setBackgroundColor(Color.parseColor("#00ff00"));
       if(treeEntity.getChildrenList().size()==0)
           button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setReturn(treeEntity);
            }
        });
    return button;
    }
    protected void createViews(LinearLayout view, TreeEntity treeEntity){
        LinearLayout linearLayout= ActivityUtil.getWrapLinearLayout();
        linearLayout.setPadding(10,5,5,5);
        List<TreeEntity> list=treeEntity.getChildrenList();
        for(int i=0;i<list.size();i++){
            linearLayout.addView(getViewByTreeEntity(list.get(i)));
            if(list.get(i).getChildrenList().size()>0){
                createViews(linearLayout,list.get(i));
            }
        }
        view.addView(linearLayout);
    }

}
