package com.yiyun.wasteoilcustom.activities;

import android.os.Bundle;
import android.view.View;

import com.chengyi.android.angular.UI.Loadding;
import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.angular.core.DataListener;
import com.example.views.SwitchDeleteList;
import com.yiyun.wasteoilcustom.R;
import com.yiyun.wasteoilcustom.viewModel.Table;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public class Schedule extends AngularActivity {
    private Table table;
    private String tableViewStr="tableView";
    public String listStr="listStr";
    private List<HashMap<String,Object>> list=new ArrayList<>();

    private List<View> tableList=new ArrayList<>();

    //Condition

    //Condition

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        table=new Table();
        table.setTitle(getResources().getString(R.string.schedule));
        getListView();


    }
    private void getListView(){
        Loadding.show();
        scope.key(listStr).watch(new DataListener<List<HashMap<String,Object>>>() {
            @Override
            public void hasChange(List<HashMap<String,Object>> l) {
                Schedule.this.list=l;
                getViewByData();
            }
        });
        getListData();
    }
    private void getListData(){
        //
    }

    private void getViewByData(){

        tableList=null;
        scope.key(tableViewStr).val(tableList);
        SwitchDeleteList switchDeleteList=new SwitchDeleteList(scope,tableViewStr);
        table.setTableView(switchDeleteList);
    }

    public void getOrderView(HashMap<String,Object> map){

    };
}
