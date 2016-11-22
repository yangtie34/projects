/**
 * 原生Ext扩展插件
 * @private
 */
Ext.define('Ext.ux.comboBoxTree',{
	extend:'Ext.form.field.Picker',
    enableKeyEvents : true,
	//width:210,//默认宽度210
	/**
	 * 重写Ext.form.field.Picker的initComponent方法
	 */
	initComponent:function(){
		this.callParent(arguments);
		this.addEvents('treeselect', 'selectcheck');
		var cfg = this.initialConfig;

		var rt=false,
		    ex=true,
		    ro=cfg.rootName,
		    tr=cfg.treeData,
		    he=cfg.height,
		    width = cfg.width||210;
			var pickerWidth = cfg.pickerWidth||0;
	    
		if(cfg.rootVisible==true){rt=true;}
		if(cfg.expanded==false){ex=false;}
		var rootBasic = {//Store的默认配置参数
				expanded : ex,// expanded 默认true 展开
				text : ro||'根',  // 根节点名称
				children : tr||[]// 树形数据
			},treeBasic = {  //treePanel的默认配置参数
				rootVisible : rt,//默认跟节点不显示
				height : he||300//默认高度300
			};
		
		//移除非picker所需的属性
		if(tr)delete cfg.treeData;
		if(rt)delete cfg.rootVisible;
		if(ex)delete cfg.expanded;
		if(ro)delete cfg.rootName;
		if(he)delete cfg.height;
		
		var comBasic = {//组件基础配置
				isLeafSelect:true,//是否允许选择叶子节点
				notLeafInfo:'此节点已设置为不可选,可更改isLeafSelect为true时可选',
			    isParentSelect:true,//是否允许选择父节点
			    notParentInfo:'此节点已设置为不可选,可更改isParentSelect为true时可选',
				editable : false//这里默认不可编辑
		};
		NS.apply(comBasic,cfg);
		//将转换后的值赋予root\tree属性,传递给ext原生对象
		
		var rootBasic_ ={
		     expanded: true,
             children:[]
		}; 

        var treeBasic_ = {
        		rootVisible:false,
				floating : true,
				height:300,//tree默认高度300
				autoScroll:true,
				focusOnToFront : false,
				useArrows : true
		};
        Ext.apply(rootBasic_,rootBasic);
		var self=this,store = this.__store =  Ext.create('Ext.data.TreeStore', {
			root: rootBasic_
		});
		//this.__store = Ext.clone(store);
        Ext.apply(treeBasic_,treeBasic);
        treeBasic_.store =  store;
        Ext.apply(comBasic,treeBasic_);

		this.picker = Ext.create('Ext.tree.Panel', comBasic);
		
		//此处为了解决按键时picker弹出,具体加该事件的用处,此事不知晓,先行隐藏(修改人:hanqing)
		/*this.on('keydown',function(picker,event){
            var he = event.getTarget();
            var el = Ext.fly(he);
            this.setValue(el.getAttribute('value'));
            this.expand();
        },this);*/
		
        this.on('blur',function(picker,event){
            this.setValue(this.getValue());
        },this);
        
        this.on('expand',function(){
        	if(pickerWidth!=0){
        		this.picker.setWidth(pickerWidth);//重新设置宽度
        	}
        },this);
        
		this.picker.on({
			checkchange : function(node,check) {
				self.checkedAllChildren(node,check);//设置子节点的状态
				
				//向上父节点
				var parent = node.parentNode;
				if(check == true){
				   while(parent){
					   parent.set('checked',true);
					   parent = parent.parentNode;
				   }
				}else if(check == false){
				   while(parent){
					   var flag = self.judgeHasChildChecked(node,parent);
					   if(flag == true){
						   break;
					   }else if(flag == false){
						   parent.set('checked',false);
						   parent = parent.parentNode;
						   //到root时break;
					   }
				   }
				}
				var records = self.picker.getView().getChecked(),values = [];
				Ext.Array.each(records, function(rec) {
						values.push(rec.get('id'));
				});
				self.setValue(values.join(','));// 实际值
				self.fireEvent('selectcheck', node);
			},
			itemclick : function(view, record) {
				
				
				
				if(record.isLeaf()){//如果是叶子节点并且叶子节点不可选择
					if(comBasic.isLeafSelect){
					}else{
						Ext.Msg.alert("提示",comBasic.notLeafInfo+"!");
						return;
					}
				}
				if(!record.isLeaf()){//如果是父节点,且父节点不可选
					if(comBasic.isParentSelect){
					}else{
                        Ext.Msg.alert("提示",comBasic.notParentInfo+"!");
						return;
					}
				}
				if(record.get('checked')==undefined){
					self.setValue(record.get('id'));// 显示值
					self.collapse();
				}else{
				    record.set('checked',!record.get('checked'));
				    this.fireEvent('checkchange',record,record.get('checked'));
				}
				self.fireEvent('treeselect', record);
			}
		});
	},
	/**
	 * 特殊方法扩展
	 * @param value 该node对应的值
	 */
	getCurrentNodeRaw:function(value){
		var node = this.picker.getStore().getNodeById(value);
		if(node) return node['raw'];
		return {};
	},
	/**
	 * 选中所有子节点以及子节点的子节点
	 * @param node
	 * @private
	 */
	checkedAllChildren : function(node,check){
		var childs = node.childNodes;
		var itertor = childs;// 需要迭代的节点
		//向下子节点
		if(itertor&&itertor.length>0){
			var needToIter = [];// 待被迭代的节点
			while (itertor.length != 0) {
				for (var i = 0, len = itertor.length; i < len; i++) {
					var c = itertor[i];// 获取子节点
					c.set('checked', check);// 设置该节点被选择
					if (c.childNodes)
						needToIter = needToIter.concat(c.childNodes);
				}
				itertor = needToIter;
				needToIter = [];
			}
		}
	},
	/**
	 * 判断当前节点是否有子节点被选中
	 * @param node
	 * @private
	 */
	judgeHasChildChecked : function(node,parent){
		//这里需要检测在同一节点里是否有已经被选中的值,如果有则break;
		var flag = false;
		if (parent.parentNode) {
				var c = parent;// 获取子节点
				var it_ = c.childNodes;
				for(var i=0,len=it_.length;i<len;i++){
					if(it_[i].get('checked')==true){
						flag=true;
						break;
					}
				}
		}
		return flag;
	},
	
	/**
	 * 得到picker
	 * @return {Ext.tree.Panel}
	 */
	getPicker: function() {
		var me = this;
		me.picker.ownerCt =  me.ownerCt;//添加了ownerCt属性.
		return me.picker || (me.picker = me.createPicker());
	},
	/**
	 * 设置值
	 * @param {Array/String/Number} id
	 */
	setValue:function(id){
		if (id==null||typeof id== 'undefined') {
			this.values = null;
			this.setRawValue('');
			return;
		}
		var array = id.toString().split(',');
		var names = [], values = [], store = this.picker.getStore();
		for (var i = 0, len = array.length; i < len; i++) {
			var _id = array[i];
			var node = store.getNodeById(_id);
			
			if (node) {
				if(node.get('id')!='root'){//根节点root取消
					names.push(node.get('text'));
					values.push(node.get('id'));
					if(typeof node.get('checked')!='undefined'&&node.get('checked')!=null){
					   node.set('checked',true);
					   node.commit(true);
					}
				}
			}
		}
		this.values = values.join(',');
		var text = this.text = names.join(',');
		this.setRawValue(text);
	},
	/**
	 * 得到值
	 * @return {String} ids 值集合
	 */
	getValue:function(){
	    return this.values;
	},
	/**
	 * 提供的提交时调用的方法
	 * @return
	 */
	getSubmitValue : function(){
		return this.values;
	},
    setRawValue : function(text){
        this.texts = text;
        this.callParent([text]);
    },
	/**
	 * 得到显示值
	 * @return {String}
	 */
	getRawValue:function(){
		return this.texts||'';
	},
	reset:function(){
		this.callParent();//先重置
		var store = this.picker.getStore();
		var node = store.getRootNode();//得到根节点,从根节点开始遍历将cheked置为false
		if(typeof node.get('checked')!='undefined'&&node.get('checked')!=null){
			this.checkedAllChildren(node,false);//将checked全部置为false
		}
		//以下是源码
		this.setValue(this.originalValue);
		this.clearInvalid();
		// delete here so we reset back to the original state
		delete this.wasValid;
		//this.setValue(this.values);//再将之前value属性的值还原
		//以上的实现算是真正实现了组件自身的reset方式.重置是还原其第一次展现的状态,并不是清空所有值
	},
	loadData : function(data){
		var store = this.picker.getStore();
		store.removeAll();
		store.setRootNode({expanded: true, text:'根', children:data});
	}
//	isValid:function(){
//		var me=this;
//		isValid = me.disabled || Ext.isEmpty(me.getErrors());
//		var allowBlank = this.picker.allowBlank;
//		if(typeof allowBlank =='undefined'||allowBlank==true){//如果可以为空
//			isValid = true;
//		}else{//如果不可为空
//			if((this.values!=null&&this.values!='')){//不空
//				isValid = true;
//			}else{
//				isValid = false;
//			}
//		}
//		return isValid;
//	},
//	validate : function() {
//	    var me = this,
//	        isValid = me.isValid();
//	    if (isValid !== me.wasValid) {
//	          me.wasValid = isValid;
//	          me.fireEvent('validitychange', me, isValid);
//	    }
//
//	    return isValid;
//	 }
});