/**
 * @class NS.Connection
 * @extends NS.Base
 *       Ajax请求对象，用以和后台进行ajax请求交互
 */
NS.define('NS.Connection', {
    /***
     * 后台基本请求路径
     */
    baseAction:'baseAction!queryByComponents.action',
    /***
     *  创建一个ajax交互对象
     */
    constructor:function () {
        this.addEvents('beforerequest');
        this.initConnection.apply(this, arguments);
    },
    /***
     * 初始化{Ext.data.Connection}对象
     */
    initConnection:function (config) {
//        this.conn = Ext.create('Ext.data.Connection', config || {});
        this.conn = Ext.Ajax;
    },
    /***
     * 异步请求组件数据
     *      var conn = new NS.Connection();
     *      conn.callService(,{name : 张三,age : 15});
     *
     * @param request  serviceName
     * @param params  Object
     * @return
     */
    callService:function (services, callback, scope, timeout) {
        this.asyncRequest({
            params:{components:NS.encode(services)},
            callback:callback,
            scope:scope,
            timeout : timeout || 60000
        });
    },
    /***
     * 同步调用后台service方法
     * @param {Object}services services对象
     * @return {Object} 后台返回的请求数据
     */
    syncCallService:function (services) {
        var data = this.syncRequest({
            params:{components:NS.encode(services)},
            callback:callback,
            scope:scope
        });
        return data;
    },
    /**
     * 用于获取同步加载数据
     * @param config 请求配置参数
     */
    syncRequest:function (config) {
        var me = this;
        var basic = {
            url:this.baseAction,
            params:{},
            method:'POST',
            async:false,
            success:function (response) {
                me.dataJson = Ext.JSON.decode(
                    response.responseText, true);
            },
            failure:function (response) {
                me.dataJson = Ext.JSON.decode(
                    response.responseText, true);
                Ext.Msg.alert('警告', '数据请求失败！');
            }
        };
        NS.apply(basic, config);//将参数对象合并
        this.conn.request(basic);
        return me.dataJson;
    },
    /***
     * 异步请求后台数据
     * @param config 请求配置参数
     */
    asyncRequest:function (config) {
        var me = this;
        var callback = config.callback;
        delete config.callback;
        var basic = {
            url:this.baseAction,
            params:{},
            method:'POST',
            header : {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
            success:function (response) {
                var sessionstatus=response.getResponseHeader("sessionstatus");
                if(sessionstatus=="timeout"){
                    window.location.href = "session.jsp";
                }
                var dataJson = Ext.JSON.decode(
                    response.responseText, true);
                callback.call(this, dataJson,response.responseText);
            },
            failure:function () {
                var dataJson = Ext.JSON.decode(
                    response.responseText, true);
                callback.call(this, dataJson);
                Ext.Msg.alert('警告', '数据请求失败！');
            }
        };
        NS.apply(basic, config);//将参数对象合并
        this.conn.request(basic);
    }
});