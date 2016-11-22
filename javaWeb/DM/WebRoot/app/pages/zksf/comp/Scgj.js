/**
 * highcharts 工具类
 */
NS.define('Pages.zksf.comp.Scgj', {
    /**
     * @param configs 配置对象
     *
     *  模板 configs = {
				 divId : "zksf_yqsbxx_top_chart",
	    	     title : "  ",
	    	     yAxis : "件",
	    	     isSort : false,
	    	     data : [{name : '男' ,field : '一月',value : 200 },{name : '男' ,field : '二月',value : 200},
	    	             {name : '女' ,field : '一月',value : 2020 },{name : '女' ,field : '二月',value : 2020}] ,
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
            xAxis: {
                categories: fields,
                labels: {
                    rotation:(configs.sfqx)?-45:0 ,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
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
            legend: {
                borderWidth: 1,
                borderRadius : 3,
                verticalAlign: 'middle',
                layout: 'vertical',
                align: 'right'
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
        $('#' + configs.divId).highcharts(config);
        return $('#' + configs.divId).highcharts();
    },
    /**
     * @param config 配置对象
     * 
     * 	config = {
      		divId :"zksf_yqsbxx_bottom_pie",
			title: " ",
			data : [{name: '实践课', y: 62.5, num :200 },{name: '理论课', y: 37.5, num :120 }],
			showLable: false}
     */
    renderPieChart : function(config){
    	 config.showLable = (config.showLable == null) ? true : config.showLable;
    	 
    	 var data = config.data;
    	 for ( var i in data)data[i].y = parseFloat(data[i].y);
    	 
    	 $('#' +config.divId).highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: config.title
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
        });
    	
        return  $('#' +config.divId).highcharts();
    }
});