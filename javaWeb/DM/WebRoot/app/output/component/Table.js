/*******************************************************************************
 * 定义统计报表组件
 */
NS.define('Output.Table', {
    /***************************************************************************
     * 构造函数
     */
    constructor: function (config) {
        this.config = config;// 将参数设置为内部变量
        this.dataConvert();// 数据转换
        this.createComponent();
        this.addEvent('statuschange');// 增加状态变化事件
    },
    /***************************************************************************
     * 创建组件对象
     */
    createComponent: function () {
        var me = this;
        var data = this.getTplData();
        var tpl = this.getTpl();
        var basic = {
            tpl: tpl,
            rows: data,
            baseCls: 'statistics_table',
            region: 'south',
            height: '80%',
            autoScroll: true,
            title: "" || this.config.title,
            listeners: {
                'afterrender': function () {
                    this.tpl.overwrite(this.el, this.rows);
                    var el = this.el.first();
                    var width = el.getWidth();
                    var x = el.getXY();
                    var com = this.com = clist[0];//combox组件
                    var cwidth = com.el.getWidth();
                    toolbarContainer.el.dom.style.paddingLeft = (width - cwidth + 25) + 'px';
                }
            }
        };
        var clist = me.getChangeRangeComponents(this.config.displayData.components);
        var toolbarContainer = this.toolbar = Ext.create('Ext.toolbar.Toolbar', {
            baseCls: 'style="margin:0 auto;"',
            height: 25,
            // xtype:'container',
            items: clist
        })
        var tableContainer = this.tableContainer = Ext.create('Ext.container.Container', basic);
        if (clist.length == 0) {
            this.component = tableContainer;
        } else {
            this.component = Ext.create('Ext.container.Container', {
                baseCls: '',
                items: [
                    toolbarContainer,
                    tableContainer

                ]
            });
        }

    },
    /***
     *
     */
    loadData: function (config) {
        this.config = config;//
        this.dataConvert();// 数据转换
        var data = this.getTplData();
        var tel = this.tableContainer.el;
        var table = this.tableContainer;
        table.tpl.overwrite(tel, data);
        var el = table.el.first();
        var width = el.getWidth();
        var x = el.getXY();
        var com = table.com;//combox组件
        var cwidth = com.el.getWidth();
        this.toolbar.el.dom.style.paddingLeft = (width - cwidth + 25) + 'px';
    },
    /**
     * 获取组件类型
     */
    getComponentType: function () {
        return this.config.componentType;
    },
    /***************************************************************************
     * 返回依赖底层类库的组件信息
     *
     * @return {}
     */
    getLibComponent: function () {
        return this.component;
    },
    /***************************************************************************
     * 生成带有层级列数据结构
     *
     * @return {}
     */
    makeColumnData: function (columns) {
        var data = {
            items: columns
        };
        var deep = this.getDeep(data);// 叶子节点的最大深度
        var array = new Array(deep);// 生成带有深度的节点元素
    },
    /***************************************************************************
     * 数据格式转换类
     */
    dataConvert: function () {
        var cfg = this.config;
        this.componentId = cfg.componentId;// 组件id
        var display = cfg.displayData;// 显示数据
        this.initData = TreeData.chartDataConvert(display);
    },
    /***************************************************************************
     * 获取生成tpl所需要的数据
     */
    getTplData: function () {
        var rows = this.initData.rows;// 列数据
        var columns = this.initData.columns;// 行数据
        var util = TreeData;
        var columnlist = util.getColumnsList(columns);// 获取列层级数据
        var colIndexlist = util.getLeafList({
            items: columns
        });// 获取底层列索引数据
        var rowlist = util.getRowList(rows, colIndexlist);// 获取内容层级数据
        var coldeep = util.getDeep({
            items: columns
        });// 获取表头深度
        var rowdeep = util.getDeep({
            items: rows
        });// 获取表体深度
        var data = {
            columns: columnlist,
            rows: rowlist,
            coldeep: coldeep,
            rowdeep: rowdeep - 2
        };
        return data;
    },
    /***************************************************************************
     * 获取table对象的tpl
     */
    getTpl: function () {
        var tpl = new Ext.XTemplate(
            '<table   cellpadding="0" cellspacing="1" border="1" align="center">',
            '<tpl for="columns">',
            '<tr>',
            '<th colspan={parent.rowdeep} ></th>',
            '<tpl for=".">',
            '<th colspan={colspan} rowspan = {rowspan}>{header}</th>',
            '</tpl>',
            '</tr>',
            '</tpl>',
            '<tpl for="rows">',
            '<tr>',
            '<tpl for=".">',
            '<tpl if="items">',
            '<td colspan={rowspan} rowspan = {colspan}>{header}</td>',
            '<tpl else>',
            '<td>{.}</td>',
            '</tpl>',
            '</tpl>',
            '</tr>',
            '</tpl>',
            '</table>');
        return tpl;
    },
    /***************************************************************************
     * 将组件添加到一个dom元素上或者一个id为｛id｝的dom元素
     */
    render: function (id) {
        if (id.nodeType) {
            id.appendChild(this.component.el.dom);
        } else {
            document.getElementById(id).appendChild(this.component.el.dom);
            ;
        }
    },
    setWidthAndHeight: function (width, height) {
        var C = this.component;
        // C.setWidth(width);
        // C.setHeight(height);
    },
    /**
     * 得到选择范围组件
     */
    getChangeRangeComponents: function (components) {
        var componentList = this.componentList = [];
        if (components != null) {
            var me = this, components = components
                || [];
            for (var i = 0; i < components.length; i++) {
                var component = components[i];
                var com = me.createChangeRangeComponent(component)
                if (component.comType == 'combox')
                    this.combox = com;
                componentList.push(com);
            }
        }
        return [this.combox];
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
                    "padding-top": '3px'
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
//			// 如果是指标且指标数据长度小于2个，则隐藏之。
//			if (name != 'wd' && dataList.length == 1) {
//				_obj.hidden = true;
//			}
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
                        lablewidth += 16;// 汉字
                        // lablewidth += 14;
                    } else if (!isNaN(str)) {// 数字
                        lablewidth += 10;
                        // lablewidth += 8;
                    } else {
                        lableWidth += 4;//
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
            var bWidth = 30;// 间隙宽度
            width = width + bWidth;// 相应宽度加上间隙宽度
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
            change: function (self, newV, oldV, eOpts) {
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
                        values = component.getValue();
                        var value = values[name] || "";
                        array.push(value);
                    } else if (xtype == 'combobox') {
                        values = component.getValue();// combobox的取值方法
                        array.push(values);
                    } else {
                    }
                    status[name] = array;
                }
                that.allStatus = status;
                that.fireEvent('statuschange', that.config.componentId);// 触发状态改变事件
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