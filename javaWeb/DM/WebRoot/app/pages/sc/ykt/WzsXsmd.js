/**
 * 未住宿预警名单
 * User: zhangzg
 * Date: 14-9-10
 * Time: 上午10:02
 *
 */
NS.define('Pages.sc.ykt.WzsXsmd',{
    extend:'Pages.sc.ykt.WgXsmd',
    modelConfig: {
        serviceConfig: {
            queryGridContent: {
                service:"bzksWzsService?queryGridContent",
                params:{
                    limit:10,
                    start:0
                }
            },
            getYxgly:'bzxService?queryYxgly',// 获取院系管理员
            getYxglyJszt:'bzksWzsService?queryYxglyJszt',// 管理员邮件接受状态grid
            sendMails:'bzksWzsService?sendMail',// 发送email
            getBzxMd : 'bzksWzsService?queryWzsMd'// 获得未住宿学生名单
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
                pageName:'疑似未住宿学生名单',
                pageHelpInfo:'<span style="color: #ff0040;margin-left: 20px;">系统每天将【有一卡通消费数据没有门禁刷卡记录】以及【一卡通门禁都有记录但消费记录晚于刷卡记录】的学生记录下来，' +
                    '管理员可以按日期查询每天疑似未住宿的学生名单。系统将自动发送名单到指定的邮箱。</span>'}
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
        title : "院系-疑似未住宿学生名单",
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
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig : function(){
        var arrays = this.gridFields;
        var textarrays = "院系,在校学生数,日期,未住宿学生数,占比,消息推送状态,ID".split(",");
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
    getColumnCfgsBzxmd:function(gridFields){
        var textarrays = "学号,姓名,院系,专业,宿舍,当日最后一次消费,之前最后一次出入,统计日期,学生联系电话,ID".split(",");
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
    xqGrid : function(){
        var rowData = this.tplGrid.getSelectRows()[0],me = this;
        var params = {yxid : rowData.ID,start:0,limit:15};
        params = Ext.apply(params,this.params);
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
                title:'疑似未住宿学生名单',
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
    exportMd : function(queryGridParams){
        // 导出
        var serviceAndParams ={
            servicAndMethod:'bzksWzsService?getWzsMdExport',
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
                            NS.Msg.warning("提示","没有昨日未住宿学生，无需通知管理员！");
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
    }
});