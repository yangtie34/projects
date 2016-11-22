/**
 * 基础模板页面[xuebl]
 * 	用于树形面板
 */
NS.define('Business.xg.model.TreeModel', {
	
	extend : 'Business.xg.model.BasicModel',
	
	/**
	 * 初始化数据
	 * TODO
	 */
	initData : function(){
		this.initComponent();
		this.initMask();
	},

	/**
	 * 初始化组件
	 * TODO
	 */
	initComponent : function(data){
		this.tree = this.initTree(data);
		this.grid = this.initGrid(data);
		this.tbar = this.initTbar();
		this.initPage();
	},

	/**
	 * 初始化数据
	 * @private
	 */
	initPage : function(){
		var pageHeader = Business.component.PageHeader.getInstance(this.getMenuName()),
			treeItem   = this.tree,
			tbarItem   = this.tbar,
			gridItem   = this.grid,
    		pageCenter = new NS.container.Panel({
	        	border : false,
	        	width  : '100%',
	    		height : '100%',
	        	layout : 'border',
	        	items  : [{
	        		region : 'west',
	        		title  : this.treeTitle,
	        		width  : this.treeWidth,
	        		layout : 'fit',
	        		split  : true,
					collapsible : true,
	        		items  : treeItem
	        	},{
	        		region : 'center',
	        		items  : [tbarItem, gridItem]
	        	}]
	    	});
    	this.page = new NS.container.Panel({
    		border : false,
    		cls	   : 'xg-common_container',
    		layout : 'border',
			items  : [{
        			region : 'north',
					height : 32,
					border : false,
	        		layout : 'fit',
					items  : pageHeader
				}, {
					region : 'center',
		    		border : false,
	        		layout : 'fit',
					items  : pageCenter
				}]
		});
    	this.page.on('afterrender',function(){
    		this.pageAfterRender();
		},this);
        this.setPageComponent(this.page);
    },
	
    /**
     * tree title 树形栏
     * TODO
     */
    treeWidth : '20%',
    treeTitle : '',
    /**
     * 初始化tree
     * TODO
     */
    initTree : function(){
    	return new NS.container.Panel({
    		border: false,
    		width : '100%',
    		height: '100%',
    		html  : 'tree Component',
			autoScroll : true
    	});
    },
    
    /**
     * 初始化grid
     * TODO
     */
	initGrid: function (data) {
    	var tableHeader = data['queryTableHeader'],
    		tranData    = this.tranData = NS.E2S(tableHeader), //表头数据
    		tableData   = data['queryTableData']; //grid数据
        //Grid展现必须的配置项
        var mustCfg = {
	    		columnData   : tranData,
	            columnConfig : this.addColumnConfig() || [],
	            modelConfig  : {
	                data : tableData
	            },
	    		serviceKey   : 'queryTableData',
	    		proxy        : this.model
	        },
        	config = this._updateGridConfig(); //修改Grid的配置
        NS.apply(mustCfg ,config);
        var grid = new NS.grid.SimpleGrid(mustCfg);
        grid.bindItemsEvent(this.addGridEvents() || {});
        return grid;
    },
    addColumnConfig: NS.emptyFn,
    addGridEvents  : NS.emptyFn,
    addGridConfig  : [],
    _updateGridConfig : function(){
    	var ary = this.addGridConfig || [];
    	if(ary.length>1 && ary[1]==true){ //如果 array[1]==true, 清除所有原配置，直接使用这个配置
    		return config;
    	}else{
    		var _config = ary[0],
    			_basic = {
		            border 	   : false,
		            autoScroll : true,
		            checked	   : true,
		            multiSelect: true
		        };
    		for(var name in _config){
    			_basic[name] = _config[name];
    		}
    		return _basic;
    	}
    },

    /**
     * 初始化tbar
     * TODO
     * return new NS.container.Panel({
    		title : '操作栏',
    		border: false,
    		width : '100%',
    		height: '10%',
    		html  : 'tbar Component'
    	});
     */
    initTbar: function () {
        var basicCfg = this._addTbarConfig ||{},
        	config   = this.addTbarConfig()||{};
        NS.apply(basicCfg, config);
        var tbar = new NS.toolbar.Toolbar(basicCfg);
        tbar.bindItemsEvent(this.addTbarEvents() || {});
        if(this.addTbarRow()){
        	var ary = [tbar],
        		rows= this.addTbarRow(),
        		len = rows.length;
        	for(var i=0; i<len; i++){
        		ary.push(rows[i]);
        	}
        	var tbarCtn = new NS.container.Container({
        		items : ary
        	});
        	return tbarCtn;
        }
        return tbar;
    },
    _addTbarConfig : {
		border : false,
	},
    addTbarConfig  : function(){ 
    	return {
			items  : [
    	             {xtype: 'button', text: '新增', name: 'baseAdd',   iconCls:'page-add'},
				'-', {xtype: 'button', text: '修改', name: 'baseUpdate',iconCls:'page-update'},
				'-', {xtype: 'button', text: '删除', name: 'baseDelete',iconCls:'page-delete'},
				'-', new NS.grid.query.SingleFieldQuery({
					   	data : this.tranData,
					   	grid : this.grid
					 })
			]};
    },
    addTbarEvents  : function(){
    	return {'baseAdd'   : {event: 'click', fn: this.showAddForm,    scope: this},
		        'baseUpdate': {event: 'click', fn: this.showUpdateForm, scope: this},          
		        'baseDelete': {event: 'click', fn: this.showDeleteIds,  scope: this},
		       };
    },
    addTbarRow     : NS.emptyFn,
    
});
	