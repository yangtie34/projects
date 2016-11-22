/**
 * 图形组件基类 可以在添加子类的时候，同时维护此基类，此基类可以单独通用。
 *
 * @author haw_king
 *         @example
 *         var chart = Ext.create('Ext.Chart',{
 *        		title : '标题--名称',
				unit : '只',
				"xaxisname" : "数量",
				"yaxisname" : "种类",
				componentType : 'MSColumn3D',
				model : [ '牛', '羊', '猪', '马' ],// 维度名称-label
				data : [ {
					"牛" : '200',
					"羊" : '230',
					"猪" : '120',
					"马" : '130',
					seriesname : 'M1'
				}, {
					"牛" : '100',
					"羊" : '130',
					"猪" : '190',
					"马" : '30',
					seriesname : 'M2'
				} ]
 *    });
 *    chart.getLibComponent();//放到panel内或者其他容器内即可
 */
Ext.define('Output.Chart', {
    extend: 'Ext.Component',
    baseCls: '',
    frame: false,
    alias: ['widget.outputChart'],
    chartId: Ext.id() + '_chart',
    constructor: function (componentData) {
        this.callParent();
        this.componentData = componentData;
        this.templateType = componentData.templateType;// 模板类型(已经过转换后的类型维护在chartParentComponent里)
        this.createChart();

        this.addEvents('windowshow', 'windowhide', 'windowlayout');// ,
        this.addListener({
            windowshow: this._beforeWindowShow,
            windowhide: this._beforeWindowHide,
            windowlayout: this._whenWindowLayout
        });
    },
    onRender: function () {
        this.callParent(arguments);
        var id = this.el.dom.id;
        this.fusionChart.render(id);
    },
    getLibComponent: function () {
        return this;
    },
    /**
     * 当父类窗口或者tabpanel发生变动布局的时候触发
     */
    _whenWindowLayout: function () {
        this.parentCt._whenWindowLayout(this.parentCt.container);
    },
    /**
     * 窗口显示之前触发 设置该容器的宽高
     */
    _beforeWindowShow: function () {
        this.setHeight(this.el.parent().getHeight());
        this.setWidth(this.el.parent().getWidth());
    },
    /**
     * 窗口隐藏之前出发 将该容器宽高设置为0（用于隐藏）
     */
    _beforeWindowHide: function () {
        this.setHeight(0);
        this.setWidth(0);
    },
    /**
     * 创建chart
     */
    createChart: function () {
        var that = this, jsonData;
        var chartType = that.chartType = that
            .getChartType(that.componentData.style);
        var width = '100%', heigth = '100%';
        if (!this.fusionChart) {
            this.fusionChart = new FusionCharts("FusionCharts/charts/"
                + chartType + ".swf", that.chartId, width, heigth,
                "0", "1");
            // 添加chart错误的汉化提示
            this.fusionChart.configure({
                ParsingDataText: '数据读取中,请稍候...',
                PBarLoadingText: '数据加载中,请稍候...',
                RenderingChartText: '数据正在渲染,请稍候...',
                LoadDataErrorText: '数据加载错误!',
                ChartNoDataText: "无数据可供显示!"
                // add more...-->key:value
            });
        }
        jsonData = this.getChartAllData();
        this.fusionChart.setJSONData(jsonData);

    },
    /**
     * 得到图形类型
     *
     * @param {}
     *            type 图形组件类型
     */
    getChartType: function (type) {
        // 维护的图形组件类型列表
        var chartList = {
            //1、饼形
            Pie2D: "Pie2D",
            Pie3D: "Pie3D",
            //2、线形
            Line: "Line",
            MSLine: "MSLine",
            ScrollLine2D: "ScrollLine2D",
            //3、环形
            Doughnut2D: "Doughnut2D",
            Doughnut3D: "Doughnut3D",
            //4、条形
            Bar2D: "Bar2D",
            MSBar2D: "MSBar2D",
            MSBar3D: "MSBar3D",
            StackedBar2D: "StackedBar2D",
            StackedBar3D: "StackedBar3D",
            //5、柱形
            Column2D: "Column2D",
            Column3D: "Column3D",
            MSColumn2D: "MSColumn2D",
            MSColumn3D: "MSColumn3D",
            ScrollColumn2D: "ScrollColumn2D",// 滚动条的柱形图
            StackedColumn2D: "StackedColumn2D",
            StackedColumn3D: "StackedColumn3D",
            ScrollStackedColumn2D: "ScrollStackedColumn2D",
            //6、区域形
            Area2D: "Area2D",
            ScrollArea2D: "ScrollArea2D",
            StackedArea2D: "StackedArea2D",
            MSArea: "MSArea"
            // 陆续待加...24
        };
        var chartType = chartList[type] || "MSColumn2D";// 如果类型错误 默认MSColumn2D
        return chartType;
    },
    /**
     * 加载数据
     *
     * @param {}
     *            componentData 传入的组件数据
     */
    loadChartData: function (componentData) {
        this.componentData = componentData;
        this.createChart();
    },
    /**
     * 根据模板类型过滤chart配置数据
     *
     * @param {}
     *            chart chart的配置参数
     * @return {}
     */
    filterChart: function (chart) {
        var tT = this.templateType;
        if (tT == 'quarters') {
            chart.showlegend = 0;// 四分屏模板不显示图例
        } else if (tT == 'single') {
            // chart.showlegend = 0;
            chart.legendposition = 'bottom';// 单分屏模板图例位置置于底部
        } else if (tT == 'classical') {
            // 默认
        } else {
            var s = this.chartType;
            chart.showValues = 1;
            if (s != 'Pie2D' && s != 'Pie3D' && s != 'Doughnut2D' && s != 'Doughnut3D')
                chart.showlegend = 0;// 不在维护范围内的模板不显示图例
        }
        return chart;
    },
    /**
     * 得到所有chart数据：样式+data+配置属性以及data的格式转换
     *
     * @return {}
     */
    getChartAllData: function () {
        var that = this, i, j, styles = that.getChartStyles(), chart = this
            .filterChart(that.getChartParams()), dData = that.componentData.displayData == null
            ? {}
            : that.componentData.displayData, dataArray = dData.data
            || [], modelArray = dData.model || [], len = modelArray.length;// 当范围或指标为空的时候,后台返回的是null,在前台的表现为dataArray和modelArray均未定义,因此当未定义时默认为[];

        var chartAllData = {};
        chartAllData.chart = chart;
        if (styles != null) {
            // 样式不为空则为chartAllData添加styles属性
            chartAllData.styles = styles;
        }

        if (that.isSimpleChart(that.chartType)) {
            // 随即产生一个0~modelArray.length之间的一个随即数。 用于随即展开一个扇
            var randomNum = Math.floor(Math.random() * (len - 1) + 0);
            var data = [];// 简单数据格式转换
            for (i = 0; i < len; i++) {
                var obj = {}, mc = modelArray[i];

                obj.label = mc;
                obj.color = that.getChartColor(that.chartType, i) || "";
                if (i == randomNum) {
                    obj.isSliced = '1';// 适用于饼、环的展开属性为1
                }
                obj.value = dataArray[0][mc];
                data.push(obj);
            }
            if (data.length != 0) {
                chartAllData.data = data;
            }
        } else if (that.isComplexChart(that.chartType)) {
            var categoryArray = [];// categories的数据转换
            for (i = 0; i < len; i++) {
                var obj = {};
                obj.label = modelArray[i];
                categoryArray.push(obj);
            }
            var datasetArray = [];// 转换dataset所需数据
            for (i = 0; i < dataArray.length; i++) {
                var obj = {};
                obj.seriesname = dataArray[i].seriesname;
                var _dataArray = [];
                for (j = 0; j < len; j++) {
                    var _obj = {};
                    _obj.value = dataArray[i][modelArray[j]];
                    _dataArray.push(_obj);
                }
                obj.data = _dataArray;
                obj.showvalues = 0,//适用于Stacked类型的图形
                    obj.color = that.getChartColor(that.chartType, i) || "";// 默认颜色为空
                datasetArray.push(obj);
            }
            // 格式转换后为chartAllData动态添加categories和datasheet属性
            if (categoryArray.length != 0 && datasetArray != 0) {
                chartAllData.categories = [
                    {
                        "category": categoryArray
                    }
                ];
                chartAllData.dataset = datasetArray;
            }
        } else {
            // 添加其他可能存在的情况或者子类覆盖此方法
        }
        // 如果数据为空
        if (dataArray.length == 0 || modelArray.length == 0) {
            // chartAllData.chart.bgSWF = './base/output/images/bg-border.gif';
            // 背景图需在同一个目录下
            // chartAllData.chart.canvasbgalpha = 30;
            // chartAllData.chart.fontsize = 40;
            // chartAllData.chart.bgcolor = 'DCD8EB';
            //chartAllData.chart.outCnvBaseFontSize = 40;
            // console.log(chartAllData);
            // 数据为空时设置的属性均不起作用,认为可能因为错误信息不在画布上
        }
        return chartAllData;
    },
    /**
     * 得到图形颜色放方法
     *
     * @param {}
     *            chartType 图形类型 根据类型调整不同的颜色列表
     * @param {}
     *            index 索引序号
     */
    getChartColor: function (chartType, index) {
        // 维护的默认的颜色列表
        var colorList = ["b2d9f8", "f6bc0c", "bbd66b", "f89f5b", "2ca9af",
            "e45f62", "ba84b6", "a1ba78", "ddd856"];
        //覆盖这里index随机取色也可
        try {
            if (index < colorList.length)
                return colorList[index];
            else
                return;
        } catch (e) {
            return;
        }
    },
    /**
     * 是否是简单图形
     *
     * @param {}
     *            chartType 图形类型
     * @return {Boolean} true表示该类型是简单类型
     */
    isSimpleChart: function (chartType) {
        var simpleChart = ["Pie2D", "Pie3D", "Area2D", "Column2D", "Column3D",
            "Line", "Bar2D", "Doughnut2D", "Doughnut3D", "ScrollArea2D"];// 维护的简单图形列表
        return this.isTrue(chartType, simpleChart);
    },
    /**
     * 是否是复杂图形
     *
     * @param {}
     *            chartType 图形类型
     * @return {Boolean} true表示该类型是复杂类型
     */
    isComplexChart: function (chartType) {
        var complexChart = ["MSColumn2D", "MSColumn3D", "MSLine", "MSBar3D",
            "MSBar2D", "MSArea"];// 维护的复杂图形列表
        return this.isTrue(chartType, complexChart);
    },
    /**
     * 判定是否维护在图形类型列表内
     *
     * @param {}
     *            chartType 图形类型
     * @param {}
     *            chartArray 图形维护数组
     * @return {Boolean} true值存在
     */
    isTrue: function (chartType, chartArray) {
        var i = 0, len = chartArray.length;
        for (; i < len; i++) {
            if (chartType == chartArray[i]) {
                return true;
            }
        }
        return false;
    },
    /**
     * 用于得到图形参数统一的参数配置（特殊参数配置放置在每个getChartParams方法内部实现）
     *
     */
    getCommenParamsForChart: function () {
        var caption = null, numbersuffix = null, chartData = this.componentData.displayData;//, yaxisname=null, xaxisname=null
        if (chartData != null) {
            caption = chartData.title;// 标题 caption 标题暂未让使用
            numbersuffix = chartData.unit;// 单位
            yaxisname = chartData.yaxisname;// y轴名称-暂未加入
            xaxisname = chartData.xaxisname;// x轴名称-暂未加入
        }
        /*
         * 关于饼、环形的问题：在fusioncharts中如果一个页面同时出现多个饼图或者环形图，则该图会变小（fusioncharts版本的bug,3.2中已修复）
         */
        allParamObj = {
            numbersuffix: numbersuffix || "",
            // yaxisname : yaxisname || "",
            // xaxisname : xaxisname || "",
            baseFontSize: '12',// 基本字体
            // outCnvBaseFontSize : '12',//画布之外字体
            baseFontColor: '6B6B6B',// 基本字体颜色
            showvalues: '0',// 是否显示值 1 是 0 否
            formatNumberScale: '0',// 是否格式化数字 1 是 会添加k m等英文单位
            showlabels: '0',// 是否显示标签
            legendBgColor: 'FFFFFF',// legend的背景色
            showlegend: '1',// 是否显示legend
            legendposition: 'right',// legend显示位置为右侧(四周均可)
            legendShadow: '1',// legend 的阴影显示
            legendBorderColor: 'FFFFFF',
            legendScrollBgColor: 'DEECFD',
            legendScrollBarColor: 'BAD1EF',
            legendScrollBtnColor: 'BAD1EF',
            // legendborderalpha : "0",// legend的边框宽度
            bgcolor: 'FFFFFF,FFFFFF',// 图形背景色
            showborder: '0',// 图形边框是否显示
            manageLabelOverflow: '1',// 字溢出时会自动隐藏
            useEllipsesWhenOverflow: '1',// 当溢出时,当Label溢出时候使用...将鼠标移至label处会显示
            // showAboutMenuItem : 0,// 是否显示关于fusioncharts
            aboutMenuItemLabel: '河南省精华科技有限公司',// 覆盖目录label
            aboutMenuItemLink: 'n-http://www.gilight.cn',
            zeroPlaneAlpha: '10',// 0线的像素
            use3DLighting: 1,// 是否使用3d光效果
            // defaultAnimation : 1,// 第一次是关闭动态展现
            // 添加下载
            // exportEnabled : 1,
            // exportShowMenuItem : 1,
            // exportFormats : 'PNG=导出png格式图片|JPG=导出jpg格式图片|PDF=导出pdf文件',
            // exportAction : 'download',
            // showExportDialog : 1,
            // exportAtClient : 0,
            // exportTargetWindow : '_slef',
            animation: '1'// 动态展现
        };
        if ('' != caption && null != caption) {
            allParamObj.caption = caption;// 主标题 这里后台不传递caption 其他使用者可以传递
            // 并可显示标题
        }
        return allParamObj;
    },
    /**
     * 子params覆盖父params 生产新的params
     *
     * @param {}
     *            childParam
     * @param {}
     *            parentParam
     * @return {} 返回新的params
     */
    coverChartParams: function (childParam, parentParam) {
        var newParams = null;
        for (var i in childParam) {
            parentParam[i] = childParam[i];
        }
        newParams = parentParam;
        return newParams;
    },
    /**
     * 得到图形配置参数 默认参数配置方法
     *
     * @return {} chart 图形配置参数
     */
    getChartParams: function () {
        var chart = this.getCommenParamsForChart();
        var baseChartParams = {
            canvasBorderThickness: '0',
            canvaspadding: "10"
        };
        for (var i in chart) {
            baseChartParams[i] = chart[i];
        }

        return baseChartParams;
    },
    /**
     * 得到图形样式
     *
     * @return {} styles 图形样式
     */
    getChartStyles: function () {
        var styles = {
            //"visibility": "inherit",
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