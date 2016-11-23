/***
 * @class NS.grid.plugin.CellEditor
 * @extends NS.Plugin
 *    {NS.grid.Grid}的插件
 * @author wangyongtai
 *
 *      var grid = NS.component.grid.Grid({
 *          plugins : [new NS.grid.plugin.CellEditor()]
 *      });
 */
NS.define('NS.grid.plugin.CellEditor',{
	/**
	 * 构造函数
	 * @param config 配置参数
	 */
	constructor : function(config){
		this.component = Ext.create('Ext.grid.plugin.CellEditing',config||{clicksToEdit: 2});
	},
	/***
	 * 获取底层类库组件
	 * @return
	 */
	getLibComponent : function(){
		return this.component;
	}
});