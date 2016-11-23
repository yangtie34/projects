/***
 * 定义输出组件窗体
 */
NS.define('Output.PopWindow', {
    /***
     * 构造函数
     */
    constructor: function () {
        this.initData.apply(this, arguments);
    },
    /***
     * 初始化组件数据
     */
    initData: function (config) {
        var me = this;
        me.config = config;
        Output.Model.prototype.gridHeaderRequest(config.entityName, function (data) {
            me.config.data = data;
            me.dataConvert();
            me.initComponent();
        });
    },
    /***
     * 初始化组件
     */
    initComponent: function () {
        this.initView();
    },
    /**
     * 初始化数据
     */
    dataConvert: function () {
        var data = this.config.data;
        var id = this.config.id;
        var queryTableContentParam = 'queryTableContent,queryGridResult,"id":' + id;
        var proxy = US.CommonUtil.getProxyForGrid([queryTableContentParam]);
        proxy.reader = {
            type: 'json',
            root: 'queryTableContent.data',// 读取的主要数据 键
            totalProperty: 'queryTableContent.totalCount'
            // 数据总量
        };
        this.tranData = US.CommonUtil.getTranData(data, proxy);
    },
    initView: function () {
        var grid = this.getGrid();
        var body = Ext.getBody();
        this.window = Ext.create('Ext.window.Window', {
            width: body.getWidth() - 200,
            height: body.getHeight() - 40,
            modal: true,
            layout: 'fit',
            maximizable: true,
            minimizable: true,
            autoShow: true,
            items: grid,
            tbar: this.getTbar()
        });
    },
    /***
     * 获取gridpanel
     * @return {}
     */
    getGrid: function () {
        var gridzz = this.gridzz = new ModelOne.GridZz({
            componentData: this.tranData
        });
        gridzz.initComponent();
        return gridzz.getGrid().grid;
    },
    getTbar: function () {
        var tbar = new Ext.toolbar.Toolbar({border: true});
        var single = this.getSingleField().getFieldSet();
        var senior = this.getSeniorQuery();
        tbar.add(single, '->', senior);
        return tbar;
    },
    getSingleField: function () {
        var me = this;
        var singleField = new ModelOne.SingleFieldQuery({
            tranData: this.tranData
            // 转换后的数据
        });
        singleField.addUSListener('doQuery', function () {// 所需要执行的查询操作
            var params = singleField.getParams();// 获得查询参数
            me.gridzz.getStore().load(params);// 根据传递的参数加载数据
        });
        singleField.initComponent();
        return singleField;
    },
    getSeniorQuery: function () {
        var me = this;
        var tranData = me.tranData;// 转换的数据
        var data = {
            fieldsStoreData: tranData.fieldsStoreData,
            componentsData: tranData.componentsData,
            container: this.gridContainer
        };
        var seniorQuery = new ModelOne.SeniorQuery(data);// 创建高级查询组件
        seniorQuery.addUSListener('doQuery', function (params) {
            me.gridzz.getStore().load(params);
        });
        var seniorButton = {
            xtype: 'button',
            iconCls: 'page-xtsz',
            text: '高级查询',
            handler: function () {
                seniorQuery.initComponent();// 高级查询组件初始化();// 通过点击创建高级查询组件
            }
        };
        return seniorButton;
    }
});