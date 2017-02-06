var app = angular.module('app', ['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		controller : "homeController"
	})
	.when("/info", {
		templateUrl: "tpl/info.html",
		controller :  "infoController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

app.controller("homeController",['$scope','studentService','dialog',function(scope,service,dialog){
	dialog.showLoading();
	service.getStudentInfo().then(function(data){
		scope.personalInfo = data;
		dialog.hideLoading();
	});
}]).controller("infoController",['$scope','studentService','dialog','toastrService','locationService',function(scope,service,dialog,toastr,location){
	scope.changePasswdUrl = changePasswdUrl;
	
	scope.showUpdateTelForm = false;
	dialog.showLoading();
	service.queryStudentInfoDetail().then(function(data){
		scope.person = data; 
		scope.updateTel = data.tel;
		dialog.hideLoading();
	});
	
	scope.updateStudentTel = function(){
		dialog.showLoading();
		service.updateStudentTelephone(scope.updateTel).then(function(data){
			if(data == true){
				scope.showUpdateTelForm = false;
				scope.person.tel = scope.updateTel;
				toastr.success("联系方式更新成功！");
				dialog.hideLoading();
			}else{
				dialog.hideLoading();
				toastr.error("联系方式更新失败！");
			}
		});
	}
	
	scope.logout = function(){
		var ua = navigator.userAgent.toLowerCase(); 
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {
	    	location.redirect(base + "wechat/unbind");
	    } else { 
	    	location.redirect(logoutUrl);
	    } 
	}
}]);