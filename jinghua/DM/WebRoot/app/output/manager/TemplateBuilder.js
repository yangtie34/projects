/****
 * 模版构造器（类）
 */
NS.define('Output.TemplateBuilder', {
    /***
     * 实例化工具类
     * @type Boolean
     */
    singleton: true,
    typeTemplateMap: {
        'quarters': 'Output.QuartersTemplate',//四分屏
        'classical': 'Output.ClassicalTemplate',//经典模版
        'single': 'Output.SingleTemplate',//单分平模版
        'embedded': Output.Embedded//内嵌式模版
    },
    /****
     * 根据模版配置数据以及组件管理器，创建模版信息
     * @param {} templateconfig
     * @param {} componentManager
     * @return  Template
     */
    createTemplate: function (tbody) {
        var Class = NS.ClassManager.getClassByNameSpace(this.typeTemplateMap[tbody.templateType]);//个人意见：所有的创建方法均改造为构造函数写法~
        return new Class(tbody);
    }
    //模版可放置任意地点
});