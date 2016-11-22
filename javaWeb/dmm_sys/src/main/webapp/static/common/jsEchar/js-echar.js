(function($) {
	$.fn.echartpie = function(options) {
			var defaults = {
				pid:'1',
				title_text:'饼状图',
				title_subtext:'这个一个饼状图',
				title_x:'center',
				series_name:'当前',
				mark_show:false,
				dataView_show:false,
				dataView_readOnly:true
			};
			var $this = this;
			var options = $.extend(defaults, options);
	//		var obj=eval('('+options.data+')');
            options.dataObject=options.data;
			var dataString=[];
			var dataArray=[];
			for(var i=0;i<options.dataObject.length;i++){
				dataString[i]=options.dataObject[i].name;
				dataArray[i]=options.dataObject[i];
			}
			var myChart = echarts.init(this[0]); 
			var option = {
				title : {
					text: options.title_text,
					subtext: options.title_subtext,
					x:options.title_x
				},
				tooltip : {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					show:false,
					orient : 'vertical',
					x : 'left',
					data:dataString
				},
				toolbox: {
					show : false,
					x : 'right',
					feature : {
						mark : {show: options.mark_show},
						dataView : {show: options.dataView_show, readOnly: options.dataView_readOnly},
						magicType : {
							show: true, 
							type: ['pie', 'funnel'],
							option: {
								funnel: {
									x: '25%',
									width: '50%',
									funnelAlign: 'left',
									max: 1548
								}
							}
						},
						restore : {show: true},
						saveAsImage : {show: true}
					}
				},
				calculable : true,
				series : [
					{
						name:options.series_name,
						type:'pie',
						radius : ['50%', '70%'],
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false
                                },
                                labelLine : {
                                    show : false
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
						data:dataArray
					}
				]
			};
        myChart.setOption(option); 
        $.echartManager.put(myChart);
        return myChart;
    };

    $.fn.echartline = function(options,theme) {
        var defaults = {
            trigger:'axis',
            type:'line',
            pid:'1',
            title_text:'线图',
            title_subtext:'这个一个线图',
            series_name:'当前',
            mark_show:false,
            dataView_show:false,
            dataView_readOnly:true,
            unit:''
        };

        options = $.extend(defaults, options);

        var seriesObject=[];
        for(var key in options.legend){

            var tempData = options.series[options.legend[key]];
            var series = (function(data){
                var array = [];
                for(var i =0 ,len= data.length;i<len;i++){
                    var num = (!isNaN(Number(data[i].value)))?Number(data[i].value):data[i].value;
                    array.push(num);
                }
                var series={
                    name:options.legend[key],
                    type:options.type,
                    data:array,
                    markPoint:{data : [ {type : 'max', name: '最大值'},{type : 'min', name: '最小值'} ]},
                    markLine:{data : [{type : 'average', name: '平均值'} ] }
                };
                return series;
            })(tempData);

            seriesObject.push(series);

        }
        var myChart = echarts.init(document.getElementById(options.pid),theme?theme:'macarons');
        var option = {
            title : {
                show:false,
                text: options.title_text,
                subtext: options.title_text
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                show:false,
                data:options.legend
            },
            toolbox: {
                show : options.toolbox_show,
                feature : {
                    mark : {show: options.mark_show},
                    dataView : {show: options.dataView_show, readOnly: options.dataView_readOnly},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : options.xAxis
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLabel : {
                        formatter: '{value} '+options.unit
                    }
                }
            ],
            series : seriesObject
        };
        myChart.setOption(option);
        $.echartManager.put(myChart);
        return myChart;
    };

})(jQuery);