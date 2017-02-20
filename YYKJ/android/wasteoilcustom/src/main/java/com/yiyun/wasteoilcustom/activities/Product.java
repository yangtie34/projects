package com.yiyun.wasteoilcustom.activities;

import android.os.Bundle;
import android.view.View;

import com.chengyi.android.angular.UI.Loadding;
import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.angular.entity.ResultMsg;
import com.chengyi.android.angular.entity.TreeEntity;
import com.chengyi.android.util.Convert;
import com.chengyi.android.util.PubInterface;
import com.example.views.FormDataEntity;
import com.example.views.SwitchDeleteList;
import com.yiyun.wasteoilcustom.AppUser;
import com.yiyun.wasteoilcustom.R;
import com.yiyun.wasteoilcustom.bll.Category_BLL;
import com.yiyun.wasteoilcustom.bll.ProdRec_BLL;
import com.yiyun.wasteoilcustom.bll.SysPublic_BLL;
import com.yiyun.wasteoilcustom.bll.TransRec_BLL;
import com.yiyun.wasteoilcustom.model.ProduceRec_Model;
import com.yiyun.wasteoilcustom.util.ConditionUtil;
import com.yiyun.wasteoilcustom.viewModel.Condition;
import com.yiyun.wasteoilcustom.viewModel.Order;
import com.yiyun.wasteoilcustom.viewModel.Table;
import com.yiyun.wasteoilcustom.viewModel.TableItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chengyi.android.util.ActivityUtil.alert;

/**
 * Created by Administrator on 2017/2/15.
 */
public class Product extends AngularActivity {
    private Table table;
    private Condition condition;
    private Map<String,Object> conditionResult;
    private List<HashMap<String,Object>> list=new ArrayList<>();
    private Order order;
    private List<FormDataEntity> thisOrderData=new LinkedList<>();

