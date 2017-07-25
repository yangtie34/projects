package com.eyun.jybStorageScan.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.moduleview.zxing.MipcaCaptureActivity;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.ThreadUtil;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.AppPublic;
import com.eyun.jybStorageScan.R;
import com.eyun.jybStorageScan.cusviews.CusSDDListView;
import com.eyun.jybStorageScan.product.entity.StorageInRec;
import com.eyun.jybStorageScan.product.entity.StorageInRecScan;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecDetailServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecScanServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecStateServiceImpl;
import com.eyun.jybStorageScan.support.ListItem;
import com.eyun.jybStorageScan.support.SoundSupport;

import java.util.List;
import java.util.Map;

import static android.R.attr.type;
import static com.eyun.framework.util.android.ViewUtil.alert;
import static com.eyun.jybStorageScan.R.id.scanCode;


public class InRecScan extends AngularActivity {
    private String recNumber;//入库单编号
    private StorageInRec storageInRec;//入库单

    private CusSDDListView dlistView;
    private int scanCounts=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inrec_scan);
        scope.key("title").val("入库单扫描");
        dlistView = (CusSDDListView) findViewById(R.id.droplistview);
/*
        ViewUtil.confirm("提示", "请先扫描入库单！", new CallBack() {
            @Override
            public Object run() {
                // toScan();
                return null;
            }
        }, null);
*/

        SoundSupport.play(R.raw.in_rec_scan);
        editText= (EditText) findViewById(scanCode);
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
    /**
     * 扫描完成事件
     * @param code
     */
    //@Override
    public void okScan(final String code) {
        someMode=TypeConvert.toBoolean(scope.key("someMode").val());
        Loadding.show("正在扫描！");
        if(StringUtils.hasLength(recNumber)){//扫描商品处理方法
            callBack=new CallBack() {
                @Override
                public Object run() {
                    ResultMsg resultMsg=new ResultMsg();
                    resultMsg=StorageInRecScanServiceImpl.getInstance().getScanbyRecNumberAndProBarCode(recNumber,code, AppPublic.ScanType.FJ,someModeNumber);
                    return resultMsg;
                }
            };
            dataListener=new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    Loadding.hide();
                    if(resultMsg.isTure()){
                        scanCounts++;
                        scope.key("scanCounts").val(scanCounts);
                        StorageInRecScan storageInRecScan= (StorageInRecScan) resultMsg.getObject();
                        initThisScan(storageInRecScan);
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
                someModeNumber=1;
                scope.forThread(callBack,dataListener);
            }

        }else{//扫描入库单处理方法
            scope.forThread(new CallBack<ResultMsg>() {
                @Override
                public ResultMsg run() {
                    ResultMsg resultMsg=new ResultMsg();
                    storageInRec=StorageInRecServiceImpl.getInstance().getStorageInRecByRecBarCode(code);
                    if(storageInRec!=null){
                        int state= storageInRec.getRecState();
                        if(state>=AppPublic.ScanType.FJ){
                            resultMsg.setTure(false);
                            SoundSupport.play(R.raw.hasfj);
                            resultMsg.setMsg("入库单已经完成分拣！");
                            return resultMsg;
                        }
                        resultMsg.setTure(true);
                        recNumber=storageInRec.getRecNumber();
                        if(storageInRec.getRecState()==null||storageInRec.getRecState()==AppPublic.ScanType.SAVE){
                            StorageInRecScanServiceImpl.getInstance().initScansByStorageInRecDetails(recNumber, AppPublic.ScanType.FJ);
                        }
                        resultMsg.setObject(new int[]{StorageInRecDetailServiceImpl.getInstance().getStorageInRecCountsByRecNumber(storageInRec.getRecNumber()),
                                StorageInRecScanServiceImpl.getInstance().getInRecScanNumber(recNumber)});
                        initListView();
                        SoundSupport.play(R.raw.product_scan);
                        resultMsg.setMsg("成功扫描入库单！请扫描对应商品。");
                    }else{
                        resultMsg.setTure(false);
                        SoundSupport.play(R.raw.wcxx);
                        resultMsg.setMsg("没有查询到此入库单！请重新扫描！");
                    }
                    return resultMsg;
                }
            }, new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    Loadding.hide();
                    if(resultMsg.isTure()){
                        int[] nums= (int[]) resultMsg.getObject();
                        scope.key("recNumber").val(recNumber);
                        scope.key("recDetailCounts").val(nums[0]);
                        scanCounts=nums[1];
                        scope.key("scanCounts").val(scanCounts);
                        ViewUtil.alert(resultMsg.getMsg());
                    }else{
                        ViewUtil.alert(resultMsg.getMsg());
                    }

                }
            });
        }
    }
    private void  initThisScan(StorageInRecScan storageInRecScan){
        scope.key("ProName").val(storageInRecScan.getProName());
          scope.key("ProID").val(storageInRecScan.getProId());
        scope.key("ScanNumber").val(storageInRecScan.getScanNumber());
        scope.key("ProNumber").val(storageInRecScan.getProNumber());
    }
    private void initListView() {
        CusSDDListView.QueryForList queryForList=new CusSDDListView.QueryForList() {
            @Override
            public List<Map<String, Object>> query(String Condition, int currentPage, int numPerPage) {
                return StorageInRecDetailServiceImpl.getInstance().getStorageInRecDetailByRecNumber(storageInRec.getRecNumber());
            }

            @Override
            public View data2view(Map<String, Object> map) {
                String msg1= TypeConvert.toString( map.get("ProID"));
                String msg2= TypeConvert.toString( map.get("ProName"));
                String msg3= TypeConvert.toString( map.get("ProNumber"))+map.get("ProMeasureUnitName");
                String msg4= TypeConvert.toString( map.get("ProCategoryName"));
                View view=new ListItem(msg1,msg2,msg3,msg4).getItemView();
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
        if(recNumber==null){
            alert("无需提交！");
            return;
        }
        ViewUtil.confirm("提示", "确认提交入库单编号为:" + recNumber + " 的商品分拣扫描数据？共计" + scope.key("recDetailCounts").val() + "件，扫描" + scanCounts + "件。", new CallBack() {
            @Override
            public Object run() {//提交
                Loadding.show("正在提交扫描数据...");
                scope.forThread(new CallBack() {
                    @Override
                    public Object run() {
                        return StorageInRecScanServiceImpl.getInstance().inRecScanOk(recNumber);
                    }
                }, new DataListener<ResultMsg>() {
                    @Override
                    public void hasChange(ResultMsg resultMsg) {
                        Loadding.hide();
                        SoundSupport.play(R.raw.ok);
                        ThreadUtil.sleep(SoundSupport.miles);
                    InRecScan.this.onBackPressed();
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
    public void toScan(View view) {
        String code=TypeConvert.toString(scope.key("scanCode").val());
        if(StringUtils.hasLength(code)){
            okScan(code);
        }else {
            alert("未输入扫描编号！");
        }
    }
    private boolean someMode=false;
    private int someModeNumber=1;
    private static CallBack callBack;
    private static DataListener dataListener;
    public void someModeOk(View view) {
        someModeNumber=TypeConvert.toInteger(scope.key("someModeNumber").val());
        scope.key("windowShowForSomeMode").val(false);
        Loadding.show("正在扫描！");
        scope.forThread(callBack,dataListener);
    }
}
