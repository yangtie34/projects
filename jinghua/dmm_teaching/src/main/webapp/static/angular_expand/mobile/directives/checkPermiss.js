/**
 * 验证权限指令
 */

system.directive('checkPermiss',["httpService", function(http) {
	return {
		restrict : 'AE',
		scope: {},
		link : function(scope, element, attrs) {
			var data={};
			data.params=[];
			data.params.push(attrs.shirotag);
			data.service='checkPermiss?checkPermiss';
	  		http.callService(data).success(function(data){
	  		  if(data=="false"){
	  			$(element[0]).remove();
	  		  }
		    }); 
		}
	}
}]);
