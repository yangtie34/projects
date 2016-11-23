/**
 * @class Output.BarChart
 * bar图形
 */
Ext.define('Output.BarChart', {
	extend : 'Output.Chart',
	isSimpleChart : function(chartType) {
		return chartType == "Bar2D";
	},
	isComplexChart : function(chartType) {
		return chartType == "MSBar2D" || chartType == "MSBar3D"
				|| chartType == "StackedBar2D" || chartType == "StackedBar3D";
	},
	/**
	 * 得到chart的参数配置
	 * 
	 * @return {} chart 参数配置
	 */
	getChartParams : function() {
		var chartType = this.chartType, chartParams = null;
		var chart = this.getCommenParamsForChart();
		var commonParams = {
			useroundedges : '1',
			legendShadow : '0',
			labelDisplay : 'AUTO',// 自动下移字
			decimals : '2',// 小数点的精确
			showlabels : '1',
			chartTopMargin : 22,
			chartRightMargin : 22,
			chartLeftMargin : 0
		};
		commonParams = this.coverChartParams(commonParams, chart);
		if (chartType == 'Bar2D') {
			chartParams = {};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else if (chartType == 'MSBar2D') {
			chartParams = {
				legendShadow : '1'
			};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else if (chartType == 'MSBar3D') {
			chartParams = {};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else if (chartType == 'StackedBar2D') {
			chartParams = {
				legendShadow : '1',
				showsum : "1"// 显示总和
			};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else {
			chartParams = {};
			chartParams = this.coverChartParams(chartParams, commonParams);
		}
		return chartParams;
	}
});