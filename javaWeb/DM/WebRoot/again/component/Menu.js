/**
 * @class NS.menu.Menu
 * @extends NS.container.Panel
 */
NS.define('NS.menu.Menu', {
	extend : 'NS.container.Panel',
	initData : NS.emptyFn,
	/**
	 * @cfg {Object} defaults 默认配置属性
	 */
	/**
	 * @cfg {String} defaultType 组件默认类型
	 */
	/**
	 * @cfg {Object/Object[]} dockedItems 作为停靠此面板项目的一个组件或一系列的组件被添加,默认'right','center','left'
	 */
	/**
	 * @cfg {Boolean} hideCollapseTool 是否隐藏收缩工具栏 默认false  
	 */
	/**
	 * @cfg {Boolean} ignoreParentClicks  true以忽略这是一个父项在此菜单中点击任何项目（显示一个子菜单），这样子菜单中单击父项时，没有被驳回 默认false
	 */
	/**
	 * @cfg {Boolean} plain 真正删除阴刻线下的左侧菜单中，并且不缩进一般组件项目 默认false
	 */
	/**
	 * @cfg {Boolean} showSeparator 是否显示图标分隔符  默认true
	 */
	/***************************************************************************
	 * 初始化组件所支持的属性的映射
	 * 
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			defaults:true,
			defaultType:true,
			disabled:true,
			dockedItems:true,
			hideCollapseTool:true,
			//items:true,
			ignoreParentClicks:true,
			plain:true,
			showSeparator:true
		});
	},
	initComponent : function(config) {
		this.component = Ext.create('Ext.menu.Menu', config);
	},
    onClick : function(){
        this.component.on('click',function(menu,item,event){
            NS.Event.setEventObj(event);
            this.fireEvent('click',NS.util.ComponentInstance.getInstance(menu),item,NS.Event);
        },this);
    }
});
