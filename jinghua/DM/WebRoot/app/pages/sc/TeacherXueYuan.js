/**
 * 教师学缘统计
 * User: zhangzg
 * Date: 15-1-17
 * Time: 下午15:44
 *
 */
NS.define('Pages.sc.TeacherXueYuan',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，教学组织结构
            queryXslp:'teacherXueyuanService?queryXslp',// 各学术流派人数
            queryBxWxzc:'teacherXueyuanService?queryBxWxzc',//本校、外校
            getXlzcByTjlb:'scTeacherService?getXlzcByTjlb',// 学历组成
            getRyxkByTjlb2:'scTeacherService?getRyxkByTjlb2',//学科门类组成
            queryByyxCc:'teacherXueyuanService?queryByyxCc',// 毕业院校-层次
            queryByyxLx:'teacherXueyuanService?queryByyxLx',// 毕业院校-类型
            queryByyxDy:'teacherXueyuanService?queryByyxDy',// 毕业院校-地域
            queryByyx:'teacherXueyuanService?queryByyx',//毕业院校人数
            queryInfo1:'teacherXueyuanService?queryInfo1',//学术流派文字
            queryInfo2:'teacherXueyuanService?queryInfo2'//毕业院校文字
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
                pageName:'教职工学缘结构统计',
                pageHelpInfo:'从学缘结构的第一学历毕业院校、学术流派等在类型、层次、分布方面的构成情况分析，反映各单位师资来源高校' +
                '的数量多样性程度，师资来源学校层次、类型、地域的多样性程度以及学术环境的多样性。'}
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
    chart1 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:250
    }),
    chart2 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:250
    }),
    chart3 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:250
    }),
    chart4 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:300
    }),
    chart5 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:300
    }),
    chart6 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:300
    }),
    chart7 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:280
    }),
    chart8 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:280
    }),
    title1 : new Exp.chart.PicAndInfo({
        title : "学术流派组成分布",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "毕业院校组成分布",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    info1:new Ext.Component({
        tpl : "<b>{bm}</b> 的教职工，毕业于 <b>{num}</b> 所院校，其中境外 <b>{jw}</b> 所，境内 <b>{jn}</b> 所。",
        data : {bm:'------',num:16,jw:0,jn:16},
        margin : '20px 30px'
    }),
    info2:new Ext.Component({
        tpl : "<b>{bm}</b> 的教职工，隶属于 <b>{num}</b> 种学术流派。",
        data : {bm:'------',num:16},
        margin : '20px 30px'
    }),
    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        var topContainer = new Ext.container.Container({
            layout:"column",
            margin:'20 0 0 0',
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
        var bottomContainer = new Ext.container.Container({
            layout:"column",
            margin:'20 0 0 0',
            items:[
                new Ext.container.Container(
                    {items:this.chart6,columnWidth: 1/3}
                ),
                new Ext.container.Container(
                    {items:this.chart5,columnWidth: 1/3}
                ),
                new Ext.container.Container(
                    {items:this.chart4,columnWidth: 1/3}
                )]
        });
        var containerx = new Ext.container.Container({

            items:[topContainer,
                this.title1,
                this.info2,this.chart7,
                this.title2,
                this.info1,bottomContainer,this.chart8]
        });

        return containerx;
    },
    fillCompoByData:function(){
        // 学术流派描述文字
        this.callService({key:'queryInfo1',params:this.params},function(data){
            this.info2.update(data.queryInfo1);

        });
        // 毕业院校描述文字
        this.callService({key:'queryInfo2',params:this.params},function(data){
            this.info1.update(data.queryInfo2);
        });
        // 学术流派
        this.callService({key:'queryXslp',params:this.params},function(data){
           var cfg = {
                title:'',
                type:'column',
                yAxis:'人数',
                sfqx:false
            };
            var config = this.translateData(data.queryXslp,cfg);
            this.chart7.redraw(config,true);

        });
        // 毕业院校
        this.callService({key:'queryByyx',params:this.params},function(data){
            var cfg = {
                title:'',
                type:'column',
                yAxis:'人数',
                sfqx:false
            };
            var config = this.translateData(data.queryByyx,cfg);
            this.chart8.redraw(config,true);

        });
        // 人员学科
        this.callService({key:'getRyxkByTjlb2',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.getRyxkByTjlb2.length;i++){
                var obj = data.getRyxkByTjlb2[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
            }
            cdata =[{name:'自然科学',y:30},
            	{name:'农业科学',y:18},
                {name:'医药科学',y:2},
                {name:'工程与技术科学',y:77},
                {name:'人文与社会科学',y:56}];
            this.renderPie(this.chart2,'学科门类组成',cdata,'人');
        });

        // 学历
        this.callService({key:'getXlzcByTjlb',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.getXlzcByTjlb.length;i++){
                var obj = data.getXlzcByTjlb[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
            }
            this.renderPie(this.chart1,'学历组成',cdata,'人');
        });
        // 同缘，异缘
        this.callService({key:'queryBxWxzc',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.queryBxWxzc.length;i++){
                var obj = data.queryBxWxzc[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
            }
            cdata =[{name:'本校',y:87},{name:'外校',y:596}];
            this.renderPie(this.chart3,'同缘异缘',cdata,'人');
        });

        /*=========================================================================*/
        // 毕业院校地域
        this.callService({key:'queryByyxDy',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.queryByyxDy.length;i++){
                var obj = data.queryByyxDy[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
            }
              cdata =[{name:'北京',y:18},{name:'天津',y:1},
                {name:'河北省',y:23},{name:'河南省',y:407},{name:'吉林省',y:3},{name:'江苏省',y:10},
                {name:'上海市',y:5},{name:'四川省',y:2},{name:'湖北省',y:87},{name:'湖南省',y:3},
                	{name:'内蒙古',y:1},{name:'浙江省',y:4},{name:'陕西',y:6},{name:'云南省',y:1},{name:'黑龙江',y:2}];
            this.renderPie(this.chart4,'毕业院校地域',cdata,'所');
        });

        // 毕业院校层次
        this.callService({key:'queryByyxCc',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.queryByyxCc.length;i++){
                var obj = data.queryByyxCc[i];
                cdata.push({name:obj.name,y:Number(obj.y)});
            }
            cdata =[{name:'985工程大学',y:23},{name:'211工程大学',y:45},
                {name:'中央部属高校',y:108},{name:'地方性直属高校',y:398},
                {name:'省部共建大学',y:12},{name:'其他',y:456}];
            this.renderPie(this.chart5,'毕业院校层次',cdata,'所');
        });
        // 毕业院校类型
        this.callService({key:'queryByyxLx',params:this.params},function(data){
            var cdata = [];
            for(var i=0;i<data.queryByyxLx.length;i++) {
                var obj = data.queryByyxLx[i];
                cdata.push({name: obj.name, y: Number(obj.y)});
            }
            cdata =[{name:'综合类大学',y:299},{name:'师范类大学',y:340},
                {name:'医药类大学',y:2},{name:'工商管理类大学',y:98},
                {name:'理工类大学',y:289},{name:'艺术类大学',y:67},
                {name:'财经类大学',y:23},{name:'军事类大学',y:2},
                {name:'农业类大学',y:189}];
            this.renderPie(this.chart6,'毕业院校类型',cdata,'所');
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
                pointFormat: '</b>{series.name}<b>{point.y}，占比<b>{point.percentage:.1f}%</b>'
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