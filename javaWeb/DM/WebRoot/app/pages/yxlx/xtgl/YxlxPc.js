/**
 * 迎新离校流程管理
 */
NS.define('Pages.yxlx.xtgl.YxlxPc', {
	extend : 'Template.Page',
	entityName :'TbYxlxPc',
	/**
	 * 请求后台服务配置
	 * @type 
	 */
	modelConfig : {
		serviceConfig : {
			'queryTable' : {
				service:'base_queryTableContent',
				params:{
				entityName:'TbYxlxPc'
				}
		},
		'deleteInfo':"yxlx_deletePc",
		
		'queryHeader':"base_queryForAddByEntityName",
			
		'queryPcTable' : "base_queryTableContent",
		
		'saveInitHjZt' : "yxlx_saveInitHjZt",
		//更新批次状态  是否可用
		'updatePcZt' :	'yxlx_updatePcZt'
		
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
		 /**
		  * 获取表头表体数据
		  */
		 this.baseHeaderAndData(this.entityName,'queryTable',
				 function(tranData,tabledata){
				 	
				 	this.initComponent(tranData,tabledata);
				 }
		 );
		 
		 
		 this.callService([
		 	{key:'queryHeader',params:{entityName:'TbXxzyPcbmb'}},
		 	
		 	{key:'queryPcTable',params:{entityName:'TbXxzyPcbmb',start : 0,limit : 25}}
		 	
	        ]
	        ,function(data){
	        	
	        	this.initInitXsComponent(data);
	      })
		
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
	    
	    this.initMask();
	},
	
	initInitXsComponent : function(data){
		
		var tranData = NS.E2S(data.queryHeader);
		
		var tabledata = data.queryPcTable;
	    
	    this.initPcGrid(tranData,tabledata);//初始化gird
	    
	    this.initPcTbar();//初始化tbar
	   
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
            proxy: this.model,
            pageSize:25,
            modelConfig: {
                data : tabledata
            },
             columnConfig : [
            	{
            	  text: '操作',
                  name :'operator',
                  xtype : 'linkcolumn',
                  width:150,
                  links  : [
                    {
                     linkText : '启用',
                     style : {
                         color : 'blue',
                         font : '18px',
                         'text-decoration' : 'none'
                        }
                    },
                    {
                        linkText : '停用',
                        style : {
                            color : 'blue',
                            font : '18px',
                           'text-decoration' : 'none'
                        }
                    }
                    
          		  ]
                  }
            ],
            autoScroll: true,
            multiSelect: true,
            lineNumber: false,
            border:false,
            checked:true
        };
        this.grid = new NS.grid.Grid(basic);
        
       this.grid.bindItemsEvent({
         'operator': {event: 'linkclick', fn: this.getClickData, scope: this}
        });
	},
	
	/**********响应点击事件***************************/
	getClickData : function(linkValue, recordIndex, cellIndex, data, eOpts){
		
		
		if(linkValue=='启用'){
			this.mask.show();
			 this.callService(
		 		{key:'updatePcZt',params:{pcId:data.id,sfsy:'1'}},
	        function(data){
	        	if(data.updatePcZt.success){
	        	this.mask.hide();
	        	NS.Msg.info('启用成功！');
	        	this.grid.load();
	        	}else{
	        	this.mask.hide();
	        	NS.Msg.info('启用失败！');
	        	}
	        	
	      })
		
			
		}else{
			 this.mask.show();
			 this.callService(
		 		{key:'updatePcZt',params:{pcId:data.id,sfsy:'0'}},
	        
	        function(data){
	        	if(data.updatePcZt.success){
	        	this.mask.hide();
	        	NS.Msg.info('停用成功！');
	        	this.grid.load();
	        	}else{
	        	this.mask.hide();
	        	NS.Msg.info('停用失败！');
	        	}
	        	
	      })
		
		}
		
	},
	
	/*****************批次grid**********************/
	initPcGrid : function(tranData,tabledata){
	    var basic = {
            plugins: [
             new NS.grid.plugin.HeaderQuery()],
            columnData: tranData,
            serviceKey:'queryPcTable',
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
        this.pcGrid = new NS.grid.Grid(basic);
        
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
                {xtype: 'button', text: '新建批次', name: 'add',iconCls : 'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls : 'page-update'},
                {xtype: 'button', text: '删除', name: 'delete',iconCls : 'page-delete'},
               // {xtype: 'button', text: '初始化学生', name: 'initXs',iconCls : 'page-update'},
                '-',
                single
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        
        this.tbar.bindItemsEvent({
             'add'   : {event: 'click', fn: this.showAddForm, scope: this},
             'update': {event: 'click', fn: this.showUpdateForm, scope: this},
             'delete': {event: 'click', fn: this.deleteIds, scope: this},
             //'initXs': {event: 'click', fn: this.showInitXsWin, scope: this}
        });
	},
	
	initPcTbar : function(){
		
		var checkbox1 = new NS.form.field.Checkbox({
		boxLabel:'是否生成二维码',
		name:'checkbox1',
		checked :true
		});
		
		var checkbox2 = new NS.form.field.Checkbox({
		boxLabel:'是否覆盖已有学生',
		name:'checkbox2',
		checked :true
		});
		
		var basic = {
            items: [
                checkbox1,'-',
                {xtype: 'button', text: '初始化', name: 'save',iconCls : 'page-save'},
                '-',
                {xtype: 'button', text: '取消', name: 'cancel'}
            ]
        };
        this.pcTbar = new NS.toolbar.Toolbar(basic);
        
        this.pcTbar.bindItemsEvent({
             'save'   : {event: 'click', fn: this.initXs, scope: this},
             'cancel': {event: 'click', fn: this.colseWin, scope: this}
        });
	},
	
	
	/**
	 * 新增按钮相应事件
	 */
	showAddForm : function(){
	 var form = NS.form.EntityForm;
     var form = this.form= form.create({
                title : '新建批次',
                data : this.tranData,
                autoScroll : true,
                columns : 1,
                width : 300,//窗体宽度
				height : 250,//窗体高度
                modal:true,// 模态，值为true是弹出窗口的。
                autoShow:true,
              	items : [{name :'id',hidden : true},{name :'sfsy',hidden : true},'mc','lcId','ksrq','jsrq','ms'],
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
			
			if(this.checkData(values.ksrq,values.jsrq)){
			
				this.baseAdd(this.entityName,values,function(data){
			  	
			  	if(data.success){
				 		NS.Msg.info('新增成功！');
				 		this.buttonCancel();
				 		this.grid.load();
			    	   }else{
						NS.Msg.error('新增失败！');
					   } 
			})
		
		}else{
		NS.Msg.error('开始日期大于结束日期，请检查！');
		}
		
		
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
	                title : '修改批次',
	                data : this.tranData,
	                values : data[0],
	                autoScroll : true,
	                columns : 1,
	                width : 300,//窗体宽度
					height : 250,//窗体高度
	                modal:true,// 模态，值为true是弹出窗口的。
	                autoShow:true,
	              	items : [{name :'id',hidden : true},'mc','lcId','ksrq','jsrq','ms'],
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
			if(this.checkData(values.ksrq,values.jsrq)){
			
				this.baseUpdate(this.entityName,values,function(data){
			  	if(data.success){
				 		NS.Msg.info('更新成功！');
				 		this.buttonCancel();
				 		this.grid.load();
			    	   }else{
						NS.Msg.error('更新失败！');
					   } 
			})
	}else{
		NS.Msg.error('开始日期大于结束日期，请检查！');
	}
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
		params.entityName=this.entityName;
		this.callService({
	         key : 'deleteInfo',
	         params : params
	        },function(data){
	          	if(data.deleteInfo.success){
		    	  	this.buttonCancel;
			 	  	NS.Msg.info('删除成功');
			 	  	this.grid.load();
		    	   }else{
				  	NS.Msg.error('批次已使用');
				   } 
	      });
	      
	
	
	},
	
	
	showInitXsWin : function(){
		
		var data = this.grid.getSelectRows();
		var ids=NS.Array.pluck(data,'id');
		if(ids.length == 1){
			if(!this.window){
				
				this.window = new NS.window.Window({
				title:'初始化学生',
				closeAction:'hide',
				width : 950,//窗体宽度
				height : 300,//窗体高度
				layout:'fit',
				tbar: this.pcTbar,
				items:this.pcGrid,
	            border:false,
	             modal:true,
	            autoShow: true
	        });
			
			}else{
				
			   this.window.show();
			}
		}else{
			 NS.Msg.warning({
			      msg:'请选择一条数据!'
			    	});
			
		}
	
	},
	
	/**
	 * 初始化学生
	 */

	initXs : function(){
		
		this.initMask();
		
		var data = this.pcGrid.getSelectRows();
		
		var pcId = this.grid.getSelectRows()[0].id
		
		var checkdata =this.pcTbar.getValues();
	
		var ids=NS.Array.pluck(data,'id');
		
		var params={};
		
		params.zspcId =ids;
		params.pcId = pcId;
		NS.apply(params, checkdata);
		
		if(ids.length > 0){
			this.mask.show();

			this.callServiceWithTimeOut(
		 		{key:'saveInitHjZt',params:params},
		 	function(data){
	        if(data.saveInitHjZt.success){
			 		NS.Msg.info('初始化成功！');
			 		this.mask.hide();
			 		this.colseWin();
		    	   }else{
					NS.Msg.error('初始化失败！');
					this.mask.hide();
				   } 
	        	
	      },3000000)
	      
		}else{
		 NS.Msg.warning({
		      msg:'请选择一条记录!'
		    	});
			
		}
	
	},
	
	colseWin :function(){
	
		this.window.hide(); 
	
	},
	
	
	/**
	 * 日期比较
	 * @param {} a 开始日期
	 * @param {} b结束日期
	 * @return {Boolean}
	 */
   checkData :function(a, b) {
	    var arr = a.split("-");
	    var starttime = new Date(arr[0], arr[1], arr[2]);
	    var starttimes = starttime.getTime();
	
	    var arrs = b.split("-");
	    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	    var lktimes = lktime.getTime();
	
	    if (starttimes >= lktimes) {
	
	        return false;
	    }
	    else
	        return true;

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