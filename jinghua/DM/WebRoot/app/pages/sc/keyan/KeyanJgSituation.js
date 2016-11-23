/**
 * 科研机构概况统计
 * User: zhangzg
 * Date: 14-8-27
 * Time: 上午11:16
 *
 */
NS.define('Pages.sc.keyan.KeyanJgSituation',{
    extend:'Template.Page',
    entityName:'',
    modelConfig: {
        serviceConfig: {
            queryJxzzjgTree: 'scService?getYxzzjgTree',
            queryChartData:'studentBhtjService?queryChartData',// 图形统计数据总入口
            queryKyjgChartData:'kyService?getKyjgChartData',// 科研机构
            queryKyryChartData:'kyService?getKyryChartData',// 科研人员
            queryKyxmChartData:'kyService?getKyxmChartData',// 科研项目

            queryKyjfChartData:'kyService?getKyjfChartData',// 科研经费
            queryXshdChartData:'kyService?getKyhdChartData',// 学术活动
            queryKycgChartData:'kyService?getKycgChartData'// 项目成果

        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{},
    /**
     * 页面入口
     */
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'科研机构概况统计',
                pageHelpInfo:'从学校、一级科研机构、二级科研机构逐级下钻按照日期统计已获得奖励、专利、在研人员、在研项目数、项目金额、完成项目数等的统计信息'}
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = this.pageContainer = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,navigation,containerx]
        });
        this.setPageComponent(container);

        // 刷新导航栏
        this.callService('queryJxzzjgTree',function(data){
            this.navigation.refreshTpl(data.queryJxzzjgTree);
            var i = 0;
            for(var key in data.queryJxzzjgTree){
                if(i==0){
                    var nodeId = data.queryJxzzjgTree[key].id;
                    this.navigation.setValue(nodeId);
                    this.params.zzjgId = nodeId;
                }
                i++;
            }
            this.initParams();
            this.fillCompoByData();
        },this);
        // 导航栏的点击事件
        navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;
            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;
            me.fillCompoByData();
        });
    },
    /**
     * 创建主题容器
     */
    createMain:function(){
        var me = this;
        var zychart = this.zychart = new Exp.component.ZonePart2({
            columnWidth:0.5,
            height:320
        });
        var xkflchart = this.xkflchart = new Exp.component.ZonePart2({
            columnWidth:0.5,
            height:320,
            margin:'0 0 0 10'
        });
        var nldchart = this.nldchart = new Exp.component.ZonePart2({
            columnWidth:1,
            height:320,
            margin:'20 0 0 0'
        });
        var mzchart = this.mzchart = new Exp.component.ZonePart2({
            columnWidth:0.5,
            height:320,
            margin:'20 0 0 0'
        });
        var sydchart = this.sydchart = new Exp.component.ZonePart2({
            columnWidth:0.5,
            height:320,
            margin:'20 0 0 10'
        });
        var lastchart = this.lastchart = new Exp.component.ZonePart2({
            columnWidth:1,
            height:420,
            margin:'20 0 0 0'
        });

        var containerxxx = new Ext.container.Container({
            margin:'20 0 0 0',
            layout:{
                type:'column',
                columns:2
            },
            items:[zychart,xkflchart,nldchart,mzchart,sydchart,lastchart]
        });
        return containerxxx;
    },
    /**
     * 数据填充组件
     */
    fillCompoByData:function(){

        this.callService({key:'queryKyjgChartData',params:this.params},function(respData){
            this.zychart.update([{
                theme:'blue',
                title:'科研机构',
                count:respData.queryKyjgChartData.count||'--',
                axisname:'所'
            }]);
            var mzzcChart = new Exp.chart.HighChart({
                height:300
            });
            this.zychart.insertChart(mzzcChart);
            var cdata = [];
            for(var i=0;i<respData.queryKyjgChartData.queryList.length;i++){
                var obj = respData.queryKyjgChartData.queryList[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
            }
            var config = {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                tooltip: {
                    pointFormat: ''
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{
                    type: 'pie',
                    name: '',
                    data: cdata
                }]
            };

            mzzcChart.redraw(config,true);
        },this);

        this.callService({key:'queryKyryChartData',params:this.params},function(respData){
            this.xkflchart.update([
                {
                    theme:'blue',
                    title:'科研人员',
                    count:respData.queryKyryChartData.count||'--',
                    axisname:'人'
                }]);
            var mzzcChart = new Exp.chart.HighChart({
                height:300
            });
            this.xkflchart.insertChart(mzzcChart);
            var cfg = {
                title:'',
                type:'column',
                yAxis:'人数',
                sfqx:false
            };
            var config = this.translateData(respData.queryKyryChartData.queryList,cfg);
            mzzcChart.redraw(config,true);
        },this);
        // 在研项目数、完成项目数
        this.callService({key:'queryKyxmChartData',params:this.params},function(respData){
            this.nldchart.update([{
                theme:'blue',
                title:'在研科目数',
                count:respData.queryKyxmChartData.count1||'--',
                axisname:'项'
            },{
                theme:'green',
                title:'结项科目数',
                count:respData.queryKyxmChartData.count2||'--',
                axisname:'项'
            }]);
            var mzzcChart = new Exp.chart.HighChart({
                height:300
            });
            this.nldchart.insertChart(mzzcChart);

            var cfg = {
                title:'',
                type:'column',
                yAxis:'项目数(单位：项)',
                sfqx:false
            };
            var config = this.translateData(respData.queryKyxmChartData.queryList,cfg);
            mzzcChart.redraw(config,true);
        },this);
        // 经费信息
        this.callService({key:'queryKyjfChartData',params:this.params},function(respData){
            this.mzchart.update([{
                theme:'blue',
                title:'划拨经费',
                count:respData.queryKyjfChartData.count,
                axisname:'万元'
            }]);
            var mzzcChart = new Exp.chart.HighChart({
                height:300
            });
            this.mzchart.insertChart(mzzcChart);

            var cfg = {
                title:'',
                type:'column',
                yAxis:'单位：万元',
                sfqx:false
            };
            var config = this.translateData(respData.queryKyjfChartData.queryList,cfg);
            mzzcChart.redraw(config,true);
        },this);
        // 学术活动
        this.callService({key:'queryXshdChartData',params:this.params},function(respData){
            this.sydchart.update([{
                theme:'blue',
                title:'学术活动',
                count:respData.queryXshdChartData.count,
                axisname:'场'
            }]);
            var mzzcChart = new Exp.chart.HighChart({
                height:300
            });
            this.sydchart.insertChart(mzzcChart);

            var cfg = {
                title:'',
                type:'column',
                yAxis:'单位：场',
                sfqx:false
            };
            var config = this.translateData(respData.queryXshdChartData.queryList,cfg);
            mzzcChart.redraw(config,true);
        },this);
        //取得项目成果数-复合图
        this.callService({key:'queryKycgChartData',params:this.params},function(respData){
            this.lastchart.update([{
                theme:'blue',
                title:'取得项目成果',
                count:respData.queryKycgChartData.count||'--',
                axisname:'项'
            }]);
            var mzzcChart = new Exp.chart.HighChart({
                height:390
            });
            this.lastchart.insertChart(mzzcChart);

            var cfg = {
                title:'',
                type:'column',
                yAxis:'人数',
                sfqx:false
            };
            var config = this.translateData(respData.queryKycgChartData.queryList,cfg);
            var dt = respData.queryKycgChartData;
            var lws =[],zzs=[],zls=[],pjs=[];
            for(var i=0;i<dt.lws.length;i++){
                lws.push(Number(dt.lws[i]));
            }
            for(var j=0;j<dt.zls.length;j++){
                zls.push(Number(dt.zls[j]));
            }
            for(var k=0;k<dt.zzs.length;k++){
                zzs.push(Number(dt.zzs[k]));
            }
            for(var m=0;m<dt.zzs.length;m++){
                pjs.push(Number(dt.pjs[m]));
            }
            config = {
                chart: { },
                title: { text: '取得项目成果统计' },
                xAxis: { categories: dt.dws},
                tooltip: {
                    valueSuffix:' 项',
                    shared:true
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                yAxis: {
                    title: {
                        text: '项目数，单位（个）'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                labels: { items: [
                    { html: '项目成果类型',
                    style: { left: '40px', top: '-40px', color: 'black' } }] },
                series: [{ type: 'column', name: '专利', data: zls },
                    { type: 'column', name: '著作', data: zzs },
                    { type: 'column', name: '论文', data: lws },
                    { type: 'spline', name: '平均成果数',data: pjs,
                        marker: { lineWidth: 2, lineColor: Highcharts.getOptions().colors[3], fillColor: 'white' } },
                    { type: 'pie', name: '总取得数',data: [
                        { name: '专利', y: Number(dt.zlcount), color: Highcharts.getOptions().colors[0]  },
                        { name: '著作', y: Number(dt.zzcount), color: Highcharts.getOptions().colors[1]  },
                        { name: '论文', y: Number(dt.lwcount), color: Highcharts.getOptions().colors[2]  }],
                      center: [100, 10], size: 100, showInLegend: false, dataLabels: { enabled: false } }
                ]};
            mzzcChart.redraw(config,true);
        },this);
    },
    /**
     * 初始化页面参数
     */
    initParams:function(){
        var navdata = this.navigation.getValue();
        this.params.zzjgId = navdata.nodes[0].id;
    }
});