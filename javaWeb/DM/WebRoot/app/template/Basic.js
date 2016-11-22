/**
 * 标准的增删改查模版
 */
NS.define('Template.Basic', {
    extend: 'Template.Page',
    modelConfig: {
        serviceConfig: {
            'save': "base_Service",
            'update': "xsxx_showData",
            'del': "xsxx_showData",
            'queryComponents': "xsxx_showData",
            'queryGridData': "xsxx_showData"
        }
    },
    init: function () {
        this.initData();
    },
    initData: function () {
        var me = this;
        if (this.entityName) {
            NS.Error.raise("entityName属性没有定义!");
        }
        this.callService([
            {key: 'queryComponents', params: {entityName: this.entityName}},
            {key: 'queryGridData', params: {entityName: this.entityName}}
        ],
            function (data) {
                //me.fireEvent('gridcomponentsload',headerData);
            this.initComponent(headerData);
//                this.initComponent(data)
            });
    },
    initComponent: function (data) {
        this.initGrid(data);
        this.initTbar();
        var component = new NS.container.Panel({
            layout : 'fit',
            tbar: this.tbar,
            closable: true,
            layout: 'fit',
            items: this.grid
        });
        this.setPageComponent(component);
    },
    initGrid: function (data) {
        var data = this.tranData = NS.util.DataConverter.entitysToStandards(data);
        //var data = this.tranData = NS.util.DataConverter.entitysToStandards(data.queryComponents);//转换表头数据格式
        var basic = {
            plugins: [//new NS.grid.plugin.CellEditor(),
                new NS.grid.plugin.HeaderQuery()],
            columnData: data,
            modelConfig: {
                //data: data.queryGridData
                data : showData
            },
            autoScroll: true,
            multiSelect: true,
            lineNumber: false
        };
        this.grid = new NS.grid.Grid(basic);
        this.registerDataLoader('queryComponents',this.grid);
    },
    /**
     * 初始化Tbar
     * @return {NS.toolbar.Toolbar}
     */
    initTbar: function () {
        var basic = {
            items: [
                {xtype: 'button', text: '新增', name: 'add'},
                {xtype: 'button', text: '修改', name: 'update'},
                {xtype: 'button', text: '删除', name: 'delete'},
                {xtype: 'button', text: '查询', name: 'query'}
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
            'delete': {event: 'click', fn: this.deleteIds, scope: this},
            'add': {event: 'click', fn: this.showAddForm, scope: this},
            'update': {event: 'click', fn: this.showUpdateForm, scope: this}
        });
    },
    /**
     * 绑定各个组件事件的各种监听函数
     */
    bindEvent: function () {


    },
    deleteIds : function(){
        var ids = [];
        this.baseDelete('A',ids,function(){

        },this);
    },
    /**
     * 显示新增form
     */
    showAddForm : function(){
        var form = this.form = NS.form.EntityForm.create({
            data : this.tranData,
            autoScroll : true,
            columns : 2,
            margin : '5px',
//            modal:true,// 魔态，值为true是弹出窗口的。
            buttons : [{text : '提交',name : 'add'},{text : '取消',name : 'cancel'}],
            items : [
                {
                    xtype : 'fieldset',
                    columns : 2,
                    title : '分组1',
                    height : 250,
                    items : [{name : 'id',hidden : true},'xh','xm','yyxId']
                },
                {xtype : 'fieldset',
                    title : '分组2',
                    columns : 1,
                    items : [
                        'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
                    ]},
                {xtype : 'fieldset',
                    title : '分组2',
                    columns : 3,
                    colspan : 2,
                    items : [
                        'yzy','ybjId','ynj'
                    ]}
            ]
        });
        var window  = this.window = new NS.window.Window({
            width : 900,
            height : 600,
            layout : 'fit',
            items : form,
            autoShow: true
        });
        form.bindItemsEvent({
            'add' : {event : 'click',fn : this.submitAdd,scope : this},
            'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
        })
    },
    /**
     * 提交新增form
     */
    submitAdd : function(){
       this.baseAdd('A',this.form.getValues(),function(data){

       },this);
    },
    cancelForm : function(){
       this.window.close();
    },
    /**
     * 显示修改form
     */
    showUpdateForm : function(){
        var form = this.form = NS.form.EntityForm.create({
            data : this.tranData,
            autoScroll : true,
            columns : 2,
            margin : '5px',
            //modal:true,// 魔态，值为true是弹出窗口的。
            buttons : [{text : '提交',name : 'update'},{text : '取消',name : 'cancel'}],
            items : [
                {
                    xtype : 'fieldset',
                    columns : 2,
                    title : '分组1',
                    height : 250,
                    items : [{name : 'id',hidden : true},'xh','xm','yyxId']
                },
                {xtype : 'fieldset',
                    title : '分组2',
                    columns : 1,
                    items : [
                        'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
                    ]},
                {xtype : 'fieldset',
                    title : '分组2',
                    columns : 3,
                    colspan : 2,
                    items : [
                        'yzy','ybjId','ynj'
                    ]}
            ]
        });
        var values = this.grid.getSelectRows()[0];
        form.setValues(values);
        var window  = this.window = new NS.window.Window({
            width : 900,
            height : 600,
            layout : 'fit',
            items : form,
            autoShow: true
        });
        form.bindItemsEvent({
            'update' : {event : 'click',fn : this.submitUpdate,scope : this},
            'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
        });
    },
    /**
     * 提交修改form
     */
    submitUpdate : function(){
        var form = this.form;
        this.baseUpdate('A',this.form.getValues(),function(data){

        },this);
    }
});