/***
 * @class NS.grid.ColumnManager
 * @extends NS.Base
 * 负责管理NS.grid.Grid 中Column的容器管理类
 */
NS.define('NS.grid.ColumnManager', {
    constructor: function (config) {
        this.config = config;
        this.initColumns();
    },
    data : [],
    /***
     * 初始化列集合
     * @private
     */
    initColumns: function () {
        var data = this.config.data;//组件配置数据
        this.generateColumns(data||[]);//生成column组件
        this.dealWithColumnConfig();//处理列配置数据
        //this.dealForceFit();//进行自适应处理
        //this.makeAssociate();//将组件进行级联设置
    },
    /**
     * 进行自适应处理
     */
    dealForceFit : function(){
        var len = this.columns.length;
        if(len>0){
            delete this.columns[len-1].width;
            this.columns[len-1].flex = 1;
            this.columns[len-1].align = "left";
        }
    },
    /***
     * 处理列配置数据
     * @private
     */
    dealWithColumnConfig: function () {
        var config = this.config.columnConfig;//列配置数据
        if (!this.columnset) {
            this.columnset = {};
            this.columns = [];
        }
        if (NS.isArray(config)) {
            for (var i = 0, len = config.length; i < len; i++) {
                var c = config[i];
                if (c && c.name &&  !this.columnset[c.name]) {
                    var column = this.generateColumn1(c);
                    this.registerColumn(column, c.name, c.index);
                }
            }
        }

    },
    /**
     * 进行组件之间的级联配置
     * @private
     */
    makeAssociate: function () {
        var columns = this.columns;
        var map = new Object();
        Ext.each(columns, function (column) {// 将编码实体相同的column放置到一起
            var bmst = column.codeEntityName;
            if (bmst == "tree" && map[bmst]
                && bmst) {
                map[bmst].push(column);
            } else if (!map[bmst] && bmst) {
                map[bmst] = [];
                map[bmst].push(column);
            }
        });
        for (var i in map) {
            if (i && map[i] instanceof Array) {
                map[i].sort(function (a, b) {
                    if (a.cc > b.cc) {
                        return 1;
                    }
                    if (a.cc < b.cc) {
                        return -1;
                    }
                    return 0;
                });
            }
        }
        for (var i in map) {
            if (i && map[i] instanceof Array) {
                this.associate(map[i]);
            }
        }
    },
    /***************************************************************************
     * 对组件进行关联
     *
     * @param {Array} array
     */
    associate: function (array) {
        var me = this;
        for (var i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                return;
            }
            var column1 = array[i];
            var column2 = array[i + 1];
            var editor1 = column1.editor;
            var editor2 = column2.editor;
            if (editor1 == null) {// 如果editor为空，说明当前可编辑grid未开启
                return;
            }
            var filterData;
            var pid;
            editor1.addListener('select', function (combox, records) {
                pid = records[0].get('id');
                filterData = me.filterAndSetComboBoxData(pid, column2);
                editor2.getStore().loadData(filterData);
                editor2.setValue();
                var grid = column1.up('gridpanel');
                var e1 = null;
                grid.on('beforeedit', function (editorp, e) {
                    if (editor2 == e.column.getEditor()) {
                        if (editor1.getValue() == ""
                            || editor1.getValue() == null) {
                            Ext.Msg.alert('提示', "请先选择｛" + column1.text + "}信息");
                            editorp.cancelEdit();
                            editorp.startEdit(e.record, column1);
                        } else {
                            var nowid = e.record.get(column2.dataIndex);
                            var store = editor2.getStore();
                            var record = store.getById(nowid);
                            if (nowid != "") {// 如果值存在
                                if (!record) {// 如果查找不到record
                                    if (me.getParentId(nowid, column2) == e.record
                                        .get(column1.dataIndex)) {// 如果当前组件Id的父Id和组件1的id相同
                                        editor2.getStore().loadData(me
                                            .getStoreDataById(nowid,
                                                column2));
                                        editor2.setValue(nowid);
                                    }
                                }
                            } else {
                                if (e.record.get(column1.dataIndex) != "")
                                    editor2
                                        .getStore()
                                        .loadData(me
                                            .filterAndSetComboBoxData(
                                                e.record.get(column1.dataIndex),
                                                column2));
                            }
                        }
                    }
                });
            });

            editor2.addListener('focus', function (combox) {

            });
        }
    },
    /**
     * 通过子节点Id查询父节点Id
     *
     * @param {String/Number}  childId
     * @param {Ext.grid.column.Column}  column
     * @return {String/Number}
     */
    getParentId: function (childId, column) {
        var data = column.data;
        var parentId;
        for (var i = 0; i < data.length; i++) {
            if (data[i].id == childId) {
                parentId = data[i].pid;
                break;
            }
        }
        return parentId;
    },
    /**
     * 获得该节点的父节点id 对应的数据
     *
     * @param {String/Number} childId
     * @param {Ext.grid.column.Column} column
     * @return {Array}
     */
    getStoreDataById: function (childId, column) {
        var data = column.data;
        var array = [];
        var parentId = this.getParentId(childId, column);
        for (var i = 0; i < data.length; i++) {
            if (data[i].pid == parentId) {
                array.push(data[i]);
            }
        }
        return array;
    },
    /**
     * 过滤并设置级联ComboBox数据
     * @param {String/Number} parentId
     * @param {Ext.grid.column.Column} column
     */
    filterAndSetComboBoxData: function (parentId, column) {
        var store = (column.editor || column.getEditor()).getStore();
        var data = column.data;// 数据
        var array = [];
        for (var i = 0; i < data.length; i++) {
            if (data[i].pid == parentId) {
                array.push(data[i]);
            }
        }
        store.loadData(array);
        return array;
    },
    /***
     * 生成列集合
     * @param obj
     */
    generateColumns: function (dataset) {
        if (this.config.lineNumber) {//如果行号属性为true，则显示行号
            this.registerColumn(this.generateColumn1({xtype: 'rownumberer',text : ""}));
        }
        for (var i = 0, len = dataset.length; i < len; i++) {
//            if(dataset.length == i+1){
//               dataset[i].flex = 1;
//            }
            var column = this.generateColumn(dataset[i]);

            this.registerColumn(column);
        }
    },
    /**
     * 将column注册进ColumnManager中
     * @param column
     * @param name
     */
    registerColumn: function (column, name, index) {
        if (!this.columnset) {
            this.columnset = {};
            this.columns = [];
        }
        if (column) {
            if (name) {
                this.columnset[name] = column;
            } else {
                this.columnset[column.dataIndex] = column;
            }
            if (index) {
                this.columns = NS.Array.insert(this.columns, column.index, column);
            } else {
                this.columns.push(column);
            }
        }
    },
    /**
     * 生成Column对象
     * @param config
     * @return {Ext.column.Column}
     * @private
     */
    generateColumn1: function (config) {
        var basic = {
            width: config.width || 70,//默认长度为70
            dataIndex: config.name,//列的索引
            sortable: false,// 不允许排序
            draggable: false//不允许拖动
        };
        if(config.flex == 1)delete basic.width;
        if(config.editor){
           if(NS.isNSComponent(config.editor)){
               config.editor = config.editor.getLibComponent();
           }
        }
        if(config.renderer){//处理renderer函数的过程
            config.renderer = this.processRenderer(config.renderer);
        }
        NS.apply(basic, config, this.config.defaultConfig);
        switch (config.xtype) {
            case "buttoncolumn":
                return Ext.create('Ext.grid.column.ButtonColumn', basic);
            case "linkcolumn" :
                return Ext.create("Ext.grid.column.LinkColumn", basic);
            case "rownumberer" :
                var rowConfig = {
                    text: ''
                };
                NS.apply(rowConfig, this.config.defaultConfig);
                return Ext.create('Ext.grid.RowNumberer', rowConfig);
            case "progresscolumn" :
                return Ext.create("Ext.grid.column.ProgressColumn",config);
            default :
                return Ext.create('Ext.grid.column.Column', basic);
        }
    },
    /***
     * 生成列对象
     * @param {Object} data
     * @return {Ext.grid.column.Column}
     */
    generateColumn: function (data) {
        if (!data.isColumnCreate)
            return null;
        var columnConfig = this.getConfigColumn(data.name);//获取从grid中传递的column的配置数据
        var field = NS.util.FieldCreator.createField(data,columnConfig.editorConfig||{});//创建列对应的组件
        this.registerField(data.name, field);//将组件注册到columnManager中
        var config = {
            hidden: !(data.isColumnShow),//是否显示
            codeEntityName: data.codeEntityName,// 编码实体
            cc: data.cc,// 编码层次
            width: data.columnWidth || 70,//默认长度为70
            sortable: false,// 不允许排序
            draggable: false,//不允许拖动
            header: data.nameCh,
            dataIndex: data.name,
            align : 'center',
            //align: data.dataType == "Long" ? "left" : "right",//数据显示位置
            renderer: this.getRender(data),//获取列的绑定函数
            editor: field//创建可编辑组件
        };
        NS.apply(config, columnConfig, this.config.defaultConfig);
        if(config.flex == 1)delete config.width;
        if(config.editor){
            this.registerField(data.name, config.editor);//将组件注册到columnManager中
        }
        if(columnConfig.renderer){//处理renderer函数的过程
            config.renderer = this.processRenderer(columnConfig.renderer);
        }
        if(columnConfig.columnStyle){
            config.renderer = this.processRendererStyle(config);//处理ColumnConfig新增的样式
        }
        if(data.editable === false || config.editable === false){
            delete config.editor;//如果组件不可编辑，设置不可编辑属性
        }
        switch (config.xtype) {
            case "buttoncolumn":
                return Ext.create('Ext.grid.column.ButtonColumn', config);
            case "linkcolumn" :
                return Ext.create("Ext.grid.column.LinkColumn", config);
            case "progresscolumn" :
                return Ext.create("Ext.grid.column.ProgressColumn", config);
            default :
                return Ext.create('Ext.grid.column.Column', config);
        }
    },
    /**
     * 处理renderer函数
     * @param renderder
     */
    processRenderer : function(renderer){
        return function(value,metaData,record,rowIndex,colIndex){
            return renderer.call(this.NSContainer,value,record.data,rowIndex,colIndex);
        }
    },
    /**
     * 处理添加的renderer样式
     * @param columnConfig
     * @return {Function}
     */
    processRendererStyle : function(columnConfig){
    	var me = this,style = columnConfig.columnStyle,styleString = me.generateStyleString(style),renderer = columnConfig.renderer;
	    return function(){
	       return "<div style='width:100%;height:100%;"+styleString+"'>"+renderer.apply(this,arguments)+"</div>";
	    }
    },
    /**
     * 生成样式字符串
     * @param {Object} obj 样式对象
     * @return {string}
     */
    generateStyleString : function(obj){
        var style = "";
        for(var i in obj){
            style += i + ":" + obj[i]+";";
        }
        return style;
    },
    /***
     * 通过name来查询配置列参数
     * @param {String} name 配置数据中name属性为传入的值的对象
     */
    getConfigColumn: function (name) {
        var array = this.config.columnConfig || [], item,result = {};
        for (var i = 0, len = array.length; i < len; i++) {
            item = array[i];
            if (item.name == name) {
                result = item;
                break;
            }
        }
        if(result.editor){
           if(NS.isNSComponent(result.editor)){
              result.editor = result.editor.getLibComponent();
           }
        }
        return result;
    },
    /**
     * 获取column列绑定函数
     * @param columnData
     * @return {Function} 返回的需要render的函数
     */
    getRender: function (columnData) {
        var me = this, renderer;//column列附加的函数
        switch (columnData.xtype) {
            case 'combobox' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var dataIndex = this.columns[colIndex].dataIndex;
                    return me.getComboxDisplayValue(value, record, dataIndex);// 获取显示值
                };
                break;
            case 'treecombobox' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var dataIndex = this.columns[colIndex].dataIndex;
                    return me.getTreeComDisplayValue(value, record, dataIndex);// 获取显示值
                };
                break;
            case 'tree' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var dataIndex = this.columns[colIndex].dataIndex;
                    return me.getComboxDisplayValue(value, record, dataIndex);// 获取显示值
                };
                break;
            case 'checkbox' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    if (value == "true" || value == "1") {
                    	//record.set(this.columns[colIndex].dataIndex, "1");
                        return "是";
                    } else {
                    	//record.set(this.columns[colIndex].dataIndex, "0");
                        return "否";
                    }
                };
                break;
            case 'datefield' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var v = value;
                    if (value instanceof Date) {
                        v = Ext.util.Format.date(value, 'Y-m-d');
                        record.set(this.columns[colIndex].dataIndex, v);
                    }
                    return v;
                };
                break;
            default : 
             	renderer = function(v){
             		return v;
             	}
