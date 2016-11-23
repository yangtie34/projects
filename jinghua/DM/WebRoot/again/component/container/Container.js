/**
 * @class NS.container.Container
 * @extends NS.Component
 * @author yongtaiwang
 *      组件类-基类
 *
 *      var container = new NS.container.Container({
 *              plugins : [new Plugin()],
 *              items :  [
 *                 component1,component2
 *              ]
 *      });
 *
 */
NS.define('NS.container.Container',{
    extend : 'NS.Component',
    /**
     *@cfg {Component/Object[]} items 容器的子组件
     */

    /**
     * @cfg {Object/String} layout 定义容器的布局形式
     *
     *   它可以是这种形式
     *
     *          var container = new NS.container.Container({
     *              layout : {
     *                    type : 'table',
     *                    columns : 2
     *              }});
     *
     *   也可以是这种形式
     *
     *          var container = new NS.container.Container({
     *                      layout : 'border'
     *                  });
     *
     */

    /**
     * @param｛Object｝config 配置对象
     * @private
     *     创建一个NS.Component 对象实例
     */
    constructor : function(config){
        this.procressItems(config);
        this.callParent(arguments);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
            layout : true,
            items : true
        });
    },
    /**
     * 初始化组件
     * @param {Object} config 配置对象
     * @private
     */
    initComponent : function(config){
        this.component = new Ext.container.Container(config);
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents : function(){
        this.callParent();
    },
    /***
     * 将组件添加到容器子元素列表最后面
     * @param {NS.Component} component 组件
     * @return {NS.Component} 被添加的组件
     */
    add : function(component){
        if(NS.isArray(component)){
            var array = [];
            for(var i= 0;i<component.length;i++){
                var com = component[i];
                if(NS.isNSComponent(com)){
                    array.push(com.getLibComponent());
                }
            }
            this.component.add(array);
        }else{
            if(NS.isNSComponent(component)){
                this.component.add(component.getLibComponent());
            }
        }
        return component;
    },
    /**
     * 移除容器中的组件
     * @param {NS.Component} component 组件
     * @param {Boolean} flag 是否销毁组件
     * @return {NS.Component} 被移除的组件
     */
    remove : function(component,flag){
        this.component.remove(component.getLibComponent(),flag);
        return component;
    },
    /**
     * 将组件插入到容器的指定位置
     * @param {Number} index 组件将要插入的位置
     * @param {NS.Component} component 要插入的组件
     * @return {NS.Component} 被插入的组件
     */
    insert : function(index,component){
        this.component.insert(index,component.getLibComponent());
    },
    /**
     * 移除容器中的所有组件
     * @param {Boolean} flag
     */
    removeAll : function(flag){
        this.component.removeAll(flag);
    },
    /**
     * 根据组件索引获取容器内的组件
     * @param {Number} index 组件在容器的子组件列表中存在的位置
     * @return {NS.Component} 查询到的组件
     */
    getComponent : function(index){
        var component  = this.component.getComponent(index);
        return NS.util.ComponentInstance.getInstance(component);
    },
    /**
     * 执行容器的重新布局
     */
    doLayout : function(){
        this.component.doLayout();
    },
    /**
     * 根据组件的name 查找组件 (前提组件必须拥有name属性)
     * @param {String} name 组件的name属性的值
     * @return {NS.Component} 查询到的第一个组件
     */
    queryComponentByName: function (name) {
        var queryString = 'component[name="{0}"]';
        var components = this.component.query(NS.String.format(queryString, name));
        if (components.length == 1) {
            var com = components[0];
            return NS.util.ComponentInstance.getInstance(com);
        }else{
            NS.error({
                sourceClass : this.$classname,
                sourceMethod : 'queryComponentByName',
                msg : '查询到name为'+name+"的组件共有"+components.length+"个"
            });
        }
    },
    /**
     * 根据给定的属性名，查找组件集合
     * @return {NS.Component[]} 查询到的组件数组
     */
    queryComponentsByName : function(name){
        var queryString = 'component[name="{0}"]',
            components = this.component.query(NS.String.format(queryString, name)),
            i= 0,len=components.length,com,comArray = [];
        for(;i<len;i++){
            com = components[i];
            comArray.push(NS.util.ComponentInstance.getInstance(com));
        }
        return comArray;
    },
    /***
     * 根据组件name查找组件，并且绑定该组件的监听函数
     *
     *      var component = new NS.container.Container({
     *        items : [
     *            {xtype : 'button',text : '新增',name : 'add'},
     *            {xtype : 'button',text : '修改',name : 'update'},
     *            {xtype : 'button',text : '删除',name : 'delete'}
     *         ]
     *     });
     *    component.bindEvent('add','click',function(){},component);//你可以这样做
     *    component.bindEvent({
     *        add : {
     *            event : 'click',
     *            fn : function(){},
     *            scope : component//scope默认是调用绑定事件的容器类
     *        }
     *    });
     * @param {String/Object} cname 组件名称绑定单个组件的事件的时候/绑定多个组件的事件的时候是配置对象
     * @param {String} event 事件名称
     * @param {Function} fn 监听函数
     * @param {Object} scope 作用域
     */
    bindItemsEvent : function(cname,event,fn,scope){
        var component, i,obj = arguments[0],ed;
        if(NS.isString(cname) && NS.isString(event) && NS.isFunction(fn)){
            component = this.queryComponentByName(cname);
            if(!component){
                NS.error({
                    sourceClass : this.$classname,
                    sourceMethod : 'bindItemsEvent',
                    msg : '没有找到对应的组件，绑定事件的组件的名称为:'+cname
                });
            }
            component.on(event,fn,scope);
        }else if(arguments.length == 1 && NS.isObject(obj)){
            for(i in obj){
                component = this.queryComponentByName(i);
                if(!component){//如果组件没有找到，则抛出异常
                    NS.error({
                        sourceClass : this.$classname,
                        sourceMethod : 'bindItemsEvent',
                        msg : '没有找到对应的组件，绑定事件的组件的名称为:'+cname
                    });
                }
                ed = obj[i];
                component.on(ed.event,ed.fn,ed.scope||this);
            }
        }
    },
    /***
     * 解封装，去掉包装，将容器还原成为底层类库组件（这里是Ext组件）
     * @param {Object} config 待处理的配置参数对象
     * @private
     */
    procressItems : function(config){
        if(config){
            var items = config.items||[],
                item;
            this.processChildItems(config);
        }
    },
    /***
     * 处理嵌套的组件层次
     * @param {Array} items 子组件数组
     * @private
     */
    processChildItems : function(config){
        var items = config.items,item;
//        if(config.tbar && NS.isNSComponent(config.tbar)){
//           config.tbar = config.tbar.getLibComponent();
//        }
        if(NS.isArray(items)){
            for(var i= 0,len=items.length;i<len;i++){
                item = items[i];
                if(item.getLibComponent){
                    items[i] = item.getLibComponent();
                }else if(NS.isObject(item)){
                	if(item.items){
                		arguments.callee(item);
                	}
                    if(item.tbar){
                        item.tbar = item.tbar.getLibComponent();
                    }
                }
            }
        }else if(NS.isNSComponent(items)){
            config.items = items.getLibComponent()
        }else if(NS.isObject(items)){
            if(items.items){
                arguments.callee(items);
            }
            if(items.tbar){
                items.tbar = items.tbar.getLibComponent();
            }
        }
    }
});