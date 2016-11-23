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
		location.redirect(base + "wechat/unbind");
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
	.otherwise({
		redirectTo : "/home"
	});
}]);