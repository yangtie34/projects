/***
 * @class NS.mvc.View
 * @extends NS.Base
 * mvc中视图层类
 *
 */
NS.define('NS.mvc.View',{
    /***
     * 构造函数
     */
    constructor : function(){
        this.initComponent.apply(this,arguments);
        this.init();
    },
    /***
     * 在init方法内完成对页面组件的组装
     */
    init : NS.emptyFn,
    /***
     * 初始化组件
     * @private
     */
    initComponent : function(){
        this.compManager = {};
    },
    /**
     * 设置页面渲染的最顶层组件
     */
    setComponent : function(component){
        this.component = component;
    },
    /**
     * 将组件注册进入View层的组件管理器中
     *
     *      var view = new NS.mvc.View();
     *      view.register({
     *          grid : new NS.component.grid.Grid()
     *      });
     *      view.register({
     *          grid : new NS.grid.Grid(),
     *          form : new NS.form.Form()
     *      });
     *      //可以这样做
     *      view.getGrid();//return NS.grid.Grid();
     *      view.getForm();//return NS.form.Form();
     *      //你也可以这样做
     *      view.get('grid');//return NS.grid.Grid(),
     *
     * @private
     * @param {Object} component 组件或者组件集合
     */
    register : function(component){
        var ST = NS.String,i;
        if(this.compManager){
            NS.apply(this.compManager,component);
        }
        if(arguments.length == 1){
            if(NS.isObject(component)){
                for(i in component){
                    (function(me,property,component){
                        //根据注册进来的组件的名称，生成get方法
                        me['get'+ST.capitalize(property)] = function(){
                            return component[property];
                        };
                    })(this,i,component);
                }
            }
        }else if(arguments.length == 2){//如果参数个数为2，那么参数的形式可以是
                                        // ('name',new NS.component.Component())
            if(NS.isString(component)){
                (function(me,i,component){
                    //根据注册进来的组件的名称，生成get方法
                    me['get'+ST.capitalize(i)] = function(){
                        return component;
                    };
                })(this,arguments[0],arguments[1]);
            }
        }

    },
    /**
     * 根据传递的注册进来的组件名称来获取组件
     *      var view = new NS.mvc.View();
     *      view.register({
     *          grid : new NS.grid.Grid()
     *      });
     *      view.register({
     *          grid : new NS.grid.Grid(),
     *          form : new NS.form.Form()
     *      });
     *      //你可以这样做
     *      view.getGrid();//return NS.grid.Grid(),
     *      //你也可以这样做
     *      view.get('grid');//return NS.grid.Grid(),
     *
     * @private
     * @param {String} cname
     * @return {NS.Component}
     */
    get : function(cname){
        return this.comManager[cname];
    },
    /**
     * 获取底层类库组件
     * @private
     * @return {Ext.Component}
     */
    getLibComponent : function(){
        return this.component.getLibComponent();
    }
});
