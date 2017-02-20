package com.eyunsoft.app_wasteoilCostomer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.eyunsoft.app_wasteoilCostomer.Model.VehicleDispathForwardedState_Model;
import com.eyunsoft.app_wasteoilCostomer.Model.VehicleDispathState_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;
import com.eyunsoft.app_wasteoilCostomer.Publics.NetWork;
import com.eyunsoft.app_wasteoilCostomer.Publics.TitleSet;
import com.eyunsoft.app_wasteoil.R;
import com.eyunsoft.app_wasteoilCostomer.bll.VehDisp_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.VehicleDispathHandover_BLL;

import java.util.HashMap;
import java.util.List;

public class VehdispInfo extends AppCompatActivity implements View.OnClickListener {

    public String currentRecNo;

    public TextView txtCarDrInfo;
    public TextView txtCarNo;

    private boolean isValitity=false;//是否验证验证码

    private boolean isJiaoJie=true;//是事交接

    public Button btnOpt1;
    public Button btnOpt2;

    public ListView listViewAll;
    public ListView listViewDeal;
    public ListView listViewNoDeal;


    public List<HashMap<String, Object>> listAll;
    public List<HashMap<String, Object>> listDeal;
    public List<HashMap<String, Object>> listNoDeal;


    private TabHost tabHost;

    private int disState=0;//调度状态
    private int disZState=0;//中转状态

    private String valitCode="";

