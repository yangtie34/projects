/**
 * @class NS.form.field.ComboBox
 * @extends NS.form.field.BaseField 下拉框组件
 * 
 */
NS.define('NS.form.field.ComboBox', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Array} fields 域属性集合
	 */
	/**
	 * @cfg {Array} data 数据 隶属于Store
	 */
	/**
	 * @cfg {String} valueField 值域
	 */
	/**
	 * @cfg {Boolean} autoSelect  真正可自动突出显示所收集的数据存储在下拉列表中打开时，它的第一个结果。值为false会导致没有自动突出显示在列表中，所以用户必须手动突出一个项目，然后按Enter或Tab键选择（除非是真实的价值（TYPEAHEAD）），或使用鼠标选择一个值。
	 */
	/**
	 * @cfg {String} displayField 显示域
	 */
	/**
	 * @cfg {Boolean} multiSelect如果设置为true，允许多选，并用,号分割
	 */
	/**
	 * @cfg {Number} pageSize 每页显示条数
	 */
	/**
	 * @cfg {String} pickerAlign  对齐位置对齐选择器。默认为“TL-BL？”
	 */
	/**
	 * @cfg {Number[]} pickerOffset 偏移[X，Y]定位选择器时在除了pickerAlign的使用。默认为undefined。
	 */
	/**
	 * @cfg {Number} queryDelay 延迟遍历 默认100
	 */
	/**
	 * @cfg {String} queryMode 在哪种模式下的ComboBox使用配置的商店 默认有：remote、local、remote,默认'remote'
	 *  
	 */
	/**
	 * @cfg {String} queryParam 商店所使用的参数，通过键入字符串的当ComboBox与queryMode配置的“远程”的名称。如果明确设置为一个falsy值就不会被发送。默认为：“query”
	 */
	/**
	 * @cfg {Array} data 数据源
	 */
	/**
	 * @cfg {String} vtype 验证类型名称定义
	 */
	/**
	 * @cfg {Object} listConfig 下拉时数据配置
	 */

	/**
	 * @private
	 * @param {Object} config
	 */
	initComponent : function(config) {
		var fields = config.fields||['id','mc'];//默认为id和名称mc
		var data = config.data||[];//默认为空值
		//因model、store、proxy三者关系密切,且独立,所以这样的封装适用性不广,暂有缺陷（暂无想到更好办法）
		var store  = Ext.create('Ext.data.Store', {
			fields : fields,
			data : data
		});
		delete config.fields;
		delete config.data;
		var obj = {
				store:store,
				displayField:'mc',
				valueField:'id'
		};
		Ext.apply(obj,config);
		this.component = Ext.create('Ext.form.field.ComboBox', obj);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			data:true,
			valueField:true,
			autoSelect:true,
			allowBlank:true,
			blankText:true,
			displayField:true,
			disabled:true,
			editable:true,
			emptyText:true,
			multiSelect:true,
			pageSize:true,
			pickerAlign:true,
			pickerOffset:true,
			queryDelay:true,
			queryMode:true,
			queryParam:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			fields:true,
			vtype:true,
			listConfig:true,
			vtypeText:true
		});
	},
	/**
	 * 得到行值
	 * 
	 * @return {String/Number}
	 */
	getRawValue : function() {
		return this.component.getRawValue();
	},
	/**
	 * 设置行值（显示的是显示值，设置的是隐藏的实际值）
	 * 
	 * @param {String/Number} value一般是设置id的值
	 */
	setRawValue : function(value) {
		this.component.setRawValue(value);
	},

//	/**
//	 * 设置是否只读
//	 * @param readOnly
//	 */
//	setReadOnly:function(readOnly){
//		this.component.setReadOnly(readOnly);
//	},
    /**
     * 根据传入的条件，过滤当前结果集的数据
     * @param {String} fieldname fields中的字段名
     * @param {String} value  字段对应的值
     */
    filter : function(fieldname,value){
        this.component.store.filter(fieldname,value);
    },
    /**
     * 清空过滤条件
     */
    clearFilter : function(){
        this.component.store.clearFilter();
    },
	/**
	 * 用于queryMode为remote时的请求加载
     * @private
	 * @param data
	 */
	load:function(data){
		this.component.getStore().load(data);
	},
	/**
	 * 重新加载ComboBox的数据queryMode为'local'生效 方法同loadRawData
	 * @param {Ext.data.Model[]/Object[]} data 待加载数据
	 * @param {Boolean} append 是否在原有数据基础上添加 默认false 即加载的数据仅为data,而不包含之前的数据
	 */
	loadData:function(data,append){
		if(typeof append =='undefined') append = false;
		this.component.getStore().loadData(data,append);
	},
	loadRawData:function(data,append){
		if(typeof append =='undefined') append = false;
		this.component.getStore().loadRawData(data,append);
	},
    /**
     * 通过id获取id对应的一行数据
     * @param {String/Number} id
     * @return {Object}
     */
    getItemById : function(id){
        return this.component.getStore().getById(id).data;
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
