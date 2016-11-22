/**
 *
 */
NS.define('Manager.Announcement',{
    extend : 'Template.Page',
    modelConfig : {
        serviceConfig : {
            'query' : {
                service : "annt_queryAnnouncement",
                params : {start : 0,limit : 25}
            },
            add : 'annt_addAnnouncement',
            delete1 : 'annt_removeAnnouncement',
            update : 'annt_updateAnnouncement'
        }
    },
    dataRequires : [
        {fieldname : 'initTableData',key : 'query'}
    ],
    init : function(){
        this.initComponent();
    },
    initComponent : function(){
        var grid = this.initGrid();
        var tbar = this.initTbar();
        var container = new NS.container.Panel({
            layout : 'fit',
            tbar : tbar,
            items : [grid]
        });
        this.setPageComponent(container);
        grid.refresh();
    },
    initGrid : function(){
        var grid  = this.grid = new NS.grid.Grid({
            data : this.initTableData,
            autoScroll: true,
            proxy : this.model,
            serviceKey : 'query',
            multiSelect: true,
            height : '60%',
            lineNumber: true,
            fields : [
                "id","title","content","jzg_id","jzg_xm","create_time","send_time","read_times","zzjg_id"
            ],
            columnConfig : this.getColumnConfig()
        });
        return grid;
    },
    /**
     * Column配置
     */
    getColumnConfig : function(){
        var sxarray = ["id","title","content","jzg_id","jzg_xm","read_times","send_time","create_time","zzjg_id"];
        var textarray = ['id','标题','内容','教职工ID','教职工姓名','阅读次数','发布时间','创建时间','部门ID'];
        var widtharrays = [120,160,380,120,120,120,160,160,120];
        var hiddenarrays = [true,false,false,true,true,false,false,true,true];
        var columns = [];
        for(var i=0;i<sxarray.length;i++){
            var name = sxarray[i];
            var basic = {
                xtype : 'column',
                name : sxarray[i],
                text : textarray[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            if(name == 'title'){
                basic.renderer = function(v){
                    return NS.String.ellipsis(v,70);
                };
            }else if(name = 'content'){
                basic.renderer = function(v){
                    return NS.String.ellipsis(v,100);
                };
            }
            columns.push(basic);
        }
        return columns;
    },
    initTbar : function(){
        var getBtn = function(text,name,cls){return new NS.button.Button({text:text,name : name,iconCls : cls});}
        var addButton = getBtn('发布公告','save','page-save');
        var delButton = getBtn('删除公告','delete1','page-delete');
        var updButton = getBtn('修改公告','update','page-update');
        var basic = {
            items: [
                addButton,updButton,delButton
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
            'save': {event: 'click', fn: this.saveAt, scope: this},
            'delete1': {event: 'click', fn: this.deleteAt, scope: this},
            'update': {event: 'click', fn: this.updateAt, scope: this}
        });
        return tbar;
    },
    saveAt : function(){
        this.createForm('save');
    },
    deleteAt : function(){
        var grid = this.grid;
        this.deleteWithGrid({
            grid : grid,
            serviceKey : 'delete1',
            info : "公告",
            controller : this
        });
    },
    updateAt : function(){
        var row = this.grid.getSelectRows();
        if(row.length == 0){
            NS.Msg.info("请您选中一行记录！");
        }else{
            var form = this.createForm('update');
            form.setValues(row[0]);
        }
    },
    createForm : function(type){
        var createText = function(fieldLabel,name,colspan,hidden,width){
                return new NS.form.field.Text({name : name,labelWidth:60,width : width||300,fieldLabel : fieldLabel,colspan : colspan,hidden : hidden});
        };
        var sendTime = new NS.form.field.DateTime({
            format : 'Y-m-d H:i:s',
            width : 300,
            name : 'send_time',
            hidden : true,
            labelWidth:60,
            fieldLabel : '发送时间'
        });
        var content = new NS.form.field.HtmlEditor({
            name : 'content',
            width : 600,
            colspan : 2,
            labelWidth:60,
            height : 300,
            fieldLabel : '内容'
        });
        var form = new NS.form.BasicForm({
            frame : true,
            border : false,
            width : 650,
            height : 500,
            modal : true,
//            autoShow : true,
            layout : {type : 'table',columns : 2},
            items : [
                createText('id','id',1,true),
                createText('标题','title',2,false,600),
                sendTime,
                createText('创建时间','create_time',2),
                createText('阅读次数','read_times',1),
                content
            ],
            buttons : [
                {text : '提交',name : 'submit'},
                {text : '取消',name : 'cancel'}
            ]
        });
        form.getField('read_times').setReadOnly(true);
        form.getField('read_times').hide();
        form.getField('read_times').setValue(0);
        form.getField('create_time').setReadOnly(true);
        form.show();
        form.bindItemsEvent({
            submit : {event : 'click',fn : function(){this.submit(form,type);},scope : this},
            cancel : {event : 'click',fn : function(){this.cancel(form);},scope : this}
        })
        return form;
    },
    submit : function(form,type){
        var values = form.getValues();
        values.htmleditFlag = true;
        var service = 'add';
        if(type == 'update') service = 'update';
        this.saveWithForm({
            form : form,
            serviceKey : service,
            info : "公告信息",
            grid : this.grid,
            params : values,
            controller : this
        });
    },
    cancel : function(form){
        form.close();
    }

});