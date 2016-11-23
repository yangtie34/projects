/**
 * @class NS.form.field.Date
 * @extends NS.form.field.BaseField
 * 日期组件
 */
NS.define('NS.form.field.Date', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {String} format 可以覆盖默认的日期格式字符串的本地化支持(Y-m-d H:i:s)。格式必须是有效的根据Ext.Date.parse。 默认是：月/日/年
	 */
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
	 * @cfg {String} pickerAlign 对齐位置对齐选择器。默认为“TL-BL？”
	 */
	/**
	 * @cfg {String/Date} maxValue 最大允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。
	 */
	/**
	 * @cfg {String/Date} minValue 最小允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。 
	 */
	/**
	 * @private
	 * @param {Object} cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.form.field.Date', cfg);
		if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
	},
//	/**
//	 * @private
//	 */
//	initEvents:function(){
//		this.callParent();
//		this.addEvents(
//				/**
//				 * @event keydown 键盘点下时触发
//				 * @param {NS.Component} this
//				 */
//				'keydown',
//				/**
//				 * @event keypress 按键输入字段事件。此事件只触发，如果enableKeyEvents设置为true。
//				 * @param {NS.Component} this
//				 */
//				'keypress',
//				/**
//				 * @event keyup 键盘键松开时触发
//				 * @param {NS.Component} this
//				 */
//				'keyup',
//				/**
//				 * @event select 数据被选中时触发
//				 * @param {NS.Component} this
//				 */
//				'select'
//				);
//	},
//	/**
//	 * @private
//	 */
//	packEvents:function(){
//		this.callParent();
//		this.component.on({
//			keydown : {
//				fn : function() {
//					this.fireEvent('keydown', this);
//				},
//				scope : this
//			},
//			keypress : {
//				fn : function() {
//					this.fireEvent('keypress', this);
//				},
//				scope : this
//			},
//			keyup : {
//				fn : function() {
//					this.fireEvent('keyup', this);
//				},
//				scope : this
//			},
//			select : {
//				fn : function() {
//					this.fireEvent('select', this);
//				},
//				scope : this
//			}
//		});
//	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			allowBlank:true,
			blankText:true,
			editable:true,
			emptyText:true,
			format:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			pickerAlign:true,
			readOnly:true,
			regex:true,
			regexText:true,
			vtype:true,
			vtypeText:true,
			maxValue:true,
            disabled:true,
			minValue:true
		});
	},
	/**
	 * 得到行值
	 * @return {String}
	 */
	getRawValue:function(){
		return this.component.getRawValue();
	},
	/**
	 * 是否有效
	 * @return {Boolean}
	 */
	isValid : function(){
		return this.component.isValid();
	},
	/**
	 * 得到这行提交值
	 * @return {String/Null}
	 */
	getSubmitValue:function(){
		return this.component.getSubmitValue();
	},
	/**
	 * 设置该组件是否可编辑
	 * @param {Boolean} editable
	 */
	setEditable:function(editable){
		this.component.setEditable(editable);
	},
	/**
	 * 设置这个filed的默认参数值
	 * @param {Object} defaults
	 */
	setFieldDefaults:function(defaults){
		this.component.setFieldDefaults(defaults);
	},
	/**
	 * 设置最大值
	 * @param {Date} value
	 */
	setMaxValue:function(value){
	   this.component.setMaxValue(value);	
	},
	/**
	 * 设置最小值
	 * @param {Date} value
	 */
	setMinValue:function(value){
		this.component.setMinValue(value);
	},
	/**
	 * @return {Ext.Component}
	 */
	getPicker:function(){
		return this.component.getPicker();
	},
    /**
     * 设置开始日期 对应组件(和vtype:'daterange'联合使用)
     * @param startField
     */
    setStartDateField : function(startField){
        this.component.startDateField = startField.getLibComponent();
    },
    /**
     * 设置结束日期对应组件(和vtype:'daterange'联合使用)
     * @param endField
     */
    setEndDateField : function(endField){
        this.component.endDateField = endField.getLibComponent();
    },
    /**
     * 设置是否可用
     * @param disabled
     * @returns
     */
    setDisabled:function(disabled){
    	return this.component.setDisabled(disabled);
    }
});