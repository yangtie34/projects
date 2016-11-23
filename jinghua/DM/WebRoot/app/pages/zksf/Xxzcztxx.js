/**
 * 学校资产总体信息
 */
NS.define('Pages.zksf.Xxzcztxx', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'资产总体信息',
            pageHelpInfo:'统计学校各类资产情况。'
        }
    }),
	modelConfig: {
	    serviceConfig: {
	    	queryZcztgk : "yqsbxxService?queryZcztgk",//查询资产总体概况
			queryZczjtr : "yqsbxxService?queryZcztgk",//资产投入
	   }
	},
	tplRequires : [
	   {fieldname : 'toptpl',path : 'app/pages/zksf/tpl/xxzc_main.html'}
	],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css','app/pages/zksf/css/xxzcxx.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	init: function () {
	    var comps = this.createComps();
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[this.pageTitle,comps],
			style : {
				"min-width" : "1000px"
			}
	    });
	    container.on("afterrender",function(){
	    	this.fillCompData();
	    	this.bindEvents();
	    },this);
	    this.setPageComponent(container);
	    
	},
	topMetro : new Ext.Component({
		tpl : "",
		data : {}
	}),
	title1 : new Exp.chart.PicAndInfo({
		title : "资产订购情况",
		onlyTitle : true,
		margin : "10px 0px 0px 0px"
	}),
	chartm : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		style : {
			padding : "0px"
		}
	}),
	title2 : new Exp.chart.PicAndInfo({
		title : "维修记录",
		onlyTitle : true,
		margin : "10px 0 0 0"
	}),
	tableBottom : new Ext.Component({
		tpl :'<table class="table1"><thead><tr><th >时间</th> <th>耗费金额</th> <th>维修备注</th></tr></thead>'+
		   '<tbody><tpl if="values.length == 0"> <tr><td colspan="6"><span style="color : red;">数据未维护</span></td></tr></tpl><tpl for="."><tr><td>{yx}</td><td>{kyrs}</td></tr></tpl></tbody></table>' ,
		data : []
	}),
	createComps : function(){
		this.topMetro.tpl = this.toptpl.tpl;
		var me = this;
		var result = new NS.container.Container({
			items : [me.topMetro/*,me.title1,me.chartm,me.title2,me.tableBottom*/]
		});
		return result;
	},
	
	fillCompData : function(){
		var me = this;
		me.callService({key : "queryZcztgk",params : {}},function(data){
			me.topMetro.update(data.queryZcztgk);
		});
		me.chartm.addChart(me.renderCommonChart({
			title : "资产订购情况记录",
			type : 'column',
			data : [],
			yAxis : "元",
			divId : me.chartm.id
		}));
	},
	bindEvents : function(){
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>数据未维护</span>"
});
