package com.eyun.customXghScan.activitys;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.eyun.configure.ServerConfig;
import com.eyun.customXghScan.R;
import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.AppContext;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.android.AppUtils;
import com.eyun.framework.util.android.PreferenceUtils;
import com.eyun.framework.util.android.ViewUtil;

import static com.eyun.framework.angular.core.Scope.activity;
import static com.eyun.framework.util.android.ViewUtil.alert;

public class Login extends AngularActivity {
    EditText edit_userpwd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setIndex();
        setContentView(R.layout.activity_login);
        edit_userpwd= (EditText) findViewById(R.id.edit_userpwd);
        scope.key("versionCode").val(AppUtils.getVersionName());
        scope.key("showPwd").watch(new DataListener<Boolean>() {
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
        boolean isRemeber= PreferenceUtils.getPrefBoolean("remember",false);
        if(isRemeber) {
            PreferenceUtils.setPrefBoolean("remember",true);
            scope.key("username").val(PreferenceUtils.getPrefString("username",""));
            scope.key("pwd").val(PreferenceUtils.getPrefString("pwd",""));
        }
        Object aaa= BaseDao.getInstance().queryForString(ServerConfig.JDBC.validationQuery,new Object[]{});
        if(aaa==null){
            ViewUtil.confirm("警告", "数据库连接失败！请重新配置数据库！", new CallBack() {
                @Override
                public Object run() {
                    AppContext.intent(ServerInfo.class);
                    return null;
                }
            },null);
        }
    }

    public void login(View v){
                 final String username= (String) scope.key("username").val();
                 final String pwd= (String) scope.key("pwd").val();
               if(username==null||username.length()==0) {
                   alert("请输入用户名！");
                }else if(pwd == null||pwd.length()==0){
                    alert("请输入密码！");
                }else{
                   Loadding.show("正在登录");
                  scope.forThread(new CallBack<ResultMsg>() {
                      @Override
                      public ResultMsg run() {
                          boolean isRemeber= (boolean) activity.scope.key("isRemeber").val();
                          if(isRemeber) {
                              PreferenceUtils.setPrefBoolean("remember",true);
                              PreferenceUtils.setPrefString("username",username);
                              PreferenceUtils.setPrefString("pwd",pwd);
                          }else{
                              PreferenceUtils.setPrefBoolean("remember",false);
                          }
                          return null;
                      }
                  }, new DataListener<ResultMsg>() {
                      @Override
                      public void hasChange(ResultMsg resultMsg) {
                            Loadding.hide();
                            scope.key("errorMsg").val(resultMsg.getMsg());
                          if(resultMsg.isTure()){
                            /*  CompanyUser companyUser= (CompanyUser) resultMsg.getObject();
                              AppUser.fullName=companyUser.getFullname();
                              AppUser.username=companyUser.getUserName();*/
                            AppContext.intent(Menu.class);
                          }
                      }
                  });
                };
    }

  public void serverInfoEdit(View view) {
        AppContext.intent(ServerInfo.class);
    }
}