    public long backCusID=0;
    public long backComBrID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehdisp_info);


        TitleSet.SetTitle(this,41);
        txtCarDrInfo=(TextView)findViewById(R.id.txtCarDriver);
        txtCarNo=(TextView)findViewById(R.id.txtCarNo);

        btnOpt1=(Button)findViewById(R.id.btnOpt1);
        btnOpt1.setOnClickListener(this);


        btnOpt2=(Button)findViewById(R.id.btnOpt2);
        btnOpt2.setOnClickListener(this);

        listViewAll=(ListView)findViewById(R.id.listAll);
        listViewDeal=(ListView)findViewById(R.id.listYes);
        listViewNoDeal=(ListView)findViewById(R.id.listNo);

        InitForm();
        InitList();


        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabWidget tabWidget=tabHost.getTabWidget();

        tabHost.addTab(tabHost.newTabSpec("tab01").setIndicator("全部联单", null).setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab02").setIndicator("已处理", null).setContent(R.id.tab2));
        tabHost.addTab(tabHost.newTabSpec("tab03").setIndicator("未处理", null).setContent(R.id.tab3));

    }


    /**
     * 加载列表
     */
    public  void  InitList()
    {
        //所有联单
        listAll = VehicleDispathHandover_BLL.GetVehicleDispath_Summary(0, 0, currentRecNo);
        SimpleAdapter adapterAll = new SimpleAdapter(this, listAll,//需要绑定的数据
                R.layout.listviewlayout_vehdispinfo, new String[]{"ProCategoryName", "ProMeasureUnitName", "ProNumber"},
                new int[]{R.id.txtProCategoryName, R.id.txtProMeasureUnitName, R.id.txtProNumber}
        );
        listViewAll.setAdapter(adapterAll);

        //已处理联单
        listDeal=VehicleDispathHandover_BLL.GetDispathHandover_Info(0, 20, currentRecNo);
        System.out.println("listDeal的行数是:"+listDeal.size());
        SimpleAdapter adapterDeal = new SimpleAdapter(this, listDeal,//需要绑定的数据
                R.layout.listviewlayout_handoverinfo, new String[]{"ProCategoryName", "ProMeasureUnitName", "ProNumber","HandoverTime","HandoverMoney","HandoverPaidMoney","HandoverActualMoney","ActualProNumber"},
                new int[]{R.id.txtProCategoryName, R.id.txtProMeasureUnitName, R.id.txtProNumber,R.id.txtHandoverTime,R.id.txtHandoverMoney,R.id.txtHandoverPaidMoney,R.id.txtHandoverActualMoney,R.id.txtActualProNumber}
        );
        listViewDeal.setAdapter(adapterDeal);


    }


    /**
     * 加载数据
     */
    public void InitForm()
    {
        isJiaoJie=true;
        Intent myIntent=getIntent();
        String disNumber=myIntent.getStringExtra("RecNo");
        if(!TextUtils.isEmpty(disNumber))
        {
            List<HashMap<String,Object>> listDetail= VehDisp_BLL.VehDis_LoadInfo(disNumber);
            if(listDetail.size()>0)
            {
                currentRecNo=disNumber;

                txtCarDrInfo.setText("["+listDetail.get(0).get("VehDrName").toString()+"("+listDetail.get(0).get("VehDrPhone").toString()+")]");
                txtCarNo.setText(listDetail.get(0).get("LicensePlateNumber").toString());

                disState= Convert.ToInt32(listDetail.get(0).get("VehDispState").toString());
                disZState=Convert.ToInt32(listDetail.get(0).get("VehDispForwardedState").toString());

                switch (disState)
                {

                    case 0://已调度
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("受理确认");
                        btnOpt1.setTag("ShouLi");

                        btnOpt2.setVisibility(View.INVISIBLE);
                        btnOpt2.setText("");
                        btnOpt2.setTag("");
                        break;
                    case 1://已受理
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("启程确认");
                        btnOpt1.setTag("QiCheng");

                        btnOpt2.setVisibility(View.INVISIBLE);
                        btnOpt2.setText("");
                        btnOpt2.setTag("");
                        break;
                    case 2://已启程
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("到车确认");
                        btnOpt1.setTag("DaoChe");

                        btnOpt2.setVisibility(View.INVISIBLE);
                        btnOpt2.setText("");
                        btnOpt2.setTag("");
                        break;
                    case 3://已到车
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("发车确认");
                        btnOpt1.setTag("FaChe");

                        btnOpt2.setVisibility(View.VISIBLE);
                        btnOpt2.setText("卸车确认");
                        btnOpt2.setTag("XieChe");
                        break;
                    case 4://已装车
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("发车确认");
                        btnOpt1.setTag("FaChe");

                        btnOpt2.setVisibility(View.INVISIBLE);
                        btnOpt2.setText("");
                        btnOpt2.setTag("");
                        break;
                    case 5://已封车
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("发车确认");
                        btnOpt1.setTag("FaChe");

                        btnOpt2.setVisibility(View.INVISIBLE);
                        btnOpt2.setText("");
                        btnOpt2.setTag("");

                        break;
                    case 6://已发车
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("卸车确认");
                        btnOpt1.setTag("XieChe");

                        switch (disZState) {

                            case  0://未中转
                                btnOpt2.setVisibility(View.VISIBLE);
                                btnOpt2.setText("到车确认");
                                btnOpt2.setTag("ZDaoChe");
                                break;
                            case  1:// 中转未到车
                                btnOpt2.setVisibility(View.VISIBLE);
                                btnOpt2.setText("到车确认");
                                btnOpt2.setTag("ZDaoChe");
                                break;
                            case  2://中转已到车
                                btnOpt2.setVisibility(View.VISIBLE);
                                btnOpt2.setText("发车确认");
                                btnOpt2.setTag("ZFaChe");
                                break;
                            case  3://中转已卸车
                                btnOpt2.setVisibility(View.VISIBLE);
                                btnOpt2.setText("发车确认");
                                btnOpt2.setTag("ZFaChe");
                                break;
                            case  4://中转已装车
                                btnOpt2.setVisibility(View.VISIBLE);
                                btnOpt2.setText("发车确认");
                                btnOpt2.setTag("ZFaChe");
                                break;
                            case  5://中转已封车
                                btnOpt2.setVisibility(View.VISIBLE);
                                btnOpt2.setText("发车确认");
                                btnOpt2.setTag("ZFaChe");
                                break;
                            case  6://中转已发车
                                btnOpt2.setVisibility(View.VISIBLE);
                                btnOpt2.setText("到车确认");
                                btnOpt2.setTag("ZDaoChe");
                                break;
                            case  7://中转完结
                                btnOpt2.setVisibility(View.INVISIBLE);
                                btnOpt2.setText("");
                                btnOpt2.setTag("");
                                break;

                        }
                        break;
                    case 7://已卸车
                        btnOpt1.setVisibility(View.VISIBLE);
                        btnOpt1.setText("完成确认");
                        btnOpt1.setTag("WanCheng");

                        btnOpt2.setVisibility(View.INVISIBLE);
                        btnOpt2.setText("");
                        btnOpt2.setTag("");
                        break;
                    case 8://已完成
                        btnOpt1.setVisibility(View.INVISIBLE);
                        btnOpt1.setText("");
                        btnOpt1.setTag("");

                        btnOpt2.setVisibility(View.INVISIBLE);
                        btnOpt2.setText("");
                        btnOpt2.setTag("");
                        break;
                    default:

                        break;
                }


            }

        }
    }

    @Override
    public void onClick(View v) {

        if(!NetWork.isNetworkAvailable(VehdispInfo.this))
        {
            MsgBox.Show(VehdispInfo.this,"网络不可用，请稍后再试");
            return;
        }
        Button btn = (Button) v;
        if(v.getVisibility()==View.INVISIBLE)
            return;

        final String tag = btn.getTag().toString();
        final String txt = btn.getText().toString();
        String msg = "是否确定" + txt;

        if(isJiaoJie) {
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

                    String mess = "无任何操作";
                    switch (tag) {
                        case "ZFaChe":
                            if (isJiaoJie) {
                                Intent myIntent = new Intent(VehdispInfo.this, VehdispHandover_Add.class);
                                myIntent.putExtra("RecNo", currentRecNo);
                                myIntent.putExtra("Tag", "ZFaChe");
                                startActivityForResult(myIntent, 1001);
                                return;
                            } else {
                                mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_FaChe(moForwardedState, currentRecNo);
                                break;
                            }

                            //
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
                            if (isValitity) {
                                GetDaoCheCode();
                                if (TextUtils.isEmpty(valitCode)) {
                                    return;
                                }


                                String resault = ValiitCode(valitCode, 1, 4);
                                if (!TextUtils.isEmpty(resault)) {
                                    MsgBox.Show(VehdispInfo.this, "验证码有误");
                                    return;
                                }
                            }
                            mess = VehDisp_BLL.ConfirmProcess_VehDispDispForwardedState_DaoChe(moForwardedState, currentRecNo);
                            break;
                        case "WanCheng":
                            mess = VehDisp_BLL.ConfirmProcess_VehDispState_Ok(moState, currentRecNo);
                            break;
                        case "XieChe":
                            mess = VehDisp_BLL.ConfirmProcess_VehDispState_XieChe(moState, currentRecNo);
                            break;
                        case "FaChe":
                            if (isJiaoJie) {
                                Intent myIntent1 = new Intent(VehdispInfo.this, VehdispHandover_Add.class);
                                myIntent1.putExtra("RecNo", currentRecNo);
                                myIntent1.putExtra("Tag", "FaChe");
                                startActivityForResult(myIntent1, 1002);
                                return;
                            } else {
                                mess = VehDisp_BLL.ConfirmProcess_VehDispState_FaChe(moState, currentRecNo);
                                break;
                            }
                            //

                        case "FengChe":
                            mess = VehDisp_BLL.ConfirmProcess_VehDispState_FengChe(moState, currentRecNo);
                            break;
                        case "ZhuangChe":
                            mess = VehDisp_BLL.ConfirmProcess_VehDispState_ZhuangChe(moState, currentRecNo);
                            break;
                        case "DaoChe":
                            if (isValitity) {
                                GetDaoCheCode();
                                if (TextUtils.isEmpty(valitCode)) {
                                    return;
                                }
                                String resaultDao = ValiitCode(valitCode, 1, 3);
                                if (!TextUtils.isEmpty(resaultDao)) {
                                    MsgBox.Show(VehdispInfo.this, "验证码有误");
                                    return;
                                }
                            }
                            mess = VehDisp_BLL.ConfirmProcess_VehDispState_DaoChe(moState, currentRecNo);
                            break;
                        case "QiCheng":
                            mess = VehDisp_BLL.ConfirmProcess_VehDispState_QiCheng(moState, currentRecNo);
                            break;
                        case "ZQueRen"://
                            mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_ZhongZhuan(moForwardedState, currentRecNo);
                            break;
                        case "ShouLi"://
                            mess = VehDisp_BLL.ConfirmProcess_VehDispState_ShouLi(moState, currentRecNo);
                            break;

                    }

                    if (!TextUtils.isEmpty(mess.trim())) {
                        MsgBox.Show(VehdispInfo.this, txt + "失败：" + mess);

                    } else {
                        MsgBox.Show(VehdispInfo.this, txt + "成功");
                        InitForm();
                        InitList();
                    }
                }
            }).setNegativeButton("否", null).show();
        }
        else
        {
            VehicleDispathForwardedState_Model moForwardedState = new VehicleDispathForwardedState_Model();
            moForwardedState.setProcessUpperComBrID(backComBrID);
            moForwardedState.setProcessLowerComBrID(((App) getApplication()).getSysComBrID());
            moForwardedState.setProcessLowerComCusID(0);
            moForwardedState.setProcessUpperComCusID(0);
            moForwardedState.setCreateUserID(((App) getApplication()).getCompanyUserID());
            moForwardedState.setCreateComBrID(((App) getApplication()).getSysComBrID());
            moForwardedState.setCreateComID(((App) getApplication()).getSysComID());
            moForwardedState.setCreateIp("");

            VehicleDispathState_Model moState = new VehicleDispathState_Model();
            moState.setCreateUserID(((App) getApplication()).getCompanyUserID());
            moState.setCreateComBrID(((App) getApplication()).getSysComBrID());
            moState.setCreateComID(((App) getApplication()).getSysComID());
            moState.setCreateIp("");

            String mess = "无任何操作";
            switch (tag) {
                case "ZFaChe":
                    if (isJiaoJie) {
                        Intent myIntent = new Intent(VehdispInfo.this, VehdispHandover_Add.class);
                        myIntent.putExtra("RecNo", currentRecNo);
                        myIntent.putExtra("Tag", "ZFaChe");
                        startActivityForResult(myIntent, 1001);
                        return;
                    } else {
                        mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_FaChe(moForwardedState, currentRecNo);
                        break;
                    }

                    //
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
                    if (isValitity) {
                        GetDaoCheCode();
                        if (TextUtils.isEmpty(valitCode)) {
                            return;
                        }


                        String resault = ValiitCode(valitCode, 1, 4);
                        if (!TextUtils.isEmpty(resault)) {
                            MsgBox.Show(VehdispInfo.this, "验证码有误");
                            return;
                        }
                    }
                    mess = VehDisp_BLL.ConfirmProcess_VehDispDispForwardedState_DaoChe(moForwardedState, currentRecNo);
                    break;
                case "WanCheng":
                    mess = VehDisp_BLL.ConfirmProcess_VehDispState_Ok(moState, currentRecNo);
                    break;
                case "XieChe":
                    mess = VehDisp_BLL.ConfirmProcess_VehDispState_XieChe(moState, currentRecNo);
                    break;
                case "FaChe":
                    if (isJiaoJie) {
                        Intent myIntent1 = new Intent(VehdispInfo.this, VehdispHandover_Add.class);
                        myIntent1.putExtra("RecNo", currentRecNo);
                        myIntent1.putExtra("Tag", "FaChe");
                        startActivityForResult(myIntent1, 1002);
                        return;
                    } else {
                        mess = VehDisp_BLL.ConfirmProcess_VehDispState_FaChe(moState, currentRecNo);
                        break;
                    }
                    //

                case "FengChe":
                    mess = VehDisp_BLL.ConfirmProcess_VehDispState_FengChe(moState, currentRecNo);
                    break;
                case "ZhuangChe":
                    mess = VehDisp_BLL.ConfirmProcess_VehDispState_ZhuangChe(moState, currentRecNo);
                    break;
                case "DaoChe":
                    if (isValitity) {
                        GetDaoCheCode();
                        if (TextUtils.isEmpty(valitCode)) {
                            return;
                        }
                        String resaultDao = ValiitCode(valitCode, 1, 3);
                        if (!TextUtils.isEmpty(resaultDao)) {
                            MsgBox.Show(VehdispInfo.this, "验证码有误");
                            return;
                        }
                    }
                    mess = VehDisp_BLL.ConfirmProcess_VehDispState_DaoChe(moState, currentRecNo);
                    break;
                case "QiCheng":
                    mess = VehDisp_BLL.ConfirmProcess_VehDispState_QiCheng(moState, currentRecNo);
                    break;
                case "ZQueRen"://
                    mess = VehDisp_BLL.ConfirmProcess_VehDispForwardedState_ZhongZhuan(moForwardedState, currentRecNo);
                    break;
                case "ShouLi"://
                    mess = VehDisp_BLL.ConfirmProcess_VehDispState_ShouLi(moState, currentRecNo);
                    break;

            }

            if (!TextUtils.isEmpty(mess.trim())) {
                MsgBox.Show(VehdispInfo.this, txt + "失败：" + mess);

            } else {
                MsgBox.Show(VehdispInfo.this, txt + "成功");
                InitForm();
                InitList();
            }
        }

    }

    /**
     * 设置验证码
     * @param code
     */
    public void SetCode(String code)
    {
        valitCode=code;
    }

    /**
     * 获取到车验证码
     * @return
     */
    public void GetDaoCheCode()
    {

        final EditText editCode=new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("请输入验证码：")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editCode)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(editCode.getText()))
                        {
                            MsgBox.Show(VehdispInfo.this,"验证码不能为空");
                            return;
                        }
                        SetCode(editCode.getText().toString());
                        dialog.dismiss();
                    }
                } )
                .setNegativeButton("取消",  new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        SetCode("");
                        dialog.dismiss();
                    }
                } )
                .show();
    }

    /**
     * 验证验证码
     * @param valitCode
     * @param verfityType
     * @param verfitySort
     * @return
     */
    public String ValiitCode(String valitCode,int verfityType,int verfitySort)
    {
        return  VehicleDispathHandover_BLL.IsVerifyCode(verfityType,verfitySort,valitCode,currentRecNo);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String str=b.getString("Tag");//str即为回传的值

                if(str.equals("ZFaChe"))
                {
                    isJiaoJie=false;
                    btnOpt2.setTag("ZFaChe");
                    btnOpt2.callOnClick();
                }
                else if(str.equals("FaChe"))
                {
                    isJiaoJie=false;
                    btnOpt1.setTag("FaChe");
                    btnOpt1.callOnClick();
                }

                break;
            case RESULT_CANCELED:
                InitForm();
                InitList();
            default:
                break;
        }
    }


}
