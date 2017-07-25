package com.eyun.jybStorageScan.support;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class JDBCEntity implements Serializable{
    private Config localConfig;
    private Config netConfig;
    private boolean net=true;
    private String modelName="网络";
    public Config getLocalConfig() {
        return localConfig;
    }

    public void setLocalConfig(Config localConfig) {
        this.localConfig = localConfig;
    }

    public Config getNetConfig() {
        return netConfig;
    }

    public void setNetConfig(Config netConfig) {
        this.netConfig = netConfig;
    }

    public boolean isNet() {
        return net;
    }

    public void setNet(boolean net) {
        this.net = net;
        if(net){
            setModelName("网络");
        }else {
            setModelName("本地");
        }
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public class Config{
        private   String ipAndPort="192.168.56.248:1533";
        private  String orcl="JYun_Bang_Storage_Test";
        private  String userName = "jyun_bang_storage";
        private  String passWord = "jyun_bang_storage_20170215";

        public String getIpAndPort() {
            return ipAndPort;
        }

        public void setIpAndPort(String ipAndPort) {
            this.ipAndPort = ipAndPort;
        }

        public String getOrcl() {
            return orcl;
        }

        public void setOrcl(String orcl) {
            this.orcl = orcl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }
    }
}
