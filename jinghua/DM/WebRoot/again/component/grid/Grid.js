/***
 * @class NS.grid.Grid
 * @extends NS.container.Panel
 *   例如

         var data = NS.E2S(headerData);
         var grid = new NS.grid.Grid({
                        plugins : [new NS.grid.plugin.CellEditor(),
                                    new NS.grid.plugin.HeaderQuery()],
                        columnData : data,
                        serviceKey : 'queryGridData',//你也可以写成带参数的形式serviceKey : {
                                                                                //key : 'queryGridData',
                                                                                // params : {
                                                                                //    entityName : this.entityName
                                                                                // }
                                                                                //}
                        proxy : new NS.mvc.Model(
                                        {serviceConfig:{
                                                    'queryGridData' : 'baseservice'
                                                    }
                                        }),
                        fields : ['xh','xm'],
                        data : showData.data,
                        columnConfig : [{
                              name : 'xh',
                              locked : true,able
                              editable : false,//设置该列是否可编辑
                              index : 4
                        },
                         {
                         name : 'xh',
                         editor : new NS.form.field.Text({})
                         },
                         {
                         name : 'xh',
                         editorConfig : {//后台实体属性表组件的扩展数据配置
                                    displayField : 'dm',
                                    fields : ['id','dm','mc']
                                    hidden : true
                            }
                         },
                            { text: '查看列',
                              name :'see',
                              xtype : 'buttoncolumn',
                              buttons : [
                                {
                                    buttonText : '查看',
                                    style : {
                                        color : 'red',
                                        font : '18px'
                                    }
                                },
                                {
                                    buttonText : '审核'
                                }
                        ]}
                        ]
                    });

 */