    private  List<TreeEntity> listCategory;
    private  List<TreeEntity> listProShape;
    private  List<TreeEntity> listProPack;
    private  List<TreeEntity> listProMeasureUnit;
    private ArrayList<TreeEntity> listProHazardNature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        table=new Table();
        Loadding.show();
        table.setTitle(getResources().getString(R.string.recReport));
        new Thread() {
            public void run() {
                long comId=AppUser.getInstance().getSysComID();
                listCategory= Category_BLL.GetProductCategory(comId);//危废种类
                listProShape=  SysPublic_BLL.GetProduct_Shape();//危废形态
                //包装
                listProPack=SysPublic_BLL.GetProduct_Pack();
                //计量单位
                listProMeasureUnit = SysPublic_BLL.GetProduct_MeasureUnit();
                //危害特性
                listProHazardNature = SysPublic_BLL.GetProduct_HazardNature();
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
        conditionDataList.add(ConditionUtil.getConditionDate("ProTimeStart","开始日期"));//筛选项开始
        conditionDataList.add(ConditionUtil.getConditionDate("ProTimeEnd","结束日期"));
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
        order=new Order();
        getThisOrderData(null);
        table.setOrderPage(order);

        order.setAddOK(new PubInterface() {
            @Override
            public Object run() {
                add(order.getList());
                return null;
            }
        });
        order.setEditOK(new PubInterface() {
            @Override
            public Object run() {
                update(order.getList());
                return null;
            }
        });
    }
    private void getThisOrderData(HashMap<String, Object> map){
        thisOrderData.clear();
        String key="RecNumber";
        thisOrderData.add(new FormDataEntity(key,"单据编号",FormDataEntity.Type.view,null,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="UseProName";
        thisOrderData.add(new FormDataEntity(key,"产品名称",FormDataEntity.Type.input,null,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="UseProSpec";
        thisOrderData.add(new FormDataEntity(key,"产品规格",FormDataEntity.Type.input,null,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="UseProModel";
        thisOrderData.add(new FormDataEntity(key,"产品型号",FormDataEntity.Type.input,null,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="UseProNumber";
        thisOrderData.add(new FormDataEntity(key,"使用数量",FormDataEntity.Type.spinner,listProMeasureUnit,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        thisOrderData.get(thisOrderData.size()-1).setThisData_(map!=null&&map.get(key)!=null?map.get("UseProMeasureUnitName").toString():"");
        key="ProCategoryName";
        thisOrderData.add(new FormDataEntity(key,"危废种类",FormDataEntity.Type.list,listCategory,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="ProShapeName";
        thisOrderData.add(new FormDataEntity(key,"危废形态",FormDataEntity.Type.list,listProShape,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="ProHazardNature";
        thisOrderData.add(new FormDataEntity(key,"危害特性",FormDataEntity.Type.checkTexts,listProHazardNature,map!=null&&map.get(key)!=null?map.get(key).toString():""));

        key="ProPackName";
        thisOrderData.add(new FormDataEntity(key,"包装方式",FormDataEntity.Type.list,listProPack,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="ProDangerComponent";
        thisOrderData.add(new FormDataEntity(key,"主要危险成分",FormDataEntity.Type.input,null,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        key="ProNumber";
        thisOrderData.add(new FormDataEntity(key,"生产数量",FormDataEntity.Type.spinner,listProMeasureUnit,map!=null&&map.get(key)!=null?map.get(key).toString():""));
        thisOrderData.get(thisOrderData.size()-1).setThisData_(map!=null&&map.get(key)!=null?map.get("ProMeasureUnitName").toString():"");
        key="Remark";
        thisOrderData.add(new FormDataEntity(key,"备注",FormDataEntity.Type.input,null,map!=null&&map.get(key)!=null?map.get(key).toString():""));

        order.setList(thisOrderData);
    }
    //根据筛选条件加载数据
    private void getListData(){

        //加载数据开始
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err","0")
                    .put("SysComID", Convert.ToString(AppUser.getInstance().getSysComID()))
                    .put("RecTimeStart",Convert.ToString(conditionResult.get("ProTimeStart")))
                    .put("RecTimeEnd", Convert.ToString(conditionResult.get("ProTimeEnd")));
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
                    Product.this.list=l;
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
                    map.get("RecNumber").toString(),
                    map.get("ProCategoryName").toString(),
                    map.get("RecTime").toString(),
                    map.get("ProNumber").toString()+map.get("ProMeasureUnitName").toString()
            ).getItemView();
            itemView.setOnClickListener(new ItemClick(i));
            tableList.add(itemView);
        }

        scope.key("tableView").val(tableList);
        SwitchDeleteList switchDeleteList=new SwitchDeleteList(scope,"tableView");
        switchDeleteList.setOnRightItemClickListener(new SwitchDeleteList.OnRightItemClickListener() {
            @Override
            public void onRightItemClick(View v, int position) {
                delete(list.get(position).get("RecNumber").toString());
            }
        });
        table.setTableView(switchDeleteList);
    }

    private void add(List<FormDataEntity> list){
        ProduceRec_Model mo=getModel(list);
        if(mo==null)return;
        ProdRec_BLL.ProdRec_Add(mo);
        scope.key("ProdRec_Add").watch(new DataListener<ResultMsg>() {
            @Override
            public void hasChange(ResultMsg msg) {
                alert(msg.getMsg());
                if(msg.isTure()){
                    getListData();
                }
            }
        });
    }
    private void update(List<FormDataEntity> list){

        ProduceRec_Model mo=getModel(list);
        if(mo==null)return;
        ProdRec_BLL.ProdRec_Update(mo);
        scope.key("ProdRec_Update").watch(new DataListener<ResultMsg>() {
            @Override
            public void hasChange(ResultMsg msg) {
                alert(msg.getMsg());
                if(msg.isTure()){
                    getListData();
                }
            }
        });
    }
    private ProduceRec_Model getModel(List<FormDataEntity> list){
        ProduceRec_Model model=new ProduceRec_Model();
        for (int i = 0; i <list.size() ; i++) {
            FormDataEntity formEntity=list.get(i);
            if(formEntity.getId().equalsIgnoreCase("RecNumber")){
                model.setRecNumber(formEntity.getThisData());
            }
            if(formEntity.getId().equalsIgnoreCase("UseProName"))model.setUseProName(formEntity.getThisData());
            if(formEntity.getId().equalsIgnoreCase("UseProSpec"))model.setUseProSpec(formEntity.getThisData());
            if(formEntity.getId().equalsIgnoreCase("UseProModel"))model.setUseProModel(formEntity.getThisData());

            if(formEntity.getId().equalsIgnoreCase("UseProNumber")){
                model.setUseProNumber(formEntity.getThisData());
                model.setUseProMeasureUnitName(formEntity.getThisData_());
            }
            if(formEntity.getId().equalsIgnoreCase("ProCategoryName")){
                if(formEntity.getThisData_()==null){
                    alert("请选择危废种类！");
                    return null;
                }
                model.setProCategoryName(formEntity.getThisData());
                model.setProCategory(Long.parseLong(formEntity.getThisData_()));
            }
            if(formEntity.getId().equalsIgnoreCase("ProShapeName")){
                if(formEntity.getThisData_()==null){
                    alert("请选择危废形态！");
                    return null;
                }
                model.setProShapeName(formEntity.getThisData());
                model.setProShape(Long.parseLong(formEntity.getThisData_()));
            }
            if(formEntity.getId().equalsIgnoreCase("ProHazardNature")){
                List<TreeEntity> returnData =formEntity.getReturnData();
                String ProHazardNaturestr="";
                for (int j = 0; j <returnData.size() ; j++) {
                    ProHazardNaturestr+=returnData.get(j).getName();
                }
                model.setProHazardNature(ProHazardNaturestr);
            }
            if(formEntity.getId().equalsIgnoreCase("ProPackName")){
                model.setProPackName(formEntity.getThisData());
            }
            if(formEntity.getId().equalsIgnoreCase("ProDangerComponent"))model.setProDangerComponent(formEntity.getThisData());
            if(formEntity.getId().equalsIgnoreCase("ProNumber")){
                model.setProNumber(formEntity.getThisData());
                model.setProMeasureUnitName(formEntity.getThisData_());
            }
            if(formEntity.getId().equalsIgnoreCase("Remark"))model.setRemark(formEntity.getThisData());
        }
        long comId = AppUser.getInstance().getSysComID();
        long userId = AppUser.getInstance().getCompanyUserID();
        long comBrId = AppUser.getInstance().getSysComBrID();
        model.setCreateUserID(userId);
        model.setCreateComBrID(comBrId);
        model.setCreateComID(comId);
        model.setCreateIp("");
        model.setRecSource(3);
        return model;
    }
    private void delete(String id){
        TransRec_BLL.TransRec_Delete(id);
        scope.key("TransRec_Delete").watch(new DataListener<ResultMsg>() {
            @Override
            public void hasChange(ResultMsg msg) {
                alert(msg.getMsg());
                if(msg.isTure()){
                    getListData();
                }
            }
        });
    }

    class ItemClick implements View.OnClickListener{
        private int i;
        public ItemClick(int i){
            this.i=i;
        }
        @Override
        public void onClick(View v) {
            getThisOrderData(list.get(i));
            order.getOrderInfo().getWindow().showFullScreen();
        }
    }
}
