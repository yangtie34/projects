/**
 * 1.3 定位目标-校级教学管理人员结构
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.dwmb.Xjjxglryjg',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'校级教学管理人员结构',
            pageHelpInfo:'校级教学管理人员结构'
        }
    }),
    tableName : "TB_JXPG_DWMB_XJJXGLRYJG",
    beforeSaveInvokeService : "jxpgDwmbService?saveBeforeXjjxglryjg",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/dwmb/tpl/xjjxglryjg.html'}],
    
    createSelfComps : function(){
    	var me = this;
    	me.Tchar1 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		style : {
    			padding : "0px",
    			"border-right-width" : "0px"
    		}
    	});
    	me.Tchar2 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		style : {
    			padding : "0px",
    			"border-top" : "0px",
    			"border-right-width" : "0px"
    		}
    	});
    	me.Tchar3 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		style : {
    			padding : "0px",
    			"border-top" : "0px",
    			"border-right-width" : "0px"
    		}
    	});
    	me.Fchar1 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		style : {
    			padding : "0px",
    		}
    	});
    	me.Fchar2 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		style : {
    			padding : "0px",
    			"border-top" : "0px",
    		}
    	});
    	me.Fchar3 = new Exp.chart.HighChart({
    		cls : 'div-border',
    		height : 300,
    		style : {
    			padding : "0px",
    			"border-top" : "0px",
    		}
    	});
    	var cmpTtitle = new NS.container.Container({
    		style : {'color':'#0DBE00', 'font': "bold 16px 'Microsoft YaHei"},
    		html  : '原始数据如下图：'
    	});
    	var cmpT = new NS.container.Container({
    		columnWidth : 1/2,
    		items : [me.Tchar1, me.Tchar2, me.Tchar3]
    	});
    	var cmpFtitle = new NS.container.Container({
    		style : {'color':'#E92929', 'font': "bold 16px 'Microsoft YaHei"},
    		html  : '填报数据如下图：'
    	});
    	var cmpF = new NS.container.Container({
    		columnWidth : 1/2,
    		items : [me.Fchar1,me.Fchar2,me.Fchar3]
    	});
    	var result = new NS.container.Container({
    		layout : 'column',
            items : [cmpT, cmpF]
        });
		return result;
	},
	renderCharts : function(data){
		var me = this;
		// 解析数据
		var ZC_T=[], ZC_F=[],  XL_T=[], XL_F=[],  NL_T=[], NL_F=[];
		for(var i in data){
			var obj = data[i];
			if(obj.DM == 'SL'){
				ZJ = obj.ZJ;
				// 职称
				ZC_T.push({name:'正高级', y:obj.ZGJ_T});
				ZC_T.push({name:'副高级', y:obj.FGJ_T});
				ZC_T.push({name:'中级', y:obj.ZJZC_T});
				ZC_T.push({name:'初级', y:obj.CJZC_T});
				ZC_T.push({name:'无职称', y:obj.WZC_T});
				ZC_F.push({name:'正高级', y:obj.ZGJ_F});
				ZC_F.push({name:'副高级', y:obj.FGJ_F});
				ZC_F.push({name:'中级', y:obj.ZJZC_F});
				ZC_F.push({name:'初级', y:obj.CJZC_F});
				ZC_F.push({name:'无职称', y:obj.WZC_F});
				// 学历
				var b1='博士', b2='硕士', b3='学士', b4='无学位';
				XL_T.push({name:b1, y:obj.BS_T});
				XL_T.push({name:b2, y:obj.SS_T});
				XL_T.push({name:b3, y:obj.XS_T});
				XL_T.push({name:b4, y:obj.WXW_T});
				XL_F.push({name:b1, y:obj.BS_F});
				XL_F.push({name:b2, y:obj.SS_F});
				XL_F.push({name:b3, y:obj.XS_F});
				XL_F.push({name:b4, y:obj.WXW_F});
				// 年龄
				var c1='35岁以下', c2='36-45', c3='46-35', c4='56岁以上';
				NL_T.push({name:c1, y:obj.N35_T});
				NL_T.push({name:c2, y:obj.N36_45_T});
				NL_T.push({name:c3, y:obj.N46_55_T});
				NL_T.push({name:c4, y:obj.N56_T});
				NL_F.push({name:c1, y:obj.N35_F});
				NL_F.push({name:c2, y:obj.N36_45_F});
				NL_F.push({name:c3, y:obj.N46_55_F});
				NL_F.push({name:c4, y:obj.N56_F});
			}
		}
		var nameT = '（采集）', nameF = '（上报）';
		me.Tchar1.addChart(
				me.renderPieChart({
					divId :me.Tchar1.id,
					title: "职称"+nameT,
					data : ZC_T,
					showLable: true
				})
		);
		me.Tchar2.addChart(
				me.renderPieChart({
					divId :me.Tchar2.id,
					title: "学历"+nameT,
					data : XL_T,
					showLable: true
				})
		);
		me.Tchar3.addChart(
				me.renderPieChart({
					divId :me.Tchar3.id,
					title: "年龄"+nameT,
					data : NL_T,
					showLable: true
				})
		);
		me.Fchar1.addChart(
			me.renderPieChart({
				divId :me.Fchar1.id,
				title: "职称"+nameF,
				data : ZC_F,
				showLable: true
			})
		);
		me.Fchar2.addChart(
			me.renderPieChart({
				divId :me.Fchar2.id,
				title: "学历"+nameF,
				data : XL_F,
				showLable: true
			})
		);
		me.Fchar3.addChart(
			me.renderPieChart({
				divId :me.Fchar3.id,
				title: "年龄"+nameF,
				data : NL_F,
				showLable: true
			})
		);
	}
	
});