NS.define('NS.form.CheckboxGroup', {
	extend : 'NS.container.Container',
	/**
	 * 初始化配置参数
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			items:true
		});
	},
	/**
	 * 初始化组件
	 * @param cfg
	 */
	initComponent:function(cfg){
		this.component = Ext.create('Ext.form.CheckboxGroup',cfg);
	},
    /**
     * 初始化事件(暂不知父类都支持哪些,如果使用者使用某监听不存在的话可自行维护添加)
     */	
	initEvents:function(){
		this.callParent();
	}
});