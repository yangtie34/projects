/**
 * 迎新离校流程管理
 */
NS.define('Pages.yxlx.xtgl.YxlxHj', {
	extend : 'Template.Page',
	entityName:'TbYxlxLchj',
	modelConfig : {
		serviceConfig : {
			'querTableHeader':"base_queryForAddByEntityName",
			'queryTable' :'yxlx_queryHj',
			'queryJsTable' : {
				service:'base_queryTableContent',
				params:{
				entityName:'TsJs'
				}
			},
			'deleteInfo':"yxlx_deleteHj",
			'saveQx':"yxlx_saveLcHjQx",
			'updateQzhj':"yxlx_updateQzhj"
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
		 
		   this.callService([
		 	{key:'querTableHeader',params:{entityName:'TbYxlxLchj'}},
		 
		 	{key:'queryTable',params:{start : 0,limit :25}}
	        ]
	        ,function(data){
	        	this.initComponent(data);
	      })
		
	},
	/**
	 * 初始化组件
	 * @param {} tranData
	 * @param {} tabledata
	 */
	initComponent : function(data){
		this.tranData = NS.E2S(data.querTableHeader);
		this.tabledata = data.queryTable;
	    this.initGrid();//初始化gird
	    this.initTbar();//初始化tbar
	    this.initPage();//初始化页面
	     this.initMask();//初始化遮罩层
	},
	/**
	 * 创建grid
	 * @param {} tranData
	 * @param {} tabledata
	 */
	initGrid : function(){
	    var basic = {
            plugins: [
             new NS.grid.plugin.HeaderQuery()],
            columnData: this.tranData,
            modelConfig: {
                data : this.tabledata
            },
            serviceKey:'queryTable',
            pageSize:25,
            proxy: this.model,
            autoScroll: true,
            multiSelect: true,
            lineNumber: false,
            border:false,
            checked:true
        };
        this.grid = new NS.grid.Grid(basic);
	},
	
	initQzhjGridData : function(){
		var data = this.grid.getSelectRows();
	 	var ids=NS.Array.pluck(data,'id');
	 	if(ids.length == 1){
	 		var lcId = this.grid.getSelectRows()[0].sslcId;
			this.mask.show();
			this.callService(
				{key:'queryTable',params:{lcId:lcId,start : 0,limit :25}}
			     ,function(data){
			     this.mask.hide();
				 this.showQzhjWin(data.queryTable);      	
			     })
	 		}else{
		 		  NS.Msg.warning({
			      msg:'请选择一条数据!'
			   	});
	 		}
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
                {xtype: 'button', text: '新建环节', name: 'add',iconCls : 'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls : 'page-update'},
                {xtype: 'button', text: '删除', name: 'delete',iconCls : 'page-delete'},
                {xtype: 'button', text: '设置前置环节',name:'qzhj',iconCls : 'page-update'},
                {xtype: 'button', text: '分配权限',name:'fpqx',iconCls : 'page-update'},
                '-',
                 single
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
             'add'   : {event: 'click', fn: this.showAddForm, scope: this},
             'update': {event: 'click', fn: this.showUpdateForm, scope: this},
             'delete': {event: 'click', fn: this.deleteIds, scope: this},
             'qzhj': {event: 'click', fn: this.initQzhjGridData, scope: this},
             'fpqx'  : {event: 'click', fn: this.initFpqxGrid,scope: this}
        });
	},
	/**
	 * 新增按钮相应事件
	 */
	showAddForm : function(){
	 var form = NS.form.EntityForm;
     var form = this.form= form.create({
                title : '新建环节',
                data : this.tranData,
                autoScroll : true,
                columns : 1,
                width : 320,//窗体宽度
				height : 220,//窗体高度
                modal:true,// 模态，值为true是弹出窗口的。
                autoShow:true,
              	items : [{name :'id',hidden : true},'mc','sslcId','sfbbhj','hjblbm','xh'],
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
			
			this.baseAdd(this.entityName,values,function(data){
			  	if(data.success){
				 		NS.Msg.info('新增成功！');
				 		this.buttonCancel();
				 		this.grid.load({start : 0,limit :25});
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
	 if(ids.length == 1){
	 	
		 var form = NS.form.EntityForm;
	     var form = this.form= form.create({
	                title : '修改流程',
	                data : this.tranData,
	                values : data[0],
	                autoScroll : true,
	                columns : 1,
	                width : 320,//窗体宽度
					height : 220,//窗体高度
	                modal:true,// 模态，值为true是弹出窗口的。
	                autoShow:true,
	              	items : [{name :'id',hidden : true},'mc','sslcId','sfbbhj','hjblbm','xh'],
	              	buttons:[{text : '更新',name : 'update'},{text : '取消',name : 'cancel'}]
	            });
		   	form.bindItemsEvent({
	             'update': {event: 'click', fn: this.submitUpdate, scope: this},
	             'cancel': {event: 'click', fn: this.buttonCancel, scope: this}
	        });
	 }else{
	 
	  NS.Msg.warning({
		      msg:'请选择一条数据!'
		    	});
	 	
	 }
	},
	/**
	 * 修改按钮相应事件
	 */
	submitUpdate : function(){
		if(this.form.isValid()){
		var values = this.form.getValues();
		this.baseUpdate(this.entityName,values,function(data){
		  	if(data.success){
			 		NS.Msg.info('更新成功！');
			 		this.buttonCancel();
			 		this.grid.load({start : 0,limit :25});
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
		'环节删除,相应的学生办理环节信息将被删除！',
		function(btn) {
			if (btn == 'yes') {
				
		var params={};
		params.ids=ids;
		params.entityName=this.entityName;
		this.callService({
	         key : 'deleteInfo',
	         params : params
	        },function(data){
	          	if(data.deleteInfo.success){
		    	  this.buttonCancel;
			 	  NS.Msg.info('删除成功');
			 	  this.grid.load({start : 0,limit :25});
		    	   }else{
				  NS.Msg.error('某批次正在使用本环节,不能被删除');
				   } 
	      });
				
			}
		},this)
	
	}else{
		 NS.Msg.warning({
		      msg:'请选择一条数据!'
		    	});
	}
	},
	
	showQzhjWin : function(data){
	var basic = {
            plugins: [
             new NS.grid.plugin.HeaderQuery()],
            columnData: this.tranData,
            modelConfig: {
                data : data
            },
            pageSize:25,
            autoScroll: true,
            multiSelect: true,
            lineNumber: false,
            border:false,
            checked:true
        };
        this.qzhjgrid = new NS.grid.Grid(basic);
        
       	this.qzhjwindow = new NS.window.Window({
			title:'设置前置环节',
            width : 600,
            height : 500,
            layout : 'fit',
            tbar:this.initQzhjTbar(),
            items : this.qzhjgrid,
             modal:true,
            autoShow: true
        });
        
	},
	
	
	/**
	 * 设置前置环节tbar
	 */
	initQzhjTbar : function(){
		var me = this;
		var basic = {
            items: [
                {xtype: 'button', text: '保存', name: 'save'},
                {xtype: 'button', text: '取消', name: 'cancel'}
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
             'save'   : {event: 'click', fn: this.qzhjSubmitAdd, scope: this},
             'cancel'   : {event: 'click', fn: this.qzhjCancel, scope: this}
        });
        
        return tbar;
	},
	/**
	 * 前置环节保存
	 */
	qzhjSubmitAdd:function(){
	
	var qdata = this.qzhjgrid.getSelectRows();
	
	var qzhjIds=NS.Array.pluck(qdata,'id');
	
	var params={}
	
	params.hjId=this.grid.getSelectRows()[0].id;
	
	params.qzhjIds = qzhjIds;
	
	this.callService({
	         key : 'updateQzhj',
	         params : params
	        },function(data){
	          	if(data.updateQzhj.success){
	          	  NS.Msg.info('保存成功');
			 	  this.qzhjCancel();
			 	  this.grid.load({start : 0,limit :25});
		    	   }else{
				  NS.Msg.error('环节本身不能为自己的前置环节');
				   } 
	      });
	
	},
	
	qzhjCancel : function(){
	
	this.qzhjwindow.close();
	
	},
	
	
	/**
	 * 分配权限grid
	 */
	initFpqxGrid : function(){
	var data = this.grid.getSelectRows();
	var ids=NS.Array.pluck(data,'id');
	
	if(ids.length ==1){
	this.mask.show();	
	this.baseHeaderAndData('TsJs','queryJsTable',
				 function(tranData,tabledata){
				 	   var basic = {
				            plugins: [
				            new NS.grid.plugin.HeaderQuery()],
				            columnData: tranData,
				            modelConfig: {
				                data : tabledata
				            },
				            autoScroll: true,
				            multiSelect: true,
				            lineNumber: false,
				            border:false,
				            checked:false
        					};
        		  	this.jsGrid = new NS.grid.Grid(basic);	
        		  	this.mask.hide();
        		  	this.initFpqxTbar();
    				this.initFpqxWin();
				 }
		 );
		 
	}else{
		 NS.Msg.warning({
		      msg:'请选择一条数据!'
		    });
	}
	
	},
	/**
	 * 分配权限tbar
	 */
	initFpqxTbar : function(){
		var me = this;
		var basic = {
            items: [
                {xtype: 'button', text: '保存', name: 'save'},
                {xtype: 'button', text: '取消', name: 'cancel'}
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
             'save'   : {event: 'click', fn: this.fpqxSubmitAdd, scope: this},
             'cancel'   : {event: 'click', fn: this.fpqxCancel, scope: this}
        });
        
        return tbar;
	},
	/**
	 * 分配权限win
	 */
	initFpqxWin : function(){
	
	this.fpqxwindow = new NS.window.Window({
			title:'分配权限',
            width : 600,
            height : 500,
            layout : 'fit',
            tbar:this.initFpqxTbar(),
            items : this.jsGrid,
             modal:true,
            autoShow: true
        });
	},
	/**
	 * 保存分配权限
	 */
	fpqxSubmitAdd : function(){
	 
	 var hjData = this.grid.getSelectRows()[0];
	 
	 var jsData = this.jsGrid.getSelectRows();
	 
	 var ids = NS.Array.pluck(jsData,'id');
	 
	 var params ={};
	 params.hjId = hjData.id;

	 if(ids.length>0){
	 	params.jsIds = ids;
	 }
	 params.lcId = hjData.sslcId;
	 this.mask.show();
	 this.callService({
	         key : 'saveQx',
	         params : params
	        },function(data){
	          	if(data.saveQx.success){
	          	  this.mask.hide();
			 	  NS.Msg.info('授权成功');
			 	  this.fpqxCancel();
			 	  this.grid.load({start : 0,limit :25});
		    	   }else{
		    	   	this.mask.hide();
				  NS.Msg.error('授权失败');
				   } 
	      });
	 
	},
	/**
	 * 窗口关闭
	 */
	fpqxCancel : function(){
	this.fpqxwindow.close();
	},
	
	initMask : function(){
	this.mask = new NS.mask.LoadMask({
         target : this.page,
         msg : '数据加载中,请稍候...'
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