/****
 * 组件管理器（类）
 */
NS.define('Output.ComponentManager', {
    typeComponentMap: {
        'pie': 'Output.PieChartComponent',
        'doughnut': 'Output.DoughnutChartComponent',
        'line': 'Output.LineChartComponent',
        'column': 'Output.ColumnChartComponent',
        'bar': 'Output.BarChartComponent',
        'area': 'Output.AreaChartComponent',
        'text': 'Output.Text',
        'table': 'Output.Table',
        'datelinkage': 'Output.DateLinkage',
        'treelinkage': 'Output.TreeLinkage'//树形联动组件
    },
    /***
     * 构造函数
     * @param {} componentsData
     */
    constructor: function (componentsData, templateType) {
        this.components = {};//声明组件集合
        this.comArray = [];//组件数组
        this.templateType = templateType;
        this.createComponents(componentsData);
    },
    /***
     * 通过组件数据集合创建组件对象
     * @param componentsData 组件数据集合
     */
    createComponents: function (componentsData) {
        for (var i = 0, len = componentsData.length; i < len; i++) {
            this.createComponent(componentsData[i]);
        }
    },
    /****
     * 创建组件 根据传递的数据创建对应的组件
     * @param cdata 组件数据 参照定义组件数据格式
     * @return Component
     */
    createComponent: function (cdata) {
        var type = cdata.componentType;
        cdata.templateType = this.templateType,classname = this.typeComponentMap[type];//组件创建时添加-模版类型-树形
        var Class = NS.ClassManager.get(classname)||NS.ClassManager.getClassByNameSpace(classname);//根据组件类型获取对应类
        if (!Class)return null;
        var component = new Class(cdata);//不用构造函数的原因？
        if (type == 'treelinkage' || type == 'datelinkage') {
            this.linkage = component;
        }
        this.register(cdata.componentId, component);
    },
    /****
     * 注册，将对象的注册进入组件管理器中
     * @param {String/Number}id 数据库组件标识Id
     * @param {Object}status 状态对象
     */
    register: function (id, component) {
        this.components[id] = component;
        this.comArray.push(component);
    },
    /***
     * 根据组件的数据库组件标识id获取组件
     * @param {String/Number}id 数据库组件标识Id
     * @return Component
     */
    getComponentById: function (id) {
        return this.components[id];
    },
    /**
     * 联动组件状态--每个组件事件触发的时候都调用它
     */
    getLinkComStatus: function (id) {
        //通过id得到该联动组件  调用其方法即可
        if (this.linkage) {
            return this.linkage.getStatus();
        } else {
            return null;
        }
    },
    /***
     * 获取组件的状态
     */
    getComponentStatus: function (componentId) {
        var linkstatus = this.getLinkComStatus();//获取联动组件状态
        var comstatus = this.getComponentById(componentId).getStatus();//获取组件的状态
        var params = {
            pageId: this.getPageId(),//页面id
            templateId: this.getTemplateId(),//获取模版id
            requestType: 'linkage',//联动请求类型,
            components: [
                {
                    componentId: componentId,
                    params: comstatus
                }
            ]
        };
        if (linkstatus)
            US.apply(params, {globalParams: linkstatus});
        else
            US.apply(params, {globalParams: {}});
        return params;
    },
    /**获取组件管理器管理的所有组件**/
    getComponents: function () {
        return this.comArray;
    },
//--------------------------------------------------
    /**
     * 得到所有组件的状态--联动组件触发的时候获取页面其他组件的所有状态
     */
    getAllStatus: function () {
        //应该是模版获取的吧？？？？？？
        var components = [];
        var comArray = this.comArray;
        var linkage = this.linkage;
        for (var i = 0, len = comArray.length; i < len; i++) {
            var com = comArray[i];
            if (com == linkage) continue;
            components.push(com.getStatus());
        }
        var status = {//所获取的状态
            pageId: this.getPageId(),
            templateId: this.getTemplateId(),
            components: components
        };
        if (linkage) {
            status.global = linkage.getStatus();
        }
        return status;
    },
    /**
     * @param data:[{id1:{数据}},{id2:{数据2}}]
     *
     */
    loadData: function (pageData) {
        var data = pageData.pageInitData.templates[0].components;
        for (var i = 0, len = data.length; i < len; i++) {
//		   for(var param in data[i]){
//		    this.getComponentById(param).loadData(data[param]);
//		    }
            var C = data[i];
            this.getComponentById(C.componentId).loadData(C);
        }
    },
    /***
     * 获取页面id
     */
    getPageId: function () {
        return this.pageId;
    },
    /***
     * 设定组件管理器页面id
     * @param {} pageId
     */
    setPageId: function (pageId) {
        this.pageId = pageId;
    },
    /***
     * 获取模版id
     * @return {}
     */
    getTemplateId: function () {
        return this.templateId;
    },
    /***
     * 设置组件管理器模版id
     * @param {} templateId
     */
    setTemplateId: function (templateId) {
        this.templateId = templateId;
    },
    /**
     * 添加组件监听
     */
    addComListener: function (id, eventName, listener) {

    }
});