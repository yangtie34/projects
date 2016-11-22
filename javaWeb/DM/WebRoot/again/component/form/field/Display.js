/**
 * @class NS.form.field.Display
 * @extends NS.form.field.BaseField
 * 仅作为值显示的组件
 */
NS.define('NS.form.field.Display', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} htmlEncode 当setfalse跳过HTML编码文本渲染它时。这可能是有用的，如果你要包括在该领域的innerHTML的标签，而不是使他们作为默认逻辑的字符串.
	 */
	/**
	 * @cfg {Number} saveDelay 延迟加载 默认100
	 */
	/**
	 * @cfg {Boolean} validateOnBlur 该字段是否应向验证失去焦点时。这将导致作为用户验证步骤通过表格中的字段的字段，而不管它们是否正在改变前进的道路上的那些字段。另见validateOnChange 默认true
	 */
	/**
	 * @cfg {Boolean} validateOnChange 该字段是否应向验证whSpecifies，这一领域是否应立即进行验证时，发现其价值的变化。如果验证结果字段的有效性的变化，一个validitychange事件将被解雇。这允许字段用户键入立即显示其内容的有效性的反馈意见。当设置为false，反馈不会立竿见影。但形式仍然会被验证，然后再提交如果的clientValidation的Ext.form.Basic.doAction选项被启用，如果字段或形式手动验证。默认true
	 */
	/**
	 * @private
	 * @param config
	 */
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.field.Display', config);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			disabled:true,
			htmlEncode:true,
			readOnly:true,
			saveDelay:true,
			validateOnBlur:true,
			validateOnChange:true
		});
	}
});
