NS.define('Output.PageBuilder', {
    constructor: function (data) {
        this.data = data;
        this.createPage(data);
    },
    /***
     * 创建页面
     * @param {} data
     */
    createPage: function (data) {
        var pageId = this.pageId = data.pageId;// 获取页面id
        var title = data.title;// 页面标题
        var items = data.items;
        var tarray = [];
        for (var i = 0; i < items.length; i++) {
            tarray.push(this.createTemplate(items[i]).getLibComponent());
        }
        if (tarray.length == 1) {
            this.component = Ext.create('Ext.container.Container', {
                title: data.title,
                autoScroll: true,
                items: tarray
            });
        }
    },
    /***
     * 获取页面Id
     * @return {}
     */
    getPageId: function () {
        return this.pageId;
    },
    /***
     * 获取基于底层类库交互用的组件
     * @return {}
     */
    getLibComponent: function () {
        return this.component;
    },
    /***************************************************************************
     * 创建模版对象
     * @param templateData 模版数据
     */
    createTemplate: function (templateData) {
        var componentManager = this.createComponentManager(templateData);//创建组件管理器
        this.bindListeners(componentManager);//绑定监听事件
        var body = this.getTemplateBody(componentManager, templateData);//获得模版组装对象(用于生成模版)
        var template = Output.TemplateBuilder.createTemplate(body);//创建模版
        return template;
    },
    /***
     * 创建组件管理器
     * @param {Object} templateData
     */
    createComponentManager: function (templateData) {
        var CM = new Output.ComponentManager(templateData.components);//创建组件管理器
        return CM;
    },
    /***
     * 绑定监听事件
     */
    bindListeners: function (componentManager) {
        var bindManager = Output.EventBindManager;//事件绑定管理器
        bindManager.bindEvents(componentManager.getComponents());//绑定事件
    },
    /***
     * 将组件管理器以及模版数据进行模版对象体的组装
     * @param {} componentManager
     * @param {} templateData
     * @return Components{Array}
     */
    getTemplateBody: function (componentManager, templateData) {
        var builder = Output.TemplatePackager;//模版组装工具
        templateData.componentManager = componentManager;
        var body = builder.getTemplateBody(templateData);//组装模版
        return body;
    }
});