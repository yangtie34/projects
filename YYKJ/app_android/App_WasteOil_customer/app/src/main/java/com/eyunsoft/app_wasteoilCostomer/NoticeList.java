package com.eyunsoft.app_wasteoilCostomer;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;
import com.eyunsoft.app_wasteoilCostomer.Publics.NetWork;
import com.eyunsoft.app_wasteoilCostomer.Publics.TitleSet;
import com.eyunsoft.app_wasteoilCostomer.bll.Notice_BLL;
import com.eyunsoft.app_wasteoilCostomer.utils.LoadDialog.LoadDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NoticeList extends AppCompatActivity {

    public Button btnQuery;
    public Button btnBack;


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
        setContentView(R.layout.activity_notice_list);

        TitleSet.SetTitle(this, 6);

        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(time);
        String t1 = format.format(d1);


        final Calendar ca = Calendar.getInstance();
        mYearStart = ca.get(Calendar.YEAR);
        mMonthStart = ca.get(Calendar.MONTH);
        mDayStart = ca.get(Calendar.DAY_OF_MONTH);

        mYearEnd = ca.get(Calendar.YEAR);
        mMonthEnd = ca.get(Calendar.MONTH);
        mDayEnd = ca.get(Calendar.DAY_OF_MONTH);


        editStart = (EditText) findViewById(R.id.editStartDate);
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


        editEnd = (EditText) findViewById(R.id.editEndDate);
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


        listSelect = (ListView) findViewById(R.id.listResault);
        listSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!NetWork.isNetworkAvailable(NoticeList.this)) {
                    MsgBox.Show(NoticeList.this, "网络不可用，请稍后再试");
                    return;
                }
                System.out.println("click-----");
                if (list != null && list.size() > 0) {
                    System.out.println("size-----" + list.size());
                    HashMap<String, Object> selectMap = list.get(position);
                    String disNo = selectMap.get("NoticeID").toString();
                    System.out.println("disNumber-----" + disNo);
                    if (!TextUtils.isEmpty(disNo)) {

                        Intent myIntent = new Intent(NoticeList.this, NoticeDetail.class);
                        myIntent.putExtra("NoticeID", disNo);
                        startActivity(myIntent);
                    }
                }
            }
        });


        //返回
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        //查询
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!NetWork.isNetworkAvailable(NoticeList.this)) {
                    MsgBox.Show(NoticeList.this, "网络不可用，请稍后再试");
                    return;
                }
                LoadDialog.show(NoticeList.this);
                processThread();
            }
        });
    }

        /**
         * 加载数据
         */
    public void InitData()
    {

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, list,//需要绑定的数据
                R.layout.listviewlayout_notice, new String[]{"NoticeID", "NoticeTime", "NoticeTitle"},
                new int[]{R.id.txtNoticeID, R.id.txtNoticeTime, R.id.txtNoticeTitle}
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
                LoadDialog.dismiss(NoticeList.this);
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
                    jsonObject.put("NoticeToComBrID", Convert.ToString(((App)getApplication()).getSysComBrID()));
                   // jsonObject.put("NoticeToUserID", Convert.ToString(((App)getApplication()).getCompanyUserID()));
                    jsonObject.put("NoticeToComID", Convert.ToString(((App)getApplication()).getSysComID()));
                    jsonObject.put("CreateTimeStart", editStart.getText().toString());
                    jsonObject.put("CreateTimeEnd", editEnd.getText().toString());
                    JSONObject jsonHeader = new JSONObject();
                    jsonHeader.put("Condition", jsonObject);
                    String jsonStr = jsonHeader.toString();



                    list= Notice_BLL.GetNoticeList(jsonStr,0,20);

                } catch (Exception ex) {

                }

                Message msg = new Message();
                msg.what=1;
                handler.sendMessage(msg);
            }
        }.start();
    };
}

