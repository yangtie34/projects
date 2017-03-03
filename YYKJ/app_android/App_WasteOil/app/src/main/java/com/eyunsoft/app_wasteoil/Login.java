package com.eyunsoft.app_wasteoil;

import android.app.ActionBar;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eyunsoft.app_wasteoil.Model.CompanyCustomer_Model;
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

import static android.content.Context.MODE_PRIVATE;
import static com.eyunsoft.app_wasteoil.R.id.BusinessLicenseType;
import static com.eyunsoft.app_wasteoil.R.id.OrganizationCodeNumberloyout;
import static com.eyunsoft.app_wasteoil.Register3.model;

public class Login extends AppCompatActivity {

    private long exitTime = 0;//退出次数

    public EditText editUserName;
    public EditText editPwd;

    private RadioGroup userGroup;
    private RadioButton userType;
    private RadioButton userType2;
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
        userGroup= (RadioGroup) findViewById(R.id.userType);
        userType=(RadioButton) Login.this.findViewById(R.id.userType1);
        //绑定一个匿名监听器
        userGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                userType = (RadioButton) Login.this.findViewById(radioButtonId);
            }
        });
        InitData();

        txtErrMsg = (TextView) findViewById(R.id.txtErrMsg);

        txtVersion=(TextView)findViewById(R.id.txtVersion);
        txtVersion.setText("当前版本:"+Version.getCurrentVersion(this));

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().userType=1;
                if (userType.getId() == R.id.userType1) {
                    App.getInstance().userType=0;
                    CompanyUser_Model model = new CompanyUser_Model();
                    model.setUserName(editUserName.getText().toString());
                    model.setPwd(editPwd.getText().toString());


                    if (!NetWork.isNetworkAvailable(Login.this)) {
                        MsgBox.Show(Login.this, "网络不可用，请稍后再试");
                        return;
                    }

                    long comID = App.getInstance().getSysComID();
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

                                boolean isVeh = false;
                                long vehDrNumber = jsonObject.getLong("VehicleDriverNumber");

                                if (vehDrNumber > 0)
                                    isVeh = true;
                                App.getInstance().setUserName(model.getUserName());
                                App.getInstance().setCompanyUserID(userID);
                                App.getInstance().setSysComBrID(comBrID);
                                App.getInstance().setSysComID(comID);
                                App.getInstance().setUserFullname(fullName);
                                App.getInstance().setComBrName(combrName);

                                App.getInstance().setVehDriverNumber(vehDrNumber);
                                App.getInstance().setIsVehicleDriver(isVeh);


                                //记住登录状态
                                SharedPreferences sharedPreferences = Login.this.getSharedPreferences("App_WasteOil", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                if (checkRemem.isChecked()) {
                                    editor.putBoolean("isRemember", true);
                                    editor.putString("userName", model.getUserName());
                                    editor.putString("userPwd", editPwd.getText().toString());
                                    editor.commit();
                                } else {
                                    editor.putBoolean("isRemember", false);
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
                }else{

                    CompanyCustomer_Model model = new CompanyCustomer_Model();
                    model.setLoginAccount(editUserName.getText().toString());
                    model.setLoginPwd(editPwd.getText().toString());


                    if(!NetWork.isNetworkAvailable(Login.this))
                    {
                        MsgBox.Show(Login.this,"网络不可用，请稍后再试");
                        return;
                    }

                    long comID = App.getInstance().getSysComID();
                    String msg = CompanyUser_BLL.SysLogin_CompanyCustomer(model, comID);
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
                                String LoginAccount = jsonObject.getString("LoginAccount");
                                String ComCusNumber = jsonObject.getString("ComCusNumber");
                                String Fullname = jsonObject.getString("Fullname");
                                String ComCusComName = jsonObject.getString("ComCusComName");
                                String combrName = jsonObject.getString("ComBrName");
                                String ComName = jsonObject.getString("ComName");
                                String ComGrade = jsonObject.getString("ComGrade");
                                long ComCusID = Convert.ToInt64(jsonObject.getString("ComCusID"));
                                long ComArea = Convert.ToInt64(jsonObject.getString("ComArea"));
                                long comBrID = Convert.ToInt64(jsonObject.getString("ComBrID"));//分公司
                                comID = Convert.ToInt64(jsonObject.getString("ComID"));

                                App.getInstance().setUserName(model.getLoginAccount());
                                App.getInstance().setCompanyUserID(ComCusID);
                                App.getInstance().setSysComBrID(comBrID);
                                App.getInstance().setSysComID(comID);
                                App.getInstance().setUserFullname(Fullname);
                                App.getInstance().setComBrName(combrName);



                                //记住登录状态
                                SharedPreferences sharedPreferences = Login.this.getSharedPreferences("App_WasteOil", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                if(checkRemem.isChecked()) {
                                    editor.putBoolean("isRemember",true);
                                    editor.putString("userName", model.getLoginAccount());
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

                                Intent intent = new Intent(Login.this, CircleActivityCus.class);
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
    public void reg(View view) {
        Intent intent=new Intent(this,Register1.class);
        startActivity(intent);
    }
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
}
