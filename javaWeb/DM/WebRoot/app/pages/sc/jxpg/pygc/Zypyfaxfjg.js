/**
 * 专业培养方案学分结构
 * sunwg
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.pygc.Zypyfaxfjg',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'专业培养方案学分结构',
            pageHelpInfo:'专业培养方案学分结构'
        }
    }),
    tableName : "TB_JXPG_PYGC_ZYPYFAXFJG",
    afterSaveInvokeService :"",
    beforeSaveInvokeService : "",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Zypyfaxfjg.html'}],
    
    /*//创建chart图组件，并拼装返回给基类处理
    createSelfComps : function(){
    	var me = this;
    	me.piechart1 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		columnWidth : 1/2,
    		style : {
    			padding : "0px",
    			"border-right-width" : "0px"
    		}
    	});
    	me.piechart2 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		columnWidth : 1/2,
    		style : {
    			padding : "0px",
    			"border-right-width" : "0px"
    		}
    	});
    	me.columnchart1 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		columnWidth : 1/2,
    		style : {
    			padding : "0px"
    		}
    	});
    	
    	var pieChart = new NS.container.Container({
    		layout : 'column',
            style:{
                "margin-bottom" : "10px"
            },
            items : [me.piechart1,me.piechart2]
        });
    	
    	var result = new NS.container.Container({
            style:{
                "margin-bottom" : "10px"
            },
            items : [pieChart,me.columnchart1]
        });
    	
    	
    	
		return result;
	},
	//结合数据渲染渲染chart图
	renderCharts : function(data){
		var me = this;
		me.columnchart1.addChart(
			me.renderCommonChart({
				divId : me.columnchart1.id,
	    	    title : "各院系住宿人数",
	    	    yAxis : "件",
	    	    isSort : false,
	    	    data : [{name : '男' ,field : '一月',value : 200 },{name : '男' ,field : '二月',value : 200},
	    	            {name : '女' ,field : '一月',value : 2020 },{name : '女' ,field : '二月',value : 2020}],
	    	    type :"column" 
			})
		);
		
		me.piechart1.addChart(
			me.renderPieChart({
				divId :me.piechart1.id,
				title: "住宿学生男女占比",
				data :[{name: '实践课', y: 62.5, num :200 },{name: '理论课', y: 37.5, num :120 }],
				showLable: true
			})
		);
		
		me.piechart2.addChart(
				me.renderPieChart({
					divId :me.piechart2.id,
					title: "住宿学生男女占比",
					data :[{name: '实践课', y: 62.5, num :200 },{name: '理论课', y: 37.5, num :120 }],
					showLable: true
				})
			);
	}*/
});