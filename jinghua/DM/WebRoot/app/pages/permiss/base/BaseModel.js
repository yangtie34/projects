NS.define('Pages.permiss.base.BaseModel',{
    extend : 'Template.Page',
    requires : ['Pages.permiss.base.PageTitle','Pages.permiss.base.ColumnTitle','Pages.permiss.base.Tbar','Pages.permiss.base.LayoutComponent'],
    cssRequires : [
        'app/pages/permiss/base/css/wbh-common.css',
        'app/pages/permiss/base/css/base.css'
    ],
    addCssRequires : [],
    constructor : function(){
        this.TBar =   Pages.permiss.base.Tbar;
        this.PageTitle = Pages.permiss.base.PageTitle;
        this.ColumnTitle = Pages.permiss.base.ColumnTitle;
        this.callParent(arguments);
    },
    initCss : function(){
        this.cssRequires = NS.Array.merge(this.addCssRequires,this.cssRequires);
        this.callParent();
    },
    /**
     * 执行新增操作
     *   {
     *      params : {},
     *      key : '',
     *      successMsg : '',
     *      failureMsg : '',
     *      callback : fn,
     *      scope : this
     *   }
     * @param config
     */
    doTipRequest : function(config){
        var successMsg = config.successMsg,
            failureMsg = config.failureMsg,
            key = config.key,
            confirm = config.confirm,
            confirmMsg = config.confirmMsg,
            params = config.params,
            callback = config.callback;
        if(confirm){
           NS.Msg.changeTip('确认',confirmMsg,function(){
               this.callSingle({key : key,params : params},function(data){
                   if(data.success){
                       NS.Msg.info(successMsg);
                       if(callback)callback.call(this);
                   }else{
                       NS.Msg.info(failureMsg);
                   }
               });
           },this);
        }else{
            this.callSingle({key : key,params : params},function(data){
                if(data.success){
                    NS.Msg.info(successMsg);
                    if(callback)callback.call(this);
                }else{
                    NS.Msg.info(failureMsg);
                }
            });
        }
    },
    /**
     * 确认某样信息,是则执行回调函数，否则不执行
     * @param msg
     * @param callback
     */
    confirm : function(msg,callback){
        NS.Msg.changeTip('确认',msg,function(){
            callback.call(this);
        },this);
    },
    /**
     * 提示
     * @param title
     * @param msg
     */
    alert : function(title,msg){
        NS.Msg.info(title,msg);
    },
    getBaseWindow : function(config){
        var basic = {
            closable : false,
            bodyCls : '',
            baseCls : '',
            draggable : true,
//            plain : true,
            header : false,
            style : {
                backgroundColor : 'white',
                //border : '1px solid rgb(153,188,232)'
            },
            modal : true
        };
        NS.apply(basic,config);
        return new NS.window.Window(basic);
    },
    getPackContainer : function(config){
        config.layout = 'fit';
        var container = new NS.container.Container(config);
        return container;
    },
    selectGrid : function(grid,callback){
        var rows = grid.getSelectRows();
        if(rows.length == 0){
            this.alert("提示","请您选中一行记录!");
        }else{
            callback.call(this,rows[0]);
        }
    },
    /**
     * 将LayoutComponent添加的NS.container.Container容器中
     * @param container {NS.container.Container}
     * @param child {Pages.permiss.base.LayoutComponent}
     */
    add : function(container,child){
        if(child instanceof Pages.permiss.base.LayoutComponent){
            container.add(child.component);
        }
    },
    remove : function(container,child){
        if(child instanceof Pages.permiss.base.LayoutComponent){
            container.remove(child.component);
        }
    },
    /************************一些内置过滤器********************************/
    filterA : function(element,callback){
        if(element.nodeName == "A"){
            callback.call(this,element);
        }
    },
    filterNode : function(nodeName,element,callback){
        if(element.nodeName == nodeName){
            callback.call(this,element);
        }
    },
    getLayoutInstance : function(config){
        return new Pages.permiss.base.LayoutComponent(config);
    },
    /*************************一些通用获取用户信息的方法****************************/
    getUsernameAndBmmc : function(){
        var bmmc = MainPage.getBmxx().mc,
            username = MainPage.getUserName()||MainPage.getLoginName();
        return {department : bmmc,username : username};
    }

});