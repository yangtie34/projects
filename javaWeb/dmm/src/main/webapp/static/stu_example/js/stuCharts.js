/*************************************************
 图表指令
 ************************************************/
app.directive('stuChart', ['echartService',function (service) {
    return {
        restrict: 'AE',
        scope: {
            config: "="
        },
        link : function(scope, element, attrs) {
            scope.renderChart = function(){
                if(scope.config ){
                    if(scope.config.type){
                        switch (scope.config.type){
                            case 'column' :;
                            case 'line':;
                            case 'area':
                                var myChart = echarts.init(element.context,'macarons');
                             	 myChart.setOption(service.renderAreaChart(scope.config));
                             	  break;
                            case 'columnf':
                            var myChart = echarts.init(element.context,'macarons');
                             	 myChart.setOption(service.renderColumnChart(scope.config));
                             	  break;
                            case 'areaspline':;
                            case 'map' :
                             	var myChart = echarts.init(element.context,'macarons');
                             	myChart.setOption(service.renderMapChart(scope.config));
                                break;
                            default :
                                break;
                        }
                    } else{
//                        scope.config = angular.extend(scope.config,{
//                            credits : {         // 不显示highchart标记
//                                enabled : false
//                            }
//                        });
//                        element.echarts(scope.config);
                    }
                      window.addEventListener("resize", function () {
                      myChart.resize();

                  });
                }
                  
            };
            scope.$watch("config",function(){
                scope.renderChart();
            });
        }
    };
}]);