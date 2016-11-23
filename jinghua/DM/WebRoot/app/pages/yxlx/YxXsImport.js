/**
 * 迎新管理--学生信息导入
 */
NS.define('Pages.yxlx.YxXsImport', {
	extend : 'Template.Page',
	 modelConfig: {
        serviceConfig: {
        	'queryTableHeader':"base_queryForAddByEntityName",
        	'queryTable' :  {
				service:'base_queryTableContent',
				params:{entityName:'TbYxlxXsjbxx'}
				},
        	'deleteInfo':'yxlx_deleteXsxx',
        	'saveXsInfo':'yxlx_saveXsInfo'
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
		 	{key:'queryTableHeader',params:{entityName:'TbYxlxXsjbxx'}},
		 
		 	{key:'queryTable',params:{start : 0,limit :25}}
	        ]
	        ,function(data){
	        	this.initComponent(data);
	      })
	},
	/**
	 * 初始化组件
	 * 
	 * @param {}
	 *            tranData
	 * @param {}
	 *            tabledata
	 */
	initComponent : function(data){
		
		this.tranData = NS.E2S(data.queryTableHeader);
		
		this.initGrid(data.queryTable);
	    
	    this.initTbar();// 初始化tbar
	    
	    this.initPage();// 初始化页面
	    
	    this.initMask();
	    
	},
	/**
	 * 创建grid
	 * 
	 * @param {}
	 *            tranData
	 * @param {}
	 *            tabledata
	 */
	initGrid : function(tabledata){
	    var basic = {
            plugins: [
             new NS.grid.plugin.HeaderQuery()],
            columnData: this.tranData,
            serviceKey:'queryTable',
            proxy: this.model,
            pageSize:25,
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
	 * 初始化标准Tbar(待扩展)
	 */
    initTbar: function () {
    	
    	// 单字段查询
	 	var single = new NS.grid.query.SingleFieldQuery({
                data : this.tranData,
                grid : this.grid
            });
    	// 定义公共button按钮组
        var basic = {
            items: [
                {xtype: 'button', text: '新增', name: 'add',iconCls:'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls:'page-update'},
                {xtype: 'button', text: '删除', name: 'deletes',iconCls:'page-delete'},
                '-',
                {xtype: 'button', text: '导入', name: 'import',iconCls:'page-search'},
                {xtype: 'button', text: '导出', name: 'export',iconCls:'page-search'},
                '-',
               {xtype: 'button', text: '下载模板', name: 'download',iconCls:'page-search'},
                '-',
                single
                
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
            'deletes': {event: 'click', fn: this.deleteIds, scope: this},
            'add': {event: 'click', fn: this.showAddForm, scope: this},
            'update': {event: 'click', fn: this.showUpdateForm, scope: this},
            'import': {event: 'click', fn: this.importForm, scope: this},
            'export': {event: 'click', fn: this.exportForm, scope: this},
            'download': {event: 'click', fn: this.downloadForm, scope: this}
            
        }); 
    },
    
    /**
	 * 新增按钮相应事件
	 */
	showAddForm : function(){
	 var form = NS.form.EntityForm;
     var form = this.form= form.create({
                title : '新增学生',
                data : this.tranData,
                autoScroll : true,
                columns : 2,
                width : 570,// 窗体宽度
				height : 300,// 窗体高度
                modal:true,// 模态，值为true是弹出窗口的。
                autoShow:true,
              	items : [{name :'id',hidden : true},'xh','xm','ksh','sfzh','mzId','xbId',
						'zzmmId','csrq','lxdh','jtdz','sydId','yxId','zyId',
							{name:'bjId',fields : ['id','mc','fjdId'],associateField : 'fjdId',displayField : 'mc'},
						'rxnjId','pcId'],
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
	/*		this.baseAdd("TbYxlxXsjbxx",values,function(data){
		  	if(data.success){
			 	NS.Msg.info('新增成功！');
			 	this.buttonCancel();
			 	this.grid.load({entityName:'TbYxlxXsjbxx',start : 0,limit :25});
		    }else{
				NS.Msg.error('新增失败！');
			} 
		})*/
		
		this.callService(
			{key:'saveXsInfo',params:values}
	        ,function(data){
		       if(data.saveXsInfo.success){
				 	NS.Msg.info('新增成功！');
				 	this.buttonCancel();
				 	this.grid.load({entityName:'TbYxlxXsjbxx',start : 0,limit :25});
			    }else{
					NS.Msg.error('新增失败！');
				}
	        	
	      })
			
		}
		
	
	},
	
	/**
	 * 创建更新form
	 * 
	 * @param {}
	 *            data
	 */
	showUpdateForm : function(){
		
	var data = this.grid.getSelectRows();
	var ids=NS.Array.pluck(data,'id');
	if(ids.length == 1){
		 var form = NS.form.EntityForm;
	     var form = this.form= form.create({
	                title : '修改学生',
	                data : this.tranData,
	                values : data[0],
	                autoScroll : true,
	                columns : 2,
	                width : 570,// 窗体宽度
					height : 300,// 窗体高度
	                modal:true,// 模态，值为true是弹出窗口的。
	                autoShow:true,
	              	items : [{name :'id',hidden : true},'xh','xm','ksh','sfzh','mzId','xbId',
						'zzmmId','csrq','lxdh','jtdz','sydId','yxId','zyId',
						{name:'bjId',fields : ['id','mc','fjdId'],associateField : 'fjdId',displayField : 'mc'},
						'rxnjId','pcId'],
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
			this.baseUpdate("TbYxlxXsjbxx",values,function(data){
		  	if(data.success){
			 		NS.Msg.info('更新成功！');
			 		this.buttonCancel();
			 		this.grid.load({entityName:'TbYxlxXsjbxx',start : 0,limit :25});
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
			'确定要删除,删除后记录将不能恢复！',
			function(btn) {
			if (btn == 'yes') {
				this.del(ids);
				}
				},this)
	}else{
		 NS.Msg.warning({
		      msg:'请选择一条数据!'
		    	});
	}
	},
	del : function(ids){
		var params={};
		params.ids=ids;
		params.entityName="TbYxlxXsjbxx";
		this.callService({
	         key : 'deleteInfo',
	         params : params
	        },function(data){
	          	if(data.deleteInfo.success){
		    	  	this.buttonCancel;
			 	  	NS.Msg.info('删除成功');
			 	  	this.grid.load({entityName:'TbYxlxXsjbxx',start : 0,limit :25});
		    	   }else{
				  	NS.Msg.error('删除失败');
				   } 
	      });
	      
	
	
	},
    
   /**
	 * 取消按钮相应事件
	 */
	buttonCancel : function(){
		this.form.close();
	},
    
    /**
	 * 导出
	 */
    exportForm :function(){
		 var url ="yxlxgl/YxXsDataExport.action";
		 window.document.open(url,'', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
    	
    },
    /**
	 * 下载模板
	 */
    downloadForm : function() {
		 var url ="yxlxgl/YxXsMbExport.action";
		 window.document.open(url,'', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
	},
	/**
	 * 导入
	 */
	importForm :function(){
		var htmlPanel = new NS.container.Panel({
			width: 300,
	        height:30,
	        anchor: '100%',
	        frame:true,
	        border:false,
	        html:'<font fontsize=10 color=red >注：请慎重导入数据！</font>'
		});
	   var form=this.form = new NS.form.BasicForm({
	        width: 310,
	        height:100,
	        border:false,
	         frame:true,
	    items: [new NS.form.field.File({
	        xtype: 'filefield',
	        name: 'file',
	        fieldLabel: '文件名称',
	        labelWidth: 60,
	        msgTarget: 'side',
	        allowBlank: false,
	        anchor: '100%',
	        buttonText: '选择...'	     	
	    }),htmlPanel],
	    buttons:[{text : '开始导入',name : 'save'}]
	});
	
	 form.bindItemsEvent({
             'save': {event: 'click', fn: this.submitImport, scope: this}
        });

	var win = this.win=new NS.window.Window({
			title: '导入数据', 
			width:320,
			height:140,
			closable : true,
			region:'center',
		    layout : 'border',
		    border:false,
		    modal : true,
			items:[form]
		});
	
		win.show();	
	},
	
	/**
	 * 导入提交
	 */
	submitImport : function() {
		var me = this;
		if (this.form.isValid()) {
			var fileName=me.form.getField("file").getValue();
			me.mask.show();
			me.form.submit({
						url : 'yxlxgl/importYxExcel.action?fileName=' + fileName,
						timeout:180000,
						callback : function(result) {
							me.win.close();
							if(result.success){
								me.mask.hide();
								NS.Msg.info('数据导入成功！');
								me.grid.load({entityName:'TbYxlxXsjbxx',start : 0,limit :25});
							}else{
								me.mask.hide();
								NS.Msg.info('数据导入失败，请检查数据！');
							}
						}
					});

		}
	},
	
	initMask : function(){
	this.mask = new NS.mask.LoadMask({
         target : this.page,
         msg : '数据导入中,请稍候...'
     });
	},
	initPage : function(){
		/**
		 * 流程主页面
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