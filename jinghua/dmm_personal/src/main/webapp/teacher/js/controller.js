var app = angular.module('app', ['ngRoute','system']);
app.controller("homeController",['$scope','teacherService','dialog',
    function(scope,teacherService,dialog){
		dialog.showLoading();
        teacherService.getTeacherInfo()
        .then(function(data){
        	scope.personalInfo = data;
        	dialog.hideLoading();
        });
}]).controller("infoController",['$scope','teacherService','dialog','locationService',function(scope,service,dialog,location){
	dialog.showLoading();
	service.queryPersonalInfoDetail().then(function(data){
		scope.person = data;
		dialog.hideLoading();
	});
	scope.logout = function(){
	    var ua = navigator.userAgent.toLowerCase(); 
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {
	    	location.redirect(base + "wechat/unbind");
	    } else { 
	    	location.redirect(logoutUrl);
	    } 
	}
}]).controller("historyController",['$scope','$rootScope','teacherService','dialog',function(scope,root,teacherService,dialog){
	dialog.showLoading();
	teacherService.queryTeacherHistoryList().then(function(data){
		scope.historyList = data;
		return teacherService.queryTeacherHistoryInfo();
	}).then(function(dt){
		dialog.hideLoading();
		scope.historyInfo = dt;
	});
}]).controller("feedbackController",['$scope','$rootScope','teacherService','dialog','toastrService',function(scope,root,teacherService,dialog,toast){
	scope.submit = function(){
		var advice = $("#advice").val();
		teacherService.submitAdvice(advice).then(function(data){
			if(data.success){
				toast.info("意见反馈成功！");
				window.history.back(-1);
			}
		});
	}
}]);


app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "homeController"
	})
	.when("/info", {
		templateUrl: "tpl/info.html",
		controller :  "infoController"
	})
	.when("/history", {
		templateUrl: "tpl/history.html",
		controller : "historyController"
	})
	.when("/feedback", {
		templateUrl: "tpl/feedback.html",
		controller : "feedbackController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);