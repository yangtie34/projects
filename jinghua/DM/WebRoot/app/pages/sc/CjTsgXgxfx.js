/**
 * 成绩与图书馆进出次数的相关性分析。
 * User: zhangzg
 * Date: 14-12-30
 * Time: 下午6:37
 *
 */
NS.define('Pages.sc.CjTsgXgxfx',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            querySslRzColumn:'bzksWzsService?getWgWzsColumn'

        }
    },
    tplRequires : [],
    cssRequires : ['app/pages/zksf/css/base.css'],
    mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{},
    init: function () {
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'总成绩排名与平均进出图书馆次数的相关性分析',
                pageHelpInfo:'总成绩排名与平均进出图书馆次数的相关性分析，由于数据原因 2013学年的展示可信，但是2010，2012，2011，2014学年的数据并不可信。'}
        });
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,containerx]
        });
        this.setPageComponent(container);

        this.fillCompoByData();
    },
    sslChart : new Exp.chart.HighChart({
        height:500
    }),

    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        var containerx = new Ext.container.Container({
            items:[this.sslChart]
        });
        return containerx;
    },
    fillCompoByData:function(){
        this.callService({key:'querySslRzColumn',params:this.params},function(respData){
            $('#'+this.sslChart.getId()).highcharts({
                chart: {
                    type: 'bubble',
                    zoomType: 'xy'
                },

                title: {
                    text: '总成绩排名与平均进出图书馆次数的相关性分析'
                },
                yAxis: {
                    title: {
                        text: '专业排名'
                    }
                },
                xAxis: {
                    title: {
                        text: '图书馆平均出入次数'
                    }
                },
                tooltip: {
                    formatter: function () {
                        var s = '<b>' + this.y + '</b>';
                        if(Number(this.y)==10){
                            s = '<b> 1-10名 </b>'
                        }else if(Number(this.y)>10 && Number(this.y)<=20){
                            s = '<b> 20-50名 </b>'
                        }else if(Number(this.y)>20 && Number(this.y)<=50){
                            s = '<b> 20-50名 </b>'
                        }else if(Number(this.y)>50 && Number(this.y)<=100){
                            s = '<b> 50-100名 </b>'
                        }else if(Number(this.y)>100 && Number(this.y)<=200){
                            s = '<b> 100-200名 </b>'
                        }else if(Number(this.y)>200 && Number(this.y)<=300){
                            s = '<b> 200-300名 </b>'
                        }else if(Number(this.y)>300){
                            s = '<b> 300名后 </b>'
                        }

                        return this.series.name+'<br/>专业排名： ' + s +
                            ', <br/>'+
                            '平均图书馆进出次数：<b>' + this.x + ',</b> <br/>'+
                            '对应的学生数：<b>' + this.point.z + '</b>';
                    }
                },
                series: [{
                    name:'2010-2011学年',
                    data:[[0, 10, 1448], [0, 300, 785], [0, 50, 3413], [0, 400, 35], [0, 100, 3234], [0, 200, 2572], [0, 20, 1312]]
                },{
                    name:'2011-2012学年',
                    data:[[0, 400, 45], [0, 10, 1926], [0, 100, 4628], [0, 200, 3430], [0, 300, 949], [0, 20, 1792], [0, 50, 4963]]
                },{
                    name:'2012-2013学年',
                    data:[[0, 50, 6261], [0, 20, 2273], [0, 100, 5688], [0, 400, 1], [0, 10, 2417], [0, 200, 3801], [0, 300, 845]]
                },{
                    name:'2013-2014学年',
                    data:[[74, 20, 2391], [36, 400, 22], [55, 100, 5983], [80, 10, 2500], [43, 300, 841], [64, 50, 6466], [46, 200, 3752]]
                },{
                    name:'2014-2015学年',
                    data:[[40, 50, 150], [33, 100, 238], [30, 10, 64], [31, 200, 209], [35, 20, 53]]
                }]
            });
        },this);
    }
});