(function(){
	/**
	 * EG.UI UI包
	 */
	EG.define("EG.UI",{
		statics:{
			//皮肤
			skin:"default",
			/**
			 * 获取皮肤路径
			 */
			getSkinPath:function(ui) {
				return EG.basePath + "/skins/" + EG.UI.skin + "/" + ui;
			},
			sheet:null,
			/**
			 * 设置Skin
			 * @param skin
			 */
			setSkin:function(skin) {
				if(!EG.UI.sheet){
					EG.UI.sheet=EG.Style.createSheet("");
				}
				EG.UI.sheet.href=EG.basePath + "/skins/" + skin + "/ui.css";
				EG.UI.skin=skin;
			},
			//全局组件索引
			GITEMIDX:0,
			//全局组件集合
			GITEMS:{}
		}
	});

	//是否正在变化尺寸
	EG.RESIZING=false;

	/**
	 * 设置默认样式
	 */
	EG.onload(function(){
		//EG.UI.setSkin(EG.UI.skin);
	});
})();
/**
 * @class EG.ui.Item
 * @author bianrongjun
 * 组件
 */
(function(){
	EG.define("EG.ui.Item",{
		config:{
			/** @cfg {?HTMLElement} renderTo 被添加到某节点下，并被渲染 */
			renderTo	:null,
			/** @cfg {?String} width ID */
			id			:null,
			/** @cfg {?String|?Number} width 宽度 */
			width		:null,
			/** @cfg {?String|?Number} height 高度 */
			height		:null,
			/** @cfg {?String} style 样式 */
			style		:null,
			/** @cfg {?String} cls CSS样式类 */
			cls			:null,
			/** @cfg {?String} region 区域 */
			region		:null,
			/** @cfg {?String} vAlign 垂直方式 */
			vAlign		:null,
			/** @cfg {?Boolean} hidden 是否隐藏 */
			hidden		:false
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			//初始化配置
			this.initItem(cfg);
			//创建Element
			this.build();

			//绑定Element与Item
			this.getElement().item=this;

			//设置ID
			if(this.id) this.getElement().id = this.id;
			//设置样式
			Item.setStyle(this);
			//执行创建后动作
			this.afterBuild();
			//renderTo
			Item.doRenderTo(this);

			//自动隐藏
			if(this.hidden) EG.hide(this.getElement());
		},

		/**
		 * 创建后的动作
		 */
		afterBuild:function(){},

		/**
		 * 获取要样式化的元素
		 * @returns {HTMLElement}
		 */
		getStyleEle:function(){
			return this.getElement();
		},

		/**
		 * 获取Element
		 * @returns {HTMLElement}
		 */
		getElement	:function(){
			return this.element;
		},

		/** 渲染 */
		render		:function(){},

		/**
		 * 初始化Item
		 * @param {Object} config 配置
		 */
		initItem:function(config){
			this.initConfig(config);
			this.oWidth		=this.width;	//原宽
			this.oHeight	=this.height;	//原高
			this.pItem		=null;			//父组件
		},

		/**
		 * 获取原始尺寸
		 * @return {*}
		 */
		getOWidth:function(){
			return this.oWidth;
		},

		/**
		 * 获取原始宽度
		 * @return {*}
		 */
		getOHeight:function(){
			return this.oHeight;
		},

		/**
		 * 获取元素尺寸
		 * @return {Object}
		 */
		getSize:function(){
			return EG.getSize(this.getElement());
		},

		/**
		 * 构建
		 */
		build:function(){
			this.element 		=EG.CE({tn : "div"});
		},

		/**
		 * 销毁
		 */
		destroy:function(){

		},

		/**
		 * 设置Hidden
		 * @param hidden
		 */
		setHidden:function(hidden){
			this.hidden=hidden;
			if(this.hidden){
				EG.hide(this.getElement());
			}else{
				EG.show(this.getElement());
			}
		},

		/**
		 * 是组件
		 */
		isItem:true,

		statics:{
			itemClasses:{},

			/**
			 * 注册部件
			 * @param {String} type 类型
			 * @param {Class} itemClass 部件类
			 */
			regist : function(type, itemClass) {
				EG.ui.Item.itemClasses[type] = itemClass;
			},

			/**
			 * 创建组件
			 * @param {String} xtype 类型
			 * @param {Object} cfg 配置
			 * @return {*}
			 */
			create:function(xtype,cfg){
				if(!xtype) xtype="panel";
				var itemClass=Item.itemClasses[xtype]||EG.ui[EG.Word.first2Uppercase(xtype)];
				if(!itemClass) throw new Error("EG.ui.Item#create:不支持类型:" + xtype);
				return new itemClass(cfg);
			},

			/**
			 * 设置尺寸:宽
			 * @param {HTMLElement} element 元素
			 * @param {Number|String} width 宽
			 */
			setWidth:function(element,width){
				EG.ui.Item.setSize(element,width,"width");
			},

			/**
			 * 设置尺寸:长
			 * @param {HTMLElement} element 元素
			 * @param {Number|String} height 高
			 */
			setHeight:function(element,height){
				EG.ui.Item.setSize(element,height,"height");
			},

			/**
			 * 设置样式
			 * @param {EG.ui.Item} item 部件
			 */
			setStyle:function(item){
				//设定样式类
				if(item.cls) EG.setCls(item.getStyleEle(),item.cls);
				//设置样式
				if(item.style) EG.css(item.getStyleEle(),item.style);
			},

			/**
			 * 初始化Item
			 * @param {EG.ui.Item} item 部件
			 */
			initItem:function(item){
				//自动renderTo
				if(item.renderTo){
					if(item.element.parentNode==null){
						if(EG.ui.Item.isItem(item.renderTo)){
							item.renderTo.addItem(item);
						}else if(EG.DOM.isElement(item.renderTo)){
							item.renderTo.appendChild(item.element);
						}
					}
					item.render();
				}
			},
			
			/**
			 * 设置尺寸
			 * @param {HTMLElement} element Element
			 * @param {String|Number} value 数值
			 * @param {String} type 种类
			 */
			setSize:function(element,value,type){
				if (value != null) {
					if (typeof (value) == "number"){
						EG.css(element, type+":" + value + "px");
					}else{
						EG.css(element, type+":" + value);
					}
				} else{
					EG.css(element, type+":100%");//TODO 取消自动放大
				}
			},
			
			/**
			 * 计算出element要适应的大小,保持原样式的padding,margin,bodrer值
			 * @param {Object} cfg 配置
			 * @return {Object}
			 */
			fit:function(cfg){
				if(Item.isItem(cfg)) cfg={item:cfg};
				var item		=cfg["item"],
					element		=cfg["element"];

				if(!element){
					if(item) element=item.element;
					else throw new Error("fit:没有element");
				}

				//原始尺寸,必须含有innerWidth,outerWidth||innerHeight,outerHeight
				var sSize=cfg["sSize"]||EG.getSize(element);

				//预期尺寸,必须是width和height格式,
				var dSize=cfg["dSize"];
				if(!dSize){
					if(item&&(item.width!=null||item.height!=null)){	//使用item的高宽作为预期尺寸
						dSize={width:item.width,height:item.height};
					}else{												//默认撑满
						dSize={width:"100%",height:"100%"};
					}
				}

				//父尺寸
				var pSize=cfg["pSize"]||EG.getSize(element.parentNode);//EG.getSize(item&&item.pItem?item.pItem.getElement():element.parentNode); //TODO
				if(pSize["width"]==null &&pSize["innerWidth"]!=null) 	pSize["width"]=pSize["innerWidth"];
				if(pSize["height"]==null&&pSize["innerHeight"]!=null) 	pSize["height"]=pSize["innerHeight"];

				//尺寸设置种类
				var type=cfg["type"]||"all",w,h;

				//TODO dSize["width"]!="auto" 是否应该换为item.getOWidth()!="auto"

				//目标尺寸应为内部尺寸减去boreder,margin,scrollWidth之后的尺寸

				//设宽
				if(dSize["width"]!=null&&dSize["width"]!="auto"&&(type=="all"||type=="width")){
					w=EG.Style.size2Num(dSize["width"],pSize["width"])-(sSize["outerWidth"]-sSize["innerWidth"]-sSize["vScrollWidth"]);
					if(w<0) w=0;  //TODO 等待处理负数
					EG.css(element,{
						width:w+"px"
					});
				}

				//设高
				if(dSize["height"]!=null&&dSize["height"]!="auto"&&(type=="all"||type=="height")){
					h=EG.Style.size2Num(dSize["height"],pSize["height"])-(sSize["outerHeight"]-sSize["innerHeight"]-sSize["hScrollWidth"]);
					//if(element.id=="slt2") alert("h:"+h);
					if(h<0) h=0;
					EG.css(element,{
						height:h+"px"
					});
				}

				return {
					width:w,
					height:h,
					pSize:pSize
				};
			},

			/**
			 * 用一个元素包裹另一个元素
			 * @param {Object} cfg 配置
			 */
			pack:function(cfg){
				var item		=cfg["item"];
				var pEle		=cfg["pEle"]||(item?item.getElement():null);
				var cEle		=cfg["cEle"];	//TODO 参考Default的autoSize
				var width		=cfg["width"];
				var height		=cfg["height"];
				var pSize		=cfg["pSize"]||EG.getSize(pEle);
				if(cEle&&(width==null||height==null)){
					var cS=EG.getSize(cEle);
					if(width==null) 	width=cS.outerWidth;
					if(height==null) 	height=cS.outerHeight;
				}

				var rs={};

				if(width!=null) 	{width	=(pSize.paddingLeft+width+pSize.paddingRight);	EG.css(pEle,"width:"+width+"px");		rs["width"]=width;}
				if(height!=null) 	{height	=(pSize.paddingTop+height+pSize.paddingBottom);	EG.css(pEle,"height:"+height+"px");		rs["height"]=height;}

				return rs;
			},

			/**
			 * 是否为item
			 * @param {EG.ui.Item} item 部件
			 */
			isItem:function(item) {
				return item&&typeof(item.getElement)=="function";
			},

			/**
			 * 将Item添加到renderTo指向的对象后执行render
			 * @param {EG.ui.Item} item 部件
			 */
			doRenderTo:function(item){
				if(item.renderTo){
					EG.$(item.renderTo).appendChild(item.getElement());
					item.render();
				}
			},

			/**
			 * 布局
			 * @param {EG.ui.Item} item 部件
			 * @param {Array} items 部件
			 */
			doLayout:function(item,items){
				if(!items) items=item.items;
				if(!items) return;

				if (item.layoutManager){
					item.layoutManager.doLayout();
				}

				for(var i=0,il=item.items.length;i<il;i++){
					if(items[i].doLayout) items[i].doLayout();
				}
			},

			/**
			 * 被继承后的特殊处理
			 * @param {Object} clazz 类
			 */
			afterExtend:function(clazz){
				//render Proxy
				(function(){
					var f=clazz.prototype.render;
					if(f){
						clazz.prototype.render=function(){
							//如果未添加到节点中，不渲染
							if(!EG.DOM.isActive(this.getElement())){
								return;
							}

							//如果父节点已隐藏，不渲染
							if(EG.Style.isHide(this.getElement().parentNode,true)) return;

							f.apply(this,arguments);
						};
					}
				})();
			},

			/**
			 * 父元素为body时设其样式为固定
			 * @param el
			 */
			fixBody:function(el){
				var pElement=el.parentNode;
				if (pElement == EG.getBody()) {
					EG.css(EG.getBody(),"margin:0px;padding:0px;width:100%;height:100%");
					EG.css(el, "position:absolute;left:0px;top:0px;");
				}
			},

			/**
			 * 寻找ITEM
			 * @param id
			 * @return {*|Function|*|*|*}
			 */
			$:function(id){
				return EG.$(id).item;
			}
		}
	});

	var Item=EG.ui.Item;
	EG.Item=Item;
	EG.fit=Item.fit;
	EG.isItem=Item.isItem;
})();
/**
 * @class EG.ui.Container
 * @author bianrongjun
 * @extends EG.ui.Item
 * 容器
 * 可以添加子Item,children，拥有布局器
 */
(function(){
	EG.define("EG.ui.Container",{

		extend:"EG.ui.Item",

		config:{
			/** @cfg {String?|Object?} layout 布局 */
			layout		:"default",
			/** @cfg {Array?} items 子组件 */
			itemsConfig	:[],
			/** @cfg {Array?} cns 子元素 */
			cnConfig	:[],
			/** @cfg {String?} innerHTML HTML */
			innerHTML	:null
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.items=[];

			this.callSuper([cfg]);
		},

		/**
		 * 创建后执行
		 */
		afterBuild:function(){
			//布局管理器
			this.setLayout(this.layout,false);

			//添加子组件
			if (this.itemsConfig.length > 0){
				this.addItem(this.itemsConfig,true);
			//添加子元素
			}else if(this.cnConfig.length > 0) {
				this.addChildren(this.cnConfig,null,false);
			//innerHTML
			}else if(this.innerHTML){
				this.getItemContainer().innerHTML=this.innerHTML;
			}
		},

		/**
		 * 获取Item容器
		 * @return {HTMLElement}
		 */
		getItemContainer:function(){
			return this.element;
		},

		/**
		 * 添加组件
		 * @param {Object|EG.ui.Item|Array} item 组件
		 * @param {Boolean?} noLayout 不Layout
		 * @param {Number?} idx 添加到哪个位置
		 */
		addItem:function(item,noLayout,idx) {
			//转换为数组
			var items=(!EG.isArray(item))?[item]:item;
			var rs=[];
			for (var i=0,il=items.length;i<il;i++) {
				item = items[i];
				if (EG.isLit(item)) {
					item=EG.Item.create(item.xtype,item);
				}
				item.pItem=this;

				if(idx>=0){
					EG.Array.insert(this.items,idx,item);
				}else{
					this.items.push(item);
				}


				//若为layout添加模式则返回:TableLayout
				if(this.layoutManager.addOnLayout) continue;
				//添加子
				EG.DOM.addChildren(this.getItemContainer(),item.getElement(),idx);
				//.appendChild();

				rs.push(item);
			}
			if(!noLayout){
				if(!this.whenPItemIsAuto()){
					this.doLayout();
				}
			}
			return rs.length>1?rs:rs[0];
		},

		/**
		 * 移除Item
		 * @param item
		 * @param {Boolean?} layout 是否重新布局
		 */
		removeItem:function(item,layout){
			if(layout==null) layout=true;
			//DOM移除
			this.getItemContainer().removeChild(item.getElement());
			//数组移除
			EG.Array.remove(this.items,item);
			//清空关系
			item.pItem=null;

			if(layout){
				if(!this.whenPItemIsAuto()){
					this.doLayout();
				}
			}
		},

		/**
		 * 添加子节点
		 * @param {Node} child 子节点
		 * @param {Number?} idx 插入坐标
		 * @param {Boolean?} layout 是否布局
		 */
		addChildren:function(child,idx,layout) {
			if(layout==null) layout=true;
			EG.DOM.addChildren(this.getItemContainer(),child,idx);
			if(layout) this.doLayout();
		},

		/**
		 * 渲染
		 */
		render:function() {

			//修父BODY样式
			EG.ui.Item.fixBody(this.element);
			//渲染尺寸
			EG.fit(this);
			//执行布局
			if(this.items.length>0||this.layoutManager.force){
				this.doLayout();
			}
		},

		/**
		 * 获取ITEM
		 * @param {Object|Number} cfg 参数
		 */
		getItem:function(cfg) {
			if(typeof(cfg)=="number")	{return this.items[cfg];}
			else if(this.layoutManager)	{return this.layoutManager.getItem(cfg);}
			else throw new Error("无法获取Item,参数不对或布局器不存在");
		},

		/**
		 * 设置布局
		 * @param layout 布局
		 * @param autoLayout 自动执行布局
		 */
		setLayout:function(layout,autoLayout){

			if(autoLayout==null) autoLayout=true;

			this.layout=layout;

			this.layoutManager 	=EG.ui.Layout.create(this.layout,this);

			//自动执行布局
			if(autoLayout) this.layoutManager.doLayout();
		},

		/**
		 * 执行布局
		 */
		doLayout:function() {
			//COMPAT-CHROME:[被撑时不会自动刷新]
			var oOverflow=EG.Style.current(this.element).overflow;
			EG.css(this.element,"overflow:hidden");

			this.layoutManager.doLayout();

			EG.css(this.element,"overflow:"+oOverflow);
		},

		whenPItemIsAuto:function(){
			//如果有父组件，自身是Auto尺寸并且自身尺寸发生变化，父组件重新渲染
			if(this.pItem){
				var pWA=this.pItem.isAutoWidth();
				var pHA=this.pItem.isAutoWidth();
				//父尺寸是非自动，切自身是自动，父元素重新布局
				if(!pWA&&!pHA&& (this.isAutoWidth()||this.isAutoHeight())){
					this.pItem.doLayout();
					return true;
				//如果父尺寸是自动，应在父方向寻找均为自动且最后一个为自动的父容器
				}else if(pWA||pHA){
					var p=this.pItem;
					while(p.pItem){
						if(!p.pItem.isAutoWidth()&&!p.pItem.isAutoHeight()){
							break;
						}else{
							p=p.pItem;
						}
					}
					p.doLayout();
					return true;
				}
			}
			return false;
		},

		/**
		 * 获取Left Item
		 * @retuns {EG.ui.Item}
		 **/
		getItemLeft		:function(){return this.getItem({region:"left"});},

		/**
		 * 获取Center Item
		 * @retuns {EG.ui.Item}
		 * */
		getItemCenter	:function(){return this.getItem({region:"center"});},

		/**
		 * 获取Right Item
		 * @retuns {EG.ui.Item}
		 * */
		getItemRight	:function(){return this.getItem({region:"right"});},

		/**
		 * 获取Top Item
		 * @retuns {EG.ui.Item}
		 * */
		getItemTop		:function(){return this.getItem({region:"top"});},

		/**
		 * 获取Bottom Item
		 * @retuns {EG.ui.Item}
		 * */
		getItemBottom	:function(){return this.getItem({region:"bottom"});},

		/**
		 * 是否为自动宽度
		 * @return {Boolean}
		 */
		isAutoWidth:function(){
			return this.getOWidth()=="auto";
		}
		,

		/**
		 * 是否为自动高度
		 * @return {Boolean}
		 */
		isAutoHeight:function(){
			return this.getOHeight()=="auto";
		},

		/****************************************************************
		 *
		 * 布局时，如果父元素的尺寸为自动，需要叠加子元素尺寸重新设置父的内尺寸
		 *
		 ****************************************************************/

		/**
		 * 设置内宽
		 *
		 * @param width 宽度为innerWidth
		 * @param {Boolean?} syc	将width同步为外宽
		 */
		setInnerWidth:function(width,syc){
			syc=EG.n2d(syc,true);
			EG.ui.Container.autoSize(this.getItemContainer(),width,"width");
			if(syc) this.width=this.getSize().outerWidth;
		},

		/**
		 * 设置内高
		 * @param height 高
		 * @param {Boolean?} syc	将height同步为外高
		 */
		setInnerHeight:function(height,syc){
			syc=EG.n2d(syc,true);
			EG.ui.Container.autoSize(this.getItemContainer(),height,"height");
			if(syc) this.height=this.getSize().outerHeight;
		},

		/**
		 * 获取内部高
		 * @return {*}
		 */
		getInnerHeight:function(){
			return EG.getSize(this.getItemContainer()).innerHeight;
		},

		/**
		 * 获取内部宽
		 * @return {*}
		 */
		getInnerWidth:function(){
			return EG.getSize(this.getItemContainer()).innerWidth;
		},

		/**
		 * 销毁
		 */
		destroy:function(){
			for(var i=0;i<this.items.length;i++){
				this.items[i].destroy();
			}
			this.items=null;
			//EG.Array.clear(this.items);
		},
		/**
		 * 清空
		 */
		clear:function(){
			for(var i=0;i<this.items.length;i++){
				if(this.items[i].isContainer) this.items[i].clear();
			}
			EG.DOM.removeChilds(this.getItemContainer());
			EG.Array.clear(this.items);
		},

		/**
		 * 是容器
		 */
		isContainer:true,

		statics:{
			/**
			 * 自动尺寸
			 * @param item
			 * @param size
			 * @param type
			 */
			autoSize:function(item,size,type){
				var ele=item.isItem?item.getElement():item;
				EG.css(ele,(type=="width"?"width":"height")+":"+size+"px");
			},
//			,
//			/**
//			 * 获取自动尺寸的组件
//			 * @param item
//			 * @param type
//			 * @return {*}
//			 */
//			getAutoParentItem:function(item,type){
//				if(type=="width") type="oWidth";
//				else if(type=="height") type="oHeight";
//
//				if(item.pItem&&item.pItem[type]=="auto"){
//					var pi=item;
//					while(pi.pItem&&pi.pItem[type]=="auto"){
//						pi=pi.pItem;
//					}
//					return pi;
//				}
//				return null
//			}
			afterExtend:function(clazz){
				//doLayout Proxy
				(function(){
					var f=clazz.prototype.doLayout;
					if(f){
						clazz.prototype.doLayout=function(){
							if(!EG.DOM.isActive(this.getElement())){
								return;
							}
							f.apply(this,arguments);
						};
					}
				})();
			}
		}
	});
})();
(function(){
	/******************************************************************************************************************
	 * 
	 *  EG.ui.Component 组件
	 * 
	 *******************************************************************************************************************/
	EG.ui.Component=function(){};
	
	EG.ui.Component.addChildren=function(cpt,cn){
		var cns=(!EG.isArray(cn))?[cn]:cn;
		for(var i=0,l=cns.length;i<l;i++){
			cn=cns[i];
			if(EG.isLit(cn)) cn=EG.CE(cn);
			cpt.getElement().appendChild(cn);
		}
	};

})();/**
 * @class EG.ui.Drag
 * @author bianrongjun
 * @extends EG.ui.Container
 * Drag表单
 *
 * 拖拽把手上的如果有按钮，onclick事件则不会被触发
 * 拖拽层只会在Body上创建一次，每次拖拽时会同步成被拖拽体的大小
 * parent是可移动的区域
 */
(function(){
	EG.define("EG.ui.Drag",{
		config:{
			/** @cfg {Object} target 拖拽对象 */
			target:null,
			/** @cfg {Object?} handle 拖拽把手，可以有多个 */
			handle:null,
			/** @cfg {Object?} parent 父移动区域 */
			parent:null,
			/** @cfg {Boolean?} moveTarget 是否移动目标对象 */
			moveTarget:false,
			/** @cfg {Function?} onDrag 拖拽时执行 */
			onDrag     :null,
			/** @cfg {Function?} afterDragup 拖拽后执行体 */
			afterDragup:null,
			/** @cfg {Boolean?} sycHandleSize 拖拽体与Handle尺寸同步 */
			sycHandleSize:false
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			//加载
			EG.ui.Drag.load();

			this.initConfig(cfg);

			this.handles=[];

			//默认handle为target本身
			if(this.handle||this.target) this.bindHandle(this.handle||this.target);

			//绑定父
			this.bindParent(this.parent||EG.getBody());
		},

		/**
		 * 绑定拖拽把手
		 * 支持多个把手
		 * @param {Object} handle 拖拽把手
		 */
		bindHandle:function(handle){
			var ME=this.getClass();

			this.handles.push(handle);

			//转换为数组
			var handle=(!EG.isArray(handle))?[handle]:handle;
			for (var i=0,il=handle.length;i<il;i++) {
				var h = handle[i];
				if (h.getElement) {
					h=h.getElement;
				}
				EG.CE({ele:h,style:"cursor:move",onmousedown:ME._events.handle.onmousedown,me:this});
			}
		},

		/**
		 * 绑定父移动区域
		 * @param {Object} parent 父移动区域
		 */
		bindParent:function(parent){
			this.parent=parent;
		},

		statics:{
			/**
			 * 首次被实例化时加载
			 */
			load:function(){
				var ME=EG.ui.Drag;

				ME.selectable=true;

				if(!ME.loaded){
					//绑定事件
					EG.CE({ele:EG.getBody(),_drager:true,
						onselectstart:function(){return ME.selectable;},
						onmouseup	:ME._events.parent.onmouseup,
						onmousemove	:ME._events.parent.onmousemove
					});

					//创建拖拽层
					ME._drager=EG.CE({pn:EG.getBody(),tn:"div",style:"position:absolute;z-index:3;border:1px dashed;background-color:gray;opacity:0.5;-moz-opacity:0.5;filter:alpha(opacity=50);"});

					ME.loaded=true;
				}
			},

			/**
			 * 事件
			 */
			_events:{
				handle:{
					onmousedown:function(e){
						var ME=EG.ui.Drag;
						ME.curDrag	=this.me;

						//开启拖拽
						var cd=this;
						while(true){
							if(EG.Array.has(this.me.handles,cd)){
								break;
							}else{
								cd=this.parentNode;
								if(cd==null){
									break;
								}
							}
						}

						if(cd==null) throw new Error("发生预料外的错误->未找到拖拽节点");
						ME.curHandle=cd;
						//TODO 记录鼠标与拖拽物的相对位置
						var t=ME.curDrag.sycHandleSize?cd:this.me.target;
						ME.refPos	=EG.Tools.getMousePos(e,t);

						//显示拖拽层
						ME.showDrager(e);

						return true;
					}
				},
				parent:{
					onmousemove:function(e){
						var ME=EG.ui.Drag;

						//判断是否开启
						if(!ME.curDrag){
							return;
						}

						//如果没有按住左键，进行拖拽完成动作
						if(!EG.Tools.isPressLeft(e)){
							//if(e.which!=2)
							ME.dragup();
						}

						//定位拖拽层
						var pos	=EG.Tools.getMousePos(e);

						var top	=pos.y-ME.refPos.y;
						var left=pos.x-ME.refPos.x;
						EG.css(ME._drager,"top:"+top+"px;left:"+left+"px;");

						if(ME.curDrag.onDrag){
							ME.curDrag.onDrag.apply(ME.curDrag,[e]);
						}
						//TODO 碰边检测
					},

					onmouseup:function(){
						var ME=EG.ui.Drag;
						ME.dragup();
					}
				}
			},

			/**
			 * 拖拽完成
			 */
			dragup:function(){
				var ME=EG.ui.Drag;

				//判断是否开启
				if(!ME.curDrag) return;

				//判断是否隐藏
				if(EG.Style.isHide(ME._drager)) return;

				//定位Target,获取父与Body的相对位置
				var cs	=EG.Style.current(ME._drager);
				var top	=cs.top=="auto"?"0":cs.top;
				var left=cs.left=="auto"?"0":cs.left;
				if(ME.curDrag.parent!=EG.getBody()){
					var pPos=EG.Tools.getElementPos(ME.curDrag.parent);

					top		=(parseInt(EG.String.removeEnd(top,"px"))	-pPos.y)+"px";
					left	=(parseInt(EG.String.removeEnd(left,"px"))	-pPos.x)+"px";
				}

				//定位目标对象
				if(ME.curDrag.moveTarget){
					EG.css(ME.curDrag.target,"position:absolute;top:"+top+";left:"+left+";");
				}

				//隐藏拖拽层
				ME.hideDrager();

				//执行拖拽后动作
				if(ME.curDrag.afterDragup){
					ME.curDrag.afterDragup.apply(this);
				}

				//关闭拖拽
				ME.curDrag=false;
				EG.ui.Drag.complete	=true;
			},


			/**
			 * 绑定
			 * @param cfg
			 */
			bindDrag:function(cfg){
				new EG.ui.Drag(cfg);
			},



			/**
			 * 显示拖拽层
			 * @param {Object} e 事件
			 */
			showDrager:function(e){
				e=EG.Event.getEvent(e);
				var ME=EG.ui.Drag;

				//首次创建
				if(!ME._drager){

				}

				//控制是否可选
				ME.selectable=false;

				//定位&显示
				var pos	=EG.Tools.getMousePos(e);
				var top	=pos.y-ME.refPos.y;
				var left=pos.x-ME.refPos.x;
				EG.css(ME._drager,"top:"+top+"px;left:"+left+"px;");

				EG.show(ME._drager);

				//同步尺寸
				var sycT=ME.curDrag.sycHandleSize?ME.curHandle:ME.curDrag.target;
				var s=EG.getSize(sycT);
				EG.Item.fit({
					element:ME._drager,
					dSize:{width:s.outerWidth,height:s.outerHeight}
				});
			},

			/**
			 * 隐藏拖拽层
			 */
			hideDrager:function(){
				var ME=EG.ui.Drag;
				ME.selectable=true;
				EG.hide(ME._drager);
			}
		}
	});
})();
(function(){
	/**
	 * EG.ui.Pop 弹窗
	 */
	EG.define("EG.ui.Pop",{
		extend:"EG.ui.Container",
		config:{
			cls				:"eg_pop",
			lock			:false,				//是否有关闭层
			posFix			:true,
			innerHTML		:null,
			target			:null,				//目标对象
			parent			:null,				//参考父
			afterOpen		:null				//点开后
		},
		constructor:function(cfg){
			var me=this;
			this.callSuper([cfg]);
			//隐藏
			EG.hide(this.element);
		},
		/**
		 * 创建
		 */
		build:function(){
			this.element=EG.CE({tn:"div",cls:this.cls+"-outer",cn:[
				this.dLocker=EG.CE({tn:"div",cls:this.cls+"-locker"}),
				this.dPop	=EG.CE({tn:"div",cls:this.cls})
			]});
		},
		/**
		 * 获取Style对象
		 * @returns {*}
		 */
		getStyleEle:function(){
			return this.dPop;
		},
		/**
		 * 获取容器
		 * @returns {*}
		 */
		getItemContainer:function(){
			return this.dPop;
		},
		/**
		 * 显示Pop
		 */
		open:function(){
			EG.show(this.element);
			this.render();


			if(this.afterOpen){
				this.afterOpen.apply(this);
			}
		},
		/**
		 * 关闭Pop
		 */
		close:function(){
			EG.hide(this.element);
		},
		render:function(){

			//设置element大小同parent
			var parent=this.parent||this.getElement().parentNode;
			var pSize=EG.getSize(parent);

			EG.fit({
				element:this.element,
				dSize:{width:pSize.innerWidth,height:pSize.innerHeight}
			});


			//设置大小
			EG.fit({
				element:this.dPop,
				dSize:{width:this.width,height:this.height}
			});

			//子组件布局
			if(this.items.length>0){
				//执行布局
				this.doLayout();
			}

			if(this.lock) this.fullLock();

			if(this.posFix) this.doPosFix();
		},
		/**
		 * 锁住屏幕
		 */
		fullLock:function(){
			//锁屏大小适配
			EG.fit({
				element:this.dLocker,
				dSize:{width:"100%",height:"100%"},
				pSize:EG.getSize(this.getElement())
			});
		},
		/**
		 * 固定到中央位置
		 */
		doPosFix:function(){
			EG.Style.center(this.dPop,this.getElement());
			EG.Style.middle(this.dPop,this.getElement());
		},
		/**
		 * 移动到某位置
		 * @param pos 位置
		 */
		moveTo:function(pos){
			EG.Style.moveTo(this.dPop,pos);
		},
		/**
		 * 是否已打开
		 * @return {Boolean}
		 */
		isOpen:function(){
			return !EG.Style.isHide(this.element);
		}

	});
})();
(function(){
	/**
	 * EG.ui.Dialog 对话框
	 */
	EG.define("EG.ui.Dialog",{
		extend:"EG.ui.Pop",
		config:{
			cls				:"eg_dialog",
			title			:null,				//标题
			btnsConfig		:null,				//底部按钮
			zIndex			:null,
			bodyStyle		:null,
			headStyle		:null,
			footStyle		:null,
			closeable		:true,				//是否能关闭
			fullable		:false,				//是否能全屏
			dragable		:false				//是否能拖拽
		},
		constructor:function(cfg){

			this.callSuper([cfg]);

			//设置标题
			if(this.title)			this.setTitle(this.title);

			//底部按钮
			if(this.btnsConfig) 	this.setButtons(this.btnsConfig);

			//全屏按钮
			this.setFullable(this.fullable);

			//关闭按钮
			this.setCloseable(this.closeable);

			//设定拖拽
			this.setDragable(this.dragable);
		},

		//创建
		build:function(){
			var ME=EG.ui.Dialog;

			this.callSuper("build");

			//Head
			this.dHead=EG.CE({pn:this.dPop,tn:"div",cls:this.cls+"-head",cn:[
				this.dTitle	=EG.CE({tn:"div",cls:this.cls+"-title"}),

				EG.CE({tn:"div",cls:this.cls+"-trBtns",cn:[
					//最大化按钮
					this.dFuller=EG.CE({tn:"a",cls:this.cls+"-fuller",me:this,
						onmouseup	:ME._events.dFuller.onmouseup,
						onmousedown	:ME._events.dFuller.onmousedown
					}),

					//关闭按钮
					this.dCloser=EG.CE({tn:"a",cls:this.cls+"-closer",me:this,
						onmouseup	:ME._events.dCloser.onmouseup,
						onmousedown	:ME._events.dCloser.onmousedown
					})

				]})


			]});

			//Body
			this.dBody=EG.CE({pn:this.dPop,tn:"div",cls:this.cls+"-body"});

			//Foot
			this.dFoot=EG.CE({pn:this.dPop,tn:"div",cls:this.cls+"-foot"});

			//设置Body样式
			if(this.bodyStyle)	EG.css(this.dBody,this.bodyStyle);

			//设置Head样式
			if(this.headStyle)	EG.css(this.dHead,this.headStyle);

			//设置Foot样式
			if(this.footStyle)	EG.css(this.dFoot,this.footStyle);

			//层值
			if(this.zIndex!=null){
				EG.css(this.element,"z-index:"+this.zIndex);
			}
		},

		/**
		 * 获取Item容器
		 * @returns {*}
		 */
		getItemContainer:function(){
			return this.dBody;
		},

		/**
		 * 设置是否可以全屏
		 * @param fullable
		 */
		setFullable:function(fullable){
			if(fullable){
				EG.show(this.dFuller);
			}else{
				EG.hide(this.dFuller);
			}
		},

		/**
		 * 设置是否能拖拽
		 * @param dragable
		 */
		setDragable:function(dragable){
			this.dragable=dragable;
			if(!this.drag){
				this.drag=new EG.ui.Drag({
					target:this.dPop,
					parent:this.dLocker,
					handle:this.dHead,
					moveTarget:true
				});
			}

		},

		/**
		 * 设置是否可以关闭
		 * @param closeable
		 */
		setCloseable:function(closeable){
			if(closeable){
				EG.show(this.dCloser);
			}else{
				EG.hide(this.dCloser);
			}
		},

		/**
		 * 设置按钮
		 * @param btns
		 */
		setButtons:function(btns){
			EG.DOM.removeChilds(this.dFoot);
			if(btns==null) return;
			for(var i=0,il=btns.length;i<il;i++){
				var btn=EG.isLit(btns[i])?new EG.ui.Button(btns[i]):btns[i];
				this.dFoot.appendChild(btn.getElement());
			}
		},
		/**
		 * 设置标题
		 * @param title
		 */
		setTitle:function(title){
			EG.setValue(this.dTitle,title);
		},

		setInnerWidth:function(w){
			EG.Item.pack({
				pEle:this.dBody,
				width:w
			});

			EG.Item.pack({
				pEle:this.dPop,
				width:EG.getSize(this.dBody).outerWidth
			});
		},

		setInnerHeight:function(h){
			//设定Body高度
			EG.Item.pack({
				pEle:this.dBody,
				height:h
			});

			//设定外层高度
			EG.Item.pack({
				pEle:this.dPop,
				height:EG.getSize(this.dBody).outerHeight+EG.getSize(this.dHead).outerHeight+EG.getSize(this.dFoot).outerHeight
			});
		},

		getInnerHeight:function(){
			if(typeof(this.oHeight)=="number"){
				return EG.getSize(this.dPop).innerHeight-EG.getSize(this.dHead).outerHeight-EG.getSize(this.dFoot).outerHeight;
			}else{
				return this.oHeight;
			}
		},

		full:function(){

			//放大
			if(!this._fulled){
				//保留当前元素对应的父元素和参考元素
				this.oParent=this.parent;
				this.oParentNode=this.getElement().parentNode;
				this._oWidth=this.width;
				this._oHeight=this.height;
				//将当前元素放在Body下
				EG.getBody().appendChild(this.getElement());
				this.width="100%";
				this.height="100%";
				this.render();
				this._fulled=true;
			//还原
			}else{
				this.parent=this.oParent;
				this.width=this._oWidth;
				this.height=this._oHeight;
				//将当前元素放在Body下
				this.oParentNode.appendChild(this.getElement());
				this.render();
				this._fulled=false;
			}
		},



		render:function(){

			//设置element大小同parent
			var parent=this.parent||this.getElement().parentNode;
			var pSize=EG.getSize(parent);
			EG.fit({
				element:this.element,
				dSize:{width:pSize.innerWidth,height:pSize.innerHeight},
				pSize:pSize
			});

			EG.css(this.element,"top:0px;left:0px");

//			alert("HERE")

			//设置大小
			EG.fit({
				element:this.dPop,
				dSize:{width:this.width,height:this.height}
			});

			//设定dBody高度
			var ah=this.isAutoHeight();
			if(!ah){
				//alert(this.height+":"+EG.getSize(this.dHead).outerHeight+":"+EG.getSize(this.dFoot).outerHeight)
				EG.fit({
					element:this.dBody,
					dSize:{height:(EG.getSize(this.dPop).innerHeight-EG.getSize(this.dHead).outerHeight-EG.getSize(this.dFoot).outerHeight)}
				});
			}

			//设定dBody宽度
			var aw=this.isAutoWidth();
			if(!aw){
				EG.fit({
					element:this.dBody,
					dSize:{width:"100%"}
				});
			}

			//子组件布局
			if(this.items.length>0){
				//执行布局
				this.doLayout();
			}


			pSize=EG.getSize(this.dPop);
			//设置头宽
			EG.fit({
				element:this.dHead,
				dSize:{width:pSize.innerWidth}
			});

			//设置尾宽
			EG.fit({
				element:this.dFoot,
				dSize:{width:pSize.innerWidth}
			});

			EG.css(this.dBody,"position:relative;");
//			EG.css(this.dHead,"position:absolute;top:0px");
//			EG.css(this.dBody,"position:absolute;top:"+EG.getSize(this.dHead).outerHeight+"px");
//			EG.css(this.dFoot,"position:absolute;bottom:0px");

			if(this.lock) this.fullLock();

			if(this.posFix) this.doPosFix();
		},

		statics:{
			_events:{
				dCloser:{
					onmousedown:function(e){
						EG.Event.stopPropagation(e);
					},
					onmouseup:function(e){
						var me=this.me;
						me.close();
						EG.Event.stopPropagation(e);
					}
				},

				dFuller:{
					onmousedown:function(e){
						EG.Event.stopPropagation(e);
					},
					onmouseup:function(e){
						var me=this.me;
						me.full();
						EG.Event.stopPropagation(e);
					}
				}


			}

		}
	});
})();
(function(){
	/**
	 * 贴示
	 */
	EG.define("EG.ui.Tip",{
		extend:"EG.ui.Pop",
		config:{
			lock			:false,
			cls				:"eg_tip"
		},
		constructor:function(){
			//this.callSuper([]);

			this.initItem({});

			this.build();
		},
		build:function(){
			this.element=EG.CE({pn:EG.getBody(),tn:"div",style:"border:1px solid red;border-radius:2px;background:#FFFFC0;position:absolute;z-index:2",cn:[
				this.dIcon=EG.CE({tn:"div",style:EG.Style.c.dv}),
				this.dMessage=EG.CE({tn:"div",style:EG.Style.c.dv+";padding:5px"})
			]});
			EG.hide(this.element);
		},
		error:function(msg,target){
			this.message({
				message:msg,
				target:target,
				type:"error"
			});
		},
		info:function(){
			this.message({
				message:msg,
				target:target,
				type:"info"
			});
		},
		message:function(cfg){
			cfg=cfg||{};

			var message=cfg["message"],target=cfg["target"],type=cfg["target"];
			var autoclose=cfg["autoclose"]||false;
			var closetime=cfg["closetime"];
			if(closetime){
				autoclose=true;
			}
			this.dMessage.innerHTML=message;
			EG.show(this.element);

			//寻找aim的坐标,设定浮动位置
			var pos=EG.Tools.getElementPos(target);
			var tSize=EG.getSize(target);

			var x=pos.x,y=pos.y;//alert(x+size.clientWidth)
			var size=EG.getSize(this.element);
			if((x+tSize.clientWidth+size.clientWidth)>screen.width){
				x=x+tSize.clientWidth-size.clientWidth;
				y=y+tSize.clientHeight;
			}else{
				x=x+tSize.clientWidth;
			}
			EG.css(this.element,"left:"+x+"px;top:"+y+"px");
		}
	});

	//全局
	EG.onload(function(){
		EG.Tip=new EG.ui.Tip();
	});
})();
/**
 * @class EG.ui.Locker
 * @author bianrongjun
 * @extends EG.ui.Dialog
 * 锁定器,Locker只有在window.onload时才创建
 */
