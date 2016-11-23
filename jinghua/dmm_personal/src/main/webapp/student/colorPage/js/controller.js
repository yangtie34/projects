var app = angular.module('app', ['ngRoute','system']);

app.controller("controller",['$scope','colorPageService','dialog','$interval',function(scope,service,dialog,$interval){
	dialog.showLoading();
	service.getSchool().then(function(data){
		scope.schoolData = data;
		dialog.hideLoading();
	});
	service.getFdy().then(function(data){
		scope.fdy = data;
	});
	service.getKc().then(function(data){
		scope.kc = data;
	});
	service.getStu().then(function(data){
		scope.stu = data;
	});
	service.getTmx().then(function(data){
		scope.tmx = data;
	});
	service.getTx().then(function(data){
		scope.tx = data;
	});
	service.getYc().then(function(data){
		scope.yc = data;
	});
	service.getMajor().then(function(data){
		scope.major = data;
	});
	
	
}])


