/**
 * @class US.Connection
 * @description 用于进行Ajax请求数据 Conn为其一个实例
 */
NS.define('US.Connection', {
			/**
			 * @description 初始化构造函数
			 */
			constructor : function() {
				this.initComponent(arguments[0]);// 初始化数据
			},
			/**
			 * 初始化组件
			 * 
			 * @param config
			 *            配置参数
			 */
			initComponent : function(config) {
				this.connection = Ext.create('Ext.data.Connection', config);
			},
			/**
			 * @description 请求数据
			 * @param config
			 *            配置请求
			 */
			request : function(config) {
				config.url = config.url||this.baseUrl;//设置基本请求路径
				config.params = config.params || {};
				config.method = 'post';
				this.connection.request(config);
			},
			baseUrl : 'baseAction!queryByComponents.action',// 基础请求url
			/**
			 * @description 用于获取同步加载数据
			 * @param config 请求配置参数
			 */
			syncGetData : function(config) {
				var me = this;
				config.params = config.params || {};
				config.params['requestType'] = 'query';
				me.connection.request({
							url : this.baseUrl,
							params : config.params,
							method : 'POST',
							async : false,
							success : function(response) {
								// alert(response.responseText);
								me.dataJson = Ext.JSON.decode(
										response.responseText, true);
							},
							failure : function() {
								me.dataJson = Ext.JSON.decode(
										response.responseText, true);
								Ext.Msg.alert('警告', '数据请求失败！');
							}
						});
				return me.dataJson;
			}
		});
/**
 * @description 一个US.Connection 的实例
 * */
Conn = new US.Connection();// connection的一个实例化对象
