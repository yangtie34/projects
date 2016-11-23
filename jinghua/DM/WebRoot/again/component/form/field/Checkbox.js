/**
 * @class NS.form.field.Checkbox
 * @extends NS.form.field.BaseField
 * 复选框组件
 */
NS.define('NS.form.field.Checkbox', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {String} boxLabel 复选框标签名
	 */
	/**
	 * @cfg {String/Number} inputValue 复选框实际值
	 */
	/**
	 * @cfg {Boolean} checked 是否被选中 默认false
	 */
	/**
	 * @cfg {Function} handler 调用的函数时，选中的值的变化（可以用来代替处理的变化事件）。包含两个属性checkbox 当前复选框 checked 新选中的复选框 
	 */
	/**
	 * @cfg {String} uncheckedValue 如果配置的话，这将被提交复选框的值如果复选框是选中的，在形式提交。默认情况下，这是不确定的，从而导致没有提交提交表单时（HTML复选框的默认行为）复选框字段。
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
		this.component = Ext.create('Ext.form.field.Checkbox', config);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			inputValue:true,
			boxLabel:true,
			checked:true,
			draggable:true,
			handler:true,
			invalidText:true,
			readOnly:true,
			uncheckedValue:true,
			validateOnBlur:true,
			validateOnChange:true
		});
	},
	/**
	 * 
	 * @param {Number/String/Object} width 宽度 为Ojbect时应如：{wdith:wValue,height:hValue},为undefeind/无参数时大小不更改
	 * @param {Number/String} height
	 * @return {Ext.component} 返回this
	 */
	setSize:function(width,height ){
		return this.component.setSize(width,height);
	},
    /**
     * 获取实际值的方法
     * @return {*}
     */
    getInputValue : function(){
        return this.component.getSubmitValue();
    }
});