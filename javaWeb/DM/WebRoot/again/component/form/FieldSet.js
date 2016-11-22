/**
 * @class NS.form.FieldSet
 * @extends NS.container.Container
 */
NS.define('NS.form.FieldSet', {
	extend : 'NS.container.Container',
	initData : NS.emptyFn,
    /**
     * @cfg title fieldset显示的标题
     */
	/***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
        	checkboxName : true,
        	checkboxToggle : true,
        	collapsed : true,
        	collapsible : true,
        	focusOnToFront : true,
        	saveDelay : true,
        	shadow : true,
        	stateEvents : true,
        	stateful : true,
        	title : true
        });
    },
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.FieldSet', config);
	},
	initEvents:function(){
		this.callParent();
	}
});