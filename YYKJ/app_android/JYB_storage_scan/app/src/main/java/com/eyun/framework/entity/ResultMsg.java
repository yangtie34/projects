package com.eyun.framework.entity;

/**
 * Created by Administrator on 2017/2/17.
 */

public class ResultMsg {
    private boolean isTure=false;
    private String msg;
    private Object object;
    public ResultMsg(){

    }
    public ResultMsg(String msg){
        this.msg=msg;
    }
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
