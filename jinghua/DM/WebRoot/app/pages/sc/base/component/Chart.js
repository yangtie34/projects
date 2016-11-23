/**
 * 二维图形组件，封装fusioncharts(在此框架下不可用)
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午2:40
 *
 */
Ext.define('Exp.chart.Chart',{
    extend : 'Ext.Component',
    chartData:{},
    chartType:'',
    chartTheme:'',
    margin:0,
    listeners:{
        resize:function(){
            var width = this.getWidth(),
                height = this.getHeight();
            var wh = this._countChartWH(width,height);
            this.chart.resizeTo(width,height);
        }
    },
    afterRender : function(){
        this.padding = 0;// 屏蔽padding属性。
        this.callParent(arguments);
        var domId = this.getEl().id;
        var chartCfg = this._getChartCfg();
        Ext.apply(chartCfg,this.chartData);
        this.chart = new FusionCharts({type: this.chartType,
            width : '100%',
            height : '100%',
            dataFormat: "json",
            dataSource: chartCfg
        });
        this.chart.render(domId);
    },
    /**
     * 根据组件类型获取图形组件配置数据。
     * @return {Object}
     * @private
     */
    _getChartCfg:function(){
        var chartType = this.chartType;
        switch(chartType){
            case 'column2d':
                return {
                    "chart": {
                        "caption": "",
                        "subCaption": "",
                        "xAxisName": "",
                        "yAxisName": "",
                        "theme": this.chartTheme||'zune'
                    },
                    data:{}
                };
            case 'pie2d':
                return {
                    "chart": {
                        "caption": "",
                        "subCaption": "",
                        "enableSmartLabels": "0",
                        "showPercentValues": "1",
                        "showTooltip": "0",
                        "decimals": "1",
                        "theme": this.chartTheme||'zune'
                    }
                };
            case 'doughnut2d':
                return {
                    "chart": {
                        "centerLabel": "$label: $value",
                        "theme": this.chartTheme||'zune'
                }};
            case 'mscolumn2d':
                return {
                    "chart": {
                        "subCaption": " ",
                        "palettecolors": "#0099ff,#cc6666,#009900",
                        "bgcolor": "FFFFFF",
                        "legendposition": "RIGHT",
                        "showBorder": "0"
                    },
                    "categories": [
                        {
                            "category": [
                                {
                                    "label": ""
                                }
                            ]
                        }
                    ],
                    "dataset": [
                        {
                            "seriesname": "",
                            "data": []
                        }
                    ]
                };
            case 'maps/china':
                return {
                    "map": {
                        "animation": "0",
                        "showbevel": "0",
                        "usehovercolor": "1",
                        "canvasbordercolor": "FFFFFF",
                        "bordercolor": "FFFFFF",
                        "showlegend": "1",
                        "showshadow": "0",
                        "legendposition": "RIGHT",
                        "legendborderalpha": "0",
                        "legendbordercolor": "ffffff",
                        "legendallowdrag": "1",
                        "legendshadow": "0",
                        "connectorcolor": "000000",
                        "fillalpha": "80",
                        "hovercolor": "CCCCCC"
                },
                    "colorrange": {
                        "color": [
                            {
                                "minvalue": "500",
                                "maxvalue": "600",
                                "displayvalue": "> 500",
                                "code": "892C03"
                            },
                            {
                                "minvalue": "200",
                                "maxvalue": "500",
                                "displayvalue": "200 - 500",
                                "code": "e44a00"
                            },
                            {
                                "minvalue": "100",
                                "maxvalue": "200",
                                "displayvalue": "100 - 200",
                                "code": "f8bd19"
                            },
                            {
                                "minvalue": "90",
                                "maxvalue": "100",
                                "displayvalue": "< 100",
                                "code": "6baa01"
                            }
                        ]
                    },
                    data:{}
                };
            default:
                return {}
        }
    },
    /**
     * 根据给定的长度计算图形组件的合理宽高比。
     * @param width
     * @private
     */
    _countChartWH:function(width,height){
        var chartType = this.chartType;
        switch(chartType){
            case 'column2d':
                return {width:width,height:width*1/4};
            case 'pie2d':
                return {width:width,height:width/2};
            default:
                return {width:width,height:height}
        }
    },
    /**
     * 更改图形组件的数据。
     */
    update:function(data){
        if(this.chart){
            this.chart.setJSONData(Ext.apply(this.chart.getJSONData(),data));
        }
    }
});