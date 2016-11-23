/**
 * 范围选择
 * @type {*|Class}
 */
var FwChooser=EG.define({
	extend:"EG.ui.Item",
	config:{
		popWidth		:500,
		popHeight		:300,
		parent			:null
	},
	constructor:function(cfg){
		var me=this;
		this.config=cfg;
		this.initConfig(cfg);

		this.element=EG.CE({tn:"div",cn:[
			this.dMask=EG.CE({tn:"div",style:EG.Style.c.dv+";margin-right:10px;max-width:200px;overflow:hidden;height:24px;"}),
			this.btn=new EG.ui.Button({text:"选择",click:function(){me.pop.open();},cls:"eg_button_small",style:EG.Style.c.dv})
		]});

		this.types=[["全部","G"],["人员类别","RYLB"]];

		this.typeTvs={
			G:[["全部人","G"]],
			RYLB:[["教师","JS"],["学生","XS"],["职工","ZG"],["管理员","GLY"]]
		};

		this.pop=new EG.ui.Dialog({
			style		:"z-index:1",
			closeable	:false,
			lock		:true,
			renderTo	:this.parent,
			title		:"范围",
			width		:this.popWidth,
			height		:this.popHeight,
			items:[
				new EG.ui.Panel(EG.copy(this.config,{
					layout		:"border",
					style		:"overflow:auto;margin:3px;border:1px solid #99BCE8;border-radius:4px;background-color:#DFE9F6",
					items:[
						{xtype:"panel",region:"top",style:"margin:2px",height:40,items:[
							this.sltType=new EG.ui.FormItem({title:"类型",type:"select",textvalues:this.types,onchange:function(v){
									var slvs=me.sa.getValue();
									var tvs=me.typeTvs[v];
									me.sa.setTextvalues(tvs);
							}})
						]},
						{xtype:"panel",region:"center",style:"text-align:center",items:[
							this.sa=new EG.ui.SelectArea({xtype:"selectArea",style:"margin:2px"})
						]}
					]
				},false))
			],
			btns:[
				new EG.ui.Button({text:"选择",click:function(){me.doSelect();}		,cls:"eg_button_small",style:"margin:1px 5px;"}),
				new EG.ui.Button({text:"取消",click:function(){me.pop.close();}		,cls:"eg_button_small",style:"margin:1px 5px;"})
			]
		});

		this.sltType.prop.prop.doOnChange("G");

		EG.css(this.pop.dBody,"text-align:left");
	},
	getElement:function(){
		return this.element;
	},
	getValue:function(){
		var v1=this.sltType.getValue();
		var v2=this.sa.getValue();
		var vs=[];
		for(var i=0;i<v2.length;i++){
			vs.push([v1,v2[i]]);
		}
		return vs;
	},
	setValue:function(value,d){
		if(!d) return;
		var v=d["tzfw"];
		if(!v||v.length==0) return;

		var v1=v[0]["lb"];
		var v2=EG.Array.extract(v,["lbz"]);

		this.sltType.setValue(v1);
		this.sa.setValue(v2);

		this.dMask.innerHTML=d["tzfwMc"];

		this.value=v;
	},
	doSelect:function(){
		var vtype=this.sltType.getValue();
		var text="";
		var v=[];
		if(vtype=="RYLB"){

			var rylbs=this.sa.getValue();
			for(var i=0;i<rylbs.length;i++){
				v.push({
					lb:vtype,
					lbz:rylbs[i]
				});
			}
		}else if(vtype=="G"){
			v.push({
				lb:vtype
			});
		}
		text=this.sa.getText();
		this.dMask.innerHTML=text;
		if(text){
			EG.css(this.dMask,"display:inline-block");
		}else{
			EG.hide(this.dMask);
		}
		this.value=v;
		this.pop.close();
	},
	render:function(){
		EG.css(this.dMask,"line-height:"+this.height+"px");
	},
	statics:{
		getTitle:function(value,data,cfg){return "";}
	}
});

/**
 * 附件
 */
