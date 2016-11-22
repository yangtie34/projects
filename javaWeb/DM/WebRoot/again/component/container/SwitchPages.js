/**
 * 切换页类
 */
NS.define('NS.ux.SwitchPages',{
	extend:'NS.container.Panel',
	initComponent:function(cfg){
		this.component = Ext.create('Ext.ux.SwitchPages',cfg);
	},
	/**
	 * 显示某个指定的组件
	 * @param {String} obj
	 * @param {Function} callback 回调函数,参数为传递来的对象
	 */
	showComponent:function(name,callback){
		this.component.showComponent(name,callback);
	},
	/**
	 * 添加对象或数组
	 * @param {Object/Array} items
	 */
	add:function(items){
		this.component.add(items);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			renderTo:true,
			name:true,
			init:true,
			items:true//持续更新中...
		});
	}
});