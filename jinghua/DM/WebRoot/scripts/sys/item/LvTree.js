(function(){
	EG.define("Sys.item.LvTree",{
		extend:"EG.ui.Tree",
		config:{
			keyTitle		:"mc",
			keyLevelcode	:"qxm",
			keyId			:"id",
			levelcodeLength	:6,
			service			:null,
			toExpandLv		:null,
			//cls:"eos_lvtree",
			rootTitle		:"根目录",			//根节点标题
			dragable		:false,				//是否可以拖拽
			ondrag			:null,				//拖拽时事件
			onload			:null,
			onsetValue		:null,

			filterData		:null,				//过滤数据

			keyLeveltype	:null,				//级层类别键值
			leveltypes		:null
		},
		constructor:function(cfg){
			this.initConfig(cfg);
			this.callSuper([cfg]);

			if(this.keyLeveltype){
				this.buildTypeNode();
			}

		},

		/**
		 * 建立类别节点
		 */
		buildTypeNode:function(){
			this.mLvTypes={};

			for(var i=0;i<this.leveltypes.length;i++){
				var lt=this.leveltypes[i];
				var node=new EG.ui.TreeNode({
					title		:lt[0],
					onclick		:this.onclick,
					onclickSrc	:this.onclickSrc,
					ondrag		:this.ondrag,
					ondragSrc	:this.ondragSrc,
					isTypeNode 	:true,
					value		:lt[1],
					titleStyle	:"font-weight:bold;"
				});
				this.mLvTypes[lt[1]]=node;

				this.rootNode.add(node);
			}
		},

		/**
		 * 加载
		 * @param {Function?} cb 回调
		 */
		load:function(cb){
			this.reset();

			//创建级层类别节点
			if(this.keyLeveltype){
				this.buildTypeNode();
			}

			var me=this;

			Sys.call([
					{key:this.service.key,params:(typeof(this.service.params)=="function")?this.service.params():this.service.params}
				],
				function(data){
					//alert(EG.toJSON(data));
					data=data[me.service.key];
					//标志加载完毕
					me.loadedData=true;

					//过滤数据
					if(this.filterData){
						data=this.filterData.apply(this,[data]);
					}

					me.buildNodes(data);
					if(me.onload) me.onload.apply(me,[data]);
					if(cb) cb();
				}
			);
		},

		/**
		 * 创建节点
		 * @param datas
		 */
		buildNodes:function(datas){
			var me=this;

			//循环创建节点
			for(var i=0,il=datas.length;i<il;i++){
				var d			=datas[i];
				var levelcode	=d[this.keyLevelcode];
				var tnk			=this.keyLeveltype?d[this.keyLeveltype]:null;

				//子节点
				var treeNode=new EG.ui.TreeNode({
					title		:d[this.keyTitle],
					onclick		:this.onclick,
					onclickSrc	:this.onclickSrc,
					value		:d,
					ondrag		:this.ondrag,
					ondragSrc	:this.ondragSrc
				});
				//alert(EG.toJSON(d));
				//找父节点
				var pNode=null;
				for(var j=0,jl=this.treeNodes.length;j<jl;j++){
					var jV=this.treeNodes[j].value;
					if(!jV) continue;


					//判断是否类别一致
					if(this.keyLeveltype){
					   if(jV[this.keyLeveltype]!=tnk){
						   continue;
					   }
					}

					//判断是否有级层编码
					var pLevelcode=jV[this.keyLevelcode];
					if(!pLevelcode) continue;

					//
					if(   levelcode&&pLevelcode
						&&levelcode.length>pLevelcode.length
						&&levelcode.indexOf(pLevelcode)==0
						&&levelcode.length==pLevelcode.length+this.levelcodeLength
						){
						pNode=this.treeNodes[j];
						break;
					}
				}
				if(!pNode){
					if(this.keyLeveltype){
						pNode=this.mLvTypes[tnk];
					}else{
						pNode=this.getRootNode();
					}
				}

				pNode.add(treeNode);
			}

			//自动展开到指定级层
			if(this.toExpandLv!=null){
				this.collapseAll();
				this.expandLv(this.toExpandLv);
			}

			//异步加载以后同步设置数据
			if(this.cacheValue!=null){
				me.setValue(me.cacheValue);
			}
		},

		/**
		 * 设置值
		 * @param value
		 * @param {Object?} d
		 */
		setValue:function(value,d){
			//如果未加载过,待load
			if(!(this.loadedData)){
				this.cacheValue=value;
				return;
			}
			//筛选待选中节点
			this.value=value;//TODO ?

			var sNode	=this.multiple?[]:null;
			var sValue	=this.multiple?[]:null;

			for(var i=0,il=this.treeNodes.length;i<il;i++){
				var node=this.treeNodes[i];
				var v=node.value;
				if(!v) continue;
				if(this.multiple){
					for(var j=0;j<value.length;j++){
						if(v[this.keyId]==value[j]){
							node.select(true);
							sNode.push(node);
							sValue.push(v);
							break;
						}
					}
				}else{
					if(v[this.keyId]==value){
						node.select(true);
						sNode=node;
						sValue=v;
						break;
					}
				}
			}

			if((this.multiple&&sNode.length>0)||(!this.multiple&&sNode)){
				if(this.onsetValue) this.onsetValue.apply(this,[sValue,d,sNode]);
			}
			//this.expandNode(sNode);
			this.cacheValue=null;
		},

		/**
		 * 获取值
		 * @param node
		 * @returns {*}
		 */
		getValue:function(node){
			var v;
			if(!node) node=this.getSelected();
			if(!node) return null;
			if(this.multiple){
				v=[];
				for(var i=0;i<node.length;i++){
					var nv=node[i].getValue();
					if(!nv) continue;
					v.push(nv[this.keyId]);
				}
				return v;
			}else{

				if(!(v=node.getValue())) return null;
				return v[this.keyId];
			}

		},
		getText:function(){
			if(!this.multiple){
				var node=this.getSelected();
				return node?node.title:null;
			}else{
				var nodes=this.getSelected();
				var ts=[];
				for(var i=0;i<nodes.length;i++){
					ts.push(nodes[i].title);
				}
				return ts;
			}
		},
		getNode:function(value){
			for(var i=0,il=this.treeNodes.length;i<il;i++){
				var node=this.treeNodes[i];
				if(typeof("value")=="function"){
					if(value(node)==true) return node;
				}else{
					if(node.value&&node.value[this.keyId]==value) return node;
				}

			}
			return null;
		}
	});
})();
