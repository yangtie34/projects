/**
 * 多行工具栏组件。
 * @class NS.toolbar.MultiLineTbar
 * @extends NS.toolbar.Toolbar
 */
NS.define('NS.toolbar.MultiLineTbar',{
	extend:'NS.toolbar.Toolbar',
    /**
     *@cfg {Number} contentWidth tbar内容的宽度,定义了所有的子组件所能占tbar的宽度
     */
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('contentWidth');
    },
	initComponent:function(config){
        var basic = {
            //layout : 'fit',
            margin : '1',
            items : {
                xtype : 'container',
                layout : 'column',
                items : config.items,
                defaults : {
                    margin : '4'
                },
                width : config.contentWidth||'100%'
            }
        };
        delete config.items;
        delete config.contentWidth;
        NS.apply(basic,config);
		this.component = Ext.create('Ext.toolbar.Toolbar',basic);
	}
});