/**
 * 评教等级设置
 * pjLevel.js 
 */
 NS.define('Pages.pj.pjsz.PjLevelSet',{
 	extend : 'Pages.pj.component.BaseModel',
 	entityName : 'TbPjLevel',
 	modelConfig : {
 		//service 方法在此配置
 		serviceConfig : {  
        	saveOrUpdate:'base_saveOrUpdate', //保存或修改
        	//更新保存Grid数据路径
        	update:'base_update',
        	//删除，含批量Grid数据路径
        	deletes:'base_deleteByIds',
        	//查询Grid表头数据
            queryTableHeaderAllData: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: {service:"base_queryTableContent",params:{entityName:'TbPjLevel',start:0,limit:25}}
 		}
 	},
 	
 /**-----------------------------------------------
  * 页面初始化
  * ------------------------------------------------
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
    	var delBtn={ buttonText : '删除',
                        name :'delete',
                        style : {
                            color : 'red',
                            font : '18px'
                        }};
	    var basic = {
            plugins: [ new NS.grid.plugin.HeaderQuery(),new NS.grid.plugin.CellEditor()],
            columnData: columnData,
            modelConfig: {
                data : tabledata
            },
            serviceKey:'queryGridData',
            pageSize:25,
           	proxy: this.model,
            columnConfig : [
                { text: '操作',
                  name :'operator',
                  xtype : 'linkcolumn',
                  width :100,
                  renderer:function(){
				       var link="<a href='#' style='text-decoration:none' >删除一行</a>";
				         return link; 
                  }
                }
            ],
            checked :false,
            autoScroll: true,
            multiSelect: false,
            lineNumber: true
        };
        this.grid= new NS.grid.Grid(basic);
        //给表格列增加绑定事件监听
        this.grid.bindItemsEvent({
 		 	'operator': {event: 'linkclick', fn: this.deleteIds, scope: this}
 		})
	},
	/**--------------------------初始化工具栏--------------------------------*/
	initTbar :function(){
		var me=this;
		var basic={
			items:[
 					{
	 					xtype : 'button',
	 					name :'addRow',
	 					text : '添加一行',
	 					iconCls:'page-add'
 					},{
	 					xtype : 'button',
	 					name :'save',
	 					text : '保存',
	 					iconCls:'page-jwgl'
 					}
 					]
		}
		//创建toolbar对象
 		this.tbar=new NS.toolbar.Toolbar(basic);
 		
 		//绑定事件
 		this.tbar.bindItemsEvent({
 		 	'save': {event: 'click', fn: this.saveParam, scope: this},
 		 	'addRow': {event: 'click', fn: this.addNewRow, scope: this}
 		})
 		return this.tbar;
 	},
 	/**
	 * 保存事件
	 */
	saveParam : function(){
		 var me = this;
		var selRecord=this.grid.getSelectRows();
		var len=selRecord.length;
		if(len==0){
			NS.Msg.alert('提示','你没有添加记录,请先添加一行！'); return;
		}
		if(selRecord[0].djmc==''){
			NS.Msg.alert('提示','等级名称不能为空！'); return;
		}else if(selRecord[0].djfz ==''){
			NS.Msg.alert('提示','等级对应分值不能为空！'); return;
		}
		var dataArr=new Array();
		if(len==0){
			NS.Msg.alert('提示','请选择至少一行记录！'); return;
		}else{
			var dataJson='';
			var newDataJson='';
			for(var i=0;i<selRecord.length;i++){
				dataArr[i]={'id':selRecord[i].id,'djmc': selRecord[i].djmc,'djdyfz':selRecord[i].djdyfz,'djfz':selRecord[i].djfz,'djlx':selRecord[i].djlx,'qz':selRecord[i].qz};
				dataJson += JSON.stringify(dataArr[i]);
			}
			newDataJson ={"data":[dataJson],"entityName":this.entityName};   // 表格内数据值
			this.callService(
            {key: 'saveOrUpdate', params:newDataJson},
            	function (data) {
            		if(data.saveOrUpdate.success){
            			NS.Msg.alert('提示','保存成功！');
            			this.grid.load();
            		}else{
            			NS.Msg.alert('提示','保存失败！');return;
            		}
            }
           );
		}
	},
 	 /**
     * 执行业务删除的方法
     */
    deleteIds:function(text,rowindex,colindex,rowdata){
    	 var id=rowdata.id;
    	 var me=this;
	     var jsonParam={"ids":id,"entityName":this.entityName};
    	if(id!=''){
    		  this.deleteUtil('deletes',id,function(backData){ 
	    	 	if(backData.success){
    				NS.Msg.info('删除成功！');
    				this.grid.deleteRow(rowindex);
    				this.grid.load();
    			}else{
    				NS.Msg.error('删除失败！');
    			} 
	    	})
	    	
	    }else{
	    	NS.Msg.warning({
	    		   msg:'您尚未选择任何数据!'
	    	   });
	    }
    },
     /**
     * 删除的工具方法 
     * @param {Array} ids 删除的ids数组
     * @param {Funtion} fn 回调函数
     */
    deleteUtil : function(key,ids,fn){
    	   var me = this,len = ids.length;
	       NS.Msg.changeTip('提示','您确定删除这行数据吗?',function(){
	    	       me.callService({key:key, params: {entityName:me.entityName,ids:ids.toString()}}, function(backData){
	    	            fn.call(me,backData[key]);
	    	        });
	       });
    },
    /**
     * 新增一行
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
