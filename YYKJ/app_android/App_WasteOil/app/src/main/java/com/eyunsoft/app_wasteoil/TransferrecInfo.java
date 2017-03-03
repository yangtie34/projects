package com.eyunsoft.app_wasteoil;

import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.eyunsoft.app_wasteoil.Model.NameToValue;
import com.eyunsoft.app_wasteoil.Model.TransferRecState_Model;
import com.eyunsoft.app_wasteoil.Model.TransferRec_Model;
import com.eyunsoft.app_wasteoil.Publics.MsgBox;
import com.eyunsoft.app_wasteoil.Publics.TitleSet;
import com.eyunsoft.app_wasteoil.bll.TransRec_BLL;
import com.eyunsoft.app_wasteoil.bll.TransferRecState_BLL;
import com.eyunsoft.app_wasteoil.bll.VehicleDispathHandover_BLL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransferrecInfo extends AppCompatActivity {


    public Button btnAccept;
    public Button btnBack;


    public TextView txtTranTime;
    public TextView txtTranAddress;
    public TextView txtTranTraboo;
    public TextView txtProNatuere;
    public TextView txtProCategoryName;
    public TextView txtProNumber;
    public TextView txtProDangerComponent;
    public TextView txtRemark;



    public String recNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferrec_info);
        TitleSet.SetTitle(this,23);


        txtProCategoryName = (TextView) findViewById(R.id.txtProCategoryName);
        txtRemark = (TextView) findViewById(R.id.txtRemark);
        txtTranAddress = (TextView) findViewById(R.id.txtTransAddress);
        txtTranTraboo = (TextView) findViewById(R.id.txtTransferTaboo);
        txtProNatuere = (TextView) findViewById(R.id.textProNature);
        txtTranTime = (TextView) findViewById(R.id.txtTransTime);
        txtProDangerComponent = (TextView) findViewById(R.id.txtProDangerComponent);
        txtProNumber=(TextView)findViewById(R.id.txtProNumber);


        InitForm();
        //抢单
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransferRecState_Model model=new TransferRecState_Model();
                model.setCreateComBrID(App.getInstance().getSysComBrID());
                model.setCreateComID(App.getInstance().getSysComID());
                model.setCreateUserID(App.getInstance().getCompanyUserID());
                model.setRecNumber("");

                String mess= TransferRecState_BLL.ConfirmProcess_RecState_Accept(model,recNumber);
                if(!TextUtils.isEmpty(mess))
                {
                    MsgBox.Show(TransferrecInfo.this,mess);
                    return;
                }
                else
                {
                    MsgBox.Show(TransferrecInfo.this,"抢单成功");
                    finish();
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


    }

    /**
     * 初始化窗体
     */
    public void InitForm()
    {
        Intent myIntent=getIntent();
        recNumber=myIntent.getStringExtra("RecNumber");
        System.out.println("获取到一个传值："+recNumber);
        if(!TextUtils.isEmpty(recNumber))
        {
            TransferRec_Model mo= TransRec_BLL.TransRec_LoadData(recNumber);
            if(mo.IsExist()) {
                txtProDangerComponent.setText(mo.getProDangerComponent());
                txtProNatuere.setText(mo.getProHazardNature());
                txtTranAddress.setText(mo.getTransferAddress());
                txtTranTime.setText(mo.getTransferTime().replace("T", " "));
                txtProCategoryName.setText(mo.getProCategoryName());
                txtProNumber.setText(mo.getProNumber() + mo.getProMeasureUnitName());
                txtTranTraboo.setText(mo.getTransferTaboo());
                txtRemark.setText(mo.getRemark());
            }
        }
    }

    /**
     *
     */
    public void InitData()
    {

    }
}