NS.define('NS.grid.Grid', {
	extend : 'NS.container.Panel',
    /**
     *@cfg {Boolean} paging 是否开启分页，默认为true。
     */
    /**
     * @cfg {Object} columnConfig 列配置
     */
    /**
     * @cfg {Object} modelConfig 域属性配置以及数据配置
     */
    /**
     * @cfg {Object} data 配置的grid要显示的数据，数据格式必须为 {data:[],success:true,count:13}
     */
    /**
     * @cfg {Object[]} columnData  列组件数据配置
     */
    /**
     * @cfg {Boolean} multiSelect  是否多选,默认为true
     */
    /**
     * @cfg {Boolean} disableSelect  禁止选择，默认为false
     */
    /**
     * @cfg {Boolean} lineNumber  是否显示行号,默认为false
     */
	/**
     * @cfg {String/Object} serviceKey  service对应的key值,该值需要配置在model中
     */
	/**
     * @cfg {NS.mvc.Model} proxy  grid的数据加载器,即{NS.mvc.Model}的一个实例
     */
    /**
     * @cfg {Boolean} checked  grid是否出现选择框
     */
    /**
     * @cfg {Array} fields 用于追加对象域属性的配置
     */
    /**
     * @cfg {Number} pageSize  grid 的每页数据量
     */
	/**
	 *构造函数
	 *@constructor
	 */
//	constructor : function() {
//		this.callParent(arguments);//调用父类构造函数
//	},
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping(
            'data',
            'fields',
            'modelConfig',
            'columnConfig',
            'columnData',
            'columnLines',
            'paging',
            'multiSelect',
            'disableSelect',
            'lineNumber',
            'defaultColumnConfig',//column默认配置属性
            'checked'//是否有选择框
        );
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents : function(){
        this.callParent();
        this.addEvents(
            /**
             * @event viewready grid的所有应该展示的界面展示完毕(包括表头，数据区的展示数据)
             * @param {NS.grid.Grid} grid
             */
            'viewready',
            /**
             * @event rowdbclick grid行被双击后触发的事件
             * @param {NS.grid.Grid} grid
             * @param {Object} rowData 行json数据
             * @param {HtmlElement} item  行的html对象
             * @param {Number} rowIndex  行序号
             * @param {NS.Event} event 事件对象
             */
            'rowdbclick',
            /**
             * @event rowclick grid行被单击后触发的事件
             * @param {NS.grid.Grid} grid
             * @param {Object} rowData 行json数据
             * @param {HtmlElement} item  行的html对象
             * @param {Number} rowIndex  行序号
             * @param {NS.Event} event 事件对象
             */
            'rowclick',
            /**
             * @event select grid行被选中后触发的事件
             * @param {NS.grid.Grid} grid
             * @param {Object} rowData 行json数据
             * @param {Number} rowIndex  行序号
             */
            'select',
            /**
             * @event beforeload grid自加载数据之前触发该事件
             * @param {NS.grid.Grid} grid
             * @param {Object} params 参数对象
             */
            'beforeload',
            /**
             * @event load grid自加载数据之后触发该事件
             * @param {NS.grid.Grid} grid
             * @param {Array[]} data 加载过来的数据
             */
            'load',
            /**
             * @event beforeedit grid编辑组件显示前触发
             * @param {NS.grid.Grid} grid
             * @param {Object} params 参数对象
             */
            'beforeedit',
            /**
             * @event beforeedit grid编辑结束后触发
             * @param {NS.grid.Grid} grid
             * @param {Object} params 参数对象
             */
            'afteredit'

        );
    },
    /**
     * 当添加viewready事件的时候调用
     * @private
     */
    onViewready : function(){
        this.component.on('viewready',function(event, element){
            this.fireEvent('viewready', this);
        },this);
    },
    /**
     * 当行被双击的时候触发
     * @private
     */
    onRowdbclick : function(){
        this.component.on('itemdblclick',function(view, record,item,rowindex,e){
            NS.Event.setEventObj(e);
            this.fireEvent('rowdbclick', this,record.getData(),item,rowindex,NS.Event);
        },this);
    },
    /**
     * 当行被双击的时候触发
     * @private
     */
    onRowclick : function(){
        this.component.on('itemclick',function(view, record,item,rowindex,e){
            NS.Event.setEventObj(e);
            this.fireEvent('rowclick', this,record.getData(),item,rowindex,NS.Event);
        },this);
    },
    /**
     * 当行被选中后触发
     * @private
     */
    onSelect : function(){
        this.component.on('select',function(rowmodel, record,rowindex){
            this.fireEvent('select', this,record.getData(),rowindex);
        },this);
    },
    /**
     * 标识Grid被编辑之前触发
     * @private
     */
    onBeforeedit : function(){
        this.component.on('beforeedit',function(editing,e){
            var params = {
                name :e.column.dataIndex,
                rowIndex :e.rowIdx,
                colIndex :e.colIdx,
                originalValue :e.originalValue,
                value :e.value,
                data :e.record.getData()
            };
            this.record = e.record;
            this.fireEvent('beforeedit', this,params);
        },this);
    },
    /**
     * 标识Grid被编辑之前触发
     * @private
     */
    onAfteredit : function(){
        this.component.on('edit',function(editing,e){
            var params = {
                name :e.column.dataIndex,
                rowIndex :e.rowIdx,
                colIndex :e.colIdx,
                value :e.value,
                originalValue :e.originalValue,
                data :e.record.getData()
            };
            this.record = e.record;
            this.fireEvent('afteredit', this,params);
        },this);
    },
    /**
     * 唯有在进入编辑状态的时候使用
     * @param fieldname
     * @param value
     */
    setEditRowValue : function(fieldname,value){
//        if(!record){
//           var records = this.component.getSelectionModel().getSelection();
//           if(records.length == 1){
//              var record = records[0];
//              this.record = record;
//           }else{
//              return;
//           }
//        }
        if(!this.record){return;}
        this.record.set(fieldname,value)
        this.record.save();
        this.record.commit();
        //this.component.store.fireEvent('update',this.component.store,this.record,Ext.data.Model.COMMIT);
    },
    /**
     * 唯有在进入编辑状态的时候使用
     * @param fieldname
     * @param value
     */
    getEditRowValue : function(fieldname){
        return this.record.get(fieldname);
        //this.component.store.fireEvent('update',this.component.store,this.record,Ext.data.Model.COMMIT);
    },
	/**
	 * 初始化组件配置参数数据
	 * @param {Object} config 组件配置参数对象
     * @private
	 */
	initData : function(config) {
		this.config = config;
		this.initLoader();
		this.initColumns();
        this.initStore();// 创建Store

	},
    /**
     * 初始化加载遮罩
     */
    initLoadMask : function(){
        this.loadmask = new Ext.LoadMask(this.component, {
            msg : "数据加载中,请稍等......"
        });
    },
	/**
	 * 初始化数据加载器loader
	 */
	initLoader : function(){
		this.key = this.config.serviceKey;//service对应的key值
		this.loader = this.config.proxy;//数据请求代理
	},
	/***************************************************************************
	 * 初始化组件
     * @private
	 */
	initComponent : function() {
		this.initGrid.apply(this,arguments);// 初始化grid
        this.initLoadMask();//初始化加载遮罩
	},
	/***************************************************************************
	 * 初始化Grid
	 * @private
	 */
	initGrid : function(config) {
		var me = this,
		    store = this.store,
		    columns = this.columnManager.columns,
            pbar,
		    data = this.config.columnData;
		var basic = {// 为其添加几个参数
			store : store,
			columns : columns,
			layout : 'fit',
            //disableSelection : config.disableSelect,//禁止选择
			//selModel : Ext.create('Ext.selection.CheckboxModel'),
			dockedItems: []
		};
        if(config.checked){
        	this.selModel = Ext.create('Ext.selection.CheckboxModel');
            NS.apply(basic,{selModel : this.selModel});
            basic.selModel.setSelectionMode(config.multiSelect === true? 'MULTI' : 'SINGLE');
        }
		if(config){
		   NS.apply(basic,config)
		}

		if(!this.config.hasOwnProperty('paging') || this.config.paging){//判断是否开启分页工具栏
            pbar = this.initPagingBar();//初始化分页栏
		    basic.dockedItems.push(pbar);
		}
		
		this.component = Ext.create('Ext.grid.Panel', basic);// 创建grid
	},
	/***************************************************************************
	 * 设置表头的样式  --此方法只能在grid组件初始化完成之后才能够被调用
	 * 
	 * @param {Number/String} columninfo 行信息
	 * @param {String} style 属性
	 * @param {String} value 值
	 */
	setHeadStyle : function(columninfo, style, value) {
		var grid = this.component, // 所有columns信息
		column = this.getExtColumn(columninfo);
		if (column) {
			var el = column.getEl();
			el.setStyle(style, value);
		}
	},
	/**
	 * 设置某单元格样式 --此方法只能在grid组件初始化完成之后才能够被调用
	 * 
	 * @param {HtmlElement/String/Number} rowinfo 行信息  建议用Number
	 * @param {Number/String}  columninfo 列信息 列名/列的属性名
	 * @param {String} style 样式
	 * @param {String} value 值
	 */
	setCellStyle : function(rowinfo, columninfo, style, value) {
		var grid = this.component, 
		    view = grid.getView(), 
		    row = view.getNode(rowinfo), // 获取当前行Html元素
		    column = this.getExtColumn(columninfo), // 列对象
		    cell;// 单元格元素
		cell = Ext.fly(row).down(column.getCellSelector());
		Ext.fly(cell).setStyle(style, value);
	},
	/***************************************************************************
	 * 设置某一列的样式信息--此方法只能在grid组件初始化完成之后才能够被调用
	 * 
	 * @param {Number/String}  columninfo 代表某一列的标识，属性名--name/列号--Number
	 * @param {String} style  样式
	 * @param {String} value  值
	 */
	setColumnStyle : function(columninfo, style, value) {
		var grid = this.component,
		    store = this.store, 
		    view = grid.getView(), 
		    count = store.getCount(), // 行数量
		    column = this.getExtColumn(columninfo), 
		    row, 
		    cell;
		for (var i = 0; i < count; i++) {
			row = view.getNode(i);// 获取当前行Html元素
			cell = Ext.fly(row).down(column.getCellSelector());//查找cell DOM节点
			Ext.fly(cell).setStyle(style, value);//设置cell节点样式
		}
	},
	/**
	 * 设置行样式   --此方法只能在grid组件初始化完成之后才能够被调用
	 * @param {String/Number}  rowinfo  建议用Number
	 * @param {String} style 样式属性
	 * @param {String} value 值
	 */
	setRowStyle : function(rowinfo, style, value) {
		var grid = this.component;
		var view = grid.getView();
		var row = view.getNode(rowinfo);// 获取当前行Html元素
		if (row) {
			Ext.fly(row).setStyle(style, value);
		}
	},
	/**
	 * 设置grid分页数据量
	 * 
	 * @param {Number}  number
	 */
	setPageRowCount : function(number) {
		var store = this.store;
		store.pageSize = number;
		store.load();
	},
//	/***
//	 * 设置Grid的数据过滤条件
//	 */
//	setFilter : function(){
//		var args = arguments;//参数数组
//	    var proxy = this.store.getProxy();
//	    if(args.length == 2){
//	       proxy.setExtraParam.apply(proxy,arguments);
//	    }else if(args.length == 1){
//	       for(var i in args[0]){
//	          proxy.setExtraParam(i,args[0][i]);
//	       }
//	    }
//	},
//	/***
//	 * 执行Grid的数据过滤
//	 */
//	runFilter : function(){
//	    this.getData(this.getParams());
//	},
	/***************************************************************************
	 * 根据传递的行号设定该列被选中,传递参数 为1个或者多个行号
	 * 
	 * @param {Number..|Object...} rowInfo....
	 */
	setSelectRows : function() {
		var grid = this.component;// 获取grid
		var selectModel = grid.getSelectionModel();// 获取选中对象
		NS.Array.each(arguments, function(obj) {
					selectModel.select(obj,true);
				});
	},
	/**
	 * 设置选中或者将选中的设置不选中的方法(扩展至setSelectRows)
	 * @param arg 参数类型为number或者array 是要选中或不选总的行数
	 * @param flag true表示选中指定参数的行，false则不选中指定参数的行
	 */
	setUnOrSelectRows:function(arg,flag){
		var grid = this.component;// 获取grid
		var selectModel = grid.getSelectionModel();// 获取选中对象
		if(NS.isNumber(arg)){//如果是数字
			selectModel.select(arg,flag);
		}else if(NS.isArray(arg)){//如果是数组
			NS.Array.each(arg, function(obj) {
				selectModel.select(obj,flag);
			});
		}
	},
	/***************************************************************************
	 * 获得当列所有选中列的数据
	 * 
	 * @return {Object[]}
	 */
	getSelectRows : function() {
		var array = [];
		var grid = this.component;// 获取grid
		var records = grid.getSelectionModel().getSelection();// 获取选中行数据
        NS.Array.each(records, function(record) {
					array.push(record.data);
				});
		return array;
	},
    /**
     * 获取当前选中列的行数
     * @return {Number}
     */
    getSelectCount : function(){
        var grid = this.component;// 获取grid
        var records = grid.getSelectionModel().getSelection();// 获取选中行数据
        return records.length;
    },
	/***************************************************************************
	 * 获取 指定的行，指定的列的单元格的数据
	 * 
	 * @param {Number} rowIndex 行号
	 * @param {Number/String} columninfo 列号或者列属性名
	 */
	getCellData : function(rowIndex, columninfo) {
		var grid = this.component, 
		    store = this.store,
		    record = store.getAt(rowIndex),
		    column = this.getExtColumn(columninfo);
		return record.get(column.dataIndex);
	},
	/***************************************************************************
	 * 设置某一个单元格的值{可能需要在设置值之前进行格式化}
	 * 
	 * @param {Number} rowIndex  行号/行信息
	 * @param {Number/String}  columninfo  列号或者列属性
	 * @param {String} value  需要设置的值
	 */
	setCellData : function(rowIndex, columninfo, value) {
		var grid = this.component, 
		    store = this.store,
		    record = store.getAt(rowIndex),
		    column = this.getExtColumn(columninfo);
		if(record)record.set(column.dataIndex,value);
	},
	/***************************************************************************
	 * 获取当前store 的数据总量
	 * @return {Number}
	 */
	getCount : function() {
		return this.store.getCount();
	},
	/***
	 * 获取所有修改，新增的数据的总数
	 * @param {String} type insert/delete/update
	 * @return {Number}
	 */
	getModifyCount : function(type){
	    return this.getModify(type).length;
	},
	/***************************************************************************
	 * 增加一列，譬如审核列 ---该方法必须在grid创建前被执行
	 * 
	 * @param {Number/String} columninfo 行号/列属性
	 * @param {NS.column.Column} column
	 */
	addColumn : function(columninfo,column) {
		var columns = this.component.columns;
//		this.;
	},
	/***
	 * 根据传递的行号设置其图片信息
	 * @param {Number/String} columninfo 列号或者列属性名
	 * @param {Number} rowIndex
	 * @param {String} pic
	 * @param {Function} callback
	 */
	setPictureByRow : function(columninfo,rowIndex,pic,callback){
	    var gird = this.component,
	        view = grid.getView(),
	        column = this.getExtColumn(columninfo),
	        row = view.getNode(rowIndex);// 获取当前行Html元素
	        
	    cell = Ext.fly(row).down(column.getCellSelector());
		Ext.fly(cell).setStyle('background-image', 'url('+pic+")");
		Ext.fly(cell).addListener('click',callback);
	},
	/***
	 *   通过传递的列属性或者列号信息获取Column对象
	 * @param {Number/String} columninfo 列索引，也可以是列的name
     * @return {Ext.grid.column.Column}
     * @private
     */
	getExtColumn : function(columninfo){
	    var columns = this.component.columns,cproxy, // 列对象数组
		column, // 列对象
		cell;// 单元格节点对象
		if (typeof columninfo == 'number') {
			column = columns[columninfo];
		} else if (typeof columninfo == 'string') {
			NS.Array.each(columns, function(c) {
						if (c.dataIndex == columninfo) {
							column = c;
							return false;
						}
					});
		}
        return column;
	},
    /***
     * @private   通过传递的列属性或者列号信息获取Column对象
     * @param {Number/String} columninfo 列索引，也可以是列的name
     * @return {NS.grid.Column}
     */
    getColumn : function(columninfo){
        var extcolumn = this.getExtColumn(columninfo),cproxy;
        return NS.util.ComponentInstance.getInstance(extcolumn);
    },
    /**
     * 获取一列的编辑器
     * @param {String/Number} columninfo 列索引，也可以是列的name
     * @return {NS.form.field.BaseField}
     */
    getColumnEditor : function(columninfo){
        var column = this.getColumn(columninfo);
        return column.getEditor();
    },
	/***************************************************************************
	 * 设置列的正则表达式校验
	 * 
	 * @param {String/Number} columninfo  列号或者列属性
	 * @param  regex  正则表达式
	 * @param {String} regexText  正则表达式校验信息
	 */
	setColumnValidate : function(columninfo, regex, regexText) {
		var column = this.getExtColumn(columninfo);
		var com = column.getEditor()||column.editor;
		com.regex = regex;
		com.regexText = regexText;
	},
	/***************************************************************************
	 * 设置Column的格式化字符串
	 * 
	 * @param {Number/String} columninfo 列属性/列号
	 * @param {String} format 格式化字符串
	 */
	setColumnFormat : function(columninfo, format) {
		var column = this.getExtColumn(columninfo);
		column.format = format;
	},
	/***************************************************************************
	 * 设置Column属性类型--String/Integer/Boolean/Collection
	 * 
	 * @param {Number/String} columninfo 列属性/列号
	 * @param {String} type   类型
	 */
	setColumnType : function(columninfo, type) {
		var column = this.getExtColumn(columninfo);
		column.type = type;
	},
	/***
	 * 根据列号，列组类型，列组件数据设置---列编辑组件
	 * @param {Number/String} columninfo  列属性/列号
	 * @param {String}  comType
	 * @param {Object} data
	 */
	setColumnData : function(columninfo,comType,data){
	    var column = this.getExtColumn(columninfo),
	        editor = column.getEditor();
	        if(editor == 'combobox'){
	           editor.getStore().loadData(data);
	        }else{
	           
	        }
	},
    setColumnHidden : function(columnInfo,isHidden){
        var column = this.getExtColumn(columnInfo);
        if(isHidden){
            column.hide();
        }else{
            column.show();
        }
    },
	/***
	 * 
	 */
	setColumnDataOnTree : function(columninfo,data){
	
	},
	/**
	 * 获取Column数据类型
	 * @param {Number/String}  columninfo 列属性/列号
	 * @return   {String}
	 */
	getExtColumnType : function(columninfo) {
		var column = this.getExtColumn(columninfo);
		return column.type;
	},
	/**
	 * 向列表中插入一行数据，默认插入第一行
	 * @param {Object} rowData 行数据---默认为｛key:value,...｝形式
	 */
	addRow : function(rowData,index,startEditColumnIndex) {
		var grid = this.component,
		    store = this.store, 
		    index = index||0,
		    model = store.model,
		    cellEditor = grid.plugins[0] || undefined,// 获取表编辑插件
		    record;
		if (rowData) {
			record = model.create(rowData);
		} else {
			record = model.create();
		}
		store.insert(0+index, record);
		if(cellEditor){
		   cellEditor.cancelEdit();// 取消编辑
		   cellEditor.startEdit(record, startEditColumnIndex||1);
		}
	},
	/**
	 * 想列表中插入一行或者多行数据，默认插入第一行以及往后
	 * 
	 * @param {Object[]} rowsData []  json数组
	 */
	addRows : function(rowsData) {
		var me = this;
        NS.Array.each(rowsData, function(rowData,index) {
						me.addRow(rowData,index);
					});
	},
	/***************************************************************************
	 * 向列表中指定位置--插入一行数据，
	 * 
	 * @param {Object} rowData
	 * @param {Number} rowIndex 行号
	 */
	insertRow : function(rowData, rowIndex,colIndex) {
		this.addRow(rowData,rowIndex,colIndex);
	},
	/***************************************************************************
	 * 想列表中的指定位置插入一行或者多行数据
	 * 
	 * @param {Object} rowsData []  多行数据集合
	 * @param {Number}  rowIndex  要插到的行号
	 */
	insertRows : function(rowsData, rowIndex,colIndex) {
		var me = this;
        NS.Array.each(rowsData, function(rowData,index) {
					 me.insertRow(rowData,rowIndex+index,colIndex);
				});
	},
	/***************************************************************************
	 * 删除给定行号的行数据
	 * 
	 * @param {Number} rowIndex
	 */
	deleteRow : function(rowIndex) {
        var record = this.store.getAt(rowIndex);
		this.store.remove(record);
	},
	/***************************************************************************
	 * 删除给定的多个行号的数据
     *   deleteRows(1,2,3,4);
	 * @param  {Number/...} rowindex... 多个行号参数
	 */
	deleteRows : function() {
		var me = this,
		    array = arguments;
        NS.Array.each(array, function(index) {
					me.deleteRow(index);
				});
	},
    /**
     * 移除所选中的列
     */
    removeSelectRows : function(){
        var grid = this.component,
            store = grid.getStore(),
            sm = grid.getSelectionModel();
        store.remove(sm.getSelection());
    },
	/***************************************************************************
	 * 清空store中的数据
	 */
	clear : function() {
		this.store.removeAll();
	},
	/***
	 * 根据提供的行号获取--行数据
	 * @param {Number} rowIndex
	 * @return {Object}
	 */
    getRow : function(rowIndex){
        var record = this.store.getAt(rowIndex);
        return record.getData();
    },
    /***
     * 根据提供的多个行号获取--一行或者多行数据
     * @param  {Number...} rowNumber...
     *
     *          var data = getRows(1,2,3,4);
     *
     * @return {Object[]}
     */
    getRows : function(){
        var me = this,
            array = [];
        NS.Array.each(arguments,function(index){
            array.push(me.getRow(index));
        });
        return array;
    },
    /**
     * 根据提供的域属性{Field}和值{Value}查询行对象
     */
    findRow : function(field,value){
    	var me = this,
    		store = this.component.getStore(),
    		matches=[];
    	store.each(function(item,index,length){
    		if(item.get(field) == value){
    			matches.push(item.getData());
    		}
    	});
    	return matches;
    },
    /**
     * 根据提供的域属性{Field}和值{Value}查询匹配到的所有行索引
     */
    findRowIndex : function(field,value){
    	var me = this,
		store = this.component.getStore(),
		matches=[];
		store.each(function(item,index,length){
			if(item.get(field) == value){
				matches.push(index);
			}
		});
		return matches;
    },
    /**
     * 通过id获取Row对象
     * @param id
     */
    getRowById : function(id){
    	var store = this.component.getStore();
    	store.each(function(item,index,length){
			if(item.get("id") == id){
				return item.getData();
			}
		});
    },
    /**
     * 通过id获取行号
     * @param id
     */
    getRowIndexById : function(id){
    	var store = this.component.getStore();
    	var number;
    	store.each(function(item,index,length){
			if(item.get("id") == id){
			   number = index;
			}
		});
    	return number;
    },
    /**
     * 获取第一个选中记录的索引
     */
    getFirstSelectIndex : function(){
        var records = this.component.getSelectionModel().getSelection();//获取选中的行的对象
        var collection = this.component.store.data;
        if(records.length>0){
            return collection.indexOf(records[0]);
        }else{
            return -1;
        }
    },
    /**
     *获取所有被选中的行的索引数组
     * @return {Array}
     */
    getAllSelectIndex : function(){
        var records = this.component.getSelectionModel().getSelection();//获取选中的行的对象
        var collection = this.component.store.data;
        var array = [];
        NS.Array.forEach(function(record){
            array.push(collection.indexOf(record));
        });
        return array;
    },
    /***
     * 获取store中的所有数据
     * @return {Object[]}
     */
    getAllRow  : function(){
        var array = [];
        NS.Array.each(this.component.store.data.items,function(record){
            array.push(record.data);
        });
       return array;
    },
    /***
     * 获取修改过的所有数据集
     * @param {String} type 变动数据类型 insert/update/delete
     * @return {Object[]}
     */
    getModify : function(type){
       var store = this.store,
           array = [];
           if(type == 'update'){
              records = store.getUpdatedRecords();
           }else if(type == 'insert'){
              records = store.getNewRecords(); 
           }else if(type == 'delete'){
              records = store.getRemovedRecords(); 
           }else{
           	  records = store.getModifiedRecords();
           }
        NS.Array.each(records,function(record){
           array.push(record.data);
       });
       return array;
    },
	/***************************************************************************
	 * 初始化Store
     * @private
	 */
	initStore : function() {
		var config = this.config.modelConfig,data;
        if(config){
           data = config.data;
        }else{
           data = this.config.data
        }
        this.queryParam = {};
        var basic = {
            fields : this.getFields(this.config),
            proxy : {
                type : 'memory'
            },
            pageSize : this.config.pageSize||25,
            autoLoad : false//是否自动加载数据
        };
//        if(data && data.data){
//            NS.apply(basic,{data:data.data,totalCount : data.count});
//        }else if(config && config.data){
//            NS.apply(basic,{data : config.data});
//        }
        
		this.store = Ext.create('Ext.data.Store', basic);
		if(data && data.data){
//            NS.apply(basic,{data : data.data||[],totalCount : data.count||0});
			this.loadData(data,1);
        }
	},
    /***
     * 获取域对象（用于store的fields）
     * @param {Object} config 配置对象
     * @return {Array}
     * @private
     */
    getFields : function(config){
        var fields = [],data = config.columnData||[],fieldsappend = config.fields||[],item;
        var getType = function(type){
            switch(type){
                case 'String' : return "string";
                case 'Long'   : return "int"
            }
        };
        for(var i= 0,len=data.length;i<len;i++){
            item = data[i];
            fields.push({name : item.name});
        }
        for(var i= 0,len = fieldsappend.length;i<len;i++){//将追加的域属性配置进模型中
            item = fieldsappend[i];
            if(NS.isString(item)){
                fields.push({name : item});
            }else if(NS.isObject(item)){
                fields.push(item);
            }
        }
        return fields;
    },
	/***************************************************************************
	 * 初始化Column
     * @private
	 */
	initColumns : function() {
          var  basic = {
		    data:this.config.columnData,
			columnConfig : this.config.columnConfig,
            lineNumber : this.config.lineNumber,
            defaultConfig : this.config.defaultColumnConfig//column默认配置
		};
		this.columnManager = new NS.grid.ColumnManager(basic);
	},
    /**
     * 获取用于{Ext.grid.Panel}显示的数据,并且刷新界面
     */
     loadData: function(json,page){
        var store = this.store;
        if(NS.isObject(json)){
            if(json.success == false){
                NS.error({
                    sourceClass : 'NS.grid.Grid',
                    sourceMethod : 'loadData',
                    msg : '加载数据异常，数据不是grid所需要的格式！'
                });
                store.loadRawData([]);
                return;
            }
            store.loadRawData(json.data);
//          store.loadData(json.data);
            store.totalCount = json.count;
        }else if(NS.isArray(json)){
            store.loadRawData(json);
//          store.loadData(json);
            store.totalCount = json.length;
        }
        store.currentPage  = page||1;
        this.fireEvent('load',json);
        if(this.pbar) this.pbar.onLoad();
    },
    /***
     * 讲数据加载器，注册进入Grid组件中
     * @private
     * @param key 在模型层配置的service的key值
     * @param component NS.mvc.Model
     */
    registerDataLoader : function(key,component){
        this.loader = component;
        this.key = key;
    },
    /**
     * 根据传递的请求参数，加载数据
     * @param {Object} params 请求参数
     */
    load : function(params){
        var baseParams = params||{};
        var returnParams = this.fireEvent('beforeload',this,baseParams);
        this.getData(returnParams||(baseParams||{}),1);
        if(this.selModel){this.selModel.setLocked(false);}
    },
    /**
     * 刷新当前结果集
     */
    refresh : function(){
        var page = this.store.currentPage;
        this.getData(this.queryParams,page);

    },
    /**
     * 获取用于grid显示的数据, 并加载显示
     * @private
     */
    getData : function(params,page){
        if(this.component.rendered){
            this.loadmask.show();
        }
        this.component.getSelectionModel().deselectAll(true);//全部反选
        var me = this,key = NS.clone(this.key),basic = {},pagesize = this.store.pageSize,endParams = {};
        this.queryParams = params;

        if(NS.isString(key)){
           endParams = params;
           basic = {key : key,params : endParams||{}}
        }else if(NS.isObject(key)){
           if(key.params){
               NS.apply(endParams,key.params);
               NS.apply(endParams,params||{});
           }else{
//               key.params = params ||{};
               endParams = params || {};
           }
           basic = {key : key.key,params : endParams};
        }
        if(!basic.params)basic.params = {};
        if(!basic.params.start){
            basic.params.start = 0*pagesize;
            basic.params.limit = 1*pagesize;
        }
        this.loader.callService(basic,function(json){
            this.loadData(json[basic['key']],page);
            this.loadmask.hide();
        },this);
    },
    /***
     * 初始化分页栏
     * @private
     */
    initPagingBar : function(){
        var me = this,ome = this;
        this.pbar = Ext.create('Ext.toolbar.Paging',{
            store : this.store,
            dock: 'bottom',
            displayInfo: true,
            moveFirst : function(){
                var page = 0,
                    baseParams = {
                    start : page*this.store.pageSize,
                    limit : this.store.pageSize
                    },
                    params = me.getParams();
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page+1);
            },
            movePrevious : function(){
                var page = this.store.currentPage- 2,
                    baseParams = {
                        start : page*this.store.pageSize,
                        limit : this.store.pageSize
                    },
                    params = me.getParams();
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page+1);
            },
            moveNext : function(){
                var page = this.store.currentPage,
                    baseParams = {
                        start : page*this.store.pageSize,
                        limit : this.store.pageSize
                    },
                    params = me.getParams();
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page+1);
            },
            moveLast : function(){
                var store = this.store,
                    totalCount =  store.getTotalCount(),
                    page = Math.ceil(totalCount / store.pageSize),
                    params = me.getParams();
                var baseParams = {
                    start : (page-1)*this.store.pageSize,
                    limit : this.store.pageSize
                };
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page);
            },
            doRefresh : function(){
                    var params = me.getParams(),
                        page = this.store.currentPage,
                    baseParams = {
                        start : (page-1)*this.store.pageSize,
                        limit : this.store.pageSize
                    };
                this.store.currentPage = page;
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page);
            },
            // private
            onPagingKeyDown : function(field, e){
                var me = this,
                    k = e.getKey(),
                    pageData = me.getPageData(),
                    increment = e.shiftKey ? 10 : 1,
                    pageNum,
                    baseParams,
                    params;

                if (k == e.RETURN) {
                    e.stopEvent();
                    pageNum = me.readPageFromInput(pageData);
                    if (pageNum !== false) {
                        pageNum = Math.min(Math.max(1, pageNum), pageData.pageCount);
                        if(me.fireEvent('beforechange', me, pageNum) !== false){
                            //me.store.loadPage(pageNum);//更改ext默认行为
                                baseParams = {
                                    start : (pageNum-1)*this.store.pageSize,
                                    limit : this.store.pageSize
                                },
                                params = ome.getParams();
                                NS.applyIf(baseParams,params);
                                ome.getData(baseParams,pageNum);
                        }
                    }
                } else if (k == e.HOME || k == e.END) {
                    e.stopEvent();
                    pageNum = k == e.HOME ? 1 : pageData.pageCount;
                    field.setValue(pageNum);
                } else if (k == e.UP || k == e.PAGE_UP || k == e.DOWN || k == e.PAGE_DOWN) {
                    e.stopEvent();
                    pageNum = me.readPageFromInput(pageData);
                    if (pageNum) {
                        if (k == e.DOWN || k == e.PAGE_DOWN) {
                            increment *= -1;
                        }
                        pageNum += increment;
                        if (pageNum >= 1 && pageNum <= pageData.pageCount) {
                            field.setValue(pageNum);
                        }
                    }
                }
            }
        });
        return this.pbar;
    },
    /**
     * 获取当前的可变参数
     * @private
     * @return {Object} 当前store的查询参数
     */
    getParams : function(){
        return this.queryParams||{};
    },
    /**
     * 获取当前的查询参数
     */
    getQueryParams : function(){
        var key = this.key,params = this.getParams(),ret = {};
        if(NS.isObject(key)){
            NS.apply(ret,params,key.params||{});
        }else{
            NS.apply(ret,params);
        }
        return ret;
    },
    /**
     * 获取显示的column对象name集合 ,用于导出
     * @private
     */
    getShowColumnNames : function(){
        var columns = this.component.columns;
        var array = [];
        Ext.each(columns,function(g){
            if(g && g.dataIndex!="" && g.dataIndex != null && g.hidden == false)
                array.push(g);
        });
        return array;
    }
});
