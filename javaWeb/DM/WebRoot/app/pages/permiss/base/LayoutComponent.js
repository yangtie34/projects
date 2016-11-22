NS.define('Pages.permiss.base.LayoutComponent',{
    layoutTpl : '',//用以布局的html
    renderNumber : 0,//需要渲染的组件的个数
    rendered : false,//是否已经被渲染
    constructor : function(config){
        NS.apply(this,config);
        this.id = NS.id();//生成唯一ID
        this.components = [];
        this.insertFirstComponents = [];
        this.listeners = [];
        this.initLayout();//初始化页面布局
    },
    initLayout : function(){
        var me = this,
            id = me.id,
            component,
            layoutHtml,
            tpl;
            layoutHtml = me.getLayoutHtml(this.layoutTpl.tpl.html);//获取已经生成ID的布局的html片段

            tpl = new NS.Template(layoutHtml);
            component = this.component = new NS.Component({//生成布局组件,并生成ID
                tpl : tpl,
                data : this.data,
                autoScroll : true,
                layout : 'fit'
            });
        component.on('afterrender',function(){
           this.rendered = true;
           this.processRender();
        },this);
    },
    getLayoutHtml : function(txt){
        var id = this.id,
            renderNumber = this.renderNumber,
            i = 0,item,array = [],
            formatStr = "NS.String.format(txt,";
        if(renderNumber == 0){
            console.log("renderNumber 不能为0");
            return txt;
        }else{
            for(;i<renderNumber;i++){
                array.push(id+"_auto_"+i);
                formatStr += "array["+i+"]";
                if(i != renderNumber-1){
                    formatStr += ",";
                }
            }
            formatStr += ");";
            this.ID_ARRAY = array;
            return eval(formatStr);
        }

    },
    /**
     * 将组件注册进入布局中
     * @param {NS.Component} 组件
     * @param {Number} index 组件所在的索引，参照NS.String.format("")中的组件的ID对应的大括号里对应的数字
     */
    register : function(component,index){
        if(NS.isNSComponent(component)){
            if(this.components[index] && this.components[index].component.el){
                this.components[index].component.el.remove();
            }
            this.components[index] = component;
            if(this.rendered){
                component.render(this.ID_ARRAY[index]);//渲染组件
            }
        }
    },
    /**
     * 处理renderer的渲染事宜
     */
    processRender : function(){
        for(var i in this.components){
            this.components[i].render(this.ID_ARRAY[i]);
        }
        for(var i in this.insertFirstComponents){
            this.insertFirstComponents[i].component.getRenderTree().insertFirst(document.getElementById(this.ID_ARRAY[i]));
        }
        for(var i in this.listeners){
            this.bindItemsEvent.apply(this,this.listeners[i]);
        }
    },
    /**
     * 根据组件下标的索引，获取组件
     * @param index
     * @return {NS.Component}
     */
    getComponent : function(index){
        return this.components[index];
    },
    bindItemsEvent : function(nameevent,fn,scope){
        if(this.rendered){
            if(NS.isString(nameevent)){
                var array = nameevent.split('.'),
                    name = array[0],
                    event = array[1],
                    selector = NS.String.format("[name='{0}']",name),
                    rets = $(this.component.component.el.dom).find(selector);
                if(rets.length != 1){
                    NS.log("程序出错!找到name为"+name+"的元素个数为"+rets.length+"个！");
                }else{
                    $(rets[0])[event](function(){
                        fn.call(scope||this,arguments);
                    });
                }
            }else if(NS.isObject(nameevent)){
                for(var i in nameevent){
                    var array = i.split('.'),
                        name = array[0],
                        event = array[1],
                        selector = NS.String.format("[name='{0}']",name),
                        rets = $(this.component.component.el.dom).find(selector);
                    if(rets.length != 1){
                        NS.log("程序出错!找到name为"+name+"的元素个数为"+rets.length+"个！");
                    }else{
                        (function(obj,name,event,rets){
                            $(rets[0])[event](function(){
                                obj.fn.call(obj.scope||this,arguments);
                            });
                        })(nameevent[i],name,event,rets);
                    }
                }
            }
        }else{
            var arg = [];
            arg.push(nameevent);
            arg.push(fn);
            arg.push(scope);
            this.listeners.push(arg);
        }
    },
    registerFirst :function(component,index){
        if(NS.isNSComponent(component)){
            this.insertFirstComponents[index] = component;
            if(this.rendered){
                component.component.el.dom.insertFirst(document.getElementById(this.ID_ARRAY[index]));//渲染组件
            }
        }
    },
    get$ByName : function(name){
       var selector = NS.String.format("[name='{0}']",name),
           rets = $(this.component.component.el.dom).find(selector);
       if(rets.length == 1){
           return $(rets[0]);
       }else{
           return "错误";
       }
    },
    get$ByAttribute : function(att,value){
        var selector = NS.String.format("[{0}='{1}']",att,value),
            rets = $(this.component.component.el.dom).find(selector);
        return rets;
    }
});