var Fj=EG.define({
	extend:"EG.ui.Item",
	config:{
		fjMc:"mc",
		editor:null
	},
	constructor:function(cfg){
		this.initConfig(cfg);
		this.element=EG.CE({tn:"div"});
		this.vs=[];
	},
	addFj:function(value){
		EG.CE({pn:this.element,tn:"div",cn:[
			{tn:"a",href:"javascript:void(0)",innerHTML:value[this.fjMc]},
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
NS.define('Pages.zsxy.Tzgl',{
	extend : 'Pages.zsxy.BaseList',

	modelConfig : {
		serviceConfig : {
			'queryLmC' 			: 'zsxyTzService?queryLmC',
			'add' 				: 'zsxyTzService?add',
			'delete' 			: 'zsxyTzService?delete',
			'queryById' 		: 'zsxyTzService?queryMById',
			'queryTzlbL' 		: 'zsxyTzlbService?queryL'
		}
	},

	init : function(){
		var me=this;

		this.initPage();

		this.lbs=[];

		//FN:查看
		var openView=function(e){
			me.grid.selectRow(this.idx,true,true);
			me.openView();
			EG.Event.stopPropagation(e);
		};

		//配置
		this.cfg={
			keyId:"ID",title:"通知管理",

			grid:{
				columns:[
					{header:"发布时间"	,field:"FBRQ"		,width:120	},
					{header:"发布人"		,field:"FBR_MC"		,width:80	},
					{header:"类别"		,field:"LB_MC"		,width:100	},
					{header:"标题"		,field:"BT"			,width:200	},
					{header:"操作"		,field:"oper"		,width:120	,handle:function(data,g){
						return EG.CE({tn:"a",cls:"zsxy_orange",idx:g,href:"javascript:void(0)",onclick:openView,innerHTML:"查看"});
					}}
				]
			},

			searchForm:{
				items:[
					{xtype:"formItem",width:160,title:"标题"		,name:"bt"	,type:"text"	,searchOnReady:true},
					{xtype:"formItem",width:160,title:"时间"		,name:"fbrq",type:"text"	,searchOnReady:true},
					{xtype:"panel",layout:{type:"default",middleChilds:true},style:"text-align:left",items:[
						new EG.ui.Button({text:"查询",click:function(){
							me.doSearch();
						}})
					]}
				]
			},

			tools:{
				height:30,
				items:["view","add","delete"]
			},

			form:{
				labelWidth:60,
				items:[
					{pos:[0,0],xtype:"formItem",title:"标题"		,name:"bt"	,type:"text"	,length:20},
					{pos:[1,0],xtype:"formItem",title:"类别"		,name:"lbId",type:"select"	,textvalues:this.lbs},
					{pos:[1,1],xtype:"formItem",title:"接收人"	,name:"tzfw",type:FwChooser	,length:20,typeCfg:{parent:this.pEle}},
					{pos:[2,0],xtype:"formItem",title:"内容"		,name:"nr"	,type:"editor"	,height:200,typeCfg:{
						uploadAction:"common_upload?policy=zsxyTzfjUP",onUploaded:function(r){
							//alert(EG.toJSON(fj))
							var fj={
								mc:r["filename"],
								lj:r["path"]
							};
							me.form.getFormItem("tzfj").prop.addFj(fj);
						}
					}},
					{pos:[3,0],xtype:"formItem",title:"附件"		,name:"tzfj"	,type:Fj	,height:100,typeCfg:{
						editor:function(){
							return me.form.getFormItem("nr").prop.prop;
						}
					}}
				]
			}
		};

		this.callService(
			[
				{key:'queryTzlbL',params:{}}
			],
			function(data){
				var d=data["queryTzlbL"];
				EG.Array.addAll(me.lbs,EG.Array.extract2Arrays(d,["mc","id"]));
				me._init();
			}
		)
	},

	getSubmitAddData:function(){
		if(!this.form.validate(true)) return;
		var fd=this.form.getData();
		fd["htmleditFlag"]="true";

		return fd;
	}
//
//
//
//	getSubmitEditData:function(){
//		if(!this.form.validate(true)) return;
//		var fd=this.form.getData();
//		fd[this.cfg.keyId]=this.curId;
//		return fd;
//	}
});
