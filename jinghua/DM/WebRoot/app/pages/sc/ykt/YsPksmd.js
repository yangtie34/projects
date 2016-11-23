/**
 * 疑似贫困生预警
 * User: zhangzg
 * Date: 15-2-10
 * Time: 下午4:38
 *
 */
NS.define('Pages.sc.ykt.YsPksmd',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryYxzzjgTree: 'scService?getJxzzjgTree',// 获取导航栏数据
            queryGridContent: {
                service:"ysPksService?queryGridContent",
                params:{
                    limit:20,
                    start:0
                }
            },
            export4yx :'exportBzxService?export4Yx',
            getYxgly:'ysPksService?queryChartData',
            getYxglyJszt:'bzxService?queryYxglyJszt',
            sendMails:'bzxService?sendMail',
            getBzxMd : 'ysPksService?queryGridContent4xq'
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
                pageName:'低消费学生名单',
                pageHelpInfo:'<span style="color: #ff0040;margin-left: 20px;">系统依据近三个月在餐厅就餐的学生消费明细，分析出长期处于低消费水平的学生名单。</span><br>'/* +
                    '<span style="margin-left: 40px;">统计方式：</span><br>' +
                    '<span style="margin-left: 50px;">第一步：分男、女计算近三个月学生吃早餐、午餐、晚餐的每日的平均消费值，并计算每位学生的当日、当次餐费是否为低消费</span><br>' +
                    '<span style="margin-left: 50px;">第二步：利用均值与中位值确定“经常吃饭的”这个标准的阈值，例如3个月180顿饭，在餐厅吃86顿以上的学生即为经常吃饭的学生，86即为阈值。</span><br>' +
                    '<span style="margin-left: 50px;">第三步：将经常在餐厅吃饭，并且总是处于低消费水平的学生计算出来。即低消费次数占消费次数的70%以上。</span>'*/}
        });
        pageTitle.on('click',function(){
            me.exportMd({dcqb:true});
        });
        var top = new NS.container.Container({
            layout:"column",
            style:{
                "padding" : "5px",
                "margin-bottom" : "10px"
            },
            items : [this.top1,this.top2]
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var mainContainer = this.mainContainer = this.createMain();

        var page = this.page = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,top,this.title1,navigation,mainContainer]
        });
        this.setPageComponent(page);
        this.callService({key:'getYxgly',params:{}},function(data){
            var tt = data.getYxgly;
            this.top1.update(tt);
            this.top2.update(tt);
        },this);
        // 刷新导航栏
        this.callService('queryYxzzjgTree',function(data){
            this.navigation.refreshTpl(data.queryYxzzjgTree);

            var i = 0;
            for(var key in data.queryYxzzjgTree){
                if(i==0){
                    var nodeId = data.queryYxzzjgTree[key].id;
                    this.navigation.setValue(nodeId);
                    this.params.zzjgId = nodeId;
                }
                i++;
            }

            var gridFields = this.gridFields = ["XH","XM","YX","ZY","XB","RXNJ","CFCS","BDBS","BDBL","ID"];
            var columnCfgs = this.convertColumnConfig();
            this.createGrid(gridFields,columnCfgs);
        },this);

        navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;
            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;

            me.fillCompoByData();
        });

    },
    title1 : new Exp.chart.PicAndInfo({
        title : "低消费学生名单",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    /**
     * 创建主题容器
     */
    createMain:function(){
        var container = new Ext.container.Container({
            margin:'2 0 0 0',
            items:[]
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
    createGrid:function(gridFields,columnCfgs){
        var params = {start:0,limit:20};
        Ext.apply(this.params,params);
        this.callService([{key:'queryGridContent',params:this.params}],
            function(respData){
                this.tplGrid = this.initXqGrid(respData.queryGridContent,gridFields,columnCfgs,this.params,'queryGridContent',false,true,20);
                this.mainContainer.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });

                this.tplGrid.bindItemsEvent({
                    'CFCS' :{event:'linkclick',fn:this.xqGrid,scope:this}
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
            pageSize : pagesize||20,
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
        var textarrays = "学号,姓名,院系,专业,性别,年级,吃饭次数,低消费次数,不达标率,ID".split(",");
        var widtharrays = [100,75,75,80,60,70,70,75,100,20];
        var hiddenarrays = [false,false,false,false,false,false,false,false,false,true];
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
                case 'BDBL':
                    Ext.apply(basic,{
                        renderer:function(data){
                            var str = data+"%";
                            if(Number(data)>98){
                                str="<span style='color: red;'>"+data+"%</span>";
                            }
                            return str;
                        }
                    });
                    break;
                case 'CFCS':
                    Ext.apply(basic,{
                        xtype : 'linkcolumn',
                        renderer:function(data){
                            return '<a href="javascript:void(0);">'+data+'</a>';
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
        var params = {XH : rowData.XH,start:0,limit:15};
        this.callService({key:'getBzxMd',params:params},function(respData){
            var data = respData.getBzxMd;
            var gridFields = ["XH","XM","XFRQ","CBDM","ZXFJE","PJ2","SFDB","ID"];
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getBzxMd',false,true,15);

            this.win = new NS.window.Window({
                title:rowData.XM+' 近三个月消消费明细',
                layout:'fit',
                modal:true,
                width:780,
                height:480,
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
    getColumnCfgsBzxmd:function(gridFields){
        var textarrays = "学号,姓名,消费日期,餐别,消费金额,当日平均消费,是否计入低消费,ID".split(",");
        var widtharrays = [80,100,80,80,80,80,80,20];
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
                case 'SFDB':
                    Ext.apply(basic,{
                        renderer:function(data){
                            var str = '';
                            if(Number(data)==1){
                                str="<span>否</span>";
                            }else{
                                str="<span style='color: red;'>是</span>";
                            }

                            return str;
                        }
                    });
                    break;
                case 'CBDM':
                    Ext.apply(basic,{
                        renderer:function(data){
                            var str = '';
                            if(Number(data)==1){
                                str="<span style='color: green;'>早餐</span>";
                            }else if(Number(data)==2){
                                str="<span style='color: #daa520;'>午餐</span>";
                            }else{
                                str="<span style='color: #000000;'>晚餐</span>";
                            }

                            return str;
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
    closeWindow:function(){
        this.win.close();
    },
    top1 : new Ext.Component({
        tpl : '<div style="border-radius: 100%;background-color: #ff6666;width: 150px;height: 150px;color:#fff;text-align: center;">' +

            '<span style="font-size: 40px;line-height: 60px;">{w_je}</span>  <span style="font-size: 20px;">元</span>' +
            '<br/><span style="font-size: 12px;">女生餐均消费</span><br>' +
            '<br><span style="font-size: 12px;">早：{w_zaocje}</span>' +
            '<br><span style="font-size: 12px;">午：{w_wucje}</span>' +
            '<br><span style="font-size: 12px;">晚：{w_wancje}</span>' +
            '</div>',
        data : [],
        margin : '0px 0px 10px 0px',
        columnWidth: 1/2
    }),

    top2 : new Ext.Component({
        tpl : '<div style="border-radius: 100%;background-color: #45DF45;width: 150px;height: 150px;color:#fff;text-align: center;">' +

            '<span style="font-size: 40px;line-height: 60px;">{m_je}</span><span style="font-size: 20px;">元</span>' +
            '<br/><span style="font-size: 12px;">男生餐均消费</span><br>' +
            '<br><span style="font-size: 12px;">早：{m_zaocje}</span>' +
            '<br><span style="font-size: 12px;">午：{m_wucje}</span>' +
            '<br><span style="font-size: 12px;">晚：{m_wancje}</span>' +
            '</div>',
        data : [],
        margin : '0px 0px 10px 0px',
        columnWidth: 1/2
    })
});