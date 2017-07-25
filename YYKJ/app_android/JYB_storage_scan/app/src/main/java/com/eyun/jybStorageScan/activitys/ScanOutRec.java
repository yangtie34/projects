package com.eyun.jybStorageScan.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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
import com.eyun.jybStorageScan.AppPublic;
import com.eyun.jybStorageScan.R;
import com.eyun.jybStorageScan.cusviews.CusSDDListView;
import com.eyun.jybStorageScan.product.entity.StorageLocation;
import com.eyun.jybStorageScan.product.entity.StorageOutRecScan;
import com.eyun.jybStorageScan.product.service.impl.StorageInRecScanServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageLocationServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageOutRecDetailServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageOutRecScanServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageOutRecServiceImpl;
import com.eyun.jybStorageScan.product.service.impl.StorageOutRecScanServiceImpl;
import com.eyun.jybStorageScan.support.ListItem;
import com.eyun.jybStorageScan.support.SoundSupport;

import java.util.List;
import java.util.Map;

import static com.eyun.framework.util.android.ViewUtil.alert;


public class ScanOutRec extends AngularActivity {
    private String recNumber;//出库单编号
    private StorageLocation storageLocation;
    private int scanStorageLocationCounts = 0;
    private CusSDDListView dlistView;
    private int scanCounts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_out_rec);
        scope.key("title").val("扫描出库");
        dlistView = (CusSDDListView) findViewById(R.id.droplistview);
        editText = (EditText) findViewById(R.id.scanCode);
        SoundSupport.play(R.raw.storage_scan);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String code = s.toString().replace("\n", "");
                if (StringUtils.hasLength(code)) {
                    okScan(code);
                    editText.setText("");
                }
            }
        });
    }

    /**
     * 扫描完成事件
     *
     * @param code
     */
    public void okScan(final String code) {
        someMode=TypeConvert.toBoolean(scope.key("someMode").val());
        Loadding.show("正在扫描！");
        if (storageLocation == null) {//扫描库位
            Loadding.show("正在扫描库位！");
            scope.forThread(new CallBack<StorageLocation>() {
                @Override
                public StorageLocation run() {
                    return StorageLocationServiceImpl.getInstance().getStorageLocatioin(code);
                }
            }, new DataListener<StorageLocation>() {
                @Override
                public void hasChange(final StorageLocation storageLocation) {
                    Loadding.hide();
                    if (storageLocation == null) {
                        alert("无此库位信息！");
                        SoundSupport.play(R.raw.wcxx);
                        return;
                    }

                    scope.forThread(new CallBack() {
                        @Override
                        public Object run() {
                            if(recNumber==null)return 0;
                            return StorageOutRecScanServiceImpl.getInstance().getRecOutStorageLocalNumber(recNumber, storageLocation);
                        }
                    }, new DataListener<Integer>() {
                        @Override
                        public void hasChange(Integer i) {
                            scanStorageLocationCounts = i;
                            scope.key("scanStorageLocationCounts").val(scanStorageLocationCounts);
                        }
                    });

                    ScanOutRec.this.storageLocation = storageLocation;
                    scope.key("storageLocation").val(storageLocation.getName());
                    SoundSupport.play(R.raw.product_scan);
                }
            });
        } else if (StringUtils.hasLength(recNumber)) {//扫描商品处理方法
            callBack=new CallBack() {
                @Override
                public Object run() {
                    ResultMsg resultMsg = StorageOutRecScanServiceImpl.getInstance().getScanForScanOutRec(recNumber, code, AppPublic.ScanType.FJ, storageLocation,someModeNumber);
                    return resultMsg;
                }
            };
            dataListener= new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    Loadding.hide();
                    if (resultMsg.isTure()) {
                        scanCounts++;
                        scope.key("scanCounts").val(scanCounts);
                        scanStorageLocationCounts ++;
                        scope.key("scanStorageLocationCounts").val(scanStorageLocationCounts);
                        StorageOutRecScan storageOutRecScan = (StorageOutRecScan) resultMsg.getObject();
                        initThisScan(storageOutRecScan);
                        //扫描成功
                    } else {
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
        } else {//首次扫描
            scope.forThread(new CallBack<ResultMsg>() {
                @Override
                public ResultMsg run() {
                    return StorageOutRecScanServiceImpl.getInstance().getCCScanProBarCode(code, storageLocation);
                }
            }, new DataListener<ResultMsg>() {
                @Override
                public void hasChange(ResultMsg resultMsg) {
                    Loadding.hide();
                    if (resultMsg.isTure()) {
                        scanCounts++;
                        scope.key("scanCounts").val(1);
                        scanStorageLocationCounts ++;
                        scope.key("scanStorageLocationCounts").val(1);
                        Object[] objs= (Object[]) resultMsg.getObject();
                        StorageOutRecScan storageOutRecScan= (StorageOutRecScan) objs[0];
                        initThisScan(storageOutRecScan);
                        recNumber = objs[1].toString();
                        scope.key("recNumber").val(recNumber);
                        ViewUtil.alert("成功生成临时出库单！请继续扫描商品。");
                        initListView();
                    } else {
                        ViewUtil.alert(resultMsg.getMsg());
                    }
                }
            });
        }
    }

    private void initThisScan(StorageOutRecScan storageOutRecScan) {
        scope.key("ProName").val(storageOutRecScan.getProName());
        scope.key("ProID").val(storageOutRecScan.getProId());
        scope.key("ScanNumber").val(storageOutRecScan.getScanNumber());
        scope.key("ProNumber").val(storageOutRecScan.getScanNumber());
    }

    private void initListView() {
        CusSDDListView.QueryForList queryForList = new CusSDDListView.QueryForList() {
            @Override
            public List<Map<String, Object>> query(String Condition, int currentPage, int numPerPage) {
                return StorageOutRecScanServiceImpl.getInstance().getScanbyRecNumberAndScanType(recNumber, AppPublic.ScanType.FJ);
            }

            @Override
            public View data2view(Map<String, Object> map) {
                String msg1 = map.get("ProID").toString();
                String msg2 = map.get("ProName").toString();
                String msg3 = map.get("ProNumber").toString() + map.get("ProMeasureUnitName").toString();
                String msg4 = map.get("ProCategoryName").toString();
                View view = new ListItem(msg1, msg2, msg3, msg4).getItemView();
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
        if (recNumber == null) {
            alert("无需提交！");
            return;
        }
        AlertDialog.Builder confirm = new AlertDialog.Builder(Scope.activity);
        confirm.setTitle("提示");//设置对话框标题
        confirm.setMessage("是否切换库位？");//设置显示的内容
        confirm.setPositiveButton("切换", new DialogInterface.OnClickListener() {//添加确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                storageLocation = null;
                SoundSupport.play(R.raw.storage_scan);
            }
        });
        confirm.setNegativeButton("直接出仓", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                ViewUtil.confirm("提示", "是否确认提交此次商品分拣扫描数据？共计" + scope.key("scanCounts").val() + "件。", new CallBack() {
                    @Override
                    public Object run() {//提交
                        Loadding.show("正在提交扫描数据并生成出库单...");
                        scope.forThread(new CallBack() {
                            @Override
                            public Object run() {
                                return StorageOutRecScanServiceImpl.getInstance().scanOutRecOk(recNumber);
                            }
                        }, new DataListener<ResultMsg>() {
                            @Override
                            public void hasChange(ResultMsg resultMsg) {
                                Loadding.hide();
                                SoundSupport.play(R.raw.ok);
                                ThreadUtil.sleep(SoundSupport.miles);
                                ScanOutRec.this.onBackPressed();
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
    private static CallBack callBack;
    private static DataListener dataListener;
    public void someModeOk(View view) {
        someModeNumber=TypeConvert.toInteger(scope.key("someModeNumber").val());
        scope.key("windowShowForSomeMode").val(false);
        Loadding.show("正在扫描！");
        scope.forThread(callBack,dataListener);
    }
}
