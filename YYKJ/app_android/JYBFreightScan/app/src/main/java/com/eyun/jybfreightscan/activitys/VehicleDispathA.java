package com.eyun.jybfreightscan.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.Vehicle;
import com.eyun.jybfreightscan.product.service.impl.ConsignServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.VehicleDispathServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.VehicleServiceImpl;
import com.eyun.jybfreightscan.support.SoundSupport;

import java.util.ArrayList;
import java.util.List;

import static com.eyun.framework.util.android.ViewUtil.alert;

/**
 * Created by Administrator on 2017/3/21.
 */
public class VehicleDispathA extends AngularActivity {
    private Consign consign;//托运单
    private Vehicle vehicle;//车辆

    private List<String> DispathTypes=new ArrayList<>();
    private int DispathType=0;
    private String DispathName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_dispath);
        scope.key("title").val("车辆调度");
        ////1-已装车 2-已发车， 2-中转已到车，3-中转已卸车，4-中转已装车，5-中转已发车，3-已完成
        DispathTypes.add("已装车");
        DispathTypes.add("已发车");
        DispathTypes.add("中转已到车");
        DispathTypes.add("中转已卸车");
        DispathTypes.add("中转已装车");
        DispathTypes.add("中转已发车");
        DispathTypes.add("已完成");
        for (int i = 0; i <DispathTypes.size() ; i++) {
            scope.key("DispathType").key(i).val(DispathTypes.get(i));
        }
        editText= (EditText) findViewById(R.id.scanCode);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
    /**
     * 扫描完成事件
     * @param code
     */
    //@Override
    public void okScan(final String code) {
        Loadding.show("正在扫描！");
        if(vehicle==null){//扫描车辆
            Loadding.show("正在扫描车辆！");
            scope.forThread(new CallBack<Vehicle>() {
                @Override
                public Vehicle run() {
                    return VehicleServiceImpl.getInstance().getVehicle("100000000");
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
                    VehicleDispathA.this.vehicle=vehicle;
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
                    if(consign==null){
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
                        windowShow(null);
                    }
                    ViewUtil.alert(resultMsg.getMsg());
                }
            });
        }
    }

    public void windowShow(View view) {
        scope.key("windowShow").val(true);
    }

    public void checkScanType(View view) {
        scope.key("windowShow").val(false);
        String text=((TextView)view).getText().toString();
        DispathName=text;
        scope.key("DispathName").val(DispathName);
        for (int i = 0; i <DispathTypes.size() ; i++) {
            if(DispathTypes.get(i).equalsIgnoreCase(text)){
                DispathType=i+1;
                break;
            }
        }
        alert("已选择扫描类型为："+text);
    }
    public void finalScan(View view) {
        if(vehicle==null){
            SoundSupport.play(R.raw.vehicle_scan);
            alert("请扫描车辆！");
        }else if(consign==null){
            SoundSupport.play(R.raw.consign_scan);
            alert("请扫描托运单！");
        }else if(DispathType==0){
            alert("请选择调度状态！");
        }else{
            scope.forThread(new CallBack<ResultMsg>() {
                @Override
                public ResultMsg run() {
                    return VehicleDispathServiceImpl.getInstance().okVehicleDisp(vehicle,consign,DispathType);
                }
            }, new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    if(resultMsg.isTure()){
                        SoundSupport.play(R.raw.ok);
                        VehicleDispathA.this.onBackPressed();
                    }else{
                        alert(resultMsg.getMsg());
                        consign=null;
                        SoundSupport.play(R.raw.consign_scan);
                    }
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
}
