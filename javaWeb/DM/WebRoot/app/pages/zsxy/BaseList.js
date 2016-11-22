/**
 * 基础列表类
 */
NS.define('Pages.zsxy.BaseList',{
	extend : 'NS.mvc.Controller',

	tplRequires : [
		{fieldname : 'xhglYbwbTpl', path : 'app/pages/xg/xjgl/template/xhgl/xhgl_ybwbrs.html'}
	],

	_init:function(){

		this.build();

		if(this.cfg.grid) this.buildGrid();

		if(this.cfg.searchForm) this.buildSearchForm();

		if(this.cfg.tools) this.buildTools();

		if(this.cfg.form) this.buildForm();

		this.grid.go(0);
	},

	initPage:function(){
		this.page = new NS.container.Container();
		this.setPageComponent(this.page);
		this.pEle=this.page.component.getEl().dom;

		//重置一些受EXT CSS影响下的样式(box-sizing)
		EG.Style.addCls(this.pEle,"zsxy_panelInner");
	},

	/**
	 * 创建
	 */
	build:function(){
		var me=this;
		this.pMain=new EG.ui.Panel({
			width:"100%",
			height:"100%",
			layout:{
				type:"line",
				direct:"V"
			},
			cls:"zsxy_container",
			items:[
				{xtype:"panel",cls:"zsxy_top",height:44,cn:[
					{tn:"h3",cls:"floatRight",cn:[
						{tn:"span",cls:"zsxy_term",innerHTML:""},
						{tn:"span",cls:"zsxy_marginLeft",innerHTML:""}
					]},
					{tn:"h1",cn:[
						{tn:"span",cls:"zsxy_model",innerHTML:this.cfg.title,onclick:function(){
							me.initData();
						}},
						{tn:"span",cls:"zsxy_user",innerHTML:""},
						{tn:"span",cls:"zsxy_dept"}
					]}
				],style:"margin-bottom:20px"},
				{xtype:"panel",height:"auto"},
				{xtype:"panel",height:"auto",layout:{type:"default",middleChilds:true},style:"text-align:left",items:[

				]},
				{xtype:"panel",height:400}
			],
			renderTo:this.pEle
		});
	},

	/**
	 * 创建表格
	 */
	buildGrid:function(){
		var me=this;
		this.pMain.getItem(3).addItem(
			this.grid=new EG.ui.Grid(
				EG.copy(this.cfg.grid,{
					width:"100%",
					height:400,
					remotingCallback	:function(seg){
						me.setGridData(seg);
					},
					pageSize			:30
				})
			)
		);
	},

	/**
	 * 创建查询表单
	 */
	buildSearchForm:function(){
		var me=this;

		for(var i=0;i<this.cfg.searchForm.items.length;i++){
			var it=this.cfg.searchForm.items[i];
			if(it.searchOnReady){
				it.page=this;
				if(it.type=="text"||it.type=="date"){
					it.onkeyup=this.search_text;
				}else if(it.type=="select"){
					it.onchange=this.search_select;
				}
			}
		}

		this.pMain.getItem(1).addItem(
			this.searchForm=new EG.ui.Form(
				EG.copy(this.cfg.searchForm,{
					width:"100%",
					height:40,
					layout:"line",
					style:"border:1px solid #797979;padding:7px"
				})
			)
		)
	},

	/**
	 * 创建工具栏
	 */
	buildTools:function(){
		var me=this;

		var its=this.cfg.tools.items;
		for(var i=0;i<its.length;i++){
			//查看
			if(its[i]=="view"){
				its[i]=new EG.ui.Button({text:"查看",click:function(){
					me.openView();
				},style:"display:inline-block;margin-right:10px;",textStyle:"color:#009900"});
			}

			//添加
			if(its[i]=="add"){
				its[i]=new EG.ui.Button({text:"添加",click:function(){
					me.openAdd();
				},style:"display:inline-block;margin-right:10px;",textStyle:"color:#009900"});
			}

			//修改
			if(its[i]=="edit"){
				its[i]=new EG.ui.Button({text:"修改",click:function(){
					me.openEdit();
				},style:"display:inline-block;margin-right:10px;",textStyle:"color:#009900"});
			}

			//删除
			if(its[i]=="delete"){
				its[i]=new EG.ui.Button({text:"删除",click:function(){
					me.doDelete();
				},style:"display:inline-block;margin-right:10px;",textStyle:"color:#009900"});
			}
		}

		this.pMain.getItem(2).addItem(
			this.pTools=new EG.ui.Panel(
				EG.copy(this.cfg.tools,{
					layout:{type:"default",middleChilds:true},style:"text-align:left"
				})
			)
		);
	},

	/**
	 * 创建操作表单
	 */
	buildForm:function(){
		var me=this;
		this.dForm=new EG.ui.Dialog({
			closeable	:true,
			lock		:true,
			title		:"操作",
			width		:700,
			height		:"auto",
			renderTo	:this.pEle,
			items		:[
				this.form=new EG.ui.Form(
					EG.copy(this.cfg.form,{
						height:"auto",
						style:"margin:3px;border:1px solid #99BCE8;border-radius:4px;background-color:#DFE9F6"
					})
				)
			],
			btns	:[
				{xtype:"button",text:"提交",cls:"eg_button_small",click:function(){
					me.doSubmit();
				}}
			]
		});
	},

	/**
	 * 设置表格数据
	 * @param seq
	 */
	setGridData:function(seq){
		var me=this;
		var sqlWhere={};
		if(this.searchForm){
			var fd=this.searchForm.getData();
			for(var k in fd){
				if(fd[k]!="") sqlWhere[k]=fd[k];
			}
		}
		this.callService(
			[
				{key:'queryLmC',params:{
					sqlWhere:sqlWhere,
					sqlGroup:[],
					sqlOrder:{},
					startIndex:seq*me.grid.pageSize,
					count	:me.grid.pageSize
				}}
			],
			function(data){

				var d=data["queryLmC"];
				//alert(EG.toJSON(d));
				me.grid.setDataSize(d[1]);
				me.grid.setData(d[0]);
			}
		)
	},

	/**
	 * 打开查看面板
	 */
	openView:function(){
		var me=this;
		var v=this.getSelected();
		if(v==null) return;
		EG.Locker.wait();
		this.callService(
			[{key:'queryById',params:{id:v}}],
			function(data){
				me.dForm.open();
				me.form.setData(data["queryById"]);
				me.form.setEditable(false);
				EG.Locker.lock(false);
			}
		);
		EG.hide.apply(this,this.dForm.dFoot.childNodes);
	},

	/**
	 * 打开添加面板
	 */
	openAdd:function(){
		this.operType="add";
		this.dForm.open();
		this.form.clearData();
		this.form.setEditable(true);
		EG.show.apply(this,this.dForm.dFoot.childNodes);
	},

	/**
	 * 打开修改面板
	 */
	openEdit:function(){
		var me=this;
		var v=this.getSelected();
		if(v==null) return;
		this.operType="edit";
		this.curId=v;
		EG.Locker.wait();
		this.callService(
			[{key:'queryById',params:{id:v}}],
			function(data){
				me.dForm.open();
				me.form.setData(data["queryById"]);
				this.form.setEditable(true);
				EG.Locker.lock(false);
			}
		);
		EG.show.apply(this,this.dForm.dFoot.childNodes);
	},

	/**
	 * 执行删除
	 */
	doDelete:function(){
		var me=this;
		var v=this.getSelected({
			multi:true
		});
		if(v==null) return;

		if(!confirm("确定要删除吗?")) return;

		EG.Locker.wait();
		me.callService(
			[{key:'delete',params:{
				ids:v
			}}],
			function(data){
				EG.Locker.message({
					message:"操作成功",
					autoclose:true,
					callback:function(){
						me.grid.curPage();
					}
				});
			}
		)
	},

	/**
	 * 选择
	 * @param {Object?} cfg
	 */
	getSelected:function(cfg){
		if(!cfg) cfg={};
		EG.copy(cfg,{
			multi		:false,
			msgSelect	:"请选择操作项",
			msgOne		:"您只能选择一条待操作项",
			key			:this.cfg.keyId,
			noMsgOnEpmty:false
		},false);

		var sd=this.grid.getSelectData(cfg["key"]);
		if (sd == null || sd.length == 0) {
			if(cfg["noMsgOnEpmty"]) return null;
			EG.Locker.message({message:cfg["msgSelect"],autoclose:true});
			return null;
		}
		if (!cfg["multi"] && sd.length > 1) {
			if(cfg["noMsgOnEpmty"]) return null;
			EG.Locker.message({message:cfg["msgOne"],autoclose:true});
			return null;
		}

		if (!cfg["multi"]) return sd[0];
		return sd;
	},

	/**
	 * 执行提交
	 */
	doSubmit:function(){
		if(this.operType=="add"){
			this.doSubmitAdd();
		}else if(this.operType=="edit"){
			this.doSubmitEdit();
		}
	},

	/**
	 * 执行提交:添加
	 */
	doSubmitAdd:function(){
		var me=this;

		var fd=this.getSubmitAddData();
		if(!fd) return;

//		alert(EG.toJSON(this.form.getData()))
//		if(true) return;
		EG.Locker.wait();
		this.callService(
			[{key:'add',params:fd}],
			function(data){
				EG.Locker.message({
					message:"添加成功",
					autoclose:true,
					callback:function(){
						me.dForm.close();
						me.grid.curPage();
						EG.Locker.lock(false);
					}
				})
			}
		);
	},

	getSubmitAddData:function(){
		if(!this.form.validate(true)) return;
		return this.form.getData();
	},

	/**
	 * 执行提交:修改
	 */
	doSubmitEdit:function(){
		var me=this;
		var fd=this.getSubmitEditData();
		if(!fd) return;
		EG.Locker.wait();
		this.callService(
			[{key:'edit',params:fd}],
			function(data){
				EG.Locker.message({
					message:"修改成功",
					autoclose:true,
					callback:function(){
						me.dForm.close();
						me.grid.curPage();
						EG.Locker.lock(false);
					}
				})
			}
		);
	},

	getSubmitEditData:function(){
		if(!this.form.validate(true)) return;
		var fd=this.form.getData();
		fd[this.cfg.keyId]=this.curId;
		return fd;
	},

	doSearch:function(){
		this.grid.curPage();
	},

	search_text:function(e){
		var e=EG.Event.getEvent(e);
		if(e.keyCode==13){
			this.item.page.searchType="se";
			this.item.page.doSearch();
		}
	},
	search_select:function(e){
		this.page.searchType="se";
		this.page.doSearch();
	},
	statics:{
		common_style:""

	}
})
