/**
 * @class NS.form.field.File
 * @extends NS.form.field.BaseField
 * 文件上传组件
 */
NS.define('NS.form.field.File', {
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
	/**
	 * @private
	 * @param {Object} cfg
	 */
	initComponent : function(cfg) {
		//var callBack = cfg.callBack;
		delete cfg.callBack;
		
		var basic = {
				buttonText:'上传'
		};
		Ext.apply(basic,cfg);
		this.component = Ext.create('Ext.form.field.File', basic);
//		this.component.addListener('change',function(file,value,eOpts){
//			var name = file.buttonText;
//			Ext.Msg.confirm('提示','确定'+name+'文件:&nbsp;<b>'+file.getValue()+'</b>&nbsp;吗？',function(btn){
//				if(typeof callBack != 'undefined'){
//					callBack(btn);
//				}else{
//					//系统默认提供的处理
////					alert(btn+'系统未做默认处理！');					
//				}
//			});
//		});
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			callBack:true,
			allowBlank:true,
			blankText:true,
			buttonOnly:true,
			buttonText:true,
            buttonConfig : true,
			editable:true,
			emptyText:true,
			disabled:true,
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
//				'keyup'
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
//			}
//		});
//	},
	/**
	 * 返回值（s）应该保存到的Ext.data.Model实例的这一领域，
	 * 被称为Ext.form.Basic.updateRecord时。
	 * 通常情况下，这将是一个单一的名称 - 值对这个字段的名字，
	 * 这个名字和值是其当前的数据值的对象。
	 * 更先进的领域实现可能返回多个名称 - 值对。
	 * 返回的值将被保存在模型中的相应的字段名称。
     * 请注意，这个方法返回的值都不能保证成功验证。
     * @return {Object} 提交的映射值的参数名称，如果那个特定的名称有多个值，
     * 每个值应该是一个字符串，或者一个字符串数组。
     * 如果没有要提交的参数，它也可以返回null。
	 */
	getModelData:function(){
		return this.component.getModelData();
	},
	/**
	 * 设置此组件在其父面板的停靠位置
	 * @param {Object} dock 定位位置
	 * @param {Boolean} layoutParent True为重新布局父类 默认false
	 * @return {Ext.Component} this
	 */
	setDocked:function(dock,layoutParent){
		return this.component.setDocked();
	}
});