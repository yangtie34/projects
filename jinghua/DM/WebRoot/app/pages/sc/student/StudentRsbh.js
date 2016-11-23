/**
 * 历年新生人数。
 * User: zhangzg
 * Date: 14-8-26
 * Time: 上午10:02
 *
 */
NS.define('Pages.sc.student.StudentRsbh',{
    extend:'Template.Page',
    entityName:'',
    modelConfig: {
        serviceConfig: {
            queryJxzzjgTree: 'scService?getJxzzjgTree',
            queryChartData:'studentBhtjService?queryChartData'// 图形统计数据总入口
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
                pageName:'历年来校新生人数变化趋势',
                pageHelpInfo:'从专业、学科分类、年龄段、民族对历年来来校学生的趋势进行，横向与纵向对比统计。'}
        });
        var simpleDate = this.simpleDate = new Exp.component.SimpleYear({
            start:1900,
            margin:'0 0 0 10'
        });

        var container = new Ext.container.Container({
            cls:'student-sta-titlediv',
            layout:{
                type:'hbox',
                align:'middle'
            },
            height:40,
            margin:'0 0 5 0',
            items:[simpleDate,new Ext.Component({
                html:'<span style="margin-left: 20px;height: 26px;line-height: 26px;color: #777;">☞选择开始年份和结束年份，统计该年份内的人数。</span>'
            })]
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,container,navigation,containerx]
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

        navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;

            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;
            me.fillCompoByData();
        });
        simpleDate.on('validatepass',function(){
            var data = this.getValue();
            me.params.from = data.from;
            me.params.to = data.to;
            me.fillCompoByData();
        });
    },
    /**
     * 创建主题容器
     */
    createMain:function(){
        var zychart = this.zychart = new Exp.chart.HighChart({
            height:380,
            columnWidth:0.5,
            margin:5
        });
        var xkflchart = this.xkflchart = new Exp.chart.HighChart({
            height:380,
            columnWidth:0.5,
            margin:5
        });
        var nldchart = this.nldchart = new Exp.chart.HighChart({
            height:380,
            columnWidth:0.5,
            margin:5
        });
        var mzchart = this.mzchart = new Exp.chart.HighChart({
            height:380,
            columnWidth:0.5,
            margin:5
        });
        var sydchart = this.sydchart = new Exp.chart.HighChart({
            height:380,
            columnWidth:0.5,
            margin:5
        });
        var sexchart = this.sexchart = new Exp.chart.HighChart({
            height:380,
            columnWidth:0.5,
            margin:6
        });
        var containerxxx = new Ext.container.Container({
            style:{
                'background':'#e4e4e4',
                'border-radius':'5px',
                'padding':'5px',
                'margin-top':'10px'
            },
            layout:{
                type:'column',
                columns:2
            },
            items:[zychart,xkflchart,nldchart,mzchart,sydchart,sexchart]
        });
        return containerxxx;
    },
    /**
     * 数据填充组件
     */
    fillCompoByData:function(){
        var params = {tjzb:1};
        Ext.apply(params,this.params);
        var flag = this.params.from == this.params.to;
        if(flag) {this.currentType = 'column'}else{this.currentType='line'};
        this.callService({key:'queryChartData',params:params},function(data){
            var respData = data.queryChartData;
            if(flag)this.overturnData(respData);
            this.changeChartCfg(respData,"统计历年各院系、专业的新生人数变化趋势");
            this.zychart.redraw(this.chartCfg,true);
        },this);
        params.tjzb = 2;
        this.callService({key:'queryChartData',params:params},function(data){
            var respData = data.queryChartData;
            var flag = this.params.from == this.params.to;
            if(flag)this.overturnData(respData);
            this.changeChartCfg(respData,"按学科分类统计历年新生人数变化趋势");

            this.xkflchart.redraw(this.chartCfg,true);
        },this);
        params.tjzb = 3;
        this.callService({key:'queryChartData',params:params},function(data){
            var respData = data.queryChartData;
            var flag = this.params.from == this.params.to;
            if(flag)this.overturnData(respData);
            this.changeChartCfg(respData,"按年龄段分类统计历年新生人数变化趋势");
            this.mzchart.redraw(this.chartCfg,true);
        },this);
        params.tjzb = 4;
        this.callService({key:'queryChartData',params:params},function(data){
            var respData = data.queryChartData;
            var flag = this.params.from == this.params.to;
            if(flag)this.overturnData(respData);
            this.changeChartCfg(respData,"按民族分类统计历年新生人数变化趋势");
            this.nldchart.redraw(this.chartCfg,true);
        },this);
        params.tjzb = 5;
        this.callService({key:'queryChartData',params:params},function(data){
            var respData = data.queryChartData;
            var flag = this.params.from == this.params.to;
            if(flag)this.overturnData(respData);
            this.changeChartCfg(respData,"按生源地统计历年新生人数变化趋势");
            this.sydchart.redraw(this.chartCfg,true);
        },this);
        params.tjzb = 6;
        this.callService({key:'queryChartData',params:params},function(data){
            var respData = data.queryChartData;
            var flag = this.params.from == this.params.to;
            if(flag)this.overturnData(respData);
            this.changeChartCfg(respData,"按性别统计历年新生人数变化趋势");
            this.sexchart.redraw(this.chartCfg,true);
        },this);
    },
    /**
     * 初始化页面参数
     */
    initParams:function(){
        var nfdata = this.simpleDate.getValue();
        var navdata = this.navigation.getValue();
        this.params.from = nfdata.from;
        this.params.to = nfdata.to;
        this.params.zzjgId = navdata.nodes[0].id;
    },
    /**
     * 更改图形组件配置。
     */
    changeChartCfg:function(respData,title){
        var cfg = {
            title:'<b>'+title+'</b>',
            type:this.currentType||'line',
            yAxis:'人数',
            sfqx:false
        };
        var config = this.translateData(respData,cfg);
        config.tooltip.pointFormat='<span style="color:{series.color};padding:0">{series.name}: </span><span style="padding:0"><b>{point.y} 人</b></span><br>';
        config.legend={
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0};
        this.chartCfg = config;
    },
    /**
     * 翻转数据
     */
    overturnData :function(respData){
        for(var i =0 ;i<respData.length;i++){
            var obj = respData[i];
            var temp = obj.name,field = obj.field;
            obj.name = field;
            obj.field = temp;
        }
    }
});