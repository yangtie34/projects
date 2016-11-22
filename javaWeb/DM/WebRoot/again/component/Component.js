/**
 * @class NS.Component
 * @extends NS.Base
 * @author wangyt
 * 组件类-基类
 *
 *              var component = new NS.Component({
 *                  width : 400,
 *                  height : 200,
 *                  style : {
 *                      backgroundColor : 'yellow'
 *                  },
 *                  renderTo : NS.getBody()
 *              });
 *
 */
NS.define('NS.Component', {
    /**
     * @cfg {Boolean} autoShow 是否自动显示 默认false
     */
    /**
     * @cfg {String/Number} margin 边框距 以此为例:'1 2 3 4' 分别指:'top right bottom left'处的边框大小，单位px
     */
    /**
     *@cfg {Boolean} hidden 组件是否隐藏现实，默认是false
     */
    /**
     * @cfg {Boolean} disabled 是否可用 默认为false
     */
    /**
     * @cfg {Boolean} floating 是否浮动，默认为false
     */
    /**
     * @cfg {Boolean} frame 是否框架背景颜色（浅蓝色）
     */
    /**
     * @cfg {String/Number} padding 定义组件的内边距，其可以为数字，或者字符串。例如：“10 15 10 15”
     */
    /**
     * @cfg {Number} width 定义组件的宽度
     */
    /**
     * @cfg {Number} height 定义组件的高度
     */
    /**
     * @cfg {Number} border 定义组件的边框宽度，其可以为数字，或者字符串。例如：“10 15 10 15”
     */
    /**
     * @cfg {Object} style 定义组件的样式，其可为css基本样式
     *
     *              @example
     *              var panel = new NS.container.Panel({
     *                  width : 400,
     *                  height : 200,
     *                  style : {
     *                      backgroundColor : 'yellow'
     *                  },
     *                  renderTo : NS.getBody()
     *              });
     *
     */
    /**
     * @cfg {String} html 组件内包含的html
     */
    /**
     * @cfg {Object/Object[]} plugins 定义组件的插件
     */
    /**
     * @cfg {String/HtmlElement} renderTo 定义组件的渲染到哪个元素下,其可以为DOM对象的id，DOM对象
     */
    /**
     * @cfg {Boolean} autoScroll true则当浏览器内容溢出盒子之后，允许使用滚动条来显示其余被
     *                              修剪的内容，false则不允许
     */
    /**
     * @cfg {NS.Template} tpl 模版对象
     */
    /**
     * @cfg {Object} data  为NS.Template准备的数据
     */
    /**
     * 构造函数
     * @param {Object} config 构造函数配置对象
     */
    constructor: function (config) {
        this.initConfigMapping();//初始化配置属性映射容器
        var libConfig = this.processConfigProperties(config);//处理键值映射关系
        this.processExtPlugins.apply(this, [libConfig]);//调用处理Ext插件
        this.initEvents();//初始化组件事件
        this.initData.apply(this, arguments);//初始化数据
        this.initComponent.apply(this, [libConfig]);//初始化组件
        //this.packEvents();//封装事件,需要被封装组件已经创建完毕
        this.processNSPlugins();//调用处理NS插件
        this.addPackContainer();//添加Ext对象的包装对象属性
    },
    /***
     * 初始化属性映射（为了和底层框架隔离）
     * @private
     */
    initConfigMapping: function () {
        var getTpl = function (value, property, config) {
            config[property] = value.tpl;
        };
        var renderTo = function(value,property,config){
            if(NS.isElement(value)){
                config[property] = value.element;
            }else if(NS.isString(value)){
                config[property] = value;
            }
        }
        this.configPropertiesMapping = {
            baseCls : true,
            cls : true,
            disabled : true,
            margin: true,
            padding: true,
            autoShow : true,
            autoScroll : true,
            floating:true,
            width: true,
            height: true,
            maxHeight : true,
            maxWidth  :true,
            minHeight : true,
            minWidth  :true,
            name : true,//name属性标识
            hidden : true,//是否显示组件
            colspan : true,//table中占几列
            rowspan : true,//table占几行
            columnWidth : true,//column布局使用
            style : true,
            border: true,
            plugins: true,
            items: true,
            layout: true,
            region: true,
            frame : true,
            renderTo: true,
            tpl : getTpl,
            data : true,
            html: true
            //listeners : 'listeners'
        };

    },
    /***
     * 处理配置项
     * @private
     */
    processConfigProperties: function (config) {
        var cpm = this.configPropertiesMapping, item, i, libconfig = {}, ownerproperties = {};
        for (var i in config) {
            item = cpm[i];
            if (item) {
                libconfig[i] = config[i];
            } else {
                ownerproperties[i] = config[i];
            }
        }
        NS.apply(this, ownerproperties);//将不支持的属性绑定到封装组件上
        for (var i in libconfig) {
            item = cpm[i];
            if (NS.isFunction(item)) {
                item.call(this, libconfig[i], i, libconfig);
            }
        }
        return libconfig;
    },
    /**
     * 为组件添加属性对应的处理策略
     *  this.addProcessForConfig('margin','margin');
     *  this.addProcessForConfig({
     *      name : 'value'
     *      fn : function(value){return value+1;}
     *  });
     *  @private
     */
    addConfigMapping: function (pn, fn) {
        var pp = this.configPropertiesMapping, args = arguments, item;
        switch (args.length) {
            case 1 :
            {
                if (NS.isObject(pn)) {
                    NS.apply(pp, pn);
                }
            }
            case 2 :
            {
                if (NS.isString(pn) && NS.isFunction(fn)) {
                    pp[pn] = fn;
                    break;
                }
            }
            default :
            {
                for (var i = 0, len = args.length; i < len; i++) {
                    item = args[i];
                    if (NS.isString(item)) {
                        pp[item] = true;
                    }
                }
                break;
            }

        }
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents: function () {
        this.addEvents(
            /***
             * @event activate 渲染之前
             * @param {NS.Component} this 组件
             */
            'activate',
            /***
             * @event beforerender 渲染之前
             * @param {NS.Component} this 组件
             */
            'beforerender',
            /***
             * @event afterrender 渲染之后
             * @param {NS.Component} this 组件
             */
            'afterrender',
            /**
             * @event disable 组件被禁用后触发
             * @param {NS.Component} this 组件
             */
            'disable',
            /**
             * @event enable 组件被启用后触发
             * @param {NS.Component} this 组件
             */
            'enable',
            /**
             * @event hide 组件被禁用后触发
             * @param {NS.Component} this 组件
             */
            'hide',
            /**
             * @event focus 组件获取焦点后触发
             * @param {NS.Component} this 组件
             */
            'focus',
            /**
             * @event blur 组件失去焦点后触发
             * @param {NS.Component} this 组件
             */
            'blur',
            /**
             * @event click 当组件被点击后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'click',
            /**
             * @event dbclick 当组件被双击后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'dbclick',
            /**
             * @event contextmenu 当鼠标右击的时候触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'contextmenu',
            /**
             * @event mouseover 当鼠标在组件上方的时候触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mouseover',
            /**
             * @event mouseenter 当鼠标进入组件后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mouseenter',
            /**
             * @event mouseleave 当鼠标离开组件后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mouseleave',
            /**
             * @event mousemove 当鼠标在组件上移动时触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mousemove',
            /**
             * @event keydown 在一个组件内，当一个键盘按下的时候触发
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'keydown',
            /**
             * @event keyup 在一个组件内，当一个键盘按下后松触发
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'keyup',
            /**
             * @event keypress 在一个组件内，当一个键盘按下后，松开的时候触发
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'keypress',
            /***
             * @event destroy 组件被销毁时，触发该事件
             * @params {Ext.Component} this
             */
            'destroy',
            /**
             * @event select 在一个组件内，当用户选择一些field组件的文本的时候，包括input、textarea
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'select',
            /**
             * @event change 当一个input失去焦点，并且值发生改变的时候触发该事件
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'change'

        );
    },
    /**
     * 组件被激活触发该事件
     * @private
     */
    onActivate : function () {
        this.component.on('activate',function(event, element){
            this.fireEvent('activate', this);
        },this);
    },
    /**
     * 组件被渲染到DOM Tree上后触发
     * @private
     */
    onAfterrender : function () {
        this.component.on('afterrender',function(event, element){
            this.fireEvent('afterrender', this);
        },this);
    },

    /**
     * 组件被渲染到DOM Tree上后触发
     * @private
     */
    onBeforerender : function () {
        this.component.on('beforerender',function(event, element){
            this.fireEvent('beforerender', this);
        },this);
    },
    /**
     * 组件被禁用后触发
     * @private
     */
    onDisable : function () {
        this.component.on('disable',function(event, element){
            this.fireEvent('disable', this);
        },this);
    },
    /**
     * 组件被启用后触发
     * @private
     */
    onEnable:   function () {
        this.component.on('enable',function(event, element){
            this.fireEvent('enable', this);
        },this);
    },
    /**
     * 组件获得焦点后触发
     * @private
     */
    onFocus:  function () {
        this.component.on('focus',function(event, element){
            this.fireEvent('focus', this);
        },this);
    },
    /**
     * 组件失去焦点后触发
     * @private
     */
    onBlur:  function () {
        this.component.on('blur',function(){
            this.fireEvent('blur', this);
        },this);
    },
    /**
     * 销毁的时候触发
     * @private
     */
    onDestroy : function(){
        this.component.on('destroy',function(){
            this.fireEvent('destroy', this);
        },this);
    },
    /**
     * 组件隐藏后触发
     * @private
     */
    onHide: function () {
        this.component.on('hide',function(){
            this.fireEvent('hide', this);
        },this);
    },
    /**
     * 组件被单击后触发
     * @private
     */
    onClick: function (event, element) {
        this.component.on({
            click : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('click', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标右击后触发
     * @private
     */
    onContextmenu : function(){
        this.component.on({
            contextmenu : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('contextmenu', NS.Event, element);
                }
            }
        });
    },
    onChange : function(){
        this.component.on({
            change : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('change', NS.Event, element);
                }
            }
        });
    },
    /**
     * 组件被双击后触发
     * @private
     */
    onDbclick:function () {
        this.component.on({
            dblclick : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('dbclick', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标移动到组件上后触发
     * @private
     */
    onMouseover: function () {
        this.component.on({
            mouseover : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('mouseover', NS.Event, element);
                }
            }
        });
    },
    onSelect : function(){
        this.component.on({
            select : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('select', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标进入组件后触发
     * @private
     */
    onMouseenter: function () {
        this.component.on({
            mouseenter : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('mouseenter', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标离开组件后触发
     * @private
     */
    onMouseleave: function () {
        this.component.on({
            mouseleave : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('mouseleave', NS.Event, element);
                }
            }
        });
    },
    /**
     * 当键盘按下后触发该事件
     * @private
     */
    onKeydown: function () {
        this.component.on({
            keydown : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('keydown', NS.Event, element);
                }
            }
        });
    },
    /**
     * 当键盘按下松开后触发该事件
     * @private
     */
    onKeyup: function () {
        this.component.on({
            keyup : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('keyup', NS.Event, element);
                }
            }
        });
    },
    /**
     * 当键盘按下松开后触发该事件
     * @private
     */
    onKeypress: function () {
        this.component.on({
            keypress : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('keypress', NS.Event, element);
                }
            }
        });
    },
    addListener : function(event,callback,scope){
          var eventFunName,item;
          if(NS.isString(event)){
              eventFunName = 'on'+NS.String.capitalize(event);
              if(this[eventFunName]){
                  this[eventFunName]();
              }
              this.callParent(arguments);
          }else if(NS.isObject(event)){
              for(var i in event){
                  eventFunName = 'on'+NS.String.capitalize(i);
                  if(this[eventFunName]){
                      this[eventFunName]();
                  }
                  item = event[i];
                  if(NS.isFunction(item)){
                      this.callParent([i,item]);
                  }else if(NS.isObject(item)){
                      if(item.fn){
                         this.callParent([i,item,item.scope||this]);
                      }
                  }
              }
          }

    },
    /**
     * 移除监听事件
     * @param {String} event 事件名
     */
    removeListener : function(event){
        var events = this['events']||{};
        if(NS.isString(event)){
            delete events[event];
            this.component.removeListener(event);
        }
    },

    /**
     * 增加on事件方法
     *
     * @param {String}eventName 事件名称
     * @param {Funcion} fn 回调函数
     * @param {Object} scope 作用域
     */
    on: function (event, fn, scope, options) {
        var eventFunName,item;
        if(NS.isString(event)){
            eventFunName = 'on'+NS.String.capitalize(event);
            if(this[eventFunName]){
                this[eventFunName]();
            }
            this.callParent(arguments);
        }else if(NS.isObject(event)){
            for(var i in event){
                eventFunName = 'on'+NS.String.capitalize(i);
                if(this[eventFunName]){
                    this[eventFunName]();
                }
                item = event[i];
                if(NS.isFunction(item)){
                    this.callParent([i,item]);
                }else if(NS.isObject(item)){
                    if(item.fn){
                        this.callParent([i,item,item.scope||this]);
                    }
                }
            }
        }
    },
    /**
     * @method initData
     * 初始化数据
     * @private
     */
    initData: NS.emptyFn,
    /**
     * 初始化组件
     * @private
     * @param {Object} config 配置对象
     */
    initComponent: function (config) {
        this.component = new Ext.Component(config);
    },
    /**
     * 为原生类添加一个属性，属性指向当前包装类
     * @private
     */
    addPackContainer: function () {
        this.component.NSContainer = this;
    },
    /***
     * 获取底层类库组件,用以和底层类库进行交互
     * @private
     * @return Ext.Component
     */
    getLibComponent: function () {
        return this.component;
    },
    /***
     * 处理Ext插件
     * @private
     * @param {Object} config 组件的配置对象
     */
    processExtPlugins: function (config) {
        if (config) {
            var plugins = config.plugins;//插件对象
            var extPlugins = [];
            var nsPlugins = this.nsPlugins = [];
            if (NS.isArray(plugins)) {
                for (var i = 0, len = plugins.length; i < len; i++) {
                    var p = plugins[i];
                    if (p.getLibComponent) {
                        extPlugins.push(p.getLibComponent());
                    } else {
                        nsPlugins.push(p);
                    }
                }
            } else if (NS.isObject(plugins)) {
                if (plugins.getLibComponent) {
                    extPlugins.push(plugins.getLibComponent());
                } else {
                    nsPlugins.push(plugins);
                }
            }
            config.plugins = extPlugins;
        }
    },
    /***
     * 处理NS插件对象
     * @private
     */
    processNSPlugins: function () {
        if (this.nsPlugins) {
            var nsPlugins = this.nsPlugins;
            for (var i = 0, len = nsPlugins.length; i < len; i++) {
                var p = nsPlugins[i];
                p.init(this);
            }
            delete this.nsPlugins;
        }
    },
    /**
     * 设置组件的包装对象
     * @param {Ext.component.Component} com 需要被包装的底层类库对象
     * @private
     */
    setComponent: function (com) {
        this.component = com;
        return this;
    },
    /***
     * 销毁本对象方法
     * @private
     */
    destroy: function () {
        this.component.destroy();
        for (var i in this) {
            if (i)
                delete this[i];
        }
    },
    /***
     * 将组件渲染到一个某个元素下，该元素的id为传入的id
     * @param {String} id 要渲染到的html的元素的id
     */
    render: function (id) {
        this.component.render(id);
    },
    /**
     * 显示组件
     */
    show: function () {
        this.component.show();
    },
    /**
     * 显示在某个特定的位置
     * @param {Number} x x轴的位置
     * @param {Number} y y轴的位置
     * @param {Boolean} [animate] 是否以动画效果显示
     */
    showAt : function(x,y,animate){
        this.component.showAt(x,y,animate);;
    },
    /**
     * 隐藏组件
     */
    hide: function() {
        this.component.hide();
    },
    /**
     * 判断组件是否隐藏,如果隐藏返回true，如果不隐藏显示false
     * @return {Boolean}
     */
    isHidden : function(){
        return this.component.isHidden();
    },
    /**
     * 设置组件高度
     * @param {Number}  width 宽度
     */
    setWidth: function (width) {
        this.component.setWidth(width);
    },
    /**
     * 设置组件宽度
     * @param {Number} height 高度
     */
    setHeight: function (height) {
        this.component.setHeight(height);
    },
    /**
     * 设置组件是否可用,true可用，false不可用
     * @param {Boolean} flag
     */
    setDisabled: function (flag) {
        this.component.setDisabled(flag);
    },
    /**
     * 组件是否可用
     * @return {Boolean}
     */
    isDisabled: function () {
       return this.component.isDisabled();
    },
    /**
     * 更新Component包含的Html内容
     * @param {String} html html字符串
     */
    updateHtml : function(html){
        this.component.update(html);
    },
    /**
     * 刷新模版数据
     * @param {Object} data 模版的数据
     */
    refreshTplData : function(data){
        this.component.update(data);
    },
    /**
     * 尝试把焦点转移到组件上
     */
    focus : function(){
        this.component.focus();
    },
    /**
     * 获取NS.dom.Element对象
     * @return {NS.dom.Element}
     */
    getEl : function(){
        return new NS.dom.Element(this.component.getEl().dom);
    },
    /**
     * 设置组件显示的位置
     * @param {Number} x 在浏览器上X轴的位置
     * @param {Number} y 在浏览器上Y轴的位置
     * @param {Boolean} animate 是否有动画效果
     */
    setPosition : function(x,y,animate){
        this.component.setPosition(x,y,animate);
    },
    /**
     * 获取组件的宽度
     * @return {Number}
     */
    getWidth : function(){
        return this.component.el.getWidth();
    },
    /**
     * 获取组件的高度
     * @return {Number}
     */
    getHeight : function(){
        return this.component.el.getHeight();
    },
    /**
     * 返回组件X轴所处位置
     * @return {Number}
     */
    getX : function(){
        return this.component.el.getX();
    },
    /***
     * 返回组件Y轴所处位置
     * @return {Number}
     */
    getY : function(){
        return this.component.el.getY();
    },
    contains:function(e){
    	return this.component.el.contains(e);
    }
});
