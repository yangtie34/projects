NS.define('Output.TemplatePackager', {
    singleton: true,
    constructor: function () {
        // 模板池
        this.templateBuilders = {
            'classical': this.classicBodyBuilder,
            'quarters': this.quartersBodyBuilder,
            'single': this.singleBodyBuilder
        };
    },
    getTemplateBody: function (config) {
        var builder = this.templateBuilders[config.templateType];
        if (builder)
            return builder.call(this, config);
        else
            console.log('没有定义的模版:' + config.templateType);
    },
    /***************************************************************************
     * 单分平模版构建
     */
    singleBodyBuilder: function (config) {
        var pd = config.positionData;// 位置数据
        var cm = config.componentManager;// 组件管理器
        var body = {
            title: config.title,
            templateType: "classical",
            items: []
        };
        var fd = pd[0];
        var comIds = fd.comps;// 组件Id数组
        var cl = new Ext.container.Container({// 功能区容器
            xtype: 'container',
            baseCls: 'fun_area',
            layout: 'fit',
            columnWidth: .5
            // height : 30
        });
        var titleContainer = new Ext.container.Container({
            baseCls: '',
            region: 'north',
            layout: 'column',
            items: [new Output.FunAreaTitle({//
                funtitle: fd.funAreaTitle,
                maxWidth: 255,
                minWidth: 150
            }).getLibComponent(), new Output.FunAreaHelp({
                help: fd.helpInfo
            }).getLibComponent()]
        });
        var items = [];
        items.push(titleContainer);
        for (var j = 0; j < comIds.length; j++) {
            var cc = comIds[j];// 获取组件数据
            var com = cm.getComponentById(cc.componentId);// 获取组件
            items.push(com.getLibComponent());// 将组件添加到容器中
        }
        cl.add(items);
        // var container = new Ext.container.Container({columnWidth : .5,items :
        // cl});
        body.items.push(cl);
        return body;
    },
    /***************************************************************************
     * 四分平模版体构建
     *
     * @param {}
     *            config
     */
    quartersBodyBuilder: function (config) {
        var pd = config.positionData;// 位置数据
        var cm = config.componentManager;// 组件管理器
        var body = {
            title: config.title,
            items: []
        };
        for (var i = 0, ln = pd.length; i < ln; i++) {
            var fd = pd[i];
            var comIds = fd.comps;// 组件Id数组
            var cl = new Ext.container.Container({// 功能区容器
                xtype: 'container',
                layout: 'fit',
                columnWidth: .5
            });
            var titleContainer = new Ext.container.Container({
                baseCls: '',
                layout: 'column',
                items: [new Output.FunAreaTitle({//
                    funtitle: fd.funAreaTitle,
                    maxWidth: 255,
                    minWidth: 150
                }).getLibComponent(), new Output.FunAreaHelp({
                    help: fd.helpInfo
                }).getLibComponent()]
            });
            var items = [];
            items.push(titleContainer);
            for (var j = 0; j < comIds.length; j++) {
                var cc = comIds[j];// 获取组件数据
                var com = cm.getComponentById(cc.componentId);// 获取组件
                items.push(com.getLibComponent());// 将组件添加到容器中
            }
            cl.add(items);
            body.items.push(cl);
        }
        body.title = config.title;
        body.templateType = "quarters";
        if (body.items.length == 4) {
            var array = body.items;
            body.items = [
                {
                    xtype: 'container',
                    width: '100%',
                    height: '100%',
                    layout: 'column',
                    baseCls: 'fun_area',
                    style: {
                        marginTop: '0px',
                        paddingTop: '19px',
                        backgroundImage: 'url(base/output/images/bg-border.gif)',
                        backgroundRepeat: 'repeat-y',
                        backgroundPosition: '50% 0'
                    },
                    items: [array[0], array[1]]
                },
                {
                    xtype: 'container',
                    width: '100%',
                    height: '100%',
                    layout: 'column',
                    baseCls: 'fun_area classTemplate_bottom',
                    style: {
                        marginTop: '0px',
                        paddingTop: '19px',
                        backgroundImage: 'url(base/output/images/bg-border.gif)',
                        backgroundRepeat: 'repeat-y',
                        backgroundPosition: '50% 0'
                    },
                    items: [array[2], array[3]]
                }
            ];
        }
        // body.items = A;
        return body;
    },
    /***************************************************************************
     *
     * 经典模版体构建
     */
    classicBodyBuilder: function (config) {
        var pd = config.positionData;// 位置数据
        var cm = config.componentManager;// 组件管理器
        var body = {
            title: config.title,
            items: []
        };
        for (var i = 0, ln = pd.length; i < ln; i++) {
            var fd = pd[i];// 功能区数据
            var comIds = fd.comps;// 组件Id数组
            var cl = new Ext.container.Container({// 功能区容器
                xtype: 'container',
                layout: 'fit',
                baseCls: 'fun_area'
                // height : 30
            });
            if (i == pd.length - 1)
                cl.baseCls = 'fun_area fun_area_bottom classTemplate_bottom';
            var titleContainer = new Ext.container.Container({
                baseCls: '',
                layout: 'column',
                items: [new Output.FunAreaTitle({//
                    funtitle: fd.funAreaTitle,
                    maxWidth: 255,
                    minWidth: 150
                    // columnWidth : 0.2
                }).getLibComponent(), new Output.FunAreaHelp({
                    // columnWidth : 0.75
                    help: fd.helpInfo
                }).getLibComponent(),
                    new Output.FunAreaButton({
                        fatherContainer: cl
                        // columnWidth : 0.05
                    }).getLibComponent()]
            });
            var items = [];
            items.push(titleContainer);
            for (var j = 0; j < comIds.length; j++) {
                var cc = comIds[j];// 获取组件数据
                var com = cm.getComponentById(cc.componentId);// 获取组件
                if (com.componentType == 'datelinkage') {
                    titleContainer.getComponent(0).columnWidth = 0.2;
                    titleContainer.getComponent(1).columnWidth = 0.05;
                    var insert = com.getLibComponent();
                    insert.columnWidth = 0.7;
                    titleContainer.insert(2, insert);
                    continue;
                }
                items.push(com.getLibComponent());// 将组件添加到容器中
            }
            cl.add(items);
            body.items.push(cl);
        }
        body.title = config.title;
        body.templateType = "classical";
        return body;
    }
});