(function(){
	EG.define("EG.ui.Locker",{
		extend:"EG.ui.Dialog",
		config:{
			/** @cfg {String?} CSS样式类 */
			cls			:"eg_locker",
			/** @cfg {String?} 标题*/
			title		:"提示",
			/** @cfg {Number?} 层级*/
			zIndex		:2
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.callSuper([cfg]);
			EG.css(this.dBody,"text-align:center;overflow:auto");
			this._lock=false;
		},

		/**
		 * 创建
		 */
		build:function(){
			this.callSuper("build");
			var pn=this.dBody;
			this.dType=EG.CE({pn:pn,tn:"div",cls:this.cls+"-type"});
			this.dWait=EG.CE({pn:pn,tn:"div",cls:this.cls+"-wait",innerHTML:"正在加载..."});
		},

		/**
		 * 锁定
		 * @param {Boolean?} iflock 是否锁定
		 */
		lock:function(iflock){
			if(this._lock){
				return;
			}
			iflock=EG.n2d(iflock,true);
			if(iflock) {
//				EG.hide(this.dPop);
//				EG.show(this.dLocker);
				this.open();
			}else{
				this._lock=false;
				this.close();
			}
		},

		/**
		 * 等待层
		 * @param {String?} message 消息
		 */
		wait:function(message){
			this.message({
				message:message,
				closeable:false
			});
		},

		/**
		 * 消息提示
		 * @param {Object?} cfg 配置
		 */
		message:function(cfg){
			cfg=cfg||{};

			var message="";
			if(typeof(cfg)=="string"){
				message=cfg;
			}else{
				message=cfg["message"]||"请稍等...";
			}

			if(!cfg["force"]&&this._lock) return;

			var type=cfg["type"];
			if(type){
				EG.setCls(this.dType,["type","type-"+type],this.cls);
				EG.show(this.dType);
			}else{
				EG.hide(this.dType);
			}

			EG.setCls(this.dWait,["wait","fontM"],this.cls);
			this.dWait.innerHTML=message;

			var closeable=cfg["closeable"]!=null?cfg["closeable"]:true;

			var autoclose=cfg["autoclose"]||false;
			var closetime=cfg["closetime"];
			if(closetime){
				autoclose=true;
			}
			var callback=cfg["callback"];
			//不能关闭和自动关闭时禁止显示关闭按钮
			if(!closeable||autoclose) this.setCloseable(false);
			else this.setCloseable(true);

			this.open();

			//渐进显示
			EG.Style.fade(this.dPop,0,90,null,10);

			//自动关闭
			if(autoclose){
				if(closetime==null) closetime=1200;
				var me=this;
				setTimeout(function(){
					EG.Style.fade(me.dPop,90,0,function(){
						me.lock(false);
						if(callback) callback();
					},20);
				},closetime);
			}
		}
	});

	//全局
	EG.onload(function(){

		EG.Locker=new EG.ui.Locker({
			width	:Math.min(500,document.documentElement.clientWidth),
			height	:Math.min(120,document.documentElement.clientHeight),
			renderTo:EG.getBody()
		});

		EG.lock	=function(iflock){
			EG.Locker.lock(iflock);
		};

		EG.message=function(cfg){
			EG.Locker.message(cfg);
		};

		EG.wait	=function(cfg){
			EG.Locker.wait(cfg);
		};

	});
})();

(function(){
	
	EG.ui.Option=function(cfg){
		this.btns=cfg["btns"];
		this.pop=new EG.ui.Pop({closeable:false});
		
		this.element=EG.CE({tn:"div",style:"padding:10px"});
		
		this.pop.getElement().appendChild(this.element);
		if(this.btns) this.setBtns(this.btns);
		document.body.appendChild(this.element);
	};
	EG.ui.Option.prototype={};
	var Option=EG.ui.Option;
	
	EG.ui.Option.prototype.setTextactions=function(textactions){
		for(var i=0,il=textactions.length;i<il;i++){
			
		}
	};
	
	EG.ui.Option.prototype.open=function(){
		this.pop.open();
	};
	
	EG.ui.Option.prototype.close=function(){
		this.pop.close();
	};
	
	EG.ui.Option.prototype.setBtns=function(btns){
		for(var i=0,il=btns.length;i<il;i++){
			this.element.appendChild(btns[i].getElement());
			//this.pop.addChildren();
		}
	};
	
	
})();
/**
 * @class EG.ui.Layout
 * @author bianrongjun
 * 布局器父类
 */
(function(){
	/**
	 * EG.ui.Layout 布局
	 */
	EG.define("EG.ui.Layout",{
		statics:{
			layoutManagers:{},
			
			/**
			 * 获取布局器
			 * @param {Object|String} cfg 配置
			 * @param {EG.ui.Container} container 容器
			 * @return {EG.ui.Layout}
			 */
			create:function(cfg,container) {
				if (EG.String.isString(cfg)){
					cfg = {
						type : cfg
					};
				}
				var layoutManagerClass=EG.ui.layout[EG.Word.first2Uppercase(cfg["type"])+"Layout"];
				if(!layoutManagerClass) throw new Error("EG.ui.Layout#create:该布局器无法识别:"+cfg["type"]);
				return new layoutManagerClass(cfg,container);
			},

			/**
			 * 注册布局器
			 * @param {String} type 类型
			 * @param {EG.ui.Layout} layoutManager 布局Manager
			 */
			regist:function(type, layoutManager) {
				EG.ui.Layout.layoutManagers[type] = layoutManager;
			},

			/**
			 * 组件描述
			 */
			sizeDesc:function(item){
				var d={};
				d.ow=item.getOWidth();
				d.oh=item.getOHeight();
				d.isWA=item.isContainer&&item.isAutoWidth();							//宽度-自动
				d.isHA=item.isContainer&&item.isAutoHeight();						//高度-自动
				d.isWP=(typeof(d.ow)=="string"&&d.ow.lastIndexOf("%")>=0);		//宽度-百分比
				d.isHP=(typeof(d.oh)=="string"&&d.oh.lastIndexOf("%")>=0);		//高度-百分比
				d.isWE=d.ow==null;												//宽度-空
				d.isHE=d.oh==null;												//高度-空
				d.isWN=(typeof(d.ow)=="number");									//宽度-固定值
				d.isHN=(typeof(d.ow)=="number");									//高度-固定值
				return d;
			},

			/**
			 * 渲染子元素
			 */
			renderItems:function(layout){
				for(var i=0,l=layout.items.length;i<l;i++){
					var item=layout.items[i];
					item.render();
				}
			}


		},
		/**
		 * layout添加模式
		 */
		addOnLayout:false
		,
		/**
		 * 执行布局
		 * @interface
		 */
		doLayout:function(){},

		/**
		 * 分辨出hidden属性的Item不进行布局
		 */
		hideItems:function(){
			var unHides=[];
			for(var i=0;i<this.items.length;i++){
				if(!this.items[i].hidden){
					unHides.push(this.items[i]);
				}
			}
			this.items=unHides;
		}
	});
})();/**
 * @class EG.ui.layout.DefaultLayout
 * @author bianrongjun
 * @extends EG.ui.Layout
 * 默认布局器
 */
(function(){
	EG.define("EG.ui.layout.DefaultLayout",{
		extend:"EG.ui.Layout",
		config:{
			/** @cfg {Boolean?} centerChilds 横向居中子元素 */
			centerChilds	:false,
			/** @cfg {Boolean?} middleChilds 纵向居中子元素 */
			middleChilds	:false,
			/** @cfg {Boolean?} horizontal 是否平行并排 */
			horizontal		:true
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 * @param {EG.ui.Container} container 容器
		 */
		constructor:function(cfg,container){
			this.container	=container;			//容器
			this.initConfig(cfg);
			this.force=this.centerChilds||this.middleChilds;
		},

		/**
		 * 布局
		 * @implements {EG.ui.Layout.prototype.doLayout}
		 */
		doLayout:function(){
			this.items		=this.container.items;	//items

			this.hideItems();

			this.isCtWA=this.container.isAutoWidth();
			this.isCtHA=this.container.isAutoHeight();

			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];
				item.render();
			}

			this.autoSize();

			//行向居中
			if(this.centerChilds){
				EG.Style.centerChilds(this.container.getItemContainer(),this.horizontal);
			}

			//纵向对齐
			if(this.middleChilds){
				EG.Style.middleChilds(this.container.getItemContainer(),this.horizontal);
			}
		},
		/**
		 * 自动尺寸
		 */
		autoSize:function(){
			var aw=this.container.isAutoWidth();
			var ah=this.container.isAutoHeight();
			if(this.isCtWA||this.isCtHA){
				//var cEle=this.container.getElement();
				var minX=-1,maxX=-1,minY=-1,maxY=-1;
				for(var i=0,l=this.items.length;i<l;i++){
					var item=this.items[i];
					//alert(item.getElement())
					//alert(item.id)
					var p=EG.Tools.getElementPos(item.getElement());
					var s=item.getSize();
					minX=minX==-1?(p.x-s.marginLeft)				:Math.min(p.x-s.marginLeft,minX);
					maxX=maxX==-1?(p.x-s.marginLeft+s.outerWidth)	:Math.max(p.x-s.marginLeft+ s.outerWidth,maxX);
					minY=minY==-1?(p.y-s.marginTop)					:Math.min(p.y-s.marginTop,minY);
					maxY=maxY==-1?(p.y-s.marginTop+s.outerHeight)	:Math.max(p.y-s.marginTop+s.outerHeight,maxY);
				}
				//alert(maxX+":"+minX+":"+maxY+":"+minY);

				var w=Math.max(0,maxX-minX);
				var h=Math.max(0,maxY-minY);
				if(this.isCtWA)	this.container.setInnerWidth(w);
				if(this.isCtHA)	this.container.setInnerHeight(h);
			}
		}

	});
})();
/**
 * @class EG.ui.layout.BorderLayout
 * @author bianrongjun
 * @extends EG.ui.Layout
 * Border布局器
 */
(function(){
	/**
	 * EG.ui.layout.BorderLayout Border布局器
	 */
	EG.define("EG.ui.layout.BorderLayout",{
		extend:"EG.ui.Layout",
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 * @param {EG.ui.Container} container 容器
		 */
		constructor:function(cfg,container){
			this.container	=container;			//容器
		},
		/**
		 * 获取Item
		 * @param {Object} cfg 配置
		 */
		getItem:function(cfg) {
			for (var i=0,l=this.container.items.length;i<l;i++)
				if(this.container.items[i].region == cfg["region"])
					return this.container.items[i];
			return null;
		},

		//Layout分配应按照原始尺寸
		//1.如果外层尺寸固定
		//	1.1	子的尺寸非auto,依赖Layout规则,重新分配尺寸
		//  1.2	子的尺寸为auto,暂不分配尺寸,待setItemSize结束后,将所有自动尺寸子render,固定子的尺寸
		//2.如果外层尺寸为auto,用子组件计算总的尺寸,如果总尺寸因部份子为auto而暂不能确定,标记为-1
		//
		//	子的尺寸若为固定,先render子,结束后设置外层高度
		//
		/**
		 * 布局
		 * @implements {EG.ui.Layout.prototype.doLayout}
		 */
		doLayout:function(){
			this.init();

			//计算外层尺寸
			if(this.isCtWA)	this.setCtWidth();
			if(this.isCtHA)	this.setCtHeight();

			//分配高宽
			this.setItemWidth(this.ctW);
			this.setItemHeight(this.ctH);

			//渲染子
			EG.ui.Layout.renderItems(this);

			//自动尺寸
			this.autoSize();
		},

		/**
		 * 初始化
		 */
		init:function(){
			this.items		=this.container.items;	//items

			this.hideItems();

			//父元素固定
			this.ctEle=this.container.getItemContainer();
			EG.css(this.ctEle, "position:absolute");

			//初始化各个参数
			this.itemT=this.itemB=this.itemC=this.itemL=this.itemR=null;

			//容器尺寸自动宽高
			this.isCtWA=this.container.isAutoWidth();this.isCtHA=this.container.isAutoHeight();
			//子组件尺寸有自动宽高
			this.hasItemWA=false;this.hasItemHA=false;
			//中间组件尺寸有自动宽高
			this.hasMidWA=false;this.hasMidHA=false;
			//中间组件尺寸有空值
			this.hasMidWE=false;this.hasMidHE=false;
			//中间组件尺寸有百分比
			this.hasMidWP=false;this.hasMidHP=false;
			//中间组件尺寸有百分比
			this.hasMidWN=false;this.hasMidHN=false;

			//自动宽提前计算高
			this.ctW=this.container.getInnerWidth();
			this.ctH=this.container.getInnerHeight();
			this.mItems=[];

			//1.设置组件初始的Position样式,获取各种高宽属性
			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];
				if(!item.region) throw new Error("BorderLayout#doLayout:无region信息");

				//尺寸描述
				var sd=EG.ui.Layout.sizeDesc(item);

				//是否为中间组件
				var isMid=false;

				//是否有子组件自动尺寸
				this.hasItemWA=this.hasItemWA||sd.isWA;
				this.hasItemHA=this.hasItemHA||sd.isHA;

				//识别方位
				if(item.region=="top")			{this.itemT=item;}
				else if(item.region=="bottom")	{this.itemB=item;}
				else if(item.region=="center")	{this.itemC=item;isMid=true;}
				else if(item.region=="left")	{this.itemL=item;isMid=true;}
				else if(item.region=="right")	{this.itemR=item;isMid=true;}
				else{
					throw new Error("BorderLayout#doLayout:暂不支持"+item.region);
				}

				//如果是中间组件
				if(isMid){
					this.mItems.push(item);

					this.hasMidWA=this.hasMidWA||sd.isWA;this.hasMidHA=this.hasMidHA||sd.isHA;
					this.hasMidWE=this.hasMidWE||sd.isWE;this.hasMidHE=this.hasMidHE||sd.isHE;
					this.hasMidWP=this.hasMidWP||sd.isWP;this.hasMidWP=this.hasMidWP||sd.isHP;
					this.hasMidWN=this.hasMidWN||sd.isWN;this.hasMidHN=this.hasMidHN||sd.isHN;
				}

				//设定position,设定缩进的方向
				if(item.region=="center"){
					EG.css(item,"position:absolute;");
					if(item.setCollapseAble){
						item.setCollapseAble(false);
					}
				}else{
					EG.css(item,"position:absolute;"+item.region+":0px");
					if(item.collapseAble&&item.setCollapseBehavior){
						item.setCollapseBehavior(item.region);
					}
				}
			}

			//2.判断外层尺寸计算是否参考中间尺寸计算方式
			this.refMW=false;//外层宽度是否参考中间
			this.refMH=false;//外层高度是否参考中间
			if(this.mItems.length>0){
				//中间L不含空也不含百分比=(固定值||自动)
				this.refMW=this.isCtWA&&(!this.hasMidWE&&!this.hasMidWP);
				//存在有固定值||自动
				this.refMH=this.isCtHA&&(this.hasMidHN||this.hasMidHA);
			}
		},

		/**
		 * 如果外层自动宽,动态计算一次外层宽,如果上中下的宽暂不能确定,暂设为-1
		 */
		setCtWidth:function(){
			this.ctW=0;
			//中
			if(this.refMW){
				if(!this.hasMidWA){//如果没有自动宽
					var mw=0;
					for(var i=0;i<this.mItems.length;i++){
						mw+=this.mItems[i].getOWidth();
					}
					this.ctW=mw;
				}else{
					this.ctW=-1;
				}
			}
			//上
			if(this.itemT&&this.ctW>=0&&this.itemT.getOWidth()!=null){
				this.ctW=this.itemT.isContainer&&this.itemT.isAutoWidth()?-1:Math.max(this.ctW,this.itemT.getOWidth());
			}
			//下
			if(this.itemB&&this.ctW>=0&&this.itemB.getOWidth()!=null){
				this.ctW=this.itemB.isContainer&&this.itemB.isAutoWidth()?-1:Math.max(this.ctW,this.itemB.getOWidth());
			}
		},

		/**
		 * 如果外层自动高,动态计算一次外层宽,如果上中下的高暂不能确定,暂设为-1
		 */
		setCtHeight:function(){
			this.ctH=0;
			//中
			if(this.refMH){
				if(!this.hasMidHA){//如果没有自动高
					this.ctH=0;
					var mh=0;
					for(var i=0;i<this.mItems.length;i++){
						var h=this.mItems[i].getOHeight();
						if(h==null) continue;
						if(typeof(h)!="number") throw new Error("BorderLayout#init:该处中间组件高度不能为非数字."+h);
						mh=Math.max(h,mh);
					}
					this.ctH=mh;
				}
				else{
					this.ctH=-1;
				}
			}
			//上
			if(this.itemT&&this.ctH>=0){
				if(typeof(this.itemT.getOHeight())!="number") throw new Error("BorderLayout#init:该处中间组件高度不能为非数字.");
				this.ctH=this.itemT.isContainer&&this.itemT.isAutoHeight()?-1:(this.ctH+this.itemT.getOHeight());
			}
			//下
			if(this.itemB&&this.ctH>=0){
				if(typeof(this.itemB.getOHeight())!="number") throw new Error("BorderLayout#init:该处中间组件高度不能为非数字.");
				this.ctH=this.itemB.isContainer&&this.itemB.isAutoHeight()?-1:(this.ctH+this.itemB.getOHeight());
			}
		},


		/**
		 * 根据父宽分配子组件的宽度，主要是中间组件的宽度分配
		 *
		 * @param {Number} ctW 容器宽
		 * @param {Boolean?} force 强制
		 */
		setItemWidth:function(ctW,force){
			//如果强制||中间无自动
			if(this.mItems.length>0&&(force||!this.hasMidWA)){//中间只要有auto,尺寸就不做剩余分配
				var f=function(item){
					if(!item) return 0;
					var oW=item.getOWidth();

					var w=-1;
					if(oW==null){
						w=-1;
					}else if(oW=="auto"){
						if(force) w=item.getSize().outerWidth;
						else throw new Error("意料之外的异常");
					}else if(typeof(oW)=="string"&&oW.lastIndexOf("%")>=0){
						if(ctW>0) w=size2Num(oW,ctW);
					}else if(typeof(oW)=="number"){
						w=oW;
					}
					return w;
				};

				var wL=f(this.itemL);		//Left宽
				var wC=f(this.itemC);		//Center宽
				var wR=f(this.itemR);		//Right宽

				//当ctW有定值时,计算余宽
				if(wC<0){
					if(wL<0&&wR<0)			{						wC=ctW/3;			wL=ctW/3;					wR=ctW/3;}
					else if(wL>=0&&wR>=0)	{						wC=ctW-wL-wR;}
					else if(wL<0&&wR>=0)	{var restW=ctW-wR;		wC=0.5*restW;		wL=0.5*restW;}
					else if(wR<0)			{var restW=ctW-wL;		wC=0.5*restW;									wR=0.5*restW;}
				}else{
					if(wL<0&&wR<0)			{var restW=ctW-wC;							wL=0.5*restW;				wR=0.5*restW;}
					else if(wL<0)			{											wL=ctW-wC-wR;}
					else if(wR<0)			{																		wR=ctW-wC-wL;}
				}

				wL=parseInt(wL);
				wC=parseInt(wC);
				wR=parseInt(wR);

				if(this.itemL&&wL>=0){
					this.itemL.width=wL;
				}

				if(this.itemC&&wC>=0){
					this.itemC.width=wC;
					var _posLeft=wL>=0?wL:0;
					EG.css(this.itemC,"left:"+_posLeft+"px");
				}

				if(this.itemR&&wR>=0){
					this.itemR.width=wR;
				}
			}

			var f2=function(item,ctW){
				if(!item||ctW<0||item.getOWidth()=="auto") return;
				item.width=ctW;
			};

			f2(this.itemT,ctW);
			f2(this.itemB,ctW);
		},

		/**
		 * 根据父宽分配子组件的高度
		 *
		 * @param {Number} ctH 容器高
		 * @param {Boolean?} force 强制
		 */
		setItemHeight:function(ctH,force){
			//如果非强制&&有自动高  不分配
			if(!force&&this.hasItemHA) return;

			//MID高
			var hM=-1;
			//如果强制||中间无自动
			if(this.mItems.length>0){
				//var mH=0;
				for(var i=0;i<this.mItems.length;i++){
					var item=this.mItems[i];
					var h=item.getOHeight();
					if(h==null) continue;
					if(item.isContainer&&item.isAutoHeight()){//如果是自动高
						if(force) h=item.getSize().outerHeight;
						else throw new Error("非正常异常");
					}else{
						h=size2Num(h,this.ctH);
					}
					hM=Math.max(hM,h);
				}

			}else{
				hM=0;
			}

			var f=function(item){
				if(!item) return 0;
				var oH=item.getOHeight();
				var h=-1;
				if(oH==null){
					h=-1;
				}else if(oH=="auto"){
					if(force) h=item.getSize().outerHeight;
					else throw new Error("意料之外的异常3");

				}else if(typeof(oH)=="string"&&oH.lastIndexOf("%")>=0){
					if(ctH>0) h=size2Num(oH,ctH);
				}else if(typeof(oH)=="number"){
					h=oH;
				}
				return h;
			};

			//Bootom高
			var hB=f(this.itemB);
			var hT=f(this.itemT);

			//未分配的组件采用等分原则
			if(hM<0){//如果中间没有分配高度
				if(hT<0&&hB<0)			{						hM=ctH/3;			hT=ctH/3;			hB=ctH/3;}
				else if(hT>=0&&hB>=0)	{						hM=ctH-hB-hT;}
				else if(hT<0)			{var restH=ctH-hB;		hM=restH*0.5;		hT=restH*0.5;}
				else if(hB<0)			{var restH=ctH-hT;		hM=restH*0.5;							hB=restH*0.5;}
			}else{
				if(hT<0&&hB<0)			{var restH=ctH-hM;							hT=restH*0.5;		hB=restH*0.5;}
				else if(hT<0)			{											hT=ctH-hM-hB;}
				else if(hB<0)			{																hB=ctH-hM-hT;}
			}

			hM=parseInt(hM);
			hB=parseInt(hB);
			hT=parseInt(hT);

			if(this.itemT&&hT>=0) {
				this.itemT.height=hT;
			}
			if(this.itemB&&hB>=0){
				this.itemB.height=hB;
			}

			if(this.mItems.length>0&&hM>=0){
				for(var i=0;i<this.mItems.length;i++){
					var item=this.mItems[i];
					item.height=hM;
					EG.css(item,"top:"+hT+"px");
				}
			}
		},

		/**
		 * 自动尺寸
		 */
		autoSize:function(){
			//如果父组件的宽度和高度是自适应,叠加子组件高度和宽度,设置父尺寸(innerSize),重新Render所有子
			if(this.isCtWA||this.isCtHA){
				//宽度
				//Mid
				this.ctW=0;
				if(this.mItems.length>0&&this.refMW){
					var mw=0;
					for(var i=0;i<this.mItems.length;i++){
						var item=this.mItems[i];
						if(item.isContainer&&item.isAutoWidth()){
							mw+=item.getSize().outerWidth;
						}else{
							mw+=item.getOWidth();
						}
					}
					this.ctW=mw;
				}
				//Top
				if(this.itemT&&this.itemT.getOWidth()!=null){
					var _w=this.itemT.isContainer&&this.itemT.isAutoWidth()?this.itemT.getSize().outerWidth:this.itemT.getOWidth();
					this.ctW=Math.max(this.ctW,_w);
				}
				//Bottom
				if(this.itemB&&this.itemB.getOWidth()!=null){
					var _w=this.itemB.isContainer&&this.itemB.isAutoWidth()?this.itemB.getSize().outerWidth:this.itemB.getOWidth();
					this.ctW=Math.max(this.ctW,_w);
				}

				//高度
				//Mid
				this.ctH=0;
				var mh=0;
				for(var i=0;i<this.mItems.length;i++){
					var item=this.mItems[i];
					var h=item.getOHeight();
					if(h==null) continue;
					if(h=="auto"){
						h=item.getSize().outerHeight;
					}else if(typeof(h)=="string"&&h.lastIndexOf("%")>=0){
						if(this.isCtHA) throw new Error("意料之外的异常4");
						h=size2Num(h,this.ctH);
					}else if(typeof(h)=="number"){
						//
					}
					mh=Math.max(h,mh);
				}
				this.ctH=mh;
				//Top
				if(this.itemT&&this.itemT.getOHeight()!=null){
					this.ctH+=this.itemT.isContainer&&this.itemT.isAutoHeight()?this.itemT.getSize().outerHeight:this.itemT.getOHeight();
				}

				//Bottom
				if(this.itemB&&this.itemB.getOHeight()!=null){
					this.ctH+=this.itemB.isContainer&&this.itemB.isAutoHeight()?this.itemB.getSize().outerHeight:this.itemB.getOHeight();
				}

				if(this.isCtWA)	{
					this.container.setInnerWidth(this.ctW);
				}
				if(this.isCtHA){
					this.container.setInnerHeight(this.ctH);
				}
			}

			//如果子组件有自动尺寸，重新分配子尺寸
			if(this.hasItemWA) this.setItemWidth(this.ctW,true);
			if(this.hasItemHA) this.setItemHeight(this.ctH,true);
			if(this.hasItemWA||this.hasItemHA)	EG.ui.Layout.renderItems(this);
		},

		/**
		 * 渲染子元素
		 */
		renderItems:function(){
			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];
				item.render();
			}
		}
	});

	var size2Num=EG.Style.size2Num;
})();
/**
 * @class EG.ui.layout.TableLayout
 * @author bianrongjun
 * @extends EG.ui.Layout
 * Table布局器
 */
