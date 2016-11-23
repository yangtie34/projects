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
	scope.sendRequest = function(){
		service.sendRequest(username).then(function(data){
			if(data.success){
				toastr.info("请求发送成功！");
			}else{
				toastr.error("请求发送失败！");
			}
		})
	}
	dialog.hideLoading();
}]);
