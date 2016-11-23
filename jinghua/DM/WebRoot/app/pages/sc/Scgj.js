/**
 * highcharts 工具类
 */
NS.define('Pages.sc.Scgj', {
    /**
     * @param divId 目标divId
     * @param title chart标题
     * @param yAxis Y轴单位
     * @param data 数据
     * @param type 图表类型(column,line,area,spline,areaspline)
     *
     *      模板 data = [{name : '男' ,field : '一月',value : 200 },{name : '男' ,field : '二月',value : 200},
     * 				    {name : '女' ,field : '一月',value : 2020 },{name : '女' ,field : '二月',value : 2020}]
     */
    renderCommonChart : function(divId,title,yAxis,data,type,dw,sfqx){
        var isName ={} , isField = {};
        var fields = [],series = [];
        for(var i in data){
            var tar = data[i];
            if(!isName[tar.name]){
                series.push({name : tar.name,data : []});
                isName[tar.name] = true;
            }
            if(!isField[tar.field]){
                fields.push(tar.field);
                isField[tar.field] = true;
            }
        }
        fields.sort(function(a,b){return a>b?1:-1});
        var ser,fie,dat;
        for ( var j in series) {
            ser = series[j];
            for ( var k = 0; k < fields.length; k++) {
                fie = fields[k];
                for(var m = 0; m < data.length; m++){
                    dat = data[m];
                    if(dat.name == ser.name && dat.field == fie){
                        ser.data.push(parseFloat(dat.value));
                    }
                }
                if (ser.data.length < k) {
                    ser.data.push(0);
                }
            }
        }

        type = type || 'column';
        var config = {
            title: {
                text: title,
                x: -20 //center
            },
            chart: {
                type: type
            },
            xAxis: {
                categories: fields,
                labels: {
                    rotation:(sfqx)?-45:0 ,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                title: {
                    text: yAxis
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            legend: {
                borderWidth: 1,
                borderRadius : 3
            },
            tooltip: {
                valueSuffix:' '+(dw||''),
                shared: true
            },
            credits : {// 不显示highchart标记
                enabled : false
            },
            series:series
        };
        $('#' + divId).highcharts(config);
    },
    /**
     * @param divId 目标div
     * @param title 标题
     * @param yAxis 数据含义
     * @param data 数据
     * @param showLable 是否显示lable提示
     * 
     * 		data = [{name: '实践课', y: 62.5, num :200 },{name: '理论课', y: 37.5, num :120 }]
     */
    renderPieChart : function(divId,title,yAxis,data,showLable){
    	showLable = (showLable == null) ? true : showLable;
    	
        $('#' +divId).highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: title
            },
            tooltip: {
                pointFormat: '</b>{series.name}<b>{point.y}，占比<b>{point.percentage:.1f}%</b>'
            },
            /*tooltip: {
                pointFormat: ' 数量:<b>{point.y}</b><br/> {series.name}:<b>{point.percentage:.1f}%</b>'
            },*/
            credits : {// 不显示highchart标记
                enabled : false
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: showLable
                    }
                }
            },
            series: [{
                type: 'pie',
                name: yAxis,
                data: data
            }]
        });
    },
    translateData:function(data,cfg){
        var isName ={} , isField = {};
        var fields = [],series = [];
        for(var i in data){
            var tar = data[i];
            if(!isName[tar.name]){
                series.push({name : tar.name,data : []});
                isName[tar.name] = true;
            }
            if(!isField[tar.field]){
                fields.push(tar.field);
                isField[tar.field] = true;
            }
        }
        if(cfg.isSort) fields.sort(function(a,b){return a>b?1:-1});
        var ser,fie,dat;
        for ( var j in series) {
            ser = series[j];
            for ( var k = 0; k < fields.length; k++) {
                fie = fields[k];
                for(var m = 0; m < data.length; m++){
                    dat = data[m];
                    if(dat.name == ser.name && dat.field == fie){
                        ser.data.push(parseFloat(dat.value));
                    }
                }
                if (ser.data.length < k) {
                    ser.data.push(0);
                }
            }
        }

        var config = {
            title: {
                text: cfg.title,
                x: -20 //center
            },
            chart: {
                type: cfg.type || 'column'
            },
            xAxis: {
                categories: fields,
                labels: {
                    rotation:(cfg.sfqx)?-45:0 ,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                title: {
                    text: cfg.yAxis
                },
                min: 0,
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 10,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            tooltip: {
                valueSuffix:' ',
                shared: true
            },
            credits : {// 不显示highchart标记
                enabled : false
            },
            series:series
        };
        return config;
    },
    /**
     * 获取饼图配置数据
     * @params sfby 是否半圆
     * cfg.title
     * cfg.data
     */
    getPidCfg :function(sfby,cfg){
        var chartCfg = {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: 0,
                plotShadow: false
            },
            title: {
                text: cfg.title||'',
                align: 'center',
                verticalAlign: 'middle',
                y: 40
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    dataLabels: {
                        enabled: true,
                        distance: -20,
                        style: {
                            fontWeight: 'bold',
                            color: 'white',
                            textShadow: '0px 1px 2px black'
                        }
                    },
                    startAngle: -90,
                    endAngle: 90,
                    center: ['50%', '75%']
                }
            },
            series: [{
                type: 'pie',
                name:  cfg.title||'',
                innerSize: '60%',
                data: cfg.data||[]
            }]
        };
        if(!sfby){
            chartCfg = {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: cfg.title||''
                },
                tooltip: {
                    pointFormat: '{series.name}:<b>{point.percentage:.1f}%</b>',
                    valueSuffix:' ',
                    shared: true
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: showLable
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: yAxis,
                    data: data
                }]
            };
        }
        return chartCfg;
    }
});