(function(){
	EG.define("EG.ui.layout.TableLayout",{

		extend:"EG.ui.Layout",

		config:{
			/** @cfg {?Number} maxRow 最大行 */
			maxRow		:0,
			/** @cfg {?Number} maxCol 最大列 */
			maxCol		:0,
			/** @cfg {?Number} cellSpacing 间隙 */
			cellSpacing :0,
			/** @cfg {?Number} cellPadding 间隔 */
			cellPadding :0,
			/** @cfg {?Number} border 边框 */
			border		:0,
			/** @cfg {?String} style 样式 */
			style		:null
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 * @param {EG.ui.Container} container 容器
		 */
		constructor:function(cfg,container){
			this.container	=container;			//容器
			this.initConfig(cfg);
			this.addOnLayout=true;				//layout添加模式
		},

		/**
		 * 获取Item
		 * @param {Object} cfg 配置
		 */
		getItem:function(cfg) {
			return EG.Array.get(cfg);
		},

		/**
		 * 补充列
		 */
		fixPos:function(){
			for(var i = 0,l=this.items.length;i<l; i++) {
				var pos=this.items[i].pos||[i,0];
				if(!EG.isArray(pos)) throw new Error("EG.ui.layout#fixPos:item的pos类型需为数组");
				if(pos.length==0){
					pos=[i,0];
				}else if(pos.length==1){
					pos=[pos[0],0];
				}else if(pos.length==2){
					//
				}else{
					throw new Error("EG.ui.layout#fixPos:item的pos长度最大为2");
				}


				this.maxRow=Math.max(pos[0]+1,this.maxRow);
				this.maxCol=Math.max(pos[1]+1,this.maxCol);

				this.items[i].pos=pos;
			}
		},

		/**
		 * 重新创建Table中的Row,Col
		 */
		resetTable:function(){
			//清除掉所有Row
			EG.DOM.removeAllRows(this.tbody);

			//创建Row
			for(var r=0;r<this.maxRow;r++){
				var tr=EG.CE({pn:this.tbody,tn:"tr"});
				//创建Col
				for(var c=0;c<this.maxCol;c++){
					EG.CE({pn:tr,tn:"td",style:"overflow:hidden"});
				}
			}
		},

		/**
		 * 放置Items
		 */
		putItems:function(){
			for(var i=0,l=this.items.length;i<l; i++) {
				var item=this.items[i];
				var pos=item.pos;
				var el=item.getElement();
				var td=this.tbody.childNodes[pos[0]].childNodes[pos[1]];
				if(item.vAlign){
					EG.css(td,"vertical-align:"+item.vAlign);
				}
				td.appendChild(el);
			}
		},

		/**
		 * 自动合并,设定宽比
		 */
		autoSpan:function(){
			var trs=this.tbody.childNodes;
			for ( var j = 0,jl= trs.length; j <jl; j++) {
				var tds=trs[j].childNodes;
				var rc=0;
				var oc=tds.length;

				//删除末端空TD
				for(var k=oc-1;k>=0;k--){
					if(tds[k].innerHTML=="") {
						rc++;
						trs[j].removeChild(tds[k]);
					}else{
						break;
					}
				}

				if(tds.length==0) continue;

				//设定末TD的colSpan
				if(rc>0){
					var tdEnd=tds[tds.length-1];
					tdEnd.colSpan=rc+1;
				}


				//设定TD宽比
				var p=parseInt(100/oc);
				for(var k=0,kl=tds.length;k<kl;k++){
					if(k==kl-1){
						tds[k].width=parseInt(((rc>0)?(p*(rc+1)):p))+"%";
					}else{
						tds[k].width=p+"%";
					}
				}
			}
		},

		/**
		 * @implements {EG.ui.Layout.prototype.doLayout}
		 */
		doLayout:function(){
			this.items		=this.container.items;	//items
//			if(this.container.id=="p2"){
//				alert("layout");
//			}
			this.hideItems();

			var cEle=this.container.getItemContainer();
			if(!this.table) {
				//TODO 当container Width,height为auto时候不指定
				this.table=EG.CE({pn:cEle,tn:"table",style:"width:100%;table-layout:fixed;"+(this.style?this.style:""),cellPadding:this.cellPadding,cellSpacing:this.cellSpacing,border:this.border});
				this.tbody=EG.CE({pn:this.table,tn:"tbody",style:"width:100%;"});
			}
			//修正坐标
			this.fixPos();
			//重置Table
			this.resetTable();
			//放置Items
			this.putItems();
			//自动合并行
			this.autoSpan();
//			if(this.container.id=="fp") alert(this.container)

			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];
				item.render();//渲染
			}

			this.autoSize();
		},

		/**
		 * 自动尺寸
		 */
		autoSize:function(){
			var aw=this.container.isAutoWidth();
			var ah=this.container.isAutoHeight();
			if(aw||ah){
				var s=EG.getSize(this.table);
				if(aw)	this.container.setInnerWidth(s.outerWidth);
				if(ah)	this.container.setInnerHeight(s.outerHeight);
			}
		}
	});
})();/**
 * @class EG.ui.layout.LineLayout
 * @author bianrongjun
 * @extends EG.ui.Layout
 * 线性布局器
 */
(function(){
	EG.define("EG.ui.layout.LineLayout",{

		extend:"EG.ui.Layout",

		config:{
			/** @cfg {?String} direct 方向 */
			direct		:"H",
			align		:null
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 * @param {EG.ui.Container} container 容器
		 */
		constructor:function(cfg,container){
			this.container	=container;			//容器
			this.initConfig(cfg);
			if(!this.align){
				this.align=this.direct=="H"?"left":"top";//默认值:左||上
			}
		},

		/**
		 * 获取Item
		 * @param {Object} cfg 配置
		 */
		getItem:function(cfg) {
			return EG.Array.get(cfg);
		},

		/**
		 * 布局
		 * @implements {EG.ui.Layout.prototype.doLayout}
		 */
		doLayout:function(){
			this.items		=this.container.items;	//items

			this.hideItems();

			EG.css(this.container.getItemContainer(), "position:absolute");

			//容器尺寸自动宽高
			this.isCtWA=this.container.isAutoWidth();this.isCtHA=this.container.isAutoHeight();
			//子组件尺寸有自动宽高
			this.hasItemWA=false;this.hasItemHA=false;
			//子组件尺寸有固定宽高
			this.hasItemWN=false;this.hasItemHN=false;
			//子组件尺寸有空宽高
			this.hasItemWE=false;this.hasItemHE=false;
			//子组件尺寸有百分比宽高
			this.hasItemWP=false;this.hasItemHP=false;

			this.ctW=this.isCtWA?-1:this.container.getInnerWidth();
			this.ctH=this.isCtHA?-1:this.container.getInnerHeight();

			//检测参数、设置尺寸
			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];

				//尺寸描述
				var sd=EG.ui.Layout.sizeDesc(item);

				//是否有子组件自动尺寸
				this.hasItemWA=this.hasItemWA||sd.isWA;this.hasItemHA=this.hasItemHA||sd.isHA;
				this.hasItemWN=this.hasItemWN||sd.isWN;this.hasItemHN=this.hasItemHN||sd.isHN;
				this.hasItemWE=this.hasItemWE||sd.isWE;this.hasItemHE=this.hasItemHE||sd.isHE;
				this.hasItemWP=this.hasItemWP||sd.isWP;this.hasItemHP=this.hasItemHP||sd.isHP;


				//如果父为自动，子组件不能含空||百分比
				if(this.direct=="H"){
					if(this.isCtWA&&(sd.isWE||sd.isWP)) throw new Error("EG.ui.layout.LineLayout:父组件宽度和子组件宽度不能同为空||百分比");
				}else{
					if(this.isCtHA&&(sd.isHE||sd.isHP)) throw new Error("EG.ui.layout.LineLayout:父组件高度和子组件高度不能同为空||百分比");
				}
			}

			//检测
			if(this.direct=="H"){
				if(this.isCtHA&&(!this.hasItemHN&&!this.hasItemHA)) throw new Error("EG.ui.layout.LineLayout:横向时,父组件高度为自动时,子组件高度不能既没有固定也没有自动");
			}else{
				if(this.isCtWA&&(!this.hasItemWN&&!this.hasItemWA)) throw new Error("EG.ui.layout.LineLayout:纵向时,父组件宽度为自动时,子组件宽度不能既没有固定也没有自动");
			}


			//如果宽高均为自动:子宽只能是WN或WA,高如果有HE或HP或HN默认使用最大值
			var max$= 0,	//最大尺寸
				unuse$=0,	//待分配数量
				avg$= 0,	//待平均尺寸
				use$=0  	//已占用尺寸
			;

			var sds={};

			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];
				var sd=EG.ui.Layout.sizeDesc(item);
				sds[i]=sd;

				//如果父不是自动宽,子是百分比,则分配子宽
				if(this.direct=="H"){
					if(!this.isCtWA&&sd.isWP){
						item.width=size2Num(item.getOWidth(),this.ctW);
					}
				}else{
					if(!this.isCtHA&&sd.isHP){
						item.height=size2Num(item.getOHeight(),this.ctH);
					}
				}

				//如果父尺寸为自动或者子尺寸为自动先渲染
				if(sd.isWA||sd.isHA||this.isCtHA||this.isCtWA){
					//alert(item.id+":"+item.getOWidth());
					item.render();
				}

				//如果父为自动高,计算最大高
				if(this.direct=="H"){
					if(this.isCtHA){
						if(sd.isHA){
							max$=Math.max(max$,item.getSize().outerHeight);
						}else if(sd.isHN){
							max$=Math.max(max$,item.height);
						}
					}
				}else{
					if(this.isCtWA){
						if(sd.isWA){
							max$=Math.max(max$,item.getSize().outerWidth);
						}else if(sd.isWN){
							max$=Math.max(max$,item.width);
						}
					}
				}

				//非空占用
				if(this.direct=="H"){
					if(!sd.isWE){
						use$+=item.width;
					}else{
						unuse$++;
					}
				}else{
					if(!sd.isHE){
						use$+=item.height;
					}else{
						unuse$++;
					}
				}
			}
			//如果父不是自动高,最大高为父高,
			if(this.direct=="H"){
				if(!this.isCtHA){
					max$=this.ctH;
				}
				if(unuse$) avg$=parseInt((this.ctW-use$)/unuse$);
			}else{
				if(!this.isCtWA){
					max$=this.ctW;
				}
				if(unuse$) avg$=parseInt((this.ctH-use$)/unuse$);
			}

			//设置所有子高为最大高
			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];
				var sd=sds[i];
				if(this.direct=="H"){
					item.height	=max$;
					if(sd.isWE){
						item.width=avg$;
					}
				}else{
					item.width	=max$;
					if(sd.isHE){
						item.height=avg$;
					}
				}
			}


			if(this.direct=="H"){
				//如果父宽为自动宽,累加宽度设置父的内度
				if(this.isCtWA){
					var ctW=0;
					for(var i=0,l=this.items.length;i<l;i++){
						var item=this.items[i];
						var w=item.getSize().outerWidth;
						//alert(item.id+":"+w+":"+item.getOWidth());
						ctW+=w;
					}
					this.container.setInnerWidth(ctW);
				}
				//如果父高为自动高,设置父的内高
				if(this.isCtHA){
					this.container.setInnerHeight(max$);
				}
			}else{
				if(this.isCtHA){
					var ctH=0;
					for(var i=0,l=this.items.length;i<l;i++){
						var item=this.items[i];
						var h=item.getSize().outerHeight;
						//alert(item.id+":"+w+":"+item.getOWidth());
						ctH+=h;
					}
					this.container.setInnerHeight(ctH);
				}
				if(this.isCtWA){
					this.container.setInnerWidth(max$);
				}
			}


			//最终定位
			var sub=0;
			for(var i=0,l=this.items.length;i<l;i++){
				var item=this.items[i];
				item.render();
				if(this.direct=="H"){
					EG.css(item,"position:absolute;"+this.align+":"+sub+"px");
					sub+=item.getSize().outerWidth;
				}else{
					EG.css(item,"position:absolute;"+this.align+":"+sub+"px");
					sub+=item.getSize().outerHeight;
				}

			}

			this.autoSize();
		},

		/**
		 * 自动尺寸
		 */
		autoSize:function(){
			var aw=this.container.isAutoWidth();
			var ah=this.container.isAutoHeight();
			if(aw||ah){
				//var cEle=this.container.getElement();
				var h=0,w=0;
				for(var i=0,l=this.items.length;i<l;i++){
					var item=this.items[i];
					var iSize=EG.getSize(item);
					if(this.direct=="V"){
						if(ah){
							h+=iSize.outerHeight;
						}

						if(aw){
							w=Math.max(w,iSize.outerWidth);
						}

					}else{

						if(ah){
							h=Math.max(h,iSize.outerHeight);
						}

						if(aw){
							w+=iSize.outerWidth;
						}
					}

				}

				if(aw)	this.container.setInnerWidth(w);
				if(ah)	this.container.setInnerHeight(h);
			}
		}

	});

	var size2Num=EG.Style.size2Num;
})();
/**
 * @class EG.ui.Panel
 * @author bianrongjun
 * @extends EG.ui.Container
 * 面板
 */
(function() {
	EG.define("EG.ui.Panel",{
		extend:"EG.ui.Container",

		constructor:function(cfg){
			this.callSuper([cfg]);
		}
	});
})();(function(){
	/**
	 * XPanel是带标题,可拖拽,可缩进的面板
	 */
	EG.define("EG.ui.XPanel",{
		extend:"EG.ui.Panel",
		config:{
			cls				:"eg_xpanel",
			/** @cfg {?Boolean} showTitle 显示标题 */
			showTitle		:true,
			/** @cfg {?Boolean} showExpand 显示扩展 */
			showExpand		:true,
			/** @cfg {?Boolean} showBorder 显示边框 */
			showBorder		:true,
			/** @cfg {?String} title 标题 */
			title			:null,
			collapseBehavior:"top",
			/** @cfg {?Boolean} collapseAble 能否收缩 */
			collapseAble	:false,
			collapsed		:false,
			barConfig		:null,
			bodyStyle		:null,
			headStyle		:null
		},
		constructor:function(cfg){
			this.callSuper([cfg]);

			this.setCollapseAble(this.collapseAble);
		},

		/**
		 * 创建
		 */
		build:function(){
			var me=this;
			this.element=EG.CE({tn:"div",cn:[
				this.dHead=EG.CE({tn:"div",cls:this.cls+"-dHead",cn:[
					this.dCollapse=EG.CE({tn:"div",cls:this.cls+"-dCollapse "+this.cls+"-dCollapse-top",onclick:function(){
						var d=EG.Style.isHide(me.dBody);
						me.collapse(!d);
					}}),
					this.dTitle=EG.CE({tn:"div",cls:this.cls+"-dTitle"})
				]}),
				this.dBody=EG.CE({tn:"div",cls:this.cls+"-dBody"})
			]});

			//设置标题
			if(this.title)		this.setTitle(this.title);

			//设置Bar
			if(this.barConfig)	this.setBar(this.barConfig);

			//设置Body样式
			if(this.bodyStyle)	EG.css(this.dBody,this.bodyStyle);

			//设置Head样式
			if(this.headStyle)	EG.css(this.dHead,this.headStyle);
		},

		/**
		 * 设置Bar
		 * @param bar
		 */
		setBar:function(bar){
			if(!this.dBar){
				this.dBar=new EG.ui.Panel({cls:this.cls+"-dBar"});
				this.dHead.appendChild(this.dBar.getElement());
			}
			this.dBar.clear();
			this.dBar.addItem(bar);
		},
		/**
		 * 渲染
		 */
		render:function(){
			if (this.element.parentNode == EG.getBody()) {
				EG.css(EG.getBody(),"margin:0px;padding:0px;width:100%;height:100%");
				EG.css(this.element,"position:absolute;left:0px;top:0px;");
			}
			EG.fit(this);

			//隐藏Body
			var dSize={width:"100%"};
			if(this.collapsed){
				dSize["height"]="100%";
				EG.hide(this.dBody);
			}else{
				EG.show(this.dBody);
			}

			//设置header
			EG.hide(this.dHead);
			EG.fit({
				element	:this.dHead,
				dSize	:dSize
			});
			EG.show(this.dHead);
			if(!this.collapsed){
				if(this.dBar){
					this.dBar.width	=EG.getSize(this.dHead).innerWidth-EG.getSize(this.dTitle).outerWidth-EG.getSize(this.dCollapse).outerWidth;
					this.dBar.height=EG.getSize(this.dHead).innerHeight;
					this.dBar.render();
				}
			}

			//设置body
			if(!this.collapsed){
				var sizeHead=EG.getSize(this.dHead);
				EG.fit({
					element	:this.dBody,
					dSize	:{
						width:"100%",
						height:(EG.getSize(this.element).innerHeight-sizeHead.outerHeight)
					}
				});

				if(this.items.length>0){//执行布局
					this.doLayout();
				}

//				if(this.oHeight=="auto"){
//					EG.css(this.element,"height:"+(sizeHead.outerHeight+EG.getSize(this.dBody).outerHeight)+"px");
//					//dSize["height"]=(EG.getSize(this.element).innerHeight-sizeHead.outerHeight);
//				}
			}else{
				//EG.css(this.element,"height:auto");
			}
		},
		/**
		 * 获取原高
		 * @return {*}
		 */
		getOHeight:function(){
			return this.oHeight;
		},
		/**
		 * 获取原宽
		 * @return {*}
		 */
		getOWidth:function(){
			if(this.collapsed){
				var s=EG.getSize(this.element);
				return s.outerWidth-s.innerWidth+EG.getSize(this.dCollapse).outerWidth;
			}else{
				return this.oWidth;
			}
		},		/**
		 * 获取Item容器
		 * @return {*}
		 */
		getItemContainer:function(){
			return this.dBody;
		},
		/**
		 * 设置能否缩进
		 * @param collapseAble
		 */
		setCollapseAble:function(collapseAble){
			this.collapseAble=collapseAble;
			if(!this.collapseAble){
				EG.hide(this.dCollapse);
			}
		},
		/**
		 * 设置缩进行为
		 * @param collapseBehavior 缩进行为
		 */
		setCollapseBehavior:function(collapseBehavior){
			this.collapseBehavior=collapseBehavior;
			this.refreshCollapseCln(this.collapsed);
		},
		/**
		 * 刷新缩进按钮样式
		 * @param collapsed
		 */
		refreshCollapseCln:function(collapsed){
			var b=this.collapseBehavior;
			var tv=false;
			if(collapsed){
				switch(this.collapseBehavior){
					case "top":			b="bottom"		;			break;
					case "right":		b="left"		;tv=true;	break;
					case "bottom":		b="top"			;			break;
					case "left":		b="right"		;tv=true;	break;
				};
			}
			EG.setCls(this.dCollapse,["dCollapse","dCollapse-"+b],this.cls);
			if(tv){
				EG.css(this.dTitle,"writing-mode:lr-tb");
				if(this.dBar) EG.hide(this.dBar);
			}else{
				EG.css(this.dTitle,"writing-mode:;");
				if(this.dBar) EG.show(this.dBar);
			}
		},

		/**
		 * 收缩
		 */
		collapse:function(collapsed){
			//刷新样式
			this.refreshCollapseCln(collapsed);
			if(collapsed){
				var bSize=EG.getSize(this.dBody);
				EG.hide(this.dBody);
				this._oWidth	=this.oWidth;
				this._oHeight	=this.oHeight;
				if(EG.$in(this.collapseBehavior,["top","bottom"])){
					this.oHeight=this.height-bSize.outerHeight;
				}else if(EG.$in(this.collapseBehavior,["left","right"])){
					this.oWidth=20;
					EG.css(this.dHead,"overflow:hidden;writing-mode:lr-tb;");
				}
			}else{
				this.oWidth=this._oWidth;
				this.oHeight=this._oHeight;
				EG.show(this.dBody);
				EG.css(this.dHead,"height:auto;");
			}
			this.collapsed=collapsed;
			if(this.pItem){
				this.pItem.render();
			}
		},

		/**
		 * 设置标题
		 * @param title
		 */
		setTitle:function(title){
			this.title=title;
			this.dTitle.innerHTML=this.title;
		},

		/**
		 * 设置内高(dBody)
		 * @param h
		 */
		setInnerHeight:function(h){
			//设定Body高度
			EG.Item.pack({
				pEle:this.dBody,
				height:h
			});

			//设定外层高度
			var rs=EG.Item.pack({
				pEle:this.element,
				height:EG.getSize(this.dBody).outerHeight+EG.getSize(this.dHead).outerHeight
			});

			this.height=rs.height;
		},
		/**
		 * 获取内高(dBody)
		 * @return {*}
		 */
		getInnerHeight:function(){
//			alert(this.oHeight);
//			if(typeof(this.oHeight)=="number"){
				return EG.getSize(this.element).innerHeight-EG.getSize(this.dHead).outerHeight;
//			}else{
//				return this.oHeight;
//			}
		}
	});


})();
/**
 * @class EG.ui.TabPanel
 * @author bianrongjun
 * @extends EG.ui.Container
 * 选项卡面板,不支持布局管理器
 */
(function(){
	EG.define("EG.ui.TabPanel",{
		extend:"EG.ui.Container",
		config:{
			runOnSelectOnBuild	:true,			//是否在创建时运行Tab的 onselect
			cls					:"eg_tabPanel",	//CSS类
			direct				:"top"			//选项卡位置
		},

		constructor:function(cfg){
			this.curIdx		=-1,//当前已选择的索引
			this.panels		=[];
			this.tabs		=[];
			this.callSuper([cfg]);
		},

		/**
		 * @override
		 */
		afterBuild:function(){
			if(this.itemsConfig&&this.itemsConfig.length>0){
				this.add(this.itemsConfig,true);
				this.select(0);
			}
		},

		/**
		 * @override {EG.ui.Container#build}
		 */
		build:function(){
			this.element=EG.CE({tn:"div",cn:[
				this.dPanels=EG.CE({tn:"div",cls:this.cls+"-panels"+this.direct}),
				this.dTabs	=EG.CE({tn:"div",cls:this.cls+"-tabs"+this.direct})
			]});
		},

		/**
		 * @override
		 */
		render:function() {
			//修父BODY样式
			EG.ui.Item.fixBody(this.element);

			//设定外部尺寸
			EG.fit(this);

			//缓存size
			this.size=EG.getSize(this.element);

			if(this.direct=="top"||this.direct=="bottom"){
				//设定dTabs尺寸
				EG.fit({
					element:this.dTabs,
					dSize:{width:"100%"},
					pSize:{width:this.size.innerWidth},
					type:"width"
				});
				this.tabsSize=EG.getSize(this.dTabs);

				EG.css(this.dTabs,"position:absolute;"+this.direct+":0px;left:0px");
				this.tabsSize=EG.getSize(this.dTabs);

				//设定dPanels尺寸
				EG.fit({
					element:this.dPanels,
					pSize:{width:this.size.innerWidth,height:this.size.innerHeight-this.tabsSize.outerHeight}
				});
				this.panelsSize=EG.getSize(this.dPanels);
				EG.css(this.dPanels,"position:absolute;"+this.direct+":"+this.tabsSize.outerHeight+"px;left:0px");
			}else if(this.direct=="left"||this.direct=="right"){
				//设定dTabs尺寸
				EG.fit({
					element:this.dTabs,
					dSize:{height:this.size.innerHeight},
					pSize:this.size,
					type:"height"
				});

				EG.css(this.dTabs,"position:absolute;"+this.direct+":0px;top:0px");
				this.tabsSize=EG.getSize(this.dTabs);

				//设定dPanels尺寸
				EG.fit({
					element:this.dPanels,
					pSize:{width:this.size.innerWidth-this.tabsSize.outerWidth,height:this.size.innerHeight}
				});
				this.panelsSize=EG.getSize(this.dPanels);
				EG.css(this.dPanels,"position:absolute;"+this.direct+":"+this.tabsSize.outerWidth+"px;top:0px");

			}

			this.doLayout();
		},

		/**
		 * 获取Tabs Element
		 */
		getElementTabs:function() {
			return this.dTabs;
		},

		/**
		 * 获取Panels Element
		 */
		getElementPanels:function() {
			return this.dPanels;
		},

		/**
		 * 添加组件
		 * @param cfg 配置
		 * @param render 渲染
		 * @returns {EG.ui.Tab}
		 */
		add:function(cfg,render){
			if(render==null) render=true;

			var cfgs=(!EG.isArray(cfg))?[ cfg ]:cfg;
			var tab,panel;
			for (var i = 0,il = cfgs.length; i < il; i++) {
				cfg = cfgs[i];
				if(cfg["panel"]["width"]==null) 	cfg["panel"]["width"]="100%";
				if(cfg["panel"]["height"]==null) 	cfg["panel"]["height"]="100%";

				cfg["tab"]["clsPre"]=this.cls+"-tabs"+this.direct;
				cfg["panel"]["className"]=this.cls+"-"+"panels-panel";

				tab		=new EG.ui.Tab	(this,cfg["tab"]);
				panel	=new EG.ui.Panel(cfg["panel"]);

				this.tabs.push(tab);
				this.panels.push(panel);

				this.dTabs.appendChild(tab.getElement());
				this.dPanels.appendChild(panel.getElement());



				this.items.push(panel);
			}

			//自动选择新添加的
			this.select(tab);

			return tab;
		},

		/**
		 * 关闭
		 * @param idx 索引
		 */
		close:function(idx){
			// 获取Tab
			var tab;
			if(idx instanceof EG.ui.Tab){
				tab=idx;
				idx=this.getTabIdx(tab);
			}else{
				tab=EG.Array.get(this.tabs, idx);
			}

			var p=tab.getPanel();

			var selected=(idx==this.curIdx);	//待删除的是否已选中

			//销毁
			tab.destroy();
			p.destroy();

			//DOM移除
			this.dTabs.removeChild	(tab.element);
			this.dPanels.removeChild(p.getElement());


			EG.Array.del(this.items	,idx);
			EG.Array.del(this.tabs	,idx);
			EG.Array.del(this.panels,idx);

			if(!selected) return;

			//自动选择Tab
			if(idx>=this.panels.length) idx=this.panels.length-1;
			if(idx>=0) this.select(idx);
		},

		/**
		 * 选择选项卡
		 * @param tab 索引||Tab
		 * @param noRender
		 */
		doSelect:function(tab,noRender){
			var idx=-1;

			// 变换样式,隐藏Panel
			for (var i=0,l=this.panels.length;i<l;i++) {
				var p = this.panels[i];
				var t = this.tabs[i];
				if (t!= tab) {
					EG.setCls(t.element,"tab",this.cls+"-tabs"+this.direct);
					EG.hide(p.getElement());
				} else {
					idx=i;
				}
			}

			//CP[Chrome]:如果不先隐藏掉其它Panel,会一瞬间把外层容器撑大，外层容器是overflow,当子再隐藏的时候不会还原
			EG.setCls(this.tabs[idx].element,["tab","selected"],this.cls+"-tabs"+this.direct);
			EG.show(this.panels[idx].getElement());
			if(!noRender) this.panels[idx].render();

			//设置当前索引
			this.curIdx = idx;
		},

		/**
		 * 选择
		 */
		select:function(idx){
			var tab=(idx instanceof EG.ui.Tab)?idx:EG.Array.get(this.tabs, idx);
			tab.select();
		},

		/**
		 * 获取Tab
		 */
		getTabs:function() {
			return this.tabs;
		},

		/**
		 * 获取Panels
		 */
		getPanels:function() {
			return this.panels;
		},

		/**
		 * 获取Panel
		 *
		 * @param idx 索引
		 * @returns {EG.ui.Panel}
		 */
		getPanel:function(idx) {
			return EG.Array.get(this.panels,idx);
		},

		/**
		 * 获取Tab
		 *
		 * @param idx 索引
		 * @returns {EG.ui.TabPanel.Tab}
		 */
		getTab:function(idx) {
			return EG.Array.get(this.tabs,idx);
		},

		/**
		 * 获取Tab索引
		 *
		 * @param tab {Number}
		 */
		getTabIdx:function(tab) {
			for ( var i = 0; i < this.tabs.length; i++)
				if (this.tabs[i] == tab) {
					return i;
				}
		},

		/**
		 * 获取选中的Tab索引
		 */
		getSelectedIdx:function() {
			return this.curIdx;
		},

		/**
		 * 获取选中Tab
		 * @returns {EG.ui.TabPanel.Tab}
		 */
		getSelectedTab:function() {
			return this.getTab(this.curIdx);
		},

		/**
		 * 执行
		 */
		doLayout:function() {
			if(!this.element.parentNode) return;

			if(this.getSelectedIdx<0) return;

			var p=this.panels[this.getSelectedIdx()];
			if(p==null) return;
			p.pSize=this.panelsSize;
			p.render();
		},

		destroy:function(){
			for(var i=0;i<this.tabs.length;i++){
				this.tabs[i].destroy();
			}

			for(var i=0;i<this.panels.length;i++){
				this.panels[i].destroy();
			}

			this.tabs=null;
			this.panels=null;
			this.items=null;
		}
	});
})();
(function(){
	/**
	 * EG.ui.TabPanel.Tab 选项卡
	 */
	EG.define("EG.ui.Tab",{
		extend:"EG.ui.Item",
		config:{
			title		:"选项卡",	//标题
			closeable	:false,		//是否可以关闭
			onclick		:null,		//点击时的事件
			onselect	:null,		//被选中时事件
			onclose		:null,		//在关闭时事件
			afterselect	:null,		//选中后事件
			clsPre		:null
		},
		constructor:function(tabPanel,cfg){
			this.pTabPanel	=tabPanel;

			this.callSuper([cfg]);

			//设置能否关闭
			this.setCloseable(this.closeable);
		},

		/**
		 * 创建
		 */
		build:function(){
			var ME=EG.ui.Tab;
			this.element=EG.CE({tn:"div",cls:this.clsPre+"-tab "+this.clsPre+"-selected",me:this,cn:[
				this.dTitle	=EG.CE({tn:"div"	,cls:this.pTabPanel.cls+"-tabs-tab-title",innerHTML:this.title}),
				this.dCloser=EG.CE({tn:"a"		,cls:this.pTabPanel.cls+"-tabs-tab-closer",me:this,
					onclick		:ME._events.dCloser.onclick,
					onmouseover	:ME._events.dCloser.onmouseover,
					onmouseout	:ME._events.dCloser.onmouseout
				})
			],
				onclick:ME._events.element.onclick
			});


		},

		/**
		 * 设置是否能关闭
		 * @param closeable 可关闭
		 */
		setCloseable:function(closeable){
			if(closeable){
				EG.show(this.dCloser);
			}else{
				EG.hide(this.dCloser);
			}
		},

		/**
		 * 执行点击
		 */
		doClick:function() {
			if(this.onclick) this.onclick.apply(this);
			this.select();
		},
		
		/**
		 * 设置标题
		 * @param title 标题
		 */
		setTitle:function(title) {
			this.dTitle.innerHTML = title;
		},
		
		/**
		 * 获取索引
		 */
		getIdx:function() {
			return this.pTabPanel.getTabIdx(this);
		},
		
		/**
		 * 选中选项卡
		 */
		select:function() {
			if(this.onselect)	{this.onselect.apply(this);}
			this.pTabPanel.doSelect(this);
			if(this.afterselect){this.afterselect.apply(this);}
		},
		
		/**
		 * 是否已被选中
		 * @returns {Boolean}
		 */
		isSelected:function() {
			return this.pTabPanel.curIdx == this.getIdx();
		},
		
		/**
		 * 关闭
		 */
		close:function(){
			if(this.onclose) this.onclose.apply(this);
			this.pTabPanel.close(this);
		},
		
		/**
		 * 获取关联的Panel
		 * @returns {EG.ui.Panel}
		 */
		getPanel:function(){return this.pTabPanel.getPanel(this.getIdx());},

		statics:{
			_events:{
				element:{
					onclick:function(e){
						var me=this.me;
						me.doClick();EG.Event.stopPropagation(e);
					}
				},
				dCloser:{
					onclick		:function(e){
						var me=this.me;
						me.close();EG.Event.stopPropagation(e);
					},
					onmouseover	:function(){
						var me=this.me;
						EG.setCls(this,["tabs-tab-closer","tabs-tab-closer_on"],me.pTabPanel.cls);
					},
					onmouseout	:function(){
						var me=this.me;
						EG.setCls(this,["tabs-tab-closer"],me.pTabPanel.cls	);
					}

				}

			}
		}
	});
})();

/**
 * @class EG.ui.Tree
 * @author bianrongjun
 * @extends EG.ui.Item
 * 树
 */
(function(){
	/**
	 * EG.ui.Tree 树
	 */
	EG.define("EG.ui.Tree",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {String?} 根节点标题 */
			rootTitle		:"根目录",
			/** @cfg {Boolean?} 是否多选 */
			multiple		:false,
			/** @cfg {Boolean?} 是否启用Box,如果多选自动启用box */
			usebox			:false,
			/** @cfg {Boolean?} 是否可以拖拽 */
			dragable		:false,
			/** @cfg {String?} 样式 */
			cls				:"eg_tree",
			/** @cfg {Function?} 拖拽时事件 */
			ondrag			:null,
			/** @cfg {Boolean?} 能否取消选择 */
			deSelectable	:true,
			/** @cfg {Function?} 默认点击事件 */
			onclick			:null
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			//加载
			Tree.load();

			this.callSuper([cfg]);

			//如果过选自动启用UseBox
			if(this.multiple) 	this.usebox=true;

			//创建RootNode
			this.rootNode=new EG.ui.TreeNode({
				title		:this.rootTitle,
				root		:true,
				onclick		:this.onclick,
				onclickSrc	:this.onclickSrc
			},this);
			this.rootNode.setClassNamePre(this.cls+"-");

			//是否能拖拽
			if(!this.dragable){
				EG.hide(this.rootNode.dInsert);
			}

			//根节点添加
			EG.CE({pn:this.element,ele:this.rootNode.getElement(),style:"margin-left:0px"});

			//隐藏Box
			if(!this.box){
				EG.hide(this.rootNode.box.getElement());
			}

			//清空初始化
			this.reset();
		},

		/**
		 * 创建Element
		 */
		build:function(){
			this.element=EG.CE({tn:"div",cls:this.cls,unselectable:"on",
				onmousemove:function(){
					if(Tree.curDrag!=null&&Tree.curDrag.style.display!="none"){
						EG.css(Tree.curDrag,"top:"+(event.clientY+10)+"px;left:"+(event.clientX+10)+"px;");
					}
				},
				onselectstart:function(){return false;}
			});
		},

		/**
		 * 清空初始化
		 */
		reset:function(){
			this.treeNodes		=[];														//所有树节点
			if(this.rootNode.selected){
				this.rootNode.blur();
			}

			this.selectedNode	=null;														//已选节点
			this.selectedNodes	=[];														//已选的多节点
			//this.rootNode		=null;														//根节点
			EG.DOM.removeChilds(this.rootNode.dChildNodes);
		},

		/**
		 * 渲染
		 */
		render:function(){
			EG.fit(this);
		},
		
		/**
		 * 添加
		 * @param cfg
		 */
		add:function(cfg) {
			EG.ui.TreeNode.prototype.add.apply(this.rootNode, arguments);
		},
		
		/**
		 * 添加子
		 * @param treeNode
		 */
		appendChild:function(treeNode) {
			treeNode.tree = this;
			this.childNodes.push(treeNode);
		},
		
		/**
		 * 获取选中的
		 */
		getSelected:function() {
			return this.multiple ? this.selectedNodes : this.selectedNode;
		},
		
		/**
		 * 获取Element
		 */
		getElement:function() {
			return this.element;
		},
		
		/**
		 * 获取根节点
		 * @returns {EG.ui.TreeNode}
		 */
		getRootNode:function(){
			return this.rootNode;
		},
		
//		/**
//		 * 选中节点
//		 */
//		selectedNode:function(){},

		/**
		 * 删除所有节点
		 */
		removeAll:function(){
			this.rootNode.removeChilds();
		},

		/**
		 * 注册节点
		 *
		 * @param treeNode {TreeNode}
		 */
		registNode:function(treeNode) {
			this.treeNodes.push(treeNode);
			treeNode.tree = this;
			treeNode.setClassNamePre(this.cls+"-");
		},
		
		/**
		 * 卸载节点
		 *
		 * @param treeNode
		 */
		ungistNode:function(treeNode) {
			EG.Array.remove(this.treeNodes, treeNode);
		},
		
		/**
		 * 展开所有节点
		 */
		expandAll:function() {
			for ( var i = 0, l = this.treeNodes.length; i < l; i++)
				this.treeNodes[i].expand();
		},
		
		/**
		 * 收缩所有节点
		 */
		collapseAll:function() {
			for ( var i = 0, l = this.treeNodes.length; i < l; i++) {
				this.treeNodes[i].collapse();
			}
		},
		
		/**
		 * 展开到指定层数的节点
		 * @param maxlv
		 */
		expandLv:function(maxlv){
			this.collapseAll();
			var f=function(node,lv){
				node.expand();
				if(lv>=maxlv) return;
				lv++;
				if(node.childNodes.length>0) for(var i=0,l=node.childNodes.length;i<l;i++) f(node.childNodes[i],(lv));
			};
			f(this.getRootNode(),1);
		},
		/**
		 * 取消所有选择
		 */
		deSelect:function(){
			if(this.multiple){
				if(!this.selectedNodes) return;
				for(var i=this.selectedNodes.length-1;i>=0;i--){
					this.selectedNodes[i].select(false);
				}
			}else{
				if(!this.selectedNode) return;
				this.selectedNode.select(false);
			}
		},


		/**
		 * 展开某个节点链
		 * @param node
		 */
		expandChain:function(node){																			//
			this.collapseAll();
			while((node=node.parentNode)!=null) node.expand();
		},
		
		statics:{
			//拖拽线程
			dragThread:null,
			//当前坐标
			curXY:null,
			//当前拖拽对象
			curDrag:null,
			/**
			 * 是否正在拖拽
			 */
			isDraging:function() {
				return EG.ui.Tree.curDrag != null&&!EG.Style.isHide(EG.ui.Tree.curDrag);
			},
			loaded:false,
			/**
			 * 加载
			 */
			load:function(){
				if(Tree.loaded) return;
				//监听Document onmouseup事件
				EG.bindEvent(EG.doc,"mouseup",function(){
					if(Tree.dragThread!=null) window.clearTimeout(Tree.dragThread);
					if(Tree.curDrag){
						EG.hide(Tree.curDrag);
					}
				});
				Tree.loaded=true;
			}
		}
	});

	var Tree=EG.ui.Tree;
	//注册部件类
	EG.ui.Item.regist("tree",Tree);
})();
/**
 * @class EG.ui.TreeNode
 * @author bianrongjun
 * @extends EG.ui.Item
 * 树节点
 */
