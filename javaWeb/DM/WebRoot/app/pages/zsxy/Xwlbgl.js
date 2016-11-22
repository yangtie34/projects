/**
 * 新闻类别管理
 */
NS.define('Pages.zsxy.Xwlbgl',{
	extend : 'Pages.zsxy.BaseTree',

	modelConfig : {
		serviceConfig : {
			'queryLm' 			: 'zsxyXwlbService?queryLmByJsId',
			'add' 				: 'zsxyXwlbService?add',
			'edit' 				: 'zsxyXwlbService?edit',
			'delete' 			: 'zsxyXwlbService?deleteById',
			'queryById' 		: 'zsxyXwlbService?queryById',
			'moveTo' 			: 'zsxyXwlbService?moveTo'
		}
	},

	init : function(){
		var me=this;

		this.initPage();

		//FN:查看
		var openView=function(e){
			me.grid.selectRow(this.idx,true,true);
			me.openView();
			EG.Event.stopPropagation(e);
		};

		var yymxts=[["是","Y"],["否","N"]];

		//配置
		this.cfg={
			keyId:"id",title:"内容类别管理",
			authable:true,
			form:{
				labelWidth:60,
				items:[
					{title:"名称"	,name:"mc"		,type:"text"		,notnull:true					,length:50		},
					{title:"描述"	,name:"ms"		,type:"textarea"	,height:200},
					{title:"标识"	,name:"bs"		,type:"text"	}
				]
			},
			tree:{
				dragable:true,
				service:{key:"zsxyXwlbService?queryLm",params:{}},
				toExpandLv:2
			},
			tools:{
				height:30,
				items:["view","add","edit","delete"]
			}
		};

		this._init();
	}
});
