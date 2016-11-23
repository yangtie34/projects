/**
 *@class Output.AreaChart
 */
Ext.define('Output.AreaChart', {
    extend: 'Output.Chart',
    isSimpleChart: function (chartType) {
        return chartType == "Area2D";
    },
    isComplexChart: function (chartType) {
        return chartType == "MSArea" || chartType == "StackedArea2D"
            || chartType == "ScrollArea2D";
    },
    /**
     * 得到chart的参数配置
     *
     * @return {} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;
        var chart = this.getCommenParamsForChart();
        var commonParams = {
            useroundedges: '1',
            legendShadow: '0',
            labelDisplay: 'AUTO',// 自动下移字
            decimals: '2',// 小数点的精确
            legendBorderColor: '',
            canvasBorderThickness: '0',
            canvaspadding: "10",
            showlabels: '1',
            chartLeftMargin: 0
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Area2D') {
            chartParams = commonParams;// 样式
        } else if (chartType == 'MSArea') {
            chartParams = {
//					palette : "2",
                canvasBorderThickness: '0',
                canvasBgColor: 'F5FFFA,FFFFFF',// 画布颜色
                canvasbgAlpha: '10'
//					canvaspadding : "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == "ScrollArea2D") {
            chartParams = commonParams;
        } else if (chartType == "StackedArea2D") {
            chartParams = {
                showsum: "1"// 显示总和
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else {
            chartParams = commonParams;
        }
        return chartParams;
    }
});