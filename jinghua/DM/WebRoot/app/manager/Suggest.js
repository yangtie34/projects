NS.define('Manager.Suggest',{
    extend : 'Template.Page',
    modelConfig : {
        serviceConfig : {
            'query' : {
                service : "suggest_queryMineSuggest",
                params : {start : 0,limit : 25}
            },
            'header' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TsMsgSuggest'}},
            add : 'suggest_addSuggest',
            delete : 'suggest_deleteSuggest',
            update : 'suggest_updateSuggest'
        }
    },
    dataRequires : [
        { fieldname : 'header',key :'header'},
        { fieldname : 'data',key :'query'}
    ],
    init : function(){
        this.tranData = NS.E2S(this.header);
        this.initComponent();
    },
    initComponent : function(){
        var grid = this.initGrid();
        var tbar = this.initTbar(grid);
        var container = new NS.container.Panel({
            tbar : tbar,
            layout : 'fit',
            items :grid
        });
        this.setPageComponent(container);
    },
    initGrid : function(){
        var fields = ['id','createrName','createrId','sfky','sfmy','replyContent','content'];
        var grid = this.grid = new NS.grid.Grid({
            data : this.data,
            columnData : this.tranData,
            serviceKey : 'query',
            fields : fields,
            multiSelect: false,
            proxy : this.model,
            checked : true,
            columnConfig : [
                {
                    name : 'replyContent',
                    text : '回复内容',
                    width : 300,
                    index : 3
                },
                {
                    name : 'content',
                    width : 300,
                    text : '建议内容',
                    index : 1
                }
            ]
        });
        return grid;
    },
    initTbar : function(grid){
        var single = new NS.grid.query.SingleFieldQuery({
            data : this.tranData,
            grid : grid
        });
        var basic = {
            items: [
                {xtype: 'button', text: '新增', name: 'add',buttonType : 'button_add',iconCls : 'page-add'},
                {xtype: 'button', text: '查看回复', name: 'see',buttonType : 'button_add',iconCls : 'page-update'},
                single
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
            'add': {event: 'click', fn: this.add, scope: this},
            'see': {event: 'click', fn: this.see, scope: this}
        });
        return tbar;
    },
    add : function(){
        var window = this.createShowWindow('add');
        window.show();
    },
    see : function(){
        var rows = this.grid.getSelectRows();
        if(rows.length == 0){
            NS.Msg.info("请选择至少一行数据！");
        }else{
            var row = rows[0];
            if(row.isReply == "true"){
                var window = this.createShowWindow('see');
                var content = window.queryComponentByName('content');
                var replyContent = window.queryComponentByName('replyContent');
                var saveBtn = window.queryComponentByName('save');
                saveBtn.hide();

                content.setHeight(270);
                replyContent.setHeight(270);

                replyContent.show();
                this.saveForm.setValues(row);
                window.show();

                replyContent.setReadOnly(true);
            }else{
                NS.Msg.info("您选择的信息尚未回复！");
            }
        }
    },
    /**
     * 为修改和新增创建窗体
     */
    createShowWindow : function(serviceKey,formType){
        var c = function(name){return new NS.form.field.Text({name : name,xtype : 'text',hidden : true})};
        var ce = function(name,label,config){var basic = {name : name,fieldLabel : label,labelWidth: 70,colspan : 2,width : 600};NS.apply(basic,config);return new NS.form.field.HtmlEditor(basic);};
        var form  = this.saveForm = NS.form.EntityForm.create({
            data : this.tranData,
            autoScroll : true,
            columns : 1,
            formType :formType,
            fieldConfig : {width : 330},
            margin : '5px',
            items : [
                {name :'title',colspan : 2,width : 740,labelWidth:69,allowBlank : false },
                ce('content','问题及建议',{height : 520,width : 740,allowBlank : false}),
                {name : 'createDate',hidden : true},
                {name : 'isReply',hidden : true},
                ce('replyContent','反馈内容',{hidden : true,value : '',width : 740,allowBlank : false}),
                {name :  'replyDate',hidden : true},
                c('sfky'),c('sfmy'),c('id'),c('createrName'),c('createrId')
            ]
        });
        var window = new NS.window.Window({
            width : 798,
            height : 670,
            layout : 'fit',
            items : [form],
            buttons : [
                {text : '保存',name : 'save'},{text : '取消',name : 'cancel'}
            ]
        });
        window.bindItemsEvent({
            save : {event : 'click',scope : this,fn : function(){
                this.doSubmit(form,window,serviceKey);
            }},
            cancel : {event : 'click',scope : this,fn : function(){
                window.close();
            }}
        });
        return window;
    },
    doSubmit : function(form,window,serviceKey){
        if(form.isValid()){
            var values = form.getValues();
            NS.apply(values,{htmleditFlag : true});
            this.callSingle({key : serviceKey,params : values},function(data){
                if(data.success){
                    NS.Msg.info("意见和建议保存成功!");
                    window.close();
                    this.grid.refresh();
                }else{
                    NS.Msg.info("意见和建议保存失败，请联系管理员!");
                }
            });
        };
    }
});