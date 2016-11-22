/**
 * @description 三种简单图形
 * @class SimpleChart
 * @author LJH
 * @date 2012-03-01
 * @param type  图形类型：line　线图, p2 ２Ｄ饼图，p3 ３Ｄ饼图，c2  2Ｄ柱图，c３　３Ｄ柱图
 * @param chartAttr 参数集合
 */
var SimpleChart = (function() {
    var attr = {};
    var chartId = "myChartId";
    var chartWidth = "100%";
    var chartHeight = "100%";
    var chartType = "c3";
    var toId = "";

    return function() {
        /**
         * @description 设置初始化参数
         */
        this.setParams = function() {
            var chartAttr = arguments[0];

            var param = {"chart":{
                /*"caption":chartAttr.caption||"",
                 "legendPosition":chartAttr.legendPosition||"right",
                 "showBorder":'1',
                 "borderAlpha":30,
                 "xaxisname":chartAttr.xAxisName||"",
                 "yaxisname":chartAttr.yAxisName||"",
                 "showvalues":1,
                 "showYAxisValues":0,
                 "divLineAlpha":0,
                 "showLimits":0,
                 "adjustDiv":0,
                 "numberprefix":chartAttr.numberPrefix||"" ,
                 "showCanvasBg":'0',
                 "canvasBaseDepth":chartAttr.canvasBaseDepth||'2',   */
                //-----以下属性由郭磊添加-2012-03-06
                "showLegend":chartAttr.showLegend||'0',
                "legendPosition":chartAttr.legendPosition||'bottom',
                "caption":chartAttr.caption || "",
                "showBorder":'0',
                //"canvasBorderAlpha":'0',
                //"canvasBorderThickness":0,
                "chartTopMargin":6,
                "chartBottomMargin":2,
                "chartRightMargin":2,
                "chartLeftMargin":2,
                "divLineAlpha":0,
                "showYAxisValues":0,
                "palette": "3",
                "animation":'1',
                "useroundedges": "1",
                "bgcolor": "FFFFFF,FFFFFF"
            },data:chartAttr.data, "styles": {
                "definition": [
                    {
                        "name": "Shadow_1",
                        "type": "Shadow",
                        "angle": "270" ,
                        "color":'CCCCCC',
                        "Alpha":"100" ,
                        blurX:"0" ,
                        blurY:"0",
                        Strength:"1",
                        "alpha":'100'
                    },
                    {
                        "name": "Shadow_2",
                        "type": "Shadow",
                        "angle": "90",
                        "color":'CCCCCC',
                        "Alpha":"100",
                        blurX:"0" ,
                        blurY:"0",
                        Strength:"1"
                    },
                    { "name":"MyXScaleAnim", "type":"ANIMATION", "duration":"1", "start":"0", "param":"_xScale"},
                    { "name":"MyYScaleAnim", "type":"ANIMATION", "duration":"1", "start":"0", "param":"_yScale" }
                ],
                "application": [
                    {
                        "toobject": "Canvas",
                        "styles": "Shadow_1,Shadow_2,MyXScaleAnim,MyYScaleAnim"
                    }
                ]
            }};


            /*
             var param = {"chart":{
             "caption":chartAttr.caption || "",
             "legendPosition":chartAttr.legendPosition || "right",
             "showBorder":'0',
             "borderAlpha":30,
             "xaxisname":chartAttr.xAxisName || "",
             "yaxisname":chartAttr.yAxisName || "",
             "showvalues":1,
             "showYAxisValues":0,
             "divLineAlpha":0,
             "showLimits":0,
             "adjustDiv":0,
             "numberprefix":chartAttr.numberPrefix || "" ,
             "bgColor":'FFFFFF',
             "showCanvasBg":'0',
             "canvasBaseDepth":chartAttr.canvasBaseDepth || '2'
             },
             data:chartAttr.data
             }*/

            attr = param;
            chartId = chartAttr.id;
            chartWidth = chartAttr.width;
            chartHeight = chartAttr.height;
            chartType = chartAttr.type;
        }
        /**
         * @description 调用REFRESH方法前,先调用此方法,用新数据填充图形
         * @param jsonData
         */
        this.changeData = function(jsonData) {
            attr.data = jsonData.data;
        }

        /**
         * @description 指定图形摆放位置
         * @param toID
         */
        this.renderTo = function(toID) {
            var myChart;
            toId = toID;
            if (chartType == "line") {
                myChart = new FusionCharts("FusionCharts/charts/Line.swf", chartId, chartWidth, chartHeight, "0", "1");
            } else if (chartType == "p2") {
                myChart = new FusionCharts("FusionCharts/charts/Pie2D.swf", chartId, chartWidth, chartHeight, "0", "1");
            } else if (chartType == "p3") {
                myChart = new FusionCharts("FusionCharts/charts/Pie3D.swf", chartId, chartWidth, chartHeight, "0", "1");
            } else if (chartType == "c2") {
                myChart = new FusionCharts("FusionCharts/charts/Column2D.swf", chartId, chartWidth, chartHeight, "0", "1");
            } else if (chartType == "c3") {
                myChart = new FusionCharts("FusionCharts/charts/Column3D.swf", chartId, chartWidth, chartHeight, "0", "1");
            }

            myChart.setJSONData(attr);
            myChart.render(toID);
        }
        /**
         * @description 刷新图形
         * @param chartID
         * @param jsonData
         */
        this.refresh = function() {
            var chart = FusionCharts(chartId);
            chart.setJSONData(attr);
            chart.render(toId);

        }
        /**
         * @description 动态改变图的属性
         * @param att
         * @param value
         */
        this.setAttribute = function(att, value) {
            var chart = FusionCharts(chartId);
            chart.setChartAttribute(att, value);
        }

    }
})();
