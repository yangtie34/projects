/**
 * @class NS.form.field.Radio
 * @extends NS.form.field.BaseField 单选按钮组件
 */
NS.define('NS.form.field.Radio', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {} boxLabel 复选框标签名
	 */
	/**
	 * @cfg {} inputValue 复选框实际值
	 */
	/**
	 * @cfg {Boolean} checked 是否被选中 默认false
	 */
	/**
	 * @cfg {} uncheckedValue 如果配置的话，这将被提交复选框的值如果复选框是选中的，在形式提交。默认情况下，这是不确定的，从而导致没有提交提交表单时（HTML复选框的默认行为）复选框字段。
	 */
	/**
	 * @cfg {} validateOnBlur validateOnBlur 该字段是否应向验证失去焦点时。这将导致作为用户验证步骤通过表格中的字段的字段，而不管它们是否正在改变前进的道路上的那些字段。另见validateOnChange 默认true
	 */
	/**
	 * @cfg {} validateOnChange 该字段是否应向验证whSpecifies，这一领域是否应立即进行验证时，发现其价值的变化。如果验证结果字段的有效性的变化，一个validitychange事件将被解雇。这允许字段用户键入立即显示其内容的有效性的反馈意见。当设置为false，反馈不会立竿见影。但形式仍然会被验证，然后再提交如果的clientValidation的Ext.form.Basic.doAction选项被启用，如果字段或形式手动验证。默认true
	 */
	/**
	 * @cfg {} handler 调用的函数时，选中的值的变化（可以用来代替处理的变化事件）。包含两个属性checkbox 当前复选框 checked 新选中的复选框
	 */
	/**
	 * @private
	 * @param config
	 */
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.field.Radio', config);
	},
	/**
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			boxLabel:true,
			inputValue:true,
			checked:true,
			disabled:true,
			readOnly:true,
			uncheckedValue:true,
			validateOnBlur:true,
			validateOnChange:true,
			handler:true,
			invalidText:true
		});
	},
	/**
	 * 如果该组件是单选组队一部分，则返回被选中的值
	 * 
	 * @return {String}
	 */
	getGroupValue : function() {
		return this.component.getGroupValue();
	},
	/**
	 * 重置该组件的值
	 */
	reset : function() {
		this.component.reset();
	}
});
