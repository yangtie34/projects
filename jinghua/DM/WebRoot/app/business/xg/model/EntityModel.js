/**
 * 基础模板页面[xuebl]
 * 	用于比较简单的 新增form、修改form、删除数据、单字段查询...
 */
NS.define('Business.xg.model.EntityModel', {
	
	extend : 'Business.xg.model.BasicModel',
	
	/**
	 * 初始化数据
	 * TODO
	 */
	initData : function(){
        this.callService([
            {key:'queryTableHeader',params:this.getEntityParams()},
            {key:'queryTableData',  params:this.getGridParams()}
            ], function (data) {
        		this.initComponent(data);
        		this.initMask();
            },this);
	},
	
	initComponent: function(data){
		this.afterInit();
		//grid
        var _grid = this.initGrid(data);
        if(_grid){
        	this.grid = _grid;
            this.bindGridEvents(); //如果上一个绑定事件还不能满足，再使用这个
        }
        //tbar
		var _tbar = this.initTbar();
		if(_tbar){
			this.tbar = _tbar;
	        this.bindTbarEvents(); //如果上一个绑定事件还不能满足，再使用这个
		}
		//tbar 第二行
		var _tbar2 = this.addTbarRow();
        if(_tbar2){
        	var ary = [],
        		rows= this.addTbarRow(),
        		len = rows.length;
        	if(this.tbar){ //如果tbar存在，显示
        		ary.push(this.tbar);
        	}
        	for(var i=0; i<len; i++){ //循环组装第二、三...行
        		ary.push(rows[i]);
        	}
        	var tbarCtn = new NS.container.Container({
        		items : ary
        	});
        	this.tbar = tbarCtn;
        }
		//tools
		var _tools = this.initTools();
		if(_tools){
			this.tools = _tools;
		}
        this.initPage();
    },
	/**
	 * 初始化后
	 * TODO
	 */
	afterInit  : NS.emptyFn,
	/**
	 * 初始化基本form items
	 * @private
	 */
	initBaseFormItems : function(tableHeader){
		//通过'sx'查找table里的各个字段名称
		this.defaultFormItems = NS.Array.pluck(tableHeader, 'sx');
	},
	/**
     * 基本form items
     * @private
     */
    getBaseFormItems : function(){
    	return NS.clone(this.defaultFormItems)||[];//其中[]是使用者可覆盖的，这时候请将||前面的去除。默认是两列多行
    },
    
    /**
     * 初始化页面
     * TODO
     */
    initPage : function(){
    	var cmpAry = [];  //组件数组
    	if(this.menuName!=''){
    		cmpAry.push(Business.component.PageHeader.getInstance(this.getMenuName()));
    	}
		if(this.tbar){
    		cmpAry.push(this.tbar);
    	}
    	if(this.tools){
    		cmpAry.push(this.tools);
    	}
    	if(this.grid){
    		cmpAry.push(this.grid);
    	}
    	if(this.bottomName!=''){
    		cmpAry.push(Business.component.PageBottom.getInstance(this.getBottomName()));
    	}
    	this.page = new NS.container.Container({
    		border: false,
    		cls   : 'xg-common_container',
			items : cmpAry,
			autoScroll : true
		});
    	this.page.on('afterrender',function(){
    		this.pageAfterRender();
		},this);
        this.setPageComponent(this.page);
    },
    
    /***************************************** grid **********************************************/
    /**
     * 初始化grid
     * @private
     */
    initGrid: function (data) {
    	var tableHeader = data['queryTableHeader'], //表头数据
    		tranData    = this.tranData = NS.E2S(tableHeader); //转换标准的表头数据
			tableData   = data['queryTableData'], //grid数据
			colunms     = this.addColumnConfig() || [],
			config      = this._updateGridConfig(), //修改Grid的配置
			events		= this.addGridEvents() || {};
		//初始化基本form items
		this.initBaseFormItems(tableHeader);
		
		//grid 第二版配置
		var _gridConfig = this.getGridConfig() || {};
		NS.apply(colunms, _gridConfig['colunms']); //列
		NS.apply(events, _gridConfig['events']);   //事件
		delete _gridConfig['colunms'];
		delete _gridConfig['events'];
	    
        //Grid展现必须的配置项
        var mustCfg = {
	    		columnData   : tranData,
	            columnConfig : colunms,
	            modelConfig  : {
	                data : tableData
	            },
	    		serviceKey   : 'queryTableData',
	    		proxy        : this.model
	        };
        NS.apply(mustCfg, config);
        var grid = new NS.grid.SimpleGrid(mustCfg);
        grid.bindItemsEvent(events);
        return grid;
    },
    /**
     * grid 配置 {第二版}
     * {
     *  border : '',
     * 	colunms: [],
     *  events : {}
     *  }
     */
    getGridConfig : NS.emptyFn,
    /**
     * grid "列"配置
     * TODO
     */
    addColumnConfig: NS.emptyFn,
    /**
     * grid 绑定事件
     * TODO 
     */
    addGridEvents  : NS.emptyFn,
    /**
     * 一般不重写，实在满足不了再用
     * @private
     */
    bindGridEvents : NS.emptyFn,
    /**
     * grid 配置
     * 1. {}
     * 2. [] 如果 array[1]==true, 清除所有原配置，直接使用array[0]这个配置
     * TODO
     */
    addGridConfig  : [],
    /**
     * 不能重写，更新 serviceConfig
     * @private
     */
    _updateGridConfig : function(){
    	var ary = this.addGridConfig;
    	if(ary instanceof Array && ary.length>1 && ary[1]==true){ //如果 array[1]==true, 清除所有原配置，直接使用这个配置
    		return config;
    	}else{
    		var _config = ary,
    			_basic = {
//		            plugins: [new NS.grid.plugin.HeaderQuery()], //总出错
		            border : false,
		            autoScroll : true,
		            //lineNumber : true,
		            checked	   : true,
		            multiSelect: true
		        };
    		for(var name in _config){
    			_basic[name] = _config[name];
    		}
    		return _basic;
    	}
    },

    /***************************************** tbar **********************************************/
	/**
	 * 初始化tbar，广义的tbar，即是一个container
     * @Override
	 */
    initTbar: function () {
        //第一版配置
        var basicCfg = this.getBasicTbarConfig() ||{}, //基础配置
        	config   = this.addTbarConfig()||{}, //tbar config {第一版}
        	events   = this.addTbarEvents()||{}, //tbar 事件 {第一版}
        	_tbarCfg = this.getTbarConfig(); //第二版
        if(_tbarCfg != undefined){ //tbar 事件 {第二版}
        	var _events = _tbarCfg['events'];
        	if(_events != undefined){
        		events = _events;
        		delete _tbarCfg['events'];
        	}
	        NS.apply(config, _tbarCfg); //tbar config {第二版}
        }
        NS.apply(basicCfg, config);
        
        var tbar = new NS.toolbar.Toolbar(basicCfg);
        tbar.bindItemsEvent(events);
        
        return tbar;
    },
    /**
     * Tbar 基础配置 {第二版}
     * @private
     */
    _basicTbarConfig : {},
    /**
     * Tbar 基础配置 {第二版}
     */
    getBasicTbarConfig : function(){
    	return this._basicTbarConfig;
    },
    /**
     * Tbar 配置 简化配置项与事件绑定 {第二版}
     * TODO
     * {
     *  border :'',
     * 	items  : [],
     *  events : {}
     * }
     */
    getTbarConfig : NS.emptyFn,
    /**
     * tbar config {第一版}
     * TODO
     */
    addTbarConfig  : function(){
    	return {items: [
	    	             {xtype: 'button', text: '新增', name: 'baseAdd',   iconCls:'page-add'},
					'-', {xtype: 'button', text: '修改', name: 'baseUpdate',iconCls:'page-update'},
					'-', {xtype: 'button', text: '删除', name: 'baseDelete',iconCls:'page-delete'},
					'-', new NS.grid.query.SingleFieldQuery({
						   	data : this.tranData,
						   	grid : this.grid
						 })
				]};
    },
    /**
     * tbar 绑定事件 {第一版}
     * TODO 
     */
    addTbarEvents  : function(){
    	return {
				'baseAdd'   : {event: 'click', fn: this.showAddForm,    scope: this},
		        'baseUpdate': {event: 'click', fn: this.showUpdateForm, scope: this},          
		        'baseDelete': {event: 'click', fn: this.showDeleteIds,  scope: this}
		       };
    },
    /**
     * 一般不重写，实在满足不了再用
     * @private
     */
    bindTbarEvents : NS.emptyFn,
    /**
     * addTbarRow
     * 需要时添加tbar DIV 中的行 例如：说明...
     * @return array
     * TODO
     */
    addTbarRow : NS.emptyFn,
    

    /***************************************** 小工具栏tools **********************************************/
    /**
     * 初始化小工具栏 {学号、姓名、身份证号查询框，小统计}
     */
    initTools : NS.emptyFn,
    

    /**************Form*******************************************/
	//TODO form config
	formConfig : {
		width  : 350,
	    height : 280,
	    columns: 1
//		labelWidth : 100,
//		labelAlign : 'right'
	},

    /**************AddForm*****************/
    /**
     * _新增form
     */
    showAddForm : function(){
		var basicCfg = {
    		title   : '新 增',
            formType: 'add',//默认form类型为add
    		data    : this.tranData,
    		items   : this.addFormItems(),
    		buttons : this.addFormButtons
    	};
    	NS.apply(basicCfg, this.addFormConfig||{});
    	this.form = this.addForm = this.createBaseForm(basicCfg);
    	this.addForm.bindItemsEvent(this.addFormBindEvents() || {});
		this.addForm.show();
    },
    addFormItems : function(){ //TODO 
    	return this.getBaseFormItems();
    },
    addFormButtons : [{text:'提交', name:'submit'}, 
                      {text:'取消', name:'cancel'}], //TODO 
	addFormConfig : {}, //TODO 
    addFormBindEvents : function(){ //TODO 
    	return {'submit' : {event:'click', fn:this.addFormSubmit,scope:this},
	            'cancel' : {event:'click', fn:this.closeForm,    scope:this}};
    },
    addFormSubmit : function(){ //TODO
        this.saveOrUpdate('baseSave',function(resultData){
     		if(resultData.success){
     			this.closeForm();
     			NS.Msg.info('新增成功!');
     			this.loadGrid();
     		}else{
 				NS.Msg.error('新增失败!');
 			}
        },this);
     },
     
    /**************UpdateForm*****************/
    /**
     * _修改form
     */
	showUpdateForm : function(){
     	var rawsValues = this.grid.getSelectRows();
     	if(rawsValues.length !=1){
     		NS.Msg.warning('请选择一行进行修改操作!');
     		return;
     	}
 		var basicCfg = {
    		title   : '修 改',
         	formType: 'update',
    		data    : this.tranData,
         	items   : this.updateFormItems(),
         	buttons : this.updateFormButtons
        };
 		NS.apply(basicCfg, this.updateFormConfig);
    	this.form = this.updateForm = this.createBaseForm(basicCfg);
        this.updateForm.setValues(rawsValues[0]);
        this.updateForm.bindItemsEvent(this.updateFormBindEvents());
 		this.updateForm.show();
	},
	updateFormItems : function(){ //TODO 
		return this.getBaseFormItems();
	},
	updateFormButtons: [{text:'提交', name:'update'}, 
	                    {text:'取消', name:'cancel'}], //TODO 
	updateFormConfig : {}, //TODO
	updateFormBindEvents : function(){ //TODO 
		return {'update' : {event:'click', fn:this.updateFormSubmit, scope:this},
	            'cancel' : {event:'click', fn:this.closeForm,        scope:this}};
	},
	updateFormSubmit : function(){ //TODO
		this.saveOrUpdate('baseUpdate', function(resultData){
     		if(resultData.success){
     			this.closeForm();
      			NS.Msg.info('修改成功!');
      			this.loadGrid();
      	    }else{
 				NS.Msg.error('修改失败!');
 			}
         },this);
	},

    /**************delete*****************/
    /**
     * TODO 删除方法
     */
    showDeleteIds : function(){
		var data = this.grid.getSelectRows(),
			ids  = NS.Array.pluck(data,'id');
		if(ids.length > 0){
			this.deleteUtil('baseDelete', ids, function(resultData){
				if(resultData.success){
					NS.Msg.info('删除成功!');
					this.loadGrid();
				}else{
					NS.Msg.error('删除失败!');
				} 
			},this);
	    }else{
	    	 NS.Msg.warning({
	    		 msg:'请至少选择一条数据!'
	    	 });
	    }
    },
	
	/********************这是form相关公共方法**************************/
	/**
	 * _创建form
	 */
	createBaseForm:function(config){
    	var basic = {
                data    : this.tranData,
                columns : this.formConfig.columns,
                margin  : 10,
                padding : 10,
        		width   : this.formConfig.width,
        		height  : this.formConfig.height,
                modal   : true, //模态，值为true是弹出窗口的
                autoShow   : true,
                autoScroll : true,
                resizable  : true,
//                draggable  : true, //可拖动 {拖动后关闭，firebug一直找不到dom}
        		fieldConfig : { //变更form中所有的field属性
        			labelWidth : this.formConfig.labelWidth || 100,
        			labelAlign : this.formConfig.labelAlign || 'right'
        		}
            };
    	NS.apply(basic, config);
    	return NS.form.EntityForm.create(basic);
    },
    /**
     * _提交form 
     * @param serviceKey
     * @param data
     * @param callBack
     * @param scope
     */
	saveOrUpdate:function(serviceKey, data, callBack, scope){
     	if(this.form.isValid()){
     		//处理基本form值
     		var formValues = this.form.getValues(),
  	   		entityParams   = this.getEntityParams();
     		NS.apply(formValues, entityParams);

     		//处理传过来的值
     		if(typeof data == 'function'){
    			scope	= scope || callBack;
    			callBack= data;
    			data	= undefined;
    		}
     		scope = scope || this;
      		NS.apply(formValues, data);
      		
      		//请求数据
      		this.XgAjax(serviceKey, formValues, callBack, scope);
    	}	
	},
	/**
	 * _隐藏form
	 */
    closeForm : function(){
    	this.form.close();
    },
    
    /************************delete**********************/
	/**
	 * _删除方法
	 * @param serviceKey
	 * @param ids
	 * @param callBack
	 * @param scope
	 */
	deleteUtil : function(serviceKey, ids, callBack, scope){
		var _scope = scope || this;
		NS.Msg.changeTip('提示', '您确定删除这  '+ids.length+' 行数据吗?', function(){
    	   this.XgAjax(serviceKey, {entityName:this.entityName, ids:ids.toString()}, callBack, scope);
       },_scope);
	}
	
	
});