package com.eyun.framework.util.xmlUtil;

/**
 * Created by Administrator on 2017/2/21.
 */
public class TagAttr {
    private String namespace;
    private String name;
    private String prefix;
    private String type;
    private String value;
    public TagAttr(){

    }
    public TagAttr(String namespace,
             String name,
                   String prefix,
                   String type,
             String value){
        this.namespace=namespace;
        this.name=name;
        this.prefix=prefix;
        this.type=type;
        this.value=value;
    }
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
