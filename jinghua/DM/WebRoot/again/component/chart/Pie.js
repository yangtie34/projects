NS.define('NS.chart.Pie',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Pie2D,Pie3D',
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
            enableSmartLabels: 0,
//			enableRotation:1,//是否开启旋转,这个属性跟点击图形展开冲突（它俩是互斥关系）
            // showZeroPies:'0',//是否显示0的饼 默认1
            skipOverlapLabels: '1',
            legendShadow: '0',// legend 的阴影显示
            // legendBorderColor : 'FFFFFF',
            palette: 2,// 内置样式1-5
            showvalues: '1'
            // ,
            // captionPadding : '10'// 标题到画布的距离
        };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Pie2D') {
            chartParams = {
                bgalpha: "60"
                // ,pieRadius : '100'// 饼图的半径
                // bgratio : "100"//背景比例
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'Pie3D') {
            chartParams = commonParams;
        }
        return {chart : chartParams};
    }
});