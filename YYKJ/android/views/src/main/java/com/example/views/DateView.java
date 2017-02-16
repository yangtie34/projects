package com.example.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.DatePicker;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/16.
 */

public class DateView extends ViewParent {
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mdateListener;

    public DateView(Scope parent, String Data, String Return) {
        super(parent);
        setData(Data);
        setReturn(Return);
        init();
    }
    public DateView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void init() {
        mdateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String date=new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth).toString();
                DateView.this.setReturn(date);
            }
        };
        Map<Integer,Integer> inidate= (Map<Integer, Integer>) scope.key(getData()).val();
        if(inidate==null){
            final Calendar calendar = Calendar.getInstance();
            inidate=new HashMap<>();
            inidate.put(Calendar.YEAR,calendar.get(Calendar.YEAR));
            inidate.put(Calendar.MONTH,calendar.get(Calendar.MONTH));
            inidate.put(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH));
        }
        datePickerDialog=new DatePickerDialog(
               scope.activity, mdateListener, inidate
                .get(Calendar.YEAR), inidate
                .get(Calendar.MONTH), inidate
                .get(Calendar.DAY_OF_MONTH));


    }
    public void show(){
        datePickerDialog.show();
    }
    public void hide(){
        datePickerDialog.dismiss();
    }

}
