/**
 * echartService
 * 根据传入的config 装配成echartService需要的 config
 * renderCommonChart //图表类型(bar,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */

system.factory('echartService',function(){
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
            type = configs.type || 'column';
            var isName ={} , isField = {};
            var fields = [],series = [],legendData=[];
            for(var i in configs.data){
                var tar = configs.data[i];
                if(!isName[tar.name]){
                	legendData.push(tar.name);
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
                ser.type = type;
                if(ser.type == 'area'){
                	ser.type = 'line';
                	ser.areaStyle = {normal: {}};
                }
                if(ser.type == 'spline'){
                	ser.type = 'line';
                	ser.smooth = true;
                }
                if(ser.type == 'areaspline'){
                	ser.type = 'line';
                	ser.smooth = true;
                	ser.areaStyle = {normal: {}};
                }
                
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < configs.data.length; m++){
                        dat = configs.data[m];
                        if(dat.name == ser.name && dat.field == fie){
                            ser.data.push(parseFloat(dat.value));
                        }
                    }
                    if (ser.data.length < k){
                        ser.data.push(0);
                    }
                }
            };
            var axisLabelRotate = 0;
            if(fields.length > 5){
            	axisLabelRotate = 45;
            }
            
            // 指定图表的配置项和数据
            var config = {
                title: {
                    text: configs.title,
                    left : 'center'
                },
                grid: {
                	 top : "40px",
                	 left: '1%',
                     right: '1%',
                     bottom: '30px',
                     containLabel: true
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : { // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    left: 'center',
                    bottom : 'bottom',
                    data:legendData
                },
                xAxis: {
                    data: fields,
                    axisLabel:{
                    	rotate:axisLabelRotate,
                    	interval: 0
                    }
                },
                yAxis : [{
                	name : configs.yAxis,
                    type : 'value'
                }],
                series:  series
            };
            return config;
        },
        /**
         * @param config 配置对象
         *
         * 	config = {
         * 		type : 'pie',
	            title: "饼状图标题",
	            data : [{name: '苹果', value: 30},{name: '橘子', value: 20}]
	        }
         */
        renderPieChart : function(config){
    	   var data = config.data;
    	   config.showLable = (config.showLable == null) ? true : config.showLable;
        	option = {
        		    title : {
        		        text: config.title,
        		        x:'center'
        		    },
        		    grid: {
	                   	top : "middle",
	                   	left: '1%',
                        right: '1%',
                        containLabel: false
                   },
        		    tooltip : {
        		        trigger: 'item',
        		        formatter: "{b} <br/> 数量：{c} &nbsp;&nbsp;(占比{d}%)"
        		    },
        		    label: {
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '20',
                                fontWeight: 'bold'
                            }
                        }
                    },
        		    series : [
        		        {
        		            type: 'pie',
        		            radius : '70%',
        		            center: ['50%', '55%'],
        		            avoidLabelOverlap: false,
        		            selectedMode: 'single',
        		            data: data,
        		            itemStyle: { 
        		                emphasis: {
        		                    shadowBlur: 10,
        		                    shadowOffsetX: 0,
        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
        		                }
        		            }
        		        }
        		    ]
        		};
        	return option;
        }
    };
});