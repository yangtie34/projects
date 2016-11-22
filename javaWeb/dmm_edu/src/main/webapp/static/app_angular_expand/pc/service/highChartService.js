/**
 * highchart service
 * 根据传入的config 装配成highcharts需要的 config
 * renderCommonChart //图表类型(column,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */

jxpg.factory('highChartService',function(){
	Highcharts.setOptions({
        chart: {
            style: {
                fontFamily:"微软雅黑" 
            }
        }
    });
    return {
        /**
         * @param configs 配置对象
         *
         *  模板 configs = {
             title : "  ",
             yAxis : "件",
             isSort : false,
             data : [{field : '一月',name : '男' ,value : 200 },{field : '二月',name : '男' ,value : 200},
                     {field : '一月',name : '女' ,value : 2020 },{field : '二月',name : '女' ,value : 2020}] ,
             type :"column"   //图表类型(column,line,area,spline,areaspline)
        }
         */
        renderCommonChart : function(configs){
            configs.isSort = configs.isSort || false;
            var isName ={} , isField = {};
            var fields = [],series = [];
            for(var i in configs.data){
                var tar = configs.data[i];
                if(!isName[tar.name]){
                    series.push({name : tar.name,data : []});
                    isName[tar.name] = true;
                }
                if(!isField[tar.field]){
                    fields.push(tar.field);
                    isField[tar.field] = true;
                }
            }
            if(configs.isSort) fields.sort(function(a,b){return a>b?1:-1;});
            var ser,fie,dat;
            for ( var j in series) {
                ser = series[j];
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < configs.data.length; m++){
                        dat = configs.data[m];
                        if(dat.name == ser.name && dat.field == fie){
                            ser.data.push(parseFloat(dat.value));
                        }
                    }
                    if (ser.data.length < k) {
                        ser.data.push(0);
                    }
                }
            }

            type = configs.type || 'column';
            var config = {
                title: {
                    text: configs.title,
                    x: -20 //center
                },
                chart: {
                    type: type
                },
                xAxis: [{
                    categories: fields,
                    crosshair: true
                }],
                yAxis: {
                    title: {
                        text: configs.yAxis
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix:' '+(configs.dw||''),
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
         * @param config 配置对象
         *
         * 	config = {
            title: " ",
            data : [{name: '实践课', y: 62.5},{name: '理论课', y: 37.5},{name: '生物', y: 300}],
            showLable: false}
         */
        renderPieChart : function(config){
            config.showLable = (config.showLable == null) ? true : config.showLable;
            var data = config.data;
            var result = {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: config.title,
                },
                tooltip: {
                    pointFormat: ' 数量:<b>{point.y}</b><br/> {series.name}:<b>{point.percentage:.1f}%</b>'
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: config.showLable
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: "占比",
                    data: data
                }]
            };
            return  result;
        }
    };
});