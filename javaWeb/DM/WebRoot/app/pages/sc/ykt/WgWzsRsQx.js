/**
 * 晚寝晚归及疑似未住宿学生人数曲线图。
 * User: zhangzg
 * Date: 14-11-11
 * Time: 下午6:37
 *
 */
NS.define('Pages.sc.ykt.WgWzsRsQx',{
    extend:'Pages.sc.TeacherSituation',
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
                pageName:'住宿预警分析',
                pageHelpInfo:'晚寝晚归及疑似未住宿学生人数变化曲线图'}
        });
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,containerx]
        });
        this.setPageComponent(container);
        container.on('afterrender',function(){
            var today = new Date();
            this.dateSection.setValue({from : today - 14 * 3600* 24000,to : today});
            this.params= this.dateSection.getRawValue();
            this.dateSection.addListener('validatepass',function(){
                this.params= this.dateSection.getRawValue();
                console.log(params);
                this.fillCompoByData();
            },this);

            console.log(today);
            this.fillCompoByData();
        },this);
    },
    dateSection:new NS.appExpand.DateSection(),
    title1 : new Exp.chart.PicAndInfo({
        title : "晚寝晚归学生人数变化曲线图",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    sslChart : new Exp.chart.HighChart({
        height:300
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "疑似未住宿学生人数变化曲线图",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    wzsChart : new Exp.chart.HighChart({
        height:300
    }),

    /**
     * 创建中部容器组件。
     */
    createMain:function(){

        var containerx = new Ext.container.Container({
            items:[this.title1,this.sslChart,this.title2,this.wzsChart]
        });
        return containerx;
    },
    fillCompoByData:function(){
        this.callService({key:'querySslRzColumn',params:this.params},function(respData){
            var cfg = {
                title:'晚寝晚归学生人数变化曲线',
                type:'column',
                yAxis:'人数',
                sfqx:false
            },cfg2 = {
                title:'疑似未住宿学生人数变化曲线',
                type:'column',
                yAxis:'人数',
                sfqx:false
                };
            this.sslChart.redraw(this.translateData(respData.querySslRzColumn.wg,cfg),true);
            this.wzsChart.redraw(this.translateData(respData.querySslRzColumn.wzs,cfg2),true);
        },this);
    }
});