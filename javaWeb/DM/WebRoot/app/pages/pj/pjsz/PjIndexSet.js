/**
 * 评教等级设置
 * pjLevel.js 
 */
 NS.define('Pages.pj.pjsz.PjIndexSet',{
 	extend : 'Pages.pj.component.BaseModel',
 	entityName : 'TbPjIndex',
 	modelConfig : {
 		//service 方法在此配置
 		serviceConfig : {  
 			//新增保存Grid数据路径
        	save:'base_save',
        	//更新保存Grid数据路径
        	update:'base_update',
        	//删除，含批量Grid数据路径
        	deletes:'base_deleteByIds',
        	//查询Grid表头数据
            queryTableHeaderAllData: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: "base_queryTableContent"
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
 		var params = {entityName: this.entityName,flag:0};
 		this.callService([
            {key: 'queryTableHeaderAllData', params: params},
            {key: 'queryGridData', params: params}
        ],
            function (data) {
        		this.initComponent(data);
            });
        //执行新增、修改sercieConfig方法,方法体内执行重写相应的代码即可
//        this.addOrUpdateServiceConfig();
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
        this.initTbar();
        this.initAndDoLayoutPage();
 	},
 	/**
 	 * reWrite -formItems
 	 * @return {}
 	 */
 	 baseFormItems:function(){
 		var formItems=[{name :'id',hidden : true},{name:"idxOneMc",hidden:true},{name:"idxTwoMc",hidden:true},
 			{name:"pjScore",hidden:true},{name :'flag',hidden : true},'idxType','idxSykc',{name:'idxXmnr',width:400},'idxXmfz','idxXmqz','idxScope'];
 		
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
    	return this.baseFormItems();
    },
    /**
     * 更新Grid的basic的配置参数
     * @param {Array} columnData 表头数据
     * @param {Array} gridData grid数据
     */
    updateGridBasicConfig:function(){
    	var basic = {
                plugins: [new NS.grid.plugin.HeaderQuery()],
                 //表格列配置
	            columnConfig : [{name : 'idxOneMc', hidden : true},{name : 'idxTwoMc', hidden : true},{name : 'idxXmmc', hidden : true},
	            	{name : 'idxScope', hidden : false},'idxType',{name : 'idxSykc', hidden : false},
	            	{name : 'idxXmqz', hidden : false},{name : 'idxLevel', hidden : true},{name:'pjScore',hidden:true},{name:'idxXmnr',width:600,align:'left'}
	            ],
                autoScroll: true,
                border:false,
                multiSelect: true,
                lineNumber: true,
                checked:true,
                align:'left'
            };
    	return basic;
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
	 					text : '添加',
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
 					}]
		}
		//创建toolbar对象
 		this.tbar=new NS.toolbar.Toolbar(basic);
 		//绑定事件
 		this.tbar.bindItemsEvent({
 			'add': {event: 'click', fn: this.showAddForm, scope: this},
 		 	'delete': {event: 'click', fn: this.deleteIds, scope: this},
 		 	'update': {event: 'click', fn: this.showUpdateForm, scope: this}
 		})
 		return this.tbar;
 	},
 	
 	 /**
     * 显示新增form
     */
    showAddForm : function(){
    		var items=this.addFormItems();
    		var form=NS.form.EntityForm;
    		var addForm=this.form=form.create({
    			data:this.tranData,
				columns:2,
				autoShow : true,
                modal:true,// 模态，值为true是弹出窗口的。
        		buttons:[{text : '保存',name : 'add'},{text : '取消',name : 'cancel'}],
        		items:items,
        		width:580,
        		height:250,
        		margin:'10 10 10 10',
        		padding:'15 15 15 15'
        	});
        	var idxXmnr=this.form.queryComponentByName("idxXmnr");
        	//idxXmnr.setWidth(400);
        	var idxXmnr=this.form.queryComponentByName("idxXmfz");
        	idxXmnr.setValue(10);
        	this.form.queryComponentByName("flag").setValue(0); //默认0
        	//级别数
//        	var idxJbs=this.form.queryComponentByName("idxLevel");
//        	var oneId=this.getIdByDm("idxLevel","01",this.tranData);
//        	idxJbs.setValue(oneId);//默认1级
        	
        	//指标类型
        	var idxType=this.form.queryComponentByName("idxType");
        	var xspj=this.getIdByDm("idxType","2",this.tranData);
        	idxType.setValue(xspj);
        	
        	//指标类别：校级、院系、个性
//        	var idxZblb=this.form.queryComponentByName("idxScope");
//        	var school=this.getIdByDm("idxType","01",this.tranData);
//        	idxZblb.setValue(school);
        	
        	//指标适用课别
//        	var idxKclb=this.form.queryComponentByName("idxSykc");
//        	var sykb=this.getIdByDm("idxSykc","01",this.tranData);
//        	idxKclb.setValue(sykb);
        	
        	//一级指标项
//        	var oneIdx=this.form.queryComponentByName("idxOneMc");
//        	var oneIdxMc=this.getIdByDm("idxOneMc","11",this.tranData);
//        	oneIdx.setValue(oneIdxMc);
//        	//二级指标
//        	var twoIdx=this.form.queryComponentByName("idxTwoMc");
        	//twoIdx.self.prototype.hidden=true;
        	
        	addForm.setTitle('添加评价指标');
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
       this.saveOrUpdate('save',function(backData){
    	   //具体处理业务
    	   if(backData.success){
    	   	   me.cancelForm();
    		   NS.Msg.info('新增成功！');
    		   this.grid.load( {'flag':0} );
    	   }else{
				NS.Msg.error('新增失败！');
			}
       });
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
            	columns:2,
            	width:580,
        		height:250,
        		margin:'10 10 10 10',
        		padding:'15 15 15 15',
				autoShow : true,
                modal:true,// 模态，值为true是弹出窗口的。
            	buttons : [{text : '修改',name : 'update'},{text : '取消',name : 'cancel'}],
            	items:items
            });
            var idxXmnr=this.form.queryComponentByName("idxXmnr");
        	//idxXmnr.setWidth(400);
            updateForm.setTitle('修改评价指标');
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
     		   NS.Msg.info('修改成功！');
     		   me.cancelForm();
     		   this.grid.load( {'flag':0} );
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
    		this.deleteUtil('deletes',ids,function(backData){
    			if(backData.success){
    				this.grid.load({'flag':0});
    				NS.Msg.info('删除成功！');
    			}else{
    				NS.Msg.error('删除失败！');
    			} 
    		});
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
