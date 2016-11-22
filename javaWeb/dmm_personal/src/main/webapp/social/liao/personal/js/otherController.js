var app = angular.module('app', ['ngRoute','system']);
app.controller("controller",['$scope','dialog','toastrService','personalService',function(scope,dialog,toastr,service){
	dialog.showLoading();
	var username = $("#um").attr("value");
	service.queryPersonalInfo(username).then(function(data){
		if(data.success){
			scope.personalInfo = data.result;
		}else{
			toastr.error("数据请求失败！");
		}
	});
	service.queryCard(username).then(function(data){
		if(data.success){
			scope.card = data.result;
		}else{
			toastr.error("数据请求失败！");
		}
	});
	service.queryBook(username).then(function(data){
		if(data.success){
			scope.book = data.result;
		}else{
			toastr.error("数据请求失败！");
		}
	});
	service.queryCourse(username).then(function(data){
		if(data.success){
			scope.course = data.result;
		}else{
			toastr.error("数据请求失败！");
		}
	})
	dialog.hideLoading();
}]);
