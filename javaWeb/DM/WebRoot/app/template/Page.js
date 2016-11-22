/**
 * Page页面,所有页面类都需要从此类继承
 */
NS.define('Template.Page', {
    extend: 'NS.mvc.Controller',
    /**
     * 根据name查找组件的代码数据
     * */
    getCodeDataByName : function(name,data){
        var codeData,i= 0,len=data.length,item;
        for(i;i<len;i++){
            item = data[i];
            if(item.name == name){
                codeData = item.codeData.data;
                break;
            }
        }
        return codeData;
    },
    /***
     *根据代码获得id
     * @param {String} name 列标识
     * @param {String} dm dm值
     * @param {Array} data 实体属性表转换后的数据
     * @return {Number} id
     * */
    getIdByDm : function(name,dm,data){
        var codeData = this.getCodeDataByName(name,data),item,len,i;
        if(codeData){
           for(i=0,len = codeData.length;i<len;i++){
               item = codeData[i];
               if(item.dm == dm){
                  return item.id;
               }
           }
        }
    },
    /***
     *根据id获取dm
     * @param {String} name 列标识
     * @param {String} id id值
     * @param {Array} data 实体属性表转换后的数据
     * @return {Number} id
     * */
    getDmById : function(name,id,data){
        var codeData = this.getCodeDataByName(name,data),item,len,i;
        if(codeData){
            for(i=0,len = codeData.length;i<len;i++){
                item = codeData[i];
                if(item.id == id){
                    return item.dm;
                }
            }
        }
    },
    /***
     * 通过id获取名称
     * @param {String} name 列标识
     * @param {String} id id值
     * @param {Array} data 实体属性表转换后的数据
     * @return {String} 名称
     */
    getMcById : function(name,id,data){
        var codeData = this.getCodeDataByName(name,data),item,len,i;
        if(codeData){
            for(i=0,len = codeData.length;i<len;i++){
                item = codeData[i];
                if(item.id == id){
                    return item.mc!=null?item.mc:item.text;
                }
            }
        }
    },
    /***
     * 根据代码获得名称
     * @param {String} name 列标识
     * @param {String} dm dm值
     * @param {Array} data 实体属性表转换后的数据
     * @return {Number} 名称
     * */
    getMcByDm : function(name,dm,data){
        var codeData = this.getCodeDataByName(name),item,len,i;
        if(codeData){
            for(i=0,len = codeData.length;i<len;i++){
                item = codeData[i];
                if(item.dm == dm){
                    return item.mc;
                }
            }
        }
    },
    /*********************一些获取常用数据的方法*******************************/
    /**
     * 获取当前学年学期的id和名称
     */
    getCurrentXnXq : function(){
        var xnId = MainPage.getXnId(),xqId = MainPage.getXqId(),
        	xndm = MainPage.getXnDm(),xqdm = MainPage.getXqDm();
            xnmc = MainPage.getXnMc(),xqmc = MainPage.getXqMc();
            return {xnId : xnId,xqId : xqId,xnmc : xnmc,xqmc : xqmc,xndm : xndm,xqdm:xqdm};
    },
    /**************************通用简化--创建组件方法****************************************/
    /**
     * 创建grid
     * this.createGrid({
     *     plugins :[new NS.grid.plugin.CellEditor()],
     *     proxy : this.model,
     *     serviceKey : 'querygrid',
     *     mulitSelect : false
     * });
     * @param config
     */
    createGrid : function(config){
        var basic = {
            plugins: [
                new NS.grid.plugin.HeaderQuery()],
            autoScroll: true,
            proxy : this.model,
            serviceKey : 'queryGrid',
            multiSelect: true,
            lineNumber: false
        };
        NS.apply(basic,config);
        var grid = new NS.grid.Grid(basic);
        return grid;
    },
    /**
     * 创建tbar
     * var tbar = this.createTbar({
     *     items : [
     *             {xtype : 'button',text : '查询',name : 'query'}
     *     ],
     *     bindItems  : {
     *           query : {event : 'click',fn : this.doquery,scope : this}
     *     }
     * });
     * @param config
     * @param bindItems
     * @return {NS.toolbar.Toolbar}
     */
    createTbar : function(config){
        var bindItems = config.bindItems||{};
        var tbar = new NS.toolbar.Toolbar(config);
        tbar.bindItemsEvent(bindItems);
        return  tbar;
    },
    /**
     * 创建简化版grid的方法
     * this.createGrid({
     *     plugins :[new NS.grid.plugin.CellEditor()],
     *     proxy : this.model,
     *     serviceKey : 'querygrid',
     *     mulitSelect : false
     * });
     * @param config
     */
    createSimpleGrid : function(config){
        var basic = {
            plugins: [
                new NS.grid.plugin.HeaderQuery()],
            autoScroll: true,
            proxy : this.model,
            serviceKey : 'queryGrid',
            multiSelect: true,
            lineNumber: false
        };
        NS.apply(basic,config);
        var grid = new NS.grid.SimpleGrid(basic);
        return grid;
    },
    /**
     * 创建新增form
     * form默认创建2个按钮，并且绑定事件为this.save/this.cancel,您需要自己填写这两个事件
     * this.createAddForm({
     *     data : trandata,
     *     items : ['xh','xm'],
     *     columns : 2,
     *     buttons : [
     *           {xtype : 'button',text : '查询',name : 'query'}
     *     ],
     *     bindItems : {
     *           query : {event : 'click',fn : this.doquery,scope : this}
     *           xh : {event : 'keyup',fn : this.dowithxh,scope : this}
     *     }
     * });
     * @param config
     */
    createAddForm : function(config){
        var basic = {
                autoScroll : true,
                formType : 'add',
                title : '新增',
                autoShow : true,
                modal : true,
                columns : 1,
                buttons : [{xtype:'button',text : '提交',name : 'submit'},
                    {xtype:'button',text : '取消',name : 'cancel'}]
            },
            bindConfig = config.bindItems||{};
        NS.apply(basic,config);
        var form = NS.form.EntityForm.create(basic);
        form.bindItemsEvent(bindConfig);
        return form;
    },
    /**
     * 创建修改form
     * form默认创建2个按钮，并且绑定事件为this.save/this.cancel,您需要自己填写这两个事件
     * this.createUpdateForm({
     *     data : trandata,
     *     items : ['xh','xm'],
     *     columns : 2,
     *     buttons : [
     *           {xtype : 'button',text : '查询',name : 'query'}
     *     ],
     *     bindItems : {
     *           query : {event : 'click',fn : this.doquery,scope : this}
     *           xh : {event : 'keyup',fn : this.dowithxh,scope : this}
     *     }
     * });
     * @param config
     */
    createUpdateForm : function(config){
        var basic = {
                autoScroll : true,
                formType : 'update',
                title : '修改',
                autoShow : true,
                modal : true,
                columns : 1,
                buttons : [{xtype:'button',text : '提交',name : 'submit'},
                    {xtype:'button',text : '取消',name : 'cancel'}]
            },
            bindConfig = config.bindItems||{};
        NS.apply(basic,config);
        var form = NS.form.EntityForm.create(basic);
        form.bindItemsEvent(bindConfig);
        return form;
    },
    /**
     * 根据选中的一行grid的记录创建一个修改Form
     * this.createUpdateForm({
     *     data : trandata,
     *     grid : grid,
     *     items : ['xh','xm'],
     *     columns : 2,
     *     buttons : [
     *           {xtype : 'button',text : '查询',name : 'query'}
     *     ],
     *     bindItems : {
     *           query : {event : 'click',fn : this.doquery,scope : this}
     *           xh : {event : 'keyup',fn : this.dowithxh,scope : this}
     *     }
     * });
     */
    createUpdateFormWithSelectGrid : function(config){
        var grid = config.grid;
        var count = grid.getSelectCount();
        if(count>1){
            NS.Msg.info("您修改的时候最多只能选中一行记录!");
        }else if(count == 0){
            NS.Msg.info("请您选中一行记录!");
        }else{
            var row = grid.getSelectRows()[0];
            var basic = {
                values : row
            };
            NS.apply(config,basic);
            return this.createUpdateForm(config);
        }
    },
    /*************************一些通用的操作组件的方法********************************************/
    /**
     * this.removeRowsWithGrid({
     *     grid : grid
     * });
     */
    removeRowsWithGrid : function(config){
        var grid = config.grid;
        var count = grid.getSelectCount();
        if(count == 0){
            NS.Msg.info('请选中至少一行！');
        }else{
            grid.removeSelectRows();
        }
    },
    /**
     * 根据grid删除记录
     * this.deleteWithGrid({
     *     grid : grid,
     *     serviceKey : 'deleteService',
     *     info : "成绩权重信息",
     *     controller : this
     * });
     */
    deleteWithGrid : function(config){
        var grid = config.grid,serviceKey = config.serviceKey,info = config.info,controller = config.controller,
            baseParams = config.params ||{};
        var ids = NS.Array.pluck(grid.getSelectRows(),'id');
        baseParams['ids'] = ids;
        if(ids&&ids.length==0){
            NS.Msg.alert('提示','请您选中至少一行数据！');
            return;
        }else{
            NS.Msg.changeTip('提示','您确定要删除这<span style="color:red;">'+ids.length+'</span>记录么？',function(){
                controller.callSingle({key : serviceKey,params : baseParams},function(result){
                    if(result.success){
                        NS.Msg.info((info||'')+'信息删除成功！');
                        grid.load();
                    }else{
                        NS.Msg.info((info||'')+'信息删除出错，请联系系统管理员！');
                    }
                })
            });
        }
    },
    /**
     * 根据form新增数据
     * this.saveWidthForm({
     *     form : form,
     *     serviceKey : 'queryGrid',
     *     info : "成绩权重信息",
     *     params : {name : '张三'},
     *     grid : grid,
     *     controller : this
     * });
     */
    saveWithForm : function(config){
        var form = config.form,serviceKey = config.serviceKey,info = config.info,
            submitparams = config.params,controller = config.controller,grid = config.grid,formContainer = config.formContainer;
        if(form.isValid()){
            var formvalues = form.getValues(),callobj = {};
            formvalues.entityName = this.entityName;
            NS.apply(formvalues,submitparams);
            if(NS.isString(serviceKey)){
                callobj = {
                    key : serviceKey,
                    params : formvalues
                }
            }else if(NS.isObject(serviceKey)){
                callobj.params = serviceKey.params || {};
                NS.apply(callobj.params,formvalues);
            }
            controller.callSingle(callobj,function(result){
                if(result.success){
                    NS.Msg.info((info||'')+'保存/提交成功！');
                    if(formContainer){
                        formContainer.close();
                    }else{
                        form.close();
                    }
                    grid.refresh();
                }else{
                    NS.Msg.info((info||'')+'信息保存出错，请联系系统管理员！');
                }
            });
        }
    },
    /**
     * 根据form修改数据
     * this.saveWidthForm({
     *     form : form,
     *     serviceKey : 'queryGrid',
     *     info : "成绩权重信息",
     *     infoOverride : true,
     *     grid : grid,
     *     params : {name : '张三'},
     *     controller : this
     * });
     */
    updateWithForm : function(config){
        var form = config.form,serviceKey = config.serviceKey,info = config.info,infoOverride = config.info,
            submitparams = config.params,controller = config.controller,formContainer = config.formContainer;
        if(form.isValid()){
            var formvalues = form.getValues(),callobj = {};
            formvalues.entityName = this.entityName;
            NS.apply(formvalues,submitparams);
            if(NS.isString(serviceKey)){
                callobj = {
                    key : serviceKey,
                    params : formvalues
                }
            }else if(NS.isObject(serviceKey)){
                callobj.params = serviceKey.params || {};
                NS.apply(callobj.params,formvalues);
            }
            controller.callSingle(callobj,function(result){
                if(result.success){
                    if(infoOverride){
                        NS.Msg.info(info);
                    }else{
                        NS.Msg.info((info||'')+'信息修改成功！');
                    }
                    if(formContainer){
                        formContainer.close();
                    }else{
                        form.close();
                    }
                    if(config.grid){
                        config.grid.refresh();
                    }
                }else{
                    NS.Msg.info((info||'')+'信息修改出错，请联系系统管理员！');
                }
            });
        }
    },
    /**
     * 根据EditorGrid的数据进行保存
     *      this.updateWithGrid({
     *          grid : grid,
     *          serviceKey : 'updateWithGrid',
     *          info : "成绩信息",
     *          params : {name : '张三'},
     *          controller : this,
     *          success : function(){
     *
     *          }
     *      });
     */
    updateWithGrid : function(config){
        var grid = config.grid,serviceKey = config.serviceKey,info = config.info,
            submitparams = config.params,controller = config.controller,loadParams = config.loadParams,
            rows,callobj = {};
        rows = grid.getModify();
        if(NS.isString(serviceKey)){
            callobj = {
                key : serviceKey,
                params : {rows : rows,others : submitparams||{}}
            }
        }else if(NS.isObject(serviceKey)){
            NS.apply(submitparams,serviceKey.params||{});
            callobj = {
                key : serviceKey,
                params : {rows : rows,others : submitparams||{}}
            };
        }
        controller.callSingle(callobj,function(result){
            if(result.success){
                NS.Msg.info((info||'')+'信息修改成功！');
                grid.load(loadParams||{});
            }else{
                NS.Msg.info((info||'')+'信息修改出错，请联系系统管理员！');
            }
        });
    },
    bindPageWindow : function(window){
        var activateF = function(){
                if(window)window.show();
            },
            deactivateF = function(){
                if(window)window.hide();
            },
            component = this.ownerContainer;
        component.on('activate',activateF);
        component.on('deactivate',deactivateF);
        component.on('close',function(){
            window.removeListener('activate',activateF);
            window.removeListener('deactivate',deactivateF);
        });
    }
});