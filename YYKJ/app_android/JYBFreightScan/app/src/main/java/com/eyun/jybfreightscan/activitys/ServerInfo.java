package com.eyun.jybfreightscan.activitys;

import android.os.Bundle;
import android.view.View;

import com.eyun.configure.ServerConfig;
import com.eyun.framework.angular.baseview.Loadding;
import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.AppContext;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.jdbc.jdbcUtil.DBManager;
import com.eyun.framework.jdbc.jdbcUtil.DBManagerServer;
import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.FastJsonTools;
import com.eyun.framework.util.android.PreferenceUtils;
import com.eyun.framework.util.android.ViewUtil;
import com.eyun.framework.util.common.FormCatchUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.support.JDBCEntity;

import static com.eyun.framework.util.android.ViewUtil.alert;


/**
 * Created by Administrator on 2017/3/7.
 */
public class ServerInfo extends AngularActivity {
    public static JDBCEntity jdbcEntity;
    static {
        //FormCatchUtil.getObjectFromShare()
        String jdbcEntityJson= PreferenceUtils.getPrefString("jdbcEntityJson", "");
        if(!StringUtils.hasLength(jdbcEntityJson)){
            jdbcEntity= FastJsonTools.createJsonBean(jdbcEntityJson,JDBCEntity.class);
        }else{
            jdbcEntity=new JDBCEntity();

            JDBCEntity.Config localConfig=jdbcEntity.new Config();
            localConfig.setIpAndPort(ServerConfig.JDBC.ipAndPort);
            localConfig.setOrcl(ServerConfig.JDBC.orcl);
            localConfig.setInstatnce(ServerConfig.JDBC.instance);
            localConfig.setUserName( ServerConfig.JDBC.user);
            localConfig.setPassWord(ServerConfig.JDBC.password);
            jdbcEntity.setLocalConfig(localConfig);

            JDBCEntity.Config netConfig=jdbcEntity.new Config();
            netConfig.setIpAndPort(ServerConfig.JDBC.ipAndPortServer);
            netConfig.setOrcl(ServerConfig.JDBC.orclServer);
            netConfig.setUserName( ServerConfig.JDBC.userServer);
            netConfig.setInstatnce(ServerConfig.JDBC.instanceServer);
            netConfig.setPassWord(ServerConfig.JDBC.passwordServer);
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
        String instance=scope.key("instance").val().toString();
        if(!StringUtils.hasLength(instance)){
            alert("请输入实例名");
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
        config.setInstatnce(instance);
        initDBConfig();
        Loadding.show("正在检测连接...");
        if(!jdbcEntity.isNet()) {
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
                        }, null);
                    } else {
                        ViewUtil.confirm("成功连接数据库", "数据库连接成功！确定进入登录页面", new CallBack() {
                            @Override
                            public Object run() {
                                PreferenceUtils.setPrefString("jdbcEntityJson", FastJsonTools.createJsonString(jdbcEntity));
                                AppContext.intent(Login.class);
                                return null;
                            }
                        }, null);
                    }
                }
            });
        }else{
            scope.forThread(new CallBack() {
                @Override
                public Object run() {
                    return DBManagerServer.checkConnection();
                }
            }, new DataListener<Boolean>() {
                @Override
                public void hasChange(Boolean bool) {
                    Loadding.hide();
                    if (!bool) {
                        ViewUtil.confirm("警告", "远程数据库连接失败！请重新配置数据库！", new CallBack() {
                            @Override
                            public Object run() {
                                return null;
                            }
                        }, null);
                    } else {
                        ViewUtil.confirm("成功连接远程数据库", "远程数据库连接成功！确定进入登录页面", new CallBack() {
                            @Override
                            public Object run() {
                                PreferenceUtils.setPrefString("jdbcEntityJson", FastJsonTools.createJsonString(jdbcEntity));
                                AppContext.intent(Login.class);
                                return null;
                            }
                        }, null);
                    }
                }
            });
        }

    }
    public static void  initUrl(){
        ServerConfig.JDBC.url = "jdbc:jtds:sqlserver://"+ ServerConfig.JDBC.ipAndPort+"/"+ ServerConfig.JDBC.orcl;//+";instance="+ServerConfig.JDBC.instance;
    };
    public static void  initUrlServer(){
        ServerConfig.JDBC.urlServer = "jdbc:jtds:sqlserver://"+ ServerConfig.JDBC.ipAndPortServer+"/"+ ServerConfig.JDBC.orclServer;//+";instance="+ServerConfig.JDBC.instanceServer;
    };
    public static void initDBConfig(){
        JDBCEntity.Config config;
        if(jdbcEntity.isNet()){
            config=jdbcEntity.getNetConfig();
            ServerConfig.JDBC.ipAndPort=config.getIpAndPort();
            ServerConfig.JDBC.orcl=config.getOrcl();
            ServerConfig.JDBC.instance=config.getInstatnce();
            initUrl();
            ServerConfig.JDBC.user=config.getUserName();
            ServerConfig.JDBC.password=config.getPassWord();
            DBManager.reStart();
        }else {
            config=jdbcEntity.getLocalConfig();
            //加载服务器服务
            config=jdbcEntity.getNetConfig();
            ServerConfig.JDBC.ipAndPortServer=config.getIpAndPort();
            ServerConfig.JDBC.orclServer=config.getOrcl();
            ServerConfig.JDBC.instanceServer=config.getInstatnce();
            initUrlServer();
            ServerConfig.JDBC.userServer=config.getUserName();
            ServerConfig.JDBC.passwordServer=config.getPassWord();
            DBManagerServer.reStart();
        }
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
                if (!bool) {
                    Loadding.hide();
                    ViewUtil.confirm("警告", "本地数据库连接失败！请重新配置数据库！", new CallBack() {
                        @Override
                        public Object run() {
                            jdbcEntity.setNet(false);
                            AppContext.intent(ServerInfo.class);
                            return null;
                        }
                    }, null);
                }else{
                    Loadding.show("正在检测服务器数据库连接！");
                    Scope.activity.scope.forThread(new CallBack() {
                        @Override
                        public Object run() {
                            return DBManagerServer.checkConnection();
                        }
                    }, new DataListener<Boolean>() {
                        @Override
                        public void hasChange(Boolean bool) {
                            Loadding.hide();
                            if (!bool) {
                                ViewUtil.confirm("警告", "服务器数据库连接失败！是否重新配置服务器数据库！", new CallBack() {
                                    @Override
                                    public Object run() {
                                        jdbcEntity.setNet(true);
                                        AppContext.intent(ServerInfo.class);
                                        return null;
                                    }
                                }, new CallBack() {
                                    @Override
                                    public Object run() {
                                        return null;
                                    }
                                });
                            }
                        }
                    });
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
        scope.key("instance").val(config.getInstatnce());
    }
}