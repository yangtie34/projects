package com.eyun.framework.angular.core;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;


import com.eyun.framework.util.CallBack;
import com.eyun.framework.util.FastJsonTools;
import com.eyun.framework.util.common.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/2.
 */

public class Scope {
    public static AngularActivity activity;
    public static Scope inflateScope;
    private static int staticId = 0;
    public int id;
    public Scope parent;
    private View view;
    private Object value;
    private static boolean syncLock=false;
    private List<DataListener> dataListeners=new ArrayList<>();
    protected Map<String,Scope> scopes=new HashMap<>();
    private int listSize=0;//当scope为数组使用时

    private Scope(){}

    public static Scope init(Scope parent,Object obj){
        Scope scope=new Scope();
        scope.parent=parent;
        if(obj instanceof View){
            scope.view=(View) obj;
        }else if(obj instanceof AngularActivity){
            scope.activity= (AngularActivity) obj;
            scope.view=((AngularActivity) obj).findViewById(android.R.id.content);
        }else if(obj instanceof Application){
            scope.parent=scope;
        }
        scope.id=scope.getId();
        return scope;
    }

    public Scope key(String key){
        if(key==null)return new Scope();
        String[] keys=keyParse(key);
        Scope thisScope=this;
        for (int i = 0; i <keys.length ; i++) {
            String k=keys[i];
           if(thisScope.scopes.get(k)==null){
               if(StringUtils.isNumeric(k)){
                   thisScope.push(Integer.parseInt(k),null);
               }else{
                   thisScope.scopes.put(k,new Scope());
               }
            }
            thisScope=thisScope.scopes.get(k);
        }

        return thisScope;
    }
    public Scope key(int index){
        return this.key(String.valueOf(index));
    }
    /**
     * 更改当前value
     * @param value
     */
    public void valInScope(Object value){
        this.value=value;
        if(syncLock==false&&value!=null){
            for (int i = 0; i <dataListeners.size() ; i++) {
                dataListeners.get(i).hasChange(value);
            }
            ScopeTool.valueInScope(this);
        }

    }
    public void val(Object value){
        this.value=value;
        if(syncLock==false&&value!=null){
            for (int i = 0; i <dataListeners.size() ; i++) {
                dataListeners.get(i).hasChange(value);
            }
        }
    }

    public static void lockSync(){
        syncLock=true;
    }
    public static void openSync(){
        syncLock=false;
    }
    /**
     * 获取当前value
     * @return
     */
    public Object val(){
        return this.value;
    }
    public String[] keyArray() {
        return scopes.keySet().toArray(new String[]{});
    }

    public void watch(DataListener listener){
        dataListeners.clear();
        addWatch(listener);
    }
    public void addWatch(DataListener listener){
        dataListeners.add(listener);
        if(value!=null){
            listener.hasChange(value);
        }
    }

    /**
     * 索引为index的地方放入值value
     * @param index
     * @param value
     */
    public void push(int index,Object value){
        if(this.value==null){
            this.value=new ArrayList<>();
        }
        for (int i = 0; i <index-listSize ; i=0) {
            ((List)this.value).add(listSize,null);
            scopes.put(String.valueOf(listSize),new Scope());
            listSize++;
        }
        Scope scope=new Scope();
        scope.val(value);
        scopes.put(String.valueOf(index),scope);
        ((List)this.value).add(index,value);
        this.val(this.value);
        listSize++;
    }

    /**
     * list 放入值
     * @param value
     */
    public void push(Object value){
        push(listSize,value);
    }

    /**
     * list长度
     * @return
     */
    public int size(){
        return listSize;
    }

    /**
     * 解析复杂key
     * @return
     */
    public static String[] keyParse(String key){
        key=key.replaceAll("\\[",".");
        key=key.replaceAll("]","");
        String[] keys=key.split("\\.");
        return keys;
    }

    public View inflate(int loyout) {
        inflateScope=this;
        View view = LayoutInflater.from(activity).inflate(loyout, null);
        return view;
    }
    public static int getId(){
        staticId++;
        return staticId;
    }
    public  void clear(){
        this.dataListeners.clear();
        this.scopes.clear();
        listSize=0;
    }
    public  void clear(String key){
        this.scopes.remove(key);
    }
    public  void clear(int key){
        this.scopes.remove(key);
    }
    public String toJson(){
        return FastJsonTools.createJsonString(ScopeTool.toObject(this));
    }

    private void forThreadVal(Object value){
        this.value=value;
            for (int i = 0; i <dataListeners.size() ; i++) {
                dataListeners.get(i).hasChange(value);
            }
    }
    public void forThread(final CallBack callBackThread, final DataListener dataListenerUI){
        final int threadId=Scope.getId();
        this.key(threadId).watch(new DataListener() {
            @Override
            public void hasChange(final Object o) {
                Scope.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dataListenerUI.hasChange(o);
                        Scope.this.clear(threadId);
                    }
                });
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scope.this.key(threadId).forThreadVal(callBackThread.run());
            }
        }).start();

    }
}

