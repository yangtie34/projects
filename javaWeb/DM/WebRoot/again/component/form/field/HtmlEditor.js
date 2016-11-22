/**
 * @class NS.form.field.HtmlEditor
 * @extends NS.Component field的基类
 */
NS.define('NS.form.field.HtmlEditor', {
	extend : 'NS.form.field.BaseField',
	
	initComponent:function(config){
		this.component = Ext.create('Ext.form.HtmlEditor',config);
//		if(config.enterFn) this.doEnterFn(config.enterFn);
	},
	
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			
			frame : true
		});
	}
	
	
})