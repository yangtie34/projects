/**
 * 一卡通分时段分类型统计。
 * User: zhangzg
 * Date: 15-1-5
 * Time: 下午5:19
 *
 */
NS.define('Pages.sc.ykt.YktFsdFlx',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            getTextData:'fsdFlxService?getTextData',
            queryChartData1:'fsdFlxService?getChartData1',
            queryChartData2:'fsdFlxService?getChartData2',
            queryTable:'fsdFlxService?getTableData',
            getTableData1:'fsdFlxService?getTableData1'

        }
    },
    tplRequires : [],
    cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
    mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{},
    init: function () {
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'分时段分类型消费统计',
                pageHelpInfo:'按不同消费类型分时段统计学生消费情况。'}
        });
        var containerx = this.mainContainer = this.createMain();

        var compo = new Ext.Component({
                tpl:'<div class="wbh-common-search"><form>' +
                    '<span style="margin-right: 20px;float: left;height: 26px;line-height: 26px;color: #039403;">统计区间</span>'+
                    '<div id="PagesscyktYktfsdflx_dateSection" style="float: left;"></div>'+
                    '<span style="margin-left: 20px;float: left;height: 26px;line-height: 26px;color: #777;">' +
                    '☞选择开始时间和结束时间，页面会自动刷新。</span>'+
                    '<div style="clear: both;"></div>'+
                    '</form></div>',
                data:{}
            }
        );
        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,compo,containerx]
        });
        container.on('afterrender',function(){
            this.dateSection.render('PagesscyktYktfsdflx_dateSection');
        },this);
        this.setPageComponent(container);

        this.pzateSection();

        this.fillCompoByData();
    },
    chart1 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:300
    }),
    chart2 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:300
    }),
    chart3 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:300
    }),
    info:new Ext.Component({
        tpl : "<b>{start}</b> 至 <b>{end}</b> 期间，餐厅累计消费 <b>{data.ct.ZXFJE}</b> 元，共计产生 <b>{data.ct.ZXFBS}</b> 笔 交易。",
        data : {start:'--',end:'--',data:{ZXFJE:0.0,ZXFBS:0}},
        margin : '10px 15px'
    }),
    info1:new Ext.Component({
        tpl : "<b>{start}</b> 至 <b>{end}</b> 期间，洗浴累计消费 <b>{data.xy.ZXFJE}</b> 元，共计产生 <b>{data.xy.ZXFBS}</b> 笔 交易。",
        data : {start:'--',end:'--',data:{ZXFJE:0.0,ZXFBS:0}},
        margin : '10px 15px'
    }),
    info2:new Ext.Component({
        tpl : "<b>{start}</b> 至 <b>{end}</b> 期间，超市累计消费 <b>{data.cs.ZXFJE}</b> 元，共计产生 <b>{data.cs.ZXFBS}</b> 笔 交易。",
        data : {start:'--',end:'--',data:{ZXFJE:0.0,ZXFBS:0}},
        margin : '10px 15px'
    }),
    title1 : new Exp.chart.PicAndInfo({
        title : "餐厅消费",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "洗浴消费",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title3 : new Exp.chart.PicAndInfo({
        title : "超市消费",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    dateSection:new NS.appExpand.DateSection(),
    /**
     * 创建日期区间组件。
     */
    pzateSection:function(){
        var today = new Date();
        this.dateSection.setValue({from : today - 14 * 3600* 24000,to : new Date()});
        this.params = this.dateSection.getRawValue();
        this.dateSection.addListener('validatepass',function(){
            this.params= this.dateSection.getRawValue();
            // 加载数据
            this.fillCompoByData();
        },this);

    },
    table:  new Ext.Component({
        tpl : '<table class="table1"><thead><tr>'+
        '<th>地点</th>'+
        '<th>窗口</th>'+
        '<th>早餐</th>'+
        '<th>午餐</th>'+
        '<th>晚餐</th>'+
        '</tr></thead><tbody>' +
        '<tpl for="."><tr><td rowspan="{[values.items.length+1]}">{name}</td></tr>'+
        " <tpl for='items'><tr>" +
        '<td>{name}</td>'+
        '<td>{[typeof values.a =="undefined"? "":values.a.ZXFJE]}</td>'+
        '<td>{[typeof values.b =="undefined"? "":values.b.ZXFJE]}</td>'+
        '<td>{[typeof values.c =="undefined"? "":values.c.ZXFJE]}</td>'+
        "</tr></tpl> " +
        '</tpl>'+
        '</tbody></table>',

        data : [],
        margin : '20px 0px 10px 10px'
    }),
    table1:  new Ext.Component({
        tpl : '<table class="table1"><thead><tr>'+
        '<th>地点</th>'+
        '<th>窗口</th>'+
        '<th>上午</th>'+
        '<th>下午</th>'+
        '<th>晚上</th>'+
        '</tr></thead><tbody>' +
        '<tpl for="."><tr><td rowspan="{[values.items.length+1]}">{name}</td></tr>'+
            " <tpl for='items'><tr>" +
        '<td>{name}</td>'+
        '<td>{[typeof values.a =="undefined"? "":values.a.ZXFJE]}</td>'+
        '<td>{[typeof values.b =="undefined"? "":values.b.ZXFJE]}</td>'+
        '<td>{[typeof values.c =="undefined"? "":values.c.ZXFJE]}</td>'+
            "</tr></tpl> " +
        '</tpl>'+
        '</tbody></table>',

        data : [],
        margin : '20px 0px 10px 10px'
    }),
    table2:  new Ext.Component({
        tpl : '<table class="table1"><thead><tr>'+
        '<th>地点</th>'+
        '<th>窗口</th>'+
        '<th>上午</th>'+
        '<th>下午</th>'+
        '<th>晚上</th>'+
        '</tr></thead><tbody><tpl for="."><tr><td rowspan="{[values.items.length+1]}">{name}</td></tr>'+
        " <tpl for='items'><tr>" +
        '<td>{name}</td>'+
        '<td>{[typeof values.a =="undefined"? "":values.a.ZXFJE]}</td>'+
        '<td>{[typeof values.b =="undefined"? "":values.b.ZXFJE]}</td>'+
        '<td>{[typeof values.c =="undefined"? "":values.c.ZXFJE]}</td>'+
        "</tr></tpl> " +
        '</tpl>'+
        '</tbody></table>',
        data : [],
        margin : '20px 0px 10px 10px'
    }),
    tableData:[],
    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        var cont1 = new Ext.container.Container({
            layout:"column",
            items:[new Ext.container.Container({
                items:this.chart1,columnWidth:1/2
            }),
                new Ext.container.Container({
                    items:this.table,columnWidth:1/2
                })]
        });
        var cont2 = new Ext.container.Container({
            layout:"column",
            items:[new Ext.container.Container({
                items:this.chart2,columnWidth:1/2
            }),
                new Ext.container.Container({
                    items:this.table1,columnWidth:1/2
                })]
        });
        var cont3 = new Ext.container.Container({
            layout:"column",
            items:[new Ext.container.Container({
                items:this.chart3,columnWidth:1/2
            }),
                new Ext.container.Container({
                    items:this.table2,columnWidth:1/2
                })]
        });
        return new Ext.container.Container({
            items:[
                this.title3,this.info2,
                cont3,
                this.title2,this.info1,
                cont2,
                this.title1,this.info,
                cont1


            ]
        });
    },
    fillCompoByData:function(){
        this.tableData=[];
        this.callService({key:'queryChartData2',params:this.params},function(respData){
            var cdata1 = [];
            for(var i=0;i<respData.queryChartData2.length;i++){
                var obj = respData.queryChartData2[i];
                cdata1.push({name:obj.CBMC,y:Number(obj.ZXFJE)});
                obj.dl='';
                this.tableData.push(obj);
            }

            //this.table.update(this.tableData);
            $('#'+this.chart2.getId()).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '洗浴分时段消费金额组成'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                        }
                    }
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                series: [{
                    type: 'pie',
                    name: '消费占比',
                    data: cdata1
                }]
            });
        },this);
        this.callService({key:'getTextData',params:this.params},function(respData){
            var textdata = respData.getTextData;
            this.info.update(textdata);
            this.info1.update(textdata);
            this.info2.update(textdata);
        },this);
        if(!(window.console && console.log)) {
                           console = {
                          log: function(){},
                        debug: function(){},
                         info: function(){},
                         warn: function(){},
                        error: function(){}
                          };
                        }
        this.callService({key:'queryTable',params:this.params},function(respData){
            var tableData = respData.queryTable;
            this.table.update(tableData.ct);
            window.console.log(tableData.ct);
            this.table2.update(tableData.cs);
        },this);
        this.callService({key:'getTableData1',params:this.params},function(respData){
            var tableData = respData.getTableData1;
            this.table1.update(tableData);
        },this);

        this.callService([{key:'queryChartData1',params:this.params}],function(respData){
            var cdata = [];
            for(var i=0;i<respData.queryChartData1.ct.length;i++){
                var obj = respData.queryChartData1.ct[i];
                cdata.push({name:obj.CBMC,y:Number(obj.ZXFJE)});
                obj.dl='';
                this.tableData.push(obj);
            }
            $('#'+this.chart1.getId()).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '餐厅分时段消费金额组成'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                        }
                    }
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                series: [{
                    type: 'pie',
                    name: '消费占比',
                    data: cdata
                }]
            });



        },this);

        this.callService({key:'queryChartData1',params:this.params},function(respData){
            var cdata3 = [];
            for(var i=0;i<respData.queryChartData1.cs.length;i++){
                var obj = respData.queryChartData1.cs[i];
                cdata3.push({name:obj.CBMC,y:Number(obj.ZXFJE)});
                obj.dl='';
                this.tableData.push(obj);
            }
            $('#'+this.chart3.getId()).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '超市分时段消费金额组成'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                        }
                    }
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                series: [{
                    type: 'pie',
                    name: '消费占比',
                    data: cdata3
                }]
            });
        },this);
    }
});