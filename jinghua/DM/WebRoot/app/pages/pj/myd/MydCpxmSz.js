/**
 * 定义满意度测评项目设置页面
 */
NS.define('Pages.pj.myd.MydCpxmSz',{
	extend:'Pages.pj.component.BaseModel',
	entityName :'TbPjMydCpxm',
	modelConfig :{
		serviceConfig:{
            queryTableHeaderAllData: "base_queryForAddByEntityName",//查询Grid表头数据service名
            queryGridData: {service:"base_queryTableContent",params:{entityName:'TbPjMydCpxm',start:0,limit:25}},  //查询Grid数据service名
            saveOrUpdate:'base_saveOrUpdate', //保存或修改
			saveCpxm :'cpxm_save', //保存数据service名
        	update:'base_update',//更新grid记录数据 service名
			deleteRowById : 'base_deleteByIds', //删除表格行记录
			//删除，含批量Grid数据路径
        	deletes:'base_deleteByIds'
		}
	},
	/**
	 * 初始化
	 */
	init : function(){
		this.initData(); //初始化数据
	},
	/**
 	 * ---------------------------------------------------------------
 	 * 初始化数据，包括表头数据或表体数。需要调用后台业务service
 	 * ----------------------------------------------------------------
 	 */
 	initData : function(){
 		 /**
		  * 获取表头表体数据
		  */
 		var params = {entityName: this.entityName,start:0,limit:25};
 		this.callService([
            {key: 'queryTableHeaderAllData', params: params},
            {key: 'queryGridData', params: params}
        ],
            function (data) {
        		this.initComponent(data);
            });
 	},
 	
 	/**
 	 * --------------------------------------------------------------------------------
 	 * 初始化页面组件
 	 * @param {} tranData
 	 * @param {} tabledata
 	 *  --------------------------------------------------------------------------------
 	 */
 	initComponent : function(data){
 		this.initGrid(data['queryTableHeaderAllData'],data['queryGridData']);
        this.initTbar();
        this.initAndDoLayoutPage();
 	
 	},
	/**---------------------------------------------------------------------------------
	 * 创建grid
	 * @param {} tranData
	 * @param {} tabledata
	 * ---------------------------------------------------------------------------------
	 */
	initGrid : function(tranData,tabledata){
		//转换标准的表头数据
    	var columnData = this.tranData = NS.E2S(tranData);
         //表头列定义
        var myColumn={
         			text: '操作',
                    name :'operator',
                    xtype:'linkcolumn',
                    renderer:function(){
				       var link="<a href='#' style='text-decoration:none' >删除一行</a>";
				         return link; 
                  },
          		    width :100
        };
        //表格样式       
	    var basic = {
            plugins: [ new NS.grid.plugin.HeaderQuery(),new NS.grid.plugin.CellEditor()],
            columnData: columnData,
            modelConfig: {
                data : tabledata
            },
            serviceKey:'queryGridData',
            pageSize:25,
           	proxy: this.model,
            columnConfig : [myColumn],
            checked :false,
            autoScroll: true,
            multiSelect: false,
            lineNumber: true
        };
        //创建grid对象
        this.grid= new NS.grid.Grid(basic);
        //给表格列增加绑定事件监听
        this.grid.bindItemsEvent({
 		 	'operator': {event: 'linkclick', fn: this.deleteIds, scope: this}
 		})
	},
	/**
	 * 删除一行
	 * @param {} text
	 * @param {} rowindex
	 * @param {} colindex
	 * @param {} rowdata
	 */
	deleteIds : function(text,rowindex,colindex,rowdata){
	    var id=rowdata.id;
	    var me=this;
	    var jsonParam={"ids":id,"entityName":this.entityName};
	    if(id!=""){ //如果记录的ID不为空则调用服务进行真删除
	    	 this.deleteUtil('deletes',id,function(backData){ 
	    	 	if(backData.success){
	    				NS.Msg.info('删除成功！');
	    				this.grid.deleteRow(rowindex);
	    				this.grid.load();
	    			}else{
    				NS.Msg.error('删除失败！');
    			} 
	    	}
	    	
	  )}else{
	  		this.grid.deleteRow(rowindex);
	}},
	deleteUtil : function(key,ids,fn){
    	   var me = this,len = ids.length;
	       NS.Msg.changeTip('提示','您确定删除这行数据吗?',function(){
	    	       me.callService({key:key, params: {entityName:me.entityName,ids:ids.toString()}}, function(backData){
	    	            fn.call(me,backData[key]);
	    	        });
	       });
    },
/**--------------------------初始化工具栏--------------------------------*/
	initTbar :function(){
		var me=this;
		var basic={
			items:[{
	 					xtype :'button',
	 					name :'save',
	 					text : '保存',
	 					iconCls:'page-jwgl'
 					},
 					{
	 					xtype : 'button',
	 					name :'add',
	 					text : '添加一行',
	 					iconCls:'page-add'
 					}
 				]
		}
		//创建toolbar对象
 		this.tbar=new NS.toolbar.Toolbar(basic);
 		//绑定事件
 		this.tbar.bindItemsEvent({
 			'save': {event: 'click', fn: this.saveParam, scope: this},
 		 	'add': {event: 'click', fn: this.addNewRow, scope: this}
 		})
 		return this.tbar;
 	},
 	
 	/**
	 * 保存事件
	 */
	saveParam : function(){
		var me=this;
		var selRecord=this.grid.getSelectRows();
		if(selRecord.length==0){
			NS.Msg.info('提示','行记录内容不能为空！'); return;
		}else{
			if(selRecord[0].cpxm==''){
				NS.Msg.info('提示','测评项目不能为空！'); return;
			}else if(selRecord[0].cpnr ==''){
				NS.Msg.info('提示','测评内容不能为空！'); return;
			}
			var dataArr=new Array();
			var dataJson='';
			for(var i=0;i<selRecord.length;i++){
				dataArr[i]={'id':selRecord[i].id,'cpxm': selRecord[i].cpxm,'cpnr':selRecord[i].cpnr,'bz':selRecord[i].bz};
				dataJson=dataArr[i];
			}
			var newDataJson ={"data":[dataJson],"entityName":this.entityName};   // 表格内数据值
			this.callService(
	            {key: 'saveOrUpdate', params: newDataJson},
	            	function (data) {
	            		if(data.saveOrUpdate.success){
	            			NS.Msg.info('提示','保存成功！');
	            			this.grid.load();
	            		}else{
	            			NS.Msg.info('提示','保存失败！');
	            		}
	            }
           );
		}
	},
	/**
	 * 添加一行按钮相应事件
	 */
	addNewRow : function(){
		this.grid.insertRow(); //添加一行
	},
	/**
 	 * ----------------------------------------------------------------------------------
 	 * 整体页面
 	 * @param {} tranData
 	 * @param {} tabledata
 	 * @return {}
 	 * ----------------------------------------------------------------------------------
 	 */
    initAndDoLayoutPage:function(){
    	var component = new NS.container.Panel({
            layout : 'fit',
            tbar: this.tbar,
            border:false,
            items: this.grid
        });
        this.setPageComponent(component);
    }
})