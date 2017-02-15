package com.eyunsoft.app_wasteoil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eyunsoft.app_wasteoil.Model.VehicleDispathForwardedState_Model;
import com.eyunsoft.app_wasteoil.Model.VehicleDispathState_Model;
import com.eyunsoft.app_wasteoil.Model.VehicleDispath_Model;
import com.eyunsoft.app_wasteoil.Publics.Convert;
import com.eyunsoft.app_wasteoil.Publics.MsgBox;
import com.eyunsoft.app_wasteoil.Publics.NetWork;
import com.eyunsoft.app_wasteoil.Publics.TitleSet;
import com.eyunsoft.app_wasteoil.bll.VehDisp_BLL;

import java.util.HashMap;
import java.util.List;

public class VehdispDetail extends AppCompatActivity implements View.OnClickListener {

    public String currentRecNo;

    public TextView txtCarDrInfo;


    public Button btnQiCheng;
    public Button btnDaoChe;
    public Button btnZhuangChe;
    public Button btnFengChe;
    public Button btnFaChe;
    public Button btnXieChe;
    public Button btnWanCheng;


    public Button btnZDaoChe;
    public Button btnZXieChe;
    public Button btnZZhuangChe;
    public Button btnZFengChe;
    public Button btnZFaChe;

    public Button btnBack;
    public Button btnJiaoJie;


    private int disState=0;//调度状态
    private int disZState=0;//中转状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehdisp_detail);

        TitleSet.SetTitle(this,41);
        txtCarDrInfo=(TextView)findViewById(R.id.txtCarDriver);


        btnQiCheng=(Button)findViewById(R.id.btnQiC);
        btnQiCheng.setTag("QiCheng");
        btnQiCheng.setOnClickListener(this);

        //到车
        btnDaoChe=(Button)findViewById(R.id.btnDaoC);
        btnDaoChe.setTag("DaoChe");
        btnDaoChe.setOnClickListener(this);

        //装车
        btnZhuangChe=(Button)findViewById(R.id.btnZhuangC);
        btnZhuangChe.setTag("ZhuangChe");
        btnZhuangChe.setOnClickListener(this);

        //封车
        btnFengChe=(Button)findViewById(R.id.btnFengC);
        btnFengChe.setTag("FengChe");
        btnFengChe.setOnClickListener(this);

        //发车
        btnFaChe=(Button)findViewById(R.id.btnFaC);
        btnFaChe.setTag("FaChe");
        btnFaChe.setOnClickListener(this);

        btnXieChe=(Button)findViewById(R.id.btnXieC);
        btnXieChe.setTag("XieChe");
        btnXieChe.setOnClickListener(this);

        btnWanCheng=(Button)findViewById(R.id.btnWanC);
        btnWanCheng.setTag("WanCheng");
        btnWanCheng.setOnClickListener(this);

        btnZDaoChe=(Button)findViewById(R.id.btnZDaoC);
        btnZDaoChe.setTag("ZDaoChe");
        btnZDaoChe.setOnClickListener(this);

        btnZXieChe=(Button)findViewById(R.id.btnZXieC);
        btnZXieChe.setTag("ZXieChe");
        btnZXieChe.setOnClickListener(this);

        btnZZhuangChe=(Button)findViewById(R.id.btnZZhuangC);
        btnZZhuangChe.setTag("ZZhuangChe");
        btnZZhuangChe.setOnClickListener(this);

        btnZFengChe=(Button)findViewById(R.id.btnZFengC);
        btnZFengChe.setTag("ZFengChe");
        btnZFengChe.setOnClickListener(this);

