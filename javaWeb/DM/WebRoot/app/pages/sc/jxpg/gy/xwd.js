/**
 * 教学评估-学位点概况
 */
NS.define('Pages.sc.jxpg.gy.xwd',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学位点概况',
            pageHelpInfo:'学位点概况'
        }
    }),
    tableName : "TB_JXPG_GY_XW",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/gy/tpl/xwd.html'}],
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
    			padding : "0px",
    			"border-left":"0px"
    		}
    	});
    	
    	var result = new NS.container.Container({
    		layout : 'column',
            style:{
                "margin-bottom" : "10px"
            },
            items : [me.columnchart1,me.columnchart2]
        });
		return result;
	},
	//结合数据渲染渲染chart图
	renderCharts : function(data){
		var dt = data[0];
		var me = this;
		var trData = [{name : '学位点' ,field : '博士点',value : parseInt(dt.B_T) + parseInt(dt.C_T)},
		              {name : '学位点' ,field : '博士后流动站',value : parseInt(dt.A_T)},
		              {name : '学位点' ,field : '硕士点',value : parseInt(dt.D_T) + parseInt(dt.E_T)},
		              {name : '学位点' ,field : '本科专业总数',value : parseInt(dt.F1_T)},
		              {name : '学位点' ,field : '本科新专业',value : parseInt(dt.F2_T)},
		              {name : '学位点' ,field : '重点学科数',value : parseInt(dt.G_T)}];
		var srData = [{name : '学位点' ,field : '博士点',value : parseInt(dt.B_F) + parseInt(dt.C_F)},
		              {name : '学位点' ,field : '博士后流动站',value : parseInt(dt.A_F)},
		              {name : '学位点' ,field : '硕士点',value : parseInt(dt.D_F) + parseInt(dt.E_F)},
		              {name : '学位点' ,field : '本科专业总数',value : parseInt(dt.F1_F)},
		              {name : '学位点' ,field : '本科新专业',value : parseInt(dt.F2_F)},
		              {name : '学位点' ,field : '重点学科数',value : parseInt(dt.G_F)}];
		
		
		
		me.columnchart1.addChart(
			me.renderCommonChart({
				divId : me.columnchart1.id,
	    	    title : "学位点概况(采集)",
	    	    yAxis : "个",
	    	    isSort : false,
	    	    data : trData,
	    	    type :"column" 
			})
		);
		
		me.columnchart2.addChart(
			me.renderCommonChart({
				divId : me.columnchart2.id,
	    	    title : "学位点概况(上报)",
	    	    yAxis : "个",
	    	    isSort : false,
	    	    data : srData,
	    	    type :"column" 
			})
		);
		
	}
});