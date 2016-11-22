/**
 * @class NS.Template
 * @extends NS.Base
 *   具体使用格式以及规范--参照Ext.XTemplate
 */
NS.define('NS.Template',{
    /***
     * @param {String} tplString tpl字符串
     */
    constructor : function(){
        var s = "",cls = Ext.XTemplate;
        for(var i=0;i<arguments.length;i++){
            var item = arguments[i];
            s+="arguments["+i+"]"+",";
        }
        s = s.substr(s,s.length-1);
        this.tpl = eval("new cls("+s+");");
    },
    /**
     * 把传入的数据迭代成html标签，并写入component的dom节点下面
     * @param {Sting/HtmlElement/NS.dom.ELement} component
     * @param {Object}data
     */
    writeTo : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.overwrite(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.overwrite(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.overwrite(component,data);
        }else if(NS.isElement(component)){
            this.tpl.overwrite(component,data);
        }
    },
    insertFirst : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.insertFirst(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.insertFirst(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.insertFirst(component,data);
        }else if(NS.isElement(component)){
            this.tpl.insertFirst(component,data);
        }
    },
    insertBefore : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.insertBefore(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.insertBefore(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.insertBefore(component,data);
        }else if(NS.isElement(component)){
            this.tpl.insertBefore(component,data);
        }
    },
    insertAfter : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.insertAfter(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.insertAfter(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.insertAfter(component,data);
        }else if(NS.isElement(component)){
            this.tpl.insertAfter(component,data);
        }
    }
});