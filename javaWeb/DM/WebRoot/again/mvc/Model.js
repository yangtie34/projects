/***
 * @class NS.mvc.Model
 * @extends NS.Base
 * mvc中模型层类
 *
 *     var model = new NS.mvc.Model({
 *         serviceConfig : {
 *             'xsHeader_data' : {
 *                 service : "base_Service",
 *                 params : {}
 *             },
 *             'xs_show_data' : {
 *                 service : "xsxx_showData",
 *                 params : {
 *                     age : 12,
 *                     sex : '12121212'
 *                 }
 *             }
 *         }
 *     });
 *       var data1 = model.callService('xsHeader_data',function(data1){
 *              //data1.xsHeader_data 就是你想要的数据
 *       });
 *       var data2 = model.callService([{
 *              key : 'xsHeader_data'
 *       },{
 *              key : 'xs_show_data',
 *              params : {
 *                   age : 12,
 *                   sex : '12121212'
 *              }
 *       }],function(data2){
 *              //data2 ={xsHeader_data : headerdata,xs_show_data : showdata}就是你想要的数据
 *       });
 *       model.callService({
 *              key : 'xs_show_data',
 *              params : {
 *                   age : 12,
 *                   sex : '12121212'
 *              }},function(data3){
 *                  //data3 = {xs_show_data:showdata} 就是需要的数据
 *              });

 *
 */
