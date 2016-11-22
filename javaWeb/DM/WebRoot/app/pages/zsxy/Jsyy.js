/**
 * 角色应用（授权）
 */
NS.define('Pages.zsxy.Jsyy',{
	extend : 'Pages.zsxy.BaseList',

	modelConfig : {
		serviceConfig : {
				'queryLmC' 			: 'zsxyJsyy_queryJsLmC',

			'yy_queryL' 		: 'zsxyYy_queryL',
			'jsyy_queryLByJsId'	: 'zsxyJsyy_queryLByJsId',
			'jsyy_edit'			: 'zsxyJsyy_edit'
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

		//配置
		this.cfg={
			keyId:"ID",title:"菜单授权",

			grid:{
				columns:[
					{header:"名称"		,field:"MC"		,width:100	},
					{header:"描述"		,field:"JS_MS"	,width:200	}
				]
			},

			searchForm:{
				items:[
					{xtype:"formItem",width:160,title:"名称"		,name:"MC"	,type:"text"	,searchOnReady:true},
					{xtype:"panel",layout:{type:"default",middleChilds:true},style:"text-align:left",items:[
						new EG.ui.Button({text:"查询",click:function(){
							me.doSearch();
						}})
					]}
				]
			},

			tools:{
				height:30,
				items:[
//					new EG.ui.Button({text:"应用授权",textStyle:"color:red",click:function(){
//						var v=me.getSelected();
//						if(v==null) return;
//						me.curId=v;
//						me.dJsyy.open();
//					}}),
					new EG.ui.Button({text:"应用授权",textStyle:"color:red",click:function(){
						var v=me.getSelected();
						if(v==null) return;
						MainPage.openPage("Pages.zsxy.afk.Cdgl",{jsId:v},true);
					}}),

					new EG.ui.Button({text:"访客菜单",textStyle:"color:red",style:"margin-left:10px",click:function(){
						MainPage.openPage("Pages.zsxy.afk.Cdgl",{jsId:0},true);
					}}),

					new EG.ui.Button({text:"复制菜单",textStyle:"color:red",style:"margin-left:10px",click:function(){
						var v=me.getSelected();
						if(v==null) return;
						me.curId=v;
						me.fiCopyto.setValue(me.grid.getSelectData("MC"));
						me.dFzcd.open();
					}})
				]
			},

			form:{
				items:[
					{pos:[0,0],xtype:"formItem",title:"名称"		,name:"mc"	,type:"text"	,length:20},
					{pos:[1,0],xtype:"formItem",title:"标识"		,name:"bs"	,type:"text"	,length:20}
				]
			}
		};

		this._init();

		this.buildFzcd();
	},

	/**
	 * 创建身份应用面板
	 */
	buildFzcd:function(){
		var me=this;
		this.dFzcd=new EG.ui.Dialog({
			closeable	:true,
			lock		:true,
			title		:"复制菜单",
			width		:400,
			height		:"auto",
			renderTo	:this.pEle,
			items		:[
				//this.pJsyy=new EG.ui.Panel({width:"100%",height:250,style:"margin:3px;border:1px solid #99BCE8;border-radius:4px;background-color:#DFE9F6;padding:4px"})
				new EG.ui.Panel({
					items:[
						this.fiCopyfrom	=new EG.ui.FormItem({xtype:"formItem",title:"把菜单"		,type:"select",textvalues:[],labelWidth:60}),
						this.fiCopyto	=new EG.ui.FormItem({xtype:"formItem",title:"复制給"	,type:"label",labelWidth:60})
					]
				})
			],
			btns	:[
				{xtype:"button",text:"提交",cls:"eg_button_small",click:function(){
					me.doSubmitFzcd();
				}}
			],
			afterOpen:function(){
				if(!me.loadedJs){
					Sys.call([
						{key:"zsxyJsyyService?queryJsLmC",params:{
							sqlWhere:{},
							sqlGroup:[],
							sqlOrder:{},
							startIndex:0,
							count	:9999
						}}
					],function(data){
						data=data["zsxyJsyyService?queryJsLmC"][0];
						//alert(EG.toJSON(data))
						var tvs=EG.Array.extract2Arrays(data,["MC","ID"]);
						me.fiCopyfrom.prop.prop.setOptions(tvs);
						EG.Locker.lock(false);
					});
					me.loadedJs=true;
				}
			}
		});
	},

	/**
	 * 提交身份应用
	 */
	doSubmitFzcd:function(){
		Sys.call([
			{key:"zsxyAfkCdService?copyTo",params:{destId:this.curId,sourceId:this.fiCopyfrom.getValue()}}
		],function(data){
			EG.Locker.lock(false);
		});
	}
});
