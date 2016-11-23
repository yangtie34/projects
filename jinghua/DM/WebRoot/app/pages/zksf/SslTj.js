/**
 * 宿舍楼统计
 * User: zhangzg
 * Date: 14-9-17
 * Time: 下午4:31
 *
 */
NS.define('Pages.zksf.SslTj',{
    extend:'Pages.sc.TeacherSituation',
    modelConfig: {
        serviceConfig: {
            queryTitleData:'sslService?getTitleData',
            querySslRzColumn:'sslService?getSslRzColumn',
            sslrzl : "sslzsqkService?queryDormRzl",// 宿舍楼入住率
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
                pageName:'宿舍楼统计',
                pageHelpInfo:'宿舍楼概况统计，住宿概况统计'}
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
    n1 : new Exp.component.SimpleNumberWidget({
        color:"#33cc00",
        data : {
            title:'宿舍楼总数',
            value:0,
            unit:'--'
        },
        columnWidth : 0.142,
        style : {
            "text-align" : 'left'
        }
    }),
    n2 : new Exp.component.SimpleNumberWidget({
        color:"#0099ff",
        data : {
            title:'房间总数',
            value:0,
            unit:'--'
        },
        columnWidth : 0.142,
        style : {
            "text-align" : 'left'
        }
    }),
    n3 : new Exp.component.SimpleNumberWidget({
        color:"#33cc00",
        data : {
            title:'床位总数',
            value:0,
            unit:'--'
        },
        columnWidth : 0.142,
        style : {
            "text-align" : 'left'
        }
    }),
    n4 : new Exp.component.SimpleNumberWidget({
        color:"#0099ff",
        data : {
            title:'宿舍入住率',
            value:0,
            unit:'--'
        },
        columnWidth : 0.142,
        style : {
            "text-align" : 'center'
        }
    }),
    n5 : new Exp.component.SimpleNumberWidget({
        color:"#33cc00",
        data : {
            title:'空床位数',
            value:0,
            unit:'--'
        },
        columnWidth : 0.142,
        style : {
            "text-align" : 'center'
        }
    }),
    n6 : new Exp.component.SimpleNumberWidget({
        color:"#0099ff",
        data : {
            title:'可安排男生数',
            value:0,
            unit:'--'
        },
        columnWidth : 0.142,
        style : {
            "text-align" : 'right'
        }
    }),
    n7 : new Exp.component.SimpleNumberWidget({
        color:"#33cc00",
        data : {
            title:'可安排女生数',
            value:0,
            unit:'--'
        },
        columnWidth : 0.142,
        style : {
            "text-align" : 'right'
        }
    }),
    title1 : new Exp.chart.PicAndInfo({
        title : "宿舍楼入住总体情况",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    sslChart : new Exp.chart.HighChart({
        height:300
    }),
    title2 : new Exp.chart.PicAndInfo({
		title : "各宿舍楼入住率情况",
		onlyTitle : true,
		margin : "10px 0px 10px 0px"
	}),
	columnchart2 : new Exp.chart.HighChart({
		height : 300,
	}),

    /**
     * 创建中部容器组件。
     */
    createMain:function(){

        var tits = new Ext.container.Container({
            layout:'column',
            items:[this.n1,this.n2,this.n3,this.n4,this.n5,this.n6,this.n7]
        });

        var containerx = new Ext.container.Container({
            items:[tits,this.title1,this.sslChart,this.title2,this.columnchart2]
        });

        return containerx;
    },
    fillCompoByData:function(){
    	var me=this;
        this.callService({key:'querySslRzColumn',params:this.params},function(respData){
            var cfg = {
                title:'宿舍楼入住概况',
                type:'column',
                yAxis:'床位数',
                sfqx:false
            };
            var config = this.translateData(respData.querySslRzColumn,cfg);
            this.sslChart.redraw(config,true);
        },this);

        this.callService({key:'queryTitleData',params:this.params},function(respData){
            var data = respData.queryTitleData;
            this.n1.update({title:"宿舍楼总数",value:data.lycount,unit:"栋"});
            this.n2.update({title:"房间总数",value:data.fjcount,unit:"间"});
            this.n3.update({title:"床位数",value:data.cwcount,unit:"个"});
            this.n4.update({title:"入住率",value:data.yrzcount,unit:"%"});
            this.n5.update({title:"空床位数",value:data.kcwcount,unit:"个"});
            this.n6.update({title:"可安排男生",value:data.nscwcount,unit:"名"});
            this.n7.update({title:"可安排女生",value:data.nvscwcount,unit:"名"});
        },this);

        this.callService({key:'sslrzl',params:this.params},function(respData){
            var cfg = {
                title:'各宿舍楼入住率',
                type:'column',
                yAxis:'%',
                sfqx:false
            };
            var config = this.translateData(respData.sslrzl,cfg);
            this.columnchart2.redraw(config,true);
        },this);

    }
});