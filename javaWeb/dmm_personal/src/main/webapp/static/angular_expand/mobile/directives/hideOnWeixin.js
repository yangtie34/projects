/*************************************************
 showOnPc图表指令
  
 ************************************************/
system.directive('hideOnWeixin', ['browserCheckService',function (service) {
    return {
        restrict: 'AE',
        link : function(scope, element, attrs) {
        	if(service.checkIsWeixin() == true){
        		element.remove();
        	}
        }
    };
}]);