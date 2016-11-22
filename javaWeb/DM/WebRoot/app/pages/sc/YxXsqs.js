/**
 * 各学院学年总学时变化趋势
 * User: zhangzg
 * Date: 15-1-17
 * Time: 下午15:44
 *
 */
NS.define('Pages.sc.YxXsqs',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，教学组织结构
            queryXslp:'scKcktjService?queryYxZxs',// 理论学时数据
            queryByyx:'teacherXueyuanService?queryByyx',//毕业院校人数
            queryInfo1:'teacherXueyuanService?queryInfo1',//学术流派文字
            queryInfo2:'teacherXueyuanService?queryInfo2'//毕业院校文字
        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{},
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'各学院学年总学时变化趋势',
                pageHelpInfo:'以学年为统计维度对各学院理论学时、实践学时趋势进行展示。'}
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,/*navigation,*/containerx]
        });
        this.setPageComponent(container);

        container.on('afterrender',function(){
            // 刷新导航栏
            /*this.callService('queryYxzzjgTree',function(data){
                this.navigation.refreshTpl(data.queryYxzzjgTree);
            },this);*/
            this.fillCompoByData();
            navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.params.zzjgId = data.nodes[len-1].id;
                me.params.zzjgmc = data.nodes[len-1].mc;
                me.fillCompoByData();
            });
        },this);


    },
    chart7 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:580
    }),
    chart8 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:580
    }),
    title1 : new Exp.chart.PicAndInfo({
        title : "理论学时",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "实践学时",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    /**
     * 创建中部容器组件。
     */
    createMain:function(){

        var containerx = new Ext.container.Container({

            items:[
                this.title1,this.chart7,
                this.title2,this.chart8]
        });

        return containerx;
    },
    fillCompoByData:function(){

        // 学术流派
        this.callService({key:'queryXslp',params:this.params},function(data){

            var data1 = data.queryXslp;
            for(var i= 0,len = data1.length;i<len;i++){
                data1[i].field = data1[i].KKXN;
                data1[i].name =  data1[i].YXMC;
                data1[i].value = data1[i].LLXS;
            }
            this.renderCommonChart(this.chart7.getId(),
                '学院开课理论学时对比统计','学年开课理论学时',data1,'spline','学时',true);
        });
        // 毕业院校
        this.callService({key:'queryXslp',params:this.params},function(data){
            var data1 = data.queryXslp;
            for(var i= 0,len = data1.length;i<len;i++){
                data1[i].field = data1[i].KKXN;
                data1[i].name =  data1[i].YXMC;
                data1[i].value = data1[i].SJXS;
            }
            this.renderCommonChart(this.chart8.getId(),
                '学院开课实践学时对比统计','学年开课实践学时',data1,'spline','学时',true);
        });

    },
    renderPie:function(chart,title,cdata,dw){
        var config = {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: title
            },
            tooltip: {
                pointFormat: '{series.name}: <b> {point.y} '+dw+'</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true
                    },
                    showInLegend: false
                }
            },
            credits : {// 不显示highchart标记
                enabled : false
            },
            series: [{
                type: 'pie',
                name: title,
                data: cdata
            }]
        };
        chart.redraw(config,true);
    }
});