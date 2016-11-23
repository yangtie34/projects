/**
 * 定义学生评教查询页
 */
NS.define('Pages.pj.pjcx.XwpjQuery',{
	extend:'Template.Page',
	entityName :'TbPjXwpj',
	modelConfig :{
		serviceConfig:{
            queryTableHeaderData: "base_queryForAddByEntityName",//查询Grid表头数据service名
            queryGridData :'queryXwpjData',//学委评教查询
            getUserTypeDm:'getUserRoles',// 获取用户角色DM
            initBjCbxCom:'queryBjList4ZyId'//班级列表
		}
	},
	//页面初始化
	init : function(){
		this.initData(); //初始化数据
	},
	
	//初始化数据
 	initData : function(){
 		var me=this;
 		var xnId=this.xnId=MainPage.getXnId();//当前学年ID
 		var xqId=this.xqId=MainPage.getXqId(); //当前学期ID
 		var xnMc=this.xnMc=MainPage.getXnMc(); //学年名称
 		var xqMc=this.xqMc=MainPage.getXqMc(); //学期名称
 		var bmxx=this.bmxx=MainPage.getBmxx(); //部门信息-1001000000680185
 		var roleIds=this.roleIds=MainPage.getRoleIds();//用户角色/
 		
 		var params = {entityName: this.entityName};
 		var queryParam={entityName: this.entityName,xnId:this.xnId,xqId:this.xqId,start:0,limit:25,yxId:bmxx.id};
 		this.callService([
            {key: 'queryTableHeaderData', params: params},
            {key: 'queryGridData', params: queryParam},
            {key: 'getUserTypeDm',params:{'roleIds':roleIds}},
            {key: 'initBjCbxCom',params:{'zyId':0}} //初始化专业下班级列表
        ],
            function (data) {
        		this.initComponent(data);
            });
 	},
 	//自定义下拉组件
 	initBjComp:function(data){
 		this.bjCbx=new NS.form.field.ComboBox({
 			name:'pjxsBjid',
			width:270,
			labelWidth:70,
			fieldLabel:'班级名称',
			queryMode:'local'
		});
		this.bjCbx.loadData(data);
		return this.bjCbx;
 	},
 	//初始化组件
 	initComponent : function(data){
 		var tranData = this.tranData = NS.E2S(data['queryTableHeaderData']);//表头
 		var tableData=data['queryGridData'];//grid data
		var grid= this.grid = this.initTable(tableData,tranData);
		this.tbar = this.initToolbar(tranData,grid);
		var userData=data['getUserTypeDm'];//登录用户类型
    	var roleType=this.roleType=userData.roleDm;
		//班级组件
		var bjData=data['initBjCbxCom'];
		var bjComCbx=this.bjComCbx=this.initBjComp(bjData);
		//查询form
		this.searchForm = this.createSearchForm({
				items:[{name:"xnId",readOnly:true,value:this.xnId},{name:"xqId",value:this.xqId},'month','week','yxId','zyId',bjComCbx,'jzgXm'],
				data : tranData,
				enforcementComponent:grid
			},this);
		var yxIdCom=this.yxIdCom=this.searchForm.getField('yxId');
		if(this.roleType=='6' || this.roleType=='28'){//部门负责人/班主任
			this.yxIdCom.setValue(parseInt(this.bmxx.id));
			this.yxIdCom.setReadOnly(true);
			this.yxIdCom.setEditable(false); //不可编辑
		}
		var zyCom=this.searchForm.getField('zyId');
		zyCom.addListener('change',function(data){
			var zyId=this.zyId=zyCom.getValue();
			this.callService({key:'initBjCbxCom',params:{'zyId':this.zyId}},function(data){
				var bjData=data['initBjCbxCom'].data;
				this.bjCbx.loadData(bjData);
			},this);
		},this);
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
	   			 html:'<font color="red">说明：</font><font color="#CD0000">"被评教师" 列标红表示评教得分非100分</font>'
	    });
		var basic = {
	            items: [
	                single,
	               // senior
	            	desc
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
        		fields:['bjmc'],
        		columnConfig:[{name:'jzgXm',align:'left',width:90,
        			renderer:function(value,data){
        				var pjfs=data.pjScore;
        				if(pjfs<100){
        					return "<span style='color: red;'>"+data.jzgXm+"</span>" ;
        				}else{
        					return "<span>"+data.jzgXm+"</span>" ;
        				}
        			}
        		},{name:'pjScore'},{name:'pjxsXm',align:'left'},{name:'bjmc',width:100,align:'left',header:'班级名称'}],
                modelConfig: {
                    data : gridData
                },
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
				xnId:this.xnId,
				xqId:this.xqId,
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