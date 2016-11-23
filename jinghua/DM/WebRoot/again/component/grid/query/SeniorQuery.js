/**
 * @class NS.grid.query.SeniorQuery
 * @extend NS.Component
 * 高级查询
 */
NS.define('NS.grid.query.SeniorQuery', {
    extend: 'NS.Component',
    /**
     *  初始化构造函数
     *
     */
    constructor: function () {
        this.initData.apply(this, arguments);
        this.initComponent();
    },
    /***
     * 注册下将要关联的grid
     * @param grid
     */
    registerGrid: function (grid) {
        this.grid = grid;
    },
    /**
     * 清空查询组件在grid的参数
     * @private
     */
    clear: function () {

    },
    /**
     *
     *  配置组件数据源
     * @param config 要配置的数据
     * @private
     */
    initData: function (config) {
        this.config = config;
        this.data = config.data;
        this.initFieldsStoreData(config.data);
        this.registerGrid(config.grid);
    },
    /***
     * 初始化字段数据
     * @private
     */
    initFieldsStoreData: function (data) {
        var fieldsData = [],item;
        for (var i = 0, len = data.length; i < len; i++) {
            item = data[i];
            if (item.isQuery) {
                fieldsData.push({value: item.name, display: item.nameCh});
            }
        }
        this.fieldsData = fieldsData;
    },
    /**
     * @private
     */
    initComponent: function () {
        this.component = Ext.create('Ext.button.Button', {
            text: '高级查询',
            iconCls : 'ns-btn-advanced-search',
            listeners: {
                click: {
                    scope: this,
                    fn: function () {
                        this.initWindow();
                    }
                }
            }
        });
    },
    /**
     * 初始化组件
     * @param {Object} config 配置参数
     * @private
     */
    initWindow: function () {
        this.condition = new NS.grid.query.Condition({// 创建条件面板生成器
            SeniorQuery: this,
            fieldsStoreData: this.fieldsData,// 默认属性字段store数据
            componentsData: this.data
            // 默认组件数据
        });
        this.condition.initComponent();//初始化组件
        this.createWindow({
            width: 800,
            height: 400,
            autoScroll: true,
            autoShow: true,
            title: '高级查询',
            modal: true,
            resizable: false,
            items: this.condition.getPanel(),
            buttons: this.createButtons(this.config)
        });
    },
    /**
     * 创建窗体
     * @private
     */
    createWindow: function (config) {
        this.window = Ext.create('Ext.window.Window', config);
    },
    /**
     * 创建组件
     * @private
     */
    createButtons: function (config) {
        var me = this;
        var button1 = Ext.create('Ext.button.Button', {
            text: '提交查询',
            handler: function () {
                var params = me.getParams();
                if (params.error == "yes") {
                    return;
                }
                delete params.error;
                me.window.close();
                me.grid.load(params);
            }
        });
        var button2 = Ext.create('Ext.button.Button', {
            text: '取消',
            handler: function () {
                me.window.close();
            }
        });
        return [button1, button2];
    },
    /**
     * 向后台提交数据
     */
    getParams: function (config) {
        var me = this;
        var error = "no";
        var params = [];// 查询参数
        var ids = this.condition.ids;
        for (var i = 0; i < ids.length; i++) {
            var field = me.getValueById(ids[i] + "_fields");// 属性名
            var operator = me.getValueById(ids[i] + "_operator");// 操作符
            var value = me.getValueById(ids[i] + "_remove");// 值
            if (field == "") {
                Ext.Msg.alert("警告", "条件" + (i + 1) + "-属性不能为空！");
                error = "yes";
                break;
            } else if (operator == "") {
                Ext.Msg.alert("警告", "条件" + (i + 1) + "-关系不能为空！");
                error = "yes";
                break;
            } else if (value == "") {
                Ext.Msg.alert("警告", "条件" + (i + 1) + "-值不能为空");
                error = "yes";
                break;
            }
            var logical = "";
            if (i != ids.length - 1) {
                logical = me.getValueById(ids[i] + "_logical");// 逻辑操作符
                if (logical == "") {
                    Ext.Msg.alert("警告", "条件" + (i + 1) + "-连接符不能为空！");
                    error = "yes";
                    break;
                }
            }
            params.push({
                'field': field,
                'operator': operator,
                'value': value,
                'logical': logical
            });
        }
//        var paramString = JSON.stringify(params);
        return {
            "seniorQuery": params,
            "error": error
        };
    },
    /**
     * 通过id获取组件的值
     * @param {String} id 需要获取值的组件的id
     * @private
     */
    getValueById: function (id) {
        var com = Ext.getCmp(id);
        var value;
        if (com instanceof Ext.form.field.Date || com instanceof Ext.form.field.Time)
            value = com.getRawValue();
        else
            value = com.getValue();
        if (!value || value == "null" || value == "undefined ") {
            value = "";
        }
        return value;
    }
});