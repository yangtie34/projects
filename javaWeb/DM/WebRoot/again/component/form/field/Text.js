/**
 * @class NS.form.field.Text
 * @extends NS.form.field.BaseField
 */
NS.define('NS.form.field.Text', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} grow 是否允许自增长和收缩其内容 默认false
	 */
	/**
	 * @cfg {String} growAppend 一个字符串，将被追加到字段的当前值为计算目标字段大小的目的。仅用于配置成长是真实的。默认为一个大写的“W”（常见的字体中最宽字符），留出足够的空间，为下键入的字符，避免转移前的宽度字段值进行调整。
	 */
	/**
	 * @cfg {Number} growMax 允许自增长的最大宽度，当grow为true时生效
	 */
	/**
	 * @cfg {Number} growMin 允许自增长的最小宽度，当grow为true时生效 
	 */
	initComponent:function(cfg){
		this.component = Ext.create('Ext.form.field.Text',cfg);
		if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
	},
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			allowBlank:true,
			blankText:true,
			editable:true,
			emptyText:true,
            inputType : true,
			disabled:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			maxText:true,
			maxValue:true,
			minText:true,
			minValue:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			vtype:true,
			vtypeText:true
		});
	}
});		