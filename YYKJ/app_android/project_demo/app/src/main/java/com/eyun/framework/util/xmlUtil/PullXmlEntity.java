package com.eyun.framework.util.xmlUtil;

import java.util.List;



/**
 * Created by Administrator on 2017/2/21.
 */

public class PullXmlEntity {
    private String namespace="";
    private String tag;
    private List<TagAttr> tagAttrList;
    private List<PullXmlEntity> pullXmlEntities;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<TagAttr> getTagAttrList() {
        return tagAttrList;
    }

    public void setTagAttrList(List<TagAttr> tagAttrList) {
        this.tagAttrList = tagAttrList;
    }

    public List<PullXmlEntity> getPullXmlEntities() {
        return pullXmlEntities;
    }

    public void setPullXmlEntities(List<PullXmlEntity> pullXmlEntities) {
        this.pullXmlEntities = pullXmlEntities;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
