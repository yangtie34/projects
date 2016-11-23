ComplexColumnCharts = function(){
    var ColumnArg = arguments[0];
    var columnJSONDataAll = {};
    this.setParams = function(){
       var arg = arguments[0];
       
       columnJSONDataAll = {
			"chart" : {
				"caption" : arg.caption||"考勤统计图",
				"xaxisname" : arg.xaxisname||"考勤类型",
				"subcaption " : arg.subcaption || "",// 副标题
				"formatNumberScale" : arg.formatNumberScale || '0',// 1 or// 0,默认1,格式化数据
				"legendShadow":arg.legendShadow||'0',//默认没有阴影
				"divlinealpha" : "30",
				"drawanchors" : "0",
				"labelpadding" : "10",
				"numbersuffix" : arg.numbersuffix||"人次",
				"showvalues" : "1",
				"showBorder":arg.showBorder||'0',//否显示边框
				"yaxisvaluespadding" : "10",
				"bgColor" : arg.bgColor||"ffffff",// 背景色，默认白色
				"numdivlines" : "10",
				"useroundedges" : arg.useroundedges||"1",
				"legendborderalpha" : "0"
			},
			"categories":arg.categories
		}
	}
	
}