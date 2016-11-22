/**
 * 科研人员基本情况统计
 * User: zhangzg
 * Date: 14-9-4
 * Time: 下午4:13
 *
 */
NS.define('Pages.sc.keyan.KeyanRySituation',{
    extend:'Pages.sc.TeacherSituation',
    modelConfig: {
        serviceConfig: {
            queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，教学组织结构
            getXbfbByTjlb:'kyryService?getXbfbByTjlb',// 统计性别分布
            getNldfbByTjlb:'kyryService?getNldfbByTjlb',// 年龄段
            getXwzcByTjlb:'kyryService?getXwzcByTjlb',// 学位组成
            getXlzcByTjlb:'kyryService?getXlzcByTjlb',// 学历组成
            queryTjlx:'kyryService?getTjlx',
            getCountByTjlb:'kyryService?getCountByTjlb'
        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins:['Pages.sc.Scgj','Pages.sc.student.JqueryMap'],
    requires:[],
    params:{},
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'科研人员概况统计',
                pageHelpInfo:'从性别、职称、年龄、学历、学位等多方面展示科研成果及参与科研项目的教职工分布组成情况。'}
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,navigation,containerx]
        });
        this.setPageComponent(container);

        container.on('afterrender',function(){
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
                this.initParams();
                this.fillCompoByData();
            },this);

            navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.params.zzjgId = data.nodes[len-1].id;
                me.params.zzjgmc = data.nodes[len-1].mc;
                me.fillCompoByData();
            });
        },this);


    },
    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        var states = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : []
        });
        var combobox = this.combobox = new Ext.form.ComboBox({
            width:250,
            store: states,
            fieldLabel:'参与科研活动类型',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        var text = this.text = new Ext.Component({
            html :'<span>人数总计：<span style="color: red;">--</span>人。</span>',
            margin:'13 0 0 20',
            columnWidth:0.5
        });
        this.callService('queryTjlx',function(data){
            states.loadData(data.queryTjlx);
            combobox.setValue('1');
            combobox.on('change',function(compo,newValue,oldValue){
                this.params.tjzb = newValue;
                this.fillCompoByData();
            },this);
        },this);

        var component4 = this.xbzc = new Exp.component.ZonePart({
            margin:5,
            columnWidth:0.5
        });

        component4.on('afterrender',function(){
            this.update({
                title:'科研人员性别组成',
                text:'',
                boldText:'',
                chartHeight:300
            });
            var nvratio = new Exp.component.NnvRatio({
                height:300
            });
            this.insertChart(nvratio);
        });
        var component5 = this.nlfb = new Exp.component.ZonePart({
            margin:5,
            columnWidth:0.5

        });
        component5.on('afterrender',function(){
            this.update({
                title:'科研人员年龄分布',
                text:'',
                boldText:'',
                chartHeight:300
            });
            var mzzcChart = new Exp.chart.HighChart({
                height:300
            });
            this.insertChart(mzzcChart);

        });


        var component8 = this.xlzc = new Exp.component.ZonePart({
            margin:5,
            columnWidth:0.5

        });
        component8.on('afterrender',function(){
            this.update({
                title:'科研人员学历组成',
                text:'',
                boldText:'',
                chartHeight:300
            });
            var xlChart = new Exp.chart.HighChart({
                height:300
            });
            this.insertChart(xlChart);
        });


        var xwfb = this.zcfb = new Exp.component.ZonePart({
            margin:5,
            columnWidth:0.5
        });
        xwfb.on('afterrender',function(){
            this.update({
                title:'科研人员职称组成',
                text:'',
                boldText:'',
                chartHeight:300
            });
            var xwfbChart = new Exp.chart.HighChart({
                height:300
            });
            this.insertChart(xwfbChart);
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
            items:[component4,component5,component8,xwfb]
        });
        var texcontainer = new Ext.container.Container({
            layout:{
                type:'column',
                columns:2
            },
            items:[combobox,text]
        });
        var containerx = new Ext.container.Container({
            items:[texcontainer,containerxxx]
        });

        return containerx;
    },
    initParams:function(){
        var navdata = this.navigation.getValue();
        this.params.zzjgId = navdata.nodes[0].id;
        this.params.tjzb = this.combobox.getValue();
    },
    fillCompoByData:function(){
        // 总计人数
        this.callService({key:'getCountByTjlb',params:this.params},function(respData){
            this.text.update('<span>人数总计：<span style="color: red;"><b>'+respData.getCountByTjlb+'</b></span>人。</span>');
        });

        // 性别比例
        this.callService({key:'getXbfbByTjlb',params:this.params},function(data){
            this.xbzc.getChart().refreshTpl(data.getXbfbByTjlb);
        });

        // 年龄分布
        this.callService({key:'getNldfbByTjlb',params:this.params},function(data){

            var cfg = {
                title:'',
                type:'column',
                yAxis:'人数',
                sfqx:false
            };
            var config = this.translateData(data.getNldfbByTjlb,cfg);
            this.nlfb.getChart().redraw(config,true);

        });

        // 学位
        this.callService({key:'getXwzcByTjlb',params:this.params},function(data){
            var cfg = {
                title:'',
                type:'column',
                yAxis:'人数',
                sfqx:false
            };
            var config = this.translateData(data.getXwzcByTjlb,cfg);
            this.zcfb.getChart().redraw(config,true);
        });
        // 学历
        this.callService({key:'getXlzcByTjlb',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.getXlzcByTjlb.length;i++){
                var obj = data.getXlzcByTjlb[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
            }
            config = {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                tooltip: {
                    pointFormat: '{series.name}: <b> {point.y} 人</b>'
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
                credits : {// 不显示highchart标记
                    enabled : false
                },
                series: [{
                    type: 'pie',
                    name: '就读学历',
                    data: cdata
                }]
            };
            this.xlzc.getChart().redraw(config,true);
        });
    }
});