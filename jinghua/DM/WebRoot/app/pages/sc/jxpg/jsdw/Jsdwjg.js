/**
 * 2.2 教师队伍-教师队伍结构
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.jsdw.Jsdwjg',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教师队伍结构',
            pageHelpInfo:'教师队伍结构'
        }
    }),
    tableName : "TB_JXPG_JSDW_JSDWJG",
    beforeSaveInvokeService : "jxpgJsdwService?saveBeforeJsdwjg",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/jsdwjg.html'}],

    //创建chart图组件，并拼装返回给基类处理
     createSelfComps : function(){
     var me = this;
     me.zc_t = new Exp.chart.HighChart({
         cls : 'div-border',
         height : 300,
         columnWidth : 1/2,
         style : {
         padding : "0px",
         "border-right-width" : "0px"
         }
     });
     me.zc_f = new Exp.chart.HighChart({
         cls : 'div-border',
         height : 300,
         columnWidth : 1/2,
         style : {
         padding : "0px",
         "border-right-width" : "0px"
         }
     });

         me.xw_t = new Exp.chart.HighChart({
             cls : 'div-border',
             height : 300,
             columnWidth : 1/2,
             style : {
                 padding : "0px",
                 "border-right-width" : "0px"
             }
         });
         me.xw_f = new Exp.chart.HighChart({
             cls : 'div-border',
             height : 300,
             columnWidth : 1/2,
             style : {
                 padding : "0px",
                 "border-right-width" : "0px"
             }
         });

         me.nl_t = new Exp.chart.HighChart({
             cls : 'div-border',
             height : 300,
             columnWidth : 1/2,
             style : {
                 padding : "0px",
                 "border-right-width" : "0px"
             }
         });
         me.nl_f = new Exp.chart.HighChart({
             cls : 'div-border',
             height : 300,
             columnWidth : 1/2,
             style : {
                 padding : "0px",
                 "border-right-width" : "0px"
             }
         });

         me.xy_t = new Exp.chart.HighChart({
             cls : 'div-border',
             height : 300,
             columnWidth : 1/2,
             style : {
                 padding : "0px",
                 "border-right-width" : "0px"
             }
         });
         me.xy_f = new Exp.chart.HighChart({
             cls : 'div-border',
             height : 300,
             columnWidth : 1/2,
             style : {
                 padding : "0px",
                 "border-right-width" : "0px"
             }
         });

     var pieChart = new NS.container.Container({
     layout : 'column',
     style:{
     "margin-bottom" : "10px"
     },
     items : [me.zc_t,me.zc_f,
               me.xw_t,me.xw_f,
               me.nl_t,me.nl_f,
               me.xy_t,me.xy_f]
     });

     return pieChart;
     },
     //结合数据渲染渲染chart图
     renderCharts : function(data){
     var data =data[0];
     var me = this;
     me.zc_t.addChart(
         me.renderPieChart({
         divId :me.zc_t.id,
         title: "职称（采集）",
         data :[{name: '教授', y: parseInt(data.JS_T) },
               {name: '副教授', y: parseInt(data.FJS_T) },
               {name: '讲师', y: parseInt(data.JS1_T) },
               {name: '助教', y: parseInt(data.ZJZC_T) },
               {name: '其他正高级', y: parseInt(data.QTZGJ_T) },
               {name: '其他副高级', y: parseInt(data.QTFGJ_T) },
               {name: '其他中级', y: parseInt(data.QTZJ_T) },
               {name: '其他初级', y: parseInt(data.QTCJ_T) },
               {name: '未评级', y: parseInt(data.WPJ_T) }
         ],
         showLable: true
         })
     );

     me.zc_f.addChart(
         me.renderPieChart({
         divId :me.zc_f.id,
         title: "职称（上报）",
         data :[{name: '教授', y: parseInt(data.JS_F) },
             {name: '副教授', y: parseInt(data.FJS_F) },
             {name: '讲师', y: parseInt(data.JS1_F) },
             {name: '助教', y: parseInt(data.ZJZC_F) },
             {name: '其他正高级', y: parseInt(data.QTZGJ_F) },
             {name: '其他副高级', y: parseInt(data.QTFGJ_F) },
             {name: '其他中级', y: parseInt(data.QTZJ_F) },
             {name: '其他初级', y: parseInt(data.QTCJ_F) },
             {name: '未评级', y: parseInt(data.WPJ_F) }
         ],
         showLable: true
         }));

         me.xw_t.addChart(
             me.renderPieChart({
                 divId :me.xw_t.id,
                 title: "学位（采集）",
                 data :[{name: '博士', y: parseInt(data.BS_T) },
                     {name: '硕士', y: parseInt(data.SS_T) },
                     {name: '学位', y: parseInt(data.XS_T) },
                     {name: '无学位', y: parseInt(data.WXW_T) }
                 ],
                 showLable: true
             })
         );
         me.xw_f.addChart(
             me.renderPieChart({
                 divId :me.xw_f.id,
                 title: "学位（上报）",
                 data :[{name: '博士', y: parseInt(data.BS_F) },
                     {name: '硕士', y: parseInt(data.SS_F) },
                     {name: '学位', y: parseInt(data.XS_F) },
                     {name: '无学位', y: parseInt(data.WXW_F) }
                 ],
                 showLable: true
             }));

         me.nl_t.addChart(
             me.renderPieChart({
                 divId :me.nl_t.id,
                 title: "年龄（采集）",
                 data :[{name: '35岁以下', y: parseInt(data.N35_T) },
                     {name: '36岁到45岁', y: parseInt(data.N36_45_T) },
                     {name: '46岁到55岁', y: parseInt(data.N46_55_T) },
                     {name: '56岁以上', y: parseInt(data.N56_T) }
                 ],
                 showLable: true
             })
         );

         me.nl_f.addChart(
             me.renderPieChart({
                 divId :me.nl_f.id,
                 title: "年龄（上报）",
                 data :[{name: '35岁以下', y: parseInt(data.N35_F) },
                     {name: '36岁到45岁', y: parseInt(data.N36_45_F) },
                     {name: '46岁到55岁', y: parseInt(data.N46_55_F) },
                     {name: '56岁以上', y: parseInt(data.N56_F) }
                 ],
                 showLable: true
             }));

         me.xy_t.addChart(
             me.renderPieChart({
                 divId :me.xy_t.id,
                 title: "学缘（采集）",
                 data :[{name: '本校', y: parseInt(data.BX_T) },
                     {name: '外校：境内', y: parseInt(data.JN_T) },
                     {name: '外校：境外', y: parseInt(data.JW_T) }
                 ],
                 showLable: true
             })
         );

         me.xy_f.addChart(
             me.renderPieChart({
                 divId :me.xy_f.id,
                 title: "学缘（上报）",
                 data :[{name: '本校', y: parseInt(data.BX_F) },
                     {name: '外校：境内', y: parseInt(data.JN_F) },
                     {name: '外校：境外', y: parseInt(data.JW_F) }
                 ],
                 showLable: true
             }));
     }
});