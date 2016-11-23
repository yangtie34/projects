NS.define('Manager.ProgrammerReplySuggest',{
    extend : 'Template.Page',
    modelConfig : {
        serviceConfig : {
            'query' : {
                service : "suggest_queryAllSuggest",
                params : {start : 0,limit : 25}
            },
            'header' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TsMsgSuggest'}},
            reply : 'suggest_updateSuggest'
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
                    name : 'createrName',
                    text : '意见人',
                    width : 100,
                    index : 0
                },
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
                {xtype: 'button', text: '回复', name: 'reply',buttonType : 'button_add',iconCls : 'page-update'},
                single
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
            'reply': {event: 'click', fn: this.reply, scope: this},
        });
        return tbar;
    },
    reply : function(){
        var rows = this.grid.getSelectRows();
        if(rows.length == 0){
            NS.Msg.info("请选择至少一行数据！");
        }else{
            var window = this.createShowWindow('reply');
            window.queryComponentByName('title').setReadOnly(true);
            window.show();
            this.saveForm.setValues(rows[0]);
        }
    },
    /**
     * 为修改和新增创建窗体
     */
    createShowWindow : function(serviceKey){
        var c = function(name){return new NS.form.field.Text({name : name,xtype : 'text',hidden : true})};
        var ce = function(name,label,config){var basic = {name : name,fieldLabel : label,labelWidth: 70,colspan : 2,width : 600};NS.apply(basic,config);return new NS.form.field.HtmlEditor(basic);};
        var form  = this.saveForm = NS.form.EntityForm.create({
            data : this.tranData,
            autoScroll : true,
            columns : 1,
            formType : 'add',
            fieldConfig : {width : 330},
            margin : '5px',
            items : [
                {name :'title',colspan : 2,width : 740,labelWidth:69 },
                ce('content','问题及建议',{height : 270,width : 740,editable : false}),
                {name : 'createDate',hidden : true},
                {name : 'isReply',hidden : true},
                ce('replyContent','反馈内容',{height : 270,width : 740}),
                {name :  'replyDate',hidden : true},
                c('sfky'),c('sfmy'),c('id'),c('createrName'),c('createrId')
            ]
        });
        var window = new NS.window.Window({
            width : 798,
            height : 660,
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
                    NS.Msg.info("意见和建议回复成功!");
                    window.close();
                    this.grid.refresh();
                }else{
                    NS.Msg.info("意见和建议回复回复失败，请联系管理员!");
                }
            });
        };
    }
})