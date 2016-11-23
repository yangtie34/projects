(function(){
	EG.define("Sys.FP.TreeGridChooser",{
		extend:"EG.ui.Item",
		config:{
			title			:null,
			keyName			:null,
			keyValue		:null,
			treeConfig		:null,
			gridConfig		:null,
			searchFormConfig:null,
			popWidth		:500,
			popHeight		:300
		},
		constructor:function(cfg){
			var me=this;
			this.config=cfg;
			this.initConfig(cfg);

			this.element=EG.CE({tn:"div",cn:[
				this.dMask=EG.CE({tn:"div",style:EG.Style.c.dv+";margin-right:10px;"}),
				this.btn=new EG.ui.Button({text:"选择",click:function(){me.pop.open();},cls:"eg_button_small",style:EG.Style.c.dv})
			]});

			this.searchFormConfig.push({xtype:"button",text:"查询",click:function(){me.grid.curPage();}})

			this.pop=new EG.ui.Dialog({
				closeable	:false,
				lock		:true,
				renderTo	:this.parent,
				title		:this.title,
				width		:this.popWidth,
				height		:this.popHeight,
				items:[
					new EG.ui.Panel(EG.copy(this.config,{
						layout		:"border",
						style		:"overflow:auto;border:0px;",
						items:[
							{xtype:"xPanel",region:"left",width:200,style:"margin:2px",title:this.treeTitle,items:[
								this.tree=new Sys.Item.LvTree(
									EG.copy(this.treeConfig,{
										width:"100%",
										height:"100%",
										levelcodeLength:10,
										toExpandLv	:2,
										style		:"overflow:auto;border:0px;",
										onclick:function(){
											me.doSearch();
										}
								}))
							]},
							{xtype:"panel",region:"center",layout:"border",items:[
								this.searchForm=new EG.ui.Form({
									region:"top",
									height:30,
									layout:"line",
									items:this.searchFormConfig
								}),

								this.grid=new EG.ui.Grid(
									EG.copy(this.gridConfig,{
									region:"center",

									remotingCallback	:function(seg){me.setData(seg);},
									pageSize			:30,
									style				:"margin:2px"
								}))
							]}

						]
					},false))
				],
				btns:[
					new EG.ui.Button({text:"选择",click:function(){me.doSelect();}		,cls:"eg_button_small",style:"margin:1px 5px;"}),
					new EG.ui.Button({text:"取消",click:function(){me.pop.close();}		,cls:"eg_button_small",style:"margin:1px 5px;"})
				]
			});
			EG.css(this.pop.dBody,"text-align:left");
			this.tree.load();
		},
		getElement:function(){
			return this.element;
		},
		getValue:function(){
			return this.value;
		},
		setValue:function(value,d){
			this.dMask.innerHTML=d[this.keyName];
			this.value=value;
		},
		doSelect:function(){
			var v=this.grid.getSelectData()[0];
			if(v==null){
				EG.Locker.message("请选择行数据");
				return;
			}
			this.dMask.innerHTML=v[this.keyName];
			if(!EG.String.isBlank(v[this.keyName])){
				EG.css(this.dMask,"display:inline-block");
			}else{
				EG.hide(this.dMask);
			}
			this.value=v[this.keyValue];
			this.pop.close();
		},
		render:function(){
			EG.css(this.dMask,"line-height:"+this.height+"px");
		},
		setData:function(seg){
			var me=this;
			var sqlwhere={};
			var sqlorderby={};
			var sd=this.searchForm.getData();
			var node=this.tree.getSelected();

			if(node){
				var v=node.getValue();
				if(v!=null){
					if(node.isTypeNode){
						sd[this.treeConfig["keyLeveltype"]]=v;
					}else{
						sd[this.treeConfig["searchName"]]=v[this.treeConfig["searchName"]];
					}
				}
			}

			for(var key in sd) {
				if(!EG.String.isBlank(sd[key])) sqlwhere[key]=sd[key];
			}

			EG.Locker.wait("正在加载数据...");
			var me=this;
			Sys.call([
				{key:this.gridConfig["queryRPC"]["service"],params:[sqlwhere,null,sqlorderby,seg*30,30]}
			],function(){
				if(data!=null){
					me.grid.setDataSize(data[1]);
					me.grid.setData(data[0]);
				}
				EG.Locker.lock(false);
			});
		},
		doSearch:function(){
			this.grid.curPage();
		},
		statics:{
			getTitle:function(value,data,cfg){return data?data[cfg["keyName"]]:"";}
		}
	});
})();
