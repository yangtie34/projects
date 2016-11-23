/***
 * @class NS.mvc.Controller
 * @extends NS.Base
 * mvc中控制层类
 *
 *      NS.define('App.com.MyController',{
                extend : 'NS.mvc.Controller',
                cssRequires : ['path1','path2'],
                dataRequires : [//类初始化准备数据,fieldname代表指定的内部变量名
                    {fieldname : 'headerData',
                    key : 'xsHeader_data',params : {a:12},
                    process : {scope : this,fn : function(data){return data;}}
                },
                    {fieldname : 'headerData2',key : 'xsHeader_data'}
                ],
                advanceDataRequires : [//数据提前加载，该配置项需要和this.getAdvanceData(['headerData1'],function(data){});共同使用
                    {fieldname : 'headerData1',key : 'xsHeader_data'}
                ],
                tplRequires : [
                    {fieldname : 'xxtpl',path : 'template/path/pat.html'}
                ],
                modelConfig : {
                    serviceConfig : {
                        'xsHeader_data' : {
                            service : "base_Service",
                            params : {}
                        },
                        'xs_show_data' :  "xsxx_showData"
                        }
                    }
                },
                init : function(){//子类processHeaderData1需重写的入口方法
                    this.(this.headerData1);
                }
            });

 *
 */