NS.define('NS.mvc.Model',{
    /***
     * 构造函数
     */
    constructor : function(){
        this.initData.apply(this,arguments);
        this.allServiceConfig = {};
        NS.apply(this.allServiceConfig,this.serviceConfig);
        this.init();
    },
    /**
     * @private
     * @param config
     */
    initData : function(config){
        this.initConnection();
        NS.apply(this,config);
    },
    /***
     * 模型层初始化方法
     * @private
     */
    init : NS.emptyFn,
    /***
     * 初始化{NS.Connection}
     * @private
     */
    initConnection : function(){
        this.connection = new NS.Connection();
    },
    /***
     * 通过key值来获取key值对应的service数据
     *
     *      var model = new NS.mvc.Model({
     *      serviceConfig : {
     *             'xsHeader_data' : {
     *                 service : "base_Service",
     *                 params : {}
     *             },
     *             'xs_show_data' : {
     *                 service : "xsxx_showData",
     *                 params : {
     *                     age : 12,
     *                     sex : '12121212'
     *                 }
     *             },
     *             'xs_add_data' : {
     *                 service : "xsxx_addData",
     *                 params : {
     *                     age : 12,
     *                     sex : '12121212'
     *                 }
     *             }
     *         }});
     *       model.callService('xsHeader_data',function(data){
     *              //data 就是你想要的数据
     *       });
     *       model.callService([{
     *              key : 'xsHeader_data'
     *       },{
     *              key : 'xs_show_data',
     *              params : {
     *                   age : 12,
     *                   sex : '12121212'
     *              }
     *       },'xs_add_data'],function(data){
     *
     *       });
     *
     * @param {Object[]/String} params
     * @param params
     */
    callService : function(params,callback,scope,timeout){
        var me = this,basic = {};
        var process = function(item){
            var obj = {};
            if(NS.isObject(item)){
                var key = item['key'];
                var service = me.getServiceConfig(key);//通过key值获取service配置
                obj[key] = {};
                obj[key]['request'] = service.service;
                obj[key]['params'] = NS.clone(service['params'])||{};//获取基础参数params
                NS.apply(obj[key].params,item['params']);//讲传递的params和配置的params合并
            }else if(NS.isString(item)){
               var service = me.getServiceConfig(item);//通过key值获取service配置
               if(service){
                   obj[item] = {};
                   obj[item]['request'] = service.service;
                   obj[item]['params'] = NS.clone(service.params)||{};//获取传递参数的params
               }
            }
            return obj;
        }
        var obj = Ext.Object;
        if(NS.isArray(params)){
            for(var i= 0,len=params.length;i<len;i++){
                obj.merge(basic,process(params[i]));
            }
        }else if(NS.isString(params)){
            obj.merge(basic,process(params));
        }else if(NS.isObject(params)){
            obj.merge(basic,process(params));
        }
        this.connection.callService(basic,callback,scope,timeout);
    },
    /***
     * 同步调用后台service方法，并返回数据
     * @private
     * @param {Object/String/Array} params
     * @return {Object}
     * @private
     */
    syncCallService : function(params){
        var me = this,basic = {};
        var process = function(item){
            var obj = {};
            if(NS.isObject(item)){
                var key = item['key'];
                var service = me.getServiceConfig(key);//通过key值获取service配置
                obj[key] = {};
                obj[key]['request'] = service.service;
                obj[key]['params'] = service['params']||{};//获取基础参数params
                NS.apply(obj[key].params,item['params']);//讲传递的params和配置的params合并
            }else if(NS.isString(item)){
                var service = me.getServiceConfig(item);//通过key值获取service配置
                if(service){
                    obj[item] = {};
                    obj[item]['request'] = service.service;
                    obj[item]['params'] = service.params||{};//获取传递参数的params
                }
            }
            return obj;
        }
        if(NS.isArray(params)){
            for(var i= 0,len=params.length;i<len;i++){
                NS.apply(basic,process(params[i]));
            }
        }else if(NS.isString(params)){
            NS.apply(basic,process(params));
        }else if(NS.isObject(params)){
            NS.apply(basic,process(params));
        }
        return this.connection.callService(basic,callback,scope);
    },
    /**
     * 单独调用service，直接返回所需数据，不需要再通过key值进行访问
     *
     *        var model = new NS.mvc.Model({});
     *        model.call('basequery',{entityName:'Tbxsjbxx'},function(data){
     *                  //data 就是你所需要的数据
     *        },this);
     *
     * @param {String} key 需要调用的service的key值
     * @param {Function} fun 回调函数
     * @param {Object} scope 函数作用域
     */
    callSingle : function(service,fun,scope){
        this.callService(service,function(data){
             if(NS.isString(service)){
                fun.call(scope||this,data[service]);
             }else if(NS.isObject(service)){
                fun.call(scope||this,data[service['key']]);
             }
        });
    },
    /**
     * 同步调用service方法
     * @param {String} serviceName service名称
     * @param {Object} params 参数对象
     * @return {Object}
     * @private
     */
    syncCall : function(serviceName,params){
        var obj = {
            result : {
                request : serviceName,
                params : NS.encode(params||{})
            }
        };
        var data = this.connection.syncCallService(obj);
        return data.result;
    },
    /***
     * 通过key值获取service配置对象
     * @param ｛String｝key 用来获取service配置的键
     * @return {Object}
     * @private
     */
    getServiceConfig : function(key){
        var service,obj = {};
        if(key){
           var service = this.allServiceConfig[key];
           if(service){
              if(NS.isObject(service)){
                 return service;
              }else if(NS.isString(service)){
                 obj['service'] = service;
                 return obj;
              }
           }else{
               NS.error({
                   sourceClass : 'NS.mvc.Model',
                   sourceMethod : 'getServiceConfig',
                   msg : '找不到请求的service：'+key
               })
           }
        }
        return NS.clone(this.allServiceConfig[key]);
    },
    /**
     * 替换key值映射到的service
     * @param {String} key service 对应的key值
     * @param {Object/String}serviceObj  需要更新的service的配置对象
     *
     *     var model = new NS.mvc.Model({
     *         serviceConfig : {
     *             add : 'add_service'
     *         }
     *     });
     *     model.updateServiceConfig('add','base_addService');//这是修改
     *     model.updateServiceConfig('add',{
     *         service : 'base_addService',
     *         params : {entityName : 'Tbsxs'}
     *     });
     *     model.updateServiceConfig('update','update_service');//这是修改
     *
     *     也可以用这种形式修改和添加
     *
     *     model.updateServiceConfig({
     *         'add' : 'add_service',
     *         'update' : 'update_service'
     *     });
     *
     */
    updateServiceConfig : function(key,serviceObj){
        var serviceConfig = this.allServiceConfig,i;
        if(NS.isString(key)){
            serviceConfig[key] = serviceObj;
        }else if(NS.isObject(key)){
            for(i in key){
                serviceConfig[i] = key[i];
            }
        }
    },
    /**
     * 更新key值对应的service的请求参数
     * @param {String} key 需要更新的service的key值
     * @param {Object} params 需要更新的参数对象，更新的参数遵循（有则覆盖，无则添加的规则）
     */
    updateServiceParams : function(key,params){
        var serviceConfig = this.allServiceConfig, i,service = serviceConfig[key];
        if(NS.isString(service)){
            serviceConfig[key] = {service : service,params : params};
        }else if(NS.isObject(service)){
            if(service.params){
               NS.apply(service.params,params);
            }else{
               service.params = params;
            }
        }
    }
});
