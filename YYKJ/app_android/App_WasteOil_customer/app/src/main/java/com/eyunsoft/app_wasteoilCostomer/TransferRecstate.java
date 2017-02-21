package com.eyunsoft.app_wasteoilCostomer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import com.eyunsoft.app_wasteoilCostomer.Model.TransferRecState_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;
import com.eyunsoft.app_wasteoilCostomer.Publics.NetWork;
import com.eyunsoft.app_wasteoilCostomer.Publics.TitleSet;
import com.eyunsoft.app_wasteoilCostomer.bll.TransferRecState_BLL;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TransferRecstate extends AppCompatActivity {



    public Button btnQuery;
    public Button btnAccept;
    public Button btnBack;

    public RadioGroup radioGroup;


    public EditText editStart;
    public EditText editEnd;

    public ListView listSelect;
    public List<HashMap<String,Object>> list;


    int mYearStart, mMonthStart, mDayStart,mYearEnd, mMonthEnd, mDayEnd;
    final int DATE_DIALOGStart = 1;
    final int DATE_DIALOGEnd = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_recstate);
        TitleSet.SetTitle(this,3);
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date d1=new Date(time);
        String t1=format.format(d1);
        final Calendar ca = Calendar.getInstance();
        mYearStart = ca.get(Calendar.YEAR);
        mMonthStart = ca.get(Calendar.MONTH);
        mDayStart = ca.get(Calendar.DAY_OF_MONTH);

        mYearEnd= ca.get(Calendar.YEAR);
        mMonthEnd = ca.get(Calendar.MONTH);
        mDayEnd = ca.get(Calendar.DAY_OF_MONTH);



        editStart=(EditText)findViewById(R.id.editStartDate);
        editStart.setText(t1);
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOGStart);

            }
        });
        editStart.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDialog(DATE_DIALOGStart);
                    return true;
                }
                return false;
            }
        });

        editEnd=(EditText)findViewById(R.id.editEndDate);
        editEnd.setText(t1);
        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOGEnd);

            }
        });
        editEnd.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDialog(DATE_DIALOGEnd);
                    return true;
                }
                return false;
            }
        });


        radioGroup=(RadioGroup)findViewById(R.id.rgroupState);

        listSelect=(ListView)findViewById(R.id.listResault);

        //返回
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        //接受确认
        btnAccept=(Button)findViewById(R.id.btnAccept);
        btnAccept.setEnabled(false);
        btnAccept.setBackground(ContextCompat.getDrawable(this,R.drawable.shapedisable));
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NetWork.isNetworkAvailable(TransferRecstate.this))
                {
                    MsgBox.Show(TransferRecstate.this,"网络不可用，请稍后再试");
                    return;
                }
                SimpleAdapter adapter = (SimpleAdapter) listSelect.getAdapter();
                String recNoStr = "";
                int count=0;
                for (int i = 0; i < adapter.getCount(); i++) {
                    Object s = adapter.getItem(i);
                    LinearLayout linearLayout = (LinearLayout) listSelect.getChildAt(i);
                    if (linearLayout != null) {
                        CheckBox checkBox = (CheckBox) linearLayout.getChildAt(1);
                        if(checkBox!=null) {
                            if (checkBox.isChecked()) {
                                if (count != 0) {
                                    recNoStr += ",";
                                }
                                HashMap<String,Object> map=list.get(i);
                                recNoStr +=map.get("RecNumber");
                                count++;
                            }
                        }
                        else
                        {
                            System.out.println("checkBox"+i+"为空");
                        }
                    }
                    else
                    {
                        System.out.println("linearLayout"+i+"为空");
                    }
                }

                TransferRecState_Model model=new TransferRecState_Model();
                model.setCreateComBrID(((App)getApplication()).getSysComBrID());
                model.setCreateComID(((App)getApplication()).getSysComID());
                model.setCreateUserID(((App)getApplication()).getCompanyUserID());
                model.setRecNumber("");




                String mess= TransferRecState_BLL.ConfirmProcess_RecState_Accept(model,recNoStr);
                if(!TextUtils.isEmpty(mess))
                {
                    MsgBox.Show(TransferRecstate.this,mess);
                    return;
                }
                else
                {
                    MsgBox.Show(TransferRecstate.this,"确认成功");
                    InitData();
                }

            }
        });

        //查询
        btnQuery=(Button)findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NetWork.isNetworkAvailable(TransferRecstate.this))
                {
                    MsgBox.Show(TransferRecstate.this,"网络不可用，请稍后再试");
                    return;
                }
                InitData();
            }
        });
    }


    public void InitData()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err","0");
            jsonObject.put("RecComID", Convert.ToString(((App)getApplication()).getSysComID()));
            jsonObject.put("RecTimeStart", editStart.getText().toString());
            jsonObject.put("RecTimeEnd", editEnd.getText().toString());

            if(radioGroup.getCheckedRadioButtonId()==R.id.radioAccept)
            {
                btnAccept.setEnabled(false);
                btnAccept.setBackground(ContextCompat.getDrawable(this,R.drawable.shapedisable));
                jsonObject.put("IsAccept",1);
            }
            else
            {
                btnAccept.setEnabled(true);
                btnAccept.setBackground(ContextCompat.getDrawable(this,R.drawable.shape));
                jsonObject.put("IsAccept",0);
            }
            jsonObject.put("ComBrID", Convert.ToString(((App)getApplication()).getSysComBrID()));

            JSONObject jsonHeader = new JSONObject();
            jsonHeader.put("Condition", jsonObject);
            String jsonStr = jsonHeader.toString();

            list= TransferRecState_BLL.TransRec_Select(jsonStr,0,20);

        } catch (Exception ex) {

        }

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, list,//需要绑定的数据
                R.layout.listviewlayout_trans, new String[]{"RecTime", "ProCategoryName", "ProShapeName", "ProNumber", "Remark","CreateComBrName","CreateUserFullname","ProHazardNature","TransferTime","RecStateName","AcceptTime","ApproveTime"},
                new int[]{R.id.txtRecTime, R.id.txtCategory, R.id.txtProShape, R.id.txtProNumber, R.id.txtRemark,R.id.txtCreateComBrName,R.id.txtCreateUser,R.id.txtProHazardNature,R.id.txtTransTime,R.id.txtRecState,R.id.txtAcceptTime,R.id.txtAuditTime}
        );
        listSelect.setAdapter(mSimpleAdapter);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOGStart:
                return new DatePickerDialog(this, mdateListenerStart, mYearStart, mMonthStart, mDayStart);
            case DATE_DIALOGEnd:
                return new DatePickerDialog(this, mdateListenerEnd, mYearEnd, mMonthEnd, mDayEnd);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void displayStart() {
        editStart.setText(new StringBuffer().append(mYearStart).append("-").append(mMonthStart + 1).append("-").append(mDayStart));
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void displayEnd() {
        editEnd.setText(new StringBuffer().append(mYearEnd).append("-").append(mMonthEnd + 1).append("-").append(mDayEnd));
    }

    private DatePickerDialog.OnDateSetListener mdateListenerStart = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYearStart = year;
            mMonthStart = monthOfYear;
            mDayStart = dayOfMonth;
            displayStart();
        }
    };

    private DatePickerDialog.OnDateSetListener mdateListenerEnd = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYearEnd = year;
            mMonthEnd = monthOfYear;
            mDayEnd = dayOfMonth;
            displayEnd();
        }
    };
}
