/***
 * 事件绑定器（类）
 */
NS.define('Output.EventBindManager', {
    /***
     * 实例化类
     */
    /***
     * 构造函数
     */
    constructor: function () {
        var me = this;
        me.listeners = {
            'statuschange': me.statusChange,//状态改变事件
            'pop': me.doPopfun,//弹窗事件
            'linkage': me.linkage//联动
        };
        me.componentListeners = {
            'text': ['pop'],
            'datelinkage': ['linkage'],
            'treelinkage': ['linkage'],
            'table': ['statuschange'],
            'column': ['statuschange'],
            'doughnut': ['statuschange'],
            'pie': ['statuschange'],
            'bar': ['statuschange'],
            'area': ['statuschange'],
            'line': ['statuschange']
        };
    },
    /****
     * 根据组件以及传递的组件的事件名，对该组件该事件进行绑定
     * @param {} component
     * @param {} event
     */
    bindEvent: function (component) {
        var me = this;
        var ctype = component.getComponentType();
        var listeners = me.componentListeners[ctype];
        for (var i = 0, ln = listeners.length; i < ln; i++) {
            var event = listeners[i];
            component.addListener(event, function (componentId) {
                me.listeners[event].apply(this, arguments);
            }, me);
        }
    },
    /****
     *为组件绑定对应的事件
     */
    bindEvents: function () {
        var components = this.CM.getComponents();//获取组件管理器组件列表
        for (var i = 0, len = components.length; i < len; i++) {
            this.bindEvent(components[i]);
        }
    },
    /**
     * 弹窗事件
     *@param entityName 实体属性表实体名
     *@param id 统计功能id
     */
    doPopfun: function (entityName, id) {
        //通过弹窗组件id 得到弹窗组件    params为相应的查询参数
        //需不需要格式转换--假设需要：paramsArray==包含id
        if (entityName != null && entityName != ""){
             var window = new Ext.window.Window({
                title:'请输入查询密码',
                width:240,
                autoShow:true,
                modal:true,
                layout:'hbox',
                items:[
                    new Ext.form.field.Text({width:110,inputType:'password'})
                ]
            });
            var button = new Ext.button.Button({
                text : '确定'
            });
            var button1 = new Ext.button.Button({
                text : '取消'
            });
            button.on('click',function(){
                window.hide();
                NS.Msg.error('密码输入错误','密码输入错误，请重新输入尝试！');
            });
            button1.on('click',function(){
                window.hide();
            });
            window.insert(1,button);
            window.insert(2,button1);
        }

            /*var popWindow = new Output.PopWindow({
                entityName: entityName,//请求表头数据
                id: id//请求数据id
            });*/
    },
    /**
     * 状态改变事件
     * @param compontentObj 组件对象
     * @param componentId 组件对象id
     */
    statusChange: function (componentId) {
        //通过id得到组件 组件提供的getStatus方法即可得到状态 -->给数据请求代理
        var params = this.getCM().getComponentStatus(componentId);//获取当前组件的状态
        this.getModel().pageRequest(params, function (data) {
            this.getCM().loadData(data);
        }, this);
    },
    /**
     * 联动事件~~
     * @param componentIdList 组件id列表
     * @param linkValueObj 联动值--格式是？{startDate:2012-01-29,endDate:2012-10-02,bjid:1112020101013}
     */
    linkage: function () {
        var params = this.getCM().getAllStatus();
        this.getModel().pageRequest(params, function (data) {
            this.getCM().loadData(data);
        }, this);
    },
    /***
     * 设置实体模型属性
     * @param {} model
     */
    setModel: function (model) {
        this.model = model;
    },
    /***
     * 获取数据层对象
     * @return {}
     */
    getModel: function () {
        return this.model;
    },
    /***
     * 设置组件管理器
     */
    setCM: function (componentManager) {
        this.CM = componentManager;
    },
    /***
     * 获取组件管理器
     */
    getCM: function () {
        return this.CM;
    }
});