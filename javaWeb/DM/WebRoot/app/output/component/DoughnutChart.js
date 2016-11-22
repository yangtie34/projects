/**
 * 环状图形
 */
Ext.define('Output.DoughnutChart', {
    extend: 'Output.Chart',
    /**
     * 判断是否是Column Chart组件的简单形式
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean ture表示是柱形的简单形式组件
     */
    isSimpleChart: function (chartType) {
        return chartType == "Doughnut2D" || chartType == "Doughnut3D";
    },
    /**
     * 环形无复杂图形
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean true表示是柱形的复杂图形组件
     */
    isComplexChart: function (chartType) {
        return false;
    },
    getChartColor: function (chartType, index) {
        var colorList = ['c4d0a2', 'acacac', 'aac165', '7d7d7d'];
        try {
            return colorList[index];
        } catch (e) {
            return;
        }
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
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Doughnut2D') {
            chartParams = {};
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == 'Doughnut3D') {
            chartParams = {
//						pieYScale:'180'//图立起来的角度,角度越大,图形展示就越大
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        }
        return chartParams;
    }
});