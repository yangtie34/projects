NS.define('NS.chart.Bar',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Bar2D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : 'MSBar2D,MSBar3D,StackedBar2D,StackedBar3D',

    /**
     * 得到chart的参数配置
     *
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            basicParams = this.getBasicParams(),
            chartParams,
            commonParams = {
                useroundedges : '1',
                legendShadow : '0',
                labelDisplay : 'AUTO',// 自动下移字
                decimals : '2',// 小数点的精确
                showlabels : '1',
                chartTopMargin : 22,
                chartRightMargin : 22,
                chartLeftMargin : 0
            };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Bar2D') {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSBar2D') {
            chartParams = {
                legendShadow : '1'
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSBar3D') {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'StackedBar2D') {
            chartParams = {
                legendShadow : '1',
                showsum : "1"// 显示总和
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        }

        return {chart : chartParams};
    }
});