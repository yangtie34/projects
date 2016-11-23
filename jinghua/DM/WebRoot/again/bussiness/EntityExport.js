/**
 * 定义实体导出组件类
 * @author wangyt
 * @class  NS.bussiness.EntityExport
 */
NS.define('NS.bussiness.EntityExport',{
    singleton : true,
    /**
     * 根据实体属性表以及NS.grid.Grid对象导出excel
     *
     *      NS.bussiness.EntityExport.exportExcel({
     *          grid : grid,
     *          entityName : 'TbXsjbxx',
     *          title : '导出Excel组件的',
     *          fileName : '',
     *          controller : this
     *      });
     *
     * @param {Object} config 配置对象
     */
    exportExcel : function(config){
        if(config.grid){
            var grid = config.grid,title = config.title,controller = config.controller,
                entityName = config.entityName,queryParams = grid.getQueryParams(),
                entityCN = '\"'+title+'\"';//设定标题
            var url = grid.key;//key值
            if(NS.isObject(url)){
                url = url.key
            }
            NS.apply(queryParams,{start:0,limit : 10000000});
            var service = controller.model.getServiceConfig(url);
            service = NS.isString(service)?service:service.service;
        }else{
            var title = config.title,controller = config.controller,
                entityName = config.entityName,queryParams = config.queryParams,
                serviceKey = config.serviceKey,//service对应的key值
                entityCN = '\"'+title+'\"';//设定标题
            NS.apply(queryParams,{start:0,limit : 10000000});
            var service = controller.model.getServiceConfig(serviceKey);
            service = NS.isString(service)?service:service.service;
        }
        var components = "components={'exp':{'request':\""+service+"\",'params':{}}}"
        var win = Ext.create('Ext.window.Window', {
            width : 100,
            height : 85,
            //margin : '5 10 5 10',
            closable : true,
//			preventHeader : true,
            modal : true,
            autoShow : true,
            autoDestroy : false,
            defaultType : 'radiofield',
            items : [{
                // xtype:'radio',
                name : 'exportType',
                boxLabel : '全部字段',
                inputValue : '1',
                id : 'state'
            }, {
                // xtype:'radio',
                name : 'exportType',
                boxLabel : '显示字段',
                inputValue : '0',
                id : 'state1'
            }],
            defaults : {
                listeners : {
                    click : {
                        element : 'el',
                        fn : function() {
                            var type = config.grid?Ext.getCmp('state').getGroupValue():'1'; // 待改
                            var queryCondition = Ext.encode(queryParams);
                            //添加代理数据请求参数
                            var proxyP = "proxyMenuId="+MainPage.proxyMenuId;
                            if(MainPage.proxyUserId) proxyP += "&proxyUserId"+MainPage.proxyUserId;

                            if ('0' == type) { // 只导出显示列
                                var showField = grid.getShowColumnNames();
                                // alert(showField.length)
                                var params = [];
                                for (var i = 0; i < showField.length; i++) {
                                    var field = showField[i].dataIndex
                                        .split(".");
                                    params.push("\'"+field+"\'");
                                }
                                var exportFields = '{"fields":['
                                    + params.join(",") + ']';
                                exportFields += ',"entityName":\"' + entityName+'\"';
                                exportFields += ',"entityCN":' + entityCN + '}';

                                window.location = 'baseAction.action?exportFields='
                                    + exportFields
                                    + "&queryCondition="
                                    + queryCondition
                                    +"&"+components
                                    +"&"+proxyP;
                                win.close();
                            } else if ('1' == type) { // 导出全部列
                                var exportFields = '{';
                                exportFields += 'fields:["all"]';
                                exportFields += ',entityName:"'
                                    + entityName.replace(/\"/g, "") + '"';
                                exportFields += ',entityCN:"'
                                    + entityCN.replace(/\"/g, "") + '"}';

                                window.location = 'baseAction.action?exportFields='
                                    + exportFields
                                    + "&queryCondition="
                                    + queryCondition
                                    +"&"+components
                                    +"&"+proxyP;
                                win.close();
                            }
                        }
                    }
                }
            }
        });
    }
});
(function(){
    var alias = NS.Function.alias;
    /**
     * @member NS
     * @method entityExcelExport
     * @inheritdoc NS.bussiness.EntityExport#exportExcel
     */
    NS.entityExcelExport = alias(NS.bussiness.EntityExport,'exportExcel');
})();