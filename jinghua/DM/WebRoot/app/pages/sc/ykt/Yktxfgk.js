/**
 * 一卡通消费概况统计。
 * User: zhangzg
 * Date: 15-1-5
 * Time: 下午5:19
 *
 */
NS.define('Pages.sc.ykt.Yktxfgk',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            querySslRzColumn:'yktxfqkService?getTextData',
            queryChartData1:'yktxfqkService?getChartData1',
            queryChartData2:'yktxfqkService?getChartData2',
            queryChartData3:'yktxfqkService?getChartData3'

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
                pageName:'消费情况统计',
                pageHelpInfo:'消费情况统计'}
        });
        var containerx = this.mainContainer = this.createMain();

        var compo = new Ext.Component({
                tpl:'<div class="wbh-common-search"><form>' +
                    '<span style="margin-right: 20px;float: left;height: 26px;line-height: 26px;color: #039403;">统计区间</span>'+
                    '<div id="PagesscyktYktxfgk_dateSection" style="float: left;"></div>'+
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
            items:[pageTitle,compo,this.info,containerx,this.table]
        });
        container.on('afterrender',function(){
            this.dateSection.render('PagesscyktYktxfgk_dateSection');
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
        tpl : "<b>{start}</b> 至 <b>{end}</b> 期间，全校累计消费 <b>{data.ZXFJE}</b> 元，共计产生 <b>{data.ZXFBS}</b> 笔 交易。",
        data : {start:'--',end:'--',data:{ZXFJE:0.0,ZXFBS:0}},
        margin : '10px 15px'
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
            var params = this.params= this.dateSection.getRawValue();
            // 加载数据
            this.fillCompoByData();
        },this);

    },
    table:  new Ext.Component({
        tpl : '<table class="table1"><thead><tr>'+
            '<th>统计大类</th>'+
            '<th>统计子类</th>'+
            '<th>消费金额(元)</th>'+
            '<th>消费笔数(笔)</th>'+
            '<th>平均每笔消费(元)</th>'+
            '</tr></thead><tbody><tpl for="."><tr>'+
            '<td>{dl}</td>'+
            '<td>{name}</td>'+
            '<td>{y}</td>'+
            '<td>{vv}</td>'+
            '<td>{avg}</td>'+
            '</tr></tpl>'+
            '</tbody></table>',
        data : [],
        margin : '20px 0px 10px 10px'
    }),
    tableData:[],
    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        return new Ext.container.Container({
            layout:"column",
            items:[
                new Ext.container.Container(
                {items:this.chart1,columnWidth: 1/3}
            ),
                new Ext.container.Container(
                {items:this.chart2,columnWidth: 1/3}
            ),
                new Ext.container.Container(
                {items:this.chart3,columnWidth: 1/3}
            )]
        });
    },
    fillCompoByData:function(){
        this.tableData=[];
        this.callService({key:'queryChartData2',params:this.params},function(respData){
            var cdata1 = [];
            for(var i=0;i<respData.queryChartData2.length;i++){
                var obj = respData.queryChartData2[i];
                cdata1.push({name:obj.name,y:Number(obj.y)});
                obj.dl='按消费类型统计';
                this.tableData.push(obj);
            }

            this.table.update(this.tableData);
            $('#'+this.chart2.getId()).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '按消费类型统计消费组成'
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
        this.callService({key:'querySslRzColumn',params:this.params},function(respData){
            this.info.update(respData.querySslRzColumn);
        },this);
        this.callService([{key:'queryChartData1',params:this.params}],function(respData){

            var cdata = [];
            for(var i=0;i<respData.queryChartData1.length;i++){
                var obj = respData.queryChartData1[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
                obj.dl='按持卡人身份统计';
                this.tableData.push(obj);
            }
            this.tableData.push(respData.queryChartData1);
            this.table.update(this.tableData);
            $('#'+this.chart1.getId()).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '按持卡人身份统计消费组成'
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

        this.callService({key:'queryChartData3',params:this.params},function(respData){
            var cdata3 = [];
            for(var i=0;i<respData.queryChartData3.length;i++){
                var obj = respData.queryChartData3[i];
                cdata3.push({name:obj.name,y:Number(obj.y)});
                obj.dl='按充值方式统计';
                this.tableData.push(obj);
            }
            this.tableData.push(respData.queryChartData3);
            this.table.update(this.tableData);
            $('#'+this.chart3.getId()).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '按充值方式统计充值组成'
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