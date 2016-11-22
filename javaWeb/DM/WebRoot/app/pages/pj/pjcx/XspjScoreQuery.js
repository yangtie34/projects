/**
 * 定义学生评教查询页
 */
NS.define('Pages.pj.pjcx.XspjScoreQuery',{
	extend:'Template.Page',
	entityName :'VbXspjPjdf',
	modelConfig :{
		serviceConfig:{
            queryTableHeaderAllData: "base_queryForAddByEntityName",//查询Grid表头数据service名
            queryGridData:'queryPjdf4Xspj'//查询教师平均得分
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
 		var curXnId=this.curXnId=MainPage.getXnId();//当前学年ID
 		var curXqId=this.curXqId=MainPage.getXqId(); //当前学期ID
 		var queryParam={entityName: this.entityName,xnId:curXnId,xqId:curXqId,start:0,limit:25};
 		this.callService([
            {key: 'queryTableHeaderAllData', params: params},
            {key: 'queryGridData', params: queryParam}
        ],
            function (data) {
        		this.initComponent(data);
            });
 	},
 	//初始化组件
 	initComponent : function(data){
 		var tranData = this.tranData = NS.E2S(data['queryTableHeaderAllData']);//表头
 		var tableData=data['queryGridData'];//grid data
		var grid= this.grid = this.initTable(tableData,tranData);
		this.tbar = this.initToolbar(tranData,grid);
		//查询form
		this.searchForm = this.createSearchForm({
				items:[{name:"xnId",readOnly:true,value:this.curXnId},{name:"xqId",readOnly:false,value:this.curXqId},{name:"jzgXm"},"yxId"],
				data : tranData,
				enforcementComponent:grid
			},this);
		var xnCom=this.searchForm.getField('xnId');
		xnCom.setValue(this.curXnId);
		var xqCom=this.searchForm.getField('xqId');
		xqCom.setValue(this.curXqId);
		//表格panel
		this.gridPanel =new NS.container.Panel({
            layout : 'fit',
            border:false,
            autoScroll:true,
            tbar: this.tbar,
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
        var desc=new NS.Component({
	    		 width:650,
	    		 height:20,
//	    		 margin:"0 5 5 0",
	   			 html:''//<font color="red">说明：</font><font color="#CD0000">"被评教师" 列标红表示评教得分非100分</font>
	    });
		var basic = {
	            items: [
	                single
	               // senior
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
                fields:['yxmc','week'],
                columnConfig:[{name:'xnId',hidden:true},{name:'xqId',hidden:true},{name:'yxmc',align:'left'},
                	 {name:'zgdf',hidden:true},{name:'zddf',hidden:true},{name:'jzgXm',align:'left'},
                	 {name:'yxId',hidden:true,align:'left'},{name:'pjdf',align:'left'},{name:'week'}
                ],
                proxy:this.model
        };
        if(cfg){
        	NS.apply(basic,cfg);
        }
        this.grid=new NS.grid.Grid(basic);
        return this.grid;
	},
	commonGridParams:function(){
		var params = {
				entityName:this.entityName,
				xnId:this.curXnId,
				xqId:this.curXqId,
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