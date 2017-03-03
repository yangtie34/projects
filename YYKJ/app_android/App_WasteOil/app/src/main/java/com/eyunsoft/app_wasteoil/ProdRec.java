package com.eyunsoft.app_wasteoil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.eyunsoft.app_wasteoil.Model.NameToValue;
import com.eyunsoft.app_wasteoil.Publics.Convert;
import com.eyunsoft.app_wasteoil.Publics.MsgBox;
import com.eyunsoft.app_wasteoil.Publics.NetWork;
import com.eyunsoft.app_wasteoil.Publics.TitleSet;
import com.eyunsoft.app_wasteoil.bll.Category_BLL;
import com.eyunsoft.app_wasteoil.bll.ProdRec_BLL;
import com.eyunsoft.app_wasteoil.bll.SysPublic_BLL;
import com.eyunsoft.app_wasteoil.utils.LoadDialog.LoadDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdRec extends AppCompatActivity {


    public Button btnAdd;
    public Button btnQuery;
    public Button btnDelele;
    public Button btnBack;
    public Button btnUpdate;

    public Map<String,List<NameToValue>> MapCategory;
    private Spinner dropCategorydl;
    private Spinner dropCategoryzl;
    private Spinner dropProShape;

    public ArrayList<NameToValue> listCategory;


    public ArrayList<NameToValue> listProShape;


    public TextView editStart;
    public TextView editEnd;

    public ListView listSelect;

    public List<HashMap<String,Object>> list;

    int mYearStart, mMonthStart, mDayStart,mYearEnd, mMonthEnd, mDayEnd;
    final int DATE_DIALOGStart = 1;
    final int DATE_DIALOGEnd = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_rec);
        TitleSet.SetTitle(this,1);

        dropCategorydl=(Spinner)findViewById(R.id.drop_Category_dl);
        dropCategoryzl=(Spinner)findViewById(R.id.drop_Category_zl);
        dropProShape=(Spinner)findViewById(R.id.drop_ProShape);


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


        editStart=(TextView)findViewById(R.id.editStartDate);
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

        editEnd=(TextView)findViewById(R.id.editEndDate);
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
       /*
        */


        listSelect=(ListView)findViewById(R.id.listResault);

        InitForm();

        //添加
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(ProdRec.this))
                {
                    MsgBox.Show(ProdRec.this,"网络不可用，请稍后再试");
                    return;
                }
                Intent myIntent=new Intent(ProdRec.this,ProdRecAdd.class);
                startActivity(myIntent);
            }
        });

        //返回
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        //删除
        btnDelele=(Button)findViewById(R.id.btnDelete);
        btnDelele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(ProdRec.this))
                {
                    MsgBox.Show(ProdRec.this,"网络不可用，请稍后再试");
                    return;
                }

                SimpleAdapter adapter = (SimpleAdapter) listSelect.getAdapter();
                String recNoStr = "";
                int count=0;if(adapter!=null)
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

                String mess= ProdRec_BLL.ProdRec_Delete(recNoStr);
                if(!TextUtils.isEmpty(mess))
                {
                    MsgBox.Show(ProdRec.this,mess);
                    return;
                }
                else
                {
                    MsgBox.Show(ProdRec.this,"删除成功");
                    processThread();
                }

            }
        });

        //修改
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(ProdRec.this))
                {
                    MsgBox.Show(ProdRec.this,"网络不可用，请稍后再试");
                    return;
                }

                SimpleAdapter adapter = (SimpleAdapter) listSelect.getAdapter();
                String recNoStr = "";
                int count=0;
                if(adapter!=null)
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


                if (TextUtils.isEmpty(recNoStr)) {
                    MsgBox.Show(ProdRec.this,"请选择一项再进行修改");
                    return;
                }

                if (recNoStr.indexOf(',') != -1) {
                    MsgBox.Show(ProdRec.this,"只能选择一项进行修改");
                    return;
                }

                Intent intent=new Intent(ProdRec.this,ProdRecAdd.class);
                intent.putExtra("RecNo",recNoStr);
                startActivity(intent);
            }
        });

        //查询
        btnQuery=(Button)findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoadDialog.dismiss(ProdRec.this);
                processThread();

            }
        });
    }

    public void InitForm()
    {
        long comId=App.getInstance().getSysComID();

        //危废形态
        listProShape= SysPublic_BLL.GetProduct_Shape();
        NameToValue nameAll=new NameToValue();
        nameAll.InfoValue="";
        nameAll.InfoName="==所有==";
        listProShape.add(0,nameAll);
        ArrayAdapter<NameToValue> arrayAdapterShape=new ArrayAdapter<NameToValue>(this,android.R.layout.simple_spinner_item,listProShape);
        dropProShape.setAdapter(arrayAdapterShape);

        //危废种类
        listCategory= Category_BLL.GetProductCategory(comId);
        MapCategory=SysPublic_BLL.formatSelectCategory(listCategory);
        listCategory.add(0,nameAll);
        final ArrayAdapter<NameToValue> arrayAdapterCategory=new ArrayAdapter<NameToValue>(this,android.R.layout.simple_spinner_item,MapCategory.get("0"));

        dropCategorydl.setAdapter(arrayAdapterCategory);
        dropCategorydl.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                ;//文本说明
                ArrayAdapter<NameToValue> arrayAdapterCategoryzl=new ArrayAdapter<NameToValue>(ProdRec.this,android.R.layout.simple_spinner_item,MapCategory.get(arrayAdapterCategory.getItem(arg2).InfoValue));
                dropCategoryzl.setAdapter(arrayAdapterCategoryzl);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    public void InitData()
    {
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, list,//需要绑定的数据
                R.layout.listviewlayout, new String[]{"RecTime", "ProCategoryName", "ProShapeName", "ProNumber", "Remark","CreateComBrName","CreateUserFullname","ProHazardNature"},
                new int[]{R.id.txtRecTime, R.id.txtCategory, R.id.txtProShape, R.id.txtProNumber, R.id.txtRemark,R.id.txtCreateComBrName,R.id.txtCreateUser,R.id.txtProHazardNature}
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

    //定义Handler对象
    private Handler handler =new Handler(){
        @Override
        //当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            //下载完成
            if(msg.what==1) {
                LoadDialog.dismiss(ProdRec.this);
                InitData();
            }

        }
    };
    private void processThread() {
        //构建一个下载进度条
        new Thread() {
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("err","0");
                    jsonObject.put("SysComID", Convert.ToString(App.getInstance().getSysComID()));
                    jsonObject.put("RecTimeStart", editStart.getText().toString());
                    jsonObject.put("RecTimeEnd", editEnd.getText().toString());
                    jsonObject.put("ProCategory", ((NameToValue)dropCategoryzl.getSelectedItem()).InfoValue);

                    if(App.getInstance().userType==0){
                        jsonObject.put("CreateUserID",App.getInstance().getCompanyUserID());
                    }else{
                        jsonObject.put("CreateComCusID",App.getInstance().getCompanyUserID());
                    }

                    jsonObject.put("ProShape",((NameToValue)dropProShape.getSelectedItem()).InfoValue);
                    //jsonObject.put("CreateComBrID", Convert.ToString(App.getInstance().getSysComBrID()));

                    JSONObject jsonHeader = new JSONObject();
                    jsonHeader.put("Condition", jsonObject);
                    String jsonStr = jsonHeader.toString();

                    list= ProdRec_BLL.ProdRec_Select(jsonStr,0,20);

                } catch (Exception ex) {
                        ex.printStackTrace();
                }

                Message msg = new Message();
                msg.what=1;
                handler.sendMessage(msg);
            }
        }.start();
    };
}
