/**
 * @class NS.form.field.ForumSearch
 * @extends NS.form.field.ComboBox
 * 查询下拉组件 
 * 
 */
NS.define('NS.form.field.ForumSearch',{
	extend:'NS.form.field.ComboBox',
	/**
	 * @cfg {String} name 组件名称
	 */
	/**
	 * @cfg {String} queryParam 查询的参数名
	 */
	/**
	 * @cfg {Boolean} readOnly 是否只读 默认false
	 */
	/**
	 * @cfg {String} fieldLabel 组件显示标签名称
	 */
	/**
	 * @cfg {Boolean} editable 是否可编辑 默认true 
	 */
	/**
	 * @cfg {Number} minChars 最小查询字符数  默认4
	 */
	/**
	 * @cfg {String} displayField 显示值域名称
	 */
	/**
	 * @cfg {String} valueField 实际值域值
	 */
	/**
	 * @cfg {String} emptyText 组件值为空是显示的文本
	 */
	/**
	 * @cfg {String} labelAlign 标签的位置left center right 默认 left
	 */
	/**
	 * @cfg {Boolean} allowBlank 是否允许为空 默认false
	 */
	/**
	 * @cfg {Boolean} typeAhead true其余的文字键入一个可配置的的延迟（typeAheadDelay,默认250）后，它是否符合一个已知值来填充和自选。默认true
	 */
	/**
	 * @cfg {Boolean} hideTrigger 隐藏下拉图标 默认true
	 */
	/**
	 * @cfg {Number} labelWidth 标签长度 默认70
	 */
	/**
	 * @cfg {Number} width 组件宽度 默认280
	 */
	/**
	 * @cfg {Object} listConfig 下拉列表配置项
	 */
	/**
	 * @cfg {Number} pageSize 分页条数 默认10
	 */
	/**
	 * @cfg {String} url 链接路径
	 */
	/**
	 * @cfg {Array} fields 域配置属性 name:'',mapping:'',type:'',format:'',以及其他配置信息
	 */
	/**
	 * @cfg {Function} getInnerTpl 得到下拉列表的内部配置模板
	 */
	/**
	 * @cfg {Object} service 服务配置
	 */
	/**
	 * @cfg {String} valueNotFoundText  
	 */
	/**
	 * @cfg {Number} queryDelay 默认500,当mode为remote/local时
	 */
	/**
	 * @private
	 */
	initComponent:function(cfg){
		this.component = Ext.create('Ext.form.field.ForumSearch',cfg);
	},
	getValue:function(){
		return this.component.getValue();
	},
	getRawValue:function(){
		return this.component.getRawValue();
	},
	getSubmitValue:function(){
		return this.component.getValue();
	},
	//setValue不建议使用,因为初始时根本没有值,所以setValue(id),该组件的值会变更为id,而非对应的mc
	setValue:function(id){
		this.component.setValue(id);
	},
	//loadData方法可能无法正常使用,因为queryMode已经为remote才能这么做.而该方法需要为local
	loadData:function(data){
		this.component.loadData(data);
	},
    /**
     * 初始化事件
     * @private
     */
    initEvents: function () {
        this.callParent();
        this.addEvents(
            /***
             * @event beforeload 加载数据之前
             */
            'beforeload'
        );
    },
    onBeforeload:function(){
        this.component.on('beforeload',function(params,opts){
            return this.fireEvent('beforeload', params);
        },this);
    },
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			service:true,
			name:true,
			queryParam:true,
			readOnly:true,
			fieldLabel:true,
			editable:true,
			minChars:true,
			displayField:true,
			valueField:true,
			emptyText:true,
			labelAlign:true,
			allowBlank:true,
			typeAhead:true,
			hideTrigger:true,
			labelWidth:true,
			width:true,
			valueNotFoundText:true,
			listConfig:true,
			pageSize:true,
			url:true,
			queryDelay:true,
			fields:true,
			getInnerTpl:true
		});
	},
    setQueryParams : function(params){
        this.component.setQueryParams(params);
    }
});

