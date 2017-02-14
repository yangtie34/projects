package com.yiyun.wasteoilcustom;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */

public class AppUser {

    private AppUser(){}
    private static AppUser instance;
    public static AppUser getInstance() {
        if (instance == null) {
            instance = new AppUser();
        }
        return instance;
    }

    private long sysComID=-1;

    private long sysComBrID=-1;

    private String comBrName="";

    //真实姓名
    private String userFullname="";
    //帐号
    private String userName="";

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    //密码
    private String userPwd="";
    //用户编号
    private long companyUserID=-1;

    //是否司机
    private boolean isVehicleDriver=false;

    private long vehDriverNumber=0;

    public void setUserFullname(String str) {
        userFullname = str;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setCompanyUserID(long companyUserID){this.companyUserID=companyUserID;}

    public long getCompanyUserID(){return this.companyUserID;}

    public boolean getIsVehicleDriver()
    {
        return this.isVehicleDriver;
    }

    public void setIsVehicleDriver(boolean isVehicleDriver)
    {
        this.isVehicleDriver=isVehicleDriver;
    }

    public void setVehDriverNumber(long vehDriverNumber){
        this.vehDriverNumber=vehDriverNumber;
    }

    public long getVehDriverNumber(){
        return this.vehDriverNumber;
    }

    public void setSysComBrID(long sysComBrID){
        this.sysComBrID=sysComBrID;
    }

    public long getSysComBrID(){
        return this.sysComBrID;
    }

    public void setUserName(String str) {
        userName = str;
    }

    public String getComBrName() {
        return comBrName;
    }

    public void setComBrName(String str) {
        comBrName = str;
    }

    public String getUserName() {
        return userName;
    }

    public long getSysComID(){ return this.sysComID;  }

    public void setSysComID(long sysComID){
        this.sysComID=sysComID;
    }


}

