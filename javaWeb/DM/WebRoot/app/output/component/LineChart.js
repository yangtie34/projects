/**
 * 线状图形
 */
Ext.define('Output.LineChart', {
    extend: 'Output.Chart',
    isSimpleChart: function (chartType) {
        return chartType == "Line";
    },
    isComplexChart: function (chartType) {
        return chartType == "MSLine" || chartType == "ScrollLine2D";
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
            showlabels: '1',
            legendBorderColor: '',
            chartTopMargin: 22,
            chartLeftMargin: 0
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Line') {
            chartParams = {
                palette: "2",
                canvasBorderThickness: '0',
                canvasbgAlpha: '10',
                canvaspadding: "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == 'MSLine') {
            chartParams = {
                palette: "2",
                // labelpadding : "10",
//						divlinealpha : "30",
                canvasBorderThickness: '0',//边框款第
                // canvasBorderColor:'FFFFFF',//边框颜色
                canvasBgColor: 'F5FFFA,FFFFFF',// 画布颜色
                canvasbgAlpha: '10',
//						alternatehgridalpha : "20",
                canvaspadding: "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == "ScrollLine2D") {
            chartParams = {
                canvasbgAlpha: '10',
                canvaspadding: "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        }
        return chartParams;
    }
});