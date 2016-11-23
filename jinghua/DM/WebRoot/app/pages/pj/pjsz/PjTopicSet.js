/**
 * 评价主题设置
 * PjTopicSet.js 
 */
 NS.define('Pages.pj.pjsz.PjTopicSet',{
 	extend : 'Pages.pj.component.BaseModel',
 	entityName : 'TbPjTopic',
 	modelConfig : {
 		//service 方法在此配置
 		serviceConfig : {  
 			saveOrUpdate:'base_saveOrUpdate', //保存或修改
 			saveOrUpdateIndex:'saveTopicIndex',//保存主题指标
 			//新增保存Grid数据路径
        	save:'base_save',
        	//更新保存Grid数据路径
        	update:'base_update',
        	//删除，含批量Grid数据路径
        	deletes:'base_deleteByIds',
        	//生成新主题
        	newTopic:'createNewTopic',
        	//评教指标grid Header
        	queryPjHeaderAllData:'base_queryForAddByEntityName', 
        	//评教指标grid data
    		queryPjGridData: {service:"queryPjIndex",params:{entityName:'TbPjIndex',start:0,limit:10}},
    		//判断主题是否存在
    		queryTopicExist:"queryTopicIfExist",
    		//主题是否已使用
    		queryTopicUsed:"queryTopicUsed",
        	//查询Grid表头数据
            queryTableHeaderAllData: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: { service:'base_queryTableContent',params:{entityName:'TbPjTopic',start:0,limit:25}
 		}}
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
 		var userName=this.userName=MainPage.getUserName();
 		var params = {entityName: this.entityName};
 		var indexParam={entityName:'TbPjIndex'};
 		this.callService([
            {key: 'queryTableHeaderAllData', params: params},
            {key: 'queryGridData', params: params},
            {key: 'queryPjHeaderAllData', params: indexParam}
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
 		this.tranData=data['queryTableHeaderAllData'];
 		this.tableData=data['queryGridData'];
 		this.initGrid(this.tranData,this.tableData);
 		this.pjTranData=data['queryPjHeaderAllData'];
 		this.pjTableData=data['queryPjGridData'];
 		
        this.initTbar();
        this.initAndDoLayoutPage();
 	},
 	/**
 	 * reWrite -formItems
 	 * @return {}
 	 */
 	 baseFormItems:function(){
 		var formItems=[{name :'id',hidden : true},{name:"topicId"},{name:"topicDm",hidden:true,editable:false},{name:"topicMc",hidden:true},"topicMs",{name:'sycs',hidden:true},
 			{name:'xnId',hidden:true},{name:'xqId',hidden:true},{name:'czr',hidden:true},{name:'czsj',hidden:true}];
    	return formItems;
    },
    /**
     * 
     * @return
     */
    addFormItems:function(){
    	return this.baseFormItems();
    },
    /**
     * 
     * @return
     */
    updateFormItems:function(){
    	var formItems=[{name :'id',hidden : true},{name:"topicId",editable:false},{name:"topicDm",hidden:true,editable:false},"topicMs",{name:'sycs',hidden:true},
 			{name:'xnId',hidden:true},{name:'xqId',hidden:true},{name:'czr',hidden:true},{name:'czsj',hidden:true}];
    	return formItems;
    },
	/**---------------------------------------------------------------------------------
	 * 创建grid
	 * @param {} tranData
	 * @param {} tabledata
	 * ---------------------------------------------------------------------------------
	 */
	initGrid : function(tranData,tableData){
		var me=this;
		this.defaultFormItems = NS.Array.pluck(tranData,'sx');
		//转换标准的表头数据
    	var columnData = this.tranData = NS.E2S(tranData);
        //表格样式       
    	var basic = {
                plugins: [new NS.grid.plugin.HeaderQuery()],
                columnData: columnData,
                modelConfig: {
                	data : tableData
           		 },
           		serviceKey:'queryGridData',
           		pageSize:25,
           		proxy: this.model,
                 //表格列配置
	            columnConfig : [ 
	            {name:'xnId',hidden:true},{name:'xqId',hidden:true},{name:'topicMs',align:'left'},
		             { text: '操作',
	                  name :'operator',
	                  xtype:'linkcolumn',
	                  width:120,
	                  renderer:function(){
					       var link1="<a href='#' style='text-decoration:none' >主题指标</a>";
					     //  var link2="<a href='#' style='text-decoration:none' >删除</a>";
					       return link1;
	                  }
	                }
	            ],
                autoScroll: true,
                border:false,
               // multiSelect: true,
                lineNumber: true,
                checked:false,
                align:'left'
            };
        //创建grid对象
        this.grid= new NS.grid.Grid(basic);
         //给表格列增加绑定事件监听
        var column=this.grid.getColumn('operator');
        column.addListener('linkclick',function(text,rowindex,colindex,rowdata){
        	var topId=this.topId=rowdata.id;
        	var topDm=this.topDm=rowdata.topicDm;
        	if(this.indexPanel){
				this.indexGrid.load({topicId:rowdata.id});
				this.indexPanel.show();
				return;
		    }
        	this.callService({key: 'queryPjGridData',
           					  params: {entityName:'TbPjIndex',topicId:this.topId,start:0,limit:10}},//id:rowdata.id,
           					  function(backData){
           					  	this.openIndex4TopicPanel(backData,rowdata,this.topId);
           					 }
           				)
        },this);
	},
	/**
	 * 评教主题的指标
	 * @param {} text
	 * @param {} rowindex
	 * @param {} colindex
	 * @param {} rowdata
	 */
	openIndex4TopicPanel :function(backData,rowdata,topId){
		var me=this;
		var grid =this.indexGrid= this.initPjIndexGrid(this.pjTranData,backData.queryPjGridData,{});
		var indexToolBar=this.initIndexTbar();
		var indexPanel=this.indexPanel=new NS.window.Window({
			title:'主题指标',
    		layout : 'border',
            width : 700,
            height :450,
            modal:true,
            closeAction:'hide',
            margin:'10 10 10 10',
            padding:'10',
            border:'0',
            autoShow:true,
            shadow:false,
            tbar:indexToolBar,
            items:[grid],
            buttons:[{text : '取消',name : 'cancel'}]
		});
		 this.indexPanel.bindItemsEvent({
			   'cancel':{event:'click',fn:function(){
			   		this.indexPanel.hide();
			   },scope:this}
			});
	},
	/**--------------------------初始化工具栏--------------------------------*/
	initIndexTbar :function(){
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
	 					text : '保存一行',
	 					iconCls:'page-jwgl'
 					}
 					]
		}
		//创建toolbar对象
 		this.indexTbar=new NS.toolbar.Toolbar(basic);
 		//绑定事件
 		this.indexTbar.bindItemsEvent({
 		 	'save': {event: 'click', fn: this.saveTopicIndex, scope: this},
 		 	'addRow': {event: 'click', fn: this.addNewRow, scope: this}
 		})
 		return this.indexTbar;
 	},
 	/**
 	 * 保存指标
 	 */
 	saveTopicIndex:function(){
 		var me = this;
		var selRecord=this.indexGrid.getSelectRows();
		var len=selRecord.length;
		if(len==0){
			NS.Msg.info('提示','你没有添加记录,请先添加一行！'); return;
		}
		if(selRecord[0].idxXmnr==''){
			NS.Msg.info('提示','指标内容不能为空！'); return;
		}else if(selRecord[0].idxXmfz ==''){
			NS.Msg.info('提示','指标分值不能为空！'); return;
		}
		var dataArr=new Array();
		if(len==0){
			NS.Msg.alert('提示','请选择一行记录！'); return;
		}else{
			selRecord[0].topicId=me.topId; //主题ID
			var dataJson = JSON.stringify(selRecord[0]);//当前选中的记录
			this.callService(
	            {key: 'saveOrUpdateIndex', params:{"data":dataJson}},
	            	function (data) {
	            		if(data['saveOrUpdateIndex'].success){
	            			this.indexGrid.load({'topicId':me.topId})
	            			NS.Msg.info('提示','保存成功！');
	            			
	            		}else{
	            			NS.Msg.info('提示','保存失败！');return;
	            		}
	            },this
	           );
		}
 	},
 	 /**
     * 新增一行
     */
    addNewRow : function(){
    	this.indexGrid.insertRow(); //添加一行
    },
	/**---------------------------------------------------------------------------------
	 * 创建主页grid
	 * @param {} tranData
	 * @param {} tabledata
	 * ---------------------------------------------------------------------------------
	 */
	initPjIndexGrid : function(tranData,tabledata,cfg){
		//转换标准的表头数据
    	var columnData = this.pjTranData = NS.E2S(tranData);
        //表格样式       
	    var basic = {
            plugins: [ new NS.grid.plugin.HeaderQuery(),new NS.grid.plugin.CellEditor()],
            columnData: columnData,
            serviceKey:{
            	key:'queryPjGridData',
            	params:{entityName:'TbPjIndex',start:0,limit:10,topicId:this.topId}
            },
       		pageSize:10,
       		height:350,
       		proxy: this.model,
            modelConfig: {
                data : tabledata
            },
            columnConfig : ['idxXmmc',
            	{name : 'idxScore', hidden : true},{name : 'idxType', hidden : true},{name : 'idxSykc', hidden : true},{name : 'idxXmfz', hidden : false},
            	{name : 'idxXmqz', hidden : false},{name : 'idxLevel', hidden : true},{name:'topicId',hidden:true},{name:'topicDm',hidden:true},
            	{name : 'idxXmnr',width:330,align:'left'},
            	{ text: '操作',
	                  name :'operator',
	                  xtype : 'linkcolumn',
	                  width :60,
	                  renderer:function(){
					       var link="<a href='#' style='text-decoration:none' >删除</a>";
					         return link; 
	                  }
               		 }
            ],
            checked :false,
            autoScroll: true,
            multiSelect: false,
            lineNumber: true
        };
        //创建grid对象
        NS.apply(basic,cfg);
        this.indexGrid= new NS.grid.Grid(basic);
        this.indexGrid.on('beforeload',function(slef,params){
             params.topicId=this.topId
        },this);
        
        this.indexGrid.bindItemsEvent({
 		 	'operator': {event: 'linkclick', fn: this.deleteIndexIds, scope: this}
 		})
       return this.indexGrid;
	},
	/**
     * 删除主题指标
     */
    deleteIndexIds:function(text,rowindex,colindex,rowdata){
    	var me=this;
    	 var ids=rowdata.id;
    	if(id!=''){
    		  this.deleteUtil4Index('deletes',ids,function(backData){ 
	    	 	if(backData.success){
    				NS.Msg.info('删除成功！');
    				this.indexGrid.deleteRow(rowindex);
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
     * 删除主题
    deleteIds:function(text,rowindex,colindex,rowdata){
    	var me=this;
    	 var ids=rowdata.id;
    	if(id!=''){
    		  this.deleteUtil('deletes',ids,function(backData){ 
	    	 	if(backData.success){
    				NS.Msg.info('删除成功！');
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
    */
    /**
     * 删除工具方法
     * @param {} key
     * @param {} ids
     * @param {} fn
     */
	 deleteUtil4Index : function(key,ids,fn){
    	   var me = this;
    	   var len = ids.length;
	       NS.Msg.changeTip('提示','您确定删除这行数据吗?',function(){
	    	       me.callService({key:key, params: {entityName:"TbPjIndex",ids:ids.toString()}}, function(backData){
	    	            fn.call(me,backData[key]);
	    	        });
	       });
    },
	/**--------------------------初始化工具栏--------------------------------*/
	initTbar :function(){
		var me=this;
		 var single = new NS.grid.query.SingleFieldQuery({
                data : this.tranData,
                grid : this.grid
            });
		var basic={
			items:[{
	 					xtype :'button',
	 					name :'add',
	 					text : '新增',
	 					iconCls:'page-add'
 					},{
	 					xtype : 'button',
	 					name :'update',
	 					text : '修改',
	 					iconCls:'page-update'
 					},
 					{
	 					xtype : 'button',
	 					name :'delete',
	 					text : '删除',
	 					iconCls:'page-delete'
 					},
 					{
	 					xtype : 'button',
	 					name :'new',
	 					text : '生成新主题',
	 					iconCls:'page-add'
 					}]
		}
		//创建toolbar对象
 		this.tbar=new NS.toolbar.Toolbar(basic);
 		//绑定事件
 		this.tbar.bindItemsEvent({
 			'add': {event: 'click', fn: this.showAddForm, scope: this},
 		 	'delete': {event: 'click', fn: this.deleteIds, scope: this},
 		 	'update': {event: 'click', fn: this.showUpdateForm, scope: this},
 		 	'new': {event: 'click', fn: this.createNewTopic, scope: this}
 		})
 		return this.tbar;
 	},
 	
 	 /**
     * 显示新增form
     */
    showAddForm : function(){
    	//var me=this;
    		var items=this.addFormItems();
    		var form=NS.form.EntityForm;
    		var addForm=this.form=form.create({
    			data:this.tranData,
    			formType:'save',
				columns:1,
				title:'新增评教主题',
				autoShow : true,
                modal:true,// 模态，值为true是弹出窗口的。
        		buttons:[{text : '保存',name : 'add'},{text : '取消',name : 'cancel'}],
        		items:items,
        		width:350,
        		height:200,
        		margin:'10 10 10 10',
        		padding:'15 15 15 15'
        	});
        	
        	addForm.getField("czr").setValue(this.userName);
        	addForm.getField("czsj").setValue(this.getCurrentDate(1));
        	
        	/**
        	var topicMcCom=this.form.queryComponentByName("topicMc");
        	var topicDm=this.topicDm=this.form.getFieldValueByName("topicDm");//主题代码
        	var topicMc=this.topicMc=this.form.getFieldValueByName("topicMc");//主题名称
        	if(topicMc=='学生评教'){
        		this.form.queryComponentByName("topicDm").setValue('XSPJ');
        	}
        	if(topicMc=='听课评价'){
        		this.form.queryComponentByName("topicDm").setValue('TKPJ');
        	}	
        	topicMcCom.addListener('blur',function(e,el){
        		var topMc=addForm.getFieldValueByName("topicMc");//主题名称
        		if(topMc!=''){
		        	var param={'topicMc':topMc};
			       //判断是否已存在
			       this.callService({key:'queryTopicExist',params:param},
			       				function(returnData){
						       		if(returnData['queryTopicExist'].success==true){
						       			NS.Msg.info('提示','该主题已存在');
						       			topicMcCom.setValue('');
						       			return;
						       		}
	       				},this);
	       		}
        	},this);
        	*/
           addForm.bindItemsEvent({
					                'add' : {event : 'click',fn : this.submitAdd,scope : this},
					                'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
					            });
    },
    /**
     * 提交新增form
     * 功能：新增成功后关闭窗口、刷新Grid数据
     * 新增失败提示，但不关闭窗口
     * 
     */
    submitAdd : function(){
       var me = this;
//       var topicId=this.form.getField("topicId").getValue();
//       this.saveOrUpdate({form:this.addform,key:'save',fn:function(backData){
       if(this.form.isValid()){
	       this.saveOrUpdate('save',function(backData){
	    	   //具体处理业务
	    	   if(backData.success){
	    	   	   this.grid.load();
	    		   NS.Msg.info('保存成功！');
	    		   this.cancelForm();
	    	   }else{
					NS.Msg.error('保存失败！');
				} 
	       },this)
       }else{
       		return;
       };
    },
     /**
     * 显示修改form
     */
    showUpdateForm : function(){
    	var rawsValues = this.grid.getSelectRows();
    	if(rawsValues.length !=1){
    		NS.Msg.warning('请选择一行进行修改!');return;
    	}
    	if(this.updateForm){
    		this.updateForm.setValues(rawsValues[0]);
    		this.updateForm.close();
    	}else{
    		var items = this.updateFormItems();
    		var form=NS.form.EntityForm;
    		var updateForm=this.form=form.create({
            	formType:'update',
            	data:this.tranData,
            	columns:1,
            	width:350,
        		height:200,
        		margin:'10 10 10 10',
        		padding:'15 15 15 15',
				autoShow : true,
                modal:true,// 模态，值为true是弹出窗口的。
            	buttons : [{text : '修改',name : 'update'},{text : '取消',name : 'cancel'}],
            	items:items
            });
            updateForm.setTitle('修改评教主题');
            updateForm.setValues(rawsValues[0]);
            updateForm.bindItemsEvent({
                'update' : {event : 'click',fn : this.submitUpdate,scope : this},
                'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
            });
    	}
    },
     /**
     * 提交修改form
     * 功能：用于提交修改的form,
     * 修改成功后窗口关闭,刷新Grid数据
     * 修改失败则不关闭窗口
     */
    submitUpdate : function(){
    	var me = this;
    	this.saveOrUpdate('update',function(backData){
     	   //具体处理业务
    		if(backData.success){
    		   //判断是否允许关闭
    			this.grid.load();
     		   NS.Msg.info('修改成功！');
     		   me.cancelForm();
     		
     	    }else{
				NS.Msg.error('修改失败！');
			} 
        });
    },
     /**
     * 执行业务删除的方法
     */
    deleteIds:function(){
    	var me = this,
    	data = this.grid.getSelectRows(),
    	ids = NS.Array.pluck(data,'id');
    	if(ids.length > 0){
    		this.callService({key:'queryTopicUsed',params:{'param':data}},function(data){
    			if(data['queryTopicUsed'].success==true){
    				NS.Msg.info('提示','该主题已经被使用,不能删除！');
    			}else{
	    			this.deleteUtil('deletes',ids,function(backData){
	    			if(backData.success){
	    				NS.Msg.info('删除成功！');
	    				this.grid.load();
	    			}else{
	    				NS.Msg.error('删除失败！');
	    			} 
	    		});
    			}
    		},this);
	    }else{
	    	 NS.Msg.warning({
	    		   msg:'您尚未选择任何数据!'
	    	   });
	    }
    },
     /**
     * 取消(关闭)form方法
     */
    cancelForm : function(){
        if(this.form){
        		this.form.close();
         }
          if(this.addForm){
        		this.addForm.close();
         }
    },
    /**
     * 生成新主题
     */
    createNewTopic:function(){
    	var rawsValues = this.grid.getSelectRows();
    	if(rawsValues.length !=1){
    		NS.Msg.warning('请选择一个主题进行创建!');return;
    	}else{
    		var selData=rawsValues[0];
    		var param={entityName:this.entityName,data:selData};
    		this.callService({key:'newTopic',params:param},
				function(data){
					if(data.newTopic.success==true){
						NS.Msg.info('提示','生成新主题成功！');
						this.grid.load();
					}
			},this)
    	}
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
