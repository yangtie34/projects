/**
 * 助学金统计分析，学生明细
 **/
NS.define('Business.xg.wjcf.tjbb.WjcfTjfx_mxWin',{
	/**
	 * 初始化明细窗口
	 */
	initMxWindow : function(params){
		this.initMxData(params);
	},
	/**
	 * 初始化请求数据
	 */
	initMxData : function(params){
		this.mask.show();
		this.callService([
		     {key : 'queryTableHeader',params:{entityName:this.entityName}},
		     {key : 'queryWjcfMx',params:params}
		     ],function(mxData){
				this.mxHeaderData = NS.E2S(mxData.queryTableHeader);
				this.mxTableData = mxData.queryWjcfMx;
				//初始化查询数据
				this.mxGrid = this.initMxGrid();
				this.showMxWindow();
				this.mxGrid.load(params);
		});
	},
	/**
	 * 创建明细窗口
	 */
	showMxWindow : function(){
		var window = new NS.window.Window({
            width : 800,
            height : 600,
            layout : 'fit',
            closeAction : 'destroy',
            items : this.mxGrid,
			buttons : [
	                {xtype : 'button',text : '关闭',name : 'cancel'},
	                {xtype : 'button',text : '导出',name : 'exportMx'}
	            ]
        });
        window.bindItemsEvent({
            cancel : {event : 'click',fn : function(){window.hide();this.mask.hide();},scope:this},
            exportMx : {event : 'click',fn : this.exportMxData,scope:this}
        });
        window.on('destroy',function(event,target){
			this.mask.hide();
		},this);
        window.show();
	},
	/**
	 * 初始化明细grid
	 */
	initMxGrid : function(){
			var basic = {
//					plugins : [new NS.grid.plugin.HeaderQuery()],//表头查询
					columnData : this.mxHeaderData,
					modelConfig : {
						data : this.mxTableData
					},
					serviceKey : 'queryWjcfMx',
					pageSize : 25,
					proxy : this.model,
					autoScroll : true,
					multiSelect : true,
					lineNumber: true,
					border : false,
					checked : false
					
				};
				this.mxGrid = new NS.grid.Grid(basic);
				return this.mxGrid;
	},
	/**
	 * 导出学生注册信息
	 */
	exportMxData:function(){
		NS.entityExcelExport({
			grid: this.mxGrid,
//			queryParams: params,
            entityName : this.entityName,
            title :this.cdmc+'学生名单' ,
            serviceKey : 'queryWjcfMx',
            controller : this
        }); 
	}
});