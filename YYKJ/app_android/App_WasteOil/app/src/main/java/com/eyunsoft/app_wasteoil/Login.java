package com.eyunsoft.app_wasteoil;

import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eyunsoft.app_wasteoil.Model.CompanyUser_Model;
import com.eyunsoft.app_wasteoil.Publics.Convert;
import com.eyunsoft.app_wasteoil.Publics.MsgBox;
import com.eyunsoft.app_wasteoil.Publics.NetWork;
import com.eyunsoft.app_wasteoil.Publics.TitleSet;
import com.eyunsoft.app_wasteoil.Publics.UpdateManager;
import com.eyunsoft.app_wasteoil.Publics.Version;
import com.eyunsoft.app_wasteoil.bll.CompanyUser_BLL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private long exitTime = 0;//退出次数

    public EditText editUserName;
    public EditText editPwd;

    public TextView txtErrMsg;
    public TextView txtVersion;

    public Button btnLogin;
    public CheckBox checkRemem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_login);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.logo_white);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        TitleSet.SetTitle(this,5);

        UpdateManager updateManager=new UpdateManager(this);
        updateManager.checkUpdateInfo();

        editUserName = (EditText) findViewById(R.id.editUserNo);
        editPwd = (EditText) findViewById(R.id.editUserPwd);

        checkRemem = (CheckBox) findViewById(R.id.checkBox);

        InitData();

        txtErrMsg = (TextView) findViewById(R.id.txtErrMsg);

        txtVersion=(TextView)findViewById(R.id.txtVersion);
        txtVersion.setText("当前版本:"+Version.getCurrentVersion(this));

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanyUser_Model model = new CompanyUser_Model();
                model.setUserName(editUserName.getText().toString());
                model.setPwd(editPwd.getText().toString());


                if(!NetWork.isNetworkAvailable(Login.this))
                {
                    MsgBox.Show(Login.this,"网络不可用，请稍后再试");
                    return;
                }

                long comID = ((App) getApplication()).getSysComID();
                String msg = CompanyUser_BLL.Login(model, comID);
                if (!TextUtils.isEmpty(msg)) {

                    System.out.println("return:" + msg);
                    if (msg.contains("[anyType{}"))//登录失败
                    {
                        String errMsg = msg.replace("[anyType{},", "").replace("]", "");
                        txtErrMsg.setText(errMsg);
                        MsgBox.Show(Login.this, errMsg);
                        return;
                    } else if (msg.contains("anyType{}]"))//登录成功
                    {
                        String jsonStr = msg.substring(1, msg.length()).replace(", anyType{}]", "");
                        try {

                            JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                            String fullName = jsonObject.getString("Fullname");
                            String UserNumber = jsonObject.getString("UserNumber");
                            String combrName = jsonObject.getString("ComBrName");
                            long userID = Convert.ToInt64(jsonObject.getString("UserID"));
                            long comBrID = Convert.ToInt64(jsonObject.getString("ComBrID"));
                            comID = Convert.ToInt64(jsonObject.getString("ComID"));

                            boolean isVeh=false;
                            long vehDrNumber=jsonObject.getLong("VehicleDriverNumber");

                            if(vehDrNumber>0)
                                isVeh=true;
                            ((App) getApplication()).setUserName(model.getUserName());
                            ((App) getApplication()).setCompanyUserID(userID);
                            ((App) getApplication()).setSysComBrID(comBrID);
                            ((App) getApplication()).setSysComID(comID);
                            ((App) getApplication()).setUserFullname(fullName);
                            ((App) getApplication()).setComBrName(combrName);

                            ((App) getApplication()).setVehDriverNumber(vehDrNumber);
                            ((App) getApplication()).setIsVehicleDriver(isVeh);


                            //记住登录状态
                            SharedPreferences sharedPreferences = Login.this.getSharedPreferences("App_WasteOil", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if(checkRemem.isChecked()) {
                                editor.putBoolean("isRemember",true);
                                editor.putString("userName", model.getUserName());
                                editor.putString("userPwd", editPwd.getText().toString());
                                editor.commit();
                            }
                            else
                            {
                                editor.putBoolean("isRemember",false);
                                editor.putString("userName", "");
                                editor.putString("userPwd", "");
                                editor.commit();
                            }

                            Intent intent = new Intent(Login.this, CircleActivity.class);
                            startActivity(intent);
                            finish();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        txtErrMsg.setText("未知错误");
                        MsgBox.Show(Login.this, "未知错误");
                        return;
                    }

                }
            }
        });


    }

    public void InitData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("App_WasteOil", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isFirstRun) {

            editor.putBoolean("isFirstRun", false);
            editor.commit();
        } else {
            boolean isRemember=sharedPreferences.getBoolean("isRemember", false);
            if(isRemember)
            {
                String userName = sharedPreferences.getString("userName", "");
                String userPwd = sharedPreferences.getString("userPwd", "");

                this.editUserName.setText(userName);
                this.editPwd.setText(userPwd);
                this.checkRemem.setChecked(true);

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