(function(){
	EG.define("EG.ui.TreeNode",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {String?} 标题 */
			title			:"节点",
			/** @cfg {Object?} 值 */
			value			:null,
			/** @cfg {Boolean?} 是否显示Boxs */
			showbox			:false,
			/** @cfg {Boolean?} 是否根节点 */
			root			:false,
			/** @cfg {Function?} 点击事件 */
			onclick			:null,
			/** @cfg {Function?} 拖拽事件 */
			ondrag			:null,
			/** @cfg {EG.ui.Tree?} 树 */
			tree			:null,
			/** @cfg {String?} CSS类 */
			cls				:"eg_tree",
			/** @cfg {String?} 标题样式 */
			titleStyle		:null
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 * @param {EG.ui.Tree?} tree 树
		 */
		constructor:function(cfg,tree){

			//点击事件源
			//this.onclickSrc=this;

			this.callSuper([cfg]);

			this.tree		=tree||this.tree;
			this.parentNode	=null;								//父节点
			this.childNodes	=[];								//子节点
			this.ondrag		=this.ondrag||(this.tree!=null?this.tree.ondrag:null);
																//Element
			var me=this;

			//BOX
			this.box=new EG.ui.Box({showText:false,style:"display:inline",onclick:function(){
				me.select(!me.selected);
			}});
			this.dNode.appendChild(this.box.getElement());
			EG.css(this.box.getElement().childNodes[0],"margin-left:3px");
		},

		/**
		 * 静态
		 */
		statics:{
			/**
			 * 事件
			 */
			_events:{
				dExpandBtn:{
					onclick:function(){
						var me=this.me;
						me.changeCollapsed();
					}
				},

				dTitle:{
					ondblclick:function(){
						var me=this.me;
						me.changeCollapsed();
					},
					onclick:function(){
						var me=this.me;
						me.select(me.tree.deSelectable?!me.box.selected:true);
						if(me.onclick) me.onclick.apply(me["onclickSrc"]||me);
					},
					onmouseup:function(){
						var me=this.me;
						if(Tree.isDraging()) Tree.curDrag["node"].moveto("in",me);
					},
					onmousedown:function(event){
						var me=this.me;
						//如果不能拖拽或对象为根节点，返回
						if(!me.tree.dragable||me==me.tree.rootNode) return;
						Tree.curXY=[event.clientX,event.clientY];
						var curNe=me;
						//执行创建拖动线程
						Tree.dragThread=window.setTimeout(function(){
							if(Tree.curDrag==null) Tree.curDrag=EG.CE({pn:EG.getBody(),tn:"div",cls:"eg_tree_drager"});
							Tree.curDrag.node=curNe;
							Tree.curDrag.innerHTML=curNe.title;
							EG.show(Tree.curDrag);
							EG.css(Tree.curDrag,"top:"+(Tree.curXY[1]+10)+"px;left:"+(Tree.curXY[0]+10)+"px;");
						},300);
					}

				},

				dInsert:{
					onmouseover:function(){
						var me=this.me;
						if(me==me.tree.rootNode) return;

						if(Tree.isDraging()){
							EG.css(this,"background-color:red;margin-left:7px;");
						}
					},
					onmouseout:function(){
						EG.css(this,"background-color:;margin-left:;");
					},
					onmouseup:function(){
						var me=this.me;
						if(me==me.tree.rootNode){
							return;
						}
						if(Tree.isDraging()){
							Tree.curDrag["node"].moveto("after",me);
							//Tree.curDrag=null;
						}
					}
				}

			}
		},
		/**
		 * 创建
		 */
		build:function(){
			//此处赋的className只是示意
			this.element=EG.CE({tn:"div",cn:[
				this.dNode=EG.CE({tn:"div",cn:[ 															//节点
					//树形展开符
					this.dExpandBtn=EG.CE({tn:"span",me:this,onclick:ME._events.dExpandBtn.onclick}),

					//标题
					this.dTitle=EG.CE({tn:"span",innerHTML:this.title,me:this,
						ondblclick	:ME._events.dTitle.ondblclick,
						onclick		:ME._events.dTitle.onclick,
						onmouseup	:ME._events.dTitle.onmouseup,
						onmousedown	:ME._events.dTitle.onmousedown
					})
				]}),
				//子节点集合
				this.dChildNodes=EG.CE({tn:"div"}),
				//插入的div
				this.dInsert=EG.CE({tn:"div",me:this,
					onmouseover	:ME._events.dInsert.onmouseover,
					onmouseout	:ME._events.dInsert.onmouseout,
					onmouseup	:ME._events.dInsert.onmouseup
				})
			]});

			if(this.titleStyle) EG.css(this.dTitle,this.titleStyle);
		},

		/**
		 * 执行点击
		 */
		doClick:function(){
			EG.Event.fireEvent(this.dTitle,"click");
		},

		/**
		 * 设值标题
		 * @param title 标题
		 */
		setTitle:function(title){
			this.title=title;
			this.dTitle.innerHTML=title;
		},

		/**
		 * 设值
		 * @param value 值
		 */
		setValue:function(value){
			this.value=value;
		},

		/**
		 * 获值
		 */
		getValue:function(){
			return this.value;
		},

		/**
		 * 选中
		 * @param selected 是否选中
		 */
		select:function(selected){

			if(selected){
				this.focus();
				this.box.select(true);
				if(this.tree.multiple){
					if(!EG.Array.has(this.tree.selectedNodes,this)){
						this.tree.selectedNodes.push(this);
					}
				}else{
					if(this.tree.selectedNode!=null&&this.tree.selectedNode!=this) this.tree.selectedNode.select(false);
					this.tree.selectedNode=this;
				}
			}else{
				this.blur();
				this.box.select(false);
				if(this.tree.multiple){
					EG.Array.remove(this.tree.selectedNodes,this);
				}else this.tree.selectedNode=null;
			}
			this.selected=selected;
		},

		/**
		 * 设置ClassNamePre
		 * @param clsPre
		 */
		setClassNamePre:function(clsPre){
			this.element.className		=clsPre+"node";
			this.dNode.className		=clsPre+"node-dNode";
			this.dExpandBtn.className	=clsPre+"node-dNode-dExpandBtn";
			this.dTitle.className		=clsPre+"node-dNode-dTitle";
			this.dChildNodes.className	=clsPre+"node-dChildNodes";
			this.dInsert.className		=clsPre+"node-dInsert";
		},

		/**
		 * 失去焦点
		 */
		blur:function(){
			EG.css(this.dTitle,{
				backgroundColor:"",
				color:""
			});
		},

		/**
		 * 聚焦
		 */
		focus:function(){
			EG.css(this.dTitle,{
				backgroundColor:"#3399ff",
				color:"white"
			});
		},

		/**
		 * 是否已合并
		 */
		isCollapsed:function(){
			return EG.Style.isHide(this.dChildNodes);
		},

		/**
		 * 切换合并状态
		 */
		changeCollapsed:function(){
			if(this.isCollapsed()) this.expand();
			else this.collapse();
		},

		/**
		 * 展开
		 */
		expand:function(force){

			EG.show(this.dChildNodes);

			if(force){
				var pNodes=[];
				var pNode=this;
				while(pNode.parentNode){
					pNodes.push(pNode);
					pNode=pNode.parentNode;
				}
				for(var i=pNodes.length-1;i>=0;i--){
					pNodes[i].expand();
				}

			}

			this.refreshCollapseElement();
		},

		/**
		 * 合并
		 */
		collapse:function(){
			EG.hide(this.dChildNodes);
			this.refreshCollapseElement();
		},

		/**
		 * 前一个节点
		 */
		preNode:function(){
			var cns=this.parentNode.childNodes;
			for(var i=0; i<cns.length; i++) if(cns[i]==this) return (i==0)?null:cns[i-1];
			return null;
		},

		/**
		 * 后一个节点
		 */
		nextNode:function(){
			var cns=this.parentNode.childNodes;
			for(var i=0; i<cns.length; i++) if(cns[i]==this) return (i>=cns.length-1)?null:cns[i+1];
			return null;
		},

		/**
		 * 添加节点
		 * @param {EG.ui.TreeNode} treeNode 节点
		 * @param {Number?} idx 插入位置
		 */
		add:function(treeNode,idx){

			//加到末尾
			if(idx==null){
				idx=this.childNodes.length-1;
				this.childNodes.push(treeNode);

				this.dChildNodes.appendChild(treeNode.getElement());

			//插到指定位置
			}else{
				this.childNodes=EG.Array.insert(this.childNodes,idx+1,treeNode);
				if(this.dChildNodes.childNodes.length>idx+1){
					var oChild=this.dChildNodes.childNodes[idx+1];
					this.dChildNodes.insertBefore(treeNode.getElement(),oChild);
				}else{
					this.dChildNodes.appendChild(treeNode.getElement());
				}
			}

			//注册到树
			this.tree.registNode(treeNode);

			//设定父节点
			treeNode.parentNode=this;

			//刷新当前节点
			this.refreshCollapseElement();

			//刷新子节点
			treeNode.refreshCollapseElement();

			//如果子节点大于1，刷新倒数第二个子节点的收缩显示
			if(this.childNodes.length>1){
				this.childNodes[this.childNodes.length-2].refreshCollapseElement();
			}

			//如果未开启多选，隐藏Box
			if(!this.tree.multiple) EG.hide(treeNode.box.getElement());
		},

		/**
		 * 删除
		 * @param {Boolean?} refresh 是否刷新
		 */
		remove:function(refresh){
			if(refresh==null) refresh=true;

			//移除子节点
			this.removeChilds();

			//前节点
			var preN=this.preNode();

			//从树上移除
			this.tree.ungistNode(this);

			//从父节点移除
			EG.Array.remove(this.parentNode.childNodes,this);

			//从DOM上移除
			this.parentNode.dChildNodes.removeChild(this.getElement());

			//刷新节点
			if(refresh){
				this.parentNode.refreshCollapseElement();//刷新父节点
				if(preN) preN.refreshCollapseElement(); //刷新前节点
			}
		},

		/**
		 * 删除所有子节点
		 * @param {Boolean?} refresh 是否刷新
		 */
		removeChilds:function(refresh){
			if(refresh==null) refresh=true;

			for(var i=this.childNodes.length-1; i>=0; i--){
				this.childNodes[i].remove(false);
			}

			if(refresh) this.refreshCollapseElement();
		},

		/**
		 * 刷新展开区
		 */
		refreshCollapseElement:function(){														//刷新节点
			//清除子节点
			EG.DOM.removeChilds(this.dExpandBtn);

			//设置线和文件夹的样式
			var p1	//线
				, p2;//文件夹
			if(this.isLeaf()){									//如果是叶节点
				p1=this.cls+"-node-l-"+(this.isLast()?"l":"t");
				p2=this.cls+"-node-file";//"file.png";
			}else{												//如果有子节点
				if(this.isCollapsed()){								//如果已经闭合
					p1=this.cls+"-node-l-"+(this.isLast()?"lPlus":"tPlus");
					p2=this.cls+"-node-folder";//"foldericon.gif";
				}else{												//如果已经打开
					p1=this.cls+"-node-l-"+(this.isLast()?"lMinus":"tMinus");
					p2=this.cls+"-node-openfolder";//"openfoldericon.gif";

					//刷新dChildNodes样式
					if(!this.isLast()){
						EG.setCls(this.dChildNodes,["node-dChildNodes","node-bgLine"],this.cls);
					}else{
						EG.setCls(this.dChildNodes,["node-dChildNodes"],this.cls);
					}
				}
			}
			if(!this.isRoot()){
				EG.CE({pn:this.dExpandBtn,tn:"div",cls:p1});
			}
			EG.CE({pn:this.dExpandBtn,tn:"div",cls:p2});

			//刷新dInsert样式
			if(!this.isLast()){
				EG.setCls(this.dInsert,["node-dInsert","node-bgLine"],this.cls);
			}else{
				EG.setCls(this.dInsert,["node-dInsert"],this.cls);
			}

			//刷新最margin
			if(this.parentNode&&this.parentNode.isRoot()){
				EG.css(this.element,"margin-left:0px");
			}
		},
		/**
		 * 是否为最后一个
		 * @returns {Boolean}
		 */
		isLast:function(){																	//是否为末节点
			if(this.parentNode==null) return true;
			return this.parentNode.childNodes[this.parentNode.childNodes.length-1]==this;
		},
		/**
		 * 是否为第一个节点
		 * @returns {boolean}
		 */
		isFirst:function(){
			return this.parentNode.childNodes[0]==this;
		},
		/**
		 * 是否为就页节点
		 */
		isLeaf:function(){
			return this.childNodes.length==0;
		},
		isRoot:function(){
			return this.root;
		},
		/**
		 * 当前Node的索引
		 * @return {Number}
		 */
		getIdx:function(){
			var cns=this.parentNode.childNodes;
			for(var i=0, il=cns.length; i<il; i++){
				if(cns[i]==this) return i;
			}
		},

		/**
		 * 移动节点
		 * @param action
		 * @param node
		 */
		moveto:function(action,node){//TODO 移动BUG
			if(this.ondrag){
				if(!this.ondrag.apply(this["ondragSrc"]||this,[this,action,node])) return;
			}
			if(this==node) return;
			var oPNode=this.parentNode;
			var oPreNode=this.preNode(this);
			var oNextNode=this.nextNode(this);
			var pNode=null;
			var idx=-1;
			var oIdx=this.getIdx();
			if(action=="after"){
				idx=node.getIdx();
				pNode=node.parentNode;
			}else if(action=="in"){
				pNode=node;
			}

			//pNode若为子节点，弹出错误
			var find=false;
			var ppNode=pNode;
			while((ppNode=ppNode.parentNode)&&ppNode!=null&&ppNode!=this.tree.rootNode){
				if(ppNode==this){
					find=true;
					break;
				}
			}
			if(find) throw new Error("不能移动到子节点");

			//移除原来位置的Node，增加新的Node
			this.remove();
			if(idx<0){
				pNode.add(this);
			}else{
				if(oPNode==pNode){//同级目录下移动
					if(oIdx<idx){
						idx--;
					}
				}
				pNode.add(this,idx);
			}

			//同步原前节点样式
			oPNode.refreshCollapseElement();
			if(oPreNode){
				oPreNode.refreshCollapseElement();
			}

			//同步原后节点样式
			if(oNextNode) oNextNode.refreshCollapseElement();

			if(this.parentNode.isRoot()){
				EG.css(this.element,"margin-left:0px");
			}
		}
	});

	var ME	=EG.ui.TreeNode;
	var Tree=EG.ui.Tree;
})();
/**
 * @class EG.ui.Button
 * @author bianrongjun
 * @extends EG.ui.Item
 * Button按钮类
 */
(function(){
	/**
	 * 按钮
	 */
	EG.define("EG.ui.Button",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {String} text 按钮名称 */
			text		:"",
			/** @cfg {Function} click 事件 */
			click	:null,
			/** @cfg {Function?} mouseover mouseover事件 */
			mouseover	:null,
			/** @cfg {Function?} mouseout mouseout事件 */
			mouseout	:null,
			/** @cfg {String?} icon 图标 */
			icon		:null,
			/** @cfg {String?} cls CSS样式类 */
			cls			:"eg_button",
			/** @cfg {Boolean?} iconAble 是否显示图标 */
			iconAble	:true,
			/** @cfg {Boolean?} menuConfig 下拉菜单 */
			menuConfig	:null,
			/** @cfg {Boolean?} iconOnly 只显示图标 */
			iconOnly	:false,
			/** @cfg {String?} cls 文本样式 */
			textStyle	:null
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){

			this.callSuper([cfg]);

			//设置ICON
			if(!this.icon||!this.iconAble){
				EG.hide(this.dIcon);
			}else{
				this.dIcon.className=this.cls+"-icon icon_"+this.icon;
			}

			//创建菜单
			this.buildMenu();
			
			if(this.textStyle) this.setTextStyle(this.textStyle);
		},

		/**
		 * 创建
		 */
		build:function(){
			var me=this;
			var ME=this.getClass();
			if(this.iconOnly){
				this.element=EG.CE({tn:"div",cls:this.cls,item:this,onclick	:Button._events.element.onclick,cn:[
					this.dIcon	=EG.CE({tn:"a",title:this.text})
				]});
			}else{
				this.element=EG.CE({tn:"div",cls:this.cls,item:this,
					onclick		:Button._events.element.onclick,
					onmouseover	:Button._events.element.onmouseover,
					onmouseout	:Button._events.element.onmouseout,
					cn:[
						this.dOuter=EG.CE({tn:"div",cls:this.cls+"-outer",item:this,
							cn:[
								this.dIcon	=EG.CE({tn:"div"}),
								this.dTitle	=EG.CE({tn:"div",cls:this.cls+"-title",innerHTML:this.text}),
								this.dMulti	=EG.CE({tn:"div",cls:this.cls+"-multi",onclick:Button._events.dMulti.onclick,item:this})
							],
							onmouseover	:ME._events.outer.onmouseover,
							onmouseout	:ME._events.outer.onmouseout
						})
					]
				});
			}
		},
		
		/**
		 * 设置文字样式
		 */
		setTextStyle:function(style){
			this.textStyle=style;
			EG.css(this.dTitle,this.textStyle);
		},
		
		/**
		 * 设置文字
		 * @param text
		 */
		setText:function(text){
			this.text=text;
			this.dTitle.innerHTML=text;
		},

		/**
		 * 创建按钮
		 */
		buildMenu:function(){
			//创建多级按钮
			if(this.menuConfig&&this.menuConfig.length>0){
				this.dMenus=EG.CE({pn:this.element,tn:"div",cls:this.cls+"-menu",style:"position:absolute;z-index:1;overflow:hidden;"});
				for(var i= 0,il=this.menuConfig.length;i<il;i++){
					var mc=this.menuConfig[i];
					EG.CE({pn:this.dMenus,tn:"a",idx:i,innerHTML:mc["text"],href:"javascript:void(0)",cls:this.cls+"-mi",item:this,onclick:Button._events.aMenuEle.onclick});
				}
			}else{
				if(this.dMulti) EG.hide(this.dMulti);
			}
			if(this.dMenus) EG.hide(this.dMenus);
		},

		/**
		 * 渲染
		 */
		render:function(){
			if(this.dMenus){
				var eSize=EG.getSize(this.element);
				EG.fit({
					element	:this.dMenus,
					sSize	:eSize,
					dSize	:{
						width	:eSize.outerWidth
					}
				})
			}
		},
		statics:{
			_events:{
				element:{
					onclick:function(e){
						var me=this.item;
						if(me.click) me.click.apply(me["clickSrc"]||me);
						EG.Event.stopPropagation(e);
					},
					onmouseout:function(e){
						var me=this.item;
						if(!me.dMenus) return;
						if(me.outThread!=null) return;
						me.outThread=setTimeout(function(){
							EG.hide(me.dMenus);
						},10);
						EG.Event.stopPropagation(e);
					}
					,
					onmouseover:function(e){
						var me=this.item;
						if(!me.dMenus) return;
						if(me.outThread!=null){
							clearTimeout(me.outThread);
							me.outThread=null;
						}
						EG.Event.stopPropagation(e);
					}
				},
				outer:{
					onmouseover:function(){
						var me=this.item;
						this.className=me.cls+"-outer "+me.cls+"-on";
					},

					onmouseout:function(){
						var me=this.item;
						this.className=me.cls+"-outer";
					}
				},
				dMulti:{
					onclick:function(e){
						var me=this.item;
						EG.show(me.dMenus);
						EG.Event.stopPropagation(e);
					}
				},
				aMenuEle:{
					onclick:function(e){
						var me=this.item;
						var mc=me.menuConfig[this.idx];
						mc["click"].apply(mc["clickSrc"]||me);
						EG.hide(me.dMenus);
						EG.Event.stopPropagation(e);
					}
				}
			}
		}
	});

	var Button=EG.ui.Button;
})();
/**
 * @class EG.ui.Box
 * @author bianrongjun
 * @extends EG.ui.Item
 * Box盒子类
 */
(function(){
	EG.define("EG.ui.Box",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {String?} 文本 */
			text		:"",
			/** @cfg {Object?} 值 */
			value		:"",
			/** @cfg {String} 样式类 */
			cls			:"eg_box",
			/** @cfg {Function?} select选前执行事件 */
			onselect	:null,
			/** @cfg {Function?} click时执行的动作(可以完全控制点击的行为) */
			onclick		:null,
			/** @cfg {Function?} select选中后的动作 */
			afterselect	:null,
			/** @cfg {Boolean?} 是否已选 */
			selected	:false,
			/** @cfg {Boolean?} 显示文本 */
			showText	:true
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.callSuper([cfg]);

			//是否显示文字
			if(!this.showText) EG.hide(this.dText);
		},

		/**
		 * 创建
		 */
		build:function(){
			this.element=EG.CE({tn:"div",cls:this.cls,item:this,
				onclick:Box._events.element.onclick,
				cn:[
					this.dBox	=EG.CE({tn:"a",cls:this.cls+"-b"}),
					this.dText	=EG.CE({tn:"div",cls:this.cls+"-text",innerHTML:this.text})
				]
			});
		},

		/**
		 * 选择Box
		 * @param {Boolean} selected 准备选择的状态
		 * @param {Boolean?} fireEvt 触发事件
		 */
		select:function(selected,fireEvt){

			if(fireEvt==null) fireEvt=true;

			//执行选中前的动作
			if(fireEvt&&this.onselect){
				if(this.onselect.apply(this,[selected])===false) return;
			}
			//标识
			this.selected=selected;
			//样式
			EG.setCls(this.dBox,["b",this.selected?"select":"unselect"],this.cls);

			//选中后执行
			if(fireEvt&&this.afterselect){
				this.afterselect.apply(this,[selected]);
			}
		},

		/**
		 * 取消选择
		 */
		deSelect:function(){
			this.selected=null;
			EG.setCls(this.dBox,["b","unselect"],this.cls);
		},

		/**
		 * 设值
		 * @param {Object} value 值
		 */
		setValue:function(value){
			this.value=value;
		},

		/**
		 * 返回值
		 * @returns {Object}
		 */
		getValue:function(){
			return this.value;
		},

		/**
		 * 渲染
		 */
		render:function(){
			EG.fit(this);

			var h=EG.getSize(this.element).innerHeight;

			var m=parseInt((h-EG.getSize(this.dBox).outerHeight)/2);
			EG.css(this.dBox,"margin-top:"+m+"px;margin-bottom:"+m+"px");

			EG.css(this.dText,"line-height:"+h+"px;height:"+h+"px");
		},

		statics:{
			_events:{
				element:{
					onclick:function(){
						var me=this.item;
						if(me.onclick){					//onclick事件优先，可以完全控制选中不选中
							me.onclick.apply(me);
						}else{
							me.select(!me.selected);
						}
					}
				}
			}
		}
	});
	var Box=EG.ui.Box;
})();
/**
 * @class EG.ui.BoxGroup
 * @author bianrongjun
 * @extends EG.ui.Item
 * Box盒子组类
 */
(function(){
	EG.define("EG.ui.BoxGroup",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {Boolean?} multiple 是否可多选 */
			multiple	:false,
			/** @cfg {Function?} onselect select选前执行事件,点击任意一个box时候都会触发所有的box的onselect事件 */
			onselect	:null,
			/** @cfg {Function?} onclick click时执行的动作(可以完全控制点击的行为) */
			onclick		:null,
			/** @cfg {Function?} onchange 数值变化事件 */
			onchange	:null,
			/** @cfg {Function?} afterselect select以后执行的动作,点击任意一个box时候都会触发所有的box的onselect事件 */
			afterselect	:null,
			/** @cfg {Array?} textvalues 文本-值 数组 */
			textvalues	:[],
			/** @cfg {String?} cls 样式类 */
			cls			:"eg_boxgroup",
			/** @cfg {String?} defValue 默认值 */
			defValue	:null,
			/** @cfg {Number?} boxHeight 盒子高 */
			boxHeight	:null

		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
            this.callSuper([cfg]);
            //box组
            this.boxes=[];
            //onchange事件
            this.onchangeEvents=[];
            if(this.onchange!=null) this.bindOnchange(this.onchange);
			//创建Box
			this.buildBoxes();
			//默认值
			if(this.defValue) this.setValue(this.defValue);
		},

        /**
         * 创建元素
         */
        build:function(){
            this.element=EG.CE({tn:"div",cls:this.cls});
        },

		/**
		 * 创建BOX
		 */
		buildBoxes:function(){
			var me=this;
			//创建子box
			for(var i=0,il=this.textvalues.length;i<il;i++){
				var tv=this.textvalues[i];
				this.add({
					text		:tv[0],
					value		:tv[1],
					onclick		:BoxGroup._events.box.onclick,
					group		:this,
					onselect	:me.onselect,
					afterselect	:me.afterselect
				});
			}
		},

		/**
		 * 设值
		 * @param {Object} value 值
		 * @param {Boolean?} chain 联动
		 */
		setValue:function(value,chain){
			if(chain==null) chain=true;

			//旧数据
			var oV=this.getValue();
			if(this.multiple){
				EG.Array.each(this.boxes,function(){
					this.select(EG.Array.has(value,this.value));
				});
			}else{
				EG.Array.each(this.boxes,function(){
					this.select(this.value==value);
				});
			}

			//触发onchange
			if(!EG.Object.equals(oV,value)&&chain){
				//执行OnChange
				this.doOnChange(value,oV);
			}
		},

		/**
		 * 获值
		 * @returns {Object}
		 */
		getValue:function(){
			var values=[];
			for(var i=0,l=this.boxes.length;i<l;i++){
				var box=this.boxes[i];
				if(this.multiple){
					if(box.selected) values.push(box.value);
				}else{
					if(box.selected) return box.value;
				}
			}
			if(this.multiple) return values;
			return null;
		},

		/**
		 * 获取选中的文本
		 * @returns {*}
		 */
		getText:function(){
			var texts=[];
			for(var i=0,l=this.boxes.length;i<l;i++){
				var box=this.boxes[i];
				if(this.multiple){
					if(box.selected) texts.push(box.text);
				}else{
					if(box.selected) return box.text;
				}
			}
			if(this.multiple) return texts;
			return null;
		},

		/**
		 * 获取已选Box
		 */
		getSelectedBox:function(){
			var bs=[]
			for(var i=0,l=this.boxes.length;i<l;i++){
				var box=this.boxes[i];
				if(this.multiple){
					if(box.selected) bs.push(box);
				}else{
					if(box.selected) return box;
				}
			}
			if(this.multiple) return bs;
			return null;
		},

		/**
		 * 添加Box
		 * @param {Object|EG.ui.Box} box BOX
		 */
		add:function(box){
			if(EG.isLit(box)) box=new EG.ui.Box(box);
			box.pItem=this;
			this.boxes.push(box);
			this.element.appendChild(box.getElement());
		},

		/**
		 * 获取Box组
		 * @returns {Array<EG.ui.Box>}
		 */
		getBoxes:function(){
			return this.boxes;
		},

		/**
		 * 绑定变化时事件
		 * @param {Function} onchange 当改变时事件
		 */
		bindOnchange:function(onchange){
			this.onchangeEvents.push(onchange);
		},

		/**
		 * 执行OnChange
		 * @param {Object?} value
		 * @param {Object?} oldValue
		 */
		doOnChange:function(value,oldValue){
			for(var i=0;i<this.onchangeEvents.length;i++){
				this.onchangeEvents[i].apply(this,[value,oldValue]);
			}
		},

		/**
		 * 渲染
		 */
		render:function(){
			EG.fit(this);

			var h=EG.getSize(this.element).innerHeight;
			for(var i=0;i<this.boxes.length;i++){
				this.boxes[i].height=this.boxHeight!=null?this.boxHeight:h;
				this.boxes[i].render();
			}
		},

		/**
		 * 设置文本值
		 * @param {Array} tvs 文本值
		 */
		setTextvalues:function(tvs){
			EG.Array.clear(this.boxes);
			this.textvalues=tvs;
			EG.DOM.removeChilds(this.element);
			this.buildBoxes();
		},

		statics:{
			_events:{
				box:{
                    onclick:function(){
                        var me=this.group;
						var oV=this.group.getValue();
						var needChange=false;
                        //多选
                        if(me.multiple){
                            this.select(!this.selected);
							needChange=true;
                        //单选
                        }else{
                            if(me.onclick){
                                me.onclick();
                            }else{
								if(!this.selected)  needChange=true;
                                //清除同组其它box
                                for(var i=0,l=me.boxes.length;i<l;i++){
                                    if(this!=me.boxes[i]) me.boxes[i].select(false);
                                }
                                this.select(true);
                            }
                        }

						var val=this.group.getValue();

                        //触发onchange
                        if(needChange){
							for(var i=0;i<me.onchangeEvents.length;i++){
								me.onchangeEvents[i].apply(me,[val,oV]);
							}
						}
                    }
                }

			}
		}
	});

	var BoxGroup=EG.ui.BoxGroup;
})();
/**
 * @class EG.ui.Date
 * @author bianrongjun
 * @extends EG.ui.Item
 * Date时间类,需要MyDatapiker支持
 */
(function(){
	EG.define("EG.ui.Date",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {?String} fmt 日期格式 */
			fmt			:"YMDHMS",
			/** @cfg {?Number} maxLength 最大长度 */
			maxLength	:10,
			/** @cfg {?String} 日期格式 */
			dateFmt		:null,
            cls         :"eg_date",
			onkeydown	:null,
			onkeyup		:null
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
            this.callSuper([cfg]);

			if(this.dateFmt==null){
				if (this.fmt == "Y"){
					this.dateFmt="yyyy";
					this.maxLength=4;
				}else if (this.fmt == "YMD"){
					this.dateFmt="yyyy-MM-dd";
					this.maxLength=10;
				}else if (this.fmt == "YMDHMS"){
					this.dateFmt="yyyy-MM-dd HH:mm:ss";
					this.maxLength=20;
				}else throw new Error("暂不支持时间格式" + this.fmt);
			}
		},

        build:function(){
            this.element = EG.CE({tn:"div",cls:this.cls,cn:[
                this.input=EG.CE({tn : "input",cls:this.cls+"-input",type : "text",maxLength : this.maxLength,length: this.maxLength,item:this,onclick:Date._events.input.onclick})
            ]});

//			if(this.onkeydown){
//				EG.Event.bindEvent(this.input,"onkeydown",this.onkeydown);
//			}
//
			if(this.onkeyup){
				EG.Event.bindEvent(this.input,"onkeyup",this.onkeyup);
			}
        },

		/**
		 * 设值
		 * @param {String} value 数值
		 */
		setValue:function(value){
			EG.setValue(this.input,value);
		},
		/**
		 * 获值
		 * @returns {String}
		 */
		getValue:function(){return EG.getValue(this.input);},
		/**
		 * 渲染
		 */
		render:function(){
			EG.fit(this);

			EG.fit({
				element:this.input,
				dSize:{
					width:"100%",
					height:"100%"
				},
				pSize:EG.getSize(this.element)
			});
		},
		statics:{
			_events:{
				input:{
					onclick:function(){
						var me=this.item;
						new WdatePicker({dateFmt:me.dateFmt,skin : 'ext'});
					}
				}
			}
		}
	});

	var Date=EG.ui.Date;
})();
(function(){
	/**
	 * 按钮日历
	 */
	EG.define("EG.ui.Calander",{
		extend:"EG.ui.Item",
		config:{
			 text		:""							//按钮名称
			,click		:null						//事件
			,cls:"eg_calander"
			,style		:null
		},
		constructor:function(cfg){
			this.element=EG.CE({tn:"div"});
		}
	});
})();/**
 * @class EG.ui.Editor
 * @author bianrongjun
 * @extends EG.ui.Item
 * Editor编辑类
 */
