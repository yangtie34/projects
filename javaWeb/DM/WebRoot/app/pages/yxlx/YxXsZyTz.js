/**
 * 迎新管理--学生专业调整
 */
NS.define('Pages.yxlx.YxXsZyTz', {
	extend : 'Template.Page',
	 modelConfig: {
        serviceConfig: {
        	'queryTableHeader':'base_queryForAddByEntityName',
        	'queryTable' :{
        	service:'base_queryTableContent',
        	params:{entityName:'TbYxlxXsjbxx'}
        	},
        	'saveXsZyTz':'yxlx_saveXsZyTz'
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
	 * @param {} tranData
	 * @param {} tabledata
	 */
	initComponent : function(data){
		
		this.tranData = NS.E2S(data.queryTableHeader);
		
		this.initGrid(data.queryTable);
	    
	    this.initTbar();//初始化tbar
	    
	    this.initPage();//初始化页面
	    
	    this.initMask();
	    
	},
	/**
	 * 创建grid
	 * @param {} tranData
	 * @param {} tabledata
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
            border:false
        };
        this.grid = new NS.grid.Grid(basic);
	},

    /**
     * 初始化标准Tbar(待扩展)
     */
    initTbar: function () {
    	
    	//单字段查询
	 	var single = new NS.grid.query.SingleFieldQuery({
                data : this.tranData,
                grid : this.grid
            });
    	//定义公共button按钮组
        var basic = {
            items: [
                {xtype: 'button', text: '转专业', name: 'add',iconCls:'page-add'},
                '-',
                single
                
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
            'add': {event: 'click', fn: this.showAddForm, scope: this}
            
        }); 
    },
    
    /**
	 * 新增按钮相应事件
	 */
	showAddForm : function(){
	var data = this.grid.getSelectRows();
	var ids=NS.Array.pluck(data,'id');
	if(ids.length == 1){
	 var form = NS.form.EntityForm;
     var form = this.form= form.create({
                title : '转专业',
                data : this.tranData,
                values : data[0],
                autoScroll : true,
                columns : 1,
                width : 580,//窗体宽度
				height : 350,//窗体高度
                modal:true,// 模态，值为true是弹出窗口的。
                autoShow:true,
              	items : [
              		{
                        xtype: 'fieldset',
                        title:'学生基本信息',
                        columns: 2,
                        border: true,
                        items: [{name :'id',hidden : true},
                        	{name:'xh',readOnly:true},
                        	{name:'xm',readOnly:true},
                        	{name:'ksh',readOnly:true},
                        	{name:'sfzh',readOnly:true},
                        	{name:'yxId',readOnly:true},
                        	{name:'zyId',readOnly:true},
                        	{name:'bjId',readOnly:true}]
                    },{
                        xtype:'fieldset',
                        title:'转入',
                        columns:1,
                        border:true,
                        items: ['zryxId','zrzyId',
                        {name:'zrbjId',fields : ['id','mc','fjdId'],associateField : 'fjdId',displayField : 'mc'}
                        ]
                    }
                    
                    ],
              	buttons:[{text : '保存',name : 'save'},{text : '取消',name : 'cancel'}]
            });
	   	form.bindItemsEvent({
             'save': {event: 'click', fn: this.submitAdd, scope: this},
             'cancel': {event: 'click', fn: this.buttonCancel, scope: this}
        });
        
         }else{
	 
	  NS.Msg.warning({
		      msg:'请选择一条数据!'
		    	});
	 	
	 }
	},
	
	/**
	 * 保存按钮相应事件
	 */
	submitAdd : function(){
		if(this.form.isValid()){
			var values = this.form.getValues();
			this.callService({
						key : 'saveXsZyTz',
						params : values
					}, function(data) {
						if (data.saveXsZyTz.success) {
							this.buttonCancel();
							NS.Msg.info('转专业成功！');
							this.grid.load({start : 0,limit : 25});
						}else {
							NS.Msg.error('转专业失败！');
						}
					});
			
		}
		
	
	},
    
   /**
	 * 取消按钮相应事件
	 */
	buttonCancel : function(){
		this.form.close();
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