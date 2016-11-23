/*******************************************************************************
 * 定义四分平模版 数据格式参照经典模版
 */
NS.define('Output.SingleTemplate', {
	        /***
	         * 初始化构造函数
	         * @param {} config 配置参数
	         */
            constructor : function(config) {
				 config.autoScroll = true;
				 config.style = {
							backgroundColor : 'white'
						};
				config.layout  = 'fit';
//				config.baseCls = 'classTemplate';
//				var container = new Ext.container.Container(config);
//				this.component = Ext.create('Ext.container.Container', {
//							items : container,
//							layout : 'fit'
//						});
				this.component = new Ext.container.Container(config);
			},
			/*******************************************************************
			 * 获取封装组件
			 */
			getLibComponent : function() {
				return this.component;
			},
			render : function(id) {
				this.component.render(id);
			}
		});