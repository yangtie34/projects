/**
 * 学工-奖助-设置[类型设置]
 * page model
 */
NS.define('Business.xg.jczd.JzSzLxszModel',{
	
	extend : 'Business.xg.model.EntityModelNew',
	
	/**
	 * 更新serviceConfig
     * @Override
	 */
    addServiceConfig : {
    	//查询类型
    	queryTableData  : 'jzLxszService?queryJzLxTableData',
    	//新增类型
    	insertLx		: 'jzLxszService?insertJzLx',
    	//新增类型
    	updateLx 		: 'jzLxszService?updateJzLx',
		//删除类型
		deleteLx 		: 'jzLxszService?deleteJzLx',
    },
    /**
     * 更新serviceParams
     * @Override
     */
    addServiceParams : function(){
    	return { //自己的模板可以直接用 addServiceParams
		    		'queryTableData' : {sslx:this.getSslx()},
		    		'insertLx' 		 : {sslx:this.getSslx()},
		    		'updateLx' 		 : {sslx:this.getSslx()}
		    	};
    },
	/**
	 * 更新serviceParams
     * @Override
	 */
//    beforeInit : function(){
//    	this.addServiceParams = {
//    		'queryTableData' : {sslx:this.getSslx()},
//    		'insertLx' 		 : {sslx:this.getSslx()},
//    		'updateLx' 		 : {sslx:this.getSslx()}
//    	};
//    },
   
    /**
     * 定义页面属性
     * @Override
     */
	requires   : ['Business.component.PageHeader', //表头
	              'Business.xg.jczd.TjszForm'], //条件form
	menuName   : '类型设置', //菜单名
	entityName : 'TbXgJzLx', //实体名
	lsmk	   : '', //隶属模块
	getGridParams : function(){
		return {start:0, limit:25};
    },
    getSslx : function(){
    	return this.lsmk;
    },
    /**
	 * tbar config
     * @Override
	 */
    addTbarConfig  : function(){ //TODO tbar config
    	return 	{items: [
	       		         {xtype: 'button', text: '新增', 	name: 'baseAdd',   iconCls:'page-add'},
					'-', {xtype: 'button', text: '修改', 	name: 'baseUpdate',iconCls:'page-update'},
					'-', {xtype: 'button', text: '删除', 	name: 'baseDelete',iconCls:'page-delete'},
					'-', {xtype: 'button', text: '设置评选条件', name: 'sztj',	   iconCls:'page-update'},
					'-', new NS.grid.query.SingleFieldQuery({
				            data : this.tranData,
				            grid : this.grid
				         })
				]};
    },
    /**
     * tbar event
     * @Override
     */
    addTbarEvents : function(){  //TODO grid 绑定事件
    	return {'baseAdd'   : {event: 'click', fn: this.showAddForm,    scope: this},
		        'baseUpdate': {event: 'click', fn: this.showUpdateForm, scope: this},          
		        'baseDelete': {event: 'click', fn: this.showDeleteIds,  scope: this},
		        'sztj'		: {event: 'click', fn: this.showSztj,  	 scope: this}
    			};
    },
    
    /**************AddForm*****************/
    /**
     * 新增方法
     * @Override
     */
    addFormSubmit : function(){
        this.saveOrUpdate('insertLx', function(data){
     		if(data.success){
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
     * 修改方法
     * @Override
     */
	updateFormSubmit : function(){ 
		this.saveOrUpdate('updateLx', function(data){
     		if(data.success){
     			this.closeForm();
      			NS.Msg.info('修改成功!');
      			this.loadGrid();
      	    }else{
 				NS.Msg.error('修改失败!');
 			}
         },this);
	},
    
    /************************delete**********************/
    /**
     * 删除方法
     * @Override
     */
    showDeleteIds : function(){
		var data = this.grid.getSelectRows(),
			ids = NS.Array.pluck(data,'id');
		if(ids.length > 0){
			this.deleteUtil('deleteLx', ids, function(resultData){
				if(resultData.success){
					if(resultData.info){ 
						this.XgError('类型已经被批次引用，请关闭批次后再删除！');
					}else{
						NS.Msg.info('删除成功!');
						this.loadGrid();
					}
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
    
    /*************************设置条件********************/
    /**
     * 根据行选择调用组件
     */
    showSztj : function(){
    	var data = this.grid.getSelectRows();
    	if(data.length == 1){
    		var id = data[0]['id'];
    		this.showJbtjWin(id);
	    }else{
	    	 NS.Msg.warning({
	    		 msg:'请选择一条数据!'
	    	 });
	    }
    },
    showJbtjWin : function(id){
    	var compon = new Business.xg.jczd.TjszForm();
		compon.on('submit', this.submitFn, this);
    	compon.show(id, this.getSslx());
    },
    submitFn : function(){
    	this.loadGrid();
    },
    

    /*************************修改基础条件********************/
	/**
	 * loadGrid
     * @Override
	 */
    loadGrid : function(params){
    	var _par = params || {};
    	_par.sslx = this.getSslx();
		this.grid.load(_par);
	}
    
});