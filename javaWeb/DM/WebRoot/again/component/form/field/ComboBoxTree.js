/**
 * @class NS.form.field.ComboBoxTree
 * @extends NS.form.field.BaseField
 * 下拉树
 */
NS.define('NS.form.field.ComboBoxTree', {
	extend : 'NS.form.field.BaseField',

	/**
	 * @cfg {Number} width 组件宽度 默认210
	 */
	/**
	 * @cfg {Number} height 下拉树形的高度 默认300
	 */
	/**
	 * @cfg {Boolean} expanded 是否展开  默认false
	 */
	/**
	 * @cfg {Boolean} rootVisible 是否使用根节点 默认false
	 */
	/**
	 * @cfg {String} rootName 根节点名称
	 */
	/**
	 * @cfg {String} fieldLabel 下拉树文本标签名
	 */
	/**
	 * @cfg {Boolean} isLeafSelect 是否允许选择叶子节点
	 */
	/**
	 * @cfg {String} notLeafInfo 不是子节点信息时，使用者点击父节点时触发此条信息
	 */
	/**
	 * @cfg {String} notParentInfo  不是父节点信息时使用者点击子节点时触发此条信息
	 */
	/**
	 * @cfg {Boolean} isParentSelect 是否允许选择父节点
	 */
	/**
	 * @cfg {Boolean} autoScroll 是否自动显示滚动条 默认true
	 */
	/**
	 * @cfg {Array} treeData 树形数据 不包含根节点数据
	 */
	/**
	 * @cfg {Number} pickerWidth 下拉出树形picker的宽度 默认与原生保持一致:width - lableWidth - 2||0
	 */

	/**
	 * @private
	 */
	initEvents : function() {
		this.callParent();
	},
    onSelect : function(){
        this.component.on('treeselect',function(records){
        	//console.log(records);
            this.fireEvent('select', this,records.raw,records);
        },this);
    },
	/**
	 * @private
	 * @param cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.ux.comboBoxTree', cfg);
	},
	/**
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			width:true,
			fieldLabel:true,
			editable:true,
			expanded:true,
			editable:true,
			height:true,
			treeData:true,
			pickerWidth:true,
			rootVisible:true,
			isLeafSelect:true,
			isParentSelect:true,
			rootName:true,
			emptyText:true,
			isLeafSelect : true,
			isParentSelect:true,
			notParentInfo:true,
			notLeafInfo : true
		});
	},
	/**
	 * 设置值
	 * 
	 * @param {Array/Number/String} id 这个值存在的话，显示对应的显示值，不存在的话显示为空
	 */
	setValue : function(id) {
		this.component.setValue(id);
	},
	/**
	 * 得到下拉树的实际值
	 * 
	 * @return {Object}
	 */
	getValue : function() {
		return this.component.getValue();
	},
    /**
     * 设置下拉树的显示值
     * @param {Object} value
     */
	setRawValue:function(value){
		this.component.setRawValue(value);
	},
	/**
	 * 得到下拉树的显示值
	 * 
	 * @return {String}
	 */
	getRawValue : function() {
		return this.component.getRawValue();
	},
    onChange : function(){
        //console.log(this.getValue()+"----"+this.getRawValue());
    },
	/**
	 * 重置下拉树的显示值
	 */
	reset : function(){
		this.component.reset();
	},
	/**
	 * 重新加载下拉树的数据
	 * @param {Object} data 重新加载的下拉树的数据
	 */
	loadData : function(data){
		this.component.reset();
		this.component.loadData(data);
	},
	/**
	 * 得到当前node的model对象集合值
	 * @param value
	 */
	getCurrentNodeRaw:function(value){
		return this.component.getCurrentNodeRaw(value);
	}
});
