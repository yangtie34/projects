package com.chengyi.android.angular.UI;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengyi.android.angular.R;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.angular.entity.TreeEntity;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.CSS;

import java.util.List;

import static com.chengyi.android.angular.core.Scope.activity;


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
        this.setBackgroundColor(getResources().getColor(R.color.general_grey_light));
        this.setPadding(5,5,5,5);
        scope.key(this.getData()).watch(new DataListener<TreeEntity>() {
            @Override
            public void hasChange(TreeEntity treeEntity) {
               // TreeView.this.addView(getViewByTreeEntity(treeEntity));
               createViews(TreeView.this,treeEntity);
            }
        });

    }
    protected View getViewByTreeEntity(final TreeEntity treeEntity){
        TextView button=new TextView(scope.activity);
        button.setText(treeEntity.getName());
        button.setLayoutParams(CSS.LinearLayoutParams.wrapAll());
        button.setPadding(5,5,5,5);
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
        LayoutInflater inflater = LayoutInflater.from(activity);
        LinearLayout linearLayout= ActivityUtil.getWrapLinearLayout();
        linearLayout.setPadding(10,5,5,5);
        List<TreeEntity> list=treeEntity.getChildrenList();
        for(int i=0;i<list.size();i++){
            linearLayout.addView(getViewByTreeEntity(list.get(i)));
            //linearLayout.addView(inflater.inflate(R.layout.view_line_horizontal, null));
            if(list.get(i).getChildrenList().size()>0){
                createViews(linearLayout,list.get(i));
            }
        }
        view.addView(linearLayout);
    }

}
