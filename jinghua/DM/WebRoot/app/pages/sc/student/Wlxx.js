/**
 * 网络学习统计分析。
 * User: zhangzg
 * Date: 15-1-5
 * Time: 下午5:19
 *
 */
NS.define('Pages.sc.student.Wlxx',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
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
                pageName:'网络学习统计分析',
                pageHelpInfo:'依据网络学习空间和网络教学平台2个的数据展示学校网络学习建设.'}
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
        var tableC = new Ext.container.Container({
            layout:"column",
            items:[new Ext.container.Container(
                {items:[this.title3,this.table],columnWidth: 1/2}
            ),new Ext.container.Container(
                {items:[this.title4,this.table1],columnWidth: 1/2}
            )]
        });
        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,compo,this.title1,containerx,this.title2,this.chart4,tableC]
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
    chart4 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:300
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
    title1 : new Exp.chart.PicAndInfo({
        title : "开设课程数",
        onlyTitle : true,
        margin : "0px 0px 10px 0px"
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "学生活跃数",
        onlyTitle : true,
        margin : "0px 0px 10px 0px"
    }),
    title3 : new Exp.chart.PicAndInfo({
        title : "网络学习空间课程访问排名",
        onlyTitle : true,
        margin : "0px 0px 10px 0px"
    }),
    title4 : new Exp.chart.PicAndInfo({
        title : "网络教学平台课程访问排名",
        onlyTitle : true,
        margin : "0px 0px 10px 0px"
    }),
    table:  new Ext.Component({
        tpl : '<table class="table1"><thead><tr>'+
            '<th>排名</th>'+
            '<th>课程名称</th>'+
            '<th>开课老师</th>'+
            '<th>总计访问量</th>'+
            '</tr></thead><tbody><tpl for="."><tr>'+
            '<td>{pm}</td>'+
            '<td>{kcmc}</td>'+
            '<td>{kkjs}</td>'+
            '<td>{zjfwl}</td>'+
            '</tr></tpl>'+
            '</tbody></table>',
        data : [],
        margin : '20px 0px 10px 10px'
    }),
    table1:  new Ext.Component({
        tpl : '<table class="table1"><thead><tr>'+
            '<th>排名</th>'+
            '<th>课程名称</th>'+
            '<th>开课老师</th>'+
            '<th>总计访问量</th>'+
            '</tr></thead><tbody><tr>'+
            '<td colspan="4">网络课程无访问量</td>'+
            '</tr>'+
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
                    {items:this.chart2,columnWidth: 1/3}
                ),
                new Ext.container.Container(
                {items:this.chart1,columnWidth: 1/3}
            ),
                new Ext.container.Container(
                {items:this.chart3,columnWidth: 1/3}
            )]
        });
    },
    fillCompoByData:function(){
        this.tableData=[];
        this.callService({key:'queryChartData3',params:this.params},function(respData){
            var cdata1 = [{name:'网络教学平台',y:365},{name:'网络学习空间',y:107}];
            $('#'+this.chart2.getId()).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '学习空间与教学平台开设总课程'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y} 门</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.y} 门'
                        }
                    }
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                series: [{
                    type: 'pie',
                    name: '开设课程数',
                    data: cdata1
                }]
            });
        },this);
        this.callService([{key:'queryChartData3',params:this.params}],function(respData){

            var cdata  =[{name:'开设课程数',y:107,field:'周口师范学院',value:107}];
            this.renderCommonChart(this.chart1.getId(),'网络学习空间各院系开设课程','开设课程数',cdata,'column','门',true);

        },this);
        this.callService([{key:'queryChartData3',params:this.params}],function(respData){
            var cdata =[{name:'学生活跃数',y:1524,field:'网络教学平台',value:1524},{name:'学生活跃数',y:1247,field:'网络学习空间',value:1247}];
            this.renderCommonChart(this.chart4.getId(),'教学平台与学习空间的学生活跃数对比','学生活跃数',cdata,'column','人',true);

        },this);
        this.callService({key:'queryChartData3',params:this.params},function(respData){
            var cdata3 = [{name:'数学与统计学院',y:13},
                {name:'物理与机电工程学院',y:20},{name:'化学化工学院',y:16},
                {name:'公共艺术与职业技能教学部',y:1},{name:'生命科学与农学学院',y:15},
                {name:'计算机科学与技术学院',y:43},{name:'文学院',y:11},
                {name:'政法学院',y:6},{name:'经济与管理学院',y:6},
                {name:'新闻与传媒学院',y:11},{name:'思想政治理论教研部',y:6},
                {name:'美术与设计学院',y:13},{name:'软件学院',y:1},
                {name:'教育科学学院',y:21},{name:'音乐舞蹈学院',y:5},
                {name:'体育学院',y:13},{name:'外国语学院',y:5}];

            for(var i =0;i<cdata3.length;i++){
                var data = cdata3[i];
                data.value = data.y;
                data.field = data.name;
                data.name = '开设课程数';
            }

            this.renderCommonChart(this.chart3.getId(),'网络教学平台各院系开设课程','开设课程数',cdata3,'column','门',true);


        },this);
        var tableDate = [
            {pm:1,kcmc:'ASP.NET程序设计',kkjs:'张少辉',zjfwl:14270},
            {pm:2,kcmc:'Linux操作系统',kkjs:'李骞',zjfwl:10055},
            {pm:3,kcmc:'组网技术',kkjs:'张青锋',zjfwl:9479},
            {pm:4,kcmc:'Windows网络编程',kkjs:'秦东霞',zjfwl:8192},
            {pm:5,kcmc:'软件需求分析',kkjs:'刘辛',zjfwl:7426},
            {pm:6,kcmc:'嵌入式编程',kkjs:'朱变',zjfwl:6699},
            {pm:7,kcmc:'ASP.Net程序设计',kkjs:'郭丽萍',zjfwl:6565},
            {pm:8,kcmc:'Linux操作系统-12科学/14科学专升本',kkjs:'高光',zjfwl:6499},
            {pm:9,kcmc:'网页设计与制作',kkjs:'郭慧玲',zjfwl:6152},
            {pm:10,kcmc:'数字影视特效（AE）',kkjs:'刘琳琳',zjfwl:5812}
        ];
        this.table.update(tableDate);
    }
});