package com.eyun.configure;

import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;


/**
 * Created by Administrator on 2017/2/20.
 */

public final class ServerConfig {

    public static final String URL = "";

    //---------app更新相关------------
    public static class Update {
        //返回的安装包url
        public static final String APK_URL = "http://update.eyunsoft.cn/jybfreightscan/";

        //更新文件
        public static final String FILE_APK_NAME = "com.eyunsoft.app.jybfreightscan.apk";

        //返回的更新提示
        public static final String APK_XML = "http://update.eyunsoft.cn/jybfreightscan/UpdateInfo.xml";
        /* 下载包安装路径 */
        public static final String PATH_SAVE = "/sdcard/updatedemo/";

        public static final String FILE_SAVE = PATH_SAVE + "com.eyun.jybfreightscan.apk";
    }
    //---------app更新相关------------


    //---------webservice相关------------
    public static class WebService {
        public static final String NAMESPACE = "http://tempuri.org/";

        public static final Element[] HEADER = new Element[1];

        static {
            HEADER[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

            Element username = new Element().createElement(NAMESPACE, "UserName");
            username.addChild(Node.TEXT, "eyunsoft_wasteoil");
            HEADER[0].addChild(Node.ELEMENT, username);

            Element pass = new Element().createElement(NAMESPACE, "PassWord");
            pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
            HEADER[0].addChild(Node.ELEMENT, pass);
        }
    }
    //---------webservice相关------------


    //---------数据源相关驱动------------
    public static class JDBC {//JYun_Bang_Test   jyun_bang   jyun_bang@)!*)#@*20170417

        public static final String driverName = "net.sourceforge.jtds.jdbc.Driver";//"com.microsoft.sqlserver.jdbc.SQLServerDriver";//"oracle.jdbc.driver.OracleDriver";
        public static String ipAndPort = "192.168.56.32:1533";
        public static String orcl = "JYun_Bang_LingDan_WD";
        public static String instance = "SQLEXPRESS";
        public static String url = "jdbc:jtds:sqlserver://" + ipAndPort + "/" + orcl + ";instance=" + instance;
        ;//"jdbc:sqlserver://192.168.56.248:1533;DatabaseName=HuaChi_Test";//"jdbc:oracle:thin:@ 202.196.0.180:1521:DM";
        public static String user = "sa";
        public static String password = "sa123456";

        public static String ipAndPortServer = "192.168.56.248:1533";
        public static String orclServer = "JYun_Bang_Test";
        public static String instanceServer = "SQL2005";
        public static String urlServer = "jdbc:jtds:sqlserver://" + ipAndPortServer + "/" + orclServer + ";instance=" + instanceServer;//"jdbc:sqlserver://192.168.56.248:1533;DatabaseName=HuaChi_Test";//"jdbc:oracle:thin:@ 202.196.0.180:1521:DM";
        public static String userServer = "jyun_bang";
        public static String passwordServer = "jyun_bang@)!*)#@*20170417";

        //验证数据库连接是否健康
        public static final String validationQuery = "select getdate()";//"select sysdate from dual";
        //最小连接数
        public static final int minPool = 1;
        //单次增加连接数的数量
        public static final int upPool = 5;
        //最大连接数
        public static final int maxPool = 100;


    }
    //---------数据源相关驱动------------


}
