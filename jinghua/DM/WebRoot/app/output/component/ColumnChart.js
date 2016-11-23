/**
 * 柱状图形
 */
Ext.define('Output.ColumnChart', {
    extend: 'Output.Chart',
    /**
     * 判断是否是Column Chart组件的简单形式
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean ture表示是柱形的简单形式组件
     */
    isSimpleChart: function (chartType) {
        return chartType == "Column2D" || chartType == "Column3D";
    },
    /**
     * 判断是否是Column Chart组件的复杂形式
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean true表示是柱形的复杂图形组件
     */
    isComplexChart: function (chartType) {
        return chartType == "MSColumn2D" || chartType == "MSColumn3D"
            || chartType == "ScrollColumn2D"
            || chartType == "StackedColumn2D"
            || chartType == "StackedColumn3D"
            || chartType == "ScrollStackedColumn2D";
    },
    /**
     * 得到chart的参数配置
     *
     * @return {} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;
        var chart = this.getCommenParamsForChart();

        // 柱状图形共有的属性配置
        var commonParams = {
            labelDisplay: 'AUTO',
            useEllipsesWhenOverflow: '1',
            useroundedges: '1',
            showlabels: '1',
            chartTopMargin: 22,
            chartLeftMargin: 0,
            decimals: '2',// 小数点的精确
            valuePadding: 5
            // 显示值到图形的距离
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Column2D') {
            chartParams = commonParams;
        } else if (chartType == 'Column3D') {
            chartParams = {
                canvasBgColor: 'F5FFFA,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn2D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
                anchorMinRenderDistance: '6',
                drawanchors: "0",
                labelpadding: "10"
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn3D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
//				drawanchors : "0",
                labelpadding: "10",
                canvasBgColor: 'FFFFFF,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'ScrollColumn2D') {
            chartParams = {
                pinLineThicknessDelta: 30
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == "StackedColumn2D"
            || chartType == "ScrollStackedColumn2D") {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
                anchorMinRenderDistance: '6',
                drawanchors: "0",
                showsum: "1",
                useroundedges: "1",
                bgcolor: "FFFFFF,FFFFFF",
                labelpadding: "10"
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else {
            chartParams = {};
            chartParams = this.coverChartParams(chartParams, commonParams);
        }
        return chartParams;
    }
});