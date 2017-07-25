package com.eyun.customXghScan.activitys;

import android.os.Bundle;
import android.view.View;

import com.eyun.configure.ServerConfig;
import com.eyun.customXghScan.R;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.AppContext;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.jdbc.jdbcUtil.DBManager;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.StringUtils;

import static com.eyun.framework.util.android.ViewUtil.alert;


/**
 * Created by Administrator on 2017/3/7.
 */
public class ServerInfo extends AngularActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_info);
    }

    public void ok(View view) {
        String ipAndPort= (String) scope.key("ipAndPort").val();
        if(!StringUtils.hasLength(ipAndPort)){
            alert("请输入服务器地址");
            return;
        }
        String orcl= (String) scope.key("orcl").val();
        if(!StringUtils.hasLength(orcl)){
            alert("请输入数据库名称");
            return;
        }
        String user= (String) scope.key("user").val();
        if(!StringUtils.hasLength(user)){
            alert("请输入用户名");
            return;
        }
        String password= (String) scope.key("password").val();
        if(!StringUtils.hasLength(password)){
            alert("请输入密码");
            return;
        }
        ServerConfig.JDBC.ipAndPort=ipAndPort;
        ServerConfig.JDBC.orcl=orcl;
        initUrl();
        ServerConfig.JDBC.user=user;
        ServerConfig.JDBC.password=password;
        DBManager.reStart();
        Object aaa= BaseDao.getInstance().queryForString(ServerConfig.JDBC.validationQuery,new Object[]{});
        if(aaa==null){
            ViewUtil.confirm("警告", "数据库连接失败！请重新配置数据库！", new CallBack() {
                @Override
                public Object run() {
                    return null;
                }
            },null);
        }else{
            ViewUtil.confirm("成功连接数据库", "数据库连接成功！确定进入登录页面", new CallBack() {
                @Override
                public Object run() {
                    AppContext.intent(Login.class);
                    return null;
                }
            },null);
        }
    }
    public static void  initUrl(){
        ServerConfig.JDBC.url = "jdbc:jtds:sqlserver://"+ ServerConfig.JDBC.ipAndPort+"/"+ ServerConfig.JDBC.orcl;
    };
}