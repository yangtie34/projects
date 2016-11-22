/**
 * @class NS.button.Download
 * @extends NS.button.Button
 * 下载组件
 */
NS.define('NS.button.Download', {
	extend : 'NS.button.Button',
	initComponent : function(cfg) {
		var me = this,basic = {
			text:'导出'
		};
		Ext.apply(basic,cfg);
		
		me.component = Ext.create('Ext.button.Button',basic);
		me.component.addListener('click',function(){
			//加入相应条件/当前结果集/全部结果集
			//通过条件解析（分字段）--实体属性表
			//另一种表单
			//向后台发送的链接
			//me.show();
			alert('---');
		});
	}
});