package com.yiyun.wasteoilcustom.activities;

import android.os.Bundle;
import android.view.View;

import com.chengyi.android.angular.UI.Loadding;
import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.entity.TreeEntity;
import com.chengyi.android.util.Convert;
import com.example.views.SwitchDeleteList;
import com.yiyun.wasteoilcustom.AppUser;
import com.yiyun.wasteoilcustom.R;
import com.yiyun.wasteoilcustom.bll.Category_BLL;
import com.yiyun.wasteoilcustom.bll.ProdRec_BLL;
import com.yiyun.wasteoilcustom.bll.SysPublic_BLL;
import com.yiyun.wasteoilcustom.util.ConditionUtil;
import com.yiyun.wasteoilcustom.viewModel.Condition;
import com.yiyun.wasteoilcustom.viewModel.Order;
import com.yiyun.wasteoilcustom.viewModel.Table;
import com.yiyun.wasteoilcustom.viewModel.TableItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chengyi.android.util.ActivityUtil.alert;

/**
 * Created by Administrator on 2017/2/15.
 */
public class RecReport extends AngularActivity {
    private Table table;
    private Condition condition;
    private Map<String,Object> conditionResult;
    private List<HashMap<String,Object>> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        table=new Table();
        Loadding.show();
        table.setTitle(getResources().getString(R.string.recReport));
        new Thread() {
            public void run() {
                long comId=AppUser.getInstance().getSysComID();
                final List<TreeEntity> listCategory= Category_BLL.GetProductCategory(comId);//危废种类
                final List<TreeEntity> listProShape=  SysPublic_BLL.GetProduct_Shape();//危废形态
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initCondition(listCategory,listProShape);
                        condition.initData();
                    }
                });
            }
        }.start();

    }
    //初始化筛选条件
    private void initCondition(List<TreeEntity> listCategory,List<TreeEntity> listProShape){

        Condition.DataAll conditionData= new Condition.DataAll();
        conditionData.setTitle(getResources().getString(R.string.schedule)+"筛选");//筛选页面抬头
        List<Condition.DataItem> conditionDataList =new ArrayList<>();
        conditionDataList.add(ConditionUtil.getConditionDate("VehDispTimeStart","开始日期"));//筛选项开始
        conditionDataList.add(ConditionUtil.getConditionDate("VehDispTimeEnd","结束日期"));
        conditionDataList.add(ConditionUtil.getConditionList("ProCategory","危废种类",listCategory));
        conditionDataList.add(ConditionUtil.getConditionList("ProShape","危废形态",listProShape));//筛选项结束

        conditionData.setList(conditionDataList);

        scope.key("condition").val(conditionData);
        condition=new Condition(scope,"condition","conditionResult");
        scope.key("conditionResult").watch(new DataListener<Map<String,Object>>() {
            @Override
            public void hasChange(Map<String,Object> result) {
                conditionResult=result;
                getListData();
            }
        });
        table.setFilterPage(condition.getWinPopView());
        table.setAddPage(new Order(R.layout.report_add).getAddPage());
    }
    //根据筛选条件加载数据
    private void getListData(){

        //加载数据开始
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err","0")
                    .put("SysComID", Convert.ToString(AppUser.getInstance().getSysComID()))
                    .put("RecTimeStart",Convert.ToString(conditionResult.get("RecTimeStart")))
                    .put("RecTimeEnd", Convert.ToString(conditionResult.get("RecTimeEnd")));
            TreeEntity ProCategory=(TreeEntity)conditionResult.get("ProCategory");
            String ProCategoryName=ProCategory!=null?ProCategory.getName():"";
            jsonObject.put("ProCategory", ProCategoryName);
            TreeEntity ProShape=(TreeEntity)conditionResult.get("ProShape");
            String ProShapeName=ProShape!=null?ProShape.getName():"";
            jsonObject.put("ProShape", ProShapeName);

            jsonObject.put("CreateComBrID", Convert.ToString(AppUser.getInstance().getSysComBrID()));

            JSONObject jsonHeader = new JSONObject();
            jsonHeader.put("Condition", jsonObject);
            String jsonStr = jsonHeader.toString();
            scope.key("ProdRec_Select").watch(new DataListener<List<HashMap<String,Object>>>() {
                @Override
                public void hasChange(List<HashMap<String,Object>> l) {
                    RecReport.this.list=l;
                    getViewByData();
                    Loadding.hide();
                }
            });
            ProdRec_BLL.ProdRec_Select(jsonStr,0,20);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //根据加载数据加载页面
    private void getViewByData(){
        List<View> tableList=new ArrayList<>();
        for (int i = 0; i <list.size(); i++) {
            HashMap<String, Object> map=list.get(i);
            View itemView=new TableItem(
                    map.get("RecTime").toString(),
                    map.get("ProCategoryName").toString(),
                    map.get("ProShapeName").toString(),
                    map.get("ProNumber").toString()
            ).getItemView();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查询详情
                    alert("查询详情");
                }
            });
            tableList.add(itemView);
        }

        scope.key("tableView").val(tableList);
        SwitchDeleteList switchDeleteList=new SwitchDeleteList(scope,"tableView");
        table.setTableView(switchDeleteList);
    }
}
