/*************************************************
 echarts图表指令
 功能介绍 ： 
	 	bar，line，area，spline，areaspline 生成柱状图和折线图
	 	数据格式 
	 	config = {
	         title : "图形标题",
	         yAxis : "Y轴单位",
	         data : [{field : '一月',name : '男' ,value : 200 },{field : '二月',name : '男' ,value : 200},
	                 {field : '一月',name : '女' ,value : 2020 },{field : '二月',name : '女' ,value : 2020}] ,
	         type :"bar"   //图表类型(bar,line,area,spline,areaspline)
	    }
	 	
	 	pie 生成饼状图
	 	数据格式 
	 	config = {
	 		type : 'pie',
	 		title: "饼状图标题",
	        data : [{name: '苹果', value: 30},{name: '橘子', value: 20}] }
 	
 height chart的高度，默认为300
 例子见 ： static/angular_expand/example/echart.jsp
 ************************************************/
system.directive('echart', ['echartService',function (service) {
    return {
        restrict: 'AE',
        scope: {
            config: "=",
            height: "@",
            onClick : "&"
        },
        link : function(scope, element, attrs) {
        	if (typeof(scope.height) == "undefined") { 
        		scope.height = 400;
    		}  
    		element.height(scope.height);
            scope.renderChart = function(){
            	// 基于准备好的dom，初始化echarts实例
            	var myChart = echarts.init(element[0]);
                if(scope.config){
                	var options = {};
                    if(scope.config.type){
                        switch (scope.config.type){
                            case 'bar' : ;
                            case 'line':;
                            case 'area':;
                            case 'spline':;
                            case 'areaspline':
                            	options = service.renderCommonChart(scope.config);
                                break;
                            case 'pie' :
                            	options = service.renderPieChart(scope.config);
                            	break;
                            default :
                            	options = scope.config;
                                break;
                        }
                    } else{
                    	options = scope.config;
                    	if(!options.grid){
                    		options.grid = {
    		                	 top : "60px",
    		                	 left: '1%',
    		                     right: '1%',
    		                     bottom: 40,
    		                     containLabel: true
                    		}
                    	}
                    	if(!options.toolbox){
	                    	if(options.series.length>0 && options.series[0].type == 'pie'){
	                    		options.toolbox = {
	            			        show : true,
	            			        feature : {
	            		        	    dataView : {show: true, readOnly: false},
	            			            restore : {show: true},
	            			            saveAsImage : {show: true}
	            			        }
	            			    };
	                    	}else{
	                    		options.toolbox = {
	            			        show : true,
	            			        feature : {
	            		        	    dataView : {show: true, readOnly: false},
	            			            magicType : {show: true, type: ['line', 'bar']},
	            			            restore : {show: true},
	            			            saveAsImage : {show: true}
	            			        }
	            			    };
	                    	}
                    	}
                    }
                    options.color = [ 
						'#b6a2de','#5ab1ef','#ffb980','#d87a80',
						'#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
						'#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050',
						'#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
                 ]
                    myChart.setOption(options);
                }
                if(scope.onClick){
               	 	myChart.on('click', function (params) {
                        scope.onClick({
                        	$params : params
                        });
                        scope.$apply();
                    });
                }
                $(window).resize(function(){
                	myChart.resize();
                });
            };
            scope.$watch("config",function(){
                scope.renderChart();
            });
        }
    };
}]);