(function(){
	EG.define("EG.ui.Editor",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {String?} pluginGroupName 分组名 */
			pluginGroupName		:"def",
			/** @cfg {String?} cls CSS样式类 */
			cls					:"eg_editor",
			/** @cfg {String?} uploadPolicy 上传策略 */
			uploadPolicy		:null,
			/** @cfg {String?} imgUploadPolicy 图像上传策略 */
			imgUploadPolicy		:null,

			uploadAction		:null,

			imgUploadAction		:null,

			/** @cfg {Function?} onUploaded 上传后事件 */
			onUploaded			:null,
			/** @cfg {Function?} imgOnUploaded 图像上传后事件 */
			imgOnUploaded		:null,
			/** @cfg {String?} uploadHandleType 上传处理类型 */
			uploadHandleType	:null,
			/** @cfg {Function?} deleteUpload 删除上传的操作 */
			deleteUpload		:null,
			/** @cfg {Object?} parent 外部参考物 */
			parent				:null,


			imgPickers			:null		//图片选取器
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.initItem(cfg);
			this.pluginGroup=Editor.pluginGroups[this.pluginGroupName];

			if(typeof(this.onUploaded)=="string"){
				this.onUploaded=Editor.defOnUploaded;
			}

			this.dToolbarButtons=null;
			this.dIframe		=null;
			this.dSource		=null;
			this.dCache			=null;

			this.element=EG.CE({tn:"div",cls:this.cls,cn:[
				this.dMenuPanels		=EG.CE({tn:"div"}),
				this.dToolbarButtons	=EG.CE({tn:"div",cls:this.cls+"-toolbar"}),
				this.dHtml				=EG.CE({tn:"div",cls:this.cls+"-dHtml",cn:[
					this.frame			=EG.CE({tn:"iframe",frameBorder:"0",designMode:"on",style:"height:100%",item:this})
				]}),
				this.dCache			=EG.CE({tn:"div",style:"display:none"})
			]});

			//设定样式
			EG.css(this.element,this.style);

			//初始化Iframe
			this.initIframe();

			//初始化插件
			this.buildPlugins();
		},
		/**
		 * 渲染
		 */
		render:function(){
			EG.fit(this);

			var size=EG.getSize(this.element);
			EG.fit({
				element	:this.dToolbarButtons,
				dSize	:{width:"100%"},
				pSize	:size,
				type	:"height"
			});


			var tbSize=EG.getSize(this.dToolbarButtons);
			EG.fit({
				element:this.dHtml,
				dSize:{width:"100%",height:size.innerHeight-tbSize.outerHeight},
				pSize:size
			});

			for(var i= 0,il=this.plugins.length;i<il;i++){
				this.plugins[i].render();
			}
		},
		plugins			:[],
		pluginsMap		:{},
		clickHandlers	:[],
		dblclickHandlers:[],
		/**
		 * 创建插件s
		 * @private
		 */
		buildPlugins:function(){

			this.buildInParents=[];

			for(var i=0,il=this.pluginGroup.length;i<il;i++){
				//创建插件
				var name=this.pluginGroup[i];
				var plugin=new Editor.pluginsMap[name](this);

				this.plugins.push(plugin);
				this.pluginsMap[name]=plugin;
				//创建按钮
				var toolbarButton=plugin.getToolbarButton();
				if(toolbarButton)	this.dToolbarButtons.appendChild(toolbarButton.getElement());

				//创建弹出面板
				if(plugin.getMenuPanel){
					var menuPanel=plugin.getMenuPanel();
					if(menuPanel){
						if(plugin.buildInParent&&this.parent){
							var pn=(this.parent.getElement)?this.parent.getElement():this.parent;
							pn.appendChild(menuPanel.getElement());

							//记录
							this.buildInParents.push(menuPanel);
						}else{
							this.dMenuPanels.appendChild(menuPanel.getElement());
						}
					}
				}
			}
		},
		/**
		 * 获取组件
		 * @param {String} name 组件名
		 */
		getPlugin:function(name){
			return this.pluginsMap[name];
		},
		/**
		 * 初始化Frame，加入监听事件
		 * @private
		 */
		initIframe:function(){
			//加载Onload事件
			EG.bindEvent(this.frame,"load",Editor._events.iframe.load);
		},

		/**
		 * 设值内容
		 * @param {String} content 内容
		 */
		setContent:function(content){
			if (this.loaded)
				this.frame.contentWindow.document.body.innerHTML = content;
			else
				this.cacheHTML = content;
			//this.frame.contentWindow.document.body.style.fontSize = EG.ui.Editor.def.fontSize;//TODO 测试
		},
		/**
		 * @link EG.ui.Editor.setContent
		 */
		setValue:function(value){

			var ct=EG.CE({tn:"div",innerHTML:value});

			var tns=["img","a"]
			for(var i=0;i<tns.length;i++){
				var els=ct.getElementsByTagName(tns[i]);
				for(var j=0;j<els.length;j++){
					var el=els[j];
					var uri;
					var attName="";
					if(el.hasAttribute("srcUri")){
						attName="src";
						uri=el.getAttribute("srcUri");
					}else if(el.hasAttribute("hrefUri")){
						attName="href";
						uri=el.getAttribute("hrefUri");
					}else{
						continue;
					}
					el.setAttribute(attName,uri);
				}
			}

			this.setContent(value);
		},

		/**
		 * 获取内容
		 * @returns {String}
		 */
		getContent:function(){
			return this.frame.contentWindow.document.body.innerHTML;
		},

		/**
		 * @link EG.ui.Editor.getContent
		 */
		getValue:function(){return this.getContent();},

		/**
		 * 聚焦
		 */
		focus:function(){
			if(EG.Browser.isChrome()){//21.0.1180.60
				this.frame.contentWindow.document.body.focus();
			}else if(EG.Browser.isIE()){
				this.frame.contentWindow.focus();
			}else{
				this.frame.focus();
			}
		},
		/**
		 * 隐藏menu
		 */
		hideMenus:function(){
			for(var i=0,il=this.plugins.length;i<il;i++){
				var plugin=this.plugins[i];
				if(!plugin.getMenuPanel) continue;
				var menuPanel=plugin.getMenuPanel();
				if(menuPanel) menuPanel.close();
			}
		},
		/**
		 * 执行HTML变换
		 * TODO 检测以前的getSelection方式
		 * @param {String} type 类型
		 * @param {Object} para 数值
		 */
		htmlexec:function(type,para){
	//		if(!para){
	//			var selection=this.getSelection();
	//
	//			if(EG.Browser.isIE()) this.getSelection().createRange().execCommand(type,false);
	//			else this.getSelection().createRange().execCommand(type,false,false);
	//		}else {
	//			this.frame.contentWindow.document.execCommand(type,false,para);
	//		}
			this.focus();
			if(!para){
				if(EG.Browser.isIE()){
					this.frame.contentWindow.document.execCommand(type,false);
				}else{
					this.frame.contentWindow.document.execCommand(type,false,false);
				}
			}else{
				this.frame.contentWindow.document.execCommand(type,false,para);
			}
			this.focus();
		},
		/**
		 * 粘贴HTML
		 * @param {String} html HTML
		 */
		pasteHTML:function(html){
			this.focus();
			if(EG.Browser.isIE()){
				this.getSelection().createRange().pasteHTML(html);
			}else{
				this.frame.contentWindow.document.execCommand('InsertHtml',false,html);//也可以用this.getSelection().getRangeAt(0).surroundContents(ele)
			}
		},
		/**
		 * 获取选项
		 */
		getSelection:function(){
			if(EG.Browser.isIE()) return this.frame.contentWindow.document.selection;
			return this.frame.contentWindow.document.getSelection();
		},
		/**
		 * 当资源上传时自动添加标签
		 * @param {Object} cfg 配置
		 */
		addResOnUploaded:function(cfg){
			var me=this;
			var ct=cfg["ct"];
			var r=cfg["r"];
			var type=r["type"];
			var file=r["file"];
			var doDelete=r["doDelete"];
			//在相同form中找一个相关字段或页面元素中某一个元素,插入一个带图片标识的可以删除的标签
			//方式1:横向插入一个带图片标识的可以删除的标签,名字为文件名
			var el=EG.CE({tn:"div",cls:this.cls+"-uploadLabel",style:EG.Style.c.dv+";min-width:40px;max-width:200px",
				onmouseover	:Editor._events.dUploadLabel.onmouseover,
				onmouseout	:Editor._events.dUploadLabel.onmouseout,
				cn:[
					{tn:"div",onclick:function(){
						var p;
						if(type=="image"){
							p=me.getPlugin("image");
							p.showMenuPanel();
							p.setImageForm({
								src:file["path"]
							});
						}else if(type=="video"){
							p=me.getPlugin("video");
							p.showMenuPanel();
							p.setVideoForm(file);
						}else if(type=="zip"){
							p=me.getPlugin("zip");
							p.showMenuPanel();
							p.setZipForm(file);
						}
					},innerHTML:file["name"],style:EG.Style.c.dv+";cursor:pointer"},
					{tn:"a",cls:this.cls+"-uploadLabel-closer",onclick:function(){
						var ma=this;
						doDelete(function(){
							ma.parentNode.parentNode.removeChild(ma.parentNode);
						});
					}}
			]});

			ct.appendChild(el);
			//方式2:纵向插入一个带图片标识的可以删除的标签,名字为文件名
		},

		destroy:function(){
			for(var i=0;i<this.buildInParents.length;i++){
				this.buildInParents[i].destroy();
				EG.DOM.remove(this.buildInParents[i].getElement());
			}
		},

		execute:function(name,args){
			var p=this.getPlugin(name);
			p.execute(args);
		},

		statics:{
			def:{
				fontSize:"14px"
			},
			/**
			 * 全局插件类映射
			 */
			pluginsMap:{},
			/**
			 * 插件组
			 */
			pluginGroups:{
				//默认插件组
				def:[
					"bold",
					"italic",
					"underline",
					"fontname",
					"fontsize",
					"color",
					"textalign",
					"list",
					"indent",
					"image",
					"code"
				],
				simple:[
					"bold",
					"italic",
					"underline",
					"fontname",
					"fontsize",
					"color",
					"textalign",
					"list",
					"indent"
				]
			},
			/**
			 * 注册组件
			 * @param {String} name 组件名
			 * @param {EG.ui.editor.Plugin} plugin 插件
			 */
			registPlugin:function(name,plugin){
				Editor.pluginsMap[name]=plugin;
			},
			_events:{
				iframe:{
					load:function(){
						var me	=this.item;
						this.doc=this.contentDocument || this.contentWindow.document;
						this.doc.body.designMode="on";
						this.doc.body.contentEditable = true;
						this.doc.body.style.fontSize=Editor.def.fontSize;
						EG.css(this.doc.body,"line-height:1.5;margin:0; padding:8px 8px;font-size:"+Editor.def.fontSize);
						me.loaded=true;
						if(me.cacheHTML!=null) this.contentWindow.document.body.innerHTML=me.cacheHTML;
						else this.contentWindow.document.body.innerHTML="<p></p>";
						this.contentWindow.document["editor"]=me;
						this.contentWindow.document.onclick = function(){
							me.hideMenus();
						};

						//BUGFIX:IE Iframe失去焦点后再回来失去光标
						if(EG.Browser.isIE()){
							EG.bindEvent(this,"beforedeactivate",function(){
								var range = me.doc.selection.createRange();
								if(range.getBookmark) this.ieSelectionBookmark = range.getBookmark();
							});
							EG.bindEvent(this,"activate",function(){
								if(this.ieSelectionBookmark){
									var range = me.doc.body.createTextRange();
									range.moveToBookmark(this.ieSelectionBookmark);
									range.select();
									this.ieSelectionBookmark = null;
								}
							});
						}

						//Event支持
						EG.bindEvent(this.doc,"keydown",function(e){
							if(e==null) e=window.event;
							var code=e.keyCode;
							if(e.ctrlKey){
								//switch(code){
								//TODO case 90:{me.undo();}
								//ctrl+z Undo撤销
								//TODO ctrl+y支持重做事件
								//}
							}else if(e.shiftKey){

							}else{
								switch(code){
									//case 8:	{me.htmlexec('delete');return false;}
									//TODO 删除文字时有 BUG backspace 删除选中，防止回退
									case 13:{
										//me.saveStep();break;
										//TODO !!!!!临时取消
									}//回车保存
								}
							}
						});
						//单击事件
						EG.bindEvent(this.doc,"click",function(e){
							for(var i=0,il=me.clickHandlers.length;i<il;i++){
								me.clickHandlers[i](e);
							}
						});
						//双击事件
						EG.bindEvent(this.doc,"dblclick",function(e){
							for(var i=0,il=me.dblclickHandlers.length;i<il;i++){
								me.dblclickHandlers[i](e);
							}
						});
					}
				},
				dUploadLabel:{
					onmouseover:function(){
						EG.show(this.childNodes[1]);
					},
					onmouseout:function(){
						EG.hide(this.childNodes[1]);
					}
				},
				dUploadName:{

				}
			}
		}
	});

	var Editor=EG.ui.Editor;
})();
/**
 * @class EG.ui.editor.Plugin
 * @author bianrongjun
 * @extends EG.ui.Item
 * 编辑器插件
 */
(function(){
	/**
	 * EG.ui.editor.Plugin 插件
	 */
	EG.define("EG.ui.editor.Plugin",{

		config:{
			buildInParent:false //在父参考系中创建
		},

		/**
		 * 获取Button
		 */
		getToolbarButton:function(){
			return this.toolbarButton;
		},
		/**
		 * 获取菜单
		 */
		getMenuPanel:function(){
			return this.menuPanel;
		},
		render:function(){

		}
	});
})();
/**
 * @class EG.ui.editor.ToolbarButton
 * @author bianrongjun
 * @extends EG.ui.Item
 * 工具栏按钮
 */
(function(){
	/**
	 * 工具栏按钮
	 */
	EG.define("EG.ui.editor.ToolbarButton",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {String} type 类型 */
			type:null,
			/** @cfg {String} cls CSS样式类 */
			cls:null
		},
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 * @param {Object} cfg 配置
		 */
		constructor:function(editor,cfg){
			this.initItem(cfg);
			this.editor=editor;
//			EG.copy(cfg,{
//				text:cfg["text"]||" ",
//				cls:
//			},true);
			this.button=EG.CE({tn:"a",cls:editor.cls+"-toolbar-"+this.cls,href:"javascript:void(0)"});

			if(cfg["click"]){
				EG.Event.bindEvent(this.button,"onclick",cfg["click"]);
			}

			//this.button=new EG.ui.Button(cfg);
			this.element=this.button;
		}
	});
})();



/**
 * @class EG.ui.editor.plugin.Bold
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体加粗
 */
(function(){
	EG.define("EG.ui.editor.plugin.Bold",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"bold",
				click:function(){me.execute();},
				cls:"bold"
			});
		},
		/**
		 * 执行
		 */
		execute:function(){
			this.editor.htmlexec("Bold");
		}
	});
	var Bold=EG.ui.editor.plugin.Bold;
	//注册
	EG.ui.Editor.registPlugin("bold",Bold);
})();

/**
 * 
 *//**
 * @class EG.ui.editor.plugin.Code
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-源码编辑
 */
(function(){
	EG.define("EG.ui.editor.plugin.Code",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.codeModel=false;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"code",
				click:function(){
					me.codeModel=!me.codeModel;
					me.execute(me.codeModel);
				},
				mouseover:function(){

				},
				cls:"code"
			});
			this.editPanel=EG.CE({pn:this.editor.dHtml,tn:"textarea",style:"width:100%;background:#FEFEFE;border:0px;margin:0px;padding:0px"});

			EG.hide(this.editPanel);
		},
		
		/**
		 * 执行
		 * @param {String} codeModel 模式
		 */
		execute:function(codeModel){
			if(codeModel){
				EG.setValue(this.editPanel,this.editor.getContent());
				EG.css(this.editPanel,"height:"+this.editor.frame.clientHeight+"px;width:"+this.editor.frame.clientWidth+"px");
				EG.show(this.editPanel);
				EG.hide(this.editor.frame);

				var btns=this.editor.dToolbarButtons.childNodes;
				for(var i=0,il=btns.length;i<il;i++){
					if(btns[i]!=this.toolbarButton.getElement()){
						EG.hide(btns[i]);
					}
				}

			}else{
				this.editor.setContent(EG.getValue(this.editPanel));
				EG.hide(this.editPanel);
				EG.show(this.editor.frame);
				var btns=this.editor.dToolbarButtons.childNodes;
				for(var i=0,il=btns.length;i<il;i++){
					EG.show(btns[i]);
				}
			}
		}
	});
	var Code=EG.ui.editor.plugin.Code;

	//注册
	EG.ui.Editor.registPlugin("code",Code);
})();

/**
 * @class EG.ui.editor.plugin.Color
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体颜色
 */
(function(){
	EG.define("EG.ui.editor.plugin.Color",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"color",
				click:function(){
					me.editor.hideMenus();
					var top=me.toolbarButton.getElement();
					var pos=EG.Tools.getElementPos(top,me.editor.getElement());
					pos.y=top.clientHeight;
					EG.Style.moveTo(me.menuPanel.dPop,pos);
					me.menuPanel.open();
				},
				mouseover:function(){
//				me.menuPanel.open();
				},
				cls:"color"
			});

			//创建面板
			this.buildMenuPanel();
		},
		
		/**
		 * 执行
		 * @param {String} color 颜色
		 */
		execute:function(color){
			this.editor.htmlexec("ForeColor",color);
		},
		
		/**
		 * 创建颜色板
		 */
		buildMenuPanel:function(){
			this.menuPanel=new EG.ui.Pop({closeable:true,lock:false,posFix:false,cls:"eg_pop_blank"});
			var me=this;
			var tbody;
			var tab=EG.CE({tn:"table",cellSpacing:0,cellPadding:0,style:"float:left;border:0px;margin:0px",cn:[
				tbody=EG.CE({tn:"tbody"})
			]});

			var sl=5;
			for(var i=0;i<16;i++){

				//忽略组数
				if(i%4==0) continue;

				var tr=EG.CE({pn:tbody,tn:"tr"});
				for(var j=0;j<30;j++){
					var n1=j%sl;

					//忽略组数
					if(j%4==0) continue;
					var n2=Math.floor(j/sl)*3;
					var n3=n2+3;
					(function(){
						var color= Color.wc(
							(Color.cnum[n3]*n1		+Color.cnum[n2]*(sl-n1)),
							(Color.cnum[n3+1]*n1	+Color.cnum[n2+1]*(sl-n1)),
							(Color.cnum[n3+2]*n1	+Color.cnum[n2+2]*(sl-n1)),
							i
						);
						EG.CE({pn:tr,tn:"td",cn:[
							{tn:"div",cls:me.editor.cls+"-toolbar-color-box",style:"background-color:"+color,
								onclick:function(){
									me.execute(color);
									me.menuPanel.close();
								},
								onmouseover:function(){
									EG.setCls(this,["toolbar-color-box","toolbar-color-boxOn"],me.editor.cls);
								},
								onmouseout:function(){
									EG.setCls(this,["toolbar-color-box"],me.editor.cls);
								}
							}
						]});
					})();
				}
			}

			this.menuPanel.addChildren(tab);
		},
		statics:{
			hexch:["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"],
			cnum:[1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0],
			colors:["#FF0000","#00FF00","#0000FF","#FFFF00","#00FFFF","#FF00FF"],
			
			/**
			 * toHex
			 * @param {Number} n
			 */
			toHex:function(n){
				var h,l,n=Math.round(n);
				l=n%16;
				h=Math.floor((n/16))%16;
				return (Color.hexch[h]+Color.hexch[l]);
			},
			/**
			 *
			 * @param {Number} r
			 * @param {Number} g
			 * @param {Number} b
			 * @param {Number} n
			 */
			wc:function(r, g, b, n){
				r=((r*16+r)*3*(15-n)+0x80*n)/15;
				g=((g*16+g)*3*(15-n)+0x80*n)/15;
				b=((b*16+b)*3*(15-n)+0x80*n)/15;
				return '#'+Color.toHex(r)+Color.toHex(g)+Color.toHex(b);
			}
		}
	});
	var Color=EG.ui.editor.plugin.Color;
	//注册
	EG.ui.Editor.registPlugin("color",Color);
})();

/**
 * @class EG.ui.editor.plugin.Fontname
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体
 */
(function(){
	EG.define("EG.ui.editor.plugin.Fontname",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"fontname",
				text:"字体",
				click:function(){
					me.editor.hideMenus();
					var top=me.toolbarButton.getElement();
					var pos=EG.Tools.getElementPos(top,me.editor.getElement());
					pos.y=top.clientHeight;
					EG.Style.moveTo(me.menuPanel.dPop,pos);
					me.menuPanel.open();
				},
				mouseover:function(){

				},
				style:"font-size:12px",
				cls:"fontname"
			});

			this.buildMenuPanel();
		},
		/**
		 * 执行
		 * @param {String} fontname 字体
		 */
		execute:function(fontname){
			this.editor.htmlexec("Fontname",fontname);
		},
		/**
		 * 创建字体板
		 */
		buildMenuPanel:function(){
			this.menuPanel=new EG.ui.Pop({closeable:true,lock:false,posFix:false,cls:"eg_pop_blank"});
			var me=this;
			var d=EG.CE({tn:"div",style:""});
			for(var i=0,il=Fontname.fontnames.length;i<il;i++){
				(function(){
					var fontname=Fontname.fontnames[i];
					EG.CE({pn:d,tn:"a",cls:me.editor.cls+"-toolbar-fontname-box",style:"font-family:"+fontname,innerHTML:fontname,onclick:function(){
						me.execute(fontname);
						me.menuPanel.close();
					}});
				})();
			}
			this.menuPanel.addChildren(d);
		},
		statics:{
			fontnames:['微软雅黑','宋体','黑体','楷体_GB2312','隶书','幼圆','Arial','Arial Narrow','Arial Black','Comic Sans MS','Courier','System','Times New Roman']
		}
	});

	var Fontname=EG.ui.editor.plugin.Fontname;
	//注册
	EG.ui.Editor.registPlugin("fontname",Fontname);
})();/**
 * @class EG.ui.editor.plugin.Code
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体大小
 */
(function(){
	EG.define("EG.ui.editor.plugin.Fontsize",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"fontsize",
				click:function(){
					me.editor.hideMenus();
					var top=me.toolbarButton.getElement();
					var pos=EG.Tools.getElementPos(top,me.editor.getElement());
					pos.y=top.clientHeight;
					EG.Style.moveTo(me.menuPanel.dPop,pos);
					me.menuPanel.open();
				},
				mouseover:function(){
	//				me.menuPanel.open();
				},
				cls:"fontsize"
			});

			//创建面板
			this.buildMenuPanel();
		}
		/**
		 * 执行
		 * @param {String} fontname 字体大小
		 */,
		execute:function(fontsize){
			this.editor.htmlexec("FontSize",fontsize);
		},
		/**
		 * 创建字体大小
		 */
		buildMenuPanel:function(){
			this.menuPanel=new EG.ui.Pop({closeable:true,lock:false,posFix:false,cls:"eg_pop_blank"});
			var me=this;
			var d=EG.CE({tn:"div",style:""});
			for(var i=0,il=Fontsize.fontsizes.length;i<il;i++){
				(function(){
					var fontsize=Fontsize.fontsizes[i];
					EG.CE({pn:d,tn:"a",cls:me.editor.cls+"-toolbar-fontsize-box",style:"font-size:"+(fontsize+10)+"px",innerHTML:fontsize+"号",onclick:function(){
						me.execute(fontsize);
						me.menuPanel.close();
					}});
				})();
			}
			this.menuPanel.addChildren(d);
		},
		statics:{
			fontsizes:[1,2,3,4,5,6,7]
		}
	});
	var Fontsize=EG.ui.editor.plugin.Fontsize;
	//注册
	EG.ui.Editor.registPlugin("fontsize",Fontsize);
})();

/**
 * @class EG.ui.editor.plugin.Image
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-图片
 */
(function(){
	EG.define("EG.ui.editor.plugin.Image",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;

			//在父中创建
			this.buildInParent=true;

			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"image",
				click:function(){
					if(me.resPicker) me.resPicker.close();
					me.showMenuPanel();
				},
				mouseover:function(){
//				me.menuPanel.open();
				},
				cls:"image"
			});

			//上传策略
			this.uploadPolicy	=this.editor.imgUploadPolicy||this.editor.uploadPolicy;
			this.uploadAction	=this.editor.imgUploadAction||this.editor.uploadAction;
			//上传后动作
			this.onUploaded		=this.editor.imgOnUploaded||this.editor.onUploaded;

			//创建面板
			this.buildMenuPanel();

			//编辑区双击事件
			this.editor.dblclickHandlers.push(function(e){
				if(e.target&&e.target.tagName.toUpperCase()=="IMG"){
					//设置当前选中的图像
					me.selectedImg=e.target;

					//双击讲图像属性设置到Form中
					me.setImageForm(e.target,true);
					me.showMenuPanel();
				}
			});

			//编辑区单击事件
			this.editor.clickHandlers.push(function(e){
				if(e.target&&e.target.tagName.toUpperCase()=="IMG"){
					//
				}else{
					me.selectedImg=null;
				}
			});
		},
		/**
		 * 显示面板
		 */
		showMenuPanel:function(){
			this.editor.hideMenus();
//			var top=this.toolbarButton.getElement();
//			var pos=EG.Tools.getElementPos(top,this.editor.getElement());
//			pos.y=top.clientHeight;
			//EG.Style.moveTo(this.menuPanel.getElement(),pos);
			this.menuPanel.open();

			//EG.Style.center(this.menuPanel.getElement(),this.editor.getElement());
			this.editor.curMenuPanel=this.menuPanel;
			this.render();
		},
		/**
		 * 执行
		 * @param {String} atrs 图像参数
		 */
		execute:function(atrs){
			var img;
			var me=this;
			if(typeof(atrs)=="string") atrs={src:atrs};
			if(this.selectedImg){
				img=this.selectedImg;
				this.setImageAtrs(img,atrs);
			}else{
				//插入新Img时是异步的，需要在onload中完成插入动作
				img=EG.CE({tn:"img",onload:function(){
					var ctx=EG.DOM.getOuterHTML(this);
					me.editor.pasteHTML(ctx);
				}});
				this.setImageAtrs(img,atrs);
				img.src=atrs.src;
			}
		},
		/**
		 * 渲染
		 */
		render:function(){
			this.imgForm.render();
		},
		/**
		 * 获取上传Action
		 * @param type
		 * @return {String}
		 */
		getUploadAction:function(type){
			return EG.MMVC.getPath().upload+"/?uploadPolicy="+this.uploadPolicy+"&type="+type;
		},
		/**
		 * 上传前检查
		 */
		beforeUpload:function(){
			//TODO 用activeXObject来检测文件大小
			//function ShowSize(files) {var fso=new ActiveXObject("Scripting.FileSystemObject");var mySize = fso.GetFile(files).size/1024;alert(mySize+" K ");}
			return true;
		},
		/**
		 * 创建图上先传层
		 */
		buildMenuPanel:function(){

			var me=this;

			var p=this.editor.parent||this.editor;
			if(p.getElement) p=p.getElement();

			//图像
			var imgPickers=this.editor.imgPickers;


			this.menuPanel=new EG.ui.Dialog({
				closeable	:true,
				lock		:true,
				posFix		:true,
				title		:"图片设置",
				//cls			:"eg_pop_blank",
				//style		:"z-index:1",
				fullable	:true,
				parent		:p,
				width		:350,
				height		:"auto",
				layout		:{type:"line",direct:"V"},
				items:[
					this.imgForm=new EG.ui.Form({
						width		:350,
						height		:"auto",
						labelWidth	:50,
						layout		:"default",
						items:[
							{xtype:"tabPanel",width:"100%",height:120,items:[
								{
									tab:{title:"图片",style:"width:60px"},
									panel:{
										layout:"table",
										items:[
											{xtype:"formItem",pos:[0,0],title:"图片"	,name:"imgShowArea"		,type:"label",length:15,notnull:true,height:40},
											{xtype:"button"	 ,pos:[0,1],text:"选择",hidden:imgPickers?false:true,click:function(){
												me.resPicker.open();
											}},
											{xtype:"formItem",pos:[1,0],title:"路径"	,name:"imgSrc"			,type:"text",style:"overflow:hidden"},
											{xtype:"formItem",pos:[2,0],title:"宽"	,name:"imgWidth"		,type:"text",length:5,style:"width:40px",after:"px"},
											{xtype:"formItem",pos:[2,1],title:"高"	,name:"imgHeight"		,type:"text",length:5,style:"width:40px",after:"px"},
											{xtype:"formItem",pos:[3,0],title:"隐藏"	,name:"imgSrcUri"		,type:"text",hidden:true}
										]
									}
								},
								{
									tab:{title:"浮动",style:"width:60px"},
									tabStyle:"width:100",
									panel:{
										layout:"table",
										items:[
											{xtype:"formItem",pos:[0,0],title:"环绕"	,name:"imgFloat"		,type:"select",textvalues:[["不环绕",""],["向左","left"],["向右","right"]]},
											{xtype:"formItem",pos:[1,0],title:"左距"	,name:"imgMarginLeft"	,type:"text",length:10,after:"px"},
											{xtype:"formItem",pos:[1,1],title:"右距"	,name:"imgMarginRight"	,type:"text",length:10,after:"px"},
											{xtype:"formItem",pos:[2,0],title:"上距"	,name:"imgMarginTop"	,type:"text",length:10,after:"px"},
											{xtype:"formItem",pos:[2,1],title:"下距"	,name:"imgMarginBottom"	,type:"text",length:10,after:"px"}
										]
									}
								}
							]}
						]
					})
				],
				btns:[
					{text:"确定",cls:"eg_button_small",click:function(){me.doInsertImage();}},
					{text:"取消",cls:"eg_button_small",click:function(){me.menuPanel.close();},style:"margin-left:10px"}
				]
			});


			if(imgPickers){
				this.resPicker_opened=false;
				var ipTypes=imgPickers.types;
				var tps=[];
				this.tpm={};
				for(var i=0;i<ipTypes.length;i++){
					var ipT=ipTypes[i];
					tps.push([ipT["title"],ipT["type"]]);
					this.tpm[ipT["type"]]=ipT;
				}

				//创建一个选择器
				this.resPicker=new EG.ui.Dialog({
					renderTo	:p,
					title		:"选择图片",
					width		:"100%",
					height		:"auto",
					style		:"margin:10px;z-index:2;",
					lock		:true,
					posFix		:true,
					layout:{type:"line",direct:"V"},
					items:[
						{xtype:"panel",height:EG.ui.FormItem._config.height,layout:"line",items:[
							this.sltCg=new EG.ui.FormItem({xtype:"formItem",title:"分类",type:"select",textvalues:tps,width:200,onchange:function(v){
								me.loadPickerFiles(v);
							}}),
							{xtype:"button",text:"刷新",cls:"eg_button_small",onclick:function(){
								me.loadPickerFiles(me.sltCg.getValue());
							}}
						]},
						this.rpm=new EG.ui.Panel({xtype:"panel",style:"margin:3px;border:1px solid gray;overflow:auto",height:450}),
						this.rpf=new EG.ui.Panel({xtype:"panel",style:"margin:3px",height:30})
					],
					btns:[
						{xtype:"button",text:"选择",cls:"eg_button_small",click:function(){
							me.doPickerSelect();
						}}
					],
					afterOpen:function(){
						if(!me.resPicker_opened){
							var v=me.sltCg.getValue();
							if(v){
								me.loadPickerFiles(v);
							}
						}
						me.resPicker_opened=true;
					}
				});

			}


			if(this.uploadPolicy||this.uploadAction){
				this.imgForm.items[0].add({
					tab:{title:"上传",style:"width:60px"},
					panel:{
						layout:"table",
						items:[
							{xtype:"formItem",title:"上传",name:"imgUpload",type:"upload",height:50,style:"overflow:hidden",autoupload:true,action:this.uploadAction||this.getUploadAction("image"),
								callback:function(r){
									var f=r["file"]?r["file"]:r;
									//设置图像预览及路径
									me.setImageForm({src:f["path"]});
									//取消等待信息
									EG.Locker.lock(false);
									//执行OnUpload
									if(me.onUploaded) me.onUploaded.apply(me.editor,[r]);
								},
								exts:["JPG","PNG","GIF","BMP"],showWait:true,beforeUpload:function(){return me.beforeUpload("img");}
							}
						]
					}
				});
			}

			//this.menuPanel.addItem(this.imgForm);
		},

		doPickerSelect:function(){
			var tp=this.tpm[this.sltCg.getValue()];
			var uri=tp.getSelectedUri();
			if(uri==null){
				EG.Locker.message("请选择文件");
				return;
			}

			this.imgForm.getFormItem("imgSrc")		.setValue(uri.src);
			this.imgForm.getFormItem("imgShowArea")	.setValue("<img src='"+uri.src+"' height='40' />");
			this.imgForm.getFormItem("imgSrcUri")	.setValue(uri.uri);
			this.resPicker.close();
		},

		loadPickerFiles:function(type){
			var tp=this.tpm[type];
			tp.load(this.rpm);
		},

		setFiles:function(files){

			//清空
			this.rpm.clear();

			//设置页标
			this.rpf.getElement().innerHTML=""+files.length+"个文件";

			//根据类型创建列表或表格


		},

		/**
		 * 执行插入或替换已选中的图片
		 */
		doInsertImage:function(fd){
			var img={};

			if(arguments.length==0){
				fd=this.imgForm.getData();
			}

			img.src=fd["imgSrc"];
			if(img.src==""){EG.Locker.message("请选择图片或上传新图片");return;}
			img.width				=fd["imgWidth"];
			img.height				=fd["imgHeight"];
			img.style={};
			if(fd["imgFloat"]		!="")	{EG.css(img,"float:"		+fd["imgFloat"]);}
			if(fd["imgMarginLeft"]	!="")	{EG.css(img,"marginLeft:"	+fd["imgMarginLeft"]);}
			if(fd["imgMarginRight"]	!="")	{EG.css(img,"marginRight:"	+fd["imgMarginRight"]);}
			if(fd["imgMarginTop"]	!="")	{EG.css(img,"marginTop:"	+fd["imgMarginTop"]);}
			if(fd["imgMarginBottom"]!="")	{EG.css(img,"marginBottom:"	+fd["imgMarginBottom"]);}

			img.srcUri=this.imgForm.getFormItem("imgSrcUri").getValue();

			this.execute(img);
		},
		/**
		 * 设置图像属性
		 * @param {HTMLImageElement} img 图像元素
		 * @param {Object} atrs 属性值
		 */
		setImageAtrs:function(img,atrs){

			if(atrs["style"]) EG.css(img,atrs["style"]);
			if(atrs["width"]==null||atrs["width"]==""){
				img.removeAttribute("width");
			}else{
				img.width=atrs["width"];
			}

			if(atrs["height"]==null||atrs["height"]==""){
				img.removeAttribute("height");
			}else{
				img.height=atrs["height"];
			}

			if(atrs["srcUri"]!=null&&atrs["srcUri"]!=""){
				img.setAttribute("srcUri",atrs["srcUri"]);
			}

		},
		/**
		 * 双击时设置表单,上传后设置表单
		 * @param {Object} img 属性值
		 */
		setImageFormByImg:function(img){
			var atrs={
				src:img.src,
				style:img.style
			};
			this.setImageForm(atrs);
		},
		/**
		 * 设置图像表单
		 * @param {Object} atrs 属性值
		 */
		setImageForm:function(atrs){
			this.imgForm.items[0].getTab(0).select();
			//创建预览图
			var preViewImg=EG.CE({tn:"img"});
			var me=this;
			preViewImg.onload=function(){
				var ow=this.width,oh=this.height;
				me.imgForm.getFormItem("imgWidth").setValue(this.width);
				me.imgForm.getFormItem("imgHeight").setValue(this.height);
				if(this.width>this.height){
					this.height=oh/(ow/100);
					this.width=100;//最大宽
				}else{
					this.width=ow/(oh/40);
					this.height=40;//最大高
				}
			};
			preViewImg.style.cursor	="pointer";
			preViewImg.ondblclick	=function(){window.open(this.src);};
			preViewImg.title		="双击查看图片";
			preViewImg.src			=atrs.src;
			//TODO 待使用currentStyle,并且要解决FIREFOX中float的cssFloat别名问题
			if(atrs.style){
				if(atrs.style["float"])		{this.imgForm.getFormItem("imgFloat")		.setValue(atrs.style["float"].toLowerCase());}
				if(atrs.style.marginLeft)	{this.imgForm.getFormItem("imgMarginLeft")	.setValue(atrs.style.marginLeft.toLowerCase());}
				if(atrs.style.marginRight)	{this.imgForm.getFormItem("imgMarginRight")	.setValue(atrs.style.marginRight.toLowerCase());}
				if(atrs.style.marginTop)	{this.imgForm.getFormItem("imgMarginTop")	.setValue(atrs.style.marginTop.toLowerCase());}
				if(atrs.style.marginBottom)	{this.imgForm.getFormItem("imgMarginBottom").setValue(atrs.style.marginBottom.toLowerCase());}
			}


			if(atrs.width!=null) 	this.imgForm.getFormItem("imgWidth").setValue(atrs.width);
			if(atrs.height!=null) 	this.imgForm.getFormItem("imgHeight").setValue(atrs.height);

			this.imgForm.getFormItem("imgShowArea").setValue("");
			this.imgForm.getFormItem("imgShowArea").prop.getElement().appendChild(preViewImg);


			//如果是同域名的

			var src=atrs.src;
			var domainAddress=EG.Browser.getDomainAddress();
			if(src.indexOf(domainAddress)==0){
				src=src.substr(domainAddress.length);
			}
			this.imgForm.getFormItem("imgSrc").setValue(src);
		}
	});
	var Image=EG.ui.editor.plugin.Image;
	//注册
	EG.ui.Editor.registPlugin("image",Image);
})();