        btnZFaChe=(Button)findViewById(R.id.btnZFaC);
        btnZFaChe.setTag("ZFaChe");
        btnZFaChe.setOnClickListener(this);

        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setTag("Back");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //交接确认
        btnJiaoJie=(Button)findViewById(R.id.btnJiaoJ);
        btnJiaoJie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(VehdispDetail.this,VehdispHandover_Add.class);
                myIntent.putExtra("RecNo",currentRecNo);
                startActivityForResult(myIntent,0);
            }
        });

        InitForm();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       InitForm();
    }
    @Override
    public void onClick(View v) {

        if(!NetWork.isNetworkAvailable(VehdispDetail.this))
        {
            MsgBox.Show(VehdispDetail.this,"网络不可用，请稍后再试");
            return;
        }
        Button btn = (Button) v;

        final String tag = btn.getTag().toString();
        final String txt = btn.getText().toString();
        String msg = "是否确定" + txt;

        new AlertDialog.Builder(this).setTitle("确认操作").setMessage(msg).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {

                VehicleDispathForwardedState_Model moForwardedState = new VehicleDispathForwardedState_Model();
                moForwardedState.setProcessUpperComBrID(((App) getApplication()).getSysComBrID());
                moForwardedState.setProcessLowerComBrID(((App) getApplication()).getSysComBrID());
                moForwardedState.setCreateUserID(((App) getApplication()).getCompanyUserID());
                moForwardedState.setCreateComBrID(((App) getApplication()).getSysComBrID());
                moForwardedState.setCreateComID(((App) getApplication()).getSysComID());
                moForwardedState.setCreateIp("");

                VehicleDispathState_Model moState = new VehicleDispathState_Model();
                moState.setCreateUserID(((App) getApplication()).getCompanyUserID());
                moState.setCreateComBrID(((App) getApplication()).getSysComBrID());
                moState.setCreateComID(((App) getApplication()).getSysComID());
                moState.setCreateIp("");

                String mess = "";
                switch (tag) {
                    case "ZFaChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_FaChe(moForwardedState, currentRecNo);
                        break;
                    case "ZFengChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_FengChe(moForwardedState, currentRecNo);
                        break;
                    case "ZZhuangChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_ZhuangChe(moForwardedState, currentRecNo);
                        break;
                    case "ZXieChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_XieChe(moForwardedState, currentRecNo);
                        break;
                    case "ZDaoChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispDispForwardedState_DaoChe(moForwardedState, currentRecNo);
                        break;
                    case "WanCheng":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_Ok(moState, currentRecNo);
                        break;
                    case "XieChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_XieChe(moState, currentRecNo);
                        break;
                    case "FaChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_FaChe(moState, currentRecNo);
                        break;
                    case "FengChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_FengChe(moState, currentRecNo);
                        break;
                    case "ZhuangChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_ZhuangChe(moState, currentRecNo);
                        break;
                    case "DaoChe":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_DaoChe(moState, currentRecNo);
                        break;
                    case "QiCheng":
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_QiCheng(moState, currentRecNo);
                        break;

                }

                if (!TextUtils.isEmpty(mess.trim())) {
                    MsgBox.Show(VehdispDetail.this, txt+"失败："+mess);

                } else
                {
                    MsgBox.Show(VehdispDetail.this,txt+"成功");
                    InitForm();
                }
            }
        }).setNegativeButton("否", null).show();

    }

    /**
     * 加载数据
     */
    public void InitForm()
    {
        Intent myIntent=getIntent();
        String disNumber=myIntent.getStringExtra("RecNo");
        if(!TextUtils.isEmpty(disNumber))
        {
            List<HashMap<String,Object>>  listDetail= VehDisp_BLL.VehDis_LoadInfo(disNumber);
            if(listDetail.size()>0)
            {
                currentRecNo=disNumber;

                txtCarDrInfo.setText("["+listDetail.get(0).get("LicensePlateNumber").toString()+","+listDetail.get(0).get("VehDrName").toString()+"("+listDetail.get(0).get("VehDrPhone").toString()+")]");


                switch (disState)
                {

                    case 0://已调度
                        SetButton(btnQiCheng,true);

                        SetButton(btnDaoChe,false);
                        SetButton(btnFaChe,false);
                        SetButton(btnFengChe,false);
                        SetButton(btnWanCheng,false);
                        SetButton(btnXieChe,false);
                        SetButton(btnZhuangChe,false);

                        SetButton(btnZDaoChe,false);
                        SetButton(btnZFaChe,false);
                        SetButton(btnZFengChe,false);
                        SetButton(btnZXieChe,false);
                        SetButton(btnZZhuangChe,false);
                        SetButton(btnJiaoJie,false);
                        break;
                    case 1://已启程
                        SetButton(btnDaoChe,true);

                        SetButton(btnQiCheng,false);
                        SetButton(btnFaChe,false);
                        SetButton(btnFengChe,false);
                        SetButton(btnWanCheng,false);
                        SetButton(btnXieChe,false);
                        SetButton(btnZhuangChe,false);

                        SetButton(btnZDaoChe,false);
                        SetButton(btnZFaChe,false);
                        SetButton(btnZFengChe,false);
                        SetButton(btnZXieChe,false);
                        SetButton(btnZZhuangChe,false);

                        SetButton(btnJiaoJie,false);
                        break;
                    case 2://已到车
                        SetButton(btnZhuangChe,true);

                        SetButton(btnQiCheng,false);
                        SetButton(btnFaChe,false);
                        SetButton(btnFengChe,false);
                        SetButton(btnWanCheng,false);
                        SetButton(btnXieChe,false);
                        SetButton(btnDaoChe,false);

                        SetButton(btnZDaoChe,false);
                        SetButton(btnZFaChe,false);
                        SetButton(btnZFengChe,false);
                        SetButton(btnZXieChe,false);
                        SetButton(btnZZhuangChe,false);

                        SetButton(btnJiaoJie,false);
                        break;
                    case 3://已装车
                        SetButton(btnFengChe,true);

                        SetButton(btnQiCheng,false);
                        SetButton(btnFaChe,false);
                        SetButton(btnDaoChe,false);
                        SetButton(btnWanCheng,false);
                        SetButton(btnXieChe,false);
                        SetButton(btnZhuangChe,false);

                        SetButton(btnZDaoChe,false);
                        SetButton(btnZFaChe,false);
                        SetButton(btnZFengChe,false);
                        SetButton(btnZXieChe,false);
                        SetButton(btnZZhuangChe,false);

                        SetButton(btnJiaoJie,true);
                        break;
                    case 4://已封车
                        SetButton( btnFaChe,true);

                        SetButton(btnQiCheng,false);
                        SetButton(btnDaoChe,false);
                        SetButton(btnFengChe,false);
                        SetButton(btnWanCheng,false);
                        SetButton(btnXieChe,false);
                        SetButton(btnZhuangChe,false);

                        SetButton(btnZDaoChe,false);
                        SetButton(btnZFaChe,false);
                        SetButton(btnZFengChe,false);
                        SetButton(btnZXieChe,false);
                        SetButton(btnZZhuangChe,false);

                        SetButton(btnJiaoJie,false);
                        break;
                    case 5://已发车
                        SetButton(btnXieChe,true);

                        SetButton(btnQiCheng,false);
                        SetButton(btnFaChe,false);
                        SetButton(btnFengChe,false);
                        SetButton(btnWanCheng,false);
                        SetButton(btnDaoChe,false);
                        SetButton(btnZhuangChe,false);

                        SetButton(btnJiaoJie,true);

                        switch (disZState)
                        {
                            case 0://未中转
                                SetButton(btnZDaoChe,false);
                                SetButton(btnZFaChe,false);
                                SetButton(btnZFengChe,false);
                                SetButton(btnZXieChe,false);
                                SetButton(btnZZhuangChe,false);
                                break;
                            case 1://中转未到车
                                SetButton(btnZDaoChe,true);

                                SetButton(btnZFaChe,false);
                                SetButton(btnZFengChe,false);
                                SetButton(btnZXieChe,false);
                                SetButton(btnZZhuangChe,false);
                                break;
                            case 2://中转已到车
                                SetButton(btnZDaoChe,false);
                                SetButton(btnZFaChe,true);
                                SetButton(btnZFengChe,false);
                                SetButton(btnZXieChe,true);
                                SetButton(btnZZhuangChe,true);
                                break;
                            case 3://中转已卸车
                                SetButton(btnZDaoChe,false);
                                SetButton(btnZFaChe,false);
                                SetButton(btnZFengChe,false);
                                SetButton(btnZXieChe,false);
                                SetButton(btnZZhuangChe,true);
                                break;
                            case 4://中转已装车
                                SetButton(btnZDaoChe,false);
                                SetButton(btnZFaChe,false);
                                SetButton(btnZFengChe,true);
                                SetButton(btnZXieChe,false);
                                SetButton(btnZZhuangChe,false);
                                break;
                            case 5://中转已封车
                                SetButton(btnZDaoChe,false);
                                SetButton(btnZFaChe,true);
                                SetButton(btnZFengChe,false);
                                SetButton(btnZXieChe,false);
                                SetButton(btnZZhuangChe,false);
                                break;
                            case 6://中转已发车
                                SetButton(btnZDaoChe,false);
                                SetButton(btnZFaChe,false);
                                SetButton(btnZFengChe,false);
                                SetButton(btnZXieChe,false);
                                SetButton(btnZZhuangChe,false);
                                break;
                            case 7://中转完结
                                SetButton(btnZDaoChe,false);
                                SetButton(btnZFaChe,false);
                                SetButton(btnZFengChe,false);
                                SetButton(btnZXieChe,false);
                                SetButton(btnZZhuangChe,false);
                                break;
                        }

                        break;
                    case 6://已卸车
                        SetButton( btnWanCheng,true);

                        SetButton(btnQiCheng,false);
                        SetButton(btnFaChe,false);
                        SetButton(btnFengChe,false);
                        SetButton(btnDaoChe,false);
                        SetButton(btnXieChe,false);
                        SetButton(btnZhuangChe,false);

                        SetButton(btnZDaoChe,false);
                        SetButton(btnZFaChe,false);
                        SetButton(btnZFengChe,false);
                        SetButton(btnZXieChe,false);
                        SetButton(btnZZhuangChe,false);

                        SetButton(btnJiaoJie,false);
                        break;
                    case 7://已完成
                    default:
                        SetButton(btnDaoChe,false);

                        SetButton(btnQiCheng,false);
                        SetButton(btnFaChe,false);
                        SetButton(btnFengChe,false);
                        SetButton(btnWanCheng,false);
                        SetButton(btnXieChe,false);
                        SetButton(btnZhuangChe,false);

                        SetButton(btnZDaoChe,false);
                        SetButton(btnZFaChe,false);
                        SetButton(btnZFengChe,false);
                        SetButton(btnZXieChe,false);
                        SetButton(btnZZhuangChe,false);

                        SetButton(btnJiaoJie,false);
                        break;
                }

            }
           // txtCarNo.setText(model.getVehNumber());


        }
    }

    /**
     * 设置按钮背景
     * @param btn
     * @param isEnable
     */
    public void  SetButton(Button btn,boolean isEnable)
    {
        btn.setEnabled(isEnable);
        if(isEnable) {
            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.shape));
        }
        else
        {
            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.shapedisable));
        }
    }
}
