package com.eyunsoft.app_wasteoil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.eyunsoft.app_wasteoil.Model.CompanyCustomerApply_Model;
import com.eyunsoft.app_wasteoil.Publics.MsgBox;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/2/24.
 */

public class Register2 extends Activity {
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mdateListener;
    private boolean dateStart;
    //    private EditText BusinessLicenseImg;
//    private EditText OrganizationCodeImg;
    private EditText DangerWasteName;
    private EditText DangerWasteNumber;
    private EditText DangerWasteLegalPerson;
    private EditText DangerWasteAddress;
    //private EditText DangerWasteImg;
    private EditText DangerWasteBusinessCategory;
    private EditText DangerWasteBusinessMode;
    private EditText DangerWasteBusinessScale;
    private TextView DangerWasteValidityTimeStart;
    private TextView DangerWasteValidityTimeEnd;
//    private EditText AuditState;
//    private EditText CreateIp;
//    private EditText CreateTime;

    private TextView next;
    /**
     * 是否应该隐藏输入法
     * @param v
     * @param event
     * @return
     */
    public static  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    //点击EditText文本框之外任何地方隐藏键盘的解决办法
    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v =getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        DangerWasteName = (EditText) findViewById(R.id.DangerWasteName);
        DangerWasteNumber = (EditText) findViewById(R.id.DangerWasteNumber);
        DangerWasteLegalPerson = (EditText) findViewById(R.id.DangerWasteLegalPerson);
        DangerWasteAddress = (EditText) findViewById(R.id.DangerWasteAddress);
        DangerWasteBusinessCategory = (EditText) findViewById(R.id.DangerWasteBusinessCategory);
        DangerWasteBusinessMode = (EditText) findViewById(R.id.DangerWasteBusinessMode);
        DangerWasteBusinessScale = (EditText) findViewById(R.id.DangerWasteBusinessScale);
        DangerWasteValidityTimeStart= (TextView) findViewById(R.id.DangerWasteValidityTimeStart);
        DangerWasteValidityTimeEnd = (TextView) findViewById(R.id.DangerWasteValidityTimeEnd);

        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register2.this.next();
            }
        });

        mdateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String yearstr=year+"";
                String monthstr=monthOfYear + 1+"";
                String dayOfMonthstr=dayOfMonth+"";
                if((monthOfYear + 1)<10){
                    monthstr="0"+monthstr;
                }
                if(dayOfMonth<10){
                    dayOfMonthstr="0"+dayOfMonthstr;
                }
                String date=yearstr+"-"+monthstr+"-"+dayOfMonthstr;
                if(dateStart){
                    DangerWasteValidityTimeStart.setText(date);
                }else{
                    DangerWasteValidityTimeEnd.setText(date);
                }
            }
        };
        Calendar calendar = Calendar.getInstance();
        datePickerDialog=new DatePickerDialog(
                this, mdateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        DangerWasteValidityTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateStart=true;
                datePickerDialog.show();
            }
        });
        DangerWasteValidityTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateStart=false;
                datePickerDialog.show();
            }
        });
    }

    private void next() {
        CompanyCustomerApply_Model model = Register3.model;
        String strDangerWasteName= DangerWasteName.getText().toString();
        String strDangerWasteNumber = DangerWasteNumber.getText().toString();
        String strDangerWasteLegalPerson = DangerWasteLegalPerson.getText().toString();
        String strDangerWasteAddress = DangerWasteAddress.getText().toString();
        String strDangerWasteBusinessCategory = DangerWasteBusinessCategory.getText().toString();
        String strDangerWasteBusinessMode = DangerWasteBusinessMode.getText().toString();
        String strDangerWasteBusinessScale = DangerWasteBusinessScale.getText().toString();
        String strDangerWasteValidityTimeStart= DangerWasteValidityTimeStart.getText().toString();
        String strDangerWasteValidityTimeEnd = DangerWasteValidityTimeEnd.getText().toString();

        if (strDangerWasteName.length() == 0) {
            MsgBox("法人名称不能为空！"); return;
        } else {
            model.setDangerWasteName(strDangerWasteName);
        }
        if (strDangerWasteNumber.length() == 0) {
            MsgBox("证书编号不能为空！"); return;
        } else {
            model.setDangerWasteNumber(strDangerWasteNumber);
        }
        if (strDangerWasteLegalPerson.length() == 0) {
            MsgBox("法定代表人不能为空！"); return;
        } else {
            model.setDangerWasteLegalPerson(strDangerWasteLegalPerson);
        }
        if (strDangerWasteAddress.length() == 0) {
            MsgBox("住所不能为空！"); return;
        } else {
            model.setDangerWasteAddress(strDangerWasteAddress);
        }
        if (strDangerWasteBusinessCategory.length() == 0) {
            MsgBox("核准经营类别不能为空！"); return;
        } else {
            model.setDangerWasteBusinessCategory(strDangerWasteBusinessCategory);
        }
        if (strDangerWasteBusinessMode.length() == 0) {
            MsgBox("核准经营方式不能为空！"); return;
        } else {
            model.setDangerWasteBusinessMode(strDangerWasteBusinessMode);
        }
        if (strDangerWasteBusinessScale.length() == 0) {
            MsgBox("核准经营规模不能为空！"); return;
        } else {
            model.setDangerWasteBusinessScale(strDangerWasteBusinessScale);
        }
        if (strDangerWasteValidityTimeStart.length() == 0) {
            MsgBox("开始时间不能为空！"); return;
        } else {
            model.setBusinessScope(strDangerWasteValidityTimeStart);
        }
        if (strDangerWasteValidityTimeEnd.length() == 0) {
            MsgBox("结束时间不能为空！"); return;
        } else {
            model.setDangerWasteValidityTimeEnd(strDangerWasteValidityTimeEnd);
        }
        Intent intent=new Intent(this,Register3.class);
        startActivity(intent);
    }
    private void MsgBox(String msg){
        MsgBox.Show(this,msg);
    }
}
