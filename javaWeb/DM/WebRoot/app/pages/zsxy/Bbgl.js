/**
 * 版本管理
 */
NS.define('Pages.zsxy.Bbgl',{
	extend : 'Pages.zsxy.BaseList',

	modelConfig : {
		serviceConfig : {
			'queryLmC' 			: 'zsxyBb_queryLmC',
			'add' 				: 'zsxyBb_add',
			'edit' 				: 'zsxyBb_edit',
			'delete' 			: 'zsxyBb_delete',
			'queryById' 		: 'zsxyBb_queryById'
		}
	},

	init : function(){
		var me=this;

		this.initPage();

		this.bbLbs	=[["请选择",""],["安卓","A"],["IOS","I"]];
		this.bbSfkys=[["请选择",""],["是","Y"],["否","N"]];

		//FN:查看
		var openView=function(e){
			me.grid.selectRow(this.idx,true,true);
			me.openView();
			EG.Event.stopPropagation(e);
		};


		//配置
		this.cfg={
			keyId:"id",title:"版本管理",

			grid:{
				columns:[
					{header:"类别"		,field:"lb"		,width:100	,handle:function(data){return Sys.showType(me.bbLbs,data["lb"]);}},
					{header:"版本号"		,field:"bh"		,width:100	},
					{header:"是否可用"	,field:"sfky"	,width:50	,handle:function(data){return Sys.showType(me.bbSfkys,data["sfky"]);}},
					{header:"发布时间"	,field:"rq"		,width:120	},
					{header:"操作"		,field:"oper"	,width:120	,handle:function(data,g){
						return EG.CE({tn:"a",cls:"zsxy_orange",idx:g,href:"javascript:void(0)",onclick:openView,innerHTML:"查看"});
					}}
				]
			},

			searchForm:{
				items:[
					{xtype:"formItem",width:160,title:"类别"		,name:"lb"	,type:"select"	,textvalues:this.bbLbs	,searchOnReady:true},
					{xtype:"formItem",width:160,title:"编号"		,name:"bh"	,type:"text"	,length:20				,searchOnReady:true},
					{xtype:"formItem",width:160,title:"可用"		,name:"sfky",type:"select"	,textvalues:this.bbSfkys,searchOnReady:true},
					{xtype:"panel",layout:{type:"default",middleChilds:true},style:"text-align:left",items:[
						new EG.ui.Button({text:"查询",click:function(){
							me.doSearch();
						}})
					]}
				]
			},

			tools:{
				height:30,
				items:["view","add","edit","delete",new EG.ui.Button({
					text:"测试",click:function(){
						EG.MMVC.call({rpc:"zsxyService",method:"login",params:["admin","1"],callPath:"/MOSDC_KFHG/zsxy_call.servlet",callback:function(data){alert(EG.toJSON(data))}})
					},menu:[
						{text:"课程表",click:function(){EG.MMVC.call({rpc:"pcmCurriculumService",method:"queryByCUR",params:[],callPath:"/MOSDC_KFHG/zsxy_call.servlet",callback:function(data){alert(EG.toJSON(data))}})}},
						{text:"新闻",click:function(){EG.MMVC.call({rpc:"zsxyXwService",method:"queryTopLm",params:[4],callPath:"/MOSDC_KFHG/zsxy_call.servlet",callback:function(data){alert(EG.toJSON(data))}})}}
					]
				})]
			},

			form:{
				items:[
					{pos:[0,0],xtype:"formItem",title:"类别"		,name:"lb"	,type:"select"	,textvalues:this.bbLbs			,unnull:true},
					{pos:[0,1],xtype:"formItem",title:"编号"		,name:"bh"	,type:"text"	,length:20						,unnull:true},
					{pos:[1,0],xtype:"formItem",title:"可用"		,name:"sfky",type:"select"	,textvalues:this.bbSfkys		,unnull:true},
					{pos:[1,1],xtype:"formItem",title:"日期"		,name:"rq"	,type:"date"	,fmt:"YMD"						,unnull:true},
					{pos:[2,0],xtype:"formItem",title:"地址"		,name:"dz"	,type:"text"	,length:100						,unnull:true},
					{pos:[3,0],xtype:"formItem",title:"描述"		,name:"ms"	,type:"textarea",height:100}
				]
			}
		};

		this._init();
	}
});
