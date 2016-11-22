/**
 * @class NS.form.field.TextArea
 * @extends NS.form.field.BaseField 文字域组件
 */
NS.define('NS.form.field.TextArea', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} grow 是否允许自增长和收缩其内容 默认false
	 */
	/**
	 * @cfg {String} growAppend 一个字符串，将被追加到字段的当前值为计算目标字段大小的目的。仅用于配置成长是真实的。默认为一个大写的“W”（常见的字体中最宽字符），留出足够的空间，为下键入的字符，避免转移前的宽度字段值进行调整。
	 */
	/**
	 * @cfg {Number} growMax  允许自增长的最大宽度，当grow为true时生效
	 */
	/**
	 * @cfg {Number} growMin 允许自增长的最小宽度，当grow为true时生效
	 */
	/**
	 * @private
	 * @param cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.form.field.TextArea', cfg);
		//if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
		//文本域暂不添加回车事件,因为它本身的回车应该是换行,如有需求会稍做更改，比如Ctrl+Enter的的形式
	},
	/**
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			allowBlank:true,
			blankText:true,
			emptyText:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			vtype:true,
			vtypeText:true
		});
	},
	/**
	 * 中心在其容器组件(Center this Component in its container.)
	 * 
	 * @return {Ext.Component} this
	 */
	center : function() {
		return this.component.center();
	},
	/**
	 * 启用组件
	 * 
	 * @param {Boolean}
	 *            silent 默认false
	 */
	enable : function(silent) {
		this.component.enable(silent);
	},
	/**
	 * 设置这个filed的默认参数值
	 * 
	 * @param {Object}
	 *            defaults
	 */
	setFieldDefaults : function(defaults) {
		this.component.setFieldDefaults(defaults);
	},
	/**
	 * 隐藏该组件
	 * 
	 * @param {String/Ext.Element/Ext.Component}
	 *            animateTarget
	 * @param {Function}
	 *            callback
	 * @param {Object}
	 *            scope
	 */
	hide : function(animateTarget, callback, scope) {
		this.component.hide(animateTarget, callback, scope);
	},
	/**
	 * 重置（还原）该组件的值
	 */
	reset : function() {
		this.component.reset();
	}
// initValue、initConfig...等几个应该有用,不过暂不加入了.
});
