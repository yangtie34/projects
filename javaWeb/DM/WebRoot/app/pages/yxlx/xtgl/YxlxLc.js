/**
 * 迎新离校流程管理
 */
NS.define('Pages.yxlx.xtgl.YxlxLc', {
	extend : 'Template.Page',
	/**
	 * 请求后台服务配置
	 * @type 
	 */
	modelConfig : {
		serviceConfig : {
			'queryTable' : {
				service:'base_queryTableContent',
				params:{
				entityName:'TbYxlxLc'
				}
		},
		'deleteInfo':"yxlx_deleteLc"
		}
	},
	/**
	 * 入口
	 */
	init: function () {
        this.initData();
    },
	/**
	 * 初始化页面需要数据
	 */
	initData : function() {
		 var me = this;
		 /**
		  * 获取表头表体数据
		  */
		 this.baseHeaderAndData('TbYxlxLc','queryTable',
				 function(tranData,tabledata){
				 	this.initComponent(tranData,tabledata);
				 }
		 );
		
	},
	/**
	 * 初始化组件
	 * @param {} tranData
	 * @param {} tabledata
	 */
	initComponent : function(tranData,tabledata){
		this.tranData = tranData;
	    this.initGrid(tranData,tabledata);//初始化gird
	    this.initTbar();//初始化tbar
	    this.initPage();//初始化页面
	},
	/**
	 * 创建grid
	 * @param {} tranData
	 * @param {} tabledata
	 */
	initGrid : function(tranData,tabledata){
	    var basic = {
            plugins: [
             new NS.grid.plugin.HeaderQuery()],
            columnData: tranData,
            serviceKey:'queryTable',
            pageSize:25,
            proxy: this.model,
            modelConfig: {
                data : tabledata
            },
            autoScroll: true,
            multiSelect: true,
            lineNumber: false,
            border:false,
            checked:true
        };
        this.grid = new NS.grid.Grid(basic);
	},
	/**
	 * 初始化tbar
	 */
	initTbar : function(){
		
		//单字段查询
		 	var single = new NS.grid.query.SingleFieldQuery({
                data : this.tranData,
                grid : this.grid
            });
		var basic = {
            items: [
                {xtype: 'button', text: '新建流程', name: 'add',iconCls : 'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls : 'page-update'},
                {xtype: 'button', text: '删除', name: 'delete',iconCls : 'page-delete'},
                '-',
                single
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
             'add'   : {event: 'click', fn: this.showAddForm, scope: this},
             'update': {event: 'click', fn: this.showUpdateForm, scope: this},
             'delete': {event: 'click', fn: this.deleteIds, scope: this}
        });
	},
	/**
	 * 新增按钮相应事件
	 */
	showAddForm : function(){
	 var form = NS.form.EntityForm;
     var form = this.form= form.create({
                title : '新建流程',
                data : this.tranData,
                autoScroll : true,
                columns : 1,
                width : 300,//窗体宽度
				height : 160,//窗体高度
                modal:true,// 模态，值为true是弹出窗口的。
                autoShow:true,
              	items : [{name :'id',hidden : true},'mc',{name :'lcbm',hidden : true,value:'YX'},'lcms'],
              	buttons:[{text : '保存',name : 'save'},{text : '取消',name : 'cancel'}]
            });
	   	form.bindItemsEvent({
             'save': {event: 'click', fn: this.submitAdd, scope: this},
             'cancel': {event: 'click', fn: this.buttonCancel, scope: this}
        });
	},
	
	/**
	 * 保存按钮相应事件
	 */
	submitAdd : function(){
		if(this.form.isValid()){
			var values = this.form.getValues();
			this.baseAdd("TbYxlxLc",values,function(data){
		  	if(data.success){
			 	NS.Msg.info('新增成功！');
			 	this.buttonCancel();
			 	this.grid.load();
		    }else{
				NS.Msg.error('新增失败！');
			} 
		})
			
		}
		
	
	},
	/**
	 * 取消按钮相应事件
	 */
	buttonCancel : function(){
		this.form.close();
	},
	
	/**
	 * 创建更新form
	 * @param {} data
	 */
	showUpdateForm : function(){
	var data = this.grid.getSelectRows();
	var ids=NS.Array.pluck(data,'id');
	if(ids.length==1){
	 var form = NS.form.EntityForm;
     var form = this.form= form.create({
                title : '修改流程',
                data : this.tranData,
                values : data[0],
                autoScroll : true,
                columns : 1,
                width : 300,//窗体宽度
				height : 160,//窗体高度
                modal:true,// 模态，值为true是弹出窗口的。
                autoShow:true,
              	items : [{name :'id',hidden : true},'mc',{name :'lcbm',hidden : true,value:'YX'},'lcms'],
              	buttons:[{text : '更新',name : 'update'},{text : '取消',name : 'cancel'}]
            });
	   	form.bindItemsEvent({
             'update': {event: 'click', fn: this.submitUpdate, scope: this},
             'cancel': {event: 'click', fn: this.buttonCancel, scope: this}
        });
	}else{
		 NS.Msg.warning({msg:'请选择一条数据!'});
		}
	},
	/**
	 * 修改按钮相应事件
	 */
	submitUpdate : function(){
			if(this.form.isValid()){
				var values = this.form.getValues();
				this.baseUpdate("TbYxlxLc",values,function(data){
					if(data.success){
						NS.Msg.info('更新成功！');
						this.buttonCancel();
						this.grid.load();
					}else{
						NS.Msg.error('更新失败！');
					} 
				})
				
			}

	},
	/**
	 * 删除按钮相应事件
	 */
	deleteIds : function(){
	
	var data = this.grid.getSelectRows();
	
	var ids=NS.Array.pluck(data,'id');
	
		if(ids.length > 0){
			
				NS.MessageBox.confirm(
				'提示',
				'流程删除,相应的环节和学生办理信息将被删除！',
				function(btn) {
						if (btn == 'yes') {
						
						this.del(ids);
							
						}
				},this)
				
		}else{
				 NS.Msg.warning({msg:'请选择一条数据!'});
		}
	},
	
	del : function(ids){
	
		var params={};
		params.ids=ids;
		params.entityName='TbYxlxLc';
		this.callService({
	         key : 'deleteInfo',
	         params : params
	        },function(data){
	          	if(data.deleteInfo.success){
		    	  
		    	  this.buttonCancel;
			 	  
			 	  NS.Msg.info('删除成功');
			 	  
			 	  this.grid.load();
		    	   }else{
				  NS.Msg.error('某批次正在使用本流程,不能被删除');
				   } 
	      });
	      	
	
	},
	
	initPage : function(){
		/**
		 *  流程主页面
		 */
		this.page = new NS.container.Panel({
					layout : 'border',
					autoWidth : true,
					border : false,
					tbar: this.tbar,
					items : [{
								region : 'center',
								layout : 'fit',
								border : false,
								items :	this.grid
								}									
							 ]
		});
		
		this.setPageComponent(this.page);
	}
})