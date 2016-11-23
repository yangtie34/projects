/**
 * @class NS.container.TabPanel
 * @extends NS.container.Panel
 *      标签页容器
 *
 *      var c = new NS.container.Container({
                width: '100%',
                title : '第一个页面',
                height: '20%',
                layout : 'fit',
                region : 'center',
                html : '第一个页面'
            });
        var panel = new NS.container.Panel({
                width : 600,
                height : 600,
                items : component,
                title : '1212',
                tbar : tbar,
            });
        var tab = new NS.container.TabPanel({
                width : 600,
                height : 600,
                title : '1212',
                renderTo: NS.getBody(),
                items : [c,panel]
            });
 */
NS.define('NS.container.TabPanel',{
    extend : 'NS.container.Panel',
    /**
     *  创建一个tabpanel
     * @param config
     */
    initComponent : function(config) {
        this.component = Ext.create('Ext.tab.Panel', config);
    },
    /**
     * 设置该tab为当前显示页面
     * @param {NS.Component} component 将要被设置为激活tab页的组件
     */
    setActiveTab : function(component) {
        this.component.setActiveTab(component.getLibComponent());
    },
    /**
     *获取当前激活的tab页
     * @return {NS.Component} 获取的激活的页面组件对象
     */
    getActiveTab : function(){
        var tab = this.component.getActiveTab();
        return NS.util.ComponentInstance.getInstance(tab);
    }
});
