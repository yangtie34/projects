/**
 * 图形组件父类
 */
Ext.define('Output.ChartParentComponent', {
    extend: 'Ext.Component',
    /**
     * 构造函数
     *
     * @param {}
     *            componentData 所有的组件数据
     */
    constructor: function (componentData) {
        this.callParent();
        this.componentData = componentData;
        var type = componentData.templateType || 'portal';//如果无模板类型 则默认portal类型
        var templateType = this.templateType = this
            .getTemplateType(type);// 得到模板类型
        this.componentData.templateType = templateType;// 将componentData数据内模板类型重置为转换后的类型
        this.componentList = [];// 声明componentList为空数组
        this.createChartComponent();
        this.addEvents('statuschange');
    },
    onRender: function () {
        this.callParent(arguments);
    },
    /**
     * 获取页面模板类型
     */
    getTemplateType: function (typeName) {
        // 类型维护区
        var type = {
            classical: 'classical',// 经典模板
            quarters: 'quarters',// 四分屏模板
            single: 'single'// 单分屏
            , portal: 'portal'//通用模板，该模板会仅仅展示图形 无交互
        };
        return type[typeName];// 默认经典模板
    },
    /**
     * 获取组件类型
     */
    getComponentType: function () {
        return this.componentData.componentType;
    },
    /**
     * 得到该组件
     */
    getLibComponent: function () {
        return this.container;
    },
    /**
     * 渲染方法
     *
     * @param {}
     *            id
     */
    render: function (id) {
        this.container.render(id);
    },
    _whenWindowLayout: function (me) {
        this.setContainerWidthAndHeight(me);
    },
    /**
     * 设置容器宽高
     *
     * @param {}
     *            me 容器本身
     */
    setContainerWidthAndHeight: function (me) {
        var tT = this.templateType;
        var pe = me.el.parent();
        var pwidth = pe.getWidth(), height;
        if (tT == 'quarters') {
            // 四分屏页面下
            pwidth = pe.parent().getWidth() * 0.46;
            height = pwidth * 1 / 2;
        } else if (tT == 'single') {
            // 单分屏下
            pwidth = pwidth * 0.9;
            height = pwidth * 2 / 5;
        } else if (tT == 'classical') {
            // var pheight = pe.getHeight()设置最大宽度 和最小宽度 然后再按照比例计算
            pwidth = pwidth * 0.8 > 900 ? 900 : pwidth * 0.8;
            height = (pwidth * 3) / 8;
        } else {
            //portal或者未知类型 则全部
            pwidth = "100%";
            height = this.componentData.height;
        }
        me.setWidth(pwidth);
        me.setHeight(height);
    },
    /**
     * 设置tbar的宽度（特殊情况也对高度做了调整）
     *
     * @param {}
     *            me
     */
    setTbarWidth: function (me) {
        var width = 0;
        for (var c = 0; c < this.componentList.length; c++) {
            var com = this.componentList[c];
            if (!com.hidden) {
                width += com.getWidth();
            }
        }
        me.setWidth(width);
        // 如果仅仅显示一个下拉框的话,将tbar高度置为27
        if (width == 150) {
            me.setHeight(27);
        }
    },
    /** 创建chart组件* */
    createChartComponent: function () {
        var that = this, obj = {};
        that.componentList = that.getChangeRangeComponents();
        that.getNewChart();
        //portal的模板 不再需要交互
        if (that.componentList.length != 0 && this.templateType != 'portal') {
            obj = {
                layout: 'border',
                border: false,
                xtype: 'container',
                baseCls: 'style="margin-left : 22px;background-color:white;"',
                frame: false,
                items: [
                    {
                        region: 'center',
                        border: false,
                        xtype: 'container',
                        layout: 'fit',
                        baseCls: '',
                        items: that.newChart.getLibComponent()
                    },
                    {
                        region: 'south',
                        border: false,
                        xtype: 'container',
                        items: [
                            {
                                xtype: 'toolbar',
                                baseCls: 'style="margin:0 auto;"',// 选择组件的位置css控制
                                items: that.componentList,
                                listeners: {
                                    afterrender: function () {
                                        that.setTbarWidth(this);
                                    }
                                }
                            }
                        ]
                    }
                ]
            };
        } else {
            obj = {
                layout: 'fit',
                border: false,
                items: that.newChart.getLibComponent()
            };
        }
        that.newChart.parentCt = this;
        this.container = Ext.create('Ext.container.Container', obj);
        this.container.addListener({
            afterrender: function () {
                that.setContainerWidthAndHeight(this);
            }
        });
    },
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        this.newChart = Ext.create('Output.Chart', this.componentData);
    },
    /**
     * 加载组件数据--仅针对图形本身（商议结果）
     *
     * @param {}
     *            chartData chart本身数据
     */
    loadData: function (chartData) {
        this.newChart.loadChartData(chartData);
    },

    /**
     * 得到选择范围组件
     */
    getChangeRangeComponents: function () {
        var componentList = [];
        if (this.componentData != null) {
            var that = this, dData = that.componentData.displayData == null
                ? {}
                : that.componentData.displayData, component = dData.components
                || [];
            for (var i = 0; i < component.length; i++) {
                componentList.push(that
                    .createChangeRangeComponent(component[i]));
            }
        }
        return componentList;
    },
    /**
     * 创建选择范围组件：单选组件、复选组件、下拉组件
     *
     * @param {}
     *            obj 传递的对象 obj格式： [ { comType : 'checkboxgroup', valueRange :
	 *            'wd',//数据类型 defaultValue : '值1,值2', data : [ { name : '名称1',//
	 *            显示值 value : '值1'// 实际值 }, { name : '名称2', value : '值2' } ] }, {
	 *            comType : 'radiogroup', valueRange : 'zb',//数据类型 defaultValue :
	 *            '值1', data : [ { name : '名称1',// 显示值 value : '值1'// 实际值 }, {
	 *            name : '名称2', value : '值2' } ] }, { comType : 'combox',
	 *            //defaultValue : '值1', valueRange : 'fw',//数据类型 data : [ {
	 *            name : '名称1',// 显示值 value : '值1'// 实际值 }, { name : '名称2',
	 *            value : '值2' } ] } ]
     */
    createChangeRangeComponent: function (obj) {
        var that = this, type = obj.comType, rangeComponent;// 组件类型，范围组件
        var name = obj.valueRange;// 得到名称
        var defaultValues = obj.defaultValue, defaultValuesList;// 默认值字符串
        if (defaultValues) {
            // defaultValuesList = defaultValues.split(",");//
            // 默认值数组，后台更改为数组形式了故而删除
            defaultValuesList = defaultValues;
        }
        var dataList = obj.data || [];// 数据集
        if (type == 'combox') {
            // 下拉框组件
            var states = Ext.create('Ext.data.Store', {
                fields: ['name', 'value'],
                data: dataList
            });

            var _obj = {
                store: states,
                style: {
                    "padding-top": '5px'
                },
                emptyText: '请选择...',
                queryMode: 'local',
                displayField: 'name',// 显示值
                valueField: 'value',// 实际值
                editable: false,// 不可编辑
                width: 150,// 默认下拉的宽度
                name: name
            };
            if (defaultValues) {
                _obj.value = obj.defaultValue;
            }
            // 如果是指标且指标数据长度小于2个，则隐藏之。name != 'wd' &&
            if (dataList.length == 1) {
                _obj.hidden = true;
            }
            rangeComponent = Ext.create('Ext.form.ComboBox', _obj);

        } else {
            // 数据处理
            var arrItems = [], columns = [], i = 0, width = 0;// 默认宽度0

            for (; i < dataList.length; i++) {
                var _obj = {}, j = 0;
                var boxLabel = _obj.boxLabel = dataList[i].name;
                // 检验字符串内是否包含数字
                // 得到字符 转换为array 根据具体情况做长度
                var k = 0, lablewidth = 16;// 默认的为图标长
                for (var z = 0; z < boxLabel.length; z++) {
                    var str = boxLabel.substring(k, k + 1);

                    if (str.match(/^[\u4e00-\u9fa5]$/)) {
                        lablewidth += 15;// 汉字
                        // lablewidth += 16;
                    } else if (!isNaN(str)) {// 数字
                        lablewidth += 10;
                        // lablewidth += 8;
                    } else {
                        lableWidth += 5;// 其他
                    }
                }
                width += lablewidth;
                columns.push(lablewidth);
                if (defaultValuesList) {
                    for (; j < defaultValuesList.length; j++) {
                        if (dataList[i].value == defaultValuesList[j]) {
                            _obj.checked = true;
                        }
                    }
                }
                _obj.name = name;
                _obj.inputValue = dataList[i].value;
                arrItems.push(_obj);
            }
            var bWidth = 20;// 间隙宽度
            width += bWidth;// 相应宽度加上间隙宽度
            columns.push(bWidth);// 添加间隙
            var obj_ = {
                name: name,
                columns: columns,
                width: width,
                items: arrItems
            };
            // 设置是否隐藏
            if (arrItems.length == 1) {
                obj_.hidden = true;
            }
            if (type == 'checkboxgroup') {
                // 创建复选框组件
                rangeComponent = Ext.create('Ext.form.CheckboxGroup', obj_);

            } else if (type == 'radiogroup') {
                // 创建单选组组件
                rangeComponent = Ext.create('Ext.form.RadioGroup', obj_);
            }
            // wdith = 0;
        }
        // 添加监听事件-当值改变时触发
        rangeComponent.addListener({
            change: function () {
                var status = {}, i;
                for (i = 0; i < that.componentList.length; i++) {
                    var array = [], component = that.componentList[i], name = component
                        .getName(), values, xtype = component.getXType();// 组件类型
                    if (xtype == 'checkboxgroup') {
                        values = component.getChecked();// 复选组
                        for (var j = 0; j < values.length; j++) {
                            array.push(values[j].inputValue);// 这个应该还取值
                        }
                    } else if (xtype == 'radiogroup') {
                        // 用单选控件,同一页面相同name的话,则相应也会change.所以每个组件在配置的时候请保持单个radiogroup！！
                        values = component.getValue();
                        var value = values[name] || null;
                        array.push(value);
                    } else if (xtype == 'combobox') {
                        values = component.getValue();// combobox的取值方法
                        array.push(values);
                    } else {
                    }
                    status[name] = array;
                }
                that.allStatus = status;
                that.fireEvent('statuschange', that.componentData.componentId);// 触发状态改变事件
            }
        });

        return rangeComponent;// 返回范围组件
    },
    /**
     * 得到组件所有状态ComponentAll
     *
     */
    getStatus: function () {
        return this.allStatus;
    }

});