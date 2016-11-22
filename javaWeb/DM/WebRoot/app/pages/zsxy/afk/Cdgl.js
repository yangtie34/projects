/**
 * 应用管理
 */
NS.define('Pages.zsxy.afk.Cdgl',{
	extend : 'Pages.zsxy.BaseTree',

	modelConfig : {
		serviceConfig : {
			'queryLm' 			: 'zsxyAfkCdService?queryLmByJsId',
			'add' 				: 'zsxyAfkCdService?add',
			'edit' 				: 'zsxyAfkCdService?edit',
			'delete' 			: 'zsxyAfkCdService?deleteById',
			'queryById' 		: 'zsxyAfkCdService?queryById',
			'moveTo' 			: 'zsxyAfkCdService?moveTo'
		}
	},

	init : function(){
		var me=this;

		if(this.jsId==null) return alert("请选择角色");

		this.initPage();

		//FN:查看
		var openView=function(e){
			me.grid.selectRow(this.idx,true,true);
			me.openView();
			EG.Event.stopPropagation(e);
		};

		var ymSflxs=[["请选择",""],["是","Y"],["否","N"]];

		var yymxts=[["是","Y"],["否","N"]];

		//配置
		this.cfg={
			keyId:"id",title:"菜单管理",
			authable:true,
			form:{
				labelWidth:60,
				items:[
					{title:"名称"	,name:"mc"		,type:"text"		,notnull:true					,length:50		},
					{title:"页面"	,name:"ymId"	,type:Sys.FP.GridChooser ,typeCfg:{
						keyValue:"id",fieldName:"ymMc",keyName:"mc",parent:this.pEle,title:"页面选择",
						grid:{
							columns:[
								{header:"名称"		,field:"mc"		,width:100},
								{header:"路径"		,field:"lj"		,width:200},
								{header:"是否离线"	,field:"sflx"	,width:100,handle:function(data){return Sys.showType(ymSflxs,data["sflx"]);}}
							],
							queryRPC:{service:"zsxyAfkYmService?queryLmC"}
						},
						searchForm:[
							{xtype:"formItem"	,title:"名称",name:"mc",type:"text",length:20,width:null},
							{xtype:"formItem"	,title:"路径",name:"lj",type:"text",length:20,width:null}
						]
					}},
					{title:"与页面相同",name:"yymxt"	,type:"select"		,textvalues:yymxts},
					{title:"描述"	,name:"ms"		,type:"textarea"	,height:200},
					{title:"标识"	,name:"bs"		,type:"text"	}
				]
			},
			tree:{
				dragable:true,
				service:{key:"zsxyAfkCdService?queryLmByJsId",params:{jsId:this.jsId}},
				toExpandLv:2
			},
			tools:{
				height:30,
				items:["view","add","edit","delete"]
			}
		};

		this._init();
	},

	/**
	 * 获取添加的提交数据
	 * @return {Object}
	 */
	getSubmitDataADD:function(){
		var cNode		=this.tree.getSelected();
		var cNodeData	=cNode.getValue();
		var fd=this.form.getData();
		fd["jsId"]=this.jsId;//this.sltRole.getValue();

		var p={
			po:fd,
			pId:(cNodeData!=null)?cNodeData["id"]:null
		};

		return p;
	},

	/**
	 * 获取修改的提交数据
	 */
	getSubmitDataEDIT:function(){
		var cNode		=this.tree.getSelected();
		var cNodeData	=cNode.getValue();
		var fd=this.form.getData();
		fd["id"]=cNodeData["id"];
		return {
			po:fd
		};
	}
});
