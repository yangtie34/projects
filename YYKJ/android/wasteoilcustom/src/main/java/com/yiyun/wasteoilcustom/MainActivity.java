package com.yiyun.wasteoilcustom;

import android.os.Bundle;

import com.chengyi.android.angular.UI.Loadding;
import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.angular.core.DataListener;
import com.chengyi.android.util.AppContext;
import com.chengyi.android.util.PreferenceUtils;
import com.yiyun.wasteoilcustom.Model.CompanyUser_Model;
import com.yiyun.wasteoilcustom.activities.Login;
import com.yiyun.wasteoilcustom.activities.Menu;
import com.yiyun.wasteoilcustom.bll.User_BLL;


public class MainActivity extends AngularActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class clazz=null;
            if(toLogin()){
                clazz=Login.class;
            }else{
                clazz=Menu.class;
            }
            AppContext.intent(clazz);
    }

    private boolean toLogin() {
        final boolean[] bool = {false};
       if(PreferenceUtils.getPrefBoolean(User_BLL.isFirstRunStr,true)){
           bool[0] = true;
       }else if(!PreferenceUtils.getPrefBoolean(User_BLL.isRememberStr,false)){
           bool[0] = true;
       }else{
            AppUser appUser= AppUser.getInstance();
           appUser.setUserName(PreferenceUtils.getPrefString(User_BLL.userNameStr, ""));
           appUser.setUserPwd(PreferenceUtils.getPrefString(User_BLL.userPwdStr, ""));
           final CompanyUser_Model model = new CompanyUser_Model();
           model.setUserName(appUser.getUserName());
           model.setPwd(appUser.getUserPwd());
           long comID = appUser.getSysComID();

           final boolean[] logining = {true};
           scope.key(User_BLL.msg).watch(new DataListener<Boolean>() {
               @Override
               public void hasChange(Boolean b) {
                   Loadding.hide();
                    logining[0] =false;
                        bool[0] =!b;
               }
           });
           Loadding.show("正在登录");
           User_BLL.Login(model,comID);
           while (logining[0]){
               try {
                   Thread.sleep(300);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }
        return bool[0];
    }
}
