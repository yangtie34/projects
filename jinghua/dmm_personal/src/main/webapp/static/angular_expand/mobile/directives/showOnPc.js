/*************************************************
 showOnPc图表指令
  
 ************************************************/
system.directive('showOnPc', ['browserCheckService',function (service) {
    return {
        restrict: 'AE',
        link : function(scope, element, attrs) {
        	if(service.checkDevice() == 'pc'){
        	}else{
        		 element.remove();
        	}
        }
    };
}]);