NS.define('Output.View', {
	/***************************************************************************
	 * 启动函数
	 */
	constructor : function() {

	},
	/***************************************************************************
	 * 获取模版组装器
	 */
	createPage : function(body) {
		this.component = Ext.create('Ext.container.Container', {
			autoScroll : true,
			layout : 'fit',
			style : {
				backgroundColor : '#deecfd'
			},
			items : body,
			listeners : {
//				'beforerender' : function() {
//					var window = this.findParentByType('window');
//					if (window) {
//						window.addListener('beforehide', function() {
//									var charts = this.query('outputChart');
//									for (var i = 0, len = charts.length; i < len; i++) {
//										var chart = charts[i];
//										chart.fireEvent('windowhide');
//									}
//								}, this);
//						window.addListener('beforeshow', function() {
//									var charts = this.query('outputChart');
//									for (var i = 0, len = charts.length; i < len; i++) {
//										var chart = charts[i];
//										chart.fireEvent('windowshow');
//									}
//								}, this);
//					}
//				},
				'resize' : function() {
					//在系统界面时
					var window = this.findParentByType('window');
					//在传统界面时
					var tabPanel = this.findParentByType('tabpanel');
					//在普通界面时
					var panel = this.findParentByType('panel');
					if (window || tabPanel || panel) {
						var charts = this.query('outputChart');
						for (var i = 0, len = charts.length; i < len; i++) {
							var chart = charts[i];
							chart.fireEvent('windowlayout');
						}
					}

				}
			}
		});
	},
	/***************************************************************************
	 * 返回用于和底层类系统交互的组件
	 * 
	 * @return {}
	 */
	getLibComponent : function() {
		return this.component;
	}
});