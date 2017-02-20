package com.yiyun.framework.entity;

/**
 * Created by Administrator on 2017/2/17.
 */

public class ResultMsg {
    private boolean isTure=false;
    private String msg;

    public boolean isTure() {
        return isTure;
    }

    public void setTure(boolean ture) {
        isTure = ture;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
