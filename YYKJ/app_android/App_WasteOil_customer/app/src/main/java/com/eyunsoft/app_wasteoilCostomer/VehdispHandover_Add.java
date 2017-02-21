package com.eyunsoft.app_wasteoilCostomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.eyunsoft.app_wasteoilCostomer.Model.NameToValue;
import com.eyunsoft.app_wasteoilCostomer.Model.VehicleDispathHandover_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;
import com.eyunsoft.app_wasteoilCostomer.Publics.NetWork;
import com.eyunsoft.app_wasteoilCostomer.bll.SysPublic_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.VehDisp_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.VehicleDispathHandover_BLL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class VehdispHandover_Add extends AppCompatActivity {

    public Button btnSave;
    public Button btnReset;
    public Button btnBack;

    public Spinner drop_Custuomer;
    public Spinner drop_CompanyBranch;

    private Spinner dropPaymentMode;
    private Spinner dropSettleMode;

    public EditText editCode;
    public EditText editMoney;
    public EditText editPaidMoney;

    public TextView txtVehDisNumber;
    public TextView txtDisState;
    public TextView txtDisZState;


    public ArrayList<NameToValue> arrayListCustomer;
    public ArrayList<NameToValue> arrayListCompanyBranch;

    public ArrayList<NameToValue> listPaymentMode;
    public ArrayList<NameToValue> listSettleMode;


    public List<HashMap<String, Object>> listDetail;

    public ListView listViewDetail;

    public String currentRecNo = "";

    public  int disState=0;
    public  int disZState=0;

    public String tag="";

    public long currentCusID=0;
    public long currentComBrID=0;

    boolean isVerfiy=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehdisp_handover__add);

        listViewDetail = (ListView) findViewById(R.id.listResault);

        editCode=(EditText)findViewById(R.id.editValidCode);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(VehdispHandover_Add.this))
                {
                    MsgBox.Show(VehdispHandover_Add.this,"网络不可用，请稍后再试");
                    return;
                }
                JSONArray jArray = new JSONArray();


                int validSize = 0;
                MsgBox.Show(VehdispHandover_Add.this, testAdapter.mData.get(0).get("ActualProNumber").toString());
                if (testAdapter.mData.size() > 0) {
                    int size = testAdapter.mData.size();
                    for (int i = 0; i < size; i++) {
                        if (Convert.ToInt32(testAdapter.mData.get(i).get("ActualProNumber").toString()) > 0) {
                            validSize++;
                            try {
                                JSONObject jObj = new JSONObject();
                                jObj.put("Remark","");
                                jObj.put("ProCategory", testAdapter.mData.get(i).get("ProCategory").toString());
                                jObj.put("ProCategoryName", testAdapter.mData.get(i).get("ProCategoryName").toString());
                                jObj.put("ProNumber", testAdapter.mData.get(i).get("ProNumber").toString());
                                jObj.put("ProMeasureUnitName", testAdapter.mData.get(i).get("ProMeasureUnitName").toString());
                                jObj.put("ActualProNumber", testAdapter.mData.get(i).get("ActualProNumber").toString());
                                jObj.put("ActualProMeasureUnitName", testAdapter.mData.get(i).get("ProMeasureUnitName").toString());
                                jArray.put(jObj);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }
                    JSONObject root = new JSONObject();
                    if (validSize > 0) {
                        try {

                            root.put("root", jArray);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        long comCusID = 0;
                        if (arrayListCustomer.size() > 0) {
                            comCusID = Convert.ToInt64(((NameToValue) drop_Custuomer.getSelectedItem()).InfoValue);
                        }
                        long comBrID = 0;
                        if (arrayListCompanyBranch.size() > 0) {
                            comBrID = Convert.ToInt64(((NameToValue) drop_CompanyBranch.getSelectedItem()).InfoValue);

                        }

                        long comId=((App)getApplication()).getSysComID();
                        long userId=((App)getApplication()).getCompanyUserID();
                        long comBrId=((App)getApplication()).getSysComBrID();

                        VehicleDispathHandover_Model mo = new VehicleDispathHandover_Model();
                        mo.setHandoverComID(comId);
                        mo.setHandoverComBrID(comBrID);
                        mo.setHandoverComCusID(comCusID);
                        mo.setVehDispNumber(txtVehDisNumber.getText().toString());
                        mo.setVehDispState(disState);
                        mo.setVehDispForwardedState(disZState);
                        mo.setHandleUserID(userId);
                        mo.setCreateUserID(userId);
                        mo.setCreateComBrID (comBrId);
                        mo.setCreateComID(comId);

                        NameToValue mapPayMode= (NameToValue) dropPaymentMode.getSelectedItem();
                        mo.setPaymentMode(Convert.ToInt32(mapPayMode.InfoValue));
                        mo.setHandoverMoney(editMoney.getText().toString());
                        NameToValue mapSettleMode= (NameToValue) dropPaymentMode.getSelectedItem();
                        mo.setSettlementMode(Convert.ToInt32(mapSettleMode.InfoValue));
                        mo.setHandoverPaidMoney(editPaidMoney.getText().toString());
                        mo.setCreateIp("");

                        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date d1=new Date(time);
                        String t1=format.format(d1);
                        mo.setCreateTime(t1);

                        if(isVerfiy)
                        {
                           String code=editCode.getText().toString();
                            String vehDisNumber=txtVehDisNumber.getText().toString();
                            String vefiyMess= VehicleDispathHandover_BLL.IsVerifyCode(3,1,code,vehDisNumber);
                            if(!TextUtils.isEmpty(vefiyMess))
                            {
                                MsgBox.Show(VehdispHandover_Add.this,vefiyMess);
                                return;
                            }

                        }

                        String detailJson=root.toString();

                        System.out.println(detailJson);
                        String mess=VehicleDispathHandover_BLL.Add(mo,detailJson);
                        if(!TextUtils.isEmpty(mess))
                        {
                            MsgBox.Show(VehdispHandover_Add.this,mess);
                        }
                        else
                        {
                            currentCusID=mo.getHandoverComCusID();
                            currentComBrID=mo.getHandoverComBrID();
                            MsgBox.Show(VehdispHandover_Add.this,"添加成功");
                            Intent myIntent=new Intent(VehdispHandover_Add.this,VehdispInfo.class);
                            myIntent.putExtra("RecNo",currentRecNo);
                            myIntent.putExtra("Tag",tag);
                            myIntent.putExtra("CurrentCusID",currentCusID);
                            myIntent.putExtra("CurrentComBrID",currentComBrID);
                            setResult(RESULT_OK,myIntent);
                            finish();
                        }
                    }
                } else {

                    MsgBox.Show(VehdispHandover_Add.this, "数据为空，不能保存");
                }

            }
        });

        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(VehdispHandover_Add.this))
                {
                    MsgBox.Show(VehdispHandover_Add.this,"网络不可用，请稍后再试");
                    return;
                }
                InitForm();
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(VehdispHandover_Add.this,VehdispInfo.class);
                setResult(RESULT_CANCELED,myIntent);
                finish();
            }
        });

        drop_Custuomer = (Spinner) findViewById(R.id.dropCustomer);
        drop_Custuomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!NetWork.isNetworkAvailable(VehdispHandover_Add.this))
                {
                    MsgBox.Show(VehdispHandover_Add.this,"网络不可用，请稍后再试");
                    return;
                }
                InitData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drop_CompanyBranch = (Spinner) findViewById(R.id.dropCompanyBranch);
        drop_CompanyBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!NetWork.isNetworkAvailable(VehdispHandover_Add.this))
                {
                    MsgBox.Show(VehdispHandover_Add.this,"网络不可用，请稍后再试");
                    return;
                }
                InitData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropPaymentMode=(Spinner)findViewById(R.id.drop_PayMode);
        dropSettleMode=(Spinner)findViewById(R.id.drop_SettleMode);

        txtDisState = (TextView) findViewById(R.id.txtDisState);
        txtDisZState = (TextView) findViewById(R.id.txtDisZState);
        txtVehDisNumber = (TextView) findViewById(R.id.txtDisNumber);

        editMoney=(EditText)findViewById(R.id.edit_Money);
        editPaidMoney=(EditText)findViewById(R.id.edit_PaidMoney);

        InitForm();
    }


    /**
     * 初始化窗体
     */
    public void InitForm() {

        //结算方式
        listSettleMode = SysPublic_BLL.GetVehicleDispathHandover_SettlementMode();
        ArrayAdapter<NameToValue> arrayAdapterUnit = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listSettleMode);
        dropSettleMode.setAdapter(arrayAdapterUnit);

        //支付方式
        listPaymentMode = SysPublic_BLL.GetVehicleDispathHandover_PaymentMode();
        ArrayAdapter<NameToValue> arrayAdapterShape = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listPaymentMode);
        dropPaymentMode.setAdapter(arrayAdapterShape);

        Intent myIntent = getIntent();
        String recNo = myIntent.getStringExtra("RecNo");
        if (!TextUtils.isEmpty(recNo)) {
            tag=myIntent.getStringExtra("Tag");
            List<HashMap<String, Object>> listDetail = VehDisp_BLL.VehDis_LoadInfo(recNo);
            if (listDetail.size() > 0) {
                txtVehDisNumber.setText(recNo);
                txtDisZState.setText(listDetail.get(0).get("VehDispForwardedStateName").toString());
                txtDisState.setText(listDetail.get(0).get("VehDispStateName").toString());

                disState=Convert.ToInt32(listDetail.get(0).get("VehDispState").toString());
                disZState=Convert.ToInt32(listDetail.get(0).get("VehDispForwardedState").toString());

                currentRecNo = recNo;
                //交接客户
                arrayListCustomer = VehicleDispathHandover_BLL.GetVehicleDispath_CompanyCustomer(recNo);
                ArrayAdapter<NameToValue> arrayAdapterCus = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, arrayListCustomer);
                drop_Custuomer.setAdapter(arrayAdapterCus);


                //交接分公司
                arrayListCompanyBranch = VehicleDispathHandover_BLL.GetVehicleDispath_CompanyBranch(recNo);
                ArrayAdapter<NameToValue> arrayAdapterCompanyBranch = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, arrayListCompanyBranch);
                drop_CompanyBranch.setAdapter(arrayAdapterCompanyBranch);

                InitData();
            } else {
                btnBack.callOnClick();
            }

        } else {
            btnBack.callOnClick();
        }

    }

    public ListViewTestAdapter testAdapter;

    /**
     * 加载明细
     */
    public void InitData() {
        long comCusID = 0;
        if (arrayListCustomer.size() > 0) {
            comCusID = Convert.ToInt64(((NameToValue) drop_Custuomer.getSelectedItem()).InfoValue);
        }
        long comBrID = 0;
        if (arrayListCompanyBranch.size() > 0) {
            comBrID = Convert.ToInt64(((NameToValue) drop_CompanyBranch.getSelectedItem()).InfoValue);

        }

        listDetail = VehicleDispathHandover_BLL.GetVehicleDispath_Summary(comCusID, comBrID, currentRecNo);

        testAdapter = new ListViewTestAdapter(this, listDetail);
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listDetail,//需要绑定的数据
                R.layout.listviewlayout_handover, new String[]{"ProCategoryName", "ProMeasureUnitName", "ProNumber"},
                new int[]{R.id.txtProCategoryName, R.id.txtProMeasureUnitName, R.id.txtProNumber}
        );
        listViewDetail.setAdapter(testAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            btnBack.callOnClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
