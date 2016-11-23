NS.define('NS.chart.Column',{
   extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Column2D,Column3D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : 'MSColumn2D,MSColumn3D,ScrollColumn2D,StackedColumn2D,StackedColumn3D,ScrollStackedColumn2D',

    /**
     * 得到chart的参数配置
     *
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            chartParams,
            basicParams = this.getBasicParams(),
            //柱形图的属性
            commonParams = {
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

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Column2D') {
            chartParams = commonParams;
        } else if (chartType == 'Column3D') {
            chartParams = {
                canvasBgColor: 'F5FFFA,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn2D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
                anchorMinRenderDistance: '6',
                drawanchors: "0",
                labelpadding: "10"
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn3D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
//				drawanchors : "0",
                labelpadding: "10",
                canvasBgColor: 'FFFFFF,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'ScrollColumn2D') {
            chartParams = {
                pinLineThicknessDelta: 30
            };
            chartParams = this.mergeParams(chartParams, commonParams);
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
            chartParams = this.mergeParams(chartParams, commonParams);
        } else {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        }
        return {chart : chartParams};
    }
});