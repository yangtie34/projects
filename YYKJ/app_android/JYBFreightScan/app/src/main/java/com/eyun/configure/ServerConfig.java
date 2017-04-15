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
    public static class JDBC {
        public static final String driverName = "net.sourceforge.jtds.jdbc.Driver";//"com.microsoft.sqlserver.jdbc.SQLServerDriver";//"oracle.jdbc.driver.OracleDriver";
        public static String ipAndPort="192.168.56.248:1533";
        public static String orcl="JYun_Bang_Storage_Test";
        public static String url = "jdbc:jtds:sqlserver://"+ipAndPort+"/"+orcl;//"jdbc:sqlserver://192.168.56.248:1533;DatabaseName=HuaChi_Test";//"jdbc:oracle:thin:@ 202.196.0.180:1521:DM";
        public static String user = "jyun_bang_storage";
        public static String password = "jyun_bang_storage_20170215";
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
