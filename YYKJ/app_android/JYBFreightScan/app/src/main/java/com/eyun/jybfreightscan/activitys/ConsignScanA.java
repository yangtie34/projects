package com.eyun.jybfreightscan.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.ThreadUtil;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.cusviews.CusSDDListView;
import com.eyun.jybfreightscan.product.dao.impl.ConsignProBarCodeDaoImpl;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.Vehicle;
import com.eyun.jybfreightscan.product.service.impl.ConsignScanServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.ConsignServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.VehicleServiceImpl;
import com.eyun.jybfreightscan.support.ListItem;
import com.eyun.jybfreightscan.support.SoundSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.eyun.framework.util.android.ViewUtil.alert;

/**
 * Created by Administrator on 2017/3/20. ConsignScanService
 */

public class ConsignScanA extends AngularActivity {
    private List<String> scanTypes=new ArrayList<>();
    private int scanType=0;
    private String scanTypeName;

    private Consign consign;//托运单
    private Vehicle vehicle;//车辆
    private CusSDDListView dlistView;
    private int scanCounts=0;
    private int scanVehicleCounts=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consign_scan);
        scope.key("title").val("托运单扫描");
        dlistView= (CusSDDListView) findViewById(R.id.droplistview);

        //1-始发装车，2-中转卸车，3-中转装车，4-终点卸车
        scanTypes.add("1-始发装车");
        scanTypes.add("2-中转卸车");
        scanTypes.add("3-中转装车");
        scanTypes.add("4-终点卸车");
        for (int i = 0; i <scanTypes.size() ; i++) {
            scope.key("scanType").key(i).val(scanTypes.get(i));
        }

       /* ViewUtil.confirm("提示","请扫描托运单",new CallBack(){
            @Override
            public Object run() {
                return null;
            }
        },null);*/
        editText= (EditText) findViewById(R.id.scanCode);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String code=s.toString().replace("\n","");
                if(StringUtils.hasLength(code)) {
                    okScan(code);
                    editText.setText("");
                }
            }
        });
    }
    boolean initScantype=true;
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        if (hasFocus&&scanType==0&&initScantype){
             scope.key("windowShow").val(true);
            scope.key("someModeShow").val(true);
            initScantype=false;
        }
    }
    /**
     * 扫描完成事件
     * @param code
     */
    public void okScan(final String code) {
        if(scanType==0){
            scope.key("windowShow").val(true);
            return;
        }
        //Object ooo=scope.key("someMode");
        someMode= TypeConvert.toBoolean(scope.key("someMode").val());
        Loadding.show("正在扫描！");
         if(vehicle==null){//扫描车辆
            Loadding.show("正在扫描车辆！");
            scope.forThread(new CallBack<Vehicle>() {
                @Override
                public Vehicle run() {
                    return VehicleServiceImpl.getInstance().getVehicle(code);
                }
            },new DataListener<Vehicle>(){
                @Override
                public void hasChange(final Vehicle vehicle) {
                    Loadding.hide();
                    if(vehicle==null){
                        alert("无此车辆信息！");
                        SoundSupport.play(R.raw.wcxx);
                        return;
                    }

                    ConsignScanA.this.vehicle=vehicle;
                    scope.key("VehNumber").val(vehicle.getVehNumber());//车辆编号
                    scope.key("VehContactName").val(vehicle.getVehContactName());//车辆联系人
                    SoundSupport.play(R.raw.consign_scan);
                }
            });
        }else if(consign==null){//扫描托运单处理方法
            scope.forThread(new CallBack<ResultMsg>() {
                @Override
                public ResultMsg run() {
                    ResultMsg resultMsg=new ResultMsg();
                    consign= ConsignServiceImpl.getInstance().getConsign(code);
                    if(consign!=null){
                            return checkScanType();
                    }else{
                        resultMsg.setTure(false);
                        SoundSupport.play(R.raw.wcxx);
                        resultMsg.setMsg("没有查询到此单！请重新扫描！");
                    }
                    return resultMsg;
                }
            }, new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    Loadding.hide();
                    if(resultMsg.isTure()){
                        int[] nums= (int[]) resultMsg.getObject();
                        scope.key("allCounts").val(consign.getProNumber());
                        scanVehicleCounts=nums[0];
                        scope.key("scanVehicleCounts").val(scanVehicleCounts);
                        scanCounts=nums[0];
                        scope.key("RecNumber").val(consign.getRecNumber());
                        scope.key("scanCounts").val(scanCounts);
//                        ViewUtil.confirm("提示",resultMsg.getMsg(),new CallBack(){
//                            @Override
//                            public Object run() {
//                                return null;
//                            }
//                        },null);
                    }
                    ViewUtil.alert(resultMsg.getMsg());


                }
            });
        }else{//扫描商品处理方法
             callBack=new CallBack() {
                 @Override
                 public Object run() {
                     ResultMsg resultMsg;
                     resultMsg= ConsignScanServiceImpl.getInstance().getScanProduct(consign,scanType,vehicle,code,someModeNumber);
                     return resultMsg;
                 }
             };
             dataListener=new DataListener<ResultMsg>() {
                 @Override
                 public void hasChange(ResultMsg resultMsg) {
                     Loadding.hide();
                     if(resultMsg.isTure()){
                         scanCounts+=someModeNumber;
                         scanVehicleCounts+=someModeNumber;
                         scope.key("scanCounts").val(scanCounts);
                         scope.key("scanVehicleCounts").val(scanVehicleCounts);
                         initThisScan(code);
                         //扫描成功
                     }else{
                         alert(resultMsg.getMsg());
                         //扫描失败
                     }
                 }
             };
             if(someMode==true){
                 Loadding.hide();
                 scope.key("windowShowForSomeMode").val(true);
             }else{
                 scope.forThread(callBack,dataListener);
             }
        }
    }
    private ResultMsg checkScanType(){
        int RecState=consign.getRecState();
        int RecForwardedState=consign.getRecForwardedState();
        ResultMsg resultMsg=new ResultMsg();
        if(RecState>=4){
            Loadding.hide();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ViewUtil.confirm("提示","此托运单已签收！",new CallBack(){
                        @Override
                        public Object run() {
                            ConsignScanA.this.onBackPressed();
                            return null;
                        }
                    },null);
                }
            });
            resultMsg.setTure(false);
            resultMsg.setMsg("此托运单已签收！");
            return resultMsg;
        }else if(RecState==3&&RecForwardedState!=2&&scanType==3){//已始发未中转卸车 但选择中转装车
            Loadding.hide();
            resultMsg.setTure(false);
            resultMsg.setMsg("此托运单未中转卸车！不能选择中转装车！");
            scanTypeError(resultMsg.getMsg());
            return resultMsg;
        }else if(RecState==3&&RecForwardedState==2&&scanType==2){
            Loadding.hide();
            resultMsg.setTure(false);
            resultMsg.setMsg("此托运单已中转卸车！不能选择中转卸车！");
            scanTypeError(resultMsg.getMsg());
            return resultMsg;
        }else if(RecState==3&&scanType==1){
            Loadding.hide();
            resultMsg.setTure(false);
            resultMsg.setMsg("此托运单已派发！不能选择始发装车！");
            scanTypeError(resultMsg.getMsg());
            return resultMsg;
        }else if(RecState<3&&scanType>1){
            Loadding.hide();
            resultMsg.setTure(false);
            resultMsg.setMsg("此托运单未派发！请选择始发装车操作！");
            scanTypeError(resultMsg.getMsg());
            return resultMsg;
        }else{
            resultMsg.setTure(true);
            resultMsg.setObject(new int[]{ConsignScanServiceImpl.getInstance().getConsignScanCounts(consign,scanType),
                    ConsignScanServiceImpl.getInstance().getConsignScanCounts(consign,scanType,vehicle)
            });//获取此类型下已扫描数量
            initListView();
            SoundSupport.play(R.raw.product_scan);
            resultMsg.setMsg("成功扫描托运单！请扫描商品！");
        }
        return resultMsg;
    }
    private void scanTypeError(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewUtil.confirm("提示",msg+"请重新选择！",new CallBack() {
                    @Override
                    public Object run() {
                        scanType = 0;
                        scope.key("windowShow").val(true);
                        return null;
                    }
                },null);
            }
        });
    }
    private static CallBack callBack;
    private static DataListener dataListener;
    private void  initThisScan(String code){
        scope.key("ProID").val(code);
    }
    private void initListView() {
        CusSDDListView.QueryForList queryForList=new CusSDDListView.QueryForList() {
            @Override
            public List<Map<String, Object>> query(String Condition, int currentPage, int numPerPage) {
                return ConsignProBarCodeDaoImpl.getInstance().getProducts(consign.getRecNumber());
            }

            @Override
            public View data2view(Map<String, Object> map) {
                String msg1= TypeConvert.toString( map.get("BarCode"));
                //String msg2= TypeConvert.toString( map.get("ProName"));
//                String msg3= TypeConvert.toString( map.get("ProNumber"))+map.get("ProMeasureUnitName");
//                String msg4= TypeConvert.toString( map.get("ProCategoryName"));
                View view=new ListItem(msg1,null,null,null).getItemView();
                return view;
            }

            @Override
            public String getId(Map<String, Object> map) {
                return null;
            }

            @Override
            public String getCondition() {
                return null;
            }
        };
        dlistView.setQueryForList(queryForList);
        dlistView.noDropDownStyle();
        dlistView.noOnBottomStyle();
    }

    public void finalScan(View view) {
        if(scanVehicleCounts==0){
            alert("无需提交！");
            return;
        }
        AlertDialog.Builder confirm = new AlertDialog.Builder(Scope.activity);
        confirm.setTitle("提示");//设置对话框标题
        confirm.setMessage("此单据商品已全部扫描？");//设置显示的内容
        confirm.setPositiveButton("暂存", new DialogInterface.OnClickListener() {//添加确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                SoundSupport.play(R.raw.ok);
                ThreadUtil.sleep(SoundSupport.miles);
                ConsignScanA.this.onBackPressed();
            }
        });
        confirm.setNegativeButton("提交", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                ViewUtil.confirm("提示", "确认提交托运单编号为:" + consign.getRecNumber() + " 的商品"+scanTypeName+"扫描数据？共计"
                        + consign.getProNumber() + "件，共扫描" + scanCounts + "件，此车辆扫描" + scanVehicleCounts + "件。", new CallBack() {
                    @Override
                    public Object run() {//提交
                        Loadding.show("正在提交扫描数据...");
                        scope.forThread(new CallBack() {
                            @Override
                            public Object run() {
                                return ConsignScanServiceImpl.getInstance().consignScanOk(consign.getRecNumber(),scanType);
                            }
                        }, new DataListener<ResultMsg>() {
                            @Override
                            public void hasChange(ResultMsg resultMsg) {
                                Loadding.hide();
                                alert(resultMsg.getMsg());
                                if(resultMsg.isTure()){
                                    ThreadUtil.sleep(SoundSupport.miles);
                                    ConsignScanA.this.onBackPressed();
                                }
                            }
                        });
                        return null;
                    }
                }, new CallBack() {
                    @Override
                    public Object run() {//取消
                        return null;
                    }
                });
            }
        });
        confirm.show();
    }

    public void checkScanType(View view) {
        scope.key("windowShow").val(false);
        String text=((TextView)view).getText().toString();
        scanTypeName=text;
        scope.key("scanTypeName").val(scanTypeName);
        for (int i = 0; i <scanTypes.size() ; i++) {
            if(scanTypes.get(i).equalsIgnoreCase(text)){
                scanType=i+1;
                break;
            }
        }
        alert("已选择扫描类型为："+text);
         if(vehicle==null){
            SoundSupport.play(R.raw.vehicle_scan);
        }else if(consign==null){
            SoundSupport.play(R.raw.consign_scan);
        }else{
             scope.forThread(new CallBack() {
                 @Override
                 public Object run() {
                     return checkScanType();
                 }
             }, new DataListener<ResultMsg>() {
                 @Override
                 public void hasChange(ResultMsg resultMsg) {
                     if (resultMsg.isTure()){
                         int[] nums= (int[]) resultMsg.getObject();
                         scope.key("allCounts").val(consign.getProNumber());
                         scanVehicleCounts=nums[0];
                         scope.key("scanVehicleCounts").val(scanVehicleCounts);
                         scanCounts=nums[0];
                         scope.key("RecNumber").val(consign.getRecNumber());
                         scope.key("scanCounts").val(scanCounts);
                     }
                     alert(resultMsg.getMsg());
                 }
             });
        }

    }
    public void toScan(View view) {
        String code= TypeConvert.toString(scope.key("scanCode").val());
        if(StringUtils.hasLength(code)){
            okScan(code);
        }else {
            alert("未输入扫描编号！");
        }
    }
    private boolean someMode=false;
    private int someModeNumber=1;
    public void someModeOk(View view) {
        someModeNumber= TypeConvert.toInteger(scope.key("someModeNumber").val());
        scope.key("windowShowForSomeMode").val(false);
        Loadding.show("正在扫描！");
        scope.forThread(callBack,dataListener);
    }

}