/**
 * @class EG.ui.editor.plugin.Indent
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体编号
 */
(function(){
	EG.define("EG.ui.editor.plugin.Indent",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"indent",
				click:function(){
					me.editor.hideMenus();
					var top=me.toolbarButton.getElement();
					var pos=EG.Tools.getElementPos(top,me.editor.getElement());
					pos.y=top.clientHeight;
					EG.Style.moveTo(me.menuPanel.dPop,pos);
					me.menuPanel.open();
				},
				mouseover:function(){
	//				me.menuPanel.open();
				},
				cls:"indent"
			});

			//创建面板
			this.buildMenuPanel();
		}
		/**
		 * 执行
		 * @param {String} indent 缩进
		 */,
		execute:function(indent){
			this.editor.htmlexec(indent);
		},
		/**
		 * 创建字体大小
		 */
		buildMenuPanel:function(){
			this.menuPanel=new EG.ui.Pop({closeable:true,lock:false,posFix:false,cls:"eg_pop_blank"});
			var me=this;
			var d=EG.CE({tn:"div",style:""});
			for(var i=0,il=Indent.indents.length;i<il;i++){
				(function(){
					var indent=Indent.indents[i];
					EG.CE({pn:d,tn:"a",cls:me.editor.cls+"-toolbar-indent-box",innerHTML:indent[0],onclick:function(){
						me.execute(indent[1]);
						me.menuPanel.close();
					}});
				})();
			}
			this.menuPanel.addChildren(d);
		},
		statics:{
			indents:[["增加缩进","Indent"],["减少缩进","Outdent"]]
		}
	});

	var Indent=EG.ui.editor.plugin.Indent;
	//注册
	EG.ui.Editor.registPlugin("indent",Indent);
})();

/**
 * @class EG.ui.editor.plugin.Indent
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体倾斜
 */
(function(){
	EG.define("EG.ui.editor.plugin.Italic",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"italic",
				click:function(){me.execute();},
				cls:"italic"
			});
		},
		/**
		 * 执行
		 */
		execute:function(){
			this.editor.htmlexec("Italic");
		}
	});

	var Italic=EG.ui.editor.plugin.Italic;
	//注册
	EG.ui.Editor.registPlugin("italic",Italic);
})();

/**
 * 
 *//**
 * @class EG.ui.editor.plugin.List
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体编号
 */
(function(){
	EG.define("EG.ui.editor.plugin.List",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"list",
				click:function(){
					me.editor.hideMenus();
					var top=me.toolbarButton.getElement();
					var pos=EG.Tools.getElementPos(top,me.editor.getElement());
					pos.y=top.clientHeight;
					EG.Style.moveTo(me.menuPanel.dPop,pos);
					me.menuPanel.open();
				},
				mouseover:function(){
	//				me.menuPanel.open();
				},
				cls:"list"
			});

			//创建面板
			this.buildMenuPanel();
		},
		
		/**
		 * 执行
		 * @param {String} list 编号
		 */
		execute:function(list){
			this.editor.htmlexec("Insert"+list+"List");
		},
		
		/**
		 * 创建字体大小
		 */
		buildMenuPanel:function(){
			this.menuPanel=new EG.ui.Pop({closeable:true,lock:false,posFix:false,cls:"eg_pop_blank"});
			var me=this;
			var d=EG.CE({tn:"div",style:""});
			for(var i=0,il=List.lists.length;i<il;i++){
				(function(){
					var list=List.lists[i];
					EG.CE({pn:d,tn:"a",cls:me.editor.cls+"-toolbar-list-box",innerHTML:list[0],onclick:function(){
						me.execute(list[1]);
						me.menuPanel.close();
					}});
				})();
			}
			this.menuPanel.addChildren(d);
		},
		statics:{
			lists:[["数字列表","Ordered"],["符号列表","Unordered"]]
		}
	});
	var List=EG.ui.editor.plugin.List;
	//注册
	EG.ui.Editor.registPlugin("list",List);
})();

/**
 * 
 *//**
 * @class EG.ui.editor.plugin.Textalign
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体对齐
 */
(function(){
	/**
	 * EG.ui.editor.plugin.Textalign 字体对齐
	 */
	EG.define("EG.ui.editor.plugin.Textalign",{
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function(editor){
			this.editor=editor;
			var me=this;
			this.toolbarButton=new EG.ui.editor.ToolbarButton(editor,{
				type:"textalign",
				click:function(){
					me.editor.hideMenus();
					var top=me.toolbarButton.getElement();
					var pos=EG.Tools.getElementPos(top,me.editor.getElement());
					pos.y=top.clientHeight;
					EG.Style.moveTo(me.menuPanel.dPop,pos);
					me.menuPanel.open();
				},
				mouseover:function(){
	//				me.menuPanel.open();
				},
				cls:"textalign"
			});

			//创建面板
			this.buildMenuPanel();
		},
		/**
		 * 执行
		 * @param {String} align 对齐
		 */
		execute:function(align){
			this.editor.htmlexec("Justify"+align);
		},
		/**
		 * 创建字体大小
		 */
		buildMenuPanel:function(){
			this.menuPanel=new EG.ui.Pop({closeable:true,lock:false,posFix:false,cls:"eg_pop_blank"});
			var me=this;
			var d=EG.CE({tn:"div",style:""});
			for(var i=0,il=Textalign.fontaligns.length;i<il;i++){
				(function(){
					var fontalign=Textalign.fontaligns[i];
					EG.CE({pn:d,tn:"a",cls:me.editor.cls+"-toolbar-textalign-box",innerHTML:fontalign[0],onclick:function(){
						me.execute(fontalign[1]);
						me.menuPanel.close();
					}});
				})();
			}
			this.menuPanel.addChildren(d);
		},
		statics:{
			fontaligns:[["左对齐","Left"],["居中对齐","Center"],["右对齐","Right"]]
		}
	});

	var Textalign=EG.ui.editor.plugin.Textalign;
	//注册
	EG.ui.Editor.registPlugin("textalign",Textalign);
})();

/**
 * @class EG.ui.editor.plugin.Textalign
 * @author bianrongjun
 * @extends EG.ui.editor.Plugin
 * 工具栏插件-字体倾斜
 */
(function () {
	EG.define("EG.ui.editor.plugin.Underline", {
		extend:"EG.ui.editor.Plugin",
		/**
		 * @constructor 构造函数
		 * @param {EG.ui.Editor} editor 编辑器
		 */
		constructor:function (editor) {
			this.editor = editor;
			var me = this;
			this.toolbarButton = new EG.ui.editor.ToolbarButton(editor, {
				type:"underline", click:function () {
					me.execute();
				},
				cls:"underline"
			});

			//this.menuPanel=new EG.ui.editor.MenuPanel();
		},
		/**
		 * 执行
		 */
		execute:function () {
			this.editor.htmlexec("Underline");
		}
	});
	var Underline=EG.ui.editor.plugin.Underline;
	//注册
	EG.ui.Editor.registPlugin("underline", Underline);
})();

/**
 * 
 *//**
 * 
 *//**
 * 
 */(function(){
	/**
	 * EG.ui.Upload 上传
	 */
	EG.define("EG.ui.Upload",{
		extend:"EG.ui.Item",
		config:{
			button				:null,
			action				:null,
			callback			:null,
			beforeUpload		:null,
			exts				:null,
			showWait			:false,
			showFilename		:true,
			showPath			:true,
			autoupload			:false,     //自动上传
			onselect			:null,
			filename			:null,
			subSelf		    	:true,		//用自身提交
			cls					:"eg_upload",
			showProcess			:false,
			name				:null,		//从FormItem中传递过来
			paramsConfig		:null
		},
		constructor:function(cfg){
			this.initItem(cfg);

			this.idx = EG.UI.GITEMIDX++;
			EG.UI.GITEMS[this.idx] = this;

			this.filename		=this.name||this.filename||("file" + this.idx);

			//if(!this.action)	throw new Error("EG.ui.Upload#上传action不能为空");



			//创建ActionFrame
			EG.DOM.getActionFrame();

			//创建Element
			if(this.subSelf){
				this.element=EG.CE({tn:"form",cls:this.cls,method : "post",encoding : "multipart/form-data",target:"actionFrame"});
			}else{
				this.element=EG.CE({tn:"div",cls:this.cls});
			}

			//设置Action
			this.setAction(this.action);

			//子元素
			EG.CE({ele:this.element,cn:[
				this.dPath 	=EG.CE({tn:"div",cls:this.cls+"-dPath",style:"display:none"}),
				this.dFileName =EG.CE({tn:"div",cls:this.cls+"-dFileName",style:"display:none",cn:[]}),
				this.dFileinput=EG.CE({tn:"div",cls:this.cls+"-dFileinput",style:"position:relative",cn:[

					this.dSelectBtn=new EG.ui.Button({text:"选择",click:function(){},style:"margin-left:2px"}),
					/*
					 this.dSelectBtn=EG.CE({tn:"div",cls:this.cls+"-selectBtn",innerHTML:"选择",style:"position:relative;cursor:pointer;",cn:[
					 ]}),
					* */
					this.fileinput=EG.CE({tn:"input",cls:this.cls+"-fileinput",name:this.filename,type :"file",style:"cursor:pointer;position:absolute;left:0px;top:0px;opacity:0;filter:alpha(opacity=0);"})
				]}),
				//隐藏参数区
				this.dHiddenParams=EG.CE({tn:"div",style:"display:none"})
			]});

			// BUGFIX:firefox的input file需要设置size来定宽度
//  		if (EG.Browser.isFirefox()) {
//  			this.fileinput.size = 0;
//  		}
			var me=this;

			EG.bindEvent(this.fileinput, "onchange", function() {
				var path=EG.getValue(me.fileinput);
				if(path){
					var pos = path.lastIndexOf("/");
					if(pos==-1){
						pos=path.lastIndexOf("\\");
					}
					var filename = path.substr(pos +1);
					EG.setValue(me.dFileName,filename);
					if(me.showFilename) EG.show(me.dFileName);
					if(me.onselect){
						me.onselect.apply(me,[path,filename]);
					}
					if(me.autoupload){
						me.submit();
					}
				}else{
					EG.hide(me.dFileName);
				}
			});


			//自动上传事件
			if (!this.autoupload&&this.showBtn){
				if (!this.button){
					this.button = new EG.ui.Button({text:"上传",click:function(){me.submit();},style:"veritical-align:middle;margin-left:10px"}).getElement();
				}
				this.element.appendChild(this.button);
			}

			EG.css(this.element,this.style);

			if(this.paramsConfig){
				this.setParams(this.paramsConfig);
			}
		},
		/**
		 * 设置Action
		 * @param action
		 */
		setAction:function(action){
			if(this.callback) 	action += "&callback=parent.EG.UI.GITEMS[" + this.idx + "].onUploaded&onerror=parent.EG.UI.GITEMS["+this.idx+"].onError";

			if(this.showProcess){
				action +="&callbackProcess=parent.EG.UI.GITEMS[" + this.idx + "].onProcess";
			}

			this.action=action;

			if(this.subSelf){
				EG.CE({ele:this.element,action:action});
			}
		},

		/**
		 * 设值
		 * @param value 值
		 */
		setValue:function(value) {
			EG.setValue(this.dPath,value);
		},
		/**
		 * 获值
		 * @returns {String}
		 */
		getValue:function() {
			return EG.getValue(this.dPath);
		},
		/**
		 * 获取Element
		 */
		getElement:function() {
			return this.element;
		},

		/**
		 * 设置参数值
		 * @param params
		 */
		setParams:function(params){
			for(var key in params){
				this.setParam(key,params[key]);
			}
		},



		/**
		 * 设置参数值
		 * @param name
		 * @param value
		 */
		setParam:function(name,value){

			var cns=this.dHiddenParams.childNodes;
			var ipt=null;
			for(var cn in cns){
				if(cn.name=="name"){
					ipt=cn;
					break;
				}
			}

			if(ipt==null){
				ipt=EG.CE({pn:this.dHiddenParams,tn:"input",type:"hidden",name:name,value:value});
			}

			ipt.value=value;
		},

		/**
		 * 移除混合提交的参数
		 */
		removeParam:function(name){
			var cns=this.dHiddenParams.childNodes;
			var ipt=null;
			for(var cn in cns){
				if(cn.name=="name"){
					ipt=cn;
					break;
				}
			}

			if(ipt!=null) EG.DOM.remove(ipt);
		},

		/**
		 * 提交
		 */
		submit:function() {
			if (EG.getValue(this.fileinput) == "") {
				EG.Locker.message( "请选择文件上传");
				return;
			}
			if (!this.checkExt()) {
				EG.Locker.message( "上传类型需为" + this.exts + "的一种");
				return;
			}
			if (this.beforeUpload&&!this.beforeUpload.apply(this))
				return;
			if (this.showWait)
				EG.Locker.wait( "正在上传文件,请稍后");
			this.element.submit();
		},
		/**
		 * 检测扩展类型
		 * @returns {Boolean}
		 */
		checkExt:function() {
			if (!this.exts) return true;
			var ext = this.getExt();
			for ( var i = 0; i < this.exts.length; i++){
				if (this.exts[i].toUpperCase() === ext.toUpperCase()) return true;
			}
			return false;
		},
		/**
		 * 获取扩展名
		 */
		getExt:function() {
			var path = EG.getValue(this.fileinput);
			return (path.substr(path.length - 5)).substr((path.substr(path.length - 5)).indexOf('.') + 1);
		},
		/**
		 * 上传前执行
		 * @param file
		 */
		onUploaded:function(file) {
			if(this.showPath) EG.show(this.dPath);
			//设值
			EG.setValue(this.dPath,file["path"]);

			if(typeof(this.callback) == "string") {
				if(this.callback == "showImg"){
					if(file["path"] == null) throw new Error("上传的返回值中不带path");
					EG.setValue(this.dPath,file["path"]);
					EG.DOM.removeChilds(this.dPreview);
					EG.CE({pn:this.dPreview,tn:"img",width:"50",height:"30",src:file["path"],style:"cursor:pointer",onclick:function(){window.open(this.src);}});
					//if (this.showWait) EG.Locker.lock(false);;
				}
			}else{
				this.callback.apply(this,arguments);//(arguments);
			}

			if (this.showWait) EG.Locker.lock(false);
		},
		/**
		 * 错误时执行
		 * @param error
		 */
		onError:function(error) {
			EG.Locker.message(error["exMessage"]);
		},
		onProcess:function(length,totalLength){
			EG.Locker.message("已上传:"+parseInt(length/1024)+"K"+parseInt((length/totalLength*100))+"%");
		},
		render:function(){
			EG.fit(this);
		},
		destroy:function(){
			EG.UI.GITEMS[this.idx]=null;
		},
		statics:{
			Callback:{
				showImg:"showImg"
			}
		}
	});
})();
(function(){
	/**
	 * EG.ui.Label 标签
	 */
	EG.define("EG.ui.Label",{
		extend:"EG.ui.Item",
		config:{
			title		:"",				//文字
			width		:null,				//宽度
			height		:null,				//高度
			style		:null				//样式
		},
		constructor:function(cfg){
			this.initItem(cfg);

			this.element=EG.CE({tn:"div",innerHTML:this.title});

			EG.ui.Item.setWidth(this.element,this.width);
			EG.ui.Item.setHeight(this.element,this.height);
			EG.css(this.element,this.style);
		},
		
		/**
		 * 设值
		 * @param value 数值
		 */
		setValue:function(value) {
			if(typeof(value)=="undefined") return;//TODO 其它类型也许考虑此问题
			EG.setValue(this.element, value);
		},
		
		/**
		 * 获值
		 */
		getValue:function() {
			return EG.getValue(this.element);
		},
		
		render:function(){
			EG.fit(this);

			var size=EG.getSize(this.element);

			EG.css(this.element,"line-height:"+size.innerHeight+"px");
		}
	});

	//注册部件类
	EG.ui.Item.regist("label",EG.ui.Label);
})();
(function(){
	/**
	 * EG.ui.Password 密码
	 */
	EG.define("EG.ui.Password",{
		extend:"EG.ui.Item",
		config:{
			length		:null,			//长度
			cls:"eg_password"	//样式类
		},
		constructor:function(cfg){
			this.initItem(cfg);

			//Element
			this.element=EG.CE({tn:"div",cls:this.cls,cn:[
				this.input=EG.CE({tn:"input",type:"password",cls:this.cls+"-input"})
			]});

			//设定样式
			EG.css(this.element,this.style);

			//最大长度
			if(this.length!=null) this.input.maxLength=this.length;
		},
		
		/**
		 * 设值
		 * @param value 数值
		 */
		setValue:function(value) {
			EG.setValue(this.input, value);
		},
		
		/**
		 * 获值
		 */
		getValue:function() {
			return EG.getValue(this.input);
		},
		
		render:function(){
			EG.fit(this);

			var s=EG.getSize(this.element);
			EG.fit({
				element:this.input,
				pSize:s
			});

			EG.css(this.input,"line-height:"+EG.getSize(this.input).innerHeight+"px");
		}
	});
})();(function(){
	/**
	 * EG.ui.Text 文本框
	 */
	EG.define("EG.ui.Text",{
		extend:"EG.ui.Item",
		config:{
			length		:null,			//长度
			cls			:"eg_text",		//样式类
			onkeydown	:null,
			onkeyup		:null
		},
		constructor:function(cfg){
            this.callSuper([cfg]);
			//最大长度
			if(this.length!=null) this.input.maxLength=this.length;
		},
        /**
         * 创建
         */
        build:function(){
            this.element=EG.CE({tn:"div",cls:this.cls,cn:[
                this.input=EG.CE({tn:"input",type:"text",cls:this.cls+"-input",item:this})
            ]});

			if(this.onkeydown){
				EG.Event.bindEvent(this.input,"onkeydown",this.onkeydown);
			}

			if(this.onkeyup){
				EG.Event.bindEvent(this.input,"onkeyup",this.onkeyup);
			}
        },
		/**
		 * 设值
		 * @param value 数值
		 */
		setValue:function(value) {
			EG.setValue(this.input, value);
		},
		/**
		 * 获值
		 */
		getValue:function() {
			return EG.getValue(this.input);
		},
		render:function(){
			EG.fit(this);
//			alert(this.height)
			var s=EG.getSize(this.element);
			EG.fit({
				element:this.input,
				pSize:s
			});

			EG.css(this.input,"line-height:"+EG.getSize(this.input).innerHeight+"px");
		}
	});
})();
(function(){
	/**
	 * EG.ui.Textarea 文本区域
	 */
	EG.define("EG.ui.Textarea",{
		extend:"EG.ui.Item",
		config:{
			style		:null,				//样式
			cls			:"eg_textarea"		//样式类
		},
		constructor:function(cfg){
			this.initItem(cfg);

			//Element
			this.element=EG.CE({tn:"div",cls:this.cls,cn:[
				this.input=EG.CE({tn:"textarea",cls:this.cls+"-input"})
			]});

			EG.css(this.element,this.style);
		},
		/**
		 * 设值
		 * @param value 数值
		 */
		setValue:function(value) {
			EG.setValue(this.input, value);
		},
		/**
		 * 获值
		 */
		getValue:function() {
			return EG.getValue(this.input);
		},
		render:function(){
			EG.fit(this);

			EG.fit({
				element:this.input,
				pSize:EG.getSize(this.element)
			});
		}
	});
})();(function(){
	/**
	 * EG.ui.Select 选择区
	 */
	EG.define("EG.ui.Select",{
		extend:"EG.ui.Item",
		config:{
			onchange	:null,				//数值变化事件
			textvalues	:[],				//文本-值 数组
			cls			:"eg_select",		//样式类
			edit		:false,
			onchangeOnbuild:false			//在创建的时候就触发
		},
		
		constructor:function(cfg){
			this.callSuper([cfg])
		},

		/**
		 * 创建
		 */
		build:function(){
			var me =this;

			this.builded=false;

			//onchange事件
			this.onchangeEvents=[];
			if(this.onchange!=null) this.bindOnchange(this.onchange);

			//创建Element
			this.element=EG.CE({tn:"div",cls:this.cls,item:this,
				onmouseover	:Select._events.element.onmouseover,
				onmouseout	:Select._events.element.onmouseout,
				cn:[
				this.input=EG.CE({tn:"div",cls:this.cls+"-input",style:"overflow:hidden",item:this,cn:[
					this.iptText=EG.CE({tn:"input",cls:this.cls+"-iptText",style:"overflow:hidden"}),
					this.dArrow	=EG.CE({tn:"div",cls:this.cls+"-arrow"})
				]
					,onclick:Select._events.input.onclick
				})
				,
				this.dOpts=EG.CE({tn:"div",cls:this.cls+"-opts"})
			]
			});

			//设置编辑状态
			//TODO 待支持用输入选择
			if(!this.edit){
				this.iptText.readOnly=true;
			}

			EG.hide(this.dOpts);

			//设置默认选项
			this.iptText.value="";
			//增加选项
			this.setOptions(this.textvalues);

			this.builded=true;
		},

		//显示选项Div
		showOptions:function(){
			EG.show(this.dOpts);

			//将已选中的值样式进行标注
			var v=this.getValue();
			var opts=this.dOpts.childNodes;
			for(var i= 0,il=opts.length;i<il;i++){
				if(v==opts[i].value){
					EG.setCls(opts[i],["opt","opt-selected"],this.cls);
				}else{
					EG.setCls(opts[i],"opt",this.cls);
				}
			}
		},

		/**
		 * 设值
		 * @param {String} value 数值
		 * @param {Boolean?} chain 是否连锁
		 */
		setValue:function(value,chain){
			if(chain==null) chain=true;
			if(value==null){
				return;
			}

			//value="";
			var tv,text;
			for(var i= 0,il=this.textvalues.length;i<il;i++){
				tv=this.textvalues[i];
				if(tv[1]==value){
					text=tv[0];
					break;
				}
			}

			//未找到值
			if(text==null){
				this.iptText.value="";
				return;
			}

			//旧值
			var oV=this.getValue();

			this.iptText.value=text;
			this.iptText.v=value;

			//触发onchange
			if(oV!=value&&chain){

				//刚创建的时候是否触发
				if(!this.builded&&!this.onchangeOnbuild) return;

				//执行OnChange
				this.doOnChange(value,oV);
			}
		},

		/**
		 * 执行OnChange
		 * @param {Object?} value
		 * @param {Object?} oldValue
		 */
		doOnChange:function(value,oldValue){
			for(var i=0;i<this.onchangeEvents.length;i++){
				this.onchangeEvents[i].apply(this,[value,oldValue]);
			}
		},

		/**
		 * 获值
		 */
		getValue:function(){
			return this.iptText.v;
		},

		/**
		 * 获取选择的索引
		 * @returns {number}
		 */
		getSelectedIdx:function(){
			var v=this.getValue();
			for(var i=0;i<this.textvalues.length;i++){
				if(this.textvalues[i][1]==v) return i;
			}
			return -1;
		},

		/**
		 * 设置选项
		 * @param idx 索引
		 * @param textvalue 文本值
		 */
		setOption:function(idx,textvalue){
			//设值
			this.textvalues[idx]=textvalue;

			//文本
			EG.CE({ele:this.dOpts.childNodes[idx],value:textvalue[1],innerHTML:textvalue[0]});

		   	if(this.getSelectedIdx()==idx){
				this.iptText.value	=textvalue[0];
				this.iptText.v		= textvalue[1];
			}
		},

		/**
		 * 设值选项,该操作会清空、重置Option，默认触发onchange
		 * @param {Array} textvalues 选项
		 * @param {Boolean?} fireOnchange 是否触发onchange
		 */
		setOptions:function(textvalues,fireOnchange){
			//清除Option
			this.removeOptions();

			//添加Option
			this.addOptions(textvalues,false);

			//触发Onchange
			if(this.textvalues&&this.textvalues.length>0) this.setValue(this.textvalues[0][1],fireOnchange);

			this.render();
		},

		/**
		 * 删除选项
		 */
		removeOptions:function(){
			this.textvalues=[];
			EG.DOM.removeChilds(this.dOpts);
			this.setValue("",false);
		},

		/**
		 * 绑定变化时事件
		 *
		 * onchange执行时自动获取到[新选取的值,旧值]
		 *
		 * @param {Function} onchange 变化时事件
		 */
		bindOnchange:function(onchange){
			this.onchangeEvents.push(onchange);
		},

		/**
		 * 添加选项
		 * @param textvalues
		 * @param {Boolean?} fireOnchange
		 */
		addOptions:function(textvalues,fireOnchange){
			if(fireOnchange==null) fireOnchange=true;

			//添加Option
			for(var i=0,il=textvalues.length;i<il;i++){
				this.addOption(textvalues[i],false);
			}

			//触发Onchange
			if(fireOnchange){
				if(this.textvalues&&this.textvalues.length>0) this.setValue(this.textvalues[0][1],true);
			}
		},

		/**
		 * 添加选项
		 * @param textvalue
		 * @param {Boolean?} fireOnchange
		 */
		addOption:function(textvalue,fireOnchange){

			if(fireOnchange==null) fireOnchange=true;

			EG.CE({pn:this.dOpts,tn:"div",cls:this.cls+"-opt",value:textvalue[1],item:this,innerHTML:textvalue[0],
				onmouseover	:Select._events.option.onmouseover,
				onmouseout	:Select._events.option.onmouseout,
				onclick		:Select._events.option.onclick
			});

			this.textvalues.push(textvalue);

			//触发Onchange
			if(fireOnchange){
				this.setValue(textvalue[1],true);
			}
		},

		/**
		 * 删除选项
		 */
		removeOption:function(idx,fireOnchange){
			if(fireOnchange==null) fireOnchange=true;

			var selectedIdx=this.getSelectedIdx();

			//删除TVS
			EG.Array.del(this.textvalues,idx);

			//DOM移除
			this.dOpts.removeChild(this.dOpts.childNodes[idx]);


			//已选择时触发新选项的onchange
			if(selectedIdx==idx){

				this.iptText.value="";
				if(this.textvalues.length==0) return;

				var nIdx=Math.min(idx,this.textvalues.length-1);
				var v=this.textvalues[nIdx][1];

				this.setValue(v,fireOnchange);
			}
		},

		/**
		 * 获取Text
		 * @param {Boolean?} ignoreEmpty 是否忽略空值
		 */
		getText:function(ignoreEmpty){
			return EG.getValue(this.iptText,{getText:true,ignoreEmpty:ignoreEmpty});
		},

		destroy:function(){

		},
		/**
		 * 渲染
		 */
		render:function(){

			//
			EG.fit(this);

			//this.input
			EG.fit({
				element:this.input,
				pSize:EG.getSize(this.element)
			});

			var size_select=EG.getSize(this.input);

			EG.css(this.dOpts,"width:"+size_select.outerWidth+"px");

			var size_arrow=EG.getSize(this.dArrow);

			EG.css(this.dArrow,"line-height:"+size_select.innerHeight+"px;height:"+size_select.innerHeight+"px");


			var pSize=EG.getSize(this.input);

			//alert("H:"+pSize.innerHeight)


			//alert(EG.toJSON(EG.getSize(this.iptText)));
			//this.iptText
			EG.fit({
				element:this.iptText,
				dSize:{
					width:size_select.innerWidth-size_arrow.outerWidth,
					height:"100%"
				},
				pSize:EG.getSize(this.input)
			});

			EG.css(this.iptText,"line-height:"+EG.getSize(this.iptText).innerHeight+"px;");
		},
		statics:{
			/**
			 * 事件
			 */
			_events:{
				//选项
				option:{
					onmouseover:function(){
						var me=this.item;
						EG.setCls(this,["opt","opt-over"],me.cls);
					},
					onmouseout:function(){
						var me=this.item;
						var v=me.getValue();
						if(this.value==v){
							EG.setCls(this,["opt","opt-selected"],me.cls);
						}else{
							EG.setCls(this,"opt",me.cls);
						}
					},
					onclick:function(){
						var me=this.item;
						me.setValue(this.value);
						EG.hide(me.dOpts);
					}
				},
				//外层Element
				element:{
					onmouseout:function(e){
						var me=this.item;
						if(me.outThread!=null) return;
						me.outThread=setTimeout(function(){
							EG.hide(me.dOpts);
						},10);
						EG.Event.stopPropagation(e);
					}
					,
					onmouseover:function(e){
						var me=this.item;
						if(me.outThread!=null){
							clearTimeout(me.outThread);
							me.outThread=null;
						}
						EG.Event.stopPropagation(e);
					}
				},
				//输入框
				input:{
					onclick:function(){
						var me=this.item;
						if(EG.Style.isHide(me.dOpts)){
							me.showOptions();
						}else{
							EG.hide(me.dOpts);
						}
					}
				}

			}
		}
	});
	var Select=EG.ui.Select;
})();
(function(){
	/**
	 * EG.ui.SelectArea 多项选择区
	 */
	EG.define("EG.ui.SelectArea",{
		extend:"EG.ui.Item",
		config:{
			onchange	:null,				//数值变化事件
			textvalues	:[],				//文本-值 数组
			cls			:"eg_selectArea",	//样式类
			edit		:false
		},

		constructor:function(cfg){
			this.callSuper([cfg]);

			if(this.textvalues) this.setTextvalues(this.textvalues);
		},

		/**
		 * 创建
		 */
		build:function(){
			var me=this;
			this.element=EG.CE({tn:"div",cls:this.cls,cn:[
				this.srcSlt=EG.CE({tn:"div",cls:this.cls+"-slts"}),
				this.dMid=EG.CE({tn:"div",cls:this.cls+"-dMid",cn:[
					new EG.ui.Button({text:"添加 >>",click:function(){me.move(true)}		,style:"display:block;margin:10px;"}),
					new EG.ui.Button({text:"<< 删除",click:function(){me.move()}			,style:"display:block;margin:10px;"})
				],style:"width:80px;"}),
				this.destSlt=EG.CE({tn:"div",cls:this.cls+"-slts"})
			]});

			//禁止选择
			EG.Event.bindUnselect(this.element);
		},

		/**
		 *
		 * @param tvs
		 */
		setTextvalues:function(tvs){
			EG.DOM.removeChilds(this.srcSlt);
			EG.DOM.removeChilds(this.destSlt);

			this.textvalues=tvs;
			this.addOptions(this.srcSlt,this.textvalues);
		},

		/**
		 * 移动
		 * @param {Boolean?} f 是否是正向
		 */
		move:function(f){
			var slt1=this.destSlt,slt2=this.srcSlt;
			if(f){
				slt1=this.srcSlt,slt2=this.destSlt;
			}

			var v1=this._getValues(slt1).concat(this._getValues(slt2,true));

			var tvs2=this._getTextvalues(slt2);
			var svs=[],dvs=[];
			for(var i=0;i<this.textvalues.length;i++){
				var tv=this.textvalues[i];
				if(EG.Array.has(v1,tv[1])){
					svs.push(tv);
				}else{
					dvs.push(tv);
				}
			}

			EG.DOM.removeChilds(slt1);
			EG.DOM.removeChilds(slt2);


			this.addOptions(slt2,svs);
			this.addOptions(slt1,dvs);
		},

		/**
		 * 获取选区的值
		 * @param {HTMLElement} ele 选区对象
		 * @param {Boolean?} all 是否全选
		 * @returns {Array}
		 * @private
		 */
		_getValues:function(ele,all){
			var cns=ele.childNodes;
			var vs=[];
			for(var i=0;i<cns.length;i++){
				if(all||cns[i].selected) {
						vs.push(cns[i].value);
				}
			}
			return vs;
		},

		/**
		 * 选择选项
		 * @param {HTMLElement} ele 选项元素
		 * @param {Boolean?} selected 是否选择
		 * @private
		 */
		_selectOpt:function(ele,selected){
			if(selected==null) selected=true;
			ele.selected	=selected;
			ele.className	=selected?this.cls+"-slted":this.cls+"-unslt";
		},

		/**
		 * 获取选项的索引
		 * @param {HTMLElement} ele 选项元素
		 * @returns {number}
		 * @private
		 */
		_getIdx:function(ele){
			var cns=ele.paren.childNodes;
			for(var i=0;i<cns.length;i++){
				if(cns[i]==cn) return i;
			}
			throw new Error("未找到索引");
		},

		/**
		 * 添加选项
		 * @param {HTMLElement} ele 选项区
		 * @param {Array} tvs TextValues
		 */
		addOptions:function(ele,tvs){
			var me=this;
			//alert(tvs)
			for(var i=0;i<tvs.length;i++){
				var tv=tvs[i];
				//alert(tv)
				EG.CE({pn:ele,tn:"div",innerHTML:tv[0],cls:this.cls+"-unslt",value:tv[1],onclick:function(e){
					e=EG.Event.getEvent(e);
					//如果按住shift，最后一次点击的项进行选择
					if(e.shiftKey){
						if(ele.lastIdx!=null){
							var idx=EG.DOM.getIdx(this);
							var sIdx=Math.min(ele.lastIdx,idx),bIdx=Math.max(ele.lastIdx,idx)
							var cns=this.parentNode.childNodes;
							//先取消
							for(var j=0;j<cns.length;j++){
								me._selectOpt(cns[j],false);
							}
							//再选中
							for(var j=sIdx;j<=bIdx;j++){
								me._selectOpt(cns[j],true);
							}
						}else{
							ele.lastIdx=EG.DOM.getIdx(this);
							me._selectOpt(this,!this.selected);
						}
						//me._selectOpt(this.parentNode,idx);
					//先清空选择，再选择当前
					}else{
						if(!e.ctrlKey){
							var cns=this.parentNode.childNodes;
							for(var j=0;j<cns.length;j++){
								me._selectOpt(cns[j],false);
							}
						}
						ele.lastIdx=EG.DOM.getIdx(this);
						me._selectOpt(this,!this.selected);
					}
				}});

			}
		},

		clear:function(){
			EG.DOM.removeChilds(this.destSlt);
			EG.DOM.removeChilds(this.srcSlt);
		},

		clearSourceOptions:function(){
			EG.DOM.removeChilds(this.srcSlt);
		},
		
		clearSelectedOptions:function(){
			EG.DOM.removeChilds(this.destSlt);
		},

		addSourceOptions:function(tvs){
			this.addOptions(this.srcSlt,tvs);
		},

		/**
		 * 添加选区的Options
		 * @param tvs
		 */
		addSelectedOptions:function(tvs){
			this.addOptions(this.destSlt,tvs);
		},

		/**
		 * 还原
		 */
		reset:function(){
			EG.DOM.removeChilds(this.srcSlt);
			EG.DOM.removeChilds(this.destSlt);
			this.addOptions(this.srcSlt,this.textvalues);
		},

		/**
		 * 设值
		 * @param value 数值
		 */
		setValue:function(value){
			//还原
			this.reset();

			//选中
			var cns=this.srcSlt.childNodes;
			for(var j=0;j<cns.length;j++){
				if(EG.Array.has(value,cns[j].value)){
					this._selectOpt(cns[j],true);
				}
			}
			// move
			this.move(true);
		},

		/**
		 * 获值
		 */
		getValue:function(){
			return this._getValues(this.destSlt,true);
		},

		/**
		 * 获取Text
		 */
		getText:function(){
			var tvs=[];
			var cns=this.destSlt.childNodes;
			for(var j=0;j<cns.length;j++){
				tvs.push(cns[j].innerHTML);
			}
			return tvs;
		},

		/**
		 * 获取TextValues
		 * @param ele
		 * @returns {Array}
		 * @private
		 */
		_getTextvalues:function(ele){
			var tvs=[];
			var cns=ele.childNodes;
			for(var i=0;i<cns.length;i++){
				tvs.push([cns[i].innerHTML,cns[i].value]);
			}
			return tvs;
		},

		getSelectedTextvalues:function(){
			return this._getTextvalues(this.destSlt);
		},

		/**
		 * 渲染
		 */
		render:function(){
			EG.fit(this);
			var pSize=EG.getSize(this.element);
			var wDMid=EG.getSize(this.dMid).outerWidth;
			var iw=(pSize.innerWidth-wDMid)/2;
			EG.fit({
				element:this.dMid,
				dSize:{height:"100%"},
				pSize:pSize
			});

			var sSize=EG.getSize(this.srcSlt)
			EG.fit({
				element:this.srcSlt,
				dSize:{width:iw,height:"100%"},
				pSize:pSize
			});
			EG.fit({
				element:this.destSlt,
				dSize:{width:iw,height:"100%"},
				pSize:pSize
			});
		}
	});
})();
/**
 * @class EG.ui.Grid
 * @author bianrongjun
 * @extends EG.ui.Item
 * 表格组件
 */
