package com.eyun.jybStorageScan.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.ThreadUtil;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.AppPublic;
import com.eyun.jybStorageScan.R;
import com.eyun.jybStorageScan.cusviews.CusSDDListView;
import com.eyun.jybStorageScan.product.entity.StorageInRecScan;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecDetailServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecScanServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecServiceImpl;
import com.eyun.jybStorageScan.support.ListItem;
import com.eyun.jybStorageScan.support.SoundSupport;

import java.util.List;
import java.util.Map;

import static android.R.attr.type;
import static com.eyun.framework.util.android.ViewUtil.alert;


public class ScanInRec extends AngularActivity {
    private String recNumber;//入库单编号
    private CusSDDListView dlistView;
    private int scanCounts=0;
    private long ComSuppID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_inrec);
        scope.key("title").val("扫描入库");
        dlistView= (CusSDDListView) findViewById(R.id.droplistview);
        ViewUtil.alert("请直接扫描需要分拣的商品！");
        SoundSupport.play(R.raw.product_scan);
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

    /**
     * 扫描完成事件
     * @param code
     */
    public void okScan(final String code) {
        someMode=TypeConvert.toBoolean(scope.key("someMode").val());
        Loadding.show("正在扫描！");
        if(StringUtils.hasLength(recNumber)){//扫描商品处理方法
            callBack=new CallBack() {
                @Override
                public Object run() {
                    ResultMsg resultMsg=StorageInRecScanServiceImpl.getInstance().getScanbyRecNumberAndProBarCodeForScanInRec(recNumber,code, AppPublic.ScanType.FJ,ComSuppID,someModeNumber);
                    return resultMsg;
                }
            };
            dataListener= new DataListener<ResultMsg>() {
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
        }else{//首次扫描
            scope.forThread(new CallBack<ResultMsg>() {
                @Override
                public ResultMsg run() {
                    return StorageInRecScanServiceImpl.getInstance().getFjScanProBarCode(code);
                }
            }, new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    Loadding.hide();
                    if(resultMsg.isTure()){
                        scanCounts++;
                        scope.key("scanCounts").val(1);
                        Object[] objs= (Object[]) resultMsg.getObject();
                        recNumber= (String) objs[1];
                        scope.key("recNumber").val(recNumber);
                        ViewUtil.alert("成功生成临时入库单！请继续扫描商品。");
                        StorageInRecScan storageInRecScan= (StorageInRecScan) objs[0];
                        ComSuppID=storageInRecScan.getComSuppID();
                        initThisScan(storageInRecScan);
                        initListView();
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
        scope.key("ProNumber").val(storageInRecScan.getScanNumber());
    }
    private void initListView() {
        CusSDDListView.QueryForList queryForList=new CusSDDListView.QueryForList() {
            @Override
            public List<Map<String, Object>> query(String Condition, int currentPage, int numPerPage) {
                return StorageInRecScanServiceImpl.getInstance().getScanbyRecNumberAndScanType(recNumber,AppPublic.ScanType.FJ);
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
        dlistView.noOnBottomStyle();
    }

    public void finalScan(View view) {
        if(recNumber==null){
            alert("无需提交！");
            return;
        }
        ViewUtil.confirm("提示", "是否确认提交此次商品分拣扫描数据？共计" + scope.key("scanCounts").val() + "件。", new CallBack() {
            @Override
            public Object run() {//提交
                Loadding.show("正在提交扫描数据并生成入库单...");
                scope.forThread(new CallBack() {
                    @Override
                    public Object run() {
                        return StorageInRecScanServiceImpl.getInstance().scanInRecOk(recNumber);
                    }
                }, new DataListener<ResultMsg>() {
                    @Override
                    public void hasChange(ResultMsg resultMsg) {
                        Loadding.hide();
                        SoundSupport.play(R.raw.ok);
                        ThreadUtil.sleep(SoundSupport.miles);
                        ScanInRec.this.onBackPressed();
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
