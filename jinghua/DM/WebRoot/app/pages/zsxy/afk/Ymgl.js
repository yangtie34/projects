/**
 * 页面管理
 */
NS.define('Pages.zsxy.afk.Ymgl',{
	extend : 'Pages.zsxy.BaseList',

	modelConfig : {
		serviceConfig : {
			'queryLmC' 			: 'zsxyAfkYmService?queryLmC',
			'add' 				: 'zsxyAfkYmService?add',
			'edit' 				: 'zsxyAfkYmService?edit',
			'delete' 			: 'zsxyAfkYmService?delete',
			'queryById' 		: 'zsxyAfkYmService?queryById'
		}
	},

	init : function(){
		var me=this;

		this.initPage();

		this.ymSflxs=[["请选择",""],["是","Y"],["否","N"]];
		
		//FN:查看
		var openView=function(e){
			me.grid.selectRow(this.idx,true,true);
			me.openView();
			EG.Event.stopPropagation(e);
		};

		var lxs=[["页面","P"],["原生","N"]];

		//配置
		this.cfg={
			keyId:"id",title:"页面管理",

			grid:{
				columns:[
					{header:"名称"		,field:"mc"		,width:100	},
					{header:"路径"		,field:"lj"		,width:300	,fieldClass:"txtleft"},
					{header:"离线"		,field:"sflx"	,width:50	,handle:function(data){return Sys.showType(me.ymSflxs,data["sflx"]);}},
					{header:"操作"		,field:"oper"	,width:120	,handle:function(data,g){
						return EG.CE({tn:"a",cls:"zsxy_orange",idx:g,href:"javascript:void(0)",onclick:openView,innerHTML:"查看"});
					}}
				]
			},

			searchForm:{
				items:[
					{xtype:"formItem",width:160,title:"名称"		,name:"mc"	,type:"text"	,searchOnReady:true},
					{xtype:"formItem",width:160,title:"路径"		,name:"lj"	,type:"text"	,searchOnReady:true},
					{xtype:"formItem",width:160,title:"离线"		,name:"sflx",type:"select"	,textvalues:this.ymSflxs},
					{xtype:"panel",layout:{type:"default",middleChilds:true},style:"text-align:left",items:[
						new EG.ui.Button({text:"查询",click:function(){
							me.doSearch();
						}})
					]}
				]
			},

			tools:{
				height:30,
				items:["view","add","edit","delete"]
			},

			form:{
				items:[
					{pos:[0,0],xtype:"formItem",title:"名称"		,name:"mc"	,type:"text"	,length:20},
					{xtype:"formItem",width:160,title:"离线"		,name:"sflx",type:"select"	,textvalues:this.ymSflxs},
					{pos:[2,0],xtype:"formItem",title:"路径"		,name:"lj"	,type:"text"	},
					{pos:[3,0],xtype:"formItem",title:"描述"		,name:"ms"	,type:"textarea",height:200	}
				]
			}
		};

		this._init();
	}
});
