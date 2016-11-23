var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','$rootScope','bindService','locationService','toastrService','dialog',
                          function(scope,root,bindService,location,toast,dialog){
	scope.submit = function(user){
		toast.clear();
		scope.binding_success=false;
		scope.binding_goingon = true;
		bindService.bindUserInfo(user).then(function(data){
			scope.binding_goingon = false;
			//console.log(data)
			if(data.success){
				scope.binding_success=true;
				toast.success("绑定成功");
				location.redirect(base + "wechat/redirect");
			}else{
				toast.error(data.eroormsg); 
			}
		});
    };
}]);