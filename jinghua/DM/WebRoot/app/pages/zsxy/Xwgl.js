/**
 * 附件
 */
var Fj=EG.define({
	extend:"EG.ui.Item",
	config:{
		mc:"mc",
		editor:null
	},
	constructor:function(cfg){
		this.initConfig(cfg);
		this.element=EG.CE({tn:"div"});
		this.vs=[];
	},
	addFj:function(value){
		EG.CE({pn:this.element,tn:"div",cn:[
			{tn:"a",href:"javascript:void(0)",innerHTML:value[this.mc]},
			{tn:"a",href:"javascript:void(0)",innerHTML:"插入",onclick:this.add2Editor	,onclickSrc:this,style:"margin-left:10px"},
			{tn:"a",href:"javascript:void(0)",innerHTML:"删除",onclick:this.delFj		,onclickSrc:this,style:"margin-left:10px"}
		]});
		this.vs.push(value);
	},
	add2Editor:function(e){
		var el=e.target;
		var idx=EG.DOM.getIdx(el.parentNode);
		var v=this.vs[idx];
		var editor=typeof(this.editor)=="function"?this.editor():this.editor;
		editor.execute("image",{
			src:v["lj"]
		});
	},
	delFj:function(e){
		var el=e.target;
		var idx=EG.DOM.getIdx(el.parentNode);
		EG.DOM.remove(el.parentNode);
		EG.Array.del(this.vs,idx);
	},
	getValue:function(){
		return this.vs;
	},
	setValue:function(value,d){
		EG.DOM.removeChilds(this.element);
		EG.Array.clear(this.vs);
		if(value==null) return;
		for(var i=0;i<value.length;i++){
			this.addFj(value[i]);
		}
	},
	render:function(){

	},
	statics:{
		getTitle:function(value,data,cfg){
			if(value==null) return "";
			var s="";
			for(var i=0;i<value.length;i++){
				if(i!=0) s+="<br/>";
				s+=value[i]["mc"];
			}
			return s;
		}
	}
});

/**
 * 通知管理
 */
NS.define('Pages.zsxy.Xwgl',{
	extend : 'Pages.zsxy.BaseList',

	modelConfig : {
		serviceConfig : {
			'queryLmC' 			: 'zsxyXwService?queryLmC',
			'add' 				: 'zsxyXwService?add',
			'edit' 				: 'zsxyXwService?edit',
			'delete' 			: 'zsxyXwService?delete',
			'queryById' 		: 'zsxyXwService?queryMById',
			'queryXwlbL' 		: 'zsxyXwlbService?queryL'
		}
	},

	init : function(){
		var me=this;

		this.initPage();

		this.lbs=[];
		//类别标识s
		this.lbBss=[["请选择",""]];

		//FN:查看
		var openView=function(e){
			me.grid.selectRow(this.idx,true,true);
			me.openView();
			EG.Event.stopPropagation(e);
		};

		//配置
		this.cfg={
			keyId:"id",title:"新闻管理",

			grid:{
				columns:[
					{header:"发布时间"	,field:"fbrq"		,width:120	},
					{header:"发布人"		,field:"fbrMc"		,width:80	},
					{header:"类别"		,field:"lbMc"		,width:100	},
					{header:"标题"		,field:"bt"			,width:200	},
					{header:"操作"		,field:"oper"		,width:120	,handle:function(data,g){
						return EG.CE({tn:"a",cls:"zsxy_orange",idx:g,href:"javascript:void(0)",onclick:openView,innerHTML:"查看"});
					}}
				]
			},

			searchForm:{
				items:[
					{xtype:"formItem",width:160,title:"标题"		,name:"bt"	,type:"text"	,searchOnReady:true},
					{xtype:"formItem",width:160,title:"时间"		,name:"fbrq",type:"text"	,searchOnReady:true},
					{xtype:"formItem",width:160,title:"类别"		,name:"lbBs",type:"select"	,textvalues:this.lbBss,searchOnReady:true},
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
				labelWidth:60,
				items:[
					{pos:[0,0],xtype:"formItem",title:"标题"		,name:"bt"		,type:"text"	,length:20},
					{pos:[0,1],xtype:"formItem",title:"栏目"		,name:"lbId"	,type:"select"	,textvalues:this.lbs},
					{pos:[1,0],xtype:"formItem",title:"日期"		,name:"fbrq"	,type:"date"	,fmt:"YMDHMS"},
					{pos:[1,1],xtype:"formItem",title:"来源"		,name:"fbly"	,type:"text"	,length:20},
					{pos:[2,0],xtype:'formItem',title:'摘要'		,name:'zy',type:'textarea',height:120	},
					//{pos:[2,0],xtype:"formItem",title:"摘要"		,name:"zy" 	,type:"editor"	,height:120	,pluginGroupName:"simple"},
					
					{pos:[3,0],xtype:"formItem",title:"内容"		,name:"nr"	,type:"editor"	,height:200,typeCfg:{
						uploadAction:"common_upload?policy=zsxyXwfjUP",onUploaded:function(r){
							//alert(EG.toJSON(fj))
							var fj={
								mc:r["filename"],
								lj:r["path"]
							};
							me.form.getFormItem("xwfj").prop.addFj(fj);
						}
					}},
					{pos:[4,0],xtype:"formItem",title:"附件"		,name:"xwfj"	,type:Fj	,height:100,typeCfg:{
						editor:function(){
							return me.form.getFormItem("nr").prop.prop;
						}
					}},
					{pos:[5,0],xtype:"formItem",title:"关键字"	,name:"gjz"	,type:"text"	,length:20}
				]
			}
		};

		this.callService(
			[
				{key:'queryXwlbL',params:{}}
			],
			function(data){
				var d=data["queryXwlbL"];
				EG.Array.addAll(me.lbs	,EG.Array.extract2Arrays(d,["mc","id"]));
				EG.Array.addAll(me.lbBss,EG.Array.extract2Arrays(d,["mc","bs"]));
				me._init();
			}
		);
	},

	getSubmitAddData:function(){
		var fd=this.callParent();
		fd["htmleditFlag"]="true";
		return fd;
	},

	getSubmitEditData:function(){
		var fd=this.callParent();
		fd["htmleditFlag"]="true";
		return fd;
	}
});
