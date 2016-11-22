/**
 * 教职工教学任务统计
 * User: zhangzg
 * Date: 15-1-17
 * Time: 下午15:44
 *
 */
NS.define('Pages.sc.JzgJxrw',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryJxzzjgTree: 'scService?getYxCcTree',// 获取导航栏数据，教学组织结构
            queryXslp   : 'teacherJxrwService?queryYxZyjszwJxrw',// 理论学时数据
            queryByyx   : 'teacherJxrwService?queryYxBzlbJxrw',//毕业院校人数
            queryInfo1  :'teacherJxrwService?queryZyjszwJxrw',//专业技术职务
            queryInfo2  :'teacherJxrwService?queryBzlbJxrw'//
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
                pageName:'教职工教学任务统计',
                pageHelpInfo:'按职称、编制类别分学院(部)展示教师承担教学任务比例。'}
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
            this.callService('queryJxzzjgTree',function(data){
                this.navigation.refreshTpl(data.queryJxzzjgTree);
            },this);
            this.fillCompoByData();
            navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.params.zzjgId = data.nodes[len-1].id;
                me.params.zzjgmc = data.nodes[len-1].mc;
                me.fillCompoByData();
            });

            this.refreshXslp();
        },this);


    },
    refreshXslp:function(){
        // 学术流派
        this.callService({key:'queryXslp',params:this.params},function(data){
            var cfg = {
                title:this.params.xn+'-'+(this.params.xn+1)
                    +' 第' + (this.params.xq+1) +'学期 按院系对比各职称下的教师的教学任务量',
                type:'spline',
                yAxis:'学时',
                sfqx:false
            };


            var data1 = data.queryXslp;
            for(var i= 0,len = data1.length;i<len;i++){
                data1[i].field = data1[i].YXMC;
                data1[i].name =  data1[i].NAME;
                data1[i].value = data1[i].Y;
            }
            var config = this.translateData(data1,cfg);
            this.chart7.redraw(config,true);
        });
        // 毕业院校
        this.callService({key:'queryByyx',params:this.params},function(data){
            var cfg = {
                title:this.params.xn+'-'+(this.params.xn+1)
                    +' 第' + (this.params.xq+1) +'学期 按院系对比各编制类别下的教师的教学任务量',
                type:'spline',
                yAxis:'学时',
                sfqx:false
            };

            var data1 = data.queryByyx;
            for(var i= 0,len = data1.length;i<len;i++){
                data1[i].field = data1[i].YXMC;
                data1[i].name =  data1[i].NAME;
                data1[i].value = data1[i].Y;
            }

            var config = this.translateData(data1,cfg);
            this.chart8.redraw(config,true);
        });
    },
    chart1 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:250
    }),
    chart2 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:250
    }),
    chart7 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:580
    }),
    chart8 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:580
    }),
    title1 : new Exp.chart.PicAndInfo({
        title : "按职称类别分学院对比各类教师承担教学任务",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "按编制类别分学院对比各类教师承担教学任务",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        this.createCombobox();
        var xnxqContainer = new Ext.container.Container({
            layout:"column",
            margin:'5 0 0 10',
            items:[this.combobox,this.xqcombobox]
        });
        var topContainer = new Ext.container.Container({
            layout:"column",
            margin:'20 0 0 0',
            items:[
                new Ext.container.Container(
                    {items:this.chart1,columnWidth: 1/2}
                ),
                new Ext.container.Container(
                    {items:this.chart2,columnWidth: 1/2}
                )]
        });
        var containerx = new Ext.container.Container({

            items:[
                xnxqContainer,
                topContainer,
                this.title1,this.chart7,
                this.title2,this.chart8]
        });

        return containerx;
    },
    createCombobox:function(){
        var states = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : [{id:2010,mc:'2010-2011学年'},
                {id:2011,mc:'2011-2012学年'},
                {id:2012,mc:'2012-2013学年'},
                {id:2013,mc:'2013-2014学年'},
                {id:2014,mc:'2014-2015学年'},
                {id:2015,mc:'2015-2016学年'},
                {id:2016,mc:'2016-2017学年'},
                {id:2017,mc:'2017-2018学年'}]
        });

        var combobox = this.combobox = new Ext.form.ComboBox({
            width:200,
            labelWidth:60,
            store: states,
            fieldLabel:'选择学年',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        var xq = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : [{id:0,mc:'第一学期'},
                {id:1,mc:'第二学期'}]
        });
        var xqCom = this.xqcombobox = new Ext.form.ComboBox({
            width:200,
            labelWidth:60,
            store: xq,
            fieldLabel:'选择学期',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        var myDate = new Date();
        myDate.getYear();        //获取当前年份(2位)
        var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
        var month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
        var value = month<7?Number(year-1):Number(year);
        var currentxq = month<7&&month>=2?1:0;
        combobox.setValue(value);
        this.params.xn = value;
        xqCom.setValue(currentxq);
        this.params.xq = currentxq;
        combobox.on('change',function(compo,newValue,oldValue){
            this.params.xn = newValue;
            this.fillCompoByData();
            this.refreshXslp();
        },this);
        xqCom.on('change',function(compo,newValue,oldValue){
            this.params.xq = newValue;
            this.fillCompoByData();
            this.refreshXslp();

        },this);
        /*this.callService('queryTjlx',function(data){
            //states.loadData(data.queryTjlx);
            todo:添加获取学年学期列表的方法,暂时写死了，日后再改
        },this);*/
    },
    fillCompoByData:function(){

        // 人员学科
        this.callService({key:'queryInfo1',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.queryInfo1.length;i++){
                var obj = data.queryInfo1[i];
                cdata.push({name:obj.NAME,y:Number(obj.Y)});
            }

            this.renderPie(this.chart2,'各职称承担教学任务',cdata,' 学时');
        });

        // 学历
        this.callService({key:'queryInfo2',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.queryInfo2.length;i++){
                var obj = data.queryInfo2[i];
                cdata.push({name:obj.NAME,y:Number(obj.Y)});
            }
            this.renderPie(this.chart1,'各编制类别承担教学任务',cdata,'学时');
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
                pointFormat: '数量{point.y}'+dw+'，占比<b>{point.percentage:.1f}%</b>'
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