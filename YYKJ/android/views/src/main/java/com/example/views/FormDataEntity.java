package com.example.views;

import com.chengyi.android.angular.entity.TreeEntity;

import java.util.List;

import static android.R.id.input;

/**
 * Created by Administrator on 2017/2/18.
 */

public class FormDataEntity {
    private String id;
    private String name;
    private String type;
    private List<TreeEntity> data;
    private boolean isEdit=false;
    private boolean isAdd=false;
    private boolean isView=false;
    private String thisData;
    private String thisData_;//单位
    public FormDataEntity(){}
    public FormDataEntity(String id,String name,String type,List<TreeEntity> data,String thisData){
        this.id=id;this.name=name;this.type=type;this.data=data;this.isEdit=isEdit;this.isAdd=isAdd;this.thisData=thisData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TreeEntity> getData() {
        return data;
    }

    public void setData(List<TreeEntity> data) {
        this.data = data;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getThisData() {
        return thisData;
    }

    public void setThisData(String thisData) {
        this.thisData = thisData;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getThisData_() {
        return thisData_;
    }

    public void setThisData_(String thisData_) {
        this.thisData_ = thisData_;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public static class Type{
        public final static String input="input";
        public final static String checkTexts="checkTexts";
        public final static String list="list";
        public final static String spinner="spinner";
        public final static String date="date";
    }
}