(function(){
	EG.define("EG.ui.Grid",{
		extend:"EG.ui.Item",

		config:{
			cls					:"eg_grid",		//样式类
			rowClasses			:["row-a","row-b"],	//行样式
			changeRowClassAble	:true,			//是否改变行样式
			selectRowOnclick	:true,			//是否在点击时选中
			selectSingleOnclick	:true,			//是否在点击时只单选
			boxAble				:true,			//是否显示checkbox
			seqAble				:true,			//是否显示序号
			colSelectAble		:false,			//是否可以选列
			colOrderAble		:false,			//是否可以排序
			colAdjAble			:false,			//是否可以调整列宽
			gridFixed			:false,			//无内容时是否用&nbsp;填充
			columns				:null,			//列
			rowEvents			:{},			//行事件
			colEvents			:{},			//列事件
			remotingCallback	:null,			//远程请求回调处理
			showHead			:true,			//显示头
			showFoot			:true,			//显示尾
			renderTo			:null,			//被添加到某节点下，并被渲染
			pageSize			:30,
			toolbar				:"first pre stat next last size",//工具栏
			cellInnerStyle		:null,			//表格内部样式
			onOrder				:null			//排序时动作
		},

		/**
		 * 构造函数
		 * @param cfg
		 */
		constructor:function(cfg){
			Grid.load();
			this.callSuper([cfg]);
		},

		/**
		 * 初始化组件
		 * @param cfg
		 */
		initItem:function(cfg){
			this.callSuper("initItem",[cfg]);

			this._currentPage	=0;
			this._dataSize		=0;
			this._pageCount		=0;
			this._segment		=-1;
			this.colOptAble		=false;
		},

		_ef_row:{
			changeRow_mouseover:function(e){
				this.grid.overRow(this,true);
				if(this.grid.fn_row_mouseover) this.grid.fn_row_mouseover.apply(this,[e]);
			},
			changeRow_mouseout:function(e){
				this.grid.overRow(this,false);
				if(this.grid.fn_row_mouseout) this.grid.fn_row_mouseout.apply(this,[e]);
			},
			selectRow_click:function(e){
				e=EG.Event.getEvent(e);
				var cns=this.parentNode.childNodes;
				//SHIFT范围选
				if(e.shiftKey&&this.grid.lastIdx!=null){
					var sIdx=Math.min(this.grid.lastIdx,this.index),bIdx=Math.max(this.grid.lastIdx,this.index);
					var cns=this.parentNode.childNodes;
					for(var i=0;i<cns.length;i++)	{this.grid.selectRow(cns[i],false);}
					for(var i=sIdx;i<=bIdx;i++)		{this.grid.selectRow(cns[i],true);}
					//CTRL+普通点选
				}else{
					this.grid.lastIdx=this.selected?null:this.index;
					if(!e.ctrlKey){
						for(var i=0;i<cns.length;i++){if(cns[i]!=this) this.grid.selectRow(cns[i],false);}
					}
					this.grid.selectRow(this,!this.selected);
				}
				if(this.grid.clickFn) this.grid.clickFn.apply(this,[e]);
			}
		},

		/**
		 * 创建
		 */
		build:function(){
			//Element
			this.element=EG.CE({tn:"div",cn:[
				this.dTop={tn:"div"},
				this.dMain=EG.CE({tn:"div",style:"width:100%;overflow:hidden;position:relative;"}),
				this.dFoot=EG.CE({tn:"div",cls:this.cls+"-foot"})
			]});

			//禁止选择
			EG.Event.bindUnselect(this.element);

			//创建区域
			this.buildHead();
			this.buildBody();
			this.buildFixBody();
			this.buildFixHead();

			//初始化头部
			this.initHead();
			this.buildToolBar();

			//控制显示头
			if(!this.showHead) EG.hide(this.dHead,this.dFixHead);

			//控制显示尾
			if(!this.showFoot) EG.hide(this.dFoot);

			//行样式变化
			if(this.changeRowClassAble){
				this.fn_row_mouseover	=this.rowEvents["onmouseover"];
				this.fn_row_mouseout	=this.rowEvents["onmouseout"];

				this.rowEvents["onmouseover"]	=(this._ef_row.changeRow_mouseover);
				this.rowEvents["onmouseout"]	=(this._ef_row.changeRow_mouseout);
			}

			//点击选中
			if(this.selectRowOnclick){
				this.clickFn=this.rowEvents["onclick"];
				this.rowEvents["onclick"]=(this._ef_row.selectRow_click);
			}

			//可调整
			if(this.colAdjAble){

				//创建可调整
				this.colAdjRulerL=EG.CE({pn:this.element,tn:"div",cls:this.cls+"-adjRuler"});
				this.colAdjRulerR=EG.CE({pn:this.element,tn:"div",cls:this.cls+"-adjRuler"});

				EG.hide(this.colAdjRulerL,this.colAdjRulerR);

//				EG.bindEvent(this.grid,"mousemove",function(evt){
//					evt = evt || window.event;
//					if(PagingGrid.adjIng){
//						var x=EG.Tools.getMousePos(evt).x;
//						//边界判断
//						/*if(rulerRight.offsetLeft<rulerLeft.offsetLeft+20){
//							x=(parseInt(EG.String.removeEnd(rulerLeft.style.left,"px"))+20)+"px";
//							rulerRight.style.left=x+"px";}*/
//						PagingGrid.colAdjRulerR.style.left=x+"px";
//
//						//计算table宽度,计算所有列的宽度
//						//this._head
//					}
//				});
			}

			//创建选项区
			if(this.colOptAble){
				this.buildColOpt();
			}
		},
		_fn_selectBox:function(){
			this.grid.selectRow(this.row,!this.selected);
		},

		/**
		 * 设置数据
		 */
		setData:function(data){
			//alert(EG.toJSON(data))
			var me=this;
			this.data=data;
			this.boxes=[];

			EG.DOM.removeChilds(this.tbBody);
			EG.DOM.removeChilds(this.tbFixBody);

			var globalRowNumber=0;

			//设置行，列数据
			var i,j,key;
			//行处理
			for(i=0;i<this.pageSize;i++){

				var d=this.data[i];

				//如果数据为空 && 无 空格填满策略 则断开
				if(!d&&!this.gridFixed) break;

				var row		=EG.CE({pn:this.tbBody,tn:"tr",index:i,grid:this,data:d});
				var rowFix	=EG.CE({pn:this.tbFixBody,tn:"tr",index:i});

				//绑定行事件
				for(key in me.rowEvents){
					EG.Event.bindEvent(row,key,me.rowEvents[key]);
				}

				//设定行样式,若是多样式会出现间隔性样式
				if(this.rowClasses&&this.rowClasses.length) {
					var rowClassName=this.cls+"-"+this.rowClasses[i%this.pageSize%this.rowClasses.length];
					EG.setCls(row,rowClassName);
					EG.setCls(rowFix,rowClassName);
				}

				//全部里的行标号
				globalRowNumber=this._currentPage*this.pageSize+i;

				//是否带选框
				if(this.boxAble){
					var box=new EG.ui.Box({showText:false,row:row,grid:this,onclick:this._fn_selectBox});
					EG.CE({pn:rowFix,tn:"td",cls:(this.cls+"-fixCol"),style:"width:30px",cn:[
						{tn:"div",cls:this.cls+"-fixBodyCellInner",cn:[box]}
					]});
					row.box=box;
				}

				//是否带序号
				if(this.seqAble){
					EG.CE({pn:rowFix,tn:"td",cls:this.cls+"-fixCol",style:"text-align:center;vertical-align:middle;width:30px",cn:[
						{tn:"div",cls:this.cls+"-fixBodyCellInner",innerHTML:(globalRowNumber+1)}
					]});
				}

				//列处理
				for(j=0;j<this.columns.length;j++){
					var column=this.columns[j];
					var textlength	=column["textlength"],
						textlengthEnd=column["textlengthEnd"]||"",
						handle		=column["handle"],
						field		=column["field"],
						fieldClass	=column["fieldClass"]||"txtcenter",
						width		=column["width"],
						fix			=column["fix"];

					var col,cellInner;
					col=EG.CE({tn:"td",cls:this.cls+"-bodyCol",cn:[
						cellInner=EG.CE({tn:"div",cls:this.cls+"-bodyCellInner"})
					]});

					//内样式
					if(this.cellInnerStyle){
						EG.css(cellInner,this.cellInnerStyle);
					}

					//设置样式
					if(fieldClass) 		col.className+=(" "+fieldClass);
					//绑定列事件
					for(key in this.colEvents){
						EG.Event.bindEvent(col,key,this.colEvents[key]);
					}
					//数据处理
					var ihtml;
					if(d){
						ihtml=null;
						//自定义处理器
						if(handle){
							var hr=handle.apply(this,[this.data[i],i,globalRowNumber]);
							if(hr!=null){
								if(hr.nodeType){
									cellInner.appendChild(hr);
								}else{
									if(typeof(hr)=="object"&&hr.length!=null){
										for(var x=0;x<hr.length;x++){
											if(EG.ui.Item.isItem(hr[x])){
												cellInner.appendChild(hr[x].getElement());
											}else{
												cellInner.appendChild(hr[x]);
											}
										}
									}else cellInner.innerHTML=hr;
								}
							}
							//从field取值
						}else if(this.data[i][field]||!isNaN(this.data[i][field])){
							ihtml=this.data[i][field]||"";
							if(ihtml&&textlength!=null&&ihtml.length>textlength) ihtml=ihtml.substr(0,textlength)+textlengthEnd;
							cellInner.innerHTML=ihtml;
						}else{
							cellInner.innerHTML=field;
						}
					//填白
					}else if(this.gridFixed) cellInner.innerHTML="&nbsp;";

					if(fix) {
						rowFix.appendChild(col);
						EG.setCls(col,"fixCol",this.cls);
						EG.setCls(cellInner,"fixBodyCellInner",this.cls);
					}else{
						row.appendChild(col);
					}
					//设定列宽
					if(width){
						EG.css(col,"width:"+(column.outerWidth)+"px");			//TODO 1要变为动态检测Header的Border宽
						EG.css(cellInner,"width:"+(width)+"px");//TODO 需将固定值12变为自动检测bodyCellInner的左右padding和
					}
				}
			}


			this.fitFixSize();
		},

		/**
		 * 创建头
		 */
		buildHead:function(){
			this.dHead=EG.CE({pn:this.dMain,cls:this.cls+"-head",tn:"div",style:"overflow:hidden;",cn:[
				this.tHead=EG.CE({tn:"table",border:0,cellPadding:0,cellSpacing:0,style:"table-layout:fixed;",cn:[
					this.tbHead=EG.CE({tn:"tbody"})
				]})
			]});
		},

		/**
		 * 创建Body
		 */
		buildBody:function(){
			var me=this;
			this.dBody=EG.CE({pn:this.dMain,cls:this.cls+"-body",tn:"div",style:"overflow:auto;",onscroll:function(){
				me.dHead.scrollLeft=this.scrollLeft;
				me.dFixBody.style.top=((-this.scrollTop)+me.dFixHead.clientHeight)+"px";
			},cn:[
				this.tBody=EG.CE({tn:"table",border:0,cellPadding:0,cellSpacing:0,style:"table-layout:fixed;",cn:[
					this.tbBody=EG.CE({tn:"tbody"})
				]})
			]});
		},

		/**
		 * 创建固定头
		 */
		buildFixHead:function(){
			this.dFixHead=EG.CE({pn:this.dMain,cls:this.cls+"-fixHead",tn:"div",style:"position:absolute;left:0px;top:0px",cn:[
				{tn:"table",style:"table-layout:fixed;",border:0,cellPadding:0,cellSpacing:0,cn:[
					this.tbFixHead=EG.CE({tn:"tbody"})
				]}
			]});
		},

		/**
		 * 创建固定Body
		 */
		buildFixBody:function(){
			this.dFixBody=EG.CE({pn:this.dMain,cls:this.cls+"-fixBody",tn:"div",style:"position:absolute;overflow:hidden;left:0px;top:0px",cn:[
				{tn:"table",style:"table-layout:fixed;",border:0,cellPadding:0,cellSpacing:0,cn:[
					this.tbFixBody=EG.CE({tn:"tbody"})
				]}
			]});
		},

		/**
		 * 渲染
		 */
		render:function(){
			//设置总尺寸
			EG.fit(this);

			//计算设置中间高度
			var size			=EG.getSize(this.element);
			var mainHeadsize	=EG.getSize(this.dHead);
			var footSize		=EG.getSize(this.dFoot);
			var mainHeight		=size.innerHeight-footSize.outerHeight;
			var mainBodyHeight	=mainHeight-mainHeadsize.outerHeight;
			EG.css(this.dBody,"height:"+mainBodyHeight+"px");

			//计算设置中间宽度
			var tableWidth=0;
			for(var i=0;i<this.columns.length;i++){
				var column		=this.columns[i];
				//根据head计算总宽
				var width		=column["width"];
				EG.css(column.dHeadInner,"width:"+width+"px");
				var w=EG.getSize(column.dHeadInner).outerWidth;
				EG.css(column.tdHead,"width:"+w+"px");
				column.outerWidth=w;
				tableWidth+=w;
			}
			EG.css(this.tHead,"width:"+(tableWidth+Grid.appendHeadWidth)+"px");

			//固定部分尺寸
			this.fitFixSize();
		},

		/**
		 * 设置固定部分的尺寸
		 */
		fitFixSize:function(){
			//如果没有设定列宽,同步fixHead列宽度
			if(this.tbFixBody.childNodes.length>0){
				var fixBodyTr=this.tbFixBody.childNodes[0];
				for(var i=0,il=fixBodyTr.childNodes.length;i<il;i++){
					var cSize=EG.getSize(fixBodyTr.childNodes[i]);
					EG.css(this.tbFixHead.childNodes[0].childNodes[i],"width:"+cSize["innerWidth"]+"px");
				}

				//简单实现内容TD高度和fixTD高度的同步
				for(var i=0,il=this.tbBody.childNodes.length;i<il;i++){
					if(		this.tbBody		.childNodes[i].childNodes.length==0
						||	this.tbFixBody	.childNodes[i].childNodes.length==0
					) continue;

					var td		=this.tbBody	.childNodes[i].childNodes[0];
					var tdFix	=this.tbFixBody	.childNodes[i].childNodes[0];

					var s	=EG.getSize(td);
					var sFix=EG.getSize(tdFix);

					if(sFix.innerHeight>s.innerHeight){
						EG.css(td	,"height:"+	sFix["innerHeight"]	+"px");
					}else{
						EG.css(tdFix,"height:"+	s["innerHeight"]	+"px");
					}
				}
			}

			//同步dBody和dHead的左边距
			var fixHeadsize=EG.getSize(this.dFixHead);
			EG.css(this.dBody,"margin-left:"+fixHeadsize.outerWidth+"px");
			EG.css(this.dHead,"margin-left:"+fixHeadsize.outerWidth+"px");

			//同步fixBody的Top
			this.dFixBody.style.top=((-this.dBody.scrollTop)+this.dFixHead.clientHeight)+"px";
		},
		
		/**
		 * 初始化头部
		 */
		initHead:function(){
			var me=this;

			//固定行
			var hrFix	=EG.CE({pn:this.tbFixHead	,tn:"tr",cls:me.cls+"-head"});
			//普通行
			var hr		=EG.CE({pn:this.tbHead		,tn:"tr",cls:me.cls+"-head"});

			//头部全选BOX
			if(this.boxAble){
				EG.CE({pn:hrFix,tn:"td",cls:this.cls+"-headCol",style:"text-align:center;",cn:[
					{tn:"div",cls:this.cls+"-fixHeadCellInner",style:"",cn:[
						this.boxHead=new EG.ui.Box({showText:false,onselect:function(){me.selectAllBox(!this.selected);}})
					]}
				]});
			}

			//头部序号
			if(this.seqAble){
				EG.CE({pn:hrFix,tn:"td",cls:this.cls+"-headCol",style:"text-align:center;",cn:[
					{tn:"div",cls:this.cls+"-fixHeadCellInner",style:"width:12px",innerHTML:"&nbsp;"}
				]});
			}

			//头部列
			for(var i=0;i<this.columns.length;i++){
				var column		=this.columns[i];
				var header		=column["header"],
					headerEvent	=column["headerEvent"],
					headerStyle	=column["headerStyle"],
					width		=column["width"],
					fix 		=column["fix"],
					order 		=column["order"],
					field		=column["field"];

				//列TD
				var col=EG.CE({pn:fix?hrFix:hr,tn:"td",cls:me.cls+"-headCol",style:"white-space:nowrap",me:this});
				col.dContent=EG.CE({pn:col,tn:"div",cls:me.cls+"-headCellInner",me:this});

				//内容
				if(typeof(header)=="string") col.dContent.innerHTML=header;
				else col.dContent.appendChild(header);

				//排序
				if(me.colOrderAble&&order){
					col.orderName=typeof(order)=="boolean"?field:order;
					EG.Event.bindEvent(col,"click",Grid._events.head.onclick);
				}

				//可调整区域
				if(this.colAdjAble){
					col.dAdj=EG.CE({pn:col,tn:"div",innerHTML:"&nbsp;",className:me.cls+"-head_adj",
						ondblclick:function(){
							alert("待实现，双击调整宽度");
						},
						onmousedown:function(){
							me.startAdjColWidth(this.parentNode);
						}
					});
				}

				column.tdHead		=col;
				column.dHeadInner	=col.dContent;
			}

			//头行尾部填充列
			EG.CE({pn:hr,tn:"td",cls:this.cls+"-headCol",cn:[
				{tn:"div",cls:this.cls+"-headCellInner",style:"width:"+Grid.appendHeadWidth+"px"}
			]});
		},

		/**
		 * 鼠标移动到行上时的动作
		 * @param tr 行
		 * @param over
		 */
		overRow:function(tr,over){
			if(typeof(tr)=="number") tr=this.tbBody.childNodes[tr];
			if(tr.selected) return;

			if(over){
				if(!tr.oldClass) tr.oldClass=tr.className;
				EG.setCls(tr,"row-over",this.cls);
			}else{
				EG.setCls(tr,tr.oldClass);
			}
		},

		/**
		 * 选择行,样式变化
		 * @param tr
		 * @param selected
		 * @param {Boolean?} single
		 */
		selectRow:function(tr,selected,single){

			if(typeof(tr)=="number") tr=this.tbBody.childNodes[tr];
			tr.selected=selected;
			if(tr.selected){
				if(!tr.oldClass) tr.oldClass=tr.className;
				EG.setCls(tr,"row-selected",this.cls);
			}else{
				EG.setCls(tr,tr.oldClass);
			}
			if(this.boxAble){
				tr.box.select(tr.selected);
			}

			if(single){
				var cns=tr.parentNode.childNodes;
				for(var i=0;i<cns.length;i++){if(cns[i]!=tr) this.selectRow(cns[i],false);}
			}
		},

		/**
		 * 选择所有box
		 * @param selected {Boolean}
		 */
		selectAllBox:function(selected){
			var trs=this.tbBody.childNodes;
			for(var i=0,l=trs.length;i<l;i++){
				this.selectRow(trs[i],selected);
			}
		},

		/**
		 * 获取选中的IDX
		 * @returns {Array}
		 */
		getSelectIdx:function(){
			var sd=[];
			var trs=this.tbBody.childNodes;
			for(var i=0,l=trs.length;i<l;i++){
				if(trs[i].selected){
					sd.push(i);
				}
			}
			return sd;
		},

		/**
		 * 获取选中的数据
		 * @returns {Array}
		 */
		getSelectData:function(key){
			var sd=[];
			var trs=this.tbBody.childNodes;
			for(var i=0,l=trs.length;i<l;i++){
				if(trs[i].selected){
					var d=trs[i]["data"];
					sd.push(key?d[key]:d);
				}
			}
			return sd;
		},
		
		/**
		 * 设置数据大小
		 */
		setDataSize:function(dataSize){
			this._dataSize=dataSize;
			this._pageCount=Math.ceil(this._dataSize/this.pageSize);

			//当前页码检测
			if(this._currentPage+1>this._pageCount){
				this._currentPage=this._pageCount-1;
			}

			if(this._currentPage<0){
				this._currentPage=0;
			}

			//表格变化时发生的动作
			for(var i=0;i<Grid.handler.gridChangedAction.length;i++){
				if(this[Grid.handler.gridChangedAction[i]]&&this[Grid.handler.gridChangedAction[i]]instanceof Function){
					this[Grid.handler.gridChangedAction[i]]();
				}
			}
		},
		
		/**
		 * 找到列，转移列所有数据列和到fixBody和fixHead中
		 * @param colIdx 列索引
		 */
		fixColumn:function(colIdx){
			if(typeof(colIdx)=="string"){//根据headName转化成idx
				colIdx=this.getColumnIdx(colIdx);
			}
			//移动Body
			var cns=this.tbBody.childNodes;
			for(var i=cns.length-1;i>=0;i++){
				EG.CE({pn:this.tbFixBody,tn:"tr",cn:cns[i].childNodes[colIdx]});
			}
			//移动头部
			EG.CE({pn:this.tbFixHead,tn:"tr",cn:this.tbHead.childNodes[0].childNodes[colIdx]});
		},
		
//		/**
//		 * 解锁列
//		 * @param colIdx 列索引
//		 */
//		unfixColumn:function(colIdx){
//			//TODO 转移
//		},
		
		/**
		 *
		 * @param header
		 * @return {Number}
		 */
		getColumnIdx:function(header){
			var startIdx=0;
			if(this.boxAble) startIdx++;
			if(this.seqAble) startIdx++;

			for(var i=0;i<this.columns.length;i++){
				if(header==this.columns[i]["header"]) return i+startIdx;
			}
			throw new Error("EG.ui.Grid#getColumnIdx:未找到对应列");
		},
		

		
		/**
		 * 获取数据
		 */
		getData:function() {
			return this.data;
		},
		
		/**
		 * 跳转创建动作,同时远程读取
		 */
		go:function(pageIdx){
			//alert(pageIdx+":"+this._pageCount);
			if(pageIdx>this._pageCount-1) pageIdx=this._pageCount-1;
			if(pageIdx<0) pageIdx=0;

			this._currentPage=pageIdx;

			//远程读取
			if(this.remotingCallback){
				this.remotingCallback.apply(this["remotingCallbackSrc"]||this,[pageIdx]);
			}

			if(this.boxAble){
				this.boxHead.select(false,true);
				//this.tbFixHead.childNodes[0].childNodes[0]
			}
		},

		/** 前一页 */
		prePage:function(){this.go(this._currentPage - 1);},
		/** 后一页 */
		nextPage:function(){this.go(this._currentPage+1);},
		/** 首页 */
		firstPage:function(){this.go(0);},
		/** 末页 */
		lastPage:function(){this.go(this._pageCount-1);},
		/** 当前页 */
		curPage:function(){this.go(this._currentPage);},

		/**
		 * 创建列选项
		 */
		buildColOpt:function(){
			this.dColOtp=EG.CE({pn:EG.getBody(),tn:"div",cls:"pagingGrid_dColOpt",style:"display:none",onmouseleave:function(){
				EG.hide(this);
			}});
			var overFn=function(){this.style.backgroundColor="white";};
			var outFn =function(){this.style.backgroundColor="";};
			if(this.colOrderAble){

				EG.CE({pn:this.dColOtp,tn:"div",innerHTML:"正序",cls:"ele",onclick:function(){
					//EG.show(this.dColSelect);
				},onmouseover:overFn,onmouseout:outFn});
				EG.CE({pn:this.dColOtp,tn:"div",innerHTML:"倒序",cls:"ele",onclick:function(){
					//EG.show(this.dColSelect);
				},onmouseover:overFn,onmouseout:outFn});
			}

			if(this.colSelectAble){
				EG.CE({pn:this.dColOtp,tn:"div",grid:this,innerHTML:"列",cls:"ele",onmouseenter:function(){
					var p=EG.Tools.getElementPos(this);
					EG.css(this.grid.dColSelect, {top:p.y+"px",left:(p.x+this.grid.dColOtp.clientWidth-20)+"px",display:""});
				},onmouseover:overFn,onmouseout:outFn});

				this.dColSelect=EG.CE({pn:EG.getBody(),tn:"div",cls:"pagingGrid_dColSelect",style:"display:none",onmouseleave:function(){EG.hide(this);}});

				for(var i=0,l=this.columns.length;i<l;i++){
					var column=this.columns[i];
					var b=new EG.UI.PropBox({
						title:column["header"],
						onselect:function(){}
					});
					this.dColSelect.appendChild(b.getElement());
				}
			}
		},

		/**
		 * 创建工具栏
		 */
		buildToolBar:function(){
			var tools=this.toolbar.split(/\s+/);
			for(var i=0;i<tools.length;i++){
				var method=Grid.handler.toolsMap[tools[i]];
				var tool;
				if(!method) continue;
				tool=eval('this.'+method+'()');
				this.dFoot.appendChild(tool);
			}
		},

		getOptions:function(){
			return EG.CE({tn:"a",href:"javascript:void(0)",innerHTML:"选择",onclick:function(){alert("1");}});//TODO 待实现
		},

		/**
		 * 获取第一页HTML
		 */
		getFirstPage:function(){
			var me=this;
			return EG.CE({tn:"a",href:"javascript:void(0)",cls:this.cls+"-firstPage",onclick:function(){me.firstPage();return false;}});
		},

		/**
		 * 获取第一页HTML
		 */
		getPrePage:function(){
			var me=this;
			return EG.CE({tn:"a",href:"javascript:void(0)",cls:this.cls+"-prePage",onclick:function(){me.prePage();return false;}});
		},

		/**
		 * 获取第一页HTML
		 */
		getNextPage:function(){
			var me=this;
			return EG.CE({tn:"a",href:"javascript:void(0)",cls:this.cls+"-nextPage",onclick:function(){me.nextPage();return false;}});
		},

		/**
		 * 获取第一页HTML
		 */
		getLastPage:function(){
			var me=this;
			return EG.CE({tn:"a",href:"javascript:void(0)",cls:this.cls+"-lastPage",onclick:function(){me.lastPage();return false;}});
		},
		
		/**
		 * 获取状态栏
		 */
		getState:function(){
			var me=this;
			var span=EG.CE({tn:"span",cls:this.cls+"-state",cn:[
				{tn:"a",cls:this.cls+"-gotoPage",onclick:function(){me.go(parseInt(me.currentPageValue.value)-1);}},
				{tn:"span",innerHTML:"第"},
				this.currentPageValue=EG.CE(
					{tn:"input",type:"text",size:2,style:""}
				),
				{tn:"span",innerHTML:"/"},
				this.pageCountValue=EG.CE({tn:"span",innerHTML:this._pageCount}),
				{tn:"span",innerHTML:"页"}
			],style:""});
			return span;
		},

		/**
		 * 获取RecordSize
		 * @returns {Number}
		 */
		getRecordSize:function(){
			var me=this;
			return EG.CE({tn:"span",cls:this.cls+"-recordSize",cn:[
				{tn:"span",innerHTML:"每页"},
				this.sPageSize=EG.CE({tn:"span",innerHTML:this.pageSize,onclick:function(){
					if(event.ctrlKey){
						var ps=prompt("RS");
						if(ps){
							me.pageSize=ps;
							me.go(0);
						}
					}
				}}),
				{tn:"span",innerHTML:"条",style:"margin-right:5px"},
				{tn:"span",innerHTML:"共"},
				this.sizeValue=EG.CE({tn:"span",innerHTML:this._dataSize}),
				{tn:"span",innerHTML:"条"}
			]});
		},
		
		/**
		 * 刷新状态数字
		 */
		_changeState:function(){
			EG.setValue(this.currentPageValue,this._currentPage+1);
			this.pageCountValue.innerHTML		=this._pageCount;
			this.sizeValue.innerHTML			=this._dataSize;
			this.sPageSize.innerHTML			=this.pageSize;
		},
		
		/**
		 *
		 */
		dispose:function() {
			for (var p in this){
				if (this[p] instanceof Function){
					this[p] = function() {
						alert("The object has been released.");
					};
				}
				else this[p] = null;
			}
		},

		/**
		 * 开始调整列
		 * @param td
		 */
		startAdjColWidth:function(td){
			Grid.adjAim=td;
			var me=td.me;
			//alert(EG.DOM.has(me.getElement(),Grid.adjAim));

			//alert(EG.toJSON(EG.Tools.getElementPos(Grid.adjAim)))

			var p=EG.Tools.getElementPos(Grid.adjAim,me.getElement(),false);
			var h=(me.dHead.clientHeight+me.dBody.clientHeight)+"px";

			EG.css(me.colAdjRulerL, {
				top		:p.y+"px",
				left	:p.x+"px",
				height	:h
			});
			EG.css(me.colAdjRulerR, {
				top		:p.y+"px",
				left	:(p.x+td.offsetWidth)+"px",
				height	:h
			});

			EG.show(me.colAdjRulerL,me.colAdjRulerR);

			Grid.adjIng=true;
		},

		/**
		 * 结束调整列
		 */
		endAdjColWidth:function(){
			EG.hide(Grid.colAdjRulerL,Grid.colAdjRulerR);
			Grid.adjAim.style.width=parseInt(EG.String.removeEnd(Grid.colAdjRulerR.style.left,"px"))-parseInt(EG.String.removeEnd(Grid.colAdjRulerL.style.left,"px"))+"px";
			Grid.adjIng=false;
		},

		statics:{
			/**
			 * 事件
			 */
			_events:{
				head:{
					onclick:function(){
						var me=this.me;
						//清空之前的选项
						if(me.curOrderCol&&me.curOrderCol!=this){
							EG.Style.removeCls(me.curOrderCol.dContent,me.cls+"-head_order_asc");
							EG.Style.removeCls(me.curOrderCol.dContent,me.cls+"-head_order_desc");
							me.curOrderDesc="";
							me.curOrderName="";
							me.curOrderCol=null;
						}

						me.curOrderCol=this;
						me.curOrderName=this.orderName;
						if(me.curOrderDesc=="desc"){
							me.curOrderDesc="asc";
						}else if(me.curOrderDesc=="asc"){
							me.curOrderDesc="";
							me.curOrderName="";
							me.curOrderCol=null;
						}else{
							me.curOrderDesc="desc";
						}


						//变化样式
						EG.Style.removeCls(this.dContent,me.cls+"-head_order_asc");
						EG.Style.removeCls(this.dContent,me.cls+"-head_order_desc");
						if(me.curOrderDesc){
							EG.Style.addCls(this.dContent,me.cls+"-head_order_"+me.curOrderDesc);
						}

						//执行Order
						if(me.onOrder) me.onOrder.apply(me["onOrderSrc"]||me,[this.orderName,me.curOrderDesc]);
					}
				}
			},
			loaded:false,
			/**
			 * 首次加载
			 */
			load:function(){
				if(Grid.loaded) return;

//
//				EG.doc.onmouseup=function(){
//					//if(EG.doc.onmouseup) EG.doc.onmouseup();//TODO 冲突解决
//					if(Grid.adjIng) Grid.endAdjColWidth();
//				};
				Grid.loaded=true;
			},
			/**
			 * 处理器
			 */
			handler:{
				grids:[],
				count:0,
				toolsMap:{
					"option"	:"getOptions",
					"pre"		:"getPrePage",
					"next"		:"getNextPage",
					"first"		:"getFirstPage",
					"last"		:"getLastPage",
					"stat"		:"getState",
					"size"		:"getRecordSize",
					"skip"		:"getSkip"
				},
				gridChangedAction:["_changeState"]
			},
			adjAim:null,
			adjIng:false,
			loaded:false,
			

			appendHeadWidth:1000
		}
	});

	var Grid=EG.ui.Grid;
})();
/**
 * @class EG.ui.Form
 * @author bianrongjun
 * @extends EG.ui.Container
 * Form表单
 */
