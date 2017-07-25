package com.eyun.jybfreightscan.activitys;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.inputmethodservice.Keyboard;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.eyun.framework.angular.baseview.CusEditView;
import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.common.util.NetWorkUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.product.entity.Vehicle;
import com.eyun.jybfreightscan.product.entity.VehicleDispathState;
import com.eyun.jybfreightscan.product.service.impl.ConsignProBarCodeServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.ConsignScanServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.VehicleDispathServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.VehicleDispathStateServiceImpl;
import com.eyun.jybfreightscan.product.service.impl.VehicleServiceImpl;
import com.eyun.jybfreightscan.support.JDBCEntity;
import com.eyun.jybfreightscan.support.SoundSupport;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsignScan extends AngularActivity {

    public static int recType = 0;//1-始发装车，2-中转卸车，3-中转装车，4-终点卸车
    private String[] formType = {"始发装车", "中转卸车", "中转装车", "终点卸车"};
    private String[] confirmName = {"发车确认", "到车确认", "发车确认", "到车确认"};

    private boolean isFristStart = true;
    private final static String SCAN_ACTION = "urovo.rcv.message";//扫描结束action


    private ScanManager mScanManager;
    private SoundPool soundpool = null;
    private int soundid;
    private String barcodeStr;

    private long currentVehNumber = 0;
    private String currentVehDispNumber = "";
    private long currentVehDrNumber = 0;

    private int _proNumber = 0;

    private CusEditView txtCarBarCode;
    private CusEditView txtBarCode;

    private ListView listResault;

    private Button btnConfirm;
    private Button btnUpLoad;


    int scanMode = -1;


    ArrayAdapter<String> Adapter;
    ArrayList<String> arr = new ArrayList<String>();

    private ProgressDialog pd;

    private LocationManager locationManager;

    private String locationProvider;

    private Location curLocation;

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            soundpool.play(soundid, 1, 1, 0, 0, 1);
            scope.key("scanCode").val("");
            scope.key("carBarCode").val("");

            byte[] barocode = intent.getByteArrayExtra("barocode");
            byte[] barcode = intent.getByteArrayExtra("barcode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.i("debug", "----codetype--" + temp);
            barcodeStr = new String(barcode, 0, barocodelen);

            if (TypeConvert.toBoolean(scope.key("carSelectWindows").val())) {
                scope.key("carBarCode").val(barcodeStr);
                scanMode = 0;
            } else {
                scope.key("scanCode").val(barcodeStr);
                scanMode = 1;
            }

            Scan(barcodeStr);

        }
    };

    /**
     * 界面初始化
     */
    public void initView() {
        Intent myIntent = getIntent();
        if (myIntent.getStringExtra("RecType") != null) {
            String recTypeStr = myIntent.getStringExtra("RecType");
            recType = TypeConvert.toInteger(recTypeStr);
        }

        if (recType > 0) {
            scope.key("title").val(formType[recType - 1]);
        }

        listResault = (ListView) findViewById(R.id.listResault);

        arr.add("正在上传信息");
        Adapter = new ArrayAdapter<String>(ConsignScan.this, android.R.layout.simple_expandable_list_item_1, arr);
        listResault.setAdapter(Adapter);

        btnUpLoad = (Button) findViewById(R.id.btnUpload);
        btnUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //scope.key("upLoadWindows").val(true);
                pd = ProgressDialog.show(ConsignScan.this, "正在上传", "正在上传信息，请稍后…");
                processThread();
               // scope.key("upLoadWindows").val(false);
            }
        });

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setText(confirmName[recType - 1]);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentVehDrNumber * currentVehNumber == 0) {
                    SoundSupport.play(R.raw.vehicle_scan);
                    ShowErrTip("请先扫描车辆再进行确认");
                    return;
                }

                VehicleDispathState mo = new VehicleDispathState();
                mo.setCreateComBrId(AppUser.comBrId);
                mo.setCreateComId(AppUser.comId);
                mo.setCreateIp(NetWorkUtil.getHostIP());
                mo.setCreateUserId(AppUser.userId);
                mo.setHandleUserId(AppUser.userId);
                mo.setHandleUserName(AppUser.username);
                mo.setVehDispNumber(currentVehDispNumber);
                mo.setVehDispState(0);
                if(curLocation!=null) {
                    mo.setLocationLat(curLocation.getLatitude());
                    mo.setLocationLng(curLocation.getLongitude());
                }
                else
                {
                    mo.setLocationLat(0d);
                    mo.setLocationLng(0d);
                }
                mo.setLocationZoom(0);

                String mess = VehicleDispathStateServiceImpl.getInstance().add(mo, recType, currentVehNumber, currentVehDrNumber);
                if (TextUtils.isEmpty(mess)) {
                    ShowErrTip(btnConfirm.getText() + "成功!");
                    btnConfirm.setEnabled(false);
                } else {
                    ShowErrTip(mess);
                    return;
                }

            }
        });

        if (ServerInfo.jdbcEntity.isNet()) {
            btnUpLoad.setEnabled(false);
        }

        ShowErrTip("");


        txtBarCode = (CusEditView) findViewById(R.id.txtBarCode);
        txtBarCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    String o = txtBarCode.getText().toString().trim();
                    scanMode = 1;
                    Scan(o.toString());
                }
                return false;
            }
        });

        txtCarBarCode = (CusEditView) findViewById(R.id.txtCarBarCode);
        txtCarBarCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    String o = txtCarBarCode.getText().toString();
                    scanMode = 0;
                    Scan(o.toString());
                }
                return false;
            }
        });
    }

    public void InitLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            curLocation = location;
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);

    }

    /**
     * 初始化扫描
     */
    private void initScan() {
        // TODO Auto-generated method stub
        mScanManager = new ScanManager();
        mScanManager.openScanner();

        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundid = soundpool.load("/etc/Scan_new.ogg", 1);
    }

    /**
     * 开始扫描
     */
    private void Scan(String barCode) {

        if (TextUtils.isEmpty(barCode)) {
            return;
        }
        switch (scanMode) {
            case -1://未扫描
                return;
            case 0://车辆扫描
                Vehicle vehicle = VehicleServiceImpl.getInstance().LoadInfo(barCode);
                if (vehicle != null && !TextUtils.isEmpty(vehicle.getLicensePlateNumber())) {
                    String vehDispNo = VehicleDispathServiceImpl.getInstance().GetVehDispNumber(vehicle.getVehNumber(), recType);
                    if (!TextUtils.isEmpty(vehDispNo)) {
                        currentVehNumber = vehicle.getVehNumber();
                        currentVehDispNumber = vehDispNo;
                        currentVehDrNumber = VehicleDispathServiceImpl.getInstance().GetVehDrNumber(vehDispNo);
                        scope.key("carNo").val(vehicle.getLicensePlateNumber());
                        scope.key("carSelectWindows").val(false);
                        scope.key("carBarCode").val("");
                        btnConfirm.setEnabled(true);
                    } else {
                        ShowCarErrTip("调度信息不存在！");
                        scope.key("carNo").val("");
                        scope.key("carBarCode").val("");
                        currentVehNumber = 0;
                        currentVehDrNumber = 0;
                        currentVehDispNumber = "";
                        btnConfirm.setEnabled(false);
                        return;
                    }
                } else {
                    currentVehNumber = 0;
                    currentVehDispNumber = "";
                    currentVehDrNumber = 0;
                    ShowCarErrTip("车辆信息不存在！");
                    scope.key("carNo").val("");
                    scope.key("carBarCode").val("");
                    btnConfirm.setEnabled(false);
                    return;
                }
                break;
            case 1://装车扫描

                if (InitData(barCode, recType, false)) {
                    com.eyun.jybfreightscan.product.entity.ConsignScan mo = new com.eyun.jybfreightscan.product.entity.ConsignScan();
                    mo.setCreateComBrId(AppUser.comBrId);
                    mo.setCreateComId(AppUser.comId);
                    mo.setCreateIp("");
                    mo.setScanErrorType(0);
                    mo.setCreateUserId(AppUser.userId);
                    mo.setProNumber(_proNumber);
                    mo.setScanNumber(1);
                    mo.setScanProBarCode(barCode);
                    mo.setScanType(recType);
                    mo.setRecNumber(scope.key("recNumber").val().toString());
                    mo.setVehDispNumber(currentVehDispNumber);
                    mo.setVehNumber(currentVehNumber);
                    if (ServerInfo.jdbcEntity.isNet()) {
                        mo.setIsUpload(true);
                    } else {
                        mo.setIsUpload(false);
                    }

                    String mess = ConsignScanServiceImpl.getInstance().Add(mo);
                    if (TextUtils.isEmpty(mess)) {
                        SoundSupport.play(R.raw.ok);
                        ShowErrTip("扫描成功");
                        InitData(barCode, recType, true);
                    } else {
                        ShowErrTip(mess);

                    }
                    DelBarCode();
                }
                break;
        }
    }

    /**
     * 初始化界面
     *
     * @param barcode
     * @param scanType
     * @param IsRefresh
     * @return
     */
    public boolean InitData(String barcode, int scanType, boolean IsRefresh) {
        if (currentVehNumber == 0)//未选择车辆
        {
            SoundSupport.play(R.raw.vehicle_scan);
            ShowErrTip("请先选择车辆再进行装卸车");
            DelBarCode();
            return false;
        }
        List<Map<String, Object>> list = ConsignProBarCodeServiceImpl.getInstance().GetSimpleInfo(barcode);
        if (list != null && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            String recNumber = map.get("RecNumber").toString();

            scope.key("receiveName").val(map.get("ReceiveName").toString());
            scope.key("comBrDelivery").val(map.get("ComBrNameDelivery"));
            scope.key("deliveryName").val(map.get("DeliveryName").toString());
            scope.key("recNumber").val(recNumber);

            int proNumber = TypeConvert.toInteger(map.get("ProNumber").toString());
            _proNumber = proNumber;
            boolean isBat = false;
            int barCodeNumber = ConsignProBarCodeServiceImpl.getInstance().GetSumCount(recNumber);
            if (barCodeNumber > 0) {
                if (barCodeNumber >= 15)
                    isBat = true;
            } else {
                ShowErrTip("网络错误，请重新扫描");
                DelBarCode();
                return false;

            }
            int scanNumberAll = ConsignScanServiceImpl.getInstance().GetScanNumber(recNumber, scanType);
            scope.key("scanNumber").val(scanNumberAll + "/" + proNumber);

            if (!IsRefresh) {
                if (scanNumberAll >= proNumber) {
                    ShowErrTip("扫描重复");
                    DelBarCode();
                    return true;
                }
                if (!isBat) {
                    int scanNumber = ConsignScanServiceImpl.getInstance().GetScanNumber(barcode, recNumber, scanType);
                    if (scanNumber == 1) {
                        ShowErrTip("扫描重复");
                        DelBarCode();
                        return false;
                    }
                }
            }
            return true;
        } else {
            _proNumber = 0;
            ShowErrTip("条码信息不存在，请重试");
            DelBarCode();
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consign_scan);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());


        initView();

        //初始化扫描
        initScan();

        InitLocation();


    }

    /**
     * 错误提示-货物扫描
     *
     * @param tip
     */
    public void ShowErrTip(String tip) {
        scope.key("scanErrTip").val(tip);
    }

    /**
     * 错误提示-车辆扫描
     *
     * @param tip
     */
    public void ShowCarErrTip(String tip) {
        scope.key("carErrTip").val(tip);
    }

    /**
     * 清空扫描窗口
     */
    public void DelBarCode() {
        scope.key("scanCode").val("");
    }

    /**
     * 切换车辆
     *
     * @param view
     */
    public void changeCar(View view) {
        scope.key("carSelectWindows").val(true);
    }

    //定义Handler对象
    private Handler handler = new Handler() {
        @Override
        //当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //只要执行到这里就关闭对话框
            System.out.println("当前个数" + arr.size());
            Adapter.notifyDataSetChanged();

            //下载完成
            if (msg.what == 1) {
              //  scope.key("upLoadWindows").val(false);
                pd.dismiss();
            }

        }
    };

    private void processThread() {
        //构建一个下载进度条

        new Thread() {
            public void run() {

                List<Map<String, Object>> list = ConsignScanServiceImpl.getInstance().GetNoUpLoad(AppUser.comBrId, AppUser.comId, AppUser.userId);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < 10; i++) {
                       String recNumer = list.get(i).get("RecNumber").toString();
                        String resault = ConsignScanServiceImpl.getInstance().UpLoad(recNumer);
                        //String resault="第"+i+"条";
                        arr.add(resault);
                        Message msg = new Message();
                        msg.what = 0;
                        //执行完毕后给handler发送一个消息
                        handler.sendMessage(msg);
                    }

                }

              /*  for(int i=0;i<100;i++)
                {
                    String resault="第"+i+"条";
                    arr.add(resault);
                    Message msg = new Message();
                   msg.what = 0;
                    //执行完毕后给handler发送一个消息
                    handler.sendMessage(msg);
                }*/

                Message msg = new Message();
                msg.what = 1;
                //执行完毕后给handler发送一个消息
                handler.sendMessage(msg);


            }
        }.start();
    }

    Runnable add = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            System.out.println("当前个数" + arr.size());
            Adapter.notifyDataSetChanged();
        }
    };


    class editItem extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            arr.set(Integer.parseInt(params[0]), params[1]);
//params得到的是一个数组，params[0]在这里是"0",params[1]是"第1项"
//Adapter.notifyDataSetChanged();
//执行添加后不能调用 Adapter.notifyDataSetChanged()更新UI，因为与UI不是同线程
//下面的onPostExecute方法会在doBackground执行后由UI线程调用
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
// TODO Auto-generated method stub
            super.onPostExecute(result);
            Adapter.notifyDataSetChanged();
//执行完毕，更新UI
        }

    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            curLocation = location;

        }
    };


    @Override
    protected void onDestroy() {

        // TODO Auto-generated method stub
        super.onDestroy();

        if (locationManager != null) {
            //移除监听器
            locationManager.removeUpdates(locationListener);
        }


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mScanManager != null) {
            mScanManager.stopDecode();
        }
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initScan();
        scope.key("scanCode").val("");

        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && isFristStart) {
            isFristStart = false;
            SoundSupport.play(R.raw.vehicle_scan);
            scope.key("carSelectWindows").val(true);
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }


}
