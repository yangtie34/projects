NS.define('Business.xg.wjcf.tjbb.WjcfTjfx_grid',{
	/**
	 * 初始化grid
	 */
	initGrid : function(data){
		if(data.queryGridData.success){
			var basic = {
                    data : data.queryGridData,
                    plugins: [
                        new NS.grid.plugin.HeaderQuery()],
                    autoScroll: true,
                    pageSize : 2000,
                    proxy : this.model,
                    serviceKey : {
                        key : 'queryGridData'
                    },
                    multiSelect: true,
                    lineNumber: true,
                    checked:false,
                    fields : this.getColumnArray(),
                    columnConfig : this.convertColumnConfig(data.queryGridData.data[0].queryType)
                };
			 var grid=this.grid = new NS.grid.SimpleGrid(basic);
			 for(var i=0;i<this.tplData.cflxData.length;i++){
				 var bindId = this.tplData.cflxData[i].ID;
				 (function(grid,id,scope){
					 var obj ={};
				     obj[id] = {event : 'linkclick',scope : this,fn : function(txt,rowIndex,colIndex,data){
				    	 scope.doPopDetails(id,txt,rowIndex,colIndex,data);
			         }};
					 grid.bindItemsEvent(obj);
				 })(grid,bindId,this);
				 
			 }
			 grid.bindItemsEvent({
		            dwmc : {event : 'linkclick',scope : this,fn : this.onGridDrill},
		        });
		        return grid;
		}
	},
	/**
	 *  动态创建columns
	 * @return {Array}
	 */
	convertColumnConfig : function(type){
	    var arrays = this.getColumnArray();
	    var textarrays = this.getTextArray();
	    var widtharrays = this.getWidthArray();
	    var hiddenarrays =this.getHiddenArray();
	    var columns = [];
	    for(var i=0;i<arrays.length;i++){
	        var basic = {
	            xtype : 'column',
	            name : arrays[i],
	            text : textarrays[i],
	            width : widtharrays[i],
	            hidden : hiddenarrays[i],
	            align : 'center'
	        };
	        var mc = arrays[i];
	        if(mc == "dwmc"){
	            NS.apply(basic,{
	                xtype : 'linkcolumn',
	                renderer : function(v,data,rowIndex,colIndex){
	                    if(data.queryType != ''){
	                        var aht = "<a href='javascript:void(0);' style='color: #0000ff;'>{0}</a>";
	                        return NS.String.format(aht,v);
	                    }else{
	                        return v;
	                    }
	
	                }
	            });
	        }else if(mc =="zrs"){
	            NS.apply(basic,{
	                xtype : 'linkcolumn',
	                renderer : function(v,data,rowIndex,colIndex){
	                    if(data.queryType != ''){
	                        var aht = "{0}";
	                        return NS.String.format(aht,v);
	                    }else{
	                        return v;
	                    }
	
	                }
	            });
	        }else{
	            NS.apply(basic,{
	                xtype : 'linkcolumn',
	                renderer : function(v,data,rowIndex,colIndex){
	                    if(data.queryType != ''){
	                        var aht = "<a id='"+mc+"' href='#' style='color: #0000ff;'>{0}</a>";
	                        return NS.String.format(aht,v);
	                    }else{
	                        return v;
	                    }
	
	                }
	            });
	        }
	        columns.push(basic);
	    }
	    return columns;
	},
	color : function(color,txt){
	    var ft = "<span style='color: {0};'>{1}</span>";
	    return NS.String.format(ft,color,txt);
	},
	/**
	 * 执行弹出细节
	 */
	doPopDetails : function(popType,txt,rowIndex,colIndex,data){
	    var params = this.params = {
	        start : 0,
	        limit : 25
	    };
	    if(data.queryType == 'XX'){
	    	if(this.lsmk == 'TbXgWjcfCflx'){
	    		this.params.cflxId = popType;
	    	}else if(this.lsmk == 'TbXgWjcfWjlx'){
	    		this.params.wjlxId = popType;
	    	}
	    }else if(data.queryType == 'YX' || data.queryType == 'ZY'){
	    	this.params.yxOrZyId = data.nodeId;
	    	if(this.lsmk == 'TbXgWjcfCflx'){
	    		this.params.cflxId = popType;
	    	}else if(this.lsmk == 'TbXgWjcfWjlx'){
	    		this.params.wjlxId = popType;
	    	}
	    }else if(data.queryType == 'BJ'){
	    	this.params.bjId = data.nodeId;
	    	if(this.lsmk == 'TbXgWjcfCflx'){
	    		this.params.cflxId = popType;
	    	}else if(this.lsmk == 'TbXgWjcfWjlx'){
	    		this.params.wjlxId = popType;
	    	}
	    }
//	    if("XS" == data.queryType){
//	    	params.bjId=data.nodeId;
//	    	params.zzjgId='';
//	    }
	    this.initMxWindow(params);
	},
	/**
	 * 当表格被钻取
	 */
	onGridDrill : function(txt,rowIndex,colIndex,data){
		this.rangeArray.push({queryType : data.queryType,nodeId : data.nodeId,name : txt});
		this.pageParams.queryType = data.queryType;
		this.pageParams.nodeId = data.nodeId;
	    this.grid.load({
	        queryType : data.queryType,
	        nodeId : data.nodeId,
	        lsmk : this.lsmk,
	        queryParams:this.pageParams
	    });
	    this.setColumnText(data.queryType);
	},
	/**
	 * 刷新图表
	 */
	refreshChart : function(){
		 this.grid.on('load',function(event, element){//当表格数据加载完毕之后重新刷新图表
		    this.chartData = this.changeData(this.grid.getAllRow());
		    this.fusionChart.setJSONData(this.chartData);
	     },this);
	},
	/**
	 *根据查询类型设置列表显示字段
	 * @param queryType
	 */
	setColumnText  : function(queryType){
	    this.indexCom.refreshTplData(this.rangeArray);
	    switch(queryType){
	        case "XX" : this.grid.getColumn("dwmc").setText("学校");
	            this.doForChangeColumns("");
	            document.getElementById('wjcftjfx_tupian').style.display='';
	            this.refreshChart();
	            break;
	        case "YX" : this.grid.getColumn("dwmc").setText("系部");
	            this.doForChangeColumns("");
	            document.getElementById('wjcftjfx_tupian').style.display='';
	            this.refreshChart();
	            break;
	        case "ZY" : this.grid.getColumn("dwmc").setText("专业");
	            this.doForChangeColumns("");
	            document.getElementById('wjcftjfx_tupian').style.display='';
	            this.refreshChart();
	            break;
	        case "BJ" : this.grid.getColumn("dwmc").setText("班级");
	            this.doForChangeColumns("");
	            document.getElementById('wjcftjfx_tupian').style.display='';
	            this.refreshChart();
	            break;
	        case "XS" : this.grid.getColumn("dwmc").setText("姓名");
	            this.doForChangeColumns("XS");
	            document.getElementById('wjcftjfx_tupian').style.display='none';
	            document.getElementById('wjcftj-export').style.display='none';
	            break;
	        case null : this.grid.getColumn("dwmc").setText("首页");
	        	document.getElementById('wjcftjfx_tupian').style.display='';
	            this.doForChangeColumns("");
	            this.refreshChart();
	            break;
	    }
	},
	/**
	 * 改变列
	 */
	doForChangeColumns : function(queryType){
		var zxjColumn = this.getTjColumn();
		var xsColumn = this.getXsColumn();
	    if(queryType == "XS"){
	    	this.grid_ColumnShow(xsColumn, this.grid);
	    	this.grid_ColumnHide(zxjColumn, this.grid);
	    }else{
	    	this.grid_ColumnShow(zxjColumn, this.grid);
	    	this.grid_ColumnHide(xsColumn, this.grid);
	    }
	},
	/**
     * grid列表显示
     * @param cArray
     * @param grid
     */
    grid_ColumnShow : function(cArray,grid){
        for(var i=0;i<cArray.length;i++){
            grid.getColumn(cArray[i]).show();
        }

    },
    /**
     * grid列表隐藏
     * @param cArray
     * @param grid
     */
    grid_ColumnHide : function(cArray,grid){
        for(var i=0;i<cArray.length;i++){
            grid.getColumn(cArray[i]).hide();
        }

    },
    getBindEvent : function(){
    	for(var n=0;n<this.tplData.cflxData.length;n++){

    	} 	
    }
});