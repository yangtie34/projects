/**
 * @class Ext.ux.TabPages
 * @extends Ext.tab.Panel
 * 切换页类
 */
Ext.define('Ext.ux.TabPages',{
	extend:'Ext.tab.Panel',
	border:false,
	margin:10,
	padding:10,
	initComponent:function(){
		this.callParent();
		var defaultShowName = this.initialConfig.defaultShowName;//配置的默认显示的值
		console.log(this);
		if(defaultShowName){
			var obj = this.getTabPanelByName(defaultShowName);
			if(null==obj){
				throw this['$className']+'obj is not define!';
			}else{
				obj.component = obj.init();
				obj._init = true;
				obj.add(obj.component);
				this.setActiveTab(obj);
			}
		}else{
			try{
				var obj = this.items.items[0];
				obj.component = obj.init();
				obj._init = true;
				obj.add(obj.component);
				this.setActiveTab(obj);
			}catch(e){
				throw e+' items is null!!';
			}
		}
		this.addListener('tabchange',function(tabPanel,newCard,oldCard,eOpts){
			if(!newCard._init){
				newCard.component = newCard.init();
				newCard.add(newCard.component);
				newCard._init = true;
			}
	   });
	},
	/**
	 * 通过名称显示这个组件
	 * @param {String} name
	 * @param {Function} callback 回调参数为用户自定义init里的返回参数
	 */
	showComponent:function(name,callback){
		var obj = this.getTabPanelByName(name);
		if(obj!=null){
			if(obj._init){
				this.setActiveTab(obj);
			}else{
				obj.component = obj.init();
				obj._init = true;
				obj.add(obj.component);
				this.setActiveTab(obj);
			}
			callback(obj.component);
		}else{
			throw 'com is not find!';
		}
	},
	/**
	 * 通过名称查找用户自定义的组件
	 * @return
	 */
	getComByName:function(name){
		var obj = this.getTabPanelByName(name);
		if(null==obj){
			throw this['$className']+'obj is not define!';
		}else{
			obj.component = obj.init();
		}
		com = obj.component;
		return com;
	},
	/**
	 * 通过名称查找tabPanel
	 * @private 
	 * @param {查找的名称} name
	 * @return {Ext.tab.Panel}
	 */
	getTabPanelByName:function(name){
		var page = this.items.items,obj=null;
		try{
			for(var i=0,len=page.length;i<len;i++){
				if(name==page[i].name){
					obj = page[i];
					break;
				}
			}
		}catch(e){
			throw e.message+this['$className']+',name is undefined';
		}
		return obj;
	}
});