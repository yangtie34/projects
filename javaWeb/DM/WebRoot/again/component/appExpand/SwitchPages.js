/**
 * @class Ext.ux.SwitchPages
 * @extends Ext.panel.Panel
 * 切换页类
 */
Ext.define('Ext.ux.SwitchPages',{
	extend:'Ext.panel.Panel',
	border:false,
	margin:10,
	layout:'card',
	padding:10,
	/**
	 * 通过名称获取维护在切页容器里的对象
	 * @param name
	 * @return
	 */
	getComByName:function(name){
		var items = this.items.items,obj=null;
		for(var i=0,len=items.length;i<len;i++){
			if(name==items[i].name){
				obj = items[i];break;
			}
		}
		if(null==obj){
			obj = items[0];//如果找不到,默认第一个
			throw this['$className']+'component is not find !';
		}
		return obj;
	},
	initComponent:function(){
		this.callParent();
		//初始化pages第一个组件（回头添加默认的name显示）
		try{
			var defaultShowName = this.initialConfig.defaultShowName;//配置的默认显示的值
			if(defaultShowName){
				this.showComponent(defaultShowName);
			}else{
				var obj = this.items.items[0];
				obj._init= true;
				obj.component = obj.init();
				obj.add(obj.component);
				this.getLayout().setActiveItem(obj);
			}
		}catch(e){
			throw e;
		}
	},
	/**
	 * 显示某个指定的组件
	 * @param {String} obj
	 * @param {Function} callBack
	 */
	showComponent:function(name,callBack){
		var obj = this.getComByName(name);
		if(obj._init){
			this.getLayout().setActiveItem(obj);
		}else{
			obj.component = obj.init();
			obj.add(obj.component);
			obj._init = true;
			this.getLayout().setActiveItem(obj);
		}
		if(callBack){
			callBack(obj.component);
		}
	}
});