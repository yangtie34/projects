/**
 * 不在校学生预警名单
 * User: zhangzg
 * Date: 14-9-10
 * Time: 上午10:02
 *
 */
NS.define('Pages.sc.ykt.BzxXsmd',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryGridContent: {
                service:"bzxService?queryGridContent",
                params:{
                    limit:10,
                    start:0
                }
            },
            export4yx :'exportBzxService?export4Yx',
            getYxgly:'bzxService?queryYxgly',
            getYxglyJszt:'bzxService?queryYxglyJszt',
            sendMails:'bzxService?sendMail',
            getBzxMd : 'bzxService?getBzxMd'
        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins:[],
    requires:[],
    params:{},
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'疑似不在校学生名单',
                pageHelpInfo:'<span style="color: #ff0040;margin-left: 20px;">三天或三天以上既没有一卡通消费数据也没有门禁数据的学生，将被列入疑似不在校学生名单中。' +
                    '系统在每周二、周五自动将疑似不在校学生名单发送到指定的邮箱中。</span>'}
        });
        pageTitle.on('click',function(){
            me.exportMd({dcqb:true});
        });
        var mainContainer = this.mainContainer = this.createMain();

        var page = this.page = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,mainContainer]
        });
        this.setPageComponent(page);

        this.initParams();
        this.fillCompoByData();

        var gridFields = this.gridFields = ["YX","ZCZX","LXSX","BZX","ZB","XXTSZT","ID"];
        var columnCfgs = this.convertColumnConfig();
        this.createGrid(gridFields,columnCfgs);



    },
    title1 : new Exp.chart.PicAndInfo({
        title : "院系名单列表",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    /**
     * 创建主题容器
     */
    createMain:function(){
        var container = new Ext.container.Container({
            items:[this.title1]
        });
        return container;
    },
    /**
     * 数据填充组件
     */
    fillCompoByData:function(){
        if(this.tplGrid){
            this.tplGrid.load(this.params);
        }
    },
    initParams:function(){

    },
    createGrid:function(gridFields,columnCfgs){
        var params = {start:0,limit:100};
        Ext.apply(this.params,params);
        this.callService([{key:'queryGridContent',params:this.params}],
            function(respData){
                this.tplGrid = this.initXqGrid(respData.queryGridContent,gridFields,columnCfgs,this.params,'queryGridContent',false,false,100);
                this.mainContainer.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });

                this.tplGrid.bindItemsEvent({
                    'BZX' :{event:'linkclick',fn:this.xqGrid,scope:this},
                    'XXTSZT' :{event:'linkclick',fn:this.xxts,scope:this}
                });
            }
        );
    },
    /**
     * 初始化Grid
     */
    initXqGrid : function(data,fields,columnConfig,queryParams,serviceKey,multiSelec,paging,pagesize){
        var lineNumber = Boolean(multiSelec)==true?false:true;
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : pagesize||100,
            proxy : this.model,
            serviceKey:{
                key:serviceKey,
                params:queryParams
            },
            multiSelect: multiSelec||false,
            lineNumber:lineNumber,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: multiSelec||false,
            paging:paging||false
        });
        return grid;
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig : function(){
        var arrays = this.gridFields;
        var textarrays = "院系,在校学生数,实习学生数,疑似不在校数,不在校占比,消息推送状态,ID".split(",");
        var widtharrays = [150,75,75,80,75,160,20];
        var hiddenarrays = [false,false,false,false,false,false,true];
        var columns = [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name : arrays[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            switch(arrays[i]){
                case 'ZB':
                    Ext.apply(basic,{
                        renderer:function(data){
                            var str = data+"%";
                            if(Number(data)>20){
                                str="<span style='color: red;'>"+data+"%</span>";
                            }
                            return str;
                        }
                    });
                    break;
                case 'BZX':
                    Ext.apply(basic,{
                        xtype : 'linkcolumn',
                        renderer:function(data){
                            return '<a href="javascript:void(0);">'+data+'</a>';
                        }
                    });
                    break;
                case 'XXTSZT':
                    Ext.apply(basic,{
                        xtype : 'linkcolumn',
                        renderer:function(data){
                            if(Number(data)==1){
                                return '<span style="color: green;">已发送</span>，<a href="javascript:void(0);">查看详细</a>';
                            }else if(Number(data)==0){
                                return '<span style="color: red;">未发送</span>，<a href="javascript:void(0);">发Email</a>';
                            }else{
                                return '<span style="color: red;">发送失败</span>，<a href="javascript:void(0);">补发Email</a>';
                            }

                        }
                    });
                    break;
                default:
                    break;
            }
            columns.push(basic);
        }
        return columns;
    },

    xqGrid : function(){
        var rowData = this.tplGrid.getSelectRows()[0],me = this;
        var params = {yxid : rowData.ID,start:0,limit:15};
        this.callService({key:'getBzxMd',params:params},function(respData){
            var data = respData.getBzxMd;
            var gridFields = ["XH","XM","YX","ZY","ZSDD","SCXFSJ","SCMJSJ","tjsj","LXDH","ID"];
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getBzxMd',false,true,15);
            var exportBtn1 = new NS.button.Button({
                text : "导出名单",
                handler : function(){
                    me.exportMd(params);
                },
                iconCls : "page-excel",
                border:true
            });
            var text = new NS.form.field.Text({
                width:240,
                fieldLabel:'学生姓名',
                labelWidth:60,
                emptyText:'输入姓名检索'
            });
            text.on('change',function(value,oldVal,newVal){
                dxgrid.load({xm:oldVal});
            });
            // 未填写教学日志导出
            var tbar = new NS.toolbar.Toolbar({
                items:[text,'->',exportBtn1]
            });
            this.win = new NS.window.Window({
                title:'不在校学生名单',
                layout:'fit',
                modal:true,
                width:780,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show()
        });
    },
    exportMd : function(queryGridParams){
        // 导出
        var serviceAndParams ={
            servicAndMethod:'bzxService?getBzxMdExport',
            params:queryGridParams
        } ;
        var strParams = JSON.stringify(serviceAndParams);
        window.document.open("customExportAction.action?serviceAndParams="+strParams,
            '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
    },
    xxts : function(text){
        var me = this;
        if(text=='查看详细'){
            var rowData = this.tplGrid.getSelectRows()[0];
            var params = {yxid : rowData.ID};
            this.callService({key:'getYxglyJszt',params:params},function(respData){
                var data = respData.getYxglyJszt;
                var gridFields = ["XM","YX","DZXX","JSZT","JSRQ","ZGH","LXDH","ID"];
                var columnCfgs = this.getColumnCfgsxq(gridFields);
                // 创建详情grid
                var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getYxglyJszt',false);
                this.win = new NS.window.Window({
                    title:'邮件接受详情',
                    layout:'fit',
                    modal:true,
                    width:700,
                    height:150,
                    items:dxgrid,
                    buttons:[]
                });
                this.win.show()
            });
        }else{
            var rowData = this.tplGrid.getSelectRows()[0];
            var params = {yxid : rowData.ID};
            this.callService({key:'getYxgly',params:params},function(respData){
                var data = respData.getYxgly;
                var gridFields = ["XM","YX","DZXX","ZGH","LXDH","ID"];
                var columnCfgs = this.getColumnCfgs(gridFields);
                // 创建待选grid
                var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getYxgly',false);
                this.win = new NS.window.Window({
                    title:'管理员列表',
                    layout:'fit',
                    modal:true,
                    width:700,
                    height:150,
                    items:dxgrid,
                    buttons:[{ text: '发送',handler:function(compo){
                        me.sendMail(params);
                    } },{ text: '取消',handler:function(compo){
                        me.closeWindow();
                    } }]
                });
                this.win.show()
            });

            this.tplGrid.refresh();
        }
    },
    getColumnCfgs:function(gridFields){
        var textarrays = "收件人,所属院系,电子信箱,职工号,联系电话,ID".split(",");
        var widtharrays = [150,75,75,80,75,20];
        var hiddenarrays = [false,false,false,false,false,true];
        var columns = [];
        for(var i=0;i<gridFields.length;i++){
            var basic = {
                xtype : 'column',
                name : gridFields[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            columns.push(basic);
        }
        return columns;
    },
    getColumnCfgsBzxmd:function(gridFields){
        var textarrays = "学号,姓名,院系,专业,宿舍,上次消费时间,上次出入时间,统计日期,学生联系电话,ID".split(",");
        var widtharrays = [80,100,80,80,80,150,150,80,80,20];
        var hiddenarrays = [false,false,false,false,false,false,false,false,false,true];
        var columns = [];
        for(var i=0;i<gridFields.length;i++){
            var basic = {
                xtype : 'column',
                name : gridFields[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            columns.push(basic);
        }
        return columns;
    },
    getColumnCfgsxq:function(gridFields){
        var textarrays = "收件人,所属院系,电子信箱,接受状态,接收日期,职工号,联系电话,ID".split(",");
        var widtharrays = [150,75,75,80,80,80,75,20];
        var hiddenarrays = [false,false,false,false,false,false,false,true];
        var columns = [];
        for(var i=0;i<gridFields.length;i++){
            var basic = {
                xtype : 'column',
                name : gridFields[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            switch(gridFields[i]){

                case 'JSZT':
                    Ext.apply(basic,{
                        xtype : 'linkcolumn',
                        renderer:function(data){
                            if(Number(data)==1){
                                return '<span style="color: green;">已接受</span>';
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
            columns.push(basic);
        }
        return columns;
    },
    sendMail:function(params){
        this.win.close();
        var mask = new NS.mask.LoadMask({
           target : this.page,
               msg : '邮件发送中...'
           });
        mask.show();

        var sjrs = this.dxgrid.getAllRow().length;
        if(sjrs==0){
            NS.Msg.warning("无收件人！");
        }else{
            this.callService({key:'sendMails',params:params},function(respData){
                mask.hide();
                this.tplGrid.refresh();
            });
        }

    },
    closeWindow:function(){
        this.win.close();
    }
});