//            case 'textfield' :
//                var expression = columnData.drillExp;//是否下钻属性
//                if (expression) {
//                    (function (expression) {
//                        var array = columnData.drillExp.toString().split('-');
//                        if (array.length == 3 && array[0] == 1) {
//                            renderer = function (value, metadata, record, rowIndex, colIndex) {
//                                var id = record.get(array[2]);
//                                if (array[1] == 'student') {
//                                    return "<a class= 'gobal_grid_link' href=\"javascript:XsxxWindow.showExportWindow(\'"
//                                        + id
//                                        + "\')\">" + value + "</a>";
//                                } else if (array[1] == "teacher") {
//                                    return "<a class= 'gobal_grid_link' href=\"javascript:JsxxWindow.showExportWindow(\'"
//                                        + id
//                                        + "\')\">" + value + "</a>";
//                                } else {
//                                    return value;
//                                }
//                            };
//                        }
//                    })(expression);
//                }
                break;
        }
        return renderer;
    },
    /***
     * @private
     * 通过name获取Field组件
     * @param {String} 获取name属性为传入的值的可编辑组件
     */
    getField: function (name) {
        return this.fieldset[name];
    },
    /***
     * 将生成的field注册进入ColumnManager的管理中
     * @private
     * @param {String}name  名称
     * @param {Ext.form.field.Base}field 组件
     */
    registerField: function (name, field) {
        if (!this.fieldset) {
            this.fieldset = {};
            this.fieldlist = [];
        }
        this.fieldset[name] = field;
        this.fieldlist.push(field);
    },
    /**
     * 此方法为combobox专用，通过后台传递的id的值，获取其对应名称，并且用来显示
     *
     * @param {String/Number} value
     * @param {Object} record
     * @param {String/Number} colIndex
     * @return {String/Number}
     */
    getComboxDisplayValue: function (value, record, dataIndex) {
        if (value != "" || value != null) {
//            var field = this.getField(dataIndex);
//            if(field.multiSelect === true){
//                field.setValue(value.split(','));
//            }else{
//                field.setValue(value);
//            }
//            var rawvalue = field.getRawValue();
//            if(rawvalue != 0 ||  rawvalue != false){
//               return rawvalue;
//            }else{
//               return value;
//            }
            var com = this.getField(dataIndex);
            var displayField = com.displayField;
            var valueField = com.valueField;
            var store = com.getStore();
            var crecord = store.findRecord(valueField, new RegExp("^"+value+"$"));
            if (crecord != null) {
                var displayValue =  crecord.get(com.displayField);//获取显示值
                var valuefield = crecord.get(valueField);//获取实际值
                record.data[dataIndex] = valuefield;//设置实际值
                return displayValue;//设置显示值
            } else {
                return value;
            }
        } else {
            return value;
        }
    },
    /***
     * 获取用于显示的树的节点的数据
     * @param {String/Number} value
     * @param {Object} record
     * @param {String/Number} dataIndex
     */
    getTreeComDisplayValue: function (value, record, dataIndex) {
        var me = this;
        if (value != "" || value != null) {
            var com = me.getField(dataIndex);
            var store = com.picker.getStore();
            var crecord = store.getNodeById(value);
            if (crecord != null) {
                var displayValue = crecord.get('text');
                return displayValue;
            } else {
                return value;
            }
        } else {
            return value;
        }
    }
});