/*******************************************************************************
 * 功能区标题容器
 */
NS.define('Output.FunAreaTitle', {
    /***************************************************************************
     * 构造方法
     */
    constructor: function (config) {
        US.apply(this, config);
        this.initComponent();
    },
    /***************************************************************************
     * 初始化组件
     */
    initComponent: function () {
        this.createComponent();
    },
    /***************************************************************************
     * 创建组件
     */
    createComponent: function () {
        var me = this;
        this.component = new Ext.container.Container({
            baseCls: 'fun_area_title',
            columnWidth: me.columnWidth,
            listeners: {
                'afterrender': function () {
                    var textNode = document.createTextNode(me.funtitle);// 创建标题dom节点
                    var span = document.createElement('span');
                    span.appendChild(textNode);
                    this.el.appendChild(span);
                }
            }
        });
    },
    getLibComponent: function () {
        return this.component;
    }
});
/*******************************************************************************
 * 功能区帮助容器
 */
NS.define('Output.FunAreaHelp', {
    /***************************************************************************
     * 构造方法
     */
    constructor: function (config) {
        US.apply(this, config);
        this.initComponent();
    },
    /***************************************************************************
     * 初始化组件
     */
    initComponent: function () {
        this.createComponent();
    },
    /***************************************************************************
     * 创建组件
     */
    createComponent: function () {
        var me = this;
        this.component = new Ext.container.Container({
            baseCls: 'opTextRoot_bzxx',
            columnWidth: me.columnWidth,
            listeners: {
                'afterrender': function () {
                    var img = document.createElement('IMG');
                    img.src = 'app/output/images/qs.gif';
                    var a = document.createElement('A');
                    a.appendChild(img);
                    a.href = '#';
                    // a.title='帮助信息';
                    Ext.fly(a).on('click', function (event) {// mouseover click
                        event.stopEvent();
                        if (typeof this.help == 'undefined'
                            || this.help == '' || this.help == null) {
                            this.help = '帮助信息为空！请联系相关人员维护！';
                        }
                        var tip = Ext.create('Ext.tip.ToolTip', {
                            width: 200,
//									autoWidth:true,
                            // minHeight : 80,
//									maxHeight : 120,
                            autoHide: false,
                            // hideDelay:20,
                            shadow: false,
                            bodyBorder: false,
                            // title:'帮助信息',
                            // closable : true,
                            // floating : true,
                            // draggable : true,
                            frameHeader: false,
                            hideCollapseTool: true,
                            overlapHeader: true,
                            // frame:true,
                            bodyBorder: false,
                            bodyStyle: 'background:#f0f0f0;',
                            html: '<div style="margin:8px 15px 11px 15px;"><p style="color:#666;"><b>帮助信息</b></p>'
                                + '<div style="line-height:18px; padding-top:10px; color:#808080">'
                                + this.help + '</div></div>'

                        });
                        var ae = Ext.fly(a);
                        tip.showAt([ae.getX() + 30, ae.getY() - 4]);
//								setTimeout(function(){
//									tip.close();
//								},1000*(this.help.length/4));
                        return false;
                    }, me);
                    this.el.appendChild(a);
                }
            }
        });
    },
    getLibComponent: function () {
        return this.component;
    }
});
/*******************************************************************************
 * 功能区收缩按钮容器
 */
NS.define('Output.FunAreaButton', {
    /***************************************************************************
     * 构造方法
     */
    constructor: function (config) {
        US.apply(this, config);
        this.initComponent();
    },
    /***************************************************************************
     * 初始化组件
     */
    initComponent: function () {
        this.createComponent();
    },
    /***************************************************************************
     * 创建组件
     */
    createComponent: function () {
        var me = this;
        this.component = new Ext.container.Container({
            baseCls: '',
            columnWidth: me.columnWidth,
            listeners: {
                'afterrender': function () {
                    var button = this.expandButton = document
                        .createElement('input');
                    button.className = 'fun_area_button_shrink';
                    button.type = 'button';
                    button.onclick = function () {
                        if (button.className == 'fun_area_button_shrink')
                            button.className = 'fun_area_button_expand';
                        else
                            button.className = 'fun_area_button_shrink';
                        var items = me.fatherContainer.items.items;
                        for (var i = 1; i < items.length; i++) {
                            if (!items[i].isHidden())
                                items[i].hide();
                            else
                                items[i].show();
                        }
                    };
                    this.el.appendChild(button);
                    this.el.setStyle('styleFloat', 'right');
                    this.el.setStyle('cssFloat', 'right');
                }
            }
        });
    },
    getLibComponent: function () {
        return this.component;
    }
});