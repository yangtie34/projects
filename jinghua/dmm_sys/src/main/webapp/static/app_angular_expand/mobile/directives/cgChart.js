/*************************************************
 图表指令
 ************************************************/
jxpgmobile.directive('cgChart', ['highChartService',function (service) {
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
                            case 'column' : ;
                            case 'line':;
                            case 'area':;
                            case 'spline':;
                            case 'areaspline':
                                element.highcharts(service.renderCommonChart(scope.config));
                                break;
                            case 'pie' :
                                element.highcharts(service.renderPieChart(scope.config));
                                break;
                            default :
                                break;
                        }
                    } else{
                        scope.config = angular.extend(scope.config,{
                            credits : {         // 不显示highchart标记
                                enabled : false
                            }
                        });
                        element.highcharts(scope.config);
                    }
                }
            };
            scope.$watch("config",function(){
                scope.renderChart();
            });
        }
    };
}]);