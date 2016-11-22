/**
 * 满意度测评查询页
 */
NS.define('Pages.pj.pjcx.MydQuery',{
	extend:'Template.Page',
	entityName :'VbMydCp',
	modelConfig :{
		serviceConfig:{
			initCpxm:'getMydCpxm',//初始化满意度测评项
            queryTableHeaderAllData: "base_queryForAddByEntityName",//查询Grid表头数据service名
            queryGridData: "queryMydcp"  //查询Grid数据service名
		}
	},
	//页面初始化
	init : function(){
		this.initData(); //初始化数据
	},
	
	//初始化数据
 	initData : function(){
 		var me=this;
 		var params = {entityName: this.entityName};
 		var xnId=this.xnId=MainPage.getXnId();//当前学年ID
 		var xqId=this.xqId=MainPage.getXqId(); //当前学期ID
 		
 		this.callService([
            {key: 'queryTableHeaderAllData', params: params},
            {key: 'queryGridData', params: params},
            {key: 'initCpxm',params:params}
        ],
            function (data) {
        		this.initComponent(data);
            });
 	},
 	//初始化组件
 	
 	//自定义下拉组件
 	initCpxmComp:function(data){
 		this.mydCbx=new NS.form.field.ComboBox({
//			data:config.data
 			name:'cpxm',
			width:270,
			labelWidth:70,
			fieldLabel:'测评项目',
			queryMode:'local'
		});
		this.mydCbx.loadData(data);
		return this.mydCbx;
 	},
 	initComponent : function(data){
 		var tranData = this.tranData = NS.E2S(data['queryTableHeaderAllData']);//表头
 		var tableData=data['queryGridData'];//grid data
		var grid= this.grid = this.initTable(tableData,tranData);
		this.tbar = this.initToolbar(tranData,grid);
		var cpxmData=data['initCpxm'].data;
		var cpxmCbx=this.initCpxmComp(cpxmData);
		
		//查询form
		this.searchForm = this.createSearchForm({
				items:[{name:"xnId",editable:true,readOnly:false,value:this.xnId},{name:"xqId",editable:true,readOnly:false,value:this.xqId},
 					   {name:"jzgXm",editable:true,readOnly:false},"jzgh","yxId","cprq",cpxmCbx,'mydId'],
				data : tranData,
				enforcementComponent:grid
			},this);
		
		//表格panel
		this.gridPanel =new NS.container.Panel({
            layout : 'fit',
            border:false,
            autoScroll:true,
           // tbar: this.tbar,
            items: this.grid
        });
		this.initPage();
 	},
 	//初始化工具栏
	initToolbar:function(tranData,grid){
		var single = new NS.grid.query.SingleFieldQuery({
        	data : tranData,
        	grid : grid
    	});
		var senior = new NS.grid.query.SeniorQuery({
    		data : tranData,
            grid : grid
        });
		var basic = {
	            items: [
	                single,
	                senior
	            ]
	        };	
    	var tbar = new NS.toolbar.Toolbar(basic);
    	return tbar;
	},
	
	//创建查询form
	createSearchForm:function(cfg,scope){
		//重新定义加载刷新的回调方法  执行组件
		if(cfg.items){//重新复制这个items对象
			var arr = new Array();
			arr = arr.concat(cfg.items);
			cfg.items = arr;
		}
    	var basic = {
                buttons:[{text:'查询',name:'search'},{name:'reset',text:'重置'}],
                columns : 4,
                border:false,
                buttonAlign:'left',
                margin:'5 0 0 5',
                padding:'0 5 5 0 ',
                //collapsed :true,
                frame:false
            };
    	if(cfg) NS.apply(basic,cfg);
    	var form = NS.form.EntityForm.create(basic);
    	form.bindItemsEvent({
    		search:{event : 'click',fn : function(){
    			if(cfg.load){
    				cfg.load.call(scope||this,this,this.getValues());
    				return;
    			}
    			if(this.enforcementComponent){
    				this._load(this.getValues());	
    			}else{
    				alert('请将执行组件注册(regster)到该查询组件内！');
    			}
    		},scope : form},
    		reset:{event : 'click',fn : function(){
    			if(cfg.reset){
    				cfg.reset(scope||this,this);
    				return;
    			}
    			this.reset();
    		},scope : form}
    	});
    	/**
    	 * 扩展的form方法
    	 */
    	form._regster = function(enforcementComponent){
    		this.enforcementComponent = enforcementComponent;
    	};
    	form._load = function(params){
    		this.enforcementComponent.load(params);
    	};
    	form._loadData = function(data,append){
    		this.enforcementComponent.loadData(data,append);
    	};
    	if(cfg.enforcementComponent){
    		form._regster(cfg.enforcementComponent);
    	}
    	return form;
	},
	
	//初始化表格
	initTable:function(gridData,tranData,cfg){
		var basic = {
                autoScroll: true,
                border:false,
                multiSelect: false,
                lineNumber: true,
        		columnData: tranData,
        		serviceKey:{
        			key:'queryGridData',
        			params:this.commonGridParams()
        		},
                modelConfig: {
                    data : gridData
                },
                columnConfig:[
                	 { 
                	  name:'mydCont',
                	  align:'left',
                	  width:80,
	                  renderer:function(value,data){
	                  	var cont=data.mydCont;
	                  	if(cont=='满意'){
					        return "<span href='javascript:void(0);' style='color: blue;'>"+cont+"</span>" ;
	                  	}else if(cont=='基本满意'){
	                  		return "<span href='javascript:void(0);' style='color: orange;'>"+cont+"</span>" ;
	                  	}else{
	                  		return "<span href='javascript:void(0);' style='color: red;'>"+cont+"</span>" ;
	                  	}
	                  }
                	}
                ],
                proxy:this.model
        };
        if(cfg){
        	NS.apply(basic,cfg);
        }
        return new NS.grid.Grid(basic);
	},
	commonGridParams:function(){
		var params = {
				entityName:this.entityName,
				start:0,
				limit:25
		};
		return params;
	},
 	//页面渲染
 	initPage:function(){
		var component = new NS.container.Panel({
			layout:'border',
			border:false,
			items:[{
				region:'north',
				border:false,
				layout:'fit',
				items:[this.searchForm]
			},{
				region:'center',
				border:false,
				layout:'fit',
				items:[this.gridPanel]
			}]
		});
		this.setPageComponent(component);
	}
})