NS.define('NS.mvc.Controller',{
    /**
     *@param {Array} cssRequires css的相对路径
     * */
    /**
     *@param {Array} dataRequires 具体加载的数据，在init方法之前加载
     * */
    /**
     *@param {Array} tplRequires  tpl加载，在init方法之前加载
     * */
    /**
     *@param {Array} advanceDataRequires  数据预加载，//数据提前加载，该配置项需要和this.getAdvanceData(['headerData1'],function(data){});共同使用
     * */
      /***
     * 构造函数
     * @param {Object} config 配置对象
     */
    constructor : function(config){
        var me = this;
        this.addEvents(
            /**
             * @event pageready  当页面可以被渲染的时候触发该事件
             */
            'pageready',
            /**
             * @event tplready 当tpl加载完毕之后触发该事件
             */
            'tplready'
        );
        NS.apply(this,config);
        this.initCss();//初始化Css加载
        this.initModel();//初始化模型层
        this.initView();//初始化视图层
        this.initAdvanceDataRequires();
        this.initTplRequires(function(){
            this.fireEvent('tplready');
            this.tplready = true;
            this.initDataRequires(function(){
                this.init(config);//对象初始化
            });//初始化数据依赖
        });
    },
    /**
     * 数据提前加载，该方法主要用于:当前端某个操作较大量的数据的时候，但是该操作并不是打开页面的必须数据的时候，预先加载数据，提升用户操作的流畅度
     * @private
     **/
    initAdvanceDataRequires : function(){
        var dr = this.advanceDataRequires,fieldKeyMap = {},i= 0,item;
        this.advanceDataRequiresFlag = false;
        this.advanceDataRequiresExcute = function(){};
        if(dr && dr.length!=0){
            for(;i<dr.length;i++){
                item = dr[i];
                fieldKeyMap[item.key] = item.fieldname;
            }
            this.callService(dr,function(data){
                var i,item,fieldname;
                for(var i in fieldKeyMap){
                    fieldname = fieldKeyMap[i];
                    item = data[i];
                    this[fieldname] = item;
                }
                this.advanceDataRequiresFlag = true;
                this.advanceDataRequiresExcute();//数据提前加载函数执行
            });
        }
    },
    /**
     * 获取预加载数据，异步
     * @param datafields 获取预加载的数据的域属性数组
     * @param callback 回调函数
     */
    getAdvanceData : function(datafields,callback){
        var me = this,data = {},i= 0,item,len = datafields.length;
        if(this.advanceDataRequiresFlag){
            for(;i<len;i++){
                item = datafields[i];
                data[item] = this[item];
            }
            callback.call(this,data);
        }else{
            this.advanceDataRequiresExcute = function(){
                 me.getAdvanceData(datafields,callback);
            };
        }
    },
    /**
     * @private
     * @param callback
     */
    initTplRequires : function(callback){
        var dr = this.tplRequires||[],fieldKeyMap = {},i = 0,item,tplFlag = dr.length;
        if(dr.length!=0){
            for(;i<dr.length;i++){
                item = dr[i];
                (function(name){
                    NS.loadFile(item.path,function(txt){
                        this[name] = new NS.Template(txt);
                        tplFlag = tplFlag - 1;
                        if(tplFlag == 0){
                            callback.apply(this);
                        }
                    },this);
                }).call(this,item.fieldname);
            }
        }else{
            callback.apply(this);
        }
    },
    /**
     * 初始化数据依赖
     * @private
     */
    initDataRequires : function(callback){
        var dr = this.dataRequires||[],fieldKeyMap = {},filterMap = {},i = 0,item;
        if(dr.length!=0){
            for(;i<dr.length;i++){
                item = dr[i];
                fieldKeyMap[item.key] = item.fieldname;
                filterMap[item.key] = item.process;
            }
            this.callService(dr,function(data){
                var i,item,fieldname,process;
                for(var i in fieldKeyMap){
                    fieldname = fieldKeyMap[i];
                    process = filterMap[i];
                    item = data[i];
                    if(process && NS.isFunction(process)){
                        this[fieldname]=process(item);
                    }else if(NS.isObject(process)){
                        this[fieldname]=process.fn.call(process.scope,item);
                    }else{
                        this[fieldname] = item;
                    }
                }
                callback.apply(this);
            });
        }else{
            callback.apply(this);
        }
    },
    /**
     * 初始化Css加载
     * @private
     */
    initCss : function(){
        var array = this.cssRequires||[], i=0,len;
        for(len=array.length;i<len;i++){
            NS.loadCss(array[i]);
        }
    },
    /***
     * 初始化模型层
     * @private
     */
    initModel : function(){
        var serviceBasic = {"baseSave" : "base_save",
                            "baseUpdate" : "base_update",
                            "baseDelete" : "base_deleteByIds",
                            'baseQuery' : 'base_queryTableContent',
                            'baseTableHeader' : 'base_queryForAddByEntityName'};
        if(this.modelConfig && this.modelConfig.serviceConfig){
            NS.apply(this.modelConfig.serviceConfig,serviceBasic);
        }
        this.model = new NS.mvc.Model(this.modelConfig);
    },
    /**
     * 初始化视图层
     * @private
     */
    initView : function(){
        this.view = new NS.mvc.View(this.viewConfig);
    },
    /***
     * 子类需重写方法，子类的入口从该方法开始
     * @method init
     */
    init : NS.emptyFn,
    /**
     * 获取模型层组件
     * @return {NS.mvc.Model}
     */
    getModel : function(){
        return this.model;
    },
    /***
     * 获取view层组件
     * @return {NS.mvc.View}
     */
    getView : function(){
        return this.view;
    },
    /**
     * 别名 {@link NS.mvc.View#register NS.mvc.View}.
     * @private
     * @inheritdoc NS.mvc.View#register
     * @member NS.mvc.Controller
     * @method registerComponent
     */
    registerComponent : function(component){
        var register = this.view.register;
        register.apply(this.view,arguments);
    },
    /**
     * 别名 {@link NS.mvc.View#get NS.mvc.View}.
     * @private
     * @inheritdoc NS.mvc.View#get
     * @member NS.mvc.Controller
     * @method getComponent
     */
    getComponent : function(key){
        return this.view.get(key);
    },
    /**
     * NS.mvc.Controller 代理NS.mvc.Model的方法
     *
     *       NS.define('App.com.MyController',{
                extend : 'NS.mvc.Controller',
                modelConfig : {
                    serviceConfig : {
                        'xsHeader_data' : {
                            service : "base_Service",
                            params : {}
                        },
                        'xs_show_data' : {
                            service : "xsxx_showData",
                            params : {
                                age : 12,
                                sex : '12121212'
                            }
                        }
                    }
                }
             });
             var ct = new App.com.MyController();
     *       ct.callService(['xsHeader_data','xs_show_data'],function(data){
                alert(data);//即为你获取的JSON数据
            });

         回调函数里默认函数执行作用域为当前Controller,默认等待时间是30000毫秒
     * @param {Object[]/String[]/String} params
     * @param {Function} callback 回调函数
     * @param {Object} scope 作用域
     */
    callService : function(params,callback,scope){
        this.model.callService(params,callback,scope||this);
    },
    /**
     *       NS.mvc.Controller 代理NS.mvc.Model的方法
     *
     *       NS.define('App.com.MyController',{
                 extend : 'NS.mvc.Controller',
                 modelConfig : {
                 serviceConfig : {
                 'xsHeader_data' : {
                 service : "base_Service",
                 params : {}
                 },
                 'xs_show_data' : {
                 service : "xsxx_showData",
                 params : {
                 age : 12,
                 sex : '12121212'
         }
         }
         }
         }
         });

            var ct = new App.com.MyController();
            ct.callServiceWithTimeOut(['xsHeader_data','xs_show_data'],function(data){
            alert(data);//即为你获取的JSON数据
            },30000000);

     回调函数里默认函数执行作用域为当前Controller
     *@param {Object[]/String[]/String} params
     *@param {Function} callback 回调函数
     *@param {Number} timeout 等待时间，如果超过这个时间，回调函数将不会被执行
     *@param {Object} scope 作用域
     */
    callServiceWithTimeOut : function(params,callback,timeout,scope){
        this.model.callService(params,callback,scope||this,timeout);
    },
    /**
     * NS.mvc.Controller 代理NS.mvc.Model的方法
     * 调用单独的service，并直接返回数据
     * 回调函数里默认函数执行作用域为当前Controller
     */
    callSingle : function(serviceName,fun,scope){
        this.model.callSingle.call(this.model,serviceName,fun,scope||this);
    },
    /**
     * @private
     * 为组件注册数据加载器，通常情况下，需要特殊指定的数据加载器的组件通常为{NS.grid.Grid}
     * 或者{NS.container.Tree}
     * 这样把组件对数据的依赖路径由内部转向{NS.mvc.Model}组件，这样我们的所有数据依赖全部指向
     * {NS.mvc.Model}
     */
    registerDataLoader : function(key,component){
        component.registerDataLoader(key,this.model);
    },
    /**
     * 设置要渲染的顶层组件
     * @param {NS.Component} component 组件对象
     */
    setPageComponent : function(component){
        this.view.setComponent(component);
        this.fireEvent('pageready');
        this.pageReady = true;//表示页面已经准备就绪
    },
    /**
     * 获取底层类库组件
     * @private
     * @return {Ext.component.Component}
     * @private
     */
    getLibComponent : function(){
        return this.view.getLibComponent();
    },
    /**
     * 告诉框架表明可以渲染页面了
     * 此为Controller中必掉方法之一
     * @private
     */
    ready : function(){
        this.fireEvent('pageready');
    },
    /**
     *@inheritdoc NS.mvc.Model#updateServiceConfig
     */
    updateServiceConfig : function(){
        this.model.updateServiceConfig.apply(this.model,arguments);
    },
    /**
     * 更新service的固定请求参数
     *@inheritdoc NS.mvc.Model#updateServiceParams
     */
    updateServiceParams : function(key,params){
        this.model.updateServiceParams.apply(this.model,arguments);
    },
    /**
     * 单表新增处理函数
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Object} params 实例对象
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseAdd: function (entityName, params, fn, scope) {
        var basic = {entityName: entityName};
        NS.apply(basic, params);
        this.callService({key: 'baseSave', params: basic}, function(data){
            fn.call(scope||this,data['baseSave']);
        });
    },
    /**
     * 单表删除处理函数
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Array} ids 待删除的实例对象的id集合
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseDelete: function (entityName, ids, fn, scope) {
        var basic = {entityName: entityName, ids: ids.toString()};
        this.callService({key: 'baseDelete', params: basic}, function(data){
            fn.call(scope||this,data['baseDelete']);
        });
    },
    /**
     * 单表修改处理函数
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Object} params 实例对象
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseUpdate: function (entityName, params, fn, scope) {
        var basic = {entityName: entityName};
        NS.apply(basic, params);
        this.callService({key: 'baseUpdate', params: basic}, function(data){
            fn.call(scope||this,data['baseUpdate']);
        })
    },
    /**
     * 单独请求表头数据
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseTableHeader: function (entityName, fn, scope) {
        var basic = {entityName: entityName};
        this.callService({key: 'baseTableHeader', params: basic}, function(data){
            var headerData = data['baseTableHeader'],
                sdata = NS.E2S(headerData);
            fn.call(scope||this,sdata);
        })
    },
    /**
     * 请求虚表和实体表的数据
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Function} fn 回调函数
     * @param {Object} scope 作用域
     */
    baseEntityHeaderAndTableData : function(entityName,fn,scope){
        var header = {
            key : 'baseTableHeader',
            params : {entityName : entityName}
            },
        table = {
            key : 'baseQuery',
            params : {entityName : entityName}
        }
        this.callService([header,table],function(data){
            var headerData = data['baseTableHeader'],
                sdata = NS.E2S(headerData),
                tdata = data['baseQuery'];
                fn.call(scope||this,sdata,tdata);
        });
    },
    /**
     * 请求虚表:表头数据和表体数据
     * @param {String} entityName 表头数据实体名称
     * @param {String/Object} queryTableObj 表体查询数据service配置
     * @param {} fn  回调函数
     */
    baseHeaderAndData : function(entityName,queryTableObj,fn,scope){
        var basic = {
            key : 'baseTableHeader',
            params : {entityName: entityName}
        };
        if(!queryTableObj){
            NS.Error({sourceClas : 'Template.Page',sourceMethod : 'baseHeaderAndData',msg:'必须输入请求表数据Service！'});
        }
        this.callService([basic,queryTableObj],function(data){
            var headerData = data['baseTableHeader'],
                sdata = NS.E2S(headerData);
            if(NS.isString(queryTableObj)){
                fn.call(scope||this,sdata,data[queryTableObj]);
            }else{
                fn.call(scope||this,sdata,data[queryTableObj['key']]);
            }

        });
    },
    /**
     * 绑定页面的节点信息
     * @private
     * @param node
     */
    bindNode : function(node){
        this.CD_NODE = node;
    },
    /**
     * 绑定页面遮罩
     * @private
     */
    bindLoadMask : function(mask){
        this.loadMask = mask;
    },
    /**
     * 显示遮罩
     * @private
     */
    showLoadMask : function(){
        this.loadMask.hide();
    },
    /**
     * 根据传递的功能标识，判断是否有该功能权限
     * @param funTag (String) 功能标识
     * @returns {Boolean}
     */
    hasFun : function(funTag){
        var children = this.CD_NODE.children,
            flag = false;
            buttons = [];
        for(var i=0;i<children.length;i++){
            var item = children[i];
            if(item && item.funTag == funTag){
                return true;
            }
        }
        return flag;
    },
    /**
     * 根据功能标识，获取内容标识的集合['sex','age','username']
     * @param tag {String} 功能标识
     * @returns {Array}
     */
    getContentsByFun : function(tag){
        var children = this.CD_NODE.children,
            fun,
            contents = [];
        for(var i=0;i<children.length;i++){
            var item = children[i];
            if(item.funTag == tag){
               fun = item;
               break;
            }
        }
        for(var i=0;i<fun.children.length;i++){
            contents.push(fun.children[i].funTag);
        }
        return contents;
    }
});
