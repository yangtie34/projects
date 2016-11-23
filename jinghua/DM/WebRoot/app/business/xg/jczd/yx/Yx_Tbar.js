/**
 * 助学金预选工具栏js
 */
NS.define('Business.xg.jczd.yx.Yx_Tbar', {
	/**
	 * 重写初始化tbar
	 */
	getTbar : function(data){
		var rxnjData = data.queryUserRxnj;
		rxnjData.push({id:'',mc:"全部",bzdm:'XXDM-RXNJ',dm:'2014',bzdmmc:'入学年级'});
		var treeData = data.queryTree;
		//入学年级
		var rxnjCombox = this.rxnjCombox =  new NS.form.field.ComboBox({
			
			fieldLabel : '入学年级',
			
			width:140,
			
			labelWidth :63,
			
			emptyText: '请选择入学年级...',
			editable : false,
			
			data:rxnjData,
			
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
	
//	//学生状态
//	var ztCombox = this.ztCombox = new NS.form.field.ComboBox({
//		
//		fieldLabel : '学生状态',
//		fields : ['id','mc'],
//		data : [{id : '0',mc : '未预选'},{id : '1',mc : '预选'},{id : '2',mc : '已提名'},{id : '9',mc : '已退回'},{id : '3',mc : '已通过'}],
//		
//		width:190,
//		
//		labelWidth :70,
//		
//		emptyText: '',
//		
//		queryMode:'local'
//	});
	
	var button = new NS.button.Button({
		text: '查询', 
		name: 'query',
		iconCls : 'page-search'
		
	});
	var plSetButton = new NS.button.Button({
		text: '设置资料齐全', 
		name: 'setZlqq',
		iconCls : 'page-update'
		
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
	        	plSetButton	        	
	        ]
	    };

	var tbar=this.tbar=new NS.toolbar.Toolbar(basic);
	//选择年级监听事件
	rxnjCombox.on('select',function (com,data){
    	
    	this.rxnjId = data[0].id;    	
    	this.pageParams.rxnjId = this.rxnjId;//将年级ID存入全局变量参数内
    	this.pageParams.xszt="";
    	yxZyComboxTree.setValue(null);//置空院系专业组件
    	bjCombox.setValue(null);//置空班级组件
    	this.pageParams.zzjgId = "";
    	this.pageParams.bjId = "";
   	 },this);
	//选择监听事件
	yxZyComboxTree.on('select',function(com,data){
			this.pageParams.zzjgId = data.id;//将组织机构ID存入全局变量参数内
				this.callService({key:'queryBjByJxzzjgId',params:{rxnjId:this.rxnjId,nodeId:data.id}},function(data){
					if(data){
						if(null != bjCombox.getValue()){
							bjCombox.setValue(null);
							this.pageParams.bjId = "";//将全局变量内的班级ID参数掷空
						}
						this.pageParams.xszt="";
						bjCombox.loadData(data.queryBjByJxzzjgId);
					}
				},this);	
	},this);
	//选择班级监听事件
    bjCombox.on('select',function (com,data){
    	this.pageParams.xszt="";
    	this.pageParams.bjId = data[0].id;//将班级ID存入全局变量参数内
	        
   },this);
  //选择年级监听事件
//    ztCombox.on('select',function (com,data){   	
//    	this.xszt = data[0].id;
//    	this.pageParams.xszt = this.xszt;//将学生状态ID存入全局变量参数内
//   	 },this);
	button.on('click',function(){
		this.refreshDataByPclx();
//		this.grid.load(this.pageParams);
	},this);
	plSetButton.on('click',function(){
	   //批量设置资料齐全
		this.setZlsfqq();		
//		this.grid.load(this.pageParams);
	},this);
	
	return this.tbar;
},
/***
 * 批量设置资料是否齐全
 */
 setZlsfqq:function(){
 	var rawsValues = this.grid.getSelectRows();
	var ids = NS.Array.pluck(rawsValues,'id');
	var xsIds = NS.Array.pluck(rawsValues,'xsid');
	if(ids.length > 0){
		this.callService({
			key : 'setZlsfqq',
			params : {ids:ids.toString(),xsIds:xsIds.toString(),pclxId:this.pclxId,lsmk:this.lsmk}
		},function(data){
			//判断先预选			
			if(data.setZlsfqq.success){
				NS.Msg.info('设置成功！');
				this.refreshDataByPclx();
			}else{
				NS.Msg.info(data.setZlsfqq.info);
			}
		});   		
    }else{
    	 NS.Msg.warning({
    		   msg:'您尚未选择任何数据!'
    	   });
    } 	 
 },
});

