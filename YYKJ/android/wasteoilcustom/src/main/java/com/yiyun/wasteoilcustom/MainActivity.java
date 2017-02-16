package com.yiyun.wasteoilcustom;

import android.os.Bundle;

import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.util.AppContext;
import com.chengyi.android.util.PreferenceUtils;
import com.yiyun.wasteoilcustom.activities.Login;
import com.yiyun.wasteoilcustom.bll.User_BLL;

import static com.chengyi.android.util.PreferenceUtils.getPrefString;


public class MainActivity extends AngularActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class clazz=null;
            if(toLogin()){
                clazz=Login.class;
                AppContext.intent(clazz);
            }

    }

    private boolean toLogin() {
         boolean bool = false;
       if(PreferenceUtils.getPrefBoolean(User_BLL.isFirstRunStr,true)){
           bool = true;
           PreferenceUtils.setPrefBoolean(User_BLL.isFirstRunStr,false);
       }else if(!PreferenceUtils.getPrefBoolean(User_BLL.isRememberStr,false)){
           bool = true;
       }else{

           String username=PreferenceUtils.getPrefString(User_BLL.userNameStr, "");
           String pwd=getPrefString(User_BLL.userPwdStr, "");
           Login.login(username,pwd);
       }
        return bool;
    }
}