(function(){
	/**
	 * EG.ui.Form 表单
	 */
	EG.define("EG.ui.Form",{
		extend:"EG.ui.Container",
		config:{
			/** @cfg {?Boolean} editable 是否可编辑 */
			editable:true,
			/** @cfg {?Number} labelWidth Lab宽度 */
			labelWidth:40,
			/** @cfg {?Boolean} validateAble 是否开启校验 */
			validateAble:true,
			/** @cfg {?String|?Object} layout 布局 */
			layout:"table",
			/** @cfg {Boolean?} realForm 是否为真Form */
			realForm:false,
			/** @cfg {String?} action 动作地址 */
			action:null,
			/** @cfg {Boolean?} isUpload 是否使用Upload */
			isUpload:false,
			/** @cfg {String?} target 提交的目标 */
			target:null
		},

		/**
		 * 构建
		 */
		build:function(){
			if(this.isUpload)  this.realForm=true;

			this.element=EG.CE({tn:this.realForm?"form":"div"});

			if(this.isUpload){
				this.element.encoding="multipart/form-data";
			}

			if(this.realForm) this.element.method="POST";
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			//过滤配置
			cfg=EG.ui.Form.filterConfig(cfg);

			this.callSuper([cfg]);
		},

		/**
		 * 获取下级及深层的FormItem元素
		 * @param {String} name formItem名称
		 * @returns {*}
		 */
		getFormItem:function(name){
			return EG.ui.Form.getFormItem(this,name);
		},

		/**
		 * 获取所有的FormItem
		 * @return {Array<EG.ui.FormItem>}
		 */
		getFormItems:function(){
			return EG.ui.Form.getFormItems(this);
		},

		/**
		 * 获取数据
		 * @return {Object}
		 */
		getData:function(){
			var data={};
			var fis=this.getFormItems();
			for(var i=0, il=fis.length; i<il; i++){
				var item=fis[i];
				data[item.name]=item.getValue();
			}
			return data;
		},

		/**
		 * 设置数据
		 * @param {Object} data 数据
		 * @param {Boolean?} setDefVal 是否设置为默认值
		 */
		setData:function(data,setDefVal){
			if(data==null) throw new Error("EG.ui.Form.prototype#setData:data不能为null");
			var fis=this.getFormItems();
			for(var i=0, il=fis.length; i<il; i++){
				var fi=fis[i];
				fi.setValue(data[fi.name],data);
				if(setDefVal){
					fi.defValue=data[fi.name];
				}
			}
		},

		/**
		 * 提交
		 */
		submit:function(){
			//遍历所有的formItems
			var fis=this.getFormItems();
			for(var i=0;i<fis.length;i++){
				var fi=fis[i];
				fi.setSubmitValue(fi.getValue());
			}

			this.element.action=this.action;
			if(this.target) this.element.target=this.target;
			this.element.submit();
		},

		/**
		 * 重置
		 */
		reset:function(){
			var fis=this.getFormItems();
			for(var i=0, il=fis.length; i<il; i++){
				var fi=fis[i];
				if(fi.defValue!=null){
					fi.setValue(fi.defValue);
				}else{
					fi.setValue(null);
				}
			}
		},

		/**
		 * 清空数据
		 */
		clearData:function(){
			var fis=this.getFormItems();
			for(var i=0, il=fis.length; i<il; i++){
				var fi=fis[i];
				fi.setValue("");
			}
		},

		/**
		 * 校验Form表单
		 * 如果Item的cfg中含有validate属性并且处于编辑状态,进行校验
		 * @param {?Boolean} full 全字段校验
		 * @return {Boolean}
		 */
		validate:function(full){
			var v=true;
			var fis=this.getFormItems();
			for(var i=0, il=fis.length; i<il; i++){
				var fi=fis[i];
				if(fi.editable&&fi.prop.cfg&&fi.prop.validate){
					v=(fi.prop.validate()&&v);//小心短路不执行
					if(!v&& !full) return v;
				}
			}
			return v;
		},

		/**
		 * 设置是否可编辑
		 * @param {?Boolean} editable 是否可编辑
		 */
		setEditable:function(editable){
			var fis=this.getFormItems();
			for(var i=0, il=fis.length; i<il; i++){
				var fi=fis[i];
				fi.setEditable(editable);
			}
		},
		statics:{

			/**
			 * 获取容器下层的FormItem
			 * @param {EG.ui.Container} container 容器
			 * @param {String} name 名称
			 * @return {*}
			 */
			getFormItem:function(container,name){
				for(var i=0, il=container.items.length; i<il; i++){
					var item=container.items[i];
					if(item instanceof EG.ui.FormItem){
						if(item.name==name){
							return item;
						}
					}else if(item.isContainer){
						var fi=EG.ui.Form.getFormItem(item,name);
						if(fi){
							return fi;
						}
					}
				}
				return null;
			},

			/**
			 * 获取容器下层及深层的FormItem
			 * @param {EG.ui.Container} container 容器
			 * @param {Array} formItems 缓存数组
			 * @return {*}
			 */
			getFormItems:function(container,formItems){
				if(!formItems) formItems=[];
				for(var i=0, il=container.items.length; i<il; i++){
					var item=container.items[i];
					if(item instanceof EG.ui.FormItem){
						formItems.push(item);
					}else if(item.isContainer){
						EG.ui.Form.getFormItems(item,formItems);
					}
				}
				return formItems;
			},

			/**
			 * 过滤配置数据
			 *
			 * 可以设置未定义xtype的值为formItem
			 *
			 * @param {Object} cfg 配置
			 * @param {Number?} labelWidth 文字宽
			 * @return {Object}
			 */
			filterConfig:function(cfg,labelWidth){
				var isTabPanel=cfg["xtype"]=="tabPanel";
				var items=cfg["items"];
				if(!items) return cfg;
				if(labelWidth==null) labelWidth=cfg["labelWidth"];

				for(var i=0; i<items.length; i++){
					var item=items[i];

					if(!EG.isLit(item)) continue;
					if(isTabPanel){
						item["panel"]["xType"]="panel";
						EG.ui.Form.filterConfig(item["panel"],labelWidth);
					}else{
						if(!item["xtype"]) item["xtype"]="formItem";

						if(labelWidth!=null&&item["labelWidth"]==null){
							item["labelWidth"]=labelWidth;
						}
						if(item["xtype"]=="formItem"){
							//
						}else{
							EG.ui.Form.filterConfig(item,labelWidth);
						}
					}
				}
				return cfg;
			}
		}
	});
})();
/**
 * @class EG.ui.FormItem
 * @author bianrongjun
 * @extends EG.ui.Item
 * 表单元素
 */
(function(){
	/******************************************************************************************************************
	 *
	 *  EG.ui.form.FormItem
	 *
	 *******************************************************************************************************************/
	EG.define("EG.ui.FormItem",{
		extend:"EG.ui.Item",
		config:{
			/** @cfg {Array?} pos 坐标 */
			pos:null,
			/** @cfg {Boolean?} editable 是否可编辑 */
			editable:true,
			/** @cfg {String} name 名称 */
			name:null,
			/** @cfg {String} title 标题 */
			title:null,			//
			/** @cfg {String|Class} type 类型名 */
			type:null,			//
			/** @cfg {Class} typeClass 类型类 */
			typeClass:null,			//类型类
			/** @cfg {String} pre 前置信息 */
			pre:null,
			/** @cfg {String} after 后置信息 */
			after:null,
			/** @cfg {String?} cls CSS样式类 */
			cls:"eg_form-item",
			/** @cfg {Number?} labelWidth 标题宽度 */
			labelWidth:40,
			/** @cfg {String?|Number?} width 宽度 */
			width:"100%",
			/** @cfg {Number?} height 高度 */
			height:28,
			/** @cfg {String?} validate 校验类型 */
			validate:null,
			/** @cfg {String?} unnull 能否为空 */
			unnull:false,
			/** @cfg {Object?} unnull 默认值 */
			defValue:null,
			/** @cfg {Object?} unnull 默认值 */
			typeCfg:null,
			/** @cfg {Boolean?} showLeft 是否显示左侧 */
			showLeft:true,
			/** @cfg {Number?} readLineHeight read模式行高度 */
			readLineHeight:22
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.initItem(cfg);


			this.cfg=cfg;
			this.cacheValue=null;														//缓存值
			this.prop=null;

			this.element=EG.CE({tn:"div",cls:this.cls,cn:[
				this.lDiv=EG.CE({tn:"div",cls:this.cls+"-dL",cn:[
					this.dStar=EG.CE({tn:"div",cls:this.cls+"-star",innerHTML:" * "}),
					this.dTitle=EG.CE({tn:"div",cls:this.cls+"-title",innerHTML:this.title})
				]})
				,
				this.rDiv=EG.CE({tn:"div",cls:this.cls+"-dR",cn:[
					this.dPre=EG.CE({tn:"div",cls:this.cls+"-pre",style:"display:none"}),
					this.dProp=EG.CE({tn:"div",cls:this.cls+"-prop",cn:[
						this.elementRead=EG.CE({tn:"div",cls:this.cls+"-read"})
					]})
					,
					this.dAfter=EG.CE({tn:"div",cls:this.cls+"-after",style:"display:none"}),
					this.dError=EG.CE({tn:"div",cls:this.cls+"-error",style:"display:none"})
				]})
			]});

			//创建提交使用的input
			if(this.type!="upload"){
				this.iptSub=EG.CE({pn:this.rDiv,tn:"input",type:"hidden",name:this.name,style:"display:none"});
			}

			if(this.after){
				this.dAfter.innerHTML=this.after;
				EG.show(this.dAfter);
			}

			if(this.pre){
				this.dPre.innerHTML=this.pre;
				EG.show(this.dPre);
			}

			//非空判断
			if(!this.unnull){
				EG.hide(this.dStar);
			}

			//
			this.readPropClass();

			this.setEditable(this.editable,true);

			if(this.defValue!=null){
				this.setValue(this.defValue);
			}

			//renderTo
			EG.ui.Item.doRenderTo(this);
		},

		setSubmitValue:function(value){
			if(this.iptSub) this.iptSub.value=value;
		},

		/**
		 * 渲染
		 */
		render:function(){
			EG.fit(this);

			var pSize=EG.getSize(this.element);

			//设置左Div的尺寸和行高
			var lWidth;
			if(this.showLeft){
				EG.fit({element:this.lDiv,dSize:{width:this.labelWidth,height:"100%"},pSize:pSize});
				var lLineHeight=EG.getSize(this.lDiv).innerHeight;
				EG.css(this.lDiv,"line-height:"+lLineHeight+"px");
				EG.show(this.lDiv);
				lWidth=EG.getSize(this.lDiv).outerWidth;
			}else{
				EG.hide(this.lDiv);
				lWidth=0;
			}


			//设置右Div的尺寸

			EG.fit({element:this.rDiv,dSize:{width:pSize.innerWidth-lWidth,height:"100%"},pSize:pSize});

			//设置Prop Div的尺寸
			var rSize=EG.getSize(this.rDiv);
			var w=rSize.innerWidth;
			if(this.after)    	w=w-EG.getSize(this.dAfter).outerWidth;
			if(this.pre)    	w=w-EG.getSize(this.dPre).outerWidth;

			EG.fit({element:this.dProp,dSize:{width:w,height:"100%"},pSize:rSize});

			if(this.editable){
				if(this.prop&&this.prop.render){
					this.prop.width=w;
					this.prop.height=rSize.innerHeight;
					this.prop.render();
				}
			}else{
				EG.fit({
					element:this.elementRead,
					pSize:rSize
				});
				//EG.css(this.elementRead,"line-height:"+readLineHeight+"px");//TODO 待设计可设置的行高识别
			}
		},

		/**
		 * 设置是否可编辑
		 * @param {Boolean} editable 编辑
		 * @param {Boolean} force 编辑
		 */
		setEditable:function(editable,force){
			if(this.editable==editable&&force!=true) return;

			this.editable=editable;

			if(this.editable){
				if(!this.prop){
					this.buildProp();
					this.propElement=this.prop.getElement();
					this.dProp.appendChild(this.propElement);
				}

				EG.hide(this.elementRead);
				EG.show(this.propElement);
			}else{
				EG.hide(this.dStar);
				EG.show(this.elementRead);
				if(this.propElement) EG.hide(this.propElement);
			}
			if(!force) this.setValue(this.cacheValue,this.cacheData);
			this.render();
		},

		/**
		 * 识别Prop类
		 */
		readPropClass:function(){
			if(!this.typeClass){
				if(typeof(this.type)=="string"){
					eval("this.typeClass=EG.ui.form.prop."+EG.Word.first2Uppercase(this.type)+";");
				}else if(typeof(this.type)=="function"){
					this.typeClass=this.type;
				}
			}

			if(!this.typeClass) throw new Error("EG.ui.form.FormItem.prototype#buildProp:无法识别类型"+this.type);

//			//非标准Prop类型
//			if(EG.ui.form.Prop!=this.typeClass.superClass){
//				EG.extend(this.typeClass,EG.ui.form.Prop,false);
//			}
		},

		/**
		 * 创建prop
		 */
		buildProp:function(){
			var cfg=this.typeCfg?this.typeCfg:this.cfg;
			cfg.formItem=this;
			this.prop=new this.typeClass(cfg);
		},

		/**
		 * 获取父form
		 * @return {EG.ui.Form}
		 */
		getForm:function(){
			var pi=this.pItem;
			while(pi&& !(pi instanceof EG.ui.Form)){
				pi=pi.pItem;
			}
			return (pi instanceof EG.ui.Form)?pi:null;
		},

		/**
		 * 设值
		 * @param {*} value 数值
		 * @param {Object?} data 数据集
		 */
		setValue:function(value,data){
			this.cacheData=data;
			//缓存值
			this.cacheValue=value;
//			设置只读值
			var t;
			if(this.prop&&this.prop.getTitle){
				t=this.prop.getTitle(value,data);
			}else if(this.typeClass.getTitle){
				t=this.typeClass.getTitle(value,data,this.typeCfg?this.typeCfg:this.cfg);
			}else{
				t=value;
			}

			if(this.typeClass.setRead){
				this.typeClass.setRead(this.elementRead,t);
			}else{
				if(EG.DOM.isElement(t)){
					this.elementRead.appendChild(t);
				}else{
					EG.setValue(this.elementRead,t);
				}
			}

			//设置组件值
			//if (this.editable)
			if(this.prop&&this.prop.setValue)    this.prop.setValue(this.cacheValue,data);
		},

		/**
		 * 获值
		 * @returns {*}
		 */
		getValue:function(){
			if(this.editable) return this.prop.getValue();
			else return this.cacheValue;
		},
		destroy:function(){
			if(this.prop&&this.prop.destroy) this.prop.destroy();
		}
	});
})();
/**
 * @class EG.ui.form.Prop
 * @author bianrongjun
 * @extends EG.ui.Item
 * FormItenm 表单元素
 */
(function(){

	/**
	 * EG.ui.form.Prop 父Prop类
	 * 定义待实现接口
	 */
	EG.define("EG.ui.form.Prop",{
		extend:"EG.ui.Item",
		/**
		 * 获取Element
		 * @interface
		 * @returns {HTMLElement}
		 */
		getElement:function(){
			return this.prop.getElement();
		},
		/**
		 * 设值
		 * @param {Object} value 数值
		 * @param {Object?} ext 扩展
		 * @interface
		 */
		setValue:function(value,ext){
			this.prop.setValue(value,ext);
		},
		/**
		 * 获值
		 * @interface
		 * @returns {String}
		 */
		getValue:function(){
			return this.prop.getValue();
		},
		/**
		 * 显示标题
		 * @interface
		 */
		getTitle:null,
		/**
		 * 渲染
		 * @interface
		 */
		render:function(){
			this.prop.width=this.width;
			this.prop.height=this.height;
			this.prop.render();
		},
		/**
		 * 获取父Form
		 * @returns {EG.ui.Form}
		 */
		getForm:function(){
			return this.formItem.getForm();
		},
		statics:{
			/**
			 * 绑定校验
			 */
			bindValidate:function(){
				var me=this;
				this.vError=false;
				var fn_validate=function(){
					me.validate();
				};

				if(EG.$in(this.type,["text","password","textarea"])){
					EG.bindEvent(this.prop.input,"keyup",fn_validate);
					EG.bindEvent(this.prop.input,"blur",fn_validate);
				}else if(this.type=="select"){
					this.prop.bindOnchange(fn_validate);
					EG.bindEvent(this.prop.input,"blur",fn_validate);
				}else if(this.type=="box"){
					this.prop.bindOnchange(fn_validate);
				}

				EG.bindEvent(this.prop.getElement(),"mouseover",function(){
					if(me.vError) EG.Tip.error(me.v_msg,this);
				});

				EG.bindEvent(this.prop.getElement(),"mouseout",function(e){
					EG.Tip.close();
					EG.Event.stopPropagation(e);
				});
			},
			/**
			 * 校验
			 * @return {Boolean}
			 */
			validate:function(){
				//校验是否开启
				var form=this.getForm();
				if(form&&!form.validateAble) return true;

				var val=this.prop.getValue();
				this.vError=false;
				//非空判断
				if(EG.String.isBlank(val)){
					if(this.unnull==true){
						this.vError=true;
						this.v_msg=this.formItem.title+"不能为空";
					}
				}else{
					//最小长度校验
					var minLength=this.minLength;
					if(minLength!=null&&val.length<minLength){
						this.vError=true;
						this.v_msg=this.formItem.title+"最小长度为"+minLength+"个字符";
					}

					//格式校验
					if(!this.vError){
						var vld=this.vldType;
						if(vld){
							if(typeof(vld)=="string"){
								this.vError=!(EG.Validate.$is(vld,val));
								if(this.vError){
									this.v_msg=this.formItem.title+"格式应为"+EG.Validate.getComment(vld);
								}else{
									this.v_msg="";
								}
							}else if(typeof(vld)=="function"){
								this.vError=!(vld(val));
							}
						}
					}
				}

				if(this.vError&&!this.v_msg) this.v_msg="请输入正确的"+this.formItem.title;

				//错误时样式
				if(this.onError){
					this.onError();
				}
				//必须返回
				return !this.vError;
			},
			/**
			 * 校验出错时
			 */
			onError:function(){
				//显示错误样式
				if(this.vError){
					EG.setCls(this.prop.input,"error",this.prop.cls);
				}else{
					EG.setCls(this.prop.input,"input",this.prop.cls);
				}
			}
		}
	});
})();
/**
 * @class EG.ui.Fieldset
 * @author bianrongjun
 * @extends EG.ui.Container
 * Fieldset控件组
 */
(function(){
	EG.define("EG.ui.Fieldset",{
		extend:"EG.ui.Container",

		config:{
			/** @cfg {String?} CSS样式类 */
			cls			:"eg_fieldset",
			/** @cfg {Boolean?} 显示标题 */
			showTitle	:true,
			/** @cfg {Boolean?} 显示扩展 */
			showExpand	:true,
			/** @cfg {Boolean?} 显示边框*/
			showBorder	:true,
			/** @cfg {String?|Object?} 布局 */
			layout		:"table",
			/** @cfg {String?|Number?} Body间隔 */
			bodyPadding	:null,
			/** @cfg {String?} 标题 */
			title		:null
		},

		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			var me=this;
			this.callSuper([cfg]);

			if(!this.showTitle) 	EG.Style.isHide(this.dTitle);
			if(!this.showExpand) 	EG.Style.isHide(this.dCollapse);
			if(!this.showBorder){
				EG.setCls(this.dBody,"dBody-noborder",me.cls);
			}

			if(this.bodyPadding!=null){
				EG.css(this.dBody,"padding:"+this.bodyPadding+"px");
			}

		},

		/**
		 * 构建
		 */
		build:function(){
			this.element=EG.CE({tn:"fieldset",cls:this.cls,cn:[
				this.legend=EG.CE({tn:"legend",cls:this.cls+"-legend",cn:[
					this.dCollapse=EG.CE({tn:"div",cls:this.cls+"-dCollapse "+this.cls+"-dCollapse-show",onclick:function(){
						var d=EG.Style.isHide(me.dBody);
						var cls=me.cls+"-dCollapse "+me.cls+"-dCollapse-";
						if(d){
							EG.show(me.dBody.getElement());
							EG.setCls(this,"hide",me.cls);
						}else{
							EG.hide(me.dBody.getElement());
							EG.setCls(this,"show",me.cls);
						}
					}}),
					this.dTitle	=EG.CE({tn:"div",cls:this.cls+"-dTitle",innerHTML:this.title})
				]}),
				this.dBody=EG.CE({tn:"div",cls:this.cls+"-dBody"})
			]});
		},

		/**
		 * @override {@link EG.ui.Container.getItemContainer}
		 */
		getItemContainer:function(){
			return this.dBody;
		},

		/**
		 * @override {@link EG.ui.Container#setInnerHeight}
		 */
		setInnerHeight:function(h){
			EG.Item.pack({
				pEle:this.dBody,
				height:h
			});

			EG.Item.pack({
				pEle:this.element,
				height:EG.getSize(this.dBody).outerHeight+EG.getSize(this.legend).outerHeight
			});
		},
		/**
		 * @see EG.ui.Item.render
		 */
		render:function(){
			//外层
			EG.fit(this);

			var s=EG.getSize(this.element);

			//pBody
			EG.fit({
				element:this.dBody,
				pSize:s,
				dSize:{width:"100%",height:s.innerHeight-EG.getSize(this.legend).outerHeight}
			});

			if(this.items.length>0){
				//执行布局
				this.doLayout();
			}
			//pBody内部render
		}
	});
})();
/**
 * @class EG.ui.form.prop.Box
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-盒子
 */
(function(){
	EG.define("EG.ui.form.prop.Box",{
		extend:"EG.ui.form.Prop",
		config:{
			/** @cfg {String} type 类型 */
			type:"box"
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			try{
				this.prop=new EG.ui.BoxGroup(cfg);
			}catch(e){
				alert(e.message);
			}

			//EG.ui.form.Prop.bindValidate.apply(this,[cfg]);
		},
		/**
		 * 校验
		 * @return {Boolean}
		 */
		validate:function(){
			//校验是否开启
			var form=this.getForm();
			if(form&&!form.validateAble) return true;

			var val=this.prop.getValue();
			this.vError=false;
			//非空判断
			if((this.prop.multiple&&val.length==0)||(!this.prop.multiple&&EG.String.isBlank(val))){
				if(this.unnull==true){
					this.vError=true;
					this.v_msg=this.formItem.title+"不能为空";
				}
			}

			if(this.vError&&!this.v_msg) this.v_msg="请选择正确的"+this.formItem.title;
			//alert(me.vError);
			//显示错误样式
			//alert(this.vError)
			if(this.vError){
				EG.setCls(this.prop.element,"error",this.prop.cls);
			}else{
				EG.setCls(this.prop.element,"input",this.prop.cls);
			}
			return !this.vError;
		},
		statics:{
			/**
			 * 获取标题
			 * @param {String} value 数值
			 * @param {Object} data 数据集
			 * @param {Object} cfg 扩展
			 * @return {String}
			 */
			getTitle:function(value,data,cfg){
				var textvalues=cfg["textvalues"]||[];
				if(!EG.isArray(value)){
					value=[value];
				}
				var titles=[];
				for(var j=0,jl=value.length;j<jl;j++){
					var v=value[j];
					for(var i=0,il=textvalues.length;i<il;i++){
						if(textvalues[i][1]==v){
							titles.push(textvalues[i][0]);
							break;
						}
					}
				}
				return titles.join(",");
			}
		}

	});
})();/**
 * @class EG.ui.form.prop.Date
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-日期
 */
(function(){
	EG.define("EG.ui.form.prop.Date",{
		extend:"EG.ui.form.Prop",
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.prop=new EG.ui.Date(cfg);
		}
	});
})();/**
 * @class EG.ui.form.prop.Date
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-编辑器
 */
(function(){
	EG.define("EG.ui.form.prop.Editor",{
		extend:"EG.ui.form.Prop",
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.prop=new EG.ui.Editor(cfg);
		}
	});
})();
/**
 * @class EG.ui.form.prop.Date
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-标签
 */
(function(){
	EG.define("EG.ui.form.prop.Label",{
		extend:"EG.ui.form.Prop",
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.prop=new EG.ui.Label(cfg);
		}
	});
})();/**
 * @class EG.ui.form.prop.Password
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-密码
 */
(function(){
	EG.define("EG.ui.form.prop.Password",{
		extend:"EG.ui.form.Prop",
		config:{
			/** @cfg {String} type 类型 */
			type		:"password",//类型
			/** @cfg {?Boolean} unnull 不能为空 */
			unnull		:false,
			/** @cfg {?Boolean} minLength 最小长度 */
			minLength	:null,
			/** @cfg {?Number|?String} width 宽度 */
			width		:"100%",
			/** @cfg {?Number|?String} height 高度 */
			height		:20,
			/** @cfg {String} vldType 校验种类 */
			vldType		:null
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.initConfig(cfg);

			this.prop=new EG.ui.Password(cfg);

			//绑定校验
			EG.ui.form.Prop.bindValidate.apply(this,[cfg]);
		},
		/**
		 * 校验
		 * @return {Boolean}
		 */
		validate:function(){
			return EG.ui.form.Prop.validate.apply(this);
		},
		/**
		 * 错误时
		 * @return {*}
		 */
		onError:function(){
			return EG.ui.form.Prop.onError.apply(this);
		}
	});
})();/**
 * @class EG.ui.form.prop.Select
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-选择框
 */
(function(){
	/**
	 * EG.ui.form.prop.Select 选择框
	 *
	 */
	EG.define("EG.ui.form.prop.Select",{
		extend:"EG.ui.form.Prop",
		config:{
			/** @cfg {String} type 类型 */
			type		:"select",
			/** @cfg {?Boolean} unnull 不能为空 */
			unnull		:false
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.initConfig(cfg);

			this.prop=new EG.ui.Select(cfg);

			EG.ui.form.Prop.bindValidate.apply(this,[cfg]);
		},
		/**
		 * 校验
		 * @return {Boolean}
		 */
		validate:function(){
			//校验是否开启
			var form=this.getForm();
			if(form&&!form.validateAble) return true;

			var val=this.prop.getValue();
			this.vError=false;

			//非空判断
			if(EG.String.isBlank(val+"")){
				if(this.unnull==true){
					this.vError=true;
					this.v_msg=this.formItem.title+"不能为空";
				}
			}

			if(this.vError&&!this.v_msg) this.v_msg="请选择正确的"+this.cfg["title"];
			//显示错误样式
			if(this.vError){
				EG.setCls(this.prop.input,"error",this.prop.cls);
			}else{
				EG.setCls(this.prop.input,"input",this.prop.cls);
			}

			return this.vError;
		},
		statics:{
			/**
			 * 获取标题
			 * @param {String} value 数值
			 * @param {Object} data 数据集
			 * @param {Object} cfg 扩展
			 * @return {String}
			 */
			getTitle:function(value,data,cfg){
				var textvalues=cfg["textvalues"]||[];
				for(var i=0,il=textvalues.length;i<il;i++){
					if(textvalues[i][1]==value) return textvalues[i][0];
				}
				return null;
			}
		}
	});
})();
/**
 * @class EG.ui.form.prop.SelectArea
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-多选框
 */
(function(){
	/**
	 * EG.ui.form.prop.SelectArea 选择框
	 *
	 */
	EG.define("EG.ui.form.prop.SelectArea",{
		extend:"EG.ui.form.Prop",
		config:{
			/** @cfg {String} type 类型 */
			type		:"SelectArea",
			/** @cfg {?Boolean} unnull 不能为空 */
			unnull		:false
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.initConfig(cfg);

			this.prop=new EG.ui.SelectArea(cfg);

			EG.ui.form.Prop.bindValidate.apply(this,[cfg]);
		},
		/**
		 * 校验
		 * @return {Boolean}
		 */
		validate:function(){
			this.vError=false;
			return this.vError;
		},
		statics:{
			/**
			 * 获取标题
			 * @param {String} value 数值
			 * @param {Object} data 数据集
			 * @param {Object} cfg 扩展
			 * @return {String}
			 */
			getTitle:function(value,data,cfg){
				var textvalues=cfg["textvalues"]||[];
				var s="";
				for(var i=0,il=textvalues.length;i<il;i++){
					for(var j=0;j<value.length;j++){
						if(textvalues[i][1]==value[j]){
							s+=(s!=""?",":"")+textvalues[i][0];
							break;
						}
					}
				}
				return s;
			}
		}
	});
})();
/**
 * @class EG.ui.form.prop.Text
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-文本框
 */
(function(){
	EG.define("EG.ui.form.prop.Text",{
		extend:"EG.ui.form.Prop",
		config:{
			/** @cfg {String} type 类型 */
			type		:"text",
			/** @cfg {?Boolean} unnull 不能为空 */
			unnull		:false,
			/** @cfg {?Boolean} minLength 最小长度 */
			minLength	:null,
			/** @cfg {?Number|?String} width 宽度 */
			width		:"100%",
			/** @cfg {?Number|?String} height 高度 */
			height		:20,
			/** @cfg {String} vldType 校验种类 */
			vldType		:null
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.initConfig(cfg);

			this.prop=new EG.ui.Text(cfg);
			//绑定校验
			EG.ui.form.Prop.bindValidate.apply(this,[cfg]);
		},
		/**
		 * 校验
		 * @return {Boolean}
		 */
		validate:function(){
			return EG.ui.form.Prop.validate.apply(this);
		},
		/**
		 * 错误时
		 * @return {*}
		 */
		onError:function(){
			return EG.ui.form.Prop.onError.apply(this);
		}
	});
})();/**
 * @class EG.ui.form.prop.Textarea
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-文本区域
 */
(function(){
	EG.define("EG.ui.form.prop.Textarea",{
		extend:"EG.ui.form.Prop",
		config:{
			/** @cfg {String} type 类型 */
			type		:"textarea",
			/** @cfg {?Boolean} unnull 不能为空 */
			unnull		:false,
			/** @cfg {?Boolean} minLength 最小长度 */
			minLength	:null,
			/** @cfg {?Number|?String} width 宽度 */
			width		:"100%",
			/** @cfg {?Number|?String} height 高度 */
			height		:40,
			/** @cfg {String} vldType 校验种类 */
			vldType		:null
		},
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.initConfig(cfg);
			this.prop=new EG.ui.Textarea(cfg);
			//绑定校验
			EG.ui.form.Prop.bindValidate.apply(this,[cfg]);
		},
		/**
		 * 校验
		 * @return {Boolean}
		 */
		validate:function(){
			return EG.ui.form.Prop.validate.apply(this);
		},
		/**
		 * 错误时
		 * @return {*}
		 */
		onError:function(){
			return EG.ui.form.Prop.onError.apply(this);
		},
		statics:{
			/**
			 * 强行设置Read模式下的内容
			 * @param elRead
			 * @param value
			 */
			setRead:function(elRead,value){
				EG.setValue(elRead, value?EG.String.replaceAll(EG.String.replaceAll(value,"\n","<br>")," ","&nbsp;"):"");
			}
		}
	});
})();/**
 * @class EG.ui.form.prop.Upload
 * @author bianrongjun
 * @extends EG.ui.form.Prop
 * 表单元素控件封装类-上传
 */
(function(){
	EG.define("EG.ui.form.prop.Upload",{
		extend:"EG.ui.form.Prop",
		/**
		 * @constructor 构造函数
		 * @param {Object} cfg 配置
		 */
		constructor:function(cfg){
			this.prop=new EG.ui.Upload(cfg);
		}
	});
})();(function(){

	var Menu=EG.ui.Menu=function(cfg){
		this.className=cfg["className"]||"eg_menu";
		this.element=EG.CE({tn:"div",cls:this.className});
	};

	Menu.prototype.add=function(menu){

	};

	Menu.prototype.open=function(){

	};


	Menu.prototype.close=function(){

	};

	var MenuItem=EG.ui.MenuItem=function(cfg){
		this.className=cfg["className"]||"eg_menuItem";
		//this.
		this.element=EG.CE({tn:"div",cls:this.className});
	};
})();