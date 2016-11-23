/**
 * 基础列表类
 */
NS.define('Pages.zsxy.BaseTree',{
	extend : 'NS.mvc.Controller',
	tplRequires : [
		{fieldname : 'xhglYbwbTpl', path : 'app/pages/xg/xjgl/template/xhgl/xhgl_ybwbrs.html'}
	],

	_init:function(){

		this.keyId=this.cfg.keyId;

		this.build();

		if(this.cfg.tree) this.buildTree();

		if(this.cfg.tools) this.buildTools();

		if(this.cfg.form) this.buildForm();

		this.queryData();
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
				{xtype:"panel",height:"auto",layout:{type:"default",middleChilds:true},style:"text-align:left",items:[

				]},
				{xtype:"panel",layout:"border",height:400,items:[
					{region:"left",width:"30%"},
					{region:"right"}
				]}
			],
			renderTo:this.pEle
		});
	},

	/**
	 * 创建树
	 */
	buildTree:function(){
		this.pMain.getItem(2).getItemLeft().addItem(
			{xtype:"XPanel",items:[
				this.tree=new Sys.item.LvTree(
					EG.copy(this.cfg.tree,{
						style		:"margin:1px;overflow:auto;border:0px;",
						dragable	:false,
						deSelectable:false,
						onclick		:this.showNode,
						onclickSrc	:this,
						ondrag		:this.dragNode,
						ondragSrc	:this
					})
				)
			]}
		);
	},

	/**
	 * 显示节点
	 */
	showNode:function(){
		if(!this.checkSelect(false)){
			this.form.clearData();
			//return false;
		}

		this.form.setData(this.tree.getSelected().getValue()||{});
		this.form.setEditable(false);

		EG.hide(this.btnSubmit,this.btnReset);
		this.operType="VIEW";
		this.afterShowNode();
		return true;
	},

	/**
	 * 拖拽节点
	 * @param source
	 * @param dest
	 * @param type
	 * @returns {Boolean}
	 */
	dragNode:function(source,type,dest){
		var me=this;
		var destId=null;
		var a;

		if(dest.isRoot()&&dest.tree.keyLeveltype){
			return;
		}

		if(dest.isTypeNode){
			a=2;//修改类型
			destId=dest.getValue();
		}else{
			if(type=="in") a=0;
			else if(type=="after") a=1;
			else if(type=="before") a=-1;

			if(dest.getValue()!=null){
				destId=dest.getValue()[this.keyId||"id"];
			}
		}

		var sourceId=source.getValue()[this.keyId||"id"];

		if(!confirm("确定移动该节点吗？")) return;

		EG.Locker.wait("正在处理提交的数据...");

		this.callService(
			[
				{key:'moveTo',params:{
					destId	:destId,
					sourceId:sourceId,
					action	:a
				}}
			],
			function(data){
				me.tree.load(function(){
					EG.Locker.message({message:"移动成功",autoclose:true});
				});
			}
		);

		return true;
	},

	/**
	 * 添加节点
	 */
	addNode:function(){
		this.form.reset();
		this.form.setEditable(true);
		EG.show(this.btnSubmit,this.btnReset);
		this.operType="ADD";

		if(!this.tree.getSelected()){
			this.tree.getRootNode().select(true);
		}

		return true;
	},

	/**
	 * 修改节点
	 */
	editNode:function(){
		if(!this.checkSelect(false)){
			EG.Locker.message( "请选择节点!");
			return false;
		}
		this.form.setData(this.tree.getSelected().getValue());
		this.form.setEditable(true);
		EG.show(this.btnSubmit,this.btnReset);
		this.operType="EDIT";
		return true;
	},

	/** 删除 */
	doDelete:function(){
		if(!this.checkSelect(false)){
			EG.Locker.message( "请选择节点!");
			return false;
		}
		if(!confirm("您确定删除该节点吗？")) return;
		var cNode=this.tree.getSelected();
		var v=cNode.getValue();

		EG.Locker.wait("正在处理提交的数据...");

		var me=this;

		this.callService(
			[
				{key:'delete',params:{
					id	:v[this.keyId]
				}}
			],
			function(data){
				cNode.remove();
				me.form.clearData();
				EG.Locker.message({message:"删除成功",autoclose:true});
			}
		);
		return true;
	},

	/**
	 * 重置
	 */
	doReset:function(){
		if(this.operType=="ADD"){
			this.addNode();
		}else if(this.operType=="EDIT"){
			this.editNode();
		}
	},


	/**
	 * 在现实节点后
	 */
	afterShowNode:function(){

	},

	/**
	 * 检测选择节点
	 * @param empty 是否可以为空节点
	 */
	checkSelect:function(empty){
		if(!this.tree.getSelected()) return false;
		if(!empty) if(this.tree.getSelected().getValue()==null) return false;
		return true;
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

		this.pMain.getItem(1).addItem(
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
		this.pMain.getItem(2).getItemRight().addItem([
			this.form=new EG.ui.Form(
				EG.copy(this.cfg.form,{
					region	:"right",
					height	:"auto",
					style	:"margin:3px;border:1px solid #99BCE8;border-radius:4px;background-color:#DFE9F6"
				})
			),
			{region:"button",layout:{type:"default",centerChilds:true},items:[
				this.btnSubmit=new EG.ui.Button({xtype:"button",text:"提交",cls:"eg_button_small",click:function(){
					me.doSubmit();
				}}),
				this.btnReset=new EG.ui.Button({xtype:"button",text:"重置",cls:"eg_button_small",style:"margin-left:5px",click:function(){
					me.doReset();
				}})
			]}
		]);
	},

	/**
	 * 设置表格数据
	 * @param seq
	 */
	setTreeData:function(seq){
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
		this.form.reset();
		this.form.setEditable(true);
		EG.show(this.btnSubmit,this.btnReset);
		this.operType="add";

		if(!this.tree.getSelected()){
			this.tree.getRootNode().select(true);
		}

		return true;
	},

	/**
	 * 打开修改面板
	 */
	openEdit:function(){
		if(!this.checkSelect(false)){
			EG.Locker.message( "请选择节点!");
			return false;
		}
		this.form.setData(this.tree.getSelected().getValue());
		this.form.setEditable(true);
		EG.show(this.btnSubmit,this.btnReset);
		this.operType="edit";
	},

	/**
	 * 执行删除
	 */
	doDelete:function(){
		if(!this.checkSelect(false)){
			EG.Locker.message( "请选择节点!");
			return false;
		}
		if(!confirm("您确定删除该节点吗？")) return;
		var cNode=this.tree.getSelected();
		var v=cNode.getValue();

		EG.Locker.wait("正在处理提交的数据...");

		var me=this;
		//alert("this.cfg.tree.keyId:"+this.cfg.tree.keyId)
		me.callService(
			[{key:'delete',params:{
				id:v[this.cfg.tree.keyId||"id"]
			}}],
			function(data){
				cNode.remove();
				me.form.clearData();
				//EG.hide(me.form.getElement());
				EG.Locker.message({message:"删除成功",autoclose:true});
			}
		)
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
		//校验
		if(!this.form.validate(true)) return;
		var cNode		=this.tree.getSelected();
		var me=this;
		EG.Locker.wait("正在处理提交的数据...");

		this.callService(
			[{key:'add',params:this.getSubmitDataADD()}],
			function(data){
				var nd=data["add"];
				//添加节点
				cNode.add(new EG.ui.TreeNode({
					title		:nd[me.cfg.tree.keyTitle],
					onclick		:me.showNode,
					onclickSrc	:me,
					ondrag		:me.dragNode,
					ondragSrc	:me,
					value		:nd
				}));
				//清空表单
				me.form.clearData();
				EG.Locker.message({message:"添加成功",closetime:1000,callback:function(){
					me.afterSubmitADD(nd);
				}});
			}
		);
	},

	/**
	 * 执行提交:修改
	 */
	doSubmitEdit:function(){
		if(!this.checkSelect(false)) return EG.Locker.message( "请选择节点!");
		//校验
		if(!this.form.validate(true)) return;
		var cNode		=this.tree.getSelected();
		var me=this;
		EG.Locker.wait("正在处理提交的数据...");

		this.callService(
			[{key:'edit',params:this.getSubmitDataEDIT()}],
			function(data){
				var nd=data['edit'];
				//更新节点
				cNode.setTitle(nd[me.cfg.tree.keyTitle||"mc"]);
				cNode.setValue(nd);

				EG.Locker.message({message:"修改成功",closetime:1000,callback:function(){
					me.afterSubmitEDIT(nd);
				}});
			}
		);
	},

	/**
	 * 获取添加的提交数据
	 * @return {Object}
	 */
	getSubmitDataADD:function(){
		var cNode		=this.tree.getSelected();
		var cNodeData	=cNode.getValue();
		var fd=this.form.getData();
		return {
			po:fd,
			pId:(cNodeData!=null)?cNodeData[this.cfg.tree.keyId||this.keyId]:null
		};
	},

	/**
	 * 获取编辑的提交数据
	 * @return {Object}
	 */
	getSubmitDataEDIT:function(){
		var fd=this.form.getData();
		var cNode		=this.tree.getSelected();
		var cNodeData	=cNode.getValue();
		fd[this.keyId]	=cNodeData[this.cfg.tree.keyId||this.keyId];
		return {
			po:fd
		};
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

	queryData:function(){
		EG.Locker.wait("正在加载数据...");
		var me=this;
		this.tree.load(function(){
			EG.Locker.lock(false);
			me.tree.rootNode.doClick();
		});
	},

	/**
	 * 添加提交以后
	 * @param data
	 */
	afterSubmitADD:function(data){
		var me=this;
		var curId=data[this.keyId];
		var cb=null;
		if(data){
			curId=typeof(data)=="string"?data:data[this.keyId];
			cb=function(){
				me.selectNodeAfterSubmit(curId);
			};
		}
		this.tree.load(cb);
	},

	/**
	 * 修改提交以后
	 * @param data
	 */
	afterSubmitEDIT:function(data){
		var me=this;
		var curId=data[this.keyId];
		this.tree.load(function(){
			me.selectNodeAfterSubmit(curId);
		});
	},

	/**
	 * 提交以后选择节点
	 * @param curId
	 */
	selectNodeAfterSubmit:function(curId){
		for(var i=0;i<this.tree.treeNodes.length;i++){
			var node=this.tree.treeNodes[i];
			if(node.value&&node.value[this.keyId]==curId){
				node.expand(true);
				node.doClick();
				break;
			}
		}
	},

	statics:{
		common_style:""
	}
})
