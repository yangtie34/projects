/**
 * 助学金预选工具栏js
 */
NS.define('Business.xg.jczd.yx.Yx_lsjl', {
	/**
	 *点击grid学生获奖学字得到学生获奖记录list
	 */
	doPopDetails : function(popType,txt,rowIndex,colIndex,data,jo){	
		var name = jo.attr("name");
		var xsId=data.xsid;//学生id
		if(name =="zxj"){//查询助学金
	    var params = this.params = {
		        queryType : data.queryType,
		        xsId : data.xsid,
		        start : 0,
		        limit : 25,
		        lsmk:"TbXgJzZxj"
		    };
		   this.initMxWindow(params);	
		}else if(name == "jxj"){
		 var params = this.params = {
			        queryType : data.queryType,
			        xsId : data.xsid,
			        start : 0,
			        limit : 25,
			        lsmk:"TbXgJzJxj"
			    };
			   this.initMxWindow(params);	
		}else if(name == "jm"){
		 var params = this.params = {
			        queryType : data.queryType,
			        xsId : data.xsid,
			        start : 0,
			        limit : 25,
			        lsmk:"TbXgJzJm"
			    };
			   this.initMxWindow(params);	
		}
	},
	
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
		     {key : 'queryTableHeader',params:{entityName:'VbXgXsLshj'}},//学生历史获奖grid表头数据
		     {key : 'queryZxjMx',params:params}
		     ],function(mxData){
				this.mxHeaderData = NS.E2S(mxData.queryTableHeader);
				this.mxTableData = mxData.queryZxjMx;
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
            width : 500,
            height : 300,
            layout : 'fit',
            closeAction : 'destroy',
            items : this.mxGrid,
			buttons : [
	                {xtype : 'button',text : '关闭',name : 'cancel'}//,
//	                {xtype : 'button',text : '导出',name : 'exportMx'}
	            ]
        });
        window.bindItemsEvent({
            cancel : {event : 'click',fn : function(){window.hide();this.mask.hide();},scope:this},
//            exportMx : {event : 'click',fn : this.exportMxData,scope:this}
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
					serviceKey : 'queryZxjMx',
					pageSize : 25,
					proxy : this.model,
					autoScroll : true,
					multiSelect : true,
//					lineNumber: true,
					border : false,
					checked : false					
				};
				this.mxGrid = new NS.grid.Grid(basic);
				return this.mxGrid;
	},
});

