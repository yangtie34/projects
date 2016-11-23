/**
 * 2.3 教师队伍-各教学单位教师与本科生情况
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.jsdw.Gjxdwjsybksqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各教学单位教师与本科生情况',
            pageHelpInfo:'各教学单位教师与本科生情况'
        }
    }),
    tableName : "TB_JXPG_JSDW_GJXDWJSYBKSQK",
   beforeSaveInvokeService : "jxpgJsdwService?saveBeforeGjxdwjsybksqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/gjxdwjsybksqk.html'}],
    //创建chart图组件，并拼装返回给基类处理
     createSelfComps : function(){
     var me = this;
     me.columnchart1 = new Exp.chart.HighChart({
     cls : 'div-border',
     height : 300,
     columnWidth : 1/2,
     style : {
     padding : "0px"
     }
     });
         me.columnchart2 = new Exp.chart.HighChart({
             cls : 'div-border',
             height : 300,
             columnWidth : 1/2,
             style : {
                 padding : "0px"
             }
         });
     var result = new NS.container.Container({
     style:{
     "margin-bottom" : "10px"
     },
     items : [me.columnchart1,me.columnchart2]
     });

     return result;
     },
     //结合数据渲染渲染chart图
     renderCharts : function(data){
     var me = this;
     me.columnchart1.addChart(
     me.renderCommonChart({
     divId : me.columnchart1.id,
     title : "专任教师总数生师比结构图(上报)",
     yAxis : "",
     isSort : false,
     data : me.getColumnData(data,'LR'),
     type :"column"
     })
     );
         me.columnchart2.addChart(
             me.renderCommonChart({
                 divId : me.columnchart2.id,
                 title : "专任教师总数生师比结构图(采集)",
                 yAxis : "",
                 isSort : false,
                 data : me.getColumnData(data,'ZS'),
                 type :"column"
             })
         );
     },
    getColumnData : function(data,type){
        var newData = new Array();
        for(var i=0;i<data.length;i++){
            if(type == 'LR'){
                var zyMc = data[i].MC;
                var zrjsZs = data[i].ZS_F;
                var bksrs = data[i].BKSS_F;
            }
            if(type == 'ZS'){
                var zyMc = data[i].MC;
                var zrjsZs = data[i].ZS_T;
                var bksrs = data[i].BKSS_T;
            }

            newData.push({name:'专任教师总数',field:zyMc,value:parseInt(zrjsZs)},{name:'本科生数',field:zyMc,value:parseInt(bksrs)});
        }
        return newData;
    }
});