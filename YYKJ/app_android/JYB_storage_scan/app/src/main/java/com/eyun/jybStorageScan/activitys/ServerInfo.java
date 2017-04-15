package com.eyun.jybStorageScan.activitys;

import android.os.Bundle;
import android.view.View;

import com.eyun.configure.ServerConfig;
import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.AppContext;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.jdbc.jdbcUtil.DBManager;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.FastJsonTools;
import com.eyun.framework.util.android.PreferenceUtils;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.jybStorageScan.R;
import com.eyun.jybStorageScan.support.JDBCEntity;
import com.eyun.jybStorageScan.support.SoundSupport;

import static com.eyun.framework.util.android.ViewUtil.alert;


/**
 * Created by Administrator on 2017/3/7.
 */
public class ServerInfo extends AngularActivity {
    public static JDBCEntity jdbcEntity;
    static {
        String jdbcEntityJson=PreferenceUtils.getPrefString("jdbcEntityJson", "");
        if(StringUtils.hasLength(jdbcEntityJson)){
            jdbcEntity=FastJsonTools.createJsonBean(jdbcEntityJson,JDBCEntity.class);
        }else{
            jdbcEntity=new JDBCEntity();
            JDBCEntity.Config localConfig=jdbcEntity.new Config();
            localConfig.setIpAndPort(ServerConfig.JDBC.ipAndPort);
            localConfig.setOrcl(ServerConfig.JDBC.orcl);
            localConfig.setUserName( ServerConfig.JDBC.user);
            localConfig.setPassWord(ServerConfig.JDBC.password);
            jdbcEntity.setLocalConfig(localConfig);
            JDBCEntity.Config netConfig=jdbcEntity.new Config();
            localConfig.setIpAndPort(ServerConfig.JDBC.ipAndPort);
            localConfig.setOrcl(ServerConfig.JDBC.orcl);
            localConfig.setUserName( ServerConfig.JDBC.user);
            localConfig.setPassWord(ServerConfig.JDBC.password);
            jdbcEntity.setNetConfig(netConfig);
            PreferenceUtils.setPrefString("jdbcEntityJson", FastJsonTools.createJsonString(jdbcEntity));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_info);
        intPage();
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
        JDBCEntity.Config config;
        if(jdbcEntity.isNet()){
            config=jdbcEntity.getNetConfig();
        }else {
            config=jdbcEntity.getLocalConfig();
        }
        config.setIpAndPort(ipAndPort);
        config.setOrcl(orcl);
        config.setUserName(user);
        config.setPassWord(password);
        initDBConfig();
        Loadding.show("正在检测连接...");
        scope.forThread(new CallBack() {
            @Override
            public Object run() {
                return DBManager.checkConnection();
            }
        }, new DataListener<Boolean>() {
            @Override
            public void hasChange(Boolean bool) {
                Loadding.hide();
                if (!bool) {
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
                            PreferenceUtils.setPrefString("jdbcEntityJson", FastJsonTools.createJsonString(jdbcEntity));
                            AppContext.intent(Login.class);
                            return null;
                        }
                    },null);
                }
            }
        });
    }
    public static void  initUrl(){
        ServerConfig.JDBC.url = "jdbc:jtds:sqlserver://"+ServerConfig.JDBC.ipAndPort+"/"+ServerConfig.JDBC.orcl;
    };
    public static void initDBConfig(){
        JDBCEntity.Config config;
        if(jdbcEntity.isNet()){
            config=jdbcEntity.getNetConfig();
        }else {
            config=jdbcEntity.getLocalConfig();
        }
        ServerConfig.JDBC.ipAndPort=config.getIpAndPort();
        ServerConfig.JDBC.orcl=config.getOrcl();
        initUrl();
        ServerConfig.JDBC.user=config.getUserName();
        ServerConfig.JDBC.password=config.getPassWord();
        DBManager.reStart();
    }
    public static void checkDB(){
        Loadding.show("正在检测数据库连接！");
        Scope.activity.scope.forThread(new CallBack() {
            @Override
            public Object run() {
                return DBManager.checkConnection();
            }
        }, new DataListener<Boolean>() {
            @Override
            public void hasChange(Boolean bool) {
                Loadding.hide();
                if (!bool) {
                    ViewUtil.confirm("警告", "数据库连接失败！请重新配置数据库！", new CallBack() {
                        @Override
                        public Object run() {
                           AppContext.intent(ServerInfo.class);
                            return null;
                        }
                    }, null);
                }
            }
        });
    }

    public void changeModel(View view) {
        jdbcEntity.setNet(!jdbcEntity.isNet());
        intPage();
    }
    private void intPage(){
        JDBCEntity.Config config;
        if(jdbcEntity.isNet()){
            config=jdbcEntity.getNetConfig();
        }else {
            config=jdbcEntity.getLocalConfig();
        }
        scope.key("ipAndPort").val(config.getIpAndPort());
        scope.key("orcl").val(config.getOrcl());
        scope.key("user").val(config.getUserName());
        scope.key("password").val(config.getPassWord());
        scope.key("modelName").val(jdbcEntity.getModelName());
    }
}