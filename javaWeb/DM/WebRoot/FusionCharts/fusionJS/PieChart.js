PieChart = function() {
	var pieChartArg = arguments[0];
	var pieJSONDataAll = {};

	this.setParams = function() {
		var arg = arguments[0];
		// alert(arg.JSONData);
		pieJSONDataAll = {
			"chart" : {
				"caption" : arg.caption||"",// 标题名称
				"subcaption " : arg.subcaption || "",// 副标题
				"baseFont":arg.baseFont||"宋体",//字体
				//"captionPadding":arg.captionPadding||'1',
				//"chartTopMargin":arg.chartTopMargin||'2',
				//"xAxisName":arg.xAxisName||null,
				//"yAxisName":arg.xAxisName||null,
				"numbersuffix" : arg.numbersuffix || "",// 单位名称(后)
				"formatNumberScale" : arg.formatNumberScale || '0',// 1 or
																	// 0,默认1,格式化数据
				"enablesmartlabels":arg.enablesmartlabels||'1',
				"legendposition" : arg.legendposition || "bottom",// 图例显示位置：default:BOTTOM/right
				"baseFontSize":arg.baseFontSize||"10",//默认字体大小
				"bgColor" : arg.bgColor||"ffffff",// 背景色，默认白色
				//"reverseLegend ":arg.reverseLegend||'1', 
				//"showValues":arg.showValues||'0',//否显示值
				"showLabels" : arg.showLabels||"0",// 不显示标签名称
				"showBorder":arg.showBorder||'0',//否显示边框
				"legendShadow":arg.legendShadow||'0',//默认没有阴影
				"showLegend" :arg.showLegend||"1"// 默认显示图例
				
			},
			"data" : [arg.JSONData]//这个地方也可以更改为arg.JSONData,全部为json格式
			
		};
	};

	this.getChart = function() {
		//alert(pieChartArg.divId+"-----");
		var pieChart = new FusionCharts(pieChartArg.url, pieChartArg.id,
				pieChartArg.width, pieChartArg.height,"0", "1");
		pieChart.setJSONData(pieJSONDataAll);
		//pieChart.render(pieChartArg.divId);
		return pieChart;
	};

	this.render = function(divId) {
		//alert(pieChartArg.divId+"*-*-*");为使格式相同，这种配置不要
		var myChart = new this.getChart();//getChart方法执行两次。。怎么办呢？
		myChart.render(divId);
	};
}