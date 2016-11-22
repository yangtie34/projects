/**
 * 饼状图形
 *
 * @author haw_king
 *         @example
 *         Ext.create('Ext.PieChart',{
 *          title : '标题名称',
			componentType : 'Pie2D',
			unit : '头',
			model : [ '牛', '羊', '猪', '马' ],// 维度名称
			data : [ {
				"牛" : '200',
				"羊" : '230',
				"猪" : '120',
				"马" : '130',
				label : 'hide'//饼图此处隐藏
	} ]
 *      });
 *      chart.getLibComponent();//放到panel内或者其他容器内即可
 */
Ext.define('Output.PieChart', {
    extend: 'Output.Chart',
    /**
     * 判断是否是pieChart组件
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {}
     */
    isSimpleChart: function (chartType) {
        return chartType == "Pie2D" || chartType == "Pie3D";
    },
    /**
     * 饼图无复杂图形 所有均返回false
     *
     * @return {Boolean}
     */
    isComplexChart: function () {
        return false;
    },
    getChartColor: function (chartType, index) {
        var colorListFor2D = ['b2d9f8', 'f6bc0c', 'bbd66b', 'cb97fe', 'ff94b2'];
        var colorListFor3D = ['b2d9f8', 'f6bc0c', 'bbd66b', 'f89f5b', '2ca9af',
            'e45f62', 'ba84b6', 'a1ba78', 'ddd856'];
        try {
            if (chartType == 'Pie2D') {
                return colorListFor2D[index];
            } else if (chartType == 'Pie3D') {
                return colorListFor3D[index];
            } else {
                return;
            }
        } catch (e) {
            return;// 数组越界的时候返回（颜色则自定义）
        }
    },
    /**
     * 得到chart图形的参数配置
     *
     * @return {} chart参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;

        var chart = this.getCommenParamsForChart();
        var commonParams = {
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
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Pie2D') {
            chartParams = {
                bgalpha: "60"
                // ,pieRadius : '100'// 饼图的半径
                // bgratio : "100"//背景比例
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'Pie3D') {
            chartParams = commonParams;
        }
        return chartParams;
    },
    /**
     * 得到chart类型配置
     *
     * @return {} 暂不设置其样式数据
     */
    getChartStyles: function () {
        var styles = {
            "definition": [
                {
                    "type": "font",
                    "name": "captionFont",
                    "color": "6B6B6B",
                    "size": "12"
                }
            ],
            "application": [
                {
                    "toobject": "caption",
                    "styles": "captionFont"
                }
            ]
        };
        return styles;
    }
});