package com.eyun.eyunstorage.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.eyunstorage.R;
import com.eyun.eyunstorage.cusviews.CusSDDListView;
import com.eyun.eyunstorage.product.dao.impl.StorageLocationProductDaoImpl;
import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageTakeRec;
import com.eyun.eyunstorage.product.entity.StorageTakeRecScan;
import com.eyun.eyunstorage.product.service.impl.StorageLocationServiceImpl;
import com.eyun.eyunstorage.product.service.impl.StorageTakeRecDetailServiceImpl;
import com.eyun.eyunstorage.product.service.impl.StorageTakeRecScanServiceImpl;
import com.eyun.eyunstorage.product.service.impl.StorageTakeRecServiceImpl;
import com.eyun.eyunstorage.support.ListItem;
import com.eyun.eyunstorage.support.ReceiptUtil;
import com.eyun.eyunstorage.support.SoundSupport;

import java.util.List;
import java.util.Map;

import static com.eyun.framework.util.android.ViewUtil.alert;

/**
 * Created by Administrator on 2017/3/9.
 */
public class StorageTake extends AngularActivity {

    private StorageTakeRec StorageTakeRec;//盘点单
    private StorageLocation storageLocation;
    private CusSDDListView dlistView;
    private int scanCounts=0;
    private int scanStorageLocationCounts=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_take);
        scope.key("title").val("仓库盘点");
        dlistView= (CusSDDListView) findViewById(R.id.droplistview);
        SoundSupport.play(R.raw.storage_scan);
        ViewUtil.alert("盘点开始，请扫描库位！");
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
     if(storageLocation==null){//扫描库位
            Loadding.show("正在扫描库位！");
            scope.forThread(new CallBack<StorageLocation>() {
                @Override
                public StorageLocation run() {
                    return StorageLocationServiceImpl.getInstance().getStorageLocatioin(code);
                }
            },new DataListener<StorageLocation>(){
                @Override
                public void hasChange(final StorageLocation storageLocation) {

                    if(storageLocation==null){
                        Loadding.hide();
                        alert("无此库位信息！");
                        SoundSupport.play(R.raw.wcxx);
                        return;
                    }
                    Loadding.show("正在获取此库位盘点信息...");
                    scope.forThread(new CallBack() {
                        @Override
                        public Object run() {
                            StorageTakeRec=StorageTakeRecServiceImpl.getInstance().getStorageTakeRecByStorLocaCode(storageLocation);
                                if(StringUtils.hasLength(StorageTakeRec.getRecNumber())&&StorageTakeRec.getRecState()<3){
                                    StorageTakeRec.setRecState(StorageTakeRec.getRecState()+1);
                                }else {
                                    StorageTakeRec.setRecState(0);
                                    StorageTakeRec.setRecNumber(ReceiptUtil.getReceiptID());
                                }

                            StorageTakeRecServiceImpl.getInstance().clearByStorageTakeRec(StorageTakeRec);
                            return StorageTakeRecDetailServiceImpl.getInstance().getStorageTakeRecCountsByRecNumber(StorageTakeRec);
                        }
                    }, new DataListener<Integer>() {
                        @Override
                        public void hasChange(Integer i) {
                            Loadding.hide();
                            scope.key("take_type_name").val(getTypeName(StorageTakeRec.getRecState()));
                            scanStorageLocationCounts=i;
                            scope.key("scanStorageLocationCounts").val(scanStorageLocationCounts);
                            initListView();
                        }
                    });
                    scope.forThread(new CallBack() {
                        @Override
                        public Object run() {
                            return StorageLocationProductDaoImpl.getInstance().getLocationProNumber(storageLocation.getCode());
                        }
                    }, new DataListener<Integer>() {
                        @Override
                        public void hasChange(Integer i) {
                           scope.key("recDetailCounts").val(i);
                        };
                });
                    StorageTake.this.storageLocation=storageLocation;
                    scope.key("storageLocation").val(storageLocation.getName());
                    SoundSupport.play(R.raw.product_scan);
                }
            });
        }else{//扫描商品处理方法
         callBack=new CallBack() {
                @Override
                public Object run() {
                    StorageTakeRecScan mo=new StorageTakeRecScan();
                    mo.setStorLocaCode(storageLocation.getCode());
                    mo.setStorLocaComBrId(storageLocation.getComBrId());
                    mo.setRecNumber(StorageTakeRec.getRecNumber());
                    mo.setScanType(StorageTakeRec.getRecState());
                    ResultMsg resultMsg= StorageTakeRecScanServiceImpl.getInstance().getScanbyRecNumberAndProBarCode(mo,code,someModeNumber);
                    return resultMsg;
                }
            };
         dataListener= new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    Loadding.hide();
                    if(resultMsg.isTure()){
                        scanCounts++;
                        scanStorageLocationCounts++;
                        scope.key("scanCounts").val(scanCounts);
                        scope.key("scanStorageLocationCounts").val(scanCounts);
                        StorageTakeRecScan StorageTakeRecScan= (StorageTakeRecScan) resultMsg.getObject();
                        initThisScan(StorageTakeRecScan);
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
        }
    }
    private void  initThisScan(StorageTakeRecScan StorageTakeRecScan){
        scope.key("ProName").val(StorageTakeRecScan.getProName());
        scope.key("ProID").val(StorageTakeRecScan.getProId());
        scope.key("ScanNumber").val(StorageTakeRecScan.getScanNumber());
       // scope.key("ProNumber").val(StorageTakeRecScan.getProNumber());
    }
    private void initListView() {
        CusSDDListView.QueryForList queryForList=new CusSDDListView.QueryForList() {
            @Override
            public List<Map<String, Object>> query(String Condition, int currentPage, int numPerPage) {
                return StorageTakeRecDetailServiceImpl.getInstance().getStorageTakeRecDetailByRecNumber(StorageTakeRec);
            }

            @Override
            public View data2view(Map<String, Object> map) {
                String msg1=TypeConvert.toString( map.get("ProID"));
                String msg2=TypeConvert.toString( map.get("proName"));
                String msg3=TypeConvert.toString( map.get("ProNumber"))+map.get("ProMeasureUnitName");
                String msg4=TypeConvert.toString( map.get("ProCategoryName"));
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
        if(storageLocation==null){
            alert("无需提交！");
            return;
        }
        ViewUtil.confirm("提示", "确认提交库位为:" + storageLocation.getName()+ " 的盘点"+getTypeName(StorageTakeRec.getRecState())+"扫描数据？账面计" + scope.key("recDetailCounts").val() + "件，扫描" + scanCounts + "件。", new CallBack() {
            @Override
            public Object run() {//提交
                Loadding.show("正在提交扫描数据...");
                scope.forThread(new CallBack() {
                    @Override
                    public Object run() {
                        return StorageTakeRecScanServiceImpl.getInstance().okScan(StorageTakeRec,storageLocation);
                    }
                }, new DataListener<ResultMsg>() {
                    @Override
                    public void hasChange(ResultMsg resultMsg) {
                        Loadding.hide();
                        SoundSupport.play(R.raw.ok);
                        ViewUtil.confirm("提示", "提交完成！是否继续盘点？", new CallBack() {
                            @Override
                            public Object run() {
                               StorageTakeRec=null;//盘点单
                               storageLocation=null;
                                scanCounts=0;
                               scanStorageLocationCounts=0;
                                SoundSupport.play(R.raw.storage_scan);
                                ViewUtil.alert("盘点开始，请扫描库位！");
                                return null;
                            }
                        },new CallBack(){
                            @Override
                            public Object run() {
                                StorageTake.this.onBackPressed();
                                return null;
                            }
                        });
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
    String getTypeName(int type){//0-已初盘，1-已复盘，2-已查核，3-已稽核
       String[] args=new String[]{"初盘","复盘","查核","稽核"};
        return args[type];
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
