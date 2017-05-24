package com.eyun.framework.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */

public class NodeEntity {
    private String id;
    private String name;
    private boolean check=false;
    private Object content;
    private NodeEntity pNode=null;
    private int level;
    private List<NodeEntity> childrenList=new ArrayList<NodeEntity>();
    public NodeEntity() {}
    public NodeEntity(String id, String name) {
        this.id = id;
        this.name = name;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<NodeEntity> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<NodeEntity> childrenList) {
        this.childrenList = childrenList;
    }
    public NodeEntity getpNode() {
        return pNode;
    }

    public void setpNode(NodeEntity pNode) {
        this.pNode = pNode;
    }

    public void addChildren(NodeEntity nodeEntity) {
        nodeEntity.setpNode(this);
        this.childrenList.add(nodeEntity);
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }


    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }


    @Override
    public String toString()
    {
        return name.toString();
    }
}
