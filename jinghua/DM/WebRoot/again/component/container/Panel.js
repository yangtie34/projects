/**
 * @class NS.container.Panel
 * @extends NS.container.Container
 *        面板容器
 *
 *          var component = new NS.container.Container({
                width: 500,
                height: 500,
                layout : 'border',
                items : [new NS.Component({
                    width: '100%',
                    height: '40%',
                    region : 'north',
                    padding: 20,
                    style: {
                        color: 'blue',
                        backgroundColor:'blue'
                    }
                }),
                        new NS.Component({
                            width: '100%',
                            height: '40%',
                            region : 'south',
                            padding: 20,
                            style: {
                                color: 'green',
                                backgroundColor:'green'
                            }
                        })
                    ]
            });
         var tbar = new NS.toolbar.Toolbar({
                        items : [{
                            xtype : 'button',
                            name : 'add',
                            text : '新增'
                        },{
                            xtype : 'button',
                            text : '修改',
                            name : 'update'
                        },{
                            xtype : 'button',
                            name : 'delete',
                            text : '删除'
                        }]});
         var panel = new NS.container.Panel({
                        width : 600,
                        height : 600,
                        items : component,
                        title : '1212',
                        tbar : tbar,
                        renderTo: Ext.getBody()
                    });

 */
NS.define('NS.container.Panel', {
    extend: 'NS.container.Container',
    /**
     * @cfg {String} buttonAlign 按钮的位置 含:'right','center','left',默认右侧
     */
    /**
     * @cfg {Object/Object[]} buttons 便利配置用于停靠到面板底部的添加按钮
     */
    /**
     * @cfg {Boolean} collapsed 是否可收缩 默认false
     */
    /**
     * @cfg {Boolean} closable 显示面板的关闭按钮
     */
    /**
     * @cfg {Object} bodyStyle 面板body的样式，配置对象
     */
    /**
     * @cfg {Boolean} autoShow 面板的右部工具栏
     */
    /**
     * @cfg {String} closeAction 关闭模式 'destroy' 销毁, 'hide' 则为隐藏
     */
    /**
     * @cfg {String} title 面板的标题
     */
    /**
     * @cfg {Object/Component} tbar 面板的顶部工具栏
     */
    /**
     * @cfg {Object/Component} bbar 面板的底部工具栏
     */
    /**
     * @cfg {Object/Component} lbar 面板的左部工具栏
     */
    /**
     * @cfg {Object/Component} rbar 面板的右部工具栏
     */
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping: function () {
        this.callParent();
        var getBar = function (value, property, config) {
            config[property] = value.getLibComponent();
            },
            getheader = function(value, property, config){
                config[property] = value;
                config['preventHeader'] = !value;
            };
        this.addConfigMapping({
                buttonAlign:true,
                buttons:true,
                title: true,
                closable: true,
                closeAction:true,
                collapsible : true,//可折叠属性
                collapsed : true,//如果拥有collapsible属性，那么false表示展开，true表示收缩
                collapseDirection  :true,//表示收缩的方向
                tbar: getBar,
                bodyCLs : true,
                header : getheader,
                plain : true,
                bbar: getBar,
                lbar: getBar,
                rbar: getBar,
                buttons:true,//按钮属性
                frame : true,//框架默认背景颜色
                bodyStyle : true
            }
        );
    },
    /**
     *  创建panel
     * @param {Object} config 配置对象
     * @private
     */
    initComponent: function (config) {
        this.component = Ext.create('Ext.panel.Panel', config);
    },
    /**
     * 向panel中更新html(以前的加现在传入的)
     * @param {String} html html串
     * */
    addHtml: function (html1) {
        var ownHtml = this.panel.body.html;
        this.panel.body.update(ownHtml + html1);
    },
    /**
     * 将panel的html置空
     * */
    clearHtml: function () {
        this.panel.body.update("");
    },
    /**
     *  设置标题，用于动态变更标题
     * @param {String} title 标题
     */
    setTitle: function (title) {
        this.component.setTitle(title);
    },
    /**
     * 显示面板容器
     */
    show : function(){
        this.component.show();
    },
    /**
     * 关闭面板容器
     */
    close : function(){
        this.component.close();
    }
});