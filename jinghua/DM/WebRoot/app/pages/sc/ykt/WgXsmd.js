/**
 * 入寝晚归学生名单
 * User: zhangzg
 * Date: 14-9-10
 * Time: 上午10:02
 *
 */
NS.define('Pages.sc.ykt.WgXsmd',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryGridContent: {
                service:"bzksWgService?queryGridContent",
                params:{
                    limit:10,
                    start:0
                }
            },
            getYxgly:'bzxService?queryYxgly',// 获取院系管理员
            getYxglyJszt:'bzksWgService?queryYxglyJszt',// 管理员邮件接受状态grid
            sendMails:'bzksWgService?sendMail',// 发送email
            getBzxMd :  {
                service:'bzksWgService?queryWgMd',
                params:{
                    limit:10,
                    start:0
                }
            }// 获得晚寝晚归名单
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
                pageName:'晚寝晚归学生名单',
                pageHelpInfo:'<span style="color: #ff0040;margin-left: 20px;">系统每日记录22:30以后入寝刷卡的学生名单，管理员可以按日期查询晚归学生名单，系统将自动将本周内的晚归学生名单发送到指定的邮箱。</span>'}
        });
        pageTitle.on('click',function(){
            var params = {dcqb:true};
            params = Ext.apply(params,me.params);
            me.exportMd(params);
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

        var gridFields = this.gridFields = ["YX","ZCZX","RQ","WGRS","ZB","XXTSZT","ID"];
        var columnCfgs = this.convertColumnConfig();
        this.createGrid(gridFields,columnCfgs);



    },
    title1 : new Exp.chart.PicAndInfo({
        title : "院系-晚寝晚归学生名单",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    dateQuery : Ext.create('Ext.form.field.Date', {
        width:200,
        fieldLabel: '选择日期',
        labelWidth:60,
        name: 'from_date',
        format:'Y-m-d',
        maxValue:new Date(),
        editable:false
    }),
    getDateStr:function(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        /*var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        return y+"/"+m+"/"+d;*/
        return dd;
    },
    /**
     * 创建主题容器
     */
    createMain:function(){
        var container = new Ext.container.Container({
            items:[this.title1,this.dateQuery]
        });
        this.dateQuery.on('change',function(compo,newvalue,oldvalue){
            this.params.RQ = compo.getRawValue();
            if(this.tplGrid){
                this.tplGrid.load(this.params);
            }

        },this);
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
        var yesterday = this.getDateStr(-1);
        this.params.RQ = yesterday;
        this.dateQuery.setValue(yesterday);
    },
    createGrid:function(gridFields,columnCfgs){
        var params = {start:0,limit:100};
        Ext.apply(params,this.params);
        this.callService([{key:'queryGridContent',params:params}],
            function(respData){
                this.tplGrid = this.initXqGrid(respData.queryGridContent,gridFields,columnCfgs,this.params,'queryGridContent',false,false,100);
                this.mainContainer.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });

                this.tplGrid.bindItemsEvent({
                    'WGRS' :{event:'linkclick',fn:this.xqGrid,scope:this},
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
        var textarrays = "院系,在校学生数,日期,晚寝晚归学生数,占比,消息推送状态,ID".split(",");
        var widtharrays = [150,75,110,90,75,160,20];
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
                case 'WGRS':
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
        params = Ext.apply(params,this.params);
        this.callService({key:'getBzxMd',params:params},function(respData){
            var data = respData.getBzxMd;
            var gridFields = ["XH","XM","YX","ZY","ZSDD","SCMJSJ","tjsj","LXDH","ID"];
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
                title:'晚寝晚归学生名单',
                layout:'fit',
                modal:true,
                width:780,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show();

        });
    },
    ckfj : function(){
        var data = this.dxgrid.getSelectRows()[0];
        var params = {
            yxid:this.ckxqParams.yxid,
            RQ:data.JSRQ
        };
        this.exportMd(params);
    },
    ckfj2 : function(){
        this.exportMd(this.ckxqParams);

    },
    exportMd : function(queryGridParams){
        // 导出
        var serviceAndParams ={
            servicAndMethod:'bzksWgService?getWgMdExport',
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
            var params = this.ckxqParams = {yxid : rowData.ID};
            this.ckxqParams.RQ = this.params.RQ;
            this.callService({key:'getYxglyJszt',params:params},function(respData){
                var data = respData.getYxglyJszt;
                var gridFields = ["XM","YX","DZXX","JSZT","JSRQ","ZGH","LXDH","FJ","ID"];
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

                dxgrid.bindItemsEvent({
                    'FJ' :{event:'linkclick',fn:this.ckfj,scope:this}
                });
            });
        }else{
            var rowData = this.tplGrid.getSelectRows()[0];
            var params = this.ckxqParams = {yxid : rowData.ID};
            this.ckxqParams.rs = rowData.WGRS;
            this.ckxqParams.RQ = this.params.RQ;
            this.callService({key:'getYxgly',params:params},function(respData){
                var data = respData.getYxgly;
                var gridFields = ["XM","YX","DZXX","ZGH","LXDH","FJ","ID"];
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
                        if(rowData.WGRS==0){
                            NS.Msg.warning("提示","没有晚归学生，无需通知管理员！");
                            return;
                        }

                        me.sendMail(params);
                    } },{ text: '取消',handler:function(compo){
                        me.closeWindow();
                    } }]
                });
                dxgrid.bindItemsEvent({
                    'FJ' :{event:'linkclick',fn:this.ckfj2,scope:this}
                });
                this.win.show()
            });

            this.tplGrid.refresh();
        }
    },
    getColumnCfgs:function(gridFields){
        var textarrays = "收件人,所属院系,电子信箱,职工号,联系电话,附件,ID".split(",");
        var widtharrays = [150,75,75,80,80,75,20];
        var hiddenarrays = [false,false,false,false,false,false,true];
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
                case 'FJ':
                    Ext.apply(basic,{
                        xtype : 'linkcolumn',
                        renderer:function(data){
                            return '<a href="javascript:void(0);">查看附件</a>';
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
    getColumnCfgsBzxmd:function(gridFields){
        var textarrays = "学号,姓名,院系,专业,宿舍,门禁刷卡时间,统计日期,学生联系电话,ID".split(",");
        var widtharrays = [80,100,80,80,80,150,80,80,20];
        var hiddenarrays = [false,false,false,false,false,false,false,false,true];
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
        var textarrays = "收件人,所属院系,电子信箱,接受状态,接收日期,职工号,联系电话,附件,ID".split(",");
        var widtharrays = [150,75,75,80,80,80,80,75,20];
        var hiddenarrays = [false,false,false,false,false,false,false,false,true];
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
                case 'FJ':
                    Ext.apply(basic,{
                        xtype : 'linkcolumn',
                        renderer:function(data){
                            return '<a href="javascript:void(0);">查看附件</a>';
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
                if(respData.sendMails.success){
                    this.tplGrid.refresh();
                }else{
                    NS.Msg.warning("警告","指定日期的数据还未统计完毕，请在系统统计完毕后操作！");
                }

            });
        }

    },
    closeWindow:function(){
        this.win.close();
    }
});