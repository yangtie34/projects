NS.define('NS.chart.Area',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Area2D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : 'MSArea,StackedArea2D,ScrollArea2D',

    /**
     * 得到chart的参数配置
     *@private
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            basicParams = this.getBasicParams(),
            chartParams,
            commonParams = {
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

        commonParams = this.mergeParams(commonParams,  basicParams);

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
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else if (chartType == "ScrollArea2D") {
            chartParams = commonParams;
        } else if (chartType == "StackedArea2D") {
            chartParams = {
                showsum: "1"// 显示总和
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else {
            chartParams = commonParams;
        }

        return {chart : chartParams};
    }
});