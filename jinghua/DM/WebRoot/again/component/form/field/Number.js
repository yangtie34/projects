/**
 * @class NS.form.field.Number
 * @extends NS.form.field.BaseField
 * 数字组件
 */
NS.define('NS.form.field.Number', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} hideTrigger 隐藏filed的 增长和减少键
	 */
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
	/**
	 * @cfg {String} maxText 最大值验证失败时的文本信息。
	 */
	/**
	 * @cfg {Number} maxValue 最大值
	 */
	/**
	 * @cfg {String} minText 最小值验证失败时的文本信息
	 */
	/**
	 * @cfg {Number} minValue 最小值
	 */
	/**
	 * @cfg {String} nanText 错误文本显示，如果该值是不是一个有效的数字。例如，这可能发生，如果一个有效的字符，如'。'或' - '留在现场，没有数。
	 */
	/**
	 * @cfg {String} negativeText 错误要显示的文本，如果该值是负数和minValue（最小值）被设置为0。这是用，而不是仅在该情况下的minText。
	 */
	/**
	 * @cfg {Boolean} readOnly 是否只读 默认false
	 */
	/**
	 * @cfg {RegExp} regex 正则表达式
	 */
	/**
	 * @cfg {String} regexText 正则表达式错误显示信息
	 */
	/**
	 * @cfg {Number} size 在文字输入元素的'大小'属性的初始值。这是仅用于域没有配置的宽度并没有给出其容器的布局宽度。默认为20。
	 */
	/**
	 * @cfg {Number} step 指定一个数值字段的值将递增或递减当用户调用微调的时间间隔。
	 */
	/**
	 * @cfg {String} vtype 验证类型名称定义
	 */
	/**
	 * @cfg {String} vtypeText vtypeText 自定义错误消息显示Vtype域提供的消息，目前此字段设置的默认到位。注：仅适用于如果Vtype域设置，否则忽略。
	 */
	/**
	 * @private
	 * @param cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.form.field.Number', cfg);
		if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			hideTrigger:true,
			allowBlank:true,
			blankText:true,
			editable:true,
			emptyText:true,
			disabled:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			maxText:true,
			maxValue:true,
			minText:true,
			minValue:true,
			nanText:true,
			negativeText:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			step:true,
			vtype:true,
			vtypeText:true
		});
	},
	/**
	 * 返回两者是否相等
	 * 
	 * @param {Object} v1
	 * @param {Object} v2
	 * @return {Boolean}
	 */
	isEqual : function(v1, v2) {
		return this.component.isEqual(v1, v2);
	},
	/**
	 * 重置（还原）该组件的值
	 */
	reset:function(){
		this.component.reset();
	},
    /**
     * 设置该组件的最小值。
     * @param number
     */
    setMinValue : function(number){
    this.component.setMinValue(number);
    },
    /**
     * 设置该组件的最大值。
     * @param number
     */
    setMaxValue: function(number){
        this.component.setMaxValue(number);
    }
});