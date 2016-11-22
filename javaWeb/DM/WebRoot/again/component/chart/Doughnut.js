NS.define('NS.chart.Doughnut',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Doughnut2D,Doughnut3D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : '',

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
                skipOverlapLabels: '1',
                legendShadow: '0',// legend 的阴影显示
//					enableRotation:1,//是否开启旋转,这个属性跟点击图形展开冲突（它俩是互斥关系）
                // legendBorderColor : 'FFFFFF',
                showvalues: '1',
                palette: 2,// 内置样式1-5
                // showZeroPies:'0',//是否显示0值
                enableSmartLabels: 0
                // 设置是否连接线
                // chartTopMargin : 0,
                // chartLeftMargin:20,
            };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Doughnut2D') {
            chartParams = {};
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else if (chartType == 'Doughnut3D') {
            chartParams = {
//						pieYScale:'180'//图立起来的角度,角度越大,图形展示就越大
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        }

        return {chart : chartParams};
    }
});