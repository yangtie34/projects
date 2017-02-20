package com.yiyun.wasteoilcustom.activities;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chengyi.android.angular.UI.Loadding;
import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.util.AppContext;
import com.chengyi.android.util.PreferenceUtils;
import com.yiyun.wasteoilcustom.AppUser;
import com.yiyun.wasteoilcustom.R;
import com.yiyun.wasteoilcustom.bll.User_BLL;
import com.yiyun.wasteoilcustom.model.CompanyCustomer_Model;

import org.ksoap2.serialization.SoapObject;

import static com.chengyi.android.angular.core.Scope.activity;

public class Login extends AngularActivity {
    EditText edit_username;
    EditText edit_userpwd ;
    Button btn_login;
    String switchCheck="switchCheck";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setIndex();
        setContentView(R.layout.activity_login);
        edit_username= (EditText) findViewById(R.id.edit_username);
        edit_userpwd= (EditText) findViewById(R.id.edit_userpwd);
        btn_login= (Button) findViewById(R.id.btn_login);
        scope.key("switchCheck").watch(new DataListener<Boolean>() {
            @Override
            public void hasChange(Boolean bool) {
                int type;
                if(bool){
                    type=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                }else{
                    type=InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                }
                edit_userpwd.setInputType(type);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edit_username.getText().toString();
                String pwd=edit_userpwd.getText().toString();
               /* if(username==null||username.length()==0) {
                   alert("请输入用户名！");
                }else if(pwd == null||pwd.length()==0){
                    alert("请输入密码！");
                }else if(login(username,pwd)){
                    alert("登录成功！");
                    AppContext.intent(Menu.class);
                }else{

                };*/
                login("4101052-1","000000");
            }
        });

    }

    public static void login(String username,String pwd){

        AppUser appUser= AppUser.getInstance();
        Object isRemeber=activity.scope.key("remember").val();
        if(isRemeber!=null&&(Boolean)isRemeber) {
            PreferenceUtils.setPrefBoolean(User_BLL.isRememberStr,true);
            PreferenceUtils.setPrefString(User_BLL.userNameStr,username);
            PreferenceUtils.setPrefString(User_BLL.userPwdStr,pwd);
        }
        appUser.setUserName(username);
        appUser.setUserPwd(pwd);
        final CompanyCustomer_Model model = new CompanyCustomer_Model();
        model.setLoginAccount(username);
        model.setLoginPwd(pwd);
        long comID = appUser.getSysComID();


        activity.scope.key(User_BLL.msg).watch(new DataListener<SoapObject>() {
            @Override
            public void hasChange(SoapObject result) {
                Loadding.hide();
                TextView err=((TextView)activity.findViewById(R.id.txtErrMsg));
              if(User_BLL.init(result)){
                  if(activity.isIndex())err.setText("");
                  AppContext.intent(Menu.class);
              }else{
                  String errMsg=activity.scope.key("errMsg").val().toString();
                  if(activity.isIndex())err.setText(errMsg);
                  AppContext.intent(Login.class);
              }
            }
        });
        Loadding.show("正在登录");
        User_BLL.Login(model,comID);
    }
}
