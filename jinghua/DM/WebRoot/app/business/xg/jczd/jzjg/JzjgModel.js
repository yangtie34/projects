/**
 * 助学金结果查询
 */
NS.define('Business.xg.jczd.jzjg.JzjgModel',{
	extend : 'Business.xg.jczd.JzModel',
	requires:['Business.xg.jczd.jzjg.JzjgModelCom'],
    mixins:['Business.xg.jczd.jzjg.JzjgModel_win'],
	//批次类型切换
	select : 'Y',
	//实体名
	entityName:'VTbXgJzZxjjg',
	//顶部菜单名称
	cdmc : '助学金结果查询',
	//是否直接通过
	sftg:'N',
	//隶属模块
	lsmk : 'TbXgJzZxj',
	//后台服务配置数据
	serviceConfigData1 : {
		//表头数据
        'queryTableHeader' : 'base_queryForAddByEntityName',
        //查询grid数据
        'queryTableData' : 'zxjCountService?queryZxjResult',
        //查询助学金统计结果数据
        'queryZxjjgCount' : 'zxjCountService?queryZxjResultCount',
        //根据教学组织机构ID获取班级
        'queryBjByJxzzjgId':'base_queryUserBj',
        //查询院系专业树
        'queryTree':'base_queryUserYxZy',
        //查询入学年级树
        'queryUserRxnj' : 'base_queryUserRxnj',
        'queryStuInfo':'jzShService?queryStuInfoById',//根据学生id查询学生基础信息
        'queryStuTmInfo' :'jzShService?queryStuTmInfoById',//查询学生提名信息
	},
	//页面参数
	gridParams:function(){
    	return {
    		entityName:this.entityName,
    		start:0,
    		limit:25,
    		lsmk:this.lsmk
    	};
    },
	/**
	 * 重写请求数据
	 */
	initData : function(){
		this.pageParams = this.gridParams();
		this.pageParams.pcId = this.pcId;
		this.pageParams.lxId = this.lxId;
		this.callService([
		       {key:'queryTableHeader',params:{entityName:this.entityName}},
		       {key:'queryTableData',params:this.pageParams},
		       {key:'queryZxjjgCount',params:this.pageParams},
		       {key : 'queryTree'},
               {key : 'queryUserRxnj'},
               {key : 'queryZxjjgCount'}
		 ],function(data){
			//获取隶属模块名称
			this.jzmcModel = this.initSelectJzmc();
			this.initComponent(data);//初始化组件
		});
	},
	/**
	 * 初始化选择区域页面
	 */
	initSelectJzmc : function(){
		var mc = "";
		switch(this.lsmk){
        case "TbXgJzZxj" : mc="助学金";
            break;
        case "TbXgJzJm" : mc="学费减免";
            break;
        case "TbXgJzJxj" : mc="奖学金";
            break;
        case "TbXgJzRych" : mc="荣誉称号";
            break;
		}
		return mc;
	},
	/**
	 * 重写初始化tbar
	 */
	getTbar : function(data){
//		data.queryUserRxnj.push({id:'1',mc:"全部",bzdm:'XXDM-RXNJ',dm:'2014',bzdmmc:'入学年级'});
		var rxnjData = data.queryUserRxnj;
		rxnjData.push({id:'',mc:"全部",bzdm:'XXDM-RXNJ',dm:'2014',bzdmmc:'入学年级'});
		var treeData = data.queryTree;
		//入学年级
		var rxnjCombox = this.rxnjCombox =  new NS.form.field.ComboBox({
			
			fieldLabel : '入学年级',
			
			width:140,
			
			labelWidth :63,
			
			emptyText: '请选择入学年级...',
			
			data:rxnjData,
			editable : false,
			
			queryMode:'local'
		});
		var yxZyComboxTree=this.yxZyComboxTree = new NS.form.field.ComboBoxTree({
			
			fieldLabel : '学院&专业',
			
			width:240,
			
			labelWidth :71,
			
			expanded:false,
			
			rootVisible:false,
			
			isParentSelect:true,
			editable : false,
			
			emptyText: '请选择院系或专业...',
			
			treeData:treeData
		});
	
	var bjCombox = this.bjCombox = new NS.form.field.ComboBox({
		
		fieldLabel : '班级',
		
		width:190,
		
		labelWidth :39,
		editable : false,
		emptyText: '请选择班级...',
		queryMode:'local'
	});
	var button = new NS.button.Button({
		text: '查询', 
		name: 'query',
		iconCls : 'page-search'
		
	});
	var expButton = new NS.button.Button({
		text: '导出', 
		name: 'exportZxj',
		iconCls : 'page-exportIcon'
		
	});
	var basic = {
            style:{
                background:'#FFF !important',
                border:'none'
            },
	        items: [
	            rxnjCombox,
	        	yxZyComboxTree,
	        	bjCombox,
	        	button,
	        	expButton
	        ]
	    };
	this.tbar = new NS.toolbar.Toolbar(basic);
	//选择年级监听事件
	rxnjCombox.on('select',function (com,data){
    	
    	this.rxnjId = data[0].id;
    	
    	this.pageParams.rxnjId = this.rxnjId;//将年级ID存入全局变量参数内
    	yxZyComboxTree.setValue(null);//置空院系专业组件
    	bjCombox.setValue(null);//置空班级组件
    	this.pageParams.zzjgId = "";
    	this.pageParams.bjId = "";
   	 },this);
	//选择监听事件
	yxZyComboxTree.on('select',function(com,data){
		//重新赋值
		this.pageParams.zzjgId = data.id;//将组织机构ID存入全局变量参数内
//		if(null != this.rxnjCombox.getValue()){
			this.callService({key:'queryBjByJxzzjgId',params:{rxnjId:this.rxnjId,nodeId:data.id}},function(data){
				if(data){
					if(null != bjCombox.getValue()){
						bjCombox.setValue(null);
						this.pageParams.bjId = "";//将全局变量内的班级ID参数掷空
					}
					bjCombox.loadData(data.queryBjByJxzzjgId);
				}
			},this);
//		}
	},this);
	//选择班级监听事件
    bjCombox.on('select',function (com,data){
    	
    	this.pageParams.bjId = data[0].id;//将班级ID存入全局变量参数内
	        
   },this);
   button.on('click',function(event,target){
	   var queryStr = document.getElementById('zxjjg_queryStr').value;
	   if('请输入内容回车查询...' == queryStr){
		   this.pageParams.queryStr = '';
	   }
	   this.refreshDataByPclx();
//		this.grid.load(this.pageParams);
	},this);
   expButton.on('click',function(event,target){
	   NS.entityExcelExport({
			grid: this.grid,
           entityName : this.entityName,
           title :this.jzmcModel+'学生名单' ,
           serviceKey : 'queryTableData',
           controller : this
       }); 
	},this);
	return this.tbar;
},
	/**
	 * 重写初始化统计组件
	 */
	getCountCom : function(data){
		var zxjCountTpl = new NS.Template(
		'姓名/学号/身份证号：<input onblur=\'javascript:if(this.value=="")this.value="请输入内容回车查询...";\' onfocus=\'javascript:if(this.value=="请输入内容回车查询...")this.value="";\' class="inputdefault" name="zxjjg_queryStr" id="zxjjg_queryStr" type="text" value="请输入内容回车查询..." />&nbsp;&nbsp;&nbsp;&nbsp;'+
		'<tpl for=".">'+
		'<label class="labeldefault zxj-jieguo_margin-leftfif">'+
		'<span class="zxj-jieguo_margin-leftfif">总人数：<a name="zxjjg_zrs" href="javascript:void(0);" style="text-decoration:none;">{allNum}</a>&nbsp;人&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">连片特困人数：<span class="zxj-jieguo_orange"><a name="zxjjg_lptk" href="javascript:void(0);" style="text-decoration:none;">{lptkNum}</a></span>&nbsp;人&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">贫困人数：<span class="zxj-jieguo_orange"><a name="zxjjg_pt" href="javascript:void(0);" style="text-decoration:none;">{ptNum}</a></span>&nbsp;人&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">助学金总金额：<span class="zxj_orange">{zje}&nbsp;</span>元</span>'+
		'</label>'+
		'</tpl>'		
		);
		//统计数据tpl
		this.tplZxjjgCountCom = new NS.Component({
			border : false,
			data : data.queryZxjjgCount,
			tpl : zxjCountTpl
		});
		this.tplZxjjgCountCom.on('click',function(event,target){
			//监听数字下钻事件
			if(target.nodeName == "A"){
				if("zxjjg_zrs" == target.name){
					this.pageParams.lylx = '';
					this.grid.load(this.pageParams);
				}
				if("zxjjg_lptk" == target.name){
					this.pageParams.lylx = '1';
					this.grid.load(this.pageParams);
				}
				if("zxjjg_pt" == target.name){
					this.pageParams.lylx = '0';
					this.grid.load(this.pageParams);
				}
			}
			//监听导出按钮
//			if(target.nodeName == 'BUTTON' && target.id == 'lptk-export'){
//				this.exportLptkdqData();
//			}
		},this);
		//监听输入框回车事件
		this.tplZxjjgCountCom.on('keydown',function(event,target){
			if(event.getKey() == 13){
				var queryStr = document.getElementById('zxjjg_queryStr').value;
				this.pageParams.queryStr = queryStr;
				this.grid.load(this.pageParams);
			}
		},this);
		return this.tplZxjjgCountCom;
	},
	/**
	 * 重写初始化grid
	 */
	getGrid : function(data){
		var me = this;
		//表头数据转换
		this.tranData = NS.E2S(data.queryTableHeader);
		//表格数据
		this.tableData = data.queryTableData;
		var basic = {
//				plugins : [new NS.grid.plugin.HeaderQuery()],//表头查询
				columnData : me.tranData,
				modelConfig : {
					data : me.tableData
				},
				columnConfig : [
					{
						   text : '操作',
						   name : 'operator',
						   xtype : 'linkcolumn',
						   align : 'center',
						   width : 55,
						   links : [
						      {
						       linkText : '详情',
						       style : {
						    	   color : 'blue',
						    	   font : '18px',
						    	   'text-decoration' : 'none'
						       }
							  }
						   ]
					}           
				],
				serviceKey : 
				 {
                    key:'queryTableData',
                    params:this.pageParams
                },
				pageSize : 25,
				proxy : this.model,
				autoScroll : true,
				multiSelect : true,
				lineNumber : true,
				border : true
			};
			this.grid = new NS.grid.SimpleGrid(basic);
			this.grid.bindItemsEvent({
				'operator' : {event : 'linkclick',fn:this.queryXsZxjxx,scope:this}
			});
			return this.grid;
	},
	/**
	 * 获取学生助学金详情
	 */
	queryXsZxjxx : function(linkValue,recordIndex,cellIndex,data,eOpts){
		this.createXqWin(data.id,this.pclxId,this.jzmcModel);
	},
	/**
	 * 重写刷新数据
	 */
	refreshDataByPclx : function(){
        this.pageParams.queryStr = '';
		this.pageParams.pcId = this.pcId;
		this.pageParams.lxId = this.lxId;
		this.pageParams.lylx = '';
		this.callService({key:'queryZxjjgCount',params:this.pageParams}
		        		  ,function(data){
		    			//异动类别统计
		    			this.tplZxjjgCountCom.refreshTplData(data.queryZxjjgCount);
		    			//grid数据加载
		    			this.grid.load(this.pageParams);
		